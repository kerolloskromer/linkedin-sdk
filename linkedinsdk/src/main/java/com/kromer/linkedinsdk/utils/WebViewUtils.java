package com.kromer.linkedinsdk.utils;

/*
 * *
 *  * Created by Mina Mikhail on 09/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

import android.content.Context;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WebViewUtils {

  public static String injectedJs(Context context) {
    BufferedReader stream = null;
    StringBuilder jsBuilder = new StringBuilder();
    try {
      stream = new BufferedReader(new InputStreamReader(context.getAssets().open("js.js")));
      String line;
      while ((line = stream.readLine()) != null) {
        jsBuilder.append(line.trim());
      }
      return jsBuilder.toString();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (stream != null) {
        try {
          stream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return "";
  }

  public static void clearCookies(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
      CookieManager.getInstance().removeAllCookies(null);
      CookieManager.getInstance().flush();
    } else {
      CookieSyncManager cookieSyncMngr = CookieSyncManager.createInstance(context);
      cookieSyncMngr.startSync();
      CookieManager cookieManager = CookieManager.getInstance();
      cookieManager.removeAllCookie();
      cookieManager.removeSessionCookie();
      cookieSyncMngr.stopSync();
      cookieSyncMngr.sync();
    }
  }
}