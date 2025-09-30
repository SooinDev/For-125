package com.foririon.project.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseInitializer {

  @PostConstruct
  public void initialize() {
    try {
      String serviceAccountKeyPath = "for-irion-firebase-adminsdk-fbsvc-5962b46b9e.json";

      InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream(serviceAccountKeyPath);

      if (serviceAccount == null) {
        throw new IOException(serviceAccountKeyPath + " 파일을 찾을 수 없습니다.");
      }

      FirebaseOptions options = FirebaseOptions.builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
        System.out.println("Firebase App has been initialized");
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
