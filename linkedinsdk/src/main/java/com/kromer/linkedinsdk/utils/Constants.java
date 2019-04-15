package com.kromer.linkedinsdk.utils;

/*
 * *
 *  * Created by Mina Mikhail on 09/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class Constants {

  public static final String TOKEN_TYPE = "Bearer ";
  public static final String AUTHORIZATION_URL =
      "https://www.linkedin.com/uas/oauth2/authorization";

  public static final String RESPONSE_TYPE = "code";
  public static final String SIGN_IN_SCOPE =
      PermissionsScope.LITE_PROFILE + "," + PermissionsScope.EMAIL_ADDRESS;
  public static final String GRANT_TYPE = "authorization_code";

  // SignIn Request Code
  public static final int REQ_CODE_SIGN_IN = 111;

  // SignIn Request State
  public static final int SUCCESS = 222;
  public static final int FAILURE = 333;

  // SignIn Response
  public static final String USER = "linkedinUser";
  public static final String ERROR_TYPE = "errorType";
}