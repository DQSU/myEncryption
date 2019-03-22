package gateway.hitrontech.com.encryption.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import gateway.hitrontech.com.encryption.BR
import gateway.hitrontech.com.encryption.utils.Constants
import java.io.Serializable

class EncryptionBean : BaseObservable(), Serializable {


    @get:Bindable
    var plainText: String = String()
        set(plainText) {

            if (field != plainText) {
                field = plainText
                notifyPropertyChanged(BR.plainText)
            }
        }

    @get:Bindable
    var cipherText: String = String()
        set(cipherText) {
            if (field != cipherText) {
                field = cipherText
                notifyPropertyChanged(BR.cipherText)
            }
        }

    @get:Bindable
    var key: String = Constants.KEY
        set(key) {
            if (field != key) {
                field = key
                notifyPropertyChanged(BR.key)
            }
        }
}
