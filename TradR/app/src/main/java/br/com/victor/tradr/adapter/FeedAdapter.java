package br.com.victor.tradr.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Callback;

import java.util.List;

import br.com.victor.tradr.R;
import br.com.victor.tradr.domain.Produto;
import br.com.victor.tradr.util.ImageUtils;


// Herda de RecyclerView.Adapter e declara o tipo genérico <CarroAdapter.CarrosViewHolder>
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ProdutosViewHolder> {
    protected static final String TAG = "Webservice";
    private final List<Produto> produtos;
    private final Context context;

    private final ProdutoOnClickListener onClickListener;

    public interface ProdutoOnClickListener {
        void onClickProduto(ProdutosViewHolder holder, int idx);

        void onLongClickProduto(ProdutosViewHolder holder, int idx);
    }

    public FeedAdapter(Context context, List<Produto> produtos, ProdutoOnClickListener onClickListener) {
        this.context = context;
        this.produtos = produtos;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getItemCount() {
        return this.produtos != null ? this.produtos.size() : 0;
    }

    @Override
    public ProdutosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_feed, viewGroup, false);
        // Cria a classe do ViewHolder
        ProdutosViewHolder holder = new ProdutosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ProdutosViewHolder holder, final int position) {
        // Atualiza a view
        Produto c = produtos.get(position);

        // Atualizada os valores nas views
        holder.tNome.setText(c.nome);
        holder.tDesc.setText(c.descricao);

        //Exibe a ProgressBar
        holder.progress.setVisibility(View.VISIBLE);

        Picasso.with(context).load(c.urlFoto).fit().into(holder.img, new Callback() {
                    @Override
                    //Para a ProgressBar
                    public void onSuccess() {
                        holder.progress.setVisibility(View.GONE);
                    }

                    @Override
                    //Para a ProgressBar
                    public void onError() {
                        holder.progress.setVisibility(View.GONE);
                    }
                });

        // Foto do carro
        ImageUtils.setImage(context, c.urlFoto, holder.img);

        // Click
        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chama o listener para informar que clicou no Carro
                    onClickListener.onClickProduto(holder, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Chama o listener para informar que clicou no Carro
                    onClickListener.onLongClickProduto(holder, position);
                    return true;
                }
            });
        }

        // Pinta o fundo de azul se a linha estiver selecionada
        int corFundo = ContextCompat.getColor(context, c.selected ? R.color.primary : R.color.white);
        //int corFundo = context.getResources().getColor(c.selected ? R.color.primary : R.color.white);
        holder.cardView.setCardBackgroundColor(corFundo);
        // A cor do texto é branca ou azul, depende da cor do fundo.
        int corFonte = ContextCompat.getColor(context, c.selected ? R.color.white : R.color.primary);
        //int corFonte = context.getResources().getColor(c.selected ? R.color.white : R.color.primary);
        holder.tNome.setTextColor(corFonte);
    }

    // Subclasse de RecyclerView.ViewHolder. Contém todas as views.
    public static class ProdutosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        public TextView tDesc;
        public ImageView img;
        private ProgressBar progress;
        private View view;
        public CardView cardView;

        public ProdutosViewHolder(View view) {
            super(view);
            this.view = view;
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.tNomeAdapterFeed);
            tDesc = (TextView) view.findViewById(R.id.tDescAdapterFeed);
            img = (ImageView) view.findViewById(R.id.imgAdapterFeed);
            progress = (ProgressBar) view.findViewById(R.id.progressAdapterFeed);
            cardView = (CardView) view.findViewById(R.id.card_viewAdapterFeed);
        }
    }
}