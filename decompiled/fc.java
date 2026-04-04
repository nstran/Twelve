/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class fc
extends au {
    private a i;
    private a j;
    private int k;
    private int l;
    private boolean m;
    private int n;
    private int o;
    private int p;
    private int q;
    private Image r;
    private int s;
    private int t;
    private int u;
    private int[] v;
    private Image w;
    private Image x;
    private Image y = null;
    private int z;
    private int A;
    private int B;
    private int C = -1;
    private int D = 0;
    private int E;
    private bj F;
    private Image G = f.d("/corner/4");
    private Image H = f.d("/corner/5");

    public fc() {
        this.i = new a();
        this.j = new a();
        this.v = new int[0];
        this.w = oz.c;
        this.x = f.d("/questnotifyicon");
        this.a(0, z.s, z.r, 69);
        this.s = this.d();
        this.r = f.d("/focustab");
        this.t = this.r.getWidth();
        this.k(0);
    }

    public fc(int n2, int n3, int n4) {
        this();
        this.z = 1;
        this.A = 2;
        this.B = 3;
    }

    /*
     * Unable to fully structure code
     */
    private void j(int var1_1) {
        block13: {
            if (this.i.d() <= 0) {
                return;
            }
            if (this.k < this.i.d()) {
                this.u = this.q().i;
            }
            this.k = var1_1;
            var2_4 = var1_1;
            var1_2 = this;
            if (var2_4 >= 0) {
                var1_2.v[var2_4] = 0;
                var1_2.C = -1;
                var2_4 = 0;
                while (var2_4 < var1_2.v.length) {
                    switch (var1_2.v[var2_4]) {
                        case 105: {
                            var1_2.C = var1_2.z;
                            break;
                        }
                        case 107: {
                            var1_2.C = var1_2.B;
                        }
                    }
                    ++var2_4;
                }
            }
            this.k(this.k);
            try {
                this.q().u();
                break block13;
            }
            catch (OutOfMemoryError v0) {
                var1_3 = 0;
                ** while (var1_3 < this.i.d())
            }
lbl-1000:
            // 1 sources

            {
                var2_5 = (fb)this.i.b(var1_3);
                var2_5.x();
                ++var1_3;
                continue;
            }
lbl32:
            // 1 sources

            try {
                this.q().u();
            }
            catch (OutOfMemoryError v1) {
                com.mg.sq.a.p();
                this.q().u();
            }
        }
        ak.a().f();
    }

    public final void a(fb fb2) {
        int n2 = 0;
        while (n2 < this.i.d()) {
            fb fb3 = (fb)this.i.b(n2);
            if (fb2.i == fb3.i) {
                this.j(n2);
                return;
            }
            ++n2;
        }
    }

    private void k(int n2) {
        if (this.i.d() <= 0) {
            return;
        }
        int n3 = this.i.d() * this.t;
        if (n3 > this.e()) {
            if (n2 < this.l) {
                if (this.p + n2 * this.t < 2) {
                    this.p = 2 - n2 * this.t;
                }
                if (n2 > 0 && this.p + n2 * this.t - this.t < 2) {
                    n3 = 2 - n2 * this.t + this.t;
                    this.p = n3 + n2 * this.t + this.t + this.t > this.c() + this.e() - 2 ? n3 - (n3 + this.t + this.t - this.e() - 2) : n3;
                }
            } else {
                if (this.p + n2 * this.t + this.t > this.e() - 2) {
                    this.p = this.e() - 2 - n2 * this.t - this.t;
                }
                if (n2 < this.i.d() - 1 && this.p + n2 * this.t + this.t + this.t > this.e() - 2) {
                    n3 = this.e() - 2 - n2 * this.t - this.t - this.t;
                    this.p = n3 + n2 * this.t < 2 ? 2 - n2 * this.t : n3;
                }
            }
        }
        this.l = n2;
    }

    public final void a() {
        if (this.F != null) {
            fc fc2 = this;
            int n2 = fc2.C;
            this.F.d(-1, n2);
            if (n2 == this.z || n2 == this.A) {
                return;
            }
        }
        this.c(z.s);
        this.s = this.d() - this.f();
        this.o = 0;
        this.m = true;
        fc fc3 = this;
        int n3 = fc3.t * fc3.i.d();
        if (n3 <= fc3.e()) {
            fc3.p = (fc3.e() - n3) / 2;
            return;
        }
        fc3.p = (fc3.e() - fc3.t) / 2 - fc3.k * fc3.t;
        if (fc3.p > 2) {
            fc3.p = 2;
            return;
        }
        if (fc3.p + fc3.i.d() * fc3.t < fc3.e()) {
            fc3.p = -fc3.i.d() * fc3.t + fc3.e() - 2;
        }
    }

    private void u() {
        this.s = z.s;
        this.m = false;
        this.n = 0;
        if (this.l != this.k) {
            this.j(this.l);
        }
        z.c();
    }

    private int l(int n2) {
        int n3 = 0;
        while (n3 < this.i.d()) {
            fb fb2 = (fb)this.i.b(n3);
            if (fb2.i == n2) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    public final boolean b(fb fb2) {
        return this.i.c(fb2) >= 0;
    }

    public final fb q() {
        if (this.i.d() <= 0) {
            return null;
        }
        return (fb)this.i.b(this.k);
    }

    public final fb h(int n2) {
        n2 = this.l(100);
        if (n2 >= 0) {
            return (fb)this.i.b(n2);
        }
        return null;
    }

    public final void c(fb fb2) {
        if (fb2 == null) {
            return;
        }
        if (fb2.z()) {
            this.j.a(fb2);
        }
        if (fb2.i == 107 && this.y == null) {
            this.y = f.d("/notificationnewsicon");
        }
        int[] nArray = new int[this.i.d() + 1];
        fb fb3 = this.q();
        int n2 = 0;
        while (n2 < this.i.d()) {
            fb fb4 = (fb)this.i.b(n2);
            if (fb2.i == fb4.i) {
                throw new ArrayIndexOutOfBoundsException("\u0110\u00e3 t\u1ed3n t\u1ea1i 1 gametab c\u00f3 id tr\u00f9ng v\u1edbi id c\u1ee7a gametab m\u00e0 b\u1ea1n mu\u1ed1n th\u00eam v\u00e0o");
            }
            if (fb2.i < fb4.i) {
                System.arraycopy(this.v, 0, nArray, 0, n2);
                System.arraycopy(this.v, n2, nArray, n2 + 1, this.v.length - n2);
                this.v = nArray;
                this.i.b(fb2, n2);
                fb2.y().a(this);
                if (fb3 != null && this.q() != null && fb3.i != this.q().i || this.q() == null) {
                    this.a(fb3);
                }
                return;
            }
            ++n2;
        }
        System.arraycopy(this.v, 0, nArray, 0, this.v.length);
        this.v = nArray;
        this.i.a(fb2);
        fb2.y().a(this);
        if (fb3 != null && (this.q() == null || this.q() != null && fb3.i != this.q().i)) {
            this.a(fb3);
        }
    }

    public final boolean i(int n2) {
        n2 = 0;
        while (n2 < this.i.d()) {
            fb fb2 = (fb)this.i.b(n2);
            if (100 == fb2.i) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final void d(fb fb2) {
        int n2 = this.i.c(fb2);
        this.f(fb2);
        if (fb2.i == 107) {
            this.y = null;
        }
        if (n2 >= 0) {
            int[] nArray = new int[this.v.length - 1];
            System.arraycopy(this.v, 0, nArray, 0, n2);
            System.arraycopy(this.v, n2 + 1, nArray, n2, nArray.length - n2);
            this.v = nArray;
            Object object = this.i.a(n2);
            if (object != null) {
                fb2.y().a((au)null);
            }
            if (this.i.d() <= 0) {
                this.l = 0;
                this.k = 0;
                this.u();
                return;
            }
            int n3 = this.l(this.u);
            if (n3 >= 0) {
                this.j(n3);
                return;
            }
            if (this.k >= this.i.d()) {
                this.k = this.i.d() - 1;
            }
            this.j(this.k);
        }
    }

    public final void r() {
        int n2 = 0;
        while (n2 < this.i.d()) {
            if (this.i.b(n2) != null) {
                fb fb2 = (fb)this.i.b(n2);
                fb2.y().a((au)null);
            }
            ++n2;
        }
        this.i.a();
        this.l = 0;
        this.k = 0;
        this.v = new int[0];
        this.u();
    }

    public final boolean f(int n2) {
        if (this.m) {
            int n3 = this.l;
            switch (n2) {
                case 97: {
                    if (--n3 >= 0) break;
                    n3 = this.i.d() - 1;
                    break;
                }
                case 96: {
                    if (++n3 < this.i.d()) break;
                    n3 = 0;
                    break;
                }
                case 95: {
                    this.u();
                    return true;
                }
                default: {
                    this.u();
                    if (n2 != 94 && n2 != 93) {
                        fb fb2 = this.q();
                        fb2.h(n2);
                    }
                    return true;
                }
            }
            if (n3 != this.l) {
                this.k(n3);
                this.n = 7;
                this.o = 0;
                return true;
            }
        }
        if (!this.m) {
            fb fb3;
            if (this.i.d() > 0 && (fb3 = this.q()).h(n2)) {
                return true;
            }
            switch (n2) {
                case 96: 
                case 97: {
                    this.a();
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean g(int n2) {
        fb fb2;
        return this.i.d() > 0 && (fb2 = this.q()).i(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean c(int n2, int n3) {
        block5: {
            if (!this.m) {
                if (this.i.d() <= 0) return false;
                fb fb2 = this.q();
                if (!fb2.g(n2, n3)) return false;
                return true;
            }
            if (n2 >= this.c() && n2 <= this.c() + this.e() && n3 >= this.d() && n3 <= this.d() + this.f()) {
                this.o = 0;
                int n4 = this.d() + 8 + ca.c.a();
                if (n3 < n4) return true;
                if (n3 > n4 + this.f()) return true;
                n3 = this.q;
                n4 = 0;
                while (n4 < this.i.d()) {
                    if (n2 >= n3 && n2 <= n3 + this.t) {
                        if (this.l != n4) {
                            this.k(n4);
                            this.n = 7;
                            this.o = 0;
                            return true;
                        }
                        break block5;
                    }
                    n3 += this.t;
                    ++n4;
                }
                return true;
            }
        }
        this.u();
        return true;
    }

    public final boolean f(int n2, int n3) {
        fb fb2;
        if (this.m) {
            return true;
        }
        return this.i.d() > 0 && (fb2 = this.q()).h(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        fb fb2;
        if (this.m) {
            int n4 = this.t * this.i.d();
            if (n4 > this.e()) {
                this.o = 0;
                this.p += n2;
                if (this.p >= 0) {
                    this.p = 0;
                } else if (this.p + n4 < this.e()) {
                    this.p = this.e() - n4;
                }
            }
            return true;
        }
        return this.i.d() > 0 && (fb2 = this.q()).i(n2, n3);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.j()) {
            return;
        }
        if (this.i.d() > 0) {
            fb fb2 = this.q();
            fb2.b(graphics, n2, n3);
        }
        if (this.m || this.s != this.d()) {
            int n4 = this.f();
            int n5 = this.e();
            int n6 = this.d();
            int n7 = this.c();
            Object object = graphics;
            fc fc2 = this;
            int n8 = n7 + fc2.G.getWidth() - 1;
            int n9 = n6 + 2;
            int n10 = n5 - fc2.G.getWidth() - fc2.G.getWidth() + 2;
            object.setColor(16764571);
            object.fillRect(n8, n9 + 2, n10, 15);
            object.setColor(13077582);
            object.drawLine(n8, n9, n8 + n10, n9);
            object.drawLine(n8, n9 + 17, n8 + n10, n9 + 17);
            object.setColor(7225619);
            object.drawLine(n8, n9 + 1, n8 + n10, n9 + 1);
            n8 = n7 + 1;
            n9 = n6 + 20;
            n10 = n5 - 2;
            int n11 = n4 - fc2.G.getHeight() + 2 - 1;
            object.setColor(7225619);
            object.drawRect(n8, n9, n10 - 1, n11 - 1);
            object.setColor(13077582);
            object.drawRect(n8 + 1, n9 + 1, n10 - 3, n11 - 3);
            object.setColor(14854509);
            object.drawRect(n8 + 2, n9 + 2, n10 - 5, n11 - 5);
            object.setColor(16764571);
            object.fillRect(n8 + 1, n9 + 3, n10 - 4, n11 - 6);
            object.drawImage(fc2.G, n7, n6, 0);
            object.drawRegion(fc2.G, 0, 0, fc2.G.getWidth(), fc2.G.getHeight(), 2, n7 + n5, n6, 24);
            object.setColor(13077582);
            object.drawLine(n7, n6 + 10, n7, n6 + n4);
            object.setColor(0x333333);
            object.drawLine(n7 + n5 - 1, n6 + 10, n7 + n5 - 1, n6 + n4);
            object.drawLine(n7, n6 + n4 - 1, n7 + n5 - 1, n6 + n4 - 1);
            object.drawImage(fc2.H, n7 + 2, n6 + fc2.G.getHeight() - 1, 0);
            object.drawRegion(fc2.H, 0, 0, fc2.H.getWidth(), fc2.H.getHeight(), 2, n7 + n5 - 2, n6 + fc2.G.getHeight() - 1, 24);
            object.drawRegion(fc2.H, 0, 0, fc2.H.getWidth(), fc2.H.getHeight(), 1, n7 + 2, n6 + n4 - 2, 36);
            object.drawRegion(fc2.H, 0, 0, fc2.H.getWidth(), fc2.H.getHeight(), 3, n7 + n5 - 2, n6 + n4 - 2, 40);
            n7 = this.q;
            int n12 = this.d() + 4;
            if (this.i.d() <= 0) {
                return;
            }
            object = (fb)this.i.b(this.l);
            if (object.j != null) {
                ca.e.a(graphics, object.j, this.e() / 2, n12 + 1, 1);
            }
            n12 += ca.e.a() + 6;
            cz.a(graphics);
            cz.a(graphics, this.c() + 3, this.d(), this.e() - 6, this.f());
            int n13 = this.r.getWidth();
            n6 = this.r.getHeight();
            n5 = 0;
            while (n5 < this.i.d()) {
                fb fb3 = (fb)this.i.b(n5);
                if (n5 == this.l) {
                    graphics.drawRegion(this.r, 0, 0, n13, n6, 0, n7, n12, 0);
                }
                oz.b(graphics, n7 + this.t / 2, n12 + n6 / 2, fb3.n, 3);
                if (this.v[n5] > 0) {
                    graphics.drawImage(this.w, n7 + this.t - 10, n12, 0);
                }
                n7 += this.t;
                ++n5;
            }
            cz.b(graphics);
            return;
        }
        if (this.D < 16) {
            if (this.C == this.z) {
                int n14 = this.d() - 36;
                n3 = this.c() + this.e() - 16;
                Graphics graphics2 = graphics;
                oz.d(graphics2, -21, n3, n14, 0);
                oz.d(graphics2, -23, n3 - 3, n14 - 3, 0);
                return;
            }
            if (this.C == this.A) {
                graphics.drawImage(this.x, this.c() + this.e() - 16, this.d() - 36, 0);
                return;
            }
            if (this.C == this.B && this.y != null) {
                graphics.drawImage(this.y, this.c() + this.e() - 16, this.d() - 36, 0);
            }
        }
    }

    public final void n() {
        int n2;
        if (!this.j()) {
            return;
        }
        if (this.C >= this.z) {
            this.D = this.D >= 20 ? 1 : ++this.D;
            if (this.C == this.A) {
                if (this.E < 500) {
                    ++this.E;
                } else {
                    this.t();
                }
            }
        }
        if (this.m) {
            if (this.o < 50) {
                ++this.o;
            } else {
                this.u();
            }
        }
        if (this.n > 0) {
            --this.n;
            if (this.n == 0 && this.k != this.l) {
                this.j(this.l);
            }
        }
        if (this.s != this.d()) {
            int n3;
            int n4 = this.d();
            n2 = n4 > this.s ? 1 : 0;
            n4 = n2 != 0 ? (n4 -= 20) : (n4 += 20);
            int n5 = n3 = n4 > this.s ? 1 : 0;
            if (n2 != n3) {
                n4 = this.s;
            }
            this.c(n4);
            fb fb2 = this.q();
            if (fb2 != null) {
                fb2.y().c(true);
            }
        }
        if (this.p != this.q) {
            int n6 = this.p - this.q;
            if (Math.abs(n6) > 3) {
                n6 /= 3;
            }
            this.q += n6;
        }
        if (!this.m && this.i.d() > 0) {
            fb fb3 = this.q();
            fb3.q();
        }
        n2 = 0;
        while (n2 < this.j.d()) {
            fb fb4 = (fb)this.j.b(n2);
            if (!fb4.equals(this.q())) {
                fb4.q();
            }
            ++n2;
        }
    }

    public final void s() {
        this.C = this.A;
        this.E = 1;
    }

    public final void t() {
        this.D = 0;
        this.C = -1;
        int n2 = 0;
        while (n2 < this.v.length) {
            switch (this.v[n2]) {
                case 105: {
                    this.C = this.z;
                    break;
                }
                case 107: {
                    this.C = this.B;
                }
            }
            ++n2;
        }
    }

    public final void e(fb fb2) {
        int n2 = this.i.c(fb2);
        if (n2 < 0) {
            return;
        }
        if (n2 != this.k) {
            this.v[n2] = fb2.i;
            if (this.C == this.A) {
                return;
            }
            if (fb2.i == 105) {
                this.C = this.z;
                return;
            }
            if (fb2.i == 107) {
                this.C = this.B;
            }
        }
    }

    public final void f(fb fb2) {
        if (fb2 == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.v.length) {
            if (fb2.i == this.v[n2]) {
                this.v[n2] = -1;
            }
            switch (this.v[n2]) {
                case 1: {
                    this.C = this.z;
                    break;
                }
                case 3: {
                    this.C = this.B;
                }
            }
            ++n2;
        }
    }

    public final void a(bj bj2) {
        this.F = bj2;
    }
}

