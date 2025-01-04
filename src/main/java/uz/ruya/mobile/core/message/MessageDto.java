package uz.ruya.mobile.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uz.ruya.mobile.core.config.core.Lang;


import java.io.Serializable;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto implements Serializable {

    private Lang lang;

    private String message;

}
