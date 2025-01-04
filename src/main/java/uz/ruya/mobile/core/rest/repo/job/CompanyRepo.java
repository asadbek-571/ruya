package uz.ruya.mobile.core.rest.repo.job;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.company.Company;

import java.util.Optional;

@Repository
public interface CompanyRepo extends BaseRepositoryLong<Company> {

    Optional<Company> findByUserId(Long userId);

}