package gateway.hitrontech.com.encryption.utils

import android.content.Context
import android.content.SharedPreferences
import gateway.hitrontech.com.encryption.R

class SharePreManager private constructor() {

    val appId: String
        get() = WhichContext.instance!!.getString(R.string.applicationId)

    var cipherText: String?
        get() = sSharedPre!!.getString(CIPHER_TEXT, "")
        set(cipherText) = sSharedPre!!.edit().putString(CIPHER_TEXT, cipherText).apply()

    /*fun updatePwd(pwd: String) {
        val editor = sSharedPre!!.edit()
        pwdSet?.add(pwd)
        editor.putStringSet(mPassword, pwdSet)
        editor.apply()
        editor.clear()
    }*/

    companion object {

        private const val mPreName = "pre_sharePre_encryption"

        private const val CIPHER_TEXT = "CIPHER_TEXT"
        private var sPreManager: SharePreManager? = null
        private var sSharedPre: SharedPreferences? = null
//        private val mPassword = "password"

        val instance: SharePreManager
            get() {
                synchronized(SharePreManager::class.java) {
                    if (sSharedPre == null) {
                        sPreManager = SharePreManager()
                        sSharedPre = WhichContext.instance!!.getSharedPreferences(mPreName,
                                Context.MODE_PRIVATE)
                    }
                }
                return sPreManager!!
            }

//        val pwdSet: MutableSet<String>?
//            get() = sSharedPre!!.getStringSet(mPassword, TreeSet())
    }


}
