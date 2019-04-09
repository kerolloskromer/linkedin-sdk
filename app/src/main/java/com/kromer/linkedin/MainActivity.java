package com.kromer.linkedin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.kromer.linkedinsdk.Linkedin;
import com.kromer.linkedinsdk.data.enums.ErrorCode;
import com.kromer.linkedinsdk.data.model.LinkedInUser;
import com.kromer.linkedinsdk.utils.Constants;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

  String clientId = "86kpneombjdevn";
  String clientSecret = "MlJ6doVcJ2IIOjgf";
  String redirectURL = "http://appsinnovate.com/";
  String state; // unique string of your choice designed to protect against CSRF attacks.

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    state = UUID.randomUUID().toString();

    findViewById(R.id.btn_linkedin_sign_in).setOnClickListener(
        v -> Linkedin.signIn(MainActivity.this, clientId, clientSecret, redirectURL, state));
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == Constants.REQ_CODE_SIGN_IN) {
      if (resultCode == Constants.SUCCESS) {
        LinkedInUser linkedinUser = data.getParcelableExtra(Constants.USER);
        Toast.makeText(MainActivity.this, linkedinUser.getFirstName(), Toast.LENGTH_SHORT).show();
      } else if (resultCode == Constants.FAILURE) {
        int errorType = data.getIntExtra(Constants.ERROR_TYPE, 0);
        switch (errorType) {
          case ErrorCode.ERROR_NO_INTERNET:
            Toast.makeText(MainActivity.this, "Please check your internet connection",
                Toast.LENGTH_SHORT).show();
            break;
          case ErrorCode.ERROR_USER_CANCELLED:
            Toast.makeText(MainActivity.this, "User cancelled authorization", Toast.LENGTH_SHORT)
                .show();
            break;
          case ErrorCode.ERROR_OTHER:
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            break;
        }
      }
    }
  }
}