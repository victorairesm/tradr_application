package br.com.victor.tradr.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.otto.Subscribe;

import org.parceler.Parcels;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import br.com.victor.tradr.R;
import br.com.victor.tradr.activity.ProdutoActivity;
import br.com.victor.tradr.adapter.FeedAdapter;
import br.com.victor.tradr.domain.Produto;
import br.com.victor.tradr.domain.ProdutoService;
import br.com.victor.tradr.domain.event.BusEvent;
import victor.lib.utils.AndroidUtils;

public class FeedFragment extends BaseFragment {
    private final String TAG = "Webservice"; //TAG para o LogCat
    protected RecyclerView recyclerView; //o container onde serão apresentados os dados
    private List<Produto> produtos;
    private String categoria;
    private SwipeRefreshLayout swipeLayout; //o SwipeRefresh. O objeto que identificará o movimento de swipe e reagirá

    // Action Bar de Contexto
    private ActionMode actionMode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        //configura a RecyclerView
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView); //mapeia o RecyclerView do layout.
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //associa um gerenciador de layout Linear ao recyclerView.
        recyclerView.setItemAnimator(new DefaultItemAnimator()); //associa um tipo de animação ao recyclerView.

        //configura o SwipeRefreshLayout
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        taskProdutos(false);
    }

    // Task para buscar os carros
    private class GetProdutosTask implements TaskListener<List<Produto>> {
        private String nome;

        public GetProdutosTask(String nome) {
            this.nome = nome;
        }

        @Override
        public List<Produto> execute() throws Exception {
            // Busca os carros em background
            //if (nome != null) {
            // É uma busca por nome
            //return ProdutoService.getProdutosByNome(getContext(), nome);
            //} else {
            // É para listar por tipo
            //return Retrofit.getCarroREST().getCarros(tipo);
            try {
                try {
                    Thread.sleep(1500); //Faz aparecer por 1,5 segundos a ProgressBar para fins de teste
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ProdutoService.getProdutos();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Exceção ao obter a lista de produtos, método .execute");
                return null;
            }
            //}
        }

        @Override
        public void updateView(List<Produto> produtos) {
            if (produtos != null) {
                FeedFragment.this.produtos = produtos;

                // O correto seria validar se excluiu e recarregar a lista.
//                taskCarros(true);

                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new FeedAdapter(getContext(), produtos, onClickProduto()));
            }
        }

        @Override
        public void onError(Exception e) {
            if (e instanceof SocketTimeoutException) {
                alert(getString(R.string.msg_erro_io_timeout));
            } else {
                alert(getString(R.string.msg_error_io));
            }
        }

        @Override
        public void onCancelled(String s) {

        }
    }

