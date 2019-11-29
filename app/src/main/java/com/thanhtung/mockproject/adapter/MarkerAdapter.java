package com.thanhtung.mockproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thanhtung.mockproject.R;
import com.thanhtung.mockproject.databinding.ItemEventBinding;
import com.thanhtung.mockproject.databinding.ItemMarkerBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.MarkerEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.MarkerEventHolder> {
    private List<MarkerEvent> data;
    private LayoutInflater inflater;
    private ItemEventListener listener;

    public MarkerAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setListener(ItemEventListener listener) {
        this.listener = listener;
    }

    public void setData(List<MarkerEvent> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MarkerEventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMarkerBinding binding = ItemMarkerBinding.inflate(inflater,parent,false);
        return new MarkerEventHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarkerEventHolder holder, final int position) {
        holder.bindData(data.get(position));
            if (listener!=null){
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.OnItemClick(position);
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    public class MarkerEventHolder extends RecyclerView.ViewHolder {
        private ItemMarkerBinding binding;
        public MarkerEventHolder(@NonNull ItemMarkerBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindData(MarkerEvent item){
            binding.setItem(item);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = dateFormat.parse(item.getDateStartEvent());
                if (startDate.getTime() > System.currentTimeMillis()){
                    binding.tvSchedule.setText(item.getDateStartEvent());
                }else {
                    binding.tvSchedule.setText(item.getDateEndEvent());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (item.getGoingCount()==0){
                binding.tvGoingCount.setVisibility(View.INVISIBLE);
            }
            if (item.getPhoto()!=null){
                Glide.with(binding.imvEvent)
                        .load(item.getPhoto())
                        .into(binding.imvEvent);
            } else {
                binding.imvEvent.setImageResource(R.drawable.noimage);
            }
            if (item.getGoingCount()==0){
                binding.tvGoingCountExample.setVisibility(View.INVISIBLE);
                binding.tvGoingCount.setVisibility(View.INVISIBLE);

            }

        }
    }
    public interface ItemEventListener{
        void OnItemClick(int position);

    }
}
