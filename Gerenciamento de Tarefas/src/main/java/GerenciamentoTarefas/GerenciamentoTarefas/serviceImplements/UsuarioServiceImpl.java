/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.serviceImplements;

import GerenciamentoTarefas.GerenciamentoTarefas.DTO.UsuarioDTO;
import GerenciamentoTarefas.GerenciamentoTarefas.entity.Usuario;
import GerenciamentoTarefas.GerenciamentoTarefas.repositorio.UsuarioRepositorio;
import GerenciamentoTarefas.GerenciamentoTarefas.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author alexsandro
 */
@Service

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepositorio usuarioRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServiceImpl(UsuarioRepositorio usuarioRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void salvarUsuario(UsuarioDTO usuarioDTO) {

        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getPassword());
        usuarioDTO.setPassword(senhaCriptografada);
        Usuario usuario = usuarioDTO.toEntity();
        usuarioRepositorio.save(usuario);
    }

    @Override
    public boolean validarCredenciais(String username, String password) {
        Usuario usuario = usuarioRepositorio.findByUsername(username);
        if (usuario == null) {
            return false;
        }
        return passwordEncoder.matches(password, usuario.getPassword());
    }

    @Override
    public boolean findUser(String username) {
        Usuario usuario = usuarioRepositorio.findByUsername(username);

        return usuario != null;

    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioRepositorio.findByUsername(username);
    }

    @Override
    public Usuario findById(Long id) {
        return usuarioRepositorio.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }
}
