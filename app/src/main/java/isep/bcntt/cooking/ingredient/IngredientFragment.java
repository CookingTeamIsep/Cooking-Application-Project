package isep.bcntt.cooking.ingredient;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import isep.bcntt.cooking.R;
import isep.bcntt.cooking.model.Ingredient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class IngredientFragment extends Fragment implements IngredientAdapter.IngredientAdapterOnClickHandler {

    protected RecyclerView.LayoutManager mLayoutManager;
    private final OkHttpClient client = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();
    private final Type type = Types.newParameterizedType(List.class, Ingredient.class);
    private final JsonAdapter<List<Ingredient>> gistJsonAdapter = moshi.adapter(type);
    private RecyclerView mRecyclerView;
    private IngredientAdapter mIngredientAdapter;
    private List<Ingredient> mIngredientList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        mIngredientList = new ArrayList<>();
        mRecyclerView = rootView.findViewById(R.id.rv_ingredient);


        prepareIngredients();
        return rootView;

    }

    @Override
    public void onClick() {
        Toast.makeText(getContext(), "test", Toast.LENGTH_SHORT).show();
    }

    /**
     * Adding few Ingredients for testing
     */
    private void prepareIngredients() {

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

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                mIngredientList = gistJsonAdapter.fromJson(response.body().source());

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
