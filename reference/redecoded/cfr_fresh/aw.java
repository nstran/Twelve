/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class aw
extends aq
implements bp,
bu {
    private bo i;
    private bn j;
    private int k = -1;
    private int l = 0;
    private bq m;
    private a n;
    private k o;
    private boolean p;
    private boolean q = false;

    public aw() {
        bm bm2 = new bm();
        aw aw2 = this;
        if (aw2.i != null) {
            aw2.i.a(null);
        }
        aw2.i = bm2;
        if (aw2.i != null) {
            aw2.i.a(aw2);
        }
        if (aw2.b != null) {
            aw2.b.o();
        }
        this.a(new bl());
        this.b(20, 20);
        this.o = new k();
        this.g = true;
    }

    public final void e(boolean bl2) {
        this.p = true;
    }

    public final void h(int n2) {
        this.l = n2;
    }

    public final void a(bn bn2) {
        this.j = bn2;
        this.z();
        if (this.b != null) {
            this.b.o();
        }
    }

    private void z() {
        this.n = new a();
        int n2 = 0;
        int n3 = this.a();
        while (n2 < n3) {
            this.n.a(this.j.a(this, n2));
            ++n2;
        }
        this.k(0);
        this.B();
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.z();
    }

    public final void a(Object[] objectArray) {
        this.a(objectArray, this.a());
    }

    public final void a(Object[] objectArray, int n2) {
        this.i.a(objectArray, n2);
    }

    public final void a(Object object) {
        this.a(object, this.a());
    }

    public final void a(Object object, int n2) {
        this.i.a(object, n2);
    }

    public final Object i(int n2) {
        return this.i.a(n2);
    }

    public final int a() {
        return this.i.a();
    }

    public final void b(Object object) {
        this.i.a(object);
    }

    public final void j(int n2) {
        this.i.b(n2);
    }

    public final void q() {
        this.i.b();
    }

    public final void b(Object object, int n2) {
        this.i.b(object, n2);
    }

    public final Object r() {
        return this.i.a(this.i.a() - 1);
    }

    public final void k(int n2) {
        int n3 = this.k;
        this.k = n2;
        int n4 = 0;
        while (n4 < this.n.d()) {
            aq aq2 = (aq)this.n.b(n4);
            aq2.d(false);
            ++n4;
        }
        if (this.k >= 0 && this.k < this.a()) {
            aq aq3 = (aq)this.n.b(this.k);
            aq3.d(true);
        }
        if ((n3 != this.k || this.a() > 1) && this.m != null) {
            this.m.a(this, n3, n2);
        }
        this.B();
    }

    public final int s() {
        return this.k;
    }

    public final Object t() {
        return this.i(this.k);
    }

    public final aq u() {
        return this.o(this.k);
    }

    public final void a(bq bq2) {
        this.m = bq2;
    }

    public final void f(boolean bl2) {
        this.q = true;
    }

    private k A() {
        if (this.b != null) {
            return ((ay)this.b).r();
        }
        return new k(0, 0, this.e(), this.f());
    }

    public final void n() {
        try {
            int n2 = 0;
            int n3 = this.i.a();
            while (n2 < n3) {
                Object object = this.n.b(n2);
                if (object != null) {
                    object = (aq)this.n.b(n2);
                    ((aq)object).n();
                }
                ++n2;
            }
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        this.c(true);
    }

    public final void c(boolean n2) {
        super.c(n2 != 0);
        if (n2 != 0 && this.n != null) {
            n2 = 0;
            while (n2 < this.n.d()) {
                ((aq)this.n.b(n2)).c(true);
                ++n2;
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        cw.a(graphics, this.o);
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        aw aw2 = this;
        if (aw2.n != null) {
            cw.a(graphics2, aw2.o, aw2.c() + n5, aw2.d() + n4, aw2.e(), aw2.f());
            Object object = aw2;
            object = ((aq)object).b != null ? ((ay)((aq)object).b).q() : new k(0, 0, ((aq)object).e(), ((aq)object).f());
            int n6 = ((k)object).b + ((k)object).d;
            n5 = n5 + aw2.c() - ((k)object).a;
            n4 = n4 + aw2.d() - ((k)object).b;
            int n7 = 0;
            int n8 = aw2.n.d();
            while (n7 < n8) {
                Object object2 = aw2.n.b(n7);
                if (object2 != null) {
                    object2 = (aq)aw2.n.b(n7);
                    if (!((k)object).a(((aq)object2).h())) {
                        if (((aq)object2).d() + ((aq)object2).f() > n6) {
                            break;
                        }
                    } else {
                        ((aq)object2).a(graphics2, n5, n4);
                    }
                }
                ++n7;
            }
        }
        cw.c(graphics, this.o);
    }

    public final g v() {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = this.i.a();
        while (n4 < n5) {
            aq aq2 = (aq)this.n.b(n4);
            n2 += aq2.f() + this.l;
            if (n3 < aq2.e()) {
                n3 = aq2.e();
            }
            ++n4;
        }
        return new g(n3 < this.e() ? this.e() : n3, n2 < this.f() ? this.f() : (n2 += 6));
    }

    public final int w() {
        return 20;
    }

    public final boolean c(int n2, int n3) {
        if (this.a() <= 0) {
            return false;
        }
        n3 -= this.d();
        n2 -= this.c();
        int n4 = 0;
        int n5 = 0;
        int n6 = this.i.a();
        while (n5 < n6) {
            aq aq2 = (aq)this.n.b(n5);
            if (aq2.h().a(n2, n3)) {
                if (!this.g) {
                    this.d(true);
                }
                this.k(n5);
                aw aw2 = this;
                boolean bl2 = ((aq)this.n.b(aw2.k)).c(n2, n3);
                n2 = bl2 ? 1 : 0;
                if (bl2) {
                    return true;
                }
                this.f(95);
                return true;
            }
            if (n4 + aq2.f() > n3) break;
            n4 += aq2.f() + this.l;
            ++n5;
        }
        return false;
    }

    public final boolean x() {
        if (this.b == null) {
            if (this.p) {
                int n2 = this.k - 1;
                if (this.k < 0) {
                    this.k = this.a() - 1;
                }
                this.k(n2);
                return true;
            }
            if (this.k > 0) {
                this.k(this.k - 1);
                return true;
            }
            return false;
        }
        k k2 = this.A();
        ay ay2 = (ay)this.b;
        return this.a(ay2, k2, -1);
    }

    public final boolean y() {
        if (this.b == null) {
            if (this.p) {
                int n2 = this.k + 1;
                if (this.k >= this.a()) {
                    this.k = 0;
                }
                this.k(n2);
                return true;
            }
            if (this.k < this.a() - 1) {
                this.k(this.k - 1);
                return true;
            }
            return false;
        }
        k k2 = this.A();
        ay ay2 = (ay)this.b;
        return this.a(ay2, k2, 1);
    }

    public final boolean f(int n2) {
        if (this.k < 0 || this.n.d() <= 0) {
            return false;
        }
        aw aw2 = this;
        boolean bl2 = ((aq)this.n.b(aw2.k)).f(n2);
        if (bl2) {
            return true;
        }
        if (n2 >= 96 && n2 <= 99) {
            if (n2 == 99) {
                return this.x();
            }
            if (n2 == 98) {
                return this.y();
            }
            if (this.b == null) {
                return false;
            }
            ay ay2 = (ay)this.b;
            switch (n2) {
                case 97: {
                    if (ay2.u()) {
                        return false;
                    }
                    ay2.d(1, -1);
                    return true;
                }
                case 96: {
                    if (ay2.v()) {
                        return false;
                    }
                    ay2.d(1, 1);
                    return true;
                }
            }
        } else if (n2 == 95) {
            if (this.m != null && this.k >= 0 && this.k < this.a()) {
                this.m.b(this, this.k);
            }
            return true;
        }
        return false;
    }

    public final boolean g(int n2) {
        if (this.k < 0 || this.n.d() <= 0) {
            return false;
        }
        aw aw2 = this;
        return ((aq)this.n.b(aw2.k)).g(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean a(ay ay2, k k2, int n2) {
        int n3;
        block20: {
            block21: {
                int n4;
                int n5;
                block18: {
                    block19: {
                        n5 = this.g(0, this.k - 1);
                        aq aq2 = (aq)this.n.b(this.k);
                        n4 = aq2.f();
                        n3 = 0;
                        if (n2 >= 0) break block18;
                        if (this.k <= 0) break block19;
                        if (this.q) {
                            if (n5 + this.l > k2.b + k2.d / 2 || ay2.t()) {
                                this.k(this.k - 1);
                            }
                            ay2.d(2, -1);
                            return true;
                        }
                        aq aq3 = (aq)this.n.b(this.k - 1);
                        n2 = aq3.f();
                        int n6 = n5 - this.l - n2;
                        if (n6 + n2 <= k2.b + 5) {
                            ay2.d(2, -1);
                            return true;
                        }
                        this.k(this.k - 1);
                        n5 = this.g(0, this.k - 1);
                        aq3 = (aq)this.n.b(this.k);
                        int n7 = aq3.f();
                        if (n5 <= k2.b) {
                            n3 = n5 - k2.b;
                            if (n7 >= k2.d) {
                                n3 += n7 - k2.d;
                                break block20;
                            } else if (this.k > 0) {
                                aq aq4 = (aq)this.n.b(this.k - 1);
                                n3 = n5 - n3 + aq4.f() + n7 > k2.b + k2.d ? (n3 -= k2.d - n7) : (n3 -= aq4.f());
                            }
                        }
                        break block20;
                    }
                    if (this.p) {
                        this.k(this.a() - 1);
                        ay2.g(true);
                        return true;
                    }
                    if (ay2.t()) {
                        return false;
                    }
                    ay2.d(2, -1);
                    return true;
                }
                if (this.k >= this.a() - 1) break block21;
                if (this.q) {
                    if (n5 + n4 + this.l < k2.b + k2.d / 2 || ay2.s()) {
                        this.k(this.k + 1);
                    }
                    ay2.d(2, 1);
                    return true;
                }
                this.n.b(this.k + 1);
                n2 = n5 + n4 + this.l;
                if (n2 >= k2.b + k2.d - 5) {
                    ay2.d(2, 1);
                    return true;
                }
                this.k(this.k + 1);
                n5 = this.g(0, this.k - 1);
                aq aq5 = (aq)this.n.b(this.k);
                int n8 = aq5.f();
                if (n5 + n8 >= k2.b + k2.d) {
                    n3 = n5 + n8 - k2.b - k2.d;
                    if (n8 >= k2.d) {
                        n3 -= n8 - k2.d;
                        break block20;
                    } else if (this.k < this.a() - 1) {
                        aq aq6 = (aq)this.n.b(this.k + 1);
                        n3 = n5 - n3 - aq6.f() < k2.b ? (n3 += k2.d - n8) : (n3 += aq6.f());
                    }
                }
                break block20;
            }
            if (this.p) {
                this.k(0);
                ay2.f(true);
                return true;
            }
            if (ay2.s()) {
                return false;
            }
            ay2.d(2, 1);
            return true;
        }
        ay2.j(n3);
        return true;
    }

    private int g(int n2, int n3) {
        int n4 = 0;
        while (n2 <= n3) {
            aq aq2 = (aq)this.n.b(n2++);
            n4 += aq2.f() + this.l;
        }
        return n4;
    }

    public final void l(int n2) {
        aq aq2 = this.j.a(this, n2);
        aq2.a(this);
        this.n.a(aq2, n2);
        if (this.m != null) {
            this.m.a(this, n2);
        }
        if (this.k == n2) {
            aq2.d(true);
        }
        this.B();
        if (this.b != null) {
            this.b.o();
        }
    }

    public final void d(int n2, int n3) {
        int n4 = n2;
        n2 += n3;
        while (n4 < n2) {
            aq aq2 = this.j.a(this, n4);
            aq2.a(this);
            this.n.b(aq2, n4);
            ++n4;
        }
        this.k(this.k < 0 ? 0 : this.k);
        if (this.b != null) {
            this.b.o();
        }
    }

    public final void m(int n2) {
        aq aq2 = this.j.a(this, n2);
        aq2.a(this);
        this.n.b(aq2, n2);
        this.k(this.k < 0 ? 0 : this.k);
        if (this.b != null) {
            this.b.o();
        }
    }

    public final void n(int n2) {
        if (n2 < 0) {
            this.k = -1;
            this.n.a();
            if (this.b != null) {
                this.b.p();
            }
            return;
        }
        this.n.a(n2);
        if (this.k > this.a() - 1) {
            this.k = this.a() - 1;
        }
        this.k(this.k);
        if (this.b != null) {
            this.b.p();
        }
    }

    private void B() {
        int n2 = 0;
        int n3 = 0;
        int n4 = this.n.d();
        while (n3 < n4) {
            aq aq2 = (aq)this.n.b(n3);
            aq2.c(n2);
            n2 += aq2.f() + this.l;
            ++n3;
        }
        this.c(true);
    }

    public final aq o(int n2) {
        return (aq)this.n.b(n2);
    }
}

