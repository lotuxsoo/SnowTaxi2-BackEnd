package LCK.snowTaxi2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import LCK.snowTaxi2.domain.Participation;
import LCK.snowTaxi2.domain.member.Member;
import LCK.snowTaxi2.domain.pot.TaxiPot;

public interface ParticipationRepository extends JpaRepository<Participation, Long> {

    void deleteParticipationByTaxiPotAndMember(TaxiPot taxiPot, Member member);

}
