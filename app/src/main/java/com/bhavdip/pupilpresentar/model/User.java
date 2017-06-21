package com.bhavdip.pupilpresentar.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bhavdip Bhalodia on 6/16/2017.
 */


public class User implements Parcelable {

    String fullName;
    String email;
    String gender;
    String mobile;
    String userName;
    String password;
    byte[] image;

    public User() {
        this.fullName = "";
        this.email = "";
        this.gender = "";
        this.mobile = "";
        this.userName = "";
        this.password = "";
        this.image = new byte[0];
    }

    protected User(Parcel in) {
        fullName = in.readString();
        email = in.readString();
        gender = in.readString();
        mobile = in.readString();
        userName = in.readString();
        password = in.readString();
        image = in.createByteArray();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullName);
        dest.writeString(email);
        dest.writeString(gender);
        dest.writeString(mobile);
        dest.writeString(userName);
        dest.writeString(password);
        dest.writeByteArray(image);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
