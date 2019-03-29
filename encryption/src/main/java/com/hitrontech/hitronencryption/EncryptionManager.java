package com.hitrontech.hitronencryption;

import android.content.Context;
import android.util.Log;

public class EncryptionManager implements EncryptionImpl {

  private String mTag = "Hitron:" + getClass().getSimpleName();

  private EncryptionManager() {

  }

  public static EncryptionImpl getInstance() {
    return EncryptionManagerHolder.sInstance;
  }

  @Override
  public String base64Encoder(String key, String text) {
    return AesUtils.encrypt(key, text);
  }

  @Override
  public String base64Encoder(Context context, String key, String text) {
    String packageName = null;
    if (context == null) {
      Log.w(mTag, "Context is NULL");
    } else {
      packageName = context.getPackageName();
    }
    return base64EncoderByAppId(packageName, key, text);
  }

  @Override
  public String base64EncoderByAppId(String applicationId, String key, String text) {
    StringBuffer sb = new StringBuffer();
    // sb.append(applicationId);
    sb.append(key);
    String securityKey = sb.toString();
    return base64Encoder(securityKey, text);
  }

  @Override
  public String base64Decoder(String key, String encoderStr) {
    return AesUtils.decrypt(key, encoderStr);
  }

  @Override
  public String base64Decoder(Context context, String key, String encoderStr) {
    String packageName = null;
    if (context == null) {
      Log.w(mTag, "Context is NULL");
    } else {
      packageName = context.getPackageName();
    }
    return base64DecoderByAppId(packageName, key, encoderStr);
  }

  @Override
  public String base64DecoderByAppId(String applicationId, String key, String encoderStr) {
    StringBuffer sb = new StringBuffer();
    // sb.append(applicationId);
    sb.append(key);
    String securityKey = sb.toString();
    return base64Decoder(securityKey, encoderStr);
  }

  @Override
  public String md5Str(String text) {
    return Md5Utils.md5Str(text);
  }

  @Override
  public String md5File(String filePath) {
    return Md5Utils.getFileMd5(filePath);
  }

  private static class EncryptionManagerHolder {

    private static EncryptionImpl sInstance = new EncryptionManager();
  }
}
