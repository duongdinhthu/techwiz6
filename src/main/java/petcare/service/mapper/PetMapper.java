package petcare.service.mapper;

import org.mapstruct.*;
import petcare.domain.Pet;
import petcare.service.dto.PetDTO;

/**
 * Mapper for the entity {@link Pet} and its DTO {@link PetDTO}.
 */
@Mapper(componentModel = "spring")
public interface PetMapper extends EntityMapper<PetDTO, Pet> {}
