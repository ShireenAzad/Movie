package com.example.movie.request;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.movie.models.MovieModel;

import java.util.List;

public class MovieApiClientt {
    private MutableLiveData<List<MovieModel>> viewModelMovies;

    private static MovieApiClientt instance;

    public static MovieApiClientt getInstance(){
        if(instance==null){
            instance=new MovieApiClientt();
        }
        return instance;
    }

private MovieApiClientt(){
        viewModelMovies=new MutableLiveData<>();
}
public LiveData<List<MovieModel>> getMovies(){
        return viewModelMovies;
}
}
