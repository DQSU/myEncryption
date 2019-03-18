package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jakewharton.rxbinding2.view.RxView;
import com.kryst.njit.base.BaseFragment;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import gateway.hitrontech.com.encryption.databinding.FragmentFileEncryptionDecryptionBinding;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileEncryptionDecryptionFragment extends BaseFragment implements Contract.View {

  private FragmentFileEncryptionDecryptionBinding mBinding;

  private Contract.Presenter mPresenter;

  private Adapter mAdapter = new Adapter();

  private boolean isFirst = true;

  public static Fragment getInstance() {
    Fragment fragment = new FileEncryptionDecryptionFragment();
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
          .inflate(inflater, R.layout.fragment_file_encryption_decryption, container, false);
    }
    return mBinding.getRoot();
  }

  @SuppressLint("CheckResult")
  @Override
  protected void viewFound(@Nullable View view) {

    if (!getViewExisted()) {
      new Presenter(this);

      mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
      mBinding.recyclerView.setAdapter(mAdapter);

      mPresenter.start();


      RxView.clicks(mBinding.writeCipherText)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              mPresenter.toFile(Contract.EXCEL_FILE);
            }
          });
    }
  }

  @NotNull
  @Override
  protected String getTitle() {
    return "文件加密/解密";
  }

  @Override
  public void setList(ArrayList<EncryptionBean> list) {
    if (isFirst) {
      isFirst = false;
      this.mAdapter.setList(list);
    }
  }

  @Override
  public void setPresenter(@NonNull Contract.Presenter presenter) {
    this.mPresenter = presenter;
  }
}
