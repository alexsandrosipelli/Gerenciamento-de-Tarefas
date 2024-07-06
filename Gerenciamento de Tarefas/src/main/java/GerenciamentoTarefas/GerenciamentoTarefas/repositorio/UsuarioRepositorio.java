/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.repositorio;

import GerenciamentoTarefas.GerenciamentoTarefas.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alexsandro
 */
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
    Usuario findByUsername(String username);
}
