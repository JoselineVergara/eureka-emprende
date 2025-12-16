package com.example.eureka.event.infrastructure.dto.request;

import com.example.eureka.articulo.infrastructure.specification.ValidationGroups;
import com.example.eureka.shared.enums.TipoEvento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoRequestDTO {

    @NotBlank(message = "El título es obligatorio", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    private String titulo;

    @NotBlank(message = "La descripción es obligatoria", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    private String descripcion;

    @NotNull(message = "La fecha del evento es obligatoria", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    private LocalDateTime fechaEvento;

    private String lugar;

    @NotNull(message = "El tipo de evento es obligatorio", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    private TipoEvento tipoEvento; // presencial o virtual

    private boolean activo;

    private String linkInscripcion;

    @NotNull(message = "La imagen es obligatoria", groups = {ValidationGroups.OnCreate.class})
    private MultipartFile imagen; // ← Campo para subida de archivo

}
