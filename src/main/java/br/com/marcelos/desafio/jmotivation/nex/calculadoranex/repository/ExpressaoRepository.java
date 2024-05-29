package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.repository;

import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Expressao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpressaoRepository extends JpaRepository<Expressao, Long> {
    Optional<Expressao> findByExpressao(String expressao);
}
