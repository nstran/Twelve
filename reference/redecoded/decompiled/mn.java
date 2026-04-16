/*
 * Decompiled with CFR 0.152.
 */
public final class mn
extends mk {
    public mn(mq mq2, int n2) {
        super(mq2, n2);
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
        k k2 = this.a.k();
        if (k2.b(n2, n3)) {
            n2 = (n2 - k2.a) / 28 + 2;
            n3 = (n3 - k2.b) / 28 + 2;
            int n4 = this.a.n();
            int n5 = this.a.o();
            if (n3 == n4 && n2 == n5) {
                this.a.l();
                return;
            }
            if (this.a.m()) {
                n2 -= n5;
                if ((n3 -= n4) == 1) {
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
                this.a.g(n3, n2);
            }
        }
    }

    public final void b(int n2, int n3) {
        k k2 = this.a.k();
        if (k2.b(n2, n3)) {
            n2 = (n2 - k2.a) / 28 + 2;
            n3 = (n3 - k2.b) / 28 + 2;
            int n4 = this.a.n();
            int n5 = this.a.o();
            if (n3 != n4 || n2 != n5) {
                boolean bl2 = this.a.m();
                if (!bl2) {
                    this.a.l();
                }
                if (Math.abs(n3 -= n4) > Math.abs(n2 -= n5)) {
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

