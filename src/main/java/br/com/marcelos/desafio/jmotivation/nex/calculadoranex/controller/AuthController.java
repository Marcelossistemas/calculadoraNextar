package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.controller;


import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Usuario;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.repository.UsuarioRepository;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.security.UsuarioDetalhes;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/authenticate")
    public String createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        Usuario usuario = usuarioRepository.findByLogin(authRequest.getUsername());

        if (usuario == null || !passwordEncoder.matches(authRequest.getPassword(), usuario.getPassword())) {
            throw new Exception("Incorrect username or password");
        }

        UserDetails userDetails = new UsuarioDetalhes(usuario);
        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return jwt;
    }
}

class AuthRequest {
    private String username;
    private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

    
}
