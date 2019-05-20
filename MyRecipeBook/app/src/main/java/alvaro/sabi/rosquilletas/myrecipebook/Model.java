package alvaro.sabi.rosquilletas.myrecipebook;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.android.volley.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

public class Model {

    private static Model instance; //Instancia estática que hace al modelo singleton
    private final Resources resources; //Será util para acceder a los txt que contienen las comunidades, provincias y pueblos en caso de que la database esté vacía

    private String[] recipeTypeNames;

    //Este constructor es privado ya que sigue el modelo singleton
    private Model(Context context){
        resources = context.getResources();
    }

    public static Model getInstance(Context context)
    {
        if(instance == null) //La primera vez que se llame a este método, se generará el modelo
        {
            instance = new Model(context);
        }
        return instance;
    }

    public void getButtonTexts(final Response.Listener<Void> response, boolean checkTableEmpty) {

        if(checkTableEmpty)
        {
            new AsyncTask<Void, Void, Integer>()
            {

                @Override
                protected Integer doInBackground(Void... voids) {
                    //return dao.numberOfRows();
                    return null;
                }

                protected void onPostExecute(Integer rows)
                {
                    super.onPostExecute(rows);
                    if(rows == 0)
                    {
                        initializeRecipeTypeTable(response);
                    }
                    else
                    {
                        getButtonTexts(response, false);
                    }
                }
            }.execute();
        }
        else
        {
            new AsyncTask<Void, Void, String[]>() {

                @Override
                protected String[] doInBackground(Void... voids) {
                    //return dao.loadAllRecipeTypeNames();
                    return new String[0];
                }

                protected void onPostExecute(String[] result)
                {
                    super.onPostExecute(result);
                    if(result == null || result.length == 0)
                    {
                        //errorResponse.onResponse(databaseError);
                    }
                    else
                    {
                        recipeTypeNames = result;
                        response.onResponse(null);
                    }
                }
            }.execute();
        }
    }

    private void initializeRecipeTypeTable(Response.Listener<Void> response)
    {
        ArrayList<String> recipeTypeNameList = new ArrayList<>();

        InputStream stream = resources.openRawResource(R.raw.recipe_types);
        Scanner scanner = new Scanner(stream);

        String line;
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            recipeTypeNameList.add(line);
        }
        scanner.close();

        String[] recipeTypeNameArray = new String[recipeTypeNameList.size()];

        recipeTypeNameArray = recipeTypeNameList.toArray(recipeTypeNameArray);

        insertRecipeTypeNames(recipeTypeNameArray, response);
    }

    private void insertRecipeTypeNames(final String[] recipeTypeNameArray, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>()
        {

            @Override
            protected Void doInBackground(Void... voids) {
                //dao.insertRecipeTypeNames(recipeTypeNameArray);
                return null;
            }

            protected void onPostExecute(Void aVoid)
            {
                super.onPostExecute(aVoid);
                getButtonTexts(response, false);
            }
        }.execute();
    }



    public String getButtonText(int id)
    {
        return recipeTypeNames[id];
    }

    public void getRecipeList(final Response.Listener<Recipe[]> response, final int recipeType) {

        new AsyncTask<Void, Void, Recipe[]>() {
            @Override
            protected Recipe[] doInBackground(Void... voids) {
                //return dao.getRecipeList(recipeType);
                return new Recipe[0];
            }

            protected void onPostExecute(Recipe[] result)
            {
                super.onPostExecute(result);

                response.onResponse(result);
            }
        }.execute();
    }
}
