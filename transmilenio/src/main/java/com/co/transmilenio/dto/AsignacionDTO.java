package com.co.transmilenio.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsignacionDTO {

    private Long id;
    private Long conductorId;
    private List<Long> busesIds;
}
