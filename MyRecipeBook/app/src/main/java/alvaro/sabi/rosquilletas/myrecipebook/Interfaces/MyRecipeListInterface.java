package alvaro.sabi.rosquilletas.myrecipebook.Interfaces;

import alvaro.sabi.rosquilletas.myrecipebook.model.Database.Recipe;

public interface MyRecipeListInterface {

    String getRecipeTypeName(int recipeType);
    String getDifficultyName(int difficultyID);
    void requestRecipeList(int recipeType);
    void setRecipeList(Recipe[] recipeList, int[] ingredients, int[] steps);
    void editRecipe(Recipe recipe);
    void deleteRecipe(Recipe recipe);
    void searchOnYoutube(Recipe recipe);
    void showRecipe(Recipe recipe);
}
