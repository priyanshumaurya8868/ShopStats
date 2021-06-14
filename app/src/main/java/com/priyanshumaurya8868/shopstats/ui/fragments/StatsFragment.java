package com.priyanshumaurya8868.shopstats.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.priyanshumaurya8868.shopstats.R;
import com.priyanshumaurya8868.shopstats.databinding.StatsFragmentBinding;
import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.ui.MainActivity;
import com.priyanshumaurya8868.shopstats.ui.MainViewModel;
import com.priyanshumaurya8868.shopstats.ui.adapter.MainRvAdapter;
import com.priyanshumaurya8868.shopstats.ui.adapter.listeners.OpenArticleListener;

public class StatsFragment extends Fragment implements OpenArticleListener {

    StatsFragmentBinding binding;
    MainRvAdapter adapter;
    private MainViewModel viewModel;
      private double totalPurchase;
      private  double totalSell;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new MainRvAdapter(this);
        viewModel =new ViewModelProvider(
                requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = StatsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NavController navController = ((MainActivity) requireActivity()).navController;
        binding.purchaseBtn.setOnClickListener(v -> navController.navigate(R.id.action_statsFragment_to_purchaseFragment));
        binding.sellBtn.setOnClickListener(v -> navController.navigate(R.id.action_statsFragment_to_sellFragment));
       setUpHeader();
       serUpRv();
    }

    private void serUpRv() {
        viewModel.getAllPeriodAndItems.observe(getViewLifecycleOwner(),v-> adapter.submitList(v));
        binding.mainRv.setAdapter(adapter);
        if(adapter.getCurrentList().size()>0)
        binding.mainRv.smoothScrollToPosition(adapter.getCurrentList().size()-1);
//        binding.mainRv.scrollToPosition(adapter.getCurrentList().size()-1);
    }

    private void setUpHeader() {
        viewModel.getAllItems.observe(getViewLifecycleOwner(),v->{
            for(Item i : v){
                if (i.isPurchased) totalPurchase +=i.totalBill;
                else totalSell += i.totalBill;
            }
            binding.totalPurchase.setText(totalPurchase+"");
            binding.totalSell.setText(totalSell+"");
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


    @Override
    public void openArticle(Item item) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(getResources().getString(R.string.item_args),item);
        ((MainActivity)requireActivity()).navController.navigate(R.id.action_statsFragment_to_updateFragment,bundle);
    }
}

