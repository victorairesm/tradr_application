package model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aluno
 */
@XmlRootElement
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long estado;
    private Double valor;
    private String nome;
    private String descricao;
    private String urlFoto;
    private Long categoria;
    private Long cpf;
    private Date data;
    private Timestamp data_cadastro;

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

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Timestamp getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Timestamp data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    @Override
    public String toString() {
        return "Produto [Id: " + id + ", Nome: " + nome + ", Categoria: " + categoria + ", Estado: " + estado + ", Valor: " + valor + ", Descrição: " + descricao + ", UrlFoto: " + urlFoto + "]";
    }

}
