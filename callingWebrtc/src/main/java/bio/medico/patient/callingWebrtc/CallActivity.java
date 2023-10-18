package bio.medico.patient.callingWebrtc;

import android.app.Activity;
import android.content.Intent;

import com.skh.hkhr.util.JsonUtil;
import com.theroyalsoft.mydoc.apputil.internet.NetworkUtils;

import bio.medico.patient.common.AppKey;
import bio.medico.patient.common.UiNavigation;
import bio.medico.patient.model.apiResponse.ResponseSubInfo;
import timber.log.Timber;



public class CallActivity{
    public static void goCallActivity(Activity activity, boolean isVideoCall, boolean isFreeCall, ResponseSubInfo.FreeCallInfo freeCallinfo) {
        if (!NetworkUtils.isConnected()) {
            Timber.e("No Internet Connection!");
            return;
        }


        /*PeerConnectionFactory.InitializationOptions.builder(AppUtilConfig.getAppContext())
        .setFieldTrials("WebRTC-H264HighProfile/Enabled/");


PeerConnectionFactory.initialize();
        PeerConnectionFactory.initialize(options);*/

        Timber.e("isVideoCall:" + isVideoCall);

        UiNavigation.isCallButtonClicked = true;

        Intent intent = new Intent(activity, CallActivityKotlin.class);
        intent.putExtra(AppKey.INTENT_VIDEO_CALL, isVideoCall);
        intent.putExtra(AppKey.INTENT_FREE_CALL, isFreeCall);
        intent.putExtra(AppKey.INTENT_FREE_CALL_INFO, JsonUtil.getJsonStringFromObject(freeCallinfo));
        //activity.startActivity(intent);
        activity.startActivityForResult(intent, AppKey.RESULT_CODE_BUY_PACK);
    }


    public static void goCallActivity(Activity activity, boolean isVideoCall) {
        if (!NetworkUtils.isConnected()) {
            Timber.e("No Internet Connection!");
            return;
        }

        Timber.e("isVideoCall:" + isVideoCall);

        UiNavigation.isCallButtonClicked = true;

        Intent intent = new Intent(activity, CallActivityKotlin.class);
        intent.putExtra(AppKey.INTENT_VIDEO_CALL, isVideoCall);
        activity.startActivity(intent);
    }


}

/*

public class CallActivity extends AppCompatActivity {

  //  public static final int RC_CALL = 111;

    //============================================
    //============================================


    LinearLayout llDrag;
    View llCallEnd;
    FloatingActionButton activity_hang_up_button;


    //====================solar view========================

    View rlSolarView;
    RoundDashView llRoundDashView;
    ImageView imgVDoctor1;
    ImageView imgVDoctor2;
    ImageView imgVDoctor3;
    CircleImageView imgProfile;
    ImageView imgVDoctor4;
    ImageView imgVDoctor5;
    View llCancel;
    FloatingActionButton fbVideoOn;
    FloatingActionButton fbMute;
    FloatingActionButton fbCameraRotate;
    FloatingActionButton fbLoudSpeaker;
    TextView tvSelectedDoctor;
    TextView tvSearchDoctor;
    TextView tvDoctorName;
    TextView tvCallDuration;
    RelativeLayout rlAudioLayout;
    RelativeLayout rrAppbar;
    ImageView imgCallIcon;
    RelativeLayout llMain;

    //============================================
    private SurfaceViewRenderer mLocalSurfaceView;
    private SurfaceViewRenderer mRemoteSurfaceView;

    private Activity activity;
    private CallManager callManager;

    private MediaPlayer welcomeToneMediaPlayer;

    private boolean isCallRejectedFORCE = false;
    private boolean isCallReceived = false;
    private boolean isCallNotFound = false;
    private boolean isFinish = false;
    private boolean isDoctorMakeAudioCAll = false;

    private ResponseSingleDoctor.Doctor doctorModel;
    private String releaseDoctorID = "";


    private AudioManager mAudioManager = null;
    private AppAudioManager appAudioManager;

    private final String uiName = AppKeyLog.UI_CALL;


    public static void drawPath(ImageView imageView, float radius, int delay, float x, float y) {
        Path path4 = new Path();
        path4.addCircle(x, y, radius, Path.Direction.CW);
        ViewPathAnimator.animate(imageView, path4, delay, 3);
    }

    private boolean isVideoCall() {
        return IntentUtil.getIntentBooleanValue(AppKey.INTENT_VIDEO_CALL, getIntent());
    }

    private boolean isFreeCall() {
        return IntentUtil.getIntentBooleanValue(AppKey.INTENT_FREE_CALL, getIntent());
    }


    private ResponseSubInfo.FreeCallinfo getFreeCallInfo() {
        String freeCallInfoJson = IntentUtil.getIntentValue(AppKey.INTENT_FREE_CALL_INFO, getIntent());
        return JsonUtil.getModelFromStringJson(freeCallInfoJson, ResponseSubInfo.FreeCallinfo.class);
    }

    public static void goCallActivity(Activity activity, boolean isVideoCall, boolean isFreeCall, ResponseSubInfo.FreeCallinfo freeCallinfo) {
        if (!NetworkUtils.isConnected()) {
            Timber.e("No Internet Connection!");
            return;
        }


        */
