package petcare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.UserPet;

@SuppressWarnings("unused")
@Repository
public interface UserPetRepository extends JpaRepository<UserPet, Long>, JpaSpecificationExecutor<UserPet> {
    boolean existsByEmail(String email);
    Optional<UserPet> findByEmail(String email);

    Optional<UserPet> findOneByEmail(String email);
}
