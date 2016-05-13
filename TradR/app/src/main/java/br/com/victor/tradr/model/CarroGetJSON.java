package br.com.victor.tradr.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;

import victor.lib.utils.HttpHelper;


/**
 *  Esta classe realiza o "parser" dos dados oriundos do web service no formato JSON para uma List.
 *  @author Vagner Pinto da Silva, baseado em Lecheta(2015).
 */


public class CarroGetJSON {

    private static final String TAG = "Webservice";


    public static List<Carro> getCarros(String url) throws IOException {
        String json = new HttpHelper().doGet(url); //obtém o objeto JSON do servidor através de um GET

        //Converte JSON para um List
        Type listType = new TypeToken<ArrayList<Carro>>() {}.getType();
        List<Carro> carros = new Gson().fromJson(json, listType);

        return carros;
    }
}
