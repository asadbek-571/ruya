package uz.ruya.mobile.core.rest.peyload.res.auth;

import lombok.*;

import java.io.Serializable;

/**
 Asadbek Kushakov 1/3/2025 10:32 AM 
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ResLogin implements Serializable {
    private String token;
}
