package it.unibo.pcd.assignment03.controller;

public class PeerInfo {

    private final String host;
    private final int port;

    public PeerInfo(String id, String host, int port) {
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
