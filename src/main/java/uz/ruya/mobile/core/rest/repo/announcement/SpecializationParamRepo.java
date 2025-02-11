package uz.ruya.mobile.core.rest.repo.announcement;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.specialization.Specialization;
import uz.ruya.mobile.core.rest.entity.specialization.SpecializationParam;

import java.util.List;

@Repository
public interface SpecializationParamRepo extends BaseRepositoryLong<SpecializationParam> {

    List<SpecializationParam> findAllBySpecialization(Specialization specialization);
}