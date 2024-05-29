package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Usuario;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    UsuarioService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    public Usuario registerUser(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Usuario updateUserPassword(String login, String newPassword) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario != null) {
            usuario.setPassword(passwordEncoder.encode(newPassword));
            usuarioRepository.save(usuario);
        }
        return usuario;
    }
}