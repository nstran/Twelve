/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.MGMIDlet;
import com.mg.sq.a;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class nx
extends an
implements bf,
ij,
ik {
    public static int a;
    public static int b;
    public static long c;
    private Image d;
    private Image k;
    private Image l;
    private static int m;
    private int n = 0;
    private static String o;
    private int p = -1;
    private boolean q;
    private String r;
    private String s;
    private int t;
    private int[] u;
    private int v;

    static {
        c = 0L;
        m = 2;
    }

    public nx() {
        this(null);
    }

    public nx(String string) {
        super(5);
        this.r = string;
        com.mg.sq.a.s().l();
        try {
            this.f();
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            if (com.mg.sq.a.m != null) {
                com.mg.sq.a.m.G();
            }
            System.gc();
            try {
                this.f();
                return;
            }
            catch (OutOfMemoryError outOfMemoryError2) {
                com.mg.sq.a.s().j(1);
                return;
            }
        }
    }

    private void f() {
        this.b(5);
        this.a((ba)null);
        this.p = this.r == null ? 1 : 2;
        this.q = true;
        b = 0;
        a = 0;
        if (this.d == null) {
            this.d = f.d("/offline/avatardownloadscreen");
        }
        if (this.k == null) {
            this.k = f.d("/bardownloadscreen");
        }
        if (this.l == null) {
            this.l = f.d("/fillbardownloadscreen");
        }
    }

    public final boolean e() {
        return this.p != 2;
    }

    public final void a(boolean bl2, String object, int n2, int[] nArray, int n3) {
        this.s = object;
        this.t = n2;
        this.u = nArray;
        this.v = n3;
        if (bl2) {
            nx nx2 = this;
            object = ag.b().a("Th\u00f4ng b\u00e1o", "C\u00f3 phi\u00ean b\u1ea3n m\u1edbi, vui l\u00f2ng c\u1eadp nh\u1eadt!", "C\u1eadp nh\u1eadt", -4, 1);
            ((am)object).a(nx2);
            ((am)object).b(3000);
            ag.b().a((al)object, false);
            return;
        }
        if (!i.b((String)object)) {
            nx nx3 = this;
            object = ag.b().a("Th\u00f4ng b\u00e1o", "C\u00f3 phi\u00ean b\u1ea3n m\u1edbi, b\u1ea1n c\u00f3 mu\u1ed1n c\u1eadp nh\u1eadt kh\u00f4ng?", "C\u1eadp nh\u1eadt", -4, "Kh\u00f4ng", -5, 1);
            ((am)object).a(nx3);
            ((am)object).b(3000);
            ag.b().a((al)object, false);
            return;
        }
        this.a(n2, nArray, n3);
    }

    private void a(int n2, int[] nArray, int n3) {
        if (n2 < 0) {
            this.n = 4;
            return;
        }
        o = "\u0110ang c\u00e0i \u0111\u1eb7t...";
        m = 2;
        pa.a().a(n2, nArray, n3, this);
    }

    protected final void a(Graphics graphics) {
        int n2 = 0;
        if (this.d != null && v.u > this.d.getHeight()) {
            n2 = (v.u - this.d.getHeight()) / 2;
        }
        if (m > 0 && this.d != null) {
            if (v.t > this.d.getWidth() || v.u > this.d.getHeight()) {
                graphics.setColor(0);
                graphics.fillRect(0, 0, this.f, this.g);
            }
            graphics.drawImage(this.d, this.f / 2, this.g - n2, 33);
            if (o != null) {
                bx.c.a(graphics, o, this.f / 2, this.g - n2 - 17, 1);
            }
            if (this.e()) {
                com.mg.sq.a.g.a(graphics, "0.18.0" + (ct.a() ? " TEST SVIP = 210.211.116.157" : ""), v.t / 2, 0, 1);
            }
            --m;
        }
        if (this.l != null && this.k != null) {
            int n3 = (this.f - this.k.getWidth()) / 2;
            n2 = this.g - 33 - n2;
            graphics.drawImage(this.k, n3, n2, 0);
            if (a > 0) {
                if (b > a) {
                    b = a;
                }
                int n4 = b * 144 / a;
                this.a(graphics, n3 + 2, n2 + 2, n4, 1);
                bx.d.a(graphics, String.valueOf(b * 100 / a) + "%", n3 + this.k.getWidth() / 2, n2, 1);
            }
        }
    }

    private void a(Graphics graphics, int n2, int n3, int n4, int n5) {
        if (n4 <= 0) {
            return;
        }
        while (n4 > 0) {
            n5 = 10;
            if (10 > n4) {
                n5 = n4;
            }
            cw.a(graphics, this.l, 0, 0, n5, this.l.getHeight(), n2, n3, 0);
            n4 -= n5;
            n2 += n5;
        }
    }

    protected final void c() {
        if (this.q) {
            if (this.p == 1) {
                o = "Ki\u1ec3m tra phi\u00ean b\u1ea3n...";
                m = 2;
                int n2 = pd.C();
                ks.a().a(n2, go.a, true);
            } else {
                String string = this.r;
                nx nx2 = this;
                o = "\u0110ang n\u1ea1p m\u00e0n ch\u01a1i...";
                m = 2;
                pa.a().a(string, nx2);
            }
            this.q = false;
        }
        if (this.n > 0) {
            --this.n;
            if (this.n == 1) {
                this.a();
            }
        }
        if (this.p == 1 && a > 0 && System.currentTimeMillis() - c >= 18000000L) {
            pa.a().e();
            com.mg.sq.a.s().a(1, (String)null);
            return;
        }
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        m = 2;
    }

    public final void a() {
        pc.b();
        ag.b().f(2);
    }

    public final void b() {
        MGMIDlet mGMIDlet = MGMIDlet.d();
        mGMIDlet.notifyDestroyed();
    }

    public final void d() {
    }

    public final void a(jn jn2, byte[][] byArray) {
        oa.c = jn2;
        oa.b = byArray;
        ag.b().f(1);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -4: {
                try {
                    MGMIDlet.d().a(this.s);
                    return;
                }
                catch (ConnectionNotFoundException connectionNotFoundException) {
                    MGMIDlet mGMIDlet = MGMIDlet.d();
                    mGMIDlet.notifyDestroyed();
                    connectionNotFoundException.printStackTrace();
                    return;
                }
            }
            case -5: {
                com.mg.sq.a.s().a(3000, false);
                this.a(this.t, this.u, this.v);
            }
        }
    }

    public static void a(String string) {
        o = "C\u00e0i \u0111\u1eb7t " + string + cv.a(1000) + ".pak";
        m = 1;
    }

    public static void f(int n2) {
        b += n2;
        c = System.currentTimeMillis();
    }
}

