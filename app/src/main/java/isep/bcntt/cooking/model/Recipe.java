package isep.bcntt.cooking.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    private String id;
    private int kcal;
    private int prot;
    private int calc;
    private int carbs;
    private String name;
    private int difficulty;
    private int dishesSize;
    private String description;
    private String[] ingredientsId;
    private String[] toolsId;
    private long idDb;

    public Recipe(String id, int kcal, int prot, int calc, int carbs, String name, int difficulty, int dishesSize, String description, String[] ingredientsId, String[] toolsId) {
        this.id = id;
        this.kcal = kcal;
        this.prot = prot;
        this.calc = calc;
        this.carbs = carbs;
        this.name = name;
        this.difficulty = difficulty;
        this.dishesSize = dishesSize;
        this.description = description;
        this.ingredientsId = ingredientsId;
        this.toolsId = toolsId;
    }

    public Recipe(String id, int kcal, int prot, int calc, int carbs, String name, int difficulty, int dishesSize, String description, String[] ingredientsId, String[] toolsId, long idDb) {
        this.id = id;
        this.kcal = kcal;
        this.prot = prot;
        this.calc = calc;
        this.carbs = carbs;
        this.name = name;
        this.difficulty = difficulty;
        this.dishesSize = dishesSize;
        this.description = description;
        this.ingredientsId = ingredientsId;
        this.toolsId = toolsId;
        this.idDb = idDb;
    }

    public Recipe(Parcel in) {
        this.id = in.readString();
        this.kcal = in.readInt();
        this.prot = in.readInt();
        this.calc = in.readInt();
        this.carbs = in.readInt();
        this.name = in.readString();
        this.difficulty = in.readInt();
        this.dishesSize = in.readInt();
        this.description = in.readString();
        this.ingredientsId = in.createStringArray();
        this.toolsId = in.createStringArray();
    }

    public long getIdDb() {
        return idDb;
    }

    public void setIdDb(long idDb) {
        this.idDb = idDb;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public String[] getIngredientsId() {
        return ingredientsId;
    }

    public void setIngredientsId(String[] ingredientsId) {
        this.ingredientsId = ingredientsId;
    }

    public String[] getToolsId() {
        return toolsId;
    }

    public void setToolsId(String[] toolsId) {
        this.toolsId = toolsId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getKcal() {
        return kcal;
    }

    public void setKcal(int kcal) {
        this.kcal = kcal;
    }

    public int getProt() {
        return prot;
    }

    public void setProt(int prot) {
        this.prot = prot;
    }

    public int getCalc() {
        return calc;
    }

    public void setCalc(int calc) {
        this.calc = calc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDishesSize() {
        return dishesSize;
    }

    public void setDishesSize(int dishesSize) {
        this.dishesSize = dishesSize;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeInt(kcal);
        parcel.writeInt(prot);
        parcel.writeInt(calc);
        parcel.writeInt(carbs);
        parcel.writeString(name);
        parcel.writeInt(difficulty);
        parcel.writeInt(dishesSize);
        parcel.writeString(description);
        parcel.writeStringArray(ingredientsId);
        parcel.writeStringArray(toolsId);
    }
}