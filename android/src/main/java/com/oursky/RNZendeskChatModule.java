
package com.oursky;

import android.content.Intent;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;

import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.ZopimChatActivity;
import com.zopim.android.sdk.model.VisitorInfo;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.PreChatForm;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

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
  public void startChat(ReadableMap configMap) throws Exception {
    ReactApplicationContext context = getReactApplicationContext();
    if (configMap.hasKey("preChatForm")) {
      ReadableMap form = configMap.getMap("preChatForm");
      PreChatForm preChatForm = new PreChatForm.Builder()
        .name(mapStringToPreChatFormFieldOption(form.hasKey("name") ? form.getString("name") : null))
        .email(mapStringToPreChatFormFieldOption(form.hasKey("email") ? form.getString("email") : null))
        .phoneNumber(mapStringToPreChatFormFieldOption(form.hasKey("phone") ? form.getString("phone") : null))
        .department(mapStringToPreChatFormFieldOption(form.hasKey("department") ? form.getString("department") : null))
        .message(mapStringToPreChatFormFieldOption(form.hasKey("message") ? form.getString("message") : null))
        .build();
      ZopimChat.SessionConfig config = new ZopimChat.SessionConfig()
              .preChatForm(preChatForm);

      ZopimChatActivity.startActivity(context, config);
    } else {
      context.startActivity(new Intent(context, ZopimChatActivity.class));
    }
  }

  private PreChatForm.Field mapStringToPreChatFormFieldOption(String str) throws Exception {
    if (str == null) {
      return PreChatForm.Field.NOT_REQUIRED;
    }
    if (str.contentEquals("required")) {
      return PreChatForm.Field.REQUIRED_EDITABLE;
    }
    if (str.contentEquals("optional")) {
      return PreChatForm.Field.OPTIONAL_EDITABLE;
    }

    throw new Exception("Got unknown prechat form option");
  }
}
