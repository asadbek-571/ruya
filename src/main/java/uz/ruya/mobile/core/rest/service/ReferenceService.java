package uz.ruya.mobile.core.rest.service;

import uz.ruya.mobile.core.rest.peyload.req.ReqLongId;
import uz.ruya.mobile.core.rest.peyload.res.reference.ResAddressList;

public interface ReferenceService {
    ResAddressList addressList(ReqLongId request);
}
