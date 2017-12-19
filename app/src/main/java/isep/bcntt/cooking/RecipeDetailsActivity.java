package isep.bcntt.cooking;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import isep.bcntt.cooking.model.Recipe;
import isep.bcntt.cooking.utilities.DownloadImageTask;

public class RecipeDetailsActivity extends AppCompatActivity {

    private Recipe recipe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipe = getIntent().getExtras().getParcelable("recipe");

        TextView mTitleRecipe = findViewById(R.id.tv_title_recipe_details);
        mTitleRecipe.setText(recipe.getName());

        ImageView mRecipeMainLogo = findViewById(R.id.backdrop);
        Glide.with(this).load("http://localhost:8080/picture/"+recipe.getName().replace(" ","_")).into(mRecipeMainLogo);

        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // Create the adapter that will return a fragment for each of the three primary sections of the activity.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container_tab_recipe_details);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs_recipe_details);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Recipe saved", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public static class PlaceholderFragment extends Fragment {

        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public static PlaceholderFragment newInstance(String s) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putString(ARG_SECTION_NUMBER, s);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
            TextView textView = rootView.findViewById(R.id.tv_fragment_recipe_details_inner_text);
            textView.setText(getArguments().getString(ARG_SECTION_NUMBER));
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return PlaceholderFragment.newInstance(
                        recipe.getName() + "\n\n" +
                        "kcal: " + recipe.getKcal() + "\n\n" +
                        "prot :" + recipe.getProt() + "\n\n" +
                        "calc:" + recipe.getCalc() + "\n\n\n\n" +
                        "Diffulty : " + recipe.getDifficulty()  + "/5\n\n" +
                        " Dishes size: " + recipe.getDishesSize() + "/5");
            } else if (position == 1 ){
                return PlaceholderFragment.newInstance(1);
            } else if (position == 2 ){
                return PlaceholderFragment.newInstance(recipe.getDescription());
            } else if (position == 3 ){
                return PlaceholderFragment.newInstance(recipe.getDescription());
            } else {
                return PlaceholderFragment.newInstance(recipe.getName());
            }
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
