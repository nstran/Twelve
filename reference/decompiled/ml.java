/*
 * Decompiled with CFR 0.152.
 */
public final class ml
extends mi {
    public ml(mo mo2, int n2) {
        super(mo2, n2);
    }

    public final void a(int n2) {
        switch (n2) {
            case 99: {
                this.a.g();
                return;
            }
            case 98: {
                this.a.h();
                return;
            }
            case 97: {
                this.a.i();
                return;
            }
            case 96: {
                this.a.j();
                return;
            }
            case 95: {
                this.a.l();
            }
        }
    }

    public final void a(int n2, int n3) {
        n n4 = this.a.k();
        if (n4.b(n2, n3)) {
            n2 = (n2 - n4.a) / 28 + 2;
            n3 = (n3 - n4.b) / 28 + 2;
            int n5 = this.a.n();
            int n6 = this.a.o();
            if (n3 == n5 && n2 == n6) {
                this.a.l();
                return;
            }
            if (this.a.m()) {
                n2 -= n6;
                if ((n3 -= n5) == 1) {
                    this.a.h();
                    return;
                }
                if (n3 == -1) {
                    this.a.g();
                    return;
                }
                if (n2 == 1) {
                    this.a.j();
                    return;
                }
                if (n2 == -1) {
                    this.a.i();
                    return;
                }
            } else {
                this.a.f(n3, n2);
            }
        }
    }

    public final void b(int n2, int n3) {
        n n4 = this.a.k();
        if (n4.b(n2, n3)) {
            n2 = (n2 - n4.a) / 28 + 2;
            n3 = (n3 - n4.b) / 28 + 2;
            int n5 = this.a.n();
            int n6 = this.a.o();
            if (n3 != n5 || n2 != n6) {
                boolean bl2 = this.a.m();
                if (!bl2) {
                    this.a.l();
                }
                if (Math.abs(n3 -= n5) > Math.abs(n2 -= n6)) {
                    if (n3 > 0) {
                        this.a.h();
                        return;
                    }
                    if (n3 < 0) {
                        this.a.g();
                        return;
                    }
                } else {
                    if (n2 > 0) {
                        this.a.j();
                        return;
                    }
                    if (n2 < 0) {
                        this.a.i();
                        return;
                    }
                    if (!bl2) {
                        this.a.l();
                    }
                }
            }
        }
    }
}

