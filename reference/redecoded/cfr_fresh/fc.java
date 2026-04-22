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
extends aq {
    private a j;
    private a k;
    private int l;
    private int m;
    private boolean n;
    private int o;
    private int p;
    private int q;
    private int r;
    private Image s;
    private int t;
    private int u;
    private int v;
    private int[] w;
    private Image x;
    private Image y;
    private Image z = null;
    private int A;
    private int B;
    private int C;
    private int D = -1;
    private int E = 0;
    private int F;
    public String i;
    private bf G;
    private Image H = f.d("/corner/4");
    private Image I = f.d("/corner/5");

    public fc() {
        this.j = new a();
        this.k = new a();
        this.w = new int[0];
        this.x = pc.c;
        this.y = f.d("/questnotifyicon");
        this.a(0, v.u, v.t, 69);
        this.t = this.d();
        this.s = f.d("/focustab");
        this.u = this.s.getWidth();
        this.k(0);
    }

    public fc(int n2, int n3, int n4) {
        this();
        this.A = 1;
        this.B = 2;
        this.C = 3;
    }

    /*
     * Unable to fully structure code
     */
    private void j(int var1_1) {
        block13: {
            if (this.j.d() <= 0) {
                return;
            }
            if (this.l < this.j.d()) {
                this.v = this.q().i;
            }
            this.l = var1_1;
            var2_4 = var1_1;
            var1_2 = this;
            if (var2_4 >= 0) {
                var1_2.w[var2_4] = 0;
                var1_2.D = -1;
                var2_4 = 0;
                while (var2_4 < var1_2.w.length) {
                    switch (var1_2.w[var2_4]) {
                        case 105: {
                            var1_2.D = var1_2.A;
                            break;
                        }
                        case 107: {
                            var1_2.D = var1_2.C;
                        }
                    }
                    ++var2_4;
                }
            }
            this.k(this.l);
            try {
                this.q().x();
                break block13;
            }
            catch (OutOfMemoryError v0) {
                var1_3 = 0;
                ** while (var1_3 < this.j.d())
            }
lbl-1000:
            // 1 sources

            {
                var2_5 = (fb)this.j.b(var1_3);
                var2_5.y();
                ++var1_3;
                continue;
            }
lbl32:
            // 1 sources

            try {
                this.q().x();
            }
            catch (OutOfMemoryError v1) {
                com.mg.sq.a.r();
                this.q().x();
            }
        }
        ag.a().e();
    }

    public final void a(fb fb2) {
        if (fb2 == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.j.d()) {
            fb fb3 = (fb)this.j.b(n2);
            if (fb2.i == fb3.i) {
                this.j(n2);
                return;
            }
            ++n2;
        }
    }

    private void k(int n2) {
        if (this.j.d() <= 0) {
            return;
        }
        int n3 = this.j.d() * this.u;
        if (n3 > this.e()) {
            if (n2 < this.m) {
                if (this.q + n2 * this.u < 2) {
                    this.q = 2 - n2 * this.u;
                }
                if (n2 > 0 && this.q + n2 * this.u - this.u < 2) {
                    n3 = 2 - n2 * this.u + this.u;
                    this.q = n3 + n2 * this.u + this.u + this.u > this.c() + this.e() - 2 ? n3 - (n3 + this.u + this.u - this.e() - 2) : n3;
                }
            } else {
                if (this.q + n2 * this.u + this.u > this.e() - 2) {
                    this.q = this.e() - 2 - n2 * this.u - this.u;
                }
                if (n2 < this.j.d() - 1 && this.q + n2 * this.u + this.u + this.u > this.e() - 2) {
                    n3 = this.e() - 2 - n2 * this.u - this.u - this.u;
                    this.q = n3 + n2 * this.u < 2 ? 2 - n2 * this.u : n3;
                }
            }
        }
        this.m = n2;
    }

    public final void a() {
        if (this.G != null) {
            fc fc2 = this;
            int n2 = fc2.D;
            this.G.d(-1, n2);
            if (n2 == this.A || n2 == this.B) {
                return;
            }
        }
        this.c(v.u);
        this.t = this.d() - this.f();
        this.p = 0;
        this.n = true;
        fc fc3 = this;
        int n3 = fc3.u * fc3.j.d();
        if (n3 <= fc3.e()) {
            fc3.q = (fc3.e() - n3) / 2;
            return;
        }
        fc3.q = (fc3.e() - fc3.u) / 2 - fc3.l * fc3.u;
        if (fc3.q > 2) {
            fc3.q = 2;
            return;
        }
        if (fc3.q + fc3.j.d() * fc3.u < fc3.e()) {
            fc3.q = -fc3.j.d() * fc3.u + fc3.e() - 2;
        }
    }

    private void t() {
        this.t = v.u;
        this.n = false;
        this.o = 0;
        if (this.m != this.l) {
            this.j(this.m);
        }
        v.c();
    }

    private int l(int n2) {
        int n3 = 0;
        while (n3 < this.j.d()) {
            fb fb2 = (fb)this.j.b(n3);
            if (fb2.i == n2) {
                return n3;
            }
            ++n3;
        }
        return -1;
    }

    public final boolean b(fb fb2) {
        return this.j.c(fb2) >= 0;
    }

    public final fb q() {
        if (this.j.d() <= 0) {
            return null;
        }
        return (fb)this.j.b(this.l);
    }

    public final fb h(int n2) {
        n2 = this.l(100);
        if (n2 >= 0) {
            return (fb)this.j.b(n2);
        }
        return null;
    }

    public final void c(fb fb2) {
        if (fb2 == null || this.b(fb2)) {
            return;
        }
        if (fb2.o) {
            this.k.a(fb2);
        }
        if (fb2.i == 107 && this.z == null) {
            this.z = f.d("/notificationnewsicon");
        }
        int[] nArray = new int[this.j.d() + 1];
        fb fb3 = this.q();
        int n2 = 0;
        while (n2 < this.j.d()) {
            fb fb4 = (fb)this.j.b(n2);
            if (fb2.i == fb4.i) {
                throw new ArrayIndexOutOfBoundsException("\u0110\u00e3 t\u1ed3n t\u1ea1i 1 gametab c\u00f3 id tr\u00f9ng v\u1edbi id c\u1ee7a gametab m\u00e0 b\u1ea1n mu\u1ed1n th\u00eam v\u00e0o");
            }
            if (fb2.i < fb4.i) {
                System.arraycopy(this.w, 0, nArray, 0, n2);
                System.arraycopy(this.w, n2, nArray, n2 + 1, this.w.length - n2);
                this.w = nArray;
                this.j.b(fb2, n2);
                fb2.u();
                fb2.a(this);
                if (fb3 != null && this.q() != null && fb3.i != this.q().i || this.q() == null) {
                    this.a(fb3);
                }
                return;
            }
            ++n2;
        }
        System.arraycopy(this.w, 0, nArray, 0, this.w.length);
        this.w = nArray;
        this.j.a(fb2);
        fb2.u();
        fb2.a(this);
        if (fb3 != null && (this.q() == null || this.q() != null && fb3.i != this.q().i)) {
            this.a(fb3);
        }
    }

    public final boolean i(int n2) {
        n2 = 0;
        while (n2 < this.j.d()) {
            fb fb2 = (fb)this.j.b(n2);
            if (100 == fb2.i) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public final void d(fb fb2) {
        int n2 = this.j.c(fb2);
        this.f(fb2);
        if (fb2.i == 107) {
            this.z = null;
        }
        if (n2 >= 0) {
            int[] nArray = new int[this.w.length - 1];
            System.arraycopy(this.w, 0, nArray, 0, n2);
            System.arraycopy(this.w, n2 + 1, nArray, n2, nArray.length - n2);
            this.w = nArray;
            Object object = this.j.a(n2);
            if (object != null) {
                fb2.a((aq)null);
            }
            if (this.j.d() <= 0) {
                this.m = 0;
                this.l = 0;
                this.t();
                return;
            }
            int n3 = this.l(this.v);
            if (n3 >= 0) {
                this.j(n3);
                return;
            }
            if (this.l >= this.j.d()) {
                this.l = this.j.d() - 1;
            }
            this.j(this.l);
        }
    }

    public final void r() {
        int n2 = 0;
        while (n2 < this.j.d()) {
            if (this.j.b(n2) != null) {
                fb fb2 = (fb)this.j.b(n2);
                fb2.a((aq)null);
            }
            ++n2;
        }
        this.j.a();
        this.m = 0;
        this.l = 0;
        this.w = new int[0];
        this.t();
    }

    public final boolean f(int n2) {
        if (this.n) {
            int n3 = this.m;
            switch (n2) {
                case 97: {
                    if (--n3 >= 0) break;
                    n3 = this.j.d() - 1;
                    break;
                }
                case 96: {
                    if (++n3 < this.j.d()) break;
                    n3 = 0;
                    break;
                }
                case 95: {
                    this.t();
                    return true;
                }
                default: {
                    this.t();
                    if (n2 != 94 && n2 != 93) {
                        fb fb2 = this.q();
                        fb2.h(n2);
                    }
                    return true;
                }
            }
            if (n3 != this.m) {
                this.k(n3);
                this.o = 7;
                this.p = 0;
                return true;
            }
        }
        if (!this.n) {
            fb fb3;
            if (this.j.d() > 0 && (fb3 = this.q()).h(n2)) {
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
        return this.j.d() > 0 && (fb2 = this.q()).i(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final boolean c(int n2, int n3) {
        block5: {
            if (!this.n) {
                if (this.j.d() <= 0) return false;
                fb fb2 = this.q();
                if (!fb2.g(n2, n3)) return false;
                return true;
            }
            if (n2 >= this.c() && n2 <= this.c() + this.e() && n3 >= this.d() && n3 <= this.d() + this.f()) {
                this.p = 0;
                int n4 = this.d() + 8 + bx.c.a();
                if (n3 < n4) return true;
                if (n3 > n4 + this.f()) return true;
                n3 = this.r;
                n4 = 0;
                while (n4 < this.j.d()) {
                    if (n2 >= n3 && n2 <= n3 + this.u) {
                        if (this.m != n4) {
                            this.k(n4);
                            this.o = 7;
                            this.p = 0;
                            return true;
                        }
                        break block5;
                    }
                    n3 += this.u;
                    ++n4;
                }
                return true;
            }
        }
        this.t();
        return true;
    }

    public final boolean f(int n2, int n3) {
        fb fb2;
        if (this.n) {
            return true;
        }
        return this.j.d() > 0 && (fb2 = this.q()).h(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        if (this.n) {
            int n4 = this.u * this.j.d();
            if (n4 > this.e()) {
                this.p = 0;
                this.q += n2;
                if (this.q >= 0) {
                    this.q = 0;
                } else if (this.q + n4 < this.e()) {
                    this.q = this.e() - n4;
                }
            }
            return true;
        }
        if (this.j.d() > 0) {
            fb fb2 = this.q();
            int n5 = n3;
            n3 = n2;
            fb fb3 = fb2;
            if (fb2.l != null && fb3.l.e(n3, n5) ? true : fb3.e(n3, n5)) {
                return true;
            }
        }
        return false;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.j()) {
            return;
        }
        if (this.j.d() > 0) {
            fb fb2 = this.q();
            fb2.b(graphics, n2, n3);
        }
        if (this.n || this.t != this.d()) {
            int n4 = this.f();
            int n5 = this.e();
            int n6 = this.d();
            int n7 = this.c();
            Object object = graphics;
            fc fc2 = this;
            int n8 = n7 + fc2.H.getWidth() - 1;
            int n9 = n6 + 2;
            int n10 = n5 - fc2.H.getWidth() - fc2.H.getWidth() + 2;
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
            int n11 = n4 - fc2.H.getHeight() + 2 - 1;
            object.setColor(7225619);
            object.drawRect(n8, n9, n10 - 1, n11 - 1);
            object.setColor(13077582);
            object.drawRect(n8 + 1, n9 + 1, n10 - 3, n11 - 3);
            object.setColor(14854509);
            object.drawRect(n8 + 2, n9 + 2, n10 - 5, n11 - 5);
            object.setColor(16764571);
            object.fillRect(n8 + 1, n9 + 3, n10 - 4, n11 - 6);
            object.drawImage(fc2.H, n7, n6, 0);
            object.drawRegion(fc2.H, 0, 0, fc2.H.getWidth(), fc2.H.getHeight(), 2, n7 + n5, n6, 24);
            object.setColor(13077582);
            object.drawLine(n7, n6 + 10, n7, n6 + n4);
            object.setColor(0x333333);
            object.drawLine(n7 + n5 - 1, n6 + 10, n7 + n5 - 1, n6 + n4);
            object.drawLine(n7, n6 + n4 - 1, n7 + n5 - 1, n6 + n4 - 1);
            object.drawImage(fc2.I, n7 + 2, n6 + fc2.H.getHeight() - 1, 0);
            object.drawRegion(fc2.I, 0, 0, fc2.I.getWidth(), fc2.I.getHeight(), 2, n7 + n5 - 2, n6 + fc2.H.getHeight() - 1, 24);
            object.drawRegion(fc2.I, 0, 0, fc2.I.getWidth(), fc2.I.getHeight(), 1, n7 + 2, n6 + n4 - 2, 36);
            object.drawRegion(fc2.I, 0, 0, fc2.I.getWidth(), fc2.I.getHeight(), 3, n7 + n5 - 2, n6 + n4 - 2, 40);
            n7 = this.r;
            int n12 = this.d() + 4;
            if (this.j.d() <= 0) {
                return;
            }
            object = (fb)this.j.b(this.m);
            if (object.j != null) {
                bx.e.a(graphics, object.j, this.e() / 2, n12 + 1, 1);
            }
            n12 += bx.e.a() + 6;
            cw.a(graphics);
            cw.a(graphics, this.c() + 3, this.d(), this.e() - 6, this.f());
            int n13 = this.s.getWidth();
            n6 = this.s.getHeight();
            n5 = 0;
            while (n5 < this.j.d()) {
                fb fb3 = (fb)this.j.b(n5);
                if (n5 == this.m) {
                    graphics.drawRegion(this.s, 0, 0, n13, n6, 0, n7, n12, 0);
                }
                pc.b(graphics, n7 + this.u / 2, n12 + n6 / 2, fb3.n, 3);
                if (this.w[n5] > 0) {
                    graphics.drawImage(this.x, n7 + this.u - 10, n12, 0);
                }
                n7 += this.u;
                ++n5;
            }
            cw.b(graphics);
            return;
        }
        if (this.E < 16) {
            if (this.D == this.A) {
                int n14 = this.d() - 36;
                n3 = this.c() + this.e() - 16;
                Graphics graphics2 = graphics;
                pc.d(graphics2, -21, n3, n14, 0);
                pc.d(graphics2, -23, n3 - 3, n14 - 3, 0);
                return;
            }
            if (this.D == this.B) {
                graphics.drawImage(this.y, this.c() + this.e() - 16, this.d() - 36, 0);
                return;
            }
            if (this.D == this.C && this.z != null) {
                graphics.drawImage(this.z, this.c() + this.e() - 16, this.d() - 36, 0);
            }
        }
    }

    public final void n() {
        int n2;
        if (!this.j()) {
            return;
        }
        if (this.D >= this.A) {
            this.E = this.E >= 20 ? 1 : ++this.E;
            if (this.D == this.B) {
                if (this.F < 500) {
                    ++this.F;
                } else {
                    this.s();
                }
            }
        }
        if (this.n) {
            if (this.p < 50) {
                ++this.p;
            } else {
                this.t();
            }
        }
        if (this.o > 0) {
            --this.o;
            if (this.o == 0 && this.l != this.m) {
                this.j(this.m);
            }
        }
        if (this.t != this.d()) {
            int n3;
            int n4 = this.d();
            n2 = n4 > this.t ? 1 : 0;
            n4 = n2 != 0 ? (n4 -= 20) : (n4 += 20);
            int n5 = n3 = n4 > this.t ? 1 : 0;
            if (n2 != n3) {
                n4 = this.t;
            }
            this.c(n4);
            fb fb2 = this.q();
            if (fb2 != null) {
                fb2.c(true);
            }
        }
        if (this.q != this.r) {
            int n6 = this.q - this.r;
            if (Math.abs(n6) > 3) {
                n6 /= 3;
            }
            this.r += n6;
        }
        if (!this.n && this.j.d() > 0) {
            fb fb3 = this.q();
            fb3.q();
        }
        n2 = 0;
        while (n2 < this.k.d()) {
            fb fb4 = (fb)this.k.b(n2);
            if (!fb4.equals(this.q())) {
                fb4.q();
            }
            ++n2;
        }
    }

    public final void a(String string) {
        this.D = this.B;
        this.F = 1;
        this.i = string;
    }

    public final void s() {
        this.E = 0;
        this.D = -1;
        int n2 = 0;
        while (n2 < this.w.length) {
            switch (this.w[n2]) {
                case 105: {
                    this.D = this.A;
                    break;
                }
                case 107: {
                    this.D = this.C;
                }
            }
            ++n2;
        }
    }

    public final void e(fb fb2) {
        int n2 = this.j.c(fb2);
        if (n2 < 0) {
            return;
        }
        if (n2 != this.l) {
            this.w[n2] = fb2.i;
            if (this.D == this.B) {
                return;
            }
            if (fb2.i == 105) {
                this.D = this.A;
                return;
            }
            if (fb2.i == 107) {
                this.D = this.C;
            }
        }
    }

    public final void f(fb fb2) {
        if (fb2 == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.w.length) {
            if (fb2.i == this.w[n2]) {
                this.w[n2] = -1;
            }
            switch (this.w[n2]) {
                case 1: {
                    this.D = this.A;
                    break;
                }
                case 3: {
                    this.D = this.C;
                }
            }
            ++n2;
        }
    }

    public final void a(bf bf2) {
        this.G = bf2;
    }
}

