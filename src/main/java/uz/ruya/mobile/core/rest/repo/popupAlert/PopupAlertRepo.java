package uz.ruya.mobile.core.rest.repo.popupAlert;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.base.BaseRepositoryLong;
import uz.ruya.mobile.core.rest.entity.popupAlert.PopupAlert;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PopupAlertRepo extends BaseRepositoryLong<PopupAlert> {

    @Query("SELECT p FROM PopupAlert p WHERE :currentDate BETWEEN p.startDate AND p.endDate")
    List<PopupAlert> findActivePopupAlerts(@Param("currentDate") LocalDate currentDate);

}
