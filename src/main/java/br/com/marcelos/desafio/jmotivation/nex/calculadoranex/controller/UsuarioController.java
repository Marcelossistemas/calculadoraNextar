package br.com.marcelos.desafio.jmotivation.nex.calculadoranex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.model.Usuario;
import br.com.marcelos.desafio.jmotivation.nex.calculadoranex.services.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService userService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        Usuario registeredUser = userService.registerUser(usuario);
        return ResponseEntity.ok(registeredUser);
    }

    @PutMapping("/update-password")
    public ResponseEntity<Usuario> updateUserPassword(@RequestParam String login, @RequestParam String newPassword) {
        Usuario updatedUser = userService.updateUserPassword(login, newPassword);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
