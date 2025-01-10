package uz.ruya.mobile.core.rest.repo.announcement;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.announcement.Category;

import java.util.List;

@Repository
public interface CategoryRepo extends BaseRepositoryLong<Category> {

    List<Category> findAllByParentIdIsNull();

    List<Category> findAllByParentId(Long parentId);

}