package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Menu")
public class Menu {
    public Menu () {}
    @PrimaryKey @NonNull public String name;

    @ForeignKey(entity = Recipe.class, parentColumns = "name", childColumns = "recipeName")
    @ColumnInfo(name = "recipeName")
    public String recipeName;

    public Menu(String name, String recipe) {
        this.name = name;
        this.recipeName = recipe;
    }

}
