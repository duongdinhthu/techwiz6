package petcare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petcare.domain.UserPet;
import petcare.repository.UserPetRepository;
import petcare.service.dto.UserPetDTO;
import petcare.service.mapper.UserPetMapper;

/**
 * Service Implementation for managing {@link petcare.domain.UserPet}.
 */
@Service
@Transactional
public class UserPetService {

    private static final Logger LOG = LoggerFactory.getLogger(UserPetService.class);

    private final UserPetRepository userPetRepository;

    private final UserPetMapper userPetMapper;

    public UserPetService(UserPetRepository userPetRepository, UserPetMapper userPetMapper) {
        this.userPetRepository = userPetRepository;
        this.userPetMapper = userPetMapper;
    }

    /**
     * Save a userPet.
     *
     * @param userPetDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPetDTO save(UserPetDTO userPetDTO) {
        LOG.debug("Request to save UserPet : {}", userPetDTO);
        UserPet userPet = userPetMapper.toEntity(userPetDTO);
        userPet = userPetRepository.save(userPet);
        return userPetMapper.toDto(userPet);
    }

    /**
     * Update a userPet.
     *
     * @param userPetDTO the entity to save.
     * @return the persisted entity.
     */
    public UserPetDTO update(UserPetDTO userPetDTO) {
        LOG.debug("Request to update UserPet : {}", userPetDTO);
        UserPet userPet = userPetMapper.toEntity(userPetDTO);
        userPet = userPetRepository.save(userPet);
        return userPetMapper.toDto(userPet);
    }

    /**
     * Partially update a userPet.
     *
     * @param userPetDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserPetDTO> partialUpdate(UserPetDTO userPetDTO) {
        LOG.debug("Request to partially update UserPet : {}", userPetDTO);

        return userPetRepository
            .findById(userPetDTO.getId())
            .map(existingUserPet -> {
                userPetMapper.partialUpdate(existingUserPet, userPetDTO);

                return existingUserPet;
            })
            .map(userPetRepository::save)
            .map(userPetMapper::toDto);
    }

    /**
     * Get one userPet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<UserPetDTO> findOne(Long id) {
        LOG.debug("Request to get UserPet : {}", id);
        return userPetRepository.findById(id).map(userPetMapper::toDto);
    }

    /**
     * Delete the userPet by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete UserPet : {}", id);
        userPetRepository.deleteById(id);
    }
}
