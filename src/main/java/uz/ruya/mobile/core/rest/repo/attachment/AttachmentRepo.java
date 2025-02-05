package uz.ruya.mobile.core.rest.repo.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ruya.mobile.core.rest.entity.attachemnt.Attachment;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, Long> {
}