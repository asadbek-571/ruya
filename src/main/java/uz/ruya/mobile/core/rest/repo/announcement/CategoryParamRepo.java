package uz.ruya.mobile.core.rest.repo.announcement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.announcement.CategoryParam;

import java.util.List;

@Repository
public interface CategoryParamRepo extends BaseRepositoryLong<CategoryParam> {

    @Query("select c from CategoryParam c where c.category.id = :id")
    List<CategoryParam> findAllByCategoryId(Long id);
}