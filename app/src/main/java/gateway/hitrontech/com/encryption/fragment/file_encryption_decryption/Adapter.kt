package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hitrontech.hitronencryption.EncryptionManager
import com.jakewharton.rxbinding2.view.RxView
import gateway.hitrontech.com.encryption.R
import gateway.hitrontech.com.encryption.bean.EncryptionBean
import gateway.hitrontech.com.encryption.databinding.ItemPlainTextBinding
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.Adapter.ViewHolder
import gateway.hitrontech.com.encryption.utils.SharePreManager
import java.util.*

internal class Adapter : RecyclerView.Adapter<ViewHolder>() {

    private var beanList = ArrayList<EncryptionBean>()

    internal fun setList(list: ArrayList<EncryptionBean>) {
        this.beanList = list
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPlainTextBinding>(
                LayoutInflater.from(viewGroup.context),
                R.layout.item_plain_text,
                viewGroup,
                false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val bean = beanList[i]
        viewHolder.bindViewHolder(bean)
    }

    override fun getItemCount(): Int {
        return beanList.size
    }

    internal inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: ItemPlainTextBinding? = DataBindingUtil.findBinding(itemView)

        @SuppressLint("CheckResult")
        fun bindViewHolder(item: EncryptionBean) {
            binding!!.bean = item
            RxView.clicks(binding.decryption)
                    .subscribe {
                        binding.decryptionText.text = EncryptionManager.instance.base64DecoderByAppId(
                                SharePreManager.instance.appId,
                                item.key,
                                binding.cipherText.text!!.toString()
                        )
                    }

            RxView.clicks(binding.encryption)
                    .subscribe {
                        item.cipherText = EncryptionManager.instance.base64EncoderByAppId(
                                SharePreManager.instance.appId,
                                item.key,
                                item.plainText
                        )
                    }
        }
    }

}
