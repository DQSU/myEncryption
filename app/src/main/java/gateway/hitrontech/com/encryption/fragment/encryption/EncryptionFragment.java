package gateway.hitrontech.com.encryption.fragment.encryption;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jakewharton.rxbinding2.view.RxView;
import com.kryst.njit.base.BaseFragment;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.databinding.FragmentEncryptionBinding;
import gateway.hitrontech.com.encryption.utils.SharePreManager;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EncryptionFragment extends BaseFragment implements Contract.View {

  private FragmentEncryptionBinding mBinding;
  private Contract.Presenter mPresenter;

  public static Fragment getInstance() {
    Bundle args = new Bundle();
    Fragment fragment = new EncryptionFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @NotNull
  @Override
  protected View createView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (!getViewExisted()) {
      mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_encryption, container, false);

    }
    return mBinding.getRoot();
  }

  @SuppressLint("CheckResult")
  @Override
  protected void viewFound(@Nullable View view) {
    if (!getViewExisted()) {
      new Presenter(this);
      mPresenter.start();

      RxView.clicks(mBinding.encryption)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object click) throws Exception {
              mPresenter.getEncryptionText(mBinding.plainText.getText().toString());
            }
          });

      RxView.clicks(mBinding.changePlainText)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              if (mBinding.plainText.getText().toString().equals(getString(R.string.test_number))) {
                mBinding.plainText.setText(getString(R.string.ht_security_key));
              } else {
                mBinding.plainText.setText(getString(R.string.test_number));
              }
            }
          });

    }
  }

  @NotNull
  @Override
  protected String getTitle() {
    return "加密";
  }

  @Override
  public void setEncryptionText(String cipherText) {
    SharePreManager.getInstance().setCipherText(cipherText);
    mBinding.cipherText.setText(cipherText);
  }

  @Override
  public void setPresenter(@NonNull Contract.Presenter presenter) {
    this.mPresenter = presenter;
  }
}
