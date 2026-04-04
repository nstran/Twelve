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

public abstract class al
implements bj,
v {
    private static final int[] g;
    private static final int[] h;
    private boolean i = false;
    private final a j;
    protected ar a;
    private final a k = new a(8);
    protected ap b;
    private ap l;
    private ah m;
    private ae n;
    public ae c;
    public ae d;
    private ao o;
    private int p;
    private boolean q;
    private boolean r;
    private boolean s;
    private Object[] t;
    private int u;
    private int v;
    private int w;
    ap e = null;
    protected bb f;
    private boolean x = false;
    private static final ce y;
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
        y = new ce();
    }

    public al() {
        this.j = new a(4);
        w.a().a(6);
        this.n();
    }

    public final bb b() {
        return this.f;
    }

    public final void a(bb bb2) {
        this.f = bb2;
    }

    public final void c() {
        this.x = false;
    }

    public final void a(ao ao2) {
        this.o = ao2;
    }

    public final ar d() {
        return this.a;
    }

    public final ap e() {
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

    final void a(int n2) {
        if (this.i || this.s) {
            return;
        }
        if (this.b != null) {
            if (!this.b.k()) {
                int n3 = n2;
                z.c[n3] = z.c[n3] + 1;
                this.b.c(n2);
            }
            return;
        }
        if (this.a != null && !this.a.k()) {
            int n4 = n2;
            z.c[n4] = z.c[n4] + 1;
            this.a.c(n2);
        }
    }

    final void b(int n2) {
        if (this.i || this.s) {
            return;
        }
        if (this.b != null) {
            if (!this.b.k()) {
                z.c[n2] = 0;
                this.b.d(n2);
            }
            return;
        }
        if (this.a != null && !this.a.k()) {
            z.c[n2] = 0;
            this.a.d(n2);
        }
    }

    final void a(int n2, int n3) {
        if (this.i || this.s) {
            return;
        }
        z.y = n2;
        z.z = n3;
    }

    final void b(int n2, int n3) {
        if (this.i || this.s) {
            return;
        }
        z.A = n2;
        z.B = n3;
    }

    final void c(int n2, int n3) {
        if (this.i || this.s) {
            return;
        }
        if (z.C < 0) {
            z.C = n2;
        }
        if (z.D < 0) {
            z.D = n3;
        }
    }

    protected void g() {
    }

    final void h() {
        block13: {
            ar ar2;
            ap ap2;
            Object object;
            block12: {
                block11: {
                    this.g();
                    object = this;
                    if (((al)object).f != null) {
                        ((al)object).f.i();
                    }
                    if (((al)object).u > 0) {
                        --((al)object).u;
                        int n2 = ((al)object).u % g.length;
                        ((al)object).v = g[n2];
                        ((al)object).w = h[n2];
                        if (((al)object).u == 0) {
                            ((al)object).w = 0;
                            ((al)object).v = 0;
                        }
                    }
                    ap2 = ((al)object).b;
                    ar2 = ((al)object).a;
                    if (((al)object).s) break block11;
                    if (((al)object).n == null) break block12;
                    ((al)object).m = null;
                    ((al)object).n.b();
                    if (((al)object).n.a()) {
                        ((al)object).n = null;
                        ((al)object).l = null;
                        ((al)object).i = false;
                        if (ap2 != null) {
                            ap2.i(false);
                        }
                    }
                    break block13;
                }
                ((al)object).o.b_();
                break block13;
            }
            if (((al)object).m != null) {
                object = ((al)object).m;
            } else if (ap2 != null) {
                ap2.h(true);
                if (ap2.f() && ar2 != null) {
                    ar2.h(false);
                }
            } else if (ar2 != null) {
                ar2.h(true);
            }
        }
        ct.a().e();
    }

    protected static void a(Graphics graphics) {
        graphics.setColor(0);
        graphics.fillRect(0, 0, z.t, z.u);
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
                                ((ap)this.k.b(n2)).c((Graphics)object);
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
            image.drawImage((Image)graphics, z.v + object.v, z.w + object.w, 20);
            return;
        }
        catch (Exception exception) {
            Exception exception5 = exception;
            exception.printStackTrace();
            return;
        }
    }

    final boolean i() {
        if (this.b != null) {
            return false;
        }
        if (this.a != null) {
            return this.a.a_();
        }
        return false;
    }

    public final boolean j() {
        if (this.b != null) {
            return this.b.q();
        }
        if (this.a != null) {
            return this.a.q();
        }
        return false;
    }

    public ap a(String string, String string2, String[] stringArray, int[] nArray, int n2) {
        return new by(string, string2, stringArray, nArray, n2);
    }

    public final ap a(String string, String string2, String object, int n2, int n3) {
        String[] stringArray = new String[1];
        String[] stringArray2 = stringArray;
        stringArray[0] = object;
        int[] nArray = new int[1];
        object = nArray;
        nArray[0] = n2;
        return this.a(string, string2, stringArray2, (int[])object, 1);
    }

    public final ap a(String string, String string2, String object, int n2, String string3, int n3, int n4) {
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

    public final ap a(String string, String string2, boolean bl2) {
        return this.a(string, string2, null, null, 1);
    }

    public final void a(ap ap2, boolean bl2) {
        if (ap2 != null) {
            z.c();
            ap2.a(false);
            if (this.b != null) {
                ap ap3;
                ap ap4 = ap3 = this.b;
                ap3.e(true);
                this.k.a(ap3);
            } else if (this.a != null) {
                ar ar2 = this.a;
                ar2.e(true);
            }
            this.z = false;
            this.b = ap2;
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

    public final void a(ap ap2) {
        this.a(ap2, false);
    }

    public final boolean c(int n2) {
        if (this.b != null && this.b.h() == n2) {
            return true;
        }
        int n3 = 0;
        while (n3 < this.k.d()) {
            ap ap2 = (ap)this.k.b(n3);
            if (ap2.h() == n2) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public final ap d(int n2) {
        if (this.b != null && this.b.h() == n2) {
            return this.b;
        }
        int n3 = 0;
        while (n3 < this.k.d()) {
            ap ap2 = (ap)this.k.b(n3);
            if (ap2.h() == n2) {
                return ap2;
            }
            ++n3;
        }
        return null;
    }

    public final void a(int n2, boolean bl2) {
        ap ap2 = this.d(n2);
        if (ap2 != null) {
            this.b(ap2, false);
        }
    }

    public final void e(int n2) {
        this.a(n2, false);
    }

    public final void b(ap ap2, boolean bl2) {
        this.z = false;
        if (this.b != null && ap2.equals(this.b)) {
            this.a(bl2);
            return;
        }
        this.k.b(ap2);
        ap2.s();
    }

    public final void a(boolean bl2) {
        z.c();
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
            this.b = (ap)this.k.a(this.k.d() - 1);
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

    public final void k() {
        z.c();
        if (this.b != null) {
            this.b.s();
        }
        this.b = null;
        int n2 = 0;
        while (n2 < this.k.d()) {
            ((ap)this.k.b(n2)).s();
            ++n2;
        }
        this.k.a();
        this.n = null;
        System.gc();
    }

    protected final void a(ar ar2, boolean bl2, ah ah2) {
        if (ar2 != null) {
            z.c();
            this.a = ar2;
            this.m = ah2;
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

    public final void a(ar ar2) {
        this.a(ar2, true, null);
    }

    public final void a(int n2, boolean bl2, boolean bl3, ah ah2, Object[] objectArray) {
        if (this.a == null || this.a.h() != n2) {
            this.p = n2;
            this.q = bl2;
            this.r = false;
            this.m = null;
            this.t = objectArray;
            z.c();
            al al2 = this;
            if (al2.o == null) {
                al2.o = new ao();
            }
            al2.m = null;
            al2.n = null;
            ak.a().f();
            al2.o.a();
            z.Z = false;
            al2.s = true;
            if (al2.r) {
                al2.o.a(0);
            }
            w.a().a(this);
        }
    }

    public final void f(int n2) {
        this.a(n2, false, false, null, null);
    }

    public final void a(int n2, Object[] objectArray) {
        this.a(5, false, false, null, objectArray);
    }

    public final void a() {
        ar ar2 = null;
        int n2 = 0;
        int n3 = this.j.d();
        while (n2 < n3) {
            ar ar3 = (ar)this.j.b(n2);
            if (ar3.h() == this.p) {
                ar2 = ar3;
                this.j.b(ar3);
                break;
            }
            ++n2;
        }
        n2 = -1;
        if (this.a != null) {
            ar ar4 = this.a;
            this.a = y;
            if (this.q) {
                this.j.a(ar4);
            } else {
                ar4.s();
            }
            n2 = ar4.h();
            System.gc();
        }
        n3 = 0;
        if (ar2 == null) {
            ar2 = this.a(n2, this.p, this.t);
            this.t = null;
            n3 = 1;
        }
        this.o.a(ar2, null, n3 != 0);
    }

    public final void l() {
        this.m = null;
        this.n = null;
    }

    public final void g(int n2) {
        this.u = n2;
    }

    public void m() {
        this.a(-10006, false);
        this.a(-10001, false);
        Object object = this.a("Ch\u00fa \u00fd", "M\u1ea1ng di \u0111\u1ed9ng \u0111ang c\u00f3 v\u1ea5n \u0111\u1ec1 ho\u1eb7c qu\u00e1 t\u1ea3i, vui l\u00f2ng th\u1eed l\u1ea1i sau", "\u0110\u00f3ng", 3, 1);
        ((aq)object).a(this);
        ap ap2 = object;
        object = this;
        ((al)object).a(ap2, false);
    }

    public final void a(int n2, String object, String string, String string2) {
        if (string2 != null && string2.length() >= 5) {
            String string3 = string2.toLowerCase().substring(0, 4);
            string2 = string2.trim().substring(4);
            int n3 = Integer.parseInt(string2);
            if ("reco".toLowerCase().equals(string3)) {
                cv.a(n3, n2, string, (String)object);
                if (MGMIDlet.b) {
                    MGMIDlet.f().d();
                    return;
                }
                n2 = n3;
                al al2 = this;
                if (al2.a != null && al2.a.h() == -100004) {
                    object = (cg)al2.a;
                    ((cg)object).d();
                } else {
                    object = null;
                }
                if (object != null) {
                    string = object;
                    object = al2;
                    ((al)object).a((ar)((Object)string), true, null);
                    al2.a(-10006, false);
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
                cv.a(0, new am(this));
                return;
            }
            if (n3 == 1) {
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
                MGMIDlet.f().d();
                return;
            }
            if (n3 == 6) {
                this.a(true);
                return;
            }
            if (n3 == 5) {
                try {
                    MGMIDlet.f().a("http://game.ola.vn");
                }
                catch (ConnectionNotFoundException connectionNotFoundException) {}
                this.a(false);
                return;
            }
            if (n3 == 7) {
                this.a(false);
                MGMIDlet.b("1900588883");
                return;
            }
            if (n3 == 8) {
                MGMIDlet.b("1900588883");
            }
        }
    }

    protected abstract ar a(int var1, int var2, Object[] var3);

    public abstract void n();

    public static bw h(int n2) {
        bw bw2 = new bw(n2);
        if (n2 == 1) {
            bw2.e(2);
        } else {
            bw2.d(2);
        }
        return bw2;
    }
}

