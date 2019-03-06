package com.hitrontech.hitronencryption;

import android.content.Context;

public interface EncryptionImpl {

    String base64Encoder(String key, String text);

    String base64Encoder(Context context, String key, String text);

    String base64EncoderByAppId(String applicationId, String key, String text);

    String base64Decoder(String key, String encoderStr);

    String base64Decoder(Context context, String key, String encoderStr);

    String base64DecoderByAppId(String applicationId, String key, String encoderStr);

    String md5File(String filePath);

    String md5Str(String text);

}
