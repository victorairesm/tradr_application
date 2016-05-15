/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.victor.domain;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author aluno
 */
@XmlRootElement(name = "produtos")
public class ListaProdutos implements Serializable{
    private static final long serialVersionUID = 1L;
	private List<Produto> produto;

	@XmlElement(name = "produto")
	public List<Produto> getProdutos() {
		return produto;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produto = produtos;
	}

	@Override
	public String toString() {
		return "ListaProdutos [produtos=" + produto + "]";
	}
}
