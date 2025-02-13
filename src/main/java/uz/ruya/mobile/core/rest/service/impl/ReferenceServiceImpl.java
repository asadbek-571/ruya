package uz.ruya.mobile.core.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ruya.mobile.core.config.utils.CoreUtils;
import uz.ruya.mobile.core.rest.entity.address.Address;
import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResAddressList;
import uz.ruya.mobile.core.rest.repo.user.AddressRepo;
import uz.ruya.mobile.core.rest.service.ReferenceService;

import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 2/10/2025 12:35 PM 
 */

@Service
@RequiredArgsConstructor
public class ReferenceServiceImpl implements ReferenceService {

    private final AddressRepo addressRepo;

    @Override
    public ResAddressList addressList(ReqLongId request) {
        List<Address> list = (CoreUtils.isPresent(request) && CoreUtils.isPresent(request.getId())) ?
                addressRepo.findAllByParentId(request.getId()) :
                addressRepo.findAllByParentIdIsNull();
        List<ResAddressList.ResAddressOne> resultList = new ArrayList<>();
        for (Address address : list) {
            resultList.add(new ResAddressList.ResAddressOne(address));
        }
        return new ResAddressList(resultList);
    }
}
