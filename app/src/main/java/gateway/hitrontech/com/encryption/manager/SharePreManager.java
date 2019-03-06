package gateway.hitrontech.com.encryption.manager;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;
import java.util.TreeSet;

public class SharePreManager {
    private static final String mPreName = "pre_sharePre_encryption";
    private static SharePreManager sPreManager;
    private static SharedPreferences sSharedPre;
    private final String mPassword = "password";

    private SharePreManager() {
    }

    public static SharePreManager getInstance(Context context) {
        synchronized (SharePreManager.class) {
            if (sSharedPre == null) {
                sPreManager = new SharePreManager();
                sSharedPre = context.getSharedPreferences(mPreName,
                    Context.MODE_PRIVATE);
            }
        }
        return sPreManager;
    }

    public void updatePwd(String pwd) {
        SharedPreferences.Editor editor = sSharedPre.edit();
        if (getPwd() != null) {
            getPwd().add(pwd);
        }
        editor.putStringSet(mPassword, getPwd());
        editor.apply();
        editor.clear();
    }

    public Set<String> getPwd() {
        return  sSharedPre.getStringSet(mPassword, new TreeSet<String>());
    }
}
