package uz.ruya.mobile.core.rest.peyload.res.specialization;

import lombok.*;
import uz.ruya.mobile.core.rest.entity.specialization.Specialization;
import uz.ruya.mobile.core.rest.entity.specialization.SpecializationParam;
import uz.ruya.mobile.core.rest.enums.ParamType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 1/10/2025 12:01 PM 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResSpecializationParam implements Serializable {

    private Long specializationId;
    private String label;
    private List<Param> parameters = new ArrayList<>();

    public ResSpecializationParam(Specialization specialization) {
        this.specializationId = specialization.getId();
        this.label = specialization.getLabelTranslate();
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Param implements Serializable {

        private Long id;
        private String code;
        private String label;
        private ParamType type;
        private ParamValidation validation;
        private String defaultValue;
        private List<ParamValue> selectValues = new ArrayList<>();

        public Param(SpecializationParam parent) {
            this.id = parent.getId();
            this.label = parent.getLabelTranslate();
            this.code = parent.getCode();
            this.type = parent.getType();
            this.validation = new ParamValidation(parent);
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParamValue implements Serializable {

        private String label;
        private String value;
        private String defaultValue;
        private Boolean disabled = Boolean.FALSE;

        public ParamValue(SpecializationParam child) {
            this.value = child.getValue();
            this.label = child.getLabelTranslate();
            this.disabled = child.getDisabled();
        }

        public ParamValue(SpecializationParam child, String defaultValue) {
            this.value = child.getValue();
            this.defaultValue = defaultValue;
            this.label = child.getLabelTranslate();
            this.disabled = child.getDisabled();
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParamValidation implements Serializable {

        private Integer max;
        private Integer min;
        private String pattern;
        private Boolean isRequired = Boolean.FALSE;

        public ParamValidation(SpecializationParam parent) {
            this.max = parent.getMax();
            this.min = parent.getMin();
            this.pattern = parent.getPattern();
            this.isRequired = parent.getIsRequired();
        }
    }


}
