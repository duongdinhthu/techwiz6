package petcare.repository;

import java.time.Instant;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.Appointment;

/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {
    List<Appointment> findByOwnerId(Long ownerId);
    List<Appointment> findByOwnerIdAndApptTimeAfterOrderByApptTimeAsc(Long ownerId, Instant now, Pageable pageable);
}
