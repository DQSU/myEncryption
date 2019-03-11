package gateway.hitrontech.com.encryption.utils;

import android.content.Context;
import android.content.SharedPreferences;
import gateway.hitrontech.com.encryption.R;

public class SharePreManager {

  private static final String mPreName = "pre_sharePre_encryption";
  private static SharePreManager sPreManager;
  private static SharedPreferences sSharedPre;
  private final String mPassword = "password";

  private SharePreManager() {
  }

  static SharePreManager getInstance() {
    synchronized (SharePreManager.class) {
      if (sSharedPre == null) {
        sPreManager = new SharePreManager();
        sSharedPre = WhichContext.getInstance().getSharedPreferences(mPreName,
            Context.MODE_PRIVATE);
      }
    }
    return sPreManager;
  }

//  public void updatePwd(String pwd) {
//    SharedPreferences.Editor editor = sSharedPre.edit();
//    if (getPwd() != null) {
//      getPwd().add(pwd);
//    }
//    editor.putStringSet(mPassword, getPwd());
//    editor.apply();
//    editor.clear();
//  }

//  public static Set<String> getPwd() {
//    return sSharedPre.getStringSet(mPassword, new TreeSet<String>());
//  }

  public static String getAppId() {
    return WhichContext.getInstance().getString(R.string.applicationId);
  }
}
