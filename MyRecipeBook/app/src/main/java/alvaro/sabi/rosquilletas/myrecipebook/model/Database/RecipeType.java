package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "RecipeType")
public class RecipeType {
    public RecipeType() {}

    @PrimaryKey @NonNull public String name;

    public String toString() {return name;}

    public RecipeType(String typeName){
        this.name = typeName;
    }
}
