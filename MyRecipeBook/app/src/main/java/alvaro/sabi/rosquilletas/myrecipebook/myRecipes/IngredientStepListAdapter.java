package alvaro.sabi.rosquilletas.myrecipebook.myRecipes;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

public class IngredientStepListAdapter extends BaseAdapter {

    private EditRecipeActivity view;
    private ListView listView;
    private Context context;
    private ArrayList<String> ingredientStepList;

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

        convertView = LayoutInflater.from(context).inflate(R.layout.ingredient_step_list_adapter, null);

        TextView ingredientStepNumberText = convertView.findViewById(R.id.nIngredientStepText);
        Button deleteIngredientStepButton = convertView.findViewById(R.id.ingredientStepDeleteButton);
        EditText ingredientStepEditText = convertView.findViewById(R.id.ingredientStepEditText);

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

        ingredientStepEditText.setText(ingredientStepList.get(position));

        ingredientStepNumberText.setText((position + 1) + "-");

        deleteIngredientStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteIngredientStep(position);
            }
        });

        return convertView;
    }

    public void addIngredientStep(String content)
    {
        ingredientStepList.add(content);

        notifyDataSetChanged();
        view.changeHeightList(listView);
    }

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

    private void updateText(int position, String newText)
    {
        ingredientStepList.set(position, newText);
    }

    public ArrayList<String> getIngredientStepList()
    {
        return ingredientStepList;
    }

    public void setIngredientStepList(ArrayList<String> value)
    {
        for(int i = 0; i < value.size(); i++)
        {
            addIngredientStep(value.get(i));
        }
    }

    public boolean checkFilledIngredientsSteps()
    {
        for(int i = 0; i < ingredientStepList.size(); i++)
        {
            if(ingredientStepList.get(i).length() == 0) return false;
        }
        return true;
    }
}
