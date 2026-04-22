/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.wireless.messaging.Message
 *  javax.wireless.messaging.MessageConnection
 *  javax.wireless.messaging.TextMessage
 */
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

final class cn
implements bf,
Runnable {
    private cf a;
    private cf b;
    private static cn c;
    private static boolean d;
    private boolean e;
    private Object f = new Object();
    private boolean g = false;

    public static cn a() {
        if (c == null) {
            c = new cn();
        }
        ag.g();
        return c;
    }

    protected cn() {
        Thread thread = new Thread(this);
        thread.start();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        this.e = false;
        while (true) {
            Object object;
            if (this.a == null) {
                try {
                    object = this.f;
                    synchronized (object) {
                        this.f.wait();
                    }
                }
                catch (Throwable throwable) {
                    object = throwable;
                    throwable.printStackTrace();
                }
                continue;
            }
            if (cr.d() && this.g) {
                this.a = null;
                v.Z = false;
                object = ag.b().a("Ch\u00fa \u00fd", "M\u1ea1ng \u0111ang b\u1ecb ngh\u1ebdn, vui l\u00f2ng th\u1eed l\u1ea1i sau.", "\u0110\u00f3ng", 2, 1);
                ((am)object).a(ag.b());
                ag.b().a((al)object, false);
                ag.b().e(-10010);
                continue;
            }
            v.Z = true;
            object = ag.b().a("Ch\u00fa \u00fd", "\u0110ang g\u1eedi tin nh\u1eafn SMS", false);
            ((am)object).b(-10001);
            ag.b().a((al)object, false);
            ag.b().e(-10010);
            object = "sms://" + this.a.a;
            ct.a("[SMS:" + this.a.a + "]" + this.a.b);
            Object object2 = null;
            try {
                Throwable throwable;
                try {
                    object2 = (MessageConnection)Connector.open((String)object);
                    throwable = (TextMessage)object2.newMessage("text");
                    throwable.setAddress((String)object);
                    throwable.setPayloadText(this.a.b);
                    object2.send((Message)throwable);
                    cr.f();
                    if (cr.d()) {
                        this.g = true;
                        this.a = null;
                        ag.b().a(-10001, false);
                        object = ag.b().a("Ch\u00fa \u00fd", "\u0110\u00e3 g\u1eedi tin th\u00e0nh c\u00f4ng, \u0111ang ch\u1edd ph\u1ea3n h\u1ed3i t\u1eeb h\u1ec7 th\u1ed1ng", "\u1ea8n", 3, 1);
                        ((am)object).a(this);
                        ((am)object).b(-10004);
                        ag.b().a((al)object, false);
                        d = true;
                    } else {
                        ag.b().a(-10001, false);
                        if (this.a.c != null) {
                            this.a.c.a();
                        }
                        this.a = null;
                    }
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throwable2.printStackTrace();
                    this.b = this.a;
                    this.a = null;
                    ag.b().a(-10001, false);
                    object = ag.b().a("Ch\u00fa \u00fd", "Kh\u00f4ng th\u1ec3 g\u1eedi tin SMS. B\u1ea1n c\u00f3 mu\u1ed1n g\u1eedi l\u1ea1i kh\u00f4ng?", "C\u00f3", 1, "Kh\u00f4ng", 2, 1);
                    ((am)object).a(this);
                    ((am)object).b(-10002);
                    ag.b().a((al)object);
                    if (object2 == null) continue;
                    try {
                        object2.close();
                    }
                    catch (IOException iOException) {
                        object2 = iOException;
                        iOException.printStackTrace();
                    }
                    continue;
                }
            }
            catch (Throwable throwable) {
                if (object2 != null) {
                    try {
                        object2.close();
                    }
                    catch (IOException iOException) {
                        object2 = iOException;
                        iOException.printStackTrace();
                    }
                }
                throw throwable;
            }
            if (object2 == null) continue;
            try {
                object2.close();
                continue;
            }
            catch (IOException iOException) {
                object2 = iOException;
                iOException.printStackTrace();
                continue;
            }
            break;
        }
    }

    final void a(cf cf2) {
        if (cf2 != null) {
            this.a = cf2;
            this.b();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void b() {
        Object object = this.f;
        synchronized (object) {
            this.f.notify();
            return;
        }
    }

    public final void d(int n2, int n3) {
        if (n3 == 4) {
            ag.b().a(-10002, false);
            this.a = this.b;
            this.b = null;
            this.b();
            return;
        }
        if (n3 == 5) {
            this.b = null;
            ag.b().a(-10002, false);
            return;
        }
        if (n3 == 2) {
            ag.b().a(-10002, false);
            return;
        }
        if (n3 == 1) {
            ag.b().a(-10002, false);
            this.a(this.b);
            this.b = null;
            return;
        }
        if (n3 == 3 && d) {
            d = false;
            ag.b().a(-10004, false);
        }
    }
}

