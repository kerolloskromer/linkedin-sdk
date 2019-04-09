package com.kromer.linkedinsdk.data.enums;

/*
 * *
 *  * Created by Mina Mikhail on 09/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class QueryParameter {

  public static class AuthorizationUrlParameters {
    public static final String RESPONSE_TYPE = "response_type";
    public static final String CLIENT_ID = "client_id";
    public static final String REDIRECT_URI = "redirect_uri";
    public static final String STATE = "state";
    public static final String SCOPE = "scope";
  }

  public static class CodeUrlParameters {
    public static final String CODE = "code";
  }
}