/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class on
extends fb
implements bj,
bk,
bq,
bt {
    private ff p;
    dl o;
    private ba q;
    private bc r;
    private oh s;
    private ex t;
    private au[] u;
    private int v;
    private i w;
    private hn x = null;
    private int y = -1;
    private fq z;
    private long A = -1L;
    private int B = 0;
    private int C = 0;

    public on(oh oh2, int n2, int n3, String string) {
        super(106, 3, string);
        this.s = oh2;
        this.x = new hn(1);
        this.x.a(this);
        this.a(0, 0, z.r, z.s);
        this.q = new ba();
        this.q.h(3);
        this.q.a(this);
        this.q.a(this);
        this.q.h(10);
        this.r = new bc(0);
        this.r.a(1, 22, z.r - 2, z.s - be.a - 22 - 20);
        this.r.b(this.q);
        this.p = new ff("", 300, 2);
        this.p.a(this);
        this.z = new fq("B\u1ea5m ph\u00edm xu\u1ed1ng \u0111\u1ec3 th\u00eam KUL v\u00e0 ph\u00edm ph\u1ea3i \u0111\u1ec3 th\u00eam SMILEY", this.p, 0);
        if (z.x) {
            this.p.a(0, z.s - be.a - 20, z.r - 40 - 8, 20);
            this.p.e(z.x);
            this.t = new ex("G\u1eedi", -3);
            this.t.a(this.p.e() + this.p.c() + 2, this.p.d(), 40, 20);
            this.u = new au[]{this.r, this.p, this.t};
            this.v();
        } else {
            this.p.a(0, z.s - be.a - 20, z.r, 20);
            this.w();
        }
        this.a(new be());
        this.a(new ga(-1, 0));
        this.c(com.mg.sq.a.l);
        if (z.b()) {
            this.b(new ga(-10, 1));
        }
        this.a(this);
    }

    public final void a() {
        this.q.q();
        this.o = null;
    }

    public final void a(dl object) {
        this.o = object;
        this.j = this.o != null ? com.mg.sq.a.a(this.o.b().a(), ca.e, z.r - 120) : "\u0110\u00e0m \u0110\u1ea1o";
        this.q.q();
        object = this.o == null ? new a() : this.o.c();
        Object[] objectArray = new Object[((a)object).d()];
        int n2 = 0;
        while (n2 < objectArray.length) {
            objectArray[n2] = ((a)object).b(n2);
            ++n2;
        }
        if (z.x) {
            this.v();
        } else {
            this.w();
        }
        if (((a)object).d() > 0) {
            this.q.a(objectArray);
        }
        if (this.q.a() > 0) {
            this.q.k(this.q.a() - 1);
            if (this.q.r() != null) {
                fp fp2 = (fp)this.q.o(this.q.a() - 1);
                fp2.q();
            }
        }
        this.r.g(false);
    }

    private void v() {
        this.f(false);
        this.r.d(true);
        this.v = 0;
        this.c(true);
        this.q.k(this.q.s());
        if (this.z != null && this.z.i()) {
            fq fq2 = this.z;
            fq2.a(false);
        }
    }

    private void w() {
        boolean bl2 = this.p.m();
        this.f(true);
        if (!bl2) {
            this.p.d(true);
            if (this.z != null) {
                this.A = System.currentTimeMillis();
            }
        }
        this.c(true);
        this.v = 2;
    }

    private void f(boolean bl2) {
        if (z.x && this.u != null) {
            int n2 = 0;
            while (n2 < this.u.length) {
                if (!bl2 || n2 != 1) {
                    this.u[n2].d(false);
                }
                ++n2;
            }
            return;
        }
        this.r.d(false);
        if (!bl2) {
            this.p.d(false);
        }
    }

    public final void a(i i2, boolean bl2) {
        if (bl2) {
            this.q.j(0);
        }
        if (i2.c() > 1) {
            this.q.b(i2, this.q.a() - 1);
        } else {
            this.q.a(i2);
        }
        if (this.p.m()) {
            this.r.g(true);
            this.w();
            return;
        }
        this.v();
    }

    public final void u() {
        this.r.c(true);
        if (this.o != null && this.o.a) {
            this.o.a = false;
            this.s.B();
        }
    }

    public final void x() {
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        this.r.c(bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.r.k()) {
            graphics.setColor(z.af);
            graphics.fillRect(0, 0, z.r, z.s - be.a);
            oz.a(graphics, 2, 20, z.r - 4);
            if (this.o != null) {
                du du2 = this.o.b();
                String string = du2.a();
                if (du2.b() != null) {
                    string = du2.b();
                }
                ca.d.c(true);
                ca.d.a(graphics, string, z.r >>> 1, 2, 1);
                ca.d.c();
            }
            graphics.drawImage(oz.d, z.r, z.s - be.a, 40);
            if (!this.p.m()) {
                this.p.a(graphics, this.c(), this.d());
            }
            this.r.a(graphics, this.c(), this.d());
        }
        if (z.x) {
            this.t.a(graphics, this.c(), this.d());
        }
        if (this.p.m()) {
            this.p.a(graphics, this.c(), this.d());
            if (this.z != null) {
                this.z.a(graphics, this.c(), this.d());
            }
        }
    }

    public final void n() {
        this.p.n();
        if (this.z != null && this.A >= 0L) {
            boolean bl2 = this.z.i();
            this.z.n();
            if (this.p.m()) {
                if (bl2) {
                    if (!this.z.i()) {
                        ++this.B;
                        if (this.B > 0) {
                            this.z = null;
                        }
                        this.A = -1L;
                        this.c(true);
                    }
                } else {
                    if (this.B > 0) {
                        this.z = null;
                        this.c(true);
                    }
                    if (System.currentTimeMillis() - this.A > 3000L && this.z != null) {
                        this.z.a();
                    }
                }
            }
        }
        this.r.n();
    }

    private void a(String string, int n2) {
        boolean bl2 = this.o.a(string, n2);
        this.a(this.o.a(this.o.d() - 1), bl2);
    }

    private boolean A() {
        int n2 = this.y + 1;
        String string = this.p.r();
        if (!l.a(string) || n2 > 0) {
            string = string.trim();
            this.a(string, n2);
            if (n2 > 0) {
                dv.a().a(this.o.b().a(), string, null, (short)n2);
            } else {
                dv.a().a(this.o.b().a(), string, null);
            }
            this.p.c("");
            this.y = -1;
            return true;
        }
        return false;
    }

    public final boolean f(int n2) {
        au au2;
        if (this.z != null && this.z.i()) {
            au2 = this.z;
            au2.a(false);
            this.c(true);
            ++this.B;
        }
        if (this.p.m()) {
            if (n2 == 95 && this.A()) {
                return true;
            }
            if (n2 == 99) {
                this.q.k(this.q.a() - 1);
                this.r.g(false);
                this.v();
                if (this.q.r() != null) {
                    au2 = (fp)this.q.o(this.q.a() - 1);
                    ((fp)au2).q();
                }
                return true;
            }
            if (n2 == 98 && ov.g) {
                this.B();
                return true;
            }
            boolean bl2 = this.p.f(n2);
            this.A = System.currentTimeMillis();
            if (!(bl2 || n2 != 97 && n2 != 96)) {
                ak.a().f();
                if (n2 == 97) {
                    return false;
                }
                ak.b().a(this.x, false);
                return true;
            }
            return bl2;
        }
        if (this.r.m()) {
            boolean bl3 = this.r.f(n2);
            if (!bl3 && (n2 == 98 || z.ab && (n2 < 93 || n2 > 99) || n2 >= 148 && n2 <= 157)) {
                this.w();
                this.p.f(n2);
                return true;
            }
            if (bl3) {
                return true;
            }
        }
        return false;
    }

    private void B() {
        hn hn2 = new hn(2);
        hn2.a(this);
        hn2.f(this.C);
        ak.b().a(hn2, false);
    }

    public final boolean g(int n2) {
        return this.p.g(n2);
    }

    public final boolean c(int n2, int n3) {
        int n4 = 0;
        while (n4 < this.u.length) {
            if (this.u[n4].h().b(n2, n3)) {
                this.u[n4].c(n2, n3);
                if (n4 == 2) {
                    this.A();
                }
                if (this.v != n4) {
                    if (n4 == 1) {
                        this.w();
                    } else if (n4 == 0) {
                        this.v();
                    } else {
                        this.u[this.v].d(false);
                        this.v = n4;
                        this.u[this.v].d(true);
                    }
                }
                return true;
            }
            ++n4;
        }
        if (n3 > z.s - be.a && this.p.m()) {
            this.f(98);
            return true;
        }
        return false;
    }

    public final boolean e(int n2, int n3) {
        int n4 = 0;
        while (n4 < this.u.length) {
            if (this.u[n4].m()) {
                this.u[n4].e(n2, n3);
            }
            ++n4;
        }
        return false;
    }

    public final boolean f(int n2, int n3) {
        int n4 = 0;
        while (n4 < this.u.length) {
            if (this.u[n4].m()) {
                this.u[n4].f(n2, n3);
            }
            ++n4;
        }
        return false;
    }

    public final void b(au au2, int n2) {
        int n3;
        Object object;
        Object object2;
        au2 = this;
        this.w = (i)((on)au2).q.i(n2);
        Object[] objectArray = null;
        if (((on)au2).w.a == 2) {
            if (com.mg.sq.a.k != null) {
                objectArray = og.a(((on)au2).o.b.c());
            }
        } else {
            String[] stringArray;
            Object object3 = ((on)au2).w.c(((on)au2).w.c);
            if (((on)au2).w.b >= 0) {
                stringArray = ((r)object3).a(((on)au2).w.b);
                objectArray = null;
                int[] nArray = null;
                switch (stringArray.c()) {
                    case 5: {
                        objectArray = new String[]{"Xem Bang"};
                        nArray = new int[]{10607};
                        break;
                    }
                    case 4: {
                        object3 = ((r)object3).b(stringArray.b(), ((on)au2).w.b);
                        if (((String)object3).charAt(0) == '@') {
                            object3 = ((String)object3).substring(1);
                        }
                        if (!((String)object3).equals(gr.e)) {
                            objectArray = new String[]{"Chat!", "Xem ME"};
                            nArray = new int[]{10609, 10608};
                            break;
                        }
                        objectArray = new String[]{"Xem ME"};
                        nArray = new int[]{10608};
                        break;
                    }
                    case 3: {
                        objectArray = new String[]{"Xem tin", "Ch\u00e9p link"};
                        nArray = new int[]{10611, 10610};
                        break;
                    }
                    case 1: {
                        object3 = lm.a(((lm)stringArray).i);
                        if (object3 == null) break;
                        objectArray = new String[]{object3, "G\u1eedi ti\u1ebfp"};
                        nArray = new int[]{10613, 10612};
                    }
                }
                if (objectArray != null && objectArray.length > 0) {
                    object2 = new String[objectArray.length + 1];
                    object = new int[nArray.length + 1];
                    int n4 = 0;
                    while (n4 < objectArray.length) {
                        object2[n4] = objectArray[n4];
                        object[n4] = nArray[n4];
                        ++n4;
                    }
                    object2[objectArray.length] = "Ch\u00e9p n\u1ed9i dung";
                    object[nArray.length] = 10614;
                } else {
                    object2 = new String[]{"Ch\u00e9p n\u1ed9i dung"};
                    object = new int[]{10614};
                }
            } else {
                object2 = new String[]{"Ch\u00e9p n\u1ed9i dung"};
                object = new int[]{10614};
            }
            if (ff.i != null) {
                stringArray = new String[((String[])object2).length + 1];
                objectArray = new int[((int[])object).length + 1];
                System.arraycopy(object2, 0, stringArray, 0, ((String[])object2).length);
                System.arraycopy(object, 0, objectArray, 0, ((int[])object).length);
                stringArray[stringArray.length - 1] = "D\u00e1n";
                objectArray[objectArray.length - 1] = 10606;
                object2 = stringArray;
                object = objectArray;
            }
            objectArray = new bu[((String[])object2).length];
            n3 = 0;
            while (n3 < objectArray.length) {
                objectArray[n3] = new bu(object2[n3], object[n3]);
                ++n3;
            }
        }
        if (objectArray != null) {
            bv bv2 = new bv();
            bv2.a(new ga(0, 2));
            bv2.b(new ga(1, 3));
            bv2.a((bu[])objectArray);
            object2 = ((on)au2).r.r();
            object = (fp)((on)au2).q.o(n2);
            n3 = ((on)au2).q.c() + (((on)au2).q.e() - bv2.e()) / 2;
            int n5 = ((on)au2).q.d() + ((au)object).d() + ((fp)object).a() - object2.b;
            if (((on)au2).w.b >= 0) {
                n5 += ((on)au2).w.b().a(((on)au2).w.b).e();
            }
            if (n5 + bv2.f() > ((on)au2).q.d() + ((on)au2).q.f()) {
                n5 = ((on)au2).q.d() + ((on)au2).q.f() - bv2.f();
            } else if (n5 < ((on)au2).q.d()) {
                n5 = ((on)au2).q.d();
            }
            bv2.a_(z.r, n5);
            bv2.d(n3, n5);
            bv2.a((bk)((Object)au2));
            bv2.a_(1);
            ((on)au2).a(bv2);
        }
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
        au2 = (fp)this.q.o(n3);
        if (n2 <= n3) {
            ((fp)au2).r();
            return;
        }
        ((fp)au2).q();
    }

    public final au a(ba object, int n2) {
        object = (i)((ba)object).i(n2);
        object = new fp((i)object, this.q.e());
        return object;
    }

    protected final au y() {
        return this;
    }

    public final void d(int n2, int n3) {
        if (n2 == 999999223) {
            Object object = (hn)com.mg.sq.a.q().d(999999223);
            switch (n3) {
                case -8881: {
                    boolean bl2;
                    if (object == null) break;
                    this.C = this.y = ((hn)object).v();
                    String string = gn.a().a[this.y];
                    object = this;
                    int n4 = ((on)object).y + 1;
                    if (!l.a(string) || n4 > 0) {
                        string = string.trim();
                        super.a(string, n4);
                        if (n4 > 0) {
                            dv.a().a(((on)object).o.b().a(), string, null, (short)n4);
                        } else {
                            dv.a().a(((on)object).o.b().a(), string, null);
                        }
                        ((on)object).y = -1;
                        bl2 = true;
                        break;
                    }
                    bl2 = false;
                    break;
                }
                case -8882: {
                    break;
                }
                case -8883: {
                    this.y = -1;
                }
            }
            ak.b().e(999999223);
            return;
        }
        switch (n3) {
            case -3: {
                if (this.x == null) {
                    this.x = new hn(1);
                    this.x.a(this);
                }
                ak.b().a(this.x, false);
                return;
            }
            case -8881: {
                hn hn2 = this.x;
                if (hn2 != null) {
                    this.p.b(hn2.t());
                }
                com.mg.sq.a.q().a(241224, false);
                return;
            }
            case -8882: {
                com.mg.sq.a.q().a(241224, false);
                return;
            }
            case -1: {
                Object[] objectArray;
                Object object;
                Object object2;
                on on2 = this;
                bv bv2 = new bv();
                bv2.a(new ga(0, 2));
                bv2.b(new ga(1, 3));
                Object object3 = on2.o.b().a();
                if (on2.o.a()) {
                    if (object3.length() >= 4) {
                        object3 = new String[]{"K\u1ebft Giao", "Xem", "\u0110\u00f3ng"};
                        object2 = new int[]{10603, 10602, 10604};
                    } else {
                        object3 = new String[]{"K\u1ebft Giao", "\u0110\u00f3ng"};
                        object2 = new int[]{10603, 10604};
                    }
                } else if (object3.length() >= 4) {
                    object3 = new String[]{"Xem", "\u0110\u00f3ng"};
                    object2 = new int[]{10602, 10604};
                } else {
                    object3 = new String[]{"\u0110\u00f3ng"};
                    object2 = new int[]{10604};
                }
                bu bu2 = new bu("Xem", 10602);
                bu2.a(new bu[]{new bu("Th\u00f4ng tin", 10600), new bu("ME", 10601)});
                if (on2.o != null && on2.l() instanceof fc && ((fc)on2.l()).i(100) && (object = (oj)((fc)on2.l()).h(100)) != null && object.o != null && object.o.b() != 2 && !on2.o.b().a().equals(gr.e)) {
                    objectArray = new String[((String[])object3).length + 2];
                    object = new int[((int[])object2).length + 2];
                    System.arraycopy(object3, 0, objectArray, 2, ((String[])object3).length);
                    System.arraycopy(object2, 0, object, 2, ((int[])object2).length);
                    objectArray[0] = (int)"Khi\u00eau Chi\u1ebfn";
                    object[0] = 99024;
                    objectArray[1] = (int)"Giao d\u1ecbch";
                    object[1] = 99014;
                    object3 = objectArray;
                    object2 = object;
                }
                if (ov.g && on2.p.m()) {
                    object = new String[((String[])object3).length + 1];
                    objectArray = new int[((int[])object2).length + 1];
                    System.arraycopy(object3, 0, object, 0, ((String[])object3).length - 1);
                    System.arraycopy(object2, 0, objectArray, 0, ((int[])object2).length - 1);
                    object[((Object[])object).length - 1] = object3[((String[])object3).length - 1];
                    object[((Object[])object).length - 2] = "Th\u00eam Kul";
                    objectArray[((Object[])object).length - 1] = object2[((int[])object2).length - 1];
                    objectArray[((Object[])object).length - 2] = 10605;
                    object3 = object;
                    object2 = objectArray;
                }
                if (ff.i != null) {
                    object = new String[((String[])object3).length + 1];
                    objectArray = new int[((int[])object2).length + 1];
                    System.arraycopy(object3, 0, object, 0, ((String[])object3).length - 1);
                    System.arraycopy(object2, 0, objectArray, 0, ((int[])object2).length - 1);
                    object[((Object[])object).length - 1] = object3[((String[])object3).length - 1];
                    object[((Object[])object).length - 2] = "D\u00e1n";
                    objectArray[((Object[])object).length - 1] = object2[((int[])object2).length - 1];
                    objectArray[((Object[])object).length - 2] = 10606;
                    object3 = object;
                    object2 = objectArray;
                }
                object = new bu[((String[])object3).length];
                int n5 = 0;
                while (n5 < ((String[])object3).length) {
                    object[n5] = object3[n5].equals("Xem") ? bu2 : new bu(object3[n5], object2[n5]);
                    ++n5;
                }
                if (com.mg.sq.a.k != null) {
                    object = og.b((bu[])object, ((Object[])object).length - 1);
                }
                bv2.a((bu[])object);
                n5 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n5, z.s);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(on2);
                on2.a(bv2);
                return;
            }
            case 1: {
                this.t();
                return;
            }
            case 0: {
                this.l.f(95);
                return;
            }
            case -10: {
                ((fc)this.b).a();
            }
        }
    }

    private String C() {
        return this.w.b().b(this.w.b().a(this.w.b).b(), this.w.b);
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        Object object2 = (bu)object;
        int n4 = this.l.b();
        String string = null;
        switch (n3) {
            case 99024: {
                if (this.o == null) break;
                com.mg.sq.a.d(this.o.b().a());
                break;
            }
            case 99014: {
                if (this.o == null) break;
                com.mg.sq.a.p(this.o.b().a());
                break;
            }
            case 10600: {
                if (com.mg.sq.a.k == null || this.o == null) break;
                com.mg.sq.a.b(this.o.b().a(), this.i);
                break;
            }
            case 10601: {
                if (com.mg.sq.a.k == null || this.o == null) break;
                com.mg.sq.a.k.a(this.o.b().a(), 0L);
                break;
            }
            case 10602: {
                break;
            }
            case 10603: {
                if (!this.o.a()) break;
                this.o.a(false);
                if (com.mg.sq.a.k != null) {
                    String string2 = this.o.b().a();
                    object2 = "12 S\u1ee9 Qu\u00e2n Online";
                    dv.a().a((String)object2, string2);
                }
                object2 = ak.b().a("Th\u00f4ng tin", "\u0110\u00e3 th\u00eam nick n\u00e0y v\u00e0o danh s\u00e1ch b\u1ea1n b\u00e8!", "\u0110\u00f3ng", 2, 1);
                ((aq)object2).a(ak.b());
                ak.b().a((ap)object2, false);
                break;
            }
            case 10604: {
                this.s.a(this.o);
                break;
            }
            case 10605: {
                this.B();
                break;
            }
            case 10606: {
                this.p.b(ff.i);
                this.r.d(false);
                this.p.d(true);
                break;
            }
            case 10607: {
                object2 = this.C();
                if (com.mg.sq.a.k == null) break;
                og og2 = com.mg.sq.a.k;
                og2.o.a((String)object2, 0L);
                com.mg.sq.a.k.h(true);
                break;
            }
            case 10608: {
                if (n4 == 1) {
                    string = this.C();
                    if (string != null && string.charAt(0) == '@') {
                        string = string.substring(1);
                    }
                    if (com.mg.sq.a.k == null) break;
                    og og3 = com.mg.sq.a.k;
                    og3.o.a(string, 0L);
                    com.mg.sq.a.k.h(true);
                    break;
                }
                if (com.mg.sq.a.k == null || this.o == null) break;
                com.mg.sq.a.k.a(this.o.b().a(), 0L);
                break;
            }
            case 10609: {
                string = this.C();
                if (string != null && string.charAt(0) == '@') {
                    string = string.substring(1);
                }
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.c(string);
                break;
            }
            case 10610: {
                object2 = this.C();
                ff.i = object2;
                break;
            }
            case 10611: {
                object2 = this.C();
                if (com.mg.sq.a.k == null) break;
                Object object3 = object2;
                object2 = this.o.b().a();
                og og4 = com.mg.sq.a.k;
                og4.a((String)object2, (String)object3, true);
                break;
            }
            case 10612: {
                if (this.w == null) break;
                object2 = (lm)this.w.b().a(this.w.b);
                com.mg.sq.a.d("Nh\u1eadp nick mu\u1ed1n g\u1eedi", ((lm)object2).i.a());
                break;
            }
            case 10613: {
                if (this.w == null) {
                    return;
                }
                if (((String)(object2 = ((bu)object2).b())).equals("Xem \u1ea2nh")) {
                    if (com.mg.sq.a.k == null) break;
                    object2 = (lm)this.w.b().a(this.w.b);
                    og og5 = com.mg.sq.a.k;
                    og5.p.a(((lm)object2).i.a());
                    break;
                }
                com.mg.sq.a.A();
                break;
            }
            case 10614: {
                if (this.w == null) break;
                object2 = this.w.b().i();
                ff.i = object2;
                break;
            }
            case 11399: {
                if (com.mg.sq.a.k == null || com.mg.sq.a.k.b(((bu)object2).b())) break;
                com.mg.sq.a.k.b(((bu)object2).b(), this.o.b.c());
                break;
            }
            default: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.j(n3);
            }
        }
        this.t();
    }

    public final void a(bv bv2) {
        super.a(bv2);
        this.A = -1L;
    }
}

