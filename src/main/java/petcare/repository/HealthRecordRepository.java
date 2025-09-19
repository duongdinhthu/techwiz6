package petcare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.HealthRecord;

import java.util.List;

/**
 * Spring Data JPA repository for the HealthRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long>, JpaSpecificationExecutor<HealthRecord> {
    List<HealthRecord> findByPetId(Long petId);
    @Query("SELECT COUNT(hr) FROM HealthRecord hr JOIN Pet p ON hr.petId = p.id WHERE p.ownerId = :ownerId")
    Long countByOwnerId(Long ownerId);
}
