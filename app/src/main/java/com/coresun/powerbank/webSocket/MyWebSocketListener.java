package com.coresun.powerbank.webSocket;

import okhttp3.Response;

public abstract class MyWebSocketListener {
    public void onOpen( String response) {
    }

    public void onMessage( String message) {
    }

    public void onFailure( String response) {
    }
}
