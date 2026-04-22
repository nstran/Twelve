/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class gt
extends al
implements bf {
    private byte k = 0;
    private lh l = null;
    private String m = null;
    private k n;
    private k o;
    private k p;
    private mg q;
    private String[] r = new String[]{"Danh Hi\u1ec7u", "H\u1ea1ng ", "C\u1ea5p: "};
    private cu s;
    private k t;
    private String[] u;
    private String v;
    private np w;
    private String x;
    private int y;
    private boolean z;
    private boolean A;

    public gt(lh lh2, String string, boolean bl2, boolean bl3) {
        this(lh2, 0, bl2, bl3);
        this.x = string;
        this.z = bl2;
    }

    /*
     * WARNING - void declaration
     */
    public gt(lh lh2, byte by2, boolean bl2, boolean n2) {
        super(1);
        int n3;
        int[] nArray = new int[]{7037769, 12628615, 15720104, 12628615, 7037769};
        this.y = 50;
        this.z = false;
        this.A = false;
        byte by3 = by2;
        gt gt2 = this;
        this.k = by3;
        if (by3 == 0) {
            bd bd2 = new bd("Kh\u00f4ng", 24);
            gt2.b(bd2, true);
        } else {
            gt2.a(new bd("\u0110\u00f3ng", 87));
        }
        this.b(-241209);
        this.a(new ba());
        this.a(this);
        this.z = bl2;
        this.A = n3;
        lh lh3 = lh2;
        gt2 = this;
        if (lh3 != null) {
            void var3_12;
            int n4;
            int n42 = 220;
            gt2.l = lh3;
            gt2.o = new k(10, 10, 60, 70);
            int n5 = gt2.o.b + gt2.o.d + 4;
            gt2.p = new k(gt2.o.a + gt2.o.c + 65, gt2.o.b + bx.d.a() + 4, 75, 16);
            n3 = bx.c.a(lh3.Q) + 4;
            if (n3 > gt2.p.c) {
                n4 = n3 - gt2.p.c;
                gt2.p.c = n3;
                n42 = n4 + 220;
            }
            if (gt2.k == 0) {
                int n6;
                int n7 = n5 + (4 + bx.d.a() + 4);
                if (gt2.z) {
                    n6 = n7 + (4 + bx.d.a() + 4);
                }
                if (gt2.A) {
                    void var3_9 = n6 + (4 + bx.d.a() + 4);
                }
                if (lh3.X > 0L) {
                    void var3_11;
                    gt2.m = i.a(lh3.X, ".");
                    gt2.s = new cu(gt2.o.a, (int)(var3_10 += 4));
                    n3 = bx.c.a("\u0110\u1eb7t C\u01b0\u1ee3c:");
                    n4 = bx.c.a(gt2.m);
                    gt2.t = new k(gt2.o.a + 4 + 2 + n3, (int)(var3_11 += 6), n4, 16);
                    var3_12 = var3_11 + (gt2.t.d + 4);
                }
            }
            void var3_13 = var3_12 + 4 + 4;
            gt2.n = new k((gt2.f - n42) / 2, (gt2.g - var3_13 - ba.a) / 2, n42, (int)var3_13);
            gt2.u = new String[3];
            gt2.u[0] = lh3.Q;
            gt2.u[1] = lh3.R;
            gt2.u[2] = String.valueOf(lh3.G);
            gt2.v = com.mg.sq.a.a(gt2.l.b, gt2.f / 2 - 5);
            gt2.q = mb.a(lh3, false);
            gt2.q.a(lc.a(lh3));
            gt2.q.a(nr.a(lh3));
            gt2.q.c(2);
        }
        this.w = new np();
        this.w.a();
    }

    public final String t() {
        return this.x;
    }

    public final void c(Graphics graphics) {
        pc.d(graphics, this.n.a, this.n.b, this.n.c, this.n.d, v.aj);
        int n2 = this.o.a + this.n.a;
        int n3 = this.n.b + this.o.b;
        pc.c(graphics, n2, n3, this.o.c, this.o.d);
        if (this.q != null) {
            this.q.a(graphics, n2 + 4, n3 + this.o.d - 4 - this.q.q());
        }
        int n4 = n2 + this.o.c + 4;
        pc.b(graphics, n4, n3 + bx.d.b() - pc.h.getHeight(), this.l.g);
        bx.d.c(true);
        bx.d.a(graphics, this.v, n4 += 15, n3, 0);
        bx.d.c(false);
        n3 += bx.d.a() + 4;
        n4 = 0;
        while (n4 < this.r.length) {
            pc.b(graphics, this.p.a + this.n.a, n3, this.p.c, this.p.d, 1070484, 16579764, 14542575);
            bx.d.a(graphics, this.r[n4], this.p.a + this.n.a - 4, n3 + 1, 2);
            bx.c.a(graphics, this.u[n4], this.p.a + this.n.a + this.p.c / 2, n3 + 1, 1);
            n3 += this.p.d + 4;
            ++n4;
        }
        if (this.k == 0) {
            com.mg.sq.a.h.a(graphics, "Mu\u1ed1n khi\u00eau chi\u1ebfn v\u1edbi b\u1ea1n!", this.n.a + this.n.c / 2, n3 + 2, 1);
            n3 += 4 + com.mg.sq.a.h.a();
            if (this.z) {
                com.mg.sq.a.g.a(graphics, "Ki\u1ec3u quy\u1ebft \u0111\u1ea5u: 1 chi\u1ec1u", n2, n3 + 2, 0);
                n3 += 4 + com.mg.sq.a.g.a();
            }
            if (this.A) {
                com.mg.sq.a.g.a(graphics, "Kh\u00f4ng ch\u01a1i Tuy\u1ec7t Chi\u00eau", n2, n3 + 2, 0);
                com.mg.sq.a.g.a();
            }
            if (this.m != null) {
                pc.a(graphics, this.s.a + this.n.a, this.s.b + this.n.b, this.n.c - 20);
                n3 = this.t.b + this.n.b + 2;
                bx.d.c(true);
                bx.d.a(graphics, "\u0110\u1eb7t C\u01b0\u1ee3c:", n2, n3 + 2, 0);
                bx.d.c(false);
                n2 = this.t.a + this.n.a + 4;
                com.mg.sq.a.h.a(graphics, this.m, n2, n3 + 2, 0);
                bx.d.a(graphics, " KEN", n2 + this.t.c + 2, n3 + 2, 0);
                com.mg.sq.a.h.a();
            }
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 24: {
                com.mg.sq.a.s().a(false, null);
                return;
            }
            case 12: {
                com.mg.sq.a.s().a(true, null);
                return;
            }
        }
        ag.b().a(this.h(), false);
    }

    protected final void g() {
        if (this.q != null) {
            this.q.i();
        }
        if (this.k == 0) {
            if (this.y > 0) {
                --this.y;
                if (this.y == 1) {
                    bd bd2 = new bd("\u0110\u1ed3ng \u00fd", 12);
                    gt gt2 = this;
                    gt2.a(bd2, true);
                    return;
                }
            }
            if (this.w.g() > 29L) {
                com.mg.sq.a.s().a(false, null);
                this.w.b();
            }
        }
    }

    public final long u() {
        return this.l.X;
    }
}

