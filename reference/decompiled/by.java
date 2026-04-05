/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class by
extends ap {
    private String[] k;
    private int l;
    private int m;
    private String n;
    private int o = 0;
    private int p = 2;
    private boolean q = false;
    private int r = 20;
    private int s = 0;
    private int t = 6;
    private int u;
    private int v;

    public by(String object, String string, String[] stringArray, int[] nArray, int n2) {
        super(n2);
        this.n = object;
        this.s = 1;
        if (this.s == 0) {
            this.c = 0;
            this.u = this.f = z.r;
        } else {
            this.c = 10;
            this.u = this.f = z.r - 20;
        }
        object = this;
        this.k = ca.a(string, ((by)object).u - (((by)object).t << 1));
        if (((by)object).s == 0) {
            ((by)object).l = 0;
            ((by)object).m = z.s - be.a;
        } else {
            int n3 = ((by)object).k.length;
            n3 = (n3 + 2) * ca.c.a();
            ((by)object).l = (((aq)object).g - be.a - n3) / 2;
            ((by)object).m = (((aq)object).g - be.a + n3) / 2 + ca.c.a();
            if (((by)object).l < 0) {
                ((by)object).l = 0;
            }
            if (((by)object).m > z.s - be.a) {
                ((by)object).m = z.s - be.a;
            }
        }
        ((ap)object).d = ((by)object).l;
        ((by)object).v = ((by)object).m - ((by)object).l;
        if (((by)object).s != 0) {
            ((aq)object).f = ((by)object).u;
            ((aq)object).g = ((by)object).v;
        }
        if (stringArray != null) {
            if (stringArray.length == 1) {
                this.a(new bh(stringArray[0], nArray[0]));
                return;
            }
            if (stringArray.length == 2) {
                bh bh2 = new bh(stringArray[0], nArray[0]);
                object = this;
                ((aq)object).a(bh2, true);
                bh2 = new bh(stringArray[1], nArray[1]);
                object = this;
                ((aq)object).b(bh2, true);
                return;
            }
            bh bh3 = new bh(stringArray[0], nArray[0]);
            object = this;
            ((aq)object).a(bh3, true);
            this.a(new bh(stringArray[1], nArray[1]));
            bh3 = new bh(stringArray[1], nArray[2]);
            object = this;
            ((aq)object).b(bh3, true);
        }
    }

    public final void c(int n2) {
        if (n2 == 99) {
            this.r = 20;
            if (this.o < 0) {
                this.o += this.p;
                return;
            }
        } else if (n2 == 98) {
            this.r = 20;
        }
    }

    public final void a(int n2, int n3) {
        this.r = 20;
    }

    protected final void g() {
        if (this.r > 0) {
            --this.r;
        }
    }

    public final void c(Graphics object) {
        ak.c().a((Graphics)object, this.c, this.d, this.u, this.v);
        Graphics graphics = object;
        object = this;
        this.t = 6;
        cz.a(graphics, cz.a);
        if (object.n != null && object.n.length() > 0) {
            ca.b.a(graphics, object.n, object.c + object.f / 2, object.l + ca.b.a() - 5, 1);
            ca.a(ca.c);
            cz.a(graphics, object.c + object.t, object.l + (ca.c.a() << 1) - 5, object.u - (object.t << 1), object.v - 2 * ca.c.a());
            ca.a(graphics, ca.c, object.k, object.c + object.t, object.l + (ca.c.a() << 1) - 5 + object.o + (object.n.length() > 1 ? 5 : 0), object.u - (object.t << 1), object.v - 2 * ca.c.a() + 5 - object.o, 1);
        } else {
            cz.a(graphics, object.c + object.t, object.l + ca.c.a() - 5, object.u - (object.t << 1), object.v - ca.c.a());
            ca.a(graphics, ca.c, object.k, object.c + object.t, object.l + ca.c.a() - 5 + object.o + 5, object.u - (object.t << 1), object.v - ca.c.a() + 5 - object.o, 1);
        }
        cz.c(graphics, cz.a);
    }
}

