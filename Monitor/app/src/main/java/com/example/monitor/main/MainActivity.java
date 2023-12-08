package com.example.monitor.main;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.monitor.Earthquake;
import com.example.monitor.api.RequestStatus;
import com.example.monitor.databinding.ActivityMainBinding;
import com.example.monitor.details.DetailActivity;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MainViewModel viewModel = new ViewModelProvider(this,
                new MainViewModelFactory(getApplication())).get(MainViewModel.class);

        binding.eqRecycler.setLayoutManager(new LinearLayoutManager(this));

        EqAdapter adapter = new EqAdapter(this);
        /*adapter.setOnItemClickListener(earthquake ->
                Toast.makeText(MainActivity.this,
                        earthquake.getPlace(),
                        Toast.LENGTH_SHORT).show());*/
        adapter.setOnItemClickListener(earthquake -> openDetailActivity(earthquake));
        binding.eqRecycler.setAdapter(adapter);

        viewModel.getStatusMutableLiveData().observe(this,status->{
            if(status.getStatus() == RequestStatus.LOADING){
                binding.loadingWheel.setVisibility(View.VISIBLE);
            }else{
                binding.loadingWheel.setVisibility(View.GONE);
            }
            if(status.getStatus() == RequestStatus.ERROR){
                Toast.makeText(this,"Check Internet Connection", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.downloadEarthquakes();

        viewModel.getEqList().observe(this,eqList ->{
            adapter.submitList(eqList);
        });


    }
    void openDetailActivity(Earthquake terremoto){
        Log.d("SDI", "openDetailActivity: "+terremoto.getPlace()
                +"\nMagnitude: "+terremoto.getMagnitude()
                +"\nTime: "+terremoto.getTime());
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.TERREMOTO_KEY, terremoto);
        startActivity(intent);
    }
}