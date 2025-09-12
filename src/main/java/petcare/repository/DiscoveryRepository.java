package petcare.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import petcare.domain.Discovery;

/**
 * Spring Data JPA repository for the Discovery entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DiscoveryRepository extends JpaRepository<Discovery, Long>, JpaSpecificationExecutor<Discovery> {}
