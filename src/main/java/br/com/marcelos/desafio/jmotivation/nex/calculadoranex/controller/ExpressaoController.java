package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.controller;


import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.dto.ExpressaoDTO;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Expressao;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services.ExpressaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.script.ScriptException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/expressoes")
public class ExpressaoController {

    @Autowired
    private ExpressaoService expressaoService;

    @GetMapping
    public List<Expressao> getAllExpressoes() {
        return expressaoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expressao> getExpressaoById(@PathVariable Long id) {
        Optional<Expressao> expressao = expressaoService.findById(id);
        return expressao.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Expressao> createExpressao(@RequestBody ExpressaoDTO expressaoDTO) {
        try {
            // Verificar se a expressão já existe no banco de dados
            Optional<Expressao> existingExpressao = expressaoService.findByExpressao(expressaoDTO.getExpressao());
            if (existingExpressao.isPresent()) {
                // Se a expressão já existe, retorne o valor existente
                return ResponseEntity.ok(existingExpressao.get());
            }

            // Criar uma nova instância de Expressao a partir do DTO
            Expressao expressao = new Expressao();
            expressao.setExpressao(expressaoDTO.getExpressao());

            // Calcular o resultado da expressão
            expressao.calcularResultado();

            // Salvar a expressão com o resultado calculado
            Expressao savedExpressao = expressaoService.save(expressao);

            // Retornar a expressão salva
            return ResponseEntity.ok(savedExpressao);
        } catch (ScriptException e) {
            // Retornar um bad request se houver erro no cálculo da expressão
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expressao> updateExpressao(@PathVariable Long id, @RequestBody Expressao expressaoDetails) {
        Optional<Expressao> optionalExpressao = expressaoService.findById(id);
        if (optionalExpressao.isPresent()) {
            Expressao expressao = optionalExpressao.get();
            expressao.setExpressao(expressaoDetails.getExpressao());
            try {
                expressao.calcularResultado(); // recalcular resultado
                return ResponseEntity.ok(expressaoService.save(expressao));
            } catch (ScriptException e) {
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpressao(@PathVariable Long id) {
        Optional<Expressao> optionalExpressao = expressaoService.findById(id);
        if (optionalExpressao.isPresent()) {
            expressaoService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
