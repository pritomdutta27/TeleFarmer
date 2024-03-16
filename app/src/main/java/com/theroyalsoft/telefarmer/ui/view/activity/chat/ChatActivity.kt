package com.theroyalsoft.telefarmer.ui.view.activity.chat

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.text.InputType
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bio.medico.patient.common.AppKey
import bio.medico.patient.common.AppKeyLog
import bio.medico.patient.common.AttachmentTypes
import bio.medico.patient.common.AttachmentTypes.AttachmentType
import bio.medico.patient.model.apiResponse.chat.MessageBody
import bio.medico.patient.socketUtils.SocketKeyChat
import bio.medico.patient.socketUtils.SocketManagerChat
import bio.medico.patient.socketUtils.SocketManagerChat.ISocketChatMessage
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.MessageModel
import com.skh.hkhr.util.IntentUtil
import com.skh.hkhr.util.JsonUtil
import com.skh.hkhr.util.NetUtil
import com.skh.hkhr.util.log.ToastUtil
import com.skh.hkhr.util.view.OnSingleClickListener
import com.theroyalsoft.telefarmer.databinding.ActivityChatBinding
import com.theroyalsoft.telefarmer.extensions.checkInternet
import com.theroyalsoft.telefarmer.extensions.fitSystemWindowsAndAdjustResize
import com.theroyalsoft.telefarmer.extensions.getFile
import com.theroyalsoft.telefarmer.extensions.resizeBitMapImage1
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.extensions.showLoadingDialog
import com.theroyalsoft.telefarmer.extensions.showToast
import com.theroyalsoft.telefarmer.ui.view.activity.chat.interfaces.OnMessageItemClick
import com.theroyalsoft.telefarmer.utils.ImagePickUpUtil
import com.theroyalsoft.telefarmer.utils.applyTransparentStatusBarAndNavigationBar
import com.theroyalsoft.telefarmer.utils.isInvisible
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.google.GoogleEmojiProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import timber.log.Timber
import java.io.File
import java.io.IOException

@AndroidEntryPoint
class ChatActivity : AppCompatActivity(), OnMessageItemClick {

    private val viewModel: ChatViewModel by viewModels()

    private lateinit var emojIcon: EmojiPopup
    private lateinit var activity: Activity
    private lateinit var socketManagerChat: SocketManagerChat
    private lateinit var messageAdapter: MessageAdapter

    private var currentPhotoPath: String = ""
    private var bitmapImage: Bitmap? = null
    private var imgUrl = "";

    private lateinit var binding: ActivityChatBinding
    private val uiName = AppKeyLog.UI_CHAT
    private val uiType: String
        private get() = IntentUtil.getIntentValue(AppKey.INTENT_UI, intent)

    private lateinit var loading: Dialog

    val messageBodies = ArrayList<MessageBody>()

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            // Use the returned uri.
            val uriContent = result.uriContent
            //val uriFilePath = result.getUriFilePath(requireContext()) // optional usage

            bitmapImage =
                MediaStore.Images.Media.getBitmap(contentResolver, uriContent)
            //uploadImage(bitmapImage)

