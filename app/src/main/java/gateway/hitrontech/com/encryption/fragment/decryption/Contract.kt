package gateway.hitrontech.com.encryption.fragment.decryption

import moe.xing.mvp_utils.BasePresenter
import moe.xing.mvp_utils.BaseView

interface Contract {

    interface Presenter : BasePresenter {

        fun getPlainText(cipherText: String)


        fun getCipherText()
    }

    interface View : BaseView<Presenter> {

        fun setPlainText(plainText: String)

        fun setCipherText(cipherText: String)

    }


}
