/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mr
implements mp {
    private mq h;
    public mf a;
    private mj i;
    private Image j;
    private Image k;
    private a[] l;
    private a m;
    private a n;
    private a o;
    private a p;
    private mk q;
    public mv b;
    private n r;
    private my s;
    private ac t;
    public ac c;
    private ac u;
    private nk[][] x;
    private gx y;
    public ff d;
    private mh z;
    public nn e;
    private mt A;
    lx f = new lx(30, 30, 30, 10);
    private in[] B;
    private im[] C;
    public hj g;
    private String[][] D = new String[][]{{"\u0110\u1ea1i hi\u1ec7p tha m\u1ea1ng!!", "Em v\u00f4 t\u1ed9i T.T", "Hey! C\u00f3 g\u00ec t\u1eeb t\u1eeb n\u00f3i...", "Eck eck...S\u1ee3 qu\u00e1!!"}};

    public mr(mq object) {
        try {
            this.h = object;
            this.r = new n();
            this.l();
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
        if (!this.h.a((int)1, (int)0).a().Q) {
            if (com.mg.sq.a.k != null) {
                com.mg.sq.a.k.a(this.h.a(1, 0).j(), string);
                return;
            }
            String[] stringArray = new String[this.h.a()[1].length];
            int n2 = 0;
            while (n2 < stringArray.length) {
                stringArray[n2] = this.h.a()[1][n2].j();
                ++n2;
            }
            kq.a().a(stringArray, string);
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
            if (z.x) {
                this.d.g(true);
            }
        }
    }

    public final void b() {
        this.d.a(false);
        ak.a().f();
    }

    public final void a(lk[] lkArray) {
        this.g = new hj(lkArray, new bh("D\u00f9ng", 1008), new bh("\u0110\u00f3ng", 1009), new bh("", 1008));
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
        this.x = null;
        this.c = null;
        this.s = null;
        this.a = null;
        this.z = null;
        this.y = null;
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

    private void l() {
        int n2;
        this.A = new mt(this, gp.r);
        this.m = new a();
        this.B = new in[2];
        this.C = new im[2];
        this.l = new a[2];
        int n3 = 0;
        while (n3 < this.l.length) {
            this.l[n3] = new a();
            ++n3;
        }
        this.x = new nk[2][];
        this.x[0] = new nk[op.i.length];
        this.x[1] = new nk[op.j.length];
        n3 = 0;
        while (n3 < this.x.length) {
            int n4 = 0;
            while (n4 < this.x[n3].length) {
                this.x[n3][n4] = new nk();
                this.x[n3][n4].a(com.mg.sq.a.h);
                this.x[n3][n4].a(40);
                ++n4;
            }
            ++n3;
        }
        this.n = new a();
        this.o = new a();
        this.p = new a();
        this.s = new my(z.r / 2, z.s / 2 - 15);
        this.t = new ac();
        this.t.a(40);
        this.c = new ac();
        this.c.a(Integer.MAX_VALUE);
        this.u = new ac();
        this.u.a(20);
        this.k = mn.a().b;
        this.a = new mf();
        this.a.c((z.r - mt.a()) / 2 + (com.mg.sq.a.i == 0 ? 8 : 48) + 14, (z.s - mt.b()) / 2 + 4 + 14);
        this.i = new mj();
        mp mp2 = this.a.a(0, 0);
        this.i.a(mp2.n(), mp2.o());
        Object object = this.h.a();
        this.b = new mv(this.h, this.a.b(), com.mg.sq.a.i, (z.r - mt.a()) / 2, (z.s - mt.b()) / 2);
        this.j = mn.a().a;
        this.q = new mk();
        if (op.o != 9) {
            mp2 = this;
            n2 = 0;
            while (n2 < object[0].length) {
                if (object[0][n2].j().equals(gr.j.b)) {
                    object = object[0][n2].a();
                    if (object.E.length == 0) break;
                    n2 = 0;
                    while (n2 < object.E.length) {
                        int n5 = 0;
                        block5: while (n5 < gr.j.E.length) {
                            if (object.E[n2].a == gr.j.E[n5].a) {
                                object.E[n2] = gr.j.E[n5];
                                n5 = 0;
                                while (n5 < gr.o.length) {
                                    if (gr.o[n5].a == object.E[n2].a) {
                                        object.E[n2].e = gr.o[n5].c[object.E[n2].f - 1].d;
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
                    ((mr)mp2).y = new gx((lf)object);
                    ((mr)mp2).y.b(true);
                    ((mr)mp2).y.c(true);
                    break;
                }
                ++n2;
            }
        }
        this.d = new ff(null, 255, 2);
        int n6 = z.r;
        this.d.a(0, z.s - 20, n6, 20);
        this.d.h(30);
        this.d.a(new ms(this));
        this.d.d(true);
        this.b();
        this.e = new nn();
        n n7 = this.a.b();
        int n8 = this.b.h()[0];
        n2 = this.b.g()[0];
        this.z = new mh(n8, this.b.h()[1], (n2 += this.b.g()[2]) - n8, 20);
        this.z.a(this.b.g()[1] - this.b.h()[1]);
        if (com.mg.sq.a.i == 0) {
            this.e.c(n7.a + n7.c / 2 - 5, n7.b + n7.d + 10);
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
                    if (!((nc)this.m.b(n2)).m()) {
                        this.m.a(n2);
                    } else if (this.m.b(n2) != null) {
                        ((nc)this.m.b(n2)).i();
                    }
                }
                --n2;
            }
            n2 = this.n.d() - 1;
            while (n2 >= 0) {
                if (this.n.b(n2) != null) {
                    if (!((mz)this.n.b(n2)).m()) {
                        this.n.a(n2);
                    } else if (this.n.b(n2) != null) {
                        ((mz)this.n.b(n2)).i();
                    }
                }
                --n2;
            }
            n2 = this.o.d() - 1;
            while (n2 >= 0) {
                if (this.o.b(n2) != null) {
                    if (!((il)this.o.b(n2)).m()) {
                        this.o.a(n2);
                    } else if (this.o.b(n2) != null) {
                        ((il)this.o.b(n2)).i();
                    }
                }
                --n2;
            }
            n2 = this.p.d() - 1;
            while (n2 >= 0) {
                if (this.p.b(n2) != null) {
                    ((nf)this.p.b(n2)).i();
                }
                --n2;
            }
            n2 = 0;
            while (n2 < this.x.length) {
                int n3 = 0;
                while (n3 < this.x[n2].length) {
                    this.x[n2][n3].b();
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
                            ((io)a2.b(n4)).i();
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
            if (z.r > mt.a() || z.s > mt.b()) {
                graphics.setColor(0);
                graphics.fillRect(0, 0, z.r, (z.s - mt.b()) / 2);
                graphics.fillRect(0, (z.s + mt.b()) / 2, z.r, (z.s - mt.b()) / 2);
                graphics.fillRect(0, (z.s - mt.b()) / 2, (z.r - mt.a()) / 2, mt.b());
                graphics.fillRect((z.r + mt.a()) / 2, (z.s - mt.b()) / 2, (z.r - mt.a()) / 2, mt.b());
            }
            int n2 = (z.r - mt.a()) / 2;
            int n3 = (z.s - mt.b()) / 2;
            graphics.setClip(n2, n3, mt.a(), mt.b());
            this.A.a(graphics, n2, n3, 0);
            cz.a(graphics, this.r);
            cz.a(graphics, this.a.b(), this.r);
            this.a.a(graphics);
            cz.c(graphics, this.r);
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
                cw.a("[SQCViewManager] loi view" + exception.toString());
            }
            this.b.a(graphics);
            this.t.a(graphics, 0, 0);
            this.c.a(graphics, 0, 0);
            this.u.a(graphics, 0, 0);
            n3 = 0;
            while (n3 < this.l.length) {
                a a2 = this.l[n3];
                ni ni2 = this.b.a[n3];
                int n4 = ni2.n() + (ni2.p() - a2.d() * 17) / 2;
                int n5 = ni2.o() - 15;
                int n6 = 0;
                while (n6 < a2.d()) {
                    io io2 = (io)a2.b(n6);
                    io2.a(graphics, n4 + (n3 == 0 ? ni2.p() / 2 - 20 : -ni2.p() / 2 + 20), n5);
                    n4 += 15;
                    ++n6;
                }
                ++n3;
            }
            n3 = 0;
            while (n3 < this.m.d()) {
                ((nc)this.m.b(n3)).a(graphics, 0, 0);
                ++n3;
            }
            n3 = this.p.d() - 1;
            while (n3 >= 0) {
                ((nf)this.p.b(n3)).a(graphics);
                --n3;
            }
            n3 = 0;
            while (n3 < this.n.d()) {
                ((mz)this.n.b(n3)).a(graphics, 0, 0);
                ++n3;
            }
            n3 = 0;
            while (n3 < this.o.d()) {
                ((il)this.o.b(n3)).a(graphics);
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
            if (op.o != 1) {
                this.e.a(graphics);
            }
            this.z.a(graphics);
            n3 = 0;
            while (n3 < this.x.length) {
                int n7 = 0;
                while (n7 < this.x[n3].length) {
                    this.x[n3][n7].a(graphics, 0, 0);
                    ++n7;
                }
                ++n3;
            }
            this.s.a(graphics);
            graphics.setClip(0, 0, z.r, z.s);
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
        nb nb2 = this.a.a(n3 - 2, n4 - 2);
        this.i.a(nb2.n(), nb2.o());
    }

    public final void b(int n2, int n3, int n4) {
        this.i.a(n2);
        nb nb2 = this.a.a(n3 - 2, n4 - 2);
        if (n2 == 0 && op.o != 9) {
            this.i.b(nb2.n(), nb2.o());
            return;
        }
        this.i.a(nb2.n(), nb2.o());
    }

    public final void a(int n2, int n3) {
        this.i.a(0);
        nb nb2 = this.a.a(n2 - 2, n3 - 2);
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
        this.i.b(n4, nb2.n(), nb2.o());
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
        nb nb2 = this.a.a(n5 - 2, n6 - 2);
        nb nb3 = this.a.a(n3 - 2, n4 - 2);
        this.i.c(nb3.n(), nb3.o(), nb2.n(), nb2.o());
    }

    public final void e() {
        this.f.b(false);
    }

    public final void b(int n2, int n3, int n4, int n5, int n6) {
        n5 = mr.b(n3, n4, n5, n6);
        this.i.a(n2);
        nb nb2 = this.a.a(n3 - 2, n4 - 2);
        this.i.b(n5, nb2.n(), nb2.o());
    }

    public final void a(mw mw2) {
        int n2;
        int n3 = mw2.a;
        int n4 = mw2.b;
        if ((mw2.e[0] & 0xFF) >= 3 || (mw2.e[1] & 0xFF) >= 3) {
            n2 = mr.b(mw2.c, mw2.d, mw2.a, mw2.b);
            n3 = mw2.c;
            n4 = mw2.d;
        } else {
            n2 = mr.b(mw2.a, mw2.b, mw2.c, mw2.d);
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
        nh[][] nhArray = this.h;
        nhArray = this.h.l;
        int[] nArray = new int[12];
        int n3 = 2;
        while (n3 < 10) {
            int n4 = 0;
            int n5 = 9;
            while (n5 >= 2) {
                this.a(n4, n3, n5, n3, nhArray[n5][n3], nArray[n3] + n2);
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

    public final void a(int n2, int n3, nh nh2, int n4) {
        this.a.a(n2, n3, nh2, n4);
    }

    public final void a(int n2, int n3, int n4, int n5, nh nh2, nh nh3) {
        this.a.a(n2, n3, n4, n5, nh2, nh3);
    }

    public final void a(int n2, int n3, int n4, int n5, nh nh2, int n6) {
        this.a.a(n2, n3, n4, n5, nh2, n6);
    }

    public final int a(mu mu2, int n2) {
        int n3 = 0;
        if (mu2.k) {
            int n4;
            Object object;
            if ((mu2.a.A & 1) != 0) {
                int[] nArray;
                int n5 = n2 = (n2 + 1) % 2;
                object = mu2;
                mr mr2 = this;
                if (n5 == 0) {
                    int[] nArray2 = new int[4];
                    nArray2[0] = mr2.b.a[0].n();
                    nArray2[1] = mr2.b.a[0].o();
                    nArray2[2] = mr2.b.a[0].p();
                    nArray = nArray2;
                    nArray2[3] = mr2.b.a[0].q();
                } else {
                    int[] nArray3 = new int[4];
                    nArray3[0] = mr2.b.a[1].n();
                    nArray3[1] = mr2.b.a[1].o();
                    nArray3[2] = mr2.b.a[1].p();
                    nArray = nArray3;
                    nArray3[3] = mr2.b.a[1].q();
                }
                int[] nArray4 = nArray;
                int n6 = nArray[0] + nArray4[2] / 2 + cy.a() % 10;
                int n7 = nArray4[1] + nArray4[3] / 2 + cy.a() % 10;
                if (((mu)object).d == 1 && ((mu)object).g == 1) {
                    mr2.a((mu)object, n6, n7);
                } else if (((mu)object).d == 1) {
                    mr2.b((mu)object, n6, n7);
                } else if (((mu)object).g == 1) {
                    mr2.a((mu)object, n6, n7);
                } else {
                    mr2.a((mu)object, n6, n7);
                    mr2.b((mu)object, n6, n7);
                }
                mr2.a(n5, 36, 26, true);
            }
            nh nh2 = mu2.a;
            object = null;
            n n8 = new n();
            switch (nh2.A) {
                case 1: 
                case 16: 
                case 32: 
                case 64: {
                    break;
                }
                case 2: 
                case 4: 
                case 8: {
                    if ((nh2.A & 2) != 0) {
                        object = n2 == 0 ? this.b.a() : this.b.b();
                    } else if ((nh2.A & 4) != 0) {
                        object = n2 == 0 ? this.b.c() : this.b.d();
                    } else if ((nh2.A & 8) != 0) {
                        object = n2 == 0 ? this.b.e() : this.b.f();
                    }
                    if (object == null) break;
                    n8.a = (int)(object[0] + object[2] / 2);
                    n8.b = (int)(object[1] + object[3] / 2);
                    n8.c = (int)(object[2] - 2);
                    n8.d = (int)(object[3] - 2);
                }
            }
            n3 = mu2.d == 1 && mu2.g == 1 ? this.a(mu2) : (mu2.d < 3 ? this.a(mu2) : (mu2.g < 3 ? this.b(mu2) : ((n2 = this.a(mu2)) > (n4 = this.b(mu2)) ? n2 : n4)));
        }
        return n3;
    }

    private int a(mu mu2) {
        return this.a.b(mu2);
    }

    private int b(mu mu2) {
        return this.a.a(mu2);
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

    private void a(int n2, int n3, n n4, nh nh2, int n5, boolean n6) {
        if ((nh2.A & 1) != 0 || (nh2.A & 0xFFFFFF80) != 0) {
            return;
        }
        try {
            nf nf2 = null;
            n6 = this.p.d() - 1;
            while (n6 >= 0) {
                nf nf3 = (nf)this.p.b(n6);
                if (!nf3.m()) {
                    nf2 = nf3;
                    break;
                }
                --n6;
            }
            if (nf2 == null) {
                nf2 = new nf(this.k);
                this.p.a(nf2);
            }
            nf2.a(n2, n3, n4, nh2.C, 0, false);
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            this.b(true);
            if (com.mg.sq.a.k != null) {
                com.mg.sq.a.k.G();
            }
            return;
        }
    }

    public final void b(int n2, int n3) {
        int[] nArray = null;
        n n4 = new n();
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
            n4.a = nArray[0] + nArray[2] / 2;
            n4.b = nArray[1] + nArray[3] / 2;
            n4.c = nArray[2] - 2;
            n4.d = nArray[3] - 2;
            if (com.mg.sq.a.i == 0) {
                n2 = nArray[0] + nArray[2] + 30;
                int n5 = nArray[1] - 50;
                int n6 = 0;
                while (n6 < 9) {
                    if (n6 == 0 || n6 == 8) {
                        this.a(n2 + 30, n5, n4, nh.a(n3), 0, false);
                    } else {
                        this.a(n2, n5, n4, nh.a(n3), 0, false);
                    }
                    n5 += 12;
                    ++n6;
                }
                return;
            }
            n2 = nArray[0] - 50;
            int n7 = nArray[1] - 30;
            int n8 = 0;
            while (n8 < 9) {
                if (n8 == 0 || n8 == 8) {
                    this.a(n2, n7 - 30, n4, nh.a(n3), 0, false);
                } else {
                    this.a(n2, n7, n4, nh.a(n3), 0, false);
                }
                n2 += 12;
                ++n8;
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
        le[][] leArray = this.h.a();
        String string2 = String.valueOf(n3 >= 0 ? (n3 == 0 ? (n2 != 0 ? "-" : "+") : "+") : "") + n3;
        n2 = 0;
        while (n2 < leArray.length) {
            int n4 = 0;
            while (n4 < leArray[n2].length) {
                if (leArray[n2][n4].j().equals(string)) {
                    if ((bl3 || bl2 || bl4) && n3 < 0) {
                        d2 = ca.a;
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
                    this.x[n2][n4].a(string2, this.b.a(n2)[n4].n() + this.b.a(n2)[n4].p() / 2, this.b.a(n2)[n4].o());
                    this.x[n2][n4].a(this.b.a(n2)[n4]);
                    this.x[n2][n4].a(d2);
                    if (n2 == 1 && this.h.a(1, 0).b() && n3 < 0 && this.h.a(1, 0).c() <= 50) {
                        this.a(this.D[0][cy.a(this.D[0].length)], 50);
                    }
                    return;
                }
                ++n4;
            }
            ++n2;
        }
    }

    public final void c(int n2, int n3, int n4, int n5, int n6) {
        nc nc2 = null;
        int n7 = this.m.d() - 1;
        while (n7 >= 0) {
            nc nc3 = (nc)this.m.b(n7);
            if (!nc3.a()) {
                nc2 = nc3;
                break;
            }
            --n7;
        }
        if (nc2 == null) {
            nc2 = new nc(this.j);
            this.m.a(nc2);
        }
        n7 = (n4 + n6 / 2 - 2) * 28 - (n6 % 2 == 0 ? 14 : 4) + this.a.n();
        int n8 = (n3 + n5 / 2 - 2) * 28 - (n5 % 2 == 0 ? 14 : 5) + this.a.o();
        n3 = n2 - 1;
        nc2.a(n7, n8, n3, "x" + n2);
    }

    private void c(int n2, int n3, int n4) {
        this.a(n2, n3, n4, false);
    }

    public final void a(int n2, int n3, int n4, boolean bl2) {
        this.b.a(n2, n3, bl2);
        this.b.c(n4);
    }

    private void a(mu mu2, int n2, int n3) {
        mz mz2 = null;
        int n4 = mu2.c;
        int n5 = mu2.c + mu2.d;
        while (n4 < n5) {
            mz2 = null;
            nb nb2 = this.a.a(mu2.b - 2, n4 - 2);
            int n6 = this.n.d() - 1;
            while (n6 >= 0) {
                mz mz3 = (mz)this.n.b(n6);
                if (!mz3.m()) {
                    mz2 = mz3;
                    break;
                }
                --n6;
            }
            if (mz2 == null) {
                mz2 = new mz(this.k);
                this.n.a(mz2);
            }
            mz2.a(nb2.n(), nb2.o(), n2, n3, mu2.a.z * mz2.q());
            ++n4;
        }
    }

    private void b(mu mu2, int n2, int n3) {
        mz mz2 = null;
        int n4 = mu2.e;
        int n5 = mu2.e + mu2.g;
        while (n4 < n5) {
            mz2 = null;
            nb nb2 = this.a.a(n4 - 2, mu2.f - 2);
            int n6 = this.n.d() - 1;
            while (n6 >= 0) {
                mz mz3 = (mz)this.n.b(n6);
                if (!mz3.m()) {
                    mz2 = mz3;
                    break;
                }
                --n6;
            }
            if (mz2 == null) {
                mz2 = new mz(this.k);
                this.n.a(mz2);
            }
            mz2.a(nb2.n(), nb2.o(), n2, n3, mu2.a.z * mz2.q());
            ++n4;
        }
    }

    private void a(int n2, int n3, boolean bl2, boolean bl3) {
        a a2 = this.l[n2];
        io io2 = null;
        int n4 = 0;
        while (n4 < a2.d()) {
            io io3 = (io)a2.b(n4);
            if (io3.b == bl2 && io3.a == n3) {
                io2 = io3;
                break;
            }
            ++n4;
        }
        if (io2 == null) {
            a2.a(new io(n3, bl2, 8));
        }
        if (bl3) {
            this.c(n2);
        }
    }

    public final void a(int n2, int n3, boolean bl2) {
        a a2 = this.l[n2];
        int n4 = 0;
        while (n4 < a2.d()) {
            io io2 = (io)a2.b(n4);
            if (io2.b == bl2 && io2.a == n3) {
                a2.a(n4);
                return;
            }
            ++n4;
        }
    }

    private void c(int n2) {
        int n3 = this.h.a((int)n2, (int)0).a().g / 2;
        if (this.C[n2] == null) {
            this.C[n2] = new im(n3);
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
            this.B[n2] = new in(n2, this);
        }
        int[] nArray = n2 == 0 ? new int[]{this.b.a[1].n(), this.b.a[1].o(), this.b.a[1].p(), this.b.a[1].q()} : new int[]{this.b.a[0].n(), this.b.a[0].o(), this.b.a[0].p(), this.b.a[0].q()};
        int n3 = this.h.a((int)n2, (int)0).a().g / 2;
        this.B[n2].a(n3, (ng)this.b.a[n2], nArray[0] + nArray[2] / 2, nArray[1] + nArray[3] / 2);
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

    public final int a(lt lt2, byte[] byArray, byte[] byArray2, byte[] objectArray, byte[] byArray3, int n2) {
        int[] nArray;
        int[] nArray2;
        int n3 = n2 + 1 & 1;
        int n4 = 10;
        int n5 = 0;
        int n6 = -180;
        if (n2 == 0) {
            n6 = 180;
        }
        int n7 = this.a.a(0, 0).p() >> 1;
        int n8 = this.a.a(0, 0).q() >> 1;
        if (n2 == 0) {
            nArray2 = new int[]{this.b.a[1].n(), this.b.a[1].o(), this.b.a[1].p(), this.b.a[1].q()};
            nArray = new int[]{this.b.a[0].n(), this.b.a[0].o(), this.b.a[0].p(), this.b.a[0].q()};
        } else {
            nArray2 = new int[]{this.b.a[0].n(), this.b.a[0].o(), this.b.a[0].p(), this.b.a[0].q()};
            nArray = new int[]{this.b.a[1].n(), this.b.a[1].o(), this.b.a[1].p(), this.b.a[1].q()};
        }
        nb nb2 = null;
        switch (lt2.a) {
            case 1000: {
                n2 = nArray2[0] + nArray2[2] / 2;
                int n9 = nArray2[1] + nArray2[3] / 2;
                lt lt3 = lt2;
                n5 = this.a(0, lt3.a, n2 - n6, n9 - 180, n2, n9, 10);
                this.a(n3, 32, 22, false);
                n4 = 10 + (cy.a(7) + 5);
                int n10 = 0;
                int n11 = 0;
                while (n11 < byArray3.length) {
                    nb2 = this.a.a(objectArray[n11] - 2, byArray3[n11] - 2);
                    n2 = nb2.n() + n7;
                    n9 = nb2.o() + n8;
                    n10 = 10 + cy.a(15);
                    lt3 = lt2;
                    n5 = this.a(n5, lt3.a, n2 - n6, n9 - 180, n2, n9, n10);
                    if (n10 > n4) {
                        n4 = n10;
                    }
                    ++n11;
                }
                break;
            }
            case 1001: {
                if (byArray == null) break;
                int n12 = 0;
                while (n12 < byArray.length) {
                    nb2 = this.a.a(byArray[n12] - 2, byArray2[n12] - 2);
                    this.a((int)byArray[n12], (int)byArray2[n12], nh.a(10), n4 + 5);
                    n2 = nb2.n();
                    int n13 = nb2.o();
                    lt lt4 = lt2;
                    n5 = this.a(n5, lt4.a, n2, n13, n2, n13, n4);
                    n4 += 4;
                    ++n12;
                }
                n4 -= 4;
                break;
            }
            case 1002: {
                this.a(n2, 1, true, true);
                break;
            }
            case 1003: {
                this.d(n2);
                n4 += 30;
                break;
            }
            case 1004: {
                n6 = nArray2[0] + nArray2[2] / 2;
                n5 = nArray2[1] + nArray2[3];
                lt lt5 = lt2;
                this.a(0, lt5.a, n6, n5, n6, n5, 0);
                this.c(n3, 26, 16);
                break;
            }
            case 1005: {
                n2 = nArray[0] + nArray[2] / 2;
                int n14 = nArray[1] + nArray[3] / 2;
                n6 = nArray2[0] + nArray2[2] / 2;
                n5 = nArray2[1] + nArray2[3] / 2;
                lt lt6 = lt2;
                this.a(0, lt6.a, n2, n14, n6, n5, 4);
                this.c(n3, 20, 10);
                break;
            }
            case 1006: {
                n2 = nArray2[0] + nArray2[2] / 2;
                int n15 = nArray2[1] + nArray2[3];
                lt lt7 = lt2;
                n5 = this.a(0, lt7.a, n2 - n6, n15 - 180, n2, n15, 10);
                this.c(n3, 36, 26);
                int n16 = 0;
                int n17 = 0;
                while (n17 < byArray3.length) {
                    n16 = cy.a(7) + 4;
                    nb2 = this.a.a(objectArray[n17] + 1 - 2, byArray3[n17] + 1 - 2);
                    n2 = nb2.n();
                    n15 = nb2.o();
                    lt7 = lt2;
                    n5 = this.a(n5, lt7.a, n2 - n6, n15 - 180, n2, n15, n16);
                    if (n16 > n4) {
                        n4 = n16;
                    }
                    ++n17;
                }
                break;
            }
            case 1007: {
                n2 = nArray2[0] + nArray2[2] / 2;
                int n18 = nArray2[1] + nArray2[3];
                lt lt8 = lt2;
                n5 = this.a(0, lt8.a, n2, n18, n2, n18, 10);
                this.c(n3, 30, 11);
                int n19 = 0;
                int n20 = 0;
                while (n20 < byArray3.length) {
                    n19 = cy.a(7) + 10;
                    nb2 = this.a.a(objectArray[n20] + 4 - 1 - 2, byArray3[n20] + 2 - 1 - 2);
                    n2 = nb2.n() + n7;
                    n18 = nb2.o() + n8;
                    lt8 = lt2;
                    n5 = this.a(n5, lt8.a, n2, n18, n2, n18, n19);
                    if (n19 > n4) {
                        n4 = n19;
                    }
                    ++n20;
                }
                break;
            }
            case 1008: {
                this.c(n3, 10, 4);
                nb2 = this.a.a(7, byArray3[0] + 1 - 2);
                n2 = nb2.n() + n7;
                int n21 = nb2.o() + n8;
                lt lt9 = lt2;
                this.a(0, lt9.a, n2, n21, n2, 0, 10);
                break;
            }
            case 2000: {
                nArray = new int[byArray.length + 1];
                int[] nArray3 = new int[byArray.length + 1];
                objectArray = nArray3;
                int n22 = nArray3.length - 1;
                n2 = 0;
                while (n2 < n22) {
                    nb2 = this.a.a(byArray[n2] - 2, byArray2[n2] - 2);
                    nArray[n2] = nb2.n();
                    objectArray[n2] = nb2.o();
                    ++n2;
                }
                n4 = 10 + (objectArray.length * 5 + 5);
                this.c(n3, n4, n4 - 6);
                n2 = nArray2[0] + nArray2[2] / 2;
                int n23 = nArray2[1] + nArray2[3] / 2;
                nArray[nArray.length - 1] = n2;
                objectArray[nArray.length - 1] = n23;
                lt lt10 = lt2;
                n5 = this.a(0, lt10.a, n2, n23, n2, n23, n4);
                jf jf2 = (jf)this.o.b(n5);
                jf2.a(nArray, (int[])objectArray);
                break;
            }
            case 2001: {
                this.a(n2, 0, true, true);
                break;
            }
            case 2002: {
                this.a(n2, 2, true, true);
                break;
            }
            case 2003: {
                n2 = nArray2[0] + nArray2[2] / 2;
                int n24 = nArray2[1] + nArray2[3] / 2;
                lt lt11 = lt2;
                this.a(0, lt11.a, n2 - n6, n24 - 180, n2, n24, 10);
                this.c(n3, 36, 26);
                n4 = 14;
                break;
            }
            case 2004: {
                int n25 = nArray[1] + nArray[3] / 2 + 6;
                n6 = nArray2[0] + nArray2[2] / 2;
                n5 = nArray2[1] + nArray2[3] / 2 + 6;
                if (n2 == 0) {
                    n6 += 60;
                    n2 = nArray[0] + nArray[2];
                } else {
                    n2 = nArray[0];
                    n6 -= 60;
                }
                lt lt12 = lt2;
                this.a(0, lt12.a, n2, n25, n6, n5, 10);
                this.c(n3, 20, 10);
                n4 = 10;
                if (!this.h.a(n3, 0).i()) break;
                this.a(n3, 1, false, false);
                break;
            }
            case 2005: {
                this.d(n2);
                n4 += 30;
                break;
            }
            case 2006: {
                int n26 = nArray[1] + nArray[3] / 2 + 4;
                n6 = nArray2[0] + nArray2[2] / 2;
                int n27 = (z.r - mt.a()) / 2;
                if (n2 == 0) {
                    lt lt13 = lt2;
                    n5 = this.a(0, lt13.a, (n27 -= 56) - cy.a(150), n26, n6, n26, 6);
                    this.c(n3, 16, 10);
                    n6 = n27 + mt.a() + 100;
                    n7 = 0;
                    while (n7 < objectArray.length) {
                        nb2 = this.a.a(objectArray[n7] - 2, 0);
                        lt13 = lt2;
                        n5 = this.a(n5, lt13.a, n27 - ((n7 & 1) == 1 ? 40 : 0), nb2.o(), n6, nb2.o(), 6);
                        ++n7;
                    }
                    n26 -= 40;
                    n7 = 0;
                    while (n7 < 3) {
                        lt13 = lt2;
                        n5 = this.a(n5, lt13.a, n27 - cy.a(150), n26, n6, n26, 6);
                        n26 += 20;
                        ++n7;
                    }
                } else {
                    lt lt14 = lt2;
                    n5 = this.a(0, lt14.a, n27 + mt.a() + cy.a(150), n26, n6, n26, 6);
                    this.c(n3, 16, 10);
                    n6 = n27 - 100;
                    n27 += mt.a();
                    n7 = 0;
                    while (n7 < objectArray.length) {
                        nb2 = this.a.a(objectArray[n7] - 2, 0);
                        lt14 = lt2;
                        n5 = this.a(n5, lt14.a, n27 + ((n7 & 1) == 1 ? 40 : 0), nb2.o(), n6, nb2.o(), 6);
                        ++n7;
                    }
                    n26 -= 40;
                    n7 = 0;
                    while (n7 < 3) {
                        lt14 = lt2;
                        n5 = this.a(n5, lt14.a, n27 + cy.a(150), n26, n6, n26, 6);
                        n26 += 20;
                        ++n7;
                    }
                }
                n4 = 5;
                break;
            }
            case 2007: {
                lt lt15;
                int n28;
                int n29 = 0;
                n7 = 0;
                while (n7 < byArray3.length) {
                    nb2 = this.a.a(objectArray[n7] + 1 - 2, byArray3[n7] + 1 - 2);
                    n2 = nb2.n();
                    n28 = nb2.o() - n8;
                    n29 = 10 + cy.a(10) + 5;
                    lt15 = lt2;
                    n5 = this.a(n5, lt15.a, n2, n28, n2, 0, n29 + (n7 << 1));
                    if (n29 > n4) {
                        n4 = n29;
                    }
                    ++n7;
                }
                n2 = nArray2[0] + nArray2[2] / 2;
                n28 = nArray2[1] + nArray2[3] - 10;
                lt15 = lt2;
                this.a(n5, lt15.a, n2, n28, n2, 0, n4 - 15);
                this.c(n3, n4, n4 - 6);
                break;
            }
            case 2008: {
                n6 = nArray2[0] + nArray2[2] / 2;
                n5 = nArray2[1] + nArray2[3] / 2;
                lt lt16 = lt2;
                n5 = this.a(0, lt16.a, 0, 0, n6, n5, 0);
                jk jk2 = (jk)this.o.b(n5);
                ((jk)this.o.b(n5)).s = byArray.length;
                int n30 = 0;
                while (n30 < byArray.length) {
                    nb2 = this.a.a(byArray[n30] - 2, byArray2[n30] - 2);
                    jk2.t[n30].c(nb2.n(), nb2.o());
                    ++n30;
                }
                this.a(n3, 32, 22, false);
                n4 = 0;
                break;
            }
            case 4000: {
                n2 = nArray2[0] + nArray2[2] / 2;
                int n31 = nArray2[1] + nArray2[3] / 2;
                lt lt17 = lt2;
                n5 = this.a(0, lt17.a, n2 - n6, n31 - 180, n2, n31, 10);
                this.a(n3, 32, 22, false);
                n4 = 10 + (cy.a(7) + 5);
                int n32 = 0;
                int n33 = 0;
                while (n33 < byArray3.length) {
                    nb2 = this.a.a(objectArray[n33] - 2, byArray3[n33] - 2);
                    n2 = nb2.n() + n7;
                    n31 = nb2.o() + n8;
                    n32 = 10 + cy.a(15);
                    lt17 = lt2;
                    n5 = this.a(n5, lt17.a, n2 - n6, n31 - 180, n2, n31, n32);
                    if (n32 > n4) {
                        n4 = n32;
                    }
                    ++n33;
                }
                break;
            }
            case 4001: {
                n2 = nArray[0] + nArray[2] / 2;
                int n34 = nArray[1] + nArray[3] - 10;
                lt lt18 = lt2;
                this.a(0, lt18.a, n2, n34, n2, n34, 10);
                this.b.c(10);
                n4 = 14;
                break;
            }
            case 4002: {
                n2 = nArray[0] + nArray[2] / 2;
                int n35 = nArray[1] + nArray[3] / 2 + 6;
                n6 = nArray2[0] + nArray2[2] / 2;
                lt lt19 = lt2;
                this.a(0, lt19.a, n2, n35, n6, n35, 10);
                this.a(n3, 1, false, false);
                n4 = 15;
                break;
            }
            case 4003: {
                n6 = nArray2[0] + nArray2[2] / 2;
                n5 = nArray2[1] + nArray2[3];
                lt lt20 = lt2;
                this.a(0, lt20.a, n6, n5, n6, n5, 10);
                this.b.a(n3, 10);
                this.c(n3, 15, 16);
                n4 = 13;
                break;
            }
            case 4004: {
                this.d(n2);
                n4 += 30;
                break;
            }
            case 4005: {
                n2 = nArray[0] + nArray[2] / 2;
                int n36 = nArray[1] + nArray[3] / 2;
                n6 = nArray2[0] + nArray2[2] / 2;
                n5 = nArray2[1] + nArray2[3] / 2;
                lt lt21 = lt2;
                this.a(0, lt21.a, n2, n36, n6, n5, 4);
                this.c(n3, 26, 16);
                break;
            }
            case 4006: {
                n6 = nArray2[0] + nArray2[2] / 2;
                n5 = nArray2[1] + nArray2[3] / 2;
                lt lt22 = lt2;
                n5 = this.a(0, lt22.a, n6, n5 - 180, n6, n5, 0);
                this.c(n3, 20, 10);
                int n37 = 0;
                while (n37 < byArray3.length) {
                    int n38 = cy.a(15) + 10;
                    nb2 = this.a.a(objectArray[n37] + 1 - 2, byArray3[n37] + 1 - 2);
                    n2 = nb2.n();
                    int n39 = nb2.o();
                    lt22 = lt2;
                    n5 = this.a(n5, lt22.a, n2, n39 - 180, n2, n39, n38);
                    if (n38 > n4) {
                        n4 = n38;
                    }
                    ++n37;
                }
                break;
            }
            case 4007: {
                int n40 = 0;
                int n41 = 0;
                while (n41 < byArray3.length) {
                    nb2 = this.a.a(objectArray[n41] + 3 - 1 - 2, byArray3[n41] + 1 - 2);
                    n2 = nb2.n();
                    int n42 = nb2.o() + n8;
                    n40 = 10 + cy.a(15);
                    lt lt23 = lt2;
                    n5 = this.a(n5, lt23.a, n2, n42, n2, 0, n40);
                    if (n40 > n4) {
                        n4 = n40;
                    }
                    ++n41;
                }
                this.c(n3, n4, n4 - 6);
                break;
            }
            case 4008: {
                n2 = nArray2[0] + nArray2[2] / 2;
                int n43 = nArray2[1] + nArray2[3] / 2;
                lt lt24 = lt2;
                n5 = this.a(0, lt24.a, n2 - n6, n43 - 180, n2, n43, 10);
                this.a(n3, 32, 22, false);
                n4 = 10 + (cy.a(7) + 5);
                int n44 = 0;
                int n45 = 0;
                while (n45 < byArray3.length) {
                    nb2 = this.a.a(objectArray[n45] - 2, byArray3[n45] - 2);
                    n2 = nb2.n() + n7;
                    n43 = nb2.o() + n8;
                    n44 = 10 + cy.a(20);
                    lt24 = lt2;
                    n5 = this.a(n5, lt24.a, n2 - n6, n43 - 180, n2, n43, n44);
                    if (n44 > n4) {
                        n4 = n44;
                    }
                    ++n45;
                }
                break;
            }
        }
        return n4 - 3;
    }

    private int a(int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        il il2;
        int n9;
        int n10;
        block29: {
            int n11 = n3;
            int n12 = n2;
            mr mr2 = this;
            if (n12 < 0) {
                n10 = -1;
            } else {
                while (n12 < mr2.o.d()) {
                    il il3 = (il)mr2.o.b(n12);
                    if (!il3.m() && il3.r() == n11) {
                        n10 = n12;
                        break block29;
                    }
                    ++n12;
                }
                n10 = n9 = -1;
            }
        }
        if (n10 > 0) {
            il2 = (il)this.o.b(n9);
        } else {
            switch (n3) {
                case 1000: {
                    il2 = new ir();
                    break;
                }
                case 1001: {
                    il2 = new is();
                    break;
                }
                case 1004: {
                    il2 = new it();
                    break;
                }
                case 1005: {
                    il2 = new iu();
                    break;
                }
                case 1006: {
                    il2 = new iv();
                    break;
                }
                case 1007: {
                    il2 = new iw();
                    break;
                }
                case 1008: {
                    il2 = new ix();
                    break;
                }
                case 2000: {
                    il2 = new jf();
                    break;
                }
                case 2003: {
                    il2 = new jg();
                    break;
                }
                case 2004: {
                    il2 = new jh();
                    break;
                }
                case 2006: {
                    il2 = new ji();
                    break;
                }
                case 2007: {
                    il2 = new jj();
                    break;
                }
                case 2008: {
                    il2 = new jk();
                    break;
                }
                case 4000: {
                    il2 = new iy();
                    break;
                }
                case 4001: {
                    il2 = new iz();
                    break;
                }
                case 4002: {
                    il2 = new ja();
                    break;
                }
                case 4003: {
                    il2 = new jb();
                    break;
                }
                case 4005: {
                    il2 = new jc();
                    break;
                }
                case 4006: {
                    il2 = new jd();
                    break;
                }
                case 4007: {
                    il2 = new je();
                    break;
                }
                case 4008: {
                    il2 = new iy();
                    break;
                }
                default: {
                    return -1;
                }
            }
            il2.l(n3);
            this.o.a(il2);
            n9 = this.o.d() - 1;
        }
        il2.a(n4, n5, n6, n7, n8);
        return n9;
    }

    public final void a(boolean bl2) {
        this.b.a(bl2);
    }

    public final void a(bj bj2) {
        if (this.y != null) {
            this.y.b(bj2);
            return;
        }
        com.mg.sq.a.a("Ki\u1ec3u quy\u1ebft \u0111\u1ea5u: Kh\u00f4ng ch\u01a1i Tuy\u1ec7t Chi\u00eau");
    }

    public final void a(int n2, int n3, int n4, int n5) {
        n4 = 112 + this.a.n() - 14;
        n5 = 112 + this.a.o() - 14;
        this.t.a("C\u00f2n " + n3 + " l\u01b0\u1ee3t", n4, n5);
        if (n2 == 0) {
            this.t.a(ca.c);
            return;
        }
        this.t.a(com.mg.sq.a.h);
    }

    public final void i() {
        n n2 = this.a.b();
        this.u.a("H\u1ebft n\u01b0\u1edbc \u0111i!", n2.a + n2.c / 2, n2.b + n2.d / 2);
    }

    public final void c(String string) {
        n n2 = this.a.b();
        this.c.a(string, n2.a + n2.c / 2, n2.b + n2.d / 2);
    }

    public final void j() {
        this.z.a("Vui l\u00f2ng \u0111\u1eebng g\u1eedi qu\u00e1 nhanh!", 0);
    }

    public final void k() {
        this.o.a();
    }

    public final void b(boolean bl2) {
        mr mr2 = this;
        mr2.n.a();
        mr2 = this;
        mr2.m.a();
        mr2 = this;
        mr2.p.a();
        if (bl2) {
            mr mr3 = this;
            mr3.o.a();
        }
        System.gc();
    }
}

