package com.nsdeallai.ns.ns_deallai;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

/**
 * Created by Kermit on 2015-08-31.
 */
public class Server {

    public static Socket SERVER;

    public static Socket SererConnect() {

        String SERVER_URL = "http://211.253.11.138:3003";
        try {
            SERVER = IO.socket(SERVER_URL);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return SERVER;
    }

}
