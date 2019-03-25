package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFile implements FileImpl {

  @Override
  public void readFile(ArrayList<EncryptionBean> list, String filePath) {
    File file = new File(filePath);
    if (file.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> iterator = sheet.iterator();

        int count = 0;
        while (iterator.hasNext()) {
          Row currentRow = iterator.next();
          if (count != 0) {
            Iterator<Cell> cellIterator = currentRow.iterator();
            EncryptionBean tmp = new EncryptionBean();
            tmp.setPlainText(cellIterator.next().getStringCellValue());
            list.add(tmp);
          }
          count++;
        }

        fileInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void writeFile(ArrayList<EncryptionBean> list, String filePath) {
    try {
      Workbook workbook = new XSSFWorkbook();
      Sheet sheet = workbook.createSheet("TARGET");

      int rowNumber = 0;
      Font font = workbook.createFont();
      font.setBoldweight((short) 16);
      font.setFontHeight((short) 20);
      font.setColor(IndexedColors.RED.getIndex());
      Row header = sheet.createRow(rowNumber++);
      CellStyle style = workbook.createCellStyle();
      style.setFont(font);
      header.setRowStyle(style);
      Cell plainText = header.createCell(0);
      plainText.setCellValue("PlainText");
      Cell key = header.createCell(2);
      key.setCellValue("KEY");
      Cell cipherText = header.createCell(4);
      cipherText.setCellValue("CipherText");

      for (EncryptionBean bean : list) {
        Row row = sheet.createRow(rowNumber++);
        Cell first = row.createCell(0);
        first.setCellValue(bean.getPlainText());
        Cell second = row.createCell(2);
        second.setCellValue(bean.getKey());
        Cell third = row.createCell(4);
        third.setCellValue(bean.getCipherText());
      }

      FileOutputStream fileOutputStream = new FileOutputStream(filePath);
      workbook.write(fileOutputStream);
      workbook.cloneSheet(0);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
