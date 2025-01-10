package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.rest.peyload.res.onboarding.ResOnboardingList;
import uz.ruya.mobile.core.rest.peyload.res.popupAlert.ResPopupAlertList;

public interface InfoService {

    ResPopupAlertList popupAlertList();

    ResOnboardingList getOnboardingList();

}
