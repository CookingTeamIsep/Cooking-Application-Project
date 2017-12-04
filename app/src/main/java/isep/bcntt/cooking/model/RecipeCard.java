package isep.bcntt.cooking.model;

public class RecipeCard {
    private String mName;
    private int mNumbreOfRatings;
    private int thumbnail;

    public RecipeCard() {
    }

    public RecipeCard(String name, int numOfSongs, int thumbnail) {
        this.mName = name;
        this.mNumbreOfRatings = numOfSongs;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public int getNumbreOfRatings() {
        return mNumbreOfRatings;
    }

    public void setNumbreOfRatings(int mNumbreOfRatings) {
        this.mNumbreOfRatings = mNumbreOfRatings;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}