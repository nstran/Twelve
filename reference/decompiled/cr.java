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

public final class cr
implements PlayerListener,
v {
    private static cr a;
    private Player b;
    private String c = "";
    private VolumeControl d;
    private TempoControl e;
    private boolean f;
    private int g;

    protected cr() {
    }

    public static cr b() {
        if (a == null) {
            a = new cr();
        }
        return a;
    }

    public final void a(String string, int n2) {
        try {
            this.b(string, n2);
            return;
        }
        catch (Throwable throwable) {
            cw.a("Create Back ground 1: " + throwable);
            ct.a().b();
            ct.a().c();
            this.d();
            try {
                this.b(string, n2);
                return;
            }
            catch (Throwable throwable2) {
                this.d();
                cw.a("Back ground 2: " + throwable2);
                return;
            }
        }
    }

    private void b(String string, int n2) {
        if (!z.K) {
            return;
        }
        this.d();
        this.b = Manager.createPlayer((InputStream)"".getClass().getResourceAsStream("/audio/" + string + ".mid"), (String)"audio/midi");
        this.g = n2;
        this.b.setLoopCount(n2);
        this.b.realize();
        this.b.prefetch();
        this.b.addPlayerListener((PlayerListener)this);
        Control control = this.b.getControl("VolumeControl");
        if (control != null) {
            this.d = (VolumeControl)control;
            this.d.setLevel(z.N);
            this.d.setMute(!z.K);
        }
        if ((control = this.b.getControl("TempoControl")) != null) {
            this.e = (TempoControl)control;
            z.O = this.e.getTempo();
        }
        this.c = string;
        this.f = false;
    }

    public final void c() {
        if (this.b != null) {
            try {
                this.b.stop();
                return;
            }
            catch (Throwable throwable) {
                this.d();
                cw.a("[STOP BK Music]: " + throwable);
            }
        }
    }

    public final void d() {
        if (this.b != null) {
            try {
                try {
                    this.b.stop();
                    this.b.deallocate();
                }
                catch (Throwable throwable) {
                    cw.a("[Destroy BK Music1]: " + throwable);
                    try {
                        this.b.deallocate();
                    }
                    catch (Throwable throwable2) {
                        cw.a("[Destroy BK Music2]: " + throwable2);
                    }
                    this.b.close();
                    this.b = null;
                    this.d = null;
                    this.e = null;
                    this.f = false;
                    return;
                }
            }
            finally {
                this.b.close();
                this.b = null;
                this.d = null;
                this.e = null;
                this.f = false;
            }
        }
    }

    public final void e() {
        if (!z.K) {
            return;
        }
        w.a().a(this);
    }

    public final void a() {
        try {
            if (this.b != null) {
                this.b.stop();
                if (z.aa) {
                    ct.a().d();
                }
                if (this.d != null) {
                    this.d.setMute(!z.K);
                    this.d.setLevel(z.N);
                }
                this.b.start();
                return;
            }
        }
        catch (Throwable throwable) {
            cw.a("[Player BK Music2]: " + throwable);
            this.d();
            this.a(this.c, this.g);
            try {
                if (this.b != null) {
                    this.b.start();
                    return;
                }
            }
            catch (Throwable throwable2) {
                cw.a("[Player BK Music3]: " + throwable2);
                this.d();
            }
        }
    }

    public final void a(int n2) {
        if (this.e != null) {
            try {
                this.e.setTempo(n2);
                return;
            }
            catch (Throwable throwable) {}
        }
    }

    public final boolean f() {
        return this.f;
    }

    public final void g() {
        if (!z.K) {
            return;
        }
        if (this.d != null) {
            this.d.setLevel(z.N);
            this.d.setMute(!z.K);
        }
    }

    public final void playerUpdate(Player player, String string, Object object) {
        if (!string.equals("endOfMedia")) {
            if (string.equals("started")) {
                this.f = true;
                return;
            }
            if (string.equals("stopped")) {
                this.f = false;
            }
        }
    }
}

