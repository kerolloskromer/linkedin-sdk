package com.kromer.linkedinsdk.utils;

/*
 * *
 *  * Created by Mina Mikhail on 09/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

import android.webkit.JavascriptInterface;

public class WebViewUtils {

  public static class MyJsToAndroid {
    @JavascriptInterface
    public void myClick(String idOrClass) {
      System.out.println("--clicked--element--id-or-class-->" + idOrClass);
    }
  }

  public static String addMyClickCallBackJs() {
    String js = "javascript:";
    js += "function myClick(this.area-label){" +
        "if(event.target.className == null){my.myClick(this.area-label)}" +
        "else{my.myClick(this.area-label)}}";
    js += "document.addEventListener(\"click\",myClick,true);";
    return js;
  }
}