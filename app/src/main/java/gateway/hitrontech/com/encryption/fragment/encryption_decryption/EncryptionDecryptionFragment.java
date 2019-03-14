package gateway.hitrontech.com.encryption.fragment.encryption_decryption;

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
import gateway.hitrontech.com.encryption.databinding.FragmentEncryptionDecryptionBinding;
import gateway.hitrontech.com.encryption.utils.Constants;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EncryptionDecryptionFragment extends BaseFragment implements Contract.View {

  private FragmentEncryptionDecryptionBinding mBinding;
  private Contract.Presenter mPresenter;

  public static Fragment getInstance() {
    Fragment fragment = new EncryptionDecryptionFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @NotNull
  @Override
  protected View createView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (!getViewExisted()) {
      mBinding = DataBindingUtil
          .inflate(inflater, R.layout.fragment_encryption_decryption, container, false);
    }
    return mBinding.getRoot();
  }

  @SuppressLint("CheckResult")
  @Override
  protected void viewFound(@Nullable final View view) {
    if (!getViewExisted()) {
      new Presenter(this);
      mPresenter.start();

      mBinding.key.setText(Constants.KEY);

      RxView.clicks(mBinding.encryption)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              mPresenter.getCipherText(
                  mBinding.key.getText().toString(),
                  mBinding.plainText.getText().toString()
              );
            }
          });

      // todo changing the value of key has no effect on encryption
      RxView.clicks(mBinding.changeKey)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              if (mBinding.keyLayout.getVisibility() == View.VISIBLE) {
                mBinding.keyLayout.setVisibility(View.GONE);
              } else {
                mBinding.keyLayout.setVisibility(View.VISIBLE);
              }
            }
          });

      RxView.clicks(mBinding.decryption)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              mPresenter.getPlainText(
                  mBinding.key.getText().toString(),
                  mBinding.cipherText.getText().toString()
              );
            }
          });
    }
  }

  @NotNull
  @Override
  protected String getTitle() {
    return "加密/解密";
  }

  @Override
  public void setCipherText(String cipherText) {
    mBinding.cipherText.setText(cipherText);
  }

  @Override
  public void setPlainText(String plainText) {
    mBinding.decryptionText.setText(plainText);
  }

  @Override
  public void setPresenter(@NonNull Contract.Presenter presenter) {
    mPresenter = presenter;
  }
}
