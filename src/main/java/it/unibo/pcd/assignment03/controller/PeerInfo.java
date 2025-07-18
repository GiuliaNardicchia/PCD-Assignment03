package it.unibo.pcd.assignment03.controller;

import java.io.Serializable;

public class PeerInfo implements Serializable {

    private final String host;
    private final int port;

    public PeerInfo(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
