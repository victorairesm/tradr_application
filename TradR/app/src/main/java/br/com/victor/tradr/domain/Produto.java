package br.com.victor.tradr.domain;

import android.text.Editable;

import java.io.Serializable;

/**
 * Created by aluno on 12/05/16.
 */
public class Produto implements Serializable {

    private static final long serialVersionUID = 6601006766832473959L;

    public Long id;
    public Long categoria;
    public Long estado;
    public Long valor;
    public String nome;
    public String descricao;
    public String urlFoto;
    public Long cpf;

    public boolean favorited;

    @Override
    public String toString() {
        return "Produto [Id: " + this.id + ", Nome: " + this.nome + ", Categoria: " + this.categoria + ", Estado: " + this.estado + ", Valor: " + this.valor + ", Descrição: " + this.descricao + ", UrlFoto: " + this.urlFoto +", Cpf: " + this.cpf +  "]";
    }
}
