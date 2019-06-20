package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;
import static androidx.room.ForeignKey.SET_NULL;

/*
    Clase que se corresponde con la tabla StepsToFollow de la base de datos
    La información que contiene un paso a seguir es la siguiente: recipeID (ID de la receta que contiene dicho paso), stepNum (numeración del paso dentro de la receta a la
    que pertenece) y description (descripción del paso en cuestión).
    Una vez se elimina una receta, cada fila de un paso a seguir que estuviera relacionado con dicha receta, también se elimina.
 */

@Entity(tableName =  "StepsToFollow",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "id",
                childColumns = "recipeID", onDelete = CASCADE)},
        primaryKeys = {"recipeID", "stepNum"})
public class StepToFollow implements Parcelable {
    public StepToFollow() {}
    public int stepNum;
    public int recipeID;
    public String description;

    public StepToFollow(int stepNum, String description) {
        this.stepNum = stepNum;
        this.description = description;
    }

    protected StepToFollow(Parcel in) {
        stepNum = in.readInt();
        recipeID = in.readInt();
        description = in.readString();
    }

    public static final Creator<StepToFollow> CREATOR = new Creator<StepToFollow>() {
        @Override
        public StepToFollow createFromParcel(Parcel in) {
            return new StepToFollow(in);
        }

        @Override
        public StepToFollow[] newArray(int size) {
            return new StepToFollow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(stepNum);
        dest.writeInt(recipeID);
        dest.writeString(description);
    }
}
