package com.mine.chat.network;

// Описания событий, которые могут возникать при работе
// Позволяет использовать в разных частях кода для реализации различных событий
public interface TCPConnectionListener {

    void onConnectionReady(TCPConnection tcpConnection);
    void onReceiveString(TCPConnection tcpConnection, String value);
    void onDisconnect(TCPConnection tcpConnection);
    void onException(TCPConnection tcpConnection, Exception e);


}
