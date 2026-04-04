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

    protected final void g() {
        z.k = new byte[]{-17, 96, 78, -104, 8, -54, -4, 110, -50, -101, -42, 50, -31, -19, -88, -93};
        cw.a(true, 0);
        l.c(0L).get(1);
        l.a.getTimeZone().getRawOffset();
        pa.m();
        z.j = false;
        z.R = false;
        z.K = false;
        z.n = 6;
        z.o = new int[]{3, 3, 1000, 3, 3, 3};
        z.E = true;
        z.W = 3;
        be.a = 20;
        ap.a = 750;
        z.N = 50;
        gr.b = pa.y();
        gr.a = pa.w();
        gr.d = pa.v();
        gp.j = pa.n();
        z.e = false;
        ak.a(new if());
    }

    protected final void h() {
        Object object;
        z.a(0xF0FBFF);
        z.af = 0xF0FBFF;
        z.ae = 0xFFFFFF;
        z.ad = 15698432;
        if (z.a()) {
            ff.q();
        } else if (z.J == 2) {
            ff.a();
        }
        ca.a.c(true);
        ca.a.a(6290199);
        ca.d = new cb();
        ca.c = ca.e = new ie();
        ca.b = ca.e;
        ca.a(ca.e);
        bo.a(0xFFFFFF);
        z.a(480, 480, true);
        ak.a(new a());
        ak.b().a(new oa());
        if (z.ab) {
            z.b = true;
        }
        if (!pa.D()) {
            ak.b().f(6);
            object = ak.b().a("Ch\u00fa \u00fd", "B\u1ea1n n\u00ean c\u00e0i Game v\u00e0o b\u1ed9 nh\u1edb m\u00e1y (kh\u00f4ng n\u00ean c\u00e0i v\u00e0o th\u1ebb nh\u1edb), mu\u1ed1n Game ch\u1ea1y nhanh h\u01a1n b\u1ea1n ph\u1ea3i \u0111\u1ec3 b\u1ed9 nh\u1edb c\u1ee7a m\u00e1y 5Mb tr\u1edf l\u00ean.", "\u0110\u00f3ng", 2, 1);
            ((aq)object).a(ak.b());
            ak.b().a((ap)object, false);
        } else {
            ak.b().f(5);
        }
        if (!pa.B()) {
            com.mg.sq.a.a(new go().a);
            pa.A();
        }
        kq.a().a(com.mg.sq.a.q());
        com.mg.sq.a.o();
        object = Runtime.getRuntime();
        long l2 = ((Runtime)object).totalMemory() / 1024L;
        if (l2 <= 2048L && z.aa) {
            if (cw.b()) {
                cw.a("[SYSTEM] Detect low memory device: " + l2 + "Kb");
            }
            gp.r = true;
        }
        ob.d();
    }

    protected void destroyApp(boolean bl2) {
        ox.a().b();
        super.destroyApp(bl2);
    }
}

