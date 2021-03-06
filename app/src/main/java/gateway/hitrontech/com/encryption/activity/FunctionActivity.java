package gateway.hitrontech.com.encryption.activity;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.base.BaseActivity;
import gateway.hitrontech.com.encryption.databinding.ActivityFunctionBinding;
import gateway.hitrontech.com.encryption.fragment.about.AboutFragment;
import gateway.hitrontech.com.encryption.fragment.encryption_decryption.EncryptionDecryptionFragment;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.FileEncryptionDecryptionFragment;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class FunctionActivity extends BaseActivity {

  private ActivityFunctionBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_function);

    setSupportActionBar(mBinding.toolBar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
      getSupportActionBar().setDisplayShowTitleEnabled(true);
    }

    mBinding.navigationView.setCheckedItem(R.id.encryption);
    mBinding.navigationView.setNavigationItemSelectedListener(
        new OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            drawerItemClick((String) menuItem.getTitle());
            return true;
          }
        });

    replaceFragment(FileEncryptionDecryptionFragment.getInstance(), false);
    checkPermission();
  }

  @SuppressLint("ResourceType")
  private void drawerItemClick(String title) {

    switch (title) {
      case "加密/解密":
        replaceFragment(EncryptionDecryptionFragment.getInstance(), false);
        break;
      case "文件加密/解密":
        replaceFragment(FileEncryptionDecryptionFragment.getInstance(), false);
        break;
      case "关于":
        replaceFragment(AboutFragment.getInstance(), false);
      default:
        break;
    }
  }


  @Override
  public boolean onOptionsItemSelected(MenuItem item) {

    switch (item.getItemId()) {
      case android.R.id.home: {
        if (mBinding.drawer.isDrawerOpen(GravityCompat.START)) {
          closeDrawer();
        } else {
          openDrawer();
        }

      }
      break;
      default:
        break;
    }
    return false;
  }

  private void openDrawer() {
    Observable.timer(200, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Long>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onNext(Long aLong) {
            mBinding.drawer.openDrawer(GravityCompat.START);
          }
        });
  }

  private void closeDrawer() {
    Observable.timer(200, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<Long>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
          }

          @Override
          public void onNext(Long aLong) {
            mBinding.drawer.closeDrawers();
          }
        });
  }

  @Override
  public void replaceFragment(@NotNull Fragment fragment, boolean addToBackStack) {
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    // transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
    transaction.replace(R.id.function_interface, fragment);
    if (addToBackStack) {
      transaction.addToBackStack(null);
    }

    transaction.commit();
    closeDrawer();
  }

  private void checkPermission() {

    String[] permissions = {permission.WRITE_EXTERNAL_STORAGE, permission.READ_EXTERNAL_STORAGE};

    int STORAGE_CODE = 101;
    if (ContextCompat.checkSelfPermission(this, permissions[0])
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this, permissions, STORAGE_CODE);
    }
  }
}
