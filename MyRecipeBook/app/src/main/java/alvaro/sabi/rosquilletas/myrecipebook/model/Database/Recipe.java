package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Recipes", indices = @Index(value = {"name"}, unique = true))
//@Entity(foreignKeys =  @ForeignKey(entity = RecipeType.class, parentColumns = "name", childColumns = "type"))
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
