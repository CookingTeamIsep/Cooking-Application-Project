package isep.bcntt.cooking.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import isep.bcntt.cooking.data.SavedRecipeContract.SavedRecipeEntry;

public class SavedRecipeDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "savedrecipe.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 2;
    
    public SavedRecipeDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_SAVEDRECIPE_TABLE = "CREATE TABLE " + SavedRecipeEntry.TABLE_NAME + " (" +
                SavedRecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SavedRecipeEntry.COLUMN_ID + " TEXT NOT NULL, " +
                SavedRecipeEntry.COLUMN_KCAL + " INTEGER NOT NULL, " +
                SavedRecipeEntry.COLUMN_PROT + " INTEGER NOT NULL, " +
                SavedRecipeEntry.COLUMN_CALC + " INTEGER NOT NULL, " +
                SavedRecipeEntry.COLUMN_CARBS + " INTEGER NOT NULL, " +
                SavedRecipeEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                SavedRecipeEntry.COLUMN_DIFFCULTY + " INTEGER NOT NULL, " +
                SavedRecipeEntry.COLUMN_DISHESSIZE + " INTEGER NOT NULL, " +
                SavedRecipeEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                SavedRecipeEntry.COLUMN_INGREDIENTSID + " TEXT NOT NULL, " +
                SavedRecipeEntry.COLUMN_TOOLSID + " TEXT NOT NULL " +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_SAVEDRECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SavedRecipeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}