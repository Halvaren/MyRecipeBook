package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName =  "RecipeIngredients",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "id",
                childColumns = "recipeID", onDelete = CASCADE), @ForeignKey(entity = Ingredient.class,
        parentColumns = "id", childColumns = "ingredientID", onDelete = RESTRICT)},
        primaryKeys = {"recipeID", "ingredientID"})

public class RecipeIngredients {
    public RecipeIngredients () {}
    public String amount;
    @NonNull public int recipeID;
    @NonNull public int ingredientID;

    public RecipeIngredients(int recipeID, int ingredientID) {
        this.recipeID = recipeID;
        this.ingredientID = ingredientID;
    }

    public String toString() {return amount;}

}