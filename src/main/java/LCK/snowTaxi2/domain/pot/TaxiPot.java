package LCK.snowTaxi2.domain.pot;

import LCK.snowTaxi2.domain.Member;
import LCK.snowTaxi2.domain.Participation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "taxi_pot")
public class TaxiPot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taxi_pot_id")
    private long id;

    @Enumerated(EnumType.ORDINAL)
    private Departure departure;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd-HH-mm")
    private LocalDateTime ridingTime;

    private int headCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member host;

    @OneToMany(mappedBy = "taxiPot")
    private List<Participation> participations = new ArrayList<>();
}
