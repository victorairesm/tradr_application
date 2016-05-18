package br.com.victor.tradr.model;

import java.io.Serializable;

/**
 * Created by aluno on 12/05/16.
 */
public class Produto implements Serializable {

    private static final long serialVersionUID = 6601006766832473959L;

    private Long id;
    private Long categoria;
    private Long estado;
    private Double valor;
    private String nome;
    private String descricao;
    private String urlFoto;
    private Long cpf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoria() {
        return categoria;
    }

    public void setCategoria(Long categoria) {
        this.categoria = categoria;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public Long getCpf() {return cpf;}

    public void setCpf(Long cpf) {this.cpf = cpf;}

    @Override
    public String toString() {
        return "Produto [Id: " + getId() + ", Nome: " + getNome() + ", Categoria: " + getCategoria() + ", Estado: " + getEstado() + ", Valor: " + getValor() + ", Descrição: " + getDescricao() + ", UrlFoto: " + getUrlFoto() +", Cpf: " + getCpf() +  "]";
    }
}
