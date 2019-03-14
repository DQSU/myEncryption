package gateway.hitrontech.com.encryption;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class EncryptionBean extends BaseObservable {

  private String plainText;

  private String cipherText;

  private String decryptionText;

  @Bindable
  public String getPlainText() {
    return plainText;
  }

  public void setPlainText(String plainText) {
    this.plainText = plainText;
  }

  public String getCipherText() {
    return cipherText;
  }

  public void setCipherText(String cipherText) {
    this.cipherText = cipherText;
  }

  public String getDecryptionText() {
    return decryptionText;
  }

  public void setDecryptionText(String decryptionText) {
    this.decryptionText = decryptionText;
  }
}
