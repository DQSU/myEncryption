package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption;

import com.hitrontech.hitronencryption.EncryptionManager;
import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.CommonFile;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.ExcelFile;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.FileImpl;
import gateway.hitrontech.com.encryption.utils.Constants;
import gateway.hitrontech.com.encryption.utils.FileUtils;
import gateway.hitrontech.com.encryption.utils.RandomStringUtils;
import gateway.hitrontech.com.encryption.utils.SharePreManager;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Presenter implements Contract.Presenter {

  static final String ORIGIN = "origin";

  static final String TARGET = "target";

  static final String BASE = "abcdefghijklmnopqrstuvwxyz0123456789";

  private ArrayList<EncryptionBean> beanList = new ArrayList<>();

  private Contract.View mView;

  Presenter(Contract.View view) {
    this.mView = view;
    this.mView.setPresenter(this);
  }

  @Override
  public void generatePlainText(int number) {

    beanList.clear();

    File file = new File(FileUtils.getCachePath() + "/" + ORIGIN);
    file.delete();

    List<String> list = new LinkedList<>();

    for (int i = 0; i < number; i++) {
      String data = getRandomString((new Random().nextInt(number))) + getRandomString(
          (new Random().nextInt(number)));
      list.add(data);

      EncryptionBean tmp = new EncryptionBean();
      tmp.setPlainText(data);
      tmp.setCipherText("");
      tmp.setKey("");
      beanList.add(tmp);
    }

    writePlainText(list);
    mView.setList(beanList);
  }

  @Override
  public void encryption() {
    for (EncryptionBean item : beanList) {
      item.setCipherText(
          EncryptionManager.getInstance().base64EncoderByAppId(
              SharePreManager.getInstance().getAppId(),
              Constants.KEY,
              item.getPlainText()
          )
      );
    }

//    mView.setList(beanList);
    mView.showMessage("已生成密文");
  }

  @Override
  public void toFile(final int type) {
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
            mView.showMessage("已存到文件");
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
    Observable.just(type)
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<Integer, Observable<ArrayList<EncryptionBean>>>() {
          @Override
          public Observable<ArrayList<EncryptionBean>> call(Integer integer) {
            finalFile
                .readFile(beanList, (type == 0) ? FileUtils.getOrigin() : FileUtils.getTargetXls());
            return Observable.just(beanList).observeOn(Schedulers.io());
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Subscriber<ArrayList<EncryptionBean>>() {
          @Override
          public void onCompleted() {

          }

          @Override
          public void onError(Throwable e) {
            mView.showMessage("暂无文件，请将.xls文件放到" + FileUtils.getResultPath());
          }

          @Override
          public void onNext(ArrayList<EncryptionBean> list) {
            if (list.isEmpty()) {
              mView.showMessage("暂无文件，请将.xls文件放到" + FileUtils.getResultPath());
            } else {
              mView.setList(list);
              mView.showMessage("已加载明文");
            }
          }

        });
  }


  @Override
  public void start() {
    beanList.clear();
    int type = Contract.EXCEL_FILE;
    readFile(type);
//    generateFile();
//    toFile(Contract.EXCEL_FILE);
  }

  private void generateFile() {
    beanList.clear();
    for (int i = 0; i < 15; i++) {
      EncryptionBean item = new EncryptionBean();
      item.setPlainText(RandomStringUtils.getInstance(Constants.JAPAN).getRandomString(15));
      beanList.add(item);
    }
  }


  private String getRandomString(int length) {

    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < length; i++) {
      int number = random.nextInt(BASE.length());
      sb.append(BASE.charAt(number));
    }
    return sb.toString();
  }

  private void writePlainText(List<String> list) {

    Observable.just(list)
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<List<String>, Observable<Boolean>>() {
          @Override
          public Observable<Boolean> call(List<String> list) {
            try {
              BufferedWriter bufferedWriter = new BufferedWriter(
                  new FileWriter(FileUtils.getOrigin()));

              for (String item : list) {
                bufferedWriter.append(item).append("\n\n");
              }
              bufferedWriter.flush();
              bufferedWriter.close();
            } catch (IOException e) {
              e.printStackTrace();
            }

            return Observable.just(true).observeOn(Schedulers.io());
          }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Boolean>() {
          @Override
          public void call(Boolean result) {
            if (result) {
              mView.showMessage("已生成明文");
            }
          }
        });
  }

}
