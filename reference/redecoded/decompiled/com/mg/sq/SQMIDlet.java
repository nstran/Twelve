/*
 * Decompiled with CFR 0.152.
 */
package com.mg.sq;

import com.mg.smsgame.MGMIDlet;
import com.mg.sq.a;

public class SQMIDlet
extends MGMIDlet {
    public SQMIDlet() {
        super(true, "SQOL", "OLA", "17537", "42");
    }

    protected final void e() {
        v.m = new byte[]{-17, 96, 78, -104, 8, -54, -4, 110, -50, -101, -42, 50, -31, -19, -88, -93};
        ct.a(false, 1);
        v.e = i.c(0L).get(1);
        v.f = i.a.getTimeZone().getRawOffset();
        pd.m();
        v.l = false;
        v.U = false;
        v.N = false;
        v.p = 6;
        v.q = new int[]{3, 3, 1000, 3, 3, 3};
        v.G = true;
        v.aa = 3;
        ba.a = 20;
        al.a = 750;
        v.Q = 50;
        go.b = pd.y();
        go.a = pd.w();
        go.d = pd.v();
        gr.j = pd.n();
        v.g = false;
        ag.a(new ig());
    }

    protected final void f() {
        Object object;
        v.a(0xF0FBFF);
        v.am = 0xF0FBFF;
        v.al = 0xFFFFFF;
        v.ak = 15698432;
        if (v.a()) {
            ff.q();
        } else if (v.L == 2) {
            ff.a();
        }
        bx.a.c(true);
        bx.a.a(6290199);
        bx.d = new by();
        bx.c = bx.e = new if();
        bx.b = bx.e;
        bx.a(bx.e);
        bk.a(0xFFFFFF);
        v.a(480, 480, true);
        ag.a(new a());
        ag.b().a(new oc());
        if (v.ai) {
            v.b = true;
        }
        if (!pd.D()) {
            ag.b().f(6);
            object = ag.b().a("Ch\u00fa \u00fd", "B\u1ea1n n\u00ean c\u00e0i Game v\u00e0o b\u1ed9 nh\u1edb m\u00e1y (kh\u00f4ng n\u00ean c\u00e0i v\u00e0o th\u1ebb nh\u1edb), mu\u1ed1n Game ch\u1ea1y nhanh h\u01a1n b\u1ea1n ph\u1ea3i \u0111\u1ec3 b\u1ed9 nh\u1edb c\u1ee7a m\u00e1y 5Mb tr\u1edf l\u00ean.", "\u0110\u00f3ng", 2, 1);
            ((am)object).a(ag.b());
            ag.b().a((al)object, false);
        } else {
            ag.b().f(5);
        }
        if (!pd.B()) {
            com.mg.sq.a.a(gs.a);
            pd.A();
        }
        ks.a().a(com.mg.sq.a.s());
        com.mg.sq.a.q();
        object = Runtime.getRuntime();
        long l2 = ((Runtime)object).totalMemory() / 1024L;
        if (l2 <= 2048L && v.ah) {
            if (ct.b()) {
                ct.a("[SYSTEM] Detect low memory device: " + l2 + "Kb");
            }
            gr.r = true;
        }
        od.d();
    }

    protected void destroyApp(boolean bl2) {
        pa.a().b();
        super.destroyApp(bl2);
    }
}

