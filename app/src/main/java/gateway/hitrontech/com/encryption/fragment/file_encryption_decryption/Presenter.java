package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption;

import com.hitrontech.hitronencryption.EncryptionManager;
import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.CommonFile;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.ExcelFile;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.FileImpl;
import gateway.hitrontech.com.encryption.utils.FileUtils;
import gateway.hitrontech.com.encryption.utils.SharePreManager;
import java.util.ArrayList;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Presenter implements Contract.Presenter {


  private ArrayList<EncryptionBean> beanList = new ArrayList<>();

  private Contract.View mView;

  Presenter(Contract.View view) {
    this.mView = view;
    this.mView.setPresenter(this);
  }

  @Override
  public void toFile(final int type, ArrayList<EncryptionBean> list) {
    mView.showProgressDialog();
    beanList = list;
    FileImpl file = null;
    switch (type) {
      case Contract.COMMON_FILE:
        file = new CommonFile();
        break;
      case Contract.EXCEL_FILE:
        file = new ExcelFile();
      default:
        break;
    }

    // 将未加密的明文全部加密
    for (EncryptionBean item : beanList) {
      item.setCipherText(
          EncryptionManager.getInstance().base64EncoderByAppId(
              SharePreManager.getInstance().getAppId(),
              item.getKey(),
              item.getPlainText()
          )
      );
    }

    final FileImpl finalFile = file;
    Observable.just(type)
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<Integer, Observable<?>>() {
          @Override
          public Observable<?> call(Integer integer) {
            assert finalFile != null;
            finalFile.writeFile(beanList,
                (type == 0) ? FileUtils.getOrigin() : FileUtils.getTargetXls());

            return Observable.just(null).observeOn(Schedulers.io());
          }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Object>() {
          @Override
          public void call(Object o) {
            // Log.e("", "call: " + beanList.get(0).getCipherText());
            mView.dismissProgressDialog();
            mView.showMessage("文件已存到" + FileUtils.getTargetXls());
          }
        });
  }

  @Override
  public void readFile(final int type) {
    FileImpl file = null;
    switch (type) {
      case Contract.COMMON_FILE:
        file = new CommonFile();
        break;
      case Contract.EXCEL_FILE:
        file = new ExcelFile();
      default:
        break;
    }

    final FileImpl finalFile = file;
    mView.showProgressDialog();
    Observable.just(type)
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<Integer, Observable<ArrayList<EncryptionBean>>>() {
          @Override
          public Observable<ArrayList<EncryptionBean>> call(Integer integer) {
            try {
              beanList.clear();
              finalFile
                  .readFile(beanList,
                      (type == 0) ? FileUtils.getOrigin() : FileUtils.getTargetXls());
              return Observable.just(beanList).observeOn(Schedulers.io());
            } catch (Exception e) {
              return Observable.error(e);
            }
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ArrayList<EncryptionBean>>() {
          @Override
          public void onCompleted() {
          }

          @Override
          public void onError(Throwable e) {
            mView.showMessage("暂无文件，请将.xls文件放到" + FileUtils.getTargetXls());
            mView.dismissProgressDialog();
          }

          @Override
          public void onNext(ArrayList<EncryptionBean> list) {
            if (list.isEmpty()) {
              mView.showMessage("文件内容为空，请修改后将.xls文件放到" + FileUtils.getTargetXls());
            } else {
              mView.setList(list);
              mView.showMessage("已从文件" + FileUtils.getTargetXls() + "中读取文件");
            }
            mView.dismissProgressDialog();
          }

        });
  }


  @Override
  public void start() {
    beanList.clear();
    int type = Contract.EXCEL_FILE;
    readFile(type);
  }
}
