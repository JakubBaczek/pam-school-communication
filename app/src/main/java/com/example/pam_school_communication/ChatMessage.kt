package com.example.pam_school_communication

import java.util.Date

class ChatMessage {
    var messageText: String? = ""
    var messageUser: String? = ""
    var messageTime: Long = 0

    constructor(messageText: String?, messageUser: String?) {
        this.messageText = messageText
        this.messageUser = messageUser

        // Initialize to current time
        messageTime = Date().time
    }

    constructor() {}
}
