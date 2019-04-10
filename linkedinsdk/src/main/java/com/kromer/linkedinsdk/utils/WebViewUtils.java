package com.kromer.linkedinsdk.utils;

/*
 * *
 *  * Created by Mina Mikhail on 09/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

import android.content.Context;
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
}