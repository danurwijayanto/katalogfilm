package com.example.katalogfilm.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class ReminderParcelable implements Parcelable {

    boolean isDailyReminder;
    boolean isReleaseReminder;

    public ReminderParcelable(Parcel in) {
        isDailyReminder = in.readByte() != 0;
        isReleaseReminder = in.readByte() != 0;
    }

    public static final Creator<ReminderParcelable> CREATOR = new Creator<ReminderParcelable>() {
        @Override
        public ReminderParcelable createFromParcel(Parcel in) {
            return new ReminderParcelable(in);
        }

        @Override
        public ReminderParcelable[] newArray(int size) {
            return new ReminderParcelable[size];
        }
    };

    public ReminderParcelable() {

    }

    public boolean isDailyReminder() {
        return isDailyReminder;
    }

    public void setDailyReminder(boolean dailyReminder) {
        isDailyReminder = dailyReminder;
    }

    public boolean isReleaseReminder() {
        return isReleaseReminder;
    }

    public void setReleaseReminder(boolean releaseReminder) {
        isReleaseReminder = releaseReminder;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isDailyReminder ? 1 : 0));
        dest.writeByte((byte) (isReleaseReminder ? 1 : 0));
    }
}
