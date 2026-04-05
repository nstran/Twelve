/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.util.Calendar;
import javax.microedition.lcdui.Graphics;

public final class gu
extends ap {
    private int k = 30;
    private int l = 18;
    private int m = 20;
    private int n = 1900;
    private int o;
    private int p;
    private int q;
    private int r;
    private int s = 0;
    private int t;
    private int u;
    private int v;
    private int w = 2;

    public gu() {
        this(1900, l.c(System.currentTimeMillis()).get(1));
    }

    private gu(int n2, int n3) {
        super(1);
        this.o = n3;
        Object object = l.c(System.currentTimeMillis());
        this.p = ((Calendar)object).get(5);
        this.q = ((Calendar)object).get(2) + 1;
        this.r = ((Calendar)object).get(1);
        this.v = this.k * 7 + 5;
        this.u = 6 * this.l + 5;
        this.a((z.r - this.v - 6 - 6) / 2, (z.s - be.a - this.u - this.m - this.m - 3 - 15 - 15) / 2, this.v + 6 + 6, this.u + this.m + this.m + 3 + 15 + 15);
        ga ga2 = new ga(-1099, 2);
        object = this;
        ((aq)object).a(ga2, true);
        ga2 = new ga(-1100, 3);
        object = this;
        ((aq)object).b(ga2, true);
        this.C();
    }

    public final void a(int n2, int n3, int n4) {
        this.p = n2;
        this.q = n3;
        this.r = n4;
        this.C();
    }

    private void C() {
        int n2 = this.r;
        int n3 = this.q;
        int n4 = this.p;
        this.p = n4 > (n3 = gu.d(n3, n2)) ? n3 : n4;
        Calendar calendar = l.a;
        calendar.set(5, 1);
        calendar.set(2, this.q - 1);
        calendar.set(1, this.r);
        this.s = (calendar.get(7) - 2 + 7) % 7;
        this.t = gu.d(this.q, this.r);
    }

    public final int t() {
        return this.p;
    }

    public final int u() {
        return this.r;
    }

    public final int v() {
        return this.q;
    }

    public final String w() {
        if (this.p > 9) {
            return String.valueOf(this.p);
        }
        return "0" + this.p;
    }

    public final String x() {
        return String.valueOf(this.r);
    }

    public final String y() {
        if (this.q > 9) {
            return String.valueOf(this.q);
        }
        return "0" + this.q;
    }

    public final int z() {
        return this.t;
    }

    public final int A() {
        return this.n;
    }

    public final int B() {
        return this.o;
    }

    public final void c(int n2) {
        if (n2 == 98) {
            if (this.w == 2) {
                this.w = 1;
                return;
            }
            if (this.w == 1) {
                this.w = 0;
                return;
            }
            if (this.w == 0) {
                n2 = this.p + 7;
                if (n2 > this.t) {
                    n2 = this.t;
                }
                this.p = n2;
                return;
            }
        } else if (n2 == 99) {
            if (this.w == 1) {
                this.w = 2;
                return;
            }
            if (this.w == 0) {
                n2 = this.p - 7;
                if (n2 <= 0) {
                    this.w = 1;
                    return;
                }
                this.p = n2;
                return;
            }
        } else if (n2 == 97) {
            if (this.w == 2) {
                if (this.r > this.n) {
                    --this.r;
                    this.C();
                    return;
                }
            } else if (this.w == 1) {
                if (this.q > 1) {
                    --this.q;
                    this.C();
                    return;
                }
            } else if (this.w == 0 && this.p > 1) {
                --this.p;
                return;
            }
        } else if (n2 == 96) {
            if (this.w == 2) {
                if (this.r < this.o) {
                    ++this.r;
                    this.C();
                    return;
                }
            } else if (this.w == 1) {
                if (this.q < 12) {
                    ++this.q;
                    this.C();
                    return;
                }
            } else if (this.w == 0 && this.p < this.t) {
                ++this.p;
            }
        }
    }

    public final void a(int n2, int n3) {
        int n4 = this.a() + 6;
        int n5 = this.c() + 15;
        if (n3 >= n5 && n3 <= n5 + this.m) {
            if (this.w == 2) {
                if (n2 < n4 + this.i() / 2) {
                    this.c(97);
                    return;
                }
                this.c(96);
                return;
            }
            this.w = 2;
            return;
        }
        if (n3 >= (n5 += this.m + 2) && n3 <= n5 + this.m) {
            if (this.w == 1) {
                if (n2 < n4 + this.i() / 2) {
                    this.c(97);
                    return;
                }
                this.c(96);
                return;
            }
            this.w = 1;
            return;
        }
        if (n3 >= (n5 += this.m + 3) && n3 <= n5 + this.l * 6 && n2 >= n4 && n2 <= n4 + this.k * 7) {
            this.w = 0;
            n2 -= n4;
            if ((n2 = n2 / this.k + (n3 -= n5) / this.l * 7 - this.s + 1) > 0 && n2 <= this.t) {
                this.p = n2;
            }
        }
    }

    public final void c(Graphics graphics) {
        graphics.setColor(16244470);
        graphics.fillRect(0, 0, z.r, z.s);
        cz.b(graphics, 13092820, this.a(), this.c(), this.i(), this.j());
        graphics.setColor(16244470);
        graphics.fillRect(this.a() + 10, this.c(), 33, 1);
        ca.d.c(true);
        ca.d.a(graphics, "Ng\u00e0y", this.a() + 12, this.c() - 7, 0);
        ca.d.c();
        int n2 = this.a() + 6;
        int n3 = this.c() + 15;
        int n4 = n2 + (this.v - 60) / 2;
        if (this.w == 2) {
            oz.c(graphics, n4 - 25, n3 + 1, 19, 17, 857589, 0);
            oz.c(graphics, n4 + 5 + 60, n3 + 1, 19, 17, 857589, 1);
        }
        oz.a(graphics, n4, n3, 60, this.m, this.w == 2);
        ca.d.a(graphics, String.valueOf(this.r), n2 + this.v / 2, n3 + 3, 1);
        n3 += this.m + 2;
        n4 = n2 + (this.v - 100) / 2;
        if (this.w == 1) {
            oz.c(graphics, n4 - 25, n3 + 1, 19, 17, 857589, 0);
            oz.c(graphics, n4 + 5 + 100, n3 + 1, 19, 17, 857589, 1);
        }
        oz.a(graphics, n4, n3, 100, this.m, this.w == 1);
        ca.d.a(graphics, "Th\u00e1ng " + this.q, n2 + this.v / 2, n3 + 3, 1);
        this.a(graphics, n2, n3 += this.m + 1);
    }

    private void a(Graphics graphics, int n2, int n3) {
        int n4;
        int n5;
        int n6 = 10323806;
        int n7 = 15722458;
        if (this.w == 0) {
            n6 = 22523;
            n7 = 13295359;
        }
        graphics.setColor(12564408);
        graphics.fillRect(n2 + 2, n3 + 2, this.v - 4, 1);
        oz.b(graphics, n2 + 1, n3 + 1, this.v - 2, this.u - 2, n6, n7, -1);
        n6 = 0;
        n7 = 0;
        n6 = 0;
        int n8 = n3 + 3 + this.l;
        int n9 = n3 + this.u - 1;
        while (n8 <= n9) {
            n7 = 0xFFFFFF;
            n5 = 12966138;
            n4 = 16371412;
            if (n6 == 0) {
                n7 = 14013930;
                n5 = 7184895;
                n4 = 16550298;
            }
            graphics.setColor(n7);
            graphics.fillRect(n2 + 2, n8 - this.l, this.v - 4 - this.k - this.k, this.l);
            graphics.setColor(n5);
            graphics.fillRect(n2 + this.v - 2 - this.k - this.k, n8 - this.l, this.k, this.l);
            graphics.setColor(n4);
            graphics.fillRect(n2 + this.v - 2 - this.k, n8 - this.l, this.k, this.l);
            graphics.setColor(12564408);
            graphics.fillRect(n2 + 2, n8 - 1, this.v - 4, 1);
            n6 = n6 != 0 ? 0 : 1;
            n8 += this.l;
        }
        graphics.setColor(12564408);
        n8 = n2 + 2 + this.k;
        n9 = n2 + this.v - 2;
        while (n8 < n9) {
            graphics.fillRect(n8, n3 + 2, 1, this.u - 4);
            n8 += this.k;
        }
        n8 = this.s;
        n9 = 1;
        while (n9 <= this.t) {
            n7 = n8 % 7;
            n5 = n8 / 7;
            n4 = -1;
            n6 = n2 + n7 * this.k + 2;
            n7 = n3 + n5 * this.l + 3;
            if (n9 == this.p) {
                if (this.w == 0) {
                    n4 = 16048180;
                }
                graphics.setColor(0);
                graphics.drawRect(n6, n7 - 1, this.k, this.l);
            }
            if (n4 >= 0) {
                graphics.setColor(n4);
                graphics.fillRect(n6 + 1, n7, this.k - 1, this.l - 1);
            }
            ca.d.a(graphics, "" + n9, n6 + this.k - 1, n7 + 2, 2);
            ++n8;
            ++n9;
        }
    }

    protected final void g() {
    }

    private static int d(int n2, int n3) {
        switch (n2) {
            case 2: {
                n2 = n3;
                if (n2 % 400 == 0 || n2 % 100 != 0 && n2 % 4 == 0) {
                    return 29;
                }
                return 28;
            }
            case 4: 
            case 6: 
            case 9: 
            case 11: {
                return 30;
            }
        }
        return 31;
    }
}

