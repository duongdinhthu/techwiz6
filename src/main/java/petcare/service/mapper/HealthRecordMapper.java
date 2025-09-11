package petcare.service.mapper;

import org.mapstruct.*;
import petcare.domain.HealthRecord;
import petcare.service.dto.HealthRecordDTO;

/**
 * Mapper for the entity {@link HealthRecord} and its DTO {@link HealthRecordDTO}.
 */
@Mapper(componentModel = "spring")
public interface HealthRecordMapper extends EntityMapper<HealthRecordDTO, HealthRecord> {}
