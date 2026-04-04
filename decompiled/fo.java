/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class fo
extends au {
    private do i;
    private boolean j = false;
    private fn k;
    private int l = 0;

    public fo(do do_, int n2, boolean s2) {
        short s3;
        this.i = do_;
        this.j = s2;
        this.k = new fn();
        int n3 = do_.a.c() + this.k.f();
        if (do_.g >= 0 && n3 < ov.d[do_.g]) {
            s3 = ov.d[do_.g];
        }
        void var3_6 = s3 + (ca.d.a() + 26);
        int n4 = 0;
        if (do_.n) {
            n4 = 7;
        }
        if (this.s()) {
            n4 += do_.p.c() + 8;
        }
        this.b(n2, (int)(var3_6 + n4));
        if (do_.d != null) {
            this.k.a(do_.d);
        }
        if ((n2 = do_.m.length <= 0 && gr.e.equals(do_.c) ? 0 : 1) != 0) {
            this.k.h(1);
        }
        if (do_.k > 0 || do_.l) {
            this.k.d(2, do_.k);
        }
        if (do_.i == 0) {
            this.k.d(4, do_.j);
        } else if (do_.i == 1) {
            this.k.d(3, do_.j);
        }
        if (do_.e > 0L) {
            this.k.h(5);
        }
        if (do_.h < 0) {
            this.k.i(do_.h + 6);
        }
        this.k.s();
        this.k.a_(this.e() - this.k.e() - 5, this.f() - 6 - this.k.f() - n4);
        if (this.s()) {
            do_.p.a(4, this.k.d() + this.k.f() + 6);
        }
    }

    public final void d(boolean bl2) {
        if (bl2 || this.g != bl2) {
            this.c = true;
            if (!bl2) {
                this.l = 0;
            }
            this.a(this.i.h, bl2);
        }
        super.d(bl2);
    }

    public final boolean f(int n2) {
        do do_ = this.i;
        int n3 = do.b(do_.h);
        lq lq2 = this.i.a();
        if (n3 >= 0) {
            switch (n2) {
                case 97: {
                    n3 = lq2.b(n3);
                    break;
                }
                case 96: {
                    n3 = lq2.c(n3);
                    break;
                }
                case 99: {
                    n3 = lq2.d(n3);
                    break;
                }
                case 98: {
                    n3 = lq2.e(n3);
                    break;
                }
                default: {
                    return false;
                }
            }
            if (n3 < 0) {
                if (n2 == 98 && this.i.h < 1000 || n2 == 99 && this.i.h >= 1000) {
                    if (this.k.t() < 0) {
                        return false;
                    }
                    this.a(this.k.t() - 6, this.g);
                    this.b.c(true);
                    return true;
                }
                return false;
            }
        }
        if (n3 >= 0 && n3 < lq2.d().d()) {
            if (this.i.h >= 1000) {
                this.a(n3 + 1000, this.g);
            } else {
                this.a(n3, this.g);
            }
            this.b.c(true);
            return true;
        }
        if (this.k.f(n2)) {
            this.b.c(true);
            this.a(this.k.t() - 6, this.g);
            return true;
        }
        if (this.i.h < 0 && n2 == 99) {
            n2 = this.i.a.k();
            if (n2 >= 0) {
                this.b.c(true);
                this.a(n2, this.g);
                return true;
            }
            return false;
        }
        if (this.i.h < 1000 && n2 == 98 && this.s()) {
            n2 = this.i.p.j();
            if (n2 >= 0) {
                this.b.c(true);
                this.a(n2 + 1000, this.g);
                return true;
            }
            return false;
        }
        return false;
    }

    public final void a() {
        this.l = 13;
    }

    public final boolean q() {
        int n2;
        if (this.s()) {
            if (this.i.h >= 1000) {
                return false;
            }
            n2 = this.i.p.k();
            if (n2 >= 0) {
                this.b.c(true);
                this.a(n2 + 1000, this.g);
                return true;
            }
        }
        if (this.k.t() >= 0) {
            if (this.i.h < 0) {
                return false;
            }
            this.a(this.k.t() - 6, this.g);
            this.b.c(true);
            return true;
        }
        n2 = this.i.a.k();
        if (n2 == this.i.h) {
            return false;
        }
        this.a(n2, this.g);
        this.b.c(true);
        return true;
    }

    public final boolean r() {
        int n2 = this.i.a.j();
        if (n2 == this.i.h) {
            return false;
        }
        this.a(n2, this.g);
        this.b.c(true);
        return true;
    }

    private boolean s() {
        return this.i.p != null;
    }

    private void a(int n2, boolean bl2) {
        boolean bl3;
        int n3;
        Object object;
        Object object2;
        int n4 = this.i.h;
        this.i.h = n2;
        fo fo2 = this;
        int n5 = ca.d.a() + 4;
        n5 += 6;
        if (fo2.i.h >= 0) {
            object2 = fo2.i.a();
            do do_ = fo2.i;
            object = ((r)object2).a(do.b(do_.h));
            n5 = fo2.i.h >= 1000 ? ((r)object2).f() + ((q)object).e() + ((q)object).g() / 2 : n5 + ((q)object).e() + ((q)object).g() / 2;
            n3 = ((q)object).g();
        } else {
            n5 = fo2.k.d() + 5;
            n3 = fo2.k.f();
        }
        if (fo2.l() == null || fo2.l().l() == null) {
            bl3 = false;
        } else {
            object2 = (bc)fo2.b.l();
            object = ((bc)object2).r();
            if ((n5 += fo2.d() - ((n)object).b) < 0 || n5 + n3 > ((n)object).d) {
                if (n5 < 0) {
                    ((bc)object2).d(2, -1);
                } else {
                    ((bc)object2).d(2, 1);
                }
                bl3 = true;
            } else {
                boolean bl4;
                bl3 = bl4 = false;
            }
        }
        if (bl3) {
            this.i.h = n4;
            return;
        }
        this.i.a(n4).a(do.b(n4), false);
        this.i.a(n2).a(do.b(n2), bl2);
    }

    public final boolean c(int n2, int n3) {
        int n4;
        int n5;
        if (this.k.c(n2 -= this.c(), n3 -= this.d())) {
            this.a(this.k.t() - 6, this.g);
            return false;
        }
        if (this.s()) {
            n5 = n2 - (2 + this.i.p.e());
            n4 = n3 - this.i.p.f();
            int n6 = this.i.p.d().d();
            int n7 = 0;
            while (n7 < n6) {
                q q2 = this.i.p.a(n7);
                if (n5 >= q2.d() && n5 <= q2.d() + q2.f() && n4 >= q2.e() && n4 <= q2.e() + q2.g()) {
                    if (q2.c() == 0 || q2.c() == 2) break;
                    this.a(n7 + 1000, this.g);
                    return false;
                }
                ++n7;
            }
        }
        n2 -= 2 + this.i.a.e();
        n3 -= ca.d.a() + 4 + 6;
        n5 = this.i.a.d().d();
        n4 = 0;
        while (n4 < n5) {
            q q3 = this.i.a.a(n4);
            if (n2 >= q3.d() && n2 <= q3.d() + q3.f() && n3 >= q3.e() && n3 <= q3.e() + q3.g()) {
                if (q3.c() == 0 || q3.c() == 2) break;
                this.a(n4, this.g);
                return false;
            }
            ++n4;
        }
        return false;
    }

    public final void n() {
        if (this.l > 0) {
            --this.l;
            if (this.g) {
                this.b.c(true);
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        Object object;
        int n4;
        if (!this.c) {
            return;
        }
        n3 += this.d();
        n2 += this.c();
        if (this.g) {
            graphics.setColor(7267055);
        } else if (this.j) {
            graphics.setColor(13365492);
        } else {
            graphics.setColor(15916258);
        }
        int n5 = ca.d.a() + 4;
        graphics.fillRect(n2 + 1, n3 + 1, this.e() - 2, n5);
        int n6 = 0;
        if (this.i.n) {
            n6 = 7;
        }
        n6 = this.f() - n6;
        if (this.i.o) {
            graphics.setColor(0xFFFFDD);
            graphics.fillRect(n2 + 1, n3 + n5 + 1, this.e() - 2, n6 - n5 - 2);
        }
        oz.a(graphics, n2, n3, this.e(), n6, 2401717, -1);
        if (this.i.n) {
            n4 = n2 + 20;
            graphics.drawLine(n4, n3 + n6 + 7, n4 - 8, n3 + n6 - 1);
            graphics.drawLine(n4, n3 + n6 + 7, n4 + 8, n3 + n6 - 1);
            graphics.setColor(z.af);
            graphics.fillRect(n4 - 7, n3 + n6 - 1, 15, 1);
        }
        if (this.s()) {
            graphics.setColor(13489618);
            graphics.fillRect(n2 + 1, n3 + this.i.p.f(), this.e() - 2, this.i.p.c());
            this.i.p.a(graphics, n2 + 2, n3);
        }
        n4 = n3 + n5 + 6;
        n5 = n2 + 2;
        if (this.i.g >= 0) {
            ov.a(graphics, this.i.g, n5, n4, 0);
        }
        this.i.a.a(graphics, n5, n4);
        this.k.a(graphics, n5, n3);
        Object object2 = object = this.g ? com.mg.sq.a.h : ca.c;
        if (oz.c(graphics, n2 + 4, n3 + 2, this.i.f)) {
            ((d)object).a(graphics, this.i.c, n2 + 4 + 20, n3 + 3, 0);
        } else {
            ((d)object).a(graphics, this.i.c, n2 + 4, n3 + 3, 0);
        }
        ((d)object).a(graphics, this.i.b, n2 + this.e() - 4, n3 + 3, 2);
        if (this.g && (this.l & 1) == 0) {
            int n7;
            if (this.i.h >= 0) {
                lq lq2 = this.i.a();
                object = this.i;
                q q2 = lq2.a(do.b(((do)object).h));
                n7 = n2 + lq2.e() + q2.d() + q2.f() / 2 + 6;
                n2 = this.i.h >= 1000 ? n3 + lq2.f() + q2.e() + q2.g() / 2 : n4 + q2.e() + q2.g() / 2;
            } else {
                n7 = n2 + this.k.c() + this.k.r() + 10;
                n2 = n3 + this.k.d() + 5;
            }
            graphics.drawImage(oz.e, n7, n2, 17);
        }
        this.c = false;
    }
}

