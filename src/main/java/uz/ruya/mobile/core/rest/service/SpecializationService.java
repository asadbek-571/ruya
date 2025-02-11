package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.rest.entity.specialization.Specialization;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecialization;
import uz.ruya.mobile.core.rest.peyload.req.announcement.ReqSpecializationParam;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationList;
import uz.ruya.mobile.core.rest.peyload.res.specialization.ResSpecializationParam;

public interface SpecializationService {

    ResSpecializationList specializationList(ReqSpecialization request);

    ResSpecializationParam getParam(ReqSpecializationParam request) throws EntityNotFoundException;

    ResSpecializationParam getParam(Specialization specialization);
}
