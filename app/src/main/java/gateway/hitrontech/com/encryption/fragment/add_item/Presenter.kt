package gateway.hitrontech.com.encryption.fragment.add_item

import gateway.hitrontech.com.encryption.bean.EncryptionBean

class Presenter(private val mView: Contract.View) : Contract.Presenter {

    init {
        mView.setPresenter(this)
    }

    override fun start() {}

    override fun duplicateCheck(newItem: EncryptionBean, list: ArrayList<EncryptionBean>): Boolean {
        mView.showProgressDialog()
        return if (newItem in list) {
            mView.showMessage("列表中已存在该项，请重新输入")
            mView.dismissProgressDialog()
            false
        } else {
            list.add(newItem)
            mView.showMessage("已添加到列表")
            mView.dismissProgressDialog()
            true
        }

    }

}