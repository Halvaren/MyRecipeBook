package alvaro.sabi.rosquilletas.myrecipebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import alvaro.sabi.rosquilletas.myrecipebook.menuCreator.MenuCreatorActivity;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.RecipeTypesActivity;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipeActivity;
import alvaro.sabi.rosquilletas.myrecipebook.shoppingLists.ShoppingListsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