/*        @Override
        protected List<Produto> doInBackground(Void... params) {
            //busca os carros em background, em uma thread exclusiva para esta tarefa.
            try {
                try {
                    Thread.sleep(1500); //Faz aparecer por 1,5 segundos a ProgressBar para fins de teste
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return ProdutoService.getProdutos("http://192.168.0.13:8084/rest/produtos");
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Exceção ao obter a lista de produtos, método .doInBackground()");
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Produto> produtos) {
            super.onPostExecute(produtos);
            if (produtos != null) {
                Log.d(TAG, "Quantidade de produtos no onPostExecute(): " + produtos.size());
                //atualiza a view na UIThread
                recyclerView.setAdapter(new FeedAdapter(getContext(), produtos, onClickProduto()));
                swipeLayout.setRefreshing(false); //para a animação da swipeRefrech
                progressBar.setVisibility(View.INVISIBLE); //faz com que a ProgressBar desapareça para o usuário
            }else{
                progressBar.setVisibility(View.INVISIBLE); //faz com que a ProgressBar desapareça para o usuário
                alertDialog.show(); //faz aparecer o AlertDialog para o usuário
            }
        }
    }*/

    protected FeedAdapter.ProdutoOnClickListener onClickProduto() {
        return new FeedAdapter.ProdutoOnClickListener() {
            @Override
            public void onClickProduto(FeedAdapter.ProdutosViewHolder holder, int idx) {
                Produto c = produtos.get(idx);

                if (actionMode == null) {
                    ImageView img = holder.img;

                    Intent intent = new Intent(getActivity(), ProdutoActivity.class);
                    intent.putExtra("produto", Parcels.wrap(c));
                    String key = getString(R.string.transition_key);

                    // Compat
                    ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img, key);
                    ActivityCompat.startActivity(getActivity(), intent, opts.toBundle());
                } else {
                    // Seleciona o carro e atualiza a lista
                    c.selected = !c.selected;
                    updateActionModeTitle();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onLongClickProduto(FeedAdapter.ProdutosViewHolder holder, int idx) {
                if (actionMode != null) {
                    return;
                }

                //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                actionMode = getAppCompatActivity().startSupportActionMode(getActionModeCallback());

                Produto c = produtos.get(idx);
                c.selected = true;
                recyclerView.getAdapter().notifyDataSetChanged();

                updateActionModeTitle();
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_produtos, menu);

        // SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(onSearch());
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buscaProdutos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            toast("Faça a busca");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /*
        Este método trata o evento onRefresh() do SwipeRefreshLayout.
        Ele acontece quando o usuário faz um swipe com o dedo para baixo na View.
     */

    @Subscribe
    public void onBusAtualizarListaProdutos(BusEvent.NovoProdutoEvent ev) {
        Log.d(TAG, "add: " + ev);
        // Recebeu o evento, atualiza a lista.
        taskProdutos(false);
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taskProdutos(true);
            }
        };
    }

    private void taskProdutos(boolean pullToRefresh) {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            startTask("produtos", new GetProdutosTask(null), pullToRefresh ? R.id.swipeToRefresh : R.id.IncludeProgress);
        } else {
            alert(R.string.msg_error_conexao_indisponivel);
        }
    }

    private void buscaProdutos(String nome) {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            startTask("produtos", new GetProdutosTask(nome), R.id.IncludeProgress);
        } else {
            alert(R.string.msg_error_conexao_indisponivel);
        }
    }

    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_frag_produtos_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                List<Produto> selectedProdutos = getSelectedProdutos();
                if (item.getItemId() == R.id.action_remove) {
                    deletarProdutosSelecionados();
                } else if (item.getItemId() == R.id.action_share) {
                    toast("Compartilhar: " + selectedProdutos);
                }
                // Encerra o action mode
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Limpa o ActionMode e carros selecionados
                actionMode = null;
                for (Produto c : produtos) {
                    c.selected = false;
                }
                // Atualiza a lista
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

    private void deletarProdutosSelecionados() {
        final List<Produto> selectedProdutos = getSelectedProdutos();

        if (selectedProdutos.size() > 0) {
            startTask("deletar", new BaseTask() {
                @Override
                public Object execute() throws Exception {
                    boolean ok = ProdutoService.delete(getContext(), selectedProdutos);
                    if (ok) {
                        // Se excluiu do banco, remove da lista da tela.
                        for (Produto c : selectedProdutos) {
                            produtos.remove(c);
                        }
                    }
                    return null;
                }

                @Override
                public void updateView(Object count) {
                    super.updateView(count);
                    // Mostra mensagem de sucesso
                    snack(recyclerView, selectedProdutos.size() + " produto excluído com sucesso");
                    // Atualiza a lista de carros
                    //taskCarros(true);
                    // Atualiza a lista
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }
    }

    private List<Produto> getSelectedProdutos() {
        List<Produto> list = new ArrayList<Produto>();
        for (Produto c : produtos) {
            if (c.selected) {
                list.add(c);
            }
        }
        return list;
    }

    private void updateActionModeTitle() {
        if (actionMode != null) {
            actionMode.setTitle("Selecione os produtos.");
            actionMode.setSubtitle(null);
            List<Produto> selectedProdutos = getSelectedProdutos();
            if (selectedProdutos.size() == 0) {
                actionMode.finish();
            } else if (selectedProdutos.size() == 1) {
                actionMode.setSubtitle("1 produto selecionado");
            } else if (selectedProdutos.size() > 1) {
                actionMode.setSubtitle(selectedProdutos.size() + " produtos selecionados");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos.
        //TradRApplication.getInstance().getBus().unregister(this);
    }
}
