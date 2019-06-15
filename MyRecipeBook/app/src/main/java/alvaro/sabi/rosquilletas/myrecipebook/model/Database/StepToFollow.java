package alvaro.sabi.rosquilletas.myrecipebook.model.Database;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;
import static androidx.room.ForeignKey.SET_NULL;

@Entity(tableName =  "StepsToFollow",
        foreignKeys = {@ForeignKey(entity = Recipe.class, parentColumns = "id",
                childColumns = "recipeID", onDelete = CASCADE)},
        primaryKeys = {"recipeID", "stepNum"})
public class StepToFollow implements Parcelable {
    public StepToFollow() {}
    public int stepNum;
    public int recipeID;
    public String description;

    public StepToFollow(int stepNum, String description) {
        this.stepNum = stepNum;
        this.description = description;
    }

    protected StepToFollow(Parcel in) {
        stepNum = in.readInt();
        recipeID = in.readInt();
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
        dest.writeInt(stepNum);
        dest.writeInt(recipeID);
        dest.writeString(description);
    }
}
