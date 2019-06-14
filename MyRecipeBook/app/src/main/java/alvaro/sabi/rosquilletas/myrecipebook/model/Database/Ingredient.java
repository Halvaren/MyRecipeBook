package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
