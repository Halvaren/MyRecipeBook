package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "RecipeIngredients",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "name",
                childColumns = "recipeName"), @ForeignKey(entity = Ingredient.class,
        parentColumns = "name", childColumns = "recipeIngredient")},
        primaryKeys = {"recipeName", "recipeIngredient"})

public class RecipeIngredients {
    public RecipeIngredients () {}
    public String amount;
    public String recipeName;
    public String recipeIngredient;

    public String toString() {return amount;}

    public RecipeIngredients(String amount, String recipeName, String recipeIngredient) {
        this.amount = amount;
        this.recipeName = recipeName;
        this.recipeIngredient = recipeIngredient;
    }

}