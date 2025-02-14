package uz.ruya.mobile.core.rest.entity.saved;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.ruya.mobile.core.rest.entity.announcement.Announcement;
import uz.ruya.mobile.core.rest.entity.user.UserProfile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "saved_ads")
public class SavedAd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;

    @ManyToOne
    @JoinColumn(name = "ad_id", nullable = false)
    private Announcement ad;

    @Column(name = "saved_at")
    private LocalDateTime savedAt;

    public SavedAd() {
        this.savedAt = LocalDateTime.now();
    }

    public SavedAd(UserProfile user, Announcement announcement) {
        this.user = user;
        this.ad = announcement;
        this.savedAt = LocalDateTime.now();
    }
}
