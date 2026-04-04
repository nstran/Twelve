/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.wireless.messaging.BinaryMessage
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
import java.io.IOException;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

final class cp
implements Runnable {
    private Message a;
    private MessageConnection b;
    private String c;
    private String d = "";
    private cj e;

    cp(cj cj2, MessageConnection messageConnection) {
        this.b = messageConnection;
        this.e = cj2;
    }

    public final void run() {
        while (!z.X) {
            try {
                Thread.sleep(100L);
            }
            catch (Exception exception) {}
        }
        cp cp2 = this;
        try {
            cp2.a = cp2.b.receive();
            if (cp2.a != null) {
                cp2.c = cp2.a.getAddress();
                if (cp2.a instanceof TextMessage) {
                    cp2.d = ((TextMessage)cp2.a).getPayloadText();
                    if (cp2.e != null) {
                        cp2.e.a(cp2.d);
                        return;
                    }
                } else {
                    byte[] byArray = ((BinaryMessage)cp2.a).getPayloadData();
                    cp2.d = new String(byArray);
                    if (cp2.e != null) {
                        cp2.e.a(cp2.d);
                        return;
                    }
                }
            }
        }
        catch (IOException iOException) {}
    }
}

