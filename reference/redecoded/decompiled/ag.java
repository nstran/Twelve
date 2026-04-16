/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.MGMIDlet;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ag
extends Canvas
implements Runnable {
    private static ag d;
    private static int e;
    private long f;
    private long g;
    private boolean h;
    private boolean i = true;
    private boolean j;
    private boolean k;
    private final Object l = new Object();
    public static Image a;
    private int m = -1;
    private bk n;
    public static boolean b;
    public static boolean c;
    private static boolean o;
    private Graphics p;
    private Image q;
    private ah r;
    private int s;
    private int t;
    private a u = new a(2);
    private ap v;

    static {
        e = 0;
        a = f.d("/_arrow");
        o = false;
    }

    private ag() {
        this.setFullScreenMode(true);
        cs.d();
        o = v.ai = cs.a.c(-18);
        v.z = this.hasPointerEvents();
        String string = System.getProperty("microedition.platform").toUpperCase();
        v.L = this.getKeyCode(8) == -20 ? 3 : (string == null || string.length() == 0 ? 4 : (string.indexOf("NOKIAN") >= 0 ? 1 : (string.indexOf("NOKIA") >= 0 ? 0 : (string.indexOf("SONY") >= 0 ? 2 : (string.indexOf("SAMSUNG") >= 0 ? 7 : (string.indexOf("RIM") >= 0 ? 5 : (string.indexOf("SIE-EL71") >= 0 ? 6 : 4)))))));
        ct.a("Vendor: " + string + " detected as " + String.valueOf(v.L));
        this.v = new ap();
    }

    public static ag a() {
        if (d == null) {
            d = new ag();
        }
        return d;
    }

    public static ah b() {
        return ag.a().r;
    }

    public static void a(ah ah2) {
        ag.a().r = ah2;
    }

    public static ap c() {
        return ag.a().v;
    }

    public static void a(ap ap2) {
        ag.a().v = ap2;
    }

    public final void d() {
        this.setFullScreenMode(true);
        this.i = true;
        this.q = Image.createImage((int)v.t, (int)v.u);
        this.p = ag.d.q.getGraphics();
        v.d = 2;
        if (v.u > 330) {
            v.d = 3;
        } else if (v.t > 0 && v.t <= 130) {
            v.d = 0;
        } else if (v.t <= 200) {
            v.d = 1;
        }
        if (this.r != null) {
            ag ag2 = this;
            ag2.r.g();
        }
    }

    public final void a(int n2, int n3) {
        this.e();
        this.n = new bk(n3);
        n3 = this.n.a;
        if (n2 > n3) {
            this.s = 0;
            this.t = n2 - n3;
        } else {
            this.t = 0;
            this.s = 0;
        }
        b = true;
        c = true;
        o = v.ai;
        v.ai = true;
        this.i = true;
    }

    public final void e() {
        if (!v.z) {
            return;
        }
        b = false;
        v.ai = o;
        this.n = null;
        v.e();
        this.i = true;
        ag.a().s = 0;
        v.c();
    }

    public static boolean f() {
        return b;
    }

    public final void a(int n2) {
        if (this.r != null) {
            this.r.g(n2);
        }
    }

    public final void b(int n2) {
        if (v.P) {
            this.m = (int)((long)n2 / v.n);
            if ((long)n2 % v.n > 0L) {
                ++this.m;
            }
            MGMIDlet.d().a.vibrate(n2);
        }
    }

    public static void g() {
        int n2 = 0;
        while (n2 < 300) {
            v.c[n2] = 0;
            ++n2;
        }
        v.F = -1;
        v.E = -1;
        v.D = -1;
        v.C = -1;
        v.B = -1;
        v.A = -1;
    }

    public static int a(int n2, boolean bl2) {
        if (!v.ai && !b && n2 >= 67 && n2 <= 126) {
            v.ai = true;
            o = true;
            cs.a.b(-18, new byte[]{1});
        }
        if (v.ai) {
            if (n2 == 1 || n2 == -1) {
                n2 = -1;
            } else if (n2 == 6 || n2 == -2) {
                n2 = -2;
            } else if (n2 == 2 || n2 == -3) {
                n2 = -3;
            } else if (n2 == 5 || n2 == -4) {
                n2 = -4;
            } else if (n2 == 10 || n2 == 13 || n2 == -5) {
                n2 = -5;
            } else if (n2 == -6) {
                n2 = -6;
            } else if (n2 == -7) {
                n2 = -7;
            } else if (n2 == 8 || n2 == -8) {
                n2 = -8;
            }
        }
        if (v.L == 5) {
            if (n2 == 113 || n2 == 35 || n2 == 81) {
                n2 = -6;
            } else if (n2 == 112 || n2 == 64 || n2 == 80) {
                n2 = -7;
            }
        } else if (v.L == 3) {
            int n3 = n2;
            n2 = n3;
            n2 = n3;
            switch (n3) {
                case -21: {
                    n2 = -6;
                    break;
                }
                case -22: {
                    n2 = -7;
                    break;
                }
                case -23: {
                    n2 = -8;
                    break;
                }
                case -2: {
                    n2 = -3;
                    break;
                }
                case -5: {
                    n2 = -4;
                    break;
                }
                case -6: {
                    n2 = -2;
                    break;
                }
                case -20: {
                    n2 = -5;
                }
            }
        } else if (v.L == 6) {
            int n4 = n2;
            n2 = n4;
            n2 = n4;
            switch (n4) {
                case -59: {
                    n2 = -1;
                    break;
                }
                case -60: {
                    n2 = -2;
                    break;
                }
                case -61: {
                    n2 = -3;
                    break;
                }
                case -62: {
                    n2 = -4;
                    break;
                }
                case -26: {
                    n2 = -5;
                    break;
                }
                case -1: {
                    n2 = -6;
                    break;
                }
                case -4: {
                    n2 = -7;
                }
            }
        }
        if (bl2) {
            int n5 = n2;
            n2 = n5;
            n2 = n5;
            switch (n5) {
                case 50: {
                    n2 = -1;
                    break;
                }
                case 56: {
                    n2 = -2;
                    break;
                }
                case 52: {
                    n2 = -3;
                    break;
                }
                case 54: {
                    n2 = -4;
                    break;
                }
                case 10: 
                case 53: {
                    n2 = -5;
                }
            }
        }
        switch (n2 += 100) {
            case 94: {
                if (v.L != 1 || e != 0) break;
                e = 5;
                break;
            }
            case 93: {
                if (v.L != 1 || e != 0) break;
                e = 5;
            }
        }
        return n2;
    }

    public static void c(int n2) {
        ag.a().keyPressed(n2);
    }

    public static void d(int n2) {
        ag.a().keyReleased(n2);
    }

    protected final void keyPressed(int n2) {
        if (this.r == null) {
            return;
        }
        int n3 = this.r.j();
        n2 = ag.a(n2, n3 != 0);
        if (!(v.ai || n2 >= 89 && n2 <= 157)) {
            return;
        }
        n3 = 0;
        while (n3 < this.u.d()) {
            ar ar2 = (ar)this.u.b(n3);
            ar2.a(n2);
            ++n3;
        }
        if (n2 < 0 && n2 >= v.c.length) {
            return;
        }
        if (n2 == 94 || n2 == 93 || n2 == 95 && this.r.k()) {
            if (v.c[n2] == 0) {
                v.c[n2] = 2;
                return;
            }
        } else if (v.c[n2] == 0) {
            v.c[n2] = 3;
            this.r.a(n2);
        }
    }

    protected final void keyReleased(int n2) {
        if (this.r != null) {
            boolean bl2 = this.r.j();
            n2 = ag.a(n2, bl2);
            if (!(v.ai || n2 >= 89 && n2 <= 157)) {
                return;
            }
            if (n2 < 0 && n2 >= v.c.length) {
                return;
            }
            if (v.c[n2] == 2 || v.c[n2] >= 3) {
                v.c[n2] = 1;
                this.r.b(n2);
            }
        }
    }

    protected final void pointerPressed(int n2, int n3) {
        if (b) {
            if (n3 >= this.n.o()) {
                this.n.g(n2, n3 - this.s - v.y);
                return;
            }
            if (this.r != null) {
                this.r.a(n2 - v.x, n3 - v.y);
            }
            return;
        }
        if (this.r != null) {
            this.r.a(n2 - v.x, n3 - v.y);
        }
    }

    protected final void pointerReleased(int n2, int n3) {
        if (b) {
            if (n3 >= this.n.o()) {
                this.n.h(n2, n3 - this.s - v.y);
                return;
            }
            if (this.r != null) {
                this.r.b(n2 - v.x, n3 - v.y);
            }
            return;
        }
        if (this.r != null) {
            this.r.b(n2 - v.x, n3 - v.y);
        }
    }

    protected final void pointerDragged(int n2, int n3) {
        if (this.r != null) {
            this.r.c(n2 - v.x, n3 - v.y);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void showNotify() {
        this.k = false;
        Object object = this.l;
        synchronized (object) {
            this.setFullScreenMode(true);
            this.i = true;
            v.Z = false;
            ag.g();
            if (this.r != null && !this.r.c(-10003)) {
                this.r.n();
            }
            ct.a("show notifyfalse");
            this.j = false;
            this.l.notify();
            return;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected final void hideNotify() {
        this.k = true;
        ag.g();
        if (v.U) {
            Object object = this.l;
            synchronized (object) {
                this.j = true;
                if (this.r != null) {
                    this.r.c(-10003);
                    this.r.m();
                }
                ct.a("hide notifyfalse");
                return;
            }
        }
    }

    protected final void paint(Graphics graphics) {
        graphics.setClip(0, 0, v.v, v.w);
        if (this.r == null) {
            return;
        }
        if (this.i) {
            ah.a(graphics);
            if (!b) {
                this.i = false;
            }
        }
        this.p.setClip(0, 0, this.q.getWidth(), this.q.getHeight());
        this.r.a(this.p, this.q, graphics);
        if (b && this.n != null) {
            this.n.a(graphics, v.x, v.y + this.s);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public final void run() {
        this.h = false;
        this.j = false;
        while (true) {
            Object object;
            if (!this.j) {
                try {
                    this.f = System.currentTimeMillis();
                    if (this.r != null) {
                        object = this;
                        if (e > 0) {
                            --e;
                        }
                        if (((ag)object).m > 0) {
                            --((ag)object).m;
                            if (((ag)object).m == 0) {
                                MGMIDlet.d().a.vibrate(0);
                            }
                        }
                        if (b) {
                            int n2 = ((ag)object).t - ((ag)object).s;
                            if (n2 > 3) {
                                n2 /= 2;
                            }
                            v.y -= n2;
                            ((ag)object).s += n2;
                            ((ag)object).n.i();
                        }
                        ((ag)object).r.i();
                        if (!this.k) {
                            this.repaint();
                        }
                    }
                    this.g = System.currentTimeMillis();
                    if (this.g - this.f > v.n) continue;
                    Thread.sleep(v.n - (this.g - this.f));
                }
                catch (Exception exception) {
                    object = exception;
                    ct.a(exception);
                    ((Throwable)object).printStackTrace();
                }
                continue;
            }
            object = this.l;
            synchronized (object) {
                try {
                    this.l.wait();
                }
                catch (InterruptedException interruptedException) {
                    InterruptedException interruptedException2 = interruptedException;
                    interruptedException.printStackTrace();
                }
            }
        }
    }
}

