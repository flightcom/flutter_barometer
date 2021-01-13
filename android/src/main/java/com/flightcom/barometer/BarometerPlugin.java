package com.flightcom.barometer;

import androidx.annotation.NonNull;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.embedding.engine.plugins.FlutterPlugin;

/** BarometerPlugin */
// public class BarometerPlugin implements FlutterPlugin, MethodCallHandler {
public class BarometerPlugin implements FlutterPlugin {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  // private MethodChannel channel;

  private static final String BAROMETER_CHANNEL_NAME = "com.flightcom.barometer/sensors/barometer";

  private EventChannel barometerChannel;


  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    // channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "barometer");
    // channel.setMethodCallHandler(this);

    final Context context = flutterPluginBinding.getApplicationContext();
    setupEventChannels(context, flutterPluginBinding.getBinaryMessenger());    
  }

  // @Override
  // public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
  //   if (call.method.equals("getPlatformVersion")) {
  //     result.success("Android " + android.os.Build.VERSION.RELEASE);
  //   } else {
  //     result.notImplemented();
  //   }
  // }

  private void setupEventChannels(Context context, BinaryMessenger messenger) {
    barometerChannel = new EventChannel(messenger, BAROMETER_CHANNEL_NAME);
    final StreamHandlerImpl pressureStreamHandler =
        new StreamHandlerImpl(
            (SensorManager) context.getSystemService(context.SENSOR_SERVICE),
            Sensor.TYPE_PRESSURE);
            barometerChannel.setStreamHandler(pressureStreamHandler);
  }


  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    // channel.setMethodCallHandler(null);

    teardownEventChannels();
  }

  private void teardownEventChannels() {
    barometerChannel.setStreamHandler(null);
  }  

}
