package com.thanhtung.mockproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thanhtung.mockproject.databinding.ItemNewsBinding;
import com.thanhtung.mockproject.model.News;
import com.thanhtung.mockproject.time.TimeAgo;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private List<News> data;
    private LayoutInflater inflater;
    private ItemListener listener;


    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

    public NewsAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    public void setData(List<News> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsBinding binding = ItemNewsBinding.inflate(inflater, parent, false);
        return new NewsHolder(binding);
    }
    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, final int position) {
        holder.bindData(data.get(position));
        if (listener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(data.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private ItemNewsBinding binding;

        public NewsHolder(@NonNull ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindData(News item) {
            binding.setItem(item);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date date = dateFormat.parse(item.getPublish_date());
                binding.tvPubdate.setText(TimeAgo.getTimeAgo(date.getTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (item.getThumb_img()!=null){
                Glide.with(binding.imvNews)
                        .load(item.getThumb_img())
                        .into(binding.imvNews);
            }
            if (item.getAuthor()==null|| item.getAuthor().equals("")){
                binding.tvAuthor.setVisibility(View.INVISIBLE);
            } else {
                binding.tvAuthor.setVisibility(View.VISIBLE);
            }

        }
    }

    public interface ItemListener {
        void OnItemClick(News news);
    }
}