/*PeerConnectionFactory.InitializationOptions.builder(AppUtilConfig.getAppContext())
        .setFieldTrials("WebRTC-H264HighProfile/Enabled/");


PeerConnectionFactory.initialize();
        PeerConnectionFactory.initialize(options);*//*


        Timber.e("isVideoCall:" + isVideoCall);

        UiNavigation.isCallButtonClicked = true;

        Intent intent = new Intent(activity, CallActivity.class);
        intent.putExtra(AppKey.INTENT_VIDEO_CALL, isVideoCall);
        intent.putExtra(AppKey.INTENT_FREE_CALL, isFreeCall);
        intent.putExtra(AppKey.INTENT_FREE_CALL_INFO, JsonUtil.getJsonStringFromObject(freeCallinfo));
        //activity.startActivity(intent);
        activity.startActivityForResult(intent, AppKey.RESULT_CODE_BUY_PACK);
    }


    public static void goCallActivity(Activity activity, boolean isVideoCall) {
        if (!NetworkUtils.isConnected()) {
            Timber.e("No Internet Connection!");
            return;
        }

        Timber.e("isVideoCall:" + isVideoCall);

        UiNavigation.isCallButtonClicked = true;

        Intent intent = new Intent(activity, CallActivityKotlin.class);
        intent.putExtra(AppKey.INTENT_VIDEO_CALL, isVideoCall);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);


        //============================================

        llDrag = findViewById(R.id.llDrag);


        llCallEnd = findViewById(R.id.llCallEnd);

        activity_hang_up_button = findViewById(R.id.activity_hang_up_button);


        //====================solar view========================
        rlSolarView = findViewById(R.id.rlSolarView);

        llRoundDashView = findViewById(R.id.llRoundDashView);
        imgVDoctor1 = findViewById(R.id.imgVDoctor1);

        imgVDoctor2 = findViewById(R.id.imgVDoctor2);

        imgVDoctor3 = findViewById(R.id.imgVDoctor3);
        imgProfile = findViewById(R.id.imgProfile);

        imgVDoctor4 = findViewById(R.id.imgVDoctor4);

        imgVDoctor5 = findViewById(R.id.imgVDoctor5);
        llCancel = findViewById(R.id.llCancel);

        fbVideoOn = findViewById(R.id.fbVideoOn);

        fbMute = findViewById(R.id.fbMute);

        fbCameraRotate = findViewById(R.id.fbCameraRotate);

        fbLoudSpeaker = findViewById(R.id.fbLoudSpeaker);
        tvSelectedDoctor = findViewById(R.id.tvSelectedDoctor);
        tvSearchDoctor = findViewById(R.id.tvSearchDoctor);
        tvDoctorName = findViewById(R.id.tvDoctorName);
        tvCallDuration = findViewById(R.id.tvCallDuration);
        rlAudioLayout = findViewById(R.id.rlAudioLayout);

        rrAppbar = findViewById(R.id.rrAppbar);
        imgCallIcon = findViewById(R.id.imgCallIcon);
        llMain = findViewById(R.id.llMain);

        activity = this;
        callManager = CallManager.getInstance();
        callManager.initUi(mPeerConnectionObserver);

        //=======================================================================
        mLocalSurfaceView = findViewById(R.id.LocalSurfaceView);
        mRemoteSurfaceView = findViewById(R.id.RemoteSurfaceView);
        ViewTextUtil.setVisibility(llDrag, View.GONE);


        appAudioManager = new AppAudioManager(isVideoCall(), this, fbLoudSpeaker);


        callUISetup();


        WrtcSignalManager.setSignalEventListener(mICallUiListener);

        //=======================================================================
        start();

        getDoctorApiCall();

        checkAudioFocus();
        //=======Search Doctor (with Solar UI)==============
        initSolarUI();
        initPlayerWelcomeTone();
        //==================================================
*/
/*

        AudioManager am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.onA

        am.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        //resume music player
        am.abandonAudioFocus(null);
*//*


        FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_SCREEN_VIEW, isVideoCall() ? AppKey.VIDEO_CALL : AppKey.AUDIO_CALL);

        AppLog.sendUiOpen(uiName);
    }


    private void checkAudioFocus() {

        if (mAudioManager == null) {
            mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        }
        if (mAudioManager.isMusicActive()) {

            Intent intent = new Intent("com.android.music.musicservicecommand");
            intent.putExtra("command", "pause");
            AppUtilConfig.getAppContext().sendBroadcast(intent);

            int result = mAudioManager.requestAudioFocus(new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int i) {
                    Timber.e("udioFocusChange:%s", i);
                }
            }, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        }
    }

    private void callUISetup() {
        if (isVideoCall()) {
            ViewTextUtil.setVisibility(fbVideoOn, View.VISIBLE);
            ViewTextUtil.setVisibility(fbCameraRotate, View.VISIBLE);
            imgCallIcon.setImageResource(bio.medico.patient.assets.R.drawable.camera);
            ViewTextUtil.setVisibility(rlAudioLayout, View.GONE);

            fbCameraRotate.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View view) {
                    CallManager.switchCamera();
                }
            });
        }
    }

    //===========================player setup=============================

    private void initPlayerWelcomeTone() {
        if (LocalData.getLanguage().equals(AppKey.LANGUAGE_BN)) {
            welcomeToneMediaPlayer = MediaPlayer.create(this, bio.medico.patient.assets.R.raw.welcome_tune_bn);
        } else {
            welcomeToneMediaPlayer = MediaPlayer.create(this, bio.medico.patient.assets.R.raw.welcome_tune);
        }

        welcomeToneMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        welcomeToneMediaPlayer.setLooping(true);
        if (mAudioManager == null) {
            mAudioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        }

        welcomeToneMediaPlayer.start();
    }

    private void releasePlayerWelcomeTone() {
        if (welcomeToneMediaPlayer != null) {
            welcomeToneMediaPlayer.release();
        }
    }
    //====================end player setup================

    //==========================================call duration=======================================

    private Handler timerHandler = new Handler();
    private long startTime = 0;
    private Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long callDuration = System.currentTimeMillis() - startTime;

            String minutes = TimeUtil.getMinute(callDuration);
            String seconds = TimeUtil.getSecond(callDuration);

            tvCallDuration.setText(minutes + ":" + seconds);

            timerHandler.postDelayed(this, 1000);
        }
    };


    //=================================================================================

    boolean isRinging = false;

    private void initSolarUI() {
        float aaa = 120;
        float radiusRatio1 = aaa * 1;
        float radiusRatio2 = aaa * 2;
        float radiusRatio3 = aaa * 3;
        float radiusRatio4 = aaa * 4;

        int delay1 = 1;
        int delay2 = 0;
        int delay3 = 1;
        int delay4 = 0;

        drawPath(imgVDoctor2, radiusRatio1, delay1, -1, -2);
        drawPath(imgVDoctor3, radiusRatio2, delay2, 3, 1);
        drawPath(imgVDoctor4, radiusRatio3, delay3, 2, 3);
        drawPath(imgVDoctor5, radiusRatio4, delay4, 2, 1);

        llRoundDashView.setShapeRadiusRatio(radiusRatio1, radiusRatio2, radiusRatio3, radiusRatio4);


        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiManager.getDoctorCancel();//get doctor apiCall cancel

                Intent data = new Intent();
                setResult(AppKey.RESULT_CODE_BUY_PACK, data);

                forceCloseUi(isRinging);

                closeCallUi();
                AppLog.sendButton(uiName, AppKeyLog.CLOSE_CALL_UI);
                FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_HANGUP_CLICK);
            }
        });


        */
