package uz.ruya.mobile.core.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntity;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.config.core.Lang;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages", schema = BaseScheme.INFO, uniqueConstraints = {
        @UniqueConstraint(name = "unique_message", columnNames = {"key", "lang"})
})
public class Message extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "key")
    private String key;

    @Column(name = "lang")
    @Enumerated(EnumType.STRING)
    private Lang lang;

    @Column(name = "message", columnDefinition = "text")
    private String message;

    @Column(name = "info", columnDefinition = "text")
    private String info;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private MessageType type = MessageType.EXCEPTION;

}
