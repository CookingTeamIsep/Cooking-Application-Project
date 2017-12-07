package isep.bcntt.cooking.ingredient;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import isep.bcntt.cooking.R;
import isep.bcntt.cooking.RecipesAdapter;
import isep.bcntt.cooking.model.IngredientCard;


public class IngredientFragment extends Fragment implements IngredientAdapter.IngredientAdapterOnClickHandler{

    private RecyclerView mRecyclerView;
    private IngredientAdapter mIngredientAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    private List<IngredientCard> mIngredientList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ingredient, container, false);

        mIngredientList = new ArrayList<>();
        mIngredientAdapter = new IngredientAdapter(getContext(), mIngredientList, this);

        mRecyclerView = rootView.findViewById(R.id.rv_ingredient);
        mLayoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mIngredientAdapter);

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
        int[] covers = new int[]{
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,
                R.drawable.ic_dashboard_black_24dp,};

        IngredientCard a = new IngredientCard("Ingredient 1", false, covers[0]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 2", true, covers[1]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 3", false, covers[2]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 4", false, covers[3]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 5", false, covers[4]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 6", false, covers[5]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 7", true, covers[6]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 8", true, covers[7]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 9", false, covers[8]);
        mIngredientList.add(a);

        a = new IngredientCard("Ingredient 10", false, covers[9]);
        mIngredientList.add(a);

        mIngredientAdapter.notifyDataSetChanged();
    }
}
