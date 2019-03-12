package gateway.hitrontech.com.encryption.fragment.decryption;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hitrontech.hitronencryption.EncryptionManager;
import com.jakewharton.rxbinding2.view.RxView;
import com.kryst.njit.base.BaseFragment;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.databinding.FragmentDecryptionBinding;
import gateway.hitrontech.com.encryption.utils.Constants;
import gateway.hitrontech.com.encryption.utils.SharePreManager;
import io.reactivex.functions.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DecryptionFragment extends BaseFragment implements Contract.View {

  private FragmentDecryptionBinding mBinding;

  private Contract.Presenter mPresenter;


  public static Fragment getInstance() {
    Fragment fragment = new DecryptionFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @NotNull
  @Override
  protected View createView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (!getViewExisted()) {
      mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_decryption, container, false);
    }
    return mBinding.getRoot();
  }

  @SuppressLint("CheckResult")
  @Override
  protected void viewFound(@Nullable View view) {
    if (!getViewExisted()) {
      new Presenter(this);
      mPresenter.start();

      RxView.clicks(mBinding.decryption)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              mPresenter.getPlainText(mBinding.cipherText.getText().toString());
            }
          });

      RxView.clicks(mBinding.changeCipherText)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              changeCipherText();
            }
          });


    }
  }

  @NotNull
  @Override
  protected String getTitle() {
    return "解密";
  }

  @Override
  public void setPlainText(String plainText) {
    mBinding.plainText.setText(plainText);
  }

  @Override
  public void setCipherText(String cipherText) {
    mBinding.cipherText.setText(cipherText);
  }

  @Override
  public void setPresenter(@NonNull Contract.Presenter presenter) {
    mPresenter = presenter;
  }

  private void changeCipherText() {
    if (mBinding.cipherText.getText().toString()
        .equals(EncryptionManager.getInstance().base64EncoderByAppId(
            SharePreManager.getInstance().getAppId(),
            Constants.KEY,
            getContext().getString(R.string.test_number)
        ))) {
      mBinding.cipherText.setText(
          EncryptionManager.getInstance().base64EncoderByAppId(
              SharePreManager.getInstance().getAppId(),
              Constants.KEY,
              getContext().getString(R.string.ht_security_key)
          )
      );
    } else {
      mBinding.cipherText.setText(
          EncryptionManager.getInstance().base64EncoderByAppId(
              SharePreManager.getInstance().getAppId(),
              Constants.KEY,
              getContext().getString(R.string.test_number)
          )
      );
    }
  }
}
