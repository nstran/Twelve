/*
 * Decompiled with CFR 0.152.
 */
public abstract class mq
implements bf,
bg,
kp,
mr {
    protected ms a;
    protected mt b;
    protected mk[] c;
    protected int d;
    protected a e;
    private int z;
    private int[] A = new int[21];
    private int B;
    private int C;
    protected boolean f = false;
    protected int g = -1;
    private boolean D = false;
    private boolean E = false;
    protected int h = 0;
    protected int i = 5;
    protected int j = 6;
    private int F = 5;
    private int G = 6;
    private int H;
    private int I;
    private byte[] J;
    private byte[] K;
    private byte[] L;
    private byte[] M;
    private boolean N = false;
    private static int[] O;
    private static int[] P;
    private oq Q;
    private long R;
    protected boolean k;
    protected kr l = new kr(16);
    private boolean S = false;
    protected int m = 0;
    protected int n;
    protected int o = -1;
    private long T = -1L;
    protected long p;
    protected boolean q;
    protected int r;
    private boolean U;
    private boolean V = false;
    protected boolean s;
    private String[][] W = new String[][]{{"Ng\u01b0\u01a1i ch\u00e1n s\u1ed1ng r\u1ed3i!!", "H\u1eeb, ng\u01b0\u01a1i to gan l\u1eafm!", "Ng\u01b0\u01a1i ch\u01b0a \u0111\u1ee7 s\u1ee9c!", "Ta s\u1ebd h\u1ea1 ng\u01b0\u01a1i trong v\u00f2ng 3 b\u01b0\u1edbc", "Mu\u1ed1n ch\u1ebft? Th\u00edch th\u00ec chi\u1ec1u!"}, {"Xem \u0111\u00e2y!!!", "Ti\u1ebfp chi\u00eau!!!", "Ch\u1ebft n\u00e8!!!"}};
    boolean t = false;
    public boolean u;
    private a X = new a();
    protected nq v;
    nq w = null;

    static {
        int[] nArray = new int[8];
        nArray[0] = -1;
        nArray[1] = -1;
        nArray[3] = 1;
        nArray[4] = 1;
        nArray[5] = 1;
        nArray[7] = -1;
        O = nArray;
        int[] nArray2 = new int[8];
        nArray2[1] = 1;
        nArray2[2] = 1;
        nArray2[3] = 1;
        nArray2[5] = -1;
        nArray2[6] = -1;
        nArray2[7] = -1;
        P = nArray2;
    }

    public mq(ms ms2, mt mt2, oq oq2) {
        this.Q = oq2;
        this.a = ms2;
        this.b = mt2;
        this.a.p = new int[64];
        this.a.i = new mw[64];
        int n2 = 0;
        while (n2 < this.a.i.length) {
            this.a.i[n2] = new mw();
            ++n2;
        }
        this.c = new mk[2];
        this.c[0] = new mn(this, 0);
        this.c[1] = new mn(this, 1);
        ks.a().a(this);
        this.d(0);
        if (this.a.a(1, 0).b()) {
            this.k = true;
            mt2.a(this.W[0][cv.a(this.W[0].length)], 50);
        }
        this.f(oq.p);
        this.m = 0;
        this.R = System.currentTimeMillis();
    }

    public boolean f() {
        return false;
    }

    private void a(int n2, int n3, int n4, int n5, boolean bl2, int n6) {
        mw mw2 = this.a.i[this.a.j];
        this.a.i[this.a.j].a = this.a.b(n2, n3);
        mw2.h = n3;
        mw2.i = n2;
        mw2.b = n2;
        mw2.c = n3 - (n4 >> 16 & 0xFF);
        mw2.d = n4 & 0xFF;
        mw2.e = n2 - (n5 >> 16 & 0xFF);
        mw2.f = n3;
        mw2.g = n5 & 0xFF;
        mw2.k = bl2;
        mw2.j = n6;
        ++this.a.j;
        if (this.k && (mw2.a.e & 1) != 0 && cv.a(3) == 1 && this.h == 1) {
            this.b.a(this.W[1][cv.a(this.W[1].length)], 50);
        }
    }

    private void a(String string, nl[] nlArray) {
        int n2;
        ct.a("[SQControllerManager]convertAttributeBufferToAttributeCharacter owner" + string);
        if (nlArray == null) {
            return;
        }
        lg[][] lgArray = this.a.a();
        boolean[][] blArrayArray = new boolean[lgArray.length][];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n5 < lgArray.length) {
            blArrayArray[n5] = new boolean[lgArray[n5].length];
            n2 = 0;
            while (n2 < lgArray[n5].length) {
                if (lgArray[n5][n2].j().equals(string)) {
                    n3 = n5;
                    n4 = n2;
                }
                blArrayArray[n5][n2] = lgArray[n5][n2].p();
                ++n2;
            }
            ++n5;
        }
        n2 = 0;
        while (n2 < nlArray.length) {
            int n6 = 0;
            while (n6 < lgArray.length) {
                int n7 = 0;
                while (n7 < lgArray[n6].length) {
                    if (nlArray[n2].a.equals(lgArray[n6][n7].j())) {
                        lg lg2 = lgArray[n6][n7];
                        this.b.b.a(nlArray[n2].b, nlArray[n2].c, nlArray[n2].d, nlArray[n2].a, nlArray[n2].f);
                        if (nlArray[n2].b >= 0) {
                            int n8 = nlArray[n2].b - lg2.m();
                            ct.a(" convertAttributeBufferToAttributeCharacter   " + n8);
                            if (n8 < 0 && nlArray[Math.abs((int)(n2 - 1))].e > 0) {
                                this.b.a(lg2.j(), n6, -nlArray[Math.abs((int)(n2 - 1))].e, blArrayArray[n3][n4]);
                            }
                            lg2.g(nlArray[n2].b);
                        }
                        if (nlArray[n2].c >= 0) {
                            lg2.h(nlArray[n2].c);
                        }
                        if (nlArray[n2].d >= 0) {
                            lg2.j(nlArray[n2].d);
                        }
                    }
                    ++n7;
                }
                ++n6;
            }
            ++n2;
        }
    }

    /*
     * Unable to fully structure code
     */
    public void b() {
        if (this.z > 0) {
            --this.z;
            return;
        }
        switch (this.d) {
            case 16: {
                this.c();
                return;
            }
            case 20: {
                this.d(10);
                return;
            }
            case 19: {
                this.d(15);
                return;
            }
            case 9: {
                this.z = this.b.a(this.g);
                switch (this.g) {
                    case 1: {
                        this.b.b.l(1);
                        break;
                    }
                    case 0: {
                        this.b.b.l(0);
                    }
                }
                this.d(16);
                return;
            }
            case 15: {
                var2_1 = this.v;
                var1_3 = this;
                if (var2_1 == null) ** GOTO lbl58
                var1_3.a(var2_1.a, var2_1.f);
                var4_5 = var2_1.D;
                var3_9 = var1_3;
                if (var4_5 != 0) {
                    var5_11 = var3_9.a.g + var4_5;
                    if (var5_11 <= 0) {
                        var5_11 = 1;
                    }
                    var6_13 = var3_9.b;
                    var6_13.b.j();
                    var3_9.f(var5_11);
                }
                var4_5 = var2_1.F;
                var3_9 = var1_3;
                if (oq.o != 1) {
                    var3_9.B += var4_5;
                }
                var1_3.v = null;
                if (var1_3.N) ** GOTO lbl57
                var3_9 = var1_3;
                var4_6 = var3_9.a;
                var4_7 = oz.a(var4_6.l);
                if (ct.a()) {
                    ct.a("SQControllerManager.checkSumToSyncDataWithServer()  server=" + var3_9.T + ", client=" + var4_7);
                }
                if (var3_9.T > -1L && var3_9.T != var4_7) {
                    var3_9.T = -1L;
                    var3_9.l.c();
                    ks.a().t();
                    v0 = true;
                } else {
                    v0 = false;
                }
                if (v0) ** GOTO lbl58
lbl57:
                // 2 sources

                var1_3.e(var2_1);
lbl58:
                // 3 sources

                if (this.D) {
                    this.D = false;
                    this.a.e();
                    if (this.a.f()) {
                        if (this.N) {
                            this.d(20);
                        } else {
                            this.d(10);
                        }
                    }
                } else if (this.S && this.a.f()) {
                    if (this.N) {
                        this.d(20);
                    } else {
                        this.d(10);
                    }
                }
                this.t();
                return;
            }
            case 0: {
                this.e();
                return;
            }
            case 11: {
                if (this.a.h > 0) {
                    this.d(5);
                    return;
                }
                this.d(3);
                return;
            }
            case 10: {
                this.d(0);
                return;
            }
            case 7: {
                this.d(8);
                if (oq.o == 1 && this.V) break;
                this.V = true;
                if (this.h != 0 && this.k) break;
                this.b.b(this.h, this.i, this.j);
                return;
            }
            case 1: {
                this.t();
                var1_4 = this.b;
                if (var1_4.f.m() || this.d != 1) break;
                var1_4 = this;
                var2_2 = var1_4;
                ct.a("getAreaScoreFromSwapNode");
                var2_2.a.j = 0;
                var7_15 = var2_2.a.k.a;
                var8_16 = var2_2.a.k.b;
                var3_10 = var2_2.a.k.e[0];
                var5_12 = (var3_10 & 255) >= 3;
                var4_8 = var2_2.a.k.e[1];
                v1 = var6_14 = (var4_8 & 255) >= 3;
                if (var5_12 || var6_14) {
                    var2_2.a(var7_15, var8_16, var3_10, var4_8, true, 0);
                }
                var7_15 = var2_2.a.k.c;
                var8_16 = var2_2.a.k.d;
                var3_10 = var2_2.a.k.e[2];
                var5_12 = (var3_10 & 255) >= 3;
                var4_8 = var2_2.a.k.e[3];
                v2 = var6_14 = (var4_8 & 255) >= 3;
                if (var5_12 || var6_14) {
                    var2_2.a(var7_15, var8_16, var3_10, var4_8, true, 0);
                }
                if (var1_4.a.j > 0) {
                    var1_4.d(3);
                    break;
                }
                var1_4.U = false;
                var1_4.d(2);
                return;
            }
            case 2: {
                this.d(15);
                return;
            }
            case 8: {
                this.d();
                this.t();
                return;
            }
            case 3: {
                this.d(4);
                return;
            }
            case 4: {
                this.d(5);
                return;
            }
            case 5: {
                this.d(3);
            }
        }
    }

    protected abstract void c();

    protected abstract void d();

    protected abstract void e();

    protected final void a(nq object, boolean bl2) {
        switch (((nq)object).C) {
            case 2: {
                this.a.a(false);
                boolean bl3 = false;
                ms ms2 = this.a;
                this.a.e = bl3;
                bl3 = true;
                ms2 = this.a;
                this.a.a = bl3;
                bl3 = false;
                ms2 = this.a;
                this.a.f = bl3;
                break;
            }
            case 1: {
                this.a.a(true);
                boolean bl4 = false;
                ms ms3 = this.a;
                this.a.e = bl4;
                bl4 = false;
                ms3 = this.a;
                this.a.a = bl4;
                bl4 = false;
                ms3 = this.a;
                this.a.f = bl4;
                break;
            }
            case 3: {
                this.a.a(false);
                boolean bl5 = true;
                ms ms4 = this.a;
                this.a.e = bl5;
                bl5 = false;
                ms4 = this.a;
                this.a.a = bl5;
                bl5 = false;
                ms4 = this.a;
                this.a.f = bl5;
                break;
            }
            case 4: {
                this.a.a(false);
                boolean bl6 = false;
                ms ms5 = this.a;
                this.a.e = bl6;
                bl6 = false;
                ms5 = this.a;
                this.a.a = bl6;
                bl6 = true;
                ms5 = this.a;
                this.a.f = bl6;
                break;
            }
            default: {
                this.a.a(false);
                boolean bl7 = false;
                ms ms6 = this.a;
                this.a.e = bl7;
                bl7 = false;
                ms6 = this.a;
                this.a.a = bl7;
                bl7 = false;
                ms6 = this.a;
                this.a.f = bl7;
            }
        }
        if (bl2) {
            object = this.a.a(this.a(((nq)object).a), 0);
            this.a.d = ((lg)object).p();
        }
    }

    protected final void d(int n2) {
        block17: while (true) {
            mr mr2;
            if (ct.a()) {
                ct.a("SQControllerManager.changeState(" + n2 + "): keepTimerNextTurn=" + this.q);
            }
            this.b.f();
            if (oq.o != 1) {
                this.b.g();
            }
            if (n2 != 1 && n2 != 2 && n2 != 7 && n2 != 8) {
                if (!this.q) {
                    mr2 = this.b;
                    ((mt)mr2).e.b();
                }
                this.q = false;
            }
            switch (n2) {
                case 20: {
                    this.z = 20;
                    this.N = false;
                    this.b.j();
                    break block17;
                }
                case 19: {
                    this.z = 15;
                    break block17;
                }
                case 15: {
                    this.b.a(true);
                    this.b.f();
                    if (oq.o != 1) {
                        this.b.g();
                    }
                    ag.b().e(241205);
                    break block17;
                }
                case 16: {
                    this.z = 15;
                    break block17;
                }
                case 9: {
                    this.z = 30;
                    this.b.a(true);
                    break block17;
                }
                case 0: {
                    this.b.a(true);
                    this.b.i();
                    this.z = 26;
                    break block17;
                }
                case 10: {
                    this.b.a(true);
                    this.z = this.b.h();
                    break block17;
                }
                case 7: {
                    this.s = true;
                    this.a.b(false);
                    int n3 = this.h;
                    this.h = this.E ? 0 : 1;
                    if (this.B <= 0) {
                        this.B = 0;
                    } else {
                        if (this.B > 0 || this.C > 0) {
                            this.b.a(this.h, this.B, 6, 6);
                        }
                        this.C = this.B--;
                    }
                    int n4 = 0;
                    while (n4 < 2) {
                        lg lg2 = this.a.a(n4, 0);
                        boolean bl2 = lg2.e();
                        boolean bl3 = lg2.g();
                        boolean bl4 = lg2.h();
                        boolean bl5 = lg2.i();
                        boolean bl6 = lg2.f();
                        lg2.s();
                        if (bl2 && !lg2.e()) {
                            this.b.a(n4, 1, true);
                        }
                        if (bl3 && !lg2.g()) {
                            this.b.a(n4, 0, true);
                        }
                        if (bl4 && !lg2.h()) {
                            this.b.a(n4, 2, true);
                        }
                        if (bl5 && !lg2.i()) {
                            this.b.a(n4, 1, false);
                        }
                        if (bl6 && !lg2.f()) {
                            this.b.a(n4, 1, false);
                        }
                        this.b.b(n4);
                        ++n4;
                    }
                    n4 = 0;
                    while (n4 < this.A.length) {
                        this.A[n4] = 0;
                        ++n4;
                    }
                    int n5 = this.h;
                    mt mt2 = this.b;
                    mt2.b.d(n5);
                    this.b.a(false);
                    if (this.a.a(this.h, 0).b()) {
                        this.z = 15;
                        break block17;
                    }
                    if (n3 == this.h) break block17;
                    this.z = 5;
                    break block17;
                }
                case 8: {
                    this.a.d = false;
                    if (this.h != 0) break block17;
                    mt mt3 = this.b;
                    mt3.e.a();
                    if (this.Q == null) break block17;
                    this.Q.r();
                    break block17;
                }
                case 1: {
                    this.b.a(this.a.k.a, this.a.k.b, this.a.k.c, this.a.k.d, this.a.b(this.a.k.a, this.a.k.b), this.a.b(this.a.k.c, this.a.k.d));
                    this.z = 10;
                    this.a.a(this.a.k.a, this.a.k.b, this.a.k.c, this.a.k.d);
                    this.a.b(false);
                    break block17;
                }
                case 2: {
                    mq mq2 = this;
                    mq2.b.a(mq2.a.k.a, mq2.a.k.b, mq2.a.k.c, mq2.a.k.d, mq2.a.b(mq2.a.k.a, mq2.a.k.b), mq2.a.b(mq2.a.k.c, mq2.a.k.d));
                    mq2.a.a(mq2.a.k.a, mq2.a.k.b, mq2.a.k.c, mq2.a.k.d);
                    mq2.z = 10;
                    break block17;
                }
                case 3: {
                    if (this.a.j <= 0) {
                        if (this.a.o != null) {
                            this.S = true;
                        }
                        n2 = 15;
                        continue block17;
                    }
                    this.z = 0;
                    mr2 = this.a;
                    nj[][] njArray = mr2.l;
                    this.a(njArray);
                    break block17;
                }
                case 4: {
                    this.r();
                    break block17;
                }
                case 5: {
                    this.s();
                    break block17;
                }
                case 11: {
                    this.a(this.r, this.H, this.J, this.K, this.L, this.M);
                }
            }
            break;
        }
        this.d = n2;
    }

    private void a(nj[][] object) {
        Object object2;
        if (oq.n == 1) {
            Object object3 = new mw[this.a.j];
            System.arraycopy(this.a.i, 0, object3, 0, this.a.j);
            mw[] mwArray = object3;
            object3 = this;
            ((mq)object3).X.a();
            if (mwArray.length != 0) {
                ct.a("=============================================checkMixing======================================" + mwArray.length);
                object2 = new a(5);
                a a2 = new a(5);
                int n2 = 0;
                while (n2 < mwArray.length) {
                    if (mwArray[n2].d == 1 && mwArray[n2].g == 1) {
                        ((mq)object3).X.a(mwArray[n2]);
                    } else if (mwArray[n2].d >= 3) {
                        ((a)object2).a(mwArray[n2]);
                    } else {
                        a2.a(mwArray[n2]);
                    }
                    ++n2;
                }
                mq.a((a)object2, true);
                mq.a(a2, false);
                if (((a)object2).d() == 0 || a2.d() == 0) {
                    int n3 = 0;
                    int n4 = ((a)object2).d();
                    while (n3 < n4) {
                        mw mw2 = (mw)((a)object2).b(n3);
                        super.a(mw2);
                        ((mq)object3).X.a(((a)object2).b(n3));
                        ++n3;
                    }
                    n3 = 0;
                    n4 = a2.d();
                    while (n3 < n4) {
                        mw mw3 = (mw)a2.b(n3);
                        super.a(mw3);
                        ((mq)object3).X.a(a2.b(n3));
                        ++n3;
                    }
                } else {
                    super.a((a)object2, a2);
                }
            }
        }
        int n5 = this.a.j;
        this.a((nj[][])object, n5);
        this.b((nj[][])object, n5);
        int[] nArray = new int[100];
        int n6 = 0;
        while (n6 < this.a.j) {
            object2 = this.a.i[n6];
            int n7 = this.b.a((mw)object2, this.h);
            if (n7 > this.z) {
                this.z = n7;
            }
            if (((mw)object2).a.d < 70) {
                int n8 = this.A[((mw)object2).a.d] + 1;
                if (((mw)object2).d != 1 || ((mw)object2).g != 1) {
                    byte by2 = ((mw)object2).a.d;
                    nArray[by2] = nArray[by2] + 1;
                    if (n8 > 1) {
                        if (((mw)object2).d < 3) {
                            this.b.c(n8, ((mw)object2).e, ((mw)object2).f, ((mw)object2).g, ((mw)object2).d);
                        } else if (((mw)object2).g < 3) {
                            this.b.c(n8, ((mw)object2).b, ((mw)object2).c, ((mw)object2).g, ((mw)object2).d);
                        } else {
                            this.b.c(n8, ((mw)object2).e, ((mw)object2).f, ((mw)object2).g, ((mw)object2).d);
                            this.b.c(n8, ((mw)object2).b, ((mw)object2).c, ((mw)object2).g, ((mw)object2).d);
                        }
                    }
                }
            }
            ++n6;
        }
        if (oq.n == 1) {
            n6 = 0;
            int n9 = this.X.d();
            while (n6 < n9) {
                object = (mw)this.X.b(n6);
                if (object.a.e < 64) {
                    if (object.g >= 3 && object.d >= 3 || object.g >= 5 || object.d >= 5) {
                        byte by3 = mr.y[object.a.d];
                        this.a.a(object.i, object.h, by3);
                        this.b.a(object.i, object.h, nj.a(by3), 0);
                    } else if (object.g >= 4 || object.d >= 4) {
                        byte by4 = mr.x[object.a.d];
                        this.a.a(object.i, object.h, by4);
                        this.b.a(object.i, object.h, nj.a(by4), 0);
                    }
                }
                ++n6;
            }
        }
        n6 = 0;
        while (n6 < 100) {
            if (nArray[n6] > 0) {
                int n10 = n6;
                this.A[n10] = this.A[n10] + 1;
            }
            ++n6;
        }
    }

    private void a(nj[][] njArray, int n2) {
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            mw mw2 = this.a.i[n3];
            if (mw2.d == 1 && mw2.g == 1) {
                n4 = mw2.c + mw2.d - 1;
                while (n4 >= mw2.c) {
                    if (njArray[mw2.b][n4].f > 1) {
                        this.a(mw2.b, n4, -16777215, 1, mw2.k, mw2.j);
                    }
                    njArray[mw2.b][n4] = nj.a;
                    --n4;
                }
            } else if (mw2.d < 3) {
                n4 = mw2.e + mw2.g - 1;
                while (n4 >= mw2.e) {
                    if (njArray[n4][mw2.f].f > 1) {
                        this.a(n4, mw2.f, -16777215, 1, mw2.k, mw2.j);
                    }
                    njArray[n4][mw2.f] = nj.a;
                    --n4;
                }
            } else if (mw2.g < 3) {
                n4 = mw2.c + mw2.d - 1;
                while (n4 >= mw2.c) {
                    if (njArray[mw2.b][n4].f > 1) {
                        this.a(mw2.b, n4, -16777215, 1, mw2.k, mw2.j);
                    }
                    njArray[mw2.b][n4] = nj.a;
                    --n4;
                }
            } else {
                n4 = mw2.c + mw2.d - 1;
                while (n4 >= mw2.c) {
                    if (njArray[mw2.b][n4].f > 1) {
                        this.a(mw2.b, n4, -16777215, 1, mw2.k, mw2.j);
                    }
                    njArray[mw2.b][n4] = nj.a;
                    --n4;
                }
                n4 = mw2.e + mw2.g - 1;
                while (n4 >= mw2.e) {
                    if (njArray[n4][mw2.f].f > 1) {
                        this.a(n4, mw2.f, -16777215, 1, mw2.k, mw2.j);
                    }
                    njArray[n4][mw2.f] = nj.a;
                    --n4;
                }
            }
            ++n3;
        }
    }

    /*
     * Unable to fully structure code
     */
    private void b(nj[][] var1_1, int var2_2) {
        while (var2_2 < this.a.j) {
            var3_3 = this.a.i[var2_2];
            switch (var3_3.a.f) {
                case 2: {
                    var4_4 = 0;
                    if (true) ** GOTO lbl15
                    do {
                        var5_5 = var3_3.b + mq.O[var4_4];
                        var6_6 = var3_3.c + mq.P[var4_4];
                        if (var1_1[var5_5][var6_6].d < 90) {
                            this.a(var5_5, var6_6, -16777215, 1, var3_3.k, var3_3.j);
                            var1_1[var5_5][var6_6] = nj.a;
                        }
                        ++var4_4;
lbl15:
                        // 2 sources

                    } while (var4_4 < 8);
                    break;
                }
                case 4: {
                    var4_4 = 2;
                    while (var4_4 <= 10) {
                        var5_5 = var3_3.b;
                        var6_6 = var4_4;
                        if (var1_1[var5_5][var6_6].d < 90) {
                            this.a(var5_5, var6_6, -16777215, 1, var3_3.k, var3_3.j);
                            var1_1[var5_5][var6_6] = nj.a;
                        }
                        ++var4_4;
                    }
                    var4_4 = 2;
                    while (var4_4 <= 10) {
                        var5_5 = var4_4;
                        var6_6 = var3_3.c;
                        if (var1_1[var5_5][var6_6].d < 90) {
                            this.a(var5_5, var6_6, -16777215, 1, var3_3.k, var3_3.j);
                            var1_1[var5_5][var6_6] = nj.a;
                        }
                        ++var4_4;
                    }
                    break;
                }
            }
            var1_1[var3_3.b][var3_3.c] = nj.a;
            ++var2_2;
        }
    }

    private void r() {
        int n2;
        int n3;
        ct.a(" dropAllRows ");
        this.a.h = 0;
        nj[][] njArray = this.a;
        njArray = this.a.l;
        int[] nArray = new int[12];
        int[] nArray2 = new int[12];
        int n4 = 2;
        while (n4 < 10) {
            nArray[n4] = 9;
            ++n4;
        }
        n4 = 0;
        int n5 = 2;
        while (n5 < 10) {
            n3 = 0;
            n2 = 9;
            while (n2 >= 2) {
                if ((njArray[n2][n5].e & 0xFF) != 0) {
                    if (n2 != nArray[n5]) {
                        this.a.p[this.a.h++] = nArray[n5] << 8 | n5;
                        njArray[nArray[n5]][n5] = njArray[n2][n5];
                        njArray[n2][n5] = nj.a;
                        this.b.a(n2, n5, nArray[n5], n5, njArray[nArray[n5]][n5], nArray2[n5] + n4);
                        int n6 = n5;
                        nArray2[n6] = nArray2[n6] + 1;
                        n3 = 1;
                    }
                    int n7 = n5;
                    nArray[n7] = nArray[n7] - 1;
                }
                --n2;
            }
            if (n3 != 0) {
                ++n4;
            }
            ++n5;
        }
        n4 = 0;
        n5 = 2;
        while (n5 < 10) {
            n3 = 0;
            if (nArray[n5] >= 2) {
                n2 = nArray[n5];
                while (n2 >= 2) {
                    this.a.p[this.a.h++] = n2 << 8 | n5;
                    int n8 = n5;
                    int n9 = n2;
                    ms ms2 = this.a;
                    if (ms2.m == null || ms2.n >= ms2.m.length) {
                        ms2.d();
                    }
                    ms2.l[n9][n8] = nj.a(ms2.m[ms2.n++]);
                    this.b.a(n3, n5, n2, n5, njArray[n2][n5], nArray2[n5] + n4);
                    int n10 = n5;
                    nArray2[n10] = nArray2[n10] + 1;
                    --n3;
                    this.z = 10;
                    --n2;
                }
                ++n4;
            }
            ++n5;
        }
    }

    private void s() {
        this.a.j = 0;
        int n2 = 0;
        while (n2 < this.a.h) {
            boolean bl2;
            int n3 = this.a.p[n2] >> 8 & 0xFF;
            int n4 = this.a.p[n2] & 0xFF;
            int n5 = mq.a(this.a.l, n3, n4);
            boolean bl3 = (n5 & 0xFF) >= 3;
            int n6 = mq.b(this.a.l, n3, n4);
            boolean bl4 = bl2 = (n6 & 0xFF) >= 3;
            if (bl3 || bl2) {
                this.a(n3, n4, n5, n6, true, 0);
            }
            ++n2;
        }
        this.a.h = 0;
    }

    private static int a(nj[][] njArray, int n2, int n3) {
        int n4 = 1;
        int n5 = 1;
        int n6 = njArray[n2][n3].e;
        while ((n6 & njArray[n2][n3 - n5].e) != 0) {
            ++n5;
            ++n4;
        }
        int n7 = 0xFF000000 | (n5 - 1 & 0xFF) << 16;
        n5 = 1;
        while ((n6 & njArray[n2][n3 + n5].e) != 0) {
            ++n5;
            ++n4;
        }
        n7 |= (n5 - 1 & 0xFF) << 8;
        return n7 |= n4 & 0xFF;
    }

    private static int b(nj[][] njArray, int n2, int n3) {
        int n4 = 1;
        int n5 = 1;
        int n6 = njArray[n2][n3].e;
        while ((n6 & njArray[n2 - n5][n3].e) != 0) {
            ++n5;
            ++n4;
        }
        int n7 = 0 | (n5 - 1 & 0xFF) << 16;
        n5 = 1;
        while ((n6 & njArray[n2 + n5][n3].e) != 0) {
            ++n5;
            ++n4;
        }
        n7 |= (n5 - 1 & 0xFF) << 8;
        return n7 |= n4 & 0xFF;
    }

    public abstract void b(int var1);

    public abstract void c(int var1);

    public abstract void a(int var1, int var2);

    public abstract void b(int var1, int var2);

    public abstract void c(int var1, int var2);

    public final k k() {
        mh mh2 = this.b.a;
        return mh2.a;
    }

    public final boolean a(int n2, int n3, int n4, int n5, boolean bl2) {
        int n6 = n5;
        int n7 = n4;
        int n8 = n3;
        int n9 = n2;
        ms ms2 = this.a;
        Object object = this;
        this.a.k = object = ((mq)object).a(ms2, n9, n8, n7, n6);
        if (this.a.k == null) {
            this.a.k = new my();
            this.a.k.b = n3;
            this.a.k.a = n2;
            this.a.k.d = n5;
            this.a.k.c = n4;
        }
        this.F = n2;
        this.G = n3;
        this.d(1);
        return object != null;
    }

    public final my a(ms object, int n2, int n3, int n4, int n5) {
        block5: {
            if (((ms)object).l[n2][n3] != ((ms)object).l[n4][n5]) break block5;
            return null;
        }
        try {
            ms.a(((ms)object).l, n2, n3, n4, n5);
            boolean bl2 = false;
            int n6 = mq.a(((ms)object).l, n2, n3);
            int n7 = mq.b(((ms)object).l, n2, n3);
            int n8 = mq.a(((ms)object).l, n4, n5);
            int n9 = mq.b(((ms)object).l, n4, n5);
            if ((n6 & 0xFF) >= 3 || (n7 & 0xFF) >= 3 || (n8 & 0xFF) >= 3 || (n9 & 0xFF) >= 3) {
                bl2 = true;
            }
            ms.a(((ms)object).l, n2, n3, n4, n5);
            if (bl2) {
                object = new int[]{n6, n7, n8, n9};
                my my2 = new my();
                new my().a = n2;
                my2.b = n3;
                my2.c = n4;
                my2.d = n5;
                my2.e = (int[])object;
                return my2;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        return null;
    }

    public final void l() {
        if (!this.f || this.b.f.m() || this.d != 8) {
            return;
        }
        if (this.a.b()) {
            this.a.b(false);
            this.b.a(0, this.i, this.j);
            return;
        }
        nj[][] cfr_ignored_0 = this.a.l;
        this.a.b(true);
        this.b.a(this.i, this.j);
        this.b.f();
        this.h(this.i, this.j);
    }

    public final void g(int n2, int n3) {
        this.i = n2;
        this.j = n3;
        this.a.b(false);
        this.h(n2, n3);
        this.b.a(0, this.i, this.j);
    }

    protected final void h(int n2, int n3) {
        if (!this.k && oq.o != 1 && System.currentTimeMillis() - this.R >= 500L) {
            ks.a().a(n2, n3, this.m);
            this.R = System.currentTimeMillis();
        }
    }

    public final boolean m() {
        return this.a.b();
    }

    public final int n() {
        return this.i;
    }

    public final int o() {
        return this.j;
    }

    public boolean g() {
        return false;
    }

    public boolean h() {
        return false;
    }

    public boolean i() {
        return false;
    }

    public boolean j() {
        return false;
    }

    public boolean a(int n2, int n3, int n4, int n5) {
        return false;
    }

    protected final void e(int n2) {
        this.b.a();
        if (this.Q != null) {
            this.b.d.b(this.Q.q[n2]);
        }
    }

    public final void a(int n2, int n3, Object object) {
        Object object2 = (br)object;
        switch (n3) {
            case 11111: {
                object2 = this;
                Object object3 = "B\u1ea1n mu\u1ed1n tho\u00e1t kh\u1ecfi tr\u1eadn \u0111\u1ea5u";
                object3 = ag.b().a("Ch\u00fa \u00fd", (String)object3, "C\u00f3", -1, "Kh\u00f4ng", -2, 1);
                ((am)object3).b(15000);
                ((am)object3).a((bf)object2);
                ((al)object3).c(true);
                ((al)object3).b(true);
                ag.b().a((al)object3, false);
                break;
            }
            case 11112: {
                if (!this.f()) break;
                object2 = this;
                ((mq)object2).b.a((bf)object2);
                break;
            }
            case 11113: {
                if (!this.f()) break;
                object2 = this;
                ((mq)object2).b.g.a((bf)object2);
                ag.b().a(((mq)object2).b.g);
                break;
            }
            case 11115: {
                object2 = ((br)object2).a();
                if (object2 == null) break;
                int n4 = (Integer)object2;
                this.e(n4);
            }
        }
        if (this.Q != null) {
            this.Q.q();
        }
    }

    public final void d(int n2, int n3) {
        try {
            switch (n3) {
                case 1009: {
                    com.mg.sq.a.s().a(-241439, false);
                    return;
                }
                case 1008: {
                    this.d(15);
                    ks.a().c(this.b.g.t(), this.m);
                    ++this.m;
                    this.o = 4;
                    com.mg.sq.a.s().a(-241439, false);
                    return;
                }
                case 1000: {
                    if (this.Q != null) {
                        this.Q.a();
                        return;
                    }
                    break;
                }
                case 1002: {
                    if (this.Q != null) {
                        this.Q.q();
                        return;
                    }
                    break;
                }
                case 1001: {
                    if (this.Q != null) {
                        this.Q.f(95);
                        return;
                    }
                    break;
                }
                case 1003: {
                    long l2 = 0L;
                    if (ag.b().e() instanceof gt) {
                        l2 = ((gt)ag.b().e()).u();
                    }
                    if (go.s < l2) {
                        this.d(-1, 1004);
                        al al2 = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n kh\u00f4ng \u0111\u1ee7 KEN \u0111\u1ec3 tham gia tr\u1eadn \u0111\u00e1nh n\u00e0y!", "\u0110\u00f3ng", 1006, 1);
                        al2.a(this);
                        ag.b().a(al2, false);
                        return;
                    }
                    com.mg.sq.a.s().a(true, null);
                    return;
                }
                case 1004: {
                    com.mg.sq.a.s().a(false, "\u0110ang \u0111\u00e1nh qu\u00e1i, \u0111\u1ee3i t\u00ed!");
                    return;
                }
                case -1: {
                    this.d(15);
                    ag.b().a(false);
                    if (oq.o == 9) {
                        this.p();
                        ks.a().g();
                        return;
                    }
                    this.p();
                    ks.a().h();
                    return;
                }
                case -2: {
                    ag.b().a(false);
                    return;
                }
                case -6: {
                    ag.b().a(false);
                    this.g = 1;
                    return;
                }
                case 1006: {
                    ag.b().a(false);
                    return;
                }
                case 999999: {
                    Object object = this.b;
                    if (((mt)object).e.d() && oq.o == 0) {
                        return;
                    }
                    object = this.b;
                    ((mt)object).e.b();
                    this.d(15);
                    this.z = 14;
                    if (!this.a.a((int)this.h, (int)0).a().O) {
                        int n4 = this.h;
                        object = this.b.b;
                        if (n4 == 1) {
                            ((mx)object).a[1].e();
                        } else {
                            ((mx)object).a[0].e();
                        }
                    }
                    ks.a().b(n2, this.m);
                    ++this.m;
                    this.o = 5;
                    return;
                }
                case 1010: {
                    if (this.Q == null) break;
                    this.Q.x();
                }
                default: {
                    return;
                }
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    protected final void a(byte[] object) {
        if (object != null) {
            this.N = true;
            byte[] byArray = object;
            object = this.a;
            this.a.o = byArray;
        }
    }

    protected final void b(byte[] byArray) {
        if (byArray != null) {
            this.a.a(byArray);
        }
    }

    protected void a(int n2, int n3, int n4, int n5, int n6) {
        ct.a("[SQControllerManager ]=============receiveOpponentSwapChess====================");
        this.b.b(n2, n3, n4, n5, n6);
        this.a(n3, n4, n5, n6, false);
    }

    protected void a(boolean bl2) {
        this.E = bl2;
        this.D = true;
        if (!this.f) {
            mt mt2 = this.b;
            mt2.c.a(0);
            this.f = true;
            if (this.k && !bl2) {
                try {
                    Thread.sleep(1000L);
                }
                catch (InterruptedException interruptedException) {
                    InterruptedException interruptedException2 = interruptedException;
                    interruptedException.printStackTrace();
                }
            }
        }
        mp.a().b();
    }

    public final void a(byte[] byArray, byte[] byArray2, byte[] byArray3, lh[] lhArray, lh[] lhArray2, boolean bl2, int n2) {
        this.o = -1;
        this.w = null;
        this.l.c();
        this.m = n2;
        this.a.a(byArray, byArray2, byArray3, lhArray, lhArray2);
        int n3 = 0;
        while (n3 <= 0) {
            this.b.b.a(lhArray2[0].s, lhArray2[0].u, lhArray2[0].w, lhArray2[0].b, lhArray2[0].T);
            ++n3;
        }
        n3 = 0;
        while (n3 <= 0) {
            this.b.b.a(lhArray[0].s, lhArray[0].u, lhArray[0].w, lhArray[0].b, lhArray[0].T);
            ++n3;
        }
        this.S = true;
        this.a(bl2);
        if (this.f) {
            this.d(10);
        } else {
            this.d(0);
        }
        this.b.c("L\u1ed7i! do m\u1ea1ng qu\u00e1 ch\u1eadm...");
        this.b.c.a(30);
        this.b.e();
    }

    private void a(int n2, int n3, int n4, byte[] byArray, byte[] byArray2, byte[] byArray3, byte[] byArray4) {
        ct.a("[SQCOntrollerManager]==========receiveSkillAffect================");
        this.r = n2;
        this.H = n3;
        this.I = n4 + 1;
        this.J = byArray;
        this.K = byArray2;
        this.L = byArray3;
        this.M = byArray4;
        this.d(11);
    }

    private void a(int n2, int n3, byte[] byArray, byte[] byArray2, byte[] byArray3, byte[] byArray4) {
        ct.a("[SQCOntrollerManager]=============processSkill=================" + n3);
        this.a.j = 0;
        this.a.h = 0;
        int n4 = (n2 + 1) % 2;
        Object object = this.a.a(n2, 0);
        int n5 = 0;
        object = ((lg)object).i(n3);
        boolean bl2 = true;
        if (object == null) {
            this.d(15);
            return;
        }
        switch (n3) {
            case 1000: {
                n3 = 0;
                while (n3 < byArray.length) {
                    this.a((int)byArray[n3], (int)byArray2[n3], -16777215, 1, true, 0);
                    ++n3;
                }
                break;
            }
            case 1001: {
                if (byArray == null) break;
                n3 = 0;
                while (n3 < byArray.length) {
                    this.a.a(byArray[n3], byArray2[n3], 10);
                    this.a.p[this.a.h++] = byArray[n3] << 8 | byArray2[n3] & 0xFF;
                    ++n3;
                }
                break;
            }
            case 1002: {
                this.a.a(n2, 0).a(this.I);
                bl2 = false;
                break;
            }
            case 1003: 
            case 1005: {
                bl2 = false;
                break;
            }
            case 1006: {
                n3 = 0;
                while (n3 < byArray.length) {
                    this.a((int)byArray[n3], (int)byArray2[n3], -16777215, 1, true, 0);
                    ++n3;
                }
                break;
            }
            case 1007: {
                n3 = 0;
                while (n3 < byArray.length) {
                    this.a((int)byArray[n3], (int)byArray2[n3], -16777215, 1, true, 0);
                    ++n3;
                }
                break;
            }
            case 1008: {
                n3 = 16;
                n5 = 16;
                n4 = byArray.length / 8;
                int n6 = 0;
                while (n6 < byArray.length) {
                    int n7 = 0;
                    while (n7 < n4) {
                        this.a((int)byArray[n6], (int)byArray2[n6], -16777215, 1, true, n3);
                        ++n6;
                        ++n7;
                    }
                    n3 -= 2;
                }
                break;
            }
            case 2000: {
                if (byArray == null) break;
                int n8 = 0;
                while (n8 < byArray.length) {
                    this.a((int)byArray[n8], (int)byArray2[n8], -16777215, 1, true, 0);
                    ++n8;
                }
                break;
            }
            case 2001: {
                this.a.a(n2, 0).c(this.I);
                bl2 = false;
                break;
            }
            case 2002: {
                this.a.a(n2, 0).d(this.I);
                bl2 = false;
                break;
            }
            case 2003: {
                if (byArray == null) break;
                int n9 = 0;
                while (n9 < byArray.length) {
                    this.a((int)byArray[n9], (int)byArray2[n9], -16777215, 1, true, 0);
                    ++n9;
                }
                break;
            }
            case 2004: {
                this.a.a(n4, 0).e(this.I);
            }
            case 2005: {
                bl2 = false;
                break;
            }
            case 2006: {
                if (byArray != null) {
                    int n10 = 0;
                    while (n10 < byArray3.length) {
                        n3 = n10 + 1;
                        while (n3 < byArray3.length) {
                            if (byArray3[n3] < byArray3[n10]) {
                                n4 = byArray3[n3];
                                byArray3[n3] = byArray3[n10];
                                byArray3[n10] = n4;
                            }
                            ++n3;
                        }
                        ++n10;
                    }
                    if (n2 == 0) {
                        n10 = 0;
                        while (n10 < byArray3.length) {
                            n3 = -2;
                            if (n10 == 1) {
                                n3 = 0;
                            }
                            n4 = 2;
                            while (n4 < 10) {
                                this.a((int)byArray3[n10], n4, -16777215, 1, true, n3 + n4);
                                ++n4;
                            }
                            ++n10;
                        }
                    } else {
                        n10 = 0;
                        while (n10 < byArray3.length) {
                            n3 = 9;
                            if (n10 == 1) {
                                n3 = 11;
                            }
                            n4 = 9;
                            while (n4 >= 2) {
                                this.a((int)byArray3[n10], n4, -16777215, 1, true, n3 - n4);
                                --n4;
                            }
                            ++n10;
                        }
                    }
                }
                bl2 = false;
                break;
            }
            case 2007: {
                if (byArray == null) break;
                int n11 = 0;
                while (n11 < byArray.length) {
                    this.a((int)byArray[n11], (int)byArray2[n11], -16777215, 1, true, 0);
                    ++n11;
                }
                break;
            }
            case 2008: {
                if (byArray == null) break;
                int n12 = 0;
                while (n12 < byArray.length) {
                    this.a((int)byArray[n12], (int)byArray2[n12], -16777215, 1, true, 0);
                    ++n12;
                }
                break;
            }
            case 4000: {
                if (byArray == null) break;
                int n13 = 0;
                while (n13 < byArray.length) {
                    this.a((int)byArray[n13], (int)byArray2[n13], -16777215, 1, true, 0);
                    ++n13;
                }
                break;
            }
            case 4001: {
                bl2 = false;
                break;
            }
            case 4002: {
                this.a.a(n4, 0).b(this.I);
                bl2 = false;
                break;
            }
            case 4004: 
            case 4005: {
                bl2 = false;
                break;
            }
            case 4006: {
                if (byArray == null) break;
                int n14 = 0;
                while (n14 < byArray.length) {
                    this.a((int)byArray[n14], (int)byArray2[n14], -16777215, 1, true, 0);
                    ++n14;
                }
                break;
            }
            case 4007: {
                if (byArray == null) break;
                int n15 = 0;
                while (n15 < byArray.length) {
                    this.a((int)byArray[n15], (int)byArray2[n15], -16777215, 1, true, 0);
                    ++n15;
                }
                break;
            }
            case 4008: {
                if (byArray == null) break;
                int n16 = 0;
                while (n16 < byArray.length) {
                    this.a((int)byArray[n16], (int)byArray2[n16], -16777215, 1, true, 0);
                    ++n16;
                }
                break;
            }
        }
        n4 = n2;
        mx mx2 = this.b.b;
        if (n4 == 1) {
            mx2.a[1].a(bl2);
        } else {
            mx2.a[0].a(bl2);
        }
        this.z = this.b.a((lv)object, byArray, byArray2, byArray3, byArray4, n2);
        if (this.z < n5) {
            this.z = n5;
        }
        this.z += 10;
    }

    protected final void p() {
        ks.a().a((kp)null);
        if (this.Q != null) {
            this.Q.t();
        }
    }

    protected void a(int n2, int n3, int n4, int n5, int n6, int[] nArray, int[] nArray2, ll[] llArray, lm[] lmArray) {
        this.g = n2;
        hs.n = n2 == 1 ? n3 - n4 : 0;
        hs.o = n5;
        hs.p = nArray;
        hs.q = nArray2;
        hs.r = n2 == 1;
        hs.s = n6;
        hs.t = n4;
        go.u = llArray;
        go.v = lmArray;
        this.d(9);
    }

    private void f(int n2) {
        this.a.g = n2;
        np.a = n2 * 1000;
    }

    public final void a(int n2) {
        ct.a("[SQCOntrollerManager] receiveCancelGame  " + n2);
        if (n2 < 0) {
            this.g = n2;
            al al2 = ag.b().a("", "K\u1ebft qu\u1ea3 tr\u1eadn \u0111\u1ea5u h\u00f2a!!! L\u1ed7i m\u1ea5t k\u1ebft n\u1ed1i do \u0111\u01b0\u1eddng truy\u1ec1n m\u1ea1ng...", "\u0110\u00f3ng", 1006, 1);
            al2.a(this);
            ag.b().a(al2);
            this.d(9);
            this.D = false;
            return;
        }
    }

    public void a(String object, String string) {
        object = this.b;
        ((mt)object).b(string);
    }

    public void a() {
        this.b.k();
    }

    protected void e(int n2, int n3) {
        if (n3 == 199000009 || n3 == 199000059) {
            this.b.b(n2, 1);
        } else if (n3 == 199000019 || n3 == 199000069) {
            this.b.b(n2, 2);
        } else if (n3 == 199000079) {
            this.b.b(n2, 3);
        }
        this.d(19);
    }

    public final void a(lh object, String string, long l2, String string2, boolean bl2, boolean bl3) {
        if (this.d == 16 || !this.k) {
            com.mg.sq.a.s().a(false, null);
            return;
        }
        object = com.mg.sq.a.a((lh)object, 1003, 1004, this, l2, string2, bl2, bl3);
        ((al)object).c(true);
        ((al)object).b(true);
    }

    private static void a(a a2, boolean bl2) {
        mw mw2;
        mw mw3;
        int n2 = 1;
        int n3 = a2.d();
        while (n2 < n3) {
            int n4 = n2;
            mw3 = (mw)a2.b(n4 - 1);
            mw2 = (mw)a2.b(n4);
            while (n4 > 0 && !(bl2 ? mw3.d >= mw2.d : mw3.g >= mw2.g)) {
                a2.a(n4 - 1, n4);
                if (--n4 <= 0) continue;
                mw3 = (mw)a2.b(n4 - 1);
                mw2 = (mw)a2.b(n4);
            }
            ++n2;
        }
        n2 = a2.d() - 1;
        while (n2 > 0) {
            mw3 = (mw)a2.b(n2 - 1);
            mw2 = (mw)a2.b(n2);
            if (bl2) {
                if (mw3.d == mw2.d && mw3.c == mw2.c) {
                    if (mw3.h < mw2.h) {
                        a2.a(n2);
                    } else {
                        a2.a(n2 - 1);
                    }
                }
            } else if (mw3.g == mw2.g && mw3.e == mw2.e) {
                if (mw3.i < mw2.i) {
                    a2.a(n2);
                } else {
                    a2.a(n2 - 1);
                }
            }
            --n2;
        }
    }

    private void a(a a2, a a3) {
        int n2 = 0;
        int n3 = a2.d();
        while (n2 < n3) {
            mw mw2 = (mw)a2.b(n2);
            int n4 = 0;
            int n5 = a3.d();
            while (n4 < n5) {
                mw mw3;
                mw mw4;
                mw mw5 = mw4 = (mw)a3.b(n4);
                mw4 = mw2;
                int n6 = mw5.f - mw4.c;
                int n7 = mw5.f - (mw4.c + mw4.d - 1);
                int n8 = mw4.b - mw5.e;
                int n9 = mw4.b - (mw5.e + mw5.g - 1);
                if (mw4.a.e != mw5.a.e || n6 < 0 || n7 > 0 || n8 < 0 || n9 > 0) {
                    mw3 = null;
                } else {
                    mw mw6 = new mw();
                    new mw().c = mw4.c;
                    mw6.b = mw4.b;
                    mw6.d = mw4.d;
                    mw6.f = mw5.f;
                    mw6.e = mw5.e;
                    mw6.g = mw5.g;
                    mw6.i = mw4.b;
                    mw6.h = mw5.f;
                    mw6.a = mw4.a;
                    mw3 = mw4 = mw6;
                }
                if (mw3 != null) {
                    this.X.a(mw4);
                    a2.a(n2);
                    a3.a(n4);
                    --n3;
                    --n2;
                    mw2 = null;
                    break;
                }
                ++n4;
            }
            if (mw2 != null) {
                this.a(mw2);
            }
            ++n2;
        }
        n2 = 0;
        n3 = a3.d();
        while (n2 < n3) {
            this.a((mw)a3.b(n2));
            ++n2;
        }
    }

    private void a(mw mw2) {
        if (mw2.d < 3 || mw2.g < 3) {
            if (mw2.d > 3) {
                mw2.h = mw2.c + (mw2.d - 1 >> 1);
            } else if (mw2.g > 3) {
                mw2.i = mw2.e + (mw2.g - 1 >> 1);
            }
        }
        this.X.a(mw2);
    }

    public void a(nq nq2) {
        this.l.a(nq2);
        if (nq2.b >= 0) {
            this.n = nq2.b;
        }
    }

    private void d(nq nq2) {
        ct.a("[SQControllerManager ]================processUseItem=================  turnModel.owner = " + nq2.a + "   rv = " + nq2.b);
        if (oq.o == 1) {
            ag.b().e(15000);
            if (this.a.b()) {
                this.a.b(false);
                this.b.a(0, this.i, this.j);
            }
        }
        this.a(nq2, true);
        if (nq2.g != null) {
            int n2 = 0;
            while (n2 < nq2.g.length) {
                this.b(nq2.g[n2]);
                ++n2;
            }
        }
        this.a(nq2.h);
        this.e(this.a(nq2.a), nq2.e);
        this.v = nq2;
        this.b.e();
        this.m = nq2.b;
    }

    private void e(nq object) {
        this.o = -1;
        if (((nq)object).E) {
            object = this;
            this.u = true;
            ((mq)object).D = false;
            return;
        }
        this.a(((nq)object).d);
        this.d(7);
    }

    protected void c(nq nq2) {
        if (ct.a()) {
            ct.a("SQControlManager.processSwapChess(): " + nq2);
        }
        this.a(nq2, true);
        if (nq2.g != null) {
            int n2 = 0;
            while (n2 < nq2.g.length) {
                this.b(nq2.g[n2]);
                ++n2;
            }
        }
        this.a(nq2.h);
        if (this.a(nq2.a) == 1) {
            this.a(1, nq2.j, nq2.k, nq2.l, nq2.m);
        }
        this.v = nq2;
        this.b.e();
        this.m = nq2.b;
    }

    protected boolean b(nq nq2) {
        return false;
    }

    private void t() {
        Object object;
        Object object2 = this.b.b;
        if (!(((mx)object2).a[0].f() && ((mx)object2).a[1].f())) {
            return;
        }
        if (this.v == null && !this.l.a() && (object = this.l.b()) != null) {
            if (this.b((nq)(object = (nq)object))) {
                return;
            }
            if (oq.o == 1 && this.a(((nq)object).a) != 0) {
                this.f(oq.p);
                object2 = this.b.b;
                ((mx)object2).c.b();
                ((mx)object2).c.a();
            }
            switch (((nq)object).c) {
                case 5: {
                    this.T = ((nq)object).i;
                    object2 = this;
                    ct.a("[SQControllerManager ]=============processUsingSkill   turnModel==== " + ((nq)object).a + "  rv = " + ((nq)object).b);
                    if (oq.o == 1) {
                        ag.b().e(15000);
                        if (((mq)object2).a.b()) {
                            ((mq)object2).a.b(false);
                            ((mq)object2).b.a(0, ((mq)object2).i, ((mq)object2).j);
                        }
                    }
                    ((mq)object2).a((nq)object, true);
                    if (((nq)object).g != null) {
                        int n2 = 0;
                        while (n2 < ((nq)object).g.length) {
                            ((mq)object2).b(((nq)object).g[n2]);
                            ++n2;
                        }
                    }
                    ((mq)object2).a(((nq)object).h);
                    super.a(super.a(((nq)object).a), ((nq)object).n, ((nq)object).r, ((nq)object).s, ((nq)object).o, ((nq)object).q, ((nq)object).p);
                    ((mq)object2).v = object;
                    ((mq)object2).b.e();
                    ((mq)object2).m = ((nq)object).b;
                    return;
                }
                case 3: {
                    this.T = ((nq)object).i;
                    this.c((nq)object);
                    return;
                }
                case 2: {
                    object2 = this;
                    ct.a("[SQControllerManager ]========processUpdateMatch=========== rv = " + ((nq)object).b);
                    super.a(((nq)object).a, ((nq)object).f);
                    ((mq)object2).m = ((nq)object).b;
                    super.e((nq)object);
                    ((mq)object2).b.e();
                    return;
                }
                case 4: {
                    this.T = ((nq)object).i;
                    this.d((nq)object);
                    return;
                }
                case 6: {
                    object2 = this;
                    ct.a("[SQControllerManager ]========processAttack=========== rv = " + ((nq)object).b);
                    if (oq.o == 1) {
                        ag.b().e(15000);
                        if (((mq)object2).a.b()) {
                            ((mq)object2).a.b(false);
                            ((mq)object2).b.a(0, ((mq)object2).i, ((mq)object2).j);
                        }
                    }
                    ((mq)object2).a((nq)object, false);
                    super.a(((nq)object).a, ((nq)object).f);
                    int n3 = super.a(((nq)object).a);
                    Object object3 = object2;
                    ((mq)object2).z = 10;
                    ((mq)object3).b.a(n3 == 1 ? 0 : 1, 14, 4, true);
                    com.mg.sq.a.s().a(-241439, false);
                    ((mq)object2).m = ((nq)object).b;
                    super.e((nq)object);
                    ((mq)object2).b.e();
                    return;
                }
                case 8: {
                    object2 = this;
                    ((mq)object2).b.b(true);
                    com.mg.sq.a.s().l();
                    ((mq)object2).a(((nq)object).t, ((nq)object).u, ((nq)object).v, ((nq)object).w, ((nq)object).x, ((nq)object).y, ((nq)object).z, ((nq)object).A, ((nq)object).B);
                    return;
                }
                case 1: {
                    this.f(((nq)object).k, ((nq)object).j);
                    return;
                }
                case 0: {
                    object2 = this;
                    this.m = ((nq)object).b;
                    super.e((nq)object);
                }
            }
        }
    }

    protected final nq a(String string, int n2, int n3, int n4, int n5, int n6) {
        nq nq2 = new nq(n6, 3);
        new nq(n6, 3).a = string;
        nq2.d = true;
        nq2.i = this.p;
        nq2.j = n2;
        nq2.k = n3;
        nq2.l = n4;
        nq2.m = n5;
        return nq2;
    }

    protected void f(int n2, int n3) {
        this.b.a(this.h, this.F, this.G, n3, n2);
        this.F = n3;
        this.G = n2;
        if (oq.o == 9 && this.s) {
            this.s = false;
            this.b.b(true);
        }
    }

    private int a(String string) {
        int n2 = 0;
        while (n2 < this.a.a().length) {
            int n3 = 0;
            while (n3 < this.a.a()[n2].length) {
                if (string.equals(this.a.a(n2, n3).j())) {
                    return n2;
                }
                ++n3;
            }
            ++n2;
        }
        return -1;
    }

    public final void q() {
        this.l = null;
        this.Q = null;
        this.b = null;
        this.a = null;
        this.M = null;
        this.K = null;
        this.c = null;
        this.A = null;
        this.L = null;
        this.J = null;
    }
}

