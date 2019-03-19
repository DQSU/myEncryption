package gateway.hitrontech.com.encryption.utils;

import gateway.hitrontech.com.encryption.bean.LanguageUnicode;
import java.util.Random;

public class RandomStringUtils {

  private LanguageUnicode unicode;

  private RandomStringUtils(LanguageUnicode unicode) {
    this.unicode = unicode;
  }

  public static RandomStringUtils getInstance(LanguageUnicode unicode) {
    return new RandomStringUtils(unicode);
  }

  public static void main(String[] args) {
    RandomStringUtils utils = RandomStringUtils.getInstance(Constants.JAPAN);
    utils.getRandomString(15);

  }

  public String getRandomString(int length) {
    int measure = unicode.getUnicodeEnd() - unicode.getUnicodeStart();
    Random random = new Random();
    StringBuilder stringBuilder = new StringBuilder();

    for (int i = 0; i < length; i++) {
      int tmp = random.nextInt(measure);
      int result = (unicode.getUnicodeStart() + tmp);
      stringBuilder.append((char) result);
      // System.out.println(stringBuilder.toString());

    }

    return stringBuilder.toString();
  }
}
