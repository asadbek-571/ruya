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
import uz.ruya.mobile.core.config.excaption.InvalidUserException;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.config.utils.DateUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.address.Address;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.announcement.AnnouncementParameter;
import uz.ruya.mobile.core.rest.entity.saved.SavedAd;
import uz.ruya.mobile.core.rest.entity.specialization.Specialization;
import uz.ruya.mobile.core.rest.entity.specialization.SpecializationParam;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.enums.AnnouncementType;
import uz.ruya.mobile.core.rest.enums.BaseStatus;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.req.ReqPaging;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAddAnnouncement;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqAnnouncement;
import uz.ruya.mobile.core.rest.peyload.res.ResAmount;
import uz.ruya.mobile.core.rest.peyload.res.ResPaging;
import uz.ruya.mobile.core.rest.peyload.res.ResPagingParams;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOne;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResAnnouncementOneFull;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResMyAnnouncementList;
import uz.ruya.mobile.core.rest.peyload.res.announcement.ResMySavedAnnouncementList;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResAddressList;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationParam;
import uz.ruya.mobile.core.rest.repo.announcement.AnnouncementParameterRepo;
import uz.ruya.mobile.core.rest.repo.announcement.AnnouncementRepo;
import uz.ruya.mobile.core.rest.repo.announcement.SpecializationParamRepo;
import uz.ruya.mobile.core.rest.repo.announcement.SpecializationRepo;
import uz.ruya.mobile.core.rest.repo.saved.SavedAdRepo;
import uz.ruya.mobile.core.rest.repo.user.AddressRepo;
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

    private final AddressRepo addressRepo;
    private final AnnouncementRepo announcementRepo;
    private final SpecializationRepo specializationRepo;
    private final SpecializationParamRepo specializationParamRepo;
    private final AnnouncementParameterRepo announcementParameterRepo;
    private final SavedAdRepo savedAdRepository;


    @Override
    public SuccessMessage addJobAnnouncement(ReqAddAnnouncement request) throws EntityNotFoundException {

        AuthUser authUser = GlobalVar.getAuthUser();
        UserProfile profile = authUser.getProfile();

        Specialization specialization = specializationRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

        Address address = addressRepo.findById(request.getAddressId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

        Announcement announcement = new Announcement();
        announcement.setDescription(request.getDescription());
        announcement.setSpecialization(specialization);
        announcement.setTitle(request.getTitle());
        announcement.setType(request.getType());
        announcement.setAddress(address);
        announcement.setUser(profile);
        announcement.setAppliedQty(0);

        if (CoreUtils.isPresent(request.getPrice())) {
            ReqAmount price = request.getPrice();
            announcement.setCurrency(price.getCurrency());
            announcement.setPrice(price.getAmount());
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

        List<Long> savedList = new ArrayList<>();
        if (CoreUtils.isPresent(GlobalVar.getAuthUser()) && CoreUtils.isPresent(GlobalVar.getAuthUser().getProfile())) {
            UserProfile profile = GlobalVar.getAuthUser().getProfile();
            savedList.addAll(savedAdRepository.findAllByUserIdOrderByAdId(profile.getId()));
        }

        Specification<Announcement> spec = buildSpecifications(request.getFilter());

        Page<Announcement> page = announcementRepo.findAll(spec, pageRequest);

        List<ResAnnouncementOne> list = new ArrayList<>();
        for (Announcement announcement : page.getContent()) {
            var one = new ResAnnouncementOne(announcement);
            one.setIsSaved(savedList.contains(announcement.getId()));
            list.add(one);
        }
        return new ResPaging<>(list, new ResPagingParams(page.getNumber(), page.getTotalPages(), page.getTotalElements()));
    }

    @Override
    public ResAnnouncementOneFull getAnnouncement(ReqLongId request) throws EntityNotFoundException {
        Announcement announcement = announcementRepo.findById(request.getId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.ENTITY_NOT_FOUND)));

        var specialization = announcement.getSpecialization();
        List<SpecializationParam> params = specializationParamRepo.findAllBySpecialization(specialization);
        ResSpecializationParam otherInfo = new ResSpecializationParam(specialization);

        List<SpecializationParam> parentParams = params.stream()
                .filter(p -> Objects.isNull(p.getParentId()))
                .sorted(Comparator.comparing(SpecializationParam::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        List<ResSpecializationParam.Param> parameters = new ArrayList<>();
        for (SpecializationParam parent : parentParams) {
            ResSpecializationParam.Param param = new ResSpecializationParam.Param(parent);

            List<SpecializationParam> childParams = params.stream()
                    .filter(child -> parent.getId().equals(child.getParentId()))
                    .sorted(Comparator.comparing(SpecializationParam::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                    .collect(Collectors.toList());

            switch (parent.getType()) {
                case INPUT:
                    announcementParameterRepo.findByKeyAndAnnouncement(parent.getCode(), announcement)
                            .ifPresent(parameter -> param.setDefaultValue(parameter.getValue()));
                    break;
                case RADIO:
                case SELECT:
                    List<ResSpecializationParam.ParamValue> radioValues = childParams.stream()
                            .map(child -> announcementParameterRepo.findByValueAndAnnouncement(child.getCode(), announcement)
                                    .map(parameter -> new ResSpecializationParam.ParamValue(child, parameter.getValue()))
                                    .orElseGet(() -> new ResSpecializationParam.ParamValue(child)))
                            .collect(Collectors.toList());
                    param.setSelectValues(radioValues);
                    break;
                case CHECKBOX:
                    announcementParameterRepo.findByKeyAndAnnouncement(parent.getCode(), announcement)
                            .ifPresent(parameter -> {
                                List<String> multiValue = Optional.ofNullable(parameter.getMultiValue()).orElse(Collections.emptyList());
                                List<ResSpecializationParam.ParamValue> checkboxValues = childParams.stream()
                                        .map(child -> multiValue.contains(child.getCode())
                                                ? new ResSpecializationParam.ParamValue(child, child.getValue())
                                                : new ResSpecializationParam.ParamValue(child))
                                        .collect(Collectors.toList());
                                param.setSelectValues(checkboxValues);
                            });
                    break;
            }
            parameters.add(param);
        }

        otherInfo.setParameters(parameters);

        return ResAnnouncementOneFull.builder()
                .amount(new ResAmount(announcement.getPrice(), announcement.getCurrency()))
                .contact(new ResAnnouncementOneFull.Contact(announcement.getUser()))
                .address(new ResAddressList.ResAddressOne(announcement.getAddress()))
                .description(announcement.getDescription())
                .title(announcement.getTitle())
                .id(announcement.getId())
                .otherInfo(otherInfo)
                .build();
    }

    @Override
    public ResMyAnnouncementList announcementMy() {
        AuthUser authUser = GlobalVar.getAuthUser();
        UserProfile profile = authUser.getProfile();
        List<Announcement> list = announcementRepo.findAllByUser(profile);
        List<ResAnnouncementOne> activeList = new ArrayList<>();
        List<ResAnnouncementOne> archiveList = new ArrayList<>();
        for (Announcement announcement : list) {
            if (BaseStatus.ACTIVE.equals(announcement.getStatus())) {
                activeList.add(new ResAnnouncementOne(announcement));
            } else {
                archiveList.add(new ResAnnouncementOne(announcement));
            }
        }
        return new ResMyAnnouncementList(activeList, archiveList);
    }

    @Override
    public SuccessMessage toggleSaved(ReqLongId request) throws InvalidUserException {
        boolean isAdded = this.checkSaved(request.getId());
        return new SuccessMessage(messageSingleton.getMessage(isAdded ? MessageKey.ADD_AD_SUCCESS : MessageKey.REMOVE_AD_SUCCESS));
    }


    @Override
    public SuccessMessage addArchive(ReqLongId request) {
        Announcement announcement = findById(request.getId());
        announcement.setStatus(BaseStatus.INACTIVE);
        announcementRepo.save(announcement);
        return new SuccessMessage(messageSingleton.getMessage(MessageKey.SUCCESS));
    }

    @Override
    public ResMySavedAnnouncementList mySavedAnnouncement() {
        List<Long> savedAnnoucementIdList = savedAdRepository.findAllByUserIdOrderByAdId(GlobalVar.getAuthUser().getProfile().getId());
        List<Announcement> list = announcementRepo.findAllByIdIn(savedAnnoucementIdList);
        List<ResAnnouncementOne> jobList = new ArrayList<>();
        List<ResAnnouncementOne> proposalList = new ArrayList<>();
        for (Announcement announcement : list) {
            if (AnnouncementType.CANDIDATE.equals(announcement.getType())) {
                proposalList.add(new ResAnnouncementOne(announcement));
            } else {
                jobList.add(new ResAnnouncementOne(announcement));
            }
        }
        return new ResMySavedAnnouncementList(jobList, proposalList);
    }

    public boolean checkSaved(Long announcementId) {

        UserProfile user = GlobalVar.getAuthUser().getProfile();

        Announcement announcement = findById(announcementId);

        Optional<SavedAd> optional = savedAdRepository.findByUserAndAd(user, announcement);

        if (optional.isPresent()) {
            savedAdRepository.delete(optional.get());
            return false;
        } else {
            savedAdRepository.save(new SavedAd(user, announcement));
            return true;
        }
    }

    private Announcement findById(Long id) {
        return announcementRepo.findById(id)
                .orElseThrow(() -> new javax.persistence.EntityNotFoundException(messageSingleton.getMessage(MessageKey.ENTITY_NOT_FOUND)));
    }

    private Specification<Announcement> buildSpecifications(ReqAnnouncement.Filter filter) throws EntityNotFoundException {

        if (CoreUtils.isEmpty(filter)) {
            return Specification.where(null);
        }

        Specification<Announcement> spec = Specification.where(null);

        if (CoreUtils.isPresent(filter.getCategoryId())) {
            Specialization specialization = specializationRepo.findById(filter.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

            spec = spec.and(this.hasJobCategory(specialization));
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

    public Specification<Announcement> hasJobCategory(Specialization specialization) {
        return (root, query, cb) -> CoreUtils.isEmpty(specialization) ? null : cb.equal(root.get("specialization"), specialization);
    }

    public Specification<Announcement> hasAmount(Long from, Long to) {
        return (root, query, cb) -> CoreUtils.isEmpty(from) && CoreUtils.isEmpty(to) ? null : cb.between(root.get("price"), from, to);
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
