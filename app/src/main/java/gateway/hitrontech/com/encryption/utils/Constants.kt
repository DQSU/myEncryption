package gateway.hitrontech.com.encryption.utils

import gateway.hitrontech.com.encryption.bean.LanguageUnicode

object Constants {

    const val KEY = "com.hitrontech.myhitron.videotron"

    val CHINESE = LanguageUnicode(0x4e00, 0x9fa5)

    val LATIN = LanguageUnicode(0x0000, 0x024f)

    val JAPAN = LanguageUnicode(0x3040, 0x309f)

    val KOREAN = LanguageUnicode(0x1100, 0x11ff)
}
