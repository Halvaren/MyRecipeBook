package alvaro.sabi.rosquilletas.myrecipebook.recipeTypes;

import android.content.Context;

import alvaro.sabi.rosquilletas.myrecipebook.Model;

/*
    Presenter de RecipeTypesActivity
 */

public class RecipeTypesPresenter {

    RecipeTypesActivity view;
    Model model;

    public RecipeTypesPresenter(RecipeTypesActivity param0, Context context)
    {
        view = param0;
        model = Model.getInstance(context);
    }

    //Método que solicita al model el nombre de un tipo de receta a partir de su ID
    public String getButtonText(int id)
    {
        return model.getRecipeTypeName(id);
    }
}
