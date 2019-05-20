package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;

import com.android.volley.Response;

import alvaro.sabi.rosquilletas.myrecipebook.Model;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

public class MyRecipesListPresenter
{
    MyRecipesListActivity view;
    Model model;

    public MyRecipesListPresenter(MyRecipesListActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    public void requestRecipeList(int recipeType) {
        model.getRecipeList(new Response.Listener<Recipe[]>() {
            @Override
            public void onResponse(Recipe[] response) {
                sendRecipeList(response);
            }
        }, recipeType);
    }

    public void sendRecipeList(Recipe[] recipeList)
    {
        view.setRecipeList(recipeList);
    }
}