package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Recipes")
//@Entity(foreignKeys =  @ForeignKey(entity = RecipeType.class, parentColumns = "name", childColumns = "type"))
public class Recipe {
    public Recipe () {}
    @PrimaryKey @NonNull public String name;
    public float valuation;
    public int guest;
    public String comment;
    public String difficult;

    @ForeignKey(entity = RecipeType.class, parentColumns = "name", childColumns = "type")
    @ColumnInfo (name = "type")
    public String type;

    @Ignore
    private ArrayList<StepToFollow> stepsList;
    @Ignore
    private ArrayList<Ingredient> ingredientsList;

    public Recipe(String name, float valuation, int guest, String comment, String difficult, String type) {
        this.name = name;
        this.valuation = valuation;
        this.guest = guest;
        this.comment = comment;
        this.difficult = difficult;
        this.type = type;
    }

    public ArrayList<StepToFollow> getStepsList() { return stepsList; }
    public ArrayList<Ingredient> getIngredientsList() { return ingredientsList; }

    public void setStepsList(ArrayList<StepToFollow> value) { stepsList = value; }
    public void setIngredientsList(ArrayList<Ingredient> value) { ingredientsList = value; }
}
