package com.example.eureka.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id_usuario", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "genero", nullable = false)
    private String genero;

    @Column(name = "correo", nullable = false)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;

    @Column(name = "fecha_nacimiento", nullable = true)
    private Date fechaNacimiento;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", nullable = false)
    private Roles rol;
}
