/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Portifolio.Portifolio.service;

import Portifolio.Portifolio.DTO.UsuarioDTO;
import Portifolio.Portifolio.entity.Usuario;

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
