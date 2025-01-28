package uz.ruya.mobile.core.rest.peyload.res.reference;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 Asadbek Kushakov 1/27/2025 6:35 PM 
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResReference implements Serializable {

    private Integer count;
    private List<ReferenceOne> list = Lists.newArrayList();

    public ResReference(List<ReferenceOne> list) {
        this.count = list.size();
        this.list = list;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ReferenceOne implements Serializable {

        private Long id;
        private String name;

    }

}
