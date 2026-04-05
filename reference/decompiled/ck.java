/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.MessageListener
 */
import java.io.IOException;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;

final class ck
implements MessageListener {
    private static ck a;
    private static cp b;
    private cj c;
    private static MessageConnection d;

    protected ck() {
        cl cl2 = cl.a();
        ck ck2 = this;
        this.c = cl2;
    }

    private static ck a() {
        if (a == null) {
            a = new ck();
        }
        return a;
    }

    public static void a(MessageConnection object) {
        MessageConnection messageConnection = object;
        object = ck.a();
        d = messageConnection;
        try {
            d.setMessageListener((MessageListener)object);
            return;
        }
        catch (IOException iOException) {
            object = iOException;
            iOException.printStackTrace();
            return;
        }
    }

    public final void notifyIncomingMessage(MessageConnection object) {
        b = new cp(this.c, (MessageConnection)object);
        object = b;
        object = new Thread((Runnable)object);
        object.start();
    }

    public static void a(String string, String string2, bi bi2) {
        ck.a();
        cq.a().a(new ci(string, string2, bi2));
    }
}

