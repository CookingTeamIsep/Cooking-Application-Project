package isep.bcntt.cooking;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ShareCompat;
import android.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import isep.bcntt.cooking.model.Recipe;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.recipeAdapterViewHolder> {

    private final Context mContext;
    private List<Recipe> recipeList;
    private static RecipesAdapterOnClickHandler mRecipeCardClickHandler;

    public RecipesAdapter(Context mContext, List<Recipe> recipeList, RecipesAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.recipeList = recipeList;
        mRecipeCardClickHandler = clickHandler;
    }

    @Override
    public recipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        return new recipeAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final recipeAdapterViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
        holder.title.setText(recipe.getName());
        holder.info.setText("Difficulty: " + recipe.getDifficulty() + "  Dish size: " + recipe.getDishesSize());

        // loading recipe cover using Glide library
        Glide.with(mContext).load("http://localhost:8080/picture/"+recipe.getName().replace(" ","_")).into(holder.thumbnail);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, recipe);
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecipeCardClickHandler.onClick(recipe);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, Recipe recipe) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_recipe, popup.getMenu());
        popup.setOnMenuItemClickListener(new menuRecipeItemClickListener(view, recipe));
        popup.show();
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class recipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, info;
        public ImageView thumbnail, overflow;

        public recipeAdapterViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            info = view.findViewById(R.id.tv_info);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = recipeList.get(adapterPosition);
            mRecipeCardClickHandler.onClick(recipe);
        }
    }

    /**
     * Click listener for popup menu items
     */
    class menuRecipeItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private View view;
        private Recipe recipe;

        public menuRecipeItemClickListener(View view, Recipe recipe) {
            this.view = view;
            this.recipe = recipe;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    ShareCompat.IntentBuilder
                            .from((Activity) view.getContext())
                            .setType("text/plain")
                            .setChooserTitle("Partagez la recette du " + recipe.getName())
                            .setText("Recette " + recipe.getName() + "\n\n" + recipe.getDescription())
                            .startChooser();
                    return true;
                default:
            }
            return false;
        }
    }

    public interface RecipesAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }
}