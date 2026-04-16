/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.smsgame.MGMIDlet;
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class oa
extends an
implements bf,
ik {
    public boolean a = false;
    private ns m;
    private fc n;
    public static byte[][] b;
    public static jn c;
    public static boolean d;
    private ks o;
    public ol k;
    private or p;
    private boolean q;
    private boolean r;
    private boolean s;
    public String l;
    private boolean t = false;
    private byte u = 0;
    private boolean v = true;
    private final a w;

    public oa() {
        super(1);
        ct.b("Initializing platform");
        this.a((ba)null);
        this.a(this);
        this.f(true);
        this.a(false);
        this.d(false);
        this.w = com.mg.sq.a.s();
        try {
            this.B();
        }
        catch (OutOfMemoryError outOfMemoryError) {
            OutOfMemoryError outOfMemoryError2 = outOfMemoryError;
            outOfMemoryError.printStackTrace();
            if (com.mg.sq.a.m != null) {
                com.mg.sq.a.m.G();
            }
            System.gc();
            this.v = false;
            try {
                this.B();
            }
            catch (OutOfMemoryError outOfMemoryError3) {
                ct.a("[platformscreen contructor] outof memory");
                this.w.j(1);
            }
            catch (Exception exception) {
                outOfMemoryError2.printStackTrace();
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            this.w.j(6);
        }
        this.w.c();
        if (this.v && com.mg.sq.a.m != null) {
            this.e();
        } else {
            pc.a();
        }
        if (com.mg.sq.a.l != null && com.mg.sq.a.l.length > 0) {
            com.mg.sq.a.b(com.mg.sq.a.l);
            com.mg.sq.a.l = null;
        }
        if (ct.c()) {
            ct.b("Platform initialized");
        }
    }

    private void B() {
        this.n = new fc(1, 2, 3);
        this.n.a(this);
        this.b(1);
        this.o = ks.a();
        this.k = new ol(100, 0, "B\u1ea3n \u0110\u1ed3", this);
        this.n.c(this.k);
        this.w.v();
        if (!co.b().f()) {
            com.mg.sq.a.a(this);
        }
    }

    public final void e() {
        try {
            if (com.mg.sq.a.m != null && com.mg.sq.a.o) {
                this.n.c(com.mg.sq.a.m);
                com.mg.sq.a.m.C();
            }
            oi.w();
            if (com.mg.sq.a.o) {
                com.mg.sq.a.m.g(false);
                return;
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            oi.v();
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void f() {
        if (!d) {
            return;
        }
        if (hs.q != null || hs.p != null) {
            hs hs2 = new hs(1, go.k);
            hs2.a(this);
            hs2.a(new bd("\u0110\u00f3ng", 113));
            az az2 = null;
            hs hs3 = hs2;
            hs3.a(az2, true);
            az2 = null;
            hs3 = hs2;
            hs3.b(az2, true);
            this.w.a(hs2);
        }
    }

    protected final void a(Graphics graphics) {
        this.n.a(graphics, 0, 0);
    }

    protected final void c() {
        this.n.n();
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 125: {
                if (this.k != null) {
                    ol ol2 = this.k;
                    if (ol2.p != null && ol2.p.b() == 3) {
                        ((os)ol2.p).s();
                    }
                }
                os.l = null;
                this.w.a(false);
                com.mg.sq.a.s().a((String)null, (il)null);
                pa.a().a(go.w, this);
                return;
            }
            case 11: {
                this.w.e(-241229);
                this.r = false;
                this.C();
                if (go.k.G == hs.l) break;
                com.mg.sq.a.G();
                if (this.k == null) break;
                hs.l = go.k.G;
                return;
            }
            case 113: {
                this.w.e(-241219);
                this.u();
                if (!this.r) {
                    this.C();
                    if (go.k.G != hs.l) {
                        com.mg.sq.a.G();
                        this.o.o();
                        this.s = true;
                        if (this.k != null) {
                            hs.l = go.k.G;
                            ol ol3 = this.k;
                            if (ol3.p instanceof om) {
                                ((om)ol3.p).t();
                            }
                        }
                    }
                }
                if (this.k != null) {
                    ol ol4 = this.k;
                    if (ol4.p instanceof om) {
                        ((om)ol4.p).s();
                    }
                }
                hs.t();
                return;
            }
            case 114: {
                this.w.e(-241439);
                return;
            }
            case 12: {
                this.w.a(false);
                return;
            }
            case 10: {
                com.mg.sq.a.s().a(true, null);
                this.t = true;
                return;
            }
            case 13: {
                com.mg.sq.a.s().a(false, null);
                return;
            }
            case 122: {
                this.w.e(199199);
                return;
            }
            case 120: {
                if (go.v != null && go.v.length > 0) {
                    n2 = 0;
                    while (n2 < go.v.length) {
                        go.a(go.v[n2], go.v[n2].g);
                        ++n2;
                    }
                }
                this.w.e(-241439);
                return;
            }
            case 121: {
                this.w.a(false);
                com.mg.sq.a.s().a((String)null, (il)null);
                ks.a().f(this.l);
                return;
            }
            case 123: {
                this.w.l();
                this.o.a(true);
                return;
            }
            case 124: {
                this.w.a(false);
                this.o.a(false);
                return;
            }
            case 127: {
                this.w.a(false);
                hh hh2 = (hh)this.v();
                hh2.i(this.u);
                if (this.a) break;
                this.a = true;
                return;
            }
            case 2: {
                this.c(this.n.i);
                this.n.s();
                return;
            }
            case 1: {
                if (com.mg.sq.a.m == null || com.mg.sq.a.m.A() == null || !this.n.b(com.mg.sq.a.m.A())) break;
                this.n.a(com.mg.sq.a.m.A());
                return;
            }
            case 3: {
                if (com.mg.sq.a.m == null || com.mg.sq.a.m.z() == null || !this.n.b(com.mg.sq.a.m.z())) break;
                this.n.a(com.mg.sq.a.m.z());
                return;
            }
            case 99030: {
                if (this.k == null) break;
                this.k.j(2);
            }
        }
    }

    public final void a(String object, int n2, int n3) {
        if (this.k != null && this.k.p instanceof om) {
            ((om)this.k.p).n = null;
        }
        switch (n3) {
            case 1: {
                hs.k = go.k.J;
                hs.l = go.k.G;
                return;
            }
            case 0: {
                go.x = n2;
                go.w = object;
                if (((String)object).equals("M99")) {
                    this.f(4);
                    return;
                }
                String string = object;
                object = this;
                System.gc();
                try {
                    com.mg.sq.a.s().a((String)null, (il)null, 3000);
                    ((oa)object).w.a(5, new Object[]{string});
                    return;
                }
                catch (Exception exception) {
                    object = exception;
                    exception.printStackTrace();
                }
            }
        }
    }

    private void f(int n2) {
        try {
            this.n.r();
            System.gc();
            this.w.f(4);
            this.w.l();
        }
        catch (Exception exception) {
            ct.b("out memmory");
            ct.a(exception.toString());
        }
        System.gc();
    }

    public final void a(ns[] nsArray) {
        if (this.s) {
            if (nsArray != null && nsArray.length > 0) {
                this.n.a((String)null);
            }
            this.s = false;
            return;
        }
        if (this.q) {
            this.q = false;
            if (this.w.c(241204)) {
                hr hr2 = (hr)this.w.d(241204);
                hr2.a(nsArray);
                hr2.a((byte)0);
                return;
            }
        } else if (this.w.c(241204)) {
            hr hr3 = (hr)this.w.d(241204);
            hr3.a(nsArray);
            hr3.a((byte)0);
        }
    }

    public final void a(ns ns2, boolean bl2) {
        if (this.w.c(241204)) {
            hr hr2 = (hr)this.w.d(241204);
            hr2.a(ns2);
            if (bl2) {
                hr2.a((byte)2);
            } else {
                hr2.a((byte)1);
            }
        }
        this.w.v();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public final void a(lh lh2, String object, long l2, String string, boolean bl2, boolean bl3) {
        if (this.t) {
            return;
        }
        Object object2 = null;
        if (this.w.c(191919)) {
            object2 = (ha)this.w.d(191919);
        }
        if (object2 != null) {
            this.d(1, 13);
            return;
        }
        object2 = this.n.q();
        if (object2 != null) {
            ((fb)object2).t();
        }
        if (this.k != null) {
            boolean bl4 = bl3;
            boolean bl5 = bl2;
            String string2 = string;
            long l3 = l2;
            Object object3 = object;
            object2 = lh2;
            object = this.k;
            if (((ol)object).p != null) {
                if (((ol)object).p instanceof om) {
                    ((om)((ol)object).p).m.a();
                    v.c();
                } else if (((ol)object).p instanceof oq) {
                    ((oq)((ol)object).p).a((lh)object2, (String)object3, l3, string2, bl5, bl4);
                    return;
                }
            }
            boolean bl6 = false;
            if (bl6) {
                return;
            }
        }
        com.mg.sq.a.a(lh2, 10, 13, this, l2, string, bl2, bl3);
    }

    public final void a(String string, String object) {
        this.w.a(false);
        object = this.w.a("", (String)(object == null ? String.valueOf(string) + " kh\u00f4ng mu\u1ed1n \u0111\u00e1nh v\u1edbi b\u1ea1n." : object), "\u0110\u00f3ng", 12, 1);
        this.l = string;
        ((am)object).a(this);
        this.w.a((al)object, false);
    }

    public final void a(lh lh2, lh lh3, boolean bl2, byte[] byArray, byte[] byArray2, byte[] byArray3, int n2, byte by2, byte by3) {
        this.t = false;
        c = null;
        b = null;
        if (this.k != null) {
            this.k.a(lh2, lh3, bl2, byArray, byArray2, byArray3, by2, by3);
        }
    }

    public final void a(lh lh2, lh lh3, boolean bl2) {
        this.t = false;
        c = null;
        b = null;
        ha ha2 = null;
        if (this.w.c(191919)) {
            ha2 = (ha)this.w.d(191919);
        }
        if (ha2 == null) {
            this.w.e(-241209);
            this.w.e(-241249);
            ha2 = new ha(lh2, lh3, true, bl2, 99030, (bf)this);
            ha2.a(this.k);
            this.w.a(ha2, false);
        }
        this.w.v();
        ha2.t();
    }

    public final void d() {
        com.mg.sq.a.b(false);
    }

    public final void a(jn jn2, byte[][] byArray) {
        c = jn2;
        b = byArray;
        System.gc();
        if (this.k != null) {
            this.k.j(1);
        }
        this.w.v();
    }

    protected final void a(int n2) {
        this.n.f(n2);
    }

    protected final void e(int n2) {
        this.n.g(n2);
    }

    protected final void g(int n2, int n3) {
        this.n.e(n2, n3);
    }

    protected final void e(int n2, int n3) {
        this.n.c(n2, n3);
    }

    protected final void f(int n2, int n3) {
        this.n.f(n2, n3);
    }

    public final void g() {
        this.w.v();
        if (this.w.c(241204)) {
            ((hr)this.w.d(241204)).w();
        }
    }

    public final void t() {
        this.w.v();
        if (this.w.c(241204)) {
            ((hr)this.w.d(241204)).v();
        }
    }

    public final void u() {
        Object object;
        ct.a("[QUEST] Check complete quests");
        if (this.k != null) {
            boolean bl2;
            object = this.k;
            if (((ol)object).p instanceof oq) {
                bl2 = true;
            } else {
                if (((ol)object).p instanceof om) {
                    ((om)((ol)object).p).m.a();
                    v.c();
                }
                bl2 = false;
            }
            if (bl2) {
                return;
            }
        }
        this.m = nu.a();
        if (this.m != null) {
            ct.a("[QUEST] Has completed quest " + this.m);
            this.r = true;
            object = new hb(this.m, this.m.g);
            ((am)object).a(this);
            ((am)object).a(new bd("\u0110\u00f3ng", 11));
            az az2 = null;
            Object object2 = object;
            ((am)object2).a(az2, true);
            az2 = null;
            object2 = object;
            ((am)object2).b(az2, true);
            this.q = true;
            this.w.a((al)object);
            this.s = true;
            this.o.b(go.e);
            this.o.a(go.e, (byte)1);
            return;
        }
        object = nu.b();
        if (object != null) {
            this.c(((nt)object).a);
            nu.c();
            return;
        }
        object = nu.c();
        if (object != null) {
            this.n.a(((ns)object).a);
        }
    }

    public final void a(jo[] joArray, String joArray2) {
        if (!go.w.equals(joArray2)) {
            return;
        }
        boolean bl2 = false;
        joArray2 = joArray;
        ol ol2 = this.k;
        if (ol2.p instanceof om) {
            ((om)ol2.p).a(joArray2, false);
        }
    }

    public final void e(boolean bl2) {
        fb fb2;
        if (bl2 && (fb2 = this.n.q()) != null) {
            fb2.c(true);
        }
    }

    public static br[] a(fb fb2) {
        br br2 = new br("H\u1ed7 tr\u1ee3", 99001);
        br br3 = new br("Mua b\u00e1n", 99002);
        br br4 = new br("N\u1ea1p KEN", 99003);
        br4.a(new br[]{new br("Nh\u1eafn tin", 99004), new br("Th\u1ebb c\u00e0o \u0110T", 99005), new br("DS \u0111i\u1ec7n tho\u1ea1i", 99006)});
        if (com.mg.sq.a.m != null && com.mg.sq.a.o) {
            br2.a(new br[]{new br("Th\u00f4ng Tin #12", 99031), new br("Gi\u1edbi thi\u1ec7u", 99007), new br("H\u01b0\u1edbng d\u1eabn", 99008), new br("Th\u1ea3o lu\u1eadn #SQ", 99009), new br("H\u1ed7 tr\u1ee3", 99012), new br("\u0110\u1ed5i S\u0110T", 99010), new br("C\u00e0i \u0111\u1eb7t", 99011)});
            br3.a(new br[]{new br("C\u1eeda h\u00e0ng", 99013), new br("Ch\u1ee3 tr\u1eddi", 99034), new br("Giao d\u1ecbch", 99014), br4, new br("Mua vip", 99015), new br("Rao v\u1eb7t #sqgd", 99017)});
        } else {
            br2.a(new br[]{new br("Gi\u1edbi thi\u1ec7u", 99007), new br("H\u1ed7 tr\u1ee3", 99001), new br("\u0110\u1ed5i S\u0110T", 99010), new br("C\u00e0i \u0111\u1eb7t", 99011)});
            br3.a(new br[]{new br("C\u1eeda h\u00e0ng", 99013), new br("Ch\u1ee3 tr\u1eddi", 99034), new br("Giao d\u1ecbch", 99014)});
        }
        br br5 = new br("\u0110\u0103ng nh\u1eadp OLA", 99032);
        br br6 = new br("Nh\u00e2n V\u1eadt", 99018);
        br6.a(new br[]{new br("Th\u00f4ng tin", 99019), new br("Tuy\u1ec7t Chi\u00eau", 99020), new br("R\u01b0\u01a1ng \u0110\u1ed3", 99021), new br("Ch\u1ebf t\u1ea1o", 99033), new br(gs.c[0], 99022), new br(gs.c[1], 99023)});
        br[] brArray = null;
        if (fb2 instanceof ol) {
            fb2 = (ol)fb2;
            if (((ol)fb2).p != null) {
                brArray = ((ol)fb2).p instanceof os ? (!com.mg.sq.a.o ? (((os)((ol)fb2).p).q() == 1 ? new br[]{new br("Tr\u1edf v\u1ec1", 99028), new br("Khi\u00eau Chi\u1ebfn", 99024), br6, br3, new br("Nhi\u1ec7m V\u1ee5", 99025), br5, br2, new br("Tho\u00e1t", 99027)} : new br[]{new br("Khi\u00eau Chi\u1ebfn", 99024), br6, br3, new br("Nhi\u1ec7m V\u1ee5", 99025), br5, br2, new br("Tho\u00e1t", 99027)}) : (((os)((ol)fb2).p).q() == 1 ? new br[]{new br("Tr\u1edf v\u1ec1", 99028), new br("Khi\u00eau Chi\u1ebfn", 99024), br6, br3, new br("Nhi\u1ec7m V\u1ee5", 99025), br2, new br("Tho\u00e1t", 99027)} : new br[]{new br("Khi\u00eau Chi\u1ebfn", 99024), br6, br3, new br("Nhi\u1ec7m V\u1ee5", 99025), br2, new br("Tho\u00e1t", 99027)})) : (!com.mg.sq.a.o ? new br[]{new br("V\u1ec1 B\u1ea3n \u0111\u1ed3", 99035), new br("L\u00f4i \u0110\u00e0i", 99028), new br("Khi\u00eau Chi\u1ebfn", 99024), br6, br3, br4, new br("Nhi\u1ec7m V\u1ee5", 99025), br5, br2, new br("\u0110\u0103ng Xu\u1ea5t", 99026)} : new br[]{new br("V\u1ec1 B\u1ea3n \u0111\u1ed3", 99035), new br("L\u00f4i \u0110\u00e0i", 99028), new br("Khi\u00eau Chi\u1ebfn", 99024), br6, br3, br4, new br("Nhi\u1ec7m V\u1ee5", 99025), br2, new br("\u0110\u0103ng Xu\u1ea5t", 99026)});
            }
        }
        if (com.mg.sq.a.m != null) {
            brArray = com.mg.sq.a.m.a(brArray, 4);
        }
        return brArray;
    }

    public final boolean a(int n2, br object) {
        switch (n2) {
            case 99001: {
                return true;
            }
            case 99002: {
                return true;
            }
            case 99003: {
                com.mg.sq.a.s().N();
                return true;
            }
            case 99004: 
            case 99005: 
            case 99006: {
                if (!com.mg.sq.a.s().m(n2)) {
                    if (com.mg.sq.a.m != null) {
                        return com.mg.sq.a.m.b(((br)object).b());
                    }
                    return false;
                }
                return true;
            }
            case 99007: {
                com.mg.sq.a.D();
                return true;
            }
            case 99031: {
                if (com.mg.sq.a.m != null) {
                    com.mg.sq.a.m.g(true);
                }
                return true;
            }
            case 99009: {
                if (com.mg.sq.a.m != null) {
                    com.mg.sq.a.m.h(true);
                }
                return true;
            }
            case 99008: {
                if (com.mg.sq.a.m != null) {
                    com.mg.sq.a.m.F();
                }
                return true;
            }
            case 99010: {
                com.mg.sq.a.s().S();
                return true;
            }
            case 99011: {
                com.mg.sq.a.F();
                return true;
            }
            case 99012: {
                MGMIDlet.d();
                MGMIDlet.b("1900588883");
                return true;
            }
            case 99013: {
                com.mg.sq.a.s().H();
                return true;
            }
            case 99034: {
                com.mg.sq.a.s().I();
                return true;
            }
            case 99014: {
                com.mg.sq.a.e("Nh\u1eadp t\u00ean ng\u01b0\u1eddi c\u1ea7n giao d\u1ecbch");
                return true;
            }
            case 99015: {
                du.a().b((short)2412);
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 99017: {
                if (com.mg.sq.a.m != null) {
                    com.mg.sq.a.m.E();
                }
                return true;
            }
            case 99018: {
                return true;
            }
            case 99019: {
                com.mg.sq.a.G();
                return true;
            }
            case 99020: {
                oa oa2 = this;
                object = new ib();
                oa2.w.a((al)object);
                return true;
            }
            case 99021: {
                this.v();
                return true;
            }
            case 99033: {
                this.w.a(false);
                com.mg.sq.a.s().J();
                return true;
            }
            case 99022: {
                this.w.Q();
                return true;
            }
            case 99023: {
                this.w.R();
                return true;
            }
            case 99024: {
                com.mg.sq.a.d("");
                return true;
            }
            case 99025: {
                this.c((String)null);
                return true;
            }
            case 99026: {
                com.mg.sq.a.u();
                return true;
            }
            case 99027: {
                al al2 = this.w.a("Ch\u00fa \u00fd", "B\u1ea1n mu\u1ed1n tho\u00e1t kh\u1ecfi L\u00f4i \u0110\u00e0i?", "C\u00f3", 125, "Kh\u00f4ng", 12, 1);
                al2.a(this);
                this.w.a(al2, false);
                return true;
            }
            case 99028: {
                this.o.r();
                com.mg.sq.a.s().a((String)null, (il)null);
                return true;
            }
            case 11399: {
                if (com.mg.sq.a.m == null) break;
                com.mg.sq.a.m.b(((br)object).b());
                break;
            }
            case 99032: {
                ob.d();
                break;
            }
            case 99035: {
                go.w = "M99";
                go.x = 0;
                this.f(4);
            }
        }
        return false;
    }

    public final void a(String string, boolean bl2, int n2, boolean bl3, boolean bl4) {
        this.w.f(string);
        this.o.a(string, "", false, 0L, false, false);
    }

    public final al v() {
        if (this.w.c(241202)) {
            this.w.e(241202);
        }
        hh hh2 = new hh(null, null);
        this.w.a(hh2);
        return hh2;
    }

    private void c(String string) {
        hr hr2 = new hr();
        hr2.a((Object)this.k);
        this.w.a(hr2);
        if (string == null) {
            this.o.o();
        } else {
            this.o.o(string);
        }
        com.mg.sq.a.s().a((String)null, (il)null);
    }

    private void C() {
        if (go.v != null && go.v.length > 0) {
            hl hl2 = new hl("B\u1ea1n nh\u1eadn \u0111\u01b0\u1ee3c: ", go.v);
            hl2.a(this);
            az az2 = null;
            hl hl3 = hl2;
            hl3.a(az2, true);
            az2 = null;
            hl3 = hl2;
            hl3.b(az2, true);
            ((am)hl2).a(new bd("\u0110\u00f3ng", 120));
            this.w.a(hl2);
        }
    }

    public final void a(String object) {
        object = this.w.a("", String.valueOf(this.l) + " \u0111ang \u0111\u00e1nh nhau v\u1edbi " + (String)object + ". Mu\u1ed1n xem tr\u1eadn \u0111\u00e1nh kh\u00f4ng?", "Xem", 121, "\u0110\u00f3ng", 12, 1);
        ((am)object).a(this);
        this.w.a((al)object, false);
    }

    public final void b(String object) {
        if (this.k != null && this.k.p instanceof oq) {
            this.o.a(false);
            return;
        }
        object = this.w.a("", String.valueOf(object) + " mu\u1ed1n giao d\u1ecbch v\u1edbi b\u1ea1n. B\u1ea1n c\u00f3 \u0111\u1ed3ng \u00fd kh\u00f4ng?", "\u0110\u1ed3ng \u00fd", 123, "Kh\u00f4ng", 124, 1);
        ((am)object).b(241207);
        ((am)object).a(this);
        this.w.a((al)object, false);
    }

    public final boolean w() {
        int n2 = 0;
        while (n2 < go.k.D.length) {
            int n3 = 0;
            while (n3 < go.l.length) {
                if (go.k.D[n2].c.equals(go.l[n3].c) && go.l[n3].p == 0 && go.l[n3].p < go.l[n3].q) {
                    if (!this.a) {
                        al al2 = this.w.a("", "Trang b\u1ecb \u0111\u00e3 h\u01b0 h\u1ecfng s\u1ebd m\u1ea5t t\u00e1c d\u1ee5ng. B\u1ea1n c\u00f3 mu\u1ed1n s\u1eeda ch\u1eefa kh\u00f4ng? ", "\u0110\u1ed3ng \u00fd", 127, "Kh\u00f4ng", 12, 1);
                        al2.a(this);
                        this.w.a(al2, false);
                        this.u = go.l[n3].e;
                    }
                    return true;
                }
                ++n3;
            }
            ++n2;
        }
        return false;
    }

    public final void x() {
        if (this.k != null) {
            ol ol2 = this.k;
            if (ol2.p != null && ol2.p.b() == 1) {
                ((om)ol2.p).u();
            }
        }
        this.w.l();
    }

    public final void a(int n2, dh[] dhArray) {
        if (dhArray == null || dhArray.length <= 0) {
            al al2 = this.w.a("Ch\u00fa \u00fd", "T\u00ednh n\u0103ng \u0111ang \u0111\u01b0\u1ee3c b\u1ea3o tr\u00ec, vui l\u00f2ng th\u1eed l\u1ea1i sau.", "\u0110\u00f3ng", 12, 1);
            al2.a(this);
            this.w.a(al2, false);
            return;
        }
        if (this.p == null) {
            this.p = new or(this);
        }
        this.p.a(n2, dhArray);
        if (!this.n.b(this.p)) {
            this.n.c(this.p);
        }
        this.n.a(this.p);
    }

    public final void y() {
        this.t = false;
    }

    public final void z() {
        this.n.d(this.p);
        this.p = null;
        if (this.k != null) {
            this.n.a(this.k);
        }
    }

    public final void a(lr[] object) {
        if (this.k != null) {
            lr[] lrArray = object;
            object = this.k;
            this.k.t = false;
            if (((ol)object).p == null || ((ol)object).p.b() != 3) {
                ((ol)object).j(3);
                os.l = lrArray;
            } else if (((ol)object).p.b() == 3) {
                ((os)((ol)object).p).a(lrArray);
            }
        }
        this.w.v();
    }

    public final void b(lr[] object) {
        if (this.k != null) {
            lr[] lrArray = object;
            object = this.k;
            if (object.p.b() == 3) {
                ((os)object.p).b(lrArray);
            }
        }
        this.w.v();
    }

    public final void A() {
        this.t = false;
    }

    protected final void s() {
        this.k.A();
        this.k = null;
        this.n.r();
        this.n = null;
        this.p = null;
        this.i = null;
        this.m = null;
        this.o = null;
        pa.a().d();
        c = null;
        b = null;
        go.v = null;
        go.u = null;
        go.v = null;
        d = false;
        ct.b("Finish PlatformScreen.destroyed()");
    }
}

