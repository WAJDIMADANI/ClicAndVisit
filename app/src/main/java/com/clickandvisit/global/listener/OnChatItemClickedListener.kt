package com.clickandvisit.global.listener

import com.clickandvisit.data.model.chat.Discussion

interface OnChatItemClickedListener {
    fun onItemClicked(value: Discussion)
}
