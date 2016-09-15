package com.jetruby.dribbbyviewer.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.GridView;
import android.widget.Toast;

import com.jetruby.dribbbyviewer.R;
import com.jetruby.dribbbyviewer.adapter.GridViewAdapter;
import com.jetruby.dribbbyviewer.controller.RealmController;
import com.jetruby.dribbbyviewer.model.Shot;
import com.jetruby.dribbbyviewer.rest.RestClient;
import com.jetruby.dribbbyviewer.rest.RestService;
import com.jetruby.dribbbyviewer.util.Constant;
import com.jetruby.dribbbyviewer.util.Helper;

import java.util.List;

import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = GridViewActivity.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayout;
    private GridView gridView;
    private GridViewAdapter gridViewAdapter;

    private RealmController realmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_view);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        gridView = (GridView) findViewById(R.id.grid_view);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item);
        gridView.setAdapter(gridViewAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);

        realmController = RealmController.with(this);

        if (!realmController.hasAnyObjects()) {
            Toast.makeText(this, R.string.pull_down_prompt, Toast.LENGTH_LONG).show();
        } else {
            RealmResults<Shot> realmResults = realmController.getShots();
            gridViewAdapter.setDataSet(realmResults);
        }
    }

    @Override
    public void onRefresh() {
        if (Helper.isNetworkConnected(this)) {
            swipeRefreshLayout.setRefreshing(true);
            RestService restService = RestClient.getClient().create(RestService.class);
            Call<List<Shot>> call = restService.getRecentPopularShots(Constant.REST_API_ACCESS_TOKEN, 2 * Constant.SHOTS);
            call.enqueue(new Callback<List<Shot>>() {
                @Override
                public void onResponse(Call<List<Shot>> call, Response<List<Shot>> response) {
                    swipeRefreshLayout.setRefreshing(false);
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        List<Shot> shots = response.body();
                        List<Shot> copy = realmController.copyToEmptyShotRealm(Helper.filter(shots));
                        gridViewAdapter.setDataSet(copy);
                    } else {
                        Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Shot>> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e(TAG, t.getMessage());
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(this, R.string.internet_connection_unavailable, Toast.LENGTH_LONG).show();
            RealmResults<Shot> realmResults = realmController.getShots();
            gridViewAdapter.setDataSet(realmResults);
        }
    }
}
