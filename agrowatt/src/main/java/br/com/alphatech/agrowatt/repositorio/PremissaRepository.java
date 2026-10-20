package br.com.alphatech.agrowatt.repositorio;

import br.com.alphatech.agrowatt.dominio.Premissa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PremissaRepository extends JpaRepository<Premissa, String> {
}
