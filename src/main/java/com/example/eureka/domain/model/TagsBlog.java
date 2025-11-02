package com.example.eureka.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tags_blog")
public class TagsBlog {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tag", nullable = false)
    private Integer idTag;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private Set<ArticulosBlog> articulos = new HashSet<>();
}