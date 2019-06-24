package com.example.zealience.oneiromancy.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @user steven
 * @createDate 2019/2/20 14:05
 * @description 自定义
 */
public class DreamTypeEntity implements Parcelable {

    /**
     * id : 89
     * name : 其它动物
     * fid : 3
     */

    private String id;
    private String name;
    private String fid;

    protected DreamTypeEntity(Parcel in) {
        id = in.readString();
        name = in.readString();
        fid = in.readString();
    }

    public static final Creator<DreamTypeEntity> CREATOR = new Creator<DreamTypeEntity>() {
        @Override
        public DreamTypeEntity createFromParcel(Parcel in) {
            return new DreamTypeEntity(in);
        }

        @Override
        public DreamTypeEntity[] newArray(int size) {
            return new DreamTypeEntity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(fid);
    }
}
