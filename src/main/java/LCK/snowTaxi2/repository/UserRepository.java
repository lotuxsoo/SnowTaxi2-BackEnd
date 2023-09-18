package LCK.snowTaxi2.repository;

import LCK.snowTaxi2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
