package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.ToastMessages;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class EditRecipeDialog extends DialogFragment implements ToastMessages {
    private EditRecipeActivity view;
    private Recipe recipe;
    public EditRecipeDialog (EditRecipeActivity view) {

        this.view = view;
    }
    private Context ctx;

    //private EditRecipePresenter presenter = new EditRecipePresenter(view, ctx);
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.edit_recipe_dialog, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        view.showToast(recipeSaved);
                        view.exitFromActivity();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }

    @Override
    public void showToast(String message) {

    }
}
