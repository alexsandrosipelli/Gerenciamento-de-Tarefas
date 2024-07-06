/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GerenciamentoTarefas.GerenciamentoTarefas.DTO;

import GerenciamentoTarefas.GerenciamentoTarefas.entity.Tarefa;
import GerenciamentoTarefas.GerenciamentoTarefas.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

/**
 *
 * @author alexsandro
 */
public class TarefaDTO {

    private Long id;

    @NotBlank
    @NotNull
    @Size(min = 1, max = 100, message = "O título deve ter entre 1 e 100 caracteres")
    private String titulo;

    @NotBlank
    @NotNull
    @Size(max = 500, message = "A descrição não pode exceder 500 caracteres!")
    private String descricao;

    @NotNull
    private LocalDate data;

    @NotBlank
    @NotNull
    @Size(max = 50, message = "A categoria deve ter no máximo 50 caracteres")
    private String categoria;

    private Long userId;

    private boolean concluida;

    public TarefaDTO() {
    }

    public TarefaDTO(Long id, String titulo, String descricao, LocalDate data, String categoria, Long userId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.categoria = categoria;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public static TarefaDTO toDto(Tarefa tarefa) {
        TarefaDTO dto = new TarefaDTO();
        dto.setId(tarefa.getId());
        dto.setTitulo(tarefa.getTitulo());
        dto.setDescricao(tarefa.getDescricao());
        dto.setData(tarefa.getData());
        dto.setCategoria(tarefa.getCategoria());
        dto.setUserId(tarefa.getUsuario().getId());
        return dto;
    }

    public static Tarefa toEntity(TarefaDTO dto, Usuario user) {
        Tarefa tarefa = new Tarefa();
        tarefa.setId(dto.getId());
        tarefa.setTitulo(dto.getTitulo());
        tarefa.setDescricao(dto.getDescricao());
        tarefa.setData(dto.getData());
        tarefa.setCategoria(dto.getCategoria());
        tarefa.setUsuario(user);
        return tarefa;
    }
}
