package gateway.hitrontech.com.encryption.fragment.encryption_decryption;

import moe.xing.mvp_utils.BasePresenter;
import moe.xing.mvp_utils.BaseView;

public interface Contract {

  interface Presenter extends BasePresenter {

    void getCipherText(String key, String plainText);

    void getPlainText(String key, String cipherText);

  }

  interface View extends BaseView<Presenter> {

    void setCipherText(String cipherText);

    void setPlainText(String plainText);
  }

}
