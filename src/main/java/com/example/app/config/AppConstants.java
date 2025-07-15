package com.example.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConstants {

  // 调试模式
  private boolean debugEnabled;

  // 缓存配置
  private Cache cache = new Cache();

  // 安全配置
  private Security security = new Security();

  // 监控配置
  private boolean monitoringEnabled;

  // getters and setters
  public boolean isDebugEnabled() {
    return debugEnabled;
  }

  public void setDebugEnabled(boolean debugEnabled) {
    this.debugEnabled = debugEnabled;
  }

  public static class Cache {
    private boolean enabled;
    private int ttl;

    // getters and setters
  }

  public static class Security {
    private Cors cors = new Cors();
    private Ssl ssl = new Ssl();

    public static class Cors {
      private boolean enabled;
      // getters and setters
    }

    public static class Ssl {
      private boolean enabled;
      // getters and setters
    }
  }
}