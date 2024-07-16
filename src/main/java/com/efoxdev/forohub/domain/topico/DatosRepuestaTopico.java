package com.efoxdev.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosRepuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion,
        String estado,
        String autor,
        String curso
) {
}
