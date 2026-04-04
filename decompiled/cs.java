/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.media.Control
 *  javax.microedition.media.Manager
 *  javax.microedition.media.Player
 *  javax.microedition.media.PlayerListener
 *  javax.microedition.media.control.TempoControl
 *  javax.microedition.media.control.VolumeControl
 */
import java.io.InputStream;
import javax.microedition.media.Control;
import javax.microedition.media.Manager;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.TempoControl;
import javax.microedition.media.control.VolumeControl;

public final class cs
implements PlayerListener {
    private Player a;
    private VolumeControl b;
    private String c = "";
    private boolean d;
    private long e;
    private int f;
    private int g = 5000;
    private TempoControl h;

    public cs() {
    }

    public cs(String string) {
        this.a(string);
    }

    private void a(String string) {
        this.d = false;
        if (this.a != null && string.equals(this.c)) {
            this.c();
            return;
        }
        try {
            if (!z.L || !z.P) {
                return;
            }
            this.a();
            this.a = Manager.createPlayer((InputStream)"".getClass().getResourceAsStream("/audio/" + string + ".amr"), (String)"audio/amr");
            this.a.setLoopCount(1);
            this.a.realize();
            this.a.prefetch();
            this.a.addPlayerListener((PlayerListener)this);
            Control control = this.a.getControl("VolumeControl");
            if (control != null) {
                this.b = (VolumeControl)control;
                this.b.setLevel(z.N);
                this.b.setMute(!z.K);
            }
            if ((control = this.a.getControl("TempoControl")) != null) {
                this.h = (TempoControl)control;
                z.O = this.h.getTempo();
            }
            this.c = string;
            cv.a(false);
            return;
        }
        catch (Throwable throwable) {
            cv.a(true);
            this.a();
            return;
        }
    }

    public final void a() {
        if (this.a != null) {
            try {
                try {
                    this.a.stop();
                    this.a.deallocate();
                }
                catch (Throwable throwable) {
                    this.a.close();
                    this.a = null;
                    this.b = null;
                    this.c = "";
                    return;
                }
            }
            finally {
                this.a.close();
                this.a = null;
                this.b = null;
                this.c = "";
            }
        }
    }

    public final void b() {
        if (!z.L || !z.P) {
            return;
        }
        this.d = true;
        if (this.a == null && !this.c.equals("")) {
            this.a(this.c);
        }
        try {
            if (z.aa) {
                cr.b().c();
            }
            if (this.a == null) {
                this.a();
                return;
            }
            this.a.setMediaTime(0L);
            if (this.b != null) {
                this.b.setMute(!z.L);
                this.b.setLevel(z.N);
            }
            this.a.start();
            this.e = System.currentTimeMillis();
            return;
        }
        catch (Throwable throwable) {
            this.a();
            cv.a(true);
            this.d = false;
            return;
        }
    }

    public final void c() {
        if (this.a != null) {
            try {
                this.d = false;
                this.a.stop();
                return;
            }
            catch (Throwable throwable) {
                this.a();
            }
        }
    }

    public final boolean d() {
        if (this.d) {
            if (this.f > 3) {
                this.c();
            } else if (System.currentTimeMillis() - this.e > (long)this.g) {
                ++this.f;
                this.c();
            }
        }
        return this.d;
    }

    public final void playerUpdate(Player player, String string, Object object) {
        if (z.L) {
            if (string.equals("endOfMedia")) {
                if (z.aa) {
                    cr.b().e();
                }
                this.d = false;
                return;
            }
            string.equals("started");
        }
    }
}

