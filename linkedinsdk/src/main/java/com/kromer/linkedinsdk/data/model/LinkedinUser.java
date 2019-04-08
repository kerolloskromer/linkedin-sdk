package com.kromer.linkedinsdk.data.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kerollos Kromer on 07-Apr-19.
 */
public class LinkedinUser implements Parcelable {

  private String token;
  private String id;
  private String firstName;
  private String lastName;
  private String profilePicture;
  private String emailAddress;

  public LinkedinUser() {

  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFullName() {
    return firstName + " " + lastName;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  private LinkedinUser(Parcel in) {
    token = in.readString();
    id = in.readString();
    firstName = in.readString();
    lastName = in.readString();
    profilePicture = in.readString();
    emailAddress = in.readString();
  }

  public static final Creator<LinkedinUser> CREATOR = new Creator<LinkedinUser>() {
    @Override
    public LinkedinUser createFromParcel(Parcel in) {
      return new LinkedinUser(in);
    }

    @Override
    public LinkedinUser[] newArray(int size) {
      return new LinkedinUser[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(token);
    dest.writeString(id);
    dest.writeString(firstName);
    dest.writeString(lastName);
    dest.writeString(profilePicture);
    dest.writeString(emailAddress);
  }
}