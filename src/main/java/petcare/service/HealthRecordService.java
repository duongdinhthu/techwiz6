package petcare.service;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petcare.domain.HealthRecord;
import petcare.repository.HealthRecordRepository;
import petcare.service.dto.HealthRecordDTO;
import petcare.service.mapper.HealthRecordMapper;

/**
 * Service Implementation for managing {@link petcare.domain.HealthRecord}.
 */
@Service
@Transactional
public class HealthRecordService {

    private static final Logger LOG = LoggerFactory.getLogger(HealthRecordService.class);

    private final HealthRecordRepository healthRecordRepository;

    private final HealthRecordMapper healthRecordMapper;

    public HealthRecordService(HealthRecordRepository healthRecordRepository, HealthRecordMapper healthRecordMapper) {
        this.healthRecordRepository = healthRecordRepository;
        this.healthRecordMapper = healthRecordMapper;
    }

    /**
     * Save a healthRecord.
     *
     * @param healthRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public HealthRecordDTO save(HealthRecordDTO healthRecordDTO) {
        LOG.debug("Request to save HealthRecord : {}", healthRecordDTO);
        HealthRecord healthRecord = healthRecordMapper.toEntity(healthRecordDTO);
        healthRecord = healthRecordRepository.save(healthRecord);
        return healthRecordMapper.toDto(healthRecord);
    }

    /**
     * Update a healthRecord.
     *
     * @param healthRecordDTO the entity to save.
     * @return the persisted entity.
     */
    public HealthRecordDTO update(HealthRecordDTO healthRecordDTO) {
        LOG.debug("Request to update HealthRecord : {}", healthRecordDTO);
        HealthRecord healthRecord = healthRecordMapper.toEntity(healthRecordDTO);
        healthRecord = healthRecordRepository.save(healthRecord);
        return healthRecordMapper.toDto(healthRecord);
    }

    /**
     * Partially update a healthRecord.
     *
     * @param healthRecordDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<HealthRecordDTO> partialUpdate(HealthRecordDTO healthRecordDTO) {
        LOG.debug("Request to partially update HealthRecord : {}", healthRecordDTO);

        return healthRecordRepository
            .findById(healthRecordDTO.getId())
            .map(existingHealthRecord -> {
                healthRecordMapper.partialUpdate(existingHealthRecord, healthRecordDTO);

                return existingHealthRecord;
            })
            .map(healthRecordRepository::save)
            .map(healthRecordMapper::toDto);
    }

    /**
     * Get one healthRecord by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<HealthRecordDTO> findOne(Long id) {
        LOG.debug("Request to get HealthRecord : {}", id);
        return healthRecordRepository.findById(id).map(healthRecordMapper::toDto);
    }

    /**
     * Delete the healthRecord by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete HealthRecord : {}", id);
        healthRecordRepository.deleteById(id);
    }

    /**
     * Get all healthRecords by petId.
     *
     * @param petId the petId of the entity.
     * @return list of healthRecords.
     */
    @Transactional(readOnly = true)
    public List<HealthRecordDTO> findByPetId(Long petId) {
        LOG.debug("Request to get HealthRecords by petId : {}", petId);
        return healthRecordRepository.findByPetId(petId)
            .stream()
            .map(healthRecordMapper::toDto)
            .toList();
    }

    public Long countByOwnerId(Long ownerId) {
        return healthRecordRepository.countByOwnerId(ownerId);
    }

}
