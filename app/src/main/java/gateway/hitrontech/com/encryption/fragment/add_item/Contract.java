package gateway.hitrontech.com.encryption.fragment.add_item;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.util.ArrayList;
import moe.xing.mvp_utils.BasePresenter;
import moe.xing.mvp_utils.BaseView;

interface Contract {

  interface Presenter extends BasePresenter {

    Boolean duplicateCheck(EncryptionBean newItem, ArrayList<EncryptionBean> list);
  }

  interface View extends BaseView<Presenter> {

  }

}
