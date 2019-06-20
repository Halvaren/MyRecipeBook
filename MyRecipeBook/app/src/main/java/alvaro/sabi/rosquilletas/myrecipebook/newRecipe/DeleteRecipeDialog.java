package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.MyRecipesListActivity;
import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class DeleteRecipeDialog extends DialogFragment {
    private MyRecipesListActivity activity;
    private Recipe recipe;
    private int recipePosition;

    public DeleteRecipeDialog (MyRecipesListActivity activity, Recipe recipe, int recipePosition)
    {
        this.activity = activity;
        this.recipe = recipe;
        this.recipePosition = recipePosition;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.delete_recipe_dialog, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        activity.deleteRecipe(recipe, recipePosition);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        return builder.create();
    }
}