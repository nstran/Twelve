/*
 * Decompiled with CFR 0.152.
 */
import com.mg.sq.a;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;

public final class ob
extends nx
implements bj,
bl,
bm {
    private boolean b;
    private int c;
    private gj d;
    private gl k;
    private String[][] l = new String[][]{{"N\u00fat 0:", "He he"}, {"N\u00fat 1:", "Hi hi"}, {"N\u00fat 2:", "Ch\u1ebft n\u00e8!"}, {"N\u00fat 3:", "Y\u1ebfu m\u00e0 ra gi\u00f3!"}, {"N\u00fat 4:", "T\u01b0\u1edfng b\u1edf h\u1ea3!"}, {"N\u00fat 5:", "Ti\u1ebfp chi\u00eau n\u00e8!"}, {"N\u00fat 6:", "\u0102n \u0111\u00f2n n\u00e8!"}, {"N\u00fat 7:", "Xem \u0111\u00e2y!"}, {"N\u00fat 8:", "Tha m\u1ea1ng!! hix hix"}, {"N\u00fat 9:", "Kh\u00f4ng ch\u01b0\u1edfng nha!"}};

    public ob(int n2) {
        super((byte)0);
        int n3;
        Object object;
        try {
            this.c = n2;
            this.a(this);
            this.a(new be());
            bh bh2 = new bh("L\u01b0u", 1);
            bj bj2 = this;
            bj2.a(bh2, true);
            bh2 = new bh("H\u1ee7y", 0);
            bj2 = this;
            bj2.b(bh2, true);
            this.g -= be.a;
            this.j(true);
            new cx(10, 22);
            int n4 = this.i();
            object = new gg("Game");
            ((au)object).a_(-1919);
            ((au)object).d(n4);
            ((gg)object).d(5, 5);
            this.a((au)object);
            object = null;
            if (gr.j != null) {
                object = new gj("Ch\u1eb7n khi\u00eau chi\u1ebfn");
                ((gj)object).a((byte)1);
                ((au)object).a_(122);
                ((gj)object).a(0, 0, n4, 20);
                ((gj)object).a(this);
                ((gj)object).e(gr.j.ac);
                this.a((au)object);
                object = new gj("Ch\u1eb7n giao d\u1ecbch");
                ((gj)object).a((byte)1);
                ((au)object).a_(139);
                ((gj)object).a(0, 0, n4, 20);
                ((gj)object).a(this);
                ((gj)object).e(gr.j.ac);
                this.a((au)object);
            }
            object = new gj("T\u1ef1 \u0111\u1ed9ng \u0111\u0103ng nh\u1eadp Ola");
            ((gj)object).a((byte)1);
            ((au)object).a_(138);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(gp.k);
            this.a((au)object);
            object = new gj("Hi\u1ec7u \u1ee9ng \u0111\u1ed3 h\u1ecda");
            ((gj)object).a((byte)1);
            ((au)object).a_(137);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(!gp.r);
            this.a((au)object);
            object = new gj("Hi\u1ec7u \u1ee9ng \u00e2m thanh");
            ((gj)object).a((byte)1);
            ((au)object).a_(123);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(z.L);
            this.a((au)object);
            this.d = new gj("Nh\u1ea1c n\u1ec1n");
            this.d.a((byte)1);
            this.d.a_(124);
            this.d.a(0, 0, n4, 20);
            this.a(this.d);
            this.k = new gl("\u00c2m l\u01b0\u1ee3ng");
            this.k.a_(125);
            this.k.d(n4);
            this.k.h(z.N);
            this.k.a(this);
            this.d.a(this);
            this.a(this.k);
            if (!z.K) {
                this.d.e(false);
                z.N = 0;
            }
            object = new gj("B\u00e1o rung");
            ((gj)object).a((byte)1);
            ((au)object).a_(126);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(z.M);
            this.a((au)object);
            object = new fe("C\u00e2u tr\u1ea3 l\u1eddi t\u1ef1 \u0111\u1ed9ng ");
            ((au)object).a_(136);
            ((fe)object).a(0, 0, n4, 20);
            bj2 = null;
            n3 = 0;
            while (n3 < this.l.length) {
                bj2 = new fk(this.l[n3][0], this.l[n3][1]);
                ((fe)object).a((fk)bj2);
                ++n3;
            }
            this.a((au)object);
            object = new gg("Ola");
            ((au)object).a_(-1919);
            ((au)object).d(n4);
            ((gg)object).d(12, 5);
            this.a((au)object);
            object = new gj("H\u1ed7 tr\u1ee3 g\u00f5 ti\u1ebfng Vi\u1ec7t c\u00f3 d\u1ea5u");
            ((gj)object).a((byte)1);
            ((au)object).a_(127);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(z.a);
            this.a((au)object);
            object = new gj("\u0110\u0103ng nh\u1eadp \u1ea9n");
            ((gj)object).a((byte)1);
            ((au)object).a_(132);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(!gp.d);
            this.a((au)object);
            object = new gj("Hi\u1ec7n danh s\u00e1ch nick \u1ea9n");
            ((gj)object).a((byte)1);
            ((au)object).a_(133);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(gp.c);
            this.a((au)object);
            object = new gj("L\u01b0u th\u00f4ng \u0111i\u1ec7p");
            ((gj)object).a((byte)1);
            ((au)object).a_(128);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(gp.g);
            this.a((au)object);
            object = new gj("L\u01b0u danh s\u00e1ch nick");
            ((gj)object).a((byte)1);
            ((au)object).a_(129);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(gp.h);
            this.a((au)object);
            object = new gj("L\u01b0u danh s\u00e1ch chat");
            ((gj)object).a((byte)1);
            ((au)object).a_(131);
            ((gj)object).a(0, 0, n4, 20);
            ((gj)object).a(this);
            ((gj)object).e(gp.i);
            this.a((au)object);
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        this.g(1);
        ob ob2 = this;
        object = cv.a;
        boolean bl2 = false;
        n3 = 0;
        ob ob3 = ob2;
        int n5 = ob3.a.s().length;
        while (n3 < n5) {
            int n6 = ob2.f(n3).b();
            if (n6 > 0 && ((y)object).c(n6)) {
                au au2;
                Object object2 = ((y)object).a(n6);
                if (ob2.f(n3) instanceof gl) {
                    au2 = (ge)ob2.f(n3);
                    ((ge)au2).h(object2[0]);
                } else if (ob2.f(n3) instanceof fe) {
                    au2 = (fe)ob2.f(n3);
                    try {
                        ((fe)au2).a((byte[])object2);
                    }
                    catch (IOException iOException) {
                        object2 = iOException;
                        iOException.printStackTrace();
                    }
                } else if (ob2.f(n3) instanceof gj) {
                    au2 = (gj)ob2.f(n3);
                    ((gj)au2).e(object2[0] == 0);
                }
            }
            ++n3;
        }
        this.b = z.K;
    }

    public static void d() {
        y y2 = cv.a;
        int n2 = 0;
        int n3 = 0;
        while (n3 < 17) {
            n2 = n3 + 123;
            if (y2.c(n2)) {
                byte[] byArray = y2.a(n2);
                ob.h(n2, byArray[0]);
            }
            ++n3;
        }
        if (y2.c(140)) {
            gp.m = false;
        }
        if (y2.c(142)) {
            gp.n = false;
        }
        if (y2.c(143)) {
            gp.o = false;
        }
        if (y2.c(144)) {
            gp.p = false;
        }
        if (y2.c(145)) {
            gp.q = false;
        }
    }

    private static void h(int n2, int n3) {
        switch (n2) {
            case 123: {
                z.L = n3 != 1;
                return;
            }
            case 137: {
                gp.r = n3 != 0;
                return;
            }
            case 124: {
                z.K = n3 != 1;
                return;
            }
            case 126: {
                z.M = n3 != 1;
                return;
            }
            case 138: {
                gp.k = n3 != 1;
                return;
            }
            case 139: {
                gp.l = n3 != 1;
                return;
            }
            case 127: {
                z.a = n3 != 1;
                if (!z.a || !z.ab) break;
                z.b = true;
                return;
            }
            case 129: {
                gp.h = n3 != 1;
                return;
            }
            case 128: {
                gp.g = n3 != 1;
                return;
            }
            case 130: {
                return;
            }
            case 131: {
                gp.i = n3 != 1;
                return;
            }
            case 125: {
                z.N = n3;
                return;
            }
            case 132: {
                boolean bl2 = gp.d = n3 != 0;
                if (com.mg.sq.a.k == null || !com.mg.sq.a.m) break;
                com.mg.sq.a.k.I();
                return;
            }
            case 133: {
                boolean bl3 = gp.c = n3 != 1;
                if (com.mg.sq.a.k == null || !com.mg.sq.a.m) break;
                com.mg.sq.a.k.J();
            }
        }
    }

    public static String[] e() {
        Object object = new String[][]{{"N\u00fat 0:", "He he"}, {"N\u00fat 1:", "Hi hi"}, {"N\u00fat 2:", "Ch\u1ebft n\u00e8!"}, {"N\u00fat 3:", "Y\u1ebfu m\u00e0 ra gi\u00f3!"}, {"N\u00fat 4:", "T\u01b0\u1edfng b\u1edf h\u1ea3!"}, {"N\u00fat 5:", "Ti\u1ebfp chi\u00eau n\u00e8!"}, {"N\u00fat 6:", "\u0102n \u0111\u00f2n n\u00e8!"}, {"N\u00fat 7:", "Xem \u0111\u00e2y!"}, {"N\u00fat 8:", "Tha m\u1ea1ng!! hix hix"}, {"N\u00fat 9:", "Kh\u00f4ng ch\u01b0\u1edfng nha!"}};
        Object[] objectArray = null;
        if (cv.a.c(136)) {
            Object[] objectArray2 = cv.a.a(136);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])objectArray2);
            object = new DataInputStream(byteArrayInputStream);
            objectArray2 = new String[10];
            try {
                int n2 = 0;
                while (n2 < objectArray2.length) {
                    objectArray2[n2] = (byte)((DataInputStream)object).readUTF();
                    ++n2;
                }
                objectArray = objectArray2;
                ((FilterInputStream)object).close();
                byteArrayInputStream.close();
            }
            catch (IOException iOException) {
                IOException iOException2 = iOException;
                iOException.printStackTrace();
            }
        } else {
            int n3 = ((String[][])object).length;
            objectArray = new String[n3];
            int n4 = 0;
            while (n4 < n3) {
                objectArray[n4] = object[n4][1];
                ++n4;
            }
        }
        return objectArray;
    }

    public static final void f() {
        byte[] byArray;
        y y2 = cv.a;
        if (gp.d) {
            byte[] byArray2 = new byte[1];
            byArray = byArray2;
            byArray2[0] = 1;
        } else {
            byArray = new byte[1];
        }
        y2.b(132, byArray);
    }

    public static void h(int n2) {
        y y2 = cv.a;
        y2.b(n2, new byte[]{1});
    }

    /*
     * Unable to fully structure code
     */
    public final void d(int var1_1, int var2_3) {
        switch (var2_3) {
            case 0: {
                ak.b().f(this.c);
                return;
            }
            case 1: {
                var1_2 = this;
                var2_4 = cv.a;
                var3_5 = null;
                var4_7 = 0;
                var5_8 = 0;
                if (true) ** GOTO lbl57
                do {
                    var3_5 = var1_2.f(var5_8);
                    var4_7 = var3_5.b();
                    var6_9 = 0;
                    if (!(var3_5 instanceof ge)) ** GOTO lbl22
                    var3_5 = (ge)var3_5;
                    var6_9 = (byte)var3_5.a();
                    var2_4.a(var4_7, new byte[]{var6_9});
                    ** GOTO lbl55
lbl22:
                    // 1 sources

                    if (!(var3_5 instanceof fe)) ** GOTO lbl27
                    var3_5 = (fe)var3_5;
                    var2_4.a(var4_7, var3_5.a());
                    ** GOTO lbl55
lbl27:
                    // 1 sources

                    if (!(var3_5 instanceof gj)) ** GOTO lbl56
                    var3_6 = (var3_5 = (gj)var3_5).a();
                    var6_9 = var3_6 != false ? 0 : 1;
                    switch (var4_7) {
                        case 124: {
                            if (var3_6 != var1_2.b) {
                                var1_2.b = var3_6;
                                z.K = var3_6;
                                if (var3_6) {
                                    com.mg.sq.a.a(com.mg.sq.a.q().d());
                                    z.N = var1_2.k.a();
                                    cr.b().g();
                                } else {
                                    cr.b().d();
                                }
                            }
                            var2_4.a(var4_7, new byte[]{var6_9});
                            break;
                        }
                        case 122: {
                            if (gr.j != null && var3_6 != gr.j.ac) {
                                kq.a().c(var3_6);
                                com.mg.sq.a.a(null, null);
                            }
                            var2_4.a(var4_7, new byte[]{var6_9});
                            break;
                        }
                        default: {
                            var2_4.a(var4_7, new byte[]{var6_9});
                        }
                    }
lbl55:
                    // 5 sources

                    ob.h(var4_7, var6_9);
lbl56:
                    // 2 sources

                    ++var5_8;
lbl57:
                    // 2 sources

                    var3_5 = var1_2;
                } while (var5_8 < var3_5.a.s().length);
                var2_4.a();
                ak.b().f(this.c);
            }
        }
    }

    public final void a(au au2, boolean bl2) {
        if (au2.equals(this.d)) {
            if (bl2) {
                this.k.h(50);
                return;
            }
            this.k.h(0);
        }
    }

    public final void a(au au2, int n2) {
        boolean bl2;
        if (au2.equals(this.k) && (bl2 = n2 > 0) != this.d.a()) {
            this.d.e(bl2);
        }
    }
}

