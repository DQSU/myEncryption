package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File

import gateway.hitrontech.com.encryption.bean.EncryptionBean
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.IndexedColors
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ExcelFile : FileImpl {

    override fun readFile(list: ArrayList<EncryptionBean>, filePath: String) {
        val file = File(filePath)
        if (file.exists()) {
            try {
                val fileInputStream = FileInputStream(file)
                val workbook = HSSFWorkbook(fileInputStream)
                val sheet = workbook.getSheetAt(0)
                val iterator = sheet.iterator()

                var count = 0
                while (iterator.hasNext()) {
                    val currentRow = iterator.next()
                    if (count != 0) {
                        val cellIterator = currentRow.iterator()
                        val tmp = EncryptionBean()
                        tmp.plainText = cellIterator.next().stringCellValue
                        tmp.key = cellIterator.next().stringCellValue
                        list.add(tmp)
                    }
                    count++
                }

                fileInputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }

    override fun writeFile(list: ArrayList<EncryptionBean>, filePath: String) {
        try {
            val workbook = HSSFWorkbook()
            val sheet = workbook.createSheet("TARGET")

            var rowNumber = 0
            val font = workbook.createFont()
            font.boldweight = 16.toShort()
            font.fontHeight = 20.toShort()
            font.color = IndexedColors.RED.getIndex()
            val header = sheet.createRow(rowNumber++)
            val style = workbook.createCellStyle()
            style.setFont(font)
            header.setRowStyle(style)
            val plainText = header.createCell(0)
            plainText.setCellValue("PlainText")
            val key = header.createCell(2)
            key.setCellValue("KEY")
            val cipherText = header.createCell(4)
            cipherText.setCellValue("CipherText")

            for (bean in list) {
                val row = sheet.createRow(rowNumber++)
                val first = row.createCell(0)
                first.setCellValue(bean.plainText)
                val second = row.createCell(2)
                second.setCellValue(bean.key)
                val third = row.createCell(4)
                third.setCellValue(bean.cipherText)
            }

            val fileOutputStream = FileOutputStream(filePath)
            workbook.write(fileOutputStream)
            workbook.cloneSheet(0)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}
