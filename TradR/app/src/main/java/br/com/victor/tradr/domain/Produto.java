package br.com.victor.tradr.domain;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

@org.parceler.Parcel
public class Produto {

    //private static final long serialVersionUID = 6601006766832473959L;

    public Long id;
    public Integer categoria;
    public Long estado;
    public Double valor;
    public String nome;
    public String descricao;
    public String urlFoto;
    public Long cpf;
    public Timestamp data;
    // Flag para a action bar de contexto
    public boolean selected;
    // Está favoritado sem vem do banco de dados
    //public boolean marcar;

    @Override
    public String toString() {
        return "Produto [Id: " + this.id + ", Nome: " + this.nome + ", Categoria: " + this.categoria + ", Estado: " + this.estado + ", Valor: " + this.valor + ", Descrição: " + this.descricao + ", UrlFoto: " + this.urlFoto +", Cpf: " + this.cpf + "Data: " + new SimpleDateFormat("dd/MM/yyyy kk:mm:ss").format(this.data) + "]";
    }
}
