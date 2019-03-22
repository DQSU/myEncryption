package gateway.hitrontech.com.encryption.fragment.encryption_decryption

import com.hitrontech.hitronencryption.EncryptionManager
import gateway.hitrontech.com.encryption.utils.SharePreManager

class Presenter internal constructor(private val mView: Contract.View) : Contract.Presenter {

    init {
        this.mView.setPresenter(this)
    }

    override fun getCipherText(key: String, plainText: String) {
        mView.showProgressDialog()
        mView.setCipherText(EncryptionManager.instance.base64EncoderByAppId(
                SharePreManager.instance.appId,
                key,
                plainText
        ))
        mView.dismissProgressDialog()
    }

    override fun getPlainText(key: String, cipherText: String) {
        mView.showProgressDialog()
        mView.setPlainText(EncryptionManager.instance.base64DecoderByAppId(
                SharePreManager.instance.appId,
                key,
                cipherText
        )!!)
        mView.dismissProgressDialog()
    }

    override fun start() {

    }
}
