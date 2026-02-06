package ForoHub.API.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarRespuesta(
        @NotNull Long id,
        String mensaje
) { }
