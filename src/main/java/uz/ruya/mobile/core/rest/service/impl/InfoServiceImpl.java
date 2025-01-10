package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.core.GlobalVar;
import uz.ruya.mobile.core.config.core.Lang;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.entity.onboarding.Onboarding;
import uz.ruya.mobile.core.rest.entity.onboarding.OnboardingInfo;
import uz.ruya.mobile.core.rest.entity.onboarding.OnboardingTranslate;
import uz.ruya.mobile.core.rest.entity.popupAlert.PopupAlert;
import uz.ruya.mobile.core.rest.entity.popupAlert.PopupAlertInfo;
import uz.ruya.mobile.core.rest.entity.popupAlert.PopupAlertTranslate;
import uz.ruya.mobile.core.rest.peyload.res.onboarding.ResOnboarding;
import uz.ruya.mobile.core.rest.peyload.res.onboarding.ResOnboardingList;
import uz.ruya.mobile.core.rest.peyload.res.popupAlert.ResPopupAlert;
import uz.ruya.mobile.core.rest.peyload.res.popupAlert.ResPopupAlertList;
import uz.ruya.mobile.core.rest.repo.onboarding.OnboardingInfoRepo;
import uz.ruya.mobile.core.rest.repo.onboarding.OnboardingRepo;
import uz.ruya.mobile.core.rest.repo.popupAlert.PopupAlertInfoRepo;
import uz.ruya.mobile.core.rest.repo.popupAlert.PopupAlertRepo;
import uz.ruya.mobile.core.rest.service.InfoService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 Asadbek Kushakov 1/8/2025 12:27 PM 
 */

@Service
@RequiredArgsConstructor
public class InfoServiceImpl implements InfoService {

    private final OnboardingInfoRepo onboardingInfoRepo;
    private final OnboardingRepo onboardingRepo;

    private final PopupAlertInfoRepo popupAlertInfoRepo;
    private final PopupAlertRepo popupAlertRepo;


    @Override
    public ResPopupAlertList popupAlertList() {

        var lang = GlobalVar.getLANG();
        var userId = GlobalVar.getUserUUID();
        var deviceType = GlobalVar.getDEVICE_TYPE();

        List<ResPopupAlert> resultList = new ArrayList<>();

        List<PopupAlert> popupAlertList = popupAlertRepo.findActivePopupAlerts(LocalDate.now());

        for (PopupAlert popupAlert : popupAlertList) {

            boolean isAlreadyRead = popupAlertInfoRepo.existsByPopupIdAndUserIdAndDeviceType(popupAlert.getUuid(), userId, deviceType);

            if (isAlreadyRead) {
                continue;
            }

            PopupAlertInfo info = new PopupAlertInfo();
            info.setUuid(UUID.randomUUID());
            info.setPopupId(popupAlert.getUuid());
            info.setUserId(userId);
            info.setDeviceType(deviceType);
            popupAlertInfoRepo.saveAndFlush(info);

            var result = new ResPopupAlert(popupAlert);
            this.setTranslate(result, popupAlert, lang);

            resultList.add(result);

        }

        return new ResPopupAlertList(resultList);
    }


    @Override
    public ResOnboardingList getOnboardingList() {

        var userId = GlobalVar.getUserUUID();
        var deviceType = GlobalVar.getDEVICE_TYPE();
        var appVersion = CoreUtils.extractVersion(GlobalVar.getAppVersion());
        var lang = GlobalVar.getLANG();


        boolean isAlreadyRead = onboardingInfoRepo.existsByUserIdAndDeviceTypeAndVersion(userId, deviceType, appVersion);

        if (isAlreadyRead) {
            return new ResOnboardingList(new ArrayList<>());
        }

        if (onboardingRepo.existsByVersion(appVersion)) {
            OnboardingInfo info = new OnboardingInfo();
            info.setUuid(UUID.randomUUID());
            info.setUserId(userId);
            info.setDeviceType(deviceType);
            info.setVersion(appVersion);
            onboardingInfoRepo.saveAndFlush(info);
        }

        var resOnboardingList = onboardingRepo.findAllByVersionAndDeviceTypeAndIsActiveTrueOrderByOrderAsc(appVersion, deviceType)
                .stream()
                .peek(onboarding -> this.getOnboardingTranslate(onboarding, lang))
                .map(ResOnboarding::new)
                .collect(Collectors.toList());

        return new ResOnboardingList(resOnboardingList);
    }

    private void getOnboardingTranslate(Onboarding onboarding, Lang lang) {
        lang = Objects.isNull(lang) ? Lang.UZB : lang;
        for (OnboardingTranslate translate : onboarding.getTranslate()) {
            if (translate.getLang().equals(lang) && translate.getKey().equals(onboarding.getTitleKey())) {
                onboarding.setTitle(translate.getText());
                continue;
            }
            if (translate.getLang().equals(lang) && translate.getKey().equals(onboarding.getDescriptionKey())) {
                onboarding.setDescription(translate.getText());
            }
        }
    }

    private void setTranslate(ResPopupAlert result, PopupAlert popupAlert, Lang lang) {
        lang = Objects.isNull(lang) ? Lang.UZB : lang;
        for (PopupAlertTranslate translate : popupAlert.getTranslate()) {
            if (translate.getLang().equals(lang) && translate.getKey().equals(popupAlert.getTitleKey())) {
                result.setTitle(translate.getText());
                continue;
            }
            if (translate.getLang().equals(lang) && translate.getKey().equals(popupAlert.getDescriptionKey())) {
                result.setDescription(translate.getText());
            }
        }
    }
}
