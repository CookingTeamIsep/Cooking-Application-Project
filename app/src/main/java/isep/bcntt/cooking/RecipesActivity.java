package isep.bcntt.cooking;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import isep.bcntt.cooking.model.RecipeCard;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.RecipesAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private RecipesAdapter adapter;
    private List<RecipeCard> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        recyclerView = findViewById(R.id.rv_recipe);

        recipeList = new ArrayList<>();
        adapter = new RecipesAdapter(this, recipeList, this);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        preparerecipes();
    }

    /**
     * Adding few recipes for testing
     */
    private void preparerecipes() {
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

        RecipeCard a = new RecipeCard("Recipe 1", 13, covers[0]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 2", 8, covers[1]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 3", 11, covers[2]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 4", 12, covers[3]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 5", 14, covers[4]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 6", 1, covers[5]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 7", 11, covers[6]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 8", 14, covers[7]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 9", 11, covers[8]);
        recipeList.add(a);

        a = new RecipeCard("Recipe 10", 17, covers[9]);
        recipeList.add(a);

        adapter.notifyDataSetChanged();
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onClick(String recipe) {
        Intent intent = new Intent(getApplicationContext(), RecipeDetailsActivity.class);
        startActivity(intent);
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