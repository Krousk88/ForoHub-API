package ForoHub.API.controller;


import ForoHub.API.datos.Usuario;
import ForoHub.API.dto.DatosActualizarUsuario;
import ForoHub.API.dto.DatosListadoUsuario;
import ForoHub.API.dto.DatosRegistroUsuario;
import ForoHub.API.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public ResponseEntity registrarUsuario(@Valid @RequestBody DatosRegistroUsuario datos) {
        var usuario = repository.save(new Usuario(datos));
        return ResponseEntity.ok(new DatosListadoUsuario(usuario));
    }

    @GetMapping
    public ResponseEntity<List<DatosListadoUsuario>> listar() {
        var lista = repository.findAll().stream().map(DatosListadoUsuario::new).toList();
        return ResponseEntity.ok(lista);
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarUsuario datos) {
        var usuario = repository.getReferenceById(datos.id());
        usuario.actualizarDatos(datos);
        return ResponseEntity.ok(new DatosListadoUsuario(usuario));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
