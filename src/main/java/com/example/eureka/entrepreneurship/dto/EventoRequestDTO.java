package com.example.eureka.entrepreneurship.dto;

import com.example.eureka.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequestDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @NotNull(message = "La fecha del evento es obligatoria")
    private LocalDateTime fechaEvento;

    @NotBlank(message = "El lugar es obligatorio")
    private String lugar;

    @NotNull(message = "El tipo de evento es obligatorio")
    private TipoEvento tipoEvento; // presencial o virtual

    private String linkInscripcion;

    private String direccion;

    @NotNull(message = "El ID de multimedia es obligatorio")
    Integer idMultimedia;
}
