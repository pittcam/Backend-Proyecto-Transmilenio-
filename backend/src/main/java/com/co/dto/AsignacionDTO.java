package com.co.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AsignacionDTO {
    private Long id;
    private ConductorDTO conductor;
    private List<BusRutaDiaDTO> busRutaDias;
}
