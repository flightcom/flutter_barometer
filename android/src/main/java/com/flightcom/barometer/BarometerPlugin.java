package com.flightcom.barometer;

import androidx.annotation.NonNull;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
// import io.flutter.plugin.common.MethodCall;
// import io.flutter.plugin.common.MethodChannel;
// import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
// import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.EventChannel;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.PluginRegistry.Registrar;

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

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    // final MethodChannel channel = new MethodChannel(registrar.messenger(), "barometer");
    // channel.setMethodCallHandler(new BarometerPlugin());

    BarometerPlugin plugin = new BarometerPlugin();
    plugin.setupEventChannels(registrar.context(), registrar.messenger());
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
