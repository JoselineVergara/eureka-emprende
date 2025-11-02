package com.example.eureka.entrepreneurship.repository;

import com.example.eureka.domain.model.Emprendimientos;
import com.example.eureka.domain.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmprendimientosRepository extends JpaRepository<Emprendimientos, Integer> {
    List<Emprendimientos> findByUsuarios(Usuarios usuarios);
    List<Emprendimientos> findByNombreComercialContainingIgnoreCase(String nombre);
    List<Emprendimientos> findByTiposEmprendimientos_SubTipo(String tiposEmprendimientosSubTipo);
    List<Emprendimientos> findByNombreComercialContainingIgnoreCaseAndTiposEmprendimientos_TipoContainingIgnoreCase(String nombreComercial, String tiposEmprendimientosTipo);

    List<Emprendimientos> findByTiposEmprendimientos_Tipo(String tiposEmprendimientosTipo);

    @Query("SELECT DISTINCT e FROM Emprendimientos e " +
            "LEFT JOIN e.tiposEmprendimientos te " +
            "LEFT JOIN EmprendimientoCategorias ec ON ec.emprendimiento.id = e.id " +
            "LEFT JOIN ec.categoria c " +
            "LEFT JOIN e.ciudades ci " +
            "WHERE (:nombre IS NULL OR LOWER(e.nombreComercial) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:tipo IS NULL OR LOWER(te.tipo) LIKE LOWER(CONCAT('%', :tipo, '%'))) " +
            "AND (:categoria IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :categoria, '%'))) " +
            "AND (:ciudad IS NULL OR LOWER(ci.nombreCiudad) LIKE LOWER(CONCAT('%', :ciudad, '%')))")
    List<Emprendimientos> findByFiltros(@Param("nombre") String nombre,
                                        @Param("tipo") String tipo,
                                        @Param("categoria") String categoria,
                                        @Param("ciudad") String ciudad);
}
