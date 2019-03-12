package gateway.hitrontech.com.encryption.fragment.decryption;

import com.hitrontech.hitronencryption.EncryptionManager;
import gateway.hitrontech.com.encryption.utils.Constants;
import gateway.hitrontech.com.encryption.utils.SharePreManager;

public class Presenter implements Contract.Presenter {

  private Contract.View mView;

  public Presenter(Contract.View view) {
    mView = view;
    mView.setPresenter(this);
  }

  @Override
  public void getPlainText(String cipherText) {
    mView.setPlainText(
        EncryptionManager.getInstance()
            .base64DecoderByAppId(
                SharePreManager.getInstance().getAppId(),
                Constants.KEY,
                cipherText
            ));
  }

  @Override
  public void getCipherText() {
    mView.setCipherText(SharePreManager.getInstance().getCipherText());
  }

  @Override
  public void start() {
    getCipherText();
  }
}
