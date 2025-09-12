package petcare.service;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petcare.domain.Discovery;
import petcare.repository.DiscoveryRepository;
import petcare.service.dto.DiscoveryDTO;
import petcare.service.mapper.DiscoveryMapper;

/**
 * Service Implementation for managing {@link petcare.domain.Discovery}.
 */
@Service
@Transactional
public class DiscoveryService {

    private static final Logger LOG = LoggerFactory.getLogger(DiscoveryService.class);

    private final DiscoveryRepository discoveryRepository;

    private final DiscoveryMapper discoveryMapper;

    public DiscoveryService(DiscoveryRepository discoveryRepository, DiscoveryMapper discoveryMapper) {
        this.discoveryRepository = discoveryRepository;
        this.discoveryMapper = discoveryMapper;
    }

    /**
     * Save a discovery.
     *
     * @param discoveryDTO the entity to save.
     * @return the persisted entity.
     */
    public DiscoveryDTO save(DiscoveryDTO discoveryDTO) {
        LOG.debug("Request to save Discovery : {}", discoveryDTO);
        Discovery discovery = discoveryMapper.toEntity(discoveryDTO);
        discovery = discoveryRepository.save(discovery);
        return discoveryMapper.toDto(discovery);
    }

    /**
     * Update a discovery.
     *
     * @param discoveryDTO the entity to save.
     * @return the persisted entity.
     */
    public DiscoveryDTO update(DiscoveryDTO discoveryDTO) {
        LOG.debug("Request to update Discovery : {}", discoveryDTO);
        Discovery discovery = discoveryMapper.toEntity(discoveryDTO);
        discovery = discoveryRepository.save(discovery);
        return discoveryMapper.toDto(discovery);
    }

    /**
     * Partially update a discovery.
     *
     * @param discoveryDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DiscoveryDTO> partialUpdate(DiscoveryDTO discoveryDTO) {
        LOG.debug("Request to partially update Discovery : {}", discoveryDTO);

        return discoveryRepository
            .findById(discoveryDTO.getId())
            .map(existingDiscovery -> {
                discoveryMapper.partialUpdate(existingDiscovery, discoveryDTO);

                return existingDiscovery;
            })
            .map(discoveryRepository::save)
            .map(discoveryMapper::toDto);
    }

    /**
     * Get one discovery by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DiscoveryDTO> findOne(Long id) {
        LOG.debug("Request to get Discovery : {}", id);
        return discoveryRepository.findById(id).map(discoveryMapper::toDto);
    }

    /**
     * Delete the discovery by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Discovery : {}", id);
        discoveryRepository.deleteById(id);
    }
}
