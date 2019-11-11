package com.sphereon.ms.did.mapping.maps.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DidInfo {

    private String did;
    private String pushToken;
    private String boxPub;

    public DidInfo(){
    }

    public DidInfo(String did, String pushToken, String boxPub) {
        this.did = did;
        this.pushToken = pushToken;
        this.boxPub = boxPub;
    }

    public String getDid() {
        return did;
    }

    public String getPushToken() {
        return pushToken;
    }

    public String getBoxPub() {
        return boxPub;
    }
}
