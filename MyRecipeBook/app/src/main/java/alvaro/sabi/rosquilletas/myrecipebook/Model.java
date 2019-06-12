package alvaro.sabi.rosquilletas.myrecipebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeDao;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeDatabase;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeIngredients;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeType;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.StepToFollow;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipePresenter;
import androidx.room.Room;

public class Model {

    private final String[] DIFFICULTY_NAMES = {"Very easy", "Easy", "Normal", "Hard", "Very hard"};

    private static Model instance; //Instancia estática que hace al modelo singleton
    private final Resources resources; //Será util para acceder a los txt que contienen las comunidades, provincias y pueblos en caso de que la database esté vacía

    private String[] recipeTypeNames;

    private RecipeDatabase database;
    private RecipeDao dao;

    private Recipe recipe;

    //Este constructor es privado ya que sigue el modelo singleton
    private Model(Context context)
    {
        resources = context.getResources();
        database = Room.databaseBuilder(context, RecipeDatabase.class, "recipe-database").fallbackToDestructiveMigration().build();
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
    public void prepareRecipeTypeNames(final Response.Listener<Void> response, boolean checkTableEmpty) {

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
                        prepareRecipeTypeNames(response, false);
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

        int id = 0;
        String line;
        while(scanner.hasNextLine())
        {
            line = scanner.nextLine();
            recipeTypeList.add(new RecipeType(id, line));
            id++;
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
                prepareRecipeTypeNames(response, false);
            }
        }.execute();
    }

    public String getRecipeTypeName(int id)
    {
        return recipeTypeNames[id];
    }
    public String[] getRecipeTypeNames() { return recipeTypeNames; }

    public String[] getDifficultyNames() { return DIFFICULTY_NAMES; }

