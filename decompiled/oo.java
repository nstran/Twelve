/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class oo
extends fb
implements bj,
bk,
bq,
bt {
    private ba p;
    private bc q;
    private int r = 0;
    private Object[] s;
    private Object[] t;
    private Object[] u;
    private String[] v;
    private ex w;
    private a x;
    private int y;
    private String z;
    public boolean o = false;
    private ft A;
    private Object B;

    public oo(int n2, int n3, String string) {
        super(109, 7, string);
        this.a(0, 0, z.r, z.s);
        this.v = ca.a("RSS - Ch\u1ee9c n\u0103ng \u0111\u1ecdc tin t\u1ee9c tr\u00ean h\u1ea7u h\u1ebft c\u00e1c trang b\u00e1o tr\u1ef1c tuy\u1ebfn trong n\u01b0\u1edbc. Ti\u1ebft ki\u1ec7m t\u1ed1i \u0111a chi ph\u00ed GPRS khi s\u1eed d\u1ee5ng.", z.r, ca.d);
        this.w = new ex("Xem tin t\u1ee9c", -2);
        this.w.a((z.r - 100) / 2, 10 + ca.d.a() + 10 + 10 + ca.d.a() * this.v.length, 100, 20);
        this.p = new ba();
        this.p.a(this);
        this.p.a(this);
        this.p.f(true);
        this.q = new bc(0);
        this.q.a(0, 0, z.r, z.s - be.a);
        this.q.b(this.p);
        this.a();
        this.a(new be());
        this.j(0);
        this.a(new ga(-1, 0));
        this.c(com.mg.sq.a.l);
        this.b(new ga(-2, 1));
        this.a(this);
        this.x = new a();
        this.y = 0;
    }

    private hc a() {
        hc hc2 = com.mg.sq.a.a("B\u1ea1n ngh\u0129 g\u00ec n\u00e0o?", null, "Xong", 4, "H\u1ee7y", 3, -7, this);
        hc2.b(-989858);
        this.A = (ft)hc2.e(4);
        return hc2;
    }

    private void j(int n2) {
        this.r = n2;
        if (n2 == 0) {
            this.w.d(true);
        }
        this.c(true);
    }

    public final void c(boolean bl2) {
        super.c(bl2);
        if (bl2) {
            this.q.c(true);
        }
    }

    public final boolean f(int n2) {
        if (this.r == 0) {
            if (n2 == 95) {
                com.mg.sq.a.a(null, null, 375);
                dv.a().a(null, (short)this.i);
                return true;
            }
        } else {
            if (n2 == 135) {
                this.G();
                return true;
            }
            if (n2 == 142) {
                this.F();
                return true;
            }
            if (n2 == 148) {
                this.E();
                return true;
            }
            return this.q.f(n2);
        }
        return false;
    }

    public final boolean c(int n2, int n3) {
        if (this.r == 0) {
            if (this.w.h().b(n2, n3)) {
                this.w.d(true);
                this.f(95);
                return true;
            }
        } else {
            return this.q.c(n2, n3);
        }
        return super.c(n2, n3);
    }

    public final boolean e(int n2, int n3) {
        if (this.r != 0) {
            return this.q.e(n2, n3);
        }
        return super.e(n2, n3);
    }

    public final boolean f(int n2, int n3) {
        if (this.r != 0) {
            return this.q.f(n2, n3);
        }
        return super.f(n2, n3);
    }

    public final void n() {
        if (this.r != 0) {
            this.q.n();
        }
    }

    private static void a(Graphics graphics) {
        graphics.setColor(z.af);
        graphics.fillRect(0, 0, z.r, z.s - be.a);
        graphics.drawImage(oz.d, z.r, z.s - be.a, 40);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (this.r == 0) {
            if (this.c) {
                oo.a(graphics);
                ca.d.c(true);
                ca.d.a(graphics, "Gi\u1edbi thi\u1ec7u", z.r >>> 1, 10, 1);
                ca.d.c();
                n2 = 10 + (10 + ca.d.a());
                ca.a(graphics, ca.d, this.v, 0, n2, z.r, z.s, 1);
                this.w.a(graphics, this.c(), this.d());
                this.c(false);
                return;
            }
        } else if (this.q.k()) {
            oo.a(graphics);
            this.q.a(graphics, this.c(), this.d());
        }
    }

    protected final au y() {
        return this;
    }

    public final void u() {
        this.o = false;
        this.c(true);
    }

    public final void x() {
    }

    public final au a(ba object, int n2) {
        if ((object = ((ba)object).i(n2)) instanceof dp) {
            return new fr((dp)object, (n2 - 1 & 1) == 1, this.p.e());
        }
        return new et((ds)object, this.p.e());
    }

    private void v() {
        if (this.x.d() <= 0 || this.r == 0 || this.y < 0 || this.y >= this.x.d()) {
            return;
        }
        String string = (String)this.x.b(this.y);
        if (string.charAt(0) == '\u001a' && Integer.parseInt(String.valueOf(string.charAt(1))) == this.r) {
            this.x.a(this.C(), this.y);
        }
    }

    private void b(String string) {
        this.c(true);
        if (string.charAt(0) == '\u001a') {
            int n2 = Integer.parseInt(String.valueOf(string.charAt(1)));
            int n3 = string.indexOf(26, 1) + 1;
            int n4 = string.indexOf(26, n3);
            this.z = string.substring(n3, n4);
            n3 = n4 + 1;
            n4 = string.indexOf(26, n3);
            n3 = Integer.parseInt(string.substring(n3, n4));
            int n5 = Integer.parseInt(string.substring(n4 + 1));
            if (n2 == 1 && this.s != null) {
                this.D();
                this.p.k(n3);
                this.q.k(n5);
                return;
            }
            if (n2 == 2 && this.t != null) {
                this.A();
                this.p.k(n3);
                this.q.k(n5);
                return;
            }
            if (n2 == 3 && this.u != null) {
                this.B();
                this.p.k(n3);
                this.q.k(n5);
                return;
            }
            this.x.a();
            this.y = 0;
            this.z = null;
            if (this.t != null) {
                this.A();
                return;
            }
            this.a((String)null);
            return;
        }
        this.a(string);
    }

    public final void a(String string, String[] stringArray, String[] stringArray2) {
        this.v();
        try {
            this.t = new Object[stringArray.length + 2];
            this.t[0] = new dp("C\u00e1c danh m\u1ee5c", 0, this.p.e());
            int n2 = 1;
            int n3 = this.t.length - 1;
            while (n2 < n3) {
                int n4 = n2 - 1;
                String string2 = n2 < 10 ? "  " + n2 : "" + n2;
                this.t[n2] = new dp(String.valueOf(string2) + ". " + stringArray[n4], stringArray2[n4], this.p.e() - 2);
                ++n2;
            }
            this.t[this.t.length - 1] = com.mg.sq.a.k.K();
            this.A();
            this.z = string;
            this.c(this.C());
        }
        catch (OutOfMemoryError outOfMemoryError) {
            this.w();
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            cw.b(throwable.toString());
        }
        this.t();
        com.mg.sq.a.t();
    }

    private void w() {
        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "M\u00e1y b\u1ea1n kh\u00f4ng \u0111\u1ee7 b\u1ed9 nh\u1edb \u0111\u1ec3 xem trang b\u00e1o n\u00e0y!", "\u0110\u00f3ng", 5, 1);
        ap2.a(this);
        ak.b().a(ap2, false);
    }

    private void A() {
        this.p.q();
        this.p.h(0);
        this.p.a(this.t);
        if (this.p.a() > 1) {
            this.p.k(1);
        } else {
            this.p.k(0);
        }
        this.j(2);
    }

    public final void b(String string, String[] stringArray, String[] stringArray2) {
        this.v();
        try {
            this.u = new Object[stringArray.length + 2];
            this.u[0] = new dp("C\u00e1c tin m\u1edbi", 0, this.p.e());
            int n2 = 1;
            int n3 = this.u.length - 1;
            while (n2 < n3) {
                int n4 = n2 - 1;
                String string2 = n2 < 10 ? "  " + n2 : "" + n2;
                this.u[n2] = new dp(String.valueOf(string2) + ". " + stringArray[n4], stringArray2[n4], this.p.e() - 2);
                ++n2;
            }
            this.u[this.u.length - 1] = com.mg.sq.a.k.K();
            this.B();
            this.z = string;
            this.c(this.C());
        }
        catch (OutOfMemoryError outOfMemoryError) {
            this.w();
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            cw.b(throwable.toString());
        }
        this.t();
        com.mg.sq.a.t();
    }

    private void B() {
        this.p.q();
        this.p.h(0);
        this.p.a(this.u);
        if (this.p.a() > 1) {
            this.p.k(1);
        } else {
            this.p.k(0);
        }
        this.j(3);
    }

    public final void c(String string, String[] stringArray, String[] stringArray2) {
        this.v();
        try {
            this.s = new Object[stringArray.length + 2];
            this.s[0] = new dp("C\u00e1c trang b\u00e1o", 0, this.p.e());
            int n2 = 1;
            int n3 = this.s.length - 1;
            while (n2 < n3) {
                int n4 = n2 - 1;
                String string2 = n2 < 10 ? "  " + n2 : "" + n2;
                this.s[n2] = new dp(String.valueOf(string2) + ". " + stringArray[n4], stringArray2[n4], this.p.e() - 2);
                ++n2;
            }
            this.s[this.s.length - 1] = com.mg.sq.a.k.K();
            this.D();
            this.z = string;
            this.c(this.C());
        }
        catch (OutOfMemoryError outOfMemoryError) {
            this.w();
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            cw.b(throwable.toString());
        }
        this.t();
        com.mg.sq.a.t();
    }

    private String C() {
        int n2 = this.p.s();
        String string = String.valueOf(n2);
        return "\u001a" + this.r + '\u001a' + this.z + '\u001a' + string + '\u001a' + this.q.r().b;
    }

    private void D() {
        this.p.q();
        this.p.h(0);
        this.p.a(this.s);
        if (this.p.a() > 1) {
            this.p.k(1);
        } else {
            this.p.k(0);
        }
        this.j(1);
    }

    public final void a(String string, eg[] object) {
        this.v();
        try {
            Object[] objectArray = new Object[((eg[])object).length + 1];
            Object[] objectArray2 = objectArray;
            objectArray[0] = new dp(object[0].c(), 0, this.p.e());
            int n2 = 1;
            int n3 = 1;
            int n4 = objectArray2.length - 1;
            while (n3 < n4) {
                int n5 = n3;
                if (!l.b(object[n5].c())) {
                    objectArray2[n2] = new dp(object[n5].c(), 2, this.p.e() - 2);
                    ++n2;
                }
                ++n3;
            }
            objectArray2[n2] = com.mg.sq.a.k.K();
            if (++n2 < objectArray2.length) {
                Object[] objectArray3 = new Object[n2];
                System.arraycopy(objectArray2, 0, objectArray3, 0, n2);
                objectArray2 = objectArray3;
            }
            object = this;
            ((oo)object).p.q();
            ((oo)object).p.h(10);
            ((oo)object).p.a(objectArray2);
            if (((oo)object).p.a() > 1) {
                ((oo)object).p.k(1);
            } else {
                ((oo)object).p.k(0);
            }
            super.j(4);
            this.z = string;
            this.c(string);
        }
        catch (OutOfMemoryError outOfMemoryError) {
            this.w();
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            cw.b(throwable.toString());
        }
        this.t();
        com.mg.sq.a.t();
    }

    private void c(String string) {
        if (this.r != 0 && (this.y < this.x.d() - 1 || this.x.d() > 5)) {
            while (this.x.d() - 1 > this.y) {
                this.x.a(this.x.d() - 1);
            }
            while (this.x.d() > 5) {
                this.x.a(0);
            }
        }
        if (this.y >= this.x.d()) {
            this.y = this.x.d() - 1;
        }
        if (this.y < 0) {
            this.y = 0;
        }
        if (string != null) {
            String string2;
            if (this.x.d() > 0 && (string2 = (String)this.x.b(this.y)).equals(string)) {
                return;
            }
            this.x.a(string);
        }
        this.y = this.x.d() - 1;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public final void b(au object, int n2) {
        int n3;
        void var2_6;
        if (this.r == 0 || var2_6 <= 0) {
            return;
        }
        Object object2 = this.p.i((int)var2_6);
        if (object2 instanceof ds) {
            void var5_21;
            ds ds2 = (ds)object2;
            oo oo2 = this;
            this.B = ds2;
            bu[] buArray = og.a(ds2.c());
            if (buArray == null) return;
            bv bv2 = new bv();
            bv2.a(new ga(0, 2));
            bv2.b(new ga(1, 3));
            bv2.a(buArray);
            int n4 = oo2.p.s();
            au au2 = oo2.p.o(n4);
            n n5 = oo2.q.r();
            int n6 = (z.r - bv2.e()) / 2;
            int n7 = oo2.q.d() + au2.d() - n5.b;
            if (n7 + bv2.f() > z.s - be.a) {
                int n8 = z.s - be.a - bv2.f();
            } else if (n7 < 0) {
                boolean bl2 = false;
            }
            bv2.a_(z.r + bv2.e(), (int)var5_21);
            bv2.d(n6, (int)var5_21);
            bv2.a(oo2);
            bv2.a(new ga(0, 2));
            bv2.b(new ga(1, 3));
            bv2.c(com.mg.sq.a.l);
            bv2.a_(1);
            oo2.a(bv2);
            return;
        }
        dp dp2 = (dp)object2;
        if (this.r != 4) {
            if (var2_6 <= 0) return;
            this.a(dp2.a);
            return;
        }
        Object object3 = dp2;
        oo oo3 = this;
        this.B = object3;
        String[] stringArray = new String[]{"Ch\u00e9p n\u1ed9i dung"};
        int[] nArray = new int[]{10901};
        if (((dp)object3).d >= 0) {
            q q2 = ((dp)object3).c.a(((dp)object3).d);
            String[] stringArray2 = null;
            int[] nArray2 = null;
            switch (q2.c()) {
                case 5: {
                    stringArray2 = new String[]{"Xem Bang"};
                    nArray2 = new int[]{10902};
                    break;
                }
                case 4: {
                    object3 = ((dp)object3).c.b(q2.b(), ((dp)object3).d);
                    if (l.b((String)object3)) return;
                    if (((String)object3).charAt(0) == '@') {
                        object3 = ((String)object3).substring(1);
                    }
                    if (!((String)object3).equals(gr.e)) {
                        stringArray2 = new String[]{"Chat!", "Xem ME"};
                        nArray2 = new int[]{10903, 10913};
                        break;
                    }
                    stringArray2 = new String[]{"Xem ME"};
                    nArray2 = new int[]{10913};
                    break;
                }
                case 3: {
                    stringArray2 = new String[]{"Xem tin", "Ch\u00e9p link"};
                    nArray2 = new int[]{10905, 10904};
                    break;
                }
                case 1: {
                    String string = lm.a(((lm)q2).i);
                    if (string == null) break;
                    stringArray2 = new String[]{string, "G\u1eedi ti\u1ebfp"};
                    nArray2 = new int[]{10914, 10906};
                }
            }
            if (stringArray2 != null && stringArray2.length > 0) {
                object3 = new String[stringArray.length + stringArray2.length];
                int[] nArray3 = new int[nArray.length + nArray2.length];
                System.arraycopy(stringArray2, 0, object3, 0, stringArray2.length);
                System.arraycopy(nArray2, 0, nArray3, 0, nArray2.length);
                n3 = stringArray2.length;
                while (n3 < ((String[])object3).length) {
                    object3[n3] = stringArray[n3 - stringArray2.length];
                    nArray3[n3] = nArray[n3 - nArray2.length];
                    ++n3;
                }
                stringArray = object3;
                int[] nArray4 = nArray3;
            }
        }
        bu[] buArray = new bu[stringArray.length];
        int n9 = 0;
        while (n9 < buArray.length) {
            void var4_16;
            buArray[n9] = new bu(stringArray[n9], (int)var4_16[n9]);
            ++n9;
        }
        bv bv3 = new bv();
        bv3.a(new ga(0, 2));
        bv3.b(new ga(1, 3));
        bv3.a(buArray);
        int n10 = oo3.p.s();
        object3 = oo3.p.o(n10);
        n n11 = oo3.q.r();
        n3 = (z.r - bv3.e()) / 2;
        int n12 = oo3.q.d() + ((au)object3).d() - n11.b;
        if (n12 + bv3.f() > z.s - be.a) {
            n12 = z.s - be.a - bv3.f();
        } else if (n12 < 0) {
            n12 = 0;
        }
        bv3.a_(z.r + bv3.e(), n12);
        bv3.d(n3, n12);
        bv3.a(oo3);
        bv3.a(new ga(0, 2));
        bv3.b(new ga(1, 3));
        bv3.c(com.mg.sq.a.l);
        bv3.a_(1);
        oo3.a(bv3);
    }

    public final void a(String string) {
        com.mg.sq.a.a(null, null, 375);
        dv.a().a(string, (short)this.i);
    }

    public final void a(au au2, int n2) {
    }

    public final void a(au au2, int n2, int n3) {
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case -1: {
                int[] nArray;
                String[] stringArray;
                oo oo2 = this;
                bv bv2 = new bv();
                bv2.a(new ga(0, 2));
                bv2.b(new ga(1, 3));
                if (oo2.x.d() > 0) {
                    if (oo2.y < oo2.x.d() - 1) {
                        if (oo2.y > 0) {
                            stringArray = new String[]{"Trang tr\u01b0\u1edbc", "Trang k\u1ebf", "Trang ch\u1ee7", "\u0110\u00f3ng"};
                            nArray = new int[]{10907, 10909, 10908, 10900};
                        } else {
                            stringArray = new String[]{"Trang k\u1ebf", "Trang ch\u1ee7", "\u0110\u00f3ng"};
                            nArray = new int[]{10909, 10908, 10900};
                        }
                    } else if (oo2.y > 0) {
                        stringArray = new String[]{"Trang tr\u01b0\u1edbc", "Trang ch\u1ee7", "\u0110\u00f3ng"};
                        nArray = new int[]{10907, 10908, 10900};
                    } else {
                        stringArray = new String[]{"Trang ch\u1ee7", "\u0110\u00f3ng"};
                        nArray = new int[]{10908, 10900};
                    }
                } else {
                    stringArray = new String[]{"Trang ch\u1ee7", "\u0110\u00f3ng"};
                    nArray = new int[]{10908, 10900};
                }
                bu[] buArray = new bu[stringArray.length];
                int n4 = 0;
                int n5 = stringArray.length;
                while (n4 < n5) {
                    buArray[n4] = new bu(stringArray[n4], nArray[n4]);
                    ++n4;
                }
                if (com.mg.sq.a.k != null) {
                    buArray = com.mg.sq.a.k.a(buArray, buArray.length - 1);
                    buArray = og.b(buArray, buArray.length - 1);
                }
                if (oo2.r == 4) {
                    bu[] buArray2 = new bu[buArray.length + 1];
                    System.arraycopy(buArray, 0, buArray2, 0, buArray.length - 1);
                    buArray2[buArray2.length - 1] = buArray[buArray.length - 1];
                    buArray2[buArray2.length - 2] = new bu("Thao t\u00e1c", 10911);
                    bu[] buArray3 = new bu[]{new bu("G\u1eedi Me", 10912), new bu("Ch\u00e9p link", 10904)};
                    buArray2[buArray2.length - 2].a(buArray3);
                    buArray = buArray2;
                }
                bv2.a(buArray);
                int n6 = bv2.e() > bv2.f() ? bv2.e() : bv2.f();
                bv2.a_(-n6, z.s);
                bv2.d(0, z.s - be.a - bv2.f());
                bv2.a(oo2);
                oo2.a(bv2);
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
            case 4: {
                Object object = (hc)com.mg.sq.a.q().d(-989858);
                object = (ff)((hc)object).e(1);
                object = String.valueOf(((ff)object).r()) + " " + oo.d(this.z);
                n3 = this.A.r() + 1;
                com.mg.sq.a.q().a(false);
                og og2 = com.mg.sq.a.k;
                og2.o.a((String)object, 0L, n3);
                return;
            }
            case 3: {
                com.mg.sq.a.q().a(-989858, false);
                return;
            }
            case 5: {
                com.mg.sq.a.q().a(false);
                if (this.y >= 0 && this.y < this.x.d()) {
                    this.b((String)this.x.b(this.y));
                    return;
                }
                this.a((String)null);
                return;
            }
            case -7: {
                hn hn2 = new hn(2);
                hn2.a(this);
                ak.b().a(hn2, false);
                return;
            }
            case -8882: {
                ak.b().e(999999223);
                return;
            }
            case -8881: {
                hn hn3 = (hn)com.mg.sq.a.q().d(999999223);
                if (this.A == null || hn3 == null || ov.f == null) break;
                n3 = hn3.v();
                this.A.a(ov.f);
                this.A.h(n3);
                this.A.b(ov.a[n3], ov.b[n3], ov.c[n3], ov.d[n3]);
                hn3.e(n3);
                ak.b().e(999999223);
                return;
            }
            case -8883: {
                hn hn4 = (hn)com.mg.sq.a.q().d(999999223);
                if (hn4 != null) {
                    hn4.u();
                    this.A.a((Image)null);
                }
                ak.b().e(999999223);
                return;
            }
            default: {
                ((fc)this.b).a();
            }
        }
    }

    private static String d(String string) {
        return "rss://" + string;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void a(int n2, int n3, Object object) {
        Object object2;
        Object object3;
        if (object == null) {
            return;
        }
        bu bu2 = (bu)object;
        int n4 = this.l.b();
        if (n4 == 1) {
            if (this.B instanceof dp) {
                Object object4 = (dp)this.B;
                object3 = ((dp)object4).c.b(((dp)object4).c.a(((dp)object4).d).b(), ((dp)object4).d);
                switch (n3) {
                    case 10901: {
                        object2 = ((dp)object4).c.i();
                        ff.i = object2;
                        break;
                    }
                    case 10914: {
                        if (bu2.b().equals("Xem \u1ea2nh")) {
                            if (com.mg.sq.a.k == null) break;
                            object4 = (lm)((dp)object4).c.a(((dp)object4).d);
                            object3 = com.mg.sq.a.k;
                            ((og)object3).p.a(((lm)object4).i.a());
                            break;
                        }
                        if (!bu2.b().equals("Xem video") && !bu2.b().equals("Nghe audio")) break;
                        com.mg.sq.a.A();
                        break;
                    }
                    case 10906: {
                        object4 = (lm)((dp)object4).c.a(((dp)object4).d);
                        com.mg.sq.a.d("Nh\u1eadp nick mu\u1ed1n g\u1eedi", ((lm)object4).i.a());
                        break;
                    }
                    case 10905: {
                        object4 = ((dp)object4).c.b(((dp)object4).c.a(((dp)object4).d).b(), ((dp)object4).d);
                        if (com.mg.sq.a.k == null) break;
                        object2 = object4;
                        object4 = null;
                        object3 = com.mg.sq.a.k;
                        ((og)object3).a((String)object4, (String)object2, true);
                        break;
                    }
                    case 10904: {
                        object4 = ((dp)object4).c.b(((dp)object4).c.a(((dp)object4).d).b(), ((dp)object4).d);
                        ff.i = object4;
                        break;
                    }
                    case 10913: {
                        if (l.b((String)object3)) break;
                        if (((String)object3).charAt(0) == '@') {
                            object3 = ((String)object3).substring(1);
                        }
                        if (com.mg.sq.a.k == null) break;
                        com.mg.sq.a.k.a((String)object3, 0L);
                        break;
                    }
                    case 10903: {
                        if (l.b((String)object3)) break;
                        if (((String)object3).charAt(0) == '@') {
                            object3 = ((String)object3).substring(1);
                        }
                        if (com.mg.sq.a.k == null) break;
                        com.mg.sq.a.k.c((String)object3);
                        break;
                    }
                    case 10902: {
                        object4 = ((dp)object4).c.b(((dp)object4).c.a(((dp)object4).d).b(), ((dp)object4).d);
                        if (com.mg.sq.a.k == null) break;
                        com.mg.sq.a.k.a((String)object4, 0L);
                        break;
                    }
                    default: {
                        if (bu2.b().equals("Xem \u1ea2nh")) {
                            if (com.mg.sq.a.k == null) break;
                            object4 = (lm)((dp)object4).c.a(((dp)object4).d);
                            object3 = com.mg.sq.a.k;
                            ((og)object3).p.a(((lm)object4).i.a());
                            break;
                        }
                        com.mg.sq.a.A();
                        break;
                    }
                }
            } else if (com.mg.sq.a.k != null) {
                com.mg.sq.a.k.b(bu2.b(), ((ds)this.B).c());
            }
            this.t();
        }
        switch (n3) {
            case 10908: {
                this.E();
                break;
            }
            case 10907: {
                this.F();
                break;
            }
            case 10909: {
                this.G();
                break;
            }
            case 10912: {
                this.a();
                int n5 = oo.d(this.z).length() + 1;
                object3 = this.a();
                object2 = (ff)((hc)object3).e(1);
                ((ff)object2).h(300 - n5);
                ak.b().a((ap)object3, false);
                break;
            }
            case 10904: {
                ff.i = oo.d(this.z);
                break;
            }
            case 10900: {
                this.o = true;
                if (this.b == null) break;
                ((fc)this.b).d(this);
                break;
            }
            case 11399: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.b(bu2.b());
                break;
            }
            default: {
                if (com.mg.sq.a.k == null) break;
                com.mg.sq.a.k.j(n3);
            }
        }
        this.t();
    }

    private void E() {
        if (this.s != null) {
            this.v();
            this.D();
            this.z = null;
            this.c(this.C());
            return;
        }
        this.a((String)null);
    }

    private void F() {
        if (this.y > 0) {
            this.v();
            --this.y;
            this.b((String)this.x.b(this.y));
        }
    }

    private void G() {
        if (this.y < this.x.d() - 1) {
            this.v();
            ++this.y;
            this.b((String)this.x.b(this.y));
        }
    }
}

