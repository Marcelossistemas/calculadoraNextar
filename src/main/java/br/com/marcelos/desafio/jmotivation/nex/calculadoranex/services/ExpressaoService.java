package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services;

import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Expressao;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.repository.ExpressaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpressaoService {

    @Autowired
    private ExpressaoRepository expressaoRepository;

    public List<Expressao> findAll() {
        return expressaoRepository.findAll();
    }

    public Optional<Expressao> findById(Long id) {
        return expressaoRepository.findById(id);
    }

    public Optional<Expressao> findByExpressao(String expressao) {
        return expressaoRepository.findByExpressao(expressao);
    }

    public Expressao save(Expressao expressao) {
        return expressaoRepository.save(expressao);
    }

    public void deleteById(Long id) {
        expressaoRepository.deleteById(id);
    }
}
