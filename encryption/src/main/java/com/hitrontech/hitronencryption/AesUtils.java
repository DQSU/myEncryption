package com.hitrontech.hitronencryption;

import android.text.TextUtils;
import android.util.Log;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by admin on 2017/6/6.
 */

public class AesUtils {

  private final static String HEX = "0123456789ABCDEF";
  private static final String CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding";//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
  private static final String AES = "AES";//AES 加密

  // 对密钥进行处理
  // https://android-developers.googleblog.com/2016/06/security-crypto-provider-deprecated-in.html
  // todo 这里的arrb的长度可能需要改变，否则无论key如何变化，cipherText将不会改变，因为applicationId超过了arrb的长度，无论key取何值，arrb内容都不会改变；
  // 或者不改变arrb的长度，而修改此处的算法，保证得到的rawkey是正确的
  private static byte[] getRawKey(String seed) throws Exception {
    byte[] arrBTmp = seed.getBytes();
    byte[] arrB = new byte[16];
    for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
      arrB[i] = arrBTmp[i];
    }
    SecretKeySpec skeySpec = new SecretKeySpec(arrB, AES);
    return skeySpec.getEncoded();
  }

  /*
   * 加密
   */
  static String encrypt(String key, String cleartext) {
    if (TextUtils.isEmpty(cleartext)) {
      return cleartext;
    }
    try {
      byte[] result = encrypt(key, cleartext.getBytes());
      return Base64Encoder.encode(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /*
   * 加密
   */
  private static byte[] encrypt(String key, byte[] clear) throws Exception {
    byte[] raw = getRawKey(key);
    Log.e("AesUtils", new String(raw));

    SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
    Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
    cipher
        .init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
    byte[] encrypted = cipher.doFinal(clear);
    return encrypted;
  }

  static String decrypt(String key, String encrypted) {
    if (TextUtils.isEmpty(encrypted)) {
      return encrypted;
    }
    try {
      byte[] enc = Base64Decoder.decodeToBytes(encrypted);
      Log.i("123", "===>>enc  " + enc);
      byte[] result = decrypt(key, enc);
      return new String(result);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  private static byte[] decrypt(String key, byte[] encrypted) throws Exception {
    byte[] raw = getRawKey(key);
    SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
    Cipher cipher = Cipher.getInstance(CBC_PKCS5_PADDING);
    cipher
        .init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
    byte[] decrypted = cipher.doFinal(encrypted);
    return decrypted;
  }

  //二进制转字符
  static String toHex(byte[] buf) {
    if (buf == null) {
      return "";
    }
    StringBuffer result = new StringBuffer(2 * buf.length);
    for (int i = 0; i < buf.length; i++) {
      appendHex(result, buf[i]);
    }
    return result.toString();
  }

  private static void appendHex(StringBuffer sb, byte b) {
    sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
  }
}