/*ImageLode.lodeImage(imgVDoctor1, url);
        ImageLode.lodeImage(imgVDoctor2, url);
        ImageLode.lodeImage(imgVDoctor3, url);
        ImageLode.lodeImage(imgVDoctor4, url);
        ImageLode.lodeImage(imgVDoctor5, url);*//*


        //setImage(imgVDoctor1, R.drawable.ic_male_doctor);
        setImage(imgVDoctor2, bio.medico.patient.assets.R.drawable.ic_female_doctor);
        setImage(imgVDoctor3, bio.medico.patient.assets.R.drawable.ic_male_doctor);
        setImage(imgVDoctor4, bio.medico.patient.assets.R.drawable.ic_female_doctor);
        setImage(imgVDoctor5, bio.medico.patient.assets.R.drawable.ic_female_doctor);
    }

    private void setImage(ImageView imgVDoctor, int image) {
        imgVDoctor.setImageResource(image);
    }


    private void forceCloseUi(boolean isForceClose) {
        try {
            releasePlayerWelcomeTone();

            if (isForceClose) {
                if (callManager != null) {
                    callManager.doForceStopCall();
                }
            }


            if (doctorModel != null && !isCallReceived) {
                Timber.d("Api == callPostDoctorManage");
                callPostDoctorManage(doctorModel.getUuid(), doctorModel.getName());
                doctorModel = null;
            }

        } catch (Exception e) {
            Timber.e("forceCloseUi Error: " + e.toString());
            AppLog.sendApiLogErrorCodeScope(e);
        }

    }

    //===========================doctor finding=============================
    int callCount = 0;

    private void getDoctorApiCall() {
        callCount++;
        SocketKey.setReceiverDeviceId("");

        if (callManager != null)
            callManager.resetOffer("");

        if (isCallReceived) {
            Timber.e("*******Already in a calle**********************");
            return;
        }

        if (isFinish) {
            Timber.e("*******Already ui Close**********************");
            return;
        }


        Timber.e("*******getDoctorApiCall**********************");

        ApiManager.getDoctor(iApiResponseToken, isFreeCall());

    }

    private boolean isFirstTime = true;
    private ApiManager.IApiResponse iApiResponseToken = new ApiManager.IApiResponse() {
        @Override
        public <T> void onSuccess(T model) {
            try {
                ResponseSingleDoctor responseDoctor = (ResponseSingleDoctor) model;
                doctorNotFound = false;
                foundDoctorCall(responseDoctor);
                AppLog.sendApiLog(uiName, AppKeyLog.FOUND_DOCTOR, AppKeyLog.ENDPOINT_TYPE_API, AppUrl.URL_CALL_SINGLE_DOCTOR,
                        "doctorName:" + responseDoctor.getDoctor().getName() + ", " +
                                "mobile:" + responseDoctor.getDoctor().getPhoneNumber() + ", " +
                                "doctorId:" + responseDoctor.getDoctor().getUuid()
                );

            } catch (Exception exception) {
                closeHandlerRingingTime();
                Timber.e("Error:" + exception.toString());
                AppLog.sendApiLogErrorCodeScope(exception);
            }

        }

        @Override
        public void onFailed(String message) {

            doctorNotFound = true;
            LoadingUtil.hide();
            Timber.e("===onFailed getDoctor===");
            AppLog.sendApiLog(uiName, AppKeyLog.NOT_FOUND_DOCTOR, AppKeyLog.ENDPOINT_TYPE_API, AppUrl.URL_CALL_SINGLE_DOCTOR, "Doctor Not found.");

            notFoundDoctorCall();

        }
    };


    //=====================================================================
    private void notFoundDoctorCall() {
        try {
            if (!releaseDoctorID.isEmpty() && !isCallRejectedFORCE) {
                Timber.e("Api == callPostDoctorManage");
                callPostDoctorManage(releaseDoctorID, doctorModel.getName());
            }

            isCallRejectedFORCE = false;
            releaseDoctorID = "";
            doctorModel = null;

            //releaseDoctorID = doctorModel.getUuid();

            handlerRecallDoctorTime.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Timber.e("======reTryFoundDoctor====>> RecallDoctorTime End");
                    reTryFoundDoctor();
                }
            }, AppKey.TIMER_RECALL_DOCTOR_API);

        } catch (Exception exception) {
            Timber.e("Error:" + exception.toString());
            AppLog.sendApiLogErrorCodeScope(exception);
        }
    }

    private void foundDoctorCall(ResponseSingleDoctor responseDoctor) {

        if (responseDoctor == null) {
            Timber.e("Error: responseDoctorList null");
            //closeCallUi();
            return;
        }

        Timber.e("responseDoctor found");

        doctorModel = responseDoctor.getDoctor();

        SocketKey.RECEIVER_ID = doctorModel.getUuid();


        setDoctorName(doctorModel.getName(), doctorModel.getIsPushCall());


        callOffer();

        if (releaseDoctorID.isEmpty()) {
            releaseDoctorID = doctorModel.getUuid();
            return;
        }

        Timber.e("isCallRejectedFORCE:: " + isCallRejectedFORCE);

        if (isCallRejectedFORCE) {
            isCallRejectedFORCE = false;
        } else {
            Timber.e("Api == callPostDoctorManage");
            callPostDoctorManage(releaseDoctorID, doctorModel.getName());
        }

        releaseDoctorID = doctorModel.getUuid();

    }

    boolean doctorNotFound = false;

    private void setDoctorName(String name, boolean isPushCall) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvSelectedDoctor.setText(name);

                if (!isPushCall) {
                    tvSearchDoctor.setText(AppKey.Calling_to + name);
                } else {
                    String messageDoctorNotFound = "All channels are busy now,\nplease wait or try again after some time.";

                    if (doctorNotFound) {
                        tvSearchDoctor.setText(messageDoctorNotFound);
                    } else {
                        tvSearchDoctor.setText(AppKey.Searching_doctor);

                    }
                }


                tvDoctorName.setText(name);
            }
        });

    }

    //===========================doctor random selection=============================

    Handler handlerCalleeNotFound = new Handler();
    Handler handlerRecallDoctorTime = new Handler();
    Handler handlerRingingTime = new Handler();
    Handler handlerPresencesCheckTime = new Handler();
    Handler handlerConnectingTime = new Handler();

    Handler handlerFreeCallTime = new Handler();


    private void startFreeCallTimmer(int sec) {
        Timber.i("Start startFreeCallTimmer Handler");
        handlerFreeCallTime.postDelayed(new Runnable() {
            @Override
            public void run() {
                Timber.e("End timmer Run startFreeCallTimmer Handler");
                closeFreeCallTimmer();
            }
        }, (sec + 1) * 1000);
    }

    private FreeCallWarningDialog freeCallWarningDialog;

    void closeFreeCallTimmer() {
        Timber.i("closeFreeCallTimmer");

        if (callManager == null) {
            return;
        }

        FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_END_CLICK, JsonUtil.getJsonStringFromObject(doctorModel));

        callManager.doEndCall();


        Timber.i("closeFreeCallTimmer");
        handlerFreeCallTime.removeCallbacksAndMessages(null);


        Timber.e("Free call warning show and call end");


        // create bitmap screen capture
        // View v1 = activity.getWindow().getDecorView().getRootView();

        llMain.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(llMain.getDrawingCache());
        llMain.setDrawingCacheEnabled(false);

        AppLog.sendApiLog(uiName, AppKeyLog.FORCE_FREE_CALL_END, AppKeyLog.NA, AppKeyLog.NA, "Force call stop and send CALL_END");

        //  bitmap = blur(activity, bitmap);
        // bitmap = ImgBlur.applyBlur(activity, bitmap, 20);
        freeCallWarningDialog = new FreeCallWarningDialog(activity, new Function0<Unit>() {
            @Override
            public Unit invoke() {
                Timber.e("===Call->goBuyPack===");
                Intent data = new Intent();
                data.putExtra(AppKey.INTENT_UI, AppKey.UI_BUY_PACK);
                setResult(AppKey.RESULT_CODE_BUY_PACK, data);

                //===============================================
                WrtcSignalManager.removeSignalEventListener();

                isFinish = true;
                freeCallWarningDialog.dismiss();

                finish();
                Timber.e("===finish===");

                return null;
            }
        });

        freeCallWarningDialog.showDialog();

        //==============================
        closeHandlerRecallDoctorTime();
        closeHandlerConnectingTime();
        closeHandlerPresencesCheckTime();
        closeHandlerRingingTime();
        closeHandlerCallDurationStop();

        mLocalSurfaceView.release();
        mRemoteSurfaceView.release();

        if (callManager != null) {
            callManager.socketDisConnect();
            callManager.onDestroy();

            callManager = null;
        }

    }


    void reTryFoundDoctor() {

        if (!NetworkUtils.isConnected()) {
            ToastUtil.showToastMessage(AppKey.ERROR_INTERNET_CONNECTION);
        }

        closeHandlerRecallDoctorTime();
        closeHandlerPresencesCheckTime();
        closeHandlerRingingTime();
        closeHandlerConnectingTime();

        if (!isCallReceived) {
            setDoctorName("", true);
        }

        Timber.e("isCallRejectedFORCE:: " + isCallRejectedFORCE);
        getDoctorApiCall();

    }

    void closeHandlerCalleeNotFound() {
        Timber.i("handlerCalleeNotFound closeHandler");
        handlerCalleeNotFound.removeCallbacksAndMessages(null);
    }

    void closeHandlerRecallDoctorTime() {
        Timber.i("handlerRecallDoctorTime closeHandler");
        handlerRecallDoctorTime.removeCallbacksAndMessages(null);
    }

    void closeHandlerRingingTime() {
        Timber.i("noAnswerCallHandler closeHandler");
        handlerRingingTime.removeCallbacksAndMessages(null);
    }

    void closeHandlerConnectingTime() {
        Timber.i("handlerConnectingTime closeHandler");
        handlerConnectingTime.removeCallbacksAndMessages(null);
    }

    void closeHandlerCallDurationStop() {
        if (timerHandler != null) {
            Timber.i("handlerConnectingTime closeHandler");
            timerHandler.removeCallbacksAndMessages(null);
        }

    }


    private void startPresencesCheckTimmer() {
        Timber.i("Start handlerPresencesCheckTime Handler");
        closeHandlerPresencesCheckTime();
        handlerPresencesCheckTime.postDelayed(new Runnable() {
            @Override
            public void run() {
                Timber.i("Run handlerPresencesCheckTime Handler");

                Timber.e("======reTryFoundDoctor====>> PresencesCheckTime out");
                reTryFoundDoctor();
            }
        }, AppKey.TIMER_PRESENCES_CHECK_TIME);
    }

    void closeHandlerPresencesCheckTime() {
        Timber.i("handlerPresencesCheckTime closeHandler");
        handlerPresencesCheckTime.removeCallbacksAndMessages(null);
    }

    //api call for manage doctor

    private void callPostDoctorManage(String releaseDoctorID, String doctorName) {


        if (isCallNotFound) {
            Timber.e("isCallNotFound");
            isCallNotFound = false;
            return;
        }

        Timber.e("---------------------------------");
        Timber.e("Found Doctor :" + doctorName);
        Timber.e("---------------------------------");
        AppLog.sendApiLog(uiName, AppKeyLog.DOCTOR_STATUS_UPDATE, AppKeyLog.ENDPOINT_TYPE_API, AppUrl.URL_STATUS_UPDATE, "Update Doctor status online. DoctorName: " + doctorName + ", DoctorId: " + releaseDoctorID);
        ApiManager.statusUpdate(releaseDoctorID, "online", new ApiManager.IApiResponse() {
            @Override
            public <T> void onSuccess(T model) {
                try {
                    CommonResponse commonResponse = (CommonResponse) model;

                    if (commonResponse.isSuccess) {

                        */
