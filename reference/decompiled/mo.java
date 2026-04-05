/*
 * Decompiled with CFR 0.152.
 */
public abstract class mo
implements bj,
bk,
ko,
mp {
    protected mq a;
    protected mr b;
    protected mi[] c;
    protected int d;
    protected a e;
    private int x;
    private int[] y = new int[21];
    private int z;
    private int A;
    protected boolean f = false;
    protected int g = -1;
    private boolean B = false;
    private boolean C = false;
    protected int h = 0;
    protected int i = 5;
    protected int j = 6;
    private int D = 5;
    private int E = 6;
    private int F;
    private int G;
    private byte[] H;
    private byte[] I;
    private byte[] J;
    private byte[] K;
    private boolean L = false;
    private static int[] M;
    private static int[] N;
    private op O;
    private long P;
    protected boolean k;
    protected s l = new s(16);
    private boolean Q = false;
    protected int m = 0;
    protected int n;
    protected int o = -1;
    private long R = -1L;
    protected int p;
    private boolean S;
    private boolean T = false;
    protected boolean q;
    private String[][] U = new String[][]{{"Ng\u01b0\u01a1i ch\u00e1n s\u1ed1ng r\u1ed3i!!", "H\u1eeb, ng\u01b0\u01a1i to gan l\u1eafm!", "Ng\u01b0\u01a1i ch\u01b0a \u0111\u1ee7 s\u1ee9c!", "Ta s\u1ebd h\u1ea1 ng\u01b0\u01a1i trong v\u00f2ng 3 b\u01b0\u1edbc", "Mu\u1ed1n ch\u1ebft? Th\u00edch th\u00ec chi\u1ec1u!"}, {"Xem \u0111\u00e2y!!!", "Ti\u1ebfp chi\u00eau!!!", "Ch\u1ebft n\u00e8!!!"}};
    boolean r = false;
    public boolean s;
    private a V = new a();
    protected no t;
    no u = null;
    private boolean W = true;

    static {
        int[] nArray = new int[8];
        nArray[0] = -1;
        nArray[1] = -1;
        nArray[3] = 1;
        nArray[4] = 1;
        nArray[5] = 1;
        nArray[7] = -1;
        M = nArray;
        int[] nArray2 = new int[8];
        nArray2[1] = 1;
        nArray2[2] = 1;
        nArray2[3] = 1;
        nArray2[5] = -1;
        nArray2[6] = -1;
        nArray2[7] = -1;
        N = nArray2;
    }

    public mo(mq mq2, mr mr2, op op2) {
        this.O = op2;
        this.a = mq2;
        this.b = mr2;
        this.a.p = new int[64];
        this.a.i = new mu[64];
        int n2 = 0;
        while (n2 < this.a.i.length) {
            this.a.i[n2] = new mu();
            ++n2;
        }
        this.c = new mi[2];
        this.c[0] = new ml(this, 0);
        this.c[1] = new ml(this, 1);
        kq.a().a(this);
        this.d(0);
        if (this.a.a(1, 0).b()) {
            this.k = true;
            mr2.a(this.U[0][cy.a(this.U[0].length)], 50);
        }
        this.f(op.p);
        this.m = 0;
        this.P = System.currentTimeMillis();
    }

    public boolean f() {
        return false;
    }

    private void a(int n2, int n3, int n4, int n5, boolean bl2, int n6) {
        mu mu2 = this.a.i[this.a.j];
        this.a.i[this.a.j].a = this.a.b(n2, n3);
        mu2.h = n3;
        mu2.i = n2;
        mu2.b = n2;
        mu2.c = n3 - (n4 >> 16 & 0xFF);
        mu2.d = n4 & 0xFF;
        mu2.e = n2 - (n5 >> 16 & 0xFF);
        mu2.f = n3;
        mu2.g = n5 & 0xFF;
        mu2.k = bl2;
        mu2.j = n6;
        ++this.a.j;
        if (this.k && (mu2.a.A & 1) != 0 && cy.a(3) == 1 && this.h == 1) {
            this.b.a(this.U[1][cy.a(this.U[1].length)], 50);
        }
    }

    private void a(String string, nj[] njArray) {
        int n2;
        cw.a("[SQControllerManager]convertAttributeBufferToAttributeCharacter owner" + string);
        le[][] leArray = this.a.a();
        boolean[][] blArrayArray = new boolean[leArray.length][];
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        while (n5 < leArray.length) {
            blArrayArray[n5] = new boolean[leArray[n5].length];
            n2 = 0;
            while (n2 < leArray[n5].length) {
                if (leArray[n5][n2].j().equals(string)) {
                    n3 = n5;
                    n4 = n2;
                }
                blArrayArray[n5][n2] = leArray[n5][n2].p();
                ++n2;
            }
            ++n5;
        }
        le le2 = null;
        n2 = 0;
        while (n2 < njArray.length) {
            int n6 = 0;
            while (n6 < leArray.length) {
                int n7 = 0;
                while (n7 < leArray[n6].length) {
                    if (njArray[n2].a.equals(leArray[n6][n7].j())) {
                        le2 = leArray[n6][n7];
                        this.b.b.a(njArray[n2].b, njArray[n2].c, njArray[n2].d, njArray[n2].a, njArray[n2].f);
                        if (njArray[n2].b >= 0) {
                            int n8 = njArray[n2].b - le2.m();
                            cw.a(" convertAttributeBufferToAttributeCharacter   " + n8);
                            if (n8 < 0 && njArray[Math.abs((int)(n2 - 1))].e > 0) {
                                this.b.a(le2.j(), n6, -njArray[Math.abs((int)(n2 - 1))].e, blArrayArray[n3][n4]);
                            }
                            le2.g(njArray[n2].b);
                        }
                        if (njArray[n2].c >= 0) {
                            le2.h(njArray[n2].c);
                        }
                        if (njArray[n2].d >= 0) {
                            le2.j(njArray[n2].d);
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
        if (this.x > 0) {
            --this.x;
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
                this.x = this.b.a(this.g);
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
                var2_1 = this.t;
                var1_3 = this;
                if (var1_3.W) {
                    cw.a("[SQControllerManager ]========processAttibute======== newBoard=" + var1_3.L);
                    var1_3.W = false;
                }
                if (var2_1 == null) ** GOTO lbl62
                var1_3.a(var2_1.a, var2_1.f);
                var1_3.a(var2_1.i, var2_1.j);
                var4_5 = var2_1.F;
                var3_8 = var1_3;
                if (var4_5 != 0) {
                    var5_10 = var3_8.a.g + var4_5;
                    if (var5_10 <= 0) {
                        var5_10 = 1;
                    }
                    var6_12 = var3_8.b;
                    var6_12.b.j();
                    var3_8.f(var5_10);
                }
                var4_5 = var2_1.H;
                var3_8 = var1_3;
                if (op.o != 1) {
                    var3_8.z += var4_5;
                }
                var1_3.t = null;
                if (var1_3.L) ** GOTO lbl61
                var3_8 = var1_3;
                var4_6 = var3_8.a;
                cw.a("[SQControllerManager]  checkSumToSyncDataWithServer  " + var3_8.R + " = " + ow.a(var4_6.l));
                if (var3_8.R <= -1L) ** GOTO lbl-1000
                var4_6 = var3_8.a;
                if (var3_8.R != ow.a(var4_6.l)) {
                    var3_8.R = -1L;
                    var3_8.l.c();
                    kq.a().v();
                    v0 = true;
                } else lbl-1000:
                // 2 sources

                {
                    v0 = false;
                }
                if (v0) ** GOTO lbl62
lbl61:
                // 2 sources

                var1_3.e(var2_1);
lbl62:
                // 3 sources

                if (this.B) {
                    this.B = false;
                    this.a.e();
                    if (this.a.f()) {
                        if (this.L) {
                            this.d(20);
                        } else {
                            this.d(10);
                        }
                    }
                } else if (this.Q && this.a.f()) {
                    if (this.L) {
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
                if (op.o == 1 && this.T) break;
                this.T = true;
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
                cw.a("getAreaScoreFromSwapNode");
                var2_2.a.j = 0;
                var7_14 = var2_2.a.k.a;
                var8_15 = var2_2.a.k.b;
                var3_9 = var2_2.a.k.e[0];
                var5_11 = (var3_9 & 255) >= 3;
                var4_7 = var2_2.a.k.e[1];
                v1 = var6_13 = (var4_7 & 255) >= 3;
                if (var5_11 || var6_13) {
                    var2_2.a(var7_14, var8_15, var3_9, var4_7, true, 0);
                }
                var7_14 = var2_2.a.k.c;
                var8_15 = var2_2.a.k.d;
                var3_9 = var2_2.a.k.e[2];
                var5_11 = (var3_9 & 255) >= 3;
                var4_7 = var2_2.a.k.e[3];
                v2 = var6_13 = (var4_7 & 255) >= 3;
                if (var5_11 || var6_13) {
                    var2_2.a(var7_14, var8_15, var3_9, var4_7, true, 0);
                }
                if (var1_4.a.j > 0) {
                    var1_4.d(3);
                    break;
                }
                var1_4.S = false;
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

    public final a a(mq mq2) {
        a a2 = new a();
        int n2 = 2;
        while (n2 < 10) {
            int n3 = 2;
            while (n3 < 10) {
                mw mw2 = this.a(mq2, n2, n3, n2 + 1, n3);
                if (mw2 != null) {
                    a2.a(mw2);
                }
                if ((mw2 = this.a(mq2, n2, n3, n2, n3 + 1)) != null) {
                    a2.a(mw2);
                }
                ++n3;
            }
            ++n2;
        }
        return a2;
    }

    protected final void a(no object, boolean bl2) {
        switch (((no)object).E) {
            case 2: {
                this.a.a(false);
                boolean bl3 = false;
                mq mq2 = this.a;
                this.a.e = bl3;
                bl3 = true;
                mq2 = this.a;
                this.a.a = bl3;
                bl3 = false;
                mq2 = this.a;
                this.a.f = bl3;
                break;
            }
            case 1: {
                this.a.a(true);
                boolean bl4 = false;
                mq mq3 = this.a;
                this.a.e = bl4;
                bl4 = false;
                mq3 = this.a;
                this.a.a = bl4;
                bl4 = false;
                mq3 = this.a;
                this.a.f = bl4;
                break;
            }
            case 3: {
                this.a.a(false);
                boolean bl5 = true;
                mq mq4 = this.a;
                this.a.e = bl5;
                bl5 = false;
                mq4 = this.a;
                this.a.a = bl5;
                bl5 = false;
                mq4 = this.a;
                this.a.f = bl5;
                break;
            }
            case 4: {
                this.a.a(false);
                boolean bl6 = false;
                mq mq5 = this.a;
                this.a.e = bl6;
                bl6 = false;
                mq5 = this.a;
                this.a.a = bl6;
                bl6 = true;
                mq5 = this.a;
                this.a.f = bl6;
                break;
            }
            default: {
                this.a.a(false);
                boolean bl7 = false;
                mq mq6 = this.a;
                this.a.e = bl7;
                bl7 = false;
                mq6 = this.a;
                this.a.a = bl7;
                bl7 = false;
                mq6 = this.a;
                this.a.f = bl7;
            }
        }
        if (bl2) {
            object = this.a.a(this.a(((no)object).a), 0);
            this.a.d = ((le)object).p();
        }
    }

    protected final void d(int n2) {
        block17: while (true) {
            this.b.f();
            if (op.o != 1) {
                this.b.g();
            }
            Object object = this.b;
            ((mr)object).e.b();
            switch (n2) {
                case 20: {
                    this.x = 20;
                    this.L = false;
                    this.b.i();
                    break block17;
                }
                case 19: {
                    this.x = 15;
                    break block17;
                }
                case 15: {
                    this.b.a(true);
                    this.b.f();
                    if (op.o != 1) {
                        this.b.g();
                    }
                    ak.b().e(241205);
                    break block17;
                }
                case 16: {
                    this.x = 15;
                    break block17;
                }
                case 9: {
                    this.x = 30;
                    this.b.a(true);
                    break block17;
                }
                case 0: {
                    this.b.a(true);
                    this.b.h();
                    this.x = 26;
                    break block17;
                }
                case 10: {
                    this.b.a(true);
                    mr mr2 = this.b;
                    int n3 = 0;
                    int n4 = 0;
                    while (n4 < 8) {
                        int n5 = n3 + 0;
                        int n6 = 0;
                        while (n6 < 8) {
                            nb nb2 = mr2.a.a(n4, n6);
                            nb2.i(28, n5);
                            ++n5;
                            ++n6;
                        }
                        ++n3;
                        ++n4;
                    }
                    this.x = 22;
                    break block17;
                }
                case 7: {
                    this.q = true;
                    this.a.b(false);
                    int n7 = this.h;
                    this.h = this.C ? 0 : 1;
                    if (this.z <= 0) {
                        this.z = 0;
                    } else {
                        if (this.z > 0 || this.A > 0) {
                            this.b.a(this.h, this.z, 6, 6);
                        }
                        this.A = this.z--;
                    }
                    int n8 = 0;
                    while (n8 < 2) {
                        le le2 = this.a.a(n8, 0);
                        boolean bl2 = le2.e();
                        boolean bl3 = le2.g();
                        boolean bl4 = le2.h();
                        boolean bl5 = le2.i();
                        boolean bl6 = le2.f();
                        le2.s();
                        if (bl2 && !le2.e()) {
                            this.b.a(n8, 1, true);
                        }
                        if (bl3 && !le2.g()) {
                            this.b.a(n8, 0, true);
                        }
                        if (bl4 && !le2.h()) {
                            this.b.a(n8, 2, true);
                        }
                        if (bl5 && !le2.i()) {
                            this.b.a(n8, 1, false);
                        }
                        if (bl6 && !le2.f()) {
                            this.b.a(n8, 1, false);
                        }
                        this.b.b(n8);
                        ++n8;
                    }
                    n8 = 0;
                    while (n8 < this.y.length) {
                        this.y[n8] = 0;
                        ++n8;
                    }
                    int n9 = this.h;
                    mr mr3 = this.b;
                    mr3.b.d(n9);
                    this.b.a(false);
                    if (this.a.a(this.h, 0).b()) {
                        this.x = 15;
                        break block17;
                    }
                    if (n7 == this.h) break block17;
                    this.x = 5;
                    break block17;
                }
                case 8: {
                    this.a.d = false;
                    if (this.h != 0) break block17;
                    mr mr4 = this.b;
                    mr4.e.a();
                    if (this.O == null) break block17;
                    this.O.r();
                    break block17;
                }
                case 1: {
                    this.b.a(this.a.k.a, this.a.k.b, this.a.k.c, this.a.k.d, this.a.b(this.a.k.a, this.a.k.b), this.a.b(this.a.k.c, this.a.k.d));
                    this.x = 10;
                    this.a.a(this.a.k.a, this.a.k.b, this.a.k.c, this.a.k.d);
                    this.a.b(false);
                    break block17;
                }
                case 2: {
                    mo mo2 = this;
                    mo2.b.a(mo2.a.k.a, mo2.a.k.b, mo2.a.k.c, mo2.a.k.d, mo2.a.b(mo2.a.k.a, mo2.a.k.b), mo2.a.b(mo2.a.k.c, mo2.a.k.d));
                    mo2.a.a(mo2.a.k.a, mo2.a.k.b, mo2.a.k.c, mo2.a.k.d);
                    mo2.x = 10;
                    break block17;
                }
                case 3: {
                    Object object2;
                    if (this.a.j <= 0) {
                        if (this.a.o != null) {
                            this.Q = true;
                        }
                        n2 = 15;
                        continue block17;
                    }
                    this.x = 0;
                    object = this.a;
                    Object object3 = ((mq)object).l;
                    Object object4 = object3;
                    object3 = this;
                    object = null;
                    mu[] muArray = null;
                    if (op.n == 1) {
                        object = new mu[((mo)object3).a.j];
                        System.arraycopy(((mo)object3).a.i, 0, object, 0, ((mo)object3).a.j);
                        muArray = object;
                        object = object3;
                        ((mo)object).V.a();
                        if (muArray != null && muArray.length != 0) {
                            cw.a("=============================================checkMixing======================================" + muArray.length);
                            a a2 = new a(5);
                            object2 = new a(5);
                            int n10 = 0;
                            while (n10 < muArray.length) {
                                if (muArray[n10].d == 1 && muArray[n10].g == 1) {
                                    ((mo)object).V.a(muArray[n10]);
                                } else if (muArray[n10].d >= 3) {
                                    a2.a(muArray[n10]);
                                } else {
                                    ((a)object2).a(muArray[n10]);
                                }
                                ++n10;
                            }
                            mo.a(a2, true);
                            mo.a((a)object2, false);
                            mu mu2 = null;
                            if (a2.d() == 0 || ((a)object2).d() == 0) {
                                int n11 = 0;
                                int n12 = a2.d();
                                while (n11 < n12) {
                                    mu2 = (mu)a2.b(n11);
                                    super.a(mu2);
                                    ((mo)object).V.a(a2.b(n11));
                                    ++n11;
                                }
                                n11 = 0;
                                n12 = ((a)object2).d();
                                while (n11 < n12) {
                                    mu2 = (mu)((a)object2).b(n11);
                                    super.a(mu2);
                                    ((mo)object).V.a(((a)object2).b(n11));
                                    ++n11;
                                }
                            } else {
                                super.a(a2, (a)object2);
                            }
                        }
                    }
                    int n13 = ((mo)object3).a.j;
                    super.a((nh[][])object4, n13);
                    super.b((nh[][])object4, n13);
                    object2 = new int[100];
                    int n14 = 0;
                    while (n14 < ((mo)object3).a.j) {
                        object4 = ((mo)object3).a.i[n14];
                        n13 = ((mo)object3).b.a((mu)object4, ((mo)object3).h);
                        if (n13 > ((mo)object3).x) {
                            ((mo)object3).x = n13;
                        }
                        if (object4.a.z < 70) {
                            int n15 = ((mo)object3).y[object4.a.z] + 1;
                            if (object4.d != 1 || object4.g != 1) {
                                Object object5 = object2;
                                byte by2 = object4.a.z;
                                object5[by2] = object5[by2] + 1;
                                if (n15 > 1) {
                                    if (object4.d < 3) {
                                        ((mo)object3).b.c(n15, object4.e, object4.f, object4.g, object4.d);
                                    } else if (object4.g < 3) {
                                        ((mo)object3).b.c(n15, object4.b, object4.c, object4.g, object4.d);
                                    } else {
                                        ((mo)object3).b.c(n15, object4.e, object4.f, object4.g, object4.d);
                                        ((mo)object3).b.c(n15, object4.b, object4.c, object4.g, object4.d);
                                    }
                                }
                            }
                        }
                        ++n14;
                    }
                    if (op.n == 1) {
                        n14 = 0;
                        int n16 = ((mo)object3).V.d();
                        while (n14 < n16) {
                            mu mu3 = (mu)((mo)object3).V.b(n14);
                            if (mu3.a.A < 64) {
                                if (mu3.g >= 3 && mu3.d >= 3 || mu3.g >= 5 || mu3.d >= 5) {
                                    n13 = mp.w[mu3.a.z];
                                    ((mo)object3).a.a(mu3.i, mu3.h, n13);
                                    ((mo)object3).b.a(mu3.i, mu3.h, nh.a(n13), 0);
                                } else if (mu3.g >= 4 || mu3.d >= 4) {
                                    n13 = mp.v[mu3.a.z];
                                    ((mo)object3).a.a(mu3.i, mu3.h, n13);
                                    ((mo)object3).b.a(mu3.i, mu3.h, nh.a(n13), 0);
                                }
                            }
                            ++n14;
                        }
                    }
                    n14 = 0;
                    while (n14 < ((int[])object2).length) {
                        if (object2[n14] > 0) {
                            int n17 = n14;
                            ((mo)object3).y[n17] = ((mo)object3).y[n17] + 1;
                        }
                        ++n14;
                    }
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
                    this.a(this.p, this.F, this.H, this.I, this.J, this.K);
                }
            }
            break;
        }
        this.d = n2;
    }

    private void a(nh[][] nhArray, int n2) {
        int n3 = 0;
        while (n3 < n2) {
            int n4;
            mu mu2 = this.a.i[n3];
            if (mu2.d == 1 && mu2.g == 1) {
                n4 = mu2.c + mu2.d - 1;
                while (n4 >= mu2.c) {
                    if (nhArray[mu2.b][n4].B > 1 && nhArray[mu2.b][n4].A != -128) {
                        this.a(mu2.b, n4, -16777215, 1, mu2.k, mu2.j);
                    }
                    nhArray[mu2.b][n4] = nh.u;
                    --n4;
                }
            } else if (mu2.d < 3) {
                n4 = mu2.e + mu2.g - 1;
                while (n4 >= mu2.e) {
                    if (nhArray[n4][mu2.f].B > 1 && nhArray[n4][mu2.f].A != -128) {
                        this.a(n4, mu2.f, -16777215, 1, mu2.k, mu2.j);
                    }
                    nhArray[n4][mu2.f] = nh.u;
                    --n4;
                }
            } else if (mu2.g < 3) {
                n4 = mu2.c + mu2.d - 1;
                while (n4 >= mu2.c) {
                    if (nhArray[mu2.b][n4].B > 1 && nhArray[mu2.b][n4].A != -128) {
                        this.a(mu2.b, n4, -16777215, 1, mu2.k, mu2.j);
                    }
                    nhArray[mu2.b][n4] = nh.u;
                    --n4;
                }
            } else {
                n4 = mu2.c + mu2.d - 1;
                while (n4 >= mu2.c) {
                    if (nhArray[mu2.b][n4].B > 1 && nhArray[mu2.b][n4].A != -128) {
                        this.a(mu2.b, n4, -16777215, 1, mu2.k, mu2.j);
                    }
                    nhArray[mu2.b][n4] = nh.u;
                    --n4;
                }
                n4 = mu2.e + mu2.g - 1;
                while (n4 >= mu2.e) {
                    if (nhArray[n4][mu2.f].B > 1 && nhArray[n4][mu2.f].A != -128) {
                        this.a(n4, mu2.f, -16777215, 1, mu2.k, mu2.j);
                    }
                    nhArray[n4][mu2.f] = nh.u;
                    --n4;
                }
            }
            ++n3;
        }
    }

    private void b(nh[][] nhArray, int n2) {
        while (n2 < this.a.j) {
            mu mu2 = this.a.i[n2];
            switch (mu2.a.B) {
                case 2: {
                    int n3;
                    int n4;
                    int n5 = 0;
                    while (n5 < M.length) {
                        n4 = mu2.b + M[n5];
                        n3 = mu2.c + N[n5];
                        if (nhArray[n4][n3].z < 90) {
                            this.a(n4, n3, -16777215, 1, mu2.k, mu2.j);
                            nhArray[n4][n3] = nh.u;
                        }
                        ++n5;
                    }
                    break;
                }
                case 4: {
                    int n3;
                    int n4;
                    int n5 = 2;
                    while (n5 <= 10) {
                        n4 = mu2.b;
                        n3 = n5;
                        if (nhArray[n4][n3].z < 90) {
                            this.a(n4, n3, -16777215, 1, mu2.k, mu2.j);
                            nhArray[n4][n3] = nh.u;
                        }
                        ++n5;
                    }
                    n5 = 2;
                    while (n5 <= 10) {
                        n4 = n5;
                        n3 = mu2.c;
                        if (nhArray[n4][n3].z < 90) {
                            this.a(n4, n3, -16777215, 1, mu2.k, mu2.j);
                            nhArray[n4][n3] = nh.u;
                        }
                        ++n5;
                    }
                    break;
                }
            }
            nhArray[mu2.b][mu2.c] = nh.u;
            ++n2;
        }
    }

    private void r() {
        int n2;
        int n3;
        cw.a(" dropAllRows ");
        this.a.h = 0;
        nh[][] nhArray = this.a;
        nhArray = this.a.l;
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
                if ((nhArray[n2][n5].A & 0xFF) != 0) {
                    if (n2 != nArray[n5]) {
                        this.a.p[this.a.h++] = nArray[n5] << 8 | n5;
                        nhArray[nArray[n5]][n5] = nhArray[n2][n5];
                        nhArray[n2][n5] = nh.u;
                        this.b.a(n2, n5, nArray[n5], n5, nhArray[nArray[n5]][n5], nArray2[n5] + n4);
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
                    mq mq2 = this.a;
                    if (mq2.m == null || mq2.n >= mq2.m.length) {
                        mq2.d();
                    }
                    mq2.l[n9][n8] = nh.a(mq2.m[mq2.n++]);
                    this.b.a(n3, n5, n2, n5, nhArray[n2][n5], nArray2[n5] + n4);
                    int n10 = n5;
                    nArray2[n10] = nArray2[n10] + 1;
                    --n3;
                    this.x = 10;
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
            int n5 = mo.b(this.a.l, n3, n4);
            boolean bl3 = (n5 & 0xFF) >= 3;
            int n6 = mo.c(this.a.l, n3, n4);
            boolean bl4 = bl2 = (n6 & 0xFF) >= 3;
            if (bl3 || bl2) {
                this.a(n3, n4, n5, n6, true, 0);
            }
            ++n2;
        }
        this.a.h = 0;
    }

    private static int b(nh[][] nhArray, int n2, int n3) {
        if ((nhArray[n2][n3].A & 0xFFFFFF80) != 0) {
            return 0;
        }
        int n4 = 1;
        int n5 = 1;
        int n6 = nhArray[n2][n3].A;
        while ((n6 & nhArray[n2][n3 - n5].A) != 0) {
            ++n5;
            ++n4;
        }
        int n7 = 0xFF000000 | (n5 - 1 & 0xFF) << 16;
        n5 = 1;
        while ((n6 & nhArray[n2][n3 + n5].A) != 0) {
            ++n5;
            ++n4;
        }
        n7 |= (n5 - 1 & 0xFF) << 8;
        return n7 |= n4 & 0xFF;
    }

    private static int c(nh[][] nhArray, int n2, int n3) {
        if ((nhArray[n2][n3].A & 0xFFFFFF80) != 0) {
            return 0;
        }
        int n4 = 1;
        int n5 = 1;
        int n6 = nhArray[n2][n3].A;
        while ((n6 & nhArray[n2 - n5][n3].A) != 0) {
            ++n5;
            ++n4;
        }
        int n7 = 0 | (n5 - 1 & 0xFF) << 16;
        n5 = 1;
        while ((n6 & nhArray[n2 + n5][n3].A) != 0) {
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

    public final n k() {
        return this.b.a.b();
    }

    public final boolean a(int n2, int n3, int n4, int n5, boolean bl2) {
        int n6 = n5;
        int n7 = n4;
        int n8 = n3;
        int n9 = n2;
        mq mq2 = this.a;
        Object object = this;
        this.a.k = object = ((mo)object).a(mq2, n9, n8, n7, n6);
        if (this.a.k == null) {
            this.a.k = new mw();
            this.a.k.b = n3;
            this.a.k.a = n2;
            this.a.k.d = n5;
            this.a.k.c = n4;
        }
        this.D = n2;
        this.E = n3;
        this.d(1);
        return object != null;
    }

    private mw a(mq object, int n2, int n3, int n4, int n5) {
        block7: {
            block6: {
                if (((mq)object).l[n2][n3] != ((mq)object).l[n4][n5]) break block6;
                return null;
            }
            if ((((mq)object).l[n2][n3].A & 0xFFFFFF80) == 0 && (((mq)object).l[n4][n5].A & 0xFFFFFF80) == 0) break block7;
            return null;
        }
        try {
            mq.a(((mq)object).l, n2, n3, n4, n5);
            boolean bl2 = false;
            int n6 = mo.b(((mq)object).l, n2, n3);
            int n7 = mo.c(((mq)object).l, n2, n3);
            int n8 = mo.b(((mq)object).l, n4, n5);
            int n9 = mo.c(((mq)object).l, n4, n5);
            if ((n6 & 0xFF) >= 3 || (n7 & 0xFF) >= 3 || (n8 & 0xFF) >= 3 || (n9 & 0xFF) >= 3) {
                bl2 = true;
            }
            mq.a(((mq)object).l, n2, n3, n4, n5);
            if (bl2) {
                object = new int[]{n6, n7, n8, n9};
                mw mw2 = new mw();
                new mw().a = n2;
                mw2.b = n3;
                mw2.c = n4;
                mw2.d = n5;
                mw2.e = (int[])object;
                return mw2;
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
        if (mo.a(this.a.l, this.i, this.j)) {
            this.a.b(true);
            this.b.a(this.i, this.j);
            this.b.f();
            this.g(this.i, this.j);
        }
    }

    public final void f(int n2, int n3) {
        this.i = n2;
        this.j = n3;
        this.a.b(false);
        this.g(n2, n3);
        this.b.a(0, this.i, this.j);
    }

    protected final void g(int n2, int n3) {
        if (!this.k && op.o != 1 && System.currentTimeMillis() - this.P >= 500L) {
            kq.a().a(n2, n3, this.m);
            this.P = System.currentTimeMillis();
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

    public static boolean a(nh[][] nhArray, int n2, int n3) {
        return (nhArray[n2][n3].A & 0xFFFFFF80) == 0;
    }

    public static boolean a(boolean bl2, int n2, int n3) {
        if (!bl2) {
            return true;
        }
        return n2 >= 2 && n2 < 10 && n3 >= 2 && n3 < 10;
    }

    protected final void e(int n2) {
        this.b.a();
        if (this.O != null) {
            this.b.d.b(this.O.q[n2]);
        }
    }

    public final void a(int n2, int n3, Object object) {
        Object object2 = (bu)object;
        switch (n3) {
            case 11111: {
                object2 = this;
                Object object3 = "B\u1ea1n mu\u1ed1n tho\u00e1t kh\u1ecfi tr\u1eadn \u0111\u1ea5u";
                object3 = ak.b().a("Ch\u00fa \u00fd", (String)object3, "C\u00f3", -1, "Kh\u00f4ng", -2, 1);
                ((aq)object3).b(15000);
                ((aq)object3).a((bj)object2);
                ((ap)object3).c(true);
                ((ap)object3).b(true);
                ak.b().a((ap)object3, false);
                break;
            }
            case 11112: {
                if (!this.f()) break;
                object2 = this;
                ((mo)object2).b.a((bj)object2);
                break;
            }
            case 11113: {
                if (!this.f()) break;
                object2 = this;
                ((mo)object2).b.g.a((bj)object2);
                ak.b().a(((mo)object2).b.g);
                break;
            }
            case 11115: {
                object2 = ((bu)object2).a();
                if (object2 == null) break;
                int n4 = (Integer)object2;
                this.e(n4);
            }
        }
        if (this.O != null) {
            this.O.q();
        }
    }

    public final void d(int n2, int n3) {
        try {
            switch (n3) {
                case 1009: {
                    com.mg.sq.a.q().a(-241439, false);
                    return;
                }
                case 1008: {
                    this.d(15);
                    kq.a().c(this.b.g.t(), this.m);
                    ++this.m;
                    this.o = 1;
                    com.mg.sq.a.q().a(-241439, false);
                    return;
                }
                case 1000: {
                    if (this.O != null) {
                        this.O.a();
                        return;
                    }
                    break;
                }
                case 1002: {
                    if (this.O != null) {
                        this.O.q();
                        return;
                    }
                    break;
                }
                case 1001: {
                    if (this.O != null) {
                        this.O.f(95);
                        return;
                    }
                    break;
                }
                case 1003: {
                    long l2 = 0L;
                    if (ak.b().e() instanceof id) {
                        l2 = ((id)ak.b().e()).u();
                    }
                    if (gr.p < l2) {
                        this.d(-1, 1004);
                        ap ap2 = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n kh\u00f4ng \u0111\u1ee7 KEN \u0111\u1ec3 tham gia tr\u1eadn \u0111\u00e1nh n\u00e0y!", "\u0110\u00f3ng", 1006, 1);
                        ap2.a(this);
                        ak.b().a(ap2, false);
                        return;
                    }
                    com.mg.sq.a.a(true, null);
                    return;
                }
                case 1004: {
                    com.mg.sq.a.a(false, "\u0110ang \u0111\u00e1nh qu\u00e1i, \u0111\u1ee3i t\u00ed!");
                    return;
                }
                case -1: {
                    this.d(15);
                    ak.b().a(false);
                    if (op.o == 9) {
                        this.p();
                        kq.a().h();
                        return;
                    }
                    this.p();
                    kq.a().i();
                    return;
                }
                case -2: {
                    ak.b().a(false);
                    return;
                }
                case -6: {
                    ak.b().a(false);
                    this.g = 1;
                    return;
                }
                case 1006: {
                    ak.b().a(false);
                    return;
                }
                case 999999: {
                    Object object = this.b;
                    if (((mr)object).e.d() && op.o == 0) {
                        return;
                    }
                    object = this.b;
                    ((mr)object).e.b();
                    this.d(15);
                    this.x = 14;
                    if (!this.a.a((int)this.h, (int)0).a().Q) {
                        int n4 = this.h;
                        object = this.b.b;
                        if (n4 == 1) {
                            ((mv)object).a[1].e();
                        } else {
                            ((mv)object).a[0].e();
                        }
                    }
                    kq.a().b(n2, this.m);
                    ++this.m;
                    this.o = 2;
                    return;
                }
                case 1010: {
                    if (this.O == null) break;
                    this.O.x();
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
        if (object == null) {
            return;
        }
        this.L = true;
        byte[] byArray = object;
        object = this.a;
        this.a.o = byArray;
    }

    protected final void b(byte[] byArray) {
        this.a.a(byArray);
    }

    protected void a(int n2, int n3, int n4, int n5, int n6) {
        cw.a("[SQControllerManager ]=============receiveOpponentSwapChess====================");
        this.b.b(n2, n3, n4, n5, n6);
        this.a(n3, n4, n5, n6, false);
    }

    protected void a(boolean bl2) {
        this.C = bl2;
        this.B = true;
        if (!this.f) {
            mr mr2 = this.b;
            mr2.c.a(0);
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
        mn.a().b();
    }

    private void a(int[] nArray, int[] nArray2) {
        if (nArray != null && nArray2 != null) {
            int n2 = 0;
            while (n2 < nArray.length) {
                int n3 = nArray[n2];
                int n4 = nArray2[n2];
                this.a.a(n3, n4, 80);
                int n5 = n4;
                n4 = n3;
                mr mr2 = this.b;
                mr2.a.a(n4, n5, nh.a(80));
                ++n2;
            }
        }
    }

    public final void a(byte[] byArray, byte[] byArray2, byte[] byArray3, lf[] lfArray, lf[] lfArray2, boolean bl2, int n2) {
        this.o = -1;
        this.u = null;
        this.l.c();
        this.m = n2;
        this.a.a(byArray, byArray2, byArray3, lfArray, lfArray2);
        int n3 = 0;
        while (n3 < lfArray2.length) {
            this.b.b.a(lfArray2[n3].s, lfArray2[n3].u, lfArray2[n3].w, lfArray2[n3].b, lfArray2[n3].V);
            ++n3;
        }
        n3 = 0;
        while (n3 < lfArray.length) {
            this.b.b.a(lfArray[n3].s, lfArray[n3].u, lfArray[n3].w, lfArray[n3].b, lfArray[n3].V);
            ++n3;
        }
        this.Q = true;
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
        cw.a("[SQCOntrollerManager]==========receiveSkillAffect================");
        this.p = n2;
        this.F = n3;
        this.G = n4 + 1;
        this.H = byArray;
        this.I = byArray2;
        this.J = byArray3;
        this.K = byArray4;
        this.d(11);
    }

    private void a(int n2, int n3, byte[] byArray, byte[] byArray2, byte[] byArray3, byte[] byArray4) {
        cw.a("[SQCOntrollerManager]=============processSkill=================" + n3);
        this.a.j = 0;
        this.a.h = 0;
        int n4 = (n2 + 1) % 2;
        Object object = this.a.a(n2, 0);
        int n5 = 0;
        object = ((le)object).i(n3);
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
                this.a.a(n2, 0).a(this.G);
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
                this.a.a(n2, 0).c(this.G);
                bl2 = false;
                break;
            }
            case 2002: {
                this.a.a(n2, 0).d(this.G);
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
                this.a.a(n4, 0).e(this.G);
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
                this.a.a(n4, 0).b(this.G);
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
        mv mv2 = this.b.b;
        if (n4 == 1) {
            mv2.a[1].a(bl2);
        } else {
            mv2.a[0].a(bl2);
        }
        this.x = this.b.a((lt)object, byArray, byArray2, byArray3, byArray4, n2);
        if (this.x < n5) {
            this.x = n5;
        }
        this.x += 10;
    }

    protected final void p() {
        kq.a().a((ko)null);
        if (this.O != null) {
            this.O.t();
        }
    }

    protected void a(int n2, int n3, int n4, int n5, int n6, int[] nArray, int[] nArray2, lj[] ljArray, lk[] lkArray) {
        this.g = n2;
        hq.n = n2 == 1 ? n3 - n4 : 0;
        hq.o = n5;
        hq.p = nArray;
        hq.q = nArray2;
        hq.r = n2 == 1;
        hq.s = n6;
        hq.t = n4;
        gr.r = ljArray;
        gr.s = lkArray;
        this.d(9);
    }

    private void f(int n2) {
        this.a.g = n2;
        nn.a = n2 * 1000;
    }

    public final void a(int n2) {
        cw.a("[SQCOntrollerManager] receiveCancelGame  " + n2);
        if (n2 < 0) {
            this.g = n2;
            ap ap2 = ak.b().a("", "K\u1ebft qu\u1ea3 tr\u1eadn \u0111\u1ea5u h\u00f2a!!! L\u1ed7i m\u1ea5t k\u1ebft n\u1ed1i do \u0111\u01b0\u1eddng truy\u1ec1n m\u1ea1ng...", "\u0110\u00f3ng", 1006, 1);
            ap2.a(this);
            ak.b().a(ap2);
            this.d(9);
            this.B = false;
            return;
        }
    }

    public void a(String object, String string) {
        object = this.b;
        ((mr)object).b(string);
    }

    public void a() {
        this.b.j();
    }

    protected void c(int n2, int n3) {
        if (n3 == 199000009 || n3 == 199000059) {
            this.b.b(n2, 1);
        } else if (n3 == 199000019 || n3 == 199000069) {
            this.b.b(n2, 2);
        } else if (n3 == 199000079) {
            this.b.b(n2, 3);
        }
        this.d(19);
    }

    public final void a(lf object, String string, long l2, String string2, boolean bl2, boolean bl3) {
        if (this.d == 16 || !this.k) {
            com.mg.sq.a.a(false, null);
            return;
        }
        object = com.mg.sq.a.a((lf)object, 1003, 1004, this, l2, string2, bl2, bl3);
        ((ap)object).c(true);
        ((ap)object).b(true);
    }

    private static void a(a a2, boolean bl2) {
        mu mu2;
        mu mu3;
        int n2 = 0;
        int n3 = 1;
        int n4 = a2.d();
        while (n3 < n4) {
            n2 = n3;
            mu3 = (mu)a2.b(n2 - 1);
            mu2 = (mu)a2.b(n2);
            while (n2 > 0 && !(bl2 ? mu3.d >= mu2.d : mu3.g >= mu2.g)) {
                a2.a(n2 - 1, n2);
                if (--n2 <= 0) continue;
                mu3 = (mu)a2.b(n2 - 1);
                mu2 = (mu)a2.b(n2);
            }
            ++n3;
        }
        n3 = a2.d() - 1;
        while (n3 > 0) {
            mu3 = (mu)a2.b(n3 - 1);
            mu2 = (mu)a2.b(n3);
            if (bl2) {
                if (mu3.d == mu2.d && mu3.c == mu2.c) {
                    if (mu3.h < mu2.h) {
                        a2.a(n3);
                    } else {
                        a2.a(n3 - 1);
                    }
                }
            } else if (mu3.g == mu2.g && mu3.e == mu2.e) {
                if (mu3.i < mu2.i) {
                    a2.a(n3);
                } else {
                    a2.a(n3 - 1);
                }
            }
            --n3;
        }
    }

    private void a(a a2, a a3) {
        mu mu2 = null;
        int n2 = 0;
        int n3 = a2.d();
        while (n2 < n3) {
            mu mu3 = (mu)a2.b(n2);
            int n4 = 0;
            int n5 = a3.d();
            while (n4 < n5) {
                mu mu4;
                mu mu5 = mu2 = (mu)a3.b(n4);
                mu2 = mu3;
                int n6 = mu5.f - mu2.c;
                int n7 = mu5.f - (mu2.c + mu2.d - 1);
                int n8 = mu2.b - mu5.e;
                int n9 = mu2.b - (mu5.e + mu5.g - 1);
                if (mu2.a.A != mu5.a.A || n6 < 0 || n7 > 0 || n8 < 0 || n9 > 0) {
                    mu4 = null;
                } else {
                    mu mu6 = new mu();
                    new mu().c = mu2.c;
                    mu6.b = mu2.b;
                    mu6.d = mu2.d;
                    mu6.f = mu5.f;
                    mu6.e = mu5.e;
                    mu6.g = mu5.g;
                    mu6.i = mu2.b;
                    mu6.h = mu5.f;
                    mu6.a = mu2.a;
                    mu4 = mu2 = mu6;
                }
                if (mu4 != null) {
                    this.V.a(mu2);
                    a2.a(n2);
                    a3.a(n4);
                    --n3;
                    --n2;
                    mu3 = null;
                    break;
                }
                ++n4;
            }
            if (mu3 != null) {
                this.a(mu3);
            }
            ++n2;
        }
        n2 = 0;
        n3 = a3.d();
        while (n2 < n3) {
            this.a((mu)a3.b(n2));
            ++n2;
        }
    }

    private void a(mu mu2) {
        if (mu2.d < 3 || mu2.g < 3) {
            if (mu2.d > 3) {
                mu2.h = mu2.c + (mu2.d - 1 >> 1);
            } else if (mu2.g > 3) {
                mu2.i = mu2.e + (mu2.g - 1 >> 1);
            }
        }
        this.V.a(mu2);
    }

    public void a(no no2) {
        this.l.a(no2);
        if (no2.b >= 0) {
            this.n = no2.b;
        }
    }

    private void d(no no2) {
        cw.a("[SQControllerManager ]================processUseItem=================  turnModel.owner = " + no2.a + "   rv = " + no2.b);
        if (op.o == 1) {
            ak.b().e(15000);
            if (this.a.b()) {
                this.a.b(false);
                this.b.a(0, this.i, this.j);
            }
        }
        this.a(no2, true);
        if (no2.g != null) {
            int n2 = 0;
            while (n2 < no2.g.length) {
                this.b(no2.g[n2]);
                ++n2;
            }
        }
        this.a(no2.h);
        this.c(this.a(no2.a), no2.e);
        this.t = no2;
        this.b.e();
        this.m = no2.b;
    }

    private void e(no object) {
        this.o = -1;
        if (((no)object).G) {
            object = this;
            this.s = true;
            ((mo)object).B = false;
            return;
        }
        this.a(((no)object).d);
        this.d(7);
    }

    protected void c(no no2) {
        cw.a("[SQControllerManager ]=====processSwap===== rv =" + no2.b);
        this.a(no2, true);
        if (no2.g != null) {
            int n2 = 0;
            while (n2 < no2.g.length) {
                this.b(no2.g[n2]);
                ++n2;
            }
        }
        this.a(no2.h);
        if (this.a(no2.a) == 1) {
            this.a(1, no2.l, no2.m, no2.n, no2.o);
        }
        this.t = no2;
        this.b.e();
        this.m = no2.b;
    }

    protected boolean b(no no2) {
        return false;
    }

    private void t() {
        Object object;
        Object object2 = this.b.b;
        if (!(((mv)object2).a[0].f() && ((mv)object2).a[1].f())) {
            return;
        }
        if (this.t == null && !this.l.a() && (object = this.l.b()) != null) {
            this.W = true;
            if (this.b((no)(object = (no)object))) {
                return;
            }
            if (op.o == 1 && this.a(((no)object).a) != 0) {
                this.f(op.p);
                object2 = this.b.b;
                ((mv)object2).c.b();
                ((mv)object2).c.a();
            }
            switch (((no)object).c) {
                case 2: {
                    this.R = ((no)object).k;
                    object2 = this;
                    cw.a("[SQControllerManager ]=============processUsingSkill   turnModel==== " + ((no)object).a + "  rv = " + ((no)object).b);
                    if (op.o == 1) {
                        ak.b().e(15000);
                        if (((mo)object2).a.b()) {
                            ((mo)object2).a.b(false);
                            ((mo)object2).b.a(0, ((mo)object2).i, ((mo)object2).j);
                        }
                    }
                    ((mo)object2).a((no)object, true);
                    if (((no)object).g != null) {
                        int n2 = 0;
                        while (n2 < ((no)object).g.length) {
                            ((mo)object2).b(((no)object).g[n2]);
                            ++n2;
                        }
                    }
                    ((mo)object2).a(((no)object).h);
                    super.a(super.a(((no)object).a), ((no)object).p, ((no)object).t, ((no)object).u, ((no)object).q, ((no)object).s, ((no)object).r);
                    ((mo)object2).t = object;
                    ((mo)object2).b.e();
                    ((mo)object2).m = ((no)object).b;
                    return;
                }
                case 0: {
                    this.R = ((no)object).k;
                    this.c((no)object);
                    return;
                }
                case 8: {
                    object2 = this;
                    cw.a("[SQControllerManager ]========processUpdateMatch=========== rv = " + ((no)object).b);
                    super.a(((no)object).a, ((no)object).f);
                    ((mo)object2).m = ((no)object).b;
                    super.e((no)object);
                    ((mo)object2).b.e();
                    return;
                }
                case 1: {
                    this.R = ((no)object).k;
                    this.d((no)object);
                    return;
                }
                case 3: {
                    object2 = this;
                    cw.a("[SQControllerManager ]========processAttack=========== rv = " + ((no)object).b);
                    if (op.o == 1) {
                        ak.b().e(15000);
                        if (((mo)object2).a.b()) {
                            ((mo)object2).a.b(false);
                            ((mo)object2).b.a(0, ((mo)object2).i, ((mo)object2).j);
                        }
                    }
                    ((mo)object2).a((no)object, false);
                    super.a(((no)object).a, ((no)object).f);
                    int n3 = super.a(((no)object).a);
                    Object object3 = object2;
                    ((mo)object2).x = 10;
                    ((mo)object3).b.a(n3 == 1 ? 0 : 1, 14, 4, true);
                    com.mg.sq.a.q().a(-241439, false);
                    ((mo)object2).m = ((no)object).b;
                    super.e((no)object);
                    ((mo)object2).b.e();
                    return;
                }
                case 5: {
                    object2 = this;
                    ((mo)object2).b.b(true);
                    com.mg.sq.a.q().k();
                    ((mo)object2).a(((no)object).v, ((no)object).w, ((no)object).x, ((no)object).y, ((no)object).z, ((no)object).A, ((no)object).B, ((no)object).C, ((no)object).D);
                    return;
                }
                case 7: {
                    this.e(((no)object).m, ((no)object).l);
                    return;
                }
                case 6: {
                    object2 = this;
                    this.m = ((no)object).b;
                    super.e((no)object);
                }
            }
        }
    }

    protected void e(int n2, int n3) {
        this.b.a(this.h, this.D, this.E, n3, n2);
        this.D = n3;
        this.E = n2;
        if (op.o == 9 && this.q) {
            this.q = false;
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
        this.O = null;
        this.b = null;
        this.a = null;
        this.K = null;
        this.I = null;
        this.c = null;
        this.y = null;
        this.J = null;
        this.H = null;
    }
}

