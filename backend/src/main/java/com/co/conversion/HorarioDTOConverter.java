package com.co.conversion;

import org.springframework.stereotype.Component;

@Component
public class HorarioDTOConverter {

    public HorarioDTO entityToDTO(Horario horario) {
        return new HorarioDTO(
                horario.getId(),
                horario.getHoraInicio(),
                horario.getHoraFin(),
                horario.getDias()
        );
    }

    public Horario dtoToEntity(HorarioDTO horarioDTO) {
        Horario horario = new Horario();
        horario.setId(horarioDTO.getId());
        horario.setHoraInicio(horarioDTO.getHoraInicio());
        horario.setHoraFin(horarioDTO.getHoraFin());
        horario.setDias(horarioDTO.getDias());
        return horario;
    }
}
