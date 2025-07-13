package LCK.snowTaxi2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import LCK.snowTaxi2.domain.member.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
    Member findByNickname(String email);

}
