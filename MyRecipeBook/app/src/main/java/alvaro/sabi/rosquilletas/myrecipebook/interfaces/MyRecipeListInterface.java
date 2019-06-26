package alvaro.sabi.rosquilletas.myrecipebook.interfaces;

import alvaro.sabi.rosquilletas.myrecipebook.database.Recipe;

public interface MyRecipeListInterface {

    String getRecipeTypeName(int recipeType);
    String getDifficultyName(int difficultyID);
    void requestRecipeList(int recipeType);
    void setRecipeList(Recipe[] recipeList, int[] ingredients, int[] steps);
    void editRecipe(Recipe recipe);
    void deleteRecipe(Recipe recipe, int i);
    void searchOnYoutube(Recipe recipe);
    void showRecipe(Recipe recipe);
}
