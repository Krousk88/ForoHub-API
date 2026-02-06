package ForoHub.API.controller;

import ForoHub.API.datos.Topico;
import ForoHub.API.dto.DatosActualizacionTopico;
import ForoHub.API.dto.DatosDetalleTopico;
import ForoHub.API.dto.DatosListadoTopico;
import ForoHub.API.dto.DatosRegistroTopico;
import ForoHub.API.repository.TopicoRepository;
import ForoHub.API.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity registrar(@RequestBody @Valid DatosRegistroTopico datos) {

        var existeTopico = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (existeTopico) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con ese título y mensaje.");
        }

        var autor = usuarioRepository.findById(datos.autorId())
                .orElseThrow(() -> new RuntimeException("El autor con ID " + datos.autorId() + " no existe."));

        var topico = new Topico(datos, autor);
        repository.save(topico);

        return ResponseEntity.status(HttpStatus.CREATED).body(new DatosDetalleTopico(topico));
    }
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listar(
            @PageableDefault(size = 10, sort = "fechaCreacion", direction = Sort.Direction.ASC) Pageable paginacion,
            @RequestParam(required = false) String curso,
            @RequestParam(required = false) Integer year) {

        Page<Topico> pagina;

        if (curso != null && year != null) {
            pagina = repository.findByActivoTrueAndCursoAndFechaCreacionYear(curso, year, paginacion);
        } else {
            pagina = repository.findByActivoTrue(paginacion);
        }

        return ResponseEntity.ok(pagina.map(DatosListadoTopico::new));
    }
    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        var topico = repository.getReferenceById(id);
        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos) {
        Optional<Topico> topicoOptional = repository.findById(id);

        if (topicoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var esDuplicado = repository.existsByTituloAndMensaje(datos.titulo(), datos.mensaje());
        if (esDuplicado) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con los mismos datos.");
        }
        Topico topico = topicoOptional.get();
        topico.actualizarDatos(datos);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        Optional<Topico> topicoOptional = repository.findById(id);

        if (topicoOptional.isPresent()) {
            repository.deleteById(id);

            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
