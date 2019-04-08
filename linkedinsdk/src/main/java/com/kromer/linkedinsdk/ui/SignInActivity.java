package com.kromer.linkedinsdk.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import com.kromer.linkedinsdk.Linkedin;
import com.kromer.linkedinsdk.R;
import com.kromer.linkedinsdk.data.model.LinkedinUser;
import com.kromer.linkedinsdk.data.network.ApiClient;
import com.kromer.linkedinsdk.data.network.response.AccessTokenResponse;
import com.kromer.linkedinsdk.utils.NetworkUtils;
import com.uber.autodispose.ScopeProvider;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SignInActivity extends AppCompatActivity {

  private WebView webView;
  private ProgressBar progressBar;

  String responseType = "code";
  String scope = "r_liteprofile,r_emailaddress";
  String grantType = "authorization_code";
  private String clientId, clientSecret, redirectURL, state;

  private CompositeDisposable compositeDisposable;
  private LinkedinUser linkedinUser;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
      onLinkedinSignInFailure(Linkedin.ERROR_NO_INTERNET, "No Internet Connection");
    }
    setContentView(R.layout.activity_sign_in);

    clientId = getIntent().getStringExtra(Linkedin.CLIENT_ID);
    clientSecret = getIntent().getStringExtra(Linkedin.CLIENT_SECRET);
    redirectURL = getIntent().getStringExtra(Linkedin.REDIRECT_URL);
    state = getIntent().getStringExtra(Linkedin.STATE);

    webView = findViewById(R.id.webView);
    progressBar = findViewById(R.id.progressBar);

    initWebView();
  }

  @SuppressLint({ "SetJavaScriptEnabled", "JavascriptInterface" })
  private void initWebView() {
    WebSettings webSettings = webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    webView.setWebChromeClient(new WebChromeClient());
    webView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Log.w("LinkedIn", url);

        Uri uri = Uri.parse(url);
        String code = uri.getQueryParameter("code");
        if (code != null && !code.isEmpty()) {
          Log.w("LinkedIn", "code = " + code);
          showLoading();
          compositeDisposable = new CompositeDisposable();
          linkedinUser = new LinkedinUser();
          getAccessToken(code);
        }

        if (url.contains("error=user_cancelled_authorize")) {
          onLinkedinSignInFailure(Linkedin.ERROR_USER_CANCELLED, "User Cancelled");
        }

        return super.shouldOverrideUrlLoading(view, url);
      }

      @Override
      public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
          onLinkedinSignInFailure(Linkedin.ERROR_NO_INTERNET, "No Internet Connection");
        }
        showLoading();
      }

      @Override
      public void onPageFinished(WebView view, String url) {
        webView.findAllAsync("net::ERR_");
      }

      @Override
      public void onReceivedError(WebView view, int errorCode, String description,
          String failingUrl) {
        showLoading();
        super.onReceivedError(view, errorCode, description, failingUrl);
      }

      @Override
      public void onReceivedError(WebView view, WebResourceRequest request,
          WebResourceError error) {
        showLoading();
        super.onReceivedError(view, request, error);
      }

      @Override
      public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        showLoading();
        super.onReceivedSslError(view, handler, error);
      }

      @Override
      public void onReceivedHttpError(WebView view, WebResourceRequest request,
          WebResourceResponse errorResponse) {
        showLoading();
        super.onReceivedHttpError(view, request, errorResponse);
      }
    });
    webView.setFindListener((activeMatchOrdinal, numberOfMatches, isDoneCounting) -> {
      if (numberOfMatches > 0) {
        showLoading();
      } else {
        hideLoading();
      }
    });

    String url = getAuthorizationUrl();
    webView.loadUrl(url);
  }

  private String getAuthorizationUrl() {
    return Uri.parse("https://www.linkedin.com/uas/oauth2/authorization")
        .buildUpon()
        .appendQueryParameter("response_type", responseType)
        .appendQueryParameter("client_id", clientId)
        .appendQueryParameter("redirect_uri", redirectURL)
        .appendQueryParameter("state", state)
        .appendQueryParameter("scope", scope).build().toString();
  }

  private void getAccessToken(String code) {
    compositeDisposable.add(ApiClient.getInstance().getApiService()
        .getAccessToken(grantType, code, redirectURL, clientId, clientSecret)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribeWith(new DisposableSingleObserver<AccessTokenResponse>() {
          @Override
          public void onSuccess(AccessTokenResponse response) {
            if (!compositeDisposable.isDisposed()) {
              String accessToken = response.getAccess_token();
              linkedinUser.setToken(accessToken);

              Log.w("LinkedIn", "accessToken = " + accessToken);
              getFullProfile(accessToken);
              dispose();
            }
          }

          @Override
          public void onError(Throwable e) {
            if (!compositeDisposable.isDisposed()) {
              if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
                onLinkedinSignInFailure(Linkedin.ERROR_NO_INTERNET, "No Internet Connection");
              } else {
                onLinkedinSignInFailure(Linkedin.ERROR_OTHER, e.getMessage());
              }
              dispose();
            }
          }
        }));
  }

  private void getFullProfile(String accessToken) {
    compositeDisposable.add(Flowable.merge(getLiteProfile(accessToken), getEmail(accessToken))
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(throwable -> {
          if (!NetworkUtils.isNetworkConnected(SignInActivity.this)) {
            onLinkedinSignInFailure(Linkedin.ERROR_NO_INTERNET, "No Internet Connection");
          } else {
            onLinkedinSignInFailure(Linkedin.ERROR_OTHER, throwable.getMessage());
          }
        }).doOnComplete(() -> onLinkedinSignInSuccess(linkedinUser))
        .as(autoDisposable(ScopeProvider.UNBOUND))
        .subscribe());
  }

  private Flowable<LinkedinUser> getLiteProfile(String accessToken) {
    return ApiClient.getInstance().getApiService().getProfile("Bearer " + accessToken).map(
        response -> {
          String id = response.getId();
          String firstName = response.getFirstName().getLocalized().getEn_US();
          String lastName = response.getLastName().getLocalized().getEn_US();
          String profilePicture =
              response.getProfilePicture().getDisplayImage().getElements().get(1)
                  .getIdentifiers().get(0).getIdentifier();

          linkedinUser.setId(id);
          linkedinUser.setFirstName(firstName);
          linkedinUser.setLastName(lastName);
          linkedinUser.setProfilePicture(profilePicture);

          Log.w("LinkedIn", "profilePicture = " + profilePicture);
          return linkedinUser;
        });
  }

  private Flowable<LinkedinUser> getEmail(String accessToken) {
    return ApiClient.getInstance().getApiService().getEmail("Bearer " + accessToken).map(
        response -> {
          String email = response.getElements().get(0).getHandle().getEmailAddress();
          linkedinUser.setEmailAddress(email);

          Log.w("LinkedIn", "email = " + email);
          return linkedinUser;
        });
  }

  @Override
  protected void onDestroy() {

    if (compositeDisposable != null) {
      compositeDisposable.dispose();
    }

    super.onDestroy();
  }

  private void onLinkedinSignInSuccess(LinkedinUser linkedinUser) {
    clear();
    Intent intent = new Intent();
    intent.putExtra(Linkedin.USER, linkedinUser);
    setResult(Linkedin.SUCCESS, intent);
    finish();
  }

  private void onLinkedinSignInFailure(int errorType, String errorMsg) {
    Log.w("LinkedIn", errorMsg);
    clear();
    Intent intent = new Intent();
    intent.putExtra(Linkedin.ERROR_TYPE, errorType);
    setResult(Linkedin.FAILURE, intent);
    finish();
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
    if (webView == null) return;
    webView.clearCache(true);
    webView.clearHistory();
  }
}