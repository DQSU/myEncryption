package gateway.hitrontech.com.encryption.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.hitrontech.hitronencryption.EncryptionManager;
import gateway.hitrontech.com.encryption.R;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnClickListener {

  static final String KEY = "com.hitrontech.myhitron.videotron";
  private EditText mPlainText;
  private EditText mPasswordText;
  private EditText mCipheText;
  private Button mEncryptionBtn;
  private Button mDecryptionBtn;
  private EditText mClearText;
  private CheckBox mEditCb;
  private CheckBox mChoiceCb;
  private LinearLayout mPasswordLayout;
  private Spinner mPwdSpinner;
  private String mPassword;
  private List<String> mPasswordList;
  private ArrayAdapter<String> mArrayAdapter;
  private Button mChangeCleartextBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initView();
  }

  private void initView() {
    mChangeCleartextBtn = findViewById(R.id.changeClearText);
    mChangeCleartextBtn.setOnClickListener(this);
    mPlainText = findViewById(R.id.plaintext);
    mPasswordText = findViewById(R.id.password);
    mCipheText = findViewById(R.id.ciphetext);
    mEncryptionBtn = findViewById(R.id.encryption);
    mEncryptionBtn.setOnClickListener(this);
    mDecryptionBtn = findViewById(R.id.decryption);
    mDecryptionBtn.setOnClickListener(this);
    mClearText = findViewById(R.id.cleartext);
    mEditCb = findViewById(R.id.editCb);
    mEditCb.setOnClickListener(this);
    mChoiceCb = findViewById(R.id.choiceCb);
    mChoiceCb.setOnClickListener(this);
    mPasswordLayout = findViewById(R.id.passwordLayout);
    mPwdSpinner = findViewById(R.id.pwdSpinner);

    // mPasswordList = new ArrayList<>(SharePreManager.getInstance(this).getPwd());
    mArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item,
        mPasswordList);
    mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    mPwdSpinner.setAdapter(mArrayAdapter);
    mPwdSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPassword = mArrayAdapter.getItem(position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.encryption:
        encryptionData();
        break;
      case R.id.decryption:
        decryptionData();
        break;
      case R.id.editCb:
        if (mEditCb.isChecked()) {
          mChoiceCb.setChecked(false);
          mPwdSpinner.setVisibility(View.GONE);
          mPasswordLayout.setVisibility(View.VISIBLE);
        } else {
          mPasswordLayout.setVisibility(View.GONE);
        }
        break;
      case R.id.choiceCb:
        if (mChoiceCb.isChecked()) {
          mEditCb.setChecked(false);
          mPasswordLayout.setVisibility(View.GONE);
          mPwdSpinner.setVisibility(View.VISIBLE);
          mArrayAdapter.notifyDataSetChanged();
        } else {
          mPwdSpinner.setVisibility(View.GONE);
        }
        break;
      case R.id.changeClearText:
        if (mPlainText.getText().toString().equals(getString(R.string.test_number))) {
          mPlainText.setText(getString(R.string.ht_security_key));
        } else {
          mPlainText.setText(getString(R.string.test_number));
        }
    }
  }

  private void encryptionData() {
    String encryptionStr = EncryptionManager.getInstance()
        .base64EncoderByAppId(getString(R.string.applicationId), KEY,
            mPlainText.getText().toString());
    Log.e("KEY", "encryptionStr : " + encryptionStr);
    mCipheText.setText(encryptionStr);

  }

  private void decryptionData() {
    String decryptStr = EncryptionManager.getInstance()
        .base64DecoderByAppId(getString(R.string.applicationId), KEY,
            mCipheText.getText().toString());
    Log.e("KEY", " decryptStr : " + decryptStr);
    mClearText.setText(decryptStr);
  }

  private String getSecretKey() {
    if (mPasswordLayout.getVisibility() == View.VISIBLE) {
      mPassword = mPasswordText.getText().toString();
      if (mPassword != null && mPassword.length() > 0) {
        // SharePreManager.getInstance(this).updatePwd(mPassword);
        mPasswordList.clear();
        // mPasswordList.addAll(SharePreManager.getInstance(this).getPwd());
      }
    }
    String secretKey = new StringBuffer()
        .append(getString(R.string.applicationId))/*.append(getString(R.string.versionCode))*/
        .append(mPassword).toString();
    return secretKey;
  }
}
