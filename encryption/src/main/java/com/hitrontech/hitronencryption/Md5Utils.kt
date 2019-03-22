package com.hitrontech.hitronencryption


import android.util.Log
import java.io.FileInputStream
import java.io.InputStream
import java.security.MessageDigest

internal object Md5Utils {

    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F')

    fun getFileMd5(filePath: String?): String? {
        if (filePath == null) {
            Log.w("Hitron:MD5", "getFileMd5(), filePath is null")
            return null
        }
        return md5sum(filePath)
    }

    private fun toHexString(bytes: ByteArray): String {
        val sb = StringBuilder(bytes.size * 2)
        for (b in bytes) {
            sb.append(HEX_DIGITS[(b.toInt() and 0xf0).ushr(4)])
            sb.append(HEX_DIGITS[b.toInt() and 0x0f])
        }
        return sb.toString()
    }

    private fun md5sum(filename: String): String? {
        val fis: InputStream
        val buffer = ByteArray(1024)
        var numRead: Int
        val md5: MessageDigest
        try {
            fis = FileInputStream(filename)
            md5 = MessageDigest.getInstance("MD5")
            numRead = fis.read(buffer)
            while (numRead > 0) {
                md5.update(buffer, 0, numRead)
                numRead = fis.read(buffer)
            }
            fis.close()
            return toHexString(md5.digest()).trim { it <= ' ' }
        } catch (e: Exception) {
            Log.w("Hitron:MD5", "MD5 failly.")
            return null
        }

    }

    fun md5Str(src: String): String? {
        val md5: MessageDigest
        try {
            md5 = MessageDigest.getInstance("MD5")
            return toHexString(md5.digest(src.toByteArray())).trim { it <= ' ' }
        } catch (e: Exception) {
            Log.w("Hitron:MD5", "MD5 failly.")
            return null
        }

    }
}
