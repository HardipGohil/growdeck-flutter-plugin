package io.growdeck.growdeck_playtime_plugin;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import io.growdeck.sdk.playtime.GrowDeck;
import io.growdeck.sdk.playtime.Config;
import io.growdeck.sdk.playtime.listener.OnInitResultListener;


/** GrowdeckPlaytimePlugin */
public class GrowdeckPlaytimePlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private Activity activity;



  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "growdeck_playtime_plugin");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }else if (activity != null && call.method.equals("initialize")) {
        String appId = call.argument("appId");
        String userId = call.argument("userId");
        String secretKey = call.argument("secretKey");

        Config config = new Config.Builder((Context) activity)
                .setAppID(appId)
                .setUserID(userId)
                .setSecretKey(secretKey)
                .build();

        GrowDeck.init(config, new OnInitResultListener() {
          @Override
          public void onSuccess() {
            result.success("GrowDeck Initialized");
          }

          @Override
          public void onFailed(String s) {
            Log.e("GrowDeck Init Error", s);
            result.error("INIT_FAILED", s, null);
          }
        });

      } else if (activity != null && call.method.equals("show")) {
        GrowDeck.show();
        result.success("Playtime Wall Shown");
    } else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }

  @Override
  public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }

  @Override
  public void onDetachedFromActivityForConfigChanges() {

  }

  @Override
  public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
    activity = binding.getActivity();
  }


  @Override
  public void onDetachedFromActivity() {
    activity = null;
  }
}
