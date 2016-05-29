package br.com.victor.tradr.domain;

import java.io.Serializable;

@org.parceler.Parcel
public class Produto implements Serializable {

    private static final long serialVersionUID = 6601006766832473959L;

    public Long id;
    public Long categoria;
    public Long estado;
    public Double valor;
    public String nome;
    public String descricao;
    public String urlFoto;
    public Long cpf;
    // Flag para a action bar de contexto
    public boolean selected;
    // Está favoritado sem vem do banco de dadosine
    public boolean favorited;

    @Override
    public String toString() {
        return "Produto [Id: " + this.id + ", Nome: " + this.nome + ", Categoria: " + this.categoria + ", Estado: " + this.estado + ", Valor: " + this.valor + ", Descrição: " + this.descricao + ", UrlFoto: " + this.urlFoto +", Cpf: " + this.cpf +  "]";
    }
}
