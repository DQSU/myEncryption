package gateway.hitrontech.com.encryption.fragment.add_item;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.util.ArrayList;

public class Presenter implements Contract.Presenter {

  private Contract.View mView;

  Presenter(Contract.View view) {
    this.mView = view;
    mView.setPresenter(this);
  }

  @Override
  public Boolean duplicateCheck(EncryptionBean newItem, ArrayList<EncryptionBean> list) {
    mView.showProgressDialog();
    if (list.contains(newItem)) {
      mView.showMessage("列表中已包含此项，请重新输入");
      mView.dismissProgressDialog();
      return false;
    } else {
      list.add(newItem);
      mView.showMessage("已添加到列表");
      mView.dismissProgressDialog();
      return true;
    }
  }

  @Override
  public void start() {
  }
}
