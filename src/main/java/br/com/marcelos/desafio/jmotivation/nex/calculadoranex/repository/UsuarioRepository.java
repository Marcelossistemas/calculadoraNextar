package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByLogin(String login);
}
