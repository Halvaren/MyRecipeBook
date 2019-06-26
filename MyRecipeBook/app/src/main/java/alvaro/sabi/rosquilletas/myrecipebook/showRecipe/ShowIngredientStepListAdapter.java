package alvaro.sabi.rosquilletas.myrecipebook.showRecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.R;

/*
    Adapter para las ListViews de ingredientes y pasos de la actividad ShowRecipe
 */

public class ShowIngredientStepListAdapter extends BaseAdapter {

    ArrayList<String> list; //Lista que contiene la información sobre lo que debe mostrar la ListView
    Context context; //Contexto para poder utilizar una layout personalizada

    public ShowIngredientStepListAdapter(Context context)
    {
        this.context = context;
        list = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Se aplica la layout personalizada a cada elemento de la ListView
        convertView = LayoutInflater.from(context).inflate(R.layout.show_ingredient_step_list_adapter, null);

        //Se obtiene el elemento de la layout que contiene el texto a mostrar
        TextView nameText = convertView.findViewById(R.id.showIngredientStepListAdapterNameText);
        //Y se modifica su contenido para que muestre la posición más 1 y el texto determinado por la lista de información
        nameText.setText((position + 1) + " - " + getItem(position));

        return convertView;
    }

    //Método que asigna una nueva lista de información y que notifica que se ha producido un cambio en dicha información para que actualice la ListView
    public void setList(ArrayList<String> value)
    {
        list = value;
        notifyDataSetChanged();
    }
}
