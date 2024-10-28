package com.globalhackathon.nextlyf.model;

import java.util.List;

public interface GroupMessageListener {

    void onGroupMessageReceived(GroupChatMessage groupChatMessage);

    void onGroupMessageError(String error);

}
