/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class os
extends aq
implements bf,
bg,
bq {
    private oa m;
    private k n;
    private k o;
    private ay p;
    private int q;
    private boolean r = false;
    private do s;
    lh i;
    private dd t;
    private byte u = (byte)-1;
    public String j;
    fk k = new fk();
    private int v = 0;
    private String w;
    private ol x;
    private if y = new if(new int[]{5939728, 0xFFFF00});
    public static lr[] l;
    private String z;
    private long A;
    private int B;

    public os(int n2, oa oa2, ol ol2) {
        this.m = oa2;
        this.x = ol2;
        this.a_(3);
        this.a(0, 0, v.t, v.u);
        this.a((byte)0);
        this.x.a(new gb(105, 0));
        this.x.b(new gb(109, 1));
        this.x.c(com.mg.sq.a.n);
        this.x.a(new ba());
        this.x.a(this);
    }

    private void a(byte by2) {
        this.u = by2;
        os os2 = this;
        switch (os2.u) {
            case 1: {
                os2.n = new k(0, 73, v.t, 1);
                os2.o = new k(0, os2.n.b + 4, os2.e(), os2.f() - (os2.n.b + 4) - ba.a);
                break;
            }
            default: {
                os2.n = new k(0, 5, v.t, 1);
                os2.o = new k(0, os2.n.b + 5, os2.e(), os2.f() - (os2.n.b + 4) - ba.a);
            }
        }
        this.k = new fk();
        this.k.i = true;
        this.k.a(this);
        this.k.a_(this.o.a, this.o.b);
        this.p = new ay(0);
        this.p.a(this.o);
        this.p.b(this.k);
        this.p.h(1);
        switch (by2) {
            case 1: {
                this.v = 0;
                boolean bl2 = go.k.aa;
                if (bl2) {
                    go.k.e = (byte)2;
                } else if (go.k.e == 2) {
                    go.k.e = 0;
                }
                do do_ = new do();
                new do().a = go.k.b;
                do_.d = go.k.ab;
                do_.b = go.k.G;
                do_.c = go.k.e;
                do_.f = go.k.X;
                do_.e = go.k.P;
                this.a(this.j, new do[]{do_});
                ks.a().d();
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
            graphics.setColor(v.am);
            graphics.fillRect(0, 0, ((aq)object).e(), ((aq)object).f());
            graphics.drawImage(pc.d, ((aq)object).c() + ((aq)object).e(), ((aq)object).d() + ((aq)object).f() - ba.a, 40);
            switch (((os)object).u) {
                case 1: {
                    if (((os)object).t != null) {
                        ((os)object).t.a(graphics, 0, 0);
                    }
                    pc.a(graphics, ((os)object).n.a, ((os)object).n.b, ((os)object).n.c);
                    if (((os)object).z == null) break;
                    long l2 = ((os)object).A - System.currentTimeMillis() > 0L ? ((os)object).A - System.currentTimeMillis() : 0L;
                    n3 = v.t - bx.c.a(((os)object).z) - bx.c.a(i.b(l2, "hh:mm:ss")) - 5;
                    com.mg.sq.a.h.a(graphics, String.valueOf(((os)object).z) + " " + i.b(l2, "hh:mm:ss"), n3, v.u - 35, 0);
                }
            }
            if (((os)object).p != null) {
                cw.a(graphics);
                cw.b(graphics, ((os)object).p.h());
                ((os)object).p.a(graphics, ((aq)object).c(), ((aq)object).d());
                cw.b(graphics);
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
                ks.a().a(this.w, 91, (byte)101);
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

    public final synchronized void a(String string, do[] doArray) {
        int n2;
        if (string == null || !this.x.t) {
            return;
        }
        com.mg.sq.a.s().v();
        if (this.u != 1) {
            this.a((byte)1);
        }
        try {
            if (this.a(string)) {
                int n3 = 0;
                while (n3 < doArray.length) {
                    if (doArray[n3] != null) {
                        this.a(doArray[n3]);
                    }
                    ++n3;
                }
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            com.mg.sq.a.m.G();
        }
        if ((n2 = this.b(go.k.b)) >= 0) {
            this.k.i(n2);
        } else {
            this.k.i(0);
        }
        this.t();
    }

    private void a(do object) {
        try {
            int n2;
            ew ew2;
            block10: {
                ew2 = ((do)object).a.length() < 6 ? new ew((do)object, this.e(), this.y) : new ew((do)object, this.e());
                ew2.a_(this.v++);
                String string = ((do)object).a;
                object = this;
                if (((os)object).k.r() == 0) {
                    n2 = -1;
                } else {
                    int n3 = 0;
                    int n4 = ((os)object).k.r();
                    int n5 = n4 + 0 >> 1;
                    while (true) {
                        do do_ = ((ew)((os)object).k.j((int)n5)).i;
                        if (string.compareTo(do_.a) > 0) {
                            n3 = n5;
                            n5 = n3 + n4 >> 1;
                        } else {
                            if (string.compareTo(do_.a) >= 0) {
                                n2 = n5;
                                break block10;
                            }
                            n4 = n5;
                            n5 = n3 + n4 >> 1;
                        }
                        if (n3 != n5) continue;
                        do_ = ((ew)((os)object).k.j((int)n5)).i;
                        if (string.compareTo(do_.a) > 0) {
                            n2 = n4 == ((os)object).k.r() ? -1 : n4;
                            break block10;
                        }
                        if (string.compareTo(do_.a) < 0) break;
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
            com.mg.sq.a.m.G();
            ct.a("[RoomTab] khong du bo nho them ng choi vao room");
            return;
        }
    }

    private boolean a(String string) {
        return string.equals(this.j);
    }

    public final void a(lh lh2) {
        this.i = lh2;
        this.t = new dd(this.i);
        this.c(true);
    }

    public final void b(aq aq2, int n2) {
        block28: {
            block27: {
                block29: {
                    aq aq3;
                    Object object;
                    block31: {
                        block30: {
                            if (!(aq2 instanceof ew)) break block27;
                            if (this.k.r() <= 1) break block28;
                            aq2 = (ew)aq2;
                            object = ((ew)aq2).i;
                            aq2 = this;
                            if (((do)object).a.equals(go.k.b)) break block29;
                            ((os)aq2).s = object;
                            object = new bs();
                            if (com.mg.sq.a.m == null || !com.mg.sq.a.o) break block30;
                            aq3 = (fc)((os)aq2).x.l();
                            switch (((os)aq2).s.c) {
                                case 2: {
                                    if (((fc)aq3).b(com.mg.sq.a.m)) {
                                        if ((((os)aq2).B & 2) != 0) {
                                            ((bs)object).a(new br("Giao d\u1ecbch", 10100));
                                        }
                                        ((bs)object).a(new br[]{new br("Chat!", 10101), new br("Xem ME", 10102)});
                                        break;
                                    }
                                    ((bs)object).a(new br[]{new br("Giao d\u1ecbch", 10100)});
                                    break;
                                }
                                case 3: {
                                    if (((fc)aq3).b(com.mg.sq.a.m)) {
                                        ((bs)object).a(new br[]{new br("Chat!", 10101), new br("Xem ME", 10102)});
                                        break;
                                    }
                                    break block28;
                                }
                                case 1: {
                                    if (((fc)aq3).b(com.mg.sq.a.m)) {
                                        if ((((os)aq2).B & 4) != 0) {
                                            ((bs)object).a(new br("Xem Tr\u1eadn \u0111\u00e1nh", 10103));
                                        }
                                        ((bs)object).a(new br[]{new br("Chat!", 10101), new br("Xem ME", 10102)});
                                        break;
                                    }
                                    ((bs)object).a(new br[]{new br("Xem Tr\u1eadn \u0111\u00e1nh", 10103)});
                                    break;
                                }
                                default: {
                                    if (((fc)aq3).b(com.mg.sq.a.m)) {
                                        if (((os)aq2).s.f > 0L) {
                                            ((bs)object).a(new br[]{new br("\u0110\u00e1nh!", 10104), new br("Giao d\u1ecbch", 10100), new br("Chat!", 10101), new br("Xem ME", 10102)});
                                            break;
                                        }
                                        if ((((os)aq2).B & 1) != 0) {
                                            ((bs)object).a(new br("Khi\u00eau Chi\u1ebfn", 10105));
                                        }
                                        if ((((os)aq2).B & 2) != 0) {
                                            ((bs)object).a(new br("Giao d\u1ecbch", 10100));
                                        }
                                        ((bs)object).a(new br[]{new br("Chat!", 10101), new br("Xem ME", 10102)});
                                        break;
                                    }
                                    if (((os)aq2).s.f > 0L) {
                                        ((bs)object).a(new br[]{new br("\u0110\u00e1nh!", 10104), new br("Giao d\u1ecbch", 10100)});
                                        break;
                                    }
                                    if ((((os)aq2).B & 1) != 0) {
                                        ((bs)object).a(new br("Khi\u00eau Chi\u1ebfn", 10105));
                                    }
                                    if ((((os)aq2).B & 2) != 0) {
                                        ((bs)object).a(new br("Giao d\u1ecbch", 10100));
                                        break;
                                    }
                                    break block31;
                                }
                            }
                            break block31;
                        }
                        switch (((os)aq2).s.c) {
                            case 2: {
                                if ((((os)aq2).B & 2) == 0) break;
                                ((bs)object).a(new br("Giao d\u1ecbch", 10100));
                                break;
                            }
                            case 3: {
                                break block28;
                            }
                            case 1: {
                                if ((((os)aq2).B & 4) == 0) break;
                                ((bs)object).a(new br("Xem Tr\u1eadn \u0111\u00e1nh", 10103));
                                break;
                            }
                            default: {
                                if (((os)aq2).s.f > 0L) {
                                    ((bs)object).a(new br[]{new br("\u0110\u00e1nh!", 10104), new br("Giao d\u1ecbch", 10100)});
                                    break;
                                }
                                if ((((os)aq2).B & 1) != 0) {
                                    ((bs)object).a(new br("Khi\u00eau Chi\u1ebfn", 10105));
                                }
                                if ((((os)aq2).B & 2) == 0) break;
                                ((bs)object).a(new br("Giao d\u1ecbch", 10100));
                            }
                        }
                    }
                    aq3 = ((os)aq2).k.u();
                    k k2 = ((os)aq2).p.r();
                    int n3 = (v.t - ((aq)object).e()) / 2;
                    int n4 = ((os)aq2).p.d() + aq3.d() - k2.b;
                    if (n4 + ((aq)object).f() > v.u - ba.a) {
                        n4 = v.u - ba.a - ((aq)object).f();
                    }
                    ((bs)object).a_(v.t + ((aq)object).e(), n4);
                    ((bs)object).d(n3, n4);
                    ((bs)object).a((bg)((Object)aq2));
                    ((bs)object).a(new gb(113, 2));
                    ((bs)object).b(new gb(106, 3));
                    ((bs)object).c(com.mg.sq.a.n);
                    ((aq)object).a_(1);
                    ((os)aq2).x.a((bs)object);
                }
                return;
            }
            if (aq2 instanceof fz) {
                aq2 = (fz)aq2;
                if (!((fz)aq2).i.a()) {
                    ks.a().q(((fz)aq2).i.b);
                    com.mg.sq.a.s().a((String)null, (il)null);
                }
            }
        }
    }

    public final void a(aq aq2, int n2) {
    }

    public final void a(aq aq2, int n2, int n3) {
        this.t();
        aq2 = this.k.j(n3);
        if (aq2 instanceof ew) {
            aq2 = (ew)this.k.j(n3);
            if (this.i != null && this.i.b.equals(((ew)aq2).i.a)) {
                return;
            }
            if (((ew)aq2).i.a.equals(go.k.b)) {
                this.a(go.k);
                return;
            }
            this.w = ((ew)aq2).i.a;
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
                os os2 = this;
                bs bs2 = new bs();
                oa cfr_ignored_0 = os2.m;
                bs2.a(oa.a(os2.x));
                int n4 = bs2.e() > bs2.f() ? bs2.e() : bs2.f();
                bs2.a_(-n4, os2.f() - bs2.f() + n4);
                bs2.d(0, v.u - ba.a - bs2.f());
                bs2.a(os2);
                gb gb2 = new gb(113, 2);
                gb gb3 = new gb(106, 3);
                bs2.c(com.mg.sq.a.n);
                bs2.a(gb2);
                bs2.b(gb3);
                os2.x.a(bs2);
                return;
            }
            case 0: {
                ag.b().a(-9, false);
            }
        }
    }

    public final void a(int n2, int n3, Object object) {
        if (object == null) {
            return;
        }
        Object object2 = (br)object;
        switch (n3) {
            case 10101: {
                if (com.mg.sq.a.m == null) break;
                object2 = com.mg.sq.a.m.c(this.s.a);
                ((ds)object2).d("patriot");
                break;
            }
            case 10105: {
                com.mg.sq.a.d(this.s.a);
                break;
            }
            case 10102: {
                if (com.mg.sq.a.m == null) break;
                com.mg.sq.a.m.a(this.s.a, 0L);
                break;
            }
            case 10103: {
                com.mg.sq.a.s().r(this.s.a);
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
                ks.a().e();
                ks.a().r();
                com.mg.sq.a.s().a((String)null, (il)null);
                break;
            }
            default: {
                this.m.a(n3, (br)object2);
            }
        }
        this.x.t();
    }

    public final void b(lh lh2) {
        aq aq2 = this.k.u();
        if (aq2 instanceof ew) {
            aq2 = (ew)aq2;
            if (((ew)aq2).i.a.equals(lh2.b)) {
                this.a(lh2);
                this.i = lh2;
            }
        }
    }

    public final synchronized void a(String string, do do_, String string2, long l2) {
        int n2;
        if (do_ == null) {
            return;
        }
        if (this.u == 0) {
            return;
        }
        if (this.a(string) && (n2 = this.b(do_.a)) >= 0) {
            try {
                ((ew)this.k.j(n2)).a(do_, string2, l2);
                return;
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
                System.gc();
            }
        }
    }

    public final void a(lr[] lrArray) {
        if (this.u != 0) {
            this.a((byte)0);
        }
        this.k.t();
        int n2 = 0;
        while (n2 < lrArray.length) {
            this.k.b(new fz(lrArray[n2]));
            ++n2;
        }
        this.k.i(0);
        com.mg.sq.a.s().v();
        this.c(true);
    }

    public final void b(lr[] lrArray) {
        if (this.u == 0) {
            this.a((byte)0);
            int n2 = 0;
            while (n2 < lrArray.length) {
                int n3 = 0;
                while (n3 < l.length) {
                    if (lrArray[n2].b.equals(os.l[n3].b)) {
                        os.l[n3].f = lrArray[n2].f;
                        os.l[n3].e = lrArray[n2].e;
                        os.l[n3].a = lrArray[n2].a;
                        os.l[n3].c = lrArray[n2].c;
                        os.l[n3].d = lrArray[n2].d;
                        os.l[n3].b = lrArray[n2].b;
                        os.l[n3].g = lrArray[n2].g;
                    }
                    ++n3;
                }
                ++n2;
            }
        }
    }

    private int b(String string) {
        do do_;
        if (this.k.r() == 0) {
            return -1;
        }
        int n2 = 0;
        int n3 = this.k.r();
        int n4 = n3 + 0 >> 1;
        do {
            do_ = ((ew)this.k.j((int)n4)).i;
            if (string.compareTo(do_.a) > 0) {
                n2 = n4;
                n4 = n2 + n3 >> 1;
                continue;
            }
            if (string.compareTo(do_.a) < 0) {
                n3 = n4;
                n4 = n2 + n3 >> 1;
                continue;
            }
            return n4;
        } while (n2 != n4);
        do_ = ((ew)this.k.j((int)n4)).i;
        if (string.equals(do_.a)) {
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
        aq aq2 = this.k.j(n3);
        int n4 = 0;
        if (aq2.d() - n2 < 0) {
            n4 = aq2.d() - n2;
        } else if (aq2.d() + aq2.f() > n2 + this.p.r().d) {
            n4 = aq2.d() + aq2.f() - (n2 + this.p.r().d);
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
        ks.a().e(string);
    }

    public final byte q() {
        return this.u;
    }

    public final synchronized void b(String string, do do_, String string2, long l2) {
        if (do_ == null) {
            return;
        }
        if (this.a(string)) {
            int n2 = this.b(do_.a);
            if (n2 >= 0) {
                this.a(string, do_, string2, l2);
                return;
            }
            this.a(do_);
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
            ks.a().e();
            this.k.t();
            System.gc();
        }
    }
}

