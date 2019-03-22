package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption

import android.annotation.SuppressLint
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import gateway.hitrontech.com.encryption.R
import gateway.hitrontech.com.encryption.base.BaseFragment
import gateway.hitrontech.com.encryption.bean.EncryptionBean
import gateway.hitrontech.com.encryption.databinding.FragmentFileEncryptionDecryptionBinding
import java.util.*

class FileEncryptionDecryptionFragment : BaseFragment(), Contract.View {

    private var mBinding: FragmentFileEncryptionDecryptionBinding? = null

    private var mPresenter: Contract.Presenter? = null

    private val mAdapter = Adapter()

    private var isFirst = true

    override fun createView(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

        if (!viewExisted) {
            mBinding = DataBindingUtil
                    .inflate(inflater, R.layout.fragment_file_encryption_decryption, container, false)
        }
        return mBinding!!.root
    }

    @SuppressLint("CheckResult")
    override fun viewFound(view: View?) {

        if (!viewExisted) {
            Presenter(this)

            mBinding!!.recyclerView.layoutManager = LinearLayoutManager(mContext)
            mBinding!!.recyclerView.adapter = mAdapter

            mPresenter!!.start()

            RxView.clicks(mBinding!!.writeCipherText)
                    .subscribe { mPresenter!!.toFile(Contract.EXCEL_FILE) }

            RxView.clicks(mBinding!!.readFile)
                    .subscribe { mPresenter!!.readFile(Contract.EXCEL_FILE) }
        }
    }

    override fun getTitle(): String {
        return "文件加密/解密"
    }

    override fun setList(list: ArrayList<EncryptionBean>) {
        if (isFirst) {
            isFirst = false
            this.mAdapter.setList(list)
        }
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        this.mPresenter = presenter
    }

    companion object {

        val instance: Fragment
            get() {
                val fragment = FileEncryptionDecryptionFragment()
                val args = Bundle()
                fragment.arguments = args
                return fragment
            }
    }
}
