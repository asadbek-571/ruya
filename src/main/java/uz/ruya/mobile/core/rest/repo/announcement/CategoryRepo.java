package uz.ruya.mobile.core.rest.repo.announcement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.announcement.Category;

import java.util.List;

@Repository
public interface CategoryRepo extends BaseRepositoryLong<Category> {


    @Query("select c from Category c where c.parentId is null order by c.orderId")
    List<Category> findAllByParentIdIsNullOrderByOrderIdAsc();

    @Query("select c from Category c where c.parentId = :parentId order by c.orderId")
    List<Category> findAllByParentIdOrderByOrderIdAsc(Long parentId);
}