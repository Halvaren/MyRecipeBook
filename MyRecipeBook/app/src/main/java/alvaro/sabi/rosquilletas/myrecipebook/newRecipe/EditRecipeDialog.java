package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import alvaro.sabi.rosquilletas.myrecipebook.Interfaces.ToastMessages;
import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class EditRecipeDialog extends DialogFragment
{
    private EditRecipeActivity view;

    public EditRecipeDialog (EditRecipeActivity view)
    {
        this.view = view;
    }

    //private EditRecipePresenter presenter = new EditRecipePresenter(view, ctx);
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.edit_recipe_dialog, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        view.showToast(ToastMessages.recipeSaved);
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
}
