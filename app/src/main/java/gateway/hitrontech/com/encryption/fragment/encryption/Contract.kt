package gateway.hitrontech.com.encryption.fragment.encryption

import moe.xing.mvp_utils.BasePresenter
import moe.xing.mvp_utils.BaseView

interface Contract {

    interface Presenter : BasePresenter {

        fun getEncryptionText(plainText: String)
    }

    interface View : BaseView<Presenter> {

        fun setEncryptionText(cipherText: String)
    }
}
