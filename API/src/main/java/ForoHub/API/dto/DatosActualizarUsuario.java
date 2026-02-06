package ForoHub.API.dto;

import jakarta.validation.constraints.NotNull;

public record DatosActualizarUsuario(
        @NotNull Long id,
        String login,
        String clave
) {
}
