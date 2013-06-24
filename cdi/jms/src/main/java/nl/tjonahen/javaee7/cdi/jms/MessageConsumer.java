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
package nl.tjonahen.javaee7.cdi.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * MDB receiving messages. 
 * @author Philippe Tjon-A-Hen philippe@tjonahen.nl
 */
@MessageDriven(mappedName = "jms/dataqueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class MessageConsumer implements MessageListener {
    
    /**
     * Constr.
     */
    public MessageConsumer() {
    }
    
    /**
     * Message consumer.
     * @param message  -
     */
    @Override
    public void onMessage(final Message message) {
        try {
            final Payload uid = message.getBody(Payload.class);
            Logger.getLogger(MessageConsumer.class.getName()).info("Recieved " + uid.getSomeData());
        } catch (JMSException ex) {
            Logger.getLogger(MessageConsumer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
