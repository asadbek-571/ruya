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
import uz.ruya.mobile.core.config.utils.DateUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.announcement.AnnouncementParameter;
import uz.ruya.mobile.core.rest.entity.announcement.Category;
import uz.ruya.mobile.core.rest.entity.announcement.CategoryParam;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.peyload.base.AddressDto;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.ReqPaging;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqCategory;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqCategoryParameters;
import uz.ruya.mobile.core.rest.peyload.res.ResPaging;
import uz.ruya.mobile.core.rest.peyload.res.ResPagingParams;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOne;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOneFull;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResCategoryList;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResCategoryParameters;
import uz.ruya.mobile.core.rest.repo.announcement.AnnouncementParameterRepo;
import uz.ruya.mobile.core.rest.repo.announcement.AnnouncementRepo;
import uz.ruya.mobile.core.rest.repo.announcement.CategoryParamRepo;
import uz.ruya.mobile.core.rest.repo.announcement.CategoryRepo;
import uz.ruya.mobile.core.rest.service.AnnouncementService;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.*;
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
    public ResCategoryParameters getCategoryParam(ReqCategoryParameters request) throws EntityNotFoundException {

        Category category = categoryRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

        List<CategoryParam> params = categoryParamRepo.findAllByCategoryAndIsFilter(category, request.getIsFilter());

        Optional<CategoryParam> optionalMainParam = params.stream().filter(categoryParam -> Boolean.TRUE.equals(categoryParam.getIsMainParent())).findFirst();

        if (optionalMainParam.isEmpty()) {
            throw new EntityNotFoundException(messageSingleton.getMessage(MessageKey.PARAM_NOT_FOUND));
        }

        ResCategoryParameters result = new ResCategoryParameters(category, optionalMainParam.get());

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
        announcement.setAttachmentIds(request.getAttachments());
        announcement.setDescription(request.getDescription());
        announcement.setTitle(request.getTitle());
        announcement.setType(request.getType());
        announcement.setCategory(category);
        announcement.setUser(profile);
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
    public ResPaging<ResAnnouncementOne> announcementPage(ReqAnnouncement request) throws EntityNotFoundException {
        ReqPaging paging = request.getPaging();
        PageRequest pageRequest = PageRequest.of(paging.getPage(), paging.getSize());

        Specification<Announcement> spec = buildSpecifications(request.getFilter());

        Page<Announcement> page = announcementRepo.findAll(spec, pageRequest);

        List<ResAnnouncementOne> list = page.stream()
                .map(ResAnnouncementOne::new)
                .collect(Collectors.toList());

        return new ResPaging<>(list, new ResPagingParams(page.getNumber(), page.getTotalPages(), page.getTotalElements()));
    }

    @Override
    public ResAnnouncementOneFull announcementDetails(ReqLongId request) throws EntityNotFoundException {

        Announcement announcement = announcementRepo.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.ENTITY_NOT_FOUND)));


        return null;
    }

    private Specification<Announcement> buildSpecifications(ReqAnnouncement.Filter filter) throws EntityNotFoundException {

        if (CoreUtils.isEmpty(filter)) {
            return Specification.where(null);
        }

        Specification<Announcement> spec = Specification.where(null);

        if (CoreUtils.isPresent(filter.getCategoryId())) {
            Category category = categoryRepo.findById(filter.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

            spec = spec.and(this.hasJobCategory(category));
        }
        if (CoreUtils.isPresent(filter.getType())) {
            spec = spec.and(this.hasType(filter.getType()));
        }

        if (CoreUtils.isPresent(filter.getTitle())) {
            spec = spec.and(this.hasTitle(filter.getTitle()));
        }

        if (CoreUtils.isPresent(filter.getFromDate()) && CoreUtils.isPresent(filter.getToDate())) {
            LocalDateTime from = DateUtils.parseFLocalDateTime(filter.getFromDate());
            LocalDateTime to = DateUtils.parseFLocalDateTime(filter.getToDate());
            spec = spec.and(this.hasDate(from, to));
        }

        if (CoreUtils.isPresent(filter.getFromAmount()) && CoreUtils.isPresent(filter.getToAmount())) {
            spec = spec.and(this.hasAmount(filter.getFromAmount(), filter.getToAmount()));
        }

        if (CoreUtils.isPresent(filter.getCurrencyType())) {
            spec = spec.and(this.hasCurrency(filter.getCurrencyType()));
        }

        if (CoreUtils.isPresent(filter.getParamFilters())) {
            spec = spec.and(this.hasParamFilter(filter.getParamFilters()));
        }

        // AnnouncementParameter orqali filter qilish

        return spec;
    }

    public Specification<Announcement> hasJobCategory(Category category) {
        return (root, query, cb) -> CoreUtils.isEmpty(category) ? null : cb.equal(root.get("category"), category);
    }

    public Specification<Announcement> hasAmount(Long from, Long to) {
        return (root, query, cb) -> CoreUtils.isEmpty(from) && CoreUtils.isEmpty(to) ? null : cb.between(root.get("amount"), from, to);
    }

    public Specification<Announcement> hasCurrency(CurrencyType type) {
        return (root, query, cb) -> CoreUtils.isEmpty(type) ? null : cb.equal(root.get("currency"), type);
    }

    public Specification<Announcement> hasDate(LocalDateTime from, LocalDateTime to) {
        return (root, query, cb) -> CoreUtils.isEmpty(from) && CoreUtils.isEmpty(to) ? null : cb.between(root.get("createdAt"), from, to);
    }

    public Specification<Announcement> hasType(AnnouncementType type) {
        return (root, query, cb) -> CoreUtils.isEmpty(type) ? null : cb.equal(root.get("type"), type);
    }

    public Specification<Announcement> hasTitle(String title) {
        return (root, query, cb) -> CoreUtils.isEmpty(title) ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public Specification<Announcement> hasParamFilter(Map<String, String> filterMap) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filterMap.forEach((key, value) -> {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<AnnouncementParameter> paramRoot = subquery.from(AnnouncementParameter.class);
                subquery.select(paramRoot.get("announcement").get("id"))
                        .where(cb.and(
                                cb.equal(paramRoot.get("key"), key),
                                cb.equal(paramRoot.get("value"), value)
                        ));
                predicates.add(cb.in(root.get("id")).value(subquery));
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }


}
