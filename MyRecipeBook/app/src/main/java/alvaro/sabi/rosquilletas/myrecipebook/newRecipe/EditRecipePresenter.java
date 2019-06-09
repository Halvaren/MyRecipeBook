package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.content.Context;

import com.android.volley.Response;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.Model;

public class EditRecipePresenter {

    private EditRecipeActivity view;
    private Model model;

    public EditRecipePresenter(EditRecipeActivity view, Context context)
    {
        this.view = view;
        model = Model.getInstance(context);
    }

    public void requestRecipeTypeNames()
    {
        model.prepareRecipeTypeNames(new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                recipeTypeNamesAvailable();
            }
        }, true);
    }

    private void recipeTypeNamesAvailable() { view.recipeTypeNamesAvailable(); }

    public String[] getRecipeTypeNames(){
        return model.getRecipeTypeNames();
    }

    public String[] getDifficultyNames() { return model.getDifficultyNames(); }
}
