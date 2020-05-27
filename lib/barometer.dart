import 'dart:async';
import 'package:flutter/services.dart';

const EventChannel _barometerEventChannel =
    EventChannel('com.flightcom.barometer/sensors/barometer');

/// Discrete reading from an accelerometer. Accelerometers measure the velocity
/// of the device. Note that these readings include the effects of gravity. Put
/// simply, you can use accelerometer readings to tell if the device is moving in
/// a particular direction.
class BarometerEvent {
  /// Contructs an instance with the given [x], [y], and [z] values.
  BarometerEvent(this.pressure);

  /// Acceleration force along the x axis (including gravity) measured in m/s^2.
  ///
  /// When the device is held upright facing the user, positive values mean the
  /// device is moving to the right and negative mean it is moving to the left.
  final double pressure;

  @override
  String toString() => '[Barometer Event (pressure: $pressure)]';
}

Stream<BarometerEvent> _barometerEvents;

/// A broadcast stream of events from the device barometer.
Stream<BarometerEvent> get barometerEvents {
  if (_barometerEvents == null) {
    _barometerEvents = _barometerEventChannel
        .receiveBroadcastStream()
        .map((dynamic event) => BarometerEvent(event));
  }
  return _barometerEvents;
}
