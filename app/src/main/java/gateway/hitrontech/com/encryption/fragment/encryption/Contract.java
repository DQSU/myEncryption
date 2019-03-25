package gateway.hitrontech.com.encryption.fragment.encryption;

import moe.xing.mvp_utils.BasePresenter;
import moe.xing.mvp_utils.BaseView;

interface Contract {

  interface Presenter extends BasePresenter {

    void getEncryptionText(String plainText);
  }

  interface View extends BaseView<Presenter> {

    void setEncryptionText(String cipherText);
  }
}