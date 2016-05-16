/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.victor.rest;

import model.Produto;
import br.com.victor.domain.ProdutoService;
import br.com.victor.domain.Response;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author victo
 */
@Path("/produtos")
@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
public class ProdutosResource {
    private ProdutoService produtoService = new ProdutoService();

	@GET
	public List<Produto> get() {
		List<Produto> produtos = produtoService.getProdutos();
		return produtos;
	}

	@GET
	@Path("{id_produto}")
	public Produto get(@PathParam("id_produto") long id) {
		Produto c = produtoService.getProduto(id);
		return c;
	}

	@GET
	@Path("/categoria/{categoria}")
	public List<Produto> getByTipo(@PathParam("categoria") String categoria) {
		List<Produto> produtos = produtoService.findByGategoria(categoria);
		return produtos;
	}

	@GET
	@Path("/nome/{nome}")
	public List<Produto> getByNome(@PathParam("nome") String nome) {
		List<Produto> produtos = produtoService.findByName(nome);
		return produtos;
	}

	@DELETE
	@Path("{id_produto}")
	public Response delete(@PathParam("id_produto") long id) {
		produtoService.delete(id);
		return Response.Ok("Produto deletado com sucesso");
	}

	@POST
	public Response post(Produto c) {
		produtoService.save(c);
		return Response.Ok("Produto salvo com sucesso");
	}

	@PUT
	public Response put(Produto c) {
		produtoService.save(c);
		return Response.Ok("Produto atualizado com sucesso");
	}
}
