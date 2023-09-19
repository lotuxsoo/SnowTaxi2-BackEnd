package LCK.snowTaxi2.domain.pot;

import LCK.snowTaxi2.domain.Participation;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate ridingDate;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime ridingTime;

    private int headCount;

    @OneToMany(mappedBy = "taxiPot")
    private List<Participation> participations = new ArrayList<>();

}
