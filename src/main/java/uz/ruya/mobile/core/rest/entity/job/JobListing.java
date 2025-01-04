package uz.ruya.mobile.core.rest.entity.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.ruya.mobile.core.base.BaseEntityLong;
import uz.ruya.mobile.core.base.BaseScheme;
import uz.ruya.mobile.core.rest.enums.CurrencyType;
import uz.ruya.mobile.core.rest.enums.ExperienceLevel;
import uz.ruya.mobile.core.rest.enums.JobListingType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "job_listings", schema = BaseScheme.CORE)
public class JobListing extends BaseEntityLong {

    @Column(name = "name")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "address_long")
    private String addressLong;

    @Column(name = "address_lat")
    private String addressLat;

    @Column(name = "applied_qty")
    private Integer appliedQty;

    @Column(name = "employer_id")
    private Long employerId;

    @Column(name = "worker_id")
    private Long workerId;

    @Column(name = "is_worker")
    private Boolean isWorker;

    @Column(name = "price", columnDefinition = "Decimal(10,2)")
    private Long price;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency")
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "experience_level")
    private ExperienceLevel experienceLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private JobListingType type;

    @ElementCollection
    @CollectionTable(name = "job_requirements", joinColumns = @JoinColumn(name = "job_listing_id"))
    @Column(name = "requirement")
    private List<String> requirements = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "job_benefits", joinColumns = @JoinColumn(name = "job_listing_id"))
    @Column(name = "benefit")
    private List<String> benefits = new ArrayList<>();

}