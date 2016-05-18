package br.com.victor.tradr.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import victor.lib.utils.HttpHelper;

/**
 * Created by victo on 17/05/2016.
 */
public class ProdutoGetJSON {

    private static final String TAG = "Webservice";


    public static List<Produto> getProdutos(String url) throws IOException {
        String json = new HttpHelper().doGet(url); //obtém o objeto JSON do servidor através de um GET

        //Converte JSON para um List
        Type listType = new TypeToken<ArrayList<Produto>>() {}.getType();
        List<Produto> produtos = new Gson().fromJson(json, listType);

        return produtos;
    }
}
