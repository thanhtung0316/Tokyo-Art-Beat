package com.thanhtung.mockproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thanhtung.mockproject.databinding.ItemEventBinding;
import com.thanhtung.mockproject.model.Event;
import com.thanhtung.mockproject.model.EventInCateGory;
import com.thanhtung.mockproject.model.News;
import com.thanhtung.mockproject.time.TimeAgo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EventInCategoryAdapter extends RecyclerView.Adapter<EventInCategoryAdapter.EventInCateGoryHolder> {
    private List<EventInCateGory> data;
    private LayoutInflater inflater;
    private ItemEventListener listener;

    public EventInCategoryAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public void setListener(ItemEventListener listener) {
        this.listener = listener;
    }

    public void setData(List<EventInCateGory> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventInCateGoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEventBinding binding = ItemEventBinding.inflate(inflater,parent,false);
        return new EventInCateGoryHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventInCateGoryHolder holder, final int position) {
        holder.bindData(data.get(position));
        if (listener!=null){
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
        return data==null?0:data.size();
    }

    public class EventInCateGoryHolder extends RecyclerView.ViewHolder {
        private ItemEventBinding binding;
        public EventInCateGoryHolder(@NonNull ItemEventBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bindData(Event item){
            binding.setItem(item);
            binding.imvStar.setVisibility(View.INVISIBLE);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date startDate = dateFormat.parse(item.getScheduleStartDate());
                if (startDate.getTime() > System.currentTimeMillis()){
                    binding.tvSchedule.setText(item.getScheduleStartDate());
                }else {
                    binding.tvSchedule.setText(item.getScheduleEndDate());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (item.getGoingCount()==0||item.getGoingCount()==null){
                binding.tvGoingCount.setVisibility(View.INVISIBLE);
            }
            if (item.getPhoto()!=null){
                Glide.with(binding.imvEvent)
                        .load(item.getPhoto())
                        .into(binding.imvEvent);
            }
            if (item.getGoingCount()==0){
                binding.tvGoingCountExample.setVisibility(View.INVISIBLE);
                binding.tvGoingCount.setVisibility(View.INVISIBLE);

            }

        }
    }
    public interface ItemEventListener{
        void OnItemClick(EventInCateGory event);

    }
}
