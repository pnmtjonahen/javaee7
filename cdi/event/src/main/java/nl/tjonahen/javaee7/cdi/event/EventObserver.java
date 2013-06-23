/*
 * Copyright (C) 2013 Philippe Tjon-A-Hen philippe@tjonahen.nl
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package nl.tjonahen.javaee7.cdi.event;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@ServerEndpoint("/events")
public class EventObserver {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());
    
    @OnOpen
    public void onOpen(final Session session) {
        System.out.println("onOpen");
        sessions.add(session);
    }
    
    @OnClose
    public void onCLose(final Session session) {
        System.out.println("onClose");
        sessions.remove(session);
    }
    
    @OnMessage
    public void onMessage(final String message) {
        System.out.println("onMessage " + message);
    }

    /**
     * Handles the message event.
     * @param msg  -
     */
    public void onCdiEvent(final @Observes @MessageEvent Message msg) {
        System.out.println("WebSocket event recieved " + msg.getSomeData());
        
        for (final Session session : sessions) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText("Event.." + msg.getSomeData());
                } catch (IOException ex) {
                    
                }
            }
        }
    }
}
