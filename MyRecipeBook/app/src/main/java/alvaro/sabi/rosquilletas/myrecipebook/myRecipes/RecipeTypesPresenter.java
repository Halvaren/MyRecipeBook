package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;

import com.android.volley.Response;

import alvaro.sabi.rosquilletas.myrecipebook.Model;

public class RecipeTypesPresenter {

    RecipeTypesActivity view;
    Model model;

    public RecipeTypesPresenter(RecipeTypesActivity param0, Context context)
    {
        view = param0;
        model = Model.getInstance(context);
    }

    public String getButtonText(int id)
    {
        return model.getRecipeTypeName(id);
    }
}
