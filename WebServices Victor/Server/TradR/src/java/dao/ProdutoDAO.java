/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.mysql.jdbc.log.Log;
import model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aluno
 */
public class ProdutoDAO extends BaseDAO{
    
    public Produto getProdutoById(Long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            System.out.println("Entrou no post.");
            stmt = conn.prepareStatement("select * from produto where id_produto=?");
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            System.out.println("Entrou no post.");
            if (rs.next()) {
                Produto c = createProduto(rs);
                rs.close();
                return c;
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return null;
    }

    public List<Produto> findByName(String name) throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select * from produto where lower(nome) like ?");
            stmt.setString(1, "%" + name.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto c = createProduto(rs);
                produtos.add(c);
            }
            rs.close();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return produtos;
    }

    public List<Produto> findByCategoria(Long tipo) throws SQLException {
        System.out.println("Entrou no post.");
        List<Produto> produtos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select * from produto where id_categoria = ?");
            stmt.setLong(1, tipo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto c = createProduto(rs);
                produtos.add(c);
            }
            rs.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return produtos;
    }

    public List<Produto> getProdutos() throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("select * from produto");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto c = createProduto(rs);
                produtos.add(c);
            }
            rs.close();
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return produtos;
    }

    public Produto createProduto(ResultSet rs) throws SQLException {
        Produto p = new Produto();
        p.setId(rs.getLong("id_produto"));
        p.setEstado(rs.getLong("estado"));
        p.setValor(rs.getDouble("valor"));
        p.setNome(rs.getString("nome"));
        p.setDescricao(rs.getString("descricao"));
        p.setUrlFoto(rs.getString("urlFoto"));
        p.setCategoria(rs.getLong("id_categoria"));
        p.setCpf(rs.getLong("cpf"));
        return p;
    }

    public void save(Produto p) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            if (p.getId() == null) {
                stmt = conn.prepareStatement("insert into produto (id_categoria,cpf,estado,valor,nome,descricao,urlFoto) VALUES(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            } else {
                stmt = conn.prepareStatement("update produto set id_categoria=?,cpf=?,estado=?,valor=?,nome=?,descricao=?,urlFoto=? where id_produto=?");
            }
            stmt.setLong(1, p.getCategoria());
            stmt.setLong(2, p.getCpf());
            stmt.setLong(3, p.getEstado());
            stmt.setDouble(4, p.getValor());
            stmt.setString(5, p.getNome());
            stmt.setString(6, p.getDescricao());
            stmt.setString(7, p.getUrlFoto());
            if (p.getId() != null) {
                // Update
                stmt.setLong(8, p.getId());
            }
            int count = stmt.executeUpdate();
            if (count == 0) {
                throw new SQLException("Erro ao inserir o produto");
            }
            // Se inseriu, ler o id auto incremento
            if (p.getId() == null) {
                Long id = getGeneratedId(stmt);
                p.setId(id);
            }
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    // id gerado com o campo auto incremento
    public static Long getGeneratedId(Statement stmt) throws SQLException {
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            Long id = rs.getLong(1);
            return id;
        }
        return 0L;
    }

    public boolean delete(Long id) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = getConnection();
            stmt = conn.prepareStatement("delete from produto where id_produto=?");
            stmt.setLong(1, id);
            int count = stmt.executeUpdate();
            boolean ok = count > 0;
            return ok;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
    
}
