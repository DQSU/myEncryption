package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File;

import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelFile implements FileImpl {

  @Override
  public void readFile(ArrayList<EncryptionBean> list, String filePath) throws IOException {
    File file = new File(filePath);
    if (file.exists()) {
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        for (Row currentRow : sheet) {
          Iterator<Cell> cellIterator = currentRow.iterator();
          EncryptionBean tmp = new EncryptionBean();
          Cell plainCell = cellIterator.next();
          Cell keyCell = cellIterator.next();
          if (null == plainCell || null == keyCell) {
            continue;
          } else {
            plainCell.setCellType(Cell.CELL_TYPE_STRING);
            keyCell.setCellType(Cell.CELL_TYPE_STRING);
          }
          tmp.setPlainText(plainCell.getStringCellValue());
          tmp.setKey(keyCell.getStringCellValue());
          list.add(tmp);
        }

        fileInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      throw new IOException();
    }
  }

  private Cell setCellValueType(Cell cell) {
    cell.setCellType(Cell.CELL_TYPE_STRING);
    return cell;
  }

  @Override
  public void writeFile(ArrayList<EncryptionBean> list, String filePath) {
    try {
      Workbook workbook = new XSSFWorkbook();
      Sheet sheet = workbook.createSheet("TARGET");

      int rowNumber = 0;

      for (EncryptionBean bean : list) {
        Row row = sheet.createRow(rowNumber++);
        Cell first = row.createCell(0);
        first.setCellValue(bean.getPlainText());

        Cell second = row.createCell(1);
        second.setCellValue(bean.getKey());
        Cell third = row.createCell(2);
        third.setCellValue(bean.getCipherText());
      }

      FileOutputStream fileOutputStream = new FileOutputStream(filePath);
      if (!list.isEmpty()) {
        sheet.setColumnWidth(0, 50 * 255);
        sheet.setColumnWidth(1,
            (list.get(0).getKey().length() > 255 ? 255 : list.get(0).getKey().length())
                * 255);
        sheet.setColumnWidth(2, 50 * 255);
      }
      workbook.write(fileOutputStream);
      workbook.cloneSheet(0);
      fileOutputStream.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
