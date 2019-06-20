package alvaro.sabi.rosquilletas.myrecipebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.MainActivityInterface;
import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.ToastMessages;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.RecipeTypesActivity;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipeActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MainActivityInterface, ToastMessages {

    //Título de la actividad
    public final String ACTIVITY_TITLE = "My Recipe Book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(ACTIVITY_TITLE); //Indica el título de la actividad
    }

    //Método que se lanza cuando se pulsa el botón My Recipes
    public void onClickMyRecipes(View view) {

        Intent intent = new Intent(MainActivity.this, RecipeTypesActivity.class);
        startActivity(intent);
    }

    //Método que se lanza cuando se pulsa el botón New Recipe
    public void onClickNewRecipe(View view) {

        Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
        startActivity(intent);
    }

    //Método que se lanza cuando se pulsa el botón Shopping Lists
    public void onClickShoppingLists(View view) {

        //Intent intent = new Intent(MainActivity.this, ShoppingListsActivity.class);
        //startActivity(intent);

        showToast(nowInDevelopment);
    }

    //Método que se lanza cuando se pulsa el botón Menu Creator
    public void onClickMenuCreator(View view) {

        //Intent intent = new Intent(MainActivity.this, MenuCreatorActivity.class);
        //startActivity(intent);

        showToast(nowInDevelopment);
    }

    //Método que lanza un mensaje Toast con cierta información para el usuario
    public void showToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
