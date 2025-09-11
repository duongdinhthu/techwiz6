package petcare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.Appointment;

/**
 * Spring Data JPA repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment> {}
