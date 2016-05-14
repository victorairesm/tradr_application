package br.com.victor.tradr.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.victor.tradr.R;
import br.com.victor.tradr.adapter.CarroAdapter;
import br.com.victor.tradr.model.Carro;
import br.com.victor.tradr.model.CarroGetJSON;
import br.com.victor.tradr.util.AndroidUtils;

public class FeedFragment extends BaseFragment {

    private final String TAG = "Webservice"; //TAG para o LogCat
    protected RecyclerView recyclerView; //o container onde serão apresentados os dados
    private SwipeRefreshLayout swipeRefreshLayout; //o SwipeRefresh. O objeto que identificará o movimento de swipe e reagirá
    private ProgressBar progressBar; //uma animação para indicar processando
    private AlertDialog alertDialog; //uma caixa de diálogo para indicar falha no download

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);


        //configura a RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView); //mapeia o RecyclerView do layout.
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext())); //associa um gerenciador de layout Linear ao recyclerView.
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //associa um tipo de animação ao recyclerView.
        recyclerView.setHasFixedSize(true);

        //configura o SwipeRefreshLayout
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swinperefrechlayout);
        swipeRefreshLayout.setOnRefreshListener(OnRefreshListener());
        swipeRefreshLayout.setColorSchemeColors(R.color.refresh_progress_1, R.color.refresh_progress_2, R.color.refresh_progress_3);


        //Cria um ProgressBar para mostrar uma animação de processando
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        //cria um AlertDialog para informar o usuário em caso de falha no download
        AlertDialog.Builder builder= new AlertDialog.Builder(getContext()); //obtém o construtor de AlertDialog
        builder.setTitle("Erro"); //insere um título
        builder.setMessage("Falha ao tentar executar o download."); //insere uma mensagem no corpo do AlertDialog
        builder.setNeutralButton("Ok", null); //faz apresentar um botão de Ok
        alertDialog = builder.create(); //cria o AlertDialog


        if (AndroidUtils.isNetworkAvailable(getContext())) { //se houver conexão com a internet, wi-fi ou 3G ...
            new GetCarrosTask().execute(); //cria uma instância de AsyncTask a executa
        } else {
            AndroidUtils.alertDialog(getContext(), "Alerta de conectividade.", "Não há conexão com a internet. Vefirique se você ligou o wi-fi ou os dados móveis.");
        }

        return view;
    }

    /*
        Classe interna que extende uma AsyncTask.
        Lembrando: A AsyncTask gerencia a thread que acessa os dados na internet
     */
    private class GetCarrosTask extends AsyncTask<Void, Void, List<Carro>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE); //faz com que a ProgressBar apareça para o usuário
        }

        @Override
        protected List<Carro> doInBackground(Void... params) {
            //busca os carros em background, em uma thread exclusiva para esta tarefa.
            try {
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return CarroGetJSON.getCarros("http://192.168.0.13:8084/rest/carros");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Exceção ao obter a lista de carros, método .doInBackground()");
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Carro> carros) {
            super.onPostExecute(carros);
            if (carros != null) {
                Log.d(TAG, "Quantidade de carros no onPostExecute(): " + carros.size());
                //atualiza a view na UIThread
                recyclerView.setAdapter(new CarroAdapter(getContext(), carros, onClickCarro()));
                swipeRefreshLayout.setRefreshing(false); //para a animação da swipeRefrech
                progressBar.setVisibility(View.INVISIBLE); //faz com que a ProgressBar desapareça para o usuário
            }else{
                progressBar.setVisibility(View.INVISIBLE); //faz com que a ProgressBar desapareça para o usuário
                alertDialog.show(); //faz aparecer o AlertDialog para o usuário
            }
        }
    }



    /*
        Este método utiliza a interface declarada na classe PlanetaAdapter para tratar o evento onClick do item da lista.
     */
    protected CarroAdapter.CarroOnClickListener onClickCarro() {
        //chama o contrutor da interface (implícito) para cria uma instância da interface declarada no adaptador.
        return new CarroAdapter.CarroOnClickListener() {
            // Aqui trata o evento onItemClick.
            @Override
            public void onClickCarro(View view, int idx) {
                Toast.makeText(getContext(), "Você clicou no item da RecyclerView.", Toast.LENGTH_LONG).show();
            }
        };
    }

    /*
        Este método trata o evento onRefresh() do SwipeRefreshLayout.
        Ele acontece quando o usuário faz um swipe com o dedo para baixo na View.
     */

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (AndroidUtils.isNetworkAvailable(getContext())) { //se houver conexão com a internet, wi-fi ou 3G ...
                    if (AndroidUtils.isNetworkAvailable(getContext())) { //se houver conexão com a internet, wi-fi ou 3G ...
                        new GetCarrosTask().execute(); //cria uma instância de AsyncTask
                    } else {
                        AndroidUtils.alertDialog(getContext(), "Alerta de conectividade.", "Não há conexão com a internet. Vefirique se você ligou o wi-fi ou os dados móveis.");
                        recyclerView.setAdapter(new CarroAdapter(getContext(), new ArrayList<Carro>(), onClickCarro()));
                    }
                }
            }
        };
    }
}
