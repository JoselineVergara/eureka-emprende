package com.example.eureka.enums;

public enum EstadoEmprendimiento {
    BORRADOR,           // Emprendimiento recién registrado
    PENDIENTE_REVISION, // Pendiente de revisión por administrador
    APROBADO,           // Aprobado
    PENDIENTE_CORRECCION // Pendiente de corrección por usuario
}