import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'growdeck_playtime_plugin_method_channel.dart';

abstract class GrowdeckPlaytimePluginPlatform extends PlatformInterface {
  /// Constructs a GrowdeckPlaytimePluginPlatform.
  GrowdeckPlaytimePluginPlatform() : super(token: _token);

  static final Object _token = Object();

  static GrowdeckPlaytimePluginPlatform _instance = MethodChannelGrowdeckPlaytimePlugin();

  /// The default instance of [GrowdeckPlaytimePluginPlatform] to use.
  ///
  /// Defaults to [MethodChannelGrowdeckPlaytimePlugin].
  static GrowdeckPlaytimePluginPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [GrowdeckPlaytimePluginPlatform] when
  /// they register themselves.
  static set instance(GrowdeckPlaytimePluginPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
