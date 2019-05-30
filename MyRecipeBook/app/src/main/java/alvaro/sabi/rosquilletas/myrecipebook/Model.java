package alvaro.sabi.rosquilletas.myrecipebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.android.volley.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeDao;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeDatabase;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeType;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.StepToFollow;
import androidx.room.Room;

public class Model {

    private static Model instance; //Instancia estática que hace al modelo singleton
    private final Resources resources; //Será util para acceder a los txt que contienen las comunidades, provincias y pueblos en caso de que la database esté vacía

    private String[] recipeTypeNames;

    private RecipeDatabase database;
    private RecipeDao dao;

    //Este constructor es privado ya que sigue el modelo singleton
    private Model(Context context)
    {
        resources = context.getResources();
        database = Room.databaseBuilder(context, RecipeDatabase.class, "recipe-database").build();
        dao = database.recipeDao();
    }

    public static Model getInstance(Context context)
    {
        if(instance == null) //La primera vez que se llame a este método, se generará el modelo
        {
            instance = new Model(context);
        }
        return instance;
    }

    @SuppressLint("StaticFieldLeak")
    public void getButtonTexts(final Response.Listener<Void> response, boolean checkTableEmpty) {

        if(checkTableEmpty)
        {
            new AsyncTask<Void, Void, Integer>()
            {

                @Override
                protected Integer doInBackground(Void... voids) {
                    return dao.recipeTypeNamesNumberOfRows();
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
                    return dao.loadAllRecipeTypeNames();
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
        ArrayList<RecipeType> recipeTypeList = new ArrayList<>();

        InputStream stream = resources.openRawResource(R.raw.recipe_types);
        Scanner scanner = new Scanner(stream);

        String line;
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            recipeTypeList.add(new RecipeType(line));
        }
        scanner.close();

        RecipeType[] recipeTypeArray = new RecipeType[recipeTypeList.size()];

        recipeTypeArray = recipeTypeList.toArray(recipeTypeArray);

        insertRecipeTypeNames(recipeTypeArray, response);
    }

    @SuppressLint("StaticFieldLeak")
    private void insertRecipeTypeNames(final RecipeType[] recipeTypeNameArray, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>()
        {

            @Override
            protected Void doInBackground(Void... voids) {
                dao.insertRecipeTypeNames(recipeTypeNameArray);
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

    @SuppressLint("StaticFieldLeak")
    public void getRecipeList(final Response.Listener<Recipe[]> response, final int recipeType) {

        new AsyncTask<Void, Void, Recipe[]>() {
            @Override
            protected Recipe[] doInBackground(Void... voids) {
                Recipe[] recipeList = dao.loadAllRecipesOfType(recipeTypeNames[recipeType]);

                for(int i = 0; i < recipeList.length; i++)
                {
                    Ingredient[] ingredientArray = dao.loadAllIngredientsFromRecipe(recipeList[i].name);
                    ArrayList<Ingredient> ingredientList = new ArrayList<>(Arrays.asList(ingredientArray));
                    recipeList[i].setIngredientsList(ingredientList);

                    StepToFollow[] stepArray = dao.loadAllStepToFollowFromRecipe(recipeList[i].name);
                    ArrayList<StepToFollow> stepList = new ArrayList<>(Arrays.asList(stepArray));
                    recipeList[i].setStepsList(stepList);
                }

                return recipeList;
            }

            protected void onPostExecute(Recipe[] result)
            {
                super.onPostExecute(result);

                response.onResponse(result);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    public void deleteRecipe(final Recipe recipe, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.deleteRecipe(recipe);

                return null;
            }

            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);

                response.onResponse(result);
            }
        }.execute();
    }
}
