package uz.ruya.mobile.core.rest.peyload.res.announcement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 Asadbek Kushakov 2/14/2025 10:03 AM 
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResMyAnnouncementList implements Serializable {

    private List<ResAnnouncementOne> activeList = new ArrayList<>();
    private List<ResAnnouncementOne> archiveList = new ArrayList<>();

}
