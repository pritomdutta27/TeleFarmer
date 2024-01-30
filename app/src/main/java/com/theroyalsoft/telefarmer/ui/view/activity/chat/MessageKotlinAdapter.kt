//package com.theroyalsoft.telefarmer.ui.view.activity.chat
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import bio.medico.patient.model.apiResponse.chat.MessageBody
//import com.farmer.primary.network.dataSource.local.LocalData
//import com.theroyalsoft.mydoc.apputil.ImageLode
//import com.theroyalsoft.telefarmer.databinding.ItemMessageTextBinding
//import com.theroyalsoft.telefarmer.databinding.ItemMessageTextOtherBinding
//import com.theroyalsoft.telefarmer.ui.view.activity.chat.interfaces.OnMessageItemClick
//import com.theroyalsoft.telefarmer.ui.view.activity.chat.viewHolders.BaseViewHolder
//
///**
// * Created by Pritom Dutta on 29/1/24.
// */
//class MessageKotlinAdapter(
//    context: Context,
//    myId: String,
//    newMessage: View,
//    imgUrl: String
//) : ListAdapter<MessageBody, BaseViewHolder>(COMPARATOR) {
//
//    var dateText = ""
//
//    private var itemClickListener: OnMessageItemClick? = null
//    private var context: Context? = null
//
//    var messageBodies: List<MessageBody>? = null
//    private var newMessage: View? = null
//    private var imgUrl: String? = null
//
//    init {
//        this.context = context
//        this.imgUrl = imgUrl
//
//        MessageKotlinAdapter.myId = myId
//        this.newMessage = newMessage
//
//        if (context is OnMessageItemClick) {
//            itemClickListener = context
//        } else {
//            throw RuntimeException("$context must implement ChatItemClickListener")
//        }
//    }
//
//    companion object {
//
//        public val MY = 0x00000000
//        public val OTHER = 0x0000100
//        const val DATE_TIME_FORmMATE_1 = "yyyy-MM-dd'T'hh:mm:ssZ"
//        var myId: String? = null
//
//        private val COMPARATOR = object : DiffUtil.ItemCallback<MessageBody>() {
//            override fun areItemsTheSame(oldItem: MessageBody, newItem: MessageBody): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: MessageBody, newItem: MessageBody): Boolean {
//                return newItem.id == oldItem.id
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
//        return when (viewType) {
//            OTHER -> {
//                val item = ItemMessageTextOtherBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent, false
//                )
//                TextOtherHolder(item)
//            }
//
////            MY -> {}
//
//            else -> {
//                val item = ItemMessageTextBinding.inflate(
//                    LayoutInflater.from(parent.context),
//                    parent, false
//                )
//                TextHolder(item)
//            }
//        }
//    }
//
//    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        holder.onBind(position)
//    }
//
//    inner class TextOtherHolder(private val mBinding: ItemMessageTextOtherBinding) :
//        BaseViewHolder(mBinding.root) {
//        override fun onBind(position: Int) {
//            this.mBinding.apply {
//                text.text = getItem(position).body.toString()
//                senderName.text = getItem(position).senderName
//
//                if (getItem(position).dateText.isEmpty()) {
//                    llDateTime.visibility = View.GONE
//                } else {
//                    tvTextDate.text = getItem(position).dateText
//                    llDateTime.visibility = View.VISIBLE;
//                }
//                ImageLode.lodeImage(
//                    imgProfile,
//                    LocalData.getImgBaseUrl() + "/uploaded/" + getItem(position).senderImage,
//                    bio.medico.patient.assets.R.drawable.ic_patient_place_holder
//                )
//            }
//        }
//    }
//
//    inner class TextHolder(private val mBinding: ItemMessageTextBinding) :
//        BaseViewHolder(mBinding.root) {
//
//        override fun onBind(position: Int) {
//            this.mBinding.apply {
//                text.text = getItem(position).body
//                senderName.text = getItem(position).senderName
//                if (getItem(position).dateText.isEmpty()) {
//                    llDateTime.visibility = View.GONE
//                } else {
//                    tvTextDate.text = getItem(position).dateText
//                    llDateTime.visibility = View.VISIBLE;
//                }
//                ImageLode.lodeImage(
//                    imgProfile,
//                    LocalData.getImgBaseUrl() + "/uploaded/" + LocalData.getUserProfile(),
//                    bio.medico.patient.assets.R.drawable.ic_patient_place_holder
//                )
//            }
//
////            val sb = StringBuilder()
////            getItem(position).description.forEach { sb.append("• " + it + "\n") }
////
////            mItemSubscriptionViewModel = callback?.let {
////                ItemSubscriptionViewModel(
////                    getItem(position).pack_name,
////                    getItem(position).regular_price.toString(),
////                    getItem(position).currency, sb.toString(),
////                    position,
////                    getItem(position).isMyPack,
////                    getItem(position).auto_renewal,
////                    getItem(position).url,
////                    it
////                )
////            }
////            mBinding.viewModel = mItemSubscriptionViewModel
////            mBinding.executePendingBindings()
//        }
//
//    }
//
//
//    override fun getItemViewType(position: Int): Int {
//        if (itemCount == 0) {
//            return super.getItemViewType(position)
//        }
//        val message = getItem(position)
//        return if (message.senderId == myId) MY else OTHER
//    }
//}