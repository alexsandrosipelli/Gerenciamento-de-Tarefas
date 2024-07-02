/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Portifolio.Portifolio.serviceImplements;

import Portifolio.Portifolio.DTO.TarefaDTO;
import Portifolio.Portifolio.entity.Tarefa;
import Portifolio.Portifolio.entity.Usuario;
import Portifolio.Portifolio.repositorio.TarefaRepositorio;
import Portifolio.Portifolio.service.TarefaService;
import Portifolio.Portifolio.service.UsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author alexsandro
 */
@Service
public class TarefaServiceImpl implements TarefaService {

    private final TarefaRepositorio tarefaRepositorio;
    private final UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    public TarefaServiceImpl(TarefaRepositorio tarefaRepositorio, UsuarioServiceImpl usuarioServiceImpl) {
        this.tarefaRepositorio = tarefaRepositorio;
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @Override
    public void salvarTarefa(TarefaDTO tarefaDTO, Long idUser) {
        Usuario usuario = usuarioServiceImpl.findById(idUser);
        Tarefa tarefa = TarefaDTO.toEntity(tarefaDTO, usuario);
        tarefaRepositorio.save(tarefa);
    }

    @Override
    public List<TarefaDTO> listarTarefasPorUsuario(Long idUser) {
        List<Tarefa> tarefas = tarefaRepositorio.findByUsuarioIdAndConcluida(idUser, false);
        return tarefas.stream().map(TarefaDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public void marcarTarefaConcluida(Long id) {
        Optional<Tarefa> optionalTarefa = tarefaRepositorio.findById(id);
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            tarefa.setConcluida(true);
            tarefaRepositorio.save(tarefa);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada com ID: " + id);
        }
    }

    @Override
    public void marcarTarefaPendente(Long id) {
        Optional<Tarefa> optionalTarefa = tarefaRepositorio.findById(id);
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            tarefa.setConcluida(false);
            tarefaRepositorio.save(tarefa);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada com ID: " + id);
        }
    }

    @Override
    public List<TarefaDTO> listarTarefasConcluidasPorUsuario(Long idUser) {
        List<Tarefa> tarefas = tarefaRepositorio.findByUsuarioIdAndConcluida(idUser, true);
        return tarefas.stream().map(TarefaDTO::toDto).collect(Collectors.toList());
    }

    @Override
    public TarefaDTO buscarTarefaPorId(Long id) {
        Optional<Tarefa> optionalTarefa = tarefaRepositorio.findById(id);
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            return TarefaDTO.toDto(tarefa);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada com ID: " + id);
        }
    }

    @Override
    public void editarTarefa(TarefaDTO tarefaDTO) {
        Optional<Tarefa> optionalTarefa = tarefaRepositorio.findById(tarefaDTO.getId());
        if (optionalTarefa.isPresent()) {
            Tarefa tarefa = optionalTarefa.get();
            tarefa.setTitulo(tarefaDTO.getTitulo());
            tarefa.setDescricao(tarefaDTO.getDescricao());
            tarefa.setData(tarefaDTO.getData());
            tarefa.setCategoria(tarefaDTO.getCategoria());
            tarefaRepositorio.save(tarefa);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada com ID: " + tarefaDTO.getId());
        }
    }

    @Override
    public void excluirTarefa(Long id) {
        Optional<Tarefa> optionalTarefa = tarefaRepositorio.findById(id);
        if (optionalTarefa.isPresent()) {
            tarefaRepositorio.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tarefa não encontrada com ID: " + id);
        }
    }

}
