package com.priyanshumaurya8868.shopstats.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.priyanshumaurya8868.shopstats.databinding.NestedRvItemBinding;
import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.ui.adapter.listeners.OpenArticleListener;

public class NestedAdapter  extends ListAdapter<Item, NestedAdapter.NestedVH> {
    OpenArticleListener listener;

    protected NestedAdapter(OpenArticleListener listener) {
        super(new DiffUtil.ItemCallback<Item>(){

            @Override
            public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
                return oldItem == newItem;
            }

            @Override
            public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
                return oldItem.id == newItem.id;
            }
        });
        this.listener = listener;
    }

    @NonNull
    @Override
    public NestedVH onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = parent.getContext().getSystemService(LayoutInflater.class);
        NestedRvItemBinding binding = NestedRvItemBinding.inflate(inflater,parent,false);
        return new NestedVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NestedAdapter.NestedVH holder, int position) {
        Item item = getItem(position);
         holder.binding.purchaseDate.setText(item.date);
         holder.binding.purchaseTime.setText(item.time);
         holder.binding.purchaseProductName.setText(item.name);
         String str = item.totalQuantity +" * +"+item.pricePerUnit+ item.unitType;
         holder.binding.purchaseQuantityAndUnits.setText(str);

         if(item.isPurchased){
             holder.binding.purchaseTotalAmount.setText(item.totalBill+"");
             holder.binding.purchaseTotalAmount.setVisibility(View.VISIBLE);
             holder.binding.sellTotalAmount.setVisibility(View.INVISIBLE);
         }
         else{
             holder.binding.sellTotalAmount.setText(item.totalBill+"");
             holder.binding.sellTotalAmount.setVisibility(View.VISIBLE);
             holder.binding.purchaseTotalAmount.setVisibility(View.INVISIBLE);
         }
         holder.binding.container.setOnClickListener(v->{
             listener.openArticle(item);
         });
    }


    static class NestedVH extends RecyclerView.ViewHolder{
        NestedRvItemBinding binding;
       public NestedVH(NestedRvItemBinding binding) {
           super(binding.getRoot());
           this.binding = binding;
       }
   }
}
