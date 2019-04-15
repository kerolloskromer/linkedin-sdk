# Linkedin SDK (Unofficial)

Unofficial SDK for Linkedin v2 REST APIs

## Features

- [x] Sign in with Linkedin.
- [x] Fetch Linkedin user full profile (id , firstName , lastName , profilePicture , emailAddress).

## Getting Started

1- Create app on [Linkedin Developers Console](https://www.linkedin.com/developers/apps)

2- From Auth section get Client ID and Client Secret and do not forget to add “Redirect URL”. The reason being, it will be later used to get callback for identifying the response in native webviews. You can put in any Redirect URL and not necessarily a real one. It can be any value starting with https:// prefix.
For example Redirect Url
```
  https://com.kromer.linkedin.oauth/oauth
```
3- Add in your root build.gradle at the end of repositories
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
4- Add the dependency in your app build.gradle file
```
  dependencies {
    implementation 'com.github.kerolloskromer:linkedin-sdk:1.0.0'
  }
```
5- Sign in with Linkedin with single line
```
  Linkedin.signIn(MainActivity.this, clientId, clientSecret, redirectURL, state);
```
"state" is a unique string of your choice designed to protect against CSRF attacks.

6- Receive sign in response
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

## Contributers

* **Kerollos Kromer** - *Android Developer* - [Linkedin](https://www.linkedin.com/in/kerollos-kromer-39aba078/) - [Github](https://github.com/kerolloskromer)
* **Mina Mikhail** - *Android Developer* - [Linkedin](https://www.linkedin.com/in/minasamirgerges/) - [Github](https://github.com/Mina-Mikhail)
* **Esraa Nayel** - *Android Developer* - [Linkedin](https://www.linkedin.com/in/esraa-nayel-22362064/) - [Github](https://github.com/EsraaNayel)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details
