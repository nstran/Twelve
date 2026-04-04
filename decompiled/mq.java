/*
 * Decompiled with CFR 0.152.
 */
public final class mq
implements mp {
    private le[][] q;
    public boolean a = false;
    public boolean b = false;
    public boolean c = false;
    public boolean d = false;
    public boolean e = false;
    public boolean f = true;
    public int g = 0;
    private boolean r;
    private Object s = new Object();
    private boolean t = false;
    public int h;
    public mu[] i;
    public int j;
    public mw k;
    public nh[][] l;
    private byte[] u;
    private byte[] x;
    public byte[] m;
    public int n = 0;
    private boolean y;
    private byte[] z;
    public byte[] o;
    public int[] p;

    public final void a(boolean bl2) {
        this.b = bl2;
        boolean bl3 = bl2;
        mq mq2 = this;
        this.c = bl3;
    }

    public mq(lf[] lfArray, lf[] lfArray2) {
        System.gc();
        mq.a(this);
        this.q = new le[2][];
        this.q[0] = new le[lfArray.length];
        this.q[1] = new le[lfArray2.length];
        int n2 = 0;
        while (n2 < lfArray.length) {
            this.q[0][n2] = mq.a(lfArray[n2]);
            this.q[0][n2].f(3);
            ++n2;
        }
        n2 = 0;
        while (n2 < lfArray2.length) {
            this.q[1][n2] = mq.a(lfArray2[n2]);
            this.q[1][n2].f(3);
            ++n2;
        }
        System.gc();
    }

    private static le a(lf lf2) {
        return new le(lf2);
    }

    public final le[][] a() {
        return this.q;
    }

    public final le a(int n2, int n3) {
        return this.q[n2][n3];
    }

    public final boolean b() {
        return this.r;
    }

    public final void b(boolean bl2) {
        this.r = bl2;
    }

    public final boolean c() {
        return this.r;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        mq.a(this.l, n2, n3, n4, n5);
    }

    public static void a(nh[][] nhArray, int n2, int n3, int n4, int n5) {
        nh nh2 = nhArray[n2][n3];
        nhArray[n2][n3] = nhArray[n4][n5];
        nhArray[n4][n5] = nh2;
    }

    public final void a(byte[] object) {
        Object object2 = this.s;
        synchronized (object2) {
            this.z = object;
            this.e();
            object = this;
            return;
        }
    }

    public final void a(byte[] object, byte[] object2, byte[] byArray, lf[] lfArray, lf[] lfArray2) {
        this.y = false;
        this.x = null;
        this.u = null;
        this.m = null;
        this.z = null;
        byte[] byArray2 = object;
        object = this;
        this.o = byArray2;
        this.f();
        this.n = 0;
        this.a((byte[])object2);
        this.a(byArray);
        int n2 = 0;
        while (n2 < this.q[0].length) {
            object2 = this.q[0][n2].a();
            this.q[0][n2].a().r = lfArray[n2].r;
            object2.s = lfArray[n2].s;
            object2.t = lfArray[n2].t;
            object2.u = lfArray[n2].u;
            object2.v = lfArray[n2].v;
            object2.w = lfArray[n2].w;
            ++n2;
        }
        n2 = 0;
        while (n2 < this.q[1].length) {
            object2 = this.q[1][0].a();
            this.q[1][0].a().r = lfArray2[n2].r;
            object2.s = lfArray2[n2].s;
            object2.t = lfArray2[n2].t;
            object2.u = lfArray2[n2].u;
            object2.v = lfArray2[n2].v;
            object2.w = lfArray2[n2].w;
            ++n2;
        }
    }

    public final void a(int n2, int n3, int n4) {
        this.l[n2][n3] = nh.a(n4);
    }

    public final nh b(int n2, int n3) {
        return this.l[n2][n3];
    }

    public final void d() {
        Object object = this.s;
        synchronized (object) {
            this.e();
            if (this.y) {
                if (this.x == null) {
                    this.f();
                }
                this.m = this.x;
                this.x = null;
            } else {
                if (this.u == null) {
                    this.f();
                }
                this.m = this.u;
                this.u = null;
            }
            this.y = !this.y;
            this.n = 0;
            return;
        }
    }

    public final boolean e() {
        if (this.z == null) {
            return false;
        }
        if (this.y) {
            if (this.x == null) {
                this.x = this.z;
                this.z = null;
            } else if (this.u == null) {
                this.u = this.z;
                this.z = null;
            }
        } else if (this.u == null) {
            this.u = this.z;
            this.z = null;
        } else if (this.x == null) {
            this.x = this.z;
            this.z = null;
        }
        return true;
    }

    public final boolean f() {
        if (this.o == null) {
            return false;
        }
        int n2 = 0;
        int n3 = 2;
        while (n3 < 10) {
            int n4 = 2;
            while (n4 < 10) {
                this.l[n3][n4] = nh.a(this.o[n2++]);
                ++n4;
            }
            ++n3;
        }
        this.o = null;
        return true;
    }

    private static void a(mq mq2) {
        mq2.l = new nh[12][12];
        int n2 = 11;
        while (n2 >= 0) {
            mq2.l[0][n2] = nh.x;
            --n2;
        }
        n2 = 11;
        while (n2 >= 0) {
            mq2.l[11][n2] = nh.x;
            --n2;
        }
        n2 = 11;
        while (n2 >= 0) {
            mq2.l[n2][0] = nh.x;
            --n2;
        }
        n2 = 11;
        while (n2 >= 0) {
            mq2.l[n2][11] = nh.x;
            --n2;
        }
        n2 = 10;
        while (n2 > 0) {
            mq2.l[1][n2] = nh.x;
            --n2;
        }
        n2 = 10;
        while (n2 > 0) {
            mq2.l[10][n2] = nh.x;
            --n2;
        }
        n2 = 10;
        while (n2 > 0) {
            mq2.l[n2][1] = nh.x;
            --n2;
        }
        n2 = 10;
        while (n2 > 0) {
            mq2.l[n2][10] = nh.x;
            --n2;
        }
        n2 = 9;
        while (n2 >= 2) {
            int n3 = 9;
            while (n3 >= 2) {
                mq2.l[n2][n3] = nh.u;
                --n3;
            }
            --n2;
        }
    }

    public final void g() {
        this.q = null;
        this.m = null;
        this.u = null;
        this.x = null;
        this.l = null;
        this.p = null;
        this.o = null;
        this.z = null;
        this.i = null;
    }
}

