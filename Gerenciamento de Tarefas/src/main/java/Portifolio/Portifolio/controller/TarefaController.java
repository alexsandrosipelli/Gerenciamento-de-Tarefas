/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Portifolio.Portifolio.controller;

import Portifolio.Portifolio.DTO.TarefaDTO;
import Portifolio.Portifolio.entity.Tarefa;
import Portifolio.Portifolio.entity.Usuario;
import Portifolio.Portifolio.service.TarefaService;
import Portifolio.Portifolio.service.UsuarioService;
import Portifolio.Portifolio.serviceImplements.TarefaServiceImpl;
import Portifolio.Portifolio.serviceImplements.UsuarioServiceImpl;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author alexsandro
 */
@Controller
@RequestMapping("/usuario/tarefa")
public class TarefaController {

    private final TarefaServiceImpl tarefaServiceImpl;

    @Autowired
    public TarefaController(TarefaServiceImpl tarefaServiceImpl) {
        this.tarefaServiceImpl = tarefaServiceImpl;

    }

    @PostMapping("/salvar")
    public String salvarTarefa(@ModelAttribute TarefaDTO tarefaDTO, HttpSession session) {
        Usuario usuarioLogado = getUsuarioLogado(session);
        if (usuarioLogado != null) {
            tarefaServiceImpl.salvarTarefa(tarefaDTO, usuarioLogado.getId());
            return "redirect:/usuario/tarefa/listar";
        } else {
            return "redirect:/usuario/login";
        }
    }

    @GetMapping("/listar")
    public String listar(Model model, HttpSession session) {
        Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
        if (usuarioLogado != null) {
            List<TarefaDTO> tarefas = tarefaServiceImpl.listarTarefasPorUsuario(usuarioLogado.getId());
            model.addAttribute("tarefas", tarefas);
            return "Tarefas";
        } else {
            return "redirect:/usuario/login";
        }
    }

    @GetMapping("/nova")
    public String mostrarFormularioTarefa() {
        return "Cadastrar-Tarefa";
    }

    @PostMapping("/marcarConcluida/{id}")
    public String marcarTarefaConcluida(@PathVariable("id") Long id) {
        tarefaServiceImpl.marcarTarefaConcluida(id);
        return "redirect:/usuario/tarefa/listar";
    }

    // Método para editar uma tarefa
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        TarefaDTO tarefaDTO = tarefaServiceImpl.buscarTarefaPorId(id);
        System.out.println("AQUI" + tarefaDTO.getDescricao());
        model.addAttribute("tarefa", tarefaDTO);
        return "EditarTarefa";
    }

    // Método para salvar as alterações de uma tarefa editada
    @PostMapping("/editar")
    public String editarTarefa(@ModelAttribute TarefaDTO tarefaDTO) {
        tarefaServiceImpl.editarTarefa(tarefaDTO);
        return "redirect:/usuario/tarefa/listar";
    }

    // Método para excluir uma tarefa
    @PostMapping("/excluir/{id}")
    public String excluirTarefa(@PathVariable Long id) {
        tarefaServiceImpl.excluirTarefa(id);
        return "redirect:/usuario/tarefa/listar";
    }

    private Usuario getUsuarioLogado(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }
}
