/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class op
extends au
implements bj,
bk {
    private mq r;
    private mr s;
    private mo t;
    public static lf[] i;
    public static lf[] j;
    public static byte[] k;
    public static byte[] l;
    public static byte[] m;
    public static byte n;
    public static byte o;
    public static int p;
    private boolean u = false;
    private boolean v = false;
    public String[] q;
    private boolean w;
    private oj x;
    private ny y;
    private byte z = 0;

    public op(int n2, oj oj2, ny ny2) {
        cw.b("playingchess  begin===========");
        this.y = ny2;
        this.x = oj2;
        this.x.e(true);
        this.a_(2);
        mn.a(this);
        nh.a = new nh(0, 0, 1, 1, 0);
        nh.b = new nh(1, 1, 2, 1, 1);
        nh.c = new nh(2, 2, 4, 1, 2);
        nh.d = new nh(3, 3, 8, 1, 3);
        nh.e = new nh(4, 4, 16, 1, 4);
        nh.f = new nh(5, 5, 32, 1, 5);
        nh.g = new nh(10, 0, 1, 2, 8);
        nh.h = new nh(11, 1, 2, 2, 1);
        nh.i = new nh(12, 2, 4, 2, 2);
        nh.j = new nh(13, 3, 8, 2, 3);
        nh.k = new nh(14, 4, 16, 2, 4);
        nh.l = new nh(15, 5, 32, 2, 5);
        nh.m = new nh(20, 0, 1, 4, 0);
        nh.n = new nh(21, 1, 2, 4, 1);
        nh.o = new nh(22, 2, 4, 4, 2);
        nh.p = new nh(23, 3, 8, 4, 3);
        nh.q = new nh(24, 4, 16, 4, 4);
        nh.r = new nh(25, 5, 32, 4, 5);
        nh.s = new nh(70, 70, 64, 1, 6);
        nh.t = new nh(80, 80, -128, 1, 7);
        nh.u = new nh(90, 90, 0, 1, -1);
        nh.x = new nh(99, 99, 0x1000000, 1, -1);
        try {
            this.y();
        }
        catch (OutOfMemoryError outOfMemoryError) {
            cw.a("[PlayingChess] load game() OutOfMemoryError PlayingChess ");
            if (com.mg.sq.a.k != null) {
                com.mg.sq.a.k.G();
            }
            try {
                this.y();
            }
            catch (Exception exception) {
                System.gc();
                com.mg.sq.a.j(1);
                return;
            }
        }
        catch (Throwable throwable) {
            cw.a("[PlayingChess] load game() Throwabl PlayingChess ");
            throwable.printStackTrace();
            try {
                this.y();
            }
            catch (Exception exception) {
                throwable.printStackTrace();
                com.mg.sq.a.j(5);
                return;
            }
        }
        try {
            ak.b().k();
            i = null;
            j = null;
            k = null;
            l = null;
            m = null;
            com.mg.sq.a.i(0);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            cw.a(exception2);
        }
        System.gc();
        this.q();
        if (ny2 != null) {
            ny2.d(false);
        }
        this.x.a((be)null);
        this.x.a(this.t);
        com.mg.sq.a.q().e(241230);
        cw.b("khoi tao tran dau hoan taat ");
    }

    private void y() {
        cw.a("load game");
        this.r = new mq(i, j);
        byte[] byArray = k;
        lk[] lkArray = this.r;
        this.r.o = byArray;
        this.r.a(l);
        this.r.a(m);
        this.r.e();
        this.r.f();
        this.s = new mr(this.r);
        if (o == 9) {
            this.t = new mx(this.r, this.s, this);
        } else {
            this.t = new mm(this.r, this.s, this);
            lkArray = op.i[0].F;
            int n2 = 0;
            while (n2 < lkArray.length) {
                int n3 = 0;
                while (n3 < gr.l.length) {
                    if (lkArray[n2].a == gr.l[n3].a) {
                        lkArray[n2].b = gr.l[n3].b;
                        lkArray[n2].d = gr.l[n3].d;
                    }
                    ++n3;
                }
                ++n2;
            }
            this.s.a(lkArray);
        }
        this.q = ob.e();
        cw.b("playingchess  loadGame===========");
    }

    public final void a() {
        if (this.v) {
            return;
        }
        bv bv2 = new bv();
        if (!this.r.a((int)1, (int)0).a().Q) {
            Object object = this;
            if (((op)object).q == null) {
                ((op)object).q = ob.e();
            }
            bu bu2 = new bu("Chat nhanh", 11115);
            bu[] buArray = new bu[((op)object).q.length];
            int n2 = 0;
            while (n2 < ((op)object).q.length) {
                buArray[n2] = new bu(com.mg.sq.a.a(String.valueOf(n2) + ". " + ((op)object).q[n2], 120), 11115);
                buArray[n2].a(new Integer(n2));
                ++n2;
            }
            bu2.a(buArray);
            object = bu2;
            if (!this.t.f()) {
                if (o == 9) {
                    bv2.a(new bu[]{new bu("Tho\u00e1t", 11111)});
                } else {
                    bv2.a(new bu[]{object, new bu("Tho\u00e1t", 11111)});
                }
            } else {
                bv2.a(new bu[]{new bu("Tuy\u1ec7t Chi\u00eau", 11112), new bu("T\u00fai \u0111\u1ed3", 11113), object, new bu("\u0110\u1ea7u h\u00e0ng", 11111)});
            }
        } else if (!this.t.f()) {
            if (o == 0) {
                bv2.a(new bu[]{new bu("Tho\u00e1t", 11111)});
            } else if (o == 1) {
                bv2.a(new bu[]{new bu("Tuy\u1ec7t Chi\u00eau", 11112), new bu("\u0110\u1ea7u h\u00e0ng", 11111)});
            }
        } else {
            bv2.a(new bu[]{new bu("Tuy\u1ec7t Chi\u00eau", 11112), new bu("T\u00fai \u0111\u1ed3", 11113), new bu("\u0110\u1ea7u h\u00e0ng", 11111)});
        }
        int n3 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
        bv2.a_(-n3, z.s);
        bv2.d(0, z.s - be.a - bv2.f());
        bv2.a(this.t);
        z.c();
        this.x.a(bv2);
        bv2.a(new ga(1001, 2));
        bv2.b(new ga(1002, 3));
        this.x.a(new be());
    }

    public final void q() {
        z.c();
        this.x.t();
        this.x.c(true);
        op op2 = this;
        op2.x.A();
        this.r();
    }

    public final void r() {
        if (this.v) {
            return;
        }
        this.x.a(new ga(1000, 0));
        this.x.b(new ga(1010, 1));
    }

    public final boolean f(int n2) {
        if (this.v) {
            return false;
        }
        if (this.x.l != null) {
            boolean bl2 = this.x.l.f(n2);
            n2 = bl2 ? 1 : 0;
            if (!bl2) {
                this.q();
            }
            return true;
        }
        this.t.b(n2);
        return true;
    }

    public final boolean g(int n2) {
        if (this.v) {
            return false;
        }
        if (this.x.l != null) {
            return true;
        }
        this.t.c(n2);
        return true;
    }

    public final boolean c(int n2, int n3) {
        if (this.v) {
            return false;
        }
        if (this.x.l != null) {
            boolean bl2 = this.x.l.c(n2, n3);
            n2 = bl2 ? 1 : 0;
            if (!bl2) {
                this.q();
            }
            return true;
        }
        this.t.a(n2, n3);
        return true;
    }

    public final boolean f(int n2, int n3) {
        if (this.v) {
            return false;
        }
        if (this.x.l != null) {
            return true;
        }
        this.t.b(n2, n3);
        return true;
    }

    public final boolean e(int n2, int n3) {
        return true;
    }

    public final void n() {
        block8: {
            try {
                if (this.v) {
                    return;
                }
                if (this.t != null) {
                    this.t.b();
                }
                if (this.s != null) {
                    this.s.d();
                }
            }
            catch (OutOfMemoryError outOfMemoryError) {
                System.gc();
                if (com.mg.sq.a.k != null) {
                    cw.a("logout OLA ");
                    outOfMemoryError.printStackTrace();
                    com.mg.sq.a.k.G();
                    System.gc();
                } else {
                    com.mg.sq.a.j(1);
                }
                cw.a("out of memory playingchess update");
            }
            catch (Exception exception) {
                this.z = (byte)(this.z + 1);
                cw.a("Null pointer  playing " + this.z);
                if (this.z <= 9) break block8;
                com.mg.sq.a.j(0);
            }
        }
        this.z = 0;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.v) {
            return;
        }
        if (this.s != null) {
            this.s.a(graphics);
        }
    }

    public final String s() {
        if (this.v) {
            return "";
        }
        return this.r.a(1, 0).j();
    }

    public final void a(String string) {
        if (this.v) {
            return;
        }
        this.s.b(string);
    }

    public final void a(lf lfArray, lf lf2, byte[] byArray, byte[] byArray2, byte[] byArray3, byte by2, byte by3) {
        cw.a("[PlayingScreen]============receivePrepareData===========");
        ak.b().e(-241209);
        ak.b().e(-241249);
        kq.a().a((ko)null);
        this.z();
        this.w = true;
        lf[] lfArray2 = new lf[1];
        lf[] lfArray3 = lfArray2;
        lfArray2[0] = lfArray;
        lf[] lfArray4 = new lf[1];
        lfArray = lfArray4;
        lfArray4[0] = lf2;
        i = lfArray3;
        j = lfArray;
        n = by2;
        o = by3;
        k = byArray;
        l = byArray2;
        m = byArray3;
    }

    public final void a(lf lf2, String string, long l2, String string2, boolean bl2, boolean bl3) {
        if (this.v) {
            return;
        }
        this.t.a(lf2, string, l2, string2, bl2, bl3);
    }

    public final void t() {
        ny.m = o != 9;
        this.z();
        if (this.x.p == 1) {
            ox.a().a(ny.c, this.y);
        } else {
            this.x.j(3);
        }
        this.u();
        cw.b("[PlayingChess]====== exitGame ===== ");
    }

    public final void u() {
        this.q = null;
        this.x.l = null;
        kq.a().a((ko)null);
        this.x = null;
        this.y = null;
        cw.b("[PlayingChess] destroy complete");
    }

    private void z() {
        cw.b("[PlayingChess]============destroyall============== prepareData = " + this.w);
        this.v = true;
        if (this.s != null) {
            this.s.c();
        }
        this.s = null;
        if (this.r != null) {
            this.r.g();
        }
        this.r = null;
        if (this.t != null) {
            this.t.q();
        }
        this.t = null;
        if (!this.w) {
            op.v();
        }
        this.w = false;
        op op2 = this;
        op2.x.A();
        cw.b("huy PlayingChess");
    }

    public static void v() {
        cw.b("[PlayingChess]begin: clearStaticObject ");
        com.mg.sq.a.q().k();
        cr.b().d();
        i = null;
        j = null;
        k = null;
        l = null;
        m = null;
        mn.f();
        mf.a();
        nh.a = null;
        nh.b = null;
        nh.c = null;
        nh.d = null;
        nh.e = null;
        nh.f = null;
        nh.g = null;
        nh.h = null;
        nh.i = null;
        nh.j = null;
        nh.k = null;
        nh.l = null;
        nh.m = null;
        nh.n = null;
        nh.o = null;
        nh.p = null;
        nh.q = null;
        nh.r = null;
        nh.s = null;
        nh.t = null;
        nh.u = null;
        nh.x = null;
        System.gc();
        cw.b("[PlayingChess]end: clearStaticObject");
    }

    public final void w() {
        if (this.s != null) {
            this.s.k();
        }
    }

    public final void x() {
        ((fc)this.x.l()).a();
    }

    public final void a(int n2, int n3, Object object) {
        if (this.t != null) {
            this.t.a(n2, n3, object);
        }
    }

    public final void d(int n2, int n3) {
        if (this.t != null) {
            this.t.d(n2, n3);
        }
    }

    public final void a(String string, String string2) {
        int n2 = 0;
        while (n2 < this.r.a()[1].length) {
            if (string.equals(this.r.a()[1][n2].j())) {
                this.a(string2);
            }
            ++n2;
        }
    }
}

