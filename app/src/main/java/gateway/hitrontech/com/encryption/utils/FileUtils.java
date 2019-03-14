package gateway.hitrontech.com.encryption.utils;

import android.os.Environment;

public class FileUtils {

  static final String ORIGIN = "origin";

  static final String TARGET = "target";

  public static String getCachePath() {

    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
        || !Environment.isExternalStorageRemovable()) {
      return WhichContext.getInstance().getExternalCacheDir().getPath();
    } else {
      return WhichContext.getInstance().getCacheDir().getPath();
    }

  }

  public static String getOrigin() {
    return getCachePath() + "/" + ORIGIN;
  }

  public static String getTarget() {
    return getCachePath() + "/" + TARGET;
  }


  public static String getTargetXls() {
    return getCachePath() + "/" + TARGET + ".xls";
  }

}
