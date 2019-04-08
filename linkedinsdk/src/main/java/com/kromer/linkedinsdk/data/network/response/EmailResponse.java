package com.kromer.linkedinsdk.data.network.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Kerollos Kromer on 07-Apr-19.
 */
public class EmailResponse {

  private List<Element> elements;

  public List<Element> getElements() {
    return elements;
  }

  public static class Element {
    /**
     * handle : urn:li:emailAddress:730115854
     * handle~ : {"emailAddress":"kerolloskromer@gmail.com"}
     */

    @SerializedName("handle~")
    private Handle handle;

    public Handle getHandle() {
      return handle;
    }

    public static class Handle {
      /**
       * emailAddress : kerolloskromer@gmail.com
       */

      private String emailAddress;

      public String getEmailAddress() {
        return emailAddress;
      }
    }
  }
}