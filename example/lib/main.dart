import 'package:flutter/material.dart';
import 'dart:async';

import 'package:barometer/barometer.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
	StreamSubscription<BarometerEvent> _streamSubscription;
	double _barometerValue;

  @override
  void dispose() {
		super.dispose();
		_streamSubscription.cancel();
  }

  @override
  void initState() {
    super.initState();
    _streamSubscription = barometerEvents.listen((BarometerEvent event) {
      setState(() {
        _barometerValue = event.pressure;
      });
    });
  }

  @override
  Widget build(BuildContext context) {
		final String accelerometer = _barometerValue?.toStringAsFixed(3);
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Current pressure: $accelerometer\n'),
        ),
      ),
    );
  }
}
