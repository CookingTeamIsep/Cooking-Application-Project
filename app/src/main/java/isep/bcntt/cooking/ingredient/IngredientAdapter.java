package isep.bcntt.cooking.ingredient;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import isep.bcntt.cooking.R;
import isep.bcntt.cooking.model.Ingredient;

/**
 * {@link IngredientAdapter} exposes a list of ingredients to a
 * {@link android.support.v7.widget.RecyclerView}
 */
public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    private final IngredientAdapterOnClickHandler mIngredientClickHandler;
    private Context mContext;
    private List<Ingredient> mIngredientList;

    /**
     * Creates a IngredientAdapter.
     */
    public IngredientAdapter(Context mContext, List<Ingredient> ingredientList, IngredientAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.mIngredientList = ingredientList;
        mIngredientClickHandler = clickHandler;
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param parent   The ViewGroup that these ViewHolders are contained within.
     * @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you
     *                 can use this viewType integer to provide a different layout. See
     *                 {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                 for more details.
     * @return A new IngredientAdapterViewHolder that holds the View for each list item
     */
    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item_list, parent, false);
        return new IngredientAdapterViewHolder(itemView);
    }

    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder   The ViewHolder which should be updated to represent the
     *                 contents of the item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final IngredientAdapterViewHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);
        holder.name.setText(ingredient.getName());
        holder.checkBox.setChecked(ingredient.isSelected());
        Glide.with(mContext).load(ingredient.getThumbnail()).into(holder.thumbnail);

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIngredientClickHandler.onClick();
            }
        });
    }

    /**
     * This method simply returns the number of items to display.
     */
    @Override
    public int getItemCount() {
        return mIngredientList.size();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface IngredientAdapterOnClickHandler {
        void onClick();
    }

    /**
     * Cache of the children views for a Ingredient list item.
     */
    public class IngredientAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public TextView name;
        public CheckBox checkBox;
        public ImageView thumbnail;

        public IngredientAdapterViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.tv_ingredient_data);
            thumbnail = view.findViewById(R.id.thumbnail_ingredient);
            checkBox = view.findViewById(R.id.ingredient_checkbox);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            /*Ingredient ingredient = mIngredientList.get(adapterPosition);*/
            mIngredientClickHandler.onClick();
        }
    }
}