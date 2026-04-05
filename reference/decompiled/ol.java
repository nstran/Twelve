/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.smsgame.MGMIDlet;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class ol
extends fb
implements bj,
bk,
bq,
bt {
    private ba q;
    private bc r;
    private int s = 0;
    private ex t;
    private String[] u;
    private String v;
    private String w;
    private int x;
    private int y;
    private boolean z;
    private long A = 0L;
    private String[][] B;
    public a o;
    public boolean p = false;
    private int C;
    private long D = -1L;
    private ba E = null;
    private bc F = null;
    private a G = null;
    private String H = null;
    private String I;
    private int J;
    private boolean K = false;
    private int L = -1987;
    private ft M;
    private int N = 0;
    private long O;
    private String[] P = new String[]{"B\u1ea1n \u0111ang l\u00e0m g\u00ec?", "B\u1ea1n \u0111ang ngh\u0129 g\u00ec?", "H\u00f4m nay c\u00f3 g\u00ec HOT?", "Ngo\u00e0i tr\u1eddi \u0111ang...?", "B\u1ea1n \u0111ang xem g\u00ec?", "H\u00f4m nay c\u00f3 g\u00ec vui?", "N\u1ebfu c\u00f3 1 \u0111i\u1ec1u \u01b0\u1edbc..."};

    public ol(int n2, int n3, String string) {
        super(107, 5, string);
        this.a(0, 0, z.r, z.s);
        this.u = ca.a("Ola Me - Loan tin t\u1ee9c th\u1eddi. B\u1ea1n c\u00f3 th\u1ec3 l\u1eafng nghe v\u00e0 chia s\u1ebb t\u00e2m s\u1ef1 v\u1edbi nh\u1eefng ng\u01b0\u1eddi \u0111\u01b0\u1ee3c quan t\u00e2m. Phi\u00ean b\u1ea3n web t\u1ea1i http://me.ola.vn ", z.r, ca.d);
        this.t = new ex("Xem ME", -2);
        this.t.a((z.r - 100) / 2, 10 + ca.d.a() + 10 + 10 + ca.d.a() * this.u.length, 100, 20);
        this.a();
        this.a(new be());
        this.j(0);
        this.B = pa.E();
        this.o = new a();
        this.a(new ga(-1, 0));
        this.c(com.mg.sq.a.l);
        this.b(new ga(-2, 1));
        this.a(this);
    }

    private void a() {
        this.q = new ba();
        this.q.a(this);
        this.q.a(this);
        this.q.h(5);
        this.r = new bc(0);
        this.r.a(1, 0, z.r - 2, z.s - be.a);
        this.r.b(this.q);
    }

    private hc a(String object) {
        object = com.mg.sq.a.a((String)object, null, "Xong", -5, "H\u1ee7y", -3, -9, this);
        ((aq)object).b(-989858);
        this.M = (ft)((hc)object).e(4);
        return object;
    }

    private void v() {
        this.E = null;
        this.F = null;
        this.G = null;
        this.H = null;
        this.I = null;
    }

    private void w() {
        this.q = this.E;
        this.r = this.F;
        this.o = this.G;
        this.y = this.J;
        this.v = this.H;
        this.w = this.I;
        if (this.w != null) {
            ca.d.c(true);
            this.x = ca.d.a(this.w) + 6;
            ca.d.c();
        }
        this.v();
        this.c(true);
    }

    public final void a(eg eg2, eg[] egArray) {
        eg[] egArray2;
        if (!this.A()) {
            egArray2 = this;
            this.E = egArray2.q;
            egArray2.F = egArray2.r;
            egArray2.J = egArray2.y;
            egArray2.H = egArray2.v;
            egArray2.I = egArray2.w;
            egArray2.G = egArray2.o;
            super.a();
        }
        if (this.v == null || !this.v.equals("B\u00ecnh lu\u1eadn") || this.z) {
            this.o = new a();
            if (egArray == null) {
                egArray = new eg[]{eg2};
            } else {
                egArray2 = new eg[egArray.length + 1];
                System.arraycopy(egArray, 0, egArray2, 1, egArray.length);
                egArray2[0] = eg2;
                egArray = egArray2;
            }
        }
        super.a("B\u00ecnh lu\u1eadn", (short)3, egArray, true);
    }

    public final void a(String string, short s2, eg[] egArray) {
        this.a(string, s2, egArray, false);
        if (string != null && string.equals("#12")) {
            if (egArray == null || egArray.length == 0) {
                return;
            }
            if (egArray.length > 1) {
                if (egArray[0].e() < egArray[1].e()) {
                    this.O = egArray[1].e();
                }
            } else {
                this.O = egArray[0].e();
            }
            if (!pa.d(this.O)) {
                boolean bl2 = true;
                ol ol2 = this;
                if (ol2.b != null) {
                    fc fc2 = (fc)ol2.l();
                    fc2.e(ol2);
                    ol2.c(true);
                }
            }
        }
    }

    private void a(String object, short s2, eg[] egArray, boolean bl2) {
        com.mg.sq.a.t();
        if (egArray == null || egArray.length == 0) {
            return;
        }
        if (!bl2) {
            this.v();
        }
        Object object2 = new do[egArray.length];
        int n2 = 0;
        while (n2 < ((do[])object2).length) {
            object2[n2] = new do(egArray[n2], this.q.e() - 4);
            object2[n2].a(egArray[n2].d());
            ++n2;
        }
        short s3 = s2;
        String string = object;
        object = this;
        try {
            Object object3;
            int n3;
            n2 = ((ol)object).q.s();
            ((ol)object).y = s3;
            try {
                if (((ol)object).v == null && string != null || ((ol)object).v != null && string == null || ((ol)object).v != null && !((ol)object).v.equals(string) || ((ol)object).z) {
                    ((ol)object).q.q();
                    ((ol)object).o.a();
                    if (com.mg.sq.a.k != null) {
                        com.mg.sq.a.k.p.a();
                    }
                    n2 = 1;
                    ((ol)object).z = false;
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
            ((ol)object).v = string;
            if (((ol)object).v == null) {
                ((ol)object).w = "Trang ch\u1ee7";
            } else {
                ((ol)object).w = string;
                og og2 = com.mg.sq.a.k;
                if (og2 != null && og2.r != null) {
                    n3 = 0;
                    while (n3 < og2.r.length) {
                        if (og2.r[n3].equals(string)) {
                            ((ol)object).w = og2.s[n3];
                            break;
                        }
                        ++n3;
                    }
                }
            }
            if (((ol)object).w != null) {
                ca.d.c(true);
                ((ol)object).x = ca.d.a(((ol)object).w) + 6;
                ca.d.c();
            }
            boolean bl3 = super.A();
            if (((ol)object).q.a() > 0) {
                if (((ol)object).K) {
                    if (bl3) {
                        ((ol)object).q.a((Object[])object2, 2);
                    } else {
                        ((ol)object).q.a((Object[])object2, 1);
                    }
                } else {
                    ((ol)object).q.a((Object[])object2, ((ol)object).q.a() - 1);
                }
            } else {
                if (bl3) {
                    object2[0].n = true;
                }
                ((ol)object).q.a(new dr("Xem m\u1edbi nh\u1ea5t", null));
                ((ol)object).q.a((Object[])object2);
                ((ol)object).q.a(new dr("Xem th\u00eam", null));
            }
            n3 = 0;
            while (n3 < ((Object[])object2).length) {
                if (bl3 && ((ol)object).D >= 0L && ((do)object2[n3]).e == ((ol)object).D && !((do)object2[n3]).n) {
                    ((do)object2[n3]).o = true;
                }
                a a2 = ((ol)object).o;
                object3 = object2[n3];
                a a3 = ((do)object3).a.d();
                if (((do)object3).d != null && ((do)object3).d.j == -10) {
                    a2.a(((do)object3).d.i);
                }
                int n4 = 0;
                while (n4 < a3.d()) {
                    q q2 = ((do)object3).a.a(n4);
                    if (q2.c() == 1) {
                        q2 = (lm)q2;
                        if (((lm)q2).j == -10) {
                            a2.a(((lm)q2).i);
                        }
                    }
                    ++n4;
                }
                ++n3;
            }
            dr dr2 = (dr)((ol)object).q.i(((ol)object).q.a() - 1);
            object2 = (do)((ol)object).q.i(1);
            object3 = (do)((ol)object).q.i(((ol)object).q.a() - 2);
            dr2.b = new long[]{((do)object3).e, ((do)object3).e};
            dr2 = (dr)((ol)object).q.i(0);
            if (bl3 && ((ol)object).q.a() >= 4) {
                object2 = (do)((ol)object).q.i(2);
            }
            dr2.b = new long[]{object2.e, -object2.e};
            if (n2 > 1) {
                --n2;
            }
            ((ol)object).q.k(n2);
            super.j(4);
            if (!bl3) {
                ((ol)object).B = pa.a(((ol)object).B, ((ol)object).w, string);
                return;
            }
        }
        catch (Exception exception) {
            Exception exception3 = exception;
            exception.printStackTrace();
        }
    }

    private boolean A() {
        return this.v != null && this.v.equals("B\u00ecnh lu\u1eadn");
    }

    private void j(int n2) {
        this.s = n2;
        if (n2 == 0) {
            this.t.d(true);
        }
        this.c(true);
    }

    private void B() {
        long l2 = 0L;
        String string = null;
        ol ol2 = this;
        ol2.a(string, l2, true);
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (bl2) {
            this.r.c(true);
        }
    }

    public final boolean f(int n2) {
        if (this.s == 0) {
            if (n2 == 95) {
                this.B();
                return true;
            }
        } else {
            if (n2 == 153) {
                this.D();
                return true;
            }
            if (n2 == 148) {
                this.B();
                return true;
            }
            if (n2 == this.L) {
                fo fo2;
                boolean bl2 = false;
                if (this.q.t() instanceof do) {
                    fo2 = (fo)this.q.u();
                    if (n2 == 99) {
                        bl2 = fo2.r();
                    } else if (n2 == 98) {
                        bl2 = fo2.q();
                    }
                    if (bl2) {
                        fo2.a();
                        return true;
                    }
                }
                if (n2 == 99) {
                    bl2 = this.q.x();
                } else if (n2 == 98) {
                    bl2 = this.q.y();
                }
                if (bl2) {
                    if (this.q.t() instanceof do) {
                        fo2 = (fo)this.q.u();
                        fo2.a();
                    }
                    return true;
                }
            } else {
                this.L = n2;
            }
            return this.r.f(n2);
        }
        return false;
    }

    public final boolean g(int n2) {
        this.L = -1987;
        return true;
    }

    public final boolean c(int n2, int n3) {
        if (this.s == 0) {
            if (this.t.h().b(n2, n3)) {
                this.t.d(true);
                this.f(95);
                return true;
            }
        } else {
            return this.r.c(n2, n3);
        }
        return super.c(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        if (this.s != 0) {
            return this.r.e(n2, n3);
        }
        return super.e(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        if (this.s != 0) {
            return this.r.f(n2, n3);
        }
        return super.f(n2, n3);
    }

    public final void n() {
        if (this.s != 0) {
            this.r.n();
        }
    }

    private static void a(Graphics graphics) {
        graphics.setColor(z.af);
        graphics.fillRect(0, 0, z.r, z.s - be.a);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.s == 0) {
            if (this.c) {
                ol.a(graphics);
                ca.d.c(true);
                ca.d.a(graphics, "Gi\u1edbi thi\u1ec7u", z.r >>> 1, 10, 1);
                ca.d.c();
                n2 = 10 + (10 + ca.d.a());
                ca.a(graphics, ca.d, this.u, 0, n2, z.r, z.s, 1);
                this.t.a(graphics, this.c(), this.d());
                this.c(false);
                return;
            }
        } else if (this.r.k()) {
            ol.a(graphics);
            this.r.a(graphics, this.c(), this.d());
            if (this.w != null) {
                n2 = z.r - this.x;
                oz.a(graphics, n2, 0, this.x, 18, 0xEF0000, 16177368);
                ca.d.c(true);
                ca.d.a(graphics, this.w, n2 + 3, 2, 0);
                ca.d.c();
            }
        }
    }

    protected final au y() {
        return this;
    }

    public final void u() {
        this.p = false;
        this.c(true);
        if (this.v != null && this.v.equals("#12")) {
            pa.c(this.O);
        }
    }

    public final void x() {
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1: {
                ol ol2 = this;
                bv bv2 = new bv();
                bv2.a(new ga(0, 2));
                bv2.b(new ga(1, 3));
                og og2 = com.mg.sq.a.k;
                if (og2 != null) {
                    Object[] objectArray;
                    if (ol2.s == 4) {
                        Object[] objectArray2;
                        if (ol2.A()) {
                            objectArray2 = new bu[]{new bu("G\u1eedi Me", 1070007), new bu("Tr\u1edf v\u1ec1", 1070008), new bu("\u0110\u00f3ng", 1070009)};
                        } else {
                            int n4;
                            int n5;
                            Object[] objectArray3;
                            objectArray2 = new bu[]{new bu("G\u1eedi Me", 1070007), new bu("Xem ME", 1070002), new bu("C\u1eadp nh\u1eadt", 1070010), new bu("\u0110\u00f3ng", 1070009)};
                            if (ol2.B != null && ol2.B.length > 0) {
                                objectArray = new bu[objectArray2.length + 1];
                                System.arraycopy(objectArray2, 0, objectArray, 0, 2);
                                objectArray[2] = new bu("Nh\u1eadt k\u00fd", 1070011);
                                objectArray3 = new bu[ol2.B.length];
                                n5 = 0;
                                while (n5 < objectArray3.length) {
                                    objectArray3[n5] = (int)new bu(ol2.B[n5][0], 1070011);
                                    ++n5;
                                }
                                ((bu)objectArray[2]).a((bu[])objectArray3);
                                System.arraycopy(objectArray2, 2, objectArray, 3, 2);
                                objectArray2 = objectArray;
                            }
                            objectArray = new String[2 + (og2.r != null ? og2.r.length : 0)];
                            objectArray3 = new int[objectArray.length];
                            objectArray[0] = "Trang ch\u1ee7";
                            objectArray3[0] = 1070013;
                            objectArray[objectArray.length - 1] = "Trang kh\u00e1c";
                            objectArray3[objectArray3.length - 1] = 1070014;
                            if (og2.r != null) {
                                n5 = 0;
                                int n6 = 1;
                                while (n6 < objectArray.length - 1) {
                                    objectArray[n6] = og2.s[n5++];
                                    objectArray3[n6] = 1070019;
                                    ++n6;
                                }
                            }
                            String[] stringArray = new String[objectArray.length - 1];
                            int[] nArray = new int[objectArray3.length - 1];
                            int n7 = -1;
                            if (ol2.v == null) {
                                n7 = 0;
                            } else if (og2.r != null) {
                                int n8 = 0;
                                n4 = 1;
                                while (n4 < objectArray.length - 1) {
                                    if (ol2.v.equals(og2.r[n8++])) {
                                        n7 = n4;
                                        break;
                                    }
                                    ++n4;
                                }
                            }
                            if (n7 >= 0) {
                                System.arraycopy(objectArray, 0, stringArray, 0, n7);
                                System.arraycopy(objectArray, n7 + 1, stringArray, n7, stringArray.length - n7);
                                System.arraycopy(objectArray3, 0, nArray, 0, n7);
                                System.arraycopy(objectArray3, n7 + 1, nArray, n7, nArray.length - n7);
                                objectArray = stringArray;
                                objectArray3 = nArray;
                            }
                            bu[] buArray = new bu[objectArray.length];
                            n4 = 0;
                            while (n4 < buArray.length) {
                                buArray[n4] = new bu((String)objectArray[n4], objectArray3[n4]);
                                ++n4;
                            }
                            ((bu)objectArray2[1]).a(buArray);
                            if (!(ol2.y != 1 && ol2.y != 0 || ol2.v == null || (ol2.v.length() < 3 || ol2.v.equals(gr.e)) && ol2.v.charAt(0) != '#')) {
                                bu[] buArray2 = new bu[objectArray2.length + 1];
                                System.arraycopy(objectArray2, 0, buArray2, 1, objectArray2.length);
                                buArray2[0] = ol2.v.charAt(0) == '#' ? (ol2.y == 1 ? new bu("R\u1eddi clan", 1070015) : new bu("Gia nh\u1eadp", 1070016)) : (ol2.y == 1 ? new bu("Ng\u1eebng quan t\u00e2m", 1070017) : new bu("Quan t\u00e2m", 1070018));
                                objectArray2 = buArray2;
                            }
                            if (com.mg.sq.a.k != null) {
                                objectArray2 = com.mg.sq.a.k.a((bu[])objectArray2, objectArray2.length - 2);
                                objectArray2 = og.b((bu[])objectArray2, objectArray2.length - 2);
                            }
                        }
                        bv2.a((bu[])objectArray2);
                    } else {
                        int n9;
                        bu[] buArray = new bu[]{new bu("G\u1eedi Me", 1070007), new bu("Xem ME", 1070002), new bu("\u0110\u00f3ng", 1070009)};
                        objectArray = new String[2 + (og2.r != null ? og2.r.length : 0)];
                        int[] nArray = new int[objectArray.length];
                        objectArray[0] = "Trang ch\u1ee7";
                        nArray[0] = 1070013;
                        objectArray[objectArray.length - 1] = "Trang kh\u00e1c";
                        nArray[nArray.length - 1] = 1070014;
                        if (og2.r != null) {
                            int n10 = 0;
                            n9 = 1;
                            while (n9 < objectArray.length - 1) {
                                objectArray[n9] = og2.s[n10++];
                                nArray[n9] = 1070019;
                                ++n9;
                            }
                        }
                        bu[] buArray3 = new bu[objectArray.length];
                        n9 = 0;
                        while (n9 < buArray3.length) {
                            buArray3[n9] = new bu((String)objectArray[n9], nArray[n9]);
                            ++n9;
                        }
                        buArray[1].a(buArray3);
                        if (com.mg.sq.a.k != null) {
                            buArray = com.mg.sq.a.k.a(buArray, buArray.length - 1);
                            buArray = og.b(buArray, buArray.length - 1);
                        }
                        bv2.a(buArray);
                    }
                    int n11 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                    bv2.a_(-n11, z.s);
                    bv2.d(0, z.s - be.a - bv2.f());
                    bv2.a(ol2);
                    ol2.a(bv2);
                }
                return;
            }
            case 1: {
                this.t();
                return;
            }
            case 0: {
                this.l.f(95);
                return;
            }
            case -4: {
                Object object = com.mg.sq.a.k(241220).trim();
                if (!l.b((String)object)) {
                    long l2 = 0L;
                    String string = object;
                    object = this;
                    ((ol)object).a(string, l2, true);
                }
                com.mg.sq.a.q().a(241220, false);
                return;
            }
            case -5: {
                Object object = com.mg.sq.a.k(-989858);
                n3 = this.M.r() + 1;
                if (!l.b((String)object)) {
                    short s2 = (short)n3;
                    String string = object;
                    object = this;
                    ((ol)object).a(string, ((ol)object).A, (int)s2);
                    ((ol)object).A = 0L;
                }
                com.mg.sq.a.q().a(-989858, false);
                return;
            }
            case -3: {
                com.mg.sq.a.q().a(241220, false);
                com.mg.sq.a.q().a(-989858, false);
                return;
            }
            case -7: {
                ++this.C;
                this.C();
                com.mg.sq.a.q().a(false);
                return;
            }
            case -8: {
                com.mg.sq.a.q().a(false);
                return;
            }
            case -9: {
                hn hn2 = new hn(2);
                hn2.e(this.M.r());
                hn2.f(this.N);
                hn2.w();
                hn2.a(this);
                ak.b().a(hn2, false);
                return;
            }
            case -8882: {
                ak.b().e(999999223);
                return;
            }
            case -8881: {
                hn hn3 = (hn)com.mg.sq.a.q().d(999999223);
                if (this.M == null || hn3 == null || ov.f == null) break;
                this.N = hn3.v();
                this.M.a(ov.f);
                this.M.h(this.N);
                this.M.b(ov.a[this.N], ov.b[this.N], ov.c[this.N], ov.d[this.N]);
                hn3.e(this.N);
                ak.b().e(999999223);
                return;
            }
            case -8883: {
                hn hn4 = (hn)com.mg.sq.a.q().d(999999223);
                if (hn4 != null) {
                    hn4.u();
                    this.M.a((Image)null);
                    this.M.h(-1);
                }
                ak.b().e(999999223);
                return;
            }
            default: {
                ((fc)this.b).a();
            }
        }
    }

    public final void a(String string, long l2, int n2) {
        if (this.s != 0 && this.q.a() > 0 && (this.v == null || l.a(this.v, gr.e) || l2 > 0L && this.v.equals("B\u00ecnh lu\u1eadn"))) {
            Object object = new eg();
            ((eg)object).b(-1L);
            ((eg)object).b(string);
            ((eg)object).a(gr.e);
            ((eg)object).a((short)n2);
            object = new do((eg)object, this.q.e() - 4);
            ((do)object).a(0L);
            if (l2 > 0L && this.A()) {
                this.q.a(object, 2);
            } else {
                this.q.a(object, 1);
            }
        }
        if (l2 > 0L) {
            dv.a().a(string, l2, null, (short)n2);
        } else {
            dv.a().b(string, null, (short)n2);
        }
        com.mg.sq.a.C();
    }

    private void a(long l2, long l3, boolean bl2) {
        this.z = bl2;
        com.mg.sq.a.a(null, null, 375);
        dv.a().a(l2, l3, (short)this.i);
    }

    public final void a(String string, long l2) {
        this.a(string, l2, true);
    }

    private void a(String string, long l2, boolean bl2) {
        this.z = bl2;
        com.mg.sq.a.a(null, null, 375);
        dv.a().a(string, l2, (short)this.i);
    }

    private void C() {
        do do_ = (do)this.q.t();
        this.q.j(this.q.s());
        dv.a().a(do_.e);
    }

    private void f(boolean n2) {
        Object object;
        do do_ = (do)this.q.t();
        hc hc2 = this.a("Tr\u1ea3 l\u1eddi");
        String string = "@" + do_.c + " ";
        int n3 = string.length() - 1;
        if (n2 != 0) {
            n2 = 0;
            while (n2 < do_.m.length) {
                object = do_.a.a(do_.m[n2]);
                string = String.valueOf(string) + do_.a.b(((q)object).b(), do_.m[n2]) + " ";
                ++n2;
            }
        }
        if (this.v != null && this.v.charAt(0) == '#') {
            string = String.valueOf(string) + this.v + " ";
        }
        n2 = string.length() - n3;
        this.A = do_.e;
        object = (ff)hc2.e(1);
        ((ff)object).c(string);
        ((ff)object).g(n3, n2);
        ak.b().a(hc2);
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        n2 = this.l.b();
        object = (bu)object;
        Object object2 = null;
        Object object3 = null;
        Object object4 = null;
        int n4 = 0;
        if (n2 == 1 && (object2 = (do)this.q.t()) != null) {
            object3 = ((do)object2).a();
            object4 = object2;
            n4 = do.b(((do)object4).h);
            object4 = ((r)object3).b(((r)object3).a(n4).b(), n4);
            if (object4 == null || ((String)object4).length() <= 0) {
                object4 = ((do)object2).c;
            }
            if (((String)object4).charAt(0) == '@') {
                object4 = ((String)object4).substring(1);
            }
        }
        Object object5 = null;
        switch (n3) {
            case 1070000: {
                object5 = ((r)object3).b(((r)object3).a(n4).b(), n4);
                long l2 = 0L;
                object3 = object5;
                object4 = this;
                ((ol)object4).a((String)object3, l2, true);
                break;
            }
            case 1070020: {
                if (((bu)object).b().equals("Xem \u1ea2nh")) {
                    if (com.mg.sq.a.k == null) break;
                    object5 = (lm)((do)object2).a.a(((do)object2).h);
                    object4 = com.mg.sq.a.k;
                    ((og)object4).p.a(((lm)object5).i.a());
                    break;
                }
                if (!((bu)object).b().equals("Xem video") && !((bu)object).b().equals("Nghe audio")) break;
                com.mg.sq.a.A();
                break;
            }
            case 1070001: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.c((String)object4);
                break;
            }
            case 1070002: {
                long l3 = 0L;
                object3 = object4;
                object4 = this;
                ((ol)object4).a((String)object3, l3, true);
                break;
            }
            case 1070003: {
                object5 = ((r)object3).b(((r)object3).a(n4).b(), n4);
                ff.i = object5;
                break;
            }
            case 1070004: {
                object5 = ((r)object3).b(((r)object3).a(n4).b(), n4);
                if (com.mg.sq.a.k == null) break;
                Object object6 = object5;
                object3 = ((do)object2).c;
                object4 = com.mg.sq.a.k;
                ((og)object4).a((String)object3, (String)object6, true);
                break;
            }
            case 1070005: {
                object5 = (lm)((r)object3).a(n4);
                com.mg.sq.a.d("Nh\u1eadp nick mu\u1ed1n g\u1eedi", ((lm)object5).i.a());
                break;
            }
            case 1070006: {
                object5 = ((do)object2).a.i();
                if (((do)object2).d != null) {
                    object5 = String.valueOf(object5) + ((do)object2).d.a();
                }
                ff.i = object5;
                break;
            }
            case 1070007: {
                object5 = this.a(this.P[cy.a(this.P.length)]);
                ff ff2 = (ff)((hc)object5).e(1);
                if (!l.b(this.v)) {
                    if (this.v.charAt(0) == '#') {
                        ff2.c(String.valueOf(this.v) + " ");
                    } else if (!this.A() && this.v.length() > 3) {
                        ff2.c("@" + this.v + " ");
                    }
                }
                this.A = 0L;
                ak.b().a((ap)object5);
                break;
            }
            case 1070008: {
                this.w();
                break;
            }
            case 1070009: {
                this.p = true;
                if (this.b == null) break;
                ((fc)this.b).d(this);
                break;
            }
            case 1070010: {
                this.D();
                break;
            }
            case 1070011: {
                int n5 = this.l.q();
                if (n5 >= 0) break;
                n5 = Math.abs(n5);
                n5 &= 0xFF;
                object2 = this.l.r();
                if (!((bu)object2).b().equals("Nh\u1eadt k\u00fd")) break;
                long l4 = 0L;
                object3 = this.B[n5][1];
                object4 = this;
                ((ol)object4).a((String)object3, l4, true);
                this.t();
                return;
            }
            case 1070013: {
                this.B();
                break;
            }
            case 1070014: {
                al al2 = ak.b();
                object5 = this;
                hc hc2 = com.mg.sq.a.a("Nh\u1eadp nick b\u1ea1n mu\u1ed1n xem Me", null, "Xong", -4, "H\u1ee7y", -3);
                hc2.a((bj)object5);
                al2.a(hc2);
                break;
            }
            case 1070015: {
                dv.a().e(this.v);
                this.y = 0;
                break;
            }
            case 1070016: {
                dv.a().d(this.v);
                this.y = 1;
                break;
            }
            case 1070017: {
                dv.a().e(this.v);
                this.y = 0;
                break;
            }
            case 1070018: {
                dv.a().d(this.v);
                this.y = 1;
                break;
            }
            case 1070019: {
                boolean bl2;
                object5 = com.mg.sq.a.k;
                if (((og)object5).r != null) {
                    int n6 = 0;
                    while (n6 < ((og)object5).r.length) {
                        if (((og)object5).s[n6].equals(((bu)object).b())) {
                            long l5 = 0L;
                            object3 = ((og)object5).r[n6];
                            object4 = this;
                            ((ol)object4).a((String)object3, l5, true);
                        }
                        ++n6;
                    }
                }
                if (com.mg.sq.a.k == null) break;
                int n7 = n3;
                if (n7 == 99031) {
                    if (com.mg.sq.a.k != null) {
                        com.mg.sq.a.k.i(true);
                    }
                    bl2 = true;
                } else if (n7 == 99009) {
                    if (com.mg.sq.a.k != null) {
                        com.mg.sq.a.k.j(true);
                    }
                    bl2 = true;
                } else if (n7 == 99017) {
                    if (com.mg.sq.a.k != null) {
                        com.mg.sq.a.k.E();
                    }
                    bl2 = true;
                } else if (n7 == 99012) {
                    MGMIDlet.b("1900588883");
                    bl2 = true;
                } else if (n7 == 99011) {
                    com.mg.sq.a.D();
                    bl2 = true;
                } else if (n7 == 99008) {
                    if (com.mg.sq.a.k != null) {
                        com.mg.sq.a.k.F();
                    }
                    bl2 = true;
                } else if (n7 == 99007) {
                    com.mg.sq.a.B();
                    bl2 = true;
                } else {
                    bl2 = false;
                }
                if (bl2) break;
                com.mg.sq.a.k.b(((bu)object).b());
                break;
            }
            case 11399: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.b(((bu)object).b());
                break;
            }
            default: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.j(n3);
            }
        }
        this.t();
    }

    private void D() {
        if (this.A()) {
            Object object = (dr)this.q.i(this.q.a() - 1);
            object = (long[])((dr)object).b;
            long l2 = 0L;
            Object object2 = object[0];
            object = this;
            ((ol)object).a((long)object2, 0L, true);
            return;
        }
        long l3 = 0L;
        String string = this.v;
        ol ol2 = this;
        ol2.a(string, l3, true);
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) instanceof dr) {
            object = new fl(((dr)object).a, -10);
            ((au)object).a(0, 0, this.e() - 2, 20);
            return object;
        }
        return new fo((do)object, this.q.e(), (n2 & 1) == 1);
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    public final void b(au var1_1, int var2_2) {
        block34: {
            block38: {
                block37: {
                    block35: {
                        block36: {
                            if (this.q.a() <= 0) break block34;
                            if (var2_2 != this.q.a() - 1) break block35;
                            this.K = false;
                            if (!this.A()) break block36;
                            var1_1 = this;
                            if (var1_1.q.a() > 0) {
                                var2_3 = (dr)var1_1.q.i(var1_1.q.a() - 1);
                                var3_6 = (long[])var2_3.b;
                                super.a(var3_6[0], var3_6[1], false);
                                return;
                            }
                            break block34;
                        }
                        var1_1 = this;
                        var2_4 = (dr)var1_1.q.i(var1_1.q.a() - 1);
                        var3_7 = (long[])var2_4.b;
                        super.a(var1_1.v, var3_7[1], false);
                        return;
                    }
                    if (var2_2 == 0) {
                        this.K = true;
                        this.D();
                        return;
                    }
                    var1_1 = this;
                    var3_8 = (do)var1_1.q.i(var2_2);
                    var4_10 = var3_8.a();
                    var5_16 = var3_8;
                    var5_17 = do.b(var5_16.h);
                    var6_20 = new bv();
                    var6_20.a(new ga(0, 2));
                    var6_20.b(new ga(1, 3));
                    if (var3_8.h < 0) break block37;
                    var7_21 /* !! */  = new String[]{"Ch\u00e9p n\u1ed9i dung"};
                    var8_22 /* !! */  = new int[]{1070006};
                    var9_23 = var4_10.a(var5_17);
                    var10_25 = null;
                    var11_28 /* !! */  = null;
                    switch (var9_23.c()) {
                        case 5: {
                            var10_25 = new String[]{"Xem Bang"};
                            var11_28 /* !! */  = new int[]{1070000};
                            break;
                        }
                        case 4: {
                            var9_23 = var4_10.b(var9_23.b(), var5_17);
                            if (var9_23 == null || var9_23.length() <= 0) {
                                var9_23 = var3_8.c;
                            }
                            if (var9_23.charAt(0) == '@') {
                                if (!(var9_23 = var9_23.substring(1)).equals(gr.e)) {
                                    var10_25 = new String[]{"Chat!", "Xem ME"};
                                    var11_28 /* !! */  = new int[]{1070001, 1070002};
                                    break;
                                }
                                var10_25 = new String[]{"Xem ME"};
                                var11_28 /* !! */  = new int[]{1070002};
                                break;
                            }
                            if (!var9_23.equals(gr.e)) {
                                var10_25 = new String[]{"Chat!", "Xem ME"};
                                var11_28 /* !! */  = new int[]{1070001, 1070002};
                                break;
                            }
                            var10_25 = new String[]{"Xem ME"};
                            var11_28 /* !! */  = new int[]{1070002};
                            break;
                        }
                        case 3: {
                            var10_25 = new String[]{"Xem tin", "Ch\u00e9p link"};
                            var11_28 /* !! */  = new int[]{1070004, 1070003};
                            break;
                        }
                        case 1: {
                            var12_29 = lm.a(((lm)var9_23).i);
                            if (var12_29 == null) break;
                            var10_25 = new String[]{var12_29, "G\u1eedi ti\u1ebfp"};
                            var11_28 /* !! */  = new int[]{1070020, 1070005};
                        }
                    }
                    if (var10_25 != null && var10_25.length > 0) {
                        var9_23 = new String[var7_21 /* !! */ .length + var10_25.length];
                        var12_29 = new int[var8_22 /* !! */ .length + var11_28 /* !! */ .length];
                        System.arraycopy(var10_25, 0, var9_23, 0, var10_25.length);
                        System.arraycopy(var11_28 /* !! */ , 0, var12_29, 0, var11_28 /* !! */ .length);
                        var13_31 = var10_25.length;
                        while (var13_31 < ((Object)var9_23).length) {
                            var9_23[var13_31] = var7_21 /* !! */ [var13_31 - var10_25.length];
                            var12_29[var13_31] = var8_22 /* !! */ [var13_31 - var11_28 /* !! */ .length];
                            ++var13_31;
                        }
                        var7_21 /* !! */  = var9_23;
                        var8_22 /* !! */  = (int[])var12_29;
                    }
                    break block38;
                }
                var3_9 = var2_2;
                var2_5 = var3_8;
                var4_11 = var2_5.h + 6;
                switch (var4_11) {
                    case 0: {
                        var4_12 = var2_5.d;
                        if (var4_12 != null && var4_12.i != null) {
                            if (var4_12.j == -10) {
                                if (com.mg.sq.a.k != null) {
                                    var5_18 = com.mg.sq.a.k;
                                    var1_1 = var4_12;
                                    var5_18.p.a(var1_1.i.a());
                                }
                            } else {
                                com.mg.sq.a.A();
                            }
                        }
                        v0 = true;
                        break;
                    }
                    case 1: {
                        if (var2_5.m.length > 0) {
                            super.f(true);
                            v0 = false;
                            break;
                        }
                        super.f(false);
                        ** GOTO lbl140
                    }
                    case 5: {
                        var4_13 = var1_1;
                        if (var4_13.C >= 2) {
                            super.C();
                        } else {
                            var1_1 = ak.b().a("Th\u00f4ng tin", "B\u1ea1n mu\u1ed1n x\u00f3a n\u1ed9i dung ME n\u00e0y?", "C\u00f3", -7, "Kh\u00f4ng", -8, 1);
                            var1_1.a((bj)var4_13);
                            ak.b().a((ap)var1_1, false);
                        }
                        v0 = true;
                        break;
                    }
                    case 2: {
                        var1_1.D = var2_5.e;
                        var26_32 = 0L;
                        var24_33 = var2_5.e;
                        var5_19 = var1_1;
                        super.a(var24_33, 0L, true);
                        ** GOTO lbl140
                    }
                    case 3: {
                        var4_14 = var2_5;
                        var2_5.h = -2;
                        --var4_14.j;
                        var4_14.i = 0;
                        var1_1.q.b(var2_5, var3_9);
                        dv.a().a(var2_5.e, (short)0);
                        ** GOTO lbl140
                    }
                    case 4: {
                        var4_15 = var2_5;
                        var2_5.h = -3;
                        ++var4_15.j;
                        var4_15.i = 1;
                        var1_1.q.b(var2_5, var3_9);
                        dv.a().a(var2_5.e, (short)1);
                    }
lbl140:
                    // 5 sources

                    default: {
                        v0 = true;
                    }
                }
                return;
            }
            var9_23 = new bu[var7_21 /* !! */ .length];
            var10_26 = 0;
            while (var10_26 < ((Object)var9_23).length) {
                var9_23[var10_26] = new bu(var7_21 /* !! */ [var10_26], var8_22 /* !! */ [var10_26]);
                ++var10_26;
            }
            var6_20.a((bu[])var9_23);
            var10_27 = var1_1.r.r();
            var11_28 /* !! */  = (int[])var1_1.q.o(var2_2);
            var9_24 = var1_1.q.c() + (var1_1.q.e() - var6_20.e()) / 2;
            var12_30 = var1_1.q.d() + var11_28 /* !! */ .d() - var10_27.b;
            if (var3_8.h >= 0) {
                var12_30 += var4_10.f() + var4_10.a(var5_17).e();
            }
            if (var12_30 + var6_20.f() > var1_1.q.d() + var1_1.q.f()) {
                var12_30 = var1_1.q.d() + var1_1.q.f() - var6_20.f();
            } else if (var12_30 < var1_1.q.d()) {
                var12_30 = var1_1.q.d();
            }
            var6_20.a_(z.r, var12_30);
            var6_20.d(var9_24, var12_30);
            var6_20.a((bk)var1_1);
            var6_20.a_(1);
            var1_1.a(var6_20);
        }
    }
}

