package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Recipes")
//@Entity(foreignKeys =  @ForeignKey(entity = RecipeType.class, parentColumns = "name", childColumns = "type"))
public class Recipe implements Parcelable {
    public Recipe () {}
    @PrimaryKey @NonNull public String name;
    public float valuation;
    public int guests;
    public int difficultyID;

    @ForeignKey(entity = RecipeType.class, parentColumns = "ID", childColumns = "typeID")
    @ColumnInfo (name = "typeID")
    public int typeID;

    @Ignore
    private ArrayList<StepToFollow> stepList;
    @Ignore
    private ArrayList<Ingredient> ingredientList;

    @Ignore
    public String typeName;
    @Ignore
    public String difficultyName;

    public Recipe(String name, float valuation, int guests, int difficultyID, int typeID) {
        this.name = name;
        this.valuation = valuation;
        this.guests = guests;
        this.difficultyID = difficultyID;
        this.typeID = typeID;
    }

    public ArrayList<StepToFollow> getStepList() { return stepList; }
    public ArrayList<Ingredient> getIngredientList() { return ingredientList; }

    public void setStepList(ArrayList<StepToFollow> value) { stepList = value; }
    public void setIngredientList(ArrayList<Ingredient> value) { ingredientList = value; }


    protected Recipe(Parcel in) {
        name = in.readString();
        valuation = in.readFloat();
        guests = in.readInt();
        difficultyID = in.readInt();
        typeID = in.readInt();
        ingredientList = in.readArrayList(Ingredient.class.getClassLoader());
        stepList = in.readArrayList(StepToFollow.class.getClassLoader());
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
        dest.writeString(name);
        dest.writeFloat(valuation);
        dest.writeInt(guests);
        dest.writeInt(difficultyID);
        dest.writeInt(typeID);
        dest.writeList(ingredientList);
        dest.writeList(stepList);
    }
}
