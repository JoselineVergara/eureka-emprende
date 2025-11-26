package com.example.eureka.domain.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"respuesta", "opciones"})  // Excluir relación del toString
@Table(name = "opcion_respuesta")
public class OpcionRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_respuesta", nullable = false)
    private Respuesta respuesta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_opcion", nullable = false)
    private Opciones opciones;

    @Column(name = "valor_escala", nullable = false)
    private Integer valorescala;





}
