/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.service;

import GerenciamentoTarefas.GerenciamentoTarefas.DTO.UsuarioDTO;
import GerenciamentoTarefas.GerenciamentoTarefas.entity.Usuario;

/**
 *
 * @author alexsandro
 */
public interface UsuarioService {

    void salvarUsuario(UsuarioDTO usuarioDTO);

    boolean findUser(String username);

    boolean validarCredenciais(String username, String password);

    Usuario findByUsername(String username);

    Usuario findById(Long id);
}
