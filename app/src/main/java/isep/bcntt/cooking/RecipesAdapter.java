package isep.bcntt.cooking;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
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

import isep.bcntt.cooking.model.RecipeCard;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.recipeAdapterViewHolder> {

    private Context mContext;
    private List<RecipeCard> recipeList;
    private static RecipesAdapterOnClickHandler mRecipeCardClickHandler;

    public RecipesAdapter(Context mContext, List<RecipeCard> recipeList, RecipesAdapterOnClickHandler clickHandler) {
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
        RecipeCard recipe = recipeList.get(position);
        holder.title.setText(recipe.getName());
        holder.count.setText(recipe.getNumbreOfRatings() + " ratings");

        // loading recipe cover using Glide library
        Glide.with(mContext).load(recipe.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecipeCardClickHandler.onClick();
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_recipe, popup.getMenu());
        popup.setOnMenuItemClickListener(new menuRecipeItemClickListener());
        popup.show();
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public class recipeAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public recipeAdapterViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            count = view.findViewById(R.id.count);
            thumbnail = view.findViewById(R.id.thumbnail);
            overflow = view.findViewById(R.id.overflow);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mRecipeCardClickHandler.onClick();
        }
    }

    /**
     * Click listener for popup menu items
     */
    class menuRecipeItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public menuRecipeItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Share", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    public interface RecipesAdapterOnClickHandler {
        void onClick();
    }
}