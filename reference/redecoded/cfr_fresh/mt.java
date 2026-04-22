/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mt
implements mr {
    private ms h;
    public mh a;
    private ml i;
    private Image j;
    private Image k;
    private a[] l;
    private a m;
    private a n;
    private a o;
    private a p;
    private mm q;
    public mx b;
    private k r;
    private na s;
    private y t;
    public y c;
    private y u;
    private nm[][] v;
    private gz w;
    public ff d;
    private mj z;
    public np e;
    private mv A;
    lz f = new lz(30, 30, 30, 10);
    private io[] B;
    private in[] C;
    public hl g;
    private String[][] D = new String[][]{{"\u0110\u1ea1i hi\u1ec7p tha m\u1ea1ng!!", "Em v\u00f4 t\u1ed9i T.T", "Hey! C\u00f3 g\u00ec t\u1eeb t\u1eeb n\u00f3i...", "Eck eck...S\u1ee3 qu\u00e1!!"}};

    public mt(ms object) {
        try {
            this.h = object;
            this.r = new k();
            this.m();
        }
        catch (Exception exception) {
            object = exception;
            exception.printStackTrace();
        }
        this.f.b(false);
    }

    public final void a(String string) {
        if (string == null || string.length() <= 0) {
            return;
        }
        this.z.a(string, 0);
        if (!this.h.a((int)1, (int)0).a().O) {
            if (com.mg.sq.a.m != null) {
                com.mg.sq.a.m.a(this.h.a(1, 0).j(), string);
                return;
            }
            String[] stringArray = new String[this.h.a()[1].length];
            int n2 = 0;
            while (n2 < stringArray.length) {
                stringArray[n2] = this.h.a()[1][n2].j();
                ++n2;
            }
            ks.a().a(stringArray, string);
        }
    }

    public final void b(String string) {
        this.z.a(string, 1);
    }

    public final void a(String string, int n2) {
        this.z.a(string, 1, 50, 20);
    }

    public final void a() {
        if (this.h.a(1, 0).b()) {
            return;
        }
        if (!this.d.i()) {
            this.d.a(true);
            if (v.z) {
                this.d.g(true);
            }
        }
    }

    public final void b() {
        this.d.a(false);
        ag.a().e();
    }

    public final void a(lm[] lmArray) {
        this.g = new hl(lmArray, new bd("D\u00f9ng", 1008), new bd("\u0110\u00f3ng", 1009), new bd("", 1008));
        this.g.c(true);
        this.g.b(true);
    }

    public final void c() {
        this.B = null;
        this.n = null;
        this.m = null;
        this.o = null;
        this.t = null;
        this.u = null;
        this.v = null;
        this.c = null;
        this.s = null;
        this.a = null;
        this.z = null;
        this.w = null;
        this.p = null;
        this.b.b = null;
        this.b = null;
        if (this.h != null) {
            this.h.g();
        }
        this.h = null;
        this.g = null;
        if (this.A != null) {
            this.A.c();
        }
        this.A = null;
        this.k = null;
        this.j = null;
        this.i = null;
        this.q = null;
        this.C = null;
        this.l = null;
        this.d = null;
    }

    private void m() {
        int n2;
        this.A = new mv(this, gr.r);
        this.m = new a();
        this.B = new io[2];
        this.C = new in[2];
        this.l = new a[2];
        int n3 = 0;
        while (n3 < this.l.length) {
            this.l[n3] = new a();
            ++n3;
        }
        this.v = new nm[2][];
        this.v[0] = new nm[oq.i.length];
        this.v[1] = new nm[oq.j.length];
        n3 = 0;
        while (n3 < this.v.length) {
            int n4 = 0;
            while (n4 < this.v[n3].length) {
                this.v[n3][n4] = new nm();
                this.v[n3][n4].a(com.mg.sq.a.h);
                this.v[n3][n4].a(40);
                ++n4;
            }
            ++n3;
        }
        this.n = new a();
        this.o = new a();
        this.p = new a();
        this.s = new na(v.t / 2, v.u / 2 - 15);
        this.t = new y();
        this.t.a(40);
        this.c = new y();
        this.c.a(Integer.MAX_VALUE);
        this.u = new y();
        this.u.a(20);
        this.k = mp.a().b;
        this.a = new mh();
        mt mt2 = this;
        mt mt3 = this;
        this.a.c((v.t - mt2.A.a()) / 2 + (com.mg.sq.a.k == 0 ? 8 : 48) + 14, (v.u - mt3.A.b()) / 2 + 4 + 14);
        this.i = new ml();
        mr mr2 = this.a.a(0, 0);
        this.i.a(mr2.n(), mr2.o());
        Object object = this.h.a();
        mr2 = this.a;
        mt mt4 = this;
        mt mt5 = this;
        this.b = new mx(this.h, ((mh)mr2).a, com.mg.sq.a.k, (v.t - mt4.A.a()) / 2, (v.u - mt5.A.b()) / 2);
        this.j = mp.a().a;
        this.q = new mm();
        if (oq.o != 9) {
            mr2 = this;
            n2 = 0;
            while (n2 < object[0].length) {
                if (object[0][n2].j().equals(go.k.b)) {
                    object = object[0][n2].a();
                    if (object.E.length == 0) break;
                    n2 = 0;
                    while (n2 < object.E.length) {
                        int n5 = 0;
                        block5: while (n5 < go.k.E.length) {
                            if (object.E[n2].a == go.k.E[n5].a) {
                                object.E[n2] = go.k.E[n5];
                                n5 = 0;
                                while (n5 < go.r.length) {
                                    if (go.r[n5].a == object.E[n2].a) {
                                        object.E[n2].e = go.r[n5].c[object.E[n2].f - 1].d;
                                        break block5;
                                    }
                                    ++n5;
                                }
                                break;
                            }
                            ++n5;
                        }
                        ++n2;
                    }
                    ((mt)mr2).w = new gz((lh)object);
                    ((mt)mr2).w.b(true);
                    ((mt)mr2).w.c(true);
                    break;
                }
                ++n2;
            }
        }
        this.d = new ff(null, 255, 2);
        int n6 = v.t;
        this.d.a(0, v.u - 20, n6, 20);
        this.d.h(30);
        this.d.a(new mu(this));
        this.d.d(true);
        this.b();
        this.e = new np();
        Object object2 = this.a;
        object2 = ((mh)object2).a;
        int n7 = this.b.h()[0];
        n2 = this.b.g()[0];
        this.z = new mj(n7, this.b.h()[1], (n2 += this.b.g()[2]) - n7, 20);
        this.z.a(this.b.g()[1] - this.b.h()[1]);
        if (com.mg.sq.a.k == 0) {
            this.e.c(((k)object2).a + ((k)object2).c / 2 - 5, ((k)object2).b + ((k)object2).d + 10);
            return;
        }
        this.e.c(this.b.e()[0], this.b.e()[1] + this.b.e()[3] + 6);
    }

    public final void d() {
        try {
            this.a.i();
            this.i.i();
            int n2 = this.m.d() - 1;
            while (n2 >= 0) {
                if (this.m.b(n2) != null) {
                    if (!((ne)this.m.b(n2)).m()) {
                        this.m.a(n2);
                    } else if (this.m.b(n2) != null) {
                        ((ne)this.m.b(n2)).i();
                    }
                }
                --n2;
            }
            n2 = this.n.d() - 1;
            while (n2 >= 0) {
                if (this.n.b(n2) != null) {
                    if (!((nb)this.n.b(n2)).m()) {
                        this.n.a(n2);
                    } else if (this.n.b(n2) != null) {
                        ((nb)this.n.b(n2)).i();
                    }
                }
                --n2;
            }
            n2 = this.o.d() - 1;
            while (n2 >= 0) {
                if (this.o.b(n2) != null) {
                    if (!((im)this.o.b(n2)).m()) {
                        this.o.a(n2);
                    } else if (this.o.b(n2) != null) {
                        ((im)this.o.b(n2)).i();
                    }
                }
                --n2;
            }
            n2 = this.p.d() - 1;
            while (n2 >= 0) {
                if (this.p.b(n2) != null) {
                    ((nh)this.p.b(n2)).i();
                }
                --n2;
            }
            n2 = 0;
            while (n2 < this.v.length) {
                int n3 = 0;
                while (n3 < this.v[n2].length) {
                    this.v[n2][n3].b();
                    ++n3;
                }
                ++n2;
            }
            n2 = 0;
            while (n2 < this.B.length) {
                if (this.B[n2] != null) {
                    this.B[n2].i();
                }
                ++n2;
            }
            n2 = 0;
            while (n2 < this.C.length) {
                if (this.C[n2] != null) {
                    this.C[n2].i();
                }
                ++n2;
            }
            n2 = 0;
            while (n2 < this.l.length) {
                a a2 = this.l[n2];
                if (a2 != null) {
                    int n4 = 0;
                    while (n4 < a2.d()) {
                        if (a2.b(n4) != null) {
                            ((ip)a2.b(n4)).i();
                        }
                        ++n4;
                    }
                }
                ++n2;
            }
            this.q.i();
            this.b.i();
            this.s.i();
            this.t.b();
            this.c.b();
            this.u.b();
            this.e.i();
            this.d.n();
            this.z.i();
            if (this.f != null) {
                this.f.i();
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void a(Graphics graphics) {
        try {
            block24: {
                block23: {
                    mt mt2 = this;
                    if (v.t > mt2.A.a()) break block23;
                    mt mt3 = this;
                    if (v.u <= mt3.A.b()) break block24;
                }
                graphics.setColor(0);
                mt mt4 = this;
                graphics.fillRect(0, 0, v.t, (v.u - mt4.A.b()) / 2);
                mt mt5 = this;
                mt mt6 = this;
                graphics.fillRect(0, (v.u + mt5.A.b()) / 2, v.t, (v.u - mt6.A.b()) / 2);
                mt mt7 = this;
                mt mt8 = this;
                mt mt9 = this;
                graphics.fillRect(0, (v.u - mt7.A.b()) / 2, (v.t - mt8.A.a()) / 2, mt9.A.b());
                mt mt10 = this;
                mt mt11 = this;
                mt mt12 = this;
                mt mt13 = this;
                graphics.fillRect((v.t + mt10.A.a()) / 2, (v.u - mt11.A.b()) / 2, (v.t - mt12.A.a()) / 2, mt13.A.b());
            }
            mt mt14 = this;
            int n2 = (v.t - mt14.A.a()) / 2;
            mt mt15 = this;
            int n3 = (v.u - mt15.A.b()) / 2;
            mt mt16 = this;
            mt mt17 = this;
            graphics.setClip(n2, n3, mt16.A.a(), mt17.A.b());
            this.A.a(graphics, n2, n3, 0);
            cw.a(graphics, this.r);
            at at2 = this.a;
            cw.a(graphics, at2.a, this.r);
            this.a.a(graphics);
            cw.c(graphics, this.r);
            this.i.a(graphics);
            if (this.f != null) {
                this.f.a(graphics);
            }
            try {
                if (this.q != null) {
                    this.q.a(graphics);
                }
            }
            catch (Exception exception) {
                ct.a("[SQCViewManager] loi view" + exception.toString());
            }
            this.b.a(graphics);
            this.t.a(graphics, 0, 0);
            this.c.a(graphics, 0, 0);
            this.u.a(graphics, 0, 0);
            n3 = 0;
            while (n3 < this.l.length) {
                a a2 = this.l[n3];
                nk nk2 = this.b.a[n3];
                int n4 = nk2.n() + (nk2.p() - a2.d() * 17) / 2;
                int n5 = nk2.o() - 15;
                int n6 = 0;
                while (n6 < a2.d()) {
                    at2 = (ip)a2.b(n6);
                    ((ip)at2).a(graphics, n4 + (n3 == 0 ? nk2.p() / 2 - 20 : -nk2.p() / 2 + 20), n5);
                    n4 += 15;
                    ++n6;
                }
                ++n3;
            }
            n3 = 0;
            while (n3 < this.m.d()) {
                ((ne)this.m.b(n3)).a(graphics, 0, 0);
                ++n3;
            }
            n3 = this.p.d() - 1;
            while (n3 >= 0) {
                ((nh)this.p.b(n3)).a(graphics);
                --n3;
            }
            n3 = 0;
            while (n3 < this.n.d()) {
                ((nb)this.n.b(n3)).a(graphics, 0, 0);
                ++n3;
            }
            n3 = 0;
            while (n3 < this.o.d()) {
                ((im)this.o.b(n3)).a(graphics);
                ++n3;
            }
            n3 = 0;
            while (n3 < this.B.length) {
                if (this.B[n3] != null) {
                    this.B[n3].a(graphics);
                }
                ++n3;
            }
            n3 = 0;
            while (n3 < this.C.length) {
                if (this.C[n3] != null) {
                    this.C[n3].a(graphics);
                }
                ++n3;
            }
            if (oq.o != 1) {
                this.e.a(graphics);
            }
            this.z.a(graphics);
            n3 = 0;
            while (n3 < this.v.length) {
                int n7 = 0;
                while (n7 < this.v[n3].length) {
                    this.v[n3][n7].a(graphics, 0, 0);
                    ++n7;
                }
                ++n3;
            }
            this.s.a(graphics);
            graphics.setClip(0, 0, v.t, v.u);
            this.d.a(graphics, 0, 0);
            return;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            return;
        }
    }

    public final void a(int n2, int n3, int n4) {
        this.i.a(n2);
        nd nd2 = this.a.a(n3 - 2, n4 - 2);
        this.i.a(nd2.n(), nd2.o());
    }

    public final void b(int n2, int n3, int n4) {
        this.i.a(n2);
        nd nd2 = this.a.a(n3 - 2, n4 - 2);
        if (n2 == 0 && oq.o != 9) {
            this.i.b(nd2.n(), nd2.o());
            return;
        }
        this.i.a(nd2.n(), nd2.o());
    }

    public final void a(int n2, int n3) {
        this.i.a(0);
        nd nd2 = this.a.a(n2 - 2, n3 - 2);
        int n4 = 0;
        if (n3 > 2) {
            n4 = 1;
        }
        if (n3 < 9) {
            n4 |= 2;
        }
        if (n2 > 2) {
            n4 |= 4;
        }
        if (n2 < 9) {
            n4 |= 8;
        }
        this.i.b(n4, nd2.n(), nd2.o());
    }

    private static int b(int n2, int n3, int n4, int n5) {
        int n6 = 0;
        if (n3 < n5) {
            n6 = 2;
        } else if (n3 > n5) {
            n6 = 1;
        } else if (n2 < n4) {
            n6 = 8;
        } else if (n2 > n4) {
            n6 = 4;
        }
        return n6;
    }

    public final void a(int n2, int n3, int n4, int n5, int n6) {
        this.i.a(n2);
        nd nd2 = this.a.a(n5 - 2, n6 - 2);
        nd nd3 = this.a.a(n3 - 2, n4 - 2);
        this.i.c(nd3.n(), nd3.o(), nd2.n(), nd2.o());
    }

    public final void e() {
        this.f.b(false);
    }

    public final void b(int n2, int n3, int n4, int n5, int n6) {
        n5 = mt.b(n3, n4, n5, n6);
        this.i.a(n2);
        nd nd2 = this.a.a(n3 - 2, n4 - 2);
        this.i.b(n5, nd2.n(), nd2.o());
    }

    public final void a(my my2) {
        int n2;
        int n3 = my2.a;
        int n4 = my2.b;
        if ((my2.e[0] & 0xFF) >= 3 || (my2.e[1] & 0xFF) >= 3) {
            n2 = mt.b(my2.c, my2.d, my2.a, my2.b);
            n3 = my2.c;
            n4 = my2.d;
        } else {
            n2 = mt.b(my2.a, my2.b, my2.c, my2.d);
        }
        int n5 = (n4 - 2) * 28 + this.a.n();
        n3 = (n3 - 2) * 28 + this.a.o();
        this.q.b(n5, n3, n2);
    }

    public final void f() {
        this.q.a();
    }

    public final void g() {
        this.i.a();
    }

    public final int h() {
        int n2 = 0;
        int n3 = 0;
        while (n3 < 8) {
            int n4 = n2 + 0;
            int n5 = 0;
            while (n5 < 8) {
                nd nd2 = this.a.a(n3, n5);
                nd2.i(28, n4);
                ++n4;
                ++n5;
            }
            ++n2;
            ++n3;
        }
        return 22;
    }

    public final int i() {
        int n2 = 0;
        nj[][] njArray = this.h;
        njArray = this.h.l;
        int[] nArray = new int[12];
        int n3 = 2;
        while (n3 < 10) {
            int n4 = 0;
            int n5 = 9;
            while (n5 >= 2) {
                this.a(n4, n3, n5, n3, njArray[n5][n3], nArray[n3] + n2);
                int n6 = n3;
                nArray[n6] = nArray[n6] + 1;
                --n4;
                --n5;
            }
            ++n2;
            ++n3;
        }
        return 21;
    }

    public final void a(int n2, int n3, nj nj2, int n4) {
        this.a.a(n2, n3, nj2, n4);
    }

    public final void a(int n2, int n3, int n4, int n5, nj nj2, nj nj3) {
        this.a.a(n2, n3, n4, n5, nj2, nj3);
    }

    public final void a(int n2, int n3, int n4, int n5, nj nj2, int n6) {
        this.a.a(n2, n3, n4, n5, nj2, n6);
    }

    public final int a(mw mw2, int n2) {
        int n3 = 0;
        if (mw2.k) {
            int n4;
            Object object;
            if ((mw2.a.e & 1) != 0) {
                int[] nArray;
                int n5 = n2 = (n2 + 1) % 2;
                object = mw2;
                mt mt2 = this;
                if (n5 == 0) {
                    int[] nArray2 = new int[4];
                    nArray2[0] = mt2.b.a[0].n();
                    nArray2[1] = mt2.b.a[0].o();
                    nArray2[2] = mt2.b.a[0].p();
                    nArray = nArray2;
                    nArray2[3] = mt2.b.a[0].q();
                } else {
                    int[] nArray3 = new int[4];
                    nArray3[0] = mt2.b.a[1].n();
                    nArray3[1] = mt2.b.a[1].o();
                    nArray3[2] = mt2.b.a[1].p();
                    nArray = nArray3;
                    nArray3[3] = mt2.b.a[1].q();
                }
                int[] nArray4 = nArray;
                int n6 = nArray[0] + nArray4[2] / 2 + cv.a() % 10;
                int n7 = nArray4[1] + nArray4[3] / 2 + cv.a() % 10;
                if (((mw)object).d == 1 && ((mw)object).g == 1) {
                    mt2.a((mw)object, n6, n7);
                } else if (((mw)object).d == 1) {
                    mt2.b((mw)object, n6, n7);
                } else if (((mw)object).g == 1) {
                    mt2.a((mw)object, n6, n7);
                } else {
                    mt2.a((mw)object, n6, n7);
                    mt2.b((mw)object, n6, n7);
                }
                mt2.a(n5, 36, 26, true);
            }
            nj nj2 = mw2.a;
            object = null;
            k k2 = new k();
            switch (nj2.e) {
                case 1: 
                case 16: 
                case 32: 
                case 64: {
                    if (n2 == 0) {
                        this.b.a[0].g();
                        break;
                    }
                    this.b.a[1].g();
                    break;
                }
                case 2: 
                case 4: 
                case 8: {
                    if ((nj2.e & 2) != 0) {
                        object = n2 == 0 ? this.b.a() : this.b.b();
                    } else if ((nj2.e & 4) != 0) {
                        object = n2 == 0 ? this.b.c() : this.b.d();
                    } else if ((nj2.e & 8) != 0) {
                        object = n2 == 0 ? this.b.e() : this.b.f();
                    }
                    if (object == null) break;
                    k2.a = (int)(object[0] + object[2] / 2);
                    k2.b = (int)(object[1] + object[3] / 2);
                    k2.c = (int)(object[2] - 2);
                    k2.d = (int)(object[3] - 2);
                }
            }
            this.a.n();
            this.a.o();
            n3 = mw2.d == 1 && mw2.g == 1 ? this.a(mw2) : (mw2.d < 3 ? this.a(mw2) : (mw2.g < 3 ? this.b(mw2) : ((n2 = this.a(mw2)) > (n4 = this.b(mw2)) ? n2 : n4)));
        }
        return n3;
    }

    private int a(mw mw2) {
        return this.a.b(mw2);
    }

    private int b(mw mw2) {
        return this.a.a(mw2);
    }

    public final int a(int n2) {
        Image image = null;
        switch (n2) {
            case 1: {
                image = f.d("/strwin");
                break;
            }
            case 0: {
                image = f.d("/strlose");
            }
        }
        return this.s.a(image);
    }

    private void a(int n2, int n3, k k2, nj nj2, int n4, boolean n5) {
        if ((nj2.e & 1) != 0) {
            return;
        }
        try {
            nh nh2 = null;
            n5 = this.p.d() - 1;
            while (n5 >= 0) {
                nh nh3 = (nh)this.p.b(n5);
                if (!nh3.m()) {
                    nh2 = nh3;
                    break;
                }
                --n5;
            }
            if (nh2 == null) {
                nh2 = new nh(this.k);
                this.p.a(nh2);
            }
            nh2.a(n2, n3, k2, nj2.g, 0, false);
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            this.b(true);
            if (com.mg.sq.a.m != null) {
                com.mg.sq.a.m.G();
            }
            return;
        }
    }

    public final void b(int n2, int n3) {
        int[] nArray = null;
        k k2 = new k();
        switch (n3) {
            case 1: {
                nArray = n2 == 0 ? this.b.a() : this.b.b();
                break;
            }
            case 2: {
                nArray = n2 == 0 ? this.b.c() : this.b.d();
                break;
            }
            case 3: {
                int[] nArray2 = nArray = n2 == 0 ? this.b.e() : this.b.f();
            }
        }
        if (nArray != null) {
            k2.a = nArray[0] + nArray[2] / 2;
            k2.b = nArray[1] + nArray[3] / 2;
            k2.c = nArray[2] - 2;
            k2.d = nArray[3] - 2;
            if (com.mg.sq.a.k == 0) {
                n2 = nArray[0] + nArray[2] + 30;
                int n4 = nArray[1] - 50;
                int n5 = 0;
                while (n5 < 9) {
                    if (n5 == 0 || n5 == 8) {
                        this.a(n2 + 30, n4, k2, nj.a(n3), 0, false);
                    } else {
                        this.a(n2, n4, k2, nj.a(n3), 0, false);
                    }
                    n4 += 12;
                    ++n5;
                }
                return;
            }
            n2 = nArray[0] - 50;
            int n6 = nArray[1] - 30;
            int n7 = 0;
            while (n7 < 9) {
                if (n7 == 0 || n7 == 8) {
                    this.a(n2, n6 - 30, k2, nj.a(n3), 0, false);
                } else {
                    this.a(n2, n6, k2, nj.a(n3), 0, false);
                }
                n2 += 12;
                ++n7;
            }
        }
    }

    public final void a(String string, int n2, int n3, boolean bl2) {
        if (n3 == 0) {
            return;
        }
        boolean bl3 = this.h.b;
        boolean bl4 = this.h.e;
        d d2 = com.mg.sq.a.h;
        lg[][] lgArray = this.h.a();
        String string2 = String.valueOf(n3 >= 0 ? (n3 == 0 ? (n2 != 0 ? "-" : "+") : "+") : "") + n3;
        n2 = 0;
        while (n2 < lgArray.length) {
            int n4 = 0;
            while (n4 < lgArray[n2].length) {
                if (lgArray[n2][n4].j().equals(string)) {
                    if ((bl3 || bl2 || bl4) && n3 < 0) {
                        d2 = bx.a;
                        if (bl3 && bl2) {
                            d2.a(12067023);
                        } else if (bl3) {
                            d2.a(6290199);
                        } else if (bl4 && bl2) {
                            d2.a(16753920);
                        } else if (bl4) {
                            d2.a(0xFFFF00);
                        } else {
                            d2.a(2371791);
                        }
                    }
                    this.v[n2][n4].a(string2, this.b.a(n2)[n4].n() + this.b.a(n2)[n4].p() / 2, this.b.a(n2)[n4].o());
                    this.v[n2][n4].a(this.b.a(n2)[n4]);
                    this.v[n2][n4].a(d2);
                    if (n2 == 1 && this.h.a(1, 0).b() && n3 < 0 && this.h.a(1, 0).c() <= 50) {
                        this.a(this.D[0][cv.a(this.D[0].length)], 50);
                    }
                    return;
                }
                ++n4;
            }
            ++n2;
        }
    }

    public final void c(int n2, int n3, int n4, int n5, int n6) {
        ne ne2 = null;
        int n7 = this.m.d() - 1;
        while (n7 >= 0) {
            ne ne3 = (ne)this.m.b(n7);
            if (!ne3.a()) {
                ne2 = ne3;
                break;
            }
            --n7;
        }
        if (ne2 == null) {
            ne2 = new ne(this.j);
            this.m.a(ne2);
        }
        n7 = (n4 + n6 / 2 - 2) * 28 - (n6 % 2 == 0 ? 14 : 4) + this.a.n();
        int n8 = (n3 + n5 / 2 - 2) * 28 - (n5 % 2 == 0 ? 14 : 5) + this.a.o();
        n3 = n2 - 1;
        ne2.a(n7, n8, n3, "x" + n2);
    }

    private void c(int n2, int n3, int n4) {
        this.a(n2, n3, n4, false);
    }

    public final void a(int n2, int n3, int n4, boolean bl2) {
        this.b.a(n2, n3, bl2);
        this.b.c(n4);
    }

    private void a(mw mw2, int n2, int n3) {
        int n4 = mw2.c;
        int n5 = mw2.c + mw2.d;
        while (n4 < n5) {
            nb nb2 = null;
            nd nd2 = this.a.a(mw2.b - 2, n4 - 2);
            int n6 = this.n.d() - 1;
            while (n6 >= 0) {
                nb nb3 = (nb)this.n.b(n6);
                if (!nb3.m()) {
                    nb2 = nb3;
                    break;
                }
                --n6;
            }
            if (nb2 == null) {
                nb2 = new nb(this.k);
                this.n.a(nb2);
            }
            nb2.a(nd2.n(), nd2.o(), n2, n3, mw2.a.d * nb2.q());
            ++n4;
        }
    }

    private void b(mw mw2, int n2, int n3) {
        int n4 = mw2.e;
        int n5 = mw2.e + mw2.g;
        while (n4 < n5) {
            nb nb2 = null;
            nd nd2 = this.a.a(n4 - 2, mw2.f - 2);
            int n6 = this.n.d() - 1;
            while (n6 >= 0) {
                nb nb3 = (nb)this.n.b(n6);
                if (!nb3.m()) {
                    nb2 = nb3;
                    break;
                }
                --n6;
            }
            if (nb2 == null) {
                nb2 = new nb(this.k);
                this.n.a(nb2);
            }
            nb2.a(nd2.n(), nd2.o(), n2, n3, mw2.a.d * nb2.q());
            ++n4;
        }
    }

    private void a(int n2, int n3, boolean bl2, boolean bl3) {
        a a2 = this.l[n2];
        ip ip2 = null;
        int n4 = 0;
        while (n4 < a2.d()) {
            ip ip3 = (ip)a2.b(n4);
            if (ip3.b == bl2 && ip3.a == n3) {
                ip2 = ip3;
                break;
            }
            ++n4;
        }
        if (ip2 == null) {
            a2.a(new ip(n3, bl2, 8));
        }
        if (bl3) {
            this.c(n2);
        }
    }

    public final void a(int n2, int n3, boolean bl2) {
        a a2 = this.l[n2];
        int n4 = 0;
        while (n4 < a2.d()) {
            ip ip2 = (ip)a2.b(n4);
            if (ip2.b == bl2 && ip2.a == n3) {
                a2.a(n4);
                return;
            }
            ++n4;
        }
    }

    private void c(int n2) {
        int n3 = this.h.a((int)n2, (int)0).a().g / 2;
        if (this.C[n2] == null) {
            this.C[n2] = new in(n3);
            int[] nArray = n2 == 0 ? new int[]{this.b.a[0].n(), this.b.a[0].o(), this.b.a[0].p(), this.b.a[0].q()} : new int[]{this.b.a[1].n(), this.b.a[1].o(), this.b.a[1].p(), this.b.a[1].q()};
            this.C[n2].c(nArray[0] + nArray[2] / 2, nArray[1] + nArray[3]);
        }
        this.C[n2].r();
    }

    private void d(int n2) {
        if (this.h.a(n2, 0).b()) {
            return;
        }
        this.h.d = false;
        if (this.B[n2] == null) {
            this.B[n2] = new io(n2, this);
        }
        int[] nArray = n2 == 0 ? new int[]{this.b.a[1].n(), this.b.a[1].o(), this.b.a[1].p(), this.b.a[1].q()} : new int[]{this.b.a[0].n(), this.b.a[0].o(), this.b.a[0].p(), this.b.a[0].q()};
        int n3 = this.h.a((int)n2, (int)0).a().g / 2;
        this.B[n2].a(n3, (ni)this.b.a[n2], nArray[0] + nArray[2] / 2, nArray[1] + nArray[3] / 2);
        this.c(n2);
    }

    public final void b(int n2) {
        if (this.h.a(n2, 0).b()) {
            return;
        }
        if (this.B[n2] != null) {
            this.B[n2].a();
        }
        this.B[n2] = null;
    }

    public final int a(lv lv2, byte[] byArray, byte[] byArray2, byte[] objectArray, byte[] byArray3, int object) {
        Object object2;
        int[] nArray;
        int n2 = object + 1 & 1;
        int n3 = 10;
        int n4 = 0;
        int n5 = -180;
        if (object == 0) {
            n5 = 180;
        }
        int n6 = this.a.a(0, 0).p() >> 1;
        int n7 = this.a.a(0, 0).q() >> 1;
        if (object == 0) {
            nArray = new int[]{this.b.a[1].n(), this.b.a[1].o(), this.b.a[1].p(), this.b.a[1].q()};
            object2 = new int[]{this.b.a[0].n(), this.b.a[0].o(), this.b.a[0].p(), this.b.a[0].q()};
        } else {
            nArray = new int[]{this.b.a[0].n(), this.b.a[0].o(), this.b.a[0].p(), this.b.a[0].q()};
            object2 = new int[]{this.b.a[1].n(), this.b.a[1].o(), this.b.a[1].p(), this.b.a[1].q()};
        }
        switch (lv2.a) {
            case 1000: {
                object = nArray[0] + nArray[2] / 2;
                int n8 = nArray[1] + nArray[3] / 2;
                lv lv3 = lv2;
                n4 = this.a(0, lv3.a, object - n5, n8 - 180, (int)object, n8, 10);
                this.a(n2, 32, 22, false);
                n3 = 10 + (cv.a(7) + 5);
                int n9 = 0;
                while (n9 < byArray3.length) {
                    object2 = this.a.a(objectArray[n9] - 2, byArray3[n9] - 2);
                    object = ((at)object2).n() + n6;
                    n8 = ((at)object2).o() + n7;
                    int n10 = 10 + cv.a(15);
                    lv3 = lv2;
                    n4 = this.a(n4, lv3.a, object - n5, n8 - 180, (int)object, n8, n10);
                    if (n10 > n3) {
                        n3 = n10;
                    }
                    ++n9;
                }
                break;
            }
            case 1001: {
                if (byArray == null) break;
                int n11 = 0;
                while (n11 < byArray.length) {
                    object2 = this.a.a(byArray[n11] - 2, byArray2[n11] - 2);
                    this.a((int)byArray[n11], (int)byArray2[n11], nj.a(10), n3 + 5);
                    object = ((at)object2).n();
                    int n12 = ((at)object2).o();
                    lv lv4 = lv2;
                    n4 = this.a(n4, lv4.a, (int)object, n12, (int)object, n12, n3);
                    n3 += 4;
                    ++n11;
                }
                n3 -= 4;
                break;
            }
            case 1002: {
                this.a((int)object, 1, true, true);
                break;
            }
            case 1003: {
                this.d((int)object);
                n3 += 30;
                break;
            }
            case 1004: {
                n5 = nArray[0] + nArray[2] / 2;
                n4 = nArray[1] + nArray[3];
                lv lv5 = lv2;
                this.a(0, lv5.a, n5, n4, n5, n4, 0);
                this.c(n2, 26, 16);
                break;
            }
            case 1005: {
                object = object2[0] + object2[2] / 2;
                int n13 = object2[1] + object2[3] / 2;
                n5 = nArray[0] + nArray[2] / 2;
                n4 = nArray[1] + nArray[3] / 2;
                lv lv6 = lv2;
                this.a(0, lv6.a, (int)object, n13, n5, n4, 4);
                this.c(n2, 20, 10);
                break;
            }
            case 1006: {
                object = nArray[0] + nArray[2] / 2;
                int n14 = nArray[1] + nArray[3];
                lv lv7 = lv2;
                n4 = this.a(0, lv7.a, object - n5, n14 - 180, (int)object, n14, 10);
                this.c(n2, 36, 26);
                int n15 = 0;
                while (n15 < byArray3.length) {
                    int n16 = cv.a(7) + 4;
                    object2 = this.a.a(objectArray[n15] + 1 - 2, byArray3[n15] + 1 - 2);
                    object = ((at)object2).n();
                    n14 = ((at)object2).o();
                    lv7 = lv2;
                    n4 = this.a(n4, lv7.a, object - n5, n14 - 180, (int)object, n14, n16);
                    if (n16 > n3) {
                        n3 = n16;
                    }
                    ++n15;
                }
                break;
            }
            case 1007: {
                object = nArray[0] + nArray[2] / 2;
                int n17 = nArray[1] + nArray[3];
                lv lv8 = lv2;
                n4 = this.a(0, lv8.a, (int)object, n17, (int)object, n17, 10);
                this.c(n2, 30, 11);
                int n18 = 0;
                while (n18 < byArray3.length) {
                    int n19 = cv.a(7) + 10;
                    object2 = this.a.a(objectArray[n18] + 4 - 1 - 2, byArray3[n18] + 2 - 1 - 2);
                    object = ((at)object2).n() + n6;
                    n17 = ((at)object2).o() + n7;
                    lv8 = lv2;
                    n4 = this.a(n4, lv8.a, (int)object, n17, (int)object, n17, n19);
                    if (n19 > n3) {
                        n3 = n19;
                    }
                    ++n18;
                }
                break;
            }
            case 1008: {
                this.c(n2, 10, 4);
                nd nd2 = this.a.a(7, byArray3[0] + 1 - 2);
                object2 = nd2;
                object = nd2.n() + n6;
                int n20 = ((at)object2).o() + n7;
                lv lv9 = lv2;
                this.a(0, lv9.a, (int)object, n20, (int)object, 0, 10);
                break;
            }
            case 2000: {
                int[] nArray2 = new int[byArray.length + 1];
                int[] nArray3 = new int[byArray.length + 1];
                objectArray = nArray3;
                int n21 = nArray3.length - 1;
                object = 0;
                while (object < n21) {
                    object2 = this.a.a(byArray[object] - 2, byArray2[object] - 2);
                    nArray2[object] = ((at)object2).n();
                    objectArray[object] = ((at)object2).o();
                    ++object;
                }
                n3 = 10 + (objectArray.length * 5 + 5);
                this.c(n2, n3, n3 - 6);
                object = nArray[0] + nArray[2] / 2;
                int n22 = nArray[1] + nArray[3] / 2;
                nArray2[nArray2.length - 1] = object;
                objectArray[nArray2.length - 1] = n22;
                lv lv10 = lv2;
                n4 = this.a(0, lv10.a, (int)object, n22, (int)object, n22, n3);
                jg jg2 = (jg)this.o.b(n4);
                jg2.a(nArray2, (int[])objectArray);
                break;
            }
            case 2001: {
                this.a((int)object, 0, true, true);
                break;
            }
            case 2002: {
                this.a((int)object, 2, true, true);
                break;
            }
            case 2003: {
                object = nArray[0] + nArray[2] / 2;
                int n23 = nArray[1] + nArray[3] / 2;
                lv lv11 = lv2;
                this.a(0, lv11.a, object - n5, n23 - 180, (int)object, n23, 10);
                this.c(n2, 36, 26);
                n3 = 14;
                break;
            }
            case 2004: {
                int n24 = object2[1] + object2[3] / 2 + 6;
                n5 = nArray[0] + nArray[2] / 2;
                n4 = nArray[1] + nArray[3] / 2 + 6;
                if (object == 0) {
                    n5 += 60;
                    object = object2[0] + object2[2];
                } else {
                    object = object2[0];
                    n5 -= 60;
                }
                lv lv12 = lv2;
                this.a(0, lv12.a, (int)object, n24, n5, n4, 10);
                this.c(n2, 20, 10);
                n3 = 10;
                if (!this.h.a(n2, 0).i()) break;
                this.a(n2, 1, false, false);
                break;
            }
            case 2005: {
                this.d((int)object);
                n3 += 30;
                break;
            }
            case 2006: {
                int n25 = object2[1] + object2[3] / 2 + 4;
                n5 = nArray[0] + nArray[2] / 2;
                mt mt2 = this;
                int n26 = (v.t - mt2.A.a()) / 2;
                if (object == 0) {
                    lv lv13 = lv2;
                    n4 = this.a(0, lv13.a, (n26 -= 56) - cv.a(150), n25, n5, n25, 6);
                    this.c(n2, 16, 10);
                    mt mt3 = this;
                    n5 = n26 + mt3.A.a() + 100;
                    n6 = 0;
                    while (n6 < objectArray.length) {
                        object2 = this.a.a(objectArray[n6] - 2, 0);
                        lv13 = lv2;
                        n4 = this.a(n4, lv13.a, n26 - ((n6 & 1) == 1 ? 40 : 0), ((at)object2).o(), n5, ((at)object2).o(), 6);
                        ++n6;
                    }
                    n25 -= 40;
                    n6 = 0;
                    while (n6 < 3) {
                        lv13 = lv2;
                        n4 = this.a(n4, lv13.a, n26 - cv.a(150), n25, n5, n25, 6);
                        n25 += 20;
                        ++n6;
                    }
                } else {
                    lv lv14 = lv2;
                    mt mt4 = this;
                    n4 = this.a(0, lv14.a, n26 + mt4.A.a() + cv.a(150), n25, n5, n25, 6);
                    this.c(n2, 16, 10);
                    n5 = n26 - 100;
                    mt mt5 = this;
                    n26 += mt5.A.a();
                    n6 = 0;
                    while (n6 < objectArray.length) {
                        object2 = this.a.a(objectArray[n6] - 2, 0);
                        lv14 = lv2;
                        n4 = this.a(n4, lv14.a, n26 + ((n6 & 1) == 1 ? 40 : 0), ((at)object2).o(), n5, ((at)object2).o(), 6);
                        ++n6;
                    }
                    n25 -= 40;
                    n6 = 0;
                    while (n6 < 3) {
                        lv14 = lv2;
                        n4 = this.a(n4, lv14.a, n26 + cv.a(150), n25, n5, n25, 6);
                        n25 += 20;
                        ++n6;
                    }
                }
                n3 = 5;
                break;
            }
            case 2007: {
                lv lv15;
                int n27;
                n6 = 0;
                while (n6 < byArray3.length) {
                    object2 = this.a.a(objectArray[n6] + 1 - 2, byArray3[n6] + 1 - 2);
                    object = ((at)object2).n();
                    n27 = ((at)object2).o() - n7;
                    int n28 = 10 + cv.a(10) + 5;
                    lv15 = lv2;
                    n4 = this.a(n4, lv15.a, (int)object, n27, (int)object, 0, n28 + (n6 << 1));
                    if (n28 > n3) {
                        n3 = n28;
                    }
                    ++n6;
                }
                object = nArray[0] + nArray[2] / 2;
                n27 = nArray[1] + nArray[3] - 10;
                lv15 = lv2;
                this.a(n4, lv15.a, (int)object, n27, (int)object, 0, n3 - 15);
                this.c(n2, n3, n3 - 6);
                break;
            }
            case 2008: {
                n5 = nArray[0] + nArray[2] / 2;
                n4 = nArray[1] + nArray[3] / 2;
                lv lv16 = lv2;
                n4 = this.a(0, lv16.a, 0, 0, n5, n4, 0);
                jl jl2 = (jl)this.o.b(n4);
                ((jl)this.o.b(n4)).s = byArray.length;
                int n29 = 0;
                while (n29 < byArray.length) {
                    object2 = this.a.a(byArray[n29] - 2, byArray2[n29] - 2);
                    jl2.t[n29].c(((at)object2).n(), ((at)object2).o());
                    ++n29;
                }
                this.a(n2, 32, 22, false);
                n3 = 0;
                break;
            }
            case 4000: {
                object = nArray[0] + nArray[2] / 2;
                int n30 = nArray[1] + nArray[3] / 2;
                lv lv17 = lv2;
                n4 = this.a(0, lv17.a, object - n5, n30 - 180, (int)object, n30, 10);
                this.a(n2, 32, 22, false);
                n3 = 10 + (cv.a(7) + 5);
                int n31 = 0;
                while (n31 < byArray3.length) {
                    object2 = this.a.a(objectArray[n31] - 2, byArray3[n31] - 2);
                    object = ((at)object2).n() + n6;
                    n30 = ((at)object2).o() + n7;
                    int n32 = 10 + cv.a(15);
                    lv17 = lv2;
                    n4 = this.a(n4, lv17.a, object - n5, n30 - 180, (int)object, n30, n32);
                    if (n32 > n3) {
                        n3 = n32;
                    }
                    ++n31;
                }
                break;
            }
            case 4001: {
                object = object2[0] + object2[2] / 2;
                int n33 = object2[1] + object2[3] - 10;
                lv lv18 = lv2;
                this.a(0, lv18.a, (int)object, n33, (int)object, n33, 10);
                this.b.c(10);
                n3 = 14;
                break;
            }
            case 4002: {
                object = object2[0] + object2[2] / 2;
                int n34 = object2[1] + object2[3] / 2 + 6;
                n5 = nArray[0] + nArray[2] / 2;
                lv lv19 = lv2;
                this.a(0, lv19.a, (int)object, n34, n5, n34, 10);
                this.a(n2, 1, false, false);
                n3 = 15;
                break;
            }
            case 4003: {
                n5 = nArray[0] + nArray[2] / 2;
                n4 = nArray[1] + nArray[3];
                lv lv20 = lv2;
                this.a(0, lv20.a, n5, n4, n5, n4, 10);
                this.b.a(n2, 10);
                this.c(n2, 15, 16);
                n3 = 13;
                break;
            }
            case 4004: {
                this.d((int)object);
                n3 += 30;
                break;
            }
            case 4005: {
                object = object2[0] + object2[2] / 2;
                int n35 = object2[1] + object2[3] / 2;
                n5 = nArray[0] + nArray[2] / 2;
                n4 = nArray[1] + nArray[3] / 2;
                lv lv21 = lv2;
                this.a(0, lv21.a, (int)object, n35, n5, n4, 4);
                this.c(n2, 26, 16);
                break;
            }
            case 4006: {
                n5 = nArray[0] + nArray[2] / 2;
                n4 = nArray[1] + nArray[3] / 2;
                lv lv22 = lv2;
                n4 = this.a(0, lv22.a, n5, n4 - 180, n5, n4, 0);
                this.c(n2, 20, 10);
                int n36 = 0;
                while (n36 < byArray3.length) {
                    int n37 = cv.a(15) + 10;
                    object2 = this.a.a(objectArray[n36] + 1 - 2, byArray3[n36] + 1 - 2);
                    object = ((at)object2).n();
                    int n38 = ((at)object2).o();
                    lv22 = lv2;
                    n4 = this.a(n4, lv22.a, (int)object, n38 - 180, (int)object, n38, n37);
                    if (n37 > n3) {
                        n3 = n37;
                    }
                    ++n36;
                }
                break;
            }
            case 4007: {
                int n39 = 0;
                while (n39 < byArray3.length) {
                    object2 = this.a.a(objectArray[n39] + 3 - 1 - 2, byArray3[n39] + 1 - 2);
                    object = ((at)object2).n();
                    int n40 = ((at)object2).o() + n7;
                    int n41 = 10 + cv.a(15);
                    lv lv23 = lv2;
                    n4 = this.a(n4, lv23.a, (int)object, n40, (int)object, 0, n41);
                    if (n41 > n3) {
                        n3 = n41;
                    }
                    ++n39;
                }
                this.c(n2, n3, n3 - 6);
                break;
            }
            case 4008: {
                object = nArray[0] + nArray[2] / 2;
                int n42 = nArray[1] + nArray[3] / 2;
                lv lv24 = lv2;
                n4 = this.a(0, lv24.a, object - n5, n42 - 180, (int)object, n42, 10);
                this.a(n2, 32, 22, false);
                n3 = 10 + (cv.a(7) + 5);
                int n43 = 0;
                while (n43 < byArray3.length) {
                    object2 = this.a.a(objectArray[n43] - 2, byArray3[n43] - 2);
                    object = ((at)object2).n() + n6;
                    n42 = ((at)object2).o() + n7;
                    int n44 = 10 + cv.a(20);
                    lv24 = lv2;
                    n4 = this.a(n4, lv24.a, object - n5, n42 - 180, (int)object, n42, n44);
                    if (n44 > n3) {
                        n3 = n44;
                    }
                    ++n43;
                }
                break;
            }
        }
        return n3 - 3;
    }

    private int a(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        im im2;
        int n9;
        int n10;
        block29: {
            int n11 = n3;
            int n12 = n2;
            mt mt2 = this;
            if (n12 < 0) {
                n10 = -1;
            } else {
                while (n12 < mt2.o.d()) {
                    im im3 = (im)mt2.o.b(n12);
                    if (!im3.m() && im3.r() == n11) {
                        n10 = n12;
                        break block29;
                    }
                    ++n12;
                }
                n10 = n9 = -1;
            }
        }
        if (n10 > 0) {
            im2 = (im)this.o.b(n9);
        } else {
            switch (n3) {
                case 1000: {
                    im2 = new is();
                    break;
                }
                case 1001: {
                    im2 = new it();
                    break;
                }
                case 1004: {
                    im2 = new iu();
                    break;
                }
                case 1005: {
                    im2 = new iv();
                    break;
                }
                case 1006: {
                    im2 = new iw();
                    break;
                }
                case 1007: {
                    im2 = new ix();
                    break;
                }
                case 1008: {
                    im2 = new iy();
                    break;
                }
                case 2000: {
                    im2 = new jg();
                    break;
                }
                case 2003: {
                    im2 = new jh();
                    break;
                }
                case 2004: {
                    im2 = new ji();
                    break;
                }
                case 2006: {
                    im2 = new jj();
                    break;
                }
                case 2007: {
                    im2 = new jk();
                    break;
                }
                case 2008: {
                    im2 = new jl();
                    break;
                }
                case 4000: {
                    im2 = new iz();
                    break;
                }
                case 4001: {
                    im2 = new ja();
                    break;
                }
                case 4002: {
                    im2 = new jb();
                    break;
                }
                case 4003: {
                    im2 = new jc();
                    break;
                }
                case 4005: {
                    im2 = new jd();
                    break;
                }
                case 4006: {
                    im2 = new je();
                    break;
                }
                case 4007: {
                    im2 = new jf();
                    break;
                }
                case 4008: {
                    im2 = new iz();
                    break;
                }
                default: {
                    return -1;
                }
            }
            im2.l(n3);
            this.o.a(im2);
            n9 = this.o.d() - 1;
        }
        im2.a(n4, n5, n6, n7, n8);
        return n9;
    }

    public final void a(boolean bl2) {
        this.b.a(bl2);
    }

    public final void a(bf bf2) {
        if (this.w != null) {
            this.w.b(bf2);
            return;
        }
        com.mg.sq.a.s().a("Ki\u1ec3u quy\u1ebft \u0111\u1ea5u: Kh\u00f4ng ch\u01a1i Tuy\u1ec7t Chi\u00eau");
    }

    public final void a(int n2, int n3, int n4, int n5) {
        n4 = 112 + this.a.n() - 14;
        n5 = 112 + this.a.o() - 14;
        this.t.a("C\u00f2n " + n3 + " l\u01b0\u1ee3t", n4, n5);
        if (n2 == 0) {
            this.t.a(bx.c);
            return;
        }
        this.t.a(com.mg.sq.a.h);
    }

    public final void j() {
        Object object = this.a;
        object = ((mh)object).a;
        this.u.a("H\u1ebft n\u01b0\u1edbc \u0111i!", ((k)object).a + ((k)object).c / 2, ((k)object).b + ((k)object).d / 2);
    }

    public final void c(String string) {
        Object object = this.a;
        object = ((mh)object).a;
        this.c.a(string, ((k)object).a + ((k)object).c / 2, ((k)object).b + ((k)object).d / 2);
    }

    public final void k() {
        this.z.a("Vui l\u00f2ng \u0111\u1eebng g\u1eedi qu\u00e1 nhanh!", 0);
    }

    public final void l() {
        this.o.a();
    }

    public final void b(boolean bl2) {
        mt mt2 = this;
        mt2.n.a();
        mt2 = this;
        mt2.m.a();
        mt2 = this;
        mt2.p.a();
        if (bl2) {
            mt mt3 = this;
            mt3.o.a();
        }
        System.gc();
    }
}

