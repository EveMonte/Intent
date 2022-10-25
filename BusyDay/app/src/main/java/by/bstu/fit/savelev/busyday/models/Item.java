package by.bstu.fit.savelev.busyday.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private String activityName;
    private String activityDescription;
    private ActivityCategories activityCategory;
    private int durationInMinutes;
    private String photo;

    public Item(Parcel in) {
        activityName = in.readString();
        activityDescription = in.readString();
        durationInMinutes = in.readInt();
        photo = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public Item() {

    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public ActivityCategories getActivityCategory() {
        return activityCategory;
    }

    public void setActivityCategory(String activityCategory) {
        for (ActivityCategories cat:
                ActivityCategories.values()
        ) {
            if(cat.getValue().equals(activityCategory)) {
                this.activityCategory = cat;
                break;
            }
        }

    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(activityName);
        parcel.writeString(activityDescription);
        parcel.writeInt(durationInMinutes);
        parcel.writeString(photo);
    }
}
