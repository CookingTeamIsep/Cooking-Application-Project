package isep.bcntt.cooking.data;

import android.provider.BaseColumns;

public class SavedRecipeContract {

    public static final class SavedRecipeEntry implements BaseColumns {

        public static final String TABLE_NAME = "savedRecipe";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_KCAL = "kcal";
        public static final String COLUMN_PROT = "prot";
        public static final String COLUMN_CALC = "calc";
        public static final String COLUMN_CARBS = "carbs";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DIFFCULTY = "difficulty";
        public static final String COLUMN_DISHESSIZE = "dishesSize";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_INGREDIENTSID = "ingredientsId";
        public static final String COLUMN_TOOLSID = "toolsId";
    }

}