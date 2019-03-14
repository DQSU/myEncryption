package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CommonFile implements FileImpl {

  @Override
  public void readFile(ArrayList<EncryptionBean> list, String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
      try {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
          if (!line.equals("")) {
            EncryptionBean tmp = new EncryptionBean();
            tmp.setPlainText(line);
            list.add(tmp);
          }
        }
        bufferedReader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void writeFile(ArrayList<EncryptionBean> list, String filePath) {
    (new File(filePath)).delete();
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

      for (EncryptionBean item : list) {
        bufferedWriter.append(item.getPlainText()).append("\n");
        bufferedWriter.append(item.getCipherText()).append("\n\n");
      }

      bufferedWriter.flush();
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
