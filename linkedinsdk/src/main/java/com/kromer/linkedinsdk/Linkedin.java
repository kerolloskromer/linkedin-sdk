package com.kromer.linkedinsdk;

import android.app.Activity;
import android.content.Intent;
import com.kromer.linkedinsdk.ui.SignInActivity;

/**
 * Created by Kerollos Kromer on 07-Apr-19.
 */
public class Linkedin {

  // SignIn Request Data
  public static final String CLIENT_ID = "clientId";
  public static final String CLIENT_SECRET = "clientSecret";
  public static final String REDIRECT_URL = "redirectURL";
  public static final String STATE = "state";

  // SignIn Request Code
  public static final int REQ_CODE_SIGN_IN = 111;

  // SignIn Request State
  public static final int SUCCESS = 222;
  public static final int FAILURE = 333;

  // SignIn Response
  public static final String USER = "linkedinUser";
  public static final String ERROR_TYPE = "errorType";

  // Error Types
  public static final int ERROR_NO_INTERNET = 1;
  public static final int ERROR_USER_CANCELLED = 2;
  public static final int ERROR_OTHER = 3;

  public static void signIn(Activity activity, String clientId, String clientSecret,
      String redirectURL, String state) {
    Intent intent = new Intent(activity, SignInActivity.class);
    intent.putExtra(Linkedin.CLIENT_ID, clientId);
    intent.putExtra(Linkedin.CLIENT_SECRET, clientSecret);
    intent.putExtra(Linkedin.REDIRECT_URL, redirectURL);
    intent.putExtra(Linkedin.STATE, state);
    activity.startActivityForResult(intent, REQ_CODE_SIGN_IN);
  }
}