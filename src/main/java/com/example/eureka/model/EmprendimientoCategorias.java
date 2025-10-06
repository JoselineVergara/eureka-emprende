package com.example.eureka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emprendimiento_categorias")
@IdClass(EmprendimientoCategorias.EmprendimientoCategoriasId.class)
public class EmprendimientoCategorias {

    @Id
    @Column(name = "emprendimiento_id", nullable = false)
    private Integer emprendimientoId;

    @Id
    @Column(name = "categoria_id", nullable = false)
    private Integer categoriaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emprendimiento_id", insertable = false, updatable = false)
    private Emprendimientos emprendimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", insertable = false, updatable = false)
    private Categorias categoria;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmprendimientoCategoriasId implements Serializable {
        private Integer emprendimientoId;
        private Integer categoriaId;
    }
}