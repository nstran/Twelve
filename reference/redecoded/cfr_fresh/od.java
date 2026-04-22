/*
 * Decompiled with CFR 0.152.
 */
import com.mg.sq.a;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;

public final class od
extends nz
implements bf,
bh,
bi {
    private boolean b;
    private int c;
    private gk d;
    private gm k;
    private String[][] l = new String[][]{{"N\u00fat 0:", "He he"}, {"N\u00fat 1:", "Hi hi"}, {"N\u00fat 2:", "Ch\u1ebft n\u00e8!"}, {"N\u00fat 3:", "Y\u1ebfu m\u00e0 ra gi\u00f3!"}, {"N\u00fat 4:", "T\u01b0\u1edfng b\u1edf h\u1ea3!"}, {"N\u00fat 5:", "Ti\u1ebfp chi\u00eau n\u00e8!"}, {"N\u00fat 6:", "\u0102n \u0111\u00f2n n\u00e8!"}, {"N\u00fat 7:", "Xem \u0111\u00e2y!"}, {"N\u00fat 8:", "Tha m\u1ea1ng!! hix hix"}, {"N\u00fat 9:", "Kh\u00f4ng ch\u01b0\u1edfng nha!"}};

    public od(int n2) {
        super((byte)0);
        int n3;
        Object object;
        bf bf2;
        try {
            this.c = n2;
            this.a(this);
            this.a(new ba());
            bd bd2 = new bd("L\u01b0u", 1);
            bf2 = this;
            bf2.a(bd2, true);
            bd2 = new bd("H\u1ee7y", 0);
            bf2 = this;
            bf2.b(bd2, true);
            this.g -= ba.a;
            this.j(true);
            new cu(10, 22);
            int n4 = this.i();
            object = new gh("Game");
            ((aq)object).a_(-1919);
            ((aq)object).d(n4);
            ((gh)object).d(5, 5);
            this.a((aq)object);
            if (go.k != null) {
                object = new gk("Ch\u1eb7n khi\u00eau chi\u1ebfn");
                ((gk)object).a((byte)1);
                ((aq)object).a_(122);
                ((gk)object).a(0, 0, n4, 20);
                ((gk)object).a(this);
                ((gk)object).e(go.k.aa);
                this.a((aq)object);
                object = new gk("Ch\u1eb7n giao d\u1ecbch");
                ((gk)object).a((byte)1);
                ((aq)object).a_(139);
                ((gk)object).a(0, 0, n4, 20);
                ((gk)object).a(this);
                ((gk)object).e(go.k.aa);
                this.a((aq)object);
            }
            object = new gk("T\u1ef1 \u0111\u1ed9ng \u0111\u0103ng nh\u1eadp Ola");
            ((gk)object).a((byte)1);
            ((aq)object).a_(138);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(gr.k);
            this.a((aq)object);
            object = new gk("Hi\u1ec7u \u1ee9ng \u0111\u1ed3 h\u1ecda");
            ((gk)object).a((byte)1);
            ((aq)object).a_(137);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(!gr.r);
            this.a((aq)object);
            object = new gk("Hi\u1ec7u \u1ee9ng \u00e2m thanh");
            ((gk)object).a((byte)1);
            ((aq)object).a_(123);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(v.O);
            this.a((aq)object);
            this.d = new gk("Nh\u1ea1c n\u1ec1n");
            this.d.a((byte)1);
            this.d.a_(124);
            this.d.a(0, 0, n4, 20);
            this.a(this.d);
            this.k = new gm("\u00c2m l\u01b0\u1ee3ng");
            this.k.a_(125);
            this.k.d(n4);
            this.k.h(v.Q);
            this.k.a(this);
            this.d.a(this);
            this.a(this.k);
            if (!v.N) {
                this.d.e(false);
                v.Q = 0;
            }
            object = new gk("B\u00e1o rung");
            ((gk)object).a((byte)1);
            ((aq)object).a_(126);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(v.P);
            this.a((aq)object);
            object = new fe("C\u00e2u tr\u1ea3 l\u1eddi t\u1ef1 \u0111\u1ed9ng ");
            ((aq)object).a_(136);
            ((fe)object).a(0, 0, n4, 20);
            n3 = 0;
            while (n3 < this.l.length) {
                bf2 = new fl(this.l[n3][0], this.l[n3][1]);
                ((fe)object).a((fl)bf2);
                ++n3;
            }
            this.a((aq)object);
            object = new gh("Ola");
            ((aq)object).a_(-1919);
            ((aq)object).d(n4);
            ((gh)object).d(12, 5);
            this.a((aq)object);
            object = new gk("H\u1ed7 tr\u1ee3 g\u00f5 ti\u1ebfng Vi\u1ec7t c\u00f3 d\u1ea5u");
            ((gk)object).a((byte)1);
            ((aq)object).a_(127);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(v.a);
            this.a((aq)object);
            object = new gk("\u0110\u0103ng nh\u1eadp \u1ea9n");
            ((gk)object).a((byte)1);
            ((aq)object).a_(132);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(!gr.d);
            this.a((aq)object);
            object = new gk("Hi\u1ec7n danh s\u00e1ch nick \u1ea9n");
            ((gk)object).a((byte)1);
            ((aq)object).a_(133);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(gr.c);
            this.a((aq)object);
            object = new gk("L\u01b0u th\u00f4ng \u0111i\u1ec7p");
            ((gk)object).a((byte)1);
            ((aq)object).a_(128);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(gr.g);
            this.a((aq)object);
            object = new gk("L\u01b0u danh s\u00e1ch nick");
            ((gk)object).a((byte)1);
            ((aq)object).a_(129);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(gr.h);
            this.a((aq)object);
            object = new gk("L\u01b0u danh s\u00e1ch chat");
            ((gk)object).a((byte)1);
            ((aq)object).a_(131);
            ((gk)object).a(0, 0, n4, 20);
            ((gk)object).a(this);
            ((gk)object).e(gr.i);
            this.a((aq)object);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.g(1);
        od od2 = this;
        object = cs.a;
        n3 = 0;
        bf2 = od2;
        int n5 = bf2.a.s().length;
        while (n3 < n5) {
            int n6 = od2.f(n3).b();
            if (n6 > 0 && ((u)object).c(n6)) {
                aq aq2;
                Object object2 = ((u)object).a(n6);
                if (od2.f(n3) instanceof gm) {
                    aq2 = (gf)od2.f(n3);
                    ((gf)aq2).h(object2[0]);
                } else if (od2.f(n3) instanceof fe) {
                    aq2 = (fe)od2.f(n3);
                    try {
                        ((fe)aq2).a((byte[])object2);
                    }
                    catch (IOException iOException) {
                        object2 = iOException;
                        iOException.printStackTrace();
                    }
                } else if (od2.f(n3) instanceof gk) {
                    aq2 = (gk)od2.f(n3);
                    ((gk)aq2).e(object2[0] == 0);
                }
            }
            ++n3;
        }
        this.b = v.N;
    }

    public static void d() {
        u u2 = cs.a;
        int n2 = 0;
        while (n2 < 17) {
            int n3 = n2 + 123;
            if (u2.c(n3)) {
                byte[] byArray = u2.a(n3);
                od.h(n3, byArray[0]);
            }
            ++n2;
        }
        if (u2.c(140)) {
            gr.m = false;
        }
        u2.c(141);
        if (u2.c(142)) {
            gr.n = false;
        }
        if (u2.c(143)) {
            gr.o = false;
        }
        if (u2.c(144)) {
            gr.p = false;
        }
        if (u2.c(145)) {
            gr.q = false;
        }
    }

    private static void h(int n2, int n3) {
        switch (n2) {
            case 123: {
                v.O = n3 != 1;
                return;
            }
            case 137: {
                gr.r = n3 != 0;
                return;
            }
            case 124: {
                v.N = n3 != 1;
                return;
            }
            case 126: {
                v.P = n3 != 1;
                return;
            }
            case 138: {
                gr.k = n3 != 1;
                return;
            }
            case 139: {
                gr.l = n3 != 1;
                return;
            }
            case 127: {
                v.a = n3 != 1;
                if (!v.a || !v.ai) break;
                v.b = true;
                return;
            }
            case 129: {
                gr.h = n3 != 1;
                return;
            }
            case 128: {
                gr.g = n3 != 1;
                return;
            }
            case 130: {
                return;
            }
            case 131: {
                gr.i = n3 != 1;
                return;
            }
            case 125: {
                v.Q = n3;
                return;
            }
            case 132: {
                boolean bl2 = gr.d = n3 != 0;
                if (com.mg.sq.a.m == null || !com.mg.sq.a.o) break;
                com.mg.sq.a.m.I();
                return;
            }
            case 133: {
                boolean bl3 = gr.c = n3 != 1;
                if (com.mg.sq.a.m == null || !com.mg.sq.a.o) break;
                com.mg.sq.a.m.J();
            }
        }
    }

    public static String[] e() {
        Object object = new String[][]{{"N\u00fat 0:", "He he"}, {"N\u00fat 1:", "Hi hi"}, {"N\u00fat 2:", "Ch\u1ebft n\u00e8!"}, {"N\u00fat 3:", "Y\u1ebfu m\u00e0 ra gi\u00f3!"}, {"N\u00fat 4:", "T\u01b0\u1edfng b\u1edf h\u1ea3!"}, {"N\u00fat 5:", "Ti\u1ebfp chi\u00eau n\u00e8!"}, {"N\u00fat 6:", "\u0102n \u0111\u00f2n n\u00e8!"}, {"N\u00fat 7:", "Xem \u0111\u00e2y!"}, {"N\u00fat 8:", "Tha m\u1ea1ng!! hix hix"}, {"N\u00fat 9:", "Kh\u00f4ng ch\u01b0\u1edfng nha!"}};
        String[] stringArray = null;
        if (cs.a.c(136)) {
            object = cs.a.a(136);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
            object = new DataInputStream(byteArrayInputStream);
            String[] stringArray2 = new String[10];
            try {
                int n2 = 0;
                while (n2 < 10) {
                    stringArray2[n2] = ((DataInputStream)object).readUTF();
                    ++n2;
                }
                stringArray = stringArray2;
                ((FilterInputStream)object).close();
                byteArrayInputStream.close();
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        } else {
            stringArray = new String[10];
            int n3 = 0;
            while (n3 < 10) {
                stringArray[n3] = object[n3][1];
                ++n3;
            }
        }
        return stringArray;
    }

    public static final void f() {
        byte[] byArray;
        u u2 = cs.a;
        if (gr.d) {
            byte[] byArray2 = new byte[1];
            byArray = byArray2;
            byArray2[0] = 1;
        } else {
            byArray = new byte[1];
        }
        u2.b(132, byArray);
    }

    public static void h(int n2) {
        u u2 = cs.a;
        u2.b(n2, new byte[]{1});
    }

    /*
     * Unable to fully structure code
     */
    public final void d(int var1_1, int var2_3) {
        switch (var2_3) {
            case 0: {
                ag.b().f(this.c);
                return;
            }
            case 1: {
                var1_2 = this;
                var2_4 = cs.a;
                var5_5 = 0;
                if (true) ** GOTO lbl54
                do {
                    var3_6 = var1_2.f(var5_5);
                    var4_8 = var3_6.b();
                    var6_9 = 0;
                    if (!(var3_6 instanceof gf)) ** GOTO lbl20
                    var3_6 = (gf)var3_6;
                    var6_9 = (byte)var3_6.a();
                    var2_4.a(var4_8, new byte[]{var6_9});
                    ** GOTO lbl52
lbl20:
                    // 1 sources

                    if (!(var3_6 instanceof fe)) ** GOTO lbl25
                    var3_6 = (fe)var3_6;
                    var2_4.a(var4_8, var3_6.a());
                    ** GOTO lbl52
lbl25:
                    // 1 sources

                    if (!(var3_6 instanceof gk)) ** GOTO lbl53
                    var3_7 = (var3_6 = (gk)var3_6).a();
                    var6_9 = var3_7 != false ? 0 : 1;
                    switch (var4_8) {
                        case 124: {
                            if (var3_7 != var1_2.b) {
                                var1_2.b = var3_7;
                                v.N = var3_7;
                                if (var3_7) {
                                    com.mg.sq.a.a(com.mg.sq.a.s().d());
                                    v.Q = var1_2.k.a();
                                    co.b().g();
                                } else {
                                    co.b().d();
                                }
                            }
                            var2_4.a(var4_8, new byte[]{var6_9});
                            break;
                        }
                        case 122: {
                            if (go.k != null && var3_7 != go.k.aa) {
                                ks.a().c(var3_7);
                                com.mg.sq.a.s().a((String)null, (il)null);
                            }
                            var2_4.a(var4_8, new byte[]{var6_9});
                            break;
                        }
                        default: {
                            var2_4.a(var4_8, new byte[]{var6_9});
                        }
                    }
lbl52:
                    // 5 sources

                    od.h(var4_8, var6_9);
lbl53:
                    // 2 sources

                    ++var5_5;
lbl54:
                    // 2 sources

                    var3_6 = var1_2;
                } while (var5_5 < var3_6.a.s().length);
                var2_4.a();
                ag.b().f(this.c);
            }
        }
    }

    public final void a(aq aq2, boolean bl2) {
        if (aq2.equals(this.d)) {
            if (bl2) {
                this.k.h(50);
                return;
            }
            this.k.h(0);
        }
    }

    public final void a(aq aq2, int n2) {
        boolean bl2;
        if (aq2.equals(this.k) && (bl2 = n2 > 0) != this.d.a()) {
            this.d.e(bl2);
        }
    }
}

