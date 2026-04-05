/*
 * Decompiled with CFR 0.152.
 */
import com.mg.smsgame.MGMIDlet;
import java.io.InputStream;

final class kw
implements Runnable,
km {
    public kp c;
    public ko d;
    public kn e;
    private kt f;
    private boolean g = false;

    public kw(InputStream inputStream) {
        this.f = new kt(inputStream);
        this.g = false;
        new Thread(this).start();
    }

    public final void run() {
        int n2 = 10;
        this.g = false;
        block103: while (!this.g) {
            Object[] objectArray;
            short s2;
            int n3;
            Object object;
            Object object2;
            Object object3;
            Object object4 = null;
            try {
                Object object5;
                block145: {
                    object4 = this;
                    if (((kw)object4).f == null) {
                        cw.a("[SocketReader] input null");
                        object5 = null;
                    } else {
                        object3 = ((kw)object4).f;
                        Object[] objectArray2 = object2 = new byte[7];
                        object4 = ((kt)object3).a;
                        int n4 = j.a((InputStream)object4, objectArray2, 0);
                        object = new ks();
                        if (n4 <= 0) {
                            cw.a("[InputBuffer] readPacket() PACKET_FAIL");
                            object5 = null;
                        } else {
                            int n5;
                            kq.i += n4;
                            n3 = p.a(object2[0], object2[1]);
                            ((ks)object).a = n5 = p.a(object2[2], object2[3], object2[4], object2[5]);
                            ((ks)object).b = n4 = p.a(object2[6]);
                            if (n5 > 0) {
                                kq.i += n5;
                                object2 = new kr[n3];
                                n3 = 0;
                                while (n3 < ((byte[])object2).length) {
                                    InputStream inputStream = ((kt)object3).a;
                                    byte[] byArray = new byte[5];
                                    objectArray2 = byArray;
                                    if (j.a(inputStream, objectArray2, 0) < 0) {
                                        object5 = null;
                                        break block145;
                                    }
                                    s2 = (short)p.a(byArray[0]);
                                    inputStream = ((kt)object3).a;
                                    n5 = p.a(byArray[1], byArray[2], byArray[3], byArray[4]);
                                    objectArray = new byte[n5];
                                    objectArray2 = objectArray;
                                    if (j.a(inputStream, objectArray2, 0) < 0) {
                                        object5 = null;
                                        break block145;
                                    }
                                    object2[n3] = (byte)new kr(s2, (byte[])objectArray);
                                    ++n3;
                                }
                                ((ks)object).c = (kr[])object2;
                            }
                            object5 = object4 = object;
                        }
                    }
                }
                if (object5 == null) {
                    if (--n2 <= 0) {
                        this.g = true;
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
                object4 = throwable;
                throwable.printStackTrace();
                if (--n2 <= 0) {
                    this.g = true;
                } else {
                    try {
                        Thread.sleep(1000L);
                    }
                    catch (Throwable throwable2) {}
                }
                object4 = null;
            }
            object3 = object4;
            object4 = this;
            if (object3 == null) {
                cw.a("[PAT] Get NULL packet on SocketReader.process()");
                continue;
            }
            if (((kw)object4).c == null) {
                throw new NullPointerException("Main listener can't be NULL");
            }
            try {
                int n6 = ((ks)object3).b;
                block4 : switch (n6) {
                    case 0: {
                        n6 = ((ks)object3).a((short)0, (byte)-1);
                        object = ((ks)object3).d((short)1);
                        switch (n6) {
                            case 7: {
                                if (((kw)object4).d == null) continue block103;
                                ((kw)object4).d.a();
                                break;
                            }
                            case 3: {
                                ((kw)object4).c.u();
                                break;
                            }
                            case 8: {
                                ((kw)object4).c.b((String)object);
                                break;
                            }
                            default: {
                                ((kw)object4).c.a(n6, (String)object);
                                break;
                            }
                        }
                        continue block103;
                    }
                    case 1: {
                        kq.a().c = 3;
                        break;
                    }
                    case 2: {
                        kq.a().a(((ks)object3).d((short)3));
                        break;
                    }
                    case 5: {
                        super.c((ks)object3);
                        break;
                    }
                    case 3: {
                        object4 = ((ks)object3).c((short)2);
                        object = kq.a().d;
                        String string = kq.a().e;
                        byte[] byArray = y.a((byte[])object4, string);
                        kq.a().a((String)object, byArray, (byte[])object4);
                        break;
                    }
                    case 8: {
                        super.f((ks)object3);
                        break;
                    }
                    case 9: {
                        byte by2 = ((ks)object3).a((short)134, (byte)-1);
                        n6 = by2;
                        if (by2 > 0) {
                            lf lf2 = super.a((ks)object3);
                            ((kw)object4).c.a(lf2, (byte)n6);
                            break;
                        }
                        super.b((ks)object3);
                        break;
                    }
                    case 30: {
                        super.r((ks)object3);
                        break;
                    }
                    case 29: {
                        String string = ((ks)object3).d((short)9);
                        String string2 = ((ks)object3).d((short)20);
                        s2 = ((ks)object3).a((short)21, 0, -1, 0);
                        ((kw)object4).c.a(string, string2, (int)s2);
                        break;
                    }
                    case 10: {
                        super.b((ks)object3);
                        ((kw)object4).c.S();
                        break;
                    }
                    case 27: {
                        ((kw)object4).c.S();
                        break;
                    }
                    case 36: {
                        super.D((ks)object3);
                        break;
                    }
                    case 42: {
                        super.s((ks)object3);
                        break;
                    }
                    case 37: {
                        byte by3 = ((ks)object3).a((short)89, (byte)-1);
                        if (by3 == 0) {
                            super.b((ks)object3);
                            break;
                        }
                        if (by3 != 1 && by3 != 2) continue block103;
                        objectArray = new String[((ks)object3).b((short)83)];
                        n6 = 0;
                        while (n6 < objectArray.length) {
                            objectArray[n6] = ((ks)object3).b(((ks)object3).b((short)83, n6));
                            ++n6;
                        }
                        if (by3 == 1) {
                            ((kw)object4).c.c((String[])objectArray);
                            break;
                        }
                        ((kw)object4).c.d((String[])objectArray);
                        break;
                    }
                    case 48: {
                        String string = ((ks)object3).d((short)83);
                        int n7 = ((ks)object3).c((short)114, -1);
                        int n8 = ((ks)object3).c((short)106, -1);
                        ((kw)object4).c.b(string, n7, n8);
                        break;
                    }
                    case 11: {
                        super.d((ks)object3);
                        break;
                    }
                    case 15: {
                        super.e((ks)object3);
                        break;
                    }
                    case 43: {
                        super.g((ks)object3);
                        break;
                    }
                    case 6: {
                        n6 = ((ks)object3).c((short)4, 0);
                        int n9 = ((ks)object3).a(((ks)object3).b((short)7, 0), -1);
                        if (n9 < 0) {
                            int n10 = ((ks)object3).a(((ks)object3).b((short)6, 0), -1);
                            int n11 = ((ks)object3).a(((ks)object3).b((short)5, 0), -1);
                            if (((kw)object4).e == null) continue block103;
                            ((kw)object4).e.a(n6, n10, n11);
                            break;
                        }
                        byte[] byArray = ((ks)object3).c((short)8);
                        if (((kw)object4).e == null) continue block103;
                        ((kw)object4).e.a(n6, n9, byArray);
                        break;
                    }
                    case 13: {
                        String string = ((ks)object3).d((short)20);
                        int n12 = p.c(((ks)object3).c((short)21));
                        byte by4 = ((ks)object3).c((short)22)[0];
                        ((kw)object4).c.a(string, n12, (int)by4);
                        break;
                    }
                    case 16: {
                        String string = ((ks)object3).d((short)9);
                        objectArray = ((ks)object3).d((short)1);
                        ((kw)object4).c.b(string, (String)objectArray);
                        break;
                    }
                    case 17: {
                        String string = ((ks)object3).d((short)28);
                        object = ((ks)object3).d((short)9);
                        String string3 = ((ks)object3).d((short)1);
                        lf lf3 = super.a((ks)object3, 0, -1);
                        super.a((ks)object3, 0, -1).b = object;
                        long l2 = ((ks)object3).a((short)132, 0L);
                        byte by5 = ((ks)object3).a((short)167, (byte)0);
                        n3 = ((ks)object3).a((short)169, (byte)0);
                        ((kw)object4).c.a(lf3, string3, l2, string, by5 > 0, n3 > 0);
                        break;
                    }
                    case 22: {
                        super.k((ks)object3);
                        break;
                    }
                    case 18: {
                        n6 = ((ks)object3).c((short)41, 0);
                        object = new no(n6, 6);
                        boolean bl2 = ((ks)object3).a((short)32);
                        if (((ks)object3).a((short)39)) {
                            ((no)object).G = true;
                        } else {
                            ((no)object).d = bl2;
                        }
                        if (((kw)object4).d == null) continue block103;
                        ((kw)object4).d.a((no)object);
                        break;
                    }
                    case 19: {
                        short s3;
                        n6 = ((ks)object3).c((short)41, 0);
                        object = new no(n6, 0);
                        new no(n6, 0).k = ((ks)object3).a((short)44, 0L);
                        int n13 = ((ks)object3).a(((ks)object3).b((short)33, 0), -1);
                        int n14 = ((ks)object3).a(((ks)object3).b((short)34, 0), -1);
                        int n15 = ((ks)object3).a(((ks)object3).b((short)33, 1), -1);
                        s2 = s3 = ((ks)object3).a(((ks)object3).b((short)34, 1), -1);
                        int n16 = n15;
                        n6 = n14;
                        n3 = n13;
                        Object object6 = object;
                        ((no)object).l = n3;
                        ((no)object6).n = n16;
                        ((no)object6).m = n6;
                        ((no)object6).o = s2;
                        super.a((ks)object3, (no)object);
                        break;
                    }
                    case 24: {
                        cw.a("[processUpdateMatch]======================================");
                        n6 = ((ks)object3).c((short)41, 0);
                        object = new no(n6, 8);
                        kw.b((ks)object3, (no)object);
                        boolean bl3 = ((ks)object3).a((short)32);
                        byte[] byArray = ((ks)object3).c((short)39);
                        if (byArray != null) {
                            ((kw)object4).d.a((no)object);
                            ((no)object).G = true;
                            super.a((ks)object3, byArray[0], ((no)object).b);
                            break;
                        }
                        ((no)object).d = bl3;
                        ((kw)object4).d.a((no)object);
                        break;
                    }
                    case 20: {
                        super.n((ks)object3);
                        break;
                    }
                    case 44: {
                        int n17;
                        n6 = ((ks)object3).c((short)41, 0);
                        object = new no(n6, 1);
                        new no(n6, 1).k = ((ks)object3).a((short)44, 0L);
                        ((no)object).e = n17 = ((ks)object3).c((short)114, 0);
                        super.a((ks)object3, (no)object);
                        break;
                    }
                    case 47: {
                        if (((kw)object4).d == null) continue block103;
                        no no2 = new no(7);
                        new no(7).m = ((ks)object3).c((short)34, 2);
                        no2.l = ((ks)object3).c((short)33, 2);
                        if (((kw)object4).d == null) continue block103;
                        ((kw)object4).d.a(no2);
                        break;
                    }
                    case 84: {
                        n6 = ((ks)object3).c((short)114, -1);
                        int n18 = ((ks)object3).c((short)106, -1);
                        ((kw)object4).c.e(n6, n18);
                        super.b((ks)object3);
                        break;
                    }
                    case 40: {
                        if (((kw)object4).d == null) continue block103;
                        byte[] byArray = ((ks)object3).c((short)39);
                        if (byArray == null) {
                            ((kw)object4).d.a(-1);
                            break;
                        }
                        ((kw)object4).d.a(byArray[0]);
                        super.a((ks)object3, byArray[0], -1);
                        break;
                    }
                    case 25: {
                        String string = ((ks)object3).d((short)9);
                        objectArray = ((ks)object3).d((short)1);
                        if (((kw)object4).d != null) {
                            ((kw)object4).d.a(string, (String)objectArray);
                        }
                        ((kw)object4).c.c(string, (String)objectArray);
                        break;
                    }
                    case 12: {
                        String string = ((ks)object3).d((short)192);
                        object = ((ks)object3).d((short)1);
                        long l3 = ((ks)object3).c((short)157, 0);
                        String string4 = ((ks)object3).d((short)9);
                        byte by6 = ((ks)object3).a((short)40, (byte)-1);
                        switch (by6) {
                            case 0: {
                                ((kw)object4).c.a(string, string4);
                                break block4;
                            }
                            case 1: {
                                ((kw)object4).c.a(string, kw.d((ks)object3, ((ks)object3).b((short)9, 0), -1), (String)object, l3);
                                break block4;
                            }
                            case 2: {
                                ((kw)object4).c.b(string, kw.d((ks)object3, ((ks)object3).b((short)9, 0), -1), (String)object, l3);
                                break block4;
                            }
                            case 3: {
                                super.i((ks)object3);
                            }
                        }
                        break;
                    }
                    case 21: {
                        n6 = ((ks)object3).c((short)41, 0);
                        object = new no(n6, 3);
                        super.a((ks)object3, (no)object);
                        break;
                    }
                    case 28: {
                        ((kw)object4).c.T();
                        break;
                    }
                    case 23: {
                        super.m((ks)object3);
                        break;
                    }
                    case 31: {
                        super.h((ks)object3);
                        break;
                    }
                    case 32: {
                        ((kw)object4).c.y();
                        break;
                    }
                    case 41: {
                        ((kw)object4).c.z();
                        break;
                    }
                    case 33: {
                        super.o((ks)object3);
                        break;
                    }
                    case 38: {
                        kw.p((ks)object3);
                        break;
                    }
                    case 34: {
                        ns.a(new nr(((ks)object3).c((short)80, -1), ((ks)object3).d((short)81), ""));
                        String string = ((ks)object3).d((short)149);
                        ((kw)object4).c.a(string, (byte)0);
                        break;
                    }
                    case 35: {
                        super.q((ks)object3);
                        break;
                    }
                    case 45: {
                        ((kw)object4).c.c(((ks)object3).d((short)1));
                        break;
                    }
                    case 46: {
                        kp kp2 = ((kw)object4).c;
                        ((ks)object3).d((short)9);
                        kp2.a(((ks)object3).a((short)132, 0L));
                        break;
                    }
                    case 55: {
                        super.t((ks)object3);
                        break;
                    }
                    case 56: {
                        super.w((ks)object3);
                        break;
                    }
                    case 57: {
                        super.u((ks)object3);
                        break;
                    }
                    case 7: {
                        n6 = 0;
                        byte by7 = ((ks)object3).a((short)147, (byte)-1);
                        switch (by7) {
                            case 0: {
                                n6 = ((ks)object3).a((short)165, (byte)-1);
                                ((kw)object4).c.c(n6 == 1);
                                break block4;
                            }
                            case 1: {
                                n6 = ((ks)object3).a((short)166, (byte)-1);
                                ((kw)object4).c.d(n6 == 1);
                            }
                        }
                        break;
                    }
                    case 125: {
                        ((kw)object4).c.a(((ks)object3).d((short)149), (byte)0);
                        break;
                    }
                    case 52: {
                        super.x((ks)object3);
                        break;
                    }
                    case 128: {
                        ((kw)object4).c.s(((ks)object3).d((short)1));
                        break;
                    }
                    case 129: {
                        byte by3 = ((ks)object3).a((short)147, (byte)-1);
                        n6 = by3;
                        switch (by3) {
                            case 0: {
                                ((kw)object4).c.a(((ks)object3).d((short)162), ((ks)object3).c((short)161) != null, ((ks)object3).c((short)163) != null);
                                break block4;
                            }
                            case 1: {
                                ((kw)object4).c.a(((ks)object3).d((short)162), ((ks)object3).c((short)101) != null, ((ks)object3).c((short)161) != null, ((ks)object3).c((short)163) != null);
                                break block4;
                            }
                            case 2: {
                                ((kw)object4).c.b(((ks)object3).d((short)162), ((ks)object3).c((short)101) != null, ((ks)object3).c((short)161) != null, ((ks)object3).c((short)163) != null);
                            }
                        }
                        break;
                    }
                    case 64: {
                        super.j((ks)object3);
                        break;
                    }
                    case 65: {
                        String string = ((ks)object3).d((short)192);
                        object = ((ks)object3).d((short)1);
                        long l4 = ((ks)object3).a((short)157, 0L);
                        int n19 = ((ks)object3).c((short)194, 0);
                        cw.a("[processJoinRoom]  " + (String)object + ":  " + l4);
                        ((kw)object4).c.a(string, (String)object, l4, n19);
                        break;
                    }
                    case 130: {
                        byte[] byArray = ((ks)object3).c((short)176);
                        object = ((ks)object3).c((short)2);
                        ((kw)object4).c.a(byArray, (byte[])object);
                        break;
                    }
                    case 131: {
                        String string = ((ks)object3).d((short)1);
                        if (((kw)object4).c == null) continue block103;
                        ((kw)object4).c.o(string);
                        break;
                    }
                    case 4: {
                        if (((kw)object4).c == null) continue block103;
                        ((kw)object4).c.R();
                        break;
                    }
                    case 51: {
                        lk[] lkArray;
                        n6 = ((ks)object3).c((short)114, 0);
                        object = ((ks)object3).d((short)1);
                        String string = ((ks)object3).d((short)83);
                        if (n6 > 0) {
                            gr.a(n6, 1);
                        }
                        if (object != null) {
                            ((kw)object4).c.w((String)object);
                        }
                        if (string != null) {
                            int n20 = ((ks)object3).b((short)83, 0);
                            int n21 = ((ks)object3).a((short)83, n20);
                            lj lj2 = kw.a((ks)object3, n20, n21, true);
                            ((kw)object4).c.c(lj2);
                        }
                        if ((lkArray = kw.a((ks)object3, 1)).length <= 0) continue block103;
                        ((kw)object4).c.a(lkArray);
                        break;
                    }
                    case 83: {
                        n6 = ((ks)object3).c((short)114, -1);
                        int n22 = ((ks)object3).c((short)106, 0);
                        ((kw)object4).c.f(n6, n22);
                        break;
                    }
                    case 99: {
                        String string = ((ks)object3).d((short)186);
                        object = ((ks)object3).d((short)1);
                        if (cw.b()) {
                            cw.a("[processRequestUpgradeEquipment]  session  " + string + "  message  " + (String)object);
                        }
                        if (((kw)object4).c == null) continue block103;
                        ((kw)object4).c.e(string, (String)object);
                        break;
                    }
                    case 100: {
                        ((ks)object3).d((short)186);
                        byte by9 = ((ks)object3).a((short)187, (byte)0);
                        String string = ((ks)object3).d((short)83);
                        int n23 = ((ks)object3).c((short)114, -1);
                        int n24 = ((ks)object3).c((short)106, -1);
                        long l5 = ((ks)object3).a((short)132, 0L);
                        String string5 = ((ks)object3).d((short)1);
                        n6 = ((ks)object3).a((short)188, (byte)0);
                        if (string != null) {
                            if (by9 == 0) {
                                ((kw)object4).c.a(string, string5, (byte)n6, l5);
                            } else {
                                ((kw)object4).c.b(string, string5, (byte)n6, l5);
                            }
                        }
                        if (n23 <= 0) continue block103;
                        if (by9 == 0) {
                            ((kw)object4).c.a(string5, (byte)n6, l5);
                            break;
                        }
                        ((kw)object4).c.a(n23, n24, string5, (byte)n6, l5);
                        break;
                    }
                    case 101: {
                        super.B((ks)object3);
                        break;
                    }
                    case 96: {
                        String string = ((ks)object3).d((short)186);
                        object = ((ks)object3).d((short)83);
                        String string6 = ((ks)object3).d((short)1);
                        if (cw.b()) {
                            cw.a("[processRequestUpgradeEquipment]  equipKey  " + (String)object + " message" + string6);
                        }
                        if (((kw)object4).c == null) continue block103;
                        ((kw)object4).c.a(string, (String)object, string6);
                        break;
                    }
                    case 97: {
                        ((ks)object3).d((short)186);
                        byte by10 = ((ks)object3).a((short)187, (byte)0);
                        String string = ((ks)object3).d((short)83);
                        int n25 = ((ks)object3).c((short)114, -1);
                        int n26 = ((ks)object3).c((short)106, -1);
                        long l6 = ((ks)object3).a((short)132, 0L);
                        String string7 = ((ks)object3).d((short)1);
                        n6 = ((ks)object3).a((short)188, (byte)0);
                        if (cw.b()) {
                            cw.a("[processModifiedUpgradeEquipment]readyStatus   " + n6);
                        }
                        if (string != null) {
                            if (by10 == 0) {
                                ((kw)object4).c.d(string, string7, (byte)n6, l6);
                            } else {
                                ((kw)object4).c.c(string, string7, (byte)n6, l6);
                            }
                        }
                        if (n25 <= 0) continue block103;
                        if (by10 == 0) {
                            ((kw)object4).c.b(string7, (byte)n6, l6);
                            break;
                        }
                        ((kw)object4).c.b(n25, n26, string7, (byte)n6, l6);
                        break;
                    }
                    case 98: {
                        super.C((ks)object3);
                        break;
                    }
                    case 112: {
                        int n27;
                        String string = ((ks)object3).d((short)83);
                        if (string != null) {
                            ((ks)object3).d((short)175);
                            long l7 = ((ks)object3).a((short)157, 0L);
                            ((kw)object4).c.a(string, l7);
                        }
                        if ((n27 = ((ks)object3).c((short)114, 0)) <= 0) continue block103;
                        ((ks)object3).d((short)175);
                        int n28 = ((ks)object3).c((short)106, 0);
                        long l2 = ((ks)object3).a((short)157, 0L);
                        ((kw)object4).c.a(n27, n28, l2);
                        break;
                    }
                    case 116: {
                        super.z((ks)object3);
                        break;
                    }
                    case 113: {
                        super.y((ks)object3);
                        break;
                    }
                    case 114: {
                        n6 = ((ks)object3).a((short)152, (byte)-1);
                        int n29 = ((ks)object3).c((short)106, 0);
                        cw.a("[processListMarketProducts]catid == " + n6 + "qty ==" + n29);
                        ((kw)object4).c.a(n6, n29, super.v((ks)object3));
                        break;
                    }
                    case 115: {
                        super.A((ks)object3);
                        break;
                    }
                    case 132: {
                        super.E((ks)object3);
                        break;
                    }
                    case 133: {
                        String string = ((ks)object3).d((short)1);
                        ((kw)object4).c.x(string);
                    }
                }
            }
            catch (Throwable throwable) {
                object2 = throwable;
                throwable.printStackTrace();
            }
        }
    }

    private lf a(ks ks2, int n2, int n3) {
        int n4;
        int n5;
        int n6;
        n2 = ks2.a((short)15, 0, -1, (byte)-1);
        lf lf2 = new lf(n2);
        new lf(n2).b = ks2.b(0);
        lf2.c = ks2.d((short)26, 0, -1);
        lf2.f = ks2.a((short)16, 0, -1, (byte)0);
        lf2.g = ks2.a((short)15, 0, -1, (byte)0);
        lf2.G = ks2.a((short)27, 0, -1, 0);
        lf2.e = ks2.a((short)24, 0, -1, (byte)0);
        lf2.H = ks2.a((short)43, 0, -1, 0);
        lf2.O = ks2.a((short)108, 0, -1, 0);
        lf2.P = ks2.a((short)109, 0, -1, 0);
        lf2.s = ks2.a((short)17, 0, -1, 0);
        lf2.r = ks2.a((short)47, 0, -1, 1);
        lf2.u = ks2.a((short)18, 0, -1, 0);
        lf2.t = ks2.a((short)48, 0, -1, 1);
        lf2.w = ks2.a((short)45, 0, -1, 0);
        lf2.v = ks2.a((short)49, 0, -1, 1);
        lf2.U = ks2.d((short)151);
        if (lf2.U == null) {
            lf2.U = "Ch\u01b0a c\u00f3";
        }
        lf2.T = "Ch\u01b0a c\u00f3";
        if (lf2.S == null) {
            lf2.S = lf2.G > 100 && lf2.G <= 200 ? "\u0110\u1ea1i Hi\u1ec7p" : (lf2.G > 200 ? "Chi\u1ebfn V\u01b0\u01a1ng" : "H\u00e0o Ki\u1ec7t");
        }
        lf2.ad = ks2.c((short)160, 0);
        n3 = ks2.a((short)64, 0, -1);
        lf2.E = new lt[n3];
        if (n3 > 0) {
            n6 = ks2.b((short)64, 0, -1);
            n5 = 0;
            while (n5 < n3) {
                n4 = ks2.a((short)64, n6);
                lf2.E[n5] = new lt(ks2.a(n6, -1));
                n6 = n4;
                ++n5;
            }
        }
        lf2.D = new lj[ks2.a((short)83, 0, -1)];
        n6 = ks2.a((short)83, 0);
        n5 = 0;
        while (n5 < lf2.D.length) {
            n4 = ks2.a((short)83, n6);
            lf2.D[n5] = kw.a(ks2, n6, n4, false);
            n6 = n4;
            ++n5;
        }
        n5 = ks2.a((short)90, 0, -1);
        n6 = ks2.a((short)90, 0);
        n4 = 0;
        while (n4 < n5) {
            n3 = ks2.a((short)90, n6);
            int n7 = ks2.a(n6, 0);
            byte by2 = ks2.a((short)91, n6, n3, (byte)0);
            di di2 = new di(n7);
            int n8 = ks2.a((short)93, n6, n3, 0);
            byte[] byArray = ks2.c((short)95, n6, n3);
            di2.d = new dj(n8, byArray);
            di2.f = new dj[]{di2.d};
            n8 = ks2.a((short)96, n6, n3, 0);
            byte[] byArray2 = ks2.c((short)98, n6, n3);
            di2.e = new dj(n8, byArray2);
            switch (by2) {
                case 0: {
                    lf2.W = di2;
                    break;
                }
                case 1: {
                    lf2.X = di2;
                    break;
                }
                case 2: {
                    lf2.Y = di2;
                }
            }
            n6 = n3;
            ++n4;
        }
        return lf2;
    }

    private lf b(ks ks2, int n2, int n3) {
        try {
            int n4;
            int n5;
            int n6;
            int n7;
            int n8;
            int n9;
            byte by2 = ks2.a((short)15, n2, n3, (byte)-1);
            lf lf2 = new lf(by2);
            new lf(by2).b = ks2.b(n2);
            lf2.c = ks2.d((short)26, n2, n3);
            lf2.Q = ks2.c((short)36, n2, n3) != null;
            lf2.f = ks2.a((short)16, n2, n3, (byte)0);
            lf2.g = ks2.a((short)15, n2, n3, (byte)0);
            lf2.G = ks2.a((short)27, n2, n3, 0);
            lf2.aa = ks2.a((short)4, n2, n3, 0);
            lf2.V = ks2.a((short)19, n2, n3, (byte)0);
            lf2.s = ks2.a((short)17, n2, n3, 0);
            lf2.r = ks2.a((short)47, n2, n3, 1);
            lf2.u = ks2.a((short)18, n2, n3, 0);
            lf2.t = ks2.a((short)48, n2, n3, 1);
            lf2.w = ks2.a((short)45, n2, n3, 0);
            lf2.v = ks2.a((short)49, n2, n3, 1);
            lf2.U = ks2.d((short)151);
            if (lf2.U == null) {
                lf2.U = "Ch\u01b0a c\u00f3";
            }
            lf2.T = "Ch\u01b0a c\u00f3";
            if (lf2.S == null) {
                lf2.S = lf2.G > 100 && lf2.G <= 200 ? "\u0110\u1ea1i Hi\u1ec7p" : (lf2.G > 200 ? "Chi\u1ebfn V\u01b0\u01a1ng" : "H\u00e0o Ki\u1ec7t");
            }
            int n10 = ks2.a((short)64, n2, n3);
            lf2.E = new lt[n10];
            if (n10 > 0) {
                n9 = ks2.b((short)64, n2, n3);
                n8 = 0;
                while (n8 < n10) {
                    n7 = ks2.a((short)64, n9);
                    lf2.E[n8] = new lt(ks2.a(n9, -1));
                    lf2.E[n8].b = ks2.d((short)26, n9, n7);
                    lf2.E[n8].d = ks2.d((short)66, n9, n7);
                    lf2.E[n8].f = ks2.a((short)67, n9, n7, -1);
                    lf2.E[n8].e = ks2.a((short)68, n9, n7, -1);
                    n6 = ks2.a((short)69, n9, n7);
                    lf2.E[n8].h = new String[n6];
                    n5 = ks2.b((short)69, n9, n7);
                    n4 = 0;
                    while (n4 < n6) {
                        int n11 = ks2.a((short)69, n5);
                        lf2.E[n8].h[n4] = ks2.b(n5);
                        n5 = n11;
                        ++n4;
                    }
                    n9 = n7;
                    ++n8;
                }
            }
            lf2.D = new lj[ks2.a((short)83, n2, n3)];
            n9 = ks2.a((short)83, n2);
            n8 = 0;
            while (n8 < lf2.D.length) {
                n7 = ks2.a((short)83, n9);
                lf2.D[n8] = kw.a(ks2, n9, n7, false);
                n9 = n7;
                ++n8;
            }
            ks ks3 = ks2;
            kw kw2 = this;
            lf2.F = kw.a(ks3, 0);
            n8 = ks2.a((short)90, n2, n3);
            int n12 = ks2.a((short)90, n2);
            n7 = 0;
            while (n7 < n8) {
                n6 = ks2.a((short)90, n12);
                n5 = ks2.a(n12, 0);
                n4 = ks2.a((short)91, n12, n6, (byte)0);
                di di2 = new di(n5);
                n2 = ks2.a((short)93, n12, n6, 0);
                byte[] byArray = ks2.c((short)95, n12, n6);
                di2.d = new dj(n2, byArray);
                di2.f = new dj[]{di2.d};
                n2 = ks2.a((short)96, n12, n6, 0);
                byArray = ks2.c((short)98, n12, n6);
                di2.e = new dj(n2, byArray);
                switch (n4) {
                    case 0: {
                        lf2.W = di2;
                        break;
                    }
                    case 1: {
                        lf2.X = di2;
                        break;
                    }
                    case 2: {
                        lf2.Y = di2;
                    }
                }
                n12 = n6;
                ++n7;
            }
            return lf2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            MGMIDlet.f().d();
            return null;
        }
    }

    private lf c(ks ks2, int n2, int n3) {
        try {
            int n4;
            int n5;
            int n6;
            lf lf2 = new lf(0);
            new lf(0).b = ks2.b(n2);
            lf2.c = ks2.d((short)26, n2, n3);
            lf2.Q = ks2.c((short)36, n2, n3) != null;
            lf2.f = ks2.a((short)16, n2, n3, (byte)0);
            lf2.g = ks2.a((short)15, n2, n3, (byte)0);
            lf2.G = ks2.a((short)27, n2, n3, 0);
            lf2.aa = ks2.a((short)4, n2, n3, 0);
            lf2.V = ks2.a((short)19, n2, n3, (byte)0);
            lf2.s = ks2.a((short)17, n2, n3, 0);
            lf2.r = ks2.a((short)47, n2, n3, 1);
            lf2.u = ks2.a((short)18, n2, n3, 0);
            lf2.t = ks2.a((short)48, n2, n3, 1);
            lf2.w = ks2.a((short)45, n2, n3, 0);
            lf2.v = ks2.a((short)49, n2, n3, 1);
            lf2.U = ks2.d((short)151);
            if (lf2.U == null) {
                lf2.U = "Ch\u01b0a c\u00f3";
            }
            lf2.T = "Ch\u01b0a c\u00f3";
            if (lf2.S == null) {
                lf2.S = lf2.G > 100 && lf2.G <= 200 ? "\u0110\u1ea1i Hi\u1ec7p" : (lf2.G > 200 ? "Chi\u1ebfn V\u01b0\u01a1ng" : "H\u00e0o Ki\u1ec7t");
            }
            int n7 = ks2.a((short)64, n2, n3);
            lf2.E = new lt[n7];
            if (n7 > 0) {
                n6 = ks2.b((short)64, n2, n3);
                n5 = 0;
                while (n5 < n7) {
                    n4 = ks2.a((short)64, n6);
                    lf2.E[n5] = new lt(ks2.a(n6, -1));
                    n6 = n4;
                    ++n5;
                }
            }
            lf2.D = new lj[ks2.a((short)83, n2, n3)];
            n6 = ks2.a((short)83, n2);
            n5 = 0;
            while (n5 < lf2.D.length) {
                n4 = ks2.a((short)83, n6);
                lf2.D[n5] = kw.a(ks2, n6, n4, false);
                n6 = n4;
                ++n5;
            }
            lf2.F = new lk[ks2.a((short)114, n2, n3)];
            n6 = ks2.a((short)114, n2);
            n5 = 0;
            while (n5 < lf2.F.length) {
                n4 = ks2.a((short)114, n6);
                n7 = ks2.a(n6, 0);
                lf2.F[n5] = new lk(n7);
                lf2.F[n5].g = ks2.a((short)106, n6, n4, 0);
                lf2.F[n5].j = ks2.a((short)4, n6, n4, 0);
                n6 = n4;
                ++n5;
            }
            n5 = ks2.a((short)90, n2, n3);
            n6 = ks2.a((short)90, n2);
            n4 = 0;
            while (n4 < n5) {
                n7 = ks2.a((short)90, n6);
                int n8 = ks2.a(n6, 0);
                n3 = ks2.a((short)91, n6, n7, (byte)0);
                di di2 = new di(n8);
                int n9 = ks2.a((short)93, n6, n7, 0);
                byte[] byArray = ks2.c((short)95, n6, n7);
                di2.d = new dj(n9, byArray);
                di2.f = new dj[]{di2.d};
                n9 = ks2.a((short)96, n6, n7, 0);
                byte[] byArray2 = ks2.c((short)98, n6, n7);
                di2.e = new dj(n9, byArray2);
                switch (n3) {
                    case 0: {
                        lf2.W = di2;
                        break;
                    }
                    case 1: {
                        lf2.X = di2;
                        break;
                    }
                    case 2: {
                        lf2.Y = di2;
                    }
                }
                n6 = n7;
                ++n4;
            }
            return lf2;
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
            MGMIDlet.f().d();
            return null;
        }
    }

    private lf a(ks ks2) {
        int n2;
        int n3;
        int n4;
        int n5;
        byte by2 = ks2.a((short)15, 0, -1, (byte)-1);
        lf lf2 = new lf(by2);
        new lf(by2).b = ks2.d((short)9);
        lf2.c = ks2.d((short)26);
        lf2.g = ks2.a((short)15, (byte)0);
        lf2.f = ks2.a((short)16, (byte)0);
        lf2.G = ks2.c((short)27, 0);
        lf2.s = ks2.c((short)17, 0);
        lf2.r = ks2.c((short)47, 0);
        lf2.u = ks2.c((short)18, 0);
        lf2.t = ks2.c((short)48, 0);
        lf2.h = ks2.c((short)118, 0);
        lf2.j = ks2.c((short)119, 0);
        lf2.i = ks2.c((short)120, 0);
        lf2.k = ks2.c((short)121, 0);
        lf2.l = ks2.c((short)196, 0);
        lf2.m = ks2.c((short)197, 0);
        lf2.n = ks2.c((short)198, 0);
        lf2.o = ks2.c((short)199, 0);
        lf2.p = ks2.c((short)116, 0);
        lf2.q = ks2.c((short)115, 0);
        cw.a("[readFighterInf] addHealth " + lf2.p + " heatlPec  " + lf2.q);
        lf2.J = ks2.c((short)42, 0);
        lf2.H = ks2.c((short)43, 0);
        lf2.I = ks2.c((short)99, 10000);
        lf2.K = ks2.c((short)53, 0);
        lf2.L = ks2.c((short)76, 0);
        lf2.M = ks2.c((short)73, 0);
        lf2.N = ks2.c((short)74, 0);
        lf2.O = ks2.c((short)108, 0);
        lf2.P = ks2.c((short)109, 0);
        lf2.U = ks2.d((short)151);
        if (lf2.U == null) {
            lf2.U = "Ch\u01b0a c\u00f3";
        }
        lf2.T = "Ch\u01b0a c\u00f3";
        if (lf2.S == null) {
            lf2.S = lf2.G > 100 && lf2.G <= 200 ? "\u0110\u1ea1i Hi\u1ec7p" : (lf2.G > 200 ? "Chi\u1ebfn V\u01b0\u01a1ng" : "H\u00e0o Ki\u1ec7t");
        }
        lf2.ad = ks2.c((short)160, 0);
        lf2.ab = ks2.a((short)165, (byte)0) == 1;
        lf2.ac = ks2.a((short)166, (byte)0) == 1;
        int n6 = ks2.b((short)64);
        lf2.E = new lt[n6];
        if (n6 > 0) {
            n5 = ks2.a((short)64, 0);
            n4 = 0;
            while (n4 < n6) {
                n3 = ks2.a((short)64, n5);
                lf2.E[n4] = new lt(ks2.a(n5, -1));
                lf2.E[n4].f = ks2.a((short)67, n5, n3, -1);
                lf2.E[n4].e = ks2.a((short)68, n5, n3, -1);
                n5 = n3;
                ++n4;
            }
        }
        lf2.D = new lj[ks2.b((short)83)];
        n5 = ks2.b((short)83, 0);
        n4 = 0;
        while (n4 < lf2.D.length) {
            n3 = ks2.a((short)83, n5);
            lf2.D[n4] = kw.a(ks2, n5, n3, false);
            n5 = n3;
            ++n4;
        }
        lf2.F = this.l(ks2);
        n4 = ks2.b((short)90);
        n5 = ks2.b((short)90, 0);
        n3 = 0;
        while (n3 < n4) {
            n6 = ks2.a((short)90, n5);
            int n7 = ks2.a(n5, 0);
            n2 = ks2.a((short)91, n5, n6, (byte)0);
            di di2 = new di(n7);
            int n8 = ks2.a((short)93, n5, n6, 0);
            byte[] byArray = ks2.c((short)95, n5, n6);
            di2.d = new dj(n8, byArray);
            di2.f = new dj[]{di2.d};
            n8 = ks2.a((short)96, n5, n6, 0);
            byte[] byArray2 = ks2.c((short)98, n5, n6);
            di2.e = new dj(n8, byArray2);
            switch (n2) {
                case 0: {
                    lf2.W = di2;
                    break;
                }
                case 1: {
                    lf2.X = di2;
                    break;
                }
                case 2: {
                    lf2.Y = di2;
                }
            }
            n5 = n6;
            ++n3;
        }
        n3 = ks2.b((short)158);
        lr[] lrArray = new lr[n3];
        n5 = ks2.b((short)158, 0);
        int n9 = 0;
        while (n9 < n3) {
            n2 = ks2.a((short)158, n5);
            lrArray[n9] = new lr();
            ks2.d((short)158, n5, n2);
            lrArray[n9].a = ks2.a((short)4, n5, n2, 0);
            lrArray[n9].b = ks2.a((short)157, n5, n2, 0L);
            n5 = n2;
            ++n9;
        }
        lf2.ae = lrArray;
        return lf2;
    }

    private void b(ks ks2) {
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9 = ks2.c((short)23, 0);
        String string = ks2.d((short)9);
        if ((n9 & 1) != 0) {
            n8 = ks2.b((short)90);
            n7 = ks2.b((short)90, 0);
            n6 = ks2.a((short)15, (byte)0);
            n5 = ks2.a((short)16, (byte)0);
            di di2 = null;
            di di3 = null;
            di di4 = null;
            n4 = 0;
            while (n4 < n8) {
                n3 = ks2.a((short)90, n7);
                int n10 = ks2.a(n7, 0);
                n2 = ks2.a((short)91, n7, n3, (byte)0);
                di di5 = new di(n10);
                int n11 = ks2.a((short)93, n7, n3, 0);
                byte[] byArray = ks2.c((short)95, n7, n3);
                di5.d = new dj(n11, byArray);
                di5.f = new dj[]{di5.d};
                n11 = ks2.a((short)96, n7, n3, 0);
                byte[] byArray2 = ks2.c((short)98, n7, n3);
                di5.e = new dj(n11, byArray2);
                switch (n2) {
                    case 0: {
                        di2 = di5;
                        break;
                    }
                    case 1: {
                        di3 = di5;
                        break;
                    }
                    case 2: {
                        di4 = di5;
                    }
                }
                n7 = n3;
                ++n4;
            }
            if (this.c != null) {
                this.c.a(string, (byte)n6, (byte)n5, di2, di3, di4);
            }
        }
        if ((n9 & 2) != 0) {
            n8 = ks2.c((short)27, 0);
            n7 = ks2.c((short)118, 0);
            n6 = ks2.c((short)119, 0);
            n5 = ks2.c((short)120, 0);
            int n12 = ks2.c((short)121, 0);
            int n13 = ks2.c((short)196, 0);
            int n14 = ks2.c((short)197, 0);
            n4 = ks2.c((short)198, 0);
            n3 = ks2.c((short)199, 0);
            int n15 = ks2.c((short)116, 0);
            n2 = ks2.c((short)115, 0);
            if (this.c != null) {
                this.c.a(string, n8, n7, n6, n5, n12, n13, n14, n4, n3, n15, n2);
            }
        }
        if ((n9 & 4) != 0) {
            n8 = ks2.c((short)17, 0);
            n7 = ks2.c((short)47, 0);
            n6 = ks2.c((short)42, 0);
            n5 = ks2.c((short)73, 0);
            int n16 = ks2.c((short)74, 0);
            int n17 = ks2.c((short)43, 0);
            int n18 = ks2.c((short)99, 10000);
            if (this.c != null) {
                this.c.a(string, n8, n7, n6, n5, n16, n17, n18);
            }
        }
        if ((n9 & 8) != 0) {
            n8 = ks2.c((short)53, 0);
            n7 = ks2.c((short)76, 0);
            n6 = ks2.c((short)108, 0);
            n5 = ks2.c((short)109, 0);
            int n19 = ks2.c((short)160, 0);
            String string2 = ks2.d((short)151);
            int n20 = ks2.c((short)27, 0);
            if (string2 == null) {
                string2 = "Ch\u01b0a c\u00f3";
            }
            String string3 = "Ch\u01b0a c\u00f3";
            String string4 = n20 > 100 && n20 <= 200 ? "\u0110\u1ea1i Hi\u1ec7p" : (n20 > 200 ? "Chi\u1ebfn V\u01b0\u01a1ng" : "H\u00e0o Ki\u1ec7t");
            if (this.c != null) {
                this.c.a(string, n8, n7, n6, n5, n19, string2, string3, string4);
            }
        }
        if ((n9 & 0x10) != 0) {
            this.c.c(ks2.a((short)165, (byte)0) == 1);
            this.c.d(ks2.a((short)166, (byte)0) == 1);
        }
        if ((n9 & 0x20) != 0) {
            n8 = ks2.b((short)64);
            lt[] ltArray = new lt[n8];
            if (n8 > 0) {
                n6 = ks2.a((short)64, 0);
                n5 = 0;
                while (n5 < n8) {
                    int n21 = ks2.a((short)64, n6);
                    ltArray[n5] = new lt(ks2.a(n6, -1));
                    ltArray[n5].f = ks2.a((short)67, n6, n21, -1);
                    n6 = n21;
                    ++n5;
                }
            }
            if (this.c != null) {
                this.c.a(ltArray);
            }
        }
        if ((n9 & 0x40) != 0) {
            lj[] ljArray = new lj[ks2.b((short)83)];
            int n22 = ks2.b((short)83, 0);
            n6 = 0;
            while (n6 < ljArray.length) {
                n5 = ks2.a((short)83, n22);
                ljArray[n6] = kw.a(ks2, n22, n5, false);
                n22 = n5;
                ++n6;
            }
            if (this.c != null) {
                this.c.a(string, ljArray);
            }
        }
        if ((n9 & 0x100) != 0) {
            int n23 = ks2.b((short)158);
            lr[] lrArray = new lr[n23];
            n6 = ks2.b((short)158, 0);
            n5 = 0;
            while (n5 < n23) {
                int n24 = ks2.a((short)158, n6);
                lrArray[n5] = new lr();
                ks2.d((short)158, n6, n24);
                lrArray[n5].a = ks2.a((short)4, n6, n24, 0);
                lrArray[n5].b = ks2.a((short)157, n6, n24, 0L);
                n6 = n24;
                ++n5;
            }
            if (this.c != null) {
                this.c.a(lrArray);
            }
        }
        if (this.c != null) {
            this.c.S();
        }
    }

    private static lj a(ks ks2, int n2, int n3, boolean bl2) {
        Object object = ks2.b(n2);
        byte by2 = ks2.a((short)84, n2, n3, (byte)0);
        object = new lj((String)object, by2);
        v0.m = ks2.a((short)4, n2, n3, 0);
        ((lj)object).n = ks2.a((short)139, n2, n3, -1);
        ((lj)object).i = ks2.a((short)27, n2, n3, 0);
        if (bl2) {
            ((lj)object).c = ks2.d((short)26, n2, n3);
            ((lj)object).h = ks2.a((short)135, n2, n3, -1);
            ((lj)object).e = ks2.a((short)15, n2, n3, (byte)7);
            ((lj)object).g = ks2.a((short)16, n2, n3, (byte)2);
            ((lj)object).l = ks2.a((short)138, n2, n3, (byte)0);
            ((lj)object).o = ks2.a((short)144, n2, n3, 0);
            ((lj)object).f = ks2.d((short)117, n2, n3);
            ((lj)object).q = ks2.a((short)156, n2, n3, (byte)-1);
            ((lj)object).r = ks2.a((short)85, n2, n3, (byte)1);
            ((lj)object).j = ks2.a((short)190, n2, n3, (byte)-1);
            kz kz2 = new kz();
            new kz().a = ks2.a((short)118, n2, n3, 0);
            kz2.b = ks2.a((short)119, n2, n3, 0);
            kz2.c = ks2.a((short)120, n2, n3, 0);
            kz2.d = ks2.a((short)121, n2, n3, 0);
            kz2.e = ks2.a((short)72, n2, n3, 0);
            kz2.f = ks2.a((short)71, n2, n3, 0);
            kz2.g = ks2.a((short)126, n2, n3, 0);
            kz2.h = ks2.a((short)124, n2, n3, 0);
            kz2.i = ks2.a((short)47, n2, n3, 0);
            kz2.j = ks2.a((short)200, n2, n3, 0);
            kz2.k = ks2.a((short)201, n2, n3, 0);
            kz2.l = ks2.a((short)202, n2, n3, 0);
            kz2.m = ks2.a((short)203, n2, n3, 0);
            kz2.n = ks2.a((short)204, n2, n3, 0);
            kz2.o = ks2.a((short)221, n2, n3, 0);
            ((lj)object).p = kz2;
        }
        return object;
    }

    private void c(ks ks2) {
        byte by2 = ks2.a((short)12, (byte)0);
        String string = ks2.d((short)131);
        int n2 = ks2.c((short)41, -1);
        int n3 = ks2.c((short)13, 0);
        int[] nArray = new int[ks2.b((short)4)];
        int n4 = ks2.b((short)4, 0);
        int n5 = 0;
        while (n5 < nArray.length) {
            nArray[n5] = ks2.a(n4, 0);
            ++n4;
            ++n5;
        }
        this.c.a(by2 == 2, string, n2, nArray, n3);
        n5 = ks2.c((short)170, -1);
        String[] stringArray = new String[ks2.b((short)1)];
        if (stringArray.length > 0) {
            int n6 = ks2.b((short)1, 0);
            n2 = 0;
            while (n2 < stringArray.length) {
                n3 = ks2.a((short)1, n6);
                stringArray[n2] = ks2.b(n6);
                n6 = n3;
                ++n2;
            }
        }
        this.c.a(n5, stringArray);
    }

    private void d(ks ks2) {
        try {
            jl jl2;
            byte by2 = ks2.a((short)12, (byte)0);
            Object object = ks2.d((short)20);
            if (by2 == 0) {
                jl jl3;
                int n2;
                jl[] jlArray = new jl[ks2.b((short)21)];
                int n3 = ks2.b((short)21, 0);
                int n4 = 0;
                while (n4 < jlArray.length) {
                    n2 = ks2.a((short)21, n3);
                    jl3 = new jl();
                    new jl().c = ks2.a(n3, -1);
                    jl3.b = ks2.d((short)26, n3, n2);
                    jl3.a = ks2.a((short)22, n3, n2, (byte)0);
                    jl3.d = ks2.a((short)102, n3, n2, 0);
                    jl3.e = ks2.a((short)103, n3, n2, 0);
                    jl3.f = ks2.a((short)104, n3, n2, 0);
                    jl3.g = ks2.a((short)105, n3, n2, 0);
                    jl3.h = ks2.a((short)101, n3, n2, (byte)0) == 1;
                    jl3.i = ks2.a((short)4, n3, n2, 0);
                    jlArray[n4] = jl3;
                    n3 = n2;
                    ++n4;
                }
                n4 = 0;
                while (n4 < jlArray.length - 1) {
                    n2 = n4 + 1;
                    while (n2 < jlArray.length) {
                        if (jlArray[n4].c > jlArray[n2].c) {
                            jl3 = jlArray[n4];
                            jlArray[n4] = jlArray[n2];
                            jlArray[n2] = jl3;
                        }
                        ++n2;
                    }
                    ++n4;
                }
                this.c.a((String)object, jlArray);
                return;
            }
            a a2 = new a();
            jm jm2 = new jm();
            new jm().a = object;
            jm2.b = ks2.d((short)26);
            jm2.c = ks2.c((short)41, 0);
            jm2.d = ks2.c((short)56, 0);
            jm2.e = ks2.c((short)57, 0);
            jm2.f = ks2.c((short)58, 0);
            jm2.g = ks2.c((short)59, 0);
            jm2.j = ks2.c((short)55);
            jm2.k = ks2.c((short)54);
            jm2.l = ks2.c((short)61);
            jm2.m = ks2.c((short)60, 0);
            jm2.h = ks2.c((short)63, 0);
            jm2.i = ks2.c((short)29, 0);
            a2.a(new Integer(jm2.m));
            a2.a(new Integer(jm2.h));
            a2.a(new Integer(jm2.i));
            jl[] jlArray = new jl[ks2.b((short)21)];
            int n5 = ks2.b((short)21, 0);
            int n6 = 0;
            while (n6 < jlArray.length) {
                int n7 = ks2.a((short)21, n5);
                jl2 = new jl();
                new jl().c = ks2.a(n5, -1);
                jl2.b = ks2.d((short)26, n5, n7);
                jl2.a = ks2.a((short)22, n5, n7, (byte)0);
                jl2.d = ks2.a((short)102, n5, n7, 0);
                jl2.e = ks2.a((short)103, n5, n7, 0);
                jl2.f = ks2.a((short)104, n5, n7, 0);
                jl2.g = ks2.a((short)105, n5, n7, 0);
                jl2.h = ks2.a((short)101, n5, n7, (byte)0) == 1;
                jl2.i = ks2.a((short)4, n5, n7, 0);
                if (jl2.i != 0) {
                    a2.a(new Integer(jl2.i));
                }
                jlArray[n6] = jl2;
                n5 = n7;
                ++n6;
            }
            n6 = 0;
            while (n6 < jlArray.length - 1) {
                int n8 = n6 + 1;
                while (n8 < jlArray.length) {
                    if (jlArray[n6].c > jlArray[n8].c) {
                        jl2 = jlArray[n6];
                        jlArray[n6] = jlArray[n8];
                        jlArray[n8] = jl2;
                    }
                    ++n8;
                }
                ++n6;
            }
            jm2.n = jlArray;
            n6 = ks2.a(ks2.b((short)6, 0), 0);
            object = new int[a2.d()];
            int n9 = 0;
            while (n9 < ((Object)object).length) {
                object[n9] = (Integer)a2.b(n9);
                ++n9;
            }
            int[] nArray = new int[ks2.b((short)154)];
            if (nArray.length > 0) {
                n5 = ks2.b((short)154, 0);
                int n10 = 0;
                while (n10 < nArray.length) {
                    int n11 = ks2.a((short)154, n5);
                    nArray[n10] = ks2.a(n5, 0);
                    n5 = n11;
                    ++n10;
                }
            }
            int n12 = ks2.a(ks2.b((short)6, 1), 0);
            this.c.a(jm2, (int[])object, n6, nArray, n12);
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            return;
        }
    }

    private final void e(ks ks2) {
        try {
            jl jl2;
            int n2;
            Object object = ks2.d((short)20);
            int n3 = ks2.a((short)12, (byte)0);
            jl[] jlArray = new jl[ks2.b((short)21)];
            int n4 = ks2.b((short)21, 0);
            int n5 = 0;
            while (n5 < jlArray.length) {
                n2 = ks2.a((short)21, n4);
                jl2 = new jl();
                new jl().c = ks2.a(n4, -1);
                jl2.b = ks2.d((short)26, n4, n2);
                jl2.a = ks2.a((short)22, n4, n2, (byte)0);
                jl2.d = ks2.a((short)102, n4, n2, 0);
                jl2.e = ks2.a((short)103, n4, n2, 0);
                jl2.f = ks2.a((short)104, n4, n2, 0);
                jl2.g = ks2.a((short)105, n4, n2, 0);
                jl2.h = ks2.a((short)101, n4, n2, (byte)0) == 1;
                jl2.i = ks2.a((short)4, n4, n2, 0);
                jlArray[n5] = jl2;
                n4 = n2;
                ++n5;
            }
            n5 = 0;
            while (n5 < jlArray.length - 1) {
                n2 = n5 + 1;
                while (n2 < jlArray.length) {
                    if (jlArray[n5].c > jlArray[n2].c) {
                        jl2 = jlArray[n5];
                        jlArray[n5] = jlArray[n2];
                        jlArray[n2] = jl2;
                    }
                    ++n2;
                }
                ++n5;
            }
            if (n3 == 0) {
                this.c.a((String)object, jlArray);
                return;
            }
            a a2 = new a();
            jm jm2 = new jm();
            new jm().a = object;
            jm2.b = ks2.d((short)26);
            jm2.c = ks2.c((short)41, 0);
            jm2.d = ks2.c((short)56, 0);
            jm2.e = ks2.c((short)57, 0);
            jm2.f = ks2.c((short)58, 0);
            jm2.g = ks2.c((short)59, 0);
            jm2.j = ks2.c((short)55);
            jm2.k = ks2.c((short)54);
            jm2.l = ks2.c((short)61);
            jm2.m = ks2.c((short)60, 0);
            jm2.h = ks2.c((short)63, 0);
            jm2.i = ks2.c((short)29, 0);
            a2.a(new Integer(jm2.m));
            a2.a(new Integer(jm2.h));
            a2.a(new Integer(jm2.i));
            jm2.n = jlArray;
            int n6 = ks2.a(ks2.b((short)6, 0), 0);
            object = new int[a2.d()];
            n3 = 0;
            while (n3 < ((Object)object).length) {
                object[n3] = (Integer)a2.b(n3);
                ++n3;
            }
            int[] nArray = new int[ks2.b((short)154)];
            if (nArray.length > 0) {
                n4 = ks2.b((short)154, 0);
                int n7 = 0;
                while (n7 < nArray.length) {
                    int n8 = ks2.a((short)154, n4);
                    nArray[n7] = ks2.a(n4, 0);
                    n4 = n8;
                    ++n7;
                }
            }
            int n9 = ks2.a(ks2.b((short)6, 1), 0);
            this.c.a(jm2, (int[])object, n6, nArray, n9);
            return;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            cw.b("processMapInfo()");
            return;
        }
    }

    private void f(ks ks2) {
        Object[] objectArray;
        int n2;
        int n3;
        int n4;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        int n9 = 0;
        int n10 = 0;
        int n11 = ks2.b((short)90);
        di[] diArray = new di[n11];
        byte[] byArray = new byte[n11];
        int n12 = ks2.b((short)90, 0);
        int n13 = 0;
        while (n13 < n11) {
            n4 = ks2.a((short)90, n12);
            n3 = ks2.a(n12, 0);
            byArray[n13] = ks2.a((short)91, n12, n4, (byte)0);
            di di2 = new di(n3);
            new di(n3).b = ks2.d((short)92, n12, n4);
            di2.c = ks2.a((short)16, n12, n4, (byte)0);
            n2 = ks2.a((short)93, n12, n4, 0);
            String string = ks2.d((short)94, n12, n4);
            objectArray = ks2.c((short)95, n12, n4);
            di2.d = new dj(n2, (byte[])objectArray);
            di2.d.b = string;
            di2.e = di2.d;
            di2.f = new dj[ks2.a((short)96, n12, n4)];
            di2.f[0] = di2.d;
            n12 = ks2.a((short)96, n12);
            int n14 = 1;
            int n15 = 0;
            while (n15 < di2.f.length) {
                int n16 = ks2.a((short)96, n12);
                int n17 = ks2.a(n12, 0);
                String string2 = ks2.d((short)97, n12, n16);
                byte[] byArray2 = ks2.c((short)98, n12, n16);
                if (n17 != n2) {
                    di2.f[n14] = new dj(n17, byArray2);
                    di2.f[n14].b = string2;
                    ++n14;
                }
                n12 = n16;
                ++n15;
            }
            if (di2.c == 0) {
                switch (byArray[n13]) {
                    case 0: {
                        ++n6;
                        break;
                    }
                    case 1: {
                        ++n5;
                        break;
                    }
                    case 2: {
                        ++n7;
                    }
                }
            } else {
                switch (byArray[n13]) {
                    case 0: {
                        ++n9;
                        break;
                    }
                    case 1: {
                        ++n8;
                        break;
                    }
                    case 2: {
                        ++n10;
                    }
                }
            }
            diArray[n13] = di2;
            n12 = n4;
            ++n13;
        }
        n13 = 0;
        n4 = 0;
        n3 = 0;
        int n18 = 0;
        n2 = 0;
        int n19 = 0;
        objectArray = new di[n5];
        di[] diArray2 = new di[n6];
        di[] diArray3 = new di[n7];
        di[] diArray4 = new di[n8];
        di[] diArray5 = new di[n9];
        di[] diArray6 = new di[n10];
        int n20 = 0;
        while (n20 < diArray.length) {
            if (diArray[n20].c == 0) {
                switch (byArray[n20]) {
                    case 0: {
                        diArray2[n4++] = diArray[n20];
                        break;
                    }
                    case 1: {
                        objectArray[n13++] = diArray[n20];
                        break;
                    }
                    case 2: {
                        diArray3[n3++] = diArray[n20];
                    }
                }
            } else {
                switch (byArray[n20]) {
                    case 0: {
                        diArray5[n2++] = diArray[n20];
                        break;
                    }
                    case 1: {
                        diArray4[n18++] = diArray[n20];
                        break;
                    }
                    case 2: {
                        diArray6[n19++] = diArray[n20];
                    }
                }
            }
            ++n20;
        }
        this.c.a(diArray2, (di[])objectArray, diArray3, diArray5, diArray4, diArray6);
    }

    private void g(ks ks2) {
        String string = ks2.d((short)20);
        byte by2 = ks2.a((short)40, (byte)3);
        jn[] jnArray = new jn[ks2.b((short)9)];
        int n2 = ks2.b((short)9, 0);
        int n3 = 0;
        while (n3 < jnArray.length) {
            int n4 = ks2.a((short)9, n2);
            jn jn2 = new jn();
            new jn().a = ks2.b(n2);
            jn2.b = ks2.d((short)26, n2, n4);
            jn2.d = ks2.a((short)27, n2, n4, 0);
            jn2.c = ks2.a((short)15, n2, n4, (byte)0);
            jn2.e = ks2.a((short)129, n2, n4, 0);
            jn2.f = ks2.a((short)106, n2, n4, 0);
            jn2.g = ks2.a((short)107, n2, n4, (byte)0);
            jnArray[n3] = jn2;
            n2 = n4;
            ++n3;
        }
        switch (by2) {
            case 0: {
                this.c.b(jnArray, string);
                return;
            }
            case 1: {
                this.c.c(jnArray, string);
                return;
            }
            case 3: {
                this.c.a(jnArray, string);
            }
        }
    }

    private void h(ks ks2) {
        nq[] nqArray = new nq[ks2.b((short)77)];
        int n2 = ks2.b((short)77, 0);
        int n3 = 0;
        while (n3 < nqArray.length) {
            int n4 = ks2.a((short)77, n2);
            String string = ks2.b(n2);
            String string2 = ks2.d((short)26, n2, n4);
            nqArray[n3] = new nq(string, string2, "");
            n2 = n4;
            ++n3;
        }
        this.c.a(nqArray);
    }

    private void i(ks ks2) {
        String string = ks2.d((short)192);
        int n2 = ks2.b((short)9);
        if (n2 > 0) {
            dq[] dqArray = new dq[n2];
            int n3 = ks2.b((short)9, 0);
            int n4 = 0;
            try {
                n4 = 0;
                while (n4 < dqArray.length) {
                    int n5 = ks2.a((short)9, n3);
                    dqArray[n4] = kw.d(ks2, n3, n5);
                    n3 = n5;
                    ++n4;
                }
                this.c.a(string, dqArray);
                return;
            }
            catch (OutOfMemoryError outOfMemoryError) {
                ks2.c = null;
                System.gc();
            }
        }
    }

    private void j(ks ks2) {
        byte by2 = ks2.a((short)193, (byte)0);
        int n2 = ks2.b((short)192, 0);
        int n3 = ks2.b((short)192);
        lp[] lpArray = new lp[n3];
        int n4 = 0;
        while (n4 < n3) {
            int n5;
            int n6 = n5 = ks2.a((short)192, n2);
            int n7 = n2;
            ks ks3 = ks2;
            String string = ks3.b(n7);
            String string2 = ks3.d((short)26, n7, n6);
            int n8 = ks3.a((short)106, n7, n6, 0);
            String string3 = ks3.d((short)1, n7, n6);
            byte by3 = ks3.a((short)101, n7, n6, (byte)0);
            n2 = ks3.a((short)143, n7, n6, (byte)0);
            lpArray[n4] = new lp(string, string2, string3, n8, by3, (byte)n2);
            n2 = n5;
            ++n4;
        }
        if (by2 != 0) {
            this.c.b(lpArray);
            return;
        }
        this.c.a(lpArray);
    }

    private static dq d(ks ks2, int n2, int n3) {
        dq dq2 = new dq();
        new dq().a = ks2.b(n2);
        dq2.b = ks2.a((short)27, n2, n3, 0);
        dq2.c = ks2.a((short)24, n2, n3, (byte)0);
        dq2.d = "Ch\u01b0a c\u00f3";
        dq2.f = ks2.a((short)132, n2, n3, 0);
        return dq2;
    }

    private void k(ks ks2) {
        cw.a("[processPrepareData]======================================");
        lf lf2 = null;
        lf lf3 = null;
        byte by2 = ks2.a((short)111, (byte)0);
        byte by3 = ks2.a((short)140, (byte)0);
        boolean bl2 = false;
        int n2 = ks2.b((short)9, 0);
        int n3 = 0;
        while (n3 < 2) {
            int n4 = ks2.a((short)9, n2);
            lf lf4 = this.c(ks2, n2, n4);
            if (by3 != 9) {
                if (lf4.b.equals(gr.e)) {
                    lf2 = lf4;
                    if (lf3 == null) {
                        bl2 = true;
                    }
                } else {
                    lf3 = lf4;
                }
            } else if (lf2 == null) {
                lf2 = lf4;
            } else {
                lf3 = lf4;
            }
            n2 = n4;
            ++n3;
        }
        kq.a().f = ks2.d((short)28);
        byte[] byArray = ks2.c((short)30);
        byte[] byArray2 = null;
        byte[] byArray3 = null;
        int n5 = ks2.b((short)35);
        int n6 = 0;
        while (n6 < n5) {
            byte[] byArray4 = ks2.a(ks2.b((short)35, n6));
            if (byArray2 == null) {
                byArray2 = byArray4;
            } else {
                byArray3 = byArray4;
            }
            ++n6;
        }
        n6 = ks2.a((short)70, (byte)0);
        this.c.a(lf2, lf3, bl2, byArray, byArray2, byArray3, n6, by2, by3);
    }

    private lk[] l(ks ks2) {
        return kw.a(ks2, 0);
    }

    private static lk[] a(ks ks2, int n2) {
        lk[] lkArray = new lk[ks2.b((short)114) - n2];
        n2 = ks2.b((short)114, n2);
        int n3 = 0;
        while (n3 < lkArray.length) {
            int n4 = ks2.a((short)114, n2);
            int n5 = ks2.a(n2, 0);
            lkArray[n3] = new lk(n5);
            lkArray[n3].b = ks2.d((short)26, n2, n4);
            lkArray[n3].d = ks2.d((short)117, n2, n4);
            lkArray[n3].g = ks2.a((short)106, n2, n4, 0);
            lkArray[n3].e = ks2.a((short)122, n2, n4, (byte)-1);
            lkArray[n3].f = ks2.a((short)123, n2, n4, (byte)-1);
            lkArray[n3].j = ks2.a((short)4, n2, n4, 0);
            lkArray[n3].h = ks2.a((short)145, n2, n4, 0);
            lkArray[n3].i = ks2.a((short)106, n2, n4, 0);
            lkArray[n3].l = ks2.a((short)82, n2, n4, -1);
            lkArray[n3].k = ks2.a((short)132, n2, n4, 0L);
            lkArray[n3].m = ks2.a((short)85, n2, n4, (byte)1);
            n2 = n4;
            ++n3;
        }
        return lkArray;
    }

    private void m(ks ks2) {
        int n2;
        int n3 = ks2.c((short)41, 0);
        byte[] byArray = ks2.c((short)30);
        byte[] byArray2 = ks2.c((short)35);
        byte[] byArray3 = ks2.a(ks2.b((short)35, 1));
        lf[] lfArray = new lf[1];
        lf[] lfArray2 = new lf[1];
        int n4 = ks2.b((short)9, 0);
        int n5 = 0;
        while (n5 < 2) {
            n2 = ks2.a((short)9, n4);
            lf lf2 = this.b(ks2, n4, n2);
            if (op.o != 9) {
                if (lf2.b.equals(gr.e)) {
                    lfArray[0] = lf2;
                } else {
                    lfArray2[0] = lf2;
                }
            } else if (lfArray[0] == null) {
                lfArray[0] = lf2;
            } else {
                lfArray2[0] = lf2;
            }
            n4 = n2;
            ++n5;
        }
        int n6 = n2 = ks2.c((short)32) != null ? 1 : 0;
        if (this.d != null) {
            this.d.a(byArray, byArray2, byArray3, lfArray, lfArray2, n2 != 0, n3);
        }
    }

    private void a(ks ks2, no no2) {
        if (this.d == null) {
            return;
        }
        int n2 = ks2.b((short)35);
        no2.g = new byte[n2][];
        int n3 = 0;
        while (n3 < n2) {
            byte[] byArray = ks2.a(ks2.b((short)35, n3));
            if (byArray != null) {
                no2.g[n3] = byArray;
            }
            ++n3;
        }
        Object[] objectArray = ks2.c((short)30);
        if (objectArray != null) {
            no2.h = objectArray;
        }
        kw.b(ks2, no2);
        int n4 = ks2.b((short)37);
        if (n4 > 0) {
            Object object = new int[n4];
            objectArray = new int[n4];
            int n5 = ks2.b((short)37, 0);
            int n6 = 0;
            while (n6 < n4) {
                int n7 = ks2.a((short)37, n5);
                int n8 = ks2.a(n5, -1);
                n5 = ks2.a((short)38, n5, n7, -1);
                object[n6] = n8;
                objectArray[n6] = n5;
                n5 = n7;
                ++n6;
            }
            Object[] objectArray2 = objectArray;
            objectArray = (Object[])object;
            object = no2;
            no2.i = objectArray;
            object.j = objectArray2;
        }
        no2.F = ks2.a((short)52, (byte)0);
        no2.H = ks2.a((short)172, (byte)0);
        no2.E = ks2.a((short)133, (byte)0);
        n2 = ks2.c((short)32) != null ? 1 : 0;
        no2.a = ks2.d((short)62);
        objectArray = ks2.c((short)39);
        if (objectArray != null) {
            this.d.a(no2);
            no2.G = true;
            this.a(ks2, objectArray[0], no2.b);
            return;
        }
        no2.d = n2;
        this.d.a(no2);
    }

    private static void b(ks ks2, no no2) {
        int n2 = ks2.b((short)9);
        int n3 = ks2.b((short)9, 0);
        no2.f = new nj[n2];
        int n4 = 0;
        while (n4 < n2) {
            int n5 = ks2.a((short)9, n3);
            String string = ks2.b(n3);
            int n6 = ks2.a((short)46, n3, n5, -1);
            byte by2 = ks2.a((short)19, n3, n5, (byte)0);
            int n7 = ks2.a((short)17, n3, n5, -1);
            int n8 = ks2.a((short)18, n3, n5, -1);
            n3 = ks2.a((short)45, n3, n5, -1);
            no2.f[n4] = new nj(string, n6, n7, n8, n3, by2);
            n3 = n5;
            cw.a("" + no2.f[n4]);
            ++n4;
        }
    }

    private void n(ks ks2) {
        int n2;
        int n3 = ks2.c((short)41, 0);
        no no2 = new no(n3, 2);
        new no(n3, 2).k = ks2.a((short)44, 0L);
        int n4 = ks2.a(ks2.b((short)64, 0), -1);
        int n5 = ks2.a(ks2.b((short)75, 0), 0);
        byte[] byArray = new byte[]{};
        byte[] byArray2 = new byte[]{};
        int n6 = ks2.b((short)50);
        if (n6 > 0) {
            byArray = new byte[n6];
            byArray2 = new byte[n6];
            int n7 = ks2.b((short)50, 0);
            int n8 = 0;
            while (n8 < n6) {
                n2 = ks2.a((short)50, n7);
                byArray[n8] = (byte)ks2.a(n7, -1);
                byArray2[n8] = (byte)ks2.a((short)51, n7, n2, -1);
                n7 = n2;
                ++n8;
            }
        }
        byte[] byArray3 = new byte[]{};
        byte[] byArray4 = new byte[]{};
        n2 = ks2.b((short)33);
        if (n2 > 0) {
            byArray3 = new byte[n2];
            byArray4 = new byte[n2];
            n6 = ks2.b((short)33, 0);
            int n9 = 0;
            while (n9 < n2) {
                int n10 = ks2.a((short)33, n6);
                byArray3[n9] = (byte)ks2.a(n6, -1);
                byArray4[n9] = (byte)ks2.a((short)34, n6, n10, -1);
                n6 = n10;
                ++n9;
            }
        }
        byte[] byArray5 = byArray3;
        byte[] byArray6 = byArray4;
        byArray4 = byArray2;
        byArray3 = byArray;
        byte[] byArray7 = byArray6;
        byArray2 = byArray5;
        int n11 = n5;
        n5 = n4;
        no no3 = no2;
        no2.p = n5;
        no3.t = n11;
        no3.u = byArray2;
        no3.q = byArray7;
        no3.r = byArray4;
        no3.s = byArray3;
        this.a(ks2, no2);
    }

    private void a(ks object, byte by2, int n2) {
        no no2 = new no(n2, 5);
        int n3 = ((ks)object).c((short)42, 0);
        int n4 = ((ks)object).c((short)43, 0);
        int n5 = ((ks)object).c((short)110, 0);
        int[] nArray = new int[((ks)object).b((short)73)];
        int[] nArray2 = new int[nArray.length];
        int n6 = 0;
        while (n6 < nArray2.length) {
            nArray[n6] = ((ks)object).a(((ks)object).b((short)73, n6), -1);
            nArray2[n6] = ((ks)object).a(((ks)object).b((short)74, n6), -1);
            ++n6;
        }
        lj[] ljArray = new lj[((ks)object).b((short)83)];
        int n7 = ((ks)object).b((short)83, 0);
        int n8 = 0;
        while (n8 < ljArray.length) {
            int n9 = ((ks)object).a((short)83, n7);
            ljArray[n8] = kw.a((ks)object, n7, n9, true);
            n7 = n9;
            ++n8;
        }
        lk[] lkArray = object;
        object = this;
        lk[] lkArray2 = kw.a((ks)lkArray, 0);
        if (this.d != null) {
            int n10 = n5;
            lkArray = lkArray2;
            boolean bl2 = false;
            n5 = n4;
            n4 = n10;
            no no3 = no2;
            no2.v = by2;
            no3.C = ljArray;
            no3.x = n4;
            no3.w = n3;
            no3.y = n5;
            no3.z = 0;
            no3.A = nArray;
            no3.B = nArray2;
            no3.D = lkArray;
            this.d.a(no2);
        }
    }

    private void o(ks object) {
        Object object2 = ((ks)object).d((short)77);
        String string = ((ks)object).d((short)26);
        String string2 = ((ks)object).d((short)79);
        boolean bl2 = ((ks)object).a((short)100, (byte)0) == 0;
        object2 = new nq((String)object2, string, string2);
        int n2 = ((ks)object).b((short)80);
        if (n2 > 0) {
            nr[] nrArray = new nr[n2];
            int n3 = ((ks)object).b((short)80, 0);
            int n4 = 0;
            while (n4 < nrArray.length) {
                int n5 = ((ks)object).a((short)80, n3);
                int n6 = ((ks)object).a(n3, -1);
                String string3 = ((ks)object).d((short)81, n3, n5);
                nrArray[n4] = new nr(n6, string3, null);
                n3 = n5;
                ++n4;
            }
            object = object2;
            ((nq)object2).d = nrArray;
        }
        this.c.a((nq)object2, bl2);
    }

    private static void p(ks object) {
        Object object2 = ((ks)object).d((short)77);
        object2 = new nq((String)object2, "", "");
        int n2 = ((ks)object).b((short)80);
        if (n2 > 0) {
            nr[] nrArray = new nr[n2];
            int n3 = ((ks)object).b((short)80, 0);
            int n4 = 0;
            while (n4 < nrArray.length) {
                int n5 = ((ks)object).a((short)80, n3);
                int n6 = ((ks)object).a(n3, -1);
                String string = ((ks)object).d((short)81, n3, n5);
                nrArray[n4] = new nr(n6, string, null);
                n3 = n5;
                ++n4;
            }
            object = object2;
            ((nq)object2).d = nrArray;
        }
        ns.b((nq)object2);
    }

    private void q(ks ks2) {
        Object object = ks2.d((short)77);
        String[] stringArray = ks2.d((short)26);
        String[] stringArray2 = new String[ks2.b((short)1)];
        int n2 = ks2.b((short)1, 0);
        int n3 = 0;
        while (n3 < stringArray2.length) {
            int n4 = ks2.a((short)1, n2);
            stringArray2[n3] = ks2.b(n2);
            n2 = n4;
            ++n3;
        }
        nq nq2 = new nq((String)object, (String)stringArray, "");
        stringArray = stringArray2;
        object = nq2;
        nq2.e = stringArray;
        ns.a(nq2);
        String string = ks2.d((short)149);
        this.c.a(string, (byte)0);
    }

    private void r(ks ks2) {
        int n2 = ks2.b((short)64);
        lu[] luArray = new lu[n2];
        int n3 = ks2.b((short)64, 0);
        int n4 = 0;
        while (n4 < n2) {
            int n5;
            int n6 = ks2.a((short)64, n3);
            luArray[n4] = new lu(ks2.a(n3, 0));
            luArray[n4].b = ks2.d((short)26, n3, n6);
            luArray[n4].d = ks2.a((short)136, n3, n6, 0);
            lv[] lvArray = new lv[ks2.a((short)67, n3, n6)];
            n3 = ks2.a((short)67, n3);
            int n7 = 0;
            while (n7 < lvArray.length) {
                n5 = ks2.a((short)67, n3);
                lvArray[n7] = new lv(luArray[n4].a);
                lvArray[n7].a = ks2.a(n3, 0);
                lvArray[n7].e = ks2.d((short)66, n3, n5);
                lvArray[n7].c = ks2.a((short)76, n3, n5, 0);
                lvArray[n7].b = ks2.a((short)135, n3, n5, 0);
                lvArray[n7].d = ks2.a((short)68, n3, n5, 0);
                n3 = n5;
                ++n7;
            }
            n7 = 0;
            while (n7 < lvArray.length) {
                n5 = n7 + 1;
                while (n5 < lvArray.length) {
                    if (lvArray[n7].a > lvArray[n5].a) {
                        lv lv2 = lvArray[n7];
                        lvArray[n7] = lvArray[n5];
                        lvArray[n5] = lv2;
                    }
                    ++n5;
                }
                ++n7;
            }
            luArray[n4].c = lvArray;
            n3 = n6;
            ++n4;
        }
        this.c.a(luArray);
    }

    private void s(ks ks2) {
        int n2;
        ks2.d((short)9);
        lj[] ljArray = new lj[ks2.b((short)83)];
        if (ljArray.length > 0) {
            int n3 = ks2.b((short)83, 0);
            int n4 = 0;
            while (n4 < ljArray.length) {
                n2 = ks2.a((short)83, n3);
                if (n2 < 0) {
                    n2 = ks2.a((short)114, n3);
                }
                ljArray[n4] = kw.a(ks2, n3, n2, true);
                n3 = n2;
                ++n4;
            }
        }
        ks ks3 = ks2;
        lk[] lkArray = this;
        lkArray = kw.a(ks3, 0);
        int n5 = ks2.c((short)86, 0);
        n2 = ks2.c((short)145, 0);
        this.c.a(ljArray, lkArray, n5, n2);
    }

    private void t(ks ks2) {
        int n2 = ks2.a((short)147, (byte)-1);
        switch (n2) {
            case 0: {
                String string = ks2.d((short)9);
                String string2 = ks2.d((short)150);
                kq.a().h.a(string2);
                this.c.k(string);
                return;
            }
            case 1: {
                String string = ks2.d((short)9);
                String string3 = ks2.d((short)150);
                byte by2 = ks2.a((short)31, (byte)-1);
                if (by2 == 0) {
                    this.c.m(string);
                    return;
                }
                this.c.n(string);
                kq.a().g = string3;
                kq.j = 0;
                return;
            }
            case 4: {
                Object object = ks2.d((short)9);
                kq.j = ks2.c((short)41, 0);
                if (((String)object).equals(kq.a().d)) {
                    this.c.I();
                    return;
                }
                int n3 = ks2.c((short)106, -1);
                String string = ks2.d((short)83);
                if (n3 > 0) {
                    object = kw.a(ks2, ks2.b((short)83, 0), -1, true);
                    this.c.b((lj)object);
                    return;
                }
                this.c.j(string);
                return;
            }
            case 3: {
                Object object = ks2.d((short)9);
                kq.j = ks2.c((short)41, 0);
                if (((String)object).equals(kq.a().d)) {
                    this.c.I();
                    return;
                }
                ks ks3 = ks2;
                object = this;
                lk[] lkArray = kw.a(ks3, 0);
                int n4 = ks2.c((short)106, -1);
                if (n4 > 0) {
                    this.c.a(lkArray[0], n4);
                    return;
                }
                this.c.a(lkArray[0]);
                return;
            }
            case 2: {
                String string = ks2.d((short)9);
                kq.j = ks2.c((short)41, 0);
                if (string.equals(kq.a().d)) {
                    this.c.I();
                    return;
                }
                long l2 = ks2.a((short)132, 0L);
                this.c.l((int)l2);
                return;
            }
            case 5: {
                String string = ks2.d((short)9);
                this.c.i(string);
                return;
            }
            case 6: {
                int n5 = (int)ks2.a((short)132, -1L);
                lj[] ljArray = new lj[ks2.b((short)83)];
                if (ljArray.length > 0) {
                    n2 = ks2.b((short)83, 0);
                    int n6 = 0;
                    while (n6 < ljArray.length) {
                        int n7 = ks2.a((short)83, n2);
                        ljArray[n6] = kw.a(ks2, n2, n7, true);
                        n2 = n7;
                        ++n6;
                    }
                }
                ks ks4 = ks2;
                lk[] lkArray = this;
                lkArray = kw.a(ks4, 0);
                this.c.a(ljArray, lkArray, n5);
                return;
            }
            case 7: {
                this.c.O();
                return;
            }
            case 8: {
                this.c.N();
                return;
            }
            case 9: {
                String string = ks2.d((short)9);
                this.c.l(string);
            }
        }
    }

    private void u(ks ks2) {
        int n2 = ks2.b((short)147);
        int[] nArray = new int[n2];
        String[] stringArray = new String[n2];
        if (nArray.length > 0) {
            int n3 = ks2.b((short)147, 0);
            int n4 = 0;
            while (n4 < nArray.length) {
                int n5 = ks2.a((short)147, n3);
                byte by2 = ks2.a(n3, (byte)0);
                String string = ks2.d((short)168, n3, n5);
                nArray[n4] = by2;
                stringArray[n4] = string;
                n3 = n5;
                ++n4;
            }
        }
        this.c.a(nArray, stringArray);
    }

    private static kz e(ks ks2, int n2, int n3) {
        kz kz2 = new kz();
        new kz().a = ks2.a((short)118, n2, n3, 0);
        kz2.b = ks2.a((short)119, n2, n3, 0);
        kz2.c = ks2.a((short)120, n2, n3, 0);
        kz2.d = ks2.a((short)121, n2, n3, 0);
        kz2.e = ks2.a((short)72, n2, n3, 0);
        kz2.f = ks2.a((short)71, n2, n3, 0);
        kz2.g = ks2.a((short)126, n2, n3, 0);
        kz2.h = ks2.a((short)124, n2, n3, 0);
        kz2.i = ks2.a((short)47, n2, n3, 0);
        kz2.j = ks2.a((short)200, n2, n3, 0);
        kz2.k = ks2.a((short)201, n2, n3, 0);
        kz2.l = ks2.a((short)202, n2, n3, 0);
        kz2.m = ks2.a((short)203, n2, n3, 0);
        kz2.n = ks2.a((short)204, n2, n3, 0);
        kz2.o = ks2.a((short)221, n2, n3, 0);
        return kz2;
    }

    private lo[] v(ks ks2) {
        lo[] loArray = new lo[ks2.b((short)175)];
        int n2 = ks2.b((short)175, 0);
        int n3 = 0;
        while (n3 < loArray.length) {
            int n4 = ks2.a((short)175, n2);
            lo lo2 = new lo();
            new lo().b = ks2.b(n2);
            lo2.c = ks2.a((short)159, n2, n4, (byte)-1);
            lo2.f = ks2.d((short)62, n2, n4);
            lo2.d = ks2.a((short)145, n2, n4, -1);
            lo2.g = ks2.a((short)157, n2, n4, 0L);
            cw.a("[readAllProductsInMarket]=== availableTime===" + lo2.g);
            switch (lo2.c) {
                case 0: {
                    byte by2 = ks2.a((short)84, n2, n4, (byte)0);
                    Object object = new lj("", by2);
                    new lj("", by2).a = ks2.a(n2, 0);
                    ((lj)object).m = ks2.a((short)4, n2, n4, 0);
                    ((lj)object).i = ks2.a((short)27, n2, n4, 0);
                    ((lj)object).k = ks2.a((short)145, n2, n4, 0);
                    ((lj)object).c = ks2.d((short)26, n2, n4);
                    ((lj)object).h = ks2.a((short)135, n2, n4, -1);
                    ((lj)object).e = ks2.a((short)15, n2, n4, (byte)7);
                    ((lj)object).g = ks2.a((short)16, n2, n4, (byte)0);
                    ((lj)object).l = ks2.a((short)138, n2, n4, (byte)0);
                    ((lj)object).n = ks2.a((short)139, n2, n4, 0);
                    ((lj)object).o = ks2.a((short)144, n2, n4, 0);
                    ((lj)object).f = ks2.d((short)117, n2, n4);
                    ((lj)object).j = ks2.a((short)190, n2, n4, (byte)-1);
                    ((lj)object).r = ks2.a((short)85, n2, n4, (byte)1);
                    ((lj)object).p = kw.e(ks2, n2, n4);
                    lo2.e = object;
                    break;
                }
                case 1: {
                    int n5 = ks2.a((short)114, n2, n4, -1);
                    Object object = new lk(n5);
                    new lk(n5).b = ks2.d((short)26, n2, n4);
                    ((lb)object).d = ks2.d((short)117, n2, n4);
                    ((lk)object).g = ks2.a((short)106, n2, n4, 0);
                    ((lk)object).e = ks2.a((short)122, n2, n4, (byte)-1);
                    ((lk)object).f = ks2.a((short)123, n2, n4, (byte)-1);
                    ((lk)object).j = ks2.a((short)4, n2, n4, 0);
                    ((lk)object).h = ks2.a((short)145, n2, n4, 0);
                    ((lk)object).i = ks2.a((short)106, n2, n4, 0);
                    ((lk)object).l = ks2.a((short)82, n2, n4, -1);
                    ((lk)object).k = ks2.a((short)132, n2, n4, 0L);
                    ((lk)object).m = ks2.a((short)85, n2, n4, (byte)1);
                    lo2.e = object;
                    break;
                }
                case 99: {
                    int n6 = ks2.a((short)155, n2, n4, -1);
                    Object object = new ls(n6);
                    new ls(n6).a = ks2.d((short)26, n2, n4);
                    ((ls)object).b = ks2.d((short)1, n2, n4);
                    ((ls)object).c = ks2.a((short)145, n2, n4, 0);
                    lo2.e = object;
                }
            }
            loArray[n3] = lo2;
            if (cw.a()) {
                cw.a("[MARKET] Parsing " + lo2);
            }
            n2 = n4;
            ++n3;
        }
        return loArray;
    }

    private void w(ks loArray) {
        int n2 = loArray.a((short)147, (byte)-1);
        switch (n2) {
            case 0: {
                ld[] ldArray = new ld[loArray.b((short)152)];
                if (ldArray.length > 0) {
                    int n3 = loArray.b((short)152, 0);
                    int n4 = 0;
                    while (n4 < ldArray.length) {
                        int n5 = loArray.a((short)152, n3);
                        byte by2 = loArray.a(n3, (byte)-1);
                        String string = loArray.d((short)26, n3, n5);
                        n3 = loArray.a((short)106, n3, n5, 0);
                        ldArray[n4] = new ld(by2, string, n3);
                        n3 = n5;
                        ++n4;
                    }
                }
                this.c.a(ldArray);
                return;
            }
            case 1: {
                byte by3 = loArray.a((short)152, (byte)-1);
                ks ks2 = loArray;
                loArray = this;
                loArray = new lo[ks2.b((short)153)];
                int n6 = ks2.b((short)153, 0);
                int n7 = 0;
                while (n7 < loArray.length) {
                    int n8 = ks2.a((short)153, n6);
                    lo lo2 = new lo();
                    new lo().a = ks2.a(n6, -1);
                    lo2.c = ks2.a((short)159, n6, n8, (byte)-1);
                    lo2.d = ks2.a((short)145, n6, n8, -1);
                    switch (lo2.c) {
                        case 0: {
                            byte by4 = ks2.a((short)84, n6, n8, (byte)0);
                            Object object = new lj("", by4);
                            new lj("", by4).a = ks2.a(n6, 0);
                            ((lj)object).m = ks2.a((short)4, n6, n8, 0);
                            ((lj)object).k = ks2.a((short)145, n6, n8, 0);
                            ((lj)object).i = ks2.a((short)27, n6, n8, 0);
                            ((lj)object).c = ks2.d((short)26, n6, n8);
                            ((lj)object).h = ks2.a((short)135, n6, n8, -1);
                            ((lj)object).e = ks2.a((short)15, n6, n8, (byte)7);
                            ((lj)object).g = ks2.a((short)16, n6, n8, (byte)0);
                            ((lj)object).l = ks2.a((short)138, n6, n8, (byte)0);
                            ((lj)object).o = ks2.a((short)144, n6, n8, 0);
                            ((lj)object).f = ks2.d((short)117, n6, n8);
                            ((lj)object).j = ks2.a((short)190, n6, n8, (byte)-1);
                            ((lj)object).r = ks2.a((short)85, n6, n8, (byte)1);
                            ((lj)object).p = kw.e(ks2, n6, n8);
                            lo2.e = object;
                            break;
                        }
                        case 1: {
                            int n9 = ks2.a((short)114, n6, n8, -1);
                            Object object = new lk(n9);
                            new lk(n9).b = ks2.d((short)26, n6, n8);
                            ((lb)object).d = ks2.d((short)117, n6, n8);
                            ((lk)object).g = ks2.a((short)106, n6, n8, 0);
                            ((lk)object).e = ks2.a((short)122, n6, n8, (byte)-1);
                            ((lk)object).f = ks2.a((short)123, n6, n8, (byte)-1);
                            ((lk)object).j = ks2.a((short)4, n6, n8, 0);
                            ((lk)object).h = ks2.a((short)145, n6, n8, 0);
                            ((lk)object).i = ks2.a((short)106, n6, n8, 0);
                            ((lk)object).l = ks2.a((short)82, n6, n8, -1);
                            ((lk)object).k = ks2.a((short)132, n6, n8, 0L);
                            ((lk)object).m = ks2.a((short)85, n6, n8, (byte)1);
                            lo2.e = object;
                            break;
                        }
                        case 99: {
                            int n10 = ks2.a((short)155, n6, n8, -1);
                            Object object = new ls(n10);
                            new ls(n10).a = ks2.d((short)26, n6, n8);
                            ((ls)object).b = ks2.d((short)1, n6, n8);
                            ((ls)object).c = ks2.a((short)145, n6, n8, 0);
                            lo2.e = object;
                        }
                    }
                    loArray[n7] = lo2;
                    n6 = n8;
                    ++n7;
                }
                this.c.a((int)by3, loArray);
                return;
            }
            case 2: {
                int n11;
                int[] nArray;
                Object[] objectArray;
                n2 = loArray.b((short)114);
                if (n2 > 0) {
                    objectArray = new int[n2];
                    nArray = new int[n2];
                    n11 = 0;
                    while (n11 < n2) {
                        objectArray[n11] = loArray.a(loArray.b((short)114, n11), -1);
                        nArray[n11] = loArray.a(loArray.b((short)106, n11), -1);
                        ++n11;
                    }
                    this.c.a((int[])objectArray, nArray);
                }
                if ((n2 = loArray.b((short)83)) <= 0) break;
                objectArray = new String[n2];
                nArray = new int[n2];
                n11 = 0;
                while (n11 < n2) {
                    objectArray[n11] = (int)loArray.b(loArray.b((short)83, n11));
                    nArray[n11] = loArray.a(loArray.b((short)146, n11), -1);
                    ++n11;
                }
                this.c.a((String[])objectArray, nArray);
            }
        }
    }

    private void x(ks ks2) {
        int n2;
        int n3;
        int n4 = ks2.b((short)9);
        dk[] dkArray = new dk[n4];
        if (n4 > 0) {
            n3 = ks2.b((short)9, 0);
            n2 = 0;
            while (n2 < dkArray.length) {
                n4 = ks2.a((short)9, n3);
                int n5 = ks2.a((short)148, n3, n4, 0);
                String string = ks2.d((short)151, n3, n4);
                String string2 = ks2.b(n3);
                String string3 = ks2.d((short)1, n3, n4);
                dkArray[n2] = new dk(n5, string, string2, string3);
                n3 = n4;
                ++n2;
            }
        }
        n3 = 0;
        while (n3 < dkArray.length - 1) {
            n2 = n3 + 1;
            while (n2 < dkArray.length) {
                if (dkArray[n3].a > dkArray[n2].a) {
                    dk dk2 = dkArray[n3];
                    dkArray[n3] = dkArray[n2];
                    dkArray[n2] = dk2;
                }
                ++n2;
            }
            ++n3;
        }
        this.c.a(dkArray);
    }

    private void y(ks object) {
        String string = ((ks)object).d((short)175);
        lj[] ljArray = new lj[((ks)object).b((short)83)];
        if (ljArray.length > 0) {
            int n2 = ((ks)object).b((short)83, 0);
            int n3 = 0;
            while (n3 < ljArray.length) {
                int n4 = ((ks)object).a((short)83, n2);
                if (n4 < 0) {
                    n4 = ((ks)object).a((short)114, n2);
                }
                ljArray[n3] = kw.a((ks)object, n2, n4, true);
                n2 = n4;
                ++n3;
            }
        }
        lk[] lkArray = object;
        object = this;
        lkArray = kw.a((ks)lkArray, 0);
        this.c.a(string, ljArray, lkArray);
    }

    private void z(ks ks2) {
        ld[] ldArray = new ld[ks2.b((short)152)];
        if (ldArray.length > 0) {
            int n2 = ks2.b((short)152, 0);
            int n3 = 0;
            while (n3 < ldArray.length) {
                int n4 = ks2.a((short)152, n2);
                byte by2 = ks2.a(n2, (byte)-1);
                String string = ks2.d((short)26, n2, n4);
                n2 = ks2.a((short)106, n2, n4, 0);
                ldArray[n3] = new ld(by2, string, n2);
                n2 = n4;
                ++n3;
            }
        }
        this.c.b(ldArray);
    }

    private void A(ks object) {
        String string = ((ks)object).d((short)175);
        lj[] ljArray = new lj[((ks)object).b((short)83)];
        if (ljArray.length > 0) {
            int n2 = ((ks)object).b((short)83, 0);
            int n3 = 0;
            while (n3 < ljArray.length) {
                int n4 = ((ks)object).a((short)83, n2);
                if (n4 < 0) {
                    n4 = ((ks)object).a((short)114, n2);
                }
                ljArray[n3] = kw.a((ks)object, n2, n4, true);
                n2 = n4;
                ++n3;
            }
        }
        lk[] lkArray = object;
        object = this;
        lkArray = kw.a((ks)lkArray, 0);
        this.c.b(string, ljArray, lkArray);
    }

    private void B(ks object) {
        String string = ((ks)object).d((short)186);
        byte by2 = ((ks)object).a((short)189, (byte)-1);
        lj[] ljArray = new lj[((ks)object).b((short)83)];
        lk[] lkArray = new lk[114];
        if (ljArray.length > 0) {
            int n2 = ((ks)object).b((short)83, 0);
            int n3 = 0;
            while (n3 < ljArray.length) {
                int n4 = ((ks)object).a((short)83, n2);
                ljArray[n3] = kw.a((ks)object, n2, n4, true);
                n2 = n4;
                ++n3;
            }
        }
        if (lkArray.length > 0) {
            lkArray = object;
            object = this;
            lkArray = kw.a((ks)lkArray, 0);
        }
        this.c.a(string, ljArray, lkArray, by2);
    }

    private void C(ks object) {
        String string = ((ks)object).d((short)186);
        byte by2 = ((ks)object).a((short)189, (byte)-1);
        lj[] ljArray = new lj[((ks)object).b((short)83)];
        lk[] lkArray = new lk[114];
        if (ljArray.length > 0) {
            int n2 = ((ks)object).b((short)83, 0);
            int n3 = 0;
            while (n3 < ljArray.length) {
                int n4 = ((ks)object).a((short)83, n2);
                ljArray[n3] = kw.a((ks)object, n2, n4, true);
                n2 = n4;
                ++n3;
            }
        }
        if (lkArray.length > 0) {
            lkArray = object;
            object = this;
            lkArray = kw.a((ks)lkArray, 0);
        }
        this.c.b(string, ljArray, lkArray, by2);
    }

    private void D(ks ks2) {
        int n2;
        ks2.d((short)9);
        int n3 = ks2.b((short)83);
        String[] stringArray = new String[n3];
        int[] nArray = new int[n3];
        int[] nArray2 = new int[n3];
        n3 = ks2.b((short)114);
        int[] nArray3 = new int[n3];
        int[] nArray4 = new int[n3];
        int n4 = ks2.b((short)83, 0);
        int n5 = 0;
        while (n5 < stringArray.length) {
            n2 = ks2.a((short)83, n4);
            stringArray[n5] = ks2.b(n4);
            nArray[n5] = ks2.a((short)139, n4, n2, 0);
            nArray2[n5] = ks2.a((short)144, n4, n2, 0);
            n4 = n2;
            ++n5;
        }
        n4 = ks2.b((short)114, 0);
        n5 = 0;
        while (n5 < nArray3.length) {
            n2 = ks2.a((short)114, n4);
            nArray3[n5] = ks2.a(n4, 0);
            nArray4[n5] = ks2.a((short)106, n4, n2, 0);
            n4 = n2;
            ++n5;
        }
        if (this.c != null) {
            this.c.a(stringArray, nArray, nArray2, nArray3, nArray4);
        }
    }

    private void E(ks object) {
        String string = ((ks)object).d((short)182);
        String[] stringArray = ((ks)object).d((short)183);
        object = ((ks)object).d((short)1);
        stringArray = l.b((String)stringArray, ";");
        String[] stringArray2 = null;
        String[] stringArray3 = null;
        if (stringArray != null && stringArray.length > 0 && stringArray.length % 2 == 0) {
            stringArray2 = new String[stringArray.length / 2];
            stringArray3 = new String[stringArray.length / 2];
            int n2 = 0;
            while (n2 < stringArray.length) {
                stringArray3[n2 / 2] = stringArray[n2];
                stringArray2[n2 / 2] = stringArray[n2 + 1];
                n2 += 2;
            }
        }
        this.c.b((String)object, string, stringArray3, stringArray2);
    }

    public final void a() {
        this.g = true;
        if (this.f != null) {
            kt kt2 = this.f;
            try {
                kt2.a.close();
            }
            catch (Throwable throwable) {}
            this.f = null;
        }
    }
}

