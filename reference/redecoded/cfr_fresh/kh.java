/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class kh
extends at {
    private k a;
    private Image b;
    private int c;
    private int d;
    private int e = 0;
    private int f = 1;
    private int g = 0;
    private int h = 0;
    private cu i = null;
    private kb[] j = new kb[3];
    private kl k;

    public kh(Image image) {
        this.b = image;
        k k2 = new k(0, 0, v.t, v.u - ba.a);
        kh kh2 = this;
        this.a = k2;
        kh2.i = new cu(k2.c >> 1, k2.d >> 1);
        this.f = v.t / 240 + (v.t % 240 > 0 ? 1 : 0);
        this.e = v.u - (320 - image.getHeight());
        if (v.u > 320) {
            this.g = v.u - 320 + 10;
            int n2 = image.getWidth();
            int n3 = image.getHeight();
            int[] nArray = new int[n2 * n3];
            image.getRGB(nArray, 0, n2, 0, 0, n2, n3);
            this.h = nArray[0];
        }
    }

    public final void a(Graphics graphics) {
        Graphics graphics2 = graphics;
        kh kh2 = this;
        if (kh2.g > 0) {
            graphics2.setColor(kh2.h);
            graphics2.fillRect(0, 0, v.t, kh2.g);
        }
        int n2 = 0;
        while (n2 < kh2.f) {
            graphics2.drawImage(kh2.b, n2 * kh2.b.getWidth(), kh2.e, 36);
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
            this.k = ((kd)this.j[2]).c();
        }
        int n2 = 0;
        int n3 = this.j.length;
        while (n2 < n3) {
            this.j[n2].a();
            ++n2;
        }
    }

    public final void a(k k2) {
        this.c = this.a.a;
        this.d = this.a.b;
        this.a.a = k2.a - this.i.a;
        this.a.b = k2.b - this.i.b;
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
        int n2 = this.c - this.a.a;
        int n3 = this.d - this.a.b;
        this.j[0].a(n2 / 3, n3 / 3);
    }

    public final void a(kb kb2) {
        int n2 = 0;
        while (n2 < this.j.length) {
            if (this.j[n2] == null) {
                this.j[n2] = kb2;
                kb2.a(this.a);
                return;
            }
            ++n2;
        }
        kb[] kbArray = new kb[this.j.length + 1];
        System.arraycopy(this.j, 0, kbArray, 0, this.j.length);
        kbArray[this.j.length] = kb2;
        this.j = kbArray;
    }

    public final kb a(int n2) {
        if (1 >= this.j.length) {
            return null;
        }
        return this.j[1];
    }

    public final void a(Graphics graphics, int n2, int n3) {
    }

    public final k a() {
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
        return (n2 & 1) == 0 && !kh.o(n2);
    }

    public final boolean n(int n2) {
        return (n2 & 2) == 0 && !kh.o(n2);
    }

    protected final boolean a(at at2) {
        boolean bl2 = false;
        if (at2.n() < 0) {
            at2.f(0);
            bl2 = true;
        } else if (at2.n() + at2.p() > this.o) {
            at2.f(this.o - at2.p());
            bl2 = true;
        }
        if (at2.o() < 0) {
            at2.g(0);
            bl2 = true;
        } else if (at2.o() + at2.q() > this.p) {
            at2.g(this.p - at2.q());
            bl2 = true;
        }
        return bl2;
    }

    protected final boolean b(k k2) {
        boolean bl2 = false;
        if (k2.a < 0) {
            k2.a = 0;
            bl2 = true;
        } else if (k2.a + k2.c > this.o) {
            k2.a = this.o - k2.c;
            bl2 = true;
        }
        if (k2.b < 0) {
            if (this.k.y) {
                k2.b = 0;
            }
            bl2 = true;
        } else if (k2.b + k2.d > this.p) {
            k2.b = this.p - k2.d;
            bl2 = true;
        }
        return bl2;
    }
}

