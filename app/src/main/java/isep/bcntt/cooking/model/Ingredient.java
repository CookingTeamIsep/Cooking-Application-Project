package isep.bcntt.cooking.model;

public class Ingredient {
    private String id;
    private String name;
    private boolean isSelected;

    public Ingredient() {
    }

    public Ingredient(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.isSelected = selected;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        this.isSelected = selected;
    }
}