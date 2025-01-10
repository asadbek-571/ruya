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
import uz.ruya.mobile.core.rest.entity.announcement.AnnouncementParameter;
import uz.ruya.mobile.core.rest.entity.announcement.Category;
import uz.ruya.mobile.core.rest.entity.announcement.CategoryParam;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.peyload.base.AddressDto;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.ReqPaging;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqCategory;
import uz.ruya.mobile.core.rest.peyload.res.ResPaging;
import uz.ruya.mobile.core.rest.peyload.res.ResPagingParams;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOne;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResCategoryList;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResCategoryParameters;
import uz.ruya.mobile.core.rest.repo.announcement.AnnouncementParameterRepo;
import uz.ruya.mobile.core.rest.repo.announcement.AnnouncementRepo;
import uz.ruya.mobile.core.rest.repo.announcement.CategoryParamRepo;
import uz.ruya.mobile.core.rest.repo.announcement.CategoryRepo;
import uz.ruya.mobile.core.rest.service.AnnouncementService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 Asadbek Kushakov 1/3/2025 6:25 PM 
 */

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {

    private final MessageSingleton messageSingleton;

    private final CategoryRepo categoryRepo;
    private final CategoryParamRepo categoryParamRepo;
    private final AnnouncementRepo announcementRepo;
    private final AnnouncementParameterRepo announcementParameterRepo;

    @Override
    public ResCategoryList getCategory(ReqCategory request) {
        List<Category> categories = CoreUtils.isPresent(request.getParentId())
                ? categoryRepo.findAllByParentId(request.getParentId())
                : categoryRepo.findAllByParentIdIsNull();

        List<ResCategoryList.ResCategoryOne> list = categories.stream()
                .map(ResCategoryList.ResCategoryOne::new)
                .collect(Collectors.toList());

        return new ResCategoryList(list);
    }

    @Override
    public ResCategoryParameters getCategoryParam(ReqLongId request) throws EntityNotFoundException {

        List<CategoryParam> params = categoryParamRepo.findAllByCategoryId(request.getId());

        Optional<CategoryParam> optionalMainParam = params.stream().filter(categoryParam -> Boolean.TRUE.equals(categoryParam.getIsMainParent())).findFirst();

        if (optionalMainParam.isEmpty()) {
            throw new EntityNotFoundException(messageSingleton.getMessage(MessageKey.PARAM_NOT_FOUND));
        }

        ResCategoryParameters result = new ResCategoryParameters(optionalMainParam.get());

        List<CategoryParam> parenParams = params
                .stream()
                .filter(p -> CoreUtils.isEmpty(p.getParentId()) && !p.getIsMainParent())
                .sorted(Comparator.comparing(CategoryParam::getOrder))
                .collect(Collectors.toList());

        List<ResCategoryParameters.Param> parameters = new ArrayList<>();
        for (CategoryParam parent : parenParams) {

            List<CategoryParam> childParams = params
                    .stream()
                    .filter(child -> parent.getId().equals(child.getParentId()))
                    .sorted(Comparator.comparing(CategoryParam::getOrder))
                    .collect(Collectors.toList());

            ResCategoryParameters.Param param = new ResCategoryParameters.Param(parent);

            List<ResCategoryParameters.ParamValue> values = new ArrayList<>();
            List<ResCategoryParameters.ParamUnits> units = new ArrayList<>();
            for (CategoryParam child : childParams) {
                if (parent.getIsHaveUnist() && child.getIsUnit()) {
                    units.add(new ResCategoryParameters.ParamUnits(child));
                } else {
                    values.add(new ResCategoryParameters.ParamValue(child));
                }
            }

            param.setValues(values);
            param.setUnits(units);
            parameters.add(param);
        }

        result.setParameters(parameters);
        return result;
    }

    @Override
    public SuccessMessage addJobAnnouncement(ReqAddAnnouncement request) throws EntityNotFoundException {

        AuthUser authUser = GlobalVar.getAuthUser();
        UserProfile profile = authUser.getProfile();

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

        Announcement announcement = new Announcement();
        announcement.setUser(profile);
        announcement.setCategory(category);
        announcement.setDescription(request.getDescription());
        announcement.setTitle(request.getTitle());
        announcement.setAppliedQty(0);

        if (CoreUtils.isPresent(request.getPrice())) {
            ReqAmount price = request.getPrice();
            announcement.setAmount(price.getAmount());
            announcement.setCurrency(price.getCurrency());
        }

        if (CoreUtils.isPresent(request.getAddress())) {
            AddressDto address = request.getAddress();
            announcement.setAddress(address.getAddressName());
            announcement.setAddressLong(address.getAddressLongitude());
            announcement.setAddressLat(address.getAddressLatitude());
        }

        announcement = announcementRepo.save(announcement);
        if (CoreUtils.isPresent(request.getParams())) {

            List<AnnouncementParameter> parameters = new ArrayList<>();
            for (ReqAddAnnouncement.ReqAddAnnouncementParam param : request.getParams()) {
                parameters.add(new AnnouncementParameter(param, announcement));
            }
            announcementParameterRepo.saveAll(parameters);
        }

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

        if (CoreUtils.isPresent(filter.getCategoryId())) {
            spec = spec.and(this.hasJobCategory(filter.getCategoryId()));
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
