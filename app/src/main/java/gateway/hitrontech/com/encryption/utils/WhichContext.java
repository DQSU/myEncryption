package gateway.hitrontech.com.encryption.utils;

import android.app.Application;
import android.content.Context;

public class WhichContext extends Application {

  private static Context mContext;

  static Context getInstance() {
    return mContext;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    WhichContext.mContext = getApplicationContext();
  }
}
