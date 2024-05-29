package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services;


import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Usuario;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.repository.UsuarioRepository;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.security.UsuarioDetalhes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsuarioDetalhesService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com o login: " + login);
        }
        return new UsuarioDetalhes(usuario);
    }
}
