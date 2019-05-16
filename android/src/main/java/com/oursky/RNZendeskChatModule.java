
package com.oursky;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.ZopimChatActivity;
import com.zopim.android.sdk.model.VisitorInfo;

public class RNZendeskChatModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNZendeskChatModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNZendeskChat";
  }
  
  @ReactMethod
  public void initialize(String accountKey) {
    ZopimChat.init(accountKey);

    VisitorInfo visitorData = new VisitorInfo.Builder().build();
    ZopimChat.setVisitorInfo(visitorData);
  }

  @ReactMethod
  public void startChat(ReadableMap config) {
    // ignore config at this moment
    ReactApplicationContext context = getReactApplicationContext();
    context.startActivity(new Intent(context, ZopimChatActivity.class));
  }
}
