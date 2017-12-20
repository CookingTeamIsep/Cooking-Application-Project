package isep.bcntt.cooking;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import isep.bcntt.cooking.model.Ingredient;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mainnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_mes_recettes:
                    showFragment(new SavedRecipeFragment());
                    return true;
                case R.id.navigation_mon_frigo:
                    showFragment(new IngredientFragment());
                    return true;
                case R.id.navigation_rechercher:
                    goToRecipeSearchActivity();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation_ingredient_category);
        navigation.setOnNavigationItemSelectedListener(mainnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_mon_frigo);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            goToRecipeSearchActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void goToRecipeSearchActivity() {
        ArrayList<String> ingredients = new ArrayList<>();
        for (Ingredient ingredient : IngredientFragment.mIngredientList) {
            if (ingredient.isSelected()) {
                ingredients.add(ingredient.getId());
            }
        }
        Intent intentToStartDetailActivity = new Intent(this, RecipesActivity.class);
        intentToStartDetailActivity.putStringArrayListExtra("Ingredients", ingredients);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_favorites) {
            showFragment(new SavedRecipeFragment());
            BottomNavigationView navigation = findViewById(R.id.navigation_ingredient_category);
            navigation.setOnNavigationItemSelectedListener(mainnNavigationItemSelectedListener);
            navigation.setSelectedItemId(R.id.navigation_mes_recettes);
        } else if (id == R.id.nav_my_account) {
            Intent intentToStartDetailActivity = new Intent(this, LoginActivity.class);
            startActivity(intentToStartDetailActivity);
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:example@cooking.com"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        } else if (id == R.id.nav_about_cooking) {
            Intent intentToStartDetailActivity = new Intent(this, AboutCookingActivity.class);
            startActivity(intentToStartDetailActivity);
        } else if (id == R.id.nav_about_dev_team) {
            Intent intentToStartDetailActivity = new Intent(this, AboutDevTeamActivity.class);
            startActivity(intentToStartDetailActivity);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fl_content_page_indregient, fragment)
                .commit();
    }
}