/*XmppKey.USER_RECEIVER_ID = XmppKey.getReceiverId(doctorID);
                        tvSelectedDoctor.setText(doctorName);
                        tvDoctorName.setText(doctorName);
                        callOffer();*//*


                    }

                } catch (Exception exception) {
                    Timber.e("Error:" + exception.toString());
                    AppLog.sendApiLogErrorCodeScope(exception);

                }

            }

            @Override
            public void onFailed(String message) {

            }

        });
    }

    //===========================ui init============================
    private void uiInit() {


        mLocalSurfaceView.init(UtilWerRtc.getEglBase().getEglBaseContext(), null);
        mLocalSurfaceView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        mLocalSurfaceView.setMirror(true);
        mLocalSurfaceView.setEnableHardwareScaler(false */
/* enabled *//*
);

        mRemoteSurfaceView.init(UtilWerRtc.getEglBase().getEglBaseContext(), null);
        mRemoteSurfaceView.setScalingType(RendererCommon.ScalingType.SCALE_ASPECT_FILL);
        mRemoteSurfaceView.setMirror(true);
        mRemoteSurfaceView.setEnableHardwareScaler(true */
/* enabled *//*
);
        //mRemoteSurfaceView.setZOrderMediaOverlay(true);


        //  mRemoteSurfaceView.setOnTouchListener(onTouchListener);

        //  llDrag.setOnDragListener(onDragListener);


        activity_hang_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (callManager == null) {
                    return;
                }

                FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_END_CLICK, JsonUtil.getJsonStringFromObject(doctorModel));

                callManager.doEndCall();
                Timber.e("===closeCallUi===");
                closeCallUi();

            }
        });


        fbVideoOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCameraOnOff();
            }
        });

        fbMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMicOnOff();
            }
        });

        //========================================================
        callManager.setProxyVideoSink(mLocalSurfaceView);
        callManager.createVideoTrack(isVideoCall());
        //========================================================


        //setLoudSpeakerOnOff();

        fbLoudSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appAudioManager.setLoudSpeakerOnOff();
            }
        });

    }


    private void callOffer() {

        if (callManager == null) {
            Timber.e("callManager == null");
            return;
        }

        isRinging = true;

        Timber.e("==========Doctor FOUND =================================================");
        Timber.e("NAME : " + doctorModel.getName() + " | UUID : " + doctorModel.getUuid());
        Timber.e("=======================================================================");
        String callType = isVideoCall() ? SocketKey.KEY_TYPE_VIDEO_PERSONAL_CODE : SocketKey.KEY_TYPE_AUDIO_PERSONAL_CODE;
        callManager.callOffer(doctorModel.getUuid(), callType, isFreeCall());

        startPresencesCheckTimmer();


    }


    //==============================================================================================
    @AfterPermissionGranted(CallKey.RC_CALL)
    private void start() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perms)) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            uiInit();

        } else {
            EasyPermissions.requestPermissions(this, "Need some permissions", CallKey.RC_CALL, perms);
        }
    }


    //=======================================================================================

    //=======================================================================================


    private PeerConnection.Observer mPeerConnectionObserver = new PeerConnection.Observer() {
        @Override
        public void onSignalingChange(PeerConnection.SignalingState signalingState) {
            Timber.e("onSignalingChange: " + signalingState);
        }

        @Override
        public void onIceConnectionChange(PeerConnection.IceConnectionState iceConnectionState) {
            Timber.e("onIceConnectionChange: " + iceConnectionState);

            AppLog.sendSocketLog(uiName, iceConnectionState.name(), AppKeyLog.WEBRTC, "Web-Rtc status. " + getDoctorInfoMessage());

            if (iceConnectionState.name().equals("CONNECTED")) {

                ViewTextUtil.setVisibility(rlSolarView, View.GONE);
                releasePlayerWelcomeTone();

                callConnected(false);


                if (!isFreeCall()) {
                    return;
                }

                try {


                    ResponseSubInfo.FreeCallinfo freeCallInfo = getFreeCallInfo();


                    //local data
                    LocalCallLimitStatusExpire callLimitStatusExpire = LocalCallLimitStatusExpire.getCallLimitStatusExpire();

                    if (callLimitStatusExpire == null) {
                        String json = LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(new LocalCallLimitStatusExpire(
                                LocalData.getUserUuid(), TimeUtil.getToday(), 1
                        ));

                        LocalData.setCallLimit(json);
                    } else {

                        if (freeCallInfo != null) {
                            switch (freeCallInfo.getCallLimitType()) {

                                case LocalCallLimitStatusExpire.STATUS_TYPE_ALL_TIME:
                                    callLimitStatusExpire.setCallCount(callLimitStatusExpire.getCallCount() + 1);
                                    callLimitStatusExpire.setDateTime(TimeUtil.getToday());
                                    String json = LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(callLimitStatusExpire);
                                    LocalData.setCallLimit(json);
                                    break;

                                default:
                                case LocalCallLimitStatusExpire.STATUS_TYPE_EVERYDAY:

                                    boolean sameDay = TimeUtil.isSameDay(TimeUtil.getTime(callLimitStatusExpire.getDateTime(), TimeUtil.DATE_TIME_FORmMATE_4), new Date());
                                    if (sameDay) {
                                        callLimitStatusExpire.setCallCount(  callLimitStatusExpire.getCallCount() + 1);
                                        callLimitStatusExpire.setDateTime(TimeUtil.getToday());

                                        String json1 = LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(callLimitStatusExpire);
                                        LocalData.setCallLimit(json1);
                                    } else {
                                        String json2 = LocalCallLimitStatusExpire.getCallLimitStatusExpireJson(new LocalCallLimitStatusExpire(
                                                LocalData.getUserUuid(), TimeUtil.getToday(), 1
                                        ));

                                        LocalData.setCallLimit(json2);
                                    }

                                    break;
                            }
                        }
                    }

                } catch (Exception e) {
                    Timber.e("Error:" + e.toString());
                }


             */
