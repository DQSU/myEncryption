package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption

import gateway.hitrontech.com.encryption.bean.EncryptionBean
import moe.xing.mvp_utils.BasePresenter
import moe.xing.mvp_utils.BaseView
import java.util.*

interface Contract {


    interface Presenter : BasePresenter {

        fun toFile(type: Int)

        fun readFile(type: Int)

    }

    interface View : BaseView<Presenter> {

        fun setList(list: ArrayList<EncryptionBean>)

    }

    companion object {

        const val COMMON_FILE = 0

        const val EXCEL_FILE = 1
    }

}
