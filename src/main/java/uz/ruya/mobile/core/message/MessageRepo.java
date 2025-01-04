package uz.ruya.mobile.core.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.config.core.Lang;

import java.util.Optional;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long> {

    Optional<Message> findTopByKeyAndLang(String key, Lang lang);

}
