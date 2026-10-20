package br.com.alphatech.agrowatt.repositorio;

import br.com.alphatech.agrowatt.dominio.Calculo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculoRepository extends JpaRepository<Calculo, Integer> {

    List<Calculo> findAllByOrderByDataCalculoDescIdDesc();
}
