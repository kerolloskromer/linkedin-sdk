# Linkedin SDK (Unofficial)

Unofficial Linkedin SDK

## Features

- [x] Sign In with LinkedIn.
- [x] Fetch Linked In user lite profile (id,firstname,lastname,profilepicture).
- [x] Fetch Linked In user email address.

## Getting Started

Add this dependency to your app build.gradle file
```
  dependencies {
    implementation "com.kromer:linkedin-sdk:1.0"
  }
```
Sign in with Linkedin with single line
```
  Linkedin.signIn(MainActivity.this, clientId, clientSecret, redirectURL, state);
```
Receive sign in response
```
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
```
## Report Bug / Issue / Improvement

Please feel free to report bug , issue or improvement - see the [Issues](https://github.com/kerolloskromer/linkedin-sdk/issues) section fisrt to prevent duplicates.
Also, if you know how to fix this issue please feel free to fork this repo and make a pull request and i will gladly review and merge and add you as a contributer :)

## Authors

* **Kerollos Kromer** - *Android Developer* - [Linkedin](https://www.linkedin.com/in/kerollos-kromer-39aba078/)
* **Mina Mikhail** - *Android Developer* - [Linkedin](https://www.linkedin.com/in/minasamirgerges/)
* **Esraa Nayel** - *Android Developer* - [Linkedin](https://www.linkedin.com/in/esraa-nayel-22362064/)

## Contributers

We are very excited to join us :)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
