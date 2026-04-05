/*
 * Decompiled with CFR 0.152.
 */
public final class ki
extends ju {
    public final void a(kh kh2, kk ax2, byte[][] byArray, kg kg2, boolean bl2) {
        if (kh2 != null) {
            kh2.i();
            switch (kh2.c()) {
                case 0: {
                    if (ax2 != null && bl2 && kh2.m() && (((kk)ax2).i == 1 || ((kk)ax2).i == 0) && ((kk)ax2).a(kh2.d()) && ax2.m()) {
                        int n2 = kh2.e.a > ((kk)ax2).s.a ? 2 : 3;
                        kh2.a(1, n2);
                        ((kk)ax2).b(n2 == 2 ? 8 : 4);
                        return;
                    }
                    kh2.b(kh2.d * kh.a[kh2.c], kh2.d * kh.b[kh2.c]);
                    ax2 = kh2;
                    int n2 = (ax2.o() + ax2.q() - 5) / 32;
                    int n3 = (ax2.n() + (((kh)ax2).b() == 2 ? 0 : ((kh)ax2).e.c)) / 32;
                    if (byArray[n2][n3] == 0) break;
                    ((kh)ax2).a(0, ((kh)ax2).b() == 2 ? 3 : 2);
                }
            }
            if (kg2.a(kh2)) {
                kh2.a(0, kh2.b() == 2 ? 3 : (kh2.b() == 3 ? 2 : (kh2.b() == 0 ? 1 : 0)));
            }
        }
    }

    public final void a(int n2, kk kk2) {
    }

    public final void a(int n2) {
    }

    public final void a(ax ax2, kg kg2) {
    }
}

