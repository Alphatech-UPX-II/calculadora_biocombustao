package br.com.alphatech.agrowatt.repositorio;

import br.com.alphatech.agrowatt.dominio.Residuo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResiduoRepository extends JpaRepository<Residuo, Integer> {

    List<Residuo> findAllByOrderByIdAsc();

    Optional<Residuo> findByNome(String nome);
}
