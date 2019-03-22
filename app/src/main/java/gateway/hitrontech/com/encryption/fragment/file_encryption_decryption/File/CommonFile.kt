package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File

import gateway.hitrontech.com.encryption.bean.EncryptionBean
import java.io.*
import java.util.*

class CommonFile : FileImpl {

    override fun readFile(list: ArrayList<EncryptionBean>, filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            try {
                val bufferedReader = BufferedReader(FileReader(file))


                bufferedReader.forEachLine {
                    if (it != "") {
                        val tmp = EncryptionBean()
                        tmp.plainText = it
                        list.add(tmp)
                    }

                }
                bufferedReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    override fun writeFile(list: ArrayList<EncryptionBean>, filePath: String) {
        File(filePath).delete()
        try {
            val bufferedWriter = BufferedWriter(FileWriter(filePath))

            for (item in list) {
                bufferedWriter.append(item.plainText).append("\n")
                bufferedWriter.append(item.cipherText).append("\n\n")
            }

            bufferedWriter.flush()
            bufferedWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
