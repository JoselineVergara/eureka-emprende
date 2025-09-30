package com.example.eureka.auth.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserDTO {

    @NonNull
    public String nombre;

    public String apellido;

    @NonNull
    public String correo;

    @NonNull
    public String contrasena;
}
