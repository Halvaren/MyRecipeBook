package alvaro.sabi.rosquilletas.myrecipebook.newRecipe;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class ReturnRecipeDialog extends DialogFragment {

    private EditRecipeActivity view;

    public ReturnRecipeDialog (EditRecipeActivity view)
    {
        this.view = view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.return_recipe_dialog, null))
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       view.finish();
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
