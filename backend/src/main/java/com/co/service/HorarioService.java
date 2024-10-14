package com.co.service;

import com.co.conversion.HorarioDTOConverter;
import com.co.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private HorarioDTOConverter horarioDTOConverter;

    public List<HorarioDTO> obtenerHorarios() {
        List<Horario> horarios = horarioRepository.findAll();
        return horarios.stream()
                .map(horarioDTOConverter::entityToDTO)
                .collect(Collectors.toList());
    }
}
