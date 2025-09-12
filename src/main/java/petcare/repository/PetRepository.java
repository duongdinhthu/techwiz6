package petcare.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.Pet;

/**
 * Spring Data JPA repository for the Pet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {
    List<Pet> findByOwnerId(Long ownerId);
}
