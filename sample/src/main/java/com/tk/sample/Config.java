package com.tk.sample;

import android.os.Parcel;
import android.os.Parcelable;

import com.tk.anythingpull.AnythingPullLayout;

/**
 * <pre>
 *     author : TK
 *     time   : 2017/04/15
 *     desc   : xxxx描述
 * </pre>
 */
public class Config implements Parcelable {
    public int refreshMode;
    public boolean refreshFixed;
    public boolean refreshEnable = true;
    public int loadMode;
    public boolean loadFixed;
    public boolean loadEnable = true;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.refreshMode);
        dest.writeByte(this.refreshFixed ? (byte) 1 : (byte) 0);
        dest.writeByte(this.refreshEnable ? (byte) 1 : (byte) 0);
        dest.writeInt(this.loadMode);
        dest.writeByte(this.loadFixed ? (byte) 1 : (byte) 0);
        dest.writeByte(this.loadEnable ? (byte) 1 : (byte) 0);
    }

    public Config() {
    }

    protected Config(Parcel in) {
        this.refreshMode = in.readInt();
        this.refreshFixed = in.readByte() != 0;
        this.refreshEnable = in.readByte() != 0;
        this.loadMode = in.readInt();
        this.loadFixed = in.readByte() != 0;
        this.loadEnable = in.readByte() != 0;
    }

    public static final Creator<Config> CREATOR = new Creator<Config>() {
        @Override
        public Config createFromParcel(Parcel source) {
            return new Config(source);
        }

        @Override
        public Config[] newArray(int size) {
            return new Config[size];
        }
    };

    public void attachToLayout(AnythingPullLayout pullLayout) {
        pullLayout.setRefreshEnable(refreshEnable);
        pullLayout.setLoadEnable(loadEnable);

        pullLayout.setRefreshFixed(refreshFixed);
        pullLayout.setLoadFixed(loadFixed);

        pullLayout.initAdapterMode(refreshMode, loadMode);
    }
}
