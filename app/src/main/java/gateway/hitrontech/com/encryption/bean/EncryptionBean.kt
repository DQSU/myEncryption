package gateway.hitrontech.com.encryption.bean

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcel
import android.os.Parcelable
import gateway.hitrontech.com.encryption.BR
import gateway.hitrontech.com.encryption.utils.Constants
import java.io.Serializable

class EncryptionBean() : BaseObservable(), Serializable, Parcelable {


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

    constructor(parcel: Parcel) : this() {
        plainText = parcel.readString()!!
        cipherText = parcel.readString()!!
        key = parcel.readString()!!
    }

    override fun equals(other: Any?): Boolean {
        var isEqual = false
        if (other is EncryptionBean) {
            if (other.plainText == plainText && other.key == key) {
                isEqual = true
            }
        }

        return isEqual
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EncryptionBean> {
        override fun createFromParcel(parcel: Parcel): EncryptionBean {
            return EncryptionBean(parcel)
        }

        override fun newArray(size: Int): Array<EncryptionBean?> {
            return arrayOfNulls(size)
        }
    }
}
