/*
 * Decompiled with CFR 0.152.
 */
public final class kj
extends jv {
    public final void a(ki ki2, kl kl2, byte[][] byArray, kh kh2, boolean bl2) {
        if (ki2 != null) {
            ki ki3;
            ki2.i();
            switch (ki2.a()) {
                case 0: {
                    Object object;
                    if (kl2 != null && bl2 && ki2.m() && (kl2.j == 1 || kl2.j == 0)) {
                        ki3 = ki2;
                        object = ki3.e;
                        kl kl3 = kl2;
                        if (kl3.t.a((k)object) && kl2.m()) {
                            int n2 = ki2.e.a > kl2.t.a ? 2 : 3;
                            ki2.a(1, n2);
                            kl2.b(n2 == 2 ? 8 : 4);
                            return;
                        }
                    }
                    ki2.b(ki2.d * ki.a[ki2.c], ki2.d * ki.b[ki2.c]);
                    object = byArray;
                    ki ki4 = ki2;
                    int n3 = (ki4.o() + ki4.q() - 5) / 32;
                    ki3 = ki4;
                    int n4 = (ki4.n() + (ki3.c == 2 ? 0 : ki4.e.c)) / 32;
                    if (object[n3][n4] == false) break;
                    ki3 = ki4;
                    ki4.a(0, ki3.c == 2 ? 3 : 2);
                }
            }
            if (kh2.a(ki2)) {
                int n5;
                ki3 = ki2;
                if (ki3.c == 2) {
                    n5 = 3;
                } else {
                    ki3 = ki2;
                    if (ki3.c == 3) {
                        n5 = 2;
                    } else {
                        ki3 = ki2;
                        n5 = ki3.c == 0 ? 1 : 0;
                    }
                }
                ki2.a(0, n5);
            }
        }
    }

    public final void a(int n2, kl kl2) {
    }

    public final void a(int n2) {
    }

    public final void a(at at2, kh kh2) {
    }
}

