package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "RecipeType")
public class RecipeType {
    public RecipeType() {}

    @PrimaryKey  public String name;

    public String toString() {return name;}

    public RecipeType(String type){
        this.name = type;
    }
}
