package com.lemuel.lemubit.bakenow.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by charl on 08/11/2017.
 */

public class Ingredients implements Parcelable {

    private double quantity;
    private String measure;
    private String ingredient;

    public Ingredients() {
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }

    public static final Parcelable.Creator
            CREATOR = new Parcelable.Creator<Ingredients>() {
        public Ingredients createFromParcel(Parcel in) {
            return new Ingredients(in);
        }

        public Ingredients[] newArray(int size) {
            return new Ingredients[size];
        }
    };

    // "De-parcel object
    private Ingredients(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }
}
