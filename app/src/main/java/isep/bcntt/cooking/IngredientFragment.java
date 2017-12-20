package isep.bcntt.cooking;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import isep.bcntt.cooking.adapter.IngredientAdapter;
import isep.bcntt.cooking.model.Ingredient;
import isep.bcntt.cooking.model.Tool;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class IngredientFragment extends Fragment implements IngredientAdapter.IngredientAdapterOnClickHandler {

    static public List<Ingredient> mIngredientList;
    static public List<Tool> mToolList;
    private final OkHttpClient client = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();
    private final Type type = Types.newParameterizedType(List.class, Ingredient.class);
    private final JsonAdapter<List<Ingredient>> gistJsonAdapter = moshi.adapter(type);
    private final Type typeTool = Types.newParameterizedType(List.class, Tool.class);
    private final JsonAdapter<List<Tool>> toolJsonAdapter = moshi.adapter(typeTool);
    protected RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private IngredientAdapter mIngredientAdapter;
    private ProgressBar mLoadingIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        mIngredientList = new ArrayList<>();
        mToolList = new ArrayList<>();
        mRecyclerView = rootView.findViewById(R.id.rv_ingredient);
        mLoadingIndicator = rootView.findViewById(R.id.pb_loading_indicator_ing);

        prepareIngredients();
        prepareTools();
        return rootView;

    }

    @Override
    public void onClick(Ingredient i) {
        if (i.isSelected()) {
            i.setSelected(false);
        } else {
            i.setSelected(true);
        }

        for (Ingredient ingredient : mIngredientList) {
            if (ingredient.isSelected()) {
                System.out.println(ingredient.getName() + " " + ingredient.isSelected());
            }
        }
    }

    private void prepareIngredients() {

        if (mIngredientList.size() > 0) {
            mIngredientAdapter.notifyDataSetChanged();
        } else {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            Request request = new Request.Builder()
                    .url("http://localhost:8080/ingredient/get")
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    mIngredientList = gistJsonAdapter.fromJson(response.body().source());

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mLoadingIndicator.setVisibility(View.INVISIBLE);
                            mIngredientAdapter = new IngredientAdapter(getContext(), mIngredientList, IngredientFragment.this);
                            mLayoutManager = new LinearLayoutManager(getActivity());
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(mIngredientAdapter);
                            mIngredientAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }

    private void prepareTools() {

        if (mToolList.size() == 0) {
            Request request = new Request.Builder()
                    .url("http://localhost:8080/tool/get")
                    .build();

            client.newCall(request).enqueue(new Callback() {

                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);
                    mToolList = toolJsonAdapter.fromJson(response.body().source());
                }
            });
        }
    }
}
