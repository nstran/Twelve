/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.MGMIDlet;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public abstract class ah
implements bf,
r {
    private static final int[] g;
    private static final int[] h;
    private boolean i = false;
    private final a j;
    protected an a;
    private final a k = new a(8);
    protected al b;
    private al l;
    private ad m;
    private aa n;
    public aa c;
    public aa d;
    private ak o;
    private int p;
    private boolean q;
    private boolean r;
    private boolean s;
    private Object[] t;
    private int u;
    private int v;
    private int w;
    al e = null;
    protected ax f;
    private boolean x = false;
    private static final cb y;
    private boolean z;

    static {
        int[] nArray = new int[5];
        nArray[0] = -2;
        nArray[1] = 2;
        g = nArray;
        int[] nArray2 = new int[5];
        nArray2[2] = 2;
        nArray2[3] = -2;
        h = nArray2;
        y = new cb();
    }

    public ah() {
        this.j = new a(4);
        s.a().a(6);
        this.p();
    }

    public final ax b() {
        return this.f;
    }

    public final void a(ax ax2) {
        this.f = ax2;
    }

    public final void c() {
        this.x = false;
    }

    public final void a(ak ak2) {
        this.o = ak2;
    }

    public final an d() {
        return this.a;
    }

    public final al e() {
        return this.b;
    }

    public final void f() {
        if (this.b != null) {
            this.b.e(true);
            return;
        }
        if (this.a != null) {
            this.a.e(true);
        }
    }

    public static void g() {
    }

    final void a(int n2) {
        if (this.i || this.s) {
            return;
        }
        if (this.b != null) {
            if (!this.b.k()) {
                int n3 = n2;
                v.c[n3] = v.c[n3] + 1;
                this.b.c(n2);
            }
            return;
        }
        if (this.a != null && !this.a.k()) {
            int n4 = n2;
            v.c[n4] = v.c[n4] + 1;
            this.a.c(n2);
        }
    }

    final void b(int n2) {
        if (this.i || this.s) {
            return;
        }
        if (this.b != null) {
            if (!this.b.k()) {
                v.c[n2] = 0;
                this.b.d(n2);
            }
            return;
        }
        if (this.a != null && !this.a.k()) {
            v.c[n2] = 0;
            this.a.d(n2);
        }
    }

    final void a(int n2, int n3) {
        if (this.i || this.s) {
            return;
        }
        v.A = n2;
        v.B = n3;
    }

    final void b(int n2, int n3) {
        if (this.i || this.s) {
            return;
        }
        v.C = n2;
        v.D = n3;
    }

    final void c(int n2, int n3) {
        if (this.i || this.s) {
            return;
        }
        if (v.E < 0) {
            v.E = n2;
        }
        if (v.F < 0) {
            v.F = n3;
        }
    }

    protected void h() {
    }

    final void i() {
        block13: {
            an an2;
            al al2;
            Object object;
            block12: {
                block11: {
                    this.h();
                    object = this;
                    if (((ah)object).f != null) {
                        ((ah)object).f.i();
                    }
                    if (((ah)object).u > 0) {
                        --((ah)object).u;
                        int n2 = ((ah)object).u % 5;
                        ((ah)object).v = g[n2];
                        ((ah)object).w = h[n2];
                        if (((ah)object).u == 0) {
                            ((ah)object).w = 0;
                            ((ah)object).v = 0;
                        }
                    }
                    al2 = ((ah)object).b;
                    an2 = ((ah)object).a;
                    if (((ah)object).s) break block11;
                    if (((ah)object).n == null) break block12;
                    ((ah)object).m = null;
                    ((ah)object).n.b();
                    if (((ah)object).n.a()) {
                        ((ah)object).n = null;
                        ((ah)object).l = null;
                        ((ah)object).i = false;
                        if (al2 != null) {
                            al2.i(false);
                        }
                    }
                    break block13;
                }
                ((ah)object).o.b_();
                break block13;
            }
            if (((ah)object).m != null) {
                ad cfr_ignored_0 = ((ah)object).m;
                object = ((ah)object).m;
            } else if (al2 != null) {
                al2.h(true);
                if (al2.f() && an2 != null) {
                    an2.h(false);
                }
            } else if (an2 != null) {
                an2.h(true);
            }
        }
        cq.a().e();
    }

    protected static void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, v.v, v.w);
    }

    final void a(Graphics object, Image image, Graphics graphics) {
        try {
            if (this.s) {
                if (this.r) {
                    this.o.a((Graphics)object, true);
                }
            } else {
                if (this.n != null) {
                    try {
                        if (this.a != null) {
                            this.a.a((Graphics)object, false);
                        }
                        this.n.a((Graphics)object);
                        if (this.l != null) {
                            object.setClip(this.n.c() + 10, this.n.d() + 10, this.n.e() - 20, this.n.f() - 20);
                            this.l.c((Graphics)object);
                        }
                    }
                    catch (Exception exception) {
                        Exception exception2 = exception;
                        exception.printStackTrace();
                    }
                } else if (this.b != null) {
                    try {
                        if (!this.z) {
                            if (this.a != null) {
                                this.a.a((Graphics)object, false);
                            }
                            int n2 = 0;
                            int n3 = this.k.d();
                            while (n2 < n3) {
                                ((al)this.k.b(n2)).c((Graphics)object);
                                ++n2;
                            }
                            if (!this.b.e()) {
                                this.z = true;
                            }
                        }
                        this.b.a((Graphics)object, true);
                    }
                    catch (Exception exception) {
                        Exception exception3 = exception;
                        exception.printStackTrace();
                    }
                } else if (this.a != null) {
                    try {
                        this.a.a((Graphics)object, true);
                    }
                    catch (Exception exception) {
                        Exception exception4 = exception;
                        exception.printStackTrace();
                    }
                }
                if (this.m != null) {
                    return;
                }
            }
            if (this.f != null) {
                this.f.a((Graphics)object);
            }
            Graphics graphics2 = graphics;
            graphics = image;
            image = graphics2;
            object = this;
            image.drawImage((Image)graphics, v.x + object.v, v.y + object.w, 20);
            return;
        }
        catch (Exception exception) {
            Exception exception5 = exception;
            exception.printStackTrace();
            return;
        }
    }

    final boolean j() {
        if (this.b != null) {
            return false;
        }
        if (this.a != null) {
            return this.a.a_();
        }
        return false;
    }

    public final boolean k() {
        if (this.b != null) {
            return this.b.q();
        }
        if (this.a != null) {
            return this.a.q();
        }
        return false;
    }

    public al a(String string, String string2, String[] stringArray, int[] nArray, int n2) {
        return new bv(string, string2, stringArray, nArray, n2);
    }

    public final al a(String string, String string2, String object, int n2, int n3) {
        String[] stringArray = new String[1];
        String[] stringArray2 = stringArray;
        stringArray[0] = object;
        int[] nArray = new int[1];
        object = nArray;
        nArray[0] = n2;
        return this.a(string, string2, stringArray2, (int[])object, 1);
    }

    public final al a(String string, String string2, String object, int n2, String string3, int n3, int n4) {
        String[] stringArray = new String[2];
        String[] stringArray2 = stringArray;
        stringArray[0] = object;
        stringArray2[1] = string3;
        int[] nArray = new int[2];
        object = nArray;
        nArray[0] = n2;
        object[1] = n3;
        return this.a(string, string2, stringArray2, (int[])object, 1);
    }

    public final al a(String string, String string2, boolean bl2) {
        return this.a(string, string2, null, null, 1);
    }

    public final void a(al al2, boolean bl2) {
        if (al2 != null) {
            v.c();
            al2.a(false);
            if (this.b != null) {
                al al3;
                al al4 = al3 = this.b;
                al3.e(true);
                this.k.a(al3);
            } else if (this.a != null) {
                an an2 = this.a;
                an2.e(true);
            }
            this.z = false;
            this.b = al2;
            if (this.b.d() && this.c != null) {
                this.m = null;
                this.n = this.c;
                this.n.a(this.b.c, this.b.d, this.b.c, this.b.d, this.b.f, this.b.g);
                this.i = true;
                this.l = this.b;
                return;
            }
            this.b.i(false);
        }
    }

    public final void a(al al2) {
        this.a(al2, false);
    }

    public final boolean c(int n2) {
        if (this.b != null && this.b.h() == n2) {
            return true;
        }
        int n3 = 0;
        while (n3 < this.k.d()) {
            al al2 = (al)this.k.b(n3);
            if (al2.h() == n2) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final al d(int n2) {
        if (this.b != null && this.b.h() == n2) {
            return this.b;
        }
        int n3 = 0;
        while (n3 < this.k.d()) {
            al al2 = (al)this.k.b(n3);
            if (al2.h() == n2) {
                return al2;
            }
            ++n3;
        }
        return null;
    }

    public final void a(int n2, boolean bl2) {
        al al2 = this.d(n2);
        if (al2 != null) {
            this.b(al2, false);
        }
    }

    public final void e(int n2) {
        this.a(n2, false);
    }

    public final void b(al al2, boolean bl2) {
        this.z = false;
        if (this.b != null && al2.equals(this.b)) {
            this.a(bl2);
            return;
        }
        this.k.b(al2);
        al2.s();
    }

    public final void a(boolean bl2) {
        v.c();
        if (this.b != null) {
            if (bl2 && (this.a == null || this.a.h() != -100001) && this.d != null) {
                this.m = null;
                this.n = this.d;
                this.n.a(this.b.c, this.b.d, this.b.c, this.b.d, this.b.f, this.b.g);
                this.i = true;
                this.l = this.b;
            }
            this.b.s();
        }
        this.b = null;
        if (this.k.d() > 0) {
            this.b = (al)this.k.a(this.k.d() - 1);
            if (this.b != null) {
                this.b.i(true);
            }
            if (this.a != null) {
                this.a.e(true);
            }
        } else if (this.a != null) {
            this.a.i(true);
        }
        System.gc();
    }

    public final void l() {
        v.c();
        if (this.b != null) {
            this.b.s();
        }
        this.b = null;
        int n2 = 0;
        while (n2 < this.k.d()) {
            ((al)this.k.b(n2)).s();
            ++n2;
        }
        this.k.a();
        this.n = null;
        System.gc();
    }

    protected final void a(an an2, boolean bl2, ad ad2) {
        if (an2 != null) {
            v.c();
            this.a = an2;
            this.m = ad2;
            if (this.m != null && this.n == null) {
                this.i = true;
                return;
            }
            this.m = null;
            this.n = null;
            this.a.i(false);
            this.s = false;
            if (bl2) {
                this.a.r();
            }
        }
    }

    public final void a(an an2) {
        this.a(an2, true, null);
    }

    public final void a(int n2, boolean bl2, boolean bl3, ad ad2, Object[] objectArray) {
        if (this.a == null || this.a.h() != n2) {
            this.p = n2;
            this.q = bl2;
            this.r = false;
            this.m = null;
            this.t = objectArray;
            v.c();
            ah ah2 = this;
            if (ah2.o == null) {
                ah2.o = new ak();
            }
            ah2.m = null;
            ah2.n = null;
            ag.a().e();
            ah2.o.a();
            v.ag = false;
            ah2.s = true;
            if (ah2.r) {
                ah2.o.a(0);
            }
            s.a().a(this);
        }
    }

    public final void f(int n2) {
        this.a(n2, false, false, null, null);
    }

    public final void a(int n2, Object[] objectArray) {
        this.a(5, false, false, null, objectArray);
    }

    public final void a() {
        an an2 = null;
        int n2 = 0;
        int n3 = this.j.d();
        while (n2 < n3) {
            an an3 = (an)this.j.b(n2);
            if (an3.h() == this.p) {
                an2 = an3;
                this.j.b(an3);
                break;
            }
            ++n2;
        }
        n2 = -1;
        if (this.a != null) {
            an an4 = this.a;
            this.a = y;
            if (this.q) {
                this.j.a(an4);
            } else {
                an4.s();
            }
            n2 = an4.h();
            System.gc();
        }
        n3 = 0;
        if (an2 == null) {
            an2 = this.a(n2, this.p, this.t);
            this.t = null;
            n3 = 1;
        }
        this.o.a(an2, null, n3 != 0);
    }

    public final void m() {
        this.m = null;
        this.n = null;
        if (this.a != null) {
        }
    }

    public final void n() {
        if (this.a != null) {
        }
    }

    public final void g(int n2) {
        this.u = n2;
    }

    public void o() {
        this.a(-10006, false);
        this.a(-10001, false);
        Object object = this.a("Ch\u00fa \u00fd", "M\u1ea1ng di \u0111\u1ed9ng \u0111ang c\u00f3 v\u1ea5n \u0111\u1ec1 ho\u1eb7c qu\u00e1 t\u1ea3i, vui l\u00f2ng th\u1eed l\u1ea1i sau", "\u0110\u00f3ng", 3, 1);
        ((am)object).a(this);
        al al2 = object;
        object = this;
        ((ah)object).a(al2, false);
    }

    public final void a(int n2, String object, String string, String string2) {
        v.M = false;
        v.ab = n2;
        v.ac = object;
        v.ad = string;
        if (string2 != null && string2.length() >= 5) {
            String string3 = string2.toLowerCase().substring(0, 4);
            string2 = string2.trim().substring(4);
            int n3 = Integer.parseInt(string2);
            if ("reco".toLowerCase().equals(string3)) {
                cs.a(n3, n2, string, object);
                if (MGMIDlet.b) {
                    object = MGMIDlet.d();
                    object.notifyDestroyed();
                    return;
                }
                n2 = n3;
                ah ah2 = this;
                if (ah2.a != null && ah2.a.h() == -100004) {
                    object = (cd)ah2.a;
                    ((cd)object).d();
                } else {
                    object = null;
                }
                if (object != null) {
                    string = object;
                    object = ah2;
                    ((ah)object).a((an)((Object)string), true, null);
                    ah2.a(-10006, false);
                }
                return;
            }
            "chal".toLowerCase().equals(string3);
        }
    }

    public void d(int n2, int n3) {
        if (n3 == -2) {
            this.a(-9999, false);
            if (this.a != null) {
                return;
            }
        } else {
            if (n3 == -1) {
                cs.a(0, new ai(this));
                return;
            }
            if (n3 == 1) {
                this.n();
                this.a(-10003, false);
                return;
            }
            if (n3 == 3) {
                this.a(true);
                return;
            }
            if (n3 == 2) {
                this.a(false);
                return;
            }
            if (n3 == 4) {
                MGMIDlet mGMIDlet = MGMIDlet.d();
                mGMIDlet.notifyDestroyed();
                return;
            }
            if (n3 == 6) {
                this.a(true);
                return;
            }
            if (n3 == 5) {
                try {
                    MGMIDlet.d().a("http://game.ola.vn");
                }
                catch (ConnectionNotFoundException connectionNotFoundException) {}
                this.a(false);
                return;
            }
            if (n3 == 7) {
                this.a(false);
                MGMIDlet.d();
                MGMIDlet.b("1900588883");
                return;
            }
            if (n3 == 8) {
                MGMIDlet.d();
                MGMIDlet.b("1900588883");
            }
        }
    }

    protected abstract an a(int var1, int var2, Object[] var3);

    public abstract void p();

    public static bt h(int n2) {
        bt bt2 = new bt(n2);
        if (n2 == 1) {
            bt2.e(2);
        } else {
            bt2.d(2);
        }
        return bt2;
    }
}

