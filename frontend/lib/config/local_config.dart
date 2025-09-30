// 로컬 개발 설정 파일
// 이 파일은 .gitignore에 포함되어 Git에 올라가지 않습니다.
// 각자의 개발 환경에 맞게 설정하세요.

class LocalConfig {
  // 로컬 개발 서버 IP 주소
  // iOS 시뮬레이터: http://localhost:8080
  // Android 에뮬레이터: http://10.0.2.2:8080
  // 실제 기기: http://192.168.x.x:8080 (서버와 같은 WiFi)
  static const String devServerUrl = 'http://localhost:8080';
}
