package com.theroyalsoft.telefarmer.ui.view.activity.chat

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import bio.medico.patient.common.AppKey
import bio.medico.patient.common.AppKeyLog
import bio.medico.patient.common.AttachmentTypes
import bio.medico.patient.common.AttachmentTypes.AttachmentType
import bio.medico.patient.model.apiResponse.chat.MessageBody
import bio.medico.patient.socketUtils.SocketKeyChat
import bio.medico.patient.socketUtils.SocketManagerChat
import bio.medico.patient.socketUtils.SocketManagerChat.ISocketChatMessage
import com.farmer.primary.network.dataSource.local.LocalData
import com.farmer.primary.network.dataSource.local.MessageModel
import com.skh.hkhr.util.IntentUtil
import com.skh.hkhr.util.JsonUtil
import com.skh.hkhr.util.NetUtil
import com.skh.hkhr.util.log.ToastUtil
import com.skh.hkhr.util.view.LoadingUtil
import com.skh.hkhr.util.view.OnSingleClickListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theroyalsoft.telefarmer.databinding.ActivityChatBinding
import com.theroyalsoft.telefarmer.extensions.checkInternet
import com.theroyalsoft.telefarmer.extensions.setSafeOnClickListener
import com.theroyalsoft.telefarmer.ui.view.activity.chat.interfaces.OnMessageItemClick
import com.theroyalsoft.telefarmer.utils.ImagePickUpUtil
import com.theroyalsoft.telefarmer.utils.applyTransparentStatusBarAndNavigationBar
import com.theroyalsoft.telefarmer.utils.isInvisible
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.google.GoogleEmojiProvider
import id.zelory.compressor.Compressor
import timber.log.Timber
import java.io.IOException


class ChatActivity : AppCompatActivity(), OnMessageItemClick {

    private lateinit var emojIcon: EmojiPopup
    private lateinit var activity: Activity
    private lateinit var socketManagerChat: SocketManagerChat
    private lateinit var messageAdapter: MessageAdapter

    private var currentPhotoPath: String = ""
    private var bitmapImage: Bitmap? = null

    private lateinit var binding: ActivityChatBinding
    private val uiName = AppKeyLog.UI_CHAT
    private val uiType: String
        private get() = IntentUtil.getIntentValue(AppKey.INTENT_UI, intent)

    companion object {
        @JvmStatic
        fun newIntent(context: Context, phone: String): Intent =
            Intent(context, ChatActivity::class.java).putExtra("phone", phone)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applyTransparentStatusBarAndNavigationBar()
        EmojiManager.install(GoogleEmojiProvider())

        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        activity = this
//        if (LocalData.getUserUuid().isEmpty()) {
//            Timber.e("User Id Not Found")
//            IntentUtil.goActivityCleatAllTop(this, LoginActivity::class.java)
//        }


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
//        callMessagesApi()
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
        messageAdapter.setData(messageBody, binding.recyclerView)
    }

