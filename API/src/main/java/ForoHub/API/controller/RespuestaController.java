package ForoHub.API.controller;

import ForoHub.API.datos.Respuesta;
import ForoHub.API.dto.DatosActualizarRespuesta;
import ForoHub.API.dto.DatosDetalleRespuesta;
import ForoHub.API.dto.DatosRegistroRespuesta;
import ForoHub.API.repository.RespuestaRepository;
import ForoHub.API.repository.TopicoRepository;
import ForoHub.API.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class RespuestaController {

    @Autowired
    private RespuestaRepository repository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
         public ResponseEntity registrar(@RequestBody @Valid DatosRegistroRespuesta datos) {
        var topico = topicoRepository.findById(datos.topicoId())
                .orElseThrow(() -> new RuntimeException("Tópico no encontrado"));
        var autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        var respuesta = new Respuesta(datos, topico, autor);
        repository.save(respuesta);
        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }
    @GetMapping("/topico/{topicoId}")
    public ResponseEntity<Page<DatosDetalleRespuesta>> listarPorTopico(
            @PathVariable Long topicoId,
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion) {

        var pagina = repository.findAllByTopicoId(topicoId, paginacion);
        return ResponseEntity.ok(pagina.map(DatosDetalleRespuesta::new));
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizar(@RequestBody @Valid DatosActualizarRespuesta datos) {
        if (!repository.existsById(datos.id())) {
            return ResponseEntity.notFound().build();
        }
        var respuesta = repository.getReferenceById(datos.id());
        respuesta.actualizarInformacion(datos);

        return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
