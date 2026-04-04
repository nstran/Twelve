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
extends ax {
    public static final byte[] a;
    public static final byte[] b;
    private int g = 0;
    private static byte[] h;
    private static byte[] i;
    public int c;
    private Image j = null;
    private int k = 0;
    public int d = 0;
    private byte[] s = null;
    private int t;
    public n e;
    private int u;
    public jn f;
    private Image v = null;
    private int w = 0;
    private int x = 0;
    private int y = 0;
    private lf z;
    private int A = 12;
    private d B = ca.c;
    private int C = 0;
    private int D;
    private int E;

    static {
        byte[] byArray = new byte[4];
        byArray[2] = -1;
        byArray[3] = 1;
        a = byArray;
        byte[] byArray2 = new byte[4];
        byArray2[0] = -1;
        byArray2[1] = 1;
        b = byArray2;
        byte[] byArray3 = new byte[9];
        byArray3[3] = 4;
        byArray3[4] = 4;
        byArray3[5] = 4;
        byArray3[6] = 5;
        byArray3[7] = 5;
        byArray3[8] = 5;
        h = byArray3;
        i = new byte[]{3, 3, 3, 2, 2, 3, 1, 1, 1};
    }

    public kh(Image object, int n2, int n3, jn jn2, lf lf2, Image image) {
        int n4 = 6;
        n3 = 1;
        Object object2 = object;
        object = this;
        this.j = object2;
        object.o = object2.getWidth() / n4;
        object.p = object2.getHeight() / n3;
        this.z = lf2;
        this.f = jn2;
        this.a(this.m, this.n, this.o - 12, 20);
        this.w = this.o >> 1;
        if (image == null) {
            lf lf3 = lf2;
            object2 = jn2;
            object = this;
            if (object2 != null) {
                n4 = object2.d - lf3.G;
                switch (object2.g) {
                    case 1: {
                        object.C = 0xFF0000;
                        break;
                    }
                    case 2: {
                        object.C = 9008914;
                        break;
                    }
                    default: {
                        if (n4 >= 5) {
                            object.C = 1471487;
                            break;
                        }
                        if (n4 < -9) {
                            object.C = 0xAAAAAA;
                            break;
                        }
                        object.B = ca.c;
                        object.C = 0xDDDDDD;
                    }
                }
                object.D = object.B.a(object2.b);
                object.E = object.B.a();
            }
            return;
        }
        this.v = image;
    }

    public final Image a() {
        return this.v;
    }

    public kh() {
        this.a(this.m, this.n, this.o, this.p);
    }

    public final void a(Graphics object, int n2, int n3) {
        this.b((Graphics)object, n2, n3);
        int n4 = n3;
        n3 = n2;
        Graphics graphics = object;
        object = this;
        if (object.f.b != null) {
            graphics.setColor(object.C);
            graphics.fillRect(object.m + object.w + n3 - object.D / 2, object.n + object.A + n4 - object.E / 2, object.D, object.E);
            object.B.a(graphics, object.f.b, object.m + object.w + n3 - object.D / 2, object.n + object.A + n4 - object.E / 2, 0);
        }
    }

    public final void b(Graphics graphics, int n2, int n3) {
        if (this.j != null) {
            graphics.drawRegion(this.j, this.s[this.t] * this.o, 0, this.o, this.p, this.u, this.m + n2, this.n + this.A + n3, 0);
            return;
        }
        graphics.setColor(0);
        graphics.fillArc(this.m + n2, this.n + n3, this.o, this.p, 0, 360);
    }

    public final void a(int n2) {
        this.g = n2;
        this.t = 0;
        switch (this.g) {
            case 0: {
                byte[] byArray = h;
                kh kh2 = this;
                this.s = byArray;
                return;
            }
            case 1: {
                byte[] byArray = i;
                kh kh3 = this;
                this.s = byArray;
            }
        }
    }

    public final void a(int n2, int n3) {
        this.b(n3);
        this.a(n2);
    }

    public final void i() {
        switch (this.g) {
            case 0: {
                ++this.t;
                if (this.t < this.s.length) break;
                this.t = 0;
                break;
            }
            default: {
                ++this.t;
                if (this.t < this.s.length) break;
                this.t = 0;
            }
        }
        if (this.e != null) {
            this.e.a = this.m + this.x;
            this.e.b = this.n + this.y;
        }
    }

    public final void b(int n2) {
        this.c = n2;
        this.u = n2 == 2 ? 0 : (n2 == 3 ? 2 : this.u);
    }

    public final int b() {
        return this.c;
    }

    public final int c() {
        return this.g;
    }

    public final void c(int n2) {
        this.d = n2;
    }

    public final void b(int n2, int n3) {
        this.m += n2;
        this.n += n3;
    }

    public final n d() {
        return this.e;
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.e = new n(n2, n3, n4, n5);
        this.x = (this.o - this.e.c) / 2;
        this.y = this.p - this.e.d;
    }

    public final boolean a(n n2) {
        return this.e.a(n2);
    }

    public static Image a(Image image, int n2) {
        int n3 = image.getWidth();
        int n4 = image.getHeight();
        int[] nArray = new int[n3 * n4];
        image.getRGB(nArray, 0, n3, 0, 0, n3, n4);
        int n5 = 0;
        int n6 = 0;
        while (n6 < n4) {
            int n7 = 0;
            while (n7 < n3) {
                n5 = n7 + n6 * n3;
                if ((nArray[n5] & 0xFFFFFF) == n2) {
                    nArray[n5] = 0;
                }
                ++n7;
            }
            ++n6;
        }
        Image image2 = Image.createRGBImage((int[])nArray, (int)n3, (int)n4, (boolean)true);
        return image2;
    }

    public final void c(int n2, int n3) {
        super.c(n2, n3);
        if (this.e != null) {
            this.e.a = n2 + this.x;
            this.e.b = n3 + this.y;
        }
    }

    public final void d(int n2) {
        this.A = n2;
    }

    public final kh e() {
        kh kh2 = new kh(this.j, 1, 6, this.f, this.z, this.v);
        kh2.h(this.o);
        kh2.i(this.p);
        kh2.a(0, 2);
        kh2.v = null;
        kh2.w = this.w;
        kh2.A = this.A;
        return kh2;
    }
}