            bitmapImage.let { image ->
                var imgFile: File = getFile(image)
                try {
                    val compressToBitmap = imgFile.path.resizeBitMapImage1(200, 200)
                    imgFile = getFile(compressToBitmap)
                } catch (e: IOException) {
                    Timber.e("Error:$e")
                    return@registerForActivityResult
                }

                val imgFileName: String = imgFile.name

                val mediaType: MediaType? = "image/jpg".toMediaTypeOrNull()

                val requestFile: RequestBody = imgFile.asRequestBody(mediaType)
                val imageBody: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "file",
                    imgFileName,
                    requestFile
                )
                loading.show()
                viewModel.uploadFile(imageBody, AppKey.CHAT_FOLDER)
            }
        } else {
            // An error occurred.
            val exception = result.error
        }
    }

    var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>? = null

    companion object {
        @JvmStatic
        fun newIntent(context: Context, phone: String): Intent =
            Intent(context, ChatActivity::class.java).putExtra("phone", phone)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransparentStatusBarAndNavigationBar()
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        EmojiManager.install(GoogleEmojiProvider())

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rootView.fitSystemWindowsAndAdjustResize()
        loading = showLoadingDialog()
        loading.show()


        photoPickerInitialize()

        binding.newMessage.apply {
            setInputType(InputType.TYPE_CLASS_TEXT)
            requestFocus()
            val mgr = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            mgr.showSoftInput(this, InputMethodManager.SHOW_FORCED)
        }


        activity = this

        // AppLog.sendUiOpen(uiName)
        // logEventWithNumber(AppKey.EVENT_CHAT_SCREEN_VIEW)
//
        socketManagerChat = SocketManagerChat.getSocketManager({ connection: Boolean ->
            Timber.e(
                "connection:$connection"
            )
        }, iSocketChatMessage)
//
        socketManagerChat.socketConnect()
//
        initUI()
        setLanguage()
        callMessagesApi()
        imgApi()
        ifApiGetError()
    }

    private fun setLanguage() {

        binding.apply {
            llHeader.btnBack.setSafeOnClickListener { finish() }
            llHeader.imgLeft.isInvisible()
            llHeader.tvToolbarTitle.text = getString(bio.medico.patient.assets.R.string.chat_bn)
            newMessage.hint = getString(bio.medico.patient.assets.R.string.type_a_message_bn)
        }
    }

    private val iSocketChatMessage = ISocketChatMessage { messageBody ->
        Timber.i("Message:" + JsonUtil.getJsonStringFromObject(messageBody))
//        messageBodies.add(messageBody)
        CoroutineScope(Dispatchers.Main).launch {
            messageAdapter.setData(messageBody,binding.recyclerView)
            binding.recyclerView.scrollToPosition(messageAdapter.itemCount - 1)
        }
    }

    private fun initUI() {
        //setup recycler view
        messageAdapter = MessageAdapter(
            this,
            LocalData.getUserUuid(),
            binding.newMessage
        )
        messageAdapter.setHasStableIds(true)


        val mLinearLayoutManager = LinearLayoutManager(activity)
//        mLinearLayoutManager.reverseLayout = true
        mLinearLayoutManager.stackFromEnd = true     // items gravity sticks to bottom
        mLinearLayoutManager.reverseLayout = false

        binding.recyclerView.apply {
            layoutManager = mLinearLayoutManager
            adapter = messageAdapter
//            val viewPool = RecyclerView.RecycledViewPool()
//            setRecycledViewPool(viewPool)
            setItemViewCacheSize(20)
            setHasFixedSize(true)
            scrollToPosition(messageAdapter.itemCount - 1)
        }

        emojIcon = EmojiPopup.Builder.fromRootView(binding.rootView).setOnEmojiPopupShownListener { /*if (addAttachmentLayout.getVisibility() == View.VISIBLE) {
                    addAttachmentLayout.setVisibility(View.GONE);
                    addAttachment.animate().setDuration(400).rotationBy(-45).start();
                }*/
        }.build(binding.newMessage)

        binding.attachmentEmoji.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                emojIcon.toggle()
            }
        })

        binding.voiceToText.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                startActivityForResult(
                    Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).putExtra(
                        RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        "en-US"
                    ), ImagePickUpUtil.REQUEST_CODE_VOICE
                )
            }
        })

        binding.addAttachment.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                pickMedia?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        })

        binding.send.setOnClickListener(object : OnSingleClickListener() {
            override fun onSingleClick(view: View) {
                if (!TextUtils.isEmpty(binding.newMessage.text.toString().trim { it <= ' ' })) {
                    sendMessage(binding.newMessage.text.toString(), AttachmentTypes.NONE_TEXT, "")
                }
            }
        })
    }

    private fun closeUi() {
        finish()
    }

    private fun sendMessage(
        messageBody: String,
        @AttachmentType attachmentType: Int,
        attachment: String
    ) {
        if (!checkInternet(true)) {
            Timber.e(AppKey.NO_INTERNET)
            return
        }

        Timber.e("messageBody:$messageBody")
        Timber.e("attachmentType:$attachmentType")
        Timber.e("attachment:$attachment")

        when (attachmentType) {
            AttachmentTypes.IMAGE -> {
                val messageBody11 =
                    MessageModel.getModel(AttachmentTypes.attachmentTypeImage, attachment)
                val messageJson1 = JsonUtil.getJsonStringFromObject(messageBody11)
                socketManagerChat.sendData(SocketKeyChat.LISTENER_message_send, messageJson1)
//                messageBodies.add(messageBody11)
                messageAdapter.setData(messageBody11, binding.recyclerView)
//                binding.recyclerView.scrollToPosition(messageAdapter.itemCount - 1)
                //logEventWithNumber(AppKey.EVENT_SEND_CHAT_CLICK, "IMAGE_SEND")
            }

            else -> {
                if (messageBody.trim { it <= ' ' }.isEmpty()) {
                    return
                }
                //logEventWithNumber(AppKey.EVENT_SEND_CHAT_CLICK, messageBody)
                val messageBody1 = MessageModel.getModel(messageBody)
                val messageJson = JsonUtil.getJsonStringFromObject(messageBody1)
                socketManagerChat.sendData(SocketKeyChat.LISTENER_message_send, messageJson)
//                messageBodies.add(messageBody1)
                messageAdapter.setData(messageBody1,binding.recyclerView)
            }
        }
        binding.newMessage.setText("")
        binding.recyclerView.scrollToPosition(messageAdapter.itemCount - 1)
    }

    fun clickImgCamera() {
        if (NetUtil.isNetworkAvailable(activity, binding.addAttachment)) {
            choosePhotoFromGallery()
        } else {
            ToastUtil.showToastMessage(AppKey.ERROR_INTERNET_CONNECTION)
        }
    }

    private fun choosePhotoFromGallery() {
        Timber.e("choosePhotoFromGallery")
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(
            galleryIntent,
            ImagePickUpUtil.REQUEST_CODE_GALLERY
        )
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Timber.e("onActivityResult")
        if (resultCode == RESULT_CANCELED) {
            Timber.e("RESULT_CANCELED")
            return
        }
        try {
            when (requestCode) {
                ImagePickUpUtil.REQUEST_CODE_GALLERY -> if (data != null) {
                    Timber.e("data:$data")
                    val contentURI = data.data
                    cropImage.launch(
                        CropImageContractOptions(
                            contentURI,
                            CropImageOptions(true, true)
                        )
                    )
                }

                ImagePickUpUtil.REQUEST_CODE_CAMERA -> setPic()
                ImagePickUpUtil.REQUEST_CODE_VOICE -> {
                    val matches = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (matches != null && matches.size > 0) {
                        val searchWrd = matches[0]
                        if (!TextUtils.isEmpty(searchWrd)) {
                            binding.newMessage.setText(searchWrd)
                            //getSearchResult(searchWrd);
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e("Error:$e")
            //  ApiManager.sendApiLogErrorCodeScope(e)
        }
    }

    private fun setPic() {
        Timber.e("file path:: $currentPhotoPath")
        val myBitmap = BitmapFactory.decodeFile(currentPhotoPath)
        //uploadImage(myBitmap)
    }

    //=================API CALL==========================
//    private fun uploadImage(bitmapImage: Bitmap?) {
//
////        LoadingUtil.show(AppKey.UPLOADING, activity)
//
//        var imgFile =
//            ImagePickUpUtil.getFile(activity, bitmapImage)
//
//        try {
//            val compressToBitmap = Compressor(activity).compressToBitmap(imgFile)
//            imgFile = ImagePickUpUtil.getFile(
//                activity,
//                compressToBitmap
//            )
//        } catch (e: IOException) {
//            Timber.e("Error:$e")
//            // ApiManager.sendApiLogErrorCodeScope(e)
//        }
//
//        val imgFileName = imgFile.name
//
//        val mediaType: MediaType? = "image/jpg".toMediaTypeOrNull()
//
//        val requestFile: RequestBody = imgFile.asRequestBody(mediaType)
//        val imageBody: MultipartBody.Part = MultipartBody.Part.createFormData(
//            "file",
//            imgFileName,
//            requestFile
//        )
//        loading.show()
//        viewModel.uploadFile(imageBody, AppKey.CHAT_FOLDER)
//    }

    private fun photoPickerInitialize() {

        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    // Start cropping activity for pre-acquired image saved on the device and customize settings.
                    cropImage.launch(CropImageContractOptions(uri, CropImageOptions(true, true)))
                } else {
                    Log.e("PhotoPicker", "No media selected")
                }
            }
    }

    private fun callMessagesApi() {
        // CustomProgress.showProgress(activity)
        lifecycleScope.launch {
                viewModel._chatStateFlow.collectLatest {
                    loading.hide()
//                    messageBodies.addAll(it.message)
                    messageAdapter.setData(it.message, binding.recyclerView)
                }
        }
    }

    private fun imgApi() {
        lifecycleScope.launch {
            viewModel._imgUrlStateFlow.collectLatest { imageUrl ->
                loading.hide()
                withContext(Dispatchers.Main) {
                    sendMessage("", AttachmentTypes.IMAGE, imageUrl)
                }
            }
        }
    }

    private fun ifApiGetError() {
        lifecycleScope.launch {
            viewModel._errorFlow.collect { errorStr ->
                withContext(Dispatchers.Main) {
                    loading.hide()
                    if (errorStr.isNotEmpty()) {
                        showToast(errorStr)
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (socketManagerChat != null) {
            socketManagerChat.socketDisconnect()
        }
    }

    override fun onBackPressed() {
        closeUi()
    }

    override fun onMessageClick(message: MessageBody, position: Int) {
        // TODO("Not yet implemented")
    }

    override fun onMessageLongClick(message: MessageBody, position: Int) {
        //  TODO("Not yet implemented")
    }

}