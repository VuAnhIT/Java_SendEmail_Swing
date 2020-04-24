package com.theanh.entity;

/**
 * Created by Vu The Anh  - vutheanhit@gmail.com
 * On 07/05/2019
 * This is the class that contain data of SMTP Servers
 */
public class SMTPServer {
    private String authentication;
    private String port;
    private String server;

    public SMTPServer(String authentication, String port, String server) {
        this.authentication = authentication;
        this.port = port;
        this.server = server;
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    //Override toString to return needed values at Jcombobox
    @Override
    public String toString() {
        return getServer() + "(" + getAuthentication() + ")";
    }
}
