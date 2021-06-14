package com.priyanshumaurya8868.shopstats.ui.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.priyanshumaurya8868.shopstats.databinding.RvItemBinding;
import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.room.relationship.PeriodWithItems;
import com.priyanshumaurya8868.shopstats.ui.adapter.listeners.OpenArticleListener;

public class MainRvAdapter extends ListAdapter<PeriodWithItems, MainRvAdapter.MainVh> implements OpenArticleListener {
    OpenArticleListener listener;

    public MainRvAdapter(OpenArticleListener listener) {
        super(new DiffUtil.ItemCallback<PeriodWithItems>(){

            @Override
            public boolean areItemsTheSame(@NonNull PeriodWithItems oldItem, @NonNull PeriodWithItems newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull PeriodWithItems oldItem, @NonNull PeriodWithItems newItem) {
                return oldItem.period.date.equals(newItem.period.date);
            }
        });
        this.listener = listener;
    }



    @NonNull
    @Override
    public MainVh onCreateViewHolder(  ViewGroup parent, int viewType) {
        LayoutInflater inflater = parent.getContext().getSystemService(LayoutInflater.class);
       RvItemBinding binding = RvItemBinding.inflate(inflater,parent,false);
       return new MainVh(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRvAdapter.MainVh holder, int position) {
        PeriodWithItems periodWithItems = getItem(position);
        double dailyPurchase =0;
        double dailysell=0;
        holder.binding.tvDate.setText(periodWithItems.period.date);

      for(Item i : periodWithItems.items){
          if(i.isPurchased) dailyPurchase += i.totalBill;
          else dailysell += i.totalBill;
      }
      String c1 = dailyPurchase+"";
      holder.binding.dailyPurchase.setText(c1);
      String c2 = dailysell+"";
      holder.binding.dailySell.setText(c2);

      NestedAdapter nestedAdapter = new NestedAdapter(this);
      nestedAdapter.submitList(periodWithItems.items);
      holder.binding.itemRv.setAdapter(nestedAdapter);

    }

    @Override
    public void openArticle(Item item) {
        if(item != null)
        listener.openArticle(item);
        else
             Log.d("omega","main adapter -> item is null");
    }

    static class MainVh extends RecyclerView.ViewHolder{
     RvItemBinding binding;
        public MainVh(RvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}




