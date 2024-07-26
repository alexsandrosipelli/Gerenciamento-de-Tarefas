/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.service;

import GerenciamentoTarefas.GerenciamentoTarefas.DTO.TarefaDTO;
import GerenciamentoTarefas.GerenciamentoTarefas.entity.Tarefa;
import java.util.List;

/**
 *
 * @author alexsandro
 */
public interface TarefaService {

    void salvarTarefa(TarefaDTO tarefaDTO, Long idUser);

    List<TarefaDTO> listarTarefasPorUsuario(Long idUser);

    void marcarTarefaConcluida(Long id);

    void marcarTarefaPendente(Long id);

    TarefaDTO buscarTarefaPorId(Long id);

    void editarTarefa(TarefaDTO tarefaDTO);

    void excluirTarefa(Long id);

    List<TarefaDTO> listarTarefasConcluidasPorUsuario(Long idUser);

    List<TarefaDTO> buscarTarefasPorTitulo(String titulo, Long usuarioId);
}
