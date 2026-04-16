/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class c
extends d {
    protected Image a;
    protected int[] b;
    protected int[] c;
    protected int[] d;
    protected int[] e;
    protected int[] f;
    protected int g;
    private int k = -1;
    private static String l = " 0123456789.,:!?()+-*/#$%abcdefghijklmnopqrstuvwxyz\u00e1\u00e0\u1ea3\u00e3\u1ea1\u0103\u1eaf\u1eb1\u1eb3\u1eb5\u1eb7\u00e2\u1ea5\u1ea7\u1ea9\u1eab\u1ead\u00e9\u00e8\u1ebb\u1ebd\u1eb9\u00ea\u1ebf\u1ec1\u1ec3\u1ec5\u1ec7\u00ed\u00ec\u1ec9\u0129\u1ecb\u00f3\u00f2\u1ecf\u00f5\u1ecd\u00f4\u1ed1\u1ed3\u1ed5\u1ed7\u1ed9\u01a1\u1edb\u1edd\u1edf\u1ee1\u1ee3\u00fa\u00f9\u1ee7\u0169\u1ee5\u01b0\u1ee9\u1eeb\u1eed\u1eef\u1ef1\u00fd\u1ef3\u1ef7\u1ef9\u1ef5\u0111ABCD\u0110EFGHIJKLMNOPQRSTUVWXYZ\u00c1\u00c0\u1ea2\u00c3\u1ea0\u0102\u1eae\u1eb0\u1eb2\u1eb4\u1eb6\u00c2\u1ea4\u1ea6\u1ea8\u1eaa\u1eac\u00c9\u00c8\u1eba\u1ebc\u1eb8\u00ca\u1ebe\u1ec0\u1ec2\u1ec4\u1ec6\u00cd\u00cc\u1ec8\u0128\u1eca\u00d3\u00d2\u1ece\u00d5\u1ecc\u00d4\u1ed0\u1ed2\u1ed4\u1ed6\u1ed8\u01a0\u1eda\u1edc\u1ede\u1ee0\u1ee2\u00da\u00d9\u1ee6\u0168\u1ee4\u01af\u1ee8\u1eea\u1eec\u1eee\u1ef0\u00dd\u1ef2\u1ef6\u1ef8\u1ef4\\\"@<=>;_&'`^~{}[]";
    private static byte[] m;
    protected int h;
    private boolean n = false;
    private boolean o = false;
    private boolean p;
    protected int i = 1;

    static {
        char c2 = '\u0000';
        int n2 = 0;
        while (n2 < l.length()) {
            if (c2 < l.charAt(n2)) {
                c2 = l.charAt(n2);
            }
            ++n2;
        }
        m = new byte[c2 + 1];
        n2 = 0;
        while (n2 < m.length) {
            c.m[n2] = 0;
            ++n2;
        }
        n2 = 0;
        while (n2 < l.length()) {
            c.m[c.l.charAt((int)n2)] = (byte)n2;
            ++n2;
        }
    }

    public final void a(Graphics graphics, String string, int n2, int n3, int n4, int n5, int n6) {
        int n7;
        int n8;
        int n9;
        int n10;
        block19: {
            block18: {
                n10 = 0 + string.length();
                n2 = graphics.getClipY();
                int n11 = graphics.getClipHeight();
                n9 = graphics.getClipX();
                n8 = graphics.getClipWidth();
                if (n5 > n2 + n11) break block18;
                c c2 = this;
                if (n5 + c2.h >= n2) break block19;
            }
            return;
        }
        int n12 = 0;
        if (n6 == 0) {
            n7 = n4;
            if (this.o) {
                n12 = n3 == string.length() ? this.a(string) : this.a(string.substring(0, n10));
            }
        } else {
            n12 = n3 == string.length() ? this.a(string) : this.a(string.substring(0, n10));
            n7 = n6 == 2 ? n4 - n12 : n4 - (n12 >> 1);
        }
        if (this.k != -1) {
            graphics.setColor(this.k);
            graphics.fillRect(n7, n5, n12, this.h);
        }
        n3 = 0;
        while (n3 < n10) {
            try {
                n2 = m[string.charAt(n3)] & 0xFF;
            }
            catch (Throwable throwable) {
                n2 = 0;
            }
            if (this.n && this.f[n2] <= this.h >> 1 && this.e[n2] - (this.h >> 1) + this.f[n2] > 1) {
                int n13 = this.e[n2] >>> 1;
                int n14 = this.e[n2] - n13;
                graphics.drawRegion(this.a, this.b[n2], this.c[n2], this.d[n2], n13, 0, n7 + 1, n5 + this.f[n2] - 1, 0);
                graphics.drawRegion(this.a, this.b[n2], this.c[n2] + n13, this.d[n2], n14, 0, n7, n5 + n13 + this.f[n2] - 1, 0);
            } else {
                graphics.drawRegion(this.a, this.b[n2], this.c[n2], this.d[n2], this.e[n2], 0, n7, n5 + this.f[n2] - 1, 0);
                if (this.p) {
                    graphics.drawRegion(this.a, this.b[n2], this.c[n2], this.d[n2], this.e[n2], 0, n7 + 1, n5 + this.f[n2] - 1, 0);
                }
            }
            if ((n7 += this.d[n2] + this.i) > n9 + n8) break;
            ++n3;
        }
        if (this.o) {
            graphics.setColor(this.j);
            if (n6 == 2) {
                n4 -= n12;
            } else if (n6 == 1) {
                n4 -= n12 / 2;
            }
            graphics.drawLine(n4, n5 + this.g + 1, n4 + n12 - 1, n5 + this.g + 1);
        }
    }

    public final int a() {
        return this.h;
    }

    public final int a(String string) {
        if (string == null || string.length() == 0) {
            return 0;
        }
        int n2 = 0;
        int n3 = 0;
        while (n3 < string.length()) {
            n2 += this.a(string.charAt(n3));
            ++n3;
        }
        return n2;
    }

    public final int a(char c2) {
        try {
            c2 = (char)(m[c2] & 0xFF);
        }
        catch (Exception exception) {
            c2 = '\u0000';
        }
        return this.d[c2] + this.i;
    }

    public final int b() {
        return this.g;
    }

    public final void a(boolean bl2) {
        this.n = bl2;
    }

    public final void c() {
        boolean bl2 = false;
        c c2 = this;
        this.n = bl2;
        bl2 = false;
        c2 = this;
        this.o = bl2;
        this.c(false);
    }

    public final void b(boolean bl2) {
        this.o = bl2;
    }

    public void c(boolean bl2) {
        this.p = bl2;
    }
}

