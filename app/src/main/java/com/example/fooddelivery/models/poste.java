package com.example.fooddelivery.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class poste implements Serializable, Parcelable {
    private String title;
    private int Image;
    private float  price;
    private String category;


    public poste(String title, int image, float price, String category) {
        this.title = title;
        Image = image;
        this.price = price;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    protected poste(Parcel in) {
        title = in.readString();
        Image = in.readInt();
        price = in.readFloat();
    }

    public static final Creator<poste> CREATOR = new Creator<poste>() {
        @Override
        public poste createFromParcel(Parcel in) {
            return new poste(in);
        }

        @Override
        public poste[] newArray(int size) {
            return new poste[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeInt(Image);
        dest.writeFloat(price);
    }
}