/*   if (isVideoCall()) {
                    setLoudSpeakerOnOff();
                } else {
                    isOn = true;
                    setLoudSpeakerOnOff();
                }*//*

            } else {

                reConnection();
            }
        }

        @Override
        public void onIceConnectionReceivingChange(boolean b) {
            Timber.e("onIceConnectionChange: " + b);
        }

        @Override
        public void onIceGatheringChange(PeerConnection.IceGatheringState iceGatheringState) {
            Timber.e("onIceGatheringChange: " + iceGatheringState);
        }

        @Override
        public void onIceCandidate(IceCandidate iceCandidate) {
            Timber.e("onIceCandidate: " + iceCandidate);
            callManager.onIceCandidate(iceCandidate);
        }

        @Override
        public void onIceCandidatesRemoved(IceCandidate[] iceCandidates) {
            callManager.removeIceCandidates(iceCandidates);
        }

        @Override
        public void onAddStream(MediaStream mediaStream) {
            Timber.e("onAddStream: " + mediaStream.videoTracks.size());
         */
/*   if(mediaStream.audioTracks.size() > 0) {
                remoteAudioTrack = stream.audioTracks.get(0);
            }*//*

        }

        @Override
        public void onRemoveStream(MediaStream mediaStream) {
            Timber.e("onRemoveStream");
        }

        @Override
        public void onDataChannel(DataChannel dataChannel) {
            Timber.e("onDataChannel");
        }

        @Override
        public void onRenegotiationNeeded() {
            Timber.e("onRenegotiationNeeded");
        }

        @Override
        public void onAddTrack(RtpReceiver rtpReceiver, MediaStream[] mediaStreams) {
            callManager.onAddTrack(rtpReceiver, mediaStreams, mRemoteSurfaceView);
        }
    };


    private void reConnection() {
        Timber.e("reConnection:...");

        if (NetUtil.isNetworkAvailable()) {
            if (callManager != null)
                callManager.reTryIceConnection();
        } else {
            AppHandler.getUiHandlerNew().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (callManager != null)
                        callManager.reTryIceConnection();
                }
            }, 2000);
        }
    }


    //==================================================================================================
    //===============================================================================================================
    private WrtcSignalManager.ICallUiListener mICallUiListener = new WrtcSignalManager.ICallUiListener() {

     */
