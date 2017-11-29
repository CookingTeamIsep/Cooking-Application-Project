package isep.bcntt.cooking;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import isep.bcntt.cooking.RecipesAdapter.RecipeAdapterOnClickHandler;

public class RecipesActivity extends AppCompatActivity implements RecipeAdapterOnClickHandler{

    private RecyclerView mRecipesList;
    private RecipesAdapter mRecipesAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        mRecipesList = findViewById(R.id.rv_recipes);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecipesList.setLayoutManager(layoutManager);
        mRecipesList.setHasFixedSize(true);

        mRecipesAdapter = new RecipesAdapter(this);
        mRecipesList.setAdapter(mRecipesAdapter);

        loadRecipeData();
    }

    @Override
    public void onClick(String data) {
        Context context = this;
        Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
    }


    private void loadRecipeData() {
        showRecipeDataView();
        new FetchRecipesTask().execute();
    }

    private void showRecipeDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecipesList.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecipesList.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
    
    public class FetchRecipesTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            return new String[30];
        }

        @Override
        protected void onPostExecute(String[] recipesData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (recipesData != null) {
                showRecipeDataView();
                mRecipesAdapter.setmRecipesData(recipesData);
            } else {
                showErrorMessage();
            }
        }
    }
}