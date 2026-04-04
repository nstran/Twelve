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

public final class hi
extends ap
implements bj {
    private int k = 10;
    private lq l;
    private Image[] m;
    private String n;
    private int o;
    private int p;
    private int q;
    private int r;
    private ec[] s;
    private bv t;

    public hi(String object, String string, ef[] efArray, ec[] ecArray) {
        super(1);
        this.n = object;
        this.s = ecArray;
        if (efArray == null) {
            efArray = new ef[]{};
        }
        this.m = new Image[efArray.length];
        try {
            int n2 = 0;
            while (n2 < this.m.length) {
                this.m[n2] = Image.createImage((byte[])efArray[n2].f, (int)0, (int)efArray[n2].f.length);
                ++n2;
            }
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
        }
        this.l = new lq(ca.d, string, 0, 0, z.r - 4, ca.d.a(), 1);
        this.r = z.s - ca.d.a() - be.a - this.k;
        this.q = this.l.c() + this.k;
        int n3 = 0;
        while (n3 < this.m.length) {
            if (this.m[n3] != null) {
                this.q += this.k + this.m[n3].getHeight();
            }
            ++n3;
        }
        this.a(new be());
        this.t();
        this.a(this);
    }

    private void t() {
        this.m();
        if (this.s == null || this.s.length < 2) {
            bh bh2 = new bh("\u0110\u00f3ng", -2);
            hi hi2 = this;
            hi2.b(bh2, true);
            if (this.s != null && this.s.length > 0) {
                bh2 = new bh(this.s[0].e(), 1);
                hi2 = this;
                hi2.a(bh2, true);
                return;
            }
        } else {
            ga ga2 = new ga(-1, 0);
            hi hi3 = this;
            hi3.a(ga2, true);
        }
    }

    public final void c(int n2) {
        if (this.q <= this.r) {
            return;
        }
        if (n2 == 99) {
            this.e(-ca.d.a());
            return;
        }
        if (n2 == 98) {
            this.e(ca.d.a());
            this.e(true);
        }
    }

    public final void c(int n2, int n3) {
        if (this.q < this.r) {
            return;
        }
        this.e(-n3);
    }

    protected final void g() {
        if (this.o != this.p) {
            int n2 = this.o - this.p;
            if (Math.abs(n2) > 4) {
                n2 /= 4;
            }
            this.p += n2;
            this.e(true);
        }
        if (this.t != null) {
            this.t.n();
        }
    }

    private void e(int n2) {
        this.o += n2;
        if (this.o < 0) {
            this.o = 0;
        }
        if (this.q - this.o < this.r) {
            this.o = this.q - this.r;
        }
        this.e(true);
    }

    public final void c(Graphics graphics) {
        if (!this.h && this.t == null) {
            return;
        }
        graphics.setColor(z.af);
        graphics.fillRect(this.a(), this.c(), this.i(), this.j());
        graphics.drawImage(oz.d, this.a() + this.i(), this.c() + this.j() - be.a, 40);
        oz.a(graphics, 4, 20, z.r - 8);
        ca.d.c(true);
        ca.d.a(graphics, this.n, this.i() / 2, 2, 1);
        ca.d.c();
        int n2 = ca.d.a() + this.k;
        graphics.setClip(0, n2, z.r, this.r);
        n2 -= this.p;
        int n3 = 0;
        while (n3 < this.m.length) {
            if (this.m[n3] != null) {
                graphics.drawImage(this.m[n3], z.r / 2, n2, 17);
                n2 += this.k + this.m[n3].getHeight();
            }
            ++n3;
        }
        this.l.a(graphics, 2, n2);
        graphics.setClip(0, 0, z.r, z.s);
        if (this.t != null) {
            this.t.a(graphics, 0, 0);
        }
        this.e(false);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1: {
                hi hi2 = this;
                hi2.m();
                Object object = new bv();
                bu[] buArray = og.a(hi2.s);
                bu[] buArray2 = new bu[buArray.length + 1];
                System.arraycopy(buArray, 0, buArray2, 0, buArray.length);
                buArray2[buArray.length] = new bu("\u0110\u00f3ng", 0);
                ((bv)object).a(buArray2);
                int n4 = ((au)object).e() > ((au)object).f() ? ((au)object).e() : ((au)object).f();
                ((bv)object).a_(-n4, hi2.j() - ((au)object).f() + n4);
                ((bv)object).d(0, z.s - be.a - ((au)object).f());
                ((bv)object).a(hi2);
                v0.t = object;
                hi2.t.a(hi2);
                ga ga2 = new ga(-3, 2);
                object = hi2;
                ((aq)object).a(ga2, true);
                ga2 = new ga(-4, 3);
                object = hi2;
                ((aq)object).b(ga2, true);
                hi2.e(true);
                return;
            }
            case -2: {
                com.mg.sq.a.q().a(false);
                return;
            }
            case -3: {
                this.t.f(95);
                return;
            }
            case -4: {
                this.u();
                return;
            }
            case 1: {
                com.mg.sq.a.k.a(this.s[0]);
                return;
            }
            case 0: {
                bu bu2 = this.t.r();
                this.u();
                if (com.mg.sq.a.k.b(bu2.b(), this.s)) break;
                com.mg.sq.a.q().a(false);
            }
        }
    }

    private void u() {
        this.t = null;
        this.t();
        this.e(true);
    }
}

