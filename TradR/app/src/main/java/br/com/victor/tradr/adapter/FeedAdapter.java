package br.com.victor.tradr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.victor.tradr.R;
import br.com.victor.tradr.model.Produto;


/**
 * Esta classe realiza a adaptação dos dados entre a RecyclerView -> List.
 * Neste projeto a List está sendo alimentada com dados oriundos da web, via JSON.
 * @author Vagner Pinto da Silva
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ProdutosViewHolder> {
    protected static final String TAG = "Webservice";
    private final List<Produto> produtos;
    private final Context context;

    private ProdutoOnClickListener produtoOnClickListener;

    public FeedAdapter(Context context, List<Produto> produto, ProdutoOnClickListener produtoOnClickListener) {
        this.context = context;
        this.produtos = produto;
        this.produtoOnClickListener = produtoOnClickListener;
    }

    @Override
    public int getItemCount() {
        return this.produtos != null ? this.produtos.size() : 0;
    }

    @Override
    public ProdutosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_feed, viewGroup, false);

        // Cria o ViewHolder
        ProdutosViewHolder holder = new ProdutosViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ProdutosViewHolder holder, final int position) {
        // Atualiza a view
        Produto c = produtos.get(position);

        holder.tNome.setText(c.getNome());
        holder.progress.setVisibility(View.VISIBLE);

        Picasso.with(context).load(c.getUrlFoto()).fit().into(holder.img, new Callback() {
            @Override
            public void onSuccess() {
                holder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                holder.progress.setVisibility(View.GONE);
            }
        });

        // Click
        if (produtoOnClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    produtoOnClickListener.onClickProduto(holder.itemView, position); // A variável position é final
                }
            });
        }
    }

    public interface ProdutoOnClickListener {
        public void onClickProduto(View view, int idx);
    }

    // ViewHolder com as views
    public static class ProdutosViewHolder extends RecyclerView.ViewHolder {
        public TextView tNome;
        ImageView img;
        ProgressBar progress;

        public ProdutosViewHolder(View view) {
            super(view);
            // Cria as views para salvar no ViewHolder
            tNome = (TextView) view.findViewById(R.id.text);
            img = (ImageView) view.findViewById(R.id.img);
            progress = (ProgressBar) view.findViewById(R.id.progressImg);
        }
    }
}
