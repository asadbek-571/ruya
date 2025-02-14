package uz.ruya.mobile.core.config.utils;

import org.springframework.stereotype.Component;
import uz.ruya.mobile.core.rest.peyload.base.ResImg;

import java.util.List;
import java.util.UUID;

/**
 Asadbek Kushakov 1/6/2025 3:50 PM 
 */

@Component
public class FileUtils {

    public static final String baseFileURl = "https://file.ruya.uz/file";

    public static ResImg getPopUpAlertImage(String name) {
        ResImg img = new ResImg();
        img.setContentType("image/png");
        img.setPath(baseFileURl + "/static/popup-alert");
        img.setName(name);
        img.setExt("png");
        img.setSuffix(null);
        img.setExtraSuffix(List.of());
        return img;
    }

    public static ResImg getOnboardingImage(String name) {
        ResImg img = new ResImg();
        img.setContentType("image/png");
        img.setPath(baseFileURl + "/static/onboarding");
        img.setName(name);
        img.setExt("png");
        img.setSuffix(null);
        img.setExtraSuffix(List.of());
        return img;
    }

    public static ResImg getCategoryIcon(String name) {
        ResImg img = new ResImg();
        img.setContentType("image/png");
        img.setPath(baseFileURl + "/static/category");
        img.setName(name);
        img.setExt("png");
        img.setSuffix(null);
        img.setExtraSuffix(List.of());
        return img;
    }

    public static ResImg getParamIcon(String name) {
        ResImg img = new ResImg();
        img.setContentType("image/png");
        img.setPath(baseFileURl + "/static/param");
        img.setName(name);
        img.setExt("png");
        img.setSuffix(null);
        img.setExtraSuffix(List.of());
        return img;
    }


    public static String avatarPath(String name){
        return baseFileURl + "/static/avatar/"+name+".png";
    }

}
