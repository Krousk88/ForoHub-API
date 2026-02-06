package ForoHub.API.dto;

import ForoHub.API.datos.Topico;
import ForoHub.API.datos.Usuario;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean activo,
        Usuario autor,
        String curso
) {
    public DatosListadoTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(),
                topico.getFechaCreacion(), topico.getActivo(),
                topico.getAutor(), topico.getCurso());
    }
}