    @SuppressLint("StaticFieldLeak")
    public void getRecipeList(final Response.Listener<Recipe[]> response, final int recipeType) {

        new AsyncTask<Void, Void, Recipe[]>() {
            @Override
            protected Recipe[] doInBackground(Void... voids) {
                Recipe[] recipeList = dao.loadAllRecipesOfType(recipeType); //Asegurado que este valor empieza en 0

                for(int i = 0; i < recipeList.length; i++)
                {
                    Ingredient[] ingredientArray = dao.loadAllIngredientsFromRecipe(recipeList[i].name);
                    ArrayList<Ingredient> ingredientList = new ArrayList<>(Arrays.asList(ingredientArray));
                    recipeList[i].setIngredientList(ingredientList);

                    StepToFollow[] stepArray = dao.loadAllStepToFollowFromRecipe(recipeList[i].name);
                    ArrayList<StepToFollow> stepList = new ArrayList<>(Arrays.asList(stepArray));
                    recipeList[i].setStepList(stepList);
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

    public Recipe getCurrentRecipe(EditRecipePresenter presenter)
    {
        generateCurrentRecipe(presenter);

        return recipe;
    }

    private void generateCurrentRecipe(EditRecipePresenter presenter)
    {
        String recipeName = presenter.getRecipeName();
        int recipeTypeID = presenter.getRecipeTypeID();
        String recipeTypeName = presenter.getRecipeTypeName();

        int numGuests = presenter.getNumGuests();
        float valuation = presenter.getValuation();

        int difficultyID = presenter.getDifficultyID();
        String difficultyName = presenter.getDifficultyName();

        ArrayList<String> ingredientNamesList = presenter.getIngredientList();
        ArrayList<String> stepNamesList = presenter.getStepList();

        ArrayList<Ingredient> ingredientList = new ArrayList<>();
        ArrayList<StepToFollow> stepList = new ArrayList<>();

        for(int i = 0; i < ingredientNamesList.size(); i++)
        {
            ingredientList.add(new Ingredient(ingredientNamesList.get(i)));
        }

        for(int i = 0; i < stepNamesList.size(); i++)
        {
            stepList.add(new StepToFollow(i, recipeName, stepNamesList.get(i)));
        }

        Recipe currentRecipe = new Recipe(recipeName, valuation, numGuests, difficultyID, recipeTypeID);

        currentRecipe.setIngredientList(ingredientList);
        currentRecipe.setStepList(stepList);

        currentRecipe.typeName = recipeTypeName;
        currentRecipe.difficultyName = difficultyName;

        recipe = currentRecipe;
    }

    public void setCurrentRecipe(EditRecipePresenter presenter, Recipe currentRecipe)
    {
        recipe = currentRecipe;

        presenter.setRecipeName(recipe.name);
        presenter.setRecipeTypeID(recipe.typeID);
        presenter.setNumGuests(recipe.guests);
        presenter.setValuation(recipe.valuation);
        presenter.setDifficultyID(recipe.difficultyID);

        ArrayList<String> ingredientNamesList = new ArrayList<>();
        ArrayList<String> stepNamesList = new ArrayList<>();

        ArrayList<Ingredient> ingredientList = recipe.getIngredientList();
        ArrayList<StepToFollow> stepList = recipe.getStepList();

        for(int i = 0; i < ingredientList.size(); i++)
        {
            ingredientNamesList.add(ingredientList.get(i).name);
        }
        for(int i = 0; i < stepList.size(); i++)
        {
            stepNamesList.add(stepList.get(i).description);
        }

        presenter.setIngredientList(ingredientNamesList);
        presenter.setStepList(stepNamesList);
    }

    public void createRecipe(final Recipe recipe, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return dao.checkRecipeExists(recipe.name);
            }

            protected void onPostExecute(Integer rows)
            {
                if(rows == 0) insertRecipe(recipe, response);
                else updateRecipe(recipe, response);
            }
        }.execute();
    }

    public void insertRecipe(final Recipe recipe, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                dao.insertRecipe(recipe);

                Ingredient[] ingredients = new Ingredient[recipe.getIngredientList().size()];
                for(int i = 0; i < ingredients.length; i++)
                {
                    ingredients[i] = recipe.getIngredientList().get(i);
                }

                dao.insertIngredients(ingredients);

                StepToFollow[] steps = new StepToFollow[recipe.getStepList().size()];
                for(int i = 0; i < steps.length; i++)
                {
                    steps[i] = recipe.getStepList().get(i);
                }
                dao.insertSteps(steps);

                RecipeIngredients[] recipeIngredients = new RecipeIngredients[ingredients.length];
                for(int i = 0; i < recipeIngredients.length; i++)
                {
                    recipeIngredients[i] = new RecipeIngredients(recipe.name, ingredients[i].name);
                }
                dao.insertRecipeIngredients(recipeIngredients);

                return null;
            }

            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);

                response.onResponse(result);
            }
        }.execute();
    }

    public void updateRecipe(final Recipe recipe, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                Ingredient[] ingredients = dao.loadAllIngredientsFromRecipe(recipe.name);
                StepToFollow[] steps = dao.loadAllStepToFollowFromRecipe(recipe.name);
                RecipeIngredients[] recipeIngredients = dao.loadAllRecipeIngredientsFromRecipe(recipe.name);

                dao.deleteRecipeIngredients(recipeIngredients);
                dao.deleteSteps(steps);
                dao.deleteIngredients(ingredients);

                dao.updateRecipe(recipe);

                ingredients = new Ingredient[recipe.getIngredientList().size()];
                for(int i = 0; i < ingredients.length; i++)
                {
                    ingredients[i] = recipe.getIngredientList().get(i);
                }

                dao.insertIngredients(ingredients);

                steps = new StepToFollow[recipe.getStepList().size()];
                for(int i = 0; i < steps.length; i++)
                {
                    steps[i] = recipe.getStepList().get(i);
                }
                dao.insertSteps(steps);

                recipeIngredients = new RecipeIngredients[ingredients.length];
                for(int i = 0; i < recipeIngredients.length; i++)
                {
                    recipeIngredients[i] = new RecipeIngredients(recipe.name, ingredients[i].name);
                }
                dao.insertRecipeIngredients(recipeIngredients);

                return null;
            }

            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);

                response.onResponse(result);
            }
        }.execute();
    }

    public void deleteRecipe(final Recipe recipe, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                Ingredient[] ingredients = new Ingredient[recipe.getIngredientList().size()];
                for(int i = 0; i < ingredients.length; i++)
                {
                    ingredients[i] = recipe.getIngredientList().get(i);
                }

                StepToFollow[] steps = new StepToFollow[recipe.getStepList().size()];
                for(int i = 0; i < steps.length; i++)
                {
                    steps[i] = recipe.getStepList().get(i);
                }

                RecipeIngredients[] recipeIngredients = new RecipeIngredients[ingredients.length];
                for(int i = 0; i < recipeIngredients.length; i++)
                {
                    recipeIngredients[i] = new RecipeIngredients(recipe.name, ingredients[i].name);
                }

                dao.deleteRecipeIngredients(recipeIngredients);
                dao.deleteSteps(steps);
                dao.deleteIngredients(ingredients);
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
