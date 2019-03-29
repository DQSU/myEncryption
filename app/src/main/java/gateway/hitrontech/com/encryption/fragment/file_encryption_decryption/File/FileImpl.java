package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.io.IOException;
import java.util.ArrayList;

public interface FileImpl {

  void readFile(ArrayList<EncryptionBean> list, String filePath) throws IOException;

  void writeFile(ArrayList<EncryptionBean> list, String filePath);

}
