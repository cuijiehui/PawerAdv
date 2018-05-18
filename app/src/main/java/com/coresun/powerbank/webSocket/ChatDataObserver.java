package com.coresun.powerbank.webSocket;

import org.json.JSONObject;

public interface ChatDataObserver {
    void OnMessage(JSONObject jsonObject);
}
