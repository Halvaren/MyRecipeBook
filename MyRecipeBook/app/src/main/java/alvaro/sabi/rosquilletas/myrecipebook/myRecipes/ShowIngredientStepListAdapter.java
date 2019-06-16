package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import alvaro.sabi.rosquilletas.myrecipebook.R;

public class ShowIngredientStepListAdapter extends BaseAdapter {

    ArrayList<String> list;
    Context context;

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

        convertView = LayoutInflater.from(context).inflate(R.layout.show_ingredient_step_list_adapter, null);

        TextView nameText = convertView.findViewById(R.id.showIngredientStepListAdapterNameText);
        nameText.setText((position + 1) + " - " + getItem(position));

        return convertView;
    }

    public void setList(ArrayList<String> value)
    {
        list = value;
        notifyDataSetChanged();
    }
}
