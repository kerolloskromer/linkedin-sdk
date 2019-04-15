package com.kromer.linkedinsdk.data.enums;

/*
 * *
 *  * Created by Mina Mikhail on 09/04/2019
 *  * Copyright (c) 2019 . All rights reserved.
 * *
 */

public class ErrorCode {

  public static final int ERROR_NO_INTERNET = 1;
  public static final int ERROR_USER_CANCELLED = 2;
  public static final int ERROR_OTHER = 3;

  public static final String ERROR_MSG = "net::ERR_";
  public static final String ERROR_USER_CANCELLED_MSG = "error=user_cancelled_authorize";
}
