package ForoHub.API.repository;

import ForoHub.API.datos.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    boolean existsByTituloAndMensaje(String titulo, String mensaje);
    Page<Topico> findByActivoTrueAndCursoAndFechaCreacionYear(String curso, int year, Pageable paginacion);
    Page<Topico> findByActivoTrue(Pageable paginacion);
}
