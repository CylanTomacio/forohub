package com.efoxdev.forohub.domain.topico;

public record DatosActualizarTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        Curso curso
) {
}
