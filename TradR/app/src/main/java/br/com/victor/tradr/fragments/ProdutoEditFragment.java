package br.com.victor.tradr.fragments;


import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;


import org.parceler.Parcels;

import java.io.File;

import br.com.victor.tradr.TradRApplication;
import br.com.victor.tradr.R;
import br.com.victor.tradr.activity.ProdutoActivity;
import br.com.victor.tradr.databinding.FragmentProdutoEditBinding;
import br.com.victor.tradr.domain.Produto;
import br.com.victor.tradr.domain.ProdutoService;
import br.com.victor.tradr.domain.event.BusEvent;
import br.com.victor.tradr.rest.Response;
import br.com.victor.tradr.rest.ResponseWithURL;
import br.com.victor.tradr.util.CameraUtil;

/**
 * Fragment com form para editar o produto.
 * <p>
 * Herda do ProdutoFragment para aproveitar a lógica de visualização.
 */
public class ProdutoEditFragment extends BaseFragment implements ProdutoActivity.ClickHeaderListener {
    // Camera Foto
    private CameraUtil camera = new CameraUtil();
    private FragmentProdutoEditBinding binding;

    protected ImageView img;

    protected RadioGroup tCategoria;

    private Produto produto;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_produto_edit, container, false);

        View view = binding.getRoot();

        initViews(view);

        initViews(view);

        if (produto != null) {
            setProduto(produto);
        }

        if (savedInstanceState != null) {
            // Se girou a tela recupera o estado
            camera.onCreate(savedInstanceState);
        }

        return view;
    }

    protected void initViews(View view) {
        img = (ImageView) view.findViewById(R.id.imgAdapterFeed);
        tCategoria = (RadioGroup) view.findViewById(R.id.radioTipo);
    }

    private void setProduto(Produto c) {
        if (c != null) {
            if (img != null) {
//                ImageUtils.setImage(getContext(), c.urlFoto, img);
//                img.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showVideo();
//                    }
//                });
            }
            // Data Binding
            binding.setProduto(c);
            setCategoria(c.categoria);
        }

        // Imagem do Header na Toolbar
        ProdutoActivity activity = (ProdutoActivity) getActivity();
        activity.setAppBarInfo(c);
    }

    // Seta o tipo no RadioGroup
    protected void setCategoria(Integer categoria) {
        if (categoria == 1) {
            tCategoria.check(R.id.catUsado);
        } else if (categoria == 2) {
            tCategoria.check(R.id.catNovo);
        } else if (categoria == 3) {
            tCategoria.check(R.id.catSemiNovo);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        camera.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_frag_edit_produto, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_salvar) {

/*            if (produto == null) {
                // Novo carro

                produto = new Produto();
                produto.categoria = Long.valueOf(1);
            }*/

            boolean formOk = validate(R.id.tNomeEdit, R.id.tDescEdit, R.id.tValor);
            if (formOk) {
                Integer categoria = 0;
                if(getCategoria().equals("usado")){
                    categoria = 1;
                } else if(getCategoria().equals("novo")) {
                    categoria = 2;
                } else if(getCategoria().equals("seminovo")) {
                    categoria = 3;
                }
                // Validação de campos preenchidos
                produto.nome = binding.tNomeEdit.getText().toString();
                produto.descricao = binding.tDescEdit.getText().toString();
                produto.valor = Double.parseDouble(binding.tValor.getText().toString());
                produto.categoria = categoria;

                Log.d(TAG, "Salvar produto na categoria: " + produto.categoria);

                startTask("salvar", taskSaveProduto());
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Retorna o tipo em string conforme marcado no RadioGroup
    protected String getCategoria() {
        if (tCategoria != null) {
            int id = tCategoria.getCheckedRadioButtonId();
            switch (id) {
                case R.id.catUsado:
                    return "usado";
                case R.id.catNovo:
                    return "novo";
                case R.id.catSemiNovo:
                    return "seminovo";
            }
        }
        return "usado";
    }

    private boolean validate(int... textViewIds) {
        for (int id : textViewIds) {
            TextView t = (TextView) getView().findViewById(id);
            String s = t.getText().toString();
            if (s == null || s.trim().length() == 0) {
                t.setError(getString(R.string.msg_error_campo_obrigatorio));
                return false;
            }
        }
        return true;
    }

    private BaseTask taskSaveProduto() {
        return new BaseTask<Response>() {
            @Override
            public Response execute() throws Exception {
                // Faz upload da foto
                File file = camera.getFile();
                if (file != null && file.exists()) {
                    //ResponseWithURL response = CarroREST.postFotoBase64(getContext(), file);
                    ResponseWithURL response = ProdutoService.postFotoBase64(getContext(), file);
                    if (response != null && response.isOk()) {
                        // Atualiza a foto do carro
                        produto.urlFoto = response.getUrl();
                    }
                }
                // Salva o carro
                Response response = ProdutoService.saveProduto(getContext(), produto);
                //Response response = Retrofit.getCarroREST().saveProduto(carro);
                return response;
            }

            @Override
            public void updateView(Response response) {
                super.updateView(response);
                if (response != null && "OK".equals(response.getStatus())) {
                    // Envia o evento para o bus
                    TradRApplication.getInstance().getBus().post(new BusEvent.NovoProdutoEvent());
                    // Fecha a tela
                    getActivity().finish();
                } else {
                    toast("Erro ao salvar o produto " + produto.nome);
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            // Resize da imagem
            Bitmap bitmap = camera.getBitmap(600, 600);

            if (bitmap != null) {
                // Salva arquivo neste tamanho
                camera.save(bitmap);

                // Atualiza imagem do Header
                ProdutoActivity activity = (ProdutoActivity) getActivity();
                activity.setImage(bitmap);
            }
        }
    }

    private void setImage(File file) {
        //ImageUtils.setImage(getContext(), file.getAbsolutePath().toString(), imgView);
        Log.d(TAG, "setImage: " + file);
        ((ProdutoActivity) getActivity()).setImage(file);
    }

    public void setImage(String url) {
        //ImageUtils.setImage(getContext(),url, imgView);
        ((ProdutoActivity) getActivity()).setImage(url);
    }

//    @Override
//    public void onHeaderClicked() {
//        // Se clicar na imagem de header, tira a foto
//        // Cria o o arquivo no sdcard
//        long ms = System.currentTimeMillis();
//        String fileName = String.format("foto_carro_%s_%s.jpg", carro != null ? carro.id : ms, ms);
//        // A classe Camera cria a intent e o arquivo no sdcard.
//        Intent intent = camera.open(fileName);
//        startActivityForResult(intent, 0);
//    }


    @Override
    public void onHeaderClicked() {
        // Se clicar na imagem de header, tira a foto
        // Cria o o arquivo no sdcard
        long ms = System.currentTimeMillis();
        String fileName = String.format("foto_carro_%s_%s.jpg", produto != null ? produto.id : ms, ms);
        // A classe Camera cria a intent e o arquivo no sdcard.
        Intent intent = camera.open(fileName);
        startActivityForResult(intent, 0);
    }

    @Override
    public void onFabButtonClicked(Produto produto) {

    }
}
