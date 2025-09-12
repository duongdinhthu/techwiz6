package petcare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petcare.domain.*; // for static metamodels
import petcare.domain.UserPet;
import petcare.repository.UserPetRepository;
import petcare.service.criteria.UserPetCriteria;
import petcare.service.dto.UserPetDTO;
import petcare.service.mapper.UserPetMapper;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link UserPet} entities in the database.
 * The main input is a {@link UserPetCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link UserPetDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserPetQueryService extends QueryService<UserPet> {

    private static final Logger LOG = LoggerFactory.getLogger(UserPetQueryService.class);

    private final UserPetRepository userPetRepository;

    private final UserPetMapper userPetMapper;

    public UserPetQueryService(UserPetRepository userPetRepository, UserPetMapper userPetMapper) {
        this.userPetRepository = userPetRepository;
        this.userPetMapper = userPetMapper;
    }

    /**
     * Return a {@link Page} of {@link UserPetDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserPetDTO> findByCriteria(UserPetCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserPet> specification = createSpecification(criteria);
        return userPetRepository.findAll(specification, page).map(userPetMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserPetCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<UserPet> specification = createSpecification(criteria);
        return userPetRepository.count(specification);
    }

    /**
     * Function to convert {@link UserPetCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserPet> createSpecification(UserPetCriteria criteria) {
        Specification<UserPet> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            specification = Specification.allOf(
                Boolean.TRUE.equals(criteria.getDistinct()) ? distinct(criteria.getDistinct()) : null,
                buildRangeSpecification(criteria.getId(), UserPet_.id),
                buildStringSpecification(criteria.getName(), UserPet_.name),
                buildStringSpecification(criteria.getEmail(), UserPet_.email),
                buildStringSpecification(criteria.getPasswordHash(), UserPet_.passwordHash),
                buildStringSpecification(criteria.getPhone(), UserPet_.phone),
                buildStringSpecification(criteria.getAddress(), UserPet_.address),
                buildSpecification(criteria.getRole(), UserPet_.role),
                buildStringSpecification(criteria.getAvatar(), UserPet_.avatar),
                buildRangeSpecification(criteria.getCreatedAt(), UserPet_.createdAt)
            );
        }
        return specification;
    }
}
