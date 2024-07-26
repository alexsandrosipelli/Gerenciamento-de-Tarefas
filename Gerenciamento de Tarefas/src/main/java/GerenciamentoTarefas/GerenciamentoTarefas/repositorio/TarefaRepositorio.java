/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.repositorio;

import GerenciamentoTarefas.GerenciamentoTarefas.entity.Tarefa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author alexsandro
 */
public interface TarefaRepositorio extends JpaRepository<Tarefa, Long> {

    List<Tarefa> findByUsuarioId(Long idUser);

    List<Tarefa> findByUsuarioIdAndConcluida(Long id, boolean concluida);

    List<Tarefa> findByTituloContainingAndUsuarioId(String titulo, Long usuarioId);

}