    private fun initUI() {


        //setup recycler view
        messageAdapter = MessageAdapter(
            this,
            LocalData.getUserUuid(),
            binding.newMessage
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = messageAdapter
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
                clickImgCamera()
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
//        if (AppConfig.appPreviousOpen) {
//            finish()
//        } else {
//            if (LocalData.getUserUuid().isEmpty()) {
//                IntentUtil.goActivityCleatAllTop(this, LoginActivity::class.java)
//            } else {
//                IntentUtil.goActivityCleatAllTop(this, MainActivity::class.java)
//            }
//        }
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
                messageAdapter.setData(messageBody11, binding.recyclerView)
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
                messageAdapter.setData(messageBody1, binding.recyclerView)
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
                    CropImage.activity(contentURI).start(this)
                }

                CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                    val result = CropImage.getActivityResult(data)
                    if (resultCode == RESULT_OK) {
                        val resultUri = result.uri
                        bitmapImage =
                            MediaStore.Images.Media.getBitmap(activity.contentResolver, resultUri)
                        uploadImage(bitmapImage)
                    } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                        ToastUtil.showToastMessage("Upload Failed!")
                    }
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
        uploadImage(myBitmap)
    }

    //=================API CALL==========================
    private fun uploadImage(bitmapImage: Bitmap?) {

        LoadingUtil.show(AppKey.UPLOADING, activity)

        var imgFile =
            ImagePickUpUtil.getFile(activity, bitmapImage)

        try {
            val compressToBitmap = Compressor(activity).compressToBitmap(imgFile)
            imgFile = ImagePickUpUtil.getFile(
                activity,
                compressToBitmap
            )
        } catch (e: IOException) {
            Timber.e("Error:$e")
            // ApiManager.sendApiLogErrorCodeScope(e)
        }

        val imgFileName = imgFile.name
//        ApiManager.uploadImage(imgFile, imgFileName, AppKey.CHAT_FOLDER, object : IApiResponse {
//            override fun <T> onSuccess(model: T) {
//                val commonResponse = model as CommonResponse
//                val imageUrl =
//                    Objects.requireNonNull(LocalData.getMetaInfoMetaData()).imgBaseUrl + commonResponse.file
//                Timber.e("imageUrl::$imageUrl")
//                ThreadUtil.startTask({
//                    sendMessage("", AttachmentTypes.IMAGE, imageUrl)
//                    ToastUtil.showToastMessage("Sent")
//                }, 1000, false)
//            }
//
//            override fun onFailed(message: String) {}
//        })
    }

    private fun callMessagesApi() {
       // CustomProgress.showProgress(activity)
        val conversionId = MessageModel.getConversationID()
//        ApiManager.getMessages(conversionId, object : IApiResponse {
//            override fun <T> onSuccess(model: T) {
//                try {
//                    val responseCallHistory = model as ResponsemessageBody
//                    if (responseCallHistory == null) {
//                        Timber.e("Error:" + "responseChat null")
//                    }
//                    messageAdapter.setData(responseCallHistory.message, binding.recyclerView)
//
//
//                    //Timber.e("messageAdapter:: :" + dataList.get(0).getSenderId());
//                } catch (exception: Exception) {
//                    Timber.e("Error:$exception")
//                    ApiManager.sendApiLogErrorApi(exception, AppUrl.URL_MESSAGES)
//                }
//                CustomProgress.hideProgress()
//            }
//
//            override fun onFailed(message: String) {
//                CustomProgress.hideProgress()
//                ToastUtil.showToastMessage(message)
//            }
//        })
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


    /*

        private val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val intent = result.data
                    val message = intent?.getStringExtra(AppUrl.KEY_MESSAGE)
                    val doctorId = IntentUtil.getIntentValue(AppKey.INTENT_doctorId, intent)
                    val getPatientId = IntentUtil.getIntentValue(AppKey.INTENT_PatientId, intent)
                    val getScheduleId = IntentUtil.getIntentValue(AppKey.INTENT_scheduleId, intent)

                    val isDoctorRoom = intent?.getBooleanExtra(AppUrl.KEY_DOCTOR_ROOM, false)

                    Timber.e("isDoctorRoom:$isDoctorRoom")



                    Timber.e("startForResult")


                }


                Timber.e("onActivityResult")
                val data = result.data


                when (val resultCode = result.resultCode) {

                    RESULT_CANCELED -> {
                        Timber.e("RESULT_CANCELED")
                        return@registerForActivityResult
                    }

                    ImagePickUpUtil.REQUEST_CODE_GALLERY -> if (data != null) {
                        Timber.e("data:$data")
                        val contentURI = data.data
                        CropImage.activity(contentURI).start(this)
                    }

                    CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                        if (data == null) {
                            Timber.e("data==null")
                            return@registerForActivityResult
                        }

                        val result = CropImage.getActivityResult(data)
                        if (resultCode == RESULT_OK) {
                            val resultUri = result.uri
                            bitmapImage =
                                MediaStore.Images.Media.getBitmap(activity.contentResolver, resultUri)
                            uploadImage(bitmapImage)
                        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                            ToastUtil.showToastMessage("Upload Failed!")
                        }
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

            }
    */

}