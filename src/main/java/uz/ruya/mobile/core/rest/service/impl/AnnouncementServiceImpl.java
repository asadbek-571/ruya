package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.auth.AuthUser;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.announcement.AnnouncementDetails;
import uz.ruya.mobile.core.rest.entity.announcement.Category;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.peyload.base.AddressDto;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;
import uz.ruya.mobile.core.rest.peyload.req.ReqPaging;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.peyload.res.ResPaging;
import uz.ruya.mobile.core.rest.peyload.res.ResPagingParams;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOne;
import uz.ruya.mobile.core.rest.repo.announcement.AnnouncementRepo;
import uz.ruya.mobile.core.rest.repo.announcement.CategoryRepo;
import uz.ruya.mobile.core.rest.service.AnnouncementService;

import java.util.List;
import java.util.stream.Collectors;

/**
 Asadbek Kushakov 1/3/2025 6:25 PM 
 */

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final AnnouncementRepo announcementRepo;
    private final CategoryRepo categoryRepo;

    private final MessageSingleton messageSingleton;

    @Override
    public SuccessMessage addJobAnnouncement(ReqAddAnnouncement request) throws EntityNotFoundException {

        AuthUser authUser = GlobalVar.getAuthUser();
        UserProfile profile = authUser.getProfile();

        Category category = categoryRepo.findById(request.getCategoryId()).orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

        AnnouncementDetails details = new AnnouncementDetails();
        details.setJobType(request.getJobType());
        details.setWorkingType(request.getWorkingType());

        if (CoreUtils.isPresent(request.getPrice())) {
            ReqAmount price = request.getPrice();
            details.setPrice(price.getAmount());
            details.setCurrency(price.getCurrency());
        }

        Announcement announcement = new Announcement();
        announcement.setDescription(request.getDescription());
        announcement.setTitle(request.getTitle());
        announcement.setType(request.getType());
        announcement.setAppliedQty(0);

        if (CoreUtils.isPresent(request.getAddress())) {
            AddressDto address = request.getAddress();
            announcement.setAddress(address.getAddressName());
            announcement.setAddressLong(address.getAddressLongitude());
            announcement.setAddressLat(address.getAddressLatitude());
        }

        announcement.setUser(profile);
        announcement.setCategory(category);

        details.setAnnouncement(announcement);
        announcement.setDetails(details);

        announcementRepo.save(announcement);

        return new SuccessMessage(messageSingleton.getMessage(MessageKey.SUCCESS));
    }

    @Override
    public ResPaging<ResAnnouncementOne> announcementPage(ReqAnnouncement request) {
        ReqPaging paging = request.getPaging();
        PageRequest pageRequest = PageRequest.of(paging.getPage(), paging.getSize());

        Specification<Announcement> spec = buildSpecifications(request.getFilter());

        Page<Announcement> page = announcementRepo.findAll(spec, pageRequest);

        List<ResAnnouncementOne> list = page.stream()
                .map(ResAnnouncementOne::new)
                .collect(Collectors.toList());

        return new ResPaging<>(list, new ResPagingParams(page.getNumber(), page.getTotalPages(), page.getTotalElements()));
    }

    private Specification<Announcement> buildSpecifications(ReqAnnouncement.Filter filter) {

        if (CoreUtils.isEmpty(filter)) {
            return Specification.where(null);
        }

        Specification<Announcement> spec = Specification.where(null);

        if (CoreUtils.isPresent(filter.getJobCategoryId())) {
            spec = spec.and(this.hasJobCategory(filter.getJobCategoryId()));
        }
        if (CoreUtils.isPresent(filter.getType())) {
            spec = spec.and(this.hasType(filter.getType()));
        }

        return spec;
    }

    public Specification<Announcement> hasJobCategory(Long id) {
        return (root, query, criteriaBuilder) -> CoreUtils.isEmpty(id) ? null : criteriaBuilder.equal(root.get("category_id"), id);
    }

    public Specification<Announcement> hasType(AnnouncementType type) {
        return (root, query, criteriaBuilder) -> CoreUtils.isEmpty(type) ? null : criteriaBuilder.equal(root.get("type"), type);
    }

}
