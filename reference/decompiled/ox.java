/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 *  javax.microedition.rms.RecordStore
 */
import com.mg.smsgame.MGMIDlet;
import javax.microedition.lcdui.Image;
import javax.microedition.rms.RecordStore;

public final class ox
implements ik,
Runnable {
    private static int a = 2;
    private static ox b;
    private y c;
    private a d;
    private int e;
    private final Object f = new Object();
    private ij g;
    private ii h;
    private jm i;
    private int[] j;
    private byte[][] k;
    private int l;
    private int m;
    private oy n;
    private boolean o = false;
    private int p;
    private boolean q;
    private int r;
    private boolean s = false;
    private int t;
    private long u = 0L;
    private long v = 0L;
    private boolean w = false;
    private boolean x = false;
    private int y = 0;

    public static ox a() {
        if (b == null) {
            b = new ox();
        }
        return b;
    }

    protected ox() {
        this.e = pa.F();
        if (z.aa || gp.j) {
            this.r = 180000;
            a = 1;
        } else {
            this.r = Integer.MAX_VALUE;
            a = 2;
        }
        this.h();
        this.n = new oy(this);
        kq.a().a(this.n);
        Thread thread = new Thread(this);
        thread.setPriority(10);
        thread.start();
    }

    private boolean f() {
        long l2 = System.currentTimeMillis();
        int n2 = 0;
        while (n2 < this.d.d()) {
            y y2 = (y)this.d.b(n2);
            int n3 = y2.a();
            if (n3 < 0) {
                if (!gp.j && n3 == -2) {
                    this.b();
                    String[] stringArray = RecordStore.listRecordStores();
                    if (stringArray != null) {
                        int n4 = 0;
                        while (n4 < stringArray.length) {
                            if (stringArray[n4] != null && (n2 = stringArray[n4].indexOf("installcacher")) >= 0) {
                                j.b(stringArray[n4]);
                            }
                            ++n4;
                        }
                    }
                    pa.e(0);
                    pa.i(0);
                    pa.o();
                    pa.s();
                    MGMIDlet.f().d();
                    return false;
                }
                ox.d(n3);
                return false;
            }
            ++n2;
        }
        this.u += System.currentTimeMillis() - l2;
        return true;
    }

    private static void d(int n2) {
        if (n2 == -2) {
            com.mg.sq.a.j(2);
            return;
        }
        if (n2 < 0) {
            com.mg.sq.a.j(3);
        }
    }

    public final void b() {
        if (this.d == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.d.d()) {
            y y2 = (y)this.d.b(n2);
            if (y2 != null) {
                y2.d();
            }
            ++n2;
        }
    }

    public final void c() {
        if (this.d == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.d.d()) {
            y y2 = (y)this.d.b(n2);
            if (y2 != null) {
                y.a(y2);
            }
            ++n2;
        }
    }

    private y a(y y2, int n2, byte[] byArray) {
        long l2 = System.currentTimeMillis();
        int n3 = 0;
        while (n3 < this.d.d()) {
            y y3 = (y)this.d.b(n3);
            if (!y3.equals(y2) && y3.c(n2)) {
                boolean bl2 = y3.a(n2, byArray);
                n3 = bl2 ? 1 : 0;
                if (!bl2) break;
                return y2;
            }
            ++n3;
        }
        this.v += System.currentTimeMillis() - l2;
        if (y2.c() < 300) {
            boolean bl3 = y2.a(n2, byArray);
            n3 = bl3 ? 1 : 0;
            if (bl3) {
                return y2;
            }
        }
        y y4 = this.g();
        y4.a(n2, byArray);
        return y4;
    }

    private y g() {
        y y2 = this.a("installcacher" + this.e);
        ++this.e;
        pa.i(this.e);
        this.d.a(y2);
        return y2;
    }

    private y a(String string) {
        return y.a(string, 512, 15360, 0, 0, this.r, false);
    }

    private void h() {
        this.d = new a();
        String[] stringArray = RecordStore.listRecordStores();
        if (stringArray != null) {
            int n2 = 0;
            while (n2 < stringArray.length) {
                int n3;
                if (stringArray[n2] != null && (n3 = stringArray[n2].indexOf("installcacher")) >= 0 && !stringArray[n2].equals("tmpinstallcacher")) {
                    this.d.a(this.a(stringArray[n2]));
                }
                ++n2;
            }
        }
    }

    private int e(int n2) {
        if (this.d == null) {
            this.h();
        }
        int n3 = 0;
        while (n3 < this.d.d()) {
            y y2 = (y)this.d.b(n3);
            int n4 = y2.d(n2);
            if (n4 > 0) {
                return n4;
            }
            ++n3;
        }
        return 0;
    }

    private static y b(String string) {
        return y.a("map" + string, 512, 5120);
    }

    public final Image a(int n2, boolean bl2) {
        int n3 = n2;
        n2 = 0;
        int n4 = n3;
        ox ox2 = this;
        return f.a(ox2.c(n4));
    }

    public final Image a(int n2) {
        return this.a(n2, false);
    }

    public final byte[] b(int n2) {
        int n3 = n2;
        n2 = 0;
        int n4 = n3;
        ox ox2 = this;
        return ox2.c(n4);
    }

    public final byte[] c(int n2) {
        if (this.d == null) {
            this.h();
        }
        int n3 = 0;
        while (n3 < this.d.d()) {
            y y2 = (y)this.d.b(n3);
            if (y2.c(n2)) {
                return y2.a(n2);
            }
            ++n3;
        }
        return null;
    }

    public final void a(String string, ij ij2) {
        Object object;
        cw.b("[SQDataCacher] getMapResource");
        if (this.o) {
            return;
        }
        this.o = true;
        if (com.mg.sq.a.q().d(11111) != null) {
            com.mg.sq.a.t();
            com.mg.sq.a.a(null, null);
        }
        int n2 = -1;
        if (!string.toUpperCase().equals("M99") && j.a("map" + (String)(object = string))) {
            int n3;
            y y2 = ox.b(string);
            object = y2;
            n2 = -1;
            if (object == null) {
                n3 = -1;
            } else {
                byte[] byArray = ((y)object).a(-1987);
                object = byArray;
                if (byArray != null) {
                    n2 = p.a((byte[])object, 0);
                }
                n3 = n2;
            }
            n2 = n3;
        }
        this.g = ij2;
        kq.a().a(string, n2);
    }

    public final void a(int n2, int[] nArray, int n3, ii ii2) {
        y y2;
        block9: {
            this.h = ii2;
            if (nArray == null) {
                if (this.h != null) {
                    this.h.a();
                }
                return;
            }
            long l2 = pa.b(nArray);
            this.t = pa.a(l2);
            if (this.t >= nArray.length) {
                pa.j();
                pa.b(l2);
                this.t = 0;
            }
            int n4 = 0;
            int n5 = 0;
            while (n5 < this.t) {
                int n6 = this.e(nArray[n5]);
                if (n6 > 0) {
                    n4 += n6;
                } else {
                    pa.j();
                    pa.b(l2);
                    this.t = 0;
                    n4 = 0;
                    break;
                }
                ++n5;
            }
            if (this.t > 0) {
                int[] nArray2 = new int[nArray.length - this.t];
                System.arraycopy(nArray, this.t, nArray2, 0, nArray2.length);
                nArray = nArray2;
            }
            ox.a(n4, n3);
            ox ox2 = this;
            String string = "installcacher" + (ox2.e - 1);
            int n7 = 0;
            while (n7 < ox2.d.d()) {
                y y3 = (y)ox2.d.b(n7);
                if (y3.b().equals(string)) {
                    y2 = y3;
                    break block9;
                }
                ++n7;
            }
            y2 = ox2.g();
        }
        this.c = y2;
        this.p = n2;
        this.a(0, nArray);
    }

    private static void a(int n2, int n3) {
        nv.a = n3;
        nv.b = n2;
        nv.c = System.currentTimeMillis();
    }

    public final void a(jm jm2, int[] nArray, int n2, int[] nArray2, int n3) {
        int n4;
        jm2.o = nArray;
        jm2.p = n2;
        this.i = jm2;
        ox.a(this.i.n);
        int n5 = 0;
        a a2 = new a(10);
        if (nArray2 != null) {
            n4 = 0;
            while (n4 < nArray2.length) {
                int n6 = this.e(nArray2[n4]);
                if (n6 <= 0) {
                    a2.a(new Integer(nArray2[n4]));
                } else {
                    n5 += n6;
                }
                ++n4;
            }
        }
        if (a2.d() > 0) {
            this.s = true;
            jm2.p += n3 - n5;
            if (nArray2 != null && nArray2.length != a2.d()) {
                nArray2 = new int[a2.d()];
                n4 = 0;
                while (n4 < nArray2.length) {
                    nArray2[n4] = (Integer)a2.b(n4);
                    ++n4;
                }
            }
            this.a(Integer.MAX_VALUE, nArray2, jm2.p, null);
            return;
        }
        this.s = false;
        this.a(nArray, 0, n2);
    }

    private void a(int[] nArray, int n2, int n3) {
        cw.a("[sqDataCache] receiveMapInfo");
        ox.a(n2, n3);
        String string = this.i.a;
        j.b("map" + string);
        this.p = this.i.c;
        this.k = new byte[nArray.length][];
        this.i();
        this.c = ox.b(this.i.a);
        this.c.a(-1988, this.i.a());
        this.c.a(-1989, p.a(System.currentTimeMillis()));
        int n4 = this.c.a();
        ox.d(n4);
        this.a(1, nArray);
    }

    public final void a(String object, jl[] jlArray) {
        cw.a("[sqDataCache] receiveMapUpToDate");
        this.i = new jm();
        int n2 = 0;
        if (!((String)object).toUpperCase().equals("M99")) {
            object = ox.b((String)object);
            ((y)object).a(-1989, p.a(System.currentTimeMillis()));
            n2 = ((y)object).a();
            ox.d(n2);
            byte[] byArray = ((y)object).a(-1988);
            jm jm2 = this.i;
            if (byArray != null) {
                jm2.h = p.a(byArray, 0);
                jm2.e = p.a(byArray, 4);
                jm2.i = p.a(byArray, 8);
                jm2.c = p.a(byArray, 12);
                jm2.d = p.a(byArray, 16);
                jm2.g = p.a(byArray, 20);
                jm2.m = p.a(byArray, 24);
                jm2.f = p.a(byArray, 28);
                int n3 = p.a(byArray, 32);
                byte[] byArray2 = new byte[n3];
                System.arraycopy(byArray, 36, byArray2, 0, n3);
                jm2.a = l.a(byArray2);
                int n4 = n3 + 36;
                n3 = p.a(byArray, n4);
                byArray2 = new byte[n3];
                System.arraycopy(byArray, n4 += 4, byArray2, 0, n3);
                jm2.b = l.a(byArray2);
                n4 += n3;
                n3 = p.a(byArray, n4);
                jm2.j = new byte[n3];
                System.arraycopy(byArray, n4 += 4, jm2.j, 0, n3);
                n4 += n3;
                n3 = p.a(byArray, n4);
                jm2.k = new byte[n3];
                System.arraycopy(byArray, n4 += 4, jm2.k, 0, n3);
                n4 += n3;
                n3 = p.a(byArray, n4);
                jm2.l = new byte[n3];
                System.arraycopy(byArray, n4 += 4, jm2.l, 0, n3);
                n4 += n3;
                n3 = p.a(byArray, n4);
                byArray2 = new byte[n3];
                System.arraycopy(byArray, n4 += 4, byArray2, 0, n3);
                jm2.o = new int[n3 / 4];
                int n5 = 0;
                while (n5 < jm2.o.length) {
                    jm2.o[n5] = p.a(byArray2, n5 << 2);
                    ++n5;
                }
                n4 += n3;
                n3 = p.a(byArray, n4);
                byArray2 = new byte[n3];
                System.arraycopy(byArray, n4 += 4, byArray2, 0, n3);
                n5 = 0;
                int n6 = 0;
                Object object2 = null;
                a a2 = new a();
                while (n5 < byArray2.length - 1) {
                    n6 = p.a(byArray2, n5);
                    object2 = new byte[n6];
                    System.arraycopy(byArray2, n5 += 4, object2, 0, n6);
                    a2.a(object2);
                    n5 += n6;
                }
                jm2.n = new jl[a2.d()];
                n6 = 0;
                while (n6 < jm2.n.length) {
                    jm2.n[n6] = new jl();
                    byArray2 = (byte[])a2.b(n6);
                    object2 = jm2.n[n6];
                    jm2.n[n6].a = p.a(byArray2, 0);
                    object2.c = p.a(byArray2, 4);
                    object2.d = p.a(byArray2, 8);
                    object2.e = p.a(byArray2, 12);
                    object2.f = p.a(byArray2, 16);
                    object2.g = p.a(byArray2, 20);
                    n5 = p.a(byArray2, 24);
                    byte[] byArray3 = new byte[n5];
                    System.arraycopy(byArray2, 28, byArray3, 0, n5);
                    object2.b = l.a(byArray3);
                    ++n6;
                }
            }
            this.i.n = jlArray;
            ox.a(this.i.n);
            this.j = this.i.o;
            this.k = new byte[this.j.length][];
            int n7 = this.k.length;
            ox.a(0, n7);
            int n8 = 0;
            while (n8 < this.j.length) {
                ap ap2 = com.mg.sq.a.q().d(11111);
                if (ap2 != null) {
                    ap2.a(ap.a);
                }
                this.k[n8] = ((y)object).a(this.j[n8]);
                ++nv.b;
                ++n8;
            }
        } else {
            this.i.n = jlArray;
        }
        if (this.g != null) {
            this.g.a(this.i, this.k);
        }
        this.l();
    }

    private void i() {
        String[] stringArray = RecordStore.listRecordStores();
        if (stringArray == null) {
            return;
        }
        a a2 = new a();
        int n2 = 0;
        while (n2 < stringArray.length) {
            int n3;
            if (stringArray[n2] != null && (n3 = stringArray[n2].indexOf("map")) >= 0) {
                a2.a(stringArray[n2]);
            }
            ++n2;
        }
        if (a2.d() >= a) {
            long l2 = Long.MAX_VALUE;
            int n4 = -1;
            int n5 = 0;
            while (n5 < a2.d()) {
                Object object = (String)a2.b(n5);
                if ((object = ox.b(((String)object).substring(3))) != null && ((y)object).c(-1989)) {
                    byte[] byArray = ((y)object).a(-1989);
                    object = byArray;
                    long l3 = p.d(byArray);
                    if (l3 < l2) {
                        l2 = l3;
                        n4 = n5;
                    }
                }
                ++n5;
            }
            if (n4 >= 0) {
                j.b((String)a2.b(n4));
            }
        }
    }

    private static void a(jl[] jlArray) {
        if (jlArray == null) {
            return;
        }
        int n2 = 0;
        while (n2 < jlArray.length) {
            jl jl2 = jlArray[n2];
            jlArray[n2].d = jl2.d - (jl2.f >> 1) - (jl2.f & 1);
            jl2.e = jl2.e - (jl2.g >> 1) - (jl2.g & 1);
            ++n2;
        }
    }

    private void a(int n2, int[] nArray) {
        this.j = nArray;
        this.m = n2;
        this.l = 0;
        ox ox2 = this;
        this.q = false;
        ox2.k();
    }

    public final void t() {
        if (this.m == 1) {
            if (this.g != null) {
                this.g.d();
            }
        } else if (this.h != null) {
            this.h.b();
        }
        this.l();
    }

    private void j() {
        Object object = this.f;
        synchronized (object) {
            try {
                this.f.wait();
            }
            catch (InterruptedException interruptedException) {
                InterruptedException interruptedException2 = interruptedException;
                interruptedException.printStackTrace();
            }
            return;
        }
    }

    private void k() {
        Object object = this.f;
        synchronized (object) {
            this.f.notify();
            return;
        }
    }

    private void l() {
        this.y = 0;
        this.c = null;
        this.i = null;
        this.g = null;
        this.h = null;
        this.k = null;
        this.j = null;
        this.o = false;
        this.s = false;
        System.gc();
    }

    public final void run() {
        while (!this.w) {
            if (this.j != null) {
                int n2;
                while (this.l < this.j.length) {
                    n2 = this.j[this.l];
                    nv.a(String.valueOf(n2));
                    kq.a().a(n2);
                    this.j();
                    if (!this.q) {
                        ap ap2 = com.mg.sq.a.q().d(11111);
                        if (ap2 != null) {
                            ap2.a(ap.a);
                        }
                        if (this.m == 1) {
                            this.k[this.l] = this.n.a;
                        } else {
                            int n3 = this.j[this.l];
                            this.c = this.a(this.c, n3, this.n.a);
                            n3 = 0;
                            ++this.y;
                            if (this.y >= 200) {
                                n3 = 1;
                                this.y = 0;
                            }
                            if (n3 != 0) {
                                boolean bl2 = this.f();
                                n3 = bl2 ? 1 : 0;
                                if (bl2) {
                                    n3 = this.l + 1;
                                    if (n3 > this.j.length) {
                                        n3 = this.j.length;
                                    }
                                    pa.b(this.t + n3);
                                }
                            }
                        }
                        this.n.a = null;
                        ++this.l;
                        continue;
                    }
                    this.l();
                    break;
                }
                if (this.q || this.l < this.j.length) continue;
                if (this.m == 1) {
                    n2 = 0;
                    while (n2 < this.j.length) {
                        this.c.a(this.j[n2], this.k[n2]);
                        ++n2;
                    }
                    n2 = this.c.a();
                    ox.d(n2);
                    this.c.a(-1987, p.a(this.i.c));
                    n2 = this.c.a();
                    ox.d(n2);
                    if (this.g != null) {
                        cw.a("[sqDataCacher ]+run");
                        this.g.a(this.i, this.k);
                    }
                    cw.a("[sqDataCacher ] complete get map resource");
                } else {
                    this.f();
                    pa.j();
                    if (!this.s) {
                        pa.e(this.p);
                        if (this.h != null) {
                            this.h.a();
                        }
                        cw.a("[sqDataCacher ] complete download file install");
                    } else {
                        this.s = false;
                        this.a(this.i.o, nv.b, this.i.p);
                        this.q = false;
                        cw.a("[sqDataCacher ] download tiep map sau khi da down du monster");
                        continue;
                    }
                }
                this.l();
                continue;
            }
            this.q = false;
            this.j();
        }
    }

    public final void d() {
        this.w = true;
        this.k();
        b = null;
    }

    public final void e() {
        int n2;
        this.q = true;
        this.w = true;
        if (this.j != null && (n2 = this.f()) != 0) {
            n2 = this.l;
            if (n2 > this.j.length) {
                n2 = this.j.length;
            }
            pa.b(this.t + n2);
        }
    }

    static void a(ox ox2) {
        ox2.k();
    }
}

