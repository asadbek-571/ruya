package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.rest.peyload.req.job.ReqAddEmployerAds;

public interface JobService {

    SuccessMessage addEmployerAds(ReqAddEmployerAds request) throws EntityNotFoundException;


}
