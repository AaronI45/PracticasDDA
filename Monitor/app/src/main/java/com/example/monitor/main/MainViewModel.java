package com.example.monitor.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.monitor.Earthquake;
import com.example.monitor.api.EqApiClient;
import com.example.monitor.database.EqDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewModel extends AndroidViewModel {
    private final MutableLiveData<List<Earthquake>> eqList = new MutableLiveData<>();
    public MainViewModel(@NonNull Application application){
        super(application);
        EqDatabase database =EqDatabase.getDatabase(application);
        repository = new MainRepository(database);
    }
    public LiveData<List<Earthquake>> getEqList(){
        return repository.getEqList();
    }
    private final MainRepository repository;

    public void downloadEarthquakes(){
        repository.downloadAndSaveEarthquakes();
    }
}
