/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class oq
extends aq
implements bf,
bg {
    private ms r;
    private mt s;
    private mq t;
    public static lh[] i;
    public static lh[] j;
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
    private ol x;
    private oa y;
    private byte z = 0;

    public oq(int n2, ol ol2, oa oa2) {
        ct.b("Start init PlayingTabView");
        this.y = oa2;
        this.x = ol2;
        this.a_(2);
        mp.a(this);
        nj.a();
        try {
            this.y();
        }
        catch (OutOfMemoryError outOfMemoryError) {
            ct.b("[!!] OutOfMemoryError: Loading PlayingTabView");
            if (com.mg.sq.a.m != null) {
                com.mg.sq.a.m.G();
                System.gc();
            }
            try {
                this.y();
            }
            catch (Exception exception) {
                com.mg.sq.a.s().j(1);
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            ou.a().a(5, exception2, "PlayingTabView()#1");
            return;
        }
        try {
            ag.b().l();
            i = null;
            j = null;
            k = null;
            l = null;
            m = null;
            com.mg.sq.a.i(0);
        }
        catch (Exception exception) {
            Exception exception3 = exception;
            exception.printStackTrace();
        }
        System.gc();
        this.q();
        if (oa2 != null) {
            oa2.d(false);
        }
        this.x.a((ba)null);
        this.x.a(this.t);
        com.mg.sq.a.s().e(241230);
        ct.b("Finish init PlayingTabView");
    }

    private void y() {
        this.r = new ms(i, j);
        byte[] byArray = k;
        lm[] lmArray = this.r;
        this.r.o = byArray;
        this.r.a(l);
        this.r.a(m);
        this.r.e();
        this.r.f();
        this.s = new mt(this.r);
        if (o == 9) {
            this.t = new mz(this.r, this.s, this);
        } else {
            this.t = new mo(this.r, this.s, this);
            lmArray = oq.i[0].F;
            int n2 = 0;
            while (n2 < lmArray.length) {
                int n3 = 0;
                while (n3 < go.m.length) {
                    if (lmArray[n2].a == go.m[n3].a) {
                        lmArray[n2].b = go.m[n3].b;
                        lmArray[n2].d = go.m[n3].d;
                    }
                    ++n3;
                }
                ++n2;
            }
            this.s.a(lmArray);
        }
        this.q = od.e();
        ct.b("Finish PlayingTabView.loadGame()");
    }

    public final void a() {
        if (this.v) {
            return;
        }
        bs bs2 = new bs();
        if (!this.r.a((int)1, (int)0).a().O) {
            Object object = this;
            if (((oq)object).q == null) {
                ((oq)object).q = od.e();
            }
            br br2 = new br("Chat nhanh", 11115);
            br[] brArray = new br[((oq)object).q.length];
            int n2 = 0;
            while (n2 < ((oq)object).q.length) {
                brArray[n2] = new br(com.mg.sq.a.a(String.valueOf(n2) + ". " + ((oq)object).q[n2], 120), 11115);
                brArray[n2].a(new Integer(n2));
                ++n2;
            }
            br2.a(brArray);
            object = br2;
            if (!this.t.f()) {
                if (o == 9) {
                    bs2.a(new br[]{new br("Tho\u00e1t", 11111)});
                } else {
                    bs2.a(new br[]{object, new br("Tho\u00e1t", 11111)});
                }
            } else {
                bs2.a(new br[]{new br("Tuy\u1ec7t Chi\u00eau", 11112), new br("T\u00fai \u0111\u1ed3", 11113), object, new br("\u0110\u1ea7u h\u00e0ng", 11111)});
            }
        } else if (!this.t.f()) {
            if (o == 0) {
                bs2.a(new br[]{new br("Tho\u00e1t", 11111)});
            } else if (o == 1) {
                bs2.a(new br[]{new br("Tuy\u1ec7t Chi\u00eau", 11112), new br("\u0110\u1ea7u h\u00e0ng", 11111)});
            }
        } else {
            bs2.a(new br[]{new br("Tuy\u1ec7t Chi\u00eau", 11112), new br("T\u00fai \u0111\u1ed3", 11113), new br("\u0110\u1ea7u h\u00e0ng", 11111)});
        }
        int n3 = bs2.e() > bs2.f() ? bs2.e() : bs2.f();
        bs2.a_(-n3, v.u);
        bs2.d(0, v.u - ba.a - bs2.f());
        bs2.a(this.t);
        v.c();
        this.x.a(bs2);
        bs2.a(new gb(1001, 2));
        bs2.b(new gb(1002, 3));
        this.x.a(new ba());
    }

    public final void q() {
        v.c();
        this.x.t();
        this.x.c(true);
        oq oq2 = this;
        oq2.x.z();
        this.r();
    }

    public final void r() {
        if (this.v) {
            return;
        }
        this.x.a(new gb(1000, 0));
        this.x.b(new gb(1010, 1));
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
        if (this.t != null) {
            this.t.c(n2, n3);
        }
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
                if (com.mg.sq.a.m != null) {
                    ct.a("logout OLA ");
                    outOfMemoryError.printStackTrace();
                    com.mg.sq.a.m.G();
                    System.gc();
                } else {
                    com.mg.sq.a.s().j(1);
                }
                ct.a("out of memory playingchess update");
            }
            catch (Exception exception) {
                this.z = (byte)(this.z + 1);
                ct.a("Null pointer playing " + this.z);
                if (this.z <= 9) break block8;
                com.mg.sq.a.s().j(0);
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

    public final void a(lh lhArray, lh lh2, byte[] byArray, byte[] byArray2, byte[] byArray3, byte by2, byte by3) {
        ct.a("[PlayingScreen]============receivePrepareData===========");
        ag.b().e(-241209);
        ag.b().e(-241249);
        ks.a().a((kp)null);
        this.z();
        this.w = true;
        lh[] lhArray2 = new lh[1];
        lh[] lhArray3 = lhArray2;
        lhArray2[0] = lhArray;
        lh[] lhArray4 = new lh[1];
        lhArray = lhArray4;
        lhArray4[0] = lh2;
        i = lhArray3;
        j = lhArray;
        n = by2;
        o = by3;
        k = byArray;
        l = byArray2;
        m = byArray3;
    }

    public final void a(lh lh2, String string, long l2, String string2, boolean bl2, boolean bl3) {
        if (this.v) {
            return;
        }
        this.t.a(lh2, string, l2, string2, bl2, bl3);
    }

    public final void t() {
        oa.d = o != 9;
        this.z();
        if (this.x.q == 1) {
            pa.a().a(go.w, this.y);
        } else {
            this.x.j(3);
        }
        this.u();
        ct.b("[PlayingChess]====== exitGame ===== ");
    }

    public final void u() {
        this.q = null;
        this.x.l = null;
        ks.a().a((kp)null);
        this.x = null;
        this.y = null;
        ct.b("[PlayingChess] destroy complete");
    }

    private void z() {
        ct.b("[PlayingChess]============destroyall============== prepareData = " + this.w);
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
            oq.v();
        }
        this.w = false;
        oq oq2 = this;
        oq2.x.z();
        ct.b("huy PlayingChess");
    }

    public static void v() {
        ct.b("[PlayingChess]begin: clearStaticObject ");
        com.mg.sq.a.s().l();
        co.b().d();
        i = null;
        j = null;
        k = null;
        l = null;
        m = null;
        mp.f();
        mh.a();
        nj.b();
        System.gc();
        ct.b("[PlayingChess]end: clearStaticObject");
    }

    public final void w() {
        if (this.s != null) {
            this.s.l();
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

