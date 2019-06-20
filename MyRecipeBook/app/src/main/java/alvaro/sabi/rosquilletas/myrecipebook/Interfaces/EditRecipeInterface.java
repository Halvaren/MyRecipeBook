package alvaro.sabi.rosquilletas.myrecipebook.Interfaces;

import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

public interface EditRecipeInterface {

    void setRecipe(Recipe recipe);

    void addNewIngredient(View view);
    void addNewStep(View view);
    void finishRecipe(View view);

    boolean checkFilledFields();
    void exitFromActivity();

    void changeHeightList(ListView list);

    void seekBarUpdated();

    String getRecipeName();
    int getRecipeTypeID();
    int getNumGuests();
    float getValuation();
    int getDifficultyID();
    ArrayList<String> getIngredientList();
    ArrayList<String> getStepList();

    void setRecipeName(String value);
    void setRecipeTypeID(int value);
    void setNumGuests(int value);
    void setValuation(float value);
    void setDifficultyID(int value);
    void setIngredientList(ArrayList<String> value);
    void setStepList(ArrayList<String> value);
}
