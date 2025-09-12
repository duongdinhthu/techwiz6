package petcare.service.mapper;

import org.mapstruct.*;
import petcare.domain.Discovery;
import petcare.service.dto.DiscoveryDTO;

/**
 * Mapper for the entity {@link Discovery} and its DTO {@link DiscoveryDTO}.
 */
@Mapper(componentModel = "spring")
public interface DiscoveryMapper extends EntityMapper<DiscoveryDTO, Discovery> {}
