import 'local_config.dart';

enum Environment { development, production }

class AppConfig {
  static Environment _environment = Environment.development;

  static void setEnvironment(Environment env) {
    _environment = env;
  }

  static String get baseUrl {
    switch (_environment) {
      case Environment.development:
        return LocalConfig.devServerUrl;
      case Environment.production:
        return 'https://your-production-server.com';
    }
  }

  static String get healthCheckUrl => '$baseUrl/api/health';
  static String get liveStatusUrl => '$baseUrl/api/stream/is-live';
  static String get notificationRegisterUrl => '$baseUrl/api/notifications/register';

  static bool get isProduction => _environment == Environment.production;
  static bool get isDevelopment => _environment == Environment.development;
}