/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class bw
extends al {
    private au[] k;
    private int l;
    private int m;
    private k n;
    private int o;
    private Image p;
    private String q;
    private d r = bx.b;
    private int s = 2;
    private int t;
    private int u = 0;

    public bw(String string) {
        this(string, null);
    }

    public final void a(au[] auArray) {
        this.k = auArray;
        this.t();
    }

    private bw(String string, au[] object) {
        super(1);
        this.b = false;
        object = this;
        try {
            object.p = ag.a;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.n = new k(10, 41, this.f - 15, this.g - (47 + ba.a + 5));
        this.q = string;
        this.k = null;
        int n2 = this.u;
        object = this;
        this.u = n2;
        if (object.q != null && object.q.length() > 0) {
            object.t = object.r.a(object.q);
            switch (n2) {
                case 0: {
                    object.s = object.n.a + 2;
                    break;
                }
                case 1: {
                    object.s = object.n.a + object.n.c / 2 - object.t / 2;
                    break;
                }
                case 2: {
                    object.s = object.n.a + object.n.c - 2 - object.t;
                }
            }
        }
        this.t();
    }

    private void t() {
        if (this.k == null) {
            return;
        }
        this.o = 0;
        this.m = 0;
        this.l = 0;
        int n2 = 0;
        while (n2 < this.k.length) {
            this.k[n2].a(this.n);
            this.o += this.k[n2].a() + 8;
            ++n2;
        }
    }

    public final void a(int n2, int n3) {
        int n4;
        int n5;
        super.a(n2, n3);
        if (this.l > 0) {
            n5 = this.f / 2 + 2 - 5;
            n4 = this.n.b - 2 - 15;
            if (n2 > n5 && n2 < n5 + 10 && n3 > n4 && n3 < n4 + 15) {
                v.c[99] = 2;
                return;
            }
        }
        if (this.o > this.n.d && this.o - this.l - this.n.d > 0) {
            n5 = this.f / 2 + 2 - 5;
            n4 = this.n.b + this.n.d + 2;
            if (n2 > n5 && n2 < n5 + 10 && n3 > n4 && n3 < n4 + 15) {
                v.c[98] = 2;
                return;
            }
        }
    }

    public final void c(int n2) {
        super.c(n2);
        if (n2 == 99 || n2 == 150) {
            n2 = this.l;
            this.l -= 15;
            if (this.l < 0) {
                this.l = 0;
            }
            if (this.l != this.m && n2 == this.m) {
                return;
            }
        } else if (n2 == 98 || n2 == 156) {
            this.l += 15;
            if (this.o - this.l < this.n.d) {
                this.l = this.o - this.n.d;
            }
            if (this.l < 0) {
                this.l = 0;
            }
            if (this.l != this.m) {
            }
        }
    }

    protected final void g() {
        int n2;
        if (this.m != this.l) {
            n2 = this.l - this.m;
            this.m = Math.abs(n2) < 3 ? (this.m += n2) : (this.m += n2 / 2);
        }
        if (this.k != null) {
            n2 = 0;
            while (n2 < this.k.length) {
                ++n2;
            }
        }
    }

    public final void c(Graphics graphics) {
        graphics.setColor(v.am);
        graphics.fillRect(0, 0, this.f, this.g);
        if (this.q != null) {
            this.r.a(graphics, this.q, this.s, 2, 0);
            graphics.setColor(0xFF0000);
            graphics.fillRect(this.s, this.r.a() + 1, this.t + 2, 2);
        }
        if (this.k != null) {
            graphics.setClip(this.n.a, this.n.b, this.n.c, this.n.d);
            int n2 = this.n.b - this.m;
            int n3 = this.n.b + this.n.d;
            k k2 = new k();
            int n4 = 0;
            while (n4 < this.k.length) {
                if (n2 > n3) break;
                k2.a(this.n.a, n2, this.n.c, this.k[n4].a());
                if (this.n.a(k2)) {
                    this.k[n4].a(graphics, this.n.a, n2);
                }
                n2 += this.k[n4].a() + 8;
                ++n4;
            }
            graphics.setClip(0, 0, this.f, this.g);
        }
        try {
            if (this.p != null) {
                if (this.l > 0) {
                    graphics.drawRegion(this.p, 0, 0, this.p.getWidth(), this.p.getHeight(), 6, this.f / 2 + 2, this.n.b - 2, 33);
                }
                if (this.o > this.n.d && this.o - this.l - this.n.d > 0) {
                    graphics.drawRegion(this.p, 0, 0, this.p.getWidth(), this.p.getHeight(), 5, this.f / 2 + 2, this.n.b + this.n.d + 2, 17);
                    return;
                }
            }
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            throwable.printStackTrace();
        }
    }
}

