package uz.ruya.mobile.core.rest.repo.announcement;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.specialization.Specialization;

import java.util.List;

@Repository
public interface SpecializationRepo extends BaseRepositoryLong<Specialization> {


    @Query("select c from Specialization c where c.parentId is null order by c.orderId")
    List<Specialization> findAllByParentIdIsNullOrderByOrderIdAsc();

    @Query("select c from Specialization c where c.parentId = :parentId order by c.orderId")
    List<Specialization> findAllByParentIdOrderByOrderIdAsc(Long parentId);
}