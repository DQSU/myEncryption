package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.util.ArrayList;
import moe.xing.mvp_utils.BasePresenter;
import moe.xing.mvp_utils.BaseView;

public interface Contract {

  static final int COMMON_FILE = 0;

  static final int EXCEL_FILE = 1;


  interface Presenter extends BasePresenter {


    void toFile(int type);

    void readFile(int type);

  }

  interface View extends BaseView<Presenter> {

    void setList(ArrayList<EncryptionBean> list);

  }

}
