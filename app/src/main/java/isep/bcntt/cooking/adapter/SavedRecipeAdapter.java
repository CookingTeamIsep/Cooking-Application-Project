package isep.bcntt.cooking.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import isep.bcntt.cooking.R;
import isep.bcntt.cooking.data.DbUtils;
import isep.bcntt.cooking.data.SavedRecipeDbHelper;
import isep.bcntt.cooking.model.Recipe;

public class SavedRecipeAdapter extends RecyclerView.Adapter<SavedRecipeAdapter.SavedRecipeAdapterViewHolder> {

    private static SavedRecipeAdapterOnClickHandler mRecipeCardClickHandler;
    private final Context mContext;
    private List<Recipe> mSavedRecipeList;

    public SavedRecipeAdapter(Context mContext, List<Recipe> savedRecipeList, SavedRecipeAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.mSavedRecipeList = savedRecipeList;
        mRecipeCardClickHandler = clickHandler;
    }

    @Override
    public SavedRecipeAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        return new SavedRecipeAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final SavedRecipeAdapterViewHolder holder, final int position) {
        final Recipe recipe = mSavedRecipeList.get(position);
        holder.title.setText(recipe.getName());
        holder.info.setText("Difficulty: " + recipe.getDifficulty() + "  Dish size: " + recipe.getDishesSize() + position);

        // loading recipe cover using Glide library
        Glide.with(mContext).load("http://localhost:8080/picture/" + recipe.getName().replace(" ", "_")).into(holder.thumbnail);
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow, recipe, position);
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
    private void showPopupMenu(View view, Recipe recipe, int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_saved_recipe, popup.getMenu());
        popup.setOnMenuItemClickListener(new menuRecipeItemClickListener(view, recipe, position));
        popup.show();
    }

    @Override
    public int getItemCount() {
        return mSavedRecipeList.size();
    }

    public interface SavedRecipeAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public class SavedRecipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, info;
        public ImageView thumbnail, overflow;

        public SavedRecipeAdapterViewHolder(View view) {
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
            Recipe recipe = mSavedRecipeList.get(adapterPosition);
            mRecipeCardClickHandler.onClick(recipe);
        }
    }

    /**
     * Click listener for popup menu items
     */
    class menuRecipeItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private View view;
        private Recipe recipe;
        private int position;

        public menuRecipeItemClickListener(View view, Recipe recipe, int position) {
            this.view = view;
            this.recipe = recipe;
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_share:
                    ShareCompat.IntentBuilder
                            .from((Activity) view.getContext())
                            .setType("text/plain")
                            .setChooserTitle("Partagez la recette du " + recipe.getName())
                            .setText("Recette " + recipe.getName() + "\n\n" + recipe.getDescription())
                            .startChooser();
                    return true;
                case R.id.action_delete:
                    DbUtils.removeSavedRecipe(new SavedRecipeDbHelper(mContext).getWritableDatabase(), recipe.getIdDb());
                    Toast.makeText(mContext, "Delete success", Toast.LENGTH_SHORT).show();
                    mSavedRecipeList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, getItemCount());
                    return true;
                default:
            }
            return false;
        }
    }
}