package gateway.hitrontech.com.encryption.fragment.add_item

import gateway.hitrontech.com.encryption.bean.EncryptionBean
import moe.xing.mvp_utils.BasePresenter
import moe.xing.mvp_utils.BaseView

interface Contract {

    interface Presenter : BasePresenter {
        fun duplicateCheck(newItem: EncryptionBean, list: ArrayList<EncryptionBean>): Boolean

    }

    interface View : BaseView<Presenter>
}