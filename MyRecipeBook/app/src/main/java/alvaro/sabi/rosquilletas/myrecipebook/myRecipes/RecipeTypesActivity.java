package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeTypesActivity extends AppCompatActivity {

    private RecipeTypesPresenter presenter;

    private Button appetizerButton;
    private Button starterButton;
    private Button secondCourseButton;
    private Button sauceButton;
    private Button dessertButton;
    private Button drinkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_types_activity);

        presenter = new RecipeTypesPresenter(this, this);

        appetizerButton = findViewById(R.id.appetizerButton);
        starterButton = findViewById(R.id.starterButton);
        secondCourseButton = findViewById(R.id.secondCourseButton);
        sauceButton = findViewById(R.id.sauceButton);
        dessertButton = findViewById(R.id.dessertButton);
        drinkButton = findViewById(R.id.drinkButton);

        for(int i = 0; i < 6; i++)
        {
            getButtonText(i);
        }
    }

    public void getButtonText(int id)
    {
        String text = presenter.getButtonText(id);
        switch(id)
        {
            case 0:
                appetizerButton.setText(text);
                break;
            case 1:
                starterButton.setText(text);
                break;
            case 2:
                secondCourseButton.setText(text);
                break;
            case 3:
                sauceButton.setText(text);
                break;
            case 4:
                dessertButton.setText(text);
                break;
            case 5:
                drinkButton.setText(text);
                break;
        }
    }

    public void OnClickAppetizer(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 0);

        startActivity(intent);
    }

    public void OnClickStarter(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 1);

        startActivity(intent);
    }

    public void OnClickSecondCourse(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 2);

        startActivity(intent);
    }


    public void OnClickSauce(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 3);

        startActivity(intent);
    }

    public void OnClickDessert(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 4);

        startActivity(intent);
    }

    public void OnClickDrink(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 5);

        startActivity(intent);
    }
}
