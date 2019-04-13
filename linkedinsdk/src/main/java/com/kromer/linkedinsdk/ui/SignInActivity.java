package com.kromer.linkedinsdk.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;
import com.kromer.linkedinsdk.Linkedin;
import com.kromer.linkedinsdk.R;
import com.kromer.linkedinsdk.data.enums.ErrorCode;
import com.kromer.linkedinsdk.data.enums.QueryParameter.AuthorizationUrlParameters;
import com.kromer.linkedinsdk.data.enums.QueryParameter.CodeUrlParameters;
import com.kromer.linkedinsdk.data.model.LinkedInUser;
import com.kromer.linkedinsdk.data.network.ApiClient;
import com.kromer.linkedinsdk.data.network.response.AccessTokenResponse;
import com.kromer.linkedinsdk.databinding.ActivitySignInBinding;
import com.kromer.linkedinsdk.utils.Constants;
import com.kromer.linkedinsdk.utils.NetworkUtils;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.List;

import static com.kromer.linkedinsdk.utils.WebViewUtils.clearCookies;
import static com.kromer.linkedinsdk.utils.WebViewUtils.injectedJs;
import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SignInActivity
    extends AppCompatActivity {

  private static final String TAG = "LINKED_IN_SDK";
  private ActivitySignInBinding mViewDataBinding;

  private WebView webView;
  private ProgressBar progressBar;

  private String clientId, clientSecret, redirectURL, state;

  private CompositeDisposable compositeDisposable;
  private LinkedInUser linkedInUser;

  private boolean isFirstTime = true;
  private boolean shouldOpenNextPage = true;
  private boolean isCancelClicked = false;
  private List<String> supportedButtons = new ArrayList<>();
  private List<String> cancelButtons = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
      onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
          getResources().getString(R.string.no_internet));
    } else {
      initSupportedButtons();
      initCancelButtons();
      performDataBinding();
      getIntentData();
      setUpViews();
    }
  }

  private void initSupportedButtons() {
    // English
    supportedButtons.add("sign in");
    supportedButtons.add("join now");
    supportedButtons.add("agree &amp; join");
    supportedButtons.add("not you?");
    supportedButtons.add("allow");

    // Arabic
    supportedButtons.add("تسجيل الدخول");
    supportedButtons.add("انضم الآن");
    supportedButtons.add("الموافقة والانضمام");
    supportedButtons.add("ألست أنت؟");
    supportedButtons.add("السماح");

    // Français
    supportedButtons.add("s’identifier");
    supportedButtons.add("a’inscrire");
    supportedButtons.add("accepter et s’inscrire");
    supportedButtons.add("ce n’est pas vous ?");
    supportedButtons.add("autoriser");
  }

  private void initCancelButtons() {
    // English
    cancelButtons.add("cancel");

    // Arabic
    cancelButtons.add("إلغاء");

    // Français
    cancelButtons.add("annuler");
  }

  private void performDataBinding() {
    mViewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in);
    mViewDataBinding.executePendingBindings();
  }

  private void getIntentData() {
    clientId = getIntent().getStringExtra(Linkedin.CLIENT_ID);
    clientSecret = getIntent().getStringExtra(Linkedin.CLIENT_SECRET);
    redirectURL = getIntent().getStringExtra(Linkedin.REDIRECT_URL);
    state = getIntent().getStringExtra(Linkedin.STATE);
  }

  private void setUpViews() {
    setUpLoader();

    initWebView();
  }

  private void setUpLoader() {
    progressBar = mViewDataBinding.progressBar;

    Sprite circle = new Circle();
    circle.setColor(getResources().getColor(R.color.linkedinBlue));
    progressBar.setIndeterminateDrawable(circle);
  }

  @SuppressLint({ "SetJavaScriptEnabled" })
  private void initWebView() {
    webView = mViewDataBinding.webView;
    webView.requestFocus(View.FOCUS_DOWN);
    webView.addJavascriptInterface(
        new Object() {
          @JavascriptInterface
          public void onClick(String clickedElement) {
            System.out.println("-->CLICK--->" + clickedElement);

            for (String str : supportedButtons) {
              if (clickedElement.toLowerCase().contains(str)) {
                shouldOpenNextPage = true;
                break;
              } else {
                shouldOpenNextPage = false;
              }
            }

            if (!shouldOpenNextPage) {
              for (String str : cancelButtons) {
                if (clickedElement.toLowerCase().contains(str)) {
                  isCancelClicked = true;
                  break;
                } else {
                  isCancelClicked = false;
                }
              }
            }
          }
        },
        "appHost"
    );

    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webView.setScrollbarFadingEnabled(true);
    webView.setVerticalScrollBarEnabled(false);
    webView.setWebChromeClient(new WebChromeClient());

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      webView.setWebViewClient(getLollipopWebViewClient());
    } else {
      webView.setWebViewClient(getPreLollipopWebViewClient());
    }

    webView.setFindListener((activeMatchOrdinal, numberOfMatches, isDoneCounting) -> {
      if (numberOfMatches > 0) {
        showLoading();
      } else {
        hideLoading();
      }
    });
    webView.loadUrl(getAuthorizationUrl());
  }

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private WebViewClient getLollipopWebViewClient() {
    return new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (shouldOpenNextPage) {
          String url = request.getUrl().toString();
          Log.w(TAG, "shouldOverrideUrlLoading-->" + url);

          if (!url.contains(redirectURL)) {
            webView.loadUrl(url);
          }

          Uri uri = Uri.parse(url);
          String code = uri.getQueryParameter(CodeUrlParameters.CODE);
          if (code != null && !TextUtils.isEmpty(code)) {
            Log.w(TAG, "code = " + code);
            showLoading();
            compositeDisposable = new CompositeDisposable();
            linkedInUser = new LinkedInUser();
            getAccessToken(code);
          }

          if (url.contains(ErrorCode.ERROR_USER_CANCELLED_MSG)) {
            onLinkedInSignInFailure(ErrorCode.ERROR_USER_CANCELLED,
                getResources().getString(R.string.user_cancelled));
          }
        } else {
          if (isCancelClicked) {
            onLinkedInSignInFailure(ErrorCode.ERROR_USER_CANCELLED,
                getResources().getString(R.string.user_cancelled));
          } else {
            Toast.makeText(SignInActivity.this,
                getResources().getString(R.string.action_not_supported), Toast.LENGTH_SHORT).show();
          }
        }

        return true;
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        Log.w(TAG, "onPageFinished-->" + url);

        //webView.findAllAsync(ErrorCode.ERROR_MSG);
        if (isFirstTime) {
          isFirstTime = false;
        } else {
          hideLoading();
        }
        injectJS();
      }

      @Override
      public void onReceivedError(WebView view, int errorCode, String description,
          String failingUrl) {
        if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
          onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
              getResources().getString(R.string.no_internet));
        } else {
          onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
              getResources().getString(R.string.some_error));
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
      }

      @Override
      public void onReceivedError(WebView view, WebResourceRequest request,
          WebResourceError error) {
        if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
          onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
              getResources().getString(R.string.no_internet));
        } else {
          onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
              getResources().getString(R.string.some_error));
        }
        super.onReceivedError(view, request, error);
      }

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
          onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
              getResources().getString(R.string.no_internet));
        } else {
          onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
              getResources().getString(R.string.some_error));
        }
        super.onReceivedSslError(view, handler, error);
      }
    };
  }

  private WebViewClient getPreLollipopWebViewClient() {
    return new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (shouldOpenNextPage) {
          Log.w(TAG, "shouldOverrideUrlLoading-->" + url);

          if (!url.contains(redirectURL)) {
            webView.loadUrl(url);
          }

          Uri uri = Uri.parse(url);
          String code = uri.getQueryParameter(CodeUrlParameters.CODE);
          if (code != null && !TextUtils.isEmpty(code)) {
            Log.w(TAG, "code = " + code);
            showLoading();
            compositeDisposable = new CompositeDisposable();
            linkedInUser = new LinkedInUser();
            getAccessToken(code);
          }

          if (url.contains(ErrorCode.ERROR_USER_CANCELLED_MSG)) {
            onLinkedInSignInFailure(ErrorCode.ERROR_USER_CANCELLED,
                getResources().getString(R.string.user_cancelled));
          }
        } else {
          if (isCancelClicked) {
            onLinkedInSignInFailure(ErrorCode.ERROR_USER_CANCELLED,
                getResources().getString(R.string.user_cancelled));
          } else {
            Toast.makeText(SignInActivity.this,
                getResources().getString(R.string.action_not_supported), Toast.LENGTH_SHORT).show();
          }
        }

        return true;
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        Log.w(TAG, "onPageFinished-->" + url);

        // webView.findAllAsync(ErrorCode.ERROR_MSG);

        if (isFirstTime) {
          isFirstTime = false;
        } else {
          hideLoading();
        }
        injectJS();
      }

      @Override
      public void onReceivedError(WebView view, int errorCode, String description,
          String failingUrl) {
        if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
          onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
              getResources().getString(R.string.no_internet));
        } else {
          onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
              getResources().getString(R.string.some_error));
        }
        super.onReceivedError(view, errorCode, description, failingUrl);
      }

      @Override
      public void onReceivedError(WebView view, WebResourceRequest request,
          WebResourceError error) {
        if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
          onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
              getResources().getString(R.string.no_internet));
        } else {
          onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
              getResources().getString(R.string.some_error));
        }
        super.onReceivedError(view, request, error);
      }

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
          onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
              getResources().getString(R.string.no_internet));
        } else {
          onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
              getResources().getString(R.string.some_error));
        }
        super.onReceivedSslError(view, handler, error);
      }
    };
  }

  private String getAuthorizationUrl() {
    return Uri.parse(Constants.AUTHORIZATION_URL)
        .buildUpon()
        .appendQueryParameter(AuthorizationUrlParameters.RESPONSE_TYPE, Constants.RESPONSE_TYPE)
        .appendQueryParameter(AuthorizationUrlParameters.CLIENT_ID, clientId)
        .appendQueryParameter(AuthorizationUrlParameters.REDIRECT_URI, redirectURL)
        .appendQueryParameter(AuthorizationUrlParameters.STATE, state)
        .appendQueryParameter(AuthorizationUrlParameters.SCOPE, Constants.SIGN_IN_SCOPE).build()
        .toString();
  }

  private void getAccessToken(String code) {
    compositeDisposable.add(ApiClient.getInstance().getApiService()
        .getAccessToken(Constants.GRANT_TYPE, code, redirectURL, clientId, clientSecret)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribeWith(new DisposableSingleObserver<AccessTokenResponse>() {
          @Override
          public void onSuccess(AccessTokenResponse response) {
            if (!compositeDisposable.isDisposed()) {
              String accessToken = response.getAccess_token();
              linkedInUser.setToken(accessToken);

              Log.w(TAG, "accessToken = " + accessToken);
              getFullProfile(accessToken);
              dispose();
            }
          }

          @Override
          public void onError(Throwable e) {
            if (!compositeDisposable.isDisposed()) {
              if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
                onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
                    getResources().getString(R.string.no_internet));
              } else {
                onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
                    getResources().getString(R.string.some_error));
              }
              dispose();
            }
          }
        }));
  }

  private void getFullProfile(String accessToken) {
    compositeDisposable.add(Flowable.merge(getLiteProfile(accessToken), getEmail(accessToken))
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(throwable -> {
          if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
            onLinkedInSignInFailure(ErrorCode.ERROR_NO_INTERNET,
                getResources().getString(R.string.no_internet));
          } else {
            onLinkedInSignInFailure(ErrorCode.ERROR_OTHER,
                getResources().getString(R.string.some_error));
          }
        })
        .doOnComplete(() -> onLinkedInSignInSuccess(linkedInUser))
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribe());
  }

  private Flowable<LinkedInUser> getLiteProfile(String accessToken) {
    return ApiClient.getInstance()
        .getApiService()
        .getProfile(Constants.TOKEN_TYPE + accessToken)
        .subscribeOn(Schedulers.newThread())
        .map(
            response -> {
              String id = response.getId();
              String firstName = response.getFirstName();
              String lastName = response.getLastName();
              String profilePicture = response.getProfilePicture();

              linkedInUser.setId(id);
              linkedInUser.setFirstName(firstName);
              linkedInUser.setLastName(lastName);
              linkedInUser.setProfilePicture(profilePicture);

              Log.w(TAG, "profilePicture = " + profilePicture);
              return linkedInUser;
            });
  }

  private Flowable<LinkedInUser> getEmail(String accessToken) {
    return ApiClient.getInstance()
        .getApiService()
        .getEmail(Constants.TOKEN_TYPE + accessToken)
        .subscribeOn(Schedulers.newThread())
        .map(
            response -> {
              String email = response.getEmail();
              linkedInUser.setEmailAddress(email);

              Log.w(TAG, "email = " + email);
              return linkedInUser;
            });
  }

  private void onLinkedInSignInSuccess(LinkedInUser linkedinUser) {
    clear();

    Intent intent = new Intent();
    intent.putExtra(Constants.USER, linkedinUser);
    setResult(Constants.SUCCESS, intent);
    finish();
  }

  private void onLinkedInSignInFailure(int errorType, String errorMsg) {
    Log.w(TAG, errorMsg);
    clear();

    Intent intent = new Intent();
    intent.putExtra(Constants.ERROR_TYPE, errorType);
    setResult(Constants.FAILURE, intent);
    finish();
  }

  private void injectJS() {
    final String injectedJs =
        "javascript:(function(){" + injectedJs(SignInActivity.this) + "})()";
    webView.loadUrl(injectedJs);
  }

  private void showLoading() {
    webView.setVisibility(View.GONE);
    progressBar.setVisibility(View.VISIBLE);
  }

  private void hideLoading() {
    webView.setVisibility(View.VISIBLE);
    progressBar.setVisibility(View.GONE);
  }

  private void clear() {
    clearCookies(this);

    if (webView == null) return;
    webView.clearCache(true);
    webView.clearHistory();
    webView.clearMatches();
    webView.clearFormData();
    webView.clearSslPreferences();
  }

  @Override
  public void onBackPressed() {
    clear();
    onLinkedInSignInFailure(ErrorCode.ERROR_USER_CANCELLED,
        getResources().getString(R.string.user_cancelled));
  }

  @Override
  protected void onDestroy() {
    if (compositeDisposable != null) {
      compositeDisposable.dispose();
    }
    super.onDestroy();
  }
}