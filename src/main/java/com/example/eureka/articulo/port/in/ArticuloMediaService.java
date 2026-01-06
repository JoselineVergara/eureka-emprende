package com.example.eureka.articulo.port.in;

import com.example.eureka.entrepreneurship.domain.model.Multimedia;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ArticuloMediaService {

    Multimedia subirNuevaImagen(MultipartFile archivo) throws IOException;

    void reemplazarImagenArticulo(Multimedia imagenActual, String urlActual) throws IOException;

    String extraerNombreArchivoDeUrl(String url);
}
