/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class gw
extends hr {
    public dd k;
    private bc o;
    private bd p;
    private bd q;
    private bd r;
    private boolean s;
    private Image t;

    public gw() {
        new ou(null);
        this.b(241201);
        this.a(new be());
        this.a(this);
        try {
            this.f = 240;
            this.g = 320 - be.a;
            if (com.mg.sq.a.i == 1) {
                this.f = z.r;
                this.g = 240 - be.a;
            }
            this.c = z.r >= this.f ? (z.r - this.f) / 2 : 0;
            this.d = z.s >= this.g ? (z.s - be.a - this.g) / 2 : 0;
            this.a(this.c, this.d, this.f, this.g);
            Object object = new n(0, 0, this.f, this.g);
            this.k = new dd();
            this.k.a((n)object);
            this.r = new ga(3, 3);
            this.q = new ga(2, 2);
            this.p = new ga(1, 0);
            bd bd2 = this.r;
            object = this;
            ((aq)object).b(bd2, true);
            bd2 = this.p;
            object = this;
            ((aq)object).a(bd2, true);
            object = this;
            this.o = new bc(2);
            ((gw)object).o.a(0, 0, z.r, z.s - be.a);
            ((gw)object).o.h(1);
            ((gw)object).k.a(gr.j);
            ((gw)object).k.q();
            ((gw)object).k.a(((gw)object).k.j.a, ((gw)object).k.j.b, ((gw)object).k.j.c, ((gw)object).k.j.d);
            if (z.s > ((gw)object).k.j.d) {
                ((aq)object).g = ((gw)object).k.j.d;
                ((ap)object).d = z.s - be.a - ((aq)object).g >> 1;
            }
            ((gw)object).o.b(((gw)object).k);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void t() {
        this.t = f.d("/createcs/bk");
        this.k.i = false;
        this.s = true;
        bd bd2 = null;
        gw gw2 = this;
        gw2.a(bd2, true);
        bd2 = null;
        gw2 = this;
        gw2.b(bd2, true);
        this.a(new bh("B\u1eaft \u0111\u1ea7u", 4));
    }

    public final void a(Graphics graphics) {
        if (this.s) {
            if (this.t != null) {
                if (z.s > this.t.getHeight()) {
                    graphics.setColor(555256);
                    graphics.fillRect(0, 0, z.r, z.s - this.t.getHeight());
                }
                int n2 = 0;
                while (n2 < z.r) {
                    graphics.drawImage(this.t, n2, z.s, 36);
                    n2 += this.t.getWidth();
                }
                return;
            }
            graphics.setColor(0);
            graphics.fillRect(0, 0, z.r, z.s);
        }
    }

    public final void b(Graphics graphics) {
        if (this.o != null) {
            this.o.a(graphics, this.c, this.d);
            this.o.c(true);
        }
    }

    public final void u() {
        this.o.n();
    }

    public final void e(int n2) {
        switch (n2) {
            case 1: {
                bu[] buArray;
                gw gw2 = this;
                if (!gw2.s) {
                    buArray = gw2.k.a() ? new bu[]{new bu("C\u1eadp nh\u1eadt", 21), new bu("Tuy\u1ec7t Chi\u00eau", 20), new bu("R\u01b0\u01a1ng \u0110\u1ed3", 22), new bu("\u0110\u00f3ng", 24)} : new bu[]{new bu("Tuy\u1ec7t Chi\u00eau", 20), new bu("R\u01b0\u01a1ng \u0110\u1ed3", 22), new bu("\u0110\u00f3ng", 24)};
                    gw2.a(buArray, gw2.q, null, gw2.r);
                }
                buArray = this.q;
                gw2 = this;
                gw2.a((bd)buArray, true);
                if (this.s) break;
                buArray = this.r;
                gw2 = this;
                gw2.b((bd)buArray, true);
                return;
            }
            case 2: {
                this.l.f(95);
                bd bd2 = this.p;
                gw gw3 = this;
                gw3.a(bd2, true);
                return;
            }
            case 3: {
                if (this.l == null) {
                    this.k.a(this, "C\u00f3", 5, "Kh\u00f4ng", 6);
                    ak.b().a(this.h(), false);
                    return;
                }
                this.z();
                return;
            }
            case 4: {
                com.mg.sq.a.a(null, null, 2000);
                kq.a().c(gr.e);
                return;
            }
            case 6: {
                ak.b().e(241226);
                this.z();
                return;
            }
            case 5: {
                ak.b().e(241226);
                this.k.r();
                this.z();
                com.mg.sq.a.a(null, null);
            }
        }
    }

    public final void f(int n2) {
        this.o.f(n2);
        switch (n2) {
            case 98: {
                this.o.j(30);
                return;
            }
            case 99: {
                this.o.j(-30);
                return;
            }
            case 97: {
                this.o.i(-30);
                return;
            }
            case 96: {
                this.o.i(30);
            }
        }
    }

    public final void e(int n2, int n3) {
        this.o.e(n2, n3);
    }

    public final void f(int n2, int n3) {
        this.o.c(n2 -= this.c, n3 -= this.d);
    }

    public final void g(int n2, int n3) {
        this.o.c(n2 -= this.c, n3 -= this.d);
    }

    public final boolean g(int n2) {
        switch (n2) {
            case 20: {
                hz hz2 = new hz();
                ak.b().a(hz2);
                ak.b().a(this.h(), false);
                this.k.a(this, "C\u00f3", 5, "Kh\u00f4ng", 6);
                return true;
            }
            case 21: {
                this.k.r();
                com.mg.sq.a.a(null, null);
                return true;
            }
            case 22: {
                if (com.mg.sq.a.q().c(241202)) {
                    com.mg.sq.a.q().e(241202);
                }
                hf hf2 = new hf(null, null);
                ak.b().a(hf2);
                ak.b().a(this.h(), false);
                this.k.a(this, "C\u00f3", 5, "Kh\u00f4ng", 6);
                return true;
            }
            case 24: {
                this.k.a(this, "C\u00f3", 5, "Kh\u00f4ng", 6);
                ak.b().a(this.h(), false);
                return true;
            }
        }
        return false;
    }
}

