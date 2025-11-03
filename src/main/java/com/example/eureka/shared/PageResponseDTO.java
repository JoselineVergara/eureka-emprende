package com.example.eureka.shared;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDTO<T> {

    private List<T> content;
    private PageableInfo pageable;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PageableInfo {
        private long length;      // Total de elementos
        private int size;         // Tamaño de página
        private int page;         // Página actual
        private int lastPage;     // Última página
    }

    // Método estático para crear desde Page de Spring
    public static <T> PageResponseDTO<T> fromPage(org.springframework.data.domain.Page<T> page) {
        PageableInfo pageableInfo = PageableInfo.builder()
                .length(page.getTotalElements())
                .size(page.getSize())
                .page(page.getNumber())
                .lastPage(page.getTotalPages() - 1)
                .build();

        return PageResponseDTO.<T>builder()
                .content(page.getContent())
                .pageable(pageableInfo)
                .build();
    }
}