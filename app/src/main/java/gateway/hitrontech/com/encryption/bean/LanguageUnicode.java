package gateway.hitrontech.com.encryption.bean;

public class LanguageUnicode {

  private int unicodeStart;

  private int unicodeEnd;

  public LanguageUnicode(int start, int end) {
    this.unicodeStart = start;
    this.unicodeEnd = end;
  }

  public int getUnicodeStart() {
    return unicodeStart;
  }

  public void setUnicodeStart(int unicodeStart) {
    this.unicodeStart = unicodeStart;
  }

  public int getUnicodeEnd() {
    return unicodeEnd;
  }

  public void setUnicodeEnd(int unicodeEnd) {
    this.unicodeEnd = unicodeEnd;
  }
}
