package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption;

import android.util.Log;
import com.hitrontech.hitronencryption.EncryptionManager;
import gateway.hitrontech.com.encryption.EncryptionBean;
import gateway.hitrontech.com.encryption.utils.Constants;
import gateway.hitrontech.com.encryption.utils.FileUtils;
import gateway.hitrontech.com.encryption.utils.SharePreManager;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import rx.Observable;
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
      tmp.setDecryptionText("");
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

    mView.setList(beanList);
    mView.showMessage("已生成密文");
  }

  @Override
  public void toFile() {
    (new File(FileUtils.getTarget())).delete();
    Observable.just(beanList)
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<ArrayList<EncryptionBean>, Observable<Boolean>>() {
          @Override
          public Observable<Boolean> call(ArrayList<EncryptionBean> encryptionBeans) {
            try {
              BufferedWriter bufferedWriter = new BufferedWriter(
                  new FileWriter(new File(FileUtils.getTarget())));

              for (EncryptionBean item : beanList) {
                bufferedWriter.append(item.getPlainText()).append("\n");
                bufferedWriter.append(item.getCipherText()).append("\n\n");
              }

              bufferedWriter.flush();
              bufferedWriter.close();

            } catch (IOException e) {
              e.printStackTrace();
            }

            return Observable.just(true).observeOn(Schedulers.io());
          }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Boolean>() {
          @Override
          public void call(Boolean aBoolean) {
            if (aBoolean) {
              mView.showMessage("已写入文件");
            }
          }
        });
  }


  @Override
  public void start() {
    beanList.clear();

    if ((new File(FileUtils.getOrigin()).exists())) {
      readOrigin();
    }

    // write2Xls();
  }

  private void readOrigin() {
    Observable.just(beanList)
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<ArrayList<EncryptionBean>, Observable<ArrayList<EncryptionBean>>>() {
          @Override
          public Observable<ArrayList<EncryptionBean>> call(
              ArrayList<EncryptionBean> encryptionBeans) {
            try {
              BufferedReader bufferedReader = new BufferedReader(
                  new FileReader(FileUtils.getOrigin()));
              String line;
              while ((line = bufferedReader.readLine()) != null) {
                if (!line.equals("")) {
                  EncryptionBean tmp = new EncryptionBean();
                  tmp.setPlainText(line);
                  beanList.add(tmp);
                }
              }

              bufferedReader.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
            return Observable.just(beanList).observeOn(Schedulers.io());
          }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<ArrayList<EncryptionBean>>() {
          @Override
          public void call(ArrayList<EncryptionBean> o) {
            if (!o.isEmpty()) {
              mView.setList(o);
              mView.showMessage("已加载明文");
            }
          }
        });
  }

  private String getRandomString(int length) { //length表示生成字符串的长度

    Random random = new Random();
    StringBuffer sb = new StringBuffer();
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
                bufferedWriter.append(item + "\n\n");
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


  private void write2Xls() {
    Observable.just(new File(FileUtils.getTargetXls()))
        .subscribeOn(Schedulers.io())
        .flatMap(new Func1<File, Observable<?>>() {
          @Override
          public Observable<?> call(File file) {
            try {
              if (!file.exists()) {
                file.createNewFile();
              }

              Workbook workbook = new HSSFWorkbook();
              Sheet sheet = workbook.createSheet("origin");

              Row headerRow = sheet.createRow(0);

              Cell cell = headerRow.createCell(0);
              cell.setCellValue("are you ok?");

              FileOutputStream fileOutputStream = new FileOutputStream(FileUtils.getTargetXls());
              workbook.write(fileOutputStream);
              fileOutputStream.close();
              workbook.cloneSheet(0);
            } catch (IOException e) {
              e.printStackTrace();
            }

            return null;
          }
        }).observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Object>() {
          @Override
          public void call(Object o) {
            Log.e("", "yes");
          }
        });
  }
}
