package com.thanhtung.mockproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.thanhtung.mockproject.databinding.ItemCategoryBinding;
import com.thanhtung.mockproject.model.Category;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {
    private List<Category> data;
    private LayoutInflater inflater;
    private ItemListener listener;

    public CategoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Category> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setListener(ItemListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(inflater,parent,false);
        return new CategoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, final int position) {
        holder.bindData(data.get(position));
        if (listener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClicked(data.get(position));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        private ItemCategoryBinding binding;
        public CategoryHolder(@NonNull ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
        public void bindData(Category item){
            binding.setItem(item);
        }
    }
    public interface ItemListener{
        void OnItemClicked(Category category);
    }
}
