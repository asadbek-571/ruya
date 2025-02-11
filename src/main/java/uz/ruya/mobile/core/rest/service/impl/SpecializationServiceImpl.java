package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.specialization.Specialization;
import uz.ruya.mobile.core.rest.entity.specialization.SpecializationParam;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecialization;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecializationParam;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationList;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationParam;
import uz.ruya.mobile.core.rest.repo.announcement.SpecializationParamRepo;
import uz.ruya.mobile.core.rest.repo.announcement.SpecializationRepo;
import uz.ruya.mobile.core.rest.service.SpecializationService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final MessageSingleton messageSingleton;

    private final SpecializationRepo specializationRepo;
    private final SpecializationParamRepo specializationParamRepo;

    @Override
    public ResSpecializationList specializationList(ReqSpecialization request) {
        List<Specialization> categories = CoreUtils.isPresent(request.getParentId())
                ? specializationRepo.findAllByParentIdOrderByOrderIdAsc(request.getParentId())
                : specializationRepo.findAllByParentIdIsNullOrderByOrderIdAsc();

        List<ResSpecializationList.ResSpecializationOne> result = new ArrayList<>();
        for (Specialization specialization : categories) {
            result.add(new ResSpecializationList.ResSpecializationOne(specialization));
        }
        return new ResSpecializationList(result);
    }

    @Override
    public ResSpecializationParam getParam(ReqSpecializationParam request) throws EntityNotFoundException {

        Specialization specialization = specializationRepo.findById(request.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException(messageSingleton.getMessage(MessageKey.CATEGORY_NOT_FOUND)));

        return getParam(specialization);
    }

    @Override
    public ResSpecializationParam getParam(Specialization specialization) {
        List<SpecializationParam> params = specializationParamRepo.findAllBySpecialization(specialization);
        ResSpecializationParam result = new ResSpecializationParam(specialization);

        List<SpecializationParam> parenParams = params
                .stream()
                .filter(p -> CoreUtils.isEmpty(p.getParentId()))
                .sorted(Comparator.comparing(SpecializationParam::getOrder))
                .collect(Collectors.toList());

        List<ResSpecializationParam.Param> parameters = new ArrayList<>();
        for (SpecializationParam parent : parenParams) {

            List<SpecializationParam> childParams = params
                    .stream()
                    .filter(child -> parent.getId().equals(child.getParentId()))
                    .sorted(Comparator.comparing(SpecializationParam::getOrder))
                    .collect(Collectors.toList());

            ResSpecializationParam.Param param = new ResSpecializationParam.Param(parent);

            List<ResSpecializationParam.ParamValue> values = new ArrayList<>();
            for (SpecializationParam child : childParams) {
                values.add(new ResSpecializationParam.ParamValue(child));
            }
            param.setSelectValues(values);
            parameters.add(param);
        }

        result.setParameters(parameters);
        return result;
    }
}
