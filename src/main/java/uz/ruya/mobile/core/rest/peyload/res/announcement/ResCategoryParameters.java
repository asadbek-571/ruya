package uz.ruya.mobile.core.rest.peyload.res.announcement;

import lombok.*;
import uz.ruya.mobile.core.config.utils.FileUtils;
import uz.ruya.mobile.core.rest.entity.announcement.Category;
import uz.ruya.mobile.core.rest.entity.announcement.CategoryParam;
import uz.ruya.mobile.core.rest.peyload.base.ResImg;

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
public class ResCategoryParameters implements Serializable {

    private Long id;
    private String label;
    private String code;
    private Integer maxPhotos;
    private ResImg icon;
    private List<Param> parameters = new ArrayList<>();

    public ResCategoryParameters(Category category, CategoryParam categoryParam) {
        this.id = categoryParam.getId();
        this.label = category.getLabelTranslate();
        this.code = categoryParam.getCode();
        this.maxPhotos = category.getMaxPhotos();
        this.icon = FileUtils.getParamIcon(categoryParam.getIcon());
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
        private String type;
        private Boolean range = Boolean.FALSE;
        private ParamValidation validation;
        private List<ParamValue> values = new ArrayList<>();
        private List<ParamUnits> units = new ArrayList<>();

        public Param(CategoryParam parent) {
            this.id = parent.getId();
            this.label = parent.getLabelTranslate();
            this.code = parent.getCode();
            this.type = parent.getType();
            this.range = parent.getRange();
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
        private Boolean disabled = Boolean.FALSE;

        public ParamValue(CategoryParam child) {
            this.value = child.getValue();
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

        public ParamValidation(CategoryParam parent) {
            this.max = parent.getMax();
            this.min = parent.getMin();
            this.pattern = parent.getPattern();
            this.isRequired = parent.getIsRequired();
        }
    }

    @Getter
    @Setter
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParamUnits implements Serializable {

        private String value;
        private String label;

        public ParamUnits(CategoryParam paramUnit) {
            this.value = paramUnit.getValue();
            this.label = paramUnit.getLabel();
        }
    }


}
