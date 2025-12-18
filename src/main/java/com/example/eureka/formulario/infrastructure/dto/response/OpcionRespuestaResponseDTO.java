package com.example.eureka.formulario.infrastructure.dto.response;

public class OpcionRespuestaResponseDTO {

    private Integer idRespuesta;
    private Integer idOpcion;
    private Integer valorescala;
    private Integer idEmprendimiento;

    public OpcionRespuestaResponseDTO() {}

    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(Integer idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public Integer getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(Integer idOpcion) {
        this.idOpcion = idOpcion;
    }

    public Integer getValorescala() {
        return valorescala;
    }

    public void setValorescala(Integer valorescala) {
        this.valorescala = valorescala;
    }

    public Integer getIdEmprendimiento() {
        return idEmprendimiento;
    }

    public void setIdEmprendimiento(Integer idEmprendimiento) {
        this.idEmprendimiento = idEmprendimiento;
    }
}
