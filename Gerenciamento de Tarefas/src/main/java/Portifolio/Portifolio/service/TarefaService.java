/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Portifolio.Portifolio.service;

import Portifolio.Portifolio.DTO.TarefaDTO;
import java.util.List;

/**
 *
 * @author alexsandro
 */
public interface TarefaService {

    void salvarTarefa(TarefaDTO tarefaDTO, Long idUser);

    List<TarefaDTO> listarTarefasPorUsuario(Long idUser);

    void marcarTarefaConcluida(Long id);

    TarefaDTO buscarTarefaPorId(Long id);

    void editarTarefa(TarefaDTO tarefaDTO);

    void excluirTarefa(Long id);
}
