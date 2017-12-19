package isep.bcntt.cooking;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import isep.bcntt.cooking.model.Recipe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {


    private final OkHttpClient client = new OkHttpClient();
    private final Moshi moshi = new Moshi.Builder().build();
    private final Type type = Types.newParameterizedType(List.class, Recipe.class);
    private final JsonAdapter<List<Recipe>> gistJsonAdapter = moshi.adapter(type);

    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private List<Recipe> mRecipeList;
    private ArrayList<String> mIngredientsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        mRecyclerView = findViewById(R.id.rv_recipe);

        mIngredientsArray = getIntent().getExtras().getStringArrayList("Ingredients");

        mRecipeList = new ArrayList<>();

        preparerecipes();
    }

    /**
     * Adding few recipes for testing
     */
    private String getPostBody(ArrayList<String> list) {
        String body = "{\"ingredientIds\" : [";
        for (String s : list) {
            body += "\"" + s + "\",";
        }
        body = body.substring(0, body.length() - 1) + "]}";
        System.out.println(body);
        return body;
    }

    private void preparerecipes() {
        String postBody = getPostBody(mIngredientsArray);

        final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Request request = new Request.Builder()
                .url("http://localhost:8080/recipe/get/withIngredients")
                .post(RequestBody.create(JSON, postBody))
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                mRecipeList = gistJsonAdapter.fromJson(response.body().source());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecipesAdapter = new RecipesAdapter(getApplicationContext(), mRecipeList, RecipesActivity.this);
                        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setAdapter(mRecipesAdapter);
                        mRecipesAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intentToStartDetailActivity = new Intent(this, RecipeDetailsActivity.class);
        intentToStartDetailActivity.putExtra("recipe", recipe);
        startActivity(intentToStartDetailActivity);
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}