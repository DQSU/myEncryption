package gateway.hitrontech.com.encryption.fragment.encryption


import com.hitrontech.hitronencryption.EncryptionManager
import gateway.hitrontech.com.encryption.utils.Constants
import gateway.hitrontech.com.encryption.utils.SharePreManager
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class Presenter(private val mView: Contract.View) : Contract.Presenter {

    init {
        this.mView.setPresenter(this)
    }

    override fun getEncryptionText(plainText: String) {
        mView.showProgressDialog()
        Observable.just(plainText)
                .subscribeOn(Schedulers.io())
                .flatMap { text ->
                    Observable.just(EncryptionManager.instance.base64EncoderByAppId(
                            SharePreManager.instance.appId,
                            Constants.KEY,
                            text
                    )).observeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s ->
                    mView.dismissProgressDialog()
                    mView.setEncryptionText(s)
                }
    }

    override fun start() {

    }
}
