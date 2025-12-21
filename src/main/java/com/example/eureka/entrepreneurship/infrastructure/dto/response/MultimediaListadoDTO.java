package com.example.eureka.entrepreneurship.infrastructure.dto.response;

import lombok.Data;

@Data
public class MultimediaListadoDTO {
    private String nombreActivo;
    private String urlArchivo;

    public MultimediaListadoDTO(String nombreActivo, String urlArchivo) {
        this.nombreActivo = nombreActivo;
        this.urlArchivo = urlArchivo;
    }
}
