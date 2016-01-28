/*
 * Copyright 2015 ThoughtWorks, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.thoughtworks.go.server.websocket;

import com.thoughtworks.go.util.SystemEnvironment;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;

import java.io.*;

public class Message {

    public static void setupPolicy(WebSocketPolicy policy, SystemEnvironment environment) {
        policy.setMaxTextMessageBufferSize(environment.getWebsocketMaxMessageSize());
        policy.setMaxTextMessageSize(environment.getWebsocketMaxMessageSize());
        policy.setMaxBinaryMessageSize(environment.getWebsocketMaxMessageSize());
        policy.setMaxBinaryMessageBufferSize(environment.getWebsocketMaxMessageSize());
    }

    public static byte[] encode(Message msg) {
        String encode = JsonMessage.encode(msg);
        try {
            return encode.getBytes("UTF8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Message decode(InputStream input) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[8192];
            int len;
            while((len = input.read(buffer))>0)
                baos.write(buffer, 0, len);
            return decode(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Message decode(byte[] msg) {
        try {
            return JsonMessage.decode(new String(msg, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private final Action action;
    private final Object data;

    public Message(Action action) {
        this(action, null);
    }

    public Message(Action action, Object data) {
        this.action = action;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "action=" + action +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (action != message.action) return false;
        return data != null ? data.equals(message.data) : message.data == null;

    }

    @Override
    public int hashCode() {
        int result = action != null ? action.hashCode() : 0;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

}
