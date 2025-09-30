# For Irion - Full Stack Project

버츄얼 유튜버 이리온을 응원하기 위한 비상업적 팬 프로젝트입니다.

## 📁 프로젝트 구조

```
for-irion/
├── frontend/          # Flutter 앱
│   ├── lib/
│   ├── android/
│   ├── ios/
│   └── pubspec.yaml
│
├── backend/           # Spring + MyBatis API 서버
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
└── README.md         # 이 파일
```

## 🛠️ 기술 스택

### Frontend
- **Framework**: Flutter (Dart)
- **UI**: Material 3 Design, Glassmorphism
- **State Management**: setState
- **Animations**: AnimationController, Custom Painters
- **Push Notifications**: Firebase Cloud Messaging

### Backend
- **Framework**: Spring Framework + MyBatis
- **Database**: MySQL
- **API**: Chzzk Unofficial API
- **Build Tool**: Maven

## 🚀 빠른 시작

### Frontend (Flutter App)

```bash
cd frontend
flutter pub get
flutter run
```

자세한 내용은 [frontend/README.md](frontend/README.md)를 참고하세요.

### Backend (Spring API Server)

```bash
cd backend

# 1. application.properties 설정
cp src/main/resources/application.properties.example src/main/resources/application.properties
# application.properties에 치지직 채널 ID 입력

# 2. 서버 실행
mvn clean install
mvn tomcat7:run
```

자세한 내용은 [backend/README.md](backend/README.md)를 참고하세요.

## 📡 API 엔드포인트

기본 URL: `http://localhost:8080`

- `GET /api/stream/live-status` - 방송 상태 확인
- `GET /api/stream/hot-clips` - 최근 30일 다시보기
- `GET /api/schedules` - 방송 일정

## 🎨 주요 기능

- ❄️ 실시간 방송 상태 확인 (아이스/사쿠라 테마)
- 📺 최근 다시보기 목록
- 🔔 방송 알림 (FCM)
- 📅 방송 일정
- 🎭 이모티콘 갤러리
- 💌 개발자 연락처

## ⚠️ 중요 사항

### GitHub에 올리면 안 되는 파일들

다음 파일들은 **절대 GitHub에 올리면 안 됩니다**:

#### Backend
- `backend/src/main/resources/application.properties` (채널 ID 포함)
- `backend/src/main/resources/jdbc.properties` (DB 정보)
- `.env` 파일들
- 로그 파일 (`*.log`)

#### Frontend
- `frontend/android/app/google-services.json` (Firebase 설정)
- `frontend/ios/Runner/GoogleService-Info.plist` (Firebase 설정)
- `frontend/lib/firebase_options.dart` (Firebase 자동 생성)
- `frontend/lib/config/local_config.dart` (로컬 IP 주소)

### Git 커밋 전 확인

```bash
git status

# application.properties나 Firebase 설정 파일이 나타나면 안 됩니다!
# 만약 나타난다면:
git rm --cached backend/src/main/resources/application.properties
git rm --cached frontend/android/app/google-services.json
```

## 🔒 보안 및 라이선스

- 이 프로젝트는 치지직 비공식 API를 사용합니다
- **비상업적 팬 프로젝트**입니다. 상업적 사용을 금지합니다
- API 사용량이 과도하면 차단될 수 있습니다
- 개인정보는 수집하지 않습니다

## 📱 배포

### Frontend
- Android: Google Play Console 등록비 ($25 1회)
- iOS: Apple Developer Program 연간비 ($99/년)

### Backend
- **추천**: Oracle Cloud Always Free Tier (무료)
  - VM.Standard.E2.1.Micro (2개 인스턴스)
  - 200GB 블록 스토리지
  - 10TB 아웃바운드 트래픽
- MySQL: Oracle Cloud MySQL 또는 PlanetScale Free Tier

## 👨‍💻 개발자

**SooinDev**
- Email: alwayswithsound@gmail.com
- GitHub: [@SooinDev](https://github.com/SooinDev)

---

Made with ❄️ & 🌸 for Irion
