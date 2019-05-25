package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Recipes")
//@Entity(foreignKeys =  @ForeignKey(entity = RecipeType.class, parentColumns = "name", childColumns = "type"))
public class Recipe {
    public Recipe () {}
    @PrimaryKey  public String name;
    public int valuation;
    public int guest;
    public String comment;
    public int difficult;

    @ForeignKey(entity = RecipeType.class, parentColumns = "name", childColumns = "type")
    @ColumnInfo (name = "type")
    public String type;

    public Recipe(String name, int valuation, int guest, String comment, int difficult, String type) {
        this.name = name;
        this.valuation = valuation;
        this.guest = guest;
        this.comment = comment;
        this.difficult = difficult;
        this.type = type;
    }

}
