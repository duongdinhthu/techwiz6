package petcare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.HealthRecord;

/**
 * Spring Data JPA repository for the HealthRecord entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long>, JpaSpecificationExecutor<HealthRecord> {}
