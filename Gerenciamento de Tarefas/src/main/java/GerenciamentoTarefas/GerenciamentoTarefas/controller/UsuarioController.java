/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.controller;

import GerenciamentoTarefas.GerenciamentoTarefas.DTO.TarefaDTO;
import GerenciamentoTarefas.GerenciamentoTarefas.DTO.UsuarioDTO;
import GerenciamentoTarefas.GerenciamentoTarefas.entity.Usuario;
import GerenciamentoTarefas.GerenciamentoTarefas.serviceImplements.TarefaServiceImpl;
import GerenciamentoTarefas.GerenciamentoTarefas.serviceImplements.UsuarioServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author alexsandro
 */
@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioServiceImpl usuarioServiceImpl;
    private final TarefaServiceImpl tarefaServiceImpl;

    @Autowired
    public UsuarioController(UsuarioServiceImpl usuarioServiceImpl, TarefaServiceImpl tarefaServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
        this.tarefaServiceImpl = tarefaServiceImpl;

    }

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "Login";
    }

    @PostMapping("/login")
    public String realizarLogin(@RequestParam String username, @RequestParam String password, Model model, HttpSession session) {
        if (!usuarioServiceImpl.validarCredenciais(username, password)) {
            model.addAttribute("error", "Credenciais inválidas. Verifique seu nome de usuário e senha.");
            return "Login";
        }

        Usuario usuarioLogado = usuarioServiceImpl.findByUsername(username);
        session.setAttribute("usuarioLogado", usuarioLogado);
        return "redirect:/usuario/tarefa/listar";
    }

    @GetMapping("/logout")
    public String realizarLogout(HttpSession session) {
        session.invalidate();
        return "redirect:/usuario/login";
    }

    @GetMapping("/novo")
    public String mostrarFormularioNovoUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioDTO());
        return "Novo-Usuario";
    }

    @PostMapping("/novo")
    public String salvarNovoUsuario(@Valid @ModelAttribute("usuario") UsuarioDTO usuarioDTO, BindingResult result, RedirectAttributes attributes) {

        if (!usuarioDTO.getPassword().equals(usuarioDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "password.mismatch", "As senhas não correspondem. Por favor, digite novamente.");
            return "Novo-Usuario";
        }
        if (result.hasErrors()) {
            return "Novo-Usuario";
        }

        if (usuarioServiceImpl.findUser(usuarioDTO.getUsername())) {
            result.rejectValue("username", "Username.exists", "O usuário já está cadastrado. Por favor, escolha outro.");
            return "Novo-Usuario";
        }

        usuarioServiceImpl.salvarUsuario(usuarioDTO);
        attributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
        return "redirect:/usuario/login";
    }
}
