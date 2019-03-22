package gateway.hitrontech.com.encryption.fragment.add_item

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.*
import gateway.hitrontech.com.encryption.R
import gateway.hitrontech.com.encryption.base.BaseFragment
import gateway.hitrontech.com.encryption.bean.EncryptionBean
import gateway.hitrontech.com.encryption.databinding.FragmentAddItemBinding

class AddItemFragment : BaseFragment(), Contract.View {

    private lateinit var mBinding: FragmentAddItemBinding

    private lateinit var mPresenter: Contract.Presenter

    private val data = EncryptionBean()

    private lateinit var list: ArrayList<EncryptionBean>

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (!viewExisted) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_item, container, false)
        }
        return mBinding.root
    }

    override fun viewFound(view: View?) {
        if (!viewExisted) {
            Presenter(this)

            mBinding.data = data
            list = arguments!![LIST_OF_ENCRYPTION] as ArrayList<EncryptionBean>

        }
    }

    override fun getTitle() = "添加"

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.commit, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun setPresenter(presenter: Contract.Presenter) {
        mPresenter = presenter
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.commit -> {
                mPresenter.duplicateCheck(data, list)
                onBackPressedSupport()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val LIST_OF_ENCRYPTION = "LIST_OF_ENCRYPTION"

        fun getInstance(list: ArrayList<EncryptionBean>): AddItemFragment {
            val fragment = AddItemFragment()
            val args = Bundle()
            args.putSerializable(LIST_OF_ENCRYPTION, list)
            fragment.arguments = args
            return fragment
        }
    }

}