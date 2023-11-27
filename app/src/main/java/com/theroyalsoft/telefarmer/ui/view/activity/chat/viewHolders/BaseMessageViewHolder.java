package com.theroyalsoft.telefarmer.ui.view.activity.chat.viewHolders;

import static com.theroyalsoft.mydoc.apputil.TimeUtil.DATE_TIME_FORMAT_ddMMMyyyy;
import static com.theroyalsoft.mydoc.apputil.TimeUtil.getTime1;
import static com.theroyalsoft.telefarmer.helper.AppKey.DATE_TIME_FORmMATE_1;
import static com.theroyalsoft.telefarmer.ui.view.activity.chat.MessageAdapter.OTHER;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.farmer.primary.network.dataSource.local.LocalData;
import com.theroyalsoft.telefarmer.R;
import com.theroyalsoft.telefarmer.extensions.DateTimeUtil;
import com.theroyalsoft.telefarmer.helper.GeneralUtils;
import com.theroyalsoft.telefarmer.ui.view.activity.chat.interfaces.OnMessageItemClick;

import bio.medico.patient.common.AttachmentTypes;
import bio.medico.patient.model.apiResponse.chat.MessageBody;
import timber.log.Timber;

public class BaseMessageViewHolder extends RecyclerView.ViewHolder {

    protected int lastPosition;
    public boolean animate;
    protected View newMessageView;

    private int attachmentType;
    protected Context context;

    private static int _48dpInPx = -1;
    private MessageBody message;
    private OnMessageItemClick itemClickListener;

    TextView tvTime;
    TextView tvSenderName;


    public BaseMessageViewHolder(View itemView, OnMessageItemClick itemClickListener) {
        super(itemView);
        if (itemClickListener != null)
            this.itemClickListener = itemClickListener;

        context = itemView.getContext();
        tvTime = itemView.findViewById(R.id.time);
        tvSenderName = itemView.findViewById(R.id.senderName);


        if (_48dpInPx == -1)
            _48dpInPx = GeneralUtils.dpToPx(itemView.getContext(), 48);
    }

    public BaseMessageViewHolder(View itemView, int attachmentType, OnMessageItemClick itemClickListener) {
        super(itemView);
        this.itemClickListener = itemClickListener;
        this.attachmentType = attachmentType;
    }

    public BaseMessageViewHolder(View itemView, View newMessage, OnMessageItemClick itemClickListener) {
        this(itemView, itemClickListener);
        this.itemClickListener = itemClickListener;
        if (newMessageView == null) newMessageView = newMessage;
    }

    protected boolean isMine() {
        return (getItemViewType() & OTHER) != OTHER;
    }

    public void setData(MessageBody message, int position) {
        Timber.e("setData:" + position);
        this.message = message;
        if (attachmentType == AttachmentTypes.NONE_TYPING)
            return;

        if (message.getDateAndTime().isEmpty()) {

            String timeSet = DateTimeUtil.getTimeCurrent();
            tvTime.setText(timeSet);
        } else {
            String timeSet = getTime1(message.getDateAndTime(), DATE_TIME_FORmMATE_1, DATE_TIME_FORMAT_ddMMMyyyy);
            tvTime.setText(timeSet);
        }


        if (isMine()) {
            tvSenderName.setVisibility(View.VISIBLE);
            tvSenderName.setText(LocalData.getUserName());
        } else {
            tvSenderName.setText(message.getSenderName());
            tvSenderName.setVisibility(View.VISIBLE);
        }


     /*   if (isMine()) {
            layoutParams.gravity = Gravity.END;
            layoutParams.leftMargin = _48dpInPx;
            tvTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, message.isSent() ? (message.isDelivered() ? R.drawable.ic_done_all_black : R.drawable.ic_done_black) : R.drawable.ic_waiting, 0);
        } else {
            layoutParams.gravity = Gravity.START;
            layoutParams.rightMargin = _48dpInPx;
            //  itemView.startAnimation(AnimationUtils.makeInAnimation(itemView.getContext(), true));
        }

        cardView.setLayoutParams(layoutParams);*/
    }

    //private Realm rChatDb;
    void onItemClick(boolean b) {

        if (itemClickListener != null && message != null) {
            if (b)
                itemClickListener.onMessageClick(message, getAdapterPosition());
            else {
                itemClickListener.onMessageLongClick(message, getAdapterPosition());
                /*rChatDb = Helper.getRealmInstance();
                Helper.deleteChat(message.getSenderId(),message.getRecipientId(),message.getId(),context);
                Helper.deleteMessageFromRealm(rChatDb, message.getId());*/
                //Toast.makeText(context, message.getId()+message.getSenderId(), Toast.LENGTH_SHORT).show();
            }
        }
    }

   /* void broadcastDownloadEvent(DownloadFileEvent downloadFileEvent) {
        Intent intent = new Intent(Helper.BROADCAST_DOWNLOAD_EVENT);
        intent.putExtra("data", downloadFileEvent);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    void broadcastDownloadEvent() {
        Intent intent = new Intent(Helper.BROADCAST_DOWNLOAD_EVENT);
        intent.putExtra("data", new DownloadFileEvent(message.getAttachmentType(), message.getAttachment(), getAdapterPosition()));
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }*/

}
