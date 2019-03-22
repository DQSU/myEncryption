package gateway.hitrontech.com.encryption.fragment.about

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gateway.hitrontech.com.encryption.R
import gateway.hitrontech.com.encryption.base.BaseFragment
import  gateway.hitrontech.com.encryption.databinding.FragmentAboutBinding
import gateway.hitrontech.com.encryption.utils.FileUtils

class AboutFragment : BaseFragment() {

    private var mBinding: FragmentAboutBinding? = null

    override fun createView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (!viewExisted) {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)
        }
        return mBinding!!.root
    }

    override fun viewFound(view: View?) {
        if (!viewExisted) {
            mBinding!!.filePath.text = FileUtils.resultPath
        }
    }

    override fun getTitle() = "关于"

    companion object {
        fun getInstance(): AboutFragment {
            val fragment = AboutFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}