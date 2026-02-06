package ForoHub.API.dto;

import ForoHub.API.datos.Topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(Long id, String titulo, String mensaje, String nombreAutor, String curso) {
    public DatosDetalleTopico(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getLogin(),
                topico.getCurso());
    }
}