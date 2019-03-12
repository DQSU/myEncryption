package gateway.hitrontech.com.encryption.fragment.decryption;

import moe.xing.mvp_utils.BasePresenter;
import moe.xing.mvp_utils.BaseView;

public interface Contract {

  interface Presenter extends BasePresenter {

    void getPlainText(String cipherText);


    void getCipherText();
  }

  interface View extends BaseView<Presenter> {

    void setPlainText(String plainText);

    void setCipherText(String cipherText);

  }


}
