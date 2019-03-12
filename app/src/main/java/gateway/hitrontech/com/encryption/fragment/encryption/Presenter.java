package gateway.hitrontech.com.encryption.fragment.encryption;


import com.hitrontech.hitronencryption.EncryptionManager;
import gateway.hitrontech.com.encryption.utils.Constants;
import gateway.hitrontech.com.encryption.utils.SharePreManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Presenter implements Contract.Presenter {

  private Contract.View mView;

  public Presenter(Contract.View view) {
    this.mView = view;
    this.mView.setPresenter(this);
  }

  @Override
  public void getEncryptionText(final String plainText) {
    Observable.just(plainText)
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<String, Observable<String>>() {
          @Override
          public Observable<String> call(String text) {
            return Observable.just(EncryptionManager.getInstance().base64EncoderByAppId(
                SharePreManager.getInstance().getAppId(),
                Constants.KEY,
                text
            )).observeOn(Schedulers.io());
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<String>() {
          @Override
          public void call(String s) {
            mView.setEncryptionText(s);
          }
        });
  }

  @Override
  public void start() {

  }
}
