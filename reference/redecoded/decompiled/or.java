/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class or
extends fb
implements bf,
bg,
bn,
bq {
    private dh[][] p = new dh[2][0];
    private int q;
    private String r = "";
    private aw s;
    private ay t;
    private final int[][] u;
    private gb v;
    private gb w;
    private gb x;
    private gb y;
    private oa z;
    private final d A;
    private final d B;
    private final d C;
    private final d D;
    private final d E;
    private final d F;

    public or(oa oa2) {
        super(103, 8, "X\u1ebfp H\u1ea1ng", false);
        this.z = oa2;
        this.a(this);
        this.a(new ba());
        this.d(v.t);
        this.e(v.u - ba.a);
        int n2 = this.e() / 10;
        this.u = new int[][]{{10, (n2 << 1) + 10, 10 + (n2 << 3)}, {10, (n2 << 1) + 10, n2 << 3}};
        this.t = new ay(0);
        this.t.a(this.c(), this.d() + 50, this.e(), this.f() - 60);
        this.t.h(1);
        this.s = new aw();
        this.s.a(this);
        this.s.a(this);
        this.s.e(true);
        this.t.b(this.s);
        this.y = new gb(5, 1);
        this.v = new gb(3, 0);
        this.w = new gb(2, 2);
        this.x = new gb(1, 3);
        this.c(com.mg.sq.a.n);
        this.a(this.v);
        this.b(this.y);
        this.A = com.mg.sq.a.g;
        this.A.c(true);
        this.B = com.mg.sq.a.g;
        this.F = bx.c;
        this.C = new if(new int[]{0xFF0000, 0xFFFFFF});
        this.D = new if(new int[]{32512, 0xFFFFFF});
        this.E = new if(new int[]{127, 0xFFFFFF});
    }

    public final void x() {
    }

    public final void y() {
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 5: {
                ((fc)this.b).a();
                return;
            }
            case 1: {
                if (this.l != null) {
                    this.t();
                    return;
                }
                this.z.z();
                return;
            }
            case 3: {
                or or2 = this;
                bs bs2 = new bs();
                if (or2.q == 0) {
                    bs2.a(new br[]{new br(gs.d[1], 9), new br("T\u1ea3i l\u1ea1i", 10), new br("\u0110\u00f3ng", 11)});
                } else {
                    bs2.a(new br[]{new br(gs.d[0], 8), new br("T\u1ea3i l\u1ea1i", 10), new br("\u0110\u00f3ng", 11)});
                }
                int n4 = bs2.e() > bs2.f() ? bs2.e() : bs2.f();
                bs2.a_(-n4, or2.f() - bs2.f() + n4);
                bs2.d(0, v.u - ba.a - bs2.f());
                bs2.a(or2);
                or2.a(bs2);
                bs2.c(com.mg.sq.a.n);
                bs2.a(or2.w);
                bs2.b(or2.x);
                return;
            }
            case 2: {
                this.l.f(95);
                this.t();
            }
        }
    }

    public final aq a(aw object, int n2) {
        if ((object = ((aw)object).i(n2)) instanceof fy) {
            return (fy)object;
        }
        if (object instanceof dp) {
            return new gl(15);
        }
        return null;
    }

    public final void b(aq object, int n2) {
        object = this.s.i(n2);
        if (object instanceof fy) {
            this.r = this.p[this.q][n2].c;
            object = this;
            bs bs2 = new bs();
            if (com.mg.sq.a.m != null && com.mg.sq.a.o) {
                bs2.a(new br[]{new br("Xem ME", 6), new br("Chi Ti\u1ebft", 7)});
            } else {
                bs2.a(new br[]{new br("Chi Ti\u1ebft", 7)});
            }
            int n3 = ((or)object).s.s();
            aq aq2 = ((or)object).s.o(n3);
            k k2 = ((or)object).t.r();
            int n4 = (v.t - bs2.e()) / 2;
            int n5 = ((or)object).t.d() + aq2.d() - k2.b;
            if (n5 + bs2.f() > v.u - ba.a) {
                n5 = v.u - ba.a - bs2.f();
            }
            bs2.a_(v.t + bs2.e(), n5);
            bs2.d(n4, n5);
            bs2.a((bg)object);
            bs2.a(((or)object).w);
            bs2.b(((or)object).x);
            bs2.c(com.mg.sq.a.n);
            bs2.a_(1);
            ((or)object).a(bs2);
        }
    }

    public final void a(bs bs2) {
        v.c();
        this.l = bs2;
        this.c(true);
    }

    public final void t() {
        v.c();
        this.l = null;
        this.c(true);
    }

    public final void a(aq aq2, int n2) {
    }

    public final void a(aq aq2, int n2, int n3) {
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(v.am);
        graphics.fillRect(this.c(), this.d(), this.e(), this.f());
        this.A.a(graphics, gs.c[this.q], this.c() + this.e() / 2, this.d() + 5, 1);
        pc.a(graphics, 10 + this.c(), 25 + this.d(), this.e() - 20);
        n2 = 30 + this.d();
        this.B.a(graphics, gs.e[this.q][0], this.u[this.q][0] - 5 + this.c(), n2, 0);
        this.B.a(graphics, gs.e[this.q][1], this.u[this.q][1] + this.c(), n2, 0);
        this.B.a(graphics, gs.e[this.q][2], this.u[this.q][2] + this.c(), n2, 0);
        this.t.c(true);
        this.t.a(graphics, this.c(), this.d());
        pc.d(graphics, this.c(), this.d(), this.e(), this.f(), -1);
    }

    public final void n() {
        this.t.n();
    }

    public final boolean f(int n2) {
        return this.t.f(n2);
    }

    public final boolean c(int n2, int n3) {
        return this.t.c(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        return this.t.e(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        return this.t.f(n2, n3);
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        br br2 = (br)object;
        switch (n3) {
            case 7: {
                ks.a().a(this.r, (byte)103);
                com.mg.sq.a.s().a((String)null, (il)null);
                break;
            }
            case 11: {
                this.z.z();
                break;
            }
            case 6: {
                if (com.mg.sq.a.m == null) break;
                com.mg.sq.a.m.a(this.r, 0L);
                break;
            }
            case 11399: {
                if (com.mg.sq.a.m == null) break;
                com.mg.sq.a.m.b(br2.b());
                break;
            }
            case 10: {
                if (this.q == 1) {
                    com.mg.sq.a.s().R();
                    break;
                }
                com.mg.sq.a.s().Q();
                break;
            }
            case 9: {
                if (this.p[1].length > 0) {
                    this.a(1, this.p[1]);
                    break;
                }
                com.mg.sq.a.s().R();
                break;
            }
            case 8: {
                if (this.p[0].length > 0) {
                    this.a(0, this.p[0]);
                    break;
                }
                com.mg.sq.a.s().Q();
                break;
            }
            default: {
                if (com.mg.sq.a.m == null) break;
                com.mg.sq.a.m.j(n3);
            }
        }
        this.t();
    }

    public final void a(int n2, dh[] dhArray) {
        this.q = n2;
        this.p[n2] = dhArray;
        this.s.q();
        n2 = 0;
        while (n2 < dhArray.length) {
            fy fy2 = new fy(dhArray[n2]);
            fy2.a(this.u[this.q]);
            if (n2 == 0) {
                fy2.a(this.C);
            } else if (n2 < 3) {
                fy2.a(this.D);
            } else if (n2 < 10) {
                fy2.a(this.E);
            } else {
                fy2.a(this.F);
            }
            if (n2 % 2 == 0) {
                fy2.e(true);
            }
            this.s.a((Object)fy2);
            ++n2;
        }
        this.c(true);
    }
}

