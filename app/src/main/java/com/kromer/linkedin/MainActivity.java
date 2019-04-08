package com.kromer.linkedin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.kromer.linkedinsdk.Linkedin;
import com.kromer.linkedinsdk.data.model.LinkedinUser;
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

    findViewById(R.id.btn_linkedin_sign_in).setOnClickListener(v -> {
      Linkedin.signIn(MainActivity.this, clientId, clientSecret, redirectURL, state);
    });
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == Linkedin.REQ_CODE_SIGN_IN) {
      if (resultCode == Linkedin.SUCCESS) {
        LinkedinUser linkedinUser = data.getParcelableExtra(Linkedin.USER);
        Toast.makeText(MainActivity.this, linkedinUser.getFirstName(), Toast.LENGTH_SHORT).show();
      } else if (resultCode == Linkedin.FAILURE) {
        int errorType = data.getIntExtra(Linkedin.ERROR_TYPE, 0);
        switch (errorType) {
          case Linkedin.ERROR_NO_INTERNET:
            Toast.makeText(MainActivity.this, "Please check your internet connection",
                Toast.LENGTH_SHORT).show();
            break;
          case Linkedin.ERROR_USER_CANCELLED:
            Toast.makeText(MainActivity.this, "User cancelled authorization", Toast.LENGTH_SHORT)
                .show();
            break;
          case Linkedin.ERROR_OTHER:
            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            break;
        }
      }
    }
  }
}