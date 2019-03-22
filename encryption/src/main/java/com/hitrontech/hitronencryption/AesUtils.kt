package com.hitrontech.hitronencryption

import android.text.TextUtils
import android.util.Log
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * Created by admin on 2017/6/6.
 */

object AesUtils {

    private const val HEX = "0123456789ABCDEF"
    private const val CBC_PKCS5_PADDING = "AES/CBC/PKCS5Padding"//AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
    private const val AES = "AES"//AES 加密

    // 对密钥进行处理
    // https://android-developers.googleblog.com/2016/06/security-crypto-provider-deprecated-in.html
    // todo 这里的arrb的长度可能需要改变，否则无论key如何变化，cipherText将不会改变，因为applicationId超过了arrb的长度，无论key取何值，arrb内容都不会改变；
    // 或者不改变arrb的长度，而修改此处的算法，保证得到的rawkey是正确的
    @Throws(Exception::class)
    private fun getRawKey(seed: String): ByteArray {
        val arrBTmp = seed.toByteArray()
        val arrB = ByteArray(16)
        var i = 0
        while (i < arrBTmp.size && i < arrB.size) {
            arrB[i] = arrBTmp[i]
            i++
        }
        val skeySpec = SecretKeySpec(arrB, AES)
        return skeySpec.encoded
    }

    /*
   * 加密
   */
    internal fun encrypt(key: String, cleartext: String): String? {
        if (TextUtils.isEmpty(cleartext)) {
            return cleartext
        }
        try {
            val result = encrypt(key, cleartext.toByteArray())
            return Base64Encoder.encode(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    /*
   * 加密
   */
    @Throws(Exception::class)
    private fun encrypt(key: String, clear: ByteArray): ByteArray {
        val raw = getRawKey(key)
        Log.e("AesUtils", String(raw))

        val skeySpec = SecretKeySpec(raw, AES)
        val cipher = Cipher.getInstance(CBC_PKCS5_PADDING)
        cipher
                .init(Cipher.ENCRYPT_MODE, skeySpec, IvParameterSpec(ByteArray(cipher.blockSize)))
        return cipher.doFinal(clear)
    }

    internal fun decrypt(key: String, encrypted: String): String? {
        if (TextUtils.isEmpty(encrypted)) {
            return encrypted
        }
        try {
            val enc = Base64Decoder.decodeToBytes(encrypted)
            Log.i("123", "===>>enc  $enc")
            val result = decrypt(key, enc)
            return String(result)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }

    @Throws(Exception::class)
    private fun decrypt(key: String, encrypted: ByteArray): ByteArray {
        val raw = getRawKey(key)
        val skeySpec = SecretKeySpec(raw, AES)
        val cipher = Cipher.getInstance(CBC_PKCS5_PADDING)
        cipher
                .init(Cipher.DECRYPT_MODE, skeySpec, IvParameterSpec(ByteArray(cipher.blockSize)))
        return cipher.doFinal(encrypted)
    }

    //二进制转字符
    internal fun toHex(buf: ByteArray?): String {
        if (buf == null) {
            return ""
        }
        val result = StringBuffer(2 * buf.size)
        for (i in buf.indices) {
            appendHex(result, buf[i])
        }
        return result.toString()
    }

    private fun appendHex(sb: StringBuffer, b: Byte) {
        sb.append(HEX[b.toInt() shr 4 and 0x0f]).append(HEX[b.toInt() and 0x0f])
    }
}
