package gateway.hitrontech.com.encryption.fragment.encryption_decryption

import moe.xing.mvp_utils.BasePresenter
import moe.xing.mvp_utils.BaseView

interface Contract {

    interface Presenter : BasePresenter {

        fun getCipherText(key: String, plainText: String)

        fun getPlainText(key: String, cipherText: String)

    }

    interface View : BaseView<Presenter> {

        fun setCipherText(cipherText: String)

        fun setPlainText(plainText: String)
    }

}
