package alvaro.sabi.rosquilletas.myrecipebook.interfaces;

public interface ToastMessages {

    //En esta interfaz quedan declarados todos los posibles mensajes que son necesarios para mostrar mediante un Toast

    String emptyRecipeList = "There is no saved recipes of that type";

    String requiresRecipeName = "You cannot save a recipe without a name";
    String requiresIngredients = "All ingredients have to be named";
    String requiresSteps = "All steps have to be named";

    String recipeSaved = "The recipe has been saved correctly";

    String youtubeVersion = "You don't have the suitable YouTube version for that function";

    String nowInDevelopment = "Now in Development";

    void showToast(String message);
}
