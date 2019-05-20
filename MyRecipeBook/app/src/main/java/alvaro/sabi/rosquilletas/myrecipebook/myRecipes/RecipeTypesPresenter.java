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

    public void requestButtonTexts()
    {
        model.getButtonTexts(new Response.Listener<Void>() {
            @Override
            public void onResponse(Void response) {
                buttonTextsAvailable();
            }
        }, true);
    }

    public void buttonTextsAvailable()
    {
        view.buttonTextsAvailable();
    }

    public String getButtonText(int id)
    {
        return model.getButtonText(id);
    }
}
