package com.example.eureka.entrepreneurship.scheduler;

import com.example.eureka.entrepreneurship.repository.IEventosRepository;
import com.example.eureka.enums.EstadoEvento;
import com.example.eureka.model.Eventos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventoScheduler {

    private final IEventosRepository eventosRepository;

    /**
     * Ejecuta cada segundo para verificar eventos que ya pasaron su fecha
     * y actualiza su estado a TERMINADO
     */
    @Scheduled(fixedRate = 1000) // Ejecuta cada 1000ms (1 segundo)
    @Transactional
    public void actualizarEventosTerminados() {
        try {
            LocalDateTime ahora = LocalDateTime.now();

            // Buscar eventos programados o en curso cuya fecha ya pasó
            List<Eventos> eventosVencidos = eventosRepository
                    .findByEstadoEventoAndFechaEventoBefore(
                            (EstadoEvento.programado),
                            ahora
                    );

            if (!eventosVencidos.isEmpty()) {
                for (Eventos evento : eventosVencidos) {
                    evento.setEstadoEvento(EstadoEvento.terminado);
                    evento.setFechaModificacion(ahora);
                }

                eventosRepository.saveAll(eventosVencidos);

                log.info("Se actualizaron {} eventos a estado TERMINADO", eventosVencidos.size());
            }

        } catch (Exception e) {
            log.error("Error al actualizar eventos terminados: {}", e.getMessage(), e);
        }
    }
}