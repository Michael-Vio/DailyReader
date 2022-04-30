package com.example.dailyreader.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyreader.R;
import com.example.dailyreader.WeatherApi;
import com.example.dailyreader.adapter.AllBookFragmentAdapter;
import com.example.dailyreader.databinding.AllBookFragmentBinding;
import com.example.dailyreader.entity.Book;
import com.example.dailyreader.viewmodel.BookViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AllBookFragment extends Fragment {
    private AllBookFragmentBinding binding;
    private AllBookFragmentAdapter adapter;
    private BookViewModel bookViewModel;


    public AllBookFragment () {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = AllBookFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.allBookRecyclerView.setLayoutManager(layoutManager);
        bookViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()).create(BookViewModel.class);
        adapter = new AllBookFragmentAdapter(bookViewModel, new ArrayList<>());
        binding.allBookRecyclerView.setAdapter(adapter);


        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {

                adapter.setBooks(books);
            }
        });

        binding.allBookRecyclerView.addItemDecoration(new DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL));

        binding.allBookRecyclerView.setHasFixedSize(true);


        //Set Marquee
        TextView text_view = (TextView) view.findViewById(R.id.weatherHello);
        text_view.setSelected(true);

        //Use weather API
        //https://api.openweathermap.org/data/2.5/weather?lat=37.8136&lon=144.9631&appid=2e1018bb418dd391d11b25c5a7fad3a0
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WeatherApi weatherApi = retrofit.create(WeatherApi.class);

        Call<WeatherRoot> call = weatherApi.getWeather();

        //Call
        call.enqueue(new Callback<WeatherRoot>() {
            @Override
            public void onResponse(Call<WeatherRoot> call, Response<WeatherRoot> response){
                WeatherRoot weatherRoot = response.body();

                //temp change
                double temp = weatherRoot.getMain().getTemp() - 273.15;
                int pressure = weatherRoot.getMain().getPressure();
                int humidity = weatherRoot.getMain().getHumidity();

                //show temp
                binding.weatherHello.setText("Melbourne temperature(C): "+ String.valueOf((int)temp)
                                                + "   Atmospheric pressure in Melbourne: " + String.valueOf(pressure) + "hPa"
                                                +"    Humidity in Melbourne: " + String.valueOf(humidity) + "%");


                /*binding.weatherTemp.setText("Melbourne temperature(C): "+ String.valueOf((int)temp));
                binding.pressure.setText("Atmospheric pressure in Melbourne(hPa): " + String.valueOf(pressure));
                binding.humidity.setText("Humidity in Melbourne(%):" + String.valueOf(humidity));*/
            }

            @Override
            public void onFailure(Call<WeatherRoot> call, Throwable throwable){
                System.out.println(throwable.getMessage());
            }


        });

        return view;
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
