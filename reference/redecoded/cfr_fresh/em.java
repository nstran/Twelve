/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

final class em
implements Runnable {
    private dv a;
    private ed b;
    private boolean c = false;
    private byte[] d;

    public em(InputStream inputStream) {
        this.b = new ed(inputStream);
        this.c = false;
        new Thread(this).start();
    }

    public final void run() {
        int n2 = 10;
        this.c = false;
        block80: while (!this.c) {
            Object object;
            try {
                object = this;
                object = ((em)object).b == null ? null : ((em)object).b.a();
                if (object == null) {
                    if (--n2 <= 0) {
                        this.c = true;
                        if (du.a().c) {
                            du.a().d();
                        }
                    } else {
                        try {
                            Thread.sleep(3000L);
                        }
                        catch (Throwable throwable) {}
                    }
                } else {
                    n2 = 10;
                }
            }
            catch (Throwable throwable) {
                if (--n2 <= 0) {
                    this.c = true;
                    if (du.a().c) {
                        du.a().d();
                    }
                } else {
                    try {
                        Thread.sleep(3000L);
                    }
                    catch (Throwable throwable2) {}
                }
                object = null;
            }
            Object object2 = object;
            object = this;
            if (object2 == null) continue;
            try {
                if (((em)object).a == null) continue;
                int n3 = ((dy)object2).b;
                switch (n3) {
                    case 0: {
                        byte by2 = ((dy)object2).a(((dy)object2).b((short)0, 0), (short)0);
                        n3 = by2;
                        if (by2 == 9) continue block80;
                        String string = ((dy)object2).b((short)8);
                        byte by3 = ((dy)object2).a(((dy)object2).b((short)255, 0), (short)0);
                        String string2 = ((dy)object2).b((short)30);
                        if (!i.a(string2)) {
                            em.a(string2);
                        }
                        ((em)object).a.a(n3, string, by3);
                        break;
                    }
                    case 95: {
                        object = object2;
                        byte[] byArray = ((dy)object).a((short)42);
                        if (byArray != null) {
                            String string = "";
                            int n4 = m.a(byArray[0]);
                            string = String.valueOf(string) + String.valueOf(n4);
                            n4 = m.a(byArray[1]);
                            string = String.valueOf(string) + ".";
                            string = String.valueOf(string) + String.valueOf(n4);
                            n4 = m.a(byArray[2]);
                            string = String.valueOf(string) + ".";
                            string = String.valueOf(string) + String.valueOf(n4);
                            n4 = m.a(byArray[3]);
                            string = String.valueOf(string) + ".";
                            string = String.valueOf(string) + String.valueOf(n4);
                            du.a().a(string);
                            break;
                        }
                        du.a().a((String)null);
                        break;
                    }
                    case 96: {
                        byte[] byArray = ((dy)object2).a((short)11);
                        du.a().m = byArray;
                        du.a().a = true;
                        if (du.a().b) {
                            du.a().e();
                            break;
                        }
                        if (((em)object).a == null) continue block80;
                        ((em)object).a.h_();
                        break;
                    }
                    case 97: {
                        ((dy)object2).b((short)4);
                        ((dy)object2).b((short)22);
                        ((dy)object2).a(((dy)object2).b((short)12, 0), (short)0);
                        ((dy)object2).a(((dy)object2).b((short)9, 0), 0L);
                        ((dy)object2).b((short)13);
                        du.a().f();
                        du.a().c = true;
                        if (du.a().b) {
                            du.a().b = false;
                            dv cfr_ignored_0 = ((em)object).a;
                            du.a().j();
                            if (du.a().i != 2 || i.a(du.a().h)) continue block80;
                            du.a().c(du.a().h);
                            break;
                        }
                        ((em)object).a.g_();
                        break;
                    }
                    case 9: {
                        n3 = ((dy)object2).a(((dy)object2).b((short)39, 0), 0);
                        byte by4 = ((dy)object2).a(((dy)object2).b((short)53, 0), (byte)0);
                        dt[] dtArray = em.a((dy)object2, null, true, by4);
                        ((em)object).a.a(dtArray, n3);
                        break;
                    }
                    case 10: {
                        super.C((dy)object2);
                        break;
                    }
                    case 11: {
                        ((dy)object2).a(((dy)object2).b((short)255, 0), (short)0);
                        eo eo2 = new eo();
                        ((dy)object2).b((short)7);
                        ((dy)object2).b((short)22);
                        eo2.a = ((dy)object2).b((short)13);
                        if (i.a(eo2.a)) {
                            eo2.a = null;
                        }
                        ((dy)object2).a((short)23);
                        ((dy)object2).a(((dy)object2).b((short)45, 0), (short)0);
                        ((dy)object2).a(((dy)object2).b((short)119, 0), 0);
                        eo2.b = ((dy)object2).b((short)109);
                        if (eo2.b != null) {
                            ((dy)object2).a(((dy)object2).b((short)114, 0), (short)0);
                        } else {
                            ((dy)object2).b((short)110);
                        }
                        ((dy)object2).b((short)111);
                        ((dy)object2).b((short)4);
                        ((dy)object2).a(((dy)object2).b((short)58, 0), 0L);
                        ((dy)object2).a(((dy)object2).b((short)124, 0), 0);
                        ((dy)object2).a(((dy)object2).b((short)125, 0), 0);
                        ((dy)object2).b((short)112);
                        ((dy)object2).b((short)113);
                        ((dy)object2).a(((dy)object2).b((short)126, 0), 0);
                        ((dy)object2).a(((dy)object2).b((short)127, 0), 0);
                        ((dy)object2).a(((dy)object2).b((short)128, 0), 0);
                        ((dy)object2).a(((dy)object2).b((short)219, 0), (byte)0);
                        ((dy)object2).a(((dy)object2).b((short)12, 0), (byte)0);
                        dv cfr_ignored_1 = ((em)object).a;
                        break;
                    }
                    case 12: {
                        dv cfr_ignored_2 = ((em)object).a;
                        break;
                    }
                    case 77: {
                        n3 = ((dy)object2).a(((dy)object2).b((short)45, 0), (short)0);
                        long l2 = ((dy)object2).a(((dy)object2).b((short)9, 0), 0L);
                        int n5 = (int)(l2 / 3600000L);
                        int n6 = n5 / 24;
                        if (n5 % 24 != 0) {
                            // empty if block
                        }
                        du.a().j = (short)n3;
                        du.a().k = ++n6;
                        dv cfr_ignored_3 = ((em)object).a;
                        break;
                    }
                    case 79: {
                        boolean bl2;
                        String string = ((dy)object2).b((short)7);
                        String string3 = ((dy)object2).b((short)109);
                        boolean bl3 = bl2 = ((dy)object2).a(((dy)object2).b((short)114, 0), (byte)0) == 1;
                        if (string == null) continue block80;
                        if (string3 != null) {
                            if (bl2) {
                                dv cfr_ignored_4 = ((em)object).a;
                                break;
                            }
                            dv cfr_ignored_5 = ((em)object).a;
                            break;
                        }
                        if (bl2) {
                            dv cfr_ignored_6 = ((em)object).a;
                            break;
                        }
                        dv cfr_ignored_7 = ((em)object).a;
                        break;
                    }
                    case 14: {
                        super.a((dy)object2);
                        break;
                    }
                    case 21: {
                        super.b((dy)object2);
                        break;
                    }
                    case 23: {
                        super.c((dy)object2);
                        break;
                    }
                    case 25: {
                        object2 = du.a();
                        du.a().g = null;
                        ((du)object2).l = j.a(((du)object2).m, ((du)object2).g);
                        dv cfr_ignored_8 = ((em)object).a;
                        break;
                    }
                    case 6: {
                        super.d((dy)object2);
                        break;
                    }
                    case 115: {
                        ep[] epArray;
                        byte[] byArray = ((dy)object2).a((short)214);
                        if (byArray == null) {
                            ((em)object).d = null;
                            dv cfr_ignored_9 = ((em)object).a;
                            break;
                        }
                        int n7 = m.a(byArray[0], byArray[1]);
                        if (du.a().t != n7) continue block80;
                        byArray = ((dy)object2).a((short)213);
                        if (byArray != null) {
                            du.a().s = m.a(byArray);
                            if (du.a().s <= 0) {
                                du.a().s = 1;
                            }
                            byArray = ((dy)object2).a((short)37);
                            int n8 = m.c(byArray);
                            ((em)object).d = new byte[n8];
                            du.a().r = n8;
                        }
                        int n9 = du.a().s;
                        byte[] byArray2 = ((dy)object2).a((short)23);
                        System.arraycopy(byArray2, 0, ((em)object).d, du.a().q, byArray2.length);
                        du.a().q += byArray2.length;
                        if (n7 >= n9 - 1) {
                            int n10 = ((dy)object2).a(((dy)object2).b((short)218, 0), 0);
                            epArray = ep.a(((em)object).d);
                            ((em)object).a.a(n10, epArray, ((em)object).d);
                            ((em)object).d = null;
                            break;
                        }
                        du.a().c(n7);
                        break;
                    }
                    case 22: {
                        du.a().b();
                        ((em)object).a.f_();
                        break;
                    }
                    case 42: {
                        du.a().h();
                        break;
                    }
                    case 50: {
                        ((dy)object2).a(((dy)object2).b((short)255, 0), (short)0);
                        String string = ((dy)object2).b((short)7);
                        if (i.a(string)) {
                            ((dy)object2).b((short)109);
                            dv cfr_ignored_10 = ((em)object).a;
                            break;
                        }
                        ((dy)object2).b((short)109);
                        ((dy)object2).b((short)110);
                        ((dy)object2).b((short)111);
                        ((dy)object2).a(((dy)object2).b((short)66, 0), (short)4);
                        ((dy)object2).a(((dy)object2).b((short)124, 0), 0);
                        ((dy)object2).a(((dy)object2).b((short)125, 0), 0);
                        ((dy)object2).a(((dy)object2).b((short)126, 0), 0);
                        ((dy)object2).a(((dy)object2).b((short)130, 0), 0L);
                        dv cfr_ignored_11 = ((em)object).a;
                        break;
                    }
                    case 67: {
                        super.e((dy)object2);
                        break;
                    }
                    case 15: {
                        super.f((dy)object2);
                        break;
                    }
                    case 17: {
                        ((dy)object2).b((short)21);
                        dv cfr_ignored_12 = ((em)object).a;
                        break;
                    }
                    case 54: {
                        super.g((dy)object2);
                        break;
                    }
                    case 103: {
                        super.h((dy)object2);
                        break;
                    }
                    case 116: {
                        super.i((dy)object2);
                        break;
                    }
                    case 124: {
                        super.j((dy)object2);
                        break;
                    }
                    case 125: {
                        String string = ((dy)object2).b((short)7);
                        byte by5 = ((dy)object2).a(((dy)object2).b((short)45, 0), (byte)0);
                        long l3 = ((dy)object2).a(((dy)object2).b((short)9, 0), 0L);
                        long l4 = ((dy)object2).a(((dy)object2).b((short)59, 0), -1L);
                        if (string.toLowerCase().equals(du.a().f.toLowerCase())) {
                            ((em)object).a.a(by5, l3, l4);
                            break;
                        }
                        if (l4 >= 0L) {
                            dv cfr_ignored_13 = ((em)object).a;
                            break;
                        }
                        ((em)object).a.a(string, (short)by5, l3);
                        break;
                    }
                    case 109: {
                        super.k((dy)object2);
                        break;
                    }
                    case 117: {
                        super.l((dy)object2);
                        break;
                    }
                    case 114: {
                        super.m((dy)object2);
                        break;
                    }
                    case 39: {
                        String string = ((dy)object2).b((short)7);
                        String string4 = ((dy)object2).b((short)24);
                        ((dy)object2).a(((dy)object2).b((short)45, 0), (short)0);
                        long l5 = ((dy)object2).a(((dy)object2).b((short)9, 0), 0L);
                        if (l5 == 0L) {
                            l5 = System.currentTimeMillis();
                        }
                        ee ee2 = new ee();
                        new ee().a = string;
                        ee2.a(string4);
                        ee2.d = l5;
                        if (du.a().i == 2 && (object2 = ((dy)object2).b((short)109)) != null) {
                            du.a().b(string, (String)object2);
                        }
                        ((em)object).a.a(string, ee2);
                        break;
                    }
                    case 104: {
                        ((dy)object2).b((short)7);
                        ((dy)object2).b((short)8);
                        dv cfr_ignored_14 = ((em)object).a;
                        break;
                    }
                    case 105: {
                        ((dy)object2).b((short)7);
                        ((dy)object2).a((short)66);
                        dv cfr_ignored_15 = ((em)object).a;
                        break;
                    }
                    case 65: {
                        ((dy)object2).b((short)7);
                        String string = ((dy)object2).b((short)30);
                        ((em)object).a.a(string);
                        break;
                    }
                    case 66: {
                        String string = ((dy)object2).b((short)28);
                        long l6 = ((dy)object2).a(((dy)object2).b((short)130, 0), 0L);
                        int n11 = ((dy)object2).a(((dy)object2).b((short)124, 0), 0);
                        ((em)object).a.a(string, l6, n11);
                        break;
                    }
                    case 129: {
                        ((dy)object2).a(((dy)object2).b((short)20, 0), (byte)0);
                        dv cfr_ignored_16 = ((em)object).a;
                        break;
                    }
                    case 133: {
                        super.n((dy)object2);
                        break;
                    }
                    case 136: {
                        String string = ((dy)object2).b((short)4);
                        String[] stringArray = i.b(string, ";");
                        ((em)object).a.a(stringArray);
                        break;
                    }
                    case 137: {
                        super.o((dy)object2);
                        break;
                    }
                    case 138: {
                        super.p((dy)object2);
                        break;
                    }
                    case 140: {
                        dt[] dtArray = em.a((dy)object2, null, true, (short)0);
                        ((em)object).a.a(dtArray);
                        break;
                    }
                    case 193: {
                        super.q((dy)object2);
                        break;
                    }
                    case 194: {
                        ((dy)object2).a(((dy)object2).b((short)255, 0), (short)0);
                        ((dy)object2).a(((dy)object2).b((short)72, 0), 0L);
                        String string = ((dy)object2).b((short)29);
                        if (string != null) {
                            i.b(string, ";");
                        }
                        dv cfr_ignored_17 = ((em)object).a;
                        break;
                    }
                    case 199: {
                        ((dy)object2).a(((dy)object2).b((short)255, 0), (short)0);
                        ((dy)object2).b((short)109);
                        ((dy)object2).b((short)110);
                        ((dy)object2).a((short)23);
                        dv cfr_ignored_18 = ((em)object).a;
                        break;
                    }
                    case 147: {
                        super.r((dy)object2);
                        break;
                    }
                    case 148: {
                        ((dy)object2).a(((dy)object2).b((short)114, 0), (short)0);
                        dv cfr_ignored_19 = ((em)object).a;
                        break;
                    }
                    case 158: {
                        ((dy)object2).a(((dy)object2).b((short)255, 0), (short)0);
                        ((dy)object2).b((short)129);
                        ((dy)object2).b((short)22);
                        ((dy)object2).b((short)13);
                        ((dy)object2).b((short)24);
                        dv cfr_ignored_20 = ((em)object).a;
                        break;
                    }
                    case 159: {
                        ((dy)object2).b((short)129);
                        ((dy)object2).b((short)7);
                        dv cfr_ignored_21 = ((em)object).a;
                        break;
                    }
                    case 160: {
                        ((dy)object2).b((short)129);
                        ((dy)object2).b((short)22);
                        ((dy)object2).b((short)13);
                        ((dy)object2).b((short)24);
                        dv cfr_ignored_22 = ((em)object).a;
                        break;
                    }
                    case 162: {
                        ((dy)object2).b((short)129);
                        String string = ((dy)object2).b((short)7);
                        String string5 = ((dy)object2).b((short)8);
                        byte by6 = ((dy)object2).a(((dy)object2).b((short)220, 0), (short)0);
                        ((dy)object2).a(((dy)object2).b((short)45, 0), (short)0);
                        ep[] epArray = new ef();
                        epArray.a(string);
                        epArray.b(string5);
                        epArray.a(by6);
                        if (du.a().i == 2 && (object2 = ((dy)object2).b((short)109)) != null) {
                            du.a().b(string, (String)object2);
                        }
                        dv cfr_ignored_23 = ((em)object).a;
                        break;
                    }
                    case 163: {
                        String string;
                        ((dy)object2).b((short)129);
                        String string6 = ((dy)object2).b((short)7);
                        ((dy)object2).b((short)24);
                        if (du.a().i == 2 && (string = ((dy)object2).b((short)109)) != null) {
                            du.a().b(string6, string);
                        }
                        dv cfr_ignored_24 = ((em)object).a;
                        break;
                    }
                    case 164: {
                        super.s((dy)object2);
                        break;
                    }
                    case 165: {
                        super.t((dy)object2);
                        break;
                    }
                    case 166: {
                        ((dy)object2).b((short)111);
                        ((dy)object2).b((short)7);
                        ((dy)object2).b((short)110);
                        ((dy)object2).a(((dy)object2).b((short)45, 0), (short)0);
                        ((dy)object2).a(((dy)object2).b((short)114, 0), (short)0);
                        ((dy)object2).a(((dy)object2).b((short)130, 0), 0L);
                        ((dy)object2).a(((dy)object2).b((short)59, 0), 0L);
                        dv cfr_ignored_25 = ((em)object).a;
                        break;
                    }
                    case 167: {
                        ((dy)object2).b((short)7);
                        byte by7 = ((dy)object2).a(((dy)object2).b((short)114, 0), (short)0);
                        if (by7 == 1) {
                            dv cfr_ignored_26 = ((em)object).a;
                            break;
                        }
                        dv cfr_ignored_27 = ((em)object).a;
                        break;
                    }
                    case 168: {
                        ((dy)object2).a(((dy)object2).b((short)124, 0), 0);
                        dv cfr_ignored_28 = ((em)object).a;
                        break;
                    }
                    case 169: {
                        super.u((dy)object2);
                        break;
                    }
                    case 171: {
                        super.v((dy)object2);
                        break;
                    }
                    case 175: {
                        ((dy)object2).a(((dy)object2).b((short)255, 0), (short)0);
                        ((dy)object2).b((short)110);
                        ((dy)object2).b((short)28);
                        ((dy)object2).b((short)8);
                        dv cfr_ignored_29 = ((em)object).a;
                        break;
                    }
                    case 35: {
                        super.w((dy)object2);
                        break;
                    }
                    case 113: {
                        du.a();
                        break;
                    }
                    case 100: {
                        super.x((dy)object2);
                        break;
                    }
                    case 101: {
                        super.y((dy)object2);
                        break;
                    }
                    case 102: {
                        super.z((dy)object2);
                        break;
                    }
                    case 20: {
                        ((dy)object2).b((short)21);
                        ((dy)object2).b((short)26);
                        dv cfr_ignored_30 = ((em)object).a;
                        break;
                    }
                    case 18: {
                        ((dy)object2).b((short)21);
                        dv cfr_ignored_31 = ((em)object).a;
                        break;
                    }
                    case 19: {
                        ((dy)object2).b((short)7);
                        ((dy)object2).b((short)21);
                        dv cfr_ignored_32 = ((em)object).a;
                        break;
                    }
                    case 92: {
                        super.A((dy)object2);
                        break;
                    }
                    case 82: {
                        super.B((dy)object2);
                        break;
                    }
                    case 128: {
                        super.D((dy)object2);
                        break;
                    }
                }
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                exception.printStackTrace();
            }
        }
    }

    /*
     * Unable to fully structure code
     * Could not resolve type clashes
     */
    private void a(dy var1_1) {
        block72: {
            block71: {
                var2_2 = var1_1.a((short)53);
                var3_3 = 0;
                if (var2_2 != null) {
                    var3_3 = var2_2[0];
                }
                var2_2 = var1_1.b((short)7);
                var4_4 /* !! */  = var1_1.b((short)8);
                var5_5 = var1_1.a(var1_1.b((short)220, 0), (short)0);
                var1_1.a(var1_1.b((short)45, 0), (short)0);
                if (var4_4 /* !! */  == null) {
                    var4_4 /* !! */  = "";
                }
                var6_6 = new ef();
                var7_7 = false;
                if (var2_2.toLowerCase().equals("ola".toLowerCase())) {
                    var7_7 = true;
                    var8_8 = i.a((String)var4_4 /* !! */ , " ", 3, false);
                    if (var8_8 != null && var8_8.length == 3 && var8_8[0].toLowerCase().equals("LOCK".toLowerCase())) {
                        try {
                            Long.parseLong(var8_8[1]);
                            if (this.a != null) {
                            }
                            return;
                        }
                        catch (Exception v0) {}
                    }
                } else if (var2_2.length() <= 3) {
                    var7_7 = true;
                    var8_8 = i.a((String)var4_4 /* !! */ , "|", 2, false);
                    if (var8_8 != null && var8_8.length == 2 && (var9_13 = em.a(var8_8[1])) != null && ((eb[])var9_13).length > 0) {
                        var4_4 /* !! */  = var8_8[0];
                        var6_6.a((eb[])var9_13);
                    }
                }
                var8_9 = true;
                var8_10 = null;
                var8_10 = var4_4 /* !! */ ;
                var12_14 = true;
                var8_11 = false;
                var19_15 = 0L;
                var11_21 = null;
                var10_23 = var8_10;
                var9_13 = du.a().f;
                var8_12 = null;
                if (!i.b((String)var10_23)) break block71;
                v1 = null;
                break block72;
            }
            var8_12 = i.b((String)var10_23, " ");
            var10_23 = null;
            var13_24 = 0;
            if (var11_21 != null) {
                var10_23 = new eb[30];
                var14_25 /* !! */  = new ee();
                var14_25 /* !! */ .a(var11_21);
                switch (var14_25 /* !! */ .f) {
                    case 1: {
                        var10_23[0] = new eb("openphoto");
                        var10_23[0].e("Xem \u1ea3nh");
                        ++var13_24;
                        break;
                    }
                    case 3: {
                        var10_23[0] = new eb("openaudio");
                        var10_23[0].e("M\u1edf \u00e2m thanh");
                        ++var13_24;
                        break;
                    }
                    case 2: {
                        var10_23[0] = new eb("openvideo");
                        var10_23[0].e("M\u1edf Video");
                        ++var13_24;
                        break;
                    }
                    default: {
                        var10_23[0] = new eb("wap");
                        var10_23[0].e("Xem WAP");
                        var10_23[0].a(var14_25 /* !! */ .b);
                        ++var13_24;
                    }
                }
            }
            if (!var9_13.equals(du.a().f.toLowerCase())) {
                if (var10_23 == null) {
                    var10_23 = new eb[30];
                }
                var10_23[var13_24] = new eb("chatto");
                var14_25 /* !! */  = var9_13;
                if (var9_13.length() >= 14) {
                    var14_25 /* !! */  = var14_25 /* !! */ .substring(0, 11);
                    var14_25 /* !! */  = String.valueOf(var14_25 /* !! */ ) + "...";
                }
                var10_23[var13_24].e("Chat \"" + (String)var14_25 /* !! */  + "\"");
                var10_23[var13_24].a((String)var9_13);
                var10_23[++var13_24] = new eb("replyto");
                var10_23[var13_24].e("Tr\u1ea3 l\u1eddi \"" + (String)var14_25 /* !! */  + "\"");
                var10_23[var13_24].a((String)var9_13);
                var10_23[++var13_24] = new eb("viewme");
                var10_23[var13_24].e("Xem \"" + (String)var14_25 /* !! */  + "\"");
                var10_23[var13_24].a((String)var9_13);
                ++var13_24;
            }
            var14_26 = 1;
            var15_27 = 1;
            var16_28 = 0;
            while (var16_28 < var8_12.length && var13_24 < 30) {
                block74: {
                    block77: {
                        block76: {
                            block75: {
                                block73: {
                                    if (!var8_12[var16_28].startsWith("@") || var8_12[var16_28].length() <= 1 || var8_12[var16_28].toLowerCase().equals("@" + (String)var9_13)) break block73;
                                    var17_30 = var8_12[var16_28].substring(1);
                                    var17_30 = var17_30.trim();
                                    var18_31 = 0;
                                    var19_16 = var17_30.length();
                                    while (var18_31 < var19_16) {
                                        if (!(var17_30.charAt(var18_31) >= 'a' && var17_30.charAt(var18_31) <= 'z' || var17_30.charAt(var18_31) >= 'A' && var17_30.charAt(var18_31) <= 'Z' || var17_30.charAt(var18_31) >= '0' && var17_30.charAt(var18_31) <= '9' || var17_30.charAt(var18_31) == '.' || var17_30.charAt(var18_31) == '_')) {
                                            var17_30 = var17_30.substring(0, var18_31);
                                            break;
                                        }
                                        ++var18_31;
                                    }
                                    if (var17_30.length() > 0 && !var17_30.toLowerCase().equals(var9_13.toLowerCase())) {
                                        if (var10_23 == null) {
                                            var10_23 = new eb[30];
                                        }
                                        if (!(var18_32 = var17_30.toLowerCase()).equals(du.a().f.toLowerCase())) {
                                            var19_16 = 0;
                                            while (var19_16 < var13_24) {
                                                if (var10_23[var19_16] != null && var10_23[var19_16].a().equals("viewme") && var10_23[var19_16].b().toLowerCase().equals(var18_32)) break;
                                                ++var19_16;
                                            }
                                            if (var19_16 >= var13_24) {
                                                var21_37 = var20_36 = var18_32.length() - 1;
                                                while (var21_37 > 0) {
                                                    if (var18_32.charAt(var21_37) != '.') break;
                                                    --var21_37;
                                                }
                                                if (var21_37 < var20_36) {
                                                    var18_32 = var18_32.substring(0, var21_37 + 1);
                                                }
                                                if ((var11_21 = var18_32).length() >= 14) {
                                                    var11_21 = var11_21.substring(0, 11);
                                                    var11_21 = String.valueOf(var11_21) + "...";
                                                }
                                                if (var12_14) {
                                                    var10_23[var13_24] = new eb("chatto");
                                                    var10_23[var13_24].e("Chat \"" + var11_21 + "\"");
                                                    var10_23[var13_24].a(var18_32);
                                                    ++var13_24;
                                                }
                                                var10_23[var13_24] = new eb("viewme");
                                                var10_23[var13_24].e("Xem \"" + var11_21 + "\"");
                                                var10_23[var13_24].a(var18_32);
                                                ++var13_24;
                                                ** GOTO lbl240
                                            }
                                        }
                                    }
                                    break block74;
                                }
                                if (!var8_12[var16_28].startsWith("#") || var8_12[var16_28].length() <= 1 || var8_12[var16_28].toLowerCase().equals("#" + (String)var9_13)) break block75;
                                var17_30 = var8_12[var16_28].substring(1);
                                if (var17_30.charAt(0) >= 'a' && var17_30.charAt(0) <= 'z' || var17_30.charAt(0) >= 'A' && var17_30.charAt(0) <= 'Z' || var17_30.charAt(0) >= '0' && var17_30.charAt(0) <= '9') {
                                    var17_30 = var17_30.trim();
                                    var18_33 = 0;
                                    var19_17 = var17_30.length();
                                    while (var18_33 < var19_17) {
                                        if (!(var17_30.charAt(var18_33) >= 'a' && var17_30.charAt(var18_33) <= 'z' || var17_30.charAt(var18_33) >= 'A' && var17_30.charAt(var18_33) <= 'Z' || var17_30.charAt(var18_33) >= '0' && var17_30.charAt(var18_33) <= '9' || var17_30.charAt(var18_33) == '.' || var17_30.charAt(var18_33) == '_')) {
                                            var17_30 = var17_30.substring(0, var18_33);
                                            break;
                                        }
                                        ++var18_33;
                                    }
                                    if (var17_30 != null && var17_30.length() > 0) {
                                        if (var10_23 == null) {
                                            var10_23 = new eb[30];
                                        }
                                        var18_34 = var17_30.toLowerCase();
                                        var19_17 = 0;
                                        while (var19_17 < var13_24) {
                                            if (var10_23[var19_17] != null && var10_23[var19_17].a().equals("viewme") && var10_23[var19_17].b().toLowerCase().equals("#" + var18_34)) break;
                                            ++var19_17;
                                        }
                                        if (var19_17 >= var13_24) {
                                            var10_23[var13_24] = new eb("viewme");
                                            var21_38 = var20_36 = var18_34.length() - 1;
                                            while (var21_38 > 0) {
                                                if (var18_34.charAt(var21_38) != '.') break;
                                                --var21_38;
                                            }
                                            if (var21_38 < var20_36) {
                                                var18_34 = var18_34.substring(0, var21_38 + 1);
                                            }
                                            if ((var11_21 = var18_34).length() >= 14) {
                                                var11_21 = var11_21.substring(0, 11);
                                                var11_21 = String.valueOf(var11_21) + "...";
                                            }
                                            var10_23[var13_24].e("Xem nh\u00f3m \"" + var11_21 + "\"");
                                            var10_23[var13_24].a("#" + var18_34);
                                            ++var13_24;
                                            ** GOTO lbl240
                                        }
                                    }
                                }
                                break block74;
                            }
                            var11_22 = var8_12[var16_28].toLowerCase().indexOf("http://");
                            if (var11_22 < 0) break block76;
                            if (var10_23 == null) {
                                var10_23 = new eb[30];
                            }
                            var17_30 = "Xem WAP " + var14_26;
                            var18_35 = var8_12[var16_28].indexOf(10);
                            if (var11_22 > var18_35 && var16_28 + 1 < var8_12.length && var8_12[var16_28 + 1].startsWith("<")) {
                                var19_18 = var16_28 + 2;
                                while (var19_18 < var8_12.length) {
                                    if (var8_12[var19_18].endsWith(">")) {
                                        var17_30 = var8_12[var16_28 + 1].substring(1);
                                        var20_36 = var16_28 + 2;
                                        while (var20_36 <= var19_18) {
                                            var21_39 = var8_12[var20_36];
                                            if (var20_36 == var19_18) {
                                                var21_39 = var21_39.substring(0, var21_39.length() - 1);
                                            }
                                            if ((var17_30 = String.valueOf(var17_30) + " " + (String)var21_39).length() >= 16) {
                                                var17_30 = var17_30.substring(0, 13);
                                                var17_30 = String.valueOf(var17_30) + "...";
                                            }
                                            --var14_26;
                                            ++var20_36;
                                        }
                                    }
                                    ++var19_18;
                                }
                            }
                            var10_23[var13_24] = new eb("wap");
                            var19_19 = var11_22 > var18_35 ? var8_12[var16_28].substring(var8_12[var16_28].toLowerCase().indexOf("http://")) : var8_12[var16_28].substring(var8_12[var16_28].toLowerCase().indexOf("http://"), var18_35);
                            ++var14_26;
                            var10_23[var13_24].e(var17_30);
                            break block77;
                        }
                        var11_22 = var8_12[var16_28].toLowerCase().indexOf("rss://");
                        if (var11_22 < 0) break block74;
                        if (var10_23 == null) {
                            var10_23 = new eb[30];
                        }
                        var17_30 = "Xem tin " + var15_27;
                        var18_35 = var8_12[var16_28].indexOf(10);
                        if (var11_22 > var18_35 && var16_28 + 1 < var8_12.length && var8_12[var16_28 + 1].startsWith("<")) {
                            var19_20 = var16_28 + 2;
                            while (var19_20 < var8_12.length) {
                                if (var8_12[var19_20].endsWith(">")) {
                                    var17_30 = var8_12[var16_28 + 1].substring(1);
                                    var20_36 = var16_28 + 2;
                                    while (var20_36 <= var19_20) {
                                        var21_40 = var8_12[var20_36];
                                        if (var20_36 == var19_20) {
                                            var21_40 = var21_40.substring(0, var21_40.length() - 1);
                                        }
                                        if ((var17_30 = String.valueOf(var17_30) + " " + (String)var21_40).length() >= 16) {
                                            var17_30 = var17_30.substring(0, 13);
                                            var17_30 = String.valueOf(var17_30) + "...";
                                        }
                                        --var15_27;
                                        ++var20_36;
                                    }
                                }
                                ++var19_20;
                            }
                        }
                        var10_23[var13_24] = new eb("rss");
                        var19_19 = var11_22 > var18_35 ? var8_12[var16_28].substring(var8_12[var16_28].toLowerCase().indexOf("rss://") + 6) : var8_12[var16_28].substring(var8_12[var16_28].toLowerCase().indexOf("rss://") + 6, var18_35);
                        ++var15_27;
                        var10_23[var13_24].e(var17_30);
                        if (var19_19.toLowerCase().equals("null")) {
                            var19_19 = null;
                        }
                    }
                    var10_23[var13_24].a(var19_19);
                    ++var13_24;
                }
                ++var16_28;
            }
            if (var10_23 == null) {
                var10_23 = new eb[30];
            }
            if (0L != 0L) {
                var10_23[var13_24] = new eb("delme");
                var10_23[var13_24].e("X\u00f3a");
                ++var13_24;
            }
            if (var13_24 <= 0) {
                v1 = null;
            } else {
                var16_29 = new eb[var13_24];
                System.arraycopy(var10_23, 0, var16_29, 0, var16_29.length);
                var10_23 = var16_29;
                v1 = var8_12 = var10_23;
            }
        }
        if (v1 != null) {
            var9_13 = var6_6.a();
            if (var9_13 != null) {
                var10_23 = new eb[var8_12.length + ((eb[])var9_13).length];
                System.arraycopy(var9_13, 0, var10_23, 0, ((eb[])var9_13).length);
                System.arraycopy(var8_12, 0, var10_23, ((eb[])var9_13).length, var8_12.length);
                var8_12 = var10_23;
            }
            var6_6.a((eb[])var8_12);
        }
        if (var7_7) {
            var6_6.a();
        }
        var6_6.a((String)var2_2);
        if (var4_4 /* !! */ .toLowerCase().equals("ola")) {
            var4_4 /* !! */  = "Ola!!!";
        }
        var6_6.b((String)var4_4 /* !! */ );
        var6_6.a(System.currentTimeMillis());
        var6_6.a(var5_5);
        if (du.a().i == 2 && (var9_13 = var1_1.b((short)109)) != null) {
            du.a().b((String)var2_2, (String)var9_13);
        }
        if (this.a != null) {
            this.a.a(var6_6, (short)var3_3);
        }
    }

    private void b(dy dy2) {
        int n2 = dy2.c((short)7);
        if (n2 == 0) {
            return;
        }
        ds[] dsArray = new ds[n2];
        int n3 = dy2.b((short)7, 0);
        boolean bl2 = false;
        int n4 = 0;
        while (!bl2) {
            int n5 = n3;
            if ((n3 = dy2.a((short)7, n5)) <= 0) {
                bl2 = true;
            }
            byte by2 = 0;
            byte by3 = 0;
            byte by4 = 0;
            String string = dy2.b(n5);
            String string2 = null;
            String string3 = null;
            int n6 = dy2.a((short)12, n5, n3);
            if (n6 >= 0) {
                by4 = dy2.a(n6, (short)0);
                by2 = dy2.a((short)45, n5, n3, (short)0);
                by3 = dy2.a((short)38, n5, n3, (short)0);
                string2 = dy2.b((short)13, n5, n3);
                if (i.a(string2)) {
                    string2 = null;
                }
                string3 = dy2.b((short)86, n5, n3);
            }
            dy2.a((short)58, n5, n3, 0L);
            dsArray[n4] = new ds();
            dsArray[n4].a(string);
            dsArray[n4].a(by2);
            dsArray[n4].b(by3);
            dsArray[n4].d(string3);
            dsArray[n4].c(by4);
            dsArray[n4].c(string2);
            ++n4;
        }
        this.a.a(dsArray);
    }

    private void c(dy dy2) {
        dy2.a(dy2.b((short)200, 0), 0L);
        int n2 = dy2.c((short)24);
        if (n2 > 0) {
            ee[] eeArray = new ee[n2];
            int n3 = dy2.b((short)24, 0);
            int n4 = dy2.b((short)7, 0);
            int n5 = 0;
            while (n5 < n2) {
                int n6 = dy2.a((short)24, n3);
                int n7 = dy2.a((short)7, n4);
                String string = dy2.b(n3);
                String string2 = dy2.b((short)22, n3, n6);
                String string3 = dy2.b(n4);
                int n8 = dy2.a((short)206, n3, n6, 0);
                long l2 = dy2.a((short)9, n4, n7, 0L);
                n3 = n6;
                n4 = n7;
                eeArray[n5] = new ee();
                eeArray[n5].a(string);
                eeArray[n5].a = string3;
                eeArray[n5].c = string2;
                eeArray[n5].e = n8;
                eeArray[n5].d = l2;
                ++n5;
            }
        }
    }

    private void d(dy dy2) {
        byte[] byArray = dy2.a((short)53);
        byte by2 = 0;
        if (byArray != null) {
            by2 = byArray[0];
        }
        int s2 = dy2.c((short)7);
        ef[] efArray = null;
        if (s2 > 0) {
            efArray = new ef[s2];
            int n2 = dy2.b((short)7, 0);
            boolean bl2 = false;
            int n3 = 0;
            while (!bl2) {
                String[] stringArray;
                int n4;
                boolean bl3 = false;
                efArray[n3] = new ef();
                int n5 = n4;
                if ((n4 = dy2.a((short)7, n5)) < 0) {
                    bl2 = true;
                }
                String string = dy2.b(n5);
                String string2 = dy2.b((short)8, n5, n4);
                long l2 = dy2.a((short)9, n5, n4, 0L);
                n5 = dy2.a((short)220, n5, n4, (short)0);
                dy2.a(dy2.b((short)45, 0), (short)0);
                if (string.toLowerCase().equals("ola".toLowerCase())) {
                    bl3 = true;
                    stringArray = i.a(string2, " ", 3, false);
                    if (stringArray != null && stringArray.length == 3 && stringArray[0].toLowerCase().equals("LOCK".toLowerCase())) {
                        try {
                            Long.parseLong(stringArray[1]);
                            return;
                        }
                        catch (Exception exception) {}
                    }
                } else if (string.length() <= 3) {
                    eb[] ebArray;
                    bl3 = true;
                    stringArray = i.a(string2, "|", 2, false);
                    if (stringArray != null && stringArray.length == 2 && (ebArray = em.a(stringArray[1])) != null && ebArray.length > 0) {
                        string2 = stringArray[0];
                        efArray[n3].a(ebArray);
                    }
                }
                if (bl3) {
                    efArray[n3].a();
                }
                efArray[n3].a(string);
                efArray[n3].a(l2);
                efArray[n3].b(string2);
                efArray[n3].a((short)n5);
                ++n3;
            }
        }
        this.a.a(efArray, (short)by2);
    }

    private void e(dy dy2) {
        int n2 = dy2.c((short)200);
        if (n2 > 0) {
            dr[] drArray = new dr[n2];
            int n3 = dy2.b((short)200, 0);
            int n4 = dy2.b((short)207, 0);
            int n5 = 0;
            while (n5 < n2) {
                dy2.a(n3, 0L);
                int n6 = dy2.a((short)200, n3);
                dy2.b(n4);
                dy2.a((short)69, n3, n6, (short)0);
                dy2.a((short)62, n3, n6, (short)0);
                dy2.a((short)9, n3, n6, 0L);
                dy2.a((short)205, n3, n6, 0);
                dy2.a((short)206, n3, n6, 0);
                drArray[n5] = new dr();
                n4 = dy2.a((short)207, n4);
                n3 = n6;
                ++n5;
            }
        }
    }

    private void f(dy dy2) {
        String string = dy2.b((short)21);
        int n2 = dy2.c((short)7);
        ds[] dsArray = null;
        if (n2 > 0) {
            dsArray = new ds[n2];
            n2 = dy2.b((short)7, 0);
            boolean bl2 = false;
            int n3 = 0;
            while (!bl2) {
                int n4 = n2;
                if ((n2 = dy2.a((short)7, n4)) <= 0) {
                    bl2 = true;
                }
                byte by2 = 0;
                byte by3 = 0;
                byte by4 = 0;
                String string2 = dy2.b(n4);
                String string3 = null;
                String string4 = null;
                int n5 = dy2.a((short)12, n4, n2);
                if (n5 >= 0) {
                    by4 = dy2.a(n5, (short)0);
                    by2 = dy2.a((short)45, n4, n2, (short)0);
                    by3 = dy2.a((short)38, n4, n2, (short)0);
                    string3 = dy2.b((short)13, n4, n2);
                    if (i.a(string3)) {
                        string3 = null;
                    }
                    string4 = dy2.b((short)86, n4, n2);
                }
                dsArray[n3] = new ds();
                dsArray[n3].a(string2);
                dsArray[n3].b(dy2.b((short)22, n4, n2));
                dsArray[n3].a(by2);
                dsArray[n3].b(by3);
                dsArray[n3].d(string4);
                dsArray[n3].c(by4);
                dsArray[n3].c(string3);
                ++n3;
            }
        }
        this.a.a(dsArray, string);
    }

    private void g(dy dy2) {
        int n2 = dy2.c((short)7);
        if (n2 > 0) {
            ds[] dsArray = new ds[n2];
            int n3 = dy2.b((short)7, 0);
            int n4 = dy2.b((short)22, 0);
            int n5 = 0;
            while (n5 < n2) {
                String string = dy2.b(n3);
                String string2 = dy2.b(n4);
                dsArray[n5] = new ds();
                dsArray[n5].a(string);
                dsArray[n5].b(string2);
                n3 = dy2.a((short)7, n3);
                n4 = dy2.a((short)22, n4);
                ++n5;
            }
        }
    }

    private void h(dy dy2) {
        Object object;
        ef[] efArray;
        byte by2 = dy2.a(dy2.b((short)255, 0), (short)0);
        if (du.a().z != by2) {
            return;
        }
        String string = dy2.b((short)7);
        short s2 = 0;
        if (string != null) {
            string = string.toLowerCase();
            if (dy2.c((short)67) <= 0) {
                try {
                    byte[] byArray = string.getBytes("UTF-8");
                    if (byArray == null || byArray.length == 0) {
                        byArray = string.getBytes();
                    }
                    dy dy3 = dy2;
                    efArray = (ef[])byArray;
                    int n2 = 67;
                    object = dy3;
                    if (dy3.c == null) {
                        object.c = new dx[1];
                        object.c[0] = new dx();
                        object.c[0].a = (short)67;
                        object.c[0].b = (byte[])efArray;
                    } else {
                        dx[] dxArray = new dx[object.c.length + 1];
                        dx[] dxArray2 = dxArray;
                        dxArray[0] = new dx();
                        dxArray2[0].a = (short)67;
                        dxArray2[0].b = (byte[])efArray;
                        System.arraycopy(object.c, 0, dxArray2, 1, object.c.length);
                        object.c = dxArray2;
                    }
                }
                catch (Throwable throwable) {}
            }
        }
        if ((object = dy2.a((short)66)) != null) {
            s2 = object[0];
        }
        if (s2 == 3) {
            this.a.a(string, s2, null);
            return;
        }
        int n3 = dy2.c((short)8);
        efArray = null;
        if (n3 > 0) {
            efArray = new ef[n3];
            n3 = 0;
            int s3 = dy2.a((short)67, -1);
            boolean bl2 = false;
            while (!bl2) {
                int n2;
                int n4 = n2;
                if ((n2 = dy2.a((short)67, n4)) < 0) {
                    bl2 = true;
                }
                String string2 = dy2.b(n4);
                string2 = string2.toLowerCase();
                dy2.b((short)109, n4, n2);
                int n5 = dy2.a((short)72, n4);
                boolean bl3 = false;
                while (!bl3) {
                    int n6 = n5;
                    if ((n5 = dy2.a((short)72, n6, n2)) < 0) {
                        n5 = n2;
                        bl3 = true;
                    }
                    long l2 = dy2.a(n6, 0L);
                    n4 = dy2.a((short)221, n6, n5, (byte)0);
                    byte by3 = dy2.a((short)220, n6, n5, (byte)0);
                    int n7 = dy2.a((short)125, n6, n5, 0);
                    int n8 = dy2.a((short)124, n6, n5, 0);
                    byte by4 = dy2.a((short)90, n6, n5, (byte)0);
                    long l3 = dy2.a((short)9, n6, n5, 0L);
                    String string3 = dy2.b((short)8, n6, n5);
                    String string4 = dy2.b((short)24, n6, n5);
                    byte by5 = dy2.a((short)12, n6, n5, (short)0);
                    dy2.a((short)114, n6, n5, (byte)0);
                    String[] stringArray = dy2.b((short)110, n6, n5);
                    if (!i.b((String)stringArray)) {
                        stringArray = i.b((String)stringArray, ";");
                        int n9 = 0;
                        while (n9 < stringArray.length) {
                            String[] stringArray2 = i.b(stringArray[n9], ":");
                            if (i.a(stringArray2[0], "border")) {
                                Short.parseShort(stringArray2[1]);
                            } else if (i.a(stringArray2[0], "color")) {
                                Short.parseShort(stringArray2[1]);
                            }
                            ++n9;
                        }
                    }
                    String n52 = dy2.b((short)29, n6, n5);
                    efArray[n3] = new ef();
                    efArray[n3].b(l2);
                    efArray[n3].a(string2);
                    efArray[n3].a(n7);
                    efArray[n3].b(n8);
                    efArray[n3].c(by4);
                    efArray[n3].d(n52);
                    efArray[n3].b((short)n4);
                    efArray[n3].a(by3);
                    efArray[n3].a(l3);
                    efArray[n3].b(string3);
                    efArray[n3].c(string4);
                    efArray[n3].d(by5);
                    ++n3;
                }
            }
            efArray = (ef[])g.a(efArray, new ek(true));
        }
        this.a.a(string, s2, efArray);
    }

    private void i(dy dy2) {
        byte by2 = dy2.a(dy2.b((short)255, 0), (short)0);
        if (du.a().A != by2) {
            return;
        }
        by2 = dy2.a(dy2.b((short)31, 0), (byte)0);
        int n2 = dy2.c((short)29);
        String[] stringArray = null;
        String[] stringArray2 = null;
        String string = dy2.b((short)207);
        dy2.b((short)109);
        if (n2 > 0) {
            int n3 = 0;
            stringArray = new String[n2];
            stringArray2 = new String[n2];
            n2 = 0;
            int n4 = 0;
            while (n3 < stringArray.length) {
                n2 = dy2.a((short)29, n2);
                n4 = dy2.a((short)30, n4);
                stringArray[n3] = dy2.b(n2);
                stringArray2[n3] = dy2.b(n4);
                ++n3;
            }
        }
        switch (by2) {
            case 0: {
                this.a.a(string, stringArray, stringArray2);
                return;
            }
            case 1: {
                this.a.b(string, stringArray, stringArray2);
                return;
            }
        }
        this.a.c(string, stringArray, stringArray2);
    }

    private void j(dy stringArray) {
        stringArray.a(stringArray.b((short)255, 0), (short)0);
        Object[] objectArray = stringArray.b((short)8);
        if (objectArray != null) {
            String[] stringArray2;
            int[] nArray = null;
            long[] lArray = null;
            if ((objectArray = i.b((String)objectArray, ";")) != null) {
                nArray = new int[objectArray.length];
                lArray = new long[objectArray.length];
                int n2 = 0;
                while (n2 < objectArray.length) {
                    stringArray2 = i.b(objectArray[n2], ":");
                    nArray[n2] = Integer.parseInt(stringArray2[0]);
                    lArray[n2] = Long.parseLong(stringArray2[1]);
                    ++n2;
                }
            }
            String string = stringArray.b((short)109);
            stringArray2 = stringArray.b((short)110);
            if (stringArray2 != null) {
                i.b((String)stringArray2, ";");
            }
            stringArray = i.b(string, ";");
            objectArray = new short[stringArray.length];
            int n3 = 0;
            while (n3 < objectArray.length) {
                objectArray[n3] = (String)((short)Integer.parseInt(stringArray[n3]));
                ++n3;
            }
            this.a.a(nArray, lArray, (short[])objectArray);
        }
    }

    private void k(dy stringArray) {
        stringArray.b((short)7);
        Object object = stringArray.a((short)209);
        byte by2 = object[0];
        switch (by2) {
            case 3: {
                byte[] byArray = new byte[((byte[])object).length - 3];
                System.arraycopy(object, 3, byArray, 0, byArray.length);
                stringArray.a(stringArray.b((short)45, 0), (short)0);
                return;
            }
            case 5: {
                Object object2 = new byte[((byte[])object).length - 1];
                System.arraycopy(object, 1, object2, 0, ((byte[])object2).length);
                stringArray = i.a(object2);
                if (i.a((String)stringArray)) break;
                stringArray = i.b((String)stringArray, ";");
                object = null;
                object2 = null;
                if (stringArray != null && stringArray.length > 0) {
                    int n2 = 0;
                    while (n2 < stringArray.length) {
                        if (stringArray[n2].startsWith("id=")) {
                            object = stringArray[n2].substring(3);
                        }
                        if (stringArray[n2].startsWith("chatgroupId=")) {
                            object2 = stringArray[n2].substring(12);
                        }
                        ++n2;
                    }
                }
                if (i.a((String)object2) && i.a((String)object)) {
                    return;
                }
                if (!i.a((String)object2)) break;
                return;
            }
        }
    }

    private void l(dy stringArray) {
        byte by2 = stringArray.a(stringArray.b((short)255, 0), (short)0);
        if (du.a().A != by2) {
            return;
        }
        ef[] efArray = stringArray.b((short)8);
        String string = stringArray.b((short)207);
        stringArray.b((short)109);
        stringArray.b((short)29);
        stringArray.a(stringArray.b((short)90, 0), (short)0);
        stringArray.a(stringArray.b((short)72, 0), 0L);
        stringArray.a(stringArray.b((short)124, 0), 0);
        stringArray.a(stringArray.b((short)125, 0), 0);
        if (efArray != null && (stringArray = i.b((String)efArray, "\n")) != null && stringArray.length > 0) {
            efArray = new ef[stringArray.length];
            int n2 = 0;
            while (n2 < efArray.length) {
                efArray[n2] = new ef();
                efArray[n2].b(stringArray[n2]);
                ++n2;
            }
            this.a.a(string, efArray);
        }
    }

    private void m(dy object) {
        ((dy)object).a(((dy)object).b((short)255, 0), (short)0);
        try {
            if (du.a().d) {
                byte[] byArray = ((dy)object).a((short)214);
                int n2 = m.a(byArray[0], byArray[1]);
                int n3 = 0;
                if (du.a().v == n2) {
                    byArray = ((dy)object).a((short)213);
                    if (byArray != null) {
                        du.a().x = m.a(byArray);
                        if (du.a().x <= 0) {
                            du.a().x = 1;
                        }
                        byArray = ((dy)object).a((short)37);
                        n3 = m.c(byArray);
                        du.a().n = new byte[n3];
                        du.a().w = n3;
                    }
                    int n4 = du.a().x;
                    byte[] byArray2 = ((dy)object).a((short)23);
                    System.arraycopy(byArray2, 0, du.a().n, du.a().u, byArray2.length);
                    du.a().u += byArray2.length;
                    if (n2 >= n4 - 1) {
                        du.a().d = false;
                        int cfr_ignored_0 = du.a().x;
                        int cfr_ignored_1 = du.a().x;
                        this.a.a(du.a().u, du.a().w);
                        ee ee2 = new ee();
                        Object object2 = ((dy)object).b((short)7);
                        object = ((dy)object).b((short)24);
                        if (object == null) {
                            object = du.a().y;
                        }
                        ee2.a = object2;
                        ee2.a((String)object);
                        object2 = du.a().n;
                        object = ee2;
                        ee2.h = (byte[])object2;
                        this.a.a(ee2);
                        du.a().n = null;
                        return;
                    }
                    if (n2 == 0 && n3 >= 204800) {
                        this.a.d_();
                        return;
                    }
                    du.a().d(n2);
                    return;
                }
            }
        }
        catch (OutOfMemoryError outOfMemoryError) {
            du.a().l();
            this.a.e_();
        }
    }

    private void n(dy dy2) {
        dy2.a(dy2.b((short)20, 0), (byte)0);
        int n2 = dy2.c((short)7);
        if (n2 > 0) {
            String[] stringArray = new String[n2];
            int n3 = dy2.b((short)7, 0);
            int n4 = 0;
            while (n4 < stringArray.length) {
                stringArray[n4] = dy2.b(n3);
                n3 = dy2.a((short)7, n3);
                ++n4;
            }
        }
    }

    private void o(dy ebArray) {
        try {
            String string = ebArray.b((short)28);
            ebArray = ebArray.b((short)30);
            if (ebArray != null) {
                ebArray = em.a((String)ebArray);
                this.a.a(string, ebArray);
                return;
            }
        }
        catch (Throwable throwable) {}
    }

    private void p(dy stringArray) {
        try {
            stringArray = stringArray.b((short)83);
            if (stringArray != null) {
                stringArray = i.b((String)stringArray, "|");
                String[] stringArray2 = new String[stringArray.length / 2];
                String[] stringArray3 = new String[stringArray2.length];
                int n2 = 0;
                while (n2 < stringArray2.length) {
                    stringArray2[n2] = stringArray[n2 << 1];
                    stringArray3[n2] = stringArray[(n2 << 1) + 1];
                    ++n2;
                }
                this.a.a(stringArray2, stringArray3);
                return;
            }
        }
        catch (Throwable throwable) {}
    }

    private void q(dy dy2) {
        dy2.a(dy2.b((short)255, 0), (short)0);
        int n2 = dy2.c((short)7);
        if (n2 > 0) {
            ds[] dsArray = new ds[n2];
            int n3 = dy2.b((short)7, 0);
            int n4 = dy2.b((short)4, 0);
            int n5 = 0;
            while (n5 < dsArray.length) {
                dsArray[n5] = new ds(dy2.b(n3));
                dy2.b(n4);
                ++n5;
            }
        }
    }

    private void r(dy object) {
        ((dy)object).b((short)7);
        ((dy)object).a(((dy)object).b((short)114, 0), (short)0);
        ((dy)object).b((short)111);
        ((dy)object).a(((dy)object).b((short)130, 0), 0L);
        ((dy)object).a(((dy)object).b((short)9, 0), 0L);
        String[] stringArray = ((dy)object).b((short)109);
        String[] stringArray2 = ((dy)object).b((short)110);
        object = ((dy)object).b((short)112);
        stringArray = i.b((String)stringArray, ";");
        stringArray2 = i.b((String)stringArray2, ";");
        String[] stringArray3 = null;
        if (object != null) {
            stringArray3 = i.b((String)object, ";");
        }
        if (stringArray != null && stringArray.length > 0) {
            object = new short[stringArray.length];
            String[] stringArray4 = new String[stringArray.length];
            boolean[] blArray = new boolean[stringArray.length];
            int n2 = 0;
            while (n2 < stringArray.length) {
                object[n2] = Short.parseShort(stringArray[n2]);
                stringArray4[n2] = stringArray2[n2];
                blArray[n2] = stringArray3 != null && stringArray3[n2] != null ? stringArray3[n2].equals("1") : false;
                ++n2;
            }
        }
    }

    private void s(dy dy2) {
        int n2 = dy2.c((short)129);
        dy2.b((short)21);
        dy2.a(dy2.b((short)39, 0), 0);
        if (n2 > 0) {
            dw[] dwArray = new dw[n2];
            int n3 = dy2.b((short)129, 0);
            int n4 = 0;
            while (n4 < n2) {
                int n5 = dy2.a((short)129, n3);
                dy2.b(n3);
                dy2.b((short)22, n3, n5);
                dy2.b((short)13, n3, n5);
                dy2.b((short)24, n3, n5);
                dwArray[n4] = new dw();
                n3 = n5;
                ++n4;
            }
        }
    }

    private void t(dy dy2) {
        dy2.b((short)129);
        int n2 = dy2.c((short)7);
        if (n2 > 0) {
            ds[] dsArray = new ds[n2];
            int n3 = dy2.b((short)7, 0);
            int n4 = 0;
            while (n4 < n2) {
                int n5 = dy2.a((short)7, n3);
                String string = dy2.b(n3);
                String string2 = dy2.b((short)22, n3, n5);
                n3 = dy2.a((short)45, n3, n5, (short)0);
                dsArray[n4] = new ds();
                dsArray[n4].a(string);
                dsArray[n4].b(string2);
                dsArray[n4].a((short)n3);
                n3 = n5;
                ++n4;
            }
        }
    }

    private void u(dy dy2) {
        dy2.a(dy2.b((short)255, 0), (short)0);
        int n2 = dy2.c((short)109);
        if (n2 > 0) {
            ej[] ejArray = new ej[n2];
            int n3 = dy2.b((short)109, 0);
            int n4 = 0;
            while (n4 < n2) {
                String string;
                int n5 = dy2.a((short)109, n3);
                ejArray[n4] = new ej();
                dy2.b(n3);
                dy2.b((short)112, n3, n5);
                dy2.a((short)9, n3, n5, 0L);
                dy2.a(dy2.a((short)23, n3, n5));
                dy2.b((short)110, n3, n5);
                String string2 = dy2.b((short)113, n3, n5);
                if (string2 != null) {
                    i.b(string2, ".");
                }
                if (!i.a(string = dy2.b((short)111, n3, n5))) {
                    em.a(string);
                }
                n3 = n5;
                ++n4;
            }
            dy2.a(dy2.b((short)124, 0), 0);
        }
    }

    private void v(dy dy2) {
        dy2.a(dy2.b((short)255, 0), (short)0);
        dy2.b((short)7);
        int n2 = dy2.c((short)110);
        if (n2 > 0) {
            ei[] eiArray = new ei[n2];
            int n3 = dy2.b((short)110, 0);
            int n4 = 0;
            while (n4 < n2) {
                int n5 = dy2.a((short)110, n3);
                eiArray[n4] = new ei();
                dy2.b(n3);
                dy2.a(dy2.a((short)115, n3, n5), (byte)0);
                dy2.a(dy2.a((short)115, n3, n5), (byte)0);
                dy2.b((short)28, n3, n5);
                dy2.b((short)30, n3, n5);
                dy2.b((short)109, n3, n5);
                dy2.a((short)9, n3, n5, 0L);
                n3 = n5;
                ++n4;
            }
            return;
        }
    }

    private void w(dy dy2) {
        int n2;
        int n3 = 0;
        byte[] byArray = dy2.a((short)31);
        short s2 = 3;
        if (byArray != null) {
            s2 = byArray[0];
        }
        if ((n2 = dy2.c((short)29)) <= 0) {
            return;
        }
        dq[] dqArray = new dq[n2];
        int n4 = dy2.b((short)29, 0);
        boolean bl2 = false;
        int n5 = 0;
        while (!bl2) {
            int n6 = n4;
            if ((n4 = dy2.a((short)29, n6)) < 0) {
                bl2 = true;
            }
            String string = dy2.b(n6);
            String string2 = dy2.b((short)28, n6, n4);
            byte by2 = dy2.a((short)70, n6, n4, (short)1);
            n3 += by2;
            eb[] ebArray = new eb[]{};
            String string3 = dy2.b((short)30, n6, n4);
            if (string3 != null) {
                ebArray = em.a(string3);
            }
            dqArray[n5] = new dq(string2, string, s2, by2, ebArray);
            ++n5;
        }
        this.a.a(n3, dqArray);
    }

    private void x(dy dy2) {
        int n2 = dy2.c((short)207);
        ea[] eaArray = null;
        if (n2 > 0) {
            eaArray = new ea[n2];
            n2 = dy2.b((short)207, 0);
            int n3 = dy2.b((short)200, 0);
            int n4 = dy2.b((short)205, 0);
            int n5 = 0;
            while (n2 >= 0) {
                String string = dy2.b(n2);
                byte[] byArray = dy2.a(n3);
                long l2 = m.d(byArray);
                byArray = dy2.a(n4);
                int n6 = m.c(byArray);
                eaArray[n5] = new ea();
                eaArray[n5].b(string);
                eaArray[n5].b(l2);
                eaArray[n5].a(n6);
                n2 = dy2.a((short)207, n2);
                n3 = dy2.a((short)200, n3);
                n4 = dy2.a((short)205, n4);
                ++n5;
            }
        }
        this.a.a(eaArray);
    }

    private void y(dy dy2) {
        int n2 = dy2.c((short)28);
        byte[] byArray = dy2.a((short)200);
        long l2 = m.d(byArray);
        ea[] eaArray = null;
        if (n2 > 0) {
            eaArray = new ea[n2];
            n2 = dy2.b((short)28, 0);
            int n3 = dy2.b((short)65, 0);
            int n4 = 0;
            while (n2 >= 0) {
                eaArray[n4] = new ea();
                String string = dy2.b(n2);
                byArray = dy2.a(n3);
                long l3 = m.d(byArray);
                eaArray[n4].a(string);
                eaArray[n4].a(l3);
                n2 = dy2.a((short)28, n2);
                n3 = dy2.a((short)65, n3);
                ++n4;
            }
        }
        this.a.a(l2, eaArray);
    }

    private void z(dy stringArray) {
        Object object;
        String[] stringArray2;
        Object object2;
        ee[] eeArray = null;
        eb[] ebArray = null;
        String string = stringArray.b((short)28);
        String string2 = stringArray.b((short)29);
        int n2 = stringArray.c((short)23);
        if (n2 > 0) {
            eeArray = new ee[n2];
            n2 = stringArray.b((short)23, 0);
            int n3 = 0;
            while (n2 >= 0) {
                eeArray[n3] = new ee();
                object2 = stringArray.a(n2);
                stringArray2 = eeArray[n3];
                eeArray[n3].h = object2;
                n2 = stringArray.b((short)23, n2);
                ++n3;
            }
        }
        if ((object = stringArray.b((short)30)) != null) {
            String[] stringArray3 = object;
            stringArray = i.b((String)object, "^");
            ebArray = new eb[stringArray.length];
            int n4 = 0;
            while (n4 < stringArray.length) {
                object = stringArray[n4];
                stringArray2 = i.a((String)object, "|", 2, true);
                object = stringArray2[0];
                object = i.a((String)object, ":", 3, true);
                object2 = object[0];
                String string3 = null;
                if (((String[])object).length == 3) {
                    string3 = object[1];
                }
                Object object3 = ((String[])object).length == 3 ? object[2] : object[1];
                int n5 = object3.indexOf(32);
                String[] stringArray4 = object3.substring(n5 + 1);
                object = object3.substring(0, n5);
                if (((String)object2).toLowerCase().equals("wap".toLowerCase())) {
                    ebArray[n4] = new eb("wap");
                    ebArray[n4].a((String)object);
                    ebArray[n4].d((String)stringArray4);
                    ebArray[n4].e(string3);
                } else if (((String)object2).toLowerCase().equals("call".toLowerCase())) {
                    ebArray[n4] = new eb("call");
                    ebArray[n4].b((String)object);
                    ebArray[n4].d((String)stringArray4);
                    ebArray[n4].e(string3);
                } else if (((String)object2).toLowerCase().equals("sms".toLowerCase())) {
                    ebArray[n4] = new eb("sms");
                    ebArray[n4].c((String)object);
                    ebArray[n4].d((String)stringArray4);
                    ebArray[n4].e(string3);
                    object = stringArray2[1];
                    stringArray2 = i.b((String)object, "#");
                    object = stringArray2[0];
                    if (stringArray2.length > 1) {
                        object2 = new ec[stringArray2.length - 1];
                        int n6 = 1;
                        while (n6 < stringArray2.length) {
                            object3 = stringArray2[n6];
                            object3 = i.b((String)object3, "|");
                            stringArray4 = object3[0];
                            int n7 = Integer.parseInt(object3[1]);
                            object2[n6 - 1] = new ec();
                            ((ec)object2[n6 - 1]).a = n7;
                            ((ec)object2[n6 - 1]).b = stringArray4;
                            if (((String[])object3).length > 2) {
                                stringArray4 = new String[(((String[])object3).length - 2) / 2];
                                String[] stringArray5 = new String[(((String[])object3).length - 2) / 2];
                                int n8 = 0;
                                int n9 = 2;
                                while (n9 < ((String[])object3).length) {
                                    stringArray4[n8] = object3[n9];
                                    stringArray5[n8] = object3[++n9];
                                    ++n8;
                                    ++n9;
                                }
                                ((ec)object2[n6 - 1]).d = stringArray4;
                                ((ec)object2[n6 - 1]).e = stringArray5;
                            }
                            ++n6;
                        }
                        ebArray[n4].f((String)object);
                        ebArray[n4].a((ec[])object2);
                    }
                }
                ++n4;
            }
        }
        this.a.a(string, string2, eeArray, ebArray);
    }

    private void A(dy dy2) {
        int n2 = dy2.a(dy2.b((short)255, 0), (short)0);
        if (du.a().z != n2) {
            return;
        }
        n2 = dy2.c((short)8);
        Object[] objectArray = null;
        if (n2 > 0) {
            objectArray = new ef[n2];
            n2 = 0;
            int s2 = dy2.a((short)67, -1);
            boolean bl2 = false;
            while (!bl2) {
                int ef2;
                int n3 = ef2;
                if ((ef2 = dy2.a((short)67, n3)) < 0) {
                    bl2 = true;
                }
                String string = dy2.b(n3);
                string = string.toLowerCase();
                int n4 = dy2.a((short)72, n3);
                boolean bl3 = false;
                while (!bl3) {
                    int n5 = n4;
                    if ((n4 = dy2.a((short)72, n5, ef2)) < 0) {
                        n4 = ef2;
                        bl3 = true;
                    }
                    long l2 = dy2.a(n5, 0L);
                    n3 = dy2.a((short)221, n5, n4, (byte)0);
                    byte by2 = dy2.a((short)220, n5, n4, (byte)0);
                    int n6 = dy2.a((short)125, n5, n4, 0);
                    int n7 = dy2.a((short)124, n5, n4, 0);
                    byte by3 = dy2.a((short)90, n5, n4, (byte)0);
                    long l3 = dy2.a((short)9, n5, n4, 0L);
                    String string2 = dy2.b((short)8, n5, n4);
                    String string3 = dy2.b((short)24, n5, n4);
                    dy2.a((short)114, n5, n4, (byte)0);
                    String[] stringArray = dy2.b((short)110, n5, n4);
                    if (!i.b((String)stringArray)) {
                        stringArray = i.b((String)stringArray, ";");
                        int n8 = 0;
                        while (n8 < stringArray.length) {
                            String[] stringArray2 = i.b(stringArray[n8], ":");
                            if (i.a(stringArray2[0], "border")) {
                                Short.parseShort(stringArray2[1]);
                            } else if (i.a(stringArray2[0], "color")) {
                                Short.parseShort(stringArray2[1]);
                            }
                            ++n8;
                        }
                    }
                    String n42 = dy2.b((short)29, n5, n4);
                    objectArray[n2] = new ef();
                    ((ef)objectArray[n2]).b(l2);
                    ((ef)objectArray[n2]).a(string);
                    ((ef)objectArray[n2]).a(n6);
                    ((ef)objectArray[n2]).b(n7);
                    ((ef)objectArray[n2]).c(by3);
                    ((ef)objectArray[n2]).d(n42);
                    ((ef)objectArray[n2]).b((short)n3);
                    ((ef)objectArray[n2]).a(by2);
                    ((ef)objectArray[n2]).a(l3);
                    ((ef)objectArray[n2]).b(string2);
                    ((ef)objectArray[n2]).c(string3);
                    long cfr_ignored_0 = du.a().p;
                    ++n2;
                }
            }
            objectArray = (ef[])g.a(objectArray, new ek(true));
        }
        if (objectArray != null && objectArray.length > 0) {
            ef[] efArray = null;
            ef ef2 = objectArray[objectArray.length - 1];
            if (objectArray.length > 1) {
                efArray = new ef[objectArray.length - 1];
                System.arraycopy(objectArray, 0, efArray, 0, efArray.length);
            }
            this.a.a(ef2, efArray);
        }
    }

    private void B(dy dy2) {
        dy2.a(dy2.b((short)255, 0), (short)0);
        dy2.b((short)109);
        int n2 = dy2.c((short)110);
        if (n2 > 0) {
            ee[] eeArray = new ee[n2];
            int n3 = dy2.b((short)110, 0);
            int n4 = dy2.b((short)23, 0);
            int n5 = 0;
            while (n5 < n2) {
                eeArray[n5] = new ee();
                eeArray[n5].a = dy2.b(n3);
                eeArray[n5].h = dy2.a(n4);
                n3 = dy2.a((short)110, n3);
                n4 = dy2.a((short)23, n4);
                ++n5;
            }
        }
    }

    private void C(dy dy2) {
        int n2 = dy2.c((short)7);
        if (n2 > 0) {
            ds[] dsArray = new ds[n2];
            int n3 = dy2.a((short)7, -1);
            int n4 = dy2.a((short)58, -1);
            int n5 = 0;
            while (n5 < n2) {
                String string = dy2.b(n3);
                dy2.a(n4, 0L);
                n3 = dy2.a((short)7, n3);
                n4 = dy2.a((short)58, n4);
                dsArray[n5] = new ds();
                dsArray[n5].a(string);
                ++n5;
            }
        }
    }

    private void D(dy stringArray) {
        String string = stringArray.b((short)5);
        String string2 = stringArray.b((short)29);
        stringArray = stringArray.b((short)8);
        if ((stringArray = i.b((String)stringArray, ";")) != null && stringArray.length > 0 && stringArray.length % 2 == 0) {
            String[] stringArray2 = new String[stringArray.length / 2];
            String[] stringArray3 = new String[stringArray.length / 2];
            int n2 = 0;
            while (n2 < stringArray.length) {
                stringArray3[n2 / 2] = stringArray[n2];
                stringArray2[n2 / 2] = stringArray[n2 + 1];
                n2 += 2;
            }
            this.a.a(string2, string, stringArray3, stringArray2);
        }
    }

    public final void a() {
        this.c = true;
        if (this.b != null) {
            this.b.b();
            this.b = null;
        }
    }

    public final void a(dv dv2) {
        this.a = dv2;
        if (this.b != null) {
            this.b.a(this.a);
        }
    }

    private static eb[] a(String stringArray) {
        if ((stringArray = i.b((String)stringArray, "^")) == null || stringArray.length == 0) {
            return null;
        }
        eb[] ebArray = new eb[stringArray.length];
        int n2 = 0;
        int n3 = 0;
        while (n3 < stringArray.length) {
            Object object = stringArray[n3];
            String[] stringArray2 = i.a((String)object, "|", 2, false);
            object = stringArray2[0];
            object = i.a((String)object, ":", 3, false);
            ec[] ecArray = object[0];
            String string = null;
            if (((String[])object).length == 3) {
                string = object[1];
            }
            Object object2 = ((String[])object).length == 3 ? object[2] : object[1];
            int n4 = object2.indexOf(32);
            Object object3 = n4 < 0 ? object2 : object2.substring(n4 + 1);
            object = n4 <= 0 ? "" : object2.substring(0, n4);
            object2 = ecArray.toLowerCase();
            if (object2.equals("wap".toLowerCase())) {
                ebArray[n2] = new eb("wap");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("viewme".toLowerCase())) {
                ebArray[n2] = new eb("viewme");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("viewbox".toLowerCase())) {
                ebArray[n2] = new eb("viewbox");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("viewproposalrequest".toLowerCase())) {
                ebArray[n2] = new eb("viewproposalrequest");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("viewproposallist".toLowerCase())) {
                ebArray[n2] = new eb("viewproposallist");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("viewmedia".toLowerCase())) {
                ebArray[n2] = new eb("viewmedia");
                object = new ee();
                ((ee)object).a((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("xt".toLowerCase())) {
                ebArray[n2] = new eb("xt");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("app".toLowerCase())) {
                ebArray[n2] = new eb("app");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("dpk".toLowerCase())) {
                ebArray[n2] = new eb("dpk");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("rss".toLowerCase())) {
                ebArray[n2] = new eb("rss");
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("vip".toLowerCase())) {
                ebArray[n2] = new eb("vip");
                if (object == null || ((String)object).length() == 0) {
                    object = du.a().f;
                }
                ebArray[n2].a((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("call".toLowerCase())) {
                ebArray[n2] = new eb("call");
                ebArray[n2].b((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                ++n2;
            } else if (object2.equals("sms".toLowerCase()) || object2.equals("msg".toLowerCase())) {
                ebArray[n2] = new eb((String)ecArray);
                ebArray[n2].c((String)object);
                ebArray[n2].d((String)object3);
                ebArray[n2].e(string);
                object = stringArray2[1];
                stringArray2 = i.b((String)object, "#");
                object = stringArray2[0];
                if (stringArray2.length > 1) {
                    ecArray = new ec[stringArray2.length - 1];
                    int n5 = 1;
                    while (n5 < stringArray2.length) {
                        object2 = stringArray2[n5];
                        object2 = i.b((String)object2, "|");
                        object3 = object2[0];
                        int n6 = Integer.parseInt(object2[1]);
                        ecArray[n5 - 1] = new ec();
                        ecArray[n5 - 1].a = n6;
                        ecArray[n5 - 1].b = object3;
                        if (n6 == 10) {
                            ecArray[n5 - 1].c = ((String[])object2).length > 2 ? object2[2] : du.a().f;
                        } else if ((n6 == 4 || n6 == 5 || n6 == 6) && ((String[])object2).length > 2) {
                            object3 = new String[(((String[])object2).length - 2) / 2];
                            String[] stringArray3 = new String[(((String[])object2).length - 2) / 2];
                            int n7 = 0;
                            int n8 = 2;
                            while (n8 < ((String[])object2).length) {
                                object3[n7] = object2[n8];
                                stringArray3[n7] = object2[++n8];
                                ++n7;
                                ++n8;
                            }
                            ecArray[n5 - 1].d = object3;
                            ecArray[n5 - 1].e = stringArray3;
                        }
                        ++n5;
                    }
                    ebArray[n2].a(ecArray);
                }
                ebArray[n2].f((String)object);
                ++n2;
            }
            ++n3;
        }
        if (n2 < ebArray.length) {
            eb[] ebArray2 = new eb[n2];
            System.arraycopy(ebArray, 0, ebArray2, 0, n2);
            ebArray = ebArray2;
        }
        return ebArray;
    }

    private static dt[] a(dy dy2, dt[] dtArray, boolean bl2, short s2) {
        int n2 = dy2.c((short)21);
        dt[] dtArray2 = null;
        if (n2 > 0) {
            short s3;
            short s4;
            short s5;
            dtArray2 = new dt[n2];
            short s6 = dy2.b((short)21, 0);
            int n3 = 0;
            while (n3 < n2) {
                int n4;
                int n5;
                short s7 = s6;
                s6 = dy2.a((short)21, (int)s7);
                String string = dy2.b((int)s7);
                dtArray2[n3] = new dt(string);
                byte by2 = dy2.a((short)20, (int)s7, (int)s6, (short)0);
                dtArray2[n3].a(by2);
                s5 = s6;
                s4 = s7;
                s3 = 7;
                Object object = dy2;
                int n6 = 0;
                if (((dy)object).c == null) {
                    n5 = 0;
                } else {
                    s3 = s4;
                    s4 = s5;
                    if (s3 < 0) {
                        s3 = -1;
                    } else if (s3 >= ((dy)object).c.length) {
                        s3 = ((dy)object).c.length - 1;
                    }
                    if (s4 < 0) {
                        s4 = ((dy)object).c.length;
                    }
                    if (s3 + 1 >= s4) {
                        n5 = 0;
                    } else {
                        ++s3;
                        while (s3 < ((dy)object).c.length && s3 < s4) {
                            if (((dy)object).c[s3].a == 7) {
                                ++n6;
                            }
                            ++s3;
                        }
                        n5 = n4 = n6;
                    }
                }
                if (n5 > 0) {
                    ds[] dsArray = new ds[n4];
                    s4 = dy2.a((short)7, (int)s7);
                    int n7 = 0;
                    while (n7 < n4) {
                        s3 = s4;
                        s4 = dy2.a((short)7, (int)s3);
                        object = dy2.b((int)s3);
                        object = ((String)object).toLowerCase();
                        dsArray[n7] = new ds();
                        dsArray[n7].a((String)object);
                        Object object2 = dy2.b((short)22, s3, s4);
                        if (object2 == null) {
                            object2 = object;
                        }
                        dsArray[n7].b((String)object2);
                        s5 = 0;
                        n6 = 0;
                        byte by3 = 0;
                        object = null;
                        object2 = null;
                        int n8 = dy2.a((short)12, s3, s4);
                        if (n8 >= 0) {
                            by3 = dy2.a(n8, (short)0);
                            s5 = dy2.a((short)45, (int)s3, (int)s4, (short)0);
                            n6 = dy2.a((short)38, (int)s3, (int)s4, (short)0);
                        }
                        object = dy2.b((short)13, s3, s4);
                        object2 = dy2.b((short)86, s3, s4);
                        dsArray[n7].a(s5);
                        dsArray[n7].b((short)n6);
                        dsArray[n7].d((String)object2);
                        dsArray[n7].c(by3);
                        dsArray[n7].c((String)object);
                        ++n7;
                    }
                    dtArray2[n3].a(dsArray);
                }
                ++n3;
            }
            dt[] dtArray3 = dtArray2;
            s3 = 0;
            s4 = 1;
            while (s4 < dtArray3.length) {
                if (dtArray3[s4 - 1].a() == 1) {
                    ++s3;
                } else {
                    s5 = s4;
                    while (s5 > s3 && dtArray3[s5].b().compareTo(dtArray3[s5 - 1].b()) < 0) {
                        dt dt2 = dtArray3[s5 - 1];
                        dtArray3[s5 - 1] = dtArray3[s5];
                        dtArray3[s5] = dt2;
                        --s5;
                    }
                }
                ++s4;
            }
        }
        return dtArray2;
    }
}

