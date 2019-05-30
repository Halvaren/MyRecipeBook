package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity (tableName = "shoppinglist")
public class ShoppingList {
    public ShoppingList() {}

    @PrimaryKey @NonNull public String name;
    @ForeignKey(entity = Ingredient.class, parentColumns = "name", childColumns = "ingredient")
    @ColumnInfo(name = "ingredient")
    public String ingredient;

    public boolean bought;

    public ShoppingList(String name, String ingredient, boolean bought) {
        this.name = name;
        this.ingredient = ingredient;
        this.bought = bought;

    }

}
