/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.PushRegistry
 *  javax.microedition.lcdui.Display
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.midlet.MIDlet
 *  javax.wireless.messaging.MessageConnection
 */
package com.mg.smsgame;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.PushRegistry;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.midlet.MIDlet;
import javax.wireless.messaging.MessageConnection;

public abstract class MGMIDlet
extends MIDlet {
    public Display a;
    public static boolean b = false;
    private boolean d = false;
    private long e = 0L;
    public boolean c;
    private static MGMIDlet f;

    public MGMIDlet(boolean bl2, String string, String string2, String string3, String string4) {
        z.T = true;
        if (true) {
            cl.h = 0;
            cl.g = 0;
            cl.f = 0;
            cl.e = 0;
            cl.i = 0;
            cl.j = 0;
        }
        if (string != null) {
            cl.a = string;
        }
        if (string2 != null) {
            cl.b = string2;
        }
        if (string3 != null) {
            cl.d = string3;
        }
        if (string4 != null) {
            cl.c = string4;
        }
        cl.k = String.valueOf(cl.a) + cl.b;
        this.g();
        this.d = false;
        Object object = this.getAppProperty("Distributor-Name");
        if (object == null || ((String)object).length() == 0) {
            object = "asao";
        }
        cl.l = object;
        this.a = Display.getDisplay((MIDlet)this);
        f = this;
        try {
            object = System.getProperty("microedition.platform");
            cw.a("Platform" + (String)object);
            z.aa = false;
            if (object != null) {
                int n2 = ((String)(object = ((String)object).toLowerCase().trim())).indexOf("nokia");
                if (n2 >= 0) {
                    z.aa = true;
                    if (((String)object).indexOf("n95") >= 0 || System.getProperty("com.nokia.mid.imei") != null || System.getProperty("com.nokia.mid.imsi") != null || System.getProperty("com.nokia.mid.networkid") != null || System.getProperty("com.nokia.mid.networksignal") != null || System.getProperty("com.nokia.mid.networkavailability") != null || System.getProperty("com.nokia.mid.batterylevel") != null || System.getProperty("com.nokia.mid.countrycode") != null) {
                        z.aa = false;
                    }
                } else {
                    n2 = ((String)object).indexOf("sonyericssonw810");
                    if (n2 >= 0) {
                        z.aa = true;
                    }
                }
            }
            cw.a("configure=" + System.getProperty("microedition.configuration"));
            cw.a("profile=" + System.getProperty("microedition.profiles"));
            cw.a("NokiaLessThanSerials60=" + z.aa);
            object = System.getProperty("com.sonyericsson.java.platform");
            if (object != null) {
                int n3 = ((String)(object = ((String)object).substring(3))).indexOf(".");
                if (n3 > 0) {
                    object = ((String)object).substring(0, n3);
                }
                Integer.parseInt((String)object);
            }
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
        }
        if (z.e) {
            if (z.T) {
                if (z.S) {
                    this.a();
                    MGMIDlet.b();
                }
                new ch();
                return;
            }
            object = PushRegistry.listConnections((boolean)false);
            if (!(object != null && ((String[])object).length != 0)) {
                this.a.setCurrent((Displayable)new an());
                return;
            }
            if (z.S) {
                MGMIDlet.b();
            }
            this.a.setCurrent((Displayable)new ch());
            return;
        }
        if (z.T) {
            new ch();
            return;
        }
        this.a.setCurrent((Displayable)new ch());
    }

    public final void a(String object) {
        cw.a("Request link: " + object);
        f.platformRequest((String)object);
        object = this;
        object.notifyDestroyed();
    }

    public static void b(String string) {
        try {
            f.platformRequest("tel:" + string);
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public final void a() {
        Object object = PushRegistry.listConnections((boolean)false);
        if (object == null || ((String[])object).length == 0) {
            try {
                object = "sms://:" + cl.d;
                String string = ((Object)((Object)this)).getClass().getName();
                PushRegistry.registerConnection((String)object, (String)string, (String)"*");
                return;
            }
            catch (SecurityException securityException) {
                return;
            }
            catch (Exception exception) {}
        }
    }

    protected void destroyApp(boolean bl2) {
        w.a().b();
    }

    protected void pauseApp() {
        this.e = System.currentTimeMillis();
    }

    protected void startApp() {
        if (!this.d) {
            this.d = true;
            this.c = true;
            return;
        }
        System.currentTimeMillis();
    }

    public static void b() {
        if (b) {
            return;
        }
        MessageConnection messageConnection = PushRegistry.listConnections((boolean)true);
        if (messageConnection != null && ((String[])messageConnection).length > 0) {
            try {
                messageConnection = (MessageConnection)Connector.open((String)("sms://:" + cl.d));
                cl.a(messageConnection);
                b = true;
            }
            catch (IOException iOException) {}
        } else {
            try {
                messageConnection = (MessageConnection)Connector.open((String)("sms://:" + cl.d));
                cl.a(messageConnection);
                b = false;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        cw.a("AutoStart=" + b);
    }

    public final void c() {
        cv.c();
        this.a.setCurrent((Displayable)ak.a());
        z.a(z.t, z.u, z.E);
        cu.b();
        this.h();
        if (z.j && cu.c()) {
            cu.i();
        }
        if (z.j && !cu.a(true)) {
            cu.i();
        }
        if (z.r < z.p || z.s < z.q) {
            cu.h();
        }
        ak.a().e();
    }

    public final void d() {
        this.notifyDestroyed();
    }

    public final void e() {
        z.c();
        z.V = true;
        cw.a(this.a, (Displayable)ak.a());
    }

    public final void a(Displayable displayable, boolean bl2) {
        z.c();
        z.V = true;
        this.a.setCurrent(displayable);
    }

    public static MGMIDlet f() {
        return f;
    }

    protected abstract void g();

    protected abstract void h();
}

