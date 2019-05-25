package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "Ingredient")
public class Ingredient {
    public Ingredient() {}

    @PrimaryKey public String name;

    public String toString() {return name;}

    public Ingredient(String type){
        this.name = type;
    }
}
