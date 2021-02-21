package com.taxi.singletons;

import com.google.gson.Gson;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/ws", encoders = {WSEndpoint.JSONEncoder.class}, decoders = {WSEndpoint.MessageDecoder.class})
public class WSEndpoint {

    @Inject
    private TaxiLogger taxiLogger;


    @OnOpen
    public void onOpen(Session session) throws IOException {
        taxiLogger.add(session);
        taxiLogger.info("Connected:" + session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        taxiLogger.info("Message:" + session.getId());
    }

    @OnClose
    public void onClose(Session session, CloseReason c) throws IOException {
        taxiLogger.remove(session);
        taxiLogger.info("Closing:" + c.getCloseCode());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        taxiLogger.error("Error ES", throwable);
    }

    public static class MessageDecoder implements Decoder.Text<LogMessage> {

        private static Gson gson = new Gson();

        @Override
        public LogMessage decode(String s) throws DecodeException {
            return gson.fromJson(s, LogMessage.class);
        }

        @Override
        public boolean willDecode(String s) {
            return (s != null);
        }

        @Override
        public void init(EndpointConfig endpointConfig) {
            // Custom initialization logic
        }

        @Override
        public void destroy() {
            // Close resources
        }
    }

    public static class JSONEncoder implements Encoder.Text<LogMessage> {

        private static Gson gson = new Gson();

        @Override
        public void init(EndpointConfig config) {

        }

        @Override
        public void destroy() {
            // do nothing
        }

        @Override
        public String encode(LogMessage object) throws EncodeException {
            return gson.toJson(object);
        }
    }
}
