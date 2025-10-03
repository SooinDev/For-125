import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/foundation.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import '../config/app_config.dart';

// 백그라운드 메시지 핸들러 (top-level function이어야 함)
@pragma('vm:entry-point')
Future<void> _firebaseMessagingBackgroundHandler(RemoteMessage message) async {
  print('[FCM] 백그라운드 메시지 수신: ${message.messageId}');
  print('[FCM] 제목: ${message.notification?.title}');
  print('[FCM] 내용: ${message.notification?.body}');
}

class FCMService {
  static final FCMService _instance = FCMService._internal();
  factory FCMService() => _instance;
  FCMService._internal();

  final FirebaseMessaging _fcm = FirebaseMessaging.instance;
  String? _fcmToken;

  String? get fcmToken => _fcmToken;

  /// FCM 초기화
  Future<void> initialize() async {
    try {
      // 1. 알림 권한 요청 (iOS)
      NotificationSettings settings = await _fcm.requestPermission(
        alert: true,
        announcement: false,
        badge: true,
        carPlay: false,
        criticalAlert: false,
        provisional: false,
        sound: true,
      );

      print('[FCM] 알림 권한 상태: ${settings.authorizationStatus}');

      if (settings.authorizationStatus == AuthorizationStatus.authorized) {
        print('[FCM] 알림 권한 승인됨');
      } else if (settings.authorizationStatus == AuthorizationStatus.provisional) {
        print('[FCM] 임시 알림 권한 승인됨');
      } else {
        print('[FCM] 알림 권한 거부됨');
        return;
      }

      // 2. FCM 토큰 가져오기
      _fcmToken = await _fcm.getToken();
      print('[FCM] 토큰: $_fcmToken');

      if (_fcmToken != null) {
        await _saveFCMTokenLocally(_fcmToken!);
        await _sendTokenToServer(_fcmToken!);
      }

      // 3. 토큰 갱신 리스너
      _fcm.onTokenRefresh.listen((newToken) {
        print('[FCM] 토큰 갱신: $newToken');
        _fcmToken = newToken;
        _saveFCMTokenLocally(newToken);
        _sendTokenToServer(newToken);
      });

      // 4. 백그라운드 메시지 핸들러 등록
      FirebaseMessaging.onBackgroundMessage(_firebaseMessagingBackgroundHandler);

      // 5. 포그라운드 메시지 핸들러
      FirebaseMessaging.onMessage.listen((RemoteMessage message) {
        print('[FCM] 포그라운드 메시지 수신: ${message.messageId}');
        print('[FCM] 제목: ${message.notification?.title}');
        print('[FCM] 내용: ${message.notification?.body}');
        print('[FCM] 데이터: ${message.data}');

        // 포그라운드에서도 알림을 표시하고 싶다면 여기서 로컬 알림 표시
        _showForegroundNotification(message);
      });

      // 6. 알림 클릭 핸들러 (앱이 백그라운드에 있을 때)
      FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage message) {
        print('[FCM] 알림 클릭으로 앱 열림: ${message.messageId}');
        _handleNotificationClick(message);
      });

      // 7. 앱이 종료된 상태에서 알림을 클릭해서 열린 경우
      RemoteMessage? initialMessage = await _fcm.getInitialMessage();
      if (initialMessage != null) {
        print('[FCM] 앱 종료 상태에서 알림으로 시작: ${initialMessage.messageId}');
        _handleNotificationClick(initialMessage);
      }

      print('[FCM] 초기화 완료');
    } catch (e) {
      print('[FCM] 초기화 실패: $e');
    }
  }

  /// FCM 토큰을 로컬에 저장
  Future<void> _saveFCMTokenLocally(String token) async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setString('fcm_token', token);
    print('[FCM] 토큰 로컬 저장 완료');
  }

  /// FCM 토큰을 서버에 전송
  Future<void> _sendTokenToServer(String token) async {
    try {
      final url = '${AppConfig.baseUrl}/api/notifications/token';
      final response = await http.post(
        Uri.parse(url),
        headers: {'Content-Type': 'application/json'},
        body: jsonEncode({'fcmToken': token}),
      );

      if (response.statusCode == 200) {
        print('[FCM] 토큰 서버 전송 완료');
      } else {
        print('[FCM] 토큰 서버 전송 실패: ${response.statusCode}');
      }
    } catch (e) {
      print('[FCM] 토큰 서버 전송 에러: $e');
    }
  }

  /// 포그라운드 알림 표시
  void _showForegroundNotification(RemoteMessage message) {
    // 여기서는 단순히 로그만 출력
    // 실제로는 flutter_local_notifications 패키지를 사용해서 로컬 알림 표시
    print('[FCM] 포그라운드 알림 표시: ${message.notification?.title}');
  }

  /// 알림 클릭 핸들러
  void _handleNotificationClick(RemoteMessage message) {
    print('[FCM] 알림 클릭 처리');
    final data = message.data;

    // 알림 타입에 따라 다른 화면으로 이동
    if (data['type'] == 'live_start') {
      print('[FCM] 방송 시작 알림 - 홈 화면으로 이동');
      // Navigator를 사용해서 홈 화면으로 이동
    } else if (data['type'] == 'new_replay') {
      print('[FCM] 새 다시보기 알림 - 다시보기 섹션으로 이동');
    } else if (data['type'] == 'schedule') {
      print('[FCM] 일정 알림 - 일정 페이지로 이동');
    }
  }

  /// 특정 토픽 구독
  Future<void> subscribeToTopic(String topic) async {
    try {
      await _fcm.subscribeToTopic(topic);
      print('[FCM] 토픽 구독 성공: $topic');
    } catch (e) {
      print('[FCM] 토픽 구독 실패: $e');
    }
  }

  /// 특정 토픽 구독 해제
  Future<void> unsubscribeFromTopic(String topic) async {
    try {
      await _fcm.unsubscribeFromTopic(topic);
      print('[FCM] 토픽 구독 해제 성공: $topic');
    } catch (e) {
      print('[FCM] 토픽 구독 해제 실패: $e');
    }
  }

  /// 알림 설정에 따라 토픽 구독 관리
  Future<void> updateTopicSubscriptions({
    required bool liveStart,
    required bool liveEnd,
    required bool newReplay,
    required bool schedule,
  }) async {
    // 방송 시작 알림
    if (liveStart) {
      await subscribeToTopic('live_start');
    } else {
      await unsubscribeFromTopic('live_start');
    }

    // 방송 종료 알림
    if (liveEnd) {
      await subscribeToTopic('live_end');
    } else {
      await unsubscribeFromTopic('live_end');
    }

    // 새 다시보기 알림
    if (newReplay) {
      await subscribeToTopic('new_replay');
    } else {
      await unsubscribeFromTopic('new_replay');
    }

    // 일정 알림
    if (schedule) {
      await subscribeToTopic('schedule');
    } else {
      await unsubscribeFromTopic('schedule');
    }
  }
}
