package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File

import gateway.hitrontech.com.encryption.bean.EncryptionBean
import java.util.*

interface FileImpl {

    fun readFile(list: ArrayList<EncryptionBean>, filePath: String)

    fun writeFile(list: ArrayList<EncryptionBean>, filePath: String)

}
