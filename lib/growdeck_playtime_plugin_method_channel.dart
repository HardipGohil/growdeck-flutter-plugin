import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'growdeck_playtime_plugin_platform_interface.dart';

/// An implementation of [GrowdeckPlaytimePluginPlatform] that uses method channels.
class MethodChannelGrowdeckPlaytimePlugin extends GrowdeckPlaytimePluginPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  static final methodChannel = const MethodChannel('growdeck_playtime_plugin');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  static Future<void> initialize({
    required String appId,
    required String userId,
    required String secretKey,
  }) async {
    await methodChannel.invokeMethod('initialize', {
      'appId': appId,
      'userId': userId,
      'secretKey': secretKey,
    });
  }

  static Future<void> show() async {
    await methodChannel.invokeMethod('show');
  }
}
