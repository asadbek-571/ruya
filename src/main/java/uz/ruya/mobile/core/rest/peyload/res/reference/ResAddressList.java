package uz.ruya.mobile.core.rest.peyload.res.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.rest.entity.address.Address;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 2/10/2025 12:27 PM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResAddressList implements Serializable {

    private List<ResAddressOne> list = new ArrayList<>();
    private Integer count;

    public ResAddressList(List<ResAddressOne> list) {
        this.list = list;
        this.count = list.size();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResAddressOne implements Serializable {
        private Long id;
        private String name;
        private String latitude;
        private String longitude;
        private Boolean isHaveChild = Boolean.FALSE;

        public ResAddressOne(Address address) {
            this.id = address.getId();
            this.name = address.getName();
            this.latitude = address.getLatitude();
            this.longitude = address.getLongitude();
            this.isHaveChild = address.getIsHaveChild();
        }
    }

}
