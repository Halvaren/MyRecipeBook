package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName =  "StepsToFollow",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "name",
                childColumns = "recipeName")},
        primaryKeys = {"recipeName", "id"})
public class StepToFollow implements Parcelable {
    public StepToFollow() {}
    public int id;
    @NonNull public String recipeName;
    public String description;

    public StepToFollow(int id, String recipeName, String description) {
        this.id = id;
        this.recipeName = recipeName;
        this.description = description;
    }

    protected StepToFollow(Parcel in) {
        id = in.readInt();
        recipeName = in.readString();
        description = in.readString();
    }

    public static final Creator<StepToFollow> CREATOR = new Creator<StepToFollow>() {
        @Override
        public StepToFollow createFromParcel(Parcel in) {
            return new StepToFollow(in);
        }

        @Override
        public StepToFollow[] newArray(int size) {
            return new StepToFollow[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(recipeName);
        dest.writeString(description);
    }
}
