package gateway.hitrontech.com.encryption.fragment.about;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.base.BaseFragment;
import gateway.hitrontech.com.encryption.databinding.FragmentAboutBinding;
import gateway.hitrontech.com.encryption.utils.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AboutFragment extends BaseFragment {

  private FragmentAboutBinding mBinding;

  public static Fragment getInstance() {
    AboutFragment fragment = new AboutFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @NotNull
  @Override
  protected View createView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (!getViewExisted()) {
      mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false);
    }
    return mBinding.getRoot();
  }

  @Override
  protected void viewFound(@Nullable View view) {
    if (!getViewExisted()) {
      mBinding.filePath.setText(FileUtils.getResultPath());
    }
  }

  @NotNull
  @Override
  protected String getTitle() {
    return "关于";
  }
}
