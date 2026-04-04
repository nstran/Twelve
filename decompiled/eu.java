/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class eu
extends au {
    public du i;
    public lq j;
    private int l;
    private int m;
    private int n;
    private boolean o = true;
    public boolean k = false;
    private d p;

    public eu(du object, int n2) {
        int n3;
        Object object2;
        this.d(n2);
        du du2 = object;
        object = this;
        this.i = du2;
        boolean bl2 = du2.e() == 2;
        String string = String.valueOf(du2.b()) + (bl2 ? (!l.b(du2.f()) ? " - " + du2.f() : "") : "");
        string = string.replace('\r', ' ');
        string = string.replace('\n', ' ');
        ((eu)object).p = ca.d;
        boolean bl3 = du2.g() != null && du2.g().equals("patriot");
        if (bl3) {
            ((eu)object).p = com.mg.sq.a.g;
        }
        ((eu)object).j = new lq(((eu)object).p, string, 0, 0, Integer.MAX_VALUE, ((eu)object).p.a(), 1);
        if (!bl3) {
            ((eu)object).p = ca.c;
        }
        ((eu)object).m = du2.d() == 3 ? -3 : (du2.d() == 1 ? -5 : 10000);
        short s2 = du2.c();
        if (((eu)object).i.c() == -13) {
            object2 = object;
            n3 = -13;
        } else {
            object2 = object;
            n3 = ((eu)object).i.e() == 2 ? (int)s2 : -19;
        }
        ((eu)object2).l = n3;
    }

    public final void d(boolean bl2) {
        if (bl2) {
            this.e(40);
            this.n = 0;
            this.o = true;
        } else {
            this.e(22);
        }
        if (bl2 || this.g != bl2) {
            this.c = true;
        }
        super.d(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        n3 += this.d();
        n2 += this.c() + 2;
        if (this.g) {
            oz.e(graphics, n2 - 2, n3, this.e(), this.f());
        }
        n3 += 3;
        boolean bl2 = this.g && this.j.b() > this.e() - 25;
        if (bl2) {
            cz.a(graphics);
            cz.a(graphics, n2, n3, this.e(), this.f());
        }
        int n4 = n3 + 4;
        try {
            this.j.a(graphics, n2 + 25 + (this.g ? this.n : 0), n4);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        if (bl2) {
            cz.b(graphics);
        }
        n4 += (this.j.c() - pb.a().b(this.l)) / 2;
        int n5 = n2 + 25;
        if (this.g) {
            n4 = n3 + 18;
            this.p.a(graphics, this.i.a(), n5, n4, 0);
            n4 += this.p.b() - pb.a().b(this.l);
        }
        if (this.l < 0) {
            oz.d(graphics, this.l, n5 - 2 - oz.g[Math.abs(this.l)], n4, 0);
        } else {
            pb.a().a(graphics, this.l, n5 - 2 - pb.a().a(this.l), n4, 0);
        }
        if (this.i.e() == 2 && this.m <= 0) {
            oz.d(graphics, this.m, n2, n4, 0);
        }
        if (this.k) {
            oz.d(graphics, -23, n2 - 2, n3 - 2, 0);
        }
        this.c = false;
    }

    public final void n() {
        if (this.g) {
            int n2 = this.e() - 4 - 25;
            if (this.j.b() > n2) {
                if (this.o) {
                    --this.n;
                    if (this.n < (n2 -= this.j.b())) {
                        this.n = n2;
                        this.o = false;
                    }
                } else {
                    ++this.n;
                    if (this.n > 0) {
                        this.n = 0;
                        this.o = true;
                    }
                }
                this.c = true;
            }
        }
    }
}

