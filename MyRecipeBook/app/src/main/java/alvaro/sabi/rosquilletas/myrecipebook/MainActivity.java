package alvaro.sabi.rosquilletas.myrecipebook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import alvaro.sabi.rosquilletas.myrecipebook.menuCreator.MenuCreatorActivity;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.RecipeTypesActivity;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipeActivity;
import alvaro.sabi.rosquilletas.myrecipebook.shoppingLists.ShoppingListsActivity;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public final String ACTIVITY_TITLE = "My Recipe Book";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(ACTIVITY_TITLE);
    }

    public void OnClickMyRecipes(View view)
    {
        Intent intent = new Intent(MainActivity.this, RecipeTypesActivity.class);
        startActivity(intent);
    }

    public void OnClickNewRecipe(View view)
    {
        Intent intent = new Intent(MainActivity.this, EditRecipeActivity.class);
        startActivity(intent);
    }

    public void OnClickShoppingLists(View view)
    {
        Intent intent = new Intent(MainActivity.this, ShoppingListsActivity.class);
        startActivity(intent);
    }

    public void OnClickMenuCreator(View view)
    {
        Intent intent = new Intent(MainActivity.this, MenuCreatorActivity.class);
        startActivity(intent);
    }
}
