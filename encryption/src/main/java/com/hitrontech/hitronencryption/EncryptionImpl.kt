package com.hitrontech.hitronencryption

import android.content.Context

interface EncryptionImpl {

    fun base64Encoder(key: String, text: String): String

    fun base64Encoder(context: Context?, key: String, text: String): String

    fun base64EncoderByAppId(applicationId: String?, key: String, text: String): String

    fun base64Decoder(key: String, encoderStr: String): String?

    fun base64Decoder(context: Context?, key: String, encoderStr: String): String?

    fun base64DecoderByAppId(applicationId: String?, key: String, encoderStr: String): String?

    fun md5File(filePath: String): String?

    fun md5Str(text: String): String?

}
