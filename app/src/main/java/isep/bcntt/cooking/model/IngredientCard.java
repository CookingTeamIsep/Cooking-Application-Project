package isep.bcntt.cooking.model;

public class IngredientCard {
    private String mName;
    private boolean isSelected;
    private int thumbnail;

    public IngredientCard() {
    }

    public IngredientCard(String name, boolean selected, int thumbnail) {
        this.mName = name;
        this.isSelected = selected;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}