package isep.bcntt.cooking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {

    private static final String TAG = RecipesAdapter.class.getSimpleName();
    private static String[] mRecipesData;
    private static RecipeAdapterOnClickHandler mClickHandler;

    public RecipesAdapter(RecipeAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public void setmRecipesData(String[] recipesData) {
        mRecipesData = recipesData;
        notifyDataSetChanged();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.recipes_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        String recipeData = mRecipesData[position];
        RecipeViewHolder.mRecipeTextView.setText(recipeData);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipesData) return 0;
        return mRecipesData.length;
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

        public static TextView mRecipeTextView;

        public RecipeViewHolder(View view) {
            super(view);
            mRecipeTextView = view.findViewById(R.id.tv_recipes_data);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(mRecipesData[adapterPosition]);
        }
    }

    public interface RecipeAdapterOnClickHandler {
        void onClick(String recipe);
    }
}