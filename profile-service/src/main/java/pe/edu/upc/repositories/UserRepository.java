package pe.edu.upc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.aggregates.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByEmail(String email);
}