/*   @Override
        public void onBroadcastReceived(String message) {

            Timber.e("onBroadcastReceived: " + message);

        }*//*



        @Override
        public void onPreOfferAnswer(PreOfferAnswerSocket preOfferAnswerSocket) {


            switch (preOfferAnswerSocket.getPreOfferAnswer()) {

                case SocketKey.KEY_TYPE_CALLEE_NOT_FOUND:
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_CALLEE_NOT_FOUND, "Doctor not connected with Socket.");
                    isCallNotFound = true;
                    Timber.e(" CALLEE_NOT_FOUND:" + isCallNotFound);
                    handlerCalleeNotFound.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Timber.e("======reTryFoundDoctor====>> CALLEE_NOT_FOUND");
                            reTryFoundDoctor();
                        }
                    }, AppKey.TIMER_NOT_FOUND_DOCTOR_API);

                    break;

                case SocketKey.KEY_TYPE_CALLEE_NO_ANSWER:
                    Timber.e("======reTryFoundDoctor====>> CALLEE_NO_ANSWER");

                    FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_NO_ANSWER, doctorModel.getName(), doctorModel.getPhoneNumber());
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_CALLEE_NO_ANSWER, "Doctor Not Receive. " + getDoctorInfoMessage());

                    reTryFoundDoctor();

                    break;

                case SocketKey.KEY_CALL_REJECTED_FORCE:
                    isCallRejectedFORCE = true;

                    Timber.e("isCallRejectedFORCE:: " + isCallRejectedFORCE);

                    Timber.e("======reTryFoundDoctor====>> CALL_REJECTED_FORCE");
                    reTryFoundDoctor();
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_CALL_REJECTED_FORCE, "Doctor Go offline or Busy. " + getDoctorInfoMessage());

                    break;

                case SocketKey.KEY_TYPE_CALL_ENDED:
                    closeCallUi();
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_CALL_ENDED, "Doctor Call End. " + getDoctorInfoMessage());

                    break;


                case SocketKey.KEY_TYPE_CALL_ACCEPTED:

                    Timber.e("=========KEY_TYPE_CALL_ACCEPTED========");
                    closeHandlerPresencesCheckTime();
                    closeHandlerRingingTime();
                    //callReceived();
                    isCallReceived = true;

                    releasePlayerWelcomeTone();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ViewTextUtil.setVisibility(rlSolarView, View.GONE);
                        }
                    });

                    callConnected(true);

                    ViewTextUtil.setVisibility(rrAppbar, View.VISIBLE);
                    ViewTextUtil.setVisibility(llCallEnd, View.VISIBLE);


                    String url = LocalData.getImgBaseUrl() + doctorModel.getImage();
                    Timber.e("doctor image::: " + url);
                    ImageLode.lodeImage(imgProfile, url, bio.medico.patient.assets.R.drawable.ic_doctor_placeholder);
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_CALL_ACCEPTED, "Doctor Call Receive. " + getDoctorInfoMessage());


                    break;

                case SocketKey.KEY_TYPE_CALL_REJECTED:
                    isRinging = false;
                    Timber.e("======reTryFoundDoctor====>> CALL_REJECTE");
                    reTryFoundDoctor();
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_CALL_REJECTED, "Doctor Call Reject." + getDoctorInfoMessage());

                    break;


                case SocketKey.KEY_TYPE_VIDEO_OFF:
                    Timber.e("======Doctor VIDEO_OFF====>> ");
                    isDoctorMakeAudioCAll = true;
                    ViewTextUtil.setVisibility(rlAudioLayout, View.VISIBLE);
                    setCameraOnOff(true);
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_VIDEO_OFF, "Users Video Off by Doctor. " + getDoctorInfoMessage());

                    break;

                case SocketKey.KEY_TYPE_VIDEO_ON:
                    Timber.e("======Doctor VIDEO_ON====>> ");

                    isDoctorMakeAudioCAll = false;

                    setCameraOnOff(false);
                    ViewTextUtil.setVisibility(rlAudioLayout, View.GONE);
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_VIDEO_ON, "Users Video On by Doctor.. " + getDoctorInfoMessage());

                    break;

                default:
                    Timber.e("onPreOfferAnswer: default:" + preOfferAnswerSocket.getPreOfferAnswer());
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, preOfferAnswerSocket.getPreOfferAnswer(), " Socket key not found. " + getDoctorInfoMessage());

                    break;
            }

        }

        @Override
        public void onWebRtcSignaling(SignalingOfferSocket signalingOfferSocket) {

            switch (signalingOfferSocket.getType()) {

                case SocketKey.KEY_TYPE_OFFER:

                    callManager.onRemoteOfferReceivedSocket(signalingOfferSocket, doctorModel.getUuid());
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, SocketKey.KEY_TYPE_OFFER, "Receive Call Offer ." + getDoctorInfoMessage());

                    break;

                case SocketKey.KEY_TYPE_CANDIDATE:
                    callManager.onRemoteCandidateReceivedSocket(signalingOfferSocket);
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, AppKeyLog.KEY_TYPE_CANDIDATE, "Receive CANDIDATE. " + getDoctorInfoMessage());

                    break;

                default:
                    Timber.e("onWebRtcSignaling: default:" + signalingOfferSocket.getType());
                    AppLog.sendSocketLog(uiName, AppKeyLog.RECEIVE_SOCKET, signalingOfferSocket.getType(), "Receive socket data but Socket key not found. " + getDoctorInfoMessage());

                    break;
            }

        }

    };

    private void callConnected(boolean isNotIceConnect) {

        FirebaseAnalyticsManager.logEventWithNumber(AppKey.EVENT_CALL_CONNECTED, JsonUtil.getJsonStringFromObject(doctorModel));

        closeHandlerConnectingTime();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ViewTextUtil.setVisibility(llDrag, View.VISIBLE);
                ViewTextUtil.setVisibility(llCallEnd, View.VISIBLE);
            }
        });

        if (isNotIceConnect) {
            startTime = System.currentTimeMillis();
            timerHandler.postDelayed(timerRunnable, 0);
            if (getFreeCallInfo() != null) {
                startFreeCallTimmer(getFreeCallInfo().getCallDuration());
            }
        }
    }


    //=================================================================================


    //==------------------------------------------------------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    protected void onResume() {
        WrtcSignalManager.setSignalEventListener(mICallUiListener);
        Timber.d("onResume");
        if (callManager != null) {
            AppHandler.getUiHandlerNew().postDelayed(() -> callManager.startCapture(), 100);

        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (callManager != null) {
            callManager.stopCapture();
        }

    }

    @Override
    protected void onDestroy() {
        forceCloseUi(true);
        closeAllHandler();
        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
    }


    private void closeCallUi() {
        closeHandlerRecallDoctorTime();
        closeHandlerConnectingTime();
        closeHandlerPresencesCheckTime();
        closeHandlerRingingTime();

        mLocalSurfaceView.release();
        mRemoteSurfaceView.release();

        if (callManager != null) {
            callManager.socketDisConnect();
            callManager.onDestroy();
        }

        callManager = null;

        WrtcSignalManager.removeSignalEventListener();

        closeHandlerCallDurationStop();

        isFinish = true;
        finish();
        Timber.e("===finish===");
    }


    void closeAllHandler() {
        closeHandlerCalleeNotFound();
        closeHandlerRecallDoctorTime();
        closeHandlerConnectingTime();
        closeHandlerPresencesCheckTime();
        closeHandlerRingingTime();
    }

    //===============================================================================================================

    private void setMicOnOff() {
        if (callManager.isMicrophoneOn()) {
            callManager.setMicrophoneOnOff(false);
            fbMute.setImageDrawable(AppResources.icMicOff);
        } else {
            callManager.setMicrophoneOnOff(true);
            fbMute.setImageDrawable(AppResources.icMicOn);
        }
    }

    private void setCameraOnOff() {
        if (isDoctorMakeAudioCAll) {
            Timber.e("isDoctorMakeAudioCAll:" + isDoctorMakeAudioCAll);
            return;
        }
        if (callManager == null) {
            Timber.d("callManager == null");
            return;
        }

        if (callManager.isCameraOn()) {
            callManager.setCameraOnOff(false);
            fbVideoOn.setImageDrawable(AppResources.icVideoCameraOff);
        } else {

            callManager.setCameraOnOff(true);
            fbVideoOn.setImageDrawable(AppResources.icVideoCameraOn);
        }
    }


    private void setCameraOnOff(boolean isOnOff) {
        if (isOnOff) {
            callManager.setCameraOnOff(false);
            fbVideoOn.setImageDrawable(AppResources.icVideoCameraOff);
        } else {
            setCameraOnOff();
        }

    }

  */
/*  private boolean isOn = false;

    private void setLoudSpeakerOnOff() {
        isOn = !isOn;

        if (mAudioManager == null) {
            mAudioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        }

        if (isOn) {
            mAudioManager.setMode(AudioManager.MODE_IN_CALL);
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            fbLoudSpeaker.setImageDrawable(AppResources.icLoudSpeakerOn);
        } else {
            //Seems that this back and forth somehow resets the audio channel
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
            mAudioManager.setMode(AudioManager.MODE_IN_CALL);

            fbLoudSpeaker.setImageDrawable(AppResources.icLoudSpeakerOff);
        }

        Timber.e("setLoudSpeakerOnOff:: " + isOn);

        mAudioManager.setSpeakerphoneOn(isOn);

    }

*//*



    String getDoctorInfoMessage() {
        if (doctorModel != null) {
            return " DoctorName: " + doctorModel.getName() + ", DoctorId: " + doctorModel.getUuid();
        }
        return "";
    }

}


*/
