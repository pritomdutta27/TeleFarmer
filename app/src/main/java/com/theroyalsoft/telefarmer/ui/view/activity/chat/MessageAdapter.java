package com.theroyalsoft.telefarmer.ui.view.activity.chat;

import static com.theroyalsoft.mydoc.apputil.TimeUtil.DATE_TIME_FORMAT_ddMMMyyyy;
import static com.theroyalsoft.mydoc.apputil.TimeUtil.getTime1;
import static com.theroyalsoft.telefarmer.helper.AppKey.DATE_TIME_FORmMATE_1;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skh.hkhr.util.NullRemoveUtil;
import com.theroyalsoft.telefarmer.R;
import com.theroyalsoft.telefarmer.ui.view.activity.chat.interfaces.OnMessageItemClick;
import com.theroyalsoft.telefarmer.ui.view.activity.chat.viewHolders.BaseMessageViewHolder;
import com.theroyalsoft.telefarmer.ui.view.activity.chat.viewHolders.MessageTextViewHolder;

import java.util.ArrayList;
import java.util.List;

import bio.medico.patient.model.apiResponse.chat.MessageBody;
import timber.log.Timber;

public class MessageAdapter extends RecyclerView.Adapter<BaseMessageViewHolder> {

    public static final int MY = 0x00000000;
    public static final int OTHER = 0x0000100;

    public static String myId;

    public String dateText = "";

    private OnMessageItemClick itemClickListener;
    private Context context;

    public List<MessageBody> messageBodies;
    private View newMessage;
    private String imgUrl;


    private Handler handler = new Handler(Looper.getMainLooper());


    public MessageAdapter(Context context, String myId, View newMessage) {
        this.context = context;

        this.myId = myId;
        this.newMessage = newMessage;

        if (context instanceof OnMessageItemClick) {
            this.itemClickListener = (OnMessageItemClick) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ChatItemClickListener");
        }

    }

    public void setData(List<MessageBody> messages, RecyclerView recyclerView) {

        if (this.messageBodies == null) {
            this.messageBodies = new ArrayList<>();
        }


        for (MessageBody messageBody : messages) {

            String dateText1 = getTime1(messageBody.getDateAndTime(), DATE_TIME_FORmMATE_1, DATE_TIME_FORMAT_ddMMMyyyy);
            if (dateText.equals(dateText1)) {
                messageBody.setDateText("");
            } else {
                dateText = getTime1(messageBody.getDateAndTime(), DATE_TIME_FORmMATE_1, DATE_TIME_FORMAT_ddMMMyyyy);
                messageBody.setDateText(dateText);
            }
        }

        Timber.e("messages size:" + messages.size());
        this.messageBodies = messages;
        Timber.e("messages size:" + messages.size());


        handler.post(() -> {
            Timber.i("notifyDataSetChanged");
            notifyDataSetChanged();

            if (getItemCount() > 1) {
//                recyclerView.scrollToPosition(getItemCount() - 1);
                scrolAgain(recyclerView);//for Image Size
            }
        });

    }

    private void scrolAgain(RecyclerView recyclerView) {
        handler.postDelayed(() -> {
            if (getItemCount() > 1) {
                recyclerView.scrollToPosition(getItemCount() - 1);
            }
        }, 100);
    }

    void setData(MessageBody messages, RecyclerView recyclerView) {

        if (this.messageBodies == null) {
            this.messageBodies = new ArrayList<>();
        }


        this.messageBodies.add(messages);


        handler.post(() -> {
            Timber.i("notifyDataSetChanged");
            notifyDataSetChanged();

            if (getItemCount() > 1) {
                //recyclerView.scrollToPosition(getItemCount() - 1);
                scrolAgain(recyclerView);//for Image Size
            }
        });

    }


    @NonNull
    @Override
    public BaseMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case OTHER:
                return new MessageTextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_text_other, parent, false), newMessage, itemClickListener);

            //  return new MessageAttachmentImageViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_attachment_image, parent, false), itemClickListener);
            //case AttachmentTypes.NONE_TYPING: return new MessageTypingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_typing, parent, false));
            case MY:
            default:
                return new MessageTextViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message_text, parent, false), newMessage, itemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(BaseMessageViewHolder holder, int position) {
        holder.setData(messageBodies.get(position), position);
    }

    @Override
    public int getItemViewType(int position) {
        if (getItemCount() == 0) {
            return super.getItemViewType(position);
        }

        MessageBody message = messageBodies.get(position);
        int userType;

        if (message.getSenderId().equals(myId))
            userType = MY;
        else
            userType = OTHER;

        return userType;

    }

    @Override
    public int getItemCount() {
        return NullRemoveUtil.getNotNull(messageBodies).size();
    }
}
