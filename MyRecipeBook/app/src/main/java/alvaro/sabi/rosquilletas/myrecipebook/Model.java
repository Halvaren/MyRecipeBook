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

    //Listas constantes que contienen los nombres de los tipos de recetas y de las dificultades
    private final String[] RECIPE_TYPE_NAMES = { "Appetizer", "Starter", "Second Course", "Sauce", "Dessert", "Drink/Cocktail"};
    private final String[] DIFFICULTY_NAMES = {"Very easy", "Easy", "Normal", "Hard", "Very hard"};

    private static Model instance; //Instancia estática que hace al modelo singleton

    private RecipeDatabase database; //Referencia a la base de datos
    private RecipeDao dao; //Referencia al DAO

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

    //Devuelve el nombre de un tipo de receta dado su ID
    public String getRecipeTypeName(int id)
    {
        return RECIPE_TYPE_NAMES[id];
    }
    //Devuelve toda la lista de nombres de los tipos de recetas
    public String[] getRecipeTypeNames() { return RECIPE_TYPE_NAMES; }

    //Devuelve el nombre de una dificultad dado su ID
    public String getDifficultyName(int id) { return DIFFICULTY_NAMES[id]; }
    //Devuelve toda la lista de nombres de dificultades
    public String[] getDifficultyNames() { return DIFFICULTY_NAMES; }

    //Método que realiza en segundo plano la carga de todas las recetas de un tipo dado dicho tipo, desde la base de datos
    @SuppressLint("StaticFieldLeak")
    public void getRecipeList(final Response.Listener<ArrayList<Object>> response, final int recipeType) {

        new AsyncTask<Void, Void, ArrayList<Object>>() {
            @Override
            protected ArrayList<Object> doInBackground(Void... voids) {
                Recipe[] recipeList = dao.loadAllRecipesOfType(recipeType); //Asegurado que este valor empieza en 0
                int[] nIngredientList = new int[recipeList.length];
                int[] nStepList = new int[recipeList.length];

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

    //Método que genera la receta actual a partir de información que obtiene a través del presenter recibido
    public Recipe getCurrentRecipe(EditRecipePresenter presenter)
    {
        String recipeName = presenter.getRecipeName();
        int recipeTypeID = presenter.getRecipeTypeID();

        int numGuests = presenter.getNumGuests();
        float valuation = presenter.getValuation();

        int difficultyID = presenter.getDifficultyID();

        return new Recipe(recipeName, valuation, numGuests, difficultyID, recipeTypeID);
    }

    //Método que realiza en segundo plano la carga de una receta a partir de su ID desde la base de datos
    @SuppressLint("StaticFieldLeak")
    public void getRecipeByID(final int recipeID, final Response.Listener<Recipe> response)
    {
        new AsyncTask<Void, Void, Recipe>()
        {
            @Override
            protected Recipe doInBackground(Void... voids) {
                return dao.loadRecipeByID(recipeID);
            }

            protected void onPostExecute(Recipe result)
            {
                response.onResponse(result);
            }
        }.execute();
    }

    //Método que realiza en segundo plano la carga de la lista de ingredientes de una receta a partir de su ID desde la base de datos
    @SuppressLint("StaticFieldLeak")
    public void getIngredientListFromRecipe(final int recipeID, final Response.Listener<Ingredient[]> response)
    {
        new AsyncTask<Void, Void, Ingredient[]>() {
            @Override
            protected Ingredient[] doInBackground(Void... voids) {
                return  dao.loadAllIngredientsFromRecipe(recipeID);
            }

            protected void onPostExecute(Ingredient[] result)
            {
                response.onResponse(result);
            }
        }.execute();
    }

    //Método que realiza en segundo plano la carga de la lista de pasos a seguir de una receta a partir de su ID desde la base de datos
    @SuppressLint("StaticFieldLeak")
    public void getStepListFromRecipe(final int recipeID, final Response.Listener<StepToFollow[]> response)
    {
        new AsyncTask<Void, Void, StepToFollow[]>() {
            @Override
            protected StepToFollow[] doInBackground(Void... voids) {
                return dao.loadAllStepToFollowFromRecipe(recipeID);
            }

            protected void onPostExecute(StepToFollow[] result)
            {
                response.onResponse(result);
            }
        }.execute();
    }

    //Método que genera primeramente las listas de ingredientes (a partir de sus nombres), de pasos a seguir (a partir de sus descripciones) y de las relaciones entre receta e ingredientes
    //y posteriormente, en función del id de la receta recibido, determina si se debe crear una receta nueva o actualizar una existente
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

        if(recipe.id == -1) insertRecipe(recipe, ingredients, steps, recipeIngredients, response);
        else updateRecipe(recipe, ingredients, steps, recipeIngredients, response);
    }

    //Método que realiza en segundo plano la creación de una nueva receta
    @SuppressLint("StaticFieldLeak")
    private void insertRecipe(final Recipe recipe, final Ingredient[] ingredients, final StepToFollow[] steps, final RecipeIngredients[] recipeIngredients, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                //Se añade a la tabla Recipes la receta en cuestión poniéndole como ID el siguiente al último que hay guardado
                recipe.id = dao.lastID() + 1;
                dao.insertRecipe(recipe);

                //También deben añadirse aquellos ingredientes que no existieran ya en la tabla Ingredients
                for(int i = 0; i < ingredients.length; i++)
                {
                    //Para ello se comprueba si el número de filas en la tabla Ingredients que contienen el nombre de cierto ingrediente es igual a 0
                    if(dao.checkIngredientExists(ingredients[i].name) == 0)
                    {
                        dao.insertIngredient(ingredients[i]);
                    }
                }

                //Se le asigna a los pasos el id de la receta con el cual ha sido añadida, y son añadidas también a la tabla StepsToFollow
                for(int i = 0; i < steps.length; i++)
                {
                    steps[i].recipeID = recipe.id;
                }
                dao.insertSteps(steps);

                //Se hace lo mismo con los RecipeIngredients pero también con los IDs de los ingredientes, y se insertan en la table RecipeIngredients
                for(int i = 0; i < recipeIngredients.length; i++)
                {
                    recipeIngredients[i].recipeID = recipe.id;
                    recipeIngredients[i].ingredientID = dao.getIngredientIDFromName(ingredients[i].name);
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

    //Método que realiza en segundo plano la actualización de receta ya existente
    @SuppressLint("StaticFieldLeak")
    private void updateRecipe(final Recipe recipe, final Ingredient[] ingredients, final StepToFollow[] steps, final RecipeIngredients[] recipeIngredients, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {

                //Primeramente se comprueba si se ha introducido algún ingrediente que no esté guardado en la tabla Ingredients. Si no existe en la table el nombre, se añade
                for(int i = 0; i < ingredients.length; i++)
                {
                    if(dao.checkIngredientExists(ingredients[i].name) == 0)
                    {
                        dao.insertIngredient(ingredients[i]);
                    }
                }

                //Se obtienen todas relaciones entre receta e ingredientes de la receta dada guardadas en la base de datos
                RecipeIngredients[] currentRecipeIngredients = dao.loadAllRecipeIngredientsFromRecipe(recipe.id);

                //Se eliminan todas ellas
                for(int i = 0; i < currentRecipeIngredients.length; i++)
                {
                    dao.deleteRecipeIngredient(recipe.id, currentRecipeIngredients[i].ingredientID);
                }

                //Se insertan las nuevas a partir del id de la receta (que no cambia) y de los IDs de los ingredientes recibidos por parámetro
                for(int i = 0; i < ingredients.length; i++)
                {
                    dao.insertRecipeIngredients(new RecipeIngredients(recipe.id, dao.getIngredientIDFromName(ingredients[i].name)));
                }

                //Se obtienen los pasos a seguir de la receta dada guardados en la base de datos
                StepToFollow[] currentSteps = dao.loadAllStepToFollowFromRecipe(recipe.id);

                //Se eliminan todos ellos
                for(int i = 0; i < currentSteps.length; i++)
                {
                    dao.deleteStep(recipe.id, currentSteps[i].stepNum);
                }

                //Se insertan los nuevos pasos, asignándoles antes el id de la receta
                for(int i = 0; i < steps.length; i++)
                {
                    steps[i].recipeID = recipe.id;
                    dao.insertStep(steps[i]);
                }

                //Se actualiza la receta
                dao.updateRecipe(recipe.id, recipe.name, recipe.typeID, recipe.guests, recipe.valuation, recipe.difficultyID);

                return null;
            }

            protected void onPostExecute(Void result)
            {
                super.onPostExecute(result);

                response.onResponse(result);
            }
        }.execute();
    }

    //Método que realiza en segundo plano la eliminación de una receta
    @SuppressLint("StaticFieldLeak")
    public void deleteRecipe(final Recipe recipe, final Response.Listener<Void> response)
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                //Únicamente requiere eliminar la receta en sí porque
                // 1- Sus pasos y relaciones con ingredientes se eliminan en cascada
                // 2- Los ingredientes asociados no cabe eliminarlos porque podría ser que se emplearan en otra receta
                dao.deleteRecipe(recipe.id);

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
