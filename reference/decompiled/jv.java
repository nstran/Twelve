/*
 * Decompiled with CFR 0.152.
 */
public final class jv
extends ju {
    public final void a(int n2, kk kk2) {
    }

    public final void a(int n2) {
    }

    public final void a(ax ax2, kg kg2) {
        int n2 = 0;
        int n3 = 0;
        ke ke2 = (ke)kg2.a(1);
        ax2 = (jt)ax2;
        ((aw)ax2).i();
        switch (((aw)ax2).h()) {
            case 1: {
                ax2.g(ax2.o() - ((jt)ax2).t);
                ax2.f(ax2.n() + ((jt)ax2).u * ((jz)ax2).w);
                --((jt)ax2).t;
                if (((jt)ax2).t == 0) {
                    ((jt)ax2).a((byte)2);
                }
                jv.a((jt)ax2, kg2, ke2);
                break;
            }
            case 2: {
                ax2.g(ax2.o() + ((jt)ax2).t);
                ax2.f(ax2.n() + ((jt)ax2).u * ((jz)ax2).w);
                ++((jt)ax2).t;
                if (((jt)ax2).t > ((jt)ax2).s) {
                    ((jt)ax2).t = ((jt)ax2).s;
                }
                n2 = (ax2.n() + ax2.p() / 2) / 32;
                n3 = (ax2.o() + ax2.q()) / 32;
                int n4 = ax2.n() / 32;
                int n5 = (ax2.n() + ax2.p()) / 32;
                if (kg.l(ke2.b(n3, n5))) {
                    n2 = 32 - (ax2.n() + ax2.p()) % 32;
                    ax2.c(ax2.n(), (n3 << 5) - ax2.q() + n2);
                    break;
                }
                if (kg.d(ke2.b(n3, n4))) {
                    ax2.c(ax2.n(), (n3 << 5) - ax2.q() + ax2.n() % 32);
                    break;
                }
                if (!kg.c(ke2.b(n3, n2))) break;
                ((jt)ax2).a((byte)0);
                ax2.g((n3 << 5) - ax2.q());
            }
        }
        jv.a((jt)ax2, kg2, ke2);
    }

    private static void a(jt jt2, kg kg2, ke ke2) {
        if (jt2.n() < 0) {
            jt2.f(0);
        } else if (jt2.n() + jt2.p() > kg2.p()) {
            jt2.f(kg2.p() - jt2.p());
        }
        if (jt2.w == -1) {
            int n2 = jt2.n() / 32;
            int n3 = (jt2.o() + jt2.q() / 2) / 32;
            if (kg2.n(ke2.b(n3, n2))) {
                jt2.f(n2 + 1 << 5);
                jt2.w = 0;
                return;
            }
        } else if (jt2.w == 1) {
            int n4 = (jt2.n() + jt2.p()) / 32;
            int n5 = (jt2.o() + jt2.q() / 2) / 32;
            if (kg2.n(ke2.b(n5, n4))) {
                jt2.f(n4 - 1 << 5);
                jt2.w = 0;
            }
        }
    }
}

