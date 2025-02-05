package uz.ruya.mobile.core.rest.peyload.res.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 1/28/2025 2:50 PM 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserCvList implements Serializable {

    private List<ResUserCvOne> list = new ArrayList<>();
    private Integer count;

    public ResUserCvList(List<ResUserCvOne> result) {
        this.count = result.size();
        this.list = result;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResUserCvOne implements Serializable {
        private String url;
        private Boolean isMain;
    }


}
