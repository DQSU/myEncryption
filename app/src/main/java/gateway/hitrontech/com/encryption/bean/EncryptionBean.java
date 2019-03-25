package gateway.hitrontech.com.encryption.bean;

import static android.content.ContentValues.TAG;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.util.Log;
import gateway.hitrontech.com.encryption.BR;
import gateway.hitrontech.com.encryption.utils.Constants;

public class EncryptionBean extends BaseObservable {

  private String plainText = "";

  private String cipherText = "";


  private String key = Constants.KEY;

  @Bindable
  public String getPlainText() {
    return plainText;
  }

  public void setPlainText(String plainText) {

    if (!this.plainText.equals(plainText)) {
      this.plainText = plainText;
      notifyPropertyChanged(BR.plainText);
    }
  }

  @Bindable
  public String getCipherText() {
    return cipherText;
  }

  public void setCipherText(String cipherText) {
    if (!this.cipherText.equals(cipherText)) {
      this.cipherText = cipherText;
      notifyPropertyChanged(BR.cipherText);
    }
  }

  @Bindable
  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    if (!this.key.equals(key)) {
      this.key = key;
      Log.e(TAG, "setKey has been invoked" + this.key);
      notifyPropertyChanged(BR.key);
    }
  }

  @Override
  public boolean equals(Object obj) {

    if (obj instanceof EncryptionBean) {
      return ((EncryptionBean) obj).plainText.equals(this.plainText) &&
          ((EncryptionBean) obj).key.equals(this.key);
    } else {
      return false;
    }
  }
}
