package petcare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petcare.domain.*; // for static metamodels
import petcare.domain.Discovery;
import petcare.repository.DiscoveryRepository;
import petcare.service.criteria.DiscoveryCriteria;
import petcare.service.dto.DiscoveryDTO;
import petcare.service.mapper.DiscoveryMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link Discovery} entities in the database.
 * The main input is a {@link DiscoveryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link DiscoveryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DiscoveryQueryService extends QueryService<Discovery> {

    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryQueryService.class);

    private final DiscoveryRepository discoveryRepository;

    private final DiscoveryMapper discoveryMapper;

    public DiscoveryQueryService(DiscoveryRepository discoveryRepository, DiscoveryMapper discoveryMapper) {
        this.discoveryRepository = discoveryRepository;
        this.discoveryMapper = discoveryMapper;
    }

    /**
     * Return a {@link Page} of {@link DiscoveryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DiscoveryDTO> findByCriteria(DiscoveryCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Discovery> specification = createSpecification(criteria);
        return discoveryRepository.findAll(specification, page).map(discoveryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DiscoveryCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Discovery> specification = createSpecification(criteria);
        return discoveryRepository.count(specification);
    }

    /**
     * Function to convert {@link DiscoveryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Discovery> createSpecification(DiscoveryCriteria criteria) {
        Specification<Discovery> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), Discovery_.id),
                buildStringSpecification(criteria.getName(), Discovery_.name),
                buildStringSpecification(criteria.getDescription(), Discovery_.description),
                buildStringSpecification(criteria.getCategory(), Discovery_.category),
                buildStringSpecification(criteria.getRequirements(), Discovery_.requirements),
                buildStringSpecification(criteria.getLocation(), Discovery_.location),
                buildRangeSpecification(criteria.getCreatedAt(), Discovery_.createdAt)
            );
        }
        return specification;
    }
}
