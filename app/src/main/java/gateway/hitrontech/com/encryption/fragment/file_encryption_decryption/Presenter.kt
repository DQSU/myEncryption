package gateway.hitrontech.com.encryption.fragment.file_encryption_decryption


import com.hitrontech.hitronencryption.EncryptionManager
import gateway.hitrontech.com.encryption.bean.EncryptionBean
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.CommonFile
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.ExcelFile
import gateway.hitrontech.com.encryption.fragment.file_encryption_decryption.File.FileImpl
import gateway.hitrontech.com.encryption.utils.FileUtils
import gateway.hitrontech.com.encryption.utils.SharePreManager
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

class Presenter internal constructor(private val mView: Contract.View) : Contract.Presenter {

    private val beanList = ArrayList<EncryptionBean>()

    init {
        this.mView.setPresenter(this)
    }


    override fun toFile(type: Int) {
        var file: FileImpl? = null
        when (type) {
            Contract.COMMON_FILE -> file = CommonFile()
            Contract.EXCEL_FILE -> file = ExcelFile()
            else -> {
            }
        }

        // 将未加密的明文全部加密
        for (item in beanList) {
            item.cipherText = EncryptionManager.instance.base64EncoderByAppId(
                    SharePreManager.instance.appId,
                    item.key,
                    item.plainText
            )
        }

        val finalFile = file
        Observable.just(type)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    assert(finalFile != null)
                    finalFile!!.writeFile(beanList,
                            if (type == 0) FileUtils.origin else FileUtils.targetXls)

                    Observable.just<Any>(null).observeOn(Schedulers.io())
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe { mView.showMessage("文件已存到" + FileUtils.targetXls) }
    }

    override fun readFile(type: Int) {
        var file: FileImpl? = null
        when (type) {
            Contract.COMMON_FILE -> file = CommonFile()
            Contract.EXCEL_FILE -> file = ExcelFile()
            else -> {
            }
        }

        val finalFile = file
        Observable.just(type)
                .subscribeOn(Schedulers.io())
                .flatMap {
                    finalFile!!
                            .readFile(beanList, if (type == 0) FileUtils.origin else FileUtils.targetXls)
                    Observable.just(beanList).observeOn(Schedulers.io())
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<ArrayList<EncryptionBean>>() {
                    override fun onCompleted() {

                    }

                    override fun onError(e: Throwable) {
                        mView.showMessage("暂无文件，请将.xls文件放到" + FileUtils.resultPath)
                    }

                    override fun onNext(list: ArrayList<EncryptionBean>) {
                        if (list.isEmpty()) {
                            mView.showMessage("暂无文件，请将.xls文件放到" + FileUtils.resultPath)
                        } else {
                            mView.setList(list)
                            mView.showMessage("已从文件" + FileUtils.targetXls + "中读取文件")
                        }
                    }

                })
    }


    override fun start() {
        beanList.clear()
        val type = Contract.EXCEL_FILE
        readFile(type)

    }

/*
    private fun generateFile() {
        beanList.clear()
        for (i in 0..14) {
            val item = EncryptionBean()
            item.plainText = RandomStringUtils.getInstance(Constants.JAPAN).getRandomString(15)
            beanList.add(item)
        }
    }
*/


/*
    private fun getRandomString(length: Int): String {

        val random = Random()
        val sb = StringBuilder()
        for (i in 0 until length) {
            val number = random.nextInt(BASE.length)
            sb.append(BASE[number])
        }
        return sb.toString()
    }
*/

/*
    private fun writePlainText(list: List<String>) {

        Observable.just(list)
                .subscribeOn(Schedulers.io())
                .flatMap { list ->
                    try {
                        val bufferedWriter = BufferedWriter(
                                FileWriter(FileUtils.origin))

                        for (item in list) {
                            bufferedWriter.append(item).append("\n\n")
                        }
                        bufferedWriter.flush()
                        bufferedWriter.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    Observable.just(true).observeOn(Schedulers.io())
                }.observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    if (result!!) {
                        mView.showMessage("已从文件" + FileUtils.targetXls + "中读取文件")
                    }
                }
    }
*/


}
