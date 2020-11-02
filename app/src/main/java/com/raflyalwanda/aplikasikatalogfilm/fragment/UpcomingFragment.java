package com.raflyalwanda.aplikasikatalogfilm.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.raflyalwanda.aplikasikatalogfilm.BuildConfig;
import com.raflyalwanda.aplikasikatalogfilm.R;
import com.raflyalwanda.aplikasikatalogfilm.adapter.MovieAdapter;
import com.raflyalwanda.aplikasikatalogfilm.api.BaseApiService;
import com.raflyalwanda.aplikasikatalogfilm.api.UtilsApi;
import com.raflyalwanda.aplikasikatalogfilm.model.Movies;
import com.raflyalwanda.aplikasikatalogfilm.model.ResponseMovies;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UpcomingFragment extends Fragment {

    @BindView(R.id.rv_movies)
    RecyclerView rvMovies;

    private MovieAdapter adapter;
    List<Movies> listMovies = new ArrayList<>();

    ProgressDialog loading;

    BaseApiService apiService;

    private final String api_key = BuildConfig.MY_MOVIE_DB_API_KEY;
    private final String language = "en-US";
    private final String include_adult = "false";
    private final String page = "1";

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_upcoming, container, false);

        ButterKnife.bind(this, v);

        apiService = UtilsApi.getAPIService();

        adapter = new MovieAdapter(getContext(), listMovies);

        rvMovies.setHasFixedSize(true);
        rvMovies.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMovies.setAdapter(adapter);

        refresh();

        return v;
    }

    private void refresh(){
        loading = ProgressDialog.show(getContext(), null, "Please wait...", true, false);

        apiService.getUpcoming(api_key, language).enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    loading.dismiss();

                    listMovies = response.body().getMovies();

                    rvMovies.setAdapter(new MovieAdapter(getContext(), listMovies));
                    adapter.notifyDataSetChanged();
                }
                else {
                    loading.dismiss();
                    Toast.makeText(getContext(), "Failed to Fetch Data ...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(getContext(), "Failed to Connect Internet ...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void searchMovie(String keyword){
        apiService.searchMovie(api_key, language, keyword, page, include_adult).enqueue(new Callback<ResponseMovies>() {
            @Override
            public void onResponse(Call<ResponseMovies> call, Response<ResponseMovies> response) {
                if (response.isSuccessful()){
                    listMovies = response.body().getMovies();

                    rvMovies.setAdapter(new MovieAdapter(getContext(), listMovies));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Failed to Fetch Data ...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMovies> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to Connect Internet ...", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
