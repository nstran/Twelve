/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.smsgame.MGMIDlet;
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class ny
extends ar
implements bj,
ij {
    public boolean a = false;
    private nq p;
    public fc b;
    public static String c;
    public static int d;
    public static byte[][] k;
    public static jm l;
    public static boolean m;
    private kq q;
    public oj n;
    private oq r;
    private boolean s;
    private boolean t;
    private boolean u;
    public String o;
    private boolean v = false;
    private byte w = 0;
    private boolean x = true;

    public ny() {
        super(1);
        Object object;
        if (cw.c()) {
            cw.b("Initializing platform");
        }
        this.b(1);
        this.a((be)null);
        this.a(this);
        this.f(true);
        this.a(false);
        this.d(false);
        try {
            this.y();
        }
        catch (OutOfMemoryError outOfMemoryError) {
            object = outOfMemoryError;
            outOfMemoryError.printStackTrace();
            if (com.mg.sq.a.k != null) {
                com.mg.sq.a.k.G();
            }
            System.gc();
            this.x = false;
            try {
                this.y();
            }
            catch (OutOfMemoryError outOfMemoryError2) {
                cw.a("[platformscreen contructor] outof memory");
                com.mg.sq.a.j(1);
            }
            catch (Exception exception) {
                ((Throwable)object).printStackTrace();
            }
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
            com.mg.sq.a.j(6);
        }
        com.mg.sq.a.q().c();
        if (this.x && com.mg.sq.a.k != null) {
            object = this.b;
            try {
                ny.a((fc)object);
                og.v();
                if (com.mg.sq.a.m) {
                    com.mg.sq.a.k.i(false);
                }
            }
            catch (OutOfMemoryError outOfMemoryError) {
                og.f(false);
            }
            catch (Exception exception) {
                object = exception;
                exception.printStackTrace();
            }
        } else {
            oz.a();
        }
        if (com.mg.sq.a.j != null && com.mg.sq.a.j.length > 0) {
            com.mg.sq.a.b(com.mg.sq.a.j);
            com.mg.sq.a.j = null;
        }
        if (cw.c()) {
            cw.b("Platform initialized");
        }
    }

    private void y() {
        this.b = new fc(1, 2, 3);
        this.b.a(this);
        this.b(1);
        this.q = kq.a();
        this.n = new oj(100, 0, "B\u1ea3n \u0110\u1ed3", this);
        this.b.c(this.n);
        com.mg.sq.a.t();
        if (!cr.b().f()) {
            com.mg.sq.a.a(this);
        }
    }

    public static void a(fc fc2) {
        if (com.mg.sq.a.k != null && com.mg.sq.a.m) {
            fc2.c(com.mg.sq.a.k);
            com.mg.sq.a.k.C();
        }
    }

    protected final void a(Graphics graphics) {
        this.b.a(graphics, 0, 0);
    }

    protected final void c() {
        this.b.n();
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 125: {
                if (this.n != null) {
                    oj oj2 = this.n;
                    if (oj2.o != null && oj2.o.b() == 3) {
                        ((or)oj2.o).s();
                    }
                }
                or.l = null;
                com.mg.sq.a.q().a(false);
                com.mg.sq.a.a(null, null);
                ox.a().a(c, this);
                return;
            }
            case 11: {
                ak.b().e(-241229);
                this.t = false;
                this.A();
                if (gr.j.G == hq.l) break;
                com.mg.sq.a.E();
                if (this.n == null) break;
                hq.l = gr.j.G;
                return;
            }
            case 113: {
                ak.b().e(-241219);
                this.g();
                if (!this.t) {
                    this.A();
                    if (gr.j.G != hq.l) {
                        com.mg.sq.a.E();
                        this.q.p();
                        this.u = true;
                        if (this.n != null) {
                            hq.l = gr.j.G;
                            oj oj3 = this.n;
                            if (oj3.o instanceof ok) {
                                ((ok)oj3.o).t();
                            }
                        }
                    }
                }
                if (this.n != null) {
                    oj oj4 = this.n;
                    if (oj4.o instanceof ok) {
                        ((ok)oj4.o).s();
                    }
                }
                hq.t();
                return;
            }
            case 114: {
                ak.b().e(-241439);
                return;
            }
            case 12: {
                ak.b().a(false);
                return;
            }
            case 10: {
                com.mg.sq.a.a(true, null);
                this.v = true;
                return;
            }
            case 13: {
                com.mg.sq.a.a(false, null);
                return;
            }
            case 122: {
                ak.b().e(199199);
                return;
            }
            case 120: {
                if (gr.s != null && gr.s.length > 0) {
                    n2 = 0;
                    while (n2 < gr.s.length) {
                        gr.a(gr.s[n2], gr.s[n2].g);
                        ++n2;
                    }
                }
                ak.b().e(-241439);
                return;
            }
            case 121: {
                ak.b().a(false);
                com.mg.sq.a.a(null, null);
                kq.a().f(this.o);
                return;
            }
            case 123: {
                ak.b().k();
                this.q.a(true);
                return;
            }
            case 124: {
                ak.b().a(false);
                this.q.a(false);
                return;
            }
            case 127: {
                ak.b().a(false);
                hf hf2 = (hf)ny.t();
                hf2.i(this.w);
                if (this.a) break;
                this.a = true;
                return;
            }
            case 2: {
                this.z();
                this.b.t();
                return;
            }
            case 1: {
                if (com.mg.sq.a.k == null || com.mg.sq.a.k.A() == null || !this.b.b(com.mg.sq.a.k.A())) break;
                this.b.a(com.mg.sq.a.k.A());
                return;
            }
            case 3: {
                if (com.mg.sq.a.k == null || com.mg.sq.a.k.w() == null || !this.b.b(com.mg.sq.a.k.w())) break;
                this.b.a(com.mg.sq.a.k.w());
                return;
            }
            case 99030: {
                if (this.n == null) break;
                this.n.j(2);
            }
        }
    }

    public final void a(String object, int n2, int n3) {
        if (this.n != null && this.n.o instanceof ok) {
            ((ok)this.n.o).n = null;
        }
        switch (n3) {
            case 1: {
                hq.k = gr.j.J;
                hq.l = gr.j.G;
                return;
            }
            case 0: {
                if (((String)object).equals("M99")) {
                    oe.a = n2;
                    int n4 = 4;
                    ny ny2 = this;
                    try {
                        ny2.b.r();
                        System.gc();
                        ak.b().f(4);
                        ak.b().k();
                    }
                    catch (Exception exception) {
                        cw.b("out memmory");
                        cw.a(exception.toString());
                    }
                    System.gc();
                    return;
                }
                d = n2;
                c = object;
                System.gc();
                try {
                    com.mg.sq.a.b(null, null, 3000);
                    ak.b().a(5, new Object[]{object});
                    return;
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                }
            }
        }
    }

    public final void a(nq[] nqArray) {
        if (this.u) {
            if (nqArray != null && nqArray.length > 0) {
                this.b.s();
            }
            this.u = false;
            return;
        }
        if (this.s) {
            this.s = false;
            if (ak.b().c(241204)) {
                hp hp2 = (hp)ak.b().d(241204);
                hp2.a(nqArray);
                hp2.a((byte)0);
                return;
            }
        } else if (ak.b().c(241204)) {
            hp hp3 = (hp)ak.b().d(241204);
            hp3.a(nqArray);
            hp3.a((byte)0);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a(lf lf2, String object, long l2, String string, boolean bl2, boolean bl3) {
        if (this.v) {
            return;
        }
        Object object2 = null;
        if (com.mg.sq.a.q().c(191919)) {
            object2 = (gy)com.mg.sq.a.q().d(191919);
        }
        if (object2 != null) {
            this.d(1, 13);
            return;
        }
        object2 = this.b.q();
        if (object2 != null) {
            ((fb)object2).t();
        }
        if (this.n != null) {
            boolean bl4 = bl3;
            boolean bl5 = bl2;
            String string2 = string;
            long l3 = l2;
            Object object3 = object;
            object2 = lf2;
            object = this.n;
            if (((oj)object).o != null) {
                if (((oj)object).o instanceof ok) {
                    ((ok)((oj)object).o).m.a();
                    z.c();
                } else if (((oj)object).o instanceof op) {
                    ((op)((oj)object).o).a((lf)object2, (String)object3, l3, string2, bl5, bl4);
                    return;
                }
            }
            boolean bl6 = false;
            if (bl6) {
                return;
            }
        }
        com.mg.sq.a.a(lf2, 10, 13, this, l2, string, bl2, bl3);
    }

    public final void a(lf lf2, lf lf3, boolean bl2, byte[] byArray, byte[] byArray2, byte[] byArray3, int n2, byte by2, byte by3) {
        this.v = false;
        l = null;
        k = null;
        if (this.n != null) {
            this.n.a(lf2, lf3, bl2, byArray, byArray2, byArray3, by2, by3);
        }
    }

    public final void a(lf lf2, lf lf3, boolean bl2) {
        this.v = false;
        l = null;
        k = null;
        gy gy2 = null;
        if (com.mg.sq.a.q().c(191919)) {
            gy2 = (gy)com.mg.sq.a.q().d(191919);
        }
        if (gy2 == null) {
            ak.b().e(-241209);
            ak.b().e(-241249);
            gy2 = new gy(lf2, lf3, true, bl2, 99030, (bj)this);
            gy2.a(this.n);
            ak.b().a(gy2, false);
        }
        com.mg.sq.a.t();
        gy2.t();
    }

    public final void d() {
        com.mg.sq.a.b(false);
    }

    public final void a(jm jm2, byte[][] byArray) {
        l = jm2;
        k = byArray;
        System.gc();
        if (this.n != null) {
            this.n.j(1);
        }
        com.mg.sq.a.t();
    }

    public final void e() {
        this.q.p();
    }

    protected final void a(int n2) {
        this.b.f(n2);
    }

    protected final void e(int n2) {
        this.b.g(n2);
    }

    protected final void g(int n2, int n3) {
        this.b.e(n2, n3);
    }

    protected final void e(int n2, int n3) {
        this.b.c(n2, n3);
    }

    protected final void f(int n2, int n3) {
        this.b.f(n2, n3);
    }

    public final void f() {
        this.q.p();
        this.s = true;
        com.mg.sq.a.a(null, null);
    }

    public final void g() {
        Object object;
        if (this.n != null) {
            boolean bl2;
            object = this.n;
            if (((oj)object).o instanceof op) {
                bl2 = true;
            } else {
                if (((oj)object).o instanceof ok) {
                    ((ok)((oj)object).o).m.a();
                    z.c();
                }
                bl2 = false;
            }
            if (bl2) {
                return;
            }
        }
        this.p = ns.a();
        if (this.p != null) {
            this.t = true;
            object = new gz(this.p, this.p.e);
            ((aq)object).a(this);
            ((aq)object).a(new bh("\u0110\u00f3ng", 11));
            bd bd2 = null;
            Object object2 = object;
            ((aq)object2).a(bd2, true);
            bd2 = null;
            object2 = object;
            ((aq)object2).b(bd2, true);
            this.s = true;
            ak.b().a((ap)object);
            this.q.p();
            this.u = true;
            this.q.b(gr.e);
            this.q.a(gr.e, (byte)1);
            return;
        }
        object = ns.b();
        if (object != null) {
            this.z();
            ns.c();
            return;
        }
        object = ns.c();
        if (object != null) {
            this.b.s();
        }
    }

    public final void a(jn[] jnArray, String jnArray2) {
        if (!c.equals(jnArray2)) {
            return;
        }
        boolean bl2 = false;
        jnArray2 = jnArray;
        oj oj2 = this.n;
        if (oj2.o instanceof ok) {
            ((ok)oj2.o).a(jnArray2, false);
        }
    }

    public final void a(lf object, int n2) {
        if (this.n != null) {
            int n3 = n2;
            lf lf2 = object;
            object = this.n;
            switch (n3) {
                case 101: {
                    if (!(((oj)object).o instanceof or)) break;
                    ((or)((oj)object).o).b(lf2);
                }
            }
        }
    }

    public final void e(boolean bl2) {
        fb fb2;
        if (bl2 && (fb2 = this.b.q()) != null) {
            fb2.c(true);
        }
    }

    public static bu[] a(fb fb2) {
        bu bu2 = new bu("H\u1ed7 tr\u1ee3", 99001);
        bu bu3 = new bu("Mua b\u00e1n", 99002);
        bu bu4 = new bu("N\u1ea1p KEN", 99003);
        bu4.a(new bu[]{new bu("Nh\u1eafn tin", 99004), new bu("Th\u1ebb c\u00e0o \u0110T", 99005), new bu("DS \u0111i\u1ec7n tho\u1ea1i", 99006)});
        if (com.mg.sq.a.k != null && com.mg.sq.a.m) {
            bu2.a(new bu[]{new bu("Th\u00f4ng Tin #12", 99031), new bu("Gi\u1edbi thi\u1ec7u", 99007), new bu("H\u01b0\u1edbng d\u1eabn", 99008), new bu("Th\u1ea3o lu\u1eadn #SQ", 99009), new bu("H\u1ed7 tr\u1ee3", 99012), new bu("\u0110\u1ed5i S\u0110T", 99010), new bu("C\u00e0i \u0111\u1eb7t", 99011)});
            bu3.a(new bu[]{new bu("C\u1eeda h\u00e0ng", 99013), new bu("Ch\u1ee3 tr\u1eddi", 99034), new bu("Giao d\u1ecbch", 99014), bu4, new bu("Mua vip", 99015), new bu("Rao v\u1eb7t #sqgd", 99017)});
        } else {
            bu2.a(new bu[]{new bu("Gi\u1edbi thi\u1ec7u", 99007), new bu("H\u1ed7 tr\u1ee3", 99001), new bu("\u0110\u1ed5i S\u0110T", 99010), new bu("C\u00e0i \u0111\u1eb7t", 99011)});
            bu3.a(new bu[]{new bu("C\u1eeda h\u00e0ng", 99013), new bu("Ch\u1ee3 tr\u1eddi", 99034), new bu("Giao d\u1ecbch", 99014)});
        }
        bu bu5 = new bu("\u0110\u0103ng nh\u1eadp OLA", 99032);
        bu bu6 = new bu("Nh\u00e2n V\u1eadt", 99018);
        bu6.a(new bu[]{new bu("Th\u00f4ng tin", 99019), new bu("Tuy\u1ec7t Chi\u00eau", 99020), new bu("R\u01b0\u01a1ng \u0110\u1ed3", 99021), new bu("Ch\u1ebf t\u1ea1o", 99033), new bu("X\u1ebfp h\u1ea1ng", 99022)});
        bu[] buArray = null;
        if (fb2 instanceof oj) {
            fb2 = (oj)fb2;
            if (((oj)fb2).o != null) {
                buArray = ((oj)fb2).o instanceof or ? (!com.mg.sq.a.m ? (((or)((oj)fb2).o).q() == 1 ? new bu[]{new bu("Tr\u1edf v\u1ec1", 99028), new bu("Khi\u00eau Chi\u1ebfn", 99024), bu6, bu3, new bu("Nhi\u1ec7m V\u1ee5", 99025), bu5, bu2, new bu("Tho\u00e1t", 99027)} : new bu[]{new bu("Khi\u00eau Chi\u1ebfn", 99024), bu6, bu3, new bu("Nhi\u1ec7m V\u1ee5", 99025), bu5, bu2, new bu("Tho\u00e1t", 99027)}) : (((or)((oj)fb2).o).q() == 1 ? new bu[]{new bu("Tr\u1edf v\u1ec1", 99028), new bu("Khi\u00eau Chi\u1ebfn", 99024), bu6, bu3, new bu("Nhi\u1ec7m V\u1ee5", 99025), bu2, new bu("Tho\u00e1t", 99027)} : new bu[]{new bu("Khi\u00eau Chi\u1ebfn", 99024), bu6, bu3, new bu("Nhi\u1ec7m V\u1ee5", 99025), bu2, new bu("Tho\u00e1t", 99027)})) : (!com.mg.sq.a.m ? new bu[]{new bu("L\u00f4i \u0110\u00e0i", 99028), new bu("Khi\u00eau Chi\u1ebfn", 99024), bu6, bu3, bu4, new bu("Nhi\u1ec7m V\u1ee5", 99025), bu5, bu2, new bu("\u0110\u0103ng Xu\u1ea5t", 99026)} : new bu[]{new bu("L\u00f4i \u0110\u00e0i", 99028), new bu("Khi\u00eau Chi\u1ebfn", 99024), bu6, bu3, bu4, new bu("Nhi\u1ec7m V\u1ee5", 99025), bu2, new bu("\u0110\u0103ng Xu\u1ea5t", 99026)});
            }
        }
        if (com.mg.sq.a.k != null) {
            buArray = com.mg.sq.a.k.a(buArray, 4);
        }
        return buArray;
    }

    public final boolean a(int n2, bu bu2) {
        switch (n2) {
            case 99001: {
                return true;
            }
            case 99002: {
                return true;
            }
            case 99003: {
                com.mg.sq.a.M();
                return true;
            }
            case 99004: 
            case 99005: 
            case 99006: {
                if (!com.mg.sq.a.m(n2)) {
                    if (com.mg.sq.a.k != null) {
                        return com.mg.sq.a.k.b(bu2.b());
                    }
                    return false;
                }
                return true;
            }
            case 99007: {
                com.mg.sq.a.B();
                return true;
            }
            case 99031: {
                if (com.mg.sq.a.k != null) {
                    com.mg.sq.a.k.i(true);
                }
                return true;
            }
            case 99009: {
                if (com.mg.sq.a.k != null) {
                    com.mg.sq.a.k.j(true);
                }
                return true;
            }
            case 99008: {
                if (com.mg.sq.a.k != null) {
                    com.mg.sq.a.k.F();
                }
                return true;
            }
            case 99010: {
                com.mg.sq.a.Q();
                return true;
            }
            case 99011: {
                com.mg.sq.a.D();
                return true;
            }
            case 99012: {
                MGMIDlet.b("1900588883");
                return true;
            }
            case 99013: {
                com.mg.sq.a.F();
                return true;
            }
            case 99034: {
                com.mg.sq.a.G();
                return true;
            }
            case 99014: {
                com.mg.sq.a.e("Nh\u1eadp t\u00ean ng\u01b0\u1eddi c\u1ea7n giao d\u1ecbch");
                return true;
            }
            case 99015: {
                dv.a().b((short)2412);
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 99017: {
                if (com.mg.sq.a.k != null) {
                    com.mg.sq.a.k.E();
                }
                return true;
            }
            case 99018: {
                return true;
            }
            case 99019: {
                com.mg.sq.a.E();
                return true;
            }
            case 99020: {
                hz hz2 = new hz();
                ak.b().a(hz2);
                return true;
            }
            case 99021: {
                ny.t();
                return true;
            }
            case 99033: {
                ak.b().a(false);
                com.mg.sq.a.H();
                return true;
            }
            case 99022: {
                com.mg.sq.a.q();
                com.mg.sq.a.P();
                return true;
            }
            case 99024: {
                com.mg.sq.a.d("");
                return true;
            }
            case 99025: {
                this.z();
                return true;
            }
            case 99026: {
                com.mg.sq.a.s();
                return true;
            }
            case 99027: {
                ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n mu\u1ed1n tho\u00e1t kh\u1ecfi L\u00f4i \u0110\u00e0i?", "C\u00f3", 125, "Kh\u00f4ng", 12, 1);
                ap2.a(this);
                ak.b().a(ap2, false);
                return true;
            }
            case 99028: {
                this.q.t();
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 11399: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.b(bu2.b());
                break;
            }
            case 99032: {
                nz.d();
            }
        }
        return false;
    }

    public final void a(String string, boolean bl2, int n2, boolean bl3, boolean bl4) {
        com.mg.sq.a.f(string);
        this.q.a(string, "", false, 0L, false, false);
    }

    public static ap t() {
        if (com.mg.sq.a.q().c(241202)) {
            com.mg.sq.a.q().e(241202);
        }
        hf hf2 = new hf(null, null);
        ak.b().a(hf2);
        return hf2;
    }

    private void z() {
        hp hp2 = new hp();
        hp2.a((Object)this.n);
        ak.b().a(hp2);
        this.q.p();
        com.mg.sq.a.a(null, null);
    }

    private void A() {
        if (gr.s != null && gr.s.length > 0) {
            hj hj2 = new hj("B\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c: ", gr.s);
            hj2.a(this);
            bd bd2 = null;
            hj hj3 = hj2;
            hj3.a(bd2, true);
            bd2 = null;
            hj3 = hj2;
            hj3.b(bd2, true);
            ((aq)hj2).a(new bh("\u0110\u00f3ng", 120));
            ak.b().a(hj2);
        }
    }

    public final void a(String object) {
        if (this.n != null && this.n.o instanceof op) {
            this.q.a(false);
            return;
        }
        object = ak.b().a("", String.valueOf(object) + " mu\u1ed1n giao d\u1ecbch v\u1edbi b\u1ea1n. B\u1ea1n c\u00f3 \u0111\u1ed3ng \u00fd kh\u00f4ng?", "\u0110\u1ed3ng \u00fd", 123, "Kh\u00f4ng", 124, 1);
        ((aq)object).b(241207);
        ((aq)object).a(this);
        ak.b().a((ap)object, false);
    }

    public final boolean u() {
        int n2 = 0;
        while (n2 < gr.j.D.length) {
            int n3 = 0;
            while (n3 < gr.k.length) {
                if (gr.j.D[n2].b.equals(gr.k[n3].b) && gr.k[n3].n == 0 && gr.k[n3].n < gr.k[n3].o) {
                    if (!this.a) {
                        ap ap2 = ak.b().a("", "Trang b\u1ecb \u0111\u00e3 h\u01b0 h\u1ecfng s\u1ebd m\u1ea5t t\u00e1c d\u1ee5ng. B\u1ea1n c\u00f3 mu\u1ed1n s\u1eeda ch\u1eefa kh\u00f4ng? ", "\u0110\u1ed3ng \u00fd", 127, "Kh\u00f4ng", 12, 1);
                        ap2.a(this);
                        ak.b().a(ap2, false);
                        this.w = gr.k[n3].d;
                    }
                    return true;
                }
                ++n3;
            }
            ++n2;
        }
        return false;
    }

    public final void a(dk[] dkArray) {
        if (dkArray == null || dkArray.length <= 0) {
            dkArray = ak.b().a("Ch\u00fa \u00fd", "H\u1ec7 th\u1ed1ng ch\u01b0a m\u1edf t\u00ednh n\u0103ng n\u00e0y! Vui l\u00f2ng th\u1eed l\u1ea1i sau.", "\u0110\u00f3ng", 12, 1);
            dkArray.a(this);
            ak.b().a((ap)dkArray, false);
            return;
        }
        if (this.r == null) {
            this.r = new oq(dkArray, this);
        }
        if (!this.b.b(this.r)) {
            this.b.c(this.r);
        }
        this.b.a(this.r);
    }

    public final void v() {
        if (this.v) {
            this.v = false;
        }
    }

    public final void w() {
        this.b.d(this.r);
        this.r = null;
        if (this.n != null) {
            this.b.a(this.n);
        }
    }

    public final void x() {
        this.v = false;
    }

    protected final void s() {
        this.n.B();
        this.n = null;
        this.b.r();
        this.b = null;
        this.r = null;
        this.i = null;
        this.p = null;
        this.q = null;
        ox.a().d();
        l = null;
        k = null;
        gr.s = null;
        gr.r = null;
        gr.s = null;
        m = false;
        cw.b("Finish PlatformScreen.destroyed()");
    }
}

