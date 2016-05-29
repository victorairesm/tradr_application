package br.com.victor.tradr.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.parceler.Parcels;

import br.com.victor.tradr.R;
import br.com.victor.tradr.activity.ProdutoActivity;
import br.com.victor.tradr.databinding.FragmentProdutoBinding;
import br.com.victor.tradr.domain.Produto;
import br.com.victor.tradr.util.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProdutoFragment extends BaseFragment implements ProdutoActivity.ClickHeaderListener {

    private static final int REQUEST_CODE_SALVAR = 1;
    protected ImageView img;
    protected Produto produto;
    protected FragmentProdutoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Lê os argumentos
        produto = Parcels.unwrap(getArguments().getParcelable("produto"));

        setHasOptionsMenu(true);

        ProdutoActivity activity = (ProdutoActivity) getActivity();
        activity.setClickHeaderListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_produto, container, false);

        View view = binding.getRoot();

        initViews(view);

        if (produto != null) {
            setProduto(produto);
        }

        // Mapa
        //SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        // Inicia o Google Maps dentro do fragment
        //mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*if(produto != null) {
            startTask("loadFavoritos",taskLoadFavoritos(),R.id.progress);
        }*/
    }

    protected void initViews(View view) {
        img = (ImageView) view.findViewById(R.id.imgAdapterFeed);
    }

/*    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
    }*/

    private void setProduto(Produto c) {
        if (c != null) {
            if (img != null) {
                ImageUtils.setImage(getContext(), c.urlFoto, img);
            }

            // Data Binding
            binding.setProduto(c);
        }

        // Imagem do Header na Toolbar
        ProdutoActivity activity = (ProdutoActivity) getActivity();
        activity.setAppBarInfo(c);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_produto, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_edit) {
            Intent intent = new Intent(getActivity(), ProdutoActivity.class);
            intent.putExtra("produto", Parcels.wrap(produto));
            intent.putExtra("editMode", true);
            ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity());
            ActivityCompat.startActivityForResult(getActivity(), intent, REQUEST_CODE_SALVAR, opts.toBundle());
            // Por definição, vamos fechar esta tela para ficar somente a de editar.
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onHeaderClicked() {
        toast("Para alterar a foto precisa editar o produto.");
    }

    @Override
    public void onFabButtonClicked(Produto produto) {

    }

/*    @Override
    public void onFabButtonClicked(final Produto produto) {
        // Favoritar o produto
        if(produto != null) {
            startTask("favorito", taskFavoritar(produto), R.id.progress);
        }
    }*/

/*    @NonNull
    protected BaseTask<Boolean> taskFavoritar(final Produto produto) {
        return new BaseTask<Boolean>(){
            @Override
            public Boolean execute() throws Exception {
                ProdutoService.toogleFavorite(getContext(),produto);
                return true;
            }

            @Override
            public void updateView(Boolean response) {
                super.updateView(response);
                // Atualiza o FAB button
                ProdutoActivity activity = (ProdutoActivity) getActivity();
                activity.toogleFavorite(produto.favorited);

                // Envia o evento para o bus
                TradRApplication.getInstance().getBus().post(new BusEvent.FavoritosEvent());
            }
        };
    }*/

    /**
     * // Atualiza a cor do FAB button, conforme se o carro está favoritado ou não
     */
/*    @NonNull
    protected BaseTask<Boolean> taskLoadFavoritos() {
        return new BaseTask<Boolean>(){
            @Override
            public Boolean execute() throws Exception {
                // Verifica se o carro está favoritado.
                Boolean favorito = ProdutoService.isFavorito(getContext(),carro);
                return favorito;
            }

            @Override
            public void updateView(Boolean favorito) {
                super.updateView(favorito);

                // Atualiza a cor do FAB button.
                CarroActivity activity = (CarroActivity) getActivity();
                activity.setFavoriteColor(favorito);
            }
        };
    }*/
}
