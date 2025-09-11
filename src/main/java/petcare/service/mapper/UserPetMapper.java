package petcare.service.mapper;

import org.mapstruct.*;
import petcare.domain.UserPet;
import petcare.service.dto.UserPetDTO;

/**
 * Mapper for the entity {@link UserPet} and its DTO {@link UserPetDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserPetMapper extends EntityMapper<UserPetDTO, UserPet> {}
