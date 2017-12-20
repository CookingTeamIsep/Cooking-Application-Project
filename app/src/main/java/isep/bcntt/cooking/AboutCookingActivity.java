package isep.bcntt.cooking;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AboutCookingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_recipe_details);

        TextView tv = findViewById(R.id.tv_fragment_recipe_details_inner_text);
        tv.setText(R.string.about_cooking);
    }
}
