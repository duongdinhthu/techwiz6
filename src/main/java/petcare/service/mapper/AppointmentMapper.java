package petcare.service.mapper;

import org.mapstruct.*;
import petcare.domain.Appointment;
import petcare.service.dto.AppointmentDTO;

/**
 * Mapper for the entity {@link Appointment} and its DTO {@link AppointmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppointmentMapper extends EntityMapper<AppointmentDTO, Appointment> {}
