
import 'growdeck_playtime_plugin_platform_interface.dart';

class GrowdeckPlaytimePlugin {
  Future<String?> getPlatformVersion() {
    return GrowdeckPlaytimePluginPlatform.instance.getPlatformVersion();
  }

}
