package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.core.SuccessMessage;
import uz.ruya.mobile.core.config.excaption.EntityNotFoundException;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.message.MessageKey;
import uz.ruya.mobile.core.message.MessageSingleton;
import uz.ruya.mobile.core.rest.entity.company.Company;
import uz.ruya.mobile.core.rest.entity.job.JobListing;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;
import uz.ruya.mobile.core.rest.peyload.req.ReqAddress;
import uz.ruya.mobile.core.rest.peyload.req.ReqAmount;
import uz.ruya.mobile.core.rest.peyload.req.job.ReqAddEmployerAds;
import uz.ruya.mobile.core.rest.repo.job.CompanyRepo;
import uz.ruya.mobile.core.rest.repo.job.JobListingRepo;
import uz.ruya.mobile.core.rest.service.JobService;

/**
 Asadbek Kushakov 1/3/2025 6:25 PM 
 */

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobListingRepo jobListingRepo;
    private final CompanyRepo companyRepo;
    private final MessageSingleton messageSingleton;


    @Override
    public SuccessMessage addEmployerAds(ReqAddEmployerAds request) throws EntityNotFoundException {

        UserProfile profile = GlobalVar.getAuthUser().getProfile();

        Company company = companyRepo.findByUserId(profile.getId()).orElseThrow(() ->
                new EntityNotFoundException(messageSingleton.getMessage(MessageKey.ENTITY_NOT_FOUND)));

        JobListing jobListing = new JobListing();

        jobListing.setName(request.getJobName());
        jobListing.setAppliedQty(0);
        jobListing.setIsWorker(false);
        jobListing.setType(request.getType());
        jobListing.setEmployerId(company.getId());
        jobListing.setBenefits(request.getBenefits());
        jobListing.setDescription(request.getDescription());
        jobListing.setRequirements(request.getRequirements());
        jobListing.setExperienceLevel(request.getExperienceLevel());

        if (CoreUtils.isPresent(request.getPrice())) {
            ReqAmount price = request.getPrice();

            jobListing.setPrice(price.getAmount());
            jobListing.setCurrency(price.getCurrency());
        }

        if (CoreUtils.isEmpty(request.getAddress())) {
            jobListing.setAddress(company.getAddress());
            jobListing.setAddressLong(company.getAddressLong());
            jobListing.setAddressLat(company.getAddressLat());
        } else {
            ReqAddress address = request.getAddress();

            jobListing.setAddress(address.getAddressName());
            jobListing.setAddressLong(address.getAddressLongitude());
            jobListing.setAddressLat(address.getAddressLatitude());
        }

        jobListingRepo.save(jobListing);

        return new SuccessMessage(messageSingleton.getMessage(MessageKey.SUCCESS));
    }

}
