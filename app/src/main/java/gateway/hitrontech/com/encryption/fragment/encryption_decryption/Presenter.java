package gateway.hitrontech.com.encryption.fragment.encryption_decryption;

import com.hitrontech.hitronencryption.EncryptionManager;
import gateway.hitrontech.com.encryption.utils.SharePreManager;

public class Presenter implements Contract.Presenter {

  private Contract.View mView;

  Presenter(Contract.View view) {
    this.mView = view;
    this.mView.setPresenter(this);
  }

  @Override
  public void getCipherText(String key, String plainText) {
    mView.setCipherText(EncryptionManager.getInstance().base64EncoderByAppId(
        SharePreManager.getInstance().getAppId(),
        key,
        plainText
    ));
  }

  @Override
  public void getPlainText(String key, String cipherText) {
    mView.setPlainText(EncryptionManager.getInstance().base64DecoderByAppId(
        SharePreManager.getInstance().getAppId(),
        key,
        cipherText
    ));
  }

  @Override
  public void start() {

  }
}
