/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fk
extends au
implements bj {
    private int i = -23232323;
    private String j;
    private String k;
    private String l;
    private cx m;

    public fk(String string, String string2) {
        this.j = string;
        this.k = string2;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.b(this.k);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        cz.a(graphics);
        cz.a(graphics, this.c() + n2, this.d() + n3 - 1, this.e(), ca.d.a());
        ca.d.c(true);
        ca.d.b(true);
        ca.d.a(graphics, this.j, this.c() + n2, this.d() + n3, 0);
        ca.d.c();
        if (this.l != null) {
            com.mg.sq.a.g.a(graphics, this.l, this.c() + n2 + this.m.a, this.d() + n3 + this.m.b, 0);
        }
        cz.b(graphics);
        this.c(true);
    }

    public final boolean f(int n2) {
        switch (n2) {
            case 95: {
                hc hc2 = com.mg.sq.a.a("C\u00e2u tr\u1ea3 l\u1eddi t\u1ef1 \u0111\u1ed9ng " + this.j, null, "Xong", 0, "H\u1ee7y", 1);
                ff ff2 = (ff)hc2.e(1);
                ff2.c(this.k);
                ff2.h(100);
                hc2.b(this.i);
                hc2.a(this);
                ak.b().a(hc2);
                return true;
            }
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        if (n2 > this.c() && n2 < this.c() + this.e() && n3 > this.d() && n3 < this.d() + this.f()) {
            if (this.g) {
                this.f(95);
            } else {
                this.d(true);
            }
            return true;
        }
        return false;
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                String string = com.mg.sq.a.k(this.i);
                if (string != null && string.length() > 0) {
                    this.a(string);
                }
                ak.b().a(this.i, false);
                return;
            }
            case 1: {
                ak.b().a(this.i, false);
            }
        }
    }

    public final String a() {
        return this.k;
    }

    public final void a(String string) {
        this.k = string;
        this.b(string);
    }

    private void b(String string) {
        if (string == null || this.d == null) {
            return;
        }
        this.m = new cx(this.d.a + ca.d.a(this.j) + 12, 0);
        int n2 = this.d.c - this.m.a - this.d.a;
        this.l = com.mg.sq.a.a(string, n2);
    }
}

