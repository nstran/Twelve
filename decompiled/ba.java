/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ba
extends au
implements bs,
bx {
    private br i;
    private bq j;
    private int k = -1;
    private int l = 0;
    private bt m;
    private a n;
    private n o;
    private boolean p;
    private boolean q = false;

    public ba() {
        br br2 = new br();
        ba ba2 = this;
        if (ba2.i != null) {
            ba2.i.a(null);
        }
        ba2.i = br2;
        if (ba2.i != null) {
            ba2.i.a(ba2);
        }
        if (ba2.b != null) {
            ba2.b.o();
        }
        this.a(new bp());
        this.b(20, 20);
        this.o = new n();
        this.g = true;
    }

    public final void e(boolean bl2) {
        this.p = true;
    }

    public final void h(int n2) {
        this.l = n2;
    }

    public final void a(bq bq2) {
        this.j = bq2;
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
        return this.i.b(n2);
    }

    public final int a() {
        return this.i.b();
    }

    public final void b(Object object) {
        this.i.a(object);
    }

    public final void j(int n2) {
        this.i.a(n2);
    }

    public final void q() {
        this.i.a();
    }

    public final void b(Object object, int n2) {
        this.i.b(object, n2);
    }

    public final Object r() {
        return this.i.b(this.i.b() - 1);
    }

    public final void k(int n2) {
        int n3 = this.k;
        this.k = n2;
        int n4 = 0;
        while (n4 < this.n.d()) {
            au au2 = (au)this.n.b(n4);
            au2.d(false);
            ++n4;
        }
        if (this.k >= 0 && this.k < this.a()) {
            au au3 = (au)this.n.b(this.k);
            au3.d(true);
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

    public final au u() {
        return this.o(this.k);
    }

    public final void a(bt bt2) {
        this.m = bt2;
    }

    public final void f(boolean bl2) {
        this.q = true;
    }

    private n A() {
        if (this.b != null) {
            return ((bc)this.b).r();
        }
        return new n(0, 0, this.e(), this.f());
    }

    public final void n() {
        try {
            int n2 = 0;
            int n3 = this.i.b();
            while (n2 < n3) {
                Object object = this.n.b(n2);
                if (object != null) {
                    object = (au)this.n.b(n2);
                    ((au)object).n();
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
                ((au)this.n.b(n2)).c(true);
                ++n2;
            }
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        cz.a(graphics, this.o);
        int n4 = n3;
        int n5 = n2;
        Graphics graphics2 = graphics;
        ba ba2 = this;
        if (ba2.n != null) {
            cz.a(graphics2, ba2.o, ba2.c() + n5, ba2.d() + n4, ba2.e(), ba2.f());
            Object object = ba2;
            object = ((au)object).b != null ? ((bc)((au)object).b).q() : new n(0, 0, ((au)object).e(), ((au)object).f());
            int n6 = ((n)object).b + ((n)object).d;
            n5 = n5 + ba2.c() - ((n)object).a;
            n4 = n4 + ba2.d() - ((n)object).b;
            int n7 = 0;
            int n8 = ba2.n.d();
            while (n7 < n8) {
                Object object2 = ba2.n.b(n7);
                if (object2 != null) {
                    object2 = (au)ba2.n.b(n7);
                    if (!((n)object).a(((au)object2).h())) {
                        if (((au)object2).d() + ((au)object2).f() > n6) {
                            break;
                        }
                    } else {
                        ((au)object2).a(graphics2, n5, n4);
                    }
                }
                ++n7;
            }
        }
        cz.c(graphics, this.o);
    }

    public final m v() {
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = this.i.b();
        while (n4 < n5) {
            au au2 = (au)this.n.b(n4);
            n2 += au2.f() + this.l;
            if (n3 < au2.e()) {
                n3 = au2.e();
            }
            ++n4;
        }
        return new m(n3 < this.e() ? this.e() : n3, n2 < this.f() ? this.f() : (n2 += 6));
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
        int n6 = this.i.b();
        while (n5 < n6) {
            au au2 = (au)this.n.b(n5);
            if (au2.h().a(n2, n3)) {
                if (!this.g) {
                    this.d(true);
                }
                this.k(n5);
                ba ba2 = this;
                boolean bl2 = ((au)this.n.b(ba2.k)).c(n2, n3);
                n2 = bl2 ? 1 : 0;
                if (bl2) {
                    return true;
                }
                this.f(95);
                return true;
            }
            if (n4 + au2.f() > n3) break;
            n4 += au2.f() + this.l;
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
        n n3 = this.A();
        bc bc2 = (bc)this.b;
        return this.a(bc2, n3, -1);
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
        n n3 = this.A();
        bc bc2 = (bc)this.b;
        return this.a(bc2, n3, 1);
    }

    public final boolean f(int n2) {
        if (this.k < 0 || this.n.d() <= 0) {
            return false;
        }
        ba ba2 = this;
        boolean bl2 = ((au)this.n.b(ba2.k)).f(n2);
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
            bc bc2 = (bc)this.b;
            switch (n2) {
                case 97: {
                    if (bc2.u()) {
                        return false;
                    }
                    bc2.d(1, -1);
                    return true;
                }
                case 96: {
                    if (bc2.v()) {
                        return false;
                    }
                    bc2.d(1, 1);
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
        ba ba2 = this;
        return ((au)this.n.b(ba2.k)).g(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean a(bc bc2, n n2, int n3) {
        int n4;
        block20: {
            block21: {
                int n5;
                int n6;
                block18: {
                    block19: {
                        n6 = this.g(0, this.k - 1);
                        au au2 = (au)this.n.b(this.k);
                        n5 = au2.f();
                        n4 = 0;
                        if (n3 >= 0) break block18;
                        if (this.k <= 0) break block19;
                        if (this.q) {
                            if (n6 + this.l > n2.b + n2.d / 2 || bc2.t()) {
                                this.k(this.k - 1);
                            }
                            bc2.d(2, -1);
                            return true;
                        }
                        au au3 = (au)this.n.b(this.k - 1);
                        n3 = au3.f();
                        int n7 = n6 - this.l - n3;
                        if (n7 + n3 <= n2.b + 5) {
                            bc2.d(2, -1);
                            return true;
                        }
                        this.k(this.k - 1);
                        n6 = this.g(0, this.k - 1);
                        au3 = (au)this.n.b(this.k);
                        int n8 = au3.f();
                        if (n6 <= n2.b) {
                            n4 = n6 - n2.b;
                            if (n8 >= n2.d) {
                                n4 += n8 - n2.d;
                                break block20;
                            } else if (this.k > 0) {
                                au au4 = (au)this.n.b(this.k - 1);
                                n4 = n6 - n4 + au4.f() + n8 > n2.b + n2.d ? (n4 -= n2.d - n8) : (n4 -= au4.f());
                            }
                        }
                        break block20;
                    }
                    if (this.p) {
                        this.k(this.a() - 1);
                        bc2.g(true);
                        return true;
                    }
                    if (bc2.t()) {
                        return false;
                    }
                    bc2.d(2, -1);
                    return true;
                }
                if (this.k >= this.a() - 1) break block21;
                if (this.q) {
                    if (n6 + n5 + this.l < n2.b + n2.d / 2 || bc2.s()) {
                        this.k(this.k + 1);
                    }
                    bc2.d(2, 1);
                    return true;
                }
                n3 = n6 + n5 + this.l;
                if (n3 >= n2.b + n2.d - 5) {
                    bc2.d(2, 1);
                    return true;
                }
                this.k(this.k + 1);
                n6 = this.g(0, this.k - 1);
                au au5 = (au)this.n.b(this.k);
                int n9 = au5.f();
                if (n6 + n9 >= n2.b + n2.d) {
                    n4 = n6 + n9 - n2.b - n2.d;
                    if (n9 >= n2.d) {
                        n4 -= n9 - n2.d;
                        break block20;
                    } else if (this.k < this.a() - 1) {
                        au au6 = (au)this.n.b(this.k + 1);
                        n4 = n6 - n4 - au6.f() < n2.b ? (n4 += n2.d - n9) : (n4 += au6.f());
                    }
                }
                break block20;
            }
            if (this.p) {
                this.k(0);
                bc2.f(true);
                return true;
            }
            if (bc2.s()) {
                return false;
            }
            bc2.d(2, 1);
            return true;
        }
        bc2.j(n4);
        return true;
    }

    private int g(int n2, int n3) {
        int n4 = 0;
        while (n2 <= n3) {
            au au2 = (au)this.n.b(n2++);
            n4 += au2.f() + this.l;
        }
        return n4;
    }

    public final void l(int n2) {
        au au2 = this.j.a(this, n2);
        au2.a(this);
        this.n.a(au2, n2);
        if (this.m != null) {
            this.m.a(this, n2);
        }
        if (this.k == n2) {
            au2.d(true);
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
            au au2 = this.j.a(this, n4);
            au2.a(this);
            this.n.b(au2, n4);
            ++n4;
        }
        this.k(this.k < 0 ? 0 : this.k);
        if (this.b != null) {
            this.b.o();
        }
    }

    public final void m(int n2) {
        au au2 = this.j.a(this, n2);
        au2.a(this);
        this.n.b(au2, n2);
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
            au au2 = (au)this.n.b(n3);
            au2.c(n2);
            n2 += au2.f() + this.l;
            ++n3;
        }
        this.c(true);
    }

    public final au o(int n2) {
        return (au)this.n.b(n2);
    }
}

