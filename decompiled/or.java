/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class or
extends au
implements bj,
bk,
bt {
    private ny m;
    private n n;
    private n o;
    private bc p;
    private int q;
    private boolean r = false;
    private dq s;
    lf i;
    private de t;
    private byte u = (byte)-1;
    public String j;
    fj k = new fj();
    private int v = 0;
    private String w;
    private oj x;
    private ie y = new ie(new int[]{5939728, 0xFFFF00});
    public static lp[] l;
    private String z;
    private long A;
    private int B;

    public or(int n2, ny ny2, oj oj2) {
        this.m = ny2;
        this.x = oj2;
        this.a_(3);
        this.a(0, 0, z.r, z.s);
        this.a((byte)0);
        this.x.a(new ga(105, 0));
        this.x.b(new ga(109, 1));
        this.x.c(com.mg.sq.a.l);
        this.x.a(new be());
        this.x.a(this);
    }

    private void a(byte by2) {
        this.u = by2;
        or or2 = this;
        switch (or2.u) {
            case 1: {
                or2.n = new n(0, 73, z.r, 1);
                or2.o = new n(0, or2.n.b + 4, or2.e(), or2.f() - (or2.n.b + 4) - be.a);
                break;
            }
            default: {
                or2.n = new n(0, 5, z.r, 1);
                or2.o = new n(0, or2.n.b + 5, or2.e(), or2.f() - (or2.n.b + 4) - be.a);
            }
        }
        this.k = new fj();
        this.k.i = true;
        this.k.a(this);
        this.k.a_(this.o.a, this.o.b);
        this.p = new bc(0);
        this.p.a(this.o);
        this.p.b(this.k);
        this.p.h(1);
        switch (by2) {
            case 1: {
                this.v = 0;
                boolean bl2 = gr.j.ac;
                if (bl2) {
                    gr.j.e = (byte)2;
                } else if (gr.j.e == 2) {
                    gr.j.e = 0;
                }
                dq dq2 = new dq();
                new dq().a = gr.j.b;
                dq2.d = gr.j.T;
                dq2.b = gr.j.G;
                dq2.c = gr.j.e;
                dq2.f = gr.j.Z;
                dq2.e = gr.j.R;
                this.a(this.j, new dq[]{dq2});
                kq.a().d();
                break;
            }
            case 0: {
                this.j = null;
                if (l == null) break;
                this.a(l);
            }
        }
        this.x.c(true);
    }

    public final void c(boolean bl2) {
        this.p.c(true);
    }

    public final void a(Graphics object, int n2, int n3) {
        if (this.p != null && this.p.k()) {
            Graphics graphics = object;
            object = this;
            graphics.setColor(z.af);
            graphics.fillRect(0, 0, ((au)object).e(), ((au)object).f());
            graphics.drawImage(oz.d, ((au)object).c() + ((au)object).e(), ((au)object).d() + ((au)object).f() - be.a, 40);
            switch (((or)object).u) {
                case 1: {
                    if (((or)object).t != null) {
                        ((or)object).t.a(graphics, 0, 0);
                    }
                    oz.a(graphics, ((or)object).n.a, ((or)object).n.b, ((or)object).n.c);
                    if (((or)object).z == null) break;
                    long l2 = ((or)object).A - System.currentTimeMillis() > 0L ? ((or)object).A - System.currentTimeMillis() : 0L;
                    n3 = z.r - ca.c.a(((or)object).z) - ca.c.a(l.b(l2, "hh:mm:ss")) - 5;
                    com.mg.sq.a.h.a(graphics, String.valueOf(((or)object).z) + " " + l.b(l2, "hh:mm:ss"), n3, z.s - 35, 0);
                }
            }
            if (((or)object).p != null) {
                cz.a(graphics);
                cz.b(graphics, ((or)object).p.h());
                ((or)object).p.a(graphics, ((au)object).c(), ((au)object).d());
                cz.b(graphics);
            }
        }
    }

    public final void n() {
        if (this.t != null) {
            this.t.i();
        }
        if (this.p != null) {
            this.p.n();
        }
        if (this.q > 0) {
            --this.q;
            if (this.q == 1) {
                kq.a().a(this.w, 91, (byte)101);
            }
        }
    }

    public final void a() {
        this.x.c(true);
    }

    public final boolean f(int n2) {
        if (n2 == 97 || n2 == 96) {
            return false;
        }
        boolean bl2 = this.p.f(n2);
        n2 = bl2 ? 1 : 0;
        return bl2;
    }

    public final boolean e(int n2, int n3) {
        return this.p.e(n2, n3);
    }

    public final boolean c(int n2, int n3) {
        return this.p.c(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        return this.p.f(n2, n3);
    }

    public final synchronized void a(String string, dq[] dqArray) {
        int n2;
        if (string == null || !this.x.s) {
            return;
        }
        com.mg.sq.a.t();
        if (this.u != 1) {
            this.a((byte)1);
        }
        try {
            if (this.a(string)) {
                int n3 = 0;
                while (n3 < dqArray.length) {
                    if (dqArray[n3] != null) {
                        this.a(dqArray[n3]);
                    }
                    ++n3;
                }
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            com.mg.sq.a.k.G();
        }
        if ((n2 = this.b(gr.j.b)) >= 0) {
            this.k.i(n2);
        } else {
            this.k.i(0);
        }
        this.t();
    }

    private void a(dq object) {
        try {
            int n2;
            ew ew2;
            block10: {
                ew2 = null;
                ew2 = ((dq)object).a.length() < 6 ? new ew((dq)object, this.e(), this.y) : new ew((dq)object, this.e());
                ew2.a_(this.v++);
                String string = ((dq)object).a;
                object = this;
                if (((or)object).k.r() == 0) {
                    n2 = -1;
                } else {
                    int n3 = 0;
                    int n4 = ((or)object).k.r();
                    int n5 = n4 + 0 >> 1;
                    while (true) {
                        dq dq2 = ((ew)((or)object).k.j((int)n5)).i;
                        if (string.compareTo(dq2.a) > 0) {
                            n3 = n5;
                            n5 = n3 + n4 >> 1;
                        } else {
                            if (string.compareTo(dq2.a) >= 0) {
                                n2 = n5;
                                break block10;
                            }
                            n4 = n5;
                            n5 = n3 + n4 >> 1;
                        }
                        if (n3 != n5) continue;
                        dq2 = ((ew)((or)object).k.j((int)n5)).i;
                        if (string.compareTo(dq2.a) > 0) {
                            n2 = n4 == ((or)object).k.r() ? -1 : n4;
                            break block10;
                        }
                        if (string.compareTo(dq2.a) < 0) break;
                    }
                    n2 = n3;
                }
            }
            int n6 = n2;
            if (n2 == -1) {
                this.k.b(ew2);
                return;
            }
            this.k.a(ew2, n6);
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            com.mg.sq.a.k.G();
            cw.a("[RoomTab] khong du bo nho them ng choi vao room");
            return;
        }
    }

    private boolean a(String string) {
        return string.equals(this.j);
    }

    public final void a(lf lf2) {
        this.i = lf2;
        this.t = new de(this.i);
        this.c(true);
    }

    public final void b(au au2, int n2) {
        block28: {
            block27: {
                block29: {
                    au au3;
                    Object object;
                    block31: {
                        block30: {
                            if (!(au2 instanceof ew)) break block27;
                            if (this.k.r() <= 1) break block28;
                            au2 = (ew)au2;
                            object = ((ew)au2).i;
                            au2 = this;
                            if (((dq)object).a.equals(gr.j.b)) break block29;
                            ((or)au2).s = object;
                            object = new bv();
                            if (com.mg.sq.a.k == null || !com.mg.sq.a.m) break block30;
                            au3 = (fc)((or)au2).x.l();
                            switch (((or)au2).s.c) {
                                case 2: {
                                    if (((fc)au3).b(com.mg.sq.a.k)) {
                                        if ((((or)au2).B & 2) != 0) {
                                            ((bv)object).a(new bu("Giao d\u1ecbch", 10100));
                                        }
                                        ((bv)object).a(new bu[]{new bu("Chat!", 10101), new bu("Xem ME", 10102)});
                                        break;
                                    }
                                    ((bv)object).a(new bu[]{new bu("Giao d\u1ecbch", 10100)});
                                    break;
                                }
                                case 3: {
                                    if (((fc)au3).b(com.mg.sq.a.k)) {
                                        ((bv)object).a(new bu[]{new bu("Chat!", 10101), new bu("Xem ME", 10102)});
                                        break;
                                    }
                                    break block28;
                                }
                                case 1: {
                                    if (((fc)au3).b(com.mg.sq.a.k)) {
                                        if ((((or)au2).B & 4) != 0) {
                                            ((bv)object).a(new bu("Xem Tr\u1eadn \u0111\u00e1nh", 10103));
                                        }
                                        ((bv)object).a(new bu[]{new bu("Chat!", 10101), new bu("Xem ME", 10102)});
                                        break;
                                    }
                                    ((bv)object).a(new bu[]{new bu("Xem Tr\u1eadn \u0111\u00e1nh", 10103)});
                                    break;
                                }
                                default: {
                                    if (((fc)au3).b(com.mg.sq.a.k)) {
                                        if (((or)au2).s.f > 0L) {
                                            ((bv)object).a(new bu[]{new bu("\u0110\u00e1nh!", 10104), new bu("Giao d\u1ecbch", 10100), new bu("Chat!", 10101), new bu("Xem ME", 10102)});
                                            break;
                                        }
                                        if ((((or)au2).B & 1) != 0) {
                                            ((bv)object).a(new bu("Khi\u00eau Chi\u1ebfn", 10105));
                                        }
                                        if ((((or)au2).B & 2) != 0) {
                                            ((bv)object).a(new bu("Giao d\u1ecbch", 10100));
                                        }
                                        ((bv)object).a(new bu[]{new bu("Chat!", 10101), new bu("Xem ME", 10102)});
                                        break;
                                    }
                                    if (((or)au2).s.f > 0L) {
                                        ((bv)object).a(new bu[]{new bu("\u0110\u00e1nh!", 10104), new bu("Giao d\u1ecbch", 10100)});
                                        break;
                                    }
                                    if ((((or)au2).B & 1) != 0) {
                                        ((bv)object).a(new bu("Khi\u00eau Chi\u1ebfn", 10105));
                                    }
                                    if ((((or)au2).B & 2) != 0) {
                                        ((bv)object).a(new bu("Giao d\u1ecbch", 10100));
                                        break;
                                    }
                                    break block31;
                                }
                            }
                            break block31;
                        }
                        switch (((or)au2).s.c) {
                            case 2: {
                                if ((((or)au2).B & 2) == 0) break;
                                ((bv)object).a(new bu("Giao d\u1ecbch", 10100));
                                break;
                            }
                            case 3: {
                                break block28;
                            }
                            case 1: {
                                if ((((or)au2).B & 4) == 0) break;
                                ((bv)object).a(new bu("Xem Tr\u1eadn \u0111\u00e1nh", 10103));
                                break;
                            }
                            default: {
                                if (((or)au2).s.f > 0L) {
                                    ((bv)object).a(new bu[]{new bu("\u0110\u00e1nh!", 10104), new bu("Giao d\u1ecbch", 10100)});
                                    break;
                                }
                                if ((((or)au2).B & 1) != 0) {
                                    ((bv)object).a(new bu("Khi\u00eau Chi\u1ebfn", 10105));
                                }
                                if ((((or)au2).B & 2) == 0) break;
                                ((bv)object).a(new bu("Giao d\u1ecbch", 10100));
                            }
                        }
                    }
                    au3 = ((or)au2).k.u();
                    n n3 = ((or)au2).p.r();
                    int n4 = (z.r - ((au)object).e()) / 2;
                    int n5 = ((or)au2).p.d() + au3.d() - n3.b;
                    if (n5 + ((au)object).f() > z.s - be.a) {
                        n5 = z.s - be.a - ((au)object).f();
                    }
                    ((bv)object).a_(z.r + ((au)object).e(), n5);
                    ((bv)object).d(n4, n5);
                    ((bv)object).a((bk)((Object)au2));
                    ((bv)object).a(new ga(113, 2));
                    ((bv)object).b(new ga(106, 3));
                    ((bv)object).c(com.mg.sq.a.l);
                    ((au)object).a_(1);
                    ((or)au2).x.a((bv)object);
                }
                return;
            }
            if (au2 instanceof fy) {
                au2 = (fy)au2;
                if (!((fy)au2).i.a()) {
                    kq.a().q(((fy)au2).i.b);
                    com.mg.sq.a.a(null, null);
                }
            }
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
        this.t();
        au2 = this.k.j(n3);
        if (au2 instanceof ew) {
            au2 = (ew)this.k.j(n3);
            if (this.i != null && this.i.b.equals(((ew)au2).i.a)) {
                return;
            }
            if (((ew)au2).i.a.equals(gr.j.b)) {
                this.a(gr.j);
                return;
            }
            this.w = ((ew)au2).i.a;
            this.q = 10;
        }
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 106: {
                this.x.t();
                return;
            }
            case 113: {
                this.x.l.f(95);
                return;
            }
            case 109: {
                ((fc)this.x.l()).a();
                return;
            }
            case 105: {
                or or2 = this;
                bv bv2 = new bv();
                bv2.a(ny.a(or2.x));
                int n4 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n4, or2.f() - bv2.f() + n4);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(or2);
                ga ga2 = new ga(113, 2);
                ga ga3 = new ga(106, 3);
                bv2.c(com.mg.sq.a.l);
                bv2.a(ga2);
                bv2.b(ga3);
                or2.x.a(bv2);
                return;
            }
            case 0: {
                ak.b().a(-9, false);
            }
        }
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        Object object2 = (bu)object;
        switch (n3) {
            case 10101: {
                if (com.mg.sq.a.k == null) break;
                object2 = com.mg.sq.a.k.c(this.s.a);
                ((du)object2).d("patriot");
                break;
            }
            case 10105: {
                com.mg.sq.a.d(this.s.a);
                break;
            }
            case 10102: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.a(this.s.a, 0L);
                break;
            }
            case 10103: {
                com.mg.sq.a.r(this.s.a);
                break;
            }
            case 10100: {
                com.mg.sq.a.p(this.s.a);
                break;
            }
            case 10104: {
                this.m.a(this.s.a, false, 0, false, false);
                break;
            }
            case 99028: {
                kq.a().f();
                kq.a().t();
                com.mg.sq.a.a(null, null);
                break;
            }
            default: {
                this.m.a(n3, (bu)object2);
            }
        }
        this.x.t();
    }

    public final void b(lf lf2) {
        au au2 = this.k.u();
        if (au2 instanceof ew) {
            au2 = (ew)au2;
            if (((ew)au2).i.a.equals(lf2.b)) {
                this.a(lf2);
                this.i = lf2;
            }
        }
    }

    public final synchronized void a(String string, dq dq2, String string2, long l2) {
        int n2;
        if (dq2 == null) {
            return;
        }
        if (this.u == 0) {
            return;
        }
        if (this.a(string) && (n2 = this.b(dq2.a)) >= 0) {
            try {
                ((ew)this.k.j(n2)).a(dq2, string2, l2);
                return;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
                System.gc();
            }
        }
    }

    public final void a(lp[] lpArray) {
        if (this.u != 0) {
            this.a((byte)0);
        }
        this.k.t();
        int n2 = 0;
        while (n2 < lpArray.length) {
            this.k.b(new fy(lpArray[n2]));
            ++n2;
        }
        this.k.i(0);
        com.mg.sq.a.t();
        this.c(true);
    }

    public final void b(lp[] lpArray) {
        if (this.u == 0) {
            this.a((byte)0);
            int n2 = 0;
            while (n2 < lpArray.length) {
                int n3 = 0;
                while (n3 < l.length) {
                    if (lpArray[n2].b.equals(or.l[n3].b)) {
                        or.l[n3].f = lpArray[n2].f;
                        or.l[n3].e = lpArray[n2].e;
                        or.l[n3].a = lpArray[n2].a;
                        or.l[n3].c = lpArray[n2].c;
                        or.l[n3].d = lpArray[n2].d;
                        or.l[n3].b = lpArray[n2].b;
                        or.l[n3].g = lpArray[n2].g;
                    }
                    ++n3;
                }
                ++n2;
            }
        }
    }

    private int b(String string) {
        dq dq2;
        if (this.k.r() == 0) {
            return -1;
        }
        int n2 = 0;
        int n3 = this.k.r();
        int n4 = n3 + 0 >> 1;
        do {
            dq2 = ((ew)this.k.j((int)n4)).i;
            if (string.compareTo(dq2.a) > 0) {
                n2 = n4;
                n4 = n2 + n3 >> 1;
                continue;
            }
            if (string.compareTo(dq2.a) < 0) {
                n3 = n4;
                n4 = n2 + n3 >> 1;
                continue;
            }
            return n4;
        } while (n2 != n4);
        dq2 = ((ew)this.k.j((int)n4)).i;
        if (string.equals(dq2.a)) {
            return n4;
        }
        return -1;
    }

    public final synchronized void a(String string, String string2) {
        int n2;
        if (this.a(string) && (n2 = this.b(string2)) >= 0) {
            this.k.c(this.k.j(n2));
            if (n2 == this.k.q()) {
                if (this.k.q() >= this.k.r()) {
                    this.k.i(this.k.r() - 1);
                } else {
                    this.k.i(n2);
                }
            }
            this.t();
        }
    }

    private void t() {
        int n2 = this.p.r().b;
        int n3 = this.k.q();
        au au2 = this.k.j(n3);
        int n4 = 0;
        if (au2.d() - n2 < 0) {
            n4 = au2.d() - n2;
        } else if (au2.d() + au2.f() > n2 + this.p.r().d) {
            n4 = au2.d() + au2.f() - (n2 + this.p.r().d);
        }
        this.p.j(n4);
    }

    public final void a(String string, String string2, long l2, int n2) {
        this.j = string;
        this.z = string2;
        this.A = l2 + System.currentTimeMillis();
        this.B = n2;
        if (this.u != 1) {
            this.a((byte)1);
        }
        kq.a().e(string);
    }

    public final byte q() {
        return this.u;
    }

    public final synchronized void b(String string, dq dq2, String string2, long l2) {
        if (dq2 == null) {
            return;
        }
        if (this.a(string)) {
            int n2 = this.b(dq2.a);
            if (n2 >= 0) {
                this.a(string, dq2, string2, l2);
                return;
            }
            this.a(dq2);
            this.t();
        }
    }

    public final void r() {
        if (this.u == 1) {
            this.a((byte)0);
        }
    }

    public final void s() {
        if (this.u == 1) {
            kq.a().f();
            this.k.t();
            System.gc();
        }
    }
}

