package fxml;

public enum Standort {
    BERN("wss://syspvm1008.kt.be.ch:8443/sysp/websocket"),
    BIEL("wss://syspvm1010.kt.be.ch:8443/sysp/websocket"),
    THUN("wss://syspvm1039.kt.be.ch:8443/sysp/websocket"),
    COURTELARY("wss://syspvm1013.kt.be.ch:8443/sysp/websocket"),
    INTERLAKEN("wss://syspvm1022.kt.be.ch:8443/sysp/websocket"),
    LANGENTHAL("wss://syspvm1023.kt.be.ch:8443/sysp/websocket"),
    LANGNAU("wss://syspvm1024.kt.be.ch:8443/sysp/websocket"),
    MIDI("wss://syspvm1134.kt.be.ch:8443/sysp/websocket");
    
    private final String url;

    Standort(String url) {
        this.url = url;
    }
    
    public String getWebSocketUrl() {
        return url;
    }
}

