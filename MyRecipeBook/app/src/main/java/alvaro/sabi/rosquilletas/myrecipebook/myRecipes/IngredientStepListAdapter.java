package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.R;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipeActivity;

/*
    Adapter para las ListViews de ingredientes y pasos de la actividad EditRecipe
 */

public class IngredientStepListAdapter extends BaseAdapter {

    private EditRecipeActivity view; //Referencia a la actividad necesaria para la llamada de métodos concretos al recibir input a través de los listeners de los botones
    private ListView listView; //Referencia a la ListView a la que se le ha asignado el adapter para poder reajustar la altura de la misma correctamente
    private Context context; //Referencia al context para poder aplicar el layout correcto al adapter
    private ArrayList<String> ingredientStepList; //Lista de Strings que almacena los textos correspondientes a los nombres de los ingredientes o a la descripcion de los pasos

    public IngredientStepListAdapter(EditRecipeActivity view, Context context, ListView listView)
    {
        this.view = view;
        this.listView = listView;
        this.context = context;

        ingredientStepList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        if(ingredientStepList != null) return ingredientStepList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return ingredientStepList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.ingredient_step_list_adapter, null); //Se aplica el layout personalizado al elemento de la ListView

        //Se obtienen los elementos del layout
        TextView ingredientStepNumberText = convertView.findViewById(R.id.nIngredientStepText);
        Button deleteIngredientStepButton = convertView.findViewById(R.id.ingredientStepDeleteButton);
        EditText ingredientStepEditText = convertView.findViewById(R.id.ingredientStepEditText);

        //Se le añade un listener al EditText para detectar cuando el texto cambia y poder modificar el elemento de la lista correspondiente
        ingredientStepEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updateText(position, String.valueOf(s));
            }
        });

        //Se cambia el texto del EditText para que muestre el texto guardado en la lista correspondiente a su posición. Es muy útil para que la ListView se actualice
        //correctamente cuando se elimina un elemento
        ingredientStepEditText.setText(ingredientStepList.get(position));

        //Se cambia el texto del NumberText para que muestre el número del elemento correcto
        ingredientStepNumberText.setText((position + 1) + "-");

        //Se le asigna un OnClickListener al botón de eliminar para detectar cuando se pulsa
        deleteIngredientStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIngredientStep(position);
            }
        });

        return convertView;
    }

    //Añade un nuevo elemento a la lista de Strings, indica que se ha cambiado la información del ListView y se llama a modificar el tamaño de la ListView
    public void addIngredientStep(String content)
    {
        ingredientStepList.add(content);

        notifyDataSetChanged();
        view.changeHeightList(listView);
    }

    //Elimina el elemento de la lista indica si el número de elemntos restantes el mayor a 1 (si no, al elemento restante le reseteará el texto), indica el cambio de información
    //y llama a modificar el tamaño de la ListView
    private void deleteIngredientStep(int position)
    {
        if(getCount() > 1)
        {
            ingredientStepList.remove(position);

            notifyDataSetChanged();
            view.changeHeightList(listView);
        }
        else
        {
            ingredientStepList.set(0, "");
            notifyDataSetChanged();
        }
    }

    //Actualiza el texto contenido en la lista en la posición dada. Es llamado desde el listener que detecta los cambios en el EditText
    private void updateText(int position, String newText)
    {
        ingredientStepList.set(position, newText);
    }

    //Devuelve la lista de textos
    public ArrayList<String> getIngredientStepList()
    {
        return ingredientStepList;
    }

    //Inserta mediante el método addIngredientStep cada uno de los elementos de la lista dada
    public void setIngredientStepList(ArrayList<String> value)
    {
        for(int i = 0; i < value.size(); i++)
        {
            addIngredientStep(value.get(i));
        }
    }

    //Devuelve true si alguno de los campos de texto de los elementos de la ListView esta vacío (es decir, su correspondiente elemento de la lista es una cadena de longitud 0)
    //Devuelve false si todos los campos contienen algo de texto
    public boolean checkFilledIngredientsSteps()
    {
        for(int i = 0; i < ingredientStepList.size(); i++)
        {
            if(ingredientStepList.get(i).length() == 0) return true;
        }
        return false;
    }
}
