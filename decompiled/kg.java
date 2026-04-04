/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class kg
extends ax {
    private n a;
    private Image b;
    private int c;
    private int d;
    private int e = 0;
    private int f = 1;
    private int g = 0;
    private int h = 0;
    private cx i = null;
    private ka[] j = new ka[3];
    private kk k;

    public kg(Image image) {
        this.b = image;
        n n2 = new n(0, 0, z.r, z.s - be.a);
        kg kg2 = this;
        this.a = n2;
        kg2.i = new cx(n2.c >> 1, n2.d >> 1);
        this.f = z.r / 240 + (z.r % 240 > 0 ? 1 : 0);
        this.e = z.s - (320 - image.getHeight());
        if (z.s > 320) {
            this.g = z.s - 320 + 10;
            int n3 = image.getWidth();
            int n4 = image.getHeight();
            int[] nArray = new int[n3 * n4];
            image.getRGB(nArray, 0, n3, 0, 0, n3, n4);
            this.h = nArray[0];
        }
    }

    public final void a(Graphics graphics) {
        Graphics graphics2 = graphics;
        kg kg2 = this;
        if (kg2.g > 0) {
            graphics2.setColor(kg2.h);
            graphics2.fillRect(0, 0, z.r, kg2.g);
        }
        int n2 = 0;
        while (n2 < kg2.f) {
            graphics2.drawImage(kg2.b, n2 * kg2.b.getWidth(), kg2.e, 36);
            ++n2;
        }
        int n3 = 0;
        int n4 = this.j.length;
        while (n3 < n4) {
            this.j[n3].a(graphics, this.m, this.n, this.a);
            ++n3;
        }
        this.j[2].b(graphics, this.m, this.n);
    }

    public final void i() {
        if (this.k == null) {
            this.k = ((kc)this.j[2]).c();
        }
        int n2 = 0;
        int n3 = this.j.length;
        while (n2 < n3) {
            this.j[n2].a();
            ++n2;
        }
    }

    public final void a(n n2) {
        this.c = this.a.a;
        this.d = this.a.b;
        this.a.a = n2.a - this.i.a;
        this.a.b = n2.b - this.i.b;
        if (this.c == this.a.a && this.d == this.a.b) {
            return;
        }
        if (this.a.a + this.a.c > this.o) {
            this.a.a = this.o - this.a.c;
        } else if (this.a.a < 0) {
            this.a.a = 0;
        }
        if (this.a.b + this.a.d > this.p) {
            this.a.b = this.p - this.a.d;
        } else if (this.a.b < 0) {
            this.a.b = 0;
        }
        this.m = -this.a.a;
        this.n = -this.a.b;
        int n3 = this.c - this.a.a;
        int n4 = this.d - this.a.b;
        this.j[0].a(n3 / 3, n4 / 3);
    }

    public final void a(ka ka2) {
        int n2 = 0;
        while (n2 < this.j.length) {
            if (this.j[n2] == null) {
                this.j[n2] = ka2;
                ka2.a(this.a);
                return;
            }
            ++n2;
        }
        ka[] kaArray = new ka[this.j.length + 1];
        System.arraycopy(this.j, 0, kaArray, 0, this.j.length);
        kaArray[this.j.length] = ka2;
        this.j = kaArray;
    }

    public final ka a(int n2) {
        if (1 >= this.j.length) {
            return null;
        }
        return this.j[1];
    }

    public final void a(Graphics graphics, int n2, int n3) {
    }

    public final n a() {
        return this.a;
    }

    public static boolean a(byte by2) {
        return (by2 & 8) != 0;
    }

    public static boolean b(int n2) {
        return (n2 & 0x20) != 0;
    }

    public static boolean c(int n2) {
        return (n2 & 0x10) != 0;
    }

    private static boolean o(int n2) {
        return (n2 & 0x40) != 0;
    }

    public static boolean d(int n2) {
        return (n2 & 0x40) != 0 && (n2 & 2) != 0;
    }

    public static boolean l(int n2) {
        return (n2 & 0x40) != 0 && (n2 & 1) != 0;
    }

    public final boolean m(int n2) {
        return (n2 & 1) == 0 && !kg.o(n2);
    }

    public final boolean n(int n2) {
        return (n2 & 2) == 0 && !kg.o(n2);
    }

    protected final boolean a(ax ax2) {
        boolean bl2 = false;
        if (ax2.n() < 0) {
            ax2.f(0);
            bl2 = true;
        } else if (ax2.n() + ax2.p() > this.o) {
            ax2.f(this.o - ax2.p());
            bl2 = true;
        }
        if (ax2.o() < 0) {
            ax2.g(0);
            bl2 = true;
        } else if (ax2.o() + ax2.q() > this.p) {
            ax2.g(this.p - ax2.q());
            bl2 = true;
        }
        return bl2;
    }

    protected final boolean b(n n2) {
        boolean bl2 = false;
        if (n2.a < 0) {
            n2.a = 0;
            bl2 = true;
        } else if (n2.a + n2.c > this.o) {
            n2.a = this.o - n2.c;
            bl2 = true;
        }
        if (n2.b < 0) {
            if (this.k.x) {
                n2.b = 0;
            }
            bl2 = true;
        } else if (n2.b + n2.d > this.p) {
            n2.b = this.p - n2.d;
            bl2 = true;
        }
        return bl2;
    }
}

