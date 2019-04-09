package com.kromer.linkedinsdk.data.network.response;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Kerollos Kromer on 07-Apr-19.
 */
public class EmailResponse {

  private List<Element> elements;

  public String getEmail() {
    return (elements != null && elements.size() > 0 && elements.get(0).getHandle() != null)
        ? elements.get(0).getHandle().getEmailAddress()
        : "";
  }

  private static class Element {
    /**
     * handle : urn:li:emailAddress:730115854
     * handle~ : {"emailAddress":"kerolloskromer@gmail.com"}
     */

    @SerializedName("handle~")
    private Handle handle;

    private Handle getHandle() {
      return handle;
    }

    private static class Handle {
      /**
       * emailAddress : kerolloskromer@gmail.com
       */

      private String emailAddress;

      private String getEmailAddress() {
        return emailAddress;
      }
    }
  }
}