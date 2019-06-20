package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import android.os.Parcel;
import android.os.Parcelable;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
    Clase que se corresponde con la tabla Recipes de la base de datos
    Contiene la siguiente información sobre la receta: ID con el que se guarda en la base de datos, nombre de la receta (puede repetirse), tipo de receta (se identifica
    mediante un id)valoración del usuario (se expresará por pantalla mediante una RatingBar), número de invitados (de 1 a 30), dificultad (se identifica mediante un id).

    La tabla StepsToFollow se relaciona con la tabla Recipes mediante la clave ajena recipeID, al igual que la tabla RecipeIngredients. Desde un punto de vista más coloquial,
    una paso a seguir almacenado en la tabla StepsToFollow se relacionará de manera única con una receta, por lo que si existen dos pasos con la misma descripción, pero para dos
    recetas distintas, habrán dos filas distintas en la tabla StepsToFollow, una para cada paso a seguir.

    La clase debe ser Parcelable para poder traspasar una receta de una actividad a otra o para poder restaurar la información de la receta al cambiar el estado de una actividad.
 */

@Entity(tableName = "Recipes")
public class Recipe implements Parcelable {
    public Recipe () {}
    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull public String name;
    public float valuation;
    public int guests;
    public int difficultyID;

    @ColumnInfo (name = "typeID")
    public int typeID;

    public Recipe(String name, float valuation, int guests, int difficultyID, int typeID) {
        this.name = name;
        this.valuation = valuation;
        this.guests = guests;
        this.difficultyID = difficultyID;
        this.typeID = typeID;
    }


    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        valuation = in.readFloat();
        guests = in.readInt();
        difficultyID = in.readInt();
        typeID = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeFloat(valuation);
        dest.writeInt(guests);
        dest.writeInt(difficultyID);
        dest.writeInt(typeID);
    }
}
