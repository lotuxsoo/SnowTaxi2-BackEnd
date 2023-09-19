package LCK.snowTaxi2.repository;

import LCK.snowTaxi2.domain.pot.TaxiPot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TaxiPotRepository extends JpaRepository<TaxiPot, Long> {

    List<TaxiPot> getTaxiPotByRidingDate(LocalDate today);

}
