/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class aw
extends ax {
    protected int a = 0;
    protected Image b;
    protected int c;
    protected int[][] d;
    protected int e;
    protected int[][] f;
    private int s;
    private int t;
    protected int g;
    private boolean u = true;
    protected int h;
    protected int i;
    protected int j;
    protected int k;

    public aw() {
    }

    public aw(Image image, int n2) {
        this.a(image, n2);
    }

    public aw(Image image, int[][] nArray) {
        this.a(image, nArray);
    }

    public final void a(Image image, int n2) {
        if (image == null) {
            throw new NullPointerException("Image is NULL!!!");
        }
        this.c = n2;
        this.g = -1;
        this.o = image.getWidth() / this.c;
        this.p = image.getHeight();
        this.b = image;
        this.d = null;
    }

    public final void a(Image image, int[][] nArray) {
        if (image == null) {
            throw new NullPointerException("Image is NULL!!!");
        }
        if (nArray == null) {
            throw new NullPointerException("Regions are NULL!!!");
        }
        this.g = -1;
        this.b = image;
        this.d = nArray;
    }

    public void a(Graphics graphics, int n2, int n3) {
        try {
            if (!this.r || this.g < 0 || this.b == null) {
                return;
            }
            int n4 = this.f[this.e][this.g];
            if (n4 >= 0) {
                if (this.d != null) {
                    int[] nArray = this.d[n4];
                    n2 = this.a == 0 ? this.m + n2 + nArray[4] : this.m + n2 + (this.o - nArray[2] - nArray[4]);
                    n3 = this.n + n3 + nArray[5];
                    if ((this.q & 1) > 0) {
                        n2 -= this.o >> 1;
                    } else if ((this.q & 8) > 0) {
                        n2 -= this.o;
                    }
                    if ((this.q & 2) > 0) {
                        n3 -= this.p >> 1;
                    } else if ((this.q & 0x20) > 0) {
                        n3 -= this.p;
                    }
                    if (this.a == 0) {
                        cz.a(graphics, this.b, nArray[0], nArray[1], nArray[2], nArray[3], n2, n3, 0);
                        return;
                    }
                    graphics.drawRegion(this.b, nArray[0], nArray[1], nArray[2], nArray[3], this.a, n2, n3, 0);
                    return;
                }
                if (this.a == 0) {
                    cz.a(graphics, this.b, this.f[this.e][this.g] * this.o, 0, this.o, this.p, this.m + n2, this.n + n3, this.q);
                    return;
                }
                graphics.drawRegion(this.b, this.f[this.e][this.g] * this.o, 0, this.o, this.p, this.a, this.m + n2, this.n + n3, this.q);
                return;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            cw.a(" Framanimate error: " + this.f.length + "            " + this.e + "             " + this.g);
            illegalArgumentException.printStackTrace();
            return;
        }
        catch (Exception exception) {
            cw.a(" Framanimate error: frames = " + this.f);
        }
    }

    public final int a() {
        return this.h;
    }

    public final int b() {
        return this.i;
    }

    public final int c() {
        return this.j;
    }

    public final int d() {
        return this.k;
    }

    public final void a(int n2, int n3) {
        this.j = n2;
        this.k = n3;
    }

    public final void b(int n2, int n3) {
        this.h = n2;
        this.i = n3;
    }

    public final int[][] e() {
        return this.f;
    }

    public final void a(int[][] nArray) {
        this.f = nArray;
    }

    public final void a(byte[][] byArray) {
        this.f = new int[byArray.length][];
        int n2 = 0;
        while (n2 < byArray.length) {
            this.f[n2] = new int[byArray[n2].length];
            int n3 = 0;
            while (n3 < byArray[n2].length) {
                this.f[n2][n3] = byArray[n2][n3];
                ++n3;
            }
            ++n2;
        }
    }

    public final void a(int n2) {
        this.g = n2;
    }

    public final int f() {
        return this.g;
    }

    public final void b(int n2) {
        this.s = n2;
        this.t = 0;
    }

    public final int g() {
        return this.a;
    }

    public void c(int n2) {
        this.a = n2;
    }

    public final void a(boolean bl2) {
        this.u = bl2;
    }

    public void d(int n2) {
        this.g = n2 != this.e ? 0 : (this.g %= this.f[n2].length);
        this.e = n2;
    }

    public final int h() {
        return this.e;
    }

    public final void i() {
        if (this.r) {
            this.k();
            if (this.f != null && this.f[this.e] != null) {
                ++this.t;
                if (this.t >= this.s) {
                    this.t = 0;
                    if (this.u) {
                        this.g = (this.g + 1) % this.f[this.e].length;
                    } else {
                        ++this.g;
                        if (this.g >= this.f[this.e].length) {
                            this.g = this.f[this.e].length - 1;
                        }
                    }
                }
            }
            if (this.e < 0) {
                this.e = 0;
                return;
            }
            if (this.e >= this.f.length) {
                this.e = this.f.length - 1;
            }
        }
    }

    public boolean j() {
        return this.g >= this.f[this.e].length - 1;
    }

    public void k() {
    }
}

