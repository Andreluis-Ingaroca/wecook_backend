package pe.edu.upc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.aggregates.Profile;

import java.util.Optional;


@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long> {

    Optional<Profile> findProfileByName(String name);

    Optional<Profile> findProfileByUserId(Long userId);
}
