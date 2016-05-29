/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.victor.domain;

import dao.ProdutoDAO;
import model.Produto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aluno
 */
public class ProdutoService {

    private ProdutoDAO db = new ProdutoDAO();

    // Lista todos os produtos do banco de dados
    public List<Produto> getProdutos() {
        try {
            List<Produto> produtos = db.getProdutos();
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<Produto>();
        }
    }

    // Busca um produtos pelo id
    public Produto getProduto(Long id) {
        try {
            return db.getProdutoById(id);
        } catch (SQLException e) {
            return null;
        }
    }

    // Deleta o produtos pelo id
    public boolean delete(Long id) {
        try {
            return db.delete(id);
        } catch (SQLException e) {
            return false;
        }
    }

    // Salva ou atualiza o produtos
    public boolean save(Produto produto) {
        try {
            db.save(produto);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // Busca o produtos pelo nome
    public List<Produto> findByName(String name) {
        try {
            return db.findByName(name);
        } catch (SQLException e) {
            return null;
        }
    }

    public List<Produto> findByGategoria(Long categoria) {
        try {
            return db.findByCategoria(categoria);
        } catch (SQLException e) {
            return null;
        }
    }

}
