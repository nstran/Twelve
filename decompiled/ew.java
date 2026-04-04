/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class ew
extends au {
    public dq i;
    private String j;
    private String k;
    private int l;
    private int m;
    private boolean n = true;
    private d o = ca.d;
    private d p = com.mg.sq.a.g;
    private String q;
    private long r;

    public ew(dq dq2, int n2) {
        this.d(n2);
        this.a(dq2, null, 0L);
    }

    public ew(dq dq2, int n2, d d2) {
        this.o = d2;
        this.d(n2);
        this.a(dq2, null, 0L);
    }

    public final void a(dq dq2, String string, long l2) {
        this.i = dq2;
        this.j = String.valueOf(dq2.a) + (!l.b(dq2.e) ? " - " + dq2.e : "");
        this.l = ca.d.a(this.j);
        this.k = "[c\u1ea5p " + dq2.b + "] " + dq2.d;
        this.q = string;
        this.r = l2 + System.currentTimeMillis();
    }

    public final void d(boolean bl2) {
        if (bl2) {
            this.m = 0;
            this.n = true;
            this.e(42);
        } else {
            this.e(32);
        }
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        boolean bl2;
        n3 += this.d();
        n2 += this.c() + 2;
        if (this.g) {
            oz.e(graphics, n2 - 2, n3, this.e(), this.f());
            n3 += 7;
        } else {
            n3 += 3;
        }
        if (bl2 = this.g && this.l > this.e() - 25 - 2) {
            cz.a(graphics);
            cz.a(graphics, n2, n3, this.e(), this.f());
        }
        int n4 = n2 + 25;
        this.o.a(graphics, this.j, n4 + (this.g ? this.m : 0), n3, 0);
        if (bl2) {
            cz.b(graphics);
        }
        oz.a(graphics, n2, n3, this.i.c);
        this.p.a(graphics, this.k, n4, n3 + 13, 0);
        if (this.q != null) {
            long l2 = this.r - System.currentTimeMillis() > 0L ? this.r - System.currentTimeMillis() : 0L;
            n2 = z.r - ca.c.a(this.q) - ca.c.a(l.b(l2, "hh:mm:ss")) - 5;
            com.mg.sq.a.h.a(graphics, String.valueOf(this.q) + " " + l.b(l2, "hh:mm:ss"), n2, z.s - 35, 0);
        }
    }

    public final void n() {
        int n2 = this.e() - 25 - 2;
        if (this.g && this.l > n2) {
            if (this.n) {
                --this.m;
                if (this.m < (n2 -= this.l)) {
                    this.m = n2;
                    this.n = false;
                }
            } else {
                ++this.m;
                if (this.m > 0) {
                    this.m = 0;
                    this.n = true;
                }
            }
            this.c = true;
        }
    }
}

