package com.example.eureka.articulo.aplication.service;

import com.example.eureka.articulo.port.in.ArticuloMediaService;
import com.example.eureka.entrepreneurship.domain.model.Multimedia;
import com.example.eureka.general.port.out.IMultimediaRepository;
import com.example.eureka.shared.exception.BusinessException;
import com.example.eureka.shared.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ArticuloMediaServiceImpl implements ArticuloMediaService {

    private final FileStorageService fileStorageService;
    private final IMultimediaRepository multimediaRepository;

    @Override
    public Multimedia subirNuevaImagen(MultipartFile archivo) throws IOException {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        String urlArchivo = fileStorageService.uploadFile(archivo);
        Multimedia multimedia = new Multimedia();
        multimedia.setUrlArchivo(urlArchivo);
        multimedia.setNombreActivo(archivo.getOriginalFilename());
        multimedia.setDescripcion("Imagen de artículo");
        return multimediaRepository.save(multimedia);
    }

    @Override
    public void reemplazarImagenArticulo(Multimedia imagenActual, String urlActual) throws IOException {
        if (imagenActual == null || urlActual == null || urlActual.isEmpty()) {
            return;
        }

        String oldFileName = extraerNombreArchivoDeUrl(urlActual);
        if (oldFileName != null) {
            fileStorageService.deleteFile(oldFileName);
        }
        multimediaRepository.delete(imagenActual);
    }

    @Override
    public String extraerNombreArchivoDeUrl(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }

        try {
            int lastSlashIndex = url.lastIndexOf('/');
            if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
                String fileName = url.substring(lastSlashIndex + 1);
                int queryIndex = fileName.indexOf('?');
                if (queryIndex != -1) {
                    fileName = fileName.substring(0, queryIndex);
                }
                return fileName;
            }
        } catch (Exception e) {
            System.err.println("Error al extraer nombre de archivo de URL: " + e.getMessage());
        }

        return null;
    }
}
