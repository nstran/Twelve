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

public final class ak
extends Canvas
implements Runnable {
    private static ak d;
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
    private bo n;
    public static boolean b;
    public static boolean c;
    private static boolean o;
    private Graphics p;
    private Image q;
    private al r;
    private int s;
    private int t;
    private a u = new a(2);
    private at v;

    static {
        e = 0;
        a = f.d("/_arrow");
        o = false;
    }

    private ak() {
        this.setFullScreenMode(true);
        cv.d();
        o = z.ab = cv.a.c(-18);
        z.x = this.hasPointerEvents();
        String string = System.getProperty("microedition.platform").toUpperCase();
        z.J = this.getKeyCode(8) == -20 ? 3 : (string == null || string.length() == 0 ? 4 : (string.indexOf("NOKIAN") >= 0 ? 1 : (string.indexOf("NOKIA") >= 0 ? 0 : (string.indexOf("SONY") >= 0 ? 2 : (string.indexOf("SAMSUNG") >= 0 ? 7 : (string.indexOf("RIM") >= 0 ? 5 : (string.indexOf("SIE-EL71") >= 0 ? 6 : 4)))))));
        cw.a("Vendor: " + string + " detected as " + String.valueOf(z.J));
        this.v = new at();
    }

    public static ak a() {
        if (d == null) {
            d = new ak();
        }
        return d;
    }

    public static al b() {
        return ak.a().r;
    }

    public static void a(al al2) {
        ak.a().r = al2;
    }

    public static at c() {
        return ak.a().v;
    }

    public static void a(at at2) {
        ak.a().v = at2;
    }

    public final void a(av av2) {
        this.u.a(av2);
    }

    public final void d() {
        this.setFullScreenMode(true);
        this.i = true;
        this.q = Image.createImage((int)z.r, (int)z.s);
        this.p = ak.d.q.getGraphics();
        z.d = 2;
        if (z.s > 330) {
            z.d = 3;
            return;
        }
        if (z.r > 0 && z.r <= 130) {
            z.d = 0;
            return;
        }
        if (z.r <= 200) {
            z.d = 1;
        }
    }

    public final void e() {
        Thread thread = new Thread(this);
        thread.start();
    }

    public final void a(int n2, int n3) {
        this.f();
        this.n = new bo(n3);
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
        o = z.ab;
        z.ab = true;
        this.i = true;
    }

    public final void f() {
        if (!z.x) {
            return;
        }
        b = false;
        z.ab = o;
        this.n = null;
        z.e();
        this.i = true;
        ak.a().s = 0;
        z.c();
    }

    public static boolean g() {
        return b;
    }

    public final void a(int n2) {
        if (this.r != null) {
            this.r.g(n2);
        }
    }

    public final void b(int n2) {
        if (z.M) {
            this.m = (int)((long)n2 / z.l);
            if ((long)n2 % z.l > 0L) {
                ++this.m;
            }
            MGMIDlet.f().a.vibrate(n2);
        }
    }

    public static void h() {
        int n2 = 0;
        while (n2 < 300) {
            z.c[n2] = 0;
            ++n2;
        }
        z.D = -1;
        z.C = -1;
        z.B = -1;
        z.A = -1;
        z.z = -1;
        z.y = -1;
    }

    public static int a(int n2, boolean bl2) {
        int n3;
        if (!z.ab && !b && n2 >= 67 && n2 <= 126) {
            z.ab = true;
            o = true;
            cv.a.b(-18, new byte[]{1});
        }
        if (z.ab) {
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
        if (z.J == 5) {
            if (n2 == 113 || n2 == 35 || n2 == 81) {
                n2 = -6;
            } else if (n2 == 112 || n2 == 64 || n2 == 80) {
                n2 = -7;
            }
        } else if (z.J == 3) {
            n3 = n2;
            switch (n2) {
                case -21: {
                    n3 = -6;
                    break;
                }
                case -22: {
                    n3 = -7;
                    break;
                }
                case -23: {
                    n3 = -8;
                    break;
                }
                case -2: {
                    n3 = -3;
                    break;
                }
                case -5: {
                    n3 = -4;
                    break;
                }
                case -6: {
                    n3 = -2;
                    break;
                }
                case -20: {
                    n3 = -5;
                }
            }
            n2 = n3;
        } else if (z.J == 6) {
            n3 = n2;
            switch (n2) {
                case -59: {
                    n3 = -1;
                    break;
                }
                case -60: {
                    n3 = -2;
                    break;
                }
                case -61: {
                    n3 = -3;
                    break;
                }
                case -62: {
                    n3 = -4;
                    break;
                }
                case -26: {
                    n3 = -5;
                    break;
                }
                case -1: {
                    n3 = -6;
                    break;
                }
                case -4: {
                    n3 = -7;
                }
            }
            n2 = n3;
        }
        if (bl2) {
            n3 = n2;
            switch (n2) {
                case 50: {
                    n3 = -1;
                    break;
                }
                case 56: {
                    n3 = -2;
                    break;
                }
                case 52: {
                    n3 = -3;
                    break;
                }
                case 54: {
                    n3 = -4;
                    break;
                }
                case 10: 
                case 53: {
                    n3 = -5;
                }
            }
            n2 = n3;
        }
        n3 = n2 + 100;
        switch (n3) {
            case 94: {
                if (z.J != 1 || e != 0) break;
                e = 5;
                break;
            }
            case 93: {
                if (z.J != 1 || e != 0) break;
                e = 5;
            }
        }
        return n3;
    }

    public static void c(int n2) {
        ak.a().keyPressed(n2);
    }

    public static void d(int n2) {
        ak.a().keyReleased(n2);
    }

    protected final void keyPressed(int n2) {
        if (this.r == null) {
            return;
        }
        int n3 = this.r.i();
        n2 = ak.a(n2, n3 != 0);
        if (!(z.ab || n2 >= 89 && n2 <= 157)) {
            return;
        }
        n3 = 0;
        while (n3 < this.u.d()) {
            av av2 = (av)this.u.b(n3);
            av2.a(n2);
            ++n3;
        }
        if (n2 < 0 && n2 >= z.c.length) {
            return;
        }
        if (n2 == 94 || n2 == 93 || n2 == 95 && this.r.j()) {
            if (z.c[n2] == 0) {
                z.c[n2] = 2;
                return;
            }
        } else if (z.c[n2] == 0) {
            z.c[n2] = 3;
            this.r.a(n2);
        }
    }

    protected final void keyReleased(int n2) {
        if (this.r != null) {
            boolean bl2 = this.r.i();
            n2 = ak.a(n2, bl2);
            if (!(z.ab || n2 >= 89 && n2 <= 157)) {
                return;
            }
            if (n2 < 0 && n2 >= z.c.length) {
                return;
            }
            if (z.c[n2] == 2 || z.c[n2] >= 3) {
                z.c[n2] = 1;
                this.r.b(n2);
            }
        }
    }

    protected final void pointerPressed(int n2, int n3) {
        if (b) {
            if (n3 >= this.n.o()) {
                this.n.g(n2, n3 - this.s - z.w);
                return;
            }
            if (this.r != null) {
                this.r.a(n2 - z.v, n3 - z.w);
            }
            return;
        }
        if (this.r != null) {
            this.r.a(n2 - z.v, n3 - z.w);
        }
    }

    protected final void pointerReleased(int n2, int n3) {
        if (b) {
            if (n3 >= this.n.o()) {
                this.n.h(n2, n3 - this.s - z.w);
                return;
            }
            if (this.r != null) {
                this.r.b(n2 - z.v, n3 - z.w);
            }
            return;
        }
        if (this.r != null) {
            this.r.b(n2 - z.v, n3 - z.w);
        }
    }

    protected final void pointerDragged(int n2, int n3) {
        if (this.r != null) {
            this.r.c(n2 - z.v, n3 - z.w);
        }
    }

    protected final void showNotify() {
        this.k = false;
        Object object = this.l;
        synchronized (object) {
            this.setFullScreenMode(true);
            this.i = true;
            z.V = false;
            ak.h();
            cw.a("show notify" + false);
            this.j = false;
            this.l.notify();
            return;
        }
    }

    protected final void hideNotify() {
        this.k = true;
        ak.h();
        if (z.R) {
            Object object = this.l;
            synchronized (object) {
                this.j = true;
                if (this.r != null) {
                    this.r.l();
                }
                cw.a("hide notify" + false);
                return;
            }
        }
    }

    protected final void paint(Graphics graphics) {
        graphics.setClip(0, 0, z.t, z.u);
        if (this.r == null) {
            return;
        }
        if (this.i) {
            al.a(graphics);
            if (!b) {
                this.i = false;
            }
        }
        this.p.setClip(0, 0, this.q.getWidth(), this.q.getHeight());
        this.r.a(this.p, this.q, graphics);
        if (b && this.n != null) {
            this.n.a(graphics, z.v, z.w + this.s);
        }
    }

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
                        if (((ak)object).m > 0) {
                            --((ak)object).m;
                            if (((ak)object).m == 0) {
                                MGMIDlet.f().a.vibrate(0);
                            }
                        }
                        if (b) {
                            int n2 = ((ak)object).t - ((ak)object).s;
                            if (n2 > 3) {
                                n2 /= 2;
                            }
                            z.w -= n2;
                            ((ak)object).s += n2;
                            ((ak)object).n.i();
                        }
                        ((ak)object).r.h();
                        if (!this.k) {
                            this.repaint();
                        }
                    }
                    this.g = System.currentTimeMillis();
                    if (this.g - this.f > z.l) continue;
                    Thread.sleep(z.l - (this.g - this.f));
                }
                catch (Exception exception) {
                    object = exception;
                    cw.a(exception);
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

