package uz.ruya.mobile.core.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.UUID;

@NoRepositoryBean
public interface BaseRepositoryUUID<R extends BaseEntityUUID> extends JpaRepository<R, UUID> {
}
