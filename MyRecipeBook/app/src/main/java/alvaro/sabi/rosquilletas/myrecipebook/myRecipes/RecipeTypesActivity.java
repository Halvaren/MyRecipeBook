package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.RecipeTypesInterface;
import alvaro.sabi.rosquilletas.myrecipebook.R;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeTypesActivity extends AppCompatActivity implements RecipeTypesInterface {

    //Título de la actividad
    public final String ACTIVITY_TITLE = "Recipe Types";

    private RecipeTypesPresenter presenter;

    //Elementos del layout
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

        //Se obtienen los elementos del layout
        appetizerButton = findViewById(R.id.appetizerButton);
        starterButton = findViewById(R.id.starterButton);
        secondCourseButton = findViewById(R.id.secondCourseButton);
        sauceButton = findViewById(R.id.sauceButton);
        dessertButton = findViewById(R.id.dessertButton);
        drinkButton = findViewById(R.id.drinkButton);

        //Para cada botón se llama al método getButtonText
        for(int i = 0; i < 6; i++)
        {
            getButtonText(i);
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //Activamos el botón de Atrás en la barra superior
        actionBar.setTitle(ACTIVITY_TITLE); //Cambiamos el título por el que se tiene guardado en la constante
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        finish(); //Se vuelve a la actividad anterior
        return true;
    }

    //Método que primeramente pide al presenter el texto para el botón y posteriormente se le asigna al botón en cuestión
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

    //Método que se llama cuando se pulsa el botón de Appetizer
    public void onClickAppetizer(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 0);

        startActivity(intent);
    }

    //Método que se llama cuando se pulsa el botón de Starter
    public void onClickStarter(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 1);

        startActivity(intent);
    }

    //Método que se llama cuando se pulsa el botón de Second Source
    public void onClickSecondCourse(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 2);

        startActivity(intent);
    }

    //Método que se llama cuando se pulsa el botón de Sauce
    public void onClickSauce(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 3);

        startActivity(intent);
    }

    //Método que se llama cuando se pulsa el botón de Dessert
    public void onClickDessert(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 4);

        startActivity(intent);
    }

    //Método que se llama cuando se pulsa el botón de Drink
    public void onClickDrink(View view)
    {
        Intent intent = new Intent(RecipeTypesActivity.this, MyRecipesListActivity.class);
        intent.putExtra("RecipeType", 5);

        startActivity(intent);
    }
}
