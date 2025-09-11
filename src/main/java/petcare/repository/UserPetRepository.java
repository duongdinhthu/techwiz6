package petcare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.UserPet;

/**
 * Spring Data JPA repository for the UserPet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserPetRepository extends JpaRepository<UserPet, Long>, JpaSpecificationExecutor<UserPet> {}
