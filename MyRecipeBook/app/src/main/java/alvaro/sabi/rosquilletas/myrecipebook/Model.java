package alvaro.sabi.rosquilletas.myrecipebook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.Response;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Ingredient;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeDao;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeDatabase;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.RecipeIngredients;
import alvaro.sabi.rosquilletas.myrecipebook.model.Database.StepToFollow;
import alvaro.sabi.rosquilletas.myrecipebook.newRecipe.EditRecipePresenter;
import androidx.room.Room;

public class Model {

    private final String[] RECIPE_TYPE_NAMES = { "Appetizer", "Starter", "Second Course", "Sauce", "Dessert", "Drink/Cocktail"};
    private final String[] DIFFICULTY_NAMES = {"Very easy", "Easy", "Normal", "Hard", "Very hard"};

    private static Model instance; //Instancia estática que hace al modelo singleton

    private RecipeDatabase database;
    private RecipeDao dao;

    private Recipe recipe;

    //Este constructor es privado ya que sigue el modelo singleton
    private Model(Context context)
    {
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

    public String getRecipeTypeName(int id)
    {
        return RECIPE_TYPE_NAMES[id];
    }
    public String[] getRecipeTypeNames() { return RECIPE_TYPE_NAMES; }

    public String getDifficultyName(int id) { return DIFFICULTY_NAMES[id]; }
    public String[] getDifficultyNames() { return DIFFICULTY_NAMES; }

    @SuppressLint("StaticFieldLeak")
    public void getRecipeList(final Response.Listener<ArrayList<Object>> response, final int recipeType) {

        new AsyncTask<Void, Void, ArrayList<Object>>() {
            @Override
            protected ArrayList<Object> doInBackground(Void... voids) {
                Recipe[] recipeList = dao.loadAllRecipesOfType(recipeType); //Asegurado que este valor empieza en 0
                Integer[] nIngredientList = new Integer[recipeList.length];
                Integer[] nStepList = new Integer[recipeList.length];

                for(int i = 0; i < recipeList.length; i++)
                {
                    nIngredientList[i] = dao.nIngredientsOfRecipe(recipeList[i].id);
                    nStepList[i] = dao.nStepsOfRecipe(recipeList[i].id);
                }

                ArrayList<Object> result = new ArrayList<>();
                result.add(0, recipeList);
                result.add(1, nIngredientList);
                result.add(2, nStepList);

                return result;
            }

            protected void onPostExecute(ArrayList<Object> result)
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

        int numGuests = presenter.getNumGuests();
        float valuation = presenter.getValuation();

        int difficultyID = presenter.getDifficultyID();

        recipe = new Recipe(recipeName, valuation, numGuests, difficultyID, recipeTypeID);
    }

    public void setCurrentRecipe(EditRecipePresenter presenter, Recipe currentRecipe)
    {
        recipe = currentRecipe;

        presenter.setRecipeName(recipe.name);
        presenter.setRecipeTypeID(recipe.typeID);
        presenter.setNumGuests(recipe.guests);
        presenter.setValuation(recipe.valuation);
        presenter.setDifficultyID(recipe.difficultyID);
    }

    public void getIngredientListFromRecipe(final Recipe recipe, final Response.Listener<Ingredient[]> response)
    {
        Log.d("id", recipe.id + "");
        new AsyncTask<Void, Void, Ingredient[]>() {
            @Override
            protected Ingredient[] doInBackground(Void... voids) {
                Ingredient[] result = dao.loadAllIngredientsFromRecipe(recipe.id);

                Log.d("hola", result.length + "");

                return  result;
            }

            protected void onPostExecute(Ingredient[] result)
            {
                response.onResponse(result);
            }
        }.execute();
    }

    public void getStepListFromRecipe(final Recipe recipe, final Response.Listener<StepToFollow[]> response)
    {
        new AsyncTask<Void, Void, StepToFollow[]>() {
            @Override
            protected StepToFollow[] doInBackground(Void... voids) {
                return dao.loadAllStepToFollowFromRecipe(recipe.id);
            }

            protected void onPostExecute(StepToFollow[] result)
            {
                response.onResponse(result);
            }
        }.execute();
    }

    public void createRecipe(final Recipe recipe, ArrayList<String> ingredientNameList, ArrayList<String> stepNameList, final Response.Listener<Void> response)
    {
        final Ingredient[] ingredients = new Ingredient[ingredientNameList.size()];
        final StepToFollow[] steps = new StepToFollow[stepNameList.size()];
        final RecipeIngredients[] recipeIngredients = new RecipeIngredients[ingredientNameList.size()];

        for(int i = 0; i < ingredients.length; i++)
        {
            ingredients[i] = new Ingredient(ingredientNameList.get(i));
        }
        for(int i = 0; i < steps.length; i++)
        {
            steps[i] = new StepToFollow(i, stepNameList.get(i));
        }
        for(int i = 0; i < recipeIngredients.length; i++)
        {
            recipeIngredients[i] = new RecipeIngredients();
        }

        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return dao.checkRecipeExists(recipe.name);
            }

            protected void onPostExecute(Integer rows)
            {
                if(rows == 0) insertRecipe(recipe, ingredients, steps, recipeIngredients, response);
                else updateRecipe(recipe, ingredients, steps, recipeIngredients, response);
            }
        }.execute();
    }

    public void insertRecipe(final Recipe recipe, final Ingredient[] ingredients, final StepToFollow[] steps, final RecipeIngredients[] recipeIngredients, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                recipe.id = (int) dao.insertRecipe(recipe);

                long[] ingredientsIDs = dao.insertIngredients(ingredients);

                for(int i = 0; i < steps.length; i++)
                {
                    steps[i].recipeID = recipe.id;
                }
                dao.insertSteps(steps);

                for(int i = 0; i < recipeIngredients.length; i++)
                {
                    recipeIngredients[i].recipeID = recipe.id;
                    recipeIngredients[i].ingredientID = (int) ingredientsIDs[i];
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

    public void updateRecipe(final Recipe recipe, final Ingredient[] ingredients, final StepToFollow[] steps, final RecipeIngredients[] recipeIngredients, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                Ingredient[] oldIngredients = dao.loadAllIngredientsFromRecipe(recipe.id);
                StepToFollow[] oldSteps = dao.loadAllStepToFollowFromRecipe(recipe.id);
                RecipeIngredients[] oldRecipeIngredients = dao.loadAllRecipeIngredientsFromRecipe(recipe.id);

                dao.deleteRecipeIngredients(oldRecipeIngredients);
                dao.deleteSteps(oldSteps);
                dao.deleteIngredients(oldIngredients);

                dao.updateRecipe(recipe);

                long[] ingredientIDs = dao.insertIngredients(ingredients);

                for(int i = 0; i < steps.length; i++)
                {
                    steps[i].recipeID = recipe.id;
                }
                dao.insertSteps(steps);

                for(int i = 0; i < recipeIngredients.length; i++)
                {
                    recipeIngredients[i].recipeID = recipe.id;
                    recipeIngredients[i].ingredientID = (int) ingredientIDs[i];
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
                Ingredient[] oldIngredients = dao.loadAllIngredientsFromRecipe(recipe.id);
                StepToFollow[] oldSteps = dao.loadAllStepToFollowFromRecipe(recipe.id);
                RecipeIngredients[] oldRecipeIngredients = dao.loadAllRecipeIngredientsFromRecipe(recipe.id);

                dao.deleteRecipeIngredients(oldRecipeIngredients);
                dao.deleteSteps(oldSteps);
                dao.deleteIngredients(oldIngredients);
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
