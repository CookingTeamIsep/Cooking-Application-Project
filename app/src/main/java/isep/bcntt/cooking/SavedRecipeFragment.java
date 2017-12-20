package isep.bcntt.cooking;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import isep.bcntt.cooking.adapter.SavedRecipeAdapter;
import isep.bcntt.cooking.data.DbUtils;
import isep.bcntt.cooking.data.SavedRecipeDbHelper;
import isep.bcntt.cooking.model.Recipe;

public class SavedRecipeFragment extends Fragment implements SavedRecipeAdapter.SavedRecipeAdapterOnClickHandler {

    private SQLiteDatabase mDb;
    private RecyclerView mRecyclerView;
    private SavedRecipeAdapter mSavedRecipeAdapter;
    private List<Recipe> mSavedRecipeList;

    public SavedRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        SavedRecipeDbHelper dbHelper = new SavedRecipeDbHelper(getContext());
        mDb = dbHelper.getWritableDatabase();
        mSavedRecipeList = DbUtils.getSavedRecipe(mDb);

        mSavedRecipeAdapter = new SavedRecipeAdapter(getContext(), mSavedRecipeList, this);

        mRecyclerView = rootView.findViewById(R.id.rv_ingredient);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mSavedRecipeAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(Recipe recipe) {
        Intent intentToStartDetailActivity = new Intent(getActivity(), RecipeDetailsActivity.class);
        intentToStartDetailActivity.putExtra("recipe", recipe);
        startActivity(intentToStartDetailActivity);
    }
}
