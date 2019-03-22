package com.hitrontech.hitronencryption

import android.content.Context
import android.util.Log

class EncryptionManager private constructor() : EncryptionImpl {
    override fun base64Encoder(key: String, text: String): String {
        return AesUtils.encrypt(key, text)!!
    }

    override fun base64Encoder(context: Context?, key: String, text: String): String {
        val packageName = if (context == null) {
            Log.w(mTag, "Context is null")
            null
        } else {
            context.packageName
        }

        return base64EncoderByAppId(packageName, key, text)

    }

    override fun base64EncoderByAppId(applicationId: String?, key: String, text: String): String {
        val sb = StringBuilder()
        sb.append(applicationId)
        sb.append(key)
        return base64Encoder(sb.toString(), text)
    }

    override fun base64Decoder(key: String, encoderStr: String): String? {
        return AesUtils.decrypt(key, encoderStr)
    }

    override fun base64Decoder(context: Context?, key: String, encoderStr: String): String? {
        val packageName = if (context == null) {
            Log.w(mTag, "Context is null")
            null
        } else {
            context.packageName
        }

        return base64DecoderByAppId(packageName, key, encoderStr)
    }

    override fun base64DecoderByAppId(applicationId: String?, key: String, encoderStr: String): String? {
        val sb = StringBuilder()
        sb.append(applicationId)
        sb.append(key)
        return base64Decoder(sb.toString(), encoderStr)
    }

    override fun md5File(filePath: String): String? {
        return Md5Utils.getFileMd5(filePath)
    }

    override fun md5Str(text: String): String? {
        return Md5Utils.md5Str(text)
    }

    private val mTag = "Hitron:" + javaClass.simpleName

    companion object {
        val instance = EncryptionManager()

    }
}
