package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "StepsToFollow",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "name",
                childColumns = "recipeName")},
        primaryKeys = {"recipeName", "id"})
public class StepToFollow {
    public StepToFollow() {}
    public int id;
    @NonNull public String recipeName;
    public String description;

    public StepToFollow(int id, String recipeName, String description) {
        this.id = id;
        this.recipeName = recipeName;
        this.description = description;
    }
}
