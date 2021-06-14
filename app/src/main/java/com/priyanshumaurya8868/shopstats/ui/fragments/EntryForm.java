package com.priyanshumaurya8868.shopstats.ui.fragments;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.priyanshumaurya8868.shopstats.R;
import com.priyanshumaurya8868.shopstats.databinding.EntryFormBinding;
import com.priyanshumaurya8868.shopstats.room.entities.Item;
import com.priyanshumaurya8868.shopstats.room.entities.Period;
import com.priyanshumaurya8868.shopstats.ui.MainActivity;
import com.priyanshumaurya8868.shopstats.ui.MainViewModel;

import java.util.Date;
import java.util.Locale;

public class EntryForm extends Fragment {
    private Item recievedArg = null;
    EntryFormBinding binding;
    private boolean isPurchased;
    private MainViewModel viewModel;

    String currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
    String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isPurchased = getArguments().getBoolean(getResources().getString(R.string.entryFragArgs));
            recievedArg = (Item) getArguments().getSerializable(getResources().getString(R.string.item_args));
        }
        viewModel =new ViewModelProvider(
                requireActivity(),
                new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())
        ).get(MainViewModel.class);

        if(recievedArg == null)
            Log.d("omega","receive Args is  null");
        else Log.d("omega", recievedArg.name +" reached...!");
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = EntryFormBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpTextChangeListener();
        if(recievedArg != null)
            initalizeRecordes();
        else setdefaultValues();

     binding.submitBtn.setOnClickListener(v->{
         if(recievedArg == null)
             viewModel.insertPeriods(getPeriod());
         viewModel.insertItems(getItem());
     });

    }

    private void initalizeRecordes() {
        binding.date.setText(recievedArg.date);
        binding.time.setText(recievedArg.time);
        binding.etEnterPrice.setText(""+recievedArg.pricePerUnit);
        binding.etEnterUnitOfPrice.setText(""+recievedArg.unitOfPrice);
        binding.etEnterTypeOfUnit.setText(recievedArg.unitType);
        binding.etEnterProductName.setText(recievedArg.name);
        binding.totalBill.setText(recievedArg.totalBill+"");
        binding.etTotalQuantity.setText(recievedArg.totalQuantity+"");
    }

    private void setdefaultValues() {
        binding.date.setText(currentDate);
        binding.time.setText(currentTime);
        binding.etEnterUnitOfPrice.setText("1");
        binding.etTotalQuantity.setText("1");
    }

    private Period getPeriod(){
     Period period=  new Period();
               period.date = binding.date.getText().toString();
               return period;
    }

    private Item getItem() {
       Item item = null;
        if (validateInputs()) {
            item = new Item();
            item.date = binding.date.getText().toString();
            item.time = binding.time.getText().toString();
            item.name = binding.etEnterProductName.getText().toString();
            item.pricePerUnit = Float.parseFloat(binding.etEnterPrice.getText().toString());
            item.unitOfPrice = Float.parseFloat(binding.etEnterUnitOfPrice.getText().toString());
            item.totalQuantity = Float.parseFloat(binding.etTotalQuantity.getText().toString());
            item.unitType = binding.etEnterTypeOfUnit.getText().toString();
            item.totalBill = Float.parseFloat(binding.totalBill.getText().toString());
            item.isPurchased = isPurchased;
            if(recievedArg != null) item.id = recievedArg.id;
            ((MainActivity) this.requireActivity()).navController.popBackStack();
        }
        return item;
    }

    private Boolean validateInputs() {

        if (binding.etEnterProductName.getText().toString().trim().isEmpty()) {
            binding.etEnterProductName.setError("Field Required!");
            binding.etEnterProductName.requestFocus();
            return false;
        }
        if (binding.etEnterPrice.getText().toString().trim().isEmpty()) {
            binding.etEnterPrice.setError("Field Required!");
            binding.etEnterPrice.requestFocus();
            return false;
        }
        if (binding.etEnterUnitOfPrice.getText().toString().trim().isEmpty()) {
            binding.etEnterUnitOfPrice.setError("Field Required!");
            binding.etEnterUnitOfPrice.requestFocus();
            return false;
        }
        if (binding.etEnterTypeOfUnit.getText().toString().trim().isEmpty()) {
            binding.etEnterTypeOfUnit.setError("Field Required!");
            binding.etEnterTypeOfUnit.requestFocus();
            return false;
        }

        if (binding.etTotalQuantity.getText().toString().trim().isEmpty()) {
            binding.etTotalQuantity.setError("Field Required!");
            binding.etTotalQuantity.requestFocus();
            return false;
        }
        return true;
    }
    private void setUpTextChangeListener() {
        binding.etEnterPrice.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               updateTotalBill();
            }

            @Override
            public void afterTextChanged(Editable s) {
                    updateTotalBill();
            }
        });
        binding.etEnterUnitOfPrice.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateTotalBill();
            }
        });
        binding.etTotalQuantity.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               updateTotalBill();
            }
        });
    }

    void updateTotalBill() {
        float totalBill=0;
        if(!binding.etEnterPrice.getText().toString().isEmpty() &&
                !binding.etEnterUnitOfPrice.getText().toString().isEmpty() &&
                        !binding.etTotalQuantity.getText().toString().isEmpty()
        ){
            float pricePerUnit = Float.parseFloat(binding.etEnterPrice.getText().toString());
            float unitOfPrice = Float.parseFloat(binding.etEnterUnitOfPrice.getText().toString());
            float totalQuantity = Float.parseFloat(binding.etTotalQuantity.getText().toString());
            totalBill  = pricePerUnit * unitOfPrice * totalQuantity;
        }
        String temp = ""+totalBill;
        binding.totalBill.setText(temp);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}