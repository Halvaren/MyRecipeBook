package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "RecipeIngredients",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "id",
                childColumns = "recipeID"), @ForeignKey(entity = Ingredient.class,
        parentColumns = "id", childColumns = "ingredientID")},
        primaryKeys = {"recipeID", "ingredientID"})

public class RecipeIngredients {
    public RecipeIngredients () {}
    public String amount;
    @NonNull public int recipeID;
    @NonNull public int ingredientID;

    public String toString() {return amount;}

}