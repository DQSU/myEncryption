package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.util.ArrayList;

public interface FileImpl {

  public void readFile(ArrayList<EncryptionBean> list, String filePath);

  public void writeFile(ArrayList<EncryptionBean> list, String filePath);

}
