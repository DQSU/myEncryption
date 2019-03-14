package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption;

import gateway.hitrontech.com.encryption.EncryptionBean;
import java.util.ArrayList;
import moe.xing.mvp_utils.BasePresenter;
import moe.xing.mvp_utils.BaseView;

public interface Contract {

  interface Presenter extends BasePresenter {

    void generatePlainText(int number);

    void encryption();

    void toFile();

  }

  interface View extends BaseView<Presenter> {

    void setList(ArrayList<EncryptionBean> list);

  }

}
