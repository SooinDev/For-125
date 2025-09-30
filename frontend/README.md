# For Irion Flutter App

이리온 팬을 위한 Flutter 앱입니다.

## 🎨 주요 기능

- ❄️ 실시간 방송 상태 확인 (아이스/사쿠라 테마)
- 📺 최근 다시보기 목록
- 🔔 방송 알림 (FCM)
- 📅 방송 일정
- 🎭 이모티콘 갤러리
- 💌 개발자 연락처

## 🛠️ 기술 스택

- **Framework**: Flutter (Dart)
- **UI**: Material 3 Design, Glassmorphism
- **State Management**: setState
- **Animations**: AnimationController, Custom Painters
- **Push Notifications**: Firebase Cloud Messaging

## 🚀 설정 방법

### 1. Firebase 설정

이 프로젝트는 Firebase를 사용합니다. 본인의 Firebase 프로젝트를 생성하고 설정 파일을 추가해야 합니다:

1. [Firebase Console](https://console.firebase.google.com/)에서 새 프로젝트 생성
2. Android 앱 추가:
   - `android/app/google-services.json` 다운로드 후 해당 위치에 배치
3. iOS 앱 추가:
   - `ios/Runner/GoogleService-Info.plist` 다운로드 후 해당 위치에 배치
4. macOS 앱 추가 (선택사항):
   - `macos/Runner/GoogleService-Info.plist` 다운로드 후 해당 위치에 배치

### 2. 로컬 설정 파일 생성

로컬 개발 서버의 IP 주소를 설정해야 합니다:

1. `lib/config/local_config.example.dart`를 복사
2. `lib/config/local_config.dart`로 이름 변경
3. `devServerUrl` 값을 본인의 로컬 서버 IP로 수정:

```dart
class LocalConfig {
  static const String devServerUrl = 'http://192.168.0.100:8080';
}
```

### 3. 의존성 설치

```bash
flutter pub get
```

### 4. 실행

```bash
flutter run
```

## ⚠️ Git에 올리지 말아야 할 파일들

`.gitignore`에 다음 파일들이 자동으로 제외되도록 설정되어 있습니다:

- `**/google-services.json` - Android Firebase 설정
- `**/GoogleService-Info.plist` - iOS/macOS Firebase 설정
- `lib/firebase_options.dart` - Firebase 자동 생성 파일
- `lib/config/local_config.dart` - 로컬 개발 설정 파일 (IP 주소 포함)
- `.env*` - 환경 변수 파일

## 📱 빌드

### Android

```bash
flutter build apk --release
# 또는 App Bundle
flutter build appbundle --release
```

### iOS

```bash
flutter build ios --release
```

## 📂 프로젝트 구조

```
lib/
├── main.dart                    # 앱 진입점
├── pages/
│   ├── developer_page.dart      # 개발자 정보 페이지
│   └── ...
├── config/
│   ├── local_config.dart        # 로컬 설정 (git 제외)
│   └── local_config.example.dart # 설정 템플릿
└── ...
```

## 🎨 디자인 시스템

이 앱은 **아이스(얼음)와 사쿠라(벚꽃)** 테마를 기반으로 합니다:

- **색상**:
  - 아이스 블루 (#E3F2FD)
  - 사쿠라 핑크 (#FCE4EC)
- **UI 스타일**: Glassmorphism (BackdropFilter 블러 효과)
- **애니메이션**: 호흡 효과, 펄스 효과, 커스텀 페인터

## 주의사항

- Firebase 설정 파일과 로컬 IP 주소는 절대 Git에 올리지 마세요
- 각자의 환경에 맞게 설정 파일을 생성해야 합니다
- 백엔드 서버가 실행 중이어야 앱이 정상 작동합니다

---

Made with ❄️ & 🌸 for Irion
