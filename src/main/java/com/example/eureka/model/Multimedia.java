package com.example.eureka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "multimedia")
public class Multimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre_activo", nullable = false)
    private String nombreActivo;

    @Column(name = "url_archivo", nullable = false)
    private String urlArchivo;

    @Column(name = "tipo_multimedia", nullable = false)
    private String tipoMultimedia;

    @Column(name = "subtipo", nullable = false)
    private String subTipo;

    @Column(name = "mime_type", nullable = false)
    private String mimeType;

    @Column(name = "tamano_kb", nullable = false)
    private Integer tamanioKb;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "fecha_subida", nullable = false)
    private String fechaSubida;
}
