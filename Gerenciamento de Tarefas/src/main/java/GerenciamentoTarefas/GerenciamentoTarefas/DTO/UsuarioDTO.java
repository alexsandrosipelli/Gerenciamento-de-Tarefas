/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.DTO;

import GerenciamentoTarefas.GerenciamentoTarefas.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @author alexsandro
 */
public class UsuarioDTO {

    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, message = "O nome de usuário deve conter no mínimo 3 caracteres e no maximo 40", max = 40)
    private String username;

    @NotBlank
    @NotNull
    @Size(min = 4, message = "A senha deve conter no mínimo 4 caracteres!", max = 256)
    private String password;
    private String confirmPassword;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsuarioDTO toDto(Usuario usuario) {
        this.id = usuario.getId();
        this.username = usuario.getUsername();
        this.password = usuario.getPassword();
        return this;
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setUsername(this.username);
        usuario.setPassword(this.password);
        return usuario;
    }
}
