package gateway.hitrontech.com.encryption.fragment.add_item;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.base.BaseFragment;
import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import gateway.hitrontech.com.encryption.databinding.FragmentAddItemBinding;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AddItemFragment extends BaseFragment implements Contract.View {

  static final String LIST_OF_ENCRYPTION = "LIST_OF_ENCRYPTION";
  private FragmentAddItemBinding mBinding;
  private Contract.Presenter mPresenter;
  private ArrayList<EncryptionBean> list;
  private EncryptionBean data = new EncryptionBean();

  public static AddItemFragment getInstance(ArrayList<EncryptionBean> list) {
    AddItemFragment fragment = new AddItemFragment();
    Bundle args = new Bundle();
    args.putSerializable(LIST_OF_ENCRYPTION, list);
    fragment.setArguments(args);
    return fragment;
  }

  @NotNull
  @Override
  protected View createView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (!getViewExisted()) {
      mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false);
    }
    return mBinding.getRoot();
  }

  @Override
  protected void viewFound(@Nullable View view) {
    if (!getViewExisted()) {
      new Presenter(this);

      mBinding.setData(data);

      list = (ArrayList<EncryptionBean>) getArguments().getSerializable(LIST_OF_ENCRYPTION);

    }
  }

  @NotNull
  @Override
  protected String getTitle() {
    return "添加";
  }

  @Override
  public void setPresenter(@NonNull Contract.Presenter presenter) {
    this.mPresenter = presenter;
  }

  @Override
  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.commit, menu);
    super.onCreateOptionsMenu(menu, inflater);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.commit: {
        if (mPresenter.duplicateCheck(data, list)) {
          onBackPressedSupport();
        }
        break;
      }

    }
    return super.onOptionsItemSelected(item);
  }
}
