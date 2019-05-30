package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "Ingredients")
public class Ingredient {
    public Ingredient() {}

    @PrimaryKey @NonNull public String name;

    public String toString() {return name;}

    public Ingredient(String type){
        this.name = type;
    }
}
