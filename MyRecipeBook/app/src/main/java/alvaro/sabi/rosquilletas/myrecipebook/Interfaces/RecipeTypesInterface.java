package alvaro.sabi.rosquilletas.myrecipebook.Interfaces;

import android.view.View;

public interface RecipeTypesInterface {

    void getButtonText(int id);

    void onClickAppetizer(View view);
    void onClickStarter(View view);
    void onClickSecondCourse(View view);
    void onClickSauce(View view);
    void onClickDessert(View view);
    void onClickDrink(View view);
}
