package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.MyRecipeListAdapter;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.MyRecipesListActivity;
import alvaro.sabi.rosquilletas.myrecipebook.myRecipes.MyRecipesListPresenter;
import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class DeleteRecipeDialog extends DialogFragment {
    private MyRecipesListActivity activity;
    private Recipe recipe;
    private int recipePosition;
    private MyRecipeListAdapter adapter;
    private MyRecipesListPresenter presenter;
    public DeleteRecipeDialog (MyRecipesListActivity activity, Recipe recipe, int i, Context ctx, String type) {
        this.activity = activity;
        this.recipe = recipe;
        adapter = new MyRecipeListAdapter(activity, ctx, type);
        recipePosition = i;

    }

    //private EditRecipePresenter presenter = new EditRecipePresenter(view, ctx);
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.delete_recipe_dialog, null))
                // Add action buttons
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