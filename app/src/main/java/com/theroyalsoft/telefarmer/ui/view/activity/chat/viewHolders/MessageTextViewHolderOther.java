package com.theroyalsoft.telefarmer.ui.view.activity.chat.viewHolders;

import static com.theroyalsoft.telefarmer.ui.view.activity.chat.MessageAdapter.MY;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.farmer.primary.network.dataSource.local.LocalData;
import com.theroyalsoft.mydoc.apputil.ImageLode;
import com.theroyalsoft.telefarmer.R;
import com.theroyalsoft.telefarmer.helper.GeneralUtils;
import com.theroyalsoft.telefarmer.helper.LinkTransformationMethod;
import com.theroyalsoft.telefarmer.ui.view.activity.chat.interfaces.OnMessageItemClick;
import com.theroyalsoft.telefarmer.ui.view.activity.viewimg.ImageLoaderActivity;

import bio.medico.patient.common.AttachmentTypes;
import bio.medico.patient.model.NullRemoveUtil;
import bio.medico.patient.model.apiResponse.chat.MessageBody;
import timber.log.Timber;

/**
 * Created by Pritom Dutta on 30/1/24.
 */
public class MessageTextViewHolderOther extends BaseMessageViewHolder {
    TextView text;
    LinearLayout ll;
    TextView tvTextDate;
    View llDateTime;
    ImageView imgV;
    ImageView imgProfile;

    private MessageBody message;
    private String imgUrl;

    private static int _4dpInPx = -1;

    public MessageTextViewHolderOther(View itemView, View newMessageView, OnMessageItemClick itemClickListener) {
        super(itemView, newMessageView, itemClickListener);
        text = itemView.findViewById(R.id.text);
        ll = itemView.findViewById(R.id.container);
        llDateTime = itemView.findViewById(R.id.llDateTime);
        tvTextDate = itemView.findViewById(R.id.tvTextDate);
        imgV = itemView.findViewById(R.id.imgV);
        imgProfile = itemView.findViewById(R.id.imgProfile);
        this.imgUrl = LocalData.getImgBaseUrl();

        imgV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageLoaderActivity.Companion.goActivityFullPath(context, message.getAttachment());
            }
        });

        text.setTransformationMethod(new LinkTransformationMethod());
        text.setMovementMethod(LinkMovementMethod.getInstance());
        if (_4dpInPx == -1) _4dpInPx = GeneralUtils.dpToPx(itemView.getContext(), 4);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(true);
            }
        });

        text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemClick(false);
//                dialog();
                return true;
            }
        });
    }

    public void dialog() {
        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose an option");

        // add a list
        String[] animals = {"Delete everyone"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // horse
                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                        onItemClick(false);
                        break;
                    default:
                }
            }
        });
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void setData(MessageBody message, int position) {
        super.setData(message, position);
        this.message = message;
        //cardView.setCardBackgroundColor(ContextCompat.getColor(context, message.isSelected() ? R.color.colorPrimary : R.color.colorBgLight));
        //ll.setBackgroundColor(message.isSelected() ? ContextCompat.getColor(context, R.color.colorPrimary) : isMine() ? Color.WHITE : ContextCompat.getColor(context, R.color.colorBgLight));

        if (message.getDateText().isEmpty()) {
            llDateTime.setVisibility(View.GONE);
        } else {
            tvTextDate.setText(message.getDateText());
            llDateTime.setVisibility(View.VISIBLE);
        }

        Timber.e("getRecipientImage:" + getRecipientImage(message.getRecipientImage()));


        if (isMine()) {

            String userProfile = "";
            if (!LocalData.getUserProfile().isEmpty()) {
                userProfile = LocalData.getImgBaseUrl()+"/upload/" + LocalData.getUserProfile();
                imgUrl = LocalData.getImgBaseUrl();
            }

            Timber.e("userProfile:" + userProfile);

            ImageLode.lodeImage(imgProfile, userProfile, bio.medico.patient.assets.R.drawable.ic_patient_place_holder);
        } else {
            ImageLode.lodeImage(imgProfile, getRecipientImage(message.getRecipientImage()), bio.medico.patient.assets.R.drawable.ic_patient_place_holder);
        }


        if (message.getBody().isEmpty()) {
            text.setVisibility(View.GONE);
        } else {
            text.setVisibility(View.VISIBLE);
        }

        if (message.getIsAttachment() == AttachmentTypes.statusTrue) {
            imgV.setVisibility(View.VISIBLE);

            Timber.e("message.getAttachment():" + message.getAttachment());
            ImageLode.lodeImage(imgV, imgUrl + "/uploaded/" + message.getAttachment());
        } else {
            imgV.setVisibility(View.GONE);
        }

        text.setText(message.getBody());

        Timber.e("position:" + position);
        // animateView(position);
        if (getItemViewType() == MY) {
            //
        }
    }


    private String getRecipientImage(String recipientImage) {
        String recipientImageUrl = "";
        if (!NullRemoveUtil.getNotNull(recipientImage).isEmpty()) {
            recipientImageUrl = LocalData.getImgBaseUrl() + NullRemoveUtil.getNotNull(recipientImage);
        }
        return recipientImageUrl;
    }
}
