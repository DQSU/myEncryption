package gateway.hitrontech.com.encryption.utils

import android.os.Environment
import java.io.File

object FileUtils {

    private val ORIGIN = "origin"

    private val TARGET = "target"

    private val RESULT = "result"

    val cachePath: String
        get() = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            WhichContext.instance!!.getExternalCacheDir()!!.getPath()
        } else {
            WhichContext.instance!!.getCacheDir().getPath()
        }

    val resultPath: String
        get() {
            val result = File(cachePath.replace("cache", "") + "/" + RESULT)
            if (!result.exists()) {
                result.mkdir()
            }
            return result.path
        }


    val origin: String
        get() = "$resultPath/$ORIGIN"

    val target: String
        get() = "$resultPath/$TARGET"


    val targetXls: String
        get() = "$resultPath/$TARGET.xls"

}
