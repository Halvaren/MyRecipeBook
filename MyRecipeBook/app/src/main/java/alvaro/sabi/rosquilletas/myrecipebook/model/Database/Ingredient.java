package alvaro.sabi.rosquilletas.myrecipebook.model.Database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*
    Clase que se corresponde con la tabla Ingredients de la base de datos
    Tiene una relación de (0,n) con la tabla de RecipeIngredients, mediante la clave ajena ingredientID que se corresponde con id en la tabla de Ingredients
    A su vez, la tabla tiene una relación de (0,n) con la tabla de Recipes. Esto, en el ámbito coloquial, significa que por un lado tenemos los ingredientes y por otro las recetas,
    y los ingredientes se vinculan con cierta receta a través de RecipeIngredients. A la hora de guardar una receta nueva en la base de datos o modificar una existente,
    primero se verificará si los ingredientes introducidos en usuario existen ya o no en la tabla de Ingredients. Si no, los introducirá. Posteriormente, actualizará la tabla
    RecipeIngredients con las relaciones entre la receta y sus ingredientes.

    Esta clase debe implementar la interface Parcelable para poder incluirse una lista de ingredientes en el paquete de estado y en algunas de las actividades y poder restaurar
    dicha lista al recargar el estado.
 */

@Entity(tableName =  "Ingredients")
public class Ingredient implements Parcelable {
    public Ingredient() {}

    @PrimaryKey(autoGenerate = true)
    public int id;
    @NonNull public String name;

    protected Ingredient(Parcel in) {
        id = in.readInt();
        name = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String toString() {return name;}

    public Ingredient(String type){
        this.name = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
    }
}
