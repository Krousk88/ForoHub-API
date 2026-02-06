package ForoHub.API.dto;

import ForoHub.API.datos.Usuario;

public record DatosListadoUsuario(Long id, String login){
    public DatosListadoUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin());
    }
}
