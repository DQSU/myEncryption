package gateway.hitrontech.com.encryption.utils

import gateway.hitrontech.com.encryption.bean.LanguageUnicode
import java.util.*

class RandomStringUtils private constructor(private val unicode: LanguageUnicode) {

    fun getRandomString(length: Int): String {
        val measure = unicode.unicodeEnd - unicode.unicodeStart
        val random = Random()
        val stringBuilder = StringBuilder()

        for (i in 0 until length) {
            val tmp = random.nextInt(measure)
            val result = unicode.unicodeStart + tmp
            stringBuilder.append(result.toChar())
            // System.out.println(stringBuilder.toString());

        }

        return stringBuilder.toString()
    }

    companion object {

        fun getInstance(unicode: LanguageUnicode): RandomStringUtils {
            return RandomStringUtils(unicode)
        }

        @JvmStatic
        fun main() {
            val utils = RandomStringUtils.getInstance(Constants.JAPAN)
            utils.getRandomString(15)

        }
    }
}
