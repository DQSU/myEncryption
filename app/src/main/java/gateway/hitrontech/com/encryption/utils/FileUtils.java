package gateway.hitrontech.com.encryption.utils;

import android.os.Environment;
import java.io.File;

public class FileUtils {

  static final String ORIGIN = "origin";

  static final String TARGET = "target";

  static final String RESULT = "result";

  public static String getCachePath() {

    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
        || !Environment.isExternalStorageRemovable()) {
      return WhichContext.getInstance().getExternalCacheDir().getPath();
    } else {
      return WhichContext.getInstance().getCacheDir().getPath();
    }

  }

  public static String getResultPath() {
    File result = new File(getCachePath().replace("cache", "") + "/" + RESULT);
    if (!result.exists()) {
      result.mkdir();
    }
    return result.getPath();
  }


  public static String getOrigin() {
    return getResultPath() + "/" + ORIGIN;
  }

  public static String getTarget() {
    return getResultPath() + "/" + TARGET;
  }


  public static String getTargetXls() {
    return getResultPath() + "/" + TARGET + ".xls";
  }

}
