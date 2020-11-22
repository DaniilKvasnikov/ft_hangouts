package com.school21.ft_hangouts.sms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.school21.ft_hangouts.R

open class MessageListAdapter(var messages: List<Message>) :
    RecyclerView.Adapter<MessageListAdapter.MyViewHolder>() {
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2

    fun addMessage(message: Message){
        messages += message
        notifyDataSetChanged()
    }

    override fun getItemCount() = messages.size

    override fun getItemViewType(position: Int): Int {
        return if (!messages[position].input) VIEW_TYPE_MESSAGE_SENT
        else VIEW_TYPE_MESSAGE_RECEIVED
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val res = when(viewType){
            VIEW_TYPE_MESSAGE_SENT -> R.layout.item_message_sent
            VIEW_TYPE_MESSAGE_RECEIVED -> R.layout.item_message_received
            else -> 0
        }

        val itemView = LayoutInflater.from(parent.context).inflate(res, parent, false)
        return MyViewHolder(itemView!!)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        when (holder.itemViewType){
            VIEW_TYPE_MESSAGE_SENT -> {
                holder.message?.text = messages[position].message
                holder.time?.text = convertSecondsToHMmSs(messages[position].createdAt)
            }
            VIEW_TYPE_MESSAGE_RECEIVED -> {
                holder.name?.text = messages[position].sender
                holder.message?.text = messages[position].message
                holder.time?.text = convertSecondsToHMmSs(messages[position].createdAt)
            }
        }
    }


    fun convertSecondsToHMmSs(seconds: Long): String? {
        val m = seconds / 60 % 60
        val h = seconds / (60 * 60) % 24
        return String.format("%d:%02d", h, m)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var message: TextView? = null
        var time: TextView? = null

        init {
            name = itemView.findViewById(R.id.text_message_name)
            message = itemView.findViewById(R.id.text_message_body)
            time = itemView.findViewById(R.id.text_message_time)
        }
    }
}