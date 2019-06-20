package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.RESTRICT;
import static androidx.room.ForeignKey.SET_NULL;

/*
    Clase que se corresponde con la tabla RecipeIngredients de la base de datos
    Sirve para establecer la relación entre una receta y sus ingredientes, de manera que cada fila contiene el ID de la receta y el ID de un ingrediente.
    Para cada ID de receta almacenado, hay una fila por cada ingrediente que tiene asociado.

    Si se elimina una receta, las filas de RecipeIngredients que estuvieran relacionadas con esa receta son eliminadas. En cambio, si se intenta eliminar un ingrediente con
    alguna fila de RecipeIngredients relacionada con él, se impedirá dicho borrado.
 */

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