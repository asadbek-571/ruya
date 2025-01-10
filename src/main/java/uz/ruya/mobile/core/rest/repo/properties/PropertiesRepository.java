package uz.ruya.mobile.core.rest.repo.properties;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.properties.PropertiesEntity;


import java.util.Optional;

@Repository
public interface PropertiesRepository extends BaseRepositoryLong<PropertiesEntity> {

    Optional<PropertiesEntity> findByKey(String key);

}
