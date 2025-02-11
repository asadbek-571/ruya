package uz.ruya.mobile.core.rest.repo.user;

import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.address.Address;

import java.util.List;

@Repository
public interface AddressRepo extends BaseRepositoryLong<Address> {

    List<Address> findAllByIsHaveChildTrue();

    List<Address> findAllByParentId(Long parentId);

}
