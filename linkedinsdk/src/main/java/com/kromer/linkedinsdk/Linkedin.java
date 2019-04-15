package com.kromer.linkedinsdk;

import android.app.Activity;
import android.content.Intent;
import com.kromer.linkedinsdk.ui.SignInActivity;
import com.kromer.linkedinsdk.utils.Constants;

/**
 * Created by Kerollos Kromer on 07-Apr-19.
 */
public class Linkedin {

  // SignIn Request Data
  public static final String CLIENT_ID = "clientId";
  public static final String CLIENT_SECRET = "clientSecret";
  public static final String REDIRECT_URL = "redirectURL";
  public static final String STATE = "state";

  public static void signIn(Activity activity, String clientId, String clientSecret,
      String redirectURL, String state) {
    Intent intent = new Intent(activity, SignInActivity.class);
    intent.putExtra(Linkedin.CLIENT_ID, clientId);
    intent.putExtra(Linkedin.CLIENT_SECRET, clientSecret);
    intent.putExtra(Linkedin.REDIRECT_URL, redirectURL);
    intent.putExtra(Linkedin.STATE, state);
    activity.startActivityForResult(intent, Constants.REQ_CODE_SIGN_IN);
  }
}