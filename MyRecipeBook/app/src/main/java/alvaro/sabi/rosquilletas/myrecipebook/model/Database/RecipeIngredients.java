package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "RecipeIngredients",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "name",
                childColumns = "recipeName"), @ForeignKey(entity = Ingredient.class,
        parentColumns = "name", childColumns = "ingredientName")},
        primaryKeys = {"recipeName", "ingredientName"})

public class RecipeIngredients {
    public RecipeIngredients () {}
    public String amount;
    @NonNull public String recipeName;
    @NonNull public String ingredientName;

    public String toString() {return amount;}

    public RecipeIngredients(String amount, String recipeName, String ingredientName) {
        this.amount = amount;
        this.recipeName = recipeName;
        this.ingredientName = ingredientName;
    }

}