package uz.ruya.mobile.core.rest.peyload.res.announcement;

import lombok.*;
import uz.ruya.mobile.core.config.utils.FileUtils;
import uz.ruya.mobile.core.rest.entity.announcement.Category;
import uz.ruya.mobile.core.rest.enums.CategoryType;
import uz.ruya.mobile.core.rest.peyload.base.ResImg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 1/10/2025 11:43 AM 
 */

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCategoryList implements Serializable {

    private List<ResCategoryOne> list = new ArrayList<>();
    private Integer count;

    public ResCategoryList(List<ResCategoryOne> list) {
        this.count = list.size();
        this.list = list;
    }

    @ToString
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResCategoryOne implements Serializable {

        private Long id;
        private ResImg icon;
        private String name;
        private CategoryType type;

        public ResCategoryOne(Category category) {
            this.id = category.getId();
            this.name = category.getName();
            this.type = category.getType();
            this.icon = FileUtils.getCategoryIcon(category.getIcon());
        }
    }

}
