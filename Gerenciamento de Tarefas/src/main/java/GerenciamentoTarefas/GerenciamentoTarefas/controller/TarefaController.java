/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.controller;

import GerenciamentoTarefas.GerenciamentoTarefas.DTO.TarefaDTO;
import GerenciamentoTarefas.GerenciamentoTarefas.entity.Tarefa;
import GerenciamentoTarefas.GerenciamentoTarefas.entity.Usuario;
import GerenciamentoTarefas.GerenciamentoTarefas.service.TarefaService;
import GerenciamentoTarefas.GerenciamentoTarefas.service.UsuarioService;
import GerenciamentoTarefas.GerenciamentoTarefas.serviceImplements.TarefaServiceImpl;
import GerenciamentoTarefas.GerenciamentoTarefas.serviceImplements.UsuarioServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/nova")
    public String mostrarFormularioTarefa(Model model, HttpSession session) {

        Usuario usuarioLogado = getUsuarioLogado(session);
        if (usuarioLogado != null) {
            TarefaDTO tarefaDTO = new TarefaDTO();
            model.addAttribute("tarefaDTO", tarefaDTO);

            return "Cadastrar-Tarefa";
        }
        return "redirect:/usuario/login";

    }

    @PostMapping("/salvar")
    public String salvarTarefa(@ModelAttribute("tarefaDTO")
            @Valid TarefaDTO tarefaDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {

        Usuario usuarioLogado = getUsuarioLogado(session);
        if (usuarioLogado != null) {
            if (result.hasErrors()) {
                return "Cadastrar-Tarefa";
            }
            tarefaServiceImpl.salvarTarefa(tarefaDTO, usuarioLogado.getId());
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa salva com sucesso!");
            return "redirect:/usuario/tarefa/listar";
        } else {
            return "redirect:/usuario/login";
        }
    }

     @GetMapping("/listar")
    public String listar(@RequestParam(value = "query", required = false) String query, Model model, HttpSession session) {
        Usuario usuarioLogado = getUsuarioLogado(session);
        if (usuarioLogado != null) {
            List<TarefaDTO> tarefas;
            if (query != null && !query.isEmpty()) {
                tarefas = tarefaServiceImpl.buscarTarefasPorTitulo(query, usuarioLogado.getId());
            } else {
                tarefas = tarefaServiceImpl.listarTarefasPorUsuario(usuarioLogado.getId());
            }
            model.addAttribute("tarefas", tarefas);
            model.addAttribute("query", query);
            return "Listar-Tarefas";
        } else {
            return "redirect:/usuario/login";
        }
    }

    @GetMapping("/Pesquisar")
    public String buscarTarefas(@RequestParam("query") String query, Model model, HttpSession session) {
        Usuario usuarioLogado = getUsuarioLogado(session);
        if (usuarioLogado != null) {
            List<TarefaDTO> tarefas = tarefaServiceImpl.buscarTarefasPorTitulo(query, usuarioLogado.getId());
            model.addAttribute("tarefas", tarefas);
            return "Listar-Tarefas";  
        } else {
            return "redirect:/usuario/login";
        }
    }

    @PostMapping("/marcarConcluida/{id}")
    public String marcarTarefaConcluida(@PathVariable("id") Long id, RedirectAttributes redirectAttributes
    ) {
        tarefaServiceImpl.marcarTarefaConcluida(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa marcada como concluída com sucesso!");
        return "redirect:/usuario/tarefa/listar";
    }

    @GetMapping("/apresentar/{id}")
    public String apresentarTarefa(@PathVariable Long id, Model model) {
        TarefaDTO tarefaDTO = tarefaServiceImpl.buscarTarefaPorId(id);
        model.addAttribute("tarefa", tarefaDTO);
        return "apresentar-tarefa";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model
    ) {
        TarefaDTO tarefaDTO = tarefaServiceImpl.buscarTarefaPorId(id);
        model.addAttribute("tarefa", tarefaDTO);

        return "Editar-Tarefa";
    }

    @PostMapping("/editar")
    public String editarTarefa(@ModelAttribute("tarefa") @Valid TarefaDTO tarefaDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            HttpSession session,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("tarefa", tarefaDTO);
            return "Editar-Tarefa";
        }

        tarefaServiceImpl.editarTarefa(tarefaDTO);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa editada com sucesso!");
        return "redirect:/usuario/tarefa/listar";
    }

    @GetMapping("/editarConcluida/{id}")
    public String mostrarFormularioEditarConcluida(@PathVariable Long id, Model model
    ) {
        TarefaDTO tarefaDTO = tarefaServiceImpl.buscarTarefaPorId(id);
        model.addAttribute("tarefa", tarefaDTO);

        return "Editar-Tarefa";
    }

    @PostMapping("/editar/concluida")
    public String editarTarefaConcluida(@ModelAttribute("tarefa") @Valid TarefaDTO tarefaDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            HttpSession session,
            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("tarefa", tarefaDTO);
            return "Editar-Tarefa";
        }

        tarefaServiceImpl.editarTarefa(tarefaDTO);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa editada com sucesso!");
        return "redirect:/usuario/tarefa/concluidas";
    }

    @PostMapping("/excluir/{id}")
    public String excluirTarefa(@PathVariable Long id, RedirectAttributes redirectAttributes
    ) {
        tarefaServiceImpl.excluirTarefa(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa excluída com sucesso!");
        return "redirect:/usuario/tarefa/listar";
    }

    @PostMapping("/excluirTarefaConcluida/{id}")
    public String excluirTarefaConcluida(@PathVariable Long id, RedirectAttributes redirectAttributes
    ) {
        tarefaServiceImpl.excluirTarefa(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa excluída com sucesso!");

        return "redirect:/usuario/tarefa/concluidas";
    }

    private Usuario getUsuarioLogado(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogado");
    }

    @GetMapping("/concluidas")
    public String listarTarefasConcluidas(Model model, HttpSession session) {
        Usuario usuarioLogado = getUsuarioLogado(session);
        if (usuarioLogado != null) {
            List<TarefaDTO> tarefasConcluidas = tarefaServiceImpl.listarTarefasConcluidasPorUsuario(usuarioLogado.getId());
            model.addAttribute("tarefas", tarefasConcluidas);
            return "Tarefas-Concluidas";
        } else {
            return "redirect:/usuario/login";
        }
    }

    @PostMapping("/marcarPendente/{id}")
    public String marcarTarefaPendente(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        tarefaServiceImpl.marcarTarefaPendente(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tarefa marcada como pendente com sucesso!");
        return "redirect:/usuario/tarefa/concluidas";
    }
}
