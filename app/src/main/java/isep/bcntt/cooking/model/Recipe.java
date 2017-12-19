package isep.bcntt.cooking.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    private String id;
    private int kcal;
    private int prot;
    private int calc;
    private String name;
    private int difficulty;
    private int dishesSize;
    private String description;

    public Recipe(String id, int kcal, int prot, int calc, String name, int difficulty, int dishesSize, String description) {
        this.id = id;
        this.kcal = kcal;
        this.prot = prot;
        this.calc = calc;
        this.name = name;
        this.difficulty = difficulty;
        this.dishesSize = dishesSize;
        this.description = description;
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
        parcel.writeString(name);
        parcel.writeInt(difficulty);
        parcel.writeInt(dishesSize);
        parcel.writeString(description);
    }

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

    public Recipe(Parcel in){
        this.id = in.readString();
        this.kcal = in.readInt();
        this.prot = in.readInt();
        this.calc = in.readInt();
        this.name = in.readString();
        this.difficulty = in.readInt();
        this.dishesSize = in.readInt();
        this.description = in.readString();
    }


}