package isep.bcntt.cooking.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import isep.bcntt.cooking.model.Recipe;

public class DbUtils {
    public static String strSeparator = "__,__";

    public static String convertArrayToString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str + array[i];
            // Do not append comma at the end of last element
            if (i < array.length - 1) {
                str = str + strSeparator;
            }
        }
        return str;
    }

    public static String[] convertStringToArray(String str) {
        String[] arr = str.split(strSeparator);
        return arr;
    }

    public static void addToSavedRecipe(SQLiteDatabase db, Recipe recipe) {

        if (db == null) {
            return;
        }

        ContentValues cv = new ContentValues();
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_ID, recipe.getId());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_KCAL, recipe.getKcal());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_PROT, recipe.getProt());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_CALC, recipe.getCalc());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_CARBS, recipe.getCarbs());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_NAME, recipe.getName());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_DIFFCULTY, recipe.getDifficulty());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_DISHESSIZE, recipe.getDishesSize());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_DESCRIPTION, recipe.getDescription());
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_INGREDIENTSID, DbUtils.convertArrayToString(recipe.getIngredientsId()));
        cv.put(SavedRecipeContract.SavedRecipeEntry.COLUMN_TOOLSID, DbUtils.convertArrayToString(recipe.getToolsId()));

        try {
            db.beginTransaction();
            db.insert(SavedRecipeContract.SavedRecipeEntry.TABLE_NAME, null, cv);
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            //too bad :(
        } finally {
            db.endTransaction();
        }
    }

    public static ArrayList<Recipe> getSavedRecipe(SQLiteDatabase db) {
        Cursor mCursor = db.query(
                SavedRecipeContract.SavedRecipeEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Recipe> mRecipeList = new ArrayList<>();
        for (int i = 0; i < mCursor.getCount(); i++) {
            mCursor.moveToPosition(i);

            mRecipeList.add(new Recipe(
                    mCursor.getString(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_ID)),
                    mCursor.getInt(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_KCAL)),
                    mCursor.getInt(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_PROT)),
                    mCursor.getInt(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_CALC)),
                    mCursor.getInt(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_CARBS)),
                    mCursor.getString(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_NAME)),
                    mCursor.getInt(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_DIFFCULTY)),
                    mCursor.getInt(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_DISHESSIZE)),
                    mCursor.getString(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_DESCRIPTION)),
                    convertStringToArray(mCursor.getString(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_INGREDIENTSID))),
                    convertStringToArray(mCursor.getString(mCursor.getColumnIndex(SavedRecipeContract.SavedRecipeEntry.COLUMN_TOOLSID)))
            ));
        }
        return mRecipeList;
    }
}
