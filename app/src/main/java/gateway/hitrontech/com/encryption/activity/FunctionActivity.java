package gateway.hitrontech.com.encryption.activity;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.view.MenuItem;
import com.kryst.njit.base.BaseActivity;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.databinding.ActivityFunctionBinding;
import gateway.hitrontech.com.encryption.fragment.decryption.DecryptionFragment;
import gateway.hitrontech.com.encryption.fragment.encryption.EncryptionFragment;
import gateway.hitrontech.com.encryption.fragment.encryption_decryption.EncryptionDecryptionFragment;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class FunctionActivity extends BaseActivity {

  private ActivityFunctionBinding mBinding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mBinding = DataBindingUtil.setContentView(this, R.layout.activity_function);

    // set action bar
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
            DrawerItemClick((String) menuItem.getTitle());
            return true;
          }
        });

    replaceFragment(EncryptionDecryptionFragment.getInstance());
  }

  @SuppressLint("ResourceType")
  private void DrawerItemClick(String title) {
    // todo international
    switch (title) {
      case "加密":
        replaceFragment(EncryptionFragment.getInstance());
        break;
      case "解密":
        replaceFragment(DecryptionFragment.getInstance());
        break;
      case "加密/解密":
        replaceFragment(EncryptionDecryptionFragment.getInstance());
        break;
      default:
        break;
    }
  }

  private void replaceFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
    transaction.replace(R.id.function_interface, fragment);
    transaction.commit();
    closeDrawer();
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
    return true;
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
}
