/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class fq
extends au {
    private int i = 0;
    private au j;
    private int k = 0;
    private lq l;
    private d m = ca.d;
    private long n;

    public fq(String object, au object2, int n2) {
        this.d(z.r - 20);
        this.j = object2;
        this.i = 0;
        object2 = object;
        object = this;
        this.l = new lq(((fq)object).m, (String)object2, 10, 10, ((au)object).e() - 10 - 10, ((fq)object).m.a(), 2);
        ((au)object).e(((fq)object).l.c() + 10 + 10);
        ((fq)object).k = (((fq)object).l.c() / (((fq)object).m.a() + 2) + 1) * 3000;
        this.e = false;
    }

    public final void a() {
        this.a(true);
        this.n = System.currentTimeMillis();
    }

    public final void n() {
        if (this.e && System.currentTimeMillis() - this.n >= (long)this.k) {
            fq fq2 = this;
            fq2.a(false);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.e) {
            n3 += this.j.d();
            n3 = this.i == 0 ? (n3 -= this.f() + 8) : (n3 += this.f() - 2);
            if ((n2 += this.j.c() + this.j.e() / 2 - this.e() / 2) < 0) {
                n2 = 0;
            }
            cz.a(graphics, n2, n3, this.e(), this.f(), n2 + this.e() / 2, this.i != 0, 13288681, 5915121);
            this.l.a(graphics, n2, n3);
        }
    }
}

