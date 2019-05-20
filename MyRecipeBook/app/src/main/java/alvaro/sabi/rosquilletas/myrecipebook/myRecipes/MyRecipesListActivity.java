package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import androidx.appcompat.app.AppCompatActivity;

public class MyRecipesListActivity extends AppCompatActivity {

    private ListView myRecipesListView;

    private MyRecipesListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_recipes_list_activity);

        presenter = new MyRecipesListPresenter(this, this);

        Intent intent = getIntent();
        int recipeType = intent.getParcelableExtra("RecipeType");

        myRecipesListView = findViewById(R.id.myRecipesListView);

        requestRecipeList(recipeType);
    }

    private void requestRecipeList(int recipeType)
    {
        presenter.requestRecipeList(recipeType);
    }

    public void setRecipeList(Recipe[] recipeList) {

    }
}
