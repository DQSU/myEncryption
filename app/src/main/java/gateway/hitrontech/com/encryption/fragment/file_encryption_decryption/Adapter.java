package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hitrontech.hitronencryption.EncryptionManager;
import com.jakewharton.rxbinding2.view.RxView;
import gateway.hitrontech.com.encryption.R;
import gateway.hitrontech.com.encryption.bean.EncryptionBean;
import gateway.hitrontech.com.encryption.databinding.ItemPlainTextBinding;
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.Adapter.ViewHolder;
import gateway.hitrontech.com.encryption.utils.SharePreManager;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

  private ArrayList<EncryptionBean> beanList = new ArrayList<>();

  private ItemEvent itemEvent;

  Adapter(ItemEvent event) {
    this.itemEvent = event;
  }

  void setList(ArrayList<EncryptionBean> list) {
    this.beanList = list;
    notifyDataSetChanged();
  }

  ArrayList<EncryptionBean> getList() {
    return beanList;
  }


  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    ItemPlainTextBinding binding = DataBindingUtil.inflate(
        LayoutInflater.from(viewGroup.getContext()),
        R.layout.item_plain_text,
        viewGroup,
        false
    );
    return new ViewHolder(binding.getRoot());
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    EncryptionBean bean = beanList.get(i);
    viewHolder.bindViewHolder(bean);
  }

  @Override
  public int getItemCount() {
    return beanList.size();
  }


  interface ItemEvent {

    void remove(EncryptionBean item, ArrayList<EncryptionBean> list);
  }


  class ViewHolder extends RecyclerView.ViewHolder {

    private ItemPlainTextBinding binding;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      binding = DataBindingUtil.findBinding(itemView);
    }

    @SuppressLint("CheckResult")
    void bindViewHolder(final EncryptionBean item) {
      binding.setBean(item);
      RxView.clicks(binding.decryption)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              binding.decryptionText.setText(
                  EncryptionManager.getInstance().base64DecoderByAppId(
                      SharePreManager.getInstance().getAppId(),
                      item.getKey(),
                      binding.cipherText.getText().toString()
                  )
              );
            }
          });

      RxView.clicks(binding.encryption)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              item.setCipherText(EncryptionManager.getInstance().base64EncoderByAppId(
                  SharePreManager.getInstance().getAppId(),
                  item.getKey(),
                  item.getPlainText()
              ));
            }
          });

      RxView.clicks(binding.remove)
          .subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
              itemEvent.remove(item, getList());
            }
          });
    }
  }

}
