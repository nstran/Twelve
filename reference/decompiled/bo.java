/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bo
extends ax
implements bj {
    private static int c = 0;
    private bn[] d;
    public int a;
    private static String e = "OK";
    private static int f;
    private static int g;
    private static int h;
    private static int i;
    public static bi b;

    static {
        i = 8160656;
        b = null;
    }

    public static void a(int n2) {
        bn.a = 0xFFFFFF;
    }

    public bo(int n2) {
        this.b(n2);
        bo bo2 = this;
        this.n = z.s;
        bo2.a = z.s - bo2.p;
    }

    private void b(int n2) {
        this.m = 0;
        this.o = z.r;
        this.p = z.s / 2;
        if (this.p < 160) {
            this.p = 160;
        }
        this.n = z.s - this.p;
        int n3 = 4;
        int n4 = 8;
        if (z.d != 3) {
            n3 = 2;
            n4 = 4;
        }
        int n5 = 0;
        int n6 = (this.p - n4 * 5) / 4;
        int n7 = (this.o - n3 * 11) / 10;
        if (n6 > n7 * 5 / 4) {
            n6 = n7 * 5 / 4;
            this.p = n6 * 4 + n4 * 5;
            this.n = z.s - this.p;
        }
        if (n2 == 0 || n2 == 1 || n2 == 2) {
            c = n2;
            this.d = new bn[34];
            String string = c == 2 ? "qwertyuiop" : "QWERTYUIOP";
            n7 = (this.o - n3 * 11) / 10;
            int n8 = (this.o - n7 * 10 - n3 * 9) / 2;
            int n9 = 0;
            while (n9 < string.length()) {
                this.d[n9 + 0] = new bn(n8 + n3 * n9 + n9 * n7, n4, n7, n6, "" + string.charAt(n9), string.charAt(n9));
                ++n9;
            }
            n5 = 0 + string.length();
            string = c == 2 ? "asdfghjkl" : "ASDFGHJKL";
            n9 = 0;
            while (n9 < string.length()) {
                this.d[n5 + n9] = new bn((z.r - n7 * 9 - n3 * 8) / 2 + n3 * n9 + n9 * n7, n4 * 2 + n6, n7, n6, "" + string.charAt(n9), string.charAt(n9));
                ++n9;
            }
            n5 = n5 + string.length() + 1;
            string = c == 2 ? "zxcvbnm" : "ZXCVBNM";
            n8 = (z.r - n7 * 7 - n3 * 6) / 2;
            n9 = 0;
            while (n9 < string.length()) {
                this.d[n5 + n9] = new bn(n8 + n3 * n9 + n9 * n7, n4 * 3 + n6 * 2, n7, n6, "" + string.charAt(n9), string.charAt(n9));
                ++n9;
            }
            if (c == 2) {
                this.d[n5 - 1] = new bn(n3, n4 * 3 + n6 * 2, n8 - n3 * 2, n6, "shift", 0);
            } else if (c == 1) {
                this.d[n5 - 1] = new bn(n3, n4 * 3 + n6 * 2, n8 - n3 * 2, n6, "Shift", 0);
            } else if (c == 0) {
                this.d[n5 - 1] = new bn(n3, n4 * 3 + n6 * 2, n8 - n3 * 2, n6, "SHIFT", 0);
            }
            f = n5 - 1;
            this.d[n5 - 1].a(this, 6);
            this.d[n5 + 7] = new bn(this.o - n3 - this.d[n5 - 1].d, n4 * 3 + n6 * 2, n8 - n3 * 2, n6, "DEL", -8);
            n5 = n5 + string.length() + 1;
            n8 = (z.r - n7 * 5 - n3 * 4) / 2;
            this.d[n5 + 2] = new bn(n8, n4 * 4 + n6 * 3, n7 * 4 + n3 * 4, n6, z.a && z.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar", 32);
            h = n5 + 2;
            this.d[n5] = new bn(n3, n4 * 4 + n6 * 3, n8 - n3 * 3 - n7, n6, ".?123", 0);
            this.d[n5].a(this, 3);
            this.d[n5 + 1] = new bn(n3 * 2 + this.d[n5].d, n4 * 4 + n6 * 3, n7, n6, z.a && z.b ? "Vi" : "En", -3);
            this.d[n5 + 1].a(this, 9);
            g = n5 + 1;
            this.d[n5 + 3] = new bn(this.d[n5 + 2].b + this.d[n5 + 2].d + n3, n4 * 4 + n6 * 3, n7, n6, "<", -3);
            this.d[n5 + 4] = new bn(this.d[n5 + 3].b + this.d[n5 + 3].d + n3, n4 * 4 + n6 * 3, n7, n6, ">", -4);
            this.d[n5 + 5] = new bn(this.d[n5 + 4].b + this.d[n5 + 4].d + n3, n4 * 4 + n6 * 3, n8 - n3 * 3 - n7, n6, e, 0);
            this.d[n5 + 5].a(this, 5);
            return;
        }
        if (n2 == 3) {
            this.d = new bn[33];
            String string = "1234567890";
            n7 = (this.o - n3 * 11) / 10;
            int n10 = (this.o - n7 * 10 - n3 * 9) / 2;
            int n11 = 0;
            while (n11 < string.length()) {
                this.d[n11 + 0] = new bn(n10 + n3 * n11 + n11 * n7, n4, n7, n6, "" + string.charAt(n11), string.charAt(n11));
                ++n11;
            }
            n5 = 0 + string.length();
            string = "-/:;()$&@\"";
            n11 = 0;
            while (n11 < string.length()) {
                this.d[n5 + n11] = new bn((z.r - n7 * 10 - n3 * 9) / 2 + n3 * n11 + n11 * n7, n4 * 2 + n6, n7, n6, "" + string.charAt(n11), string.charAt(n11));
                ++n11;
            }
            n5 = n5 + string.length() + 1;
            string = ".,?!'";
            n10 = (z.r - n7 * 7 - n3 * 4) / 2;
            n11 = 0;
            while (n11 < string.length()) {
                this.d[n5 + n11] = new bn(n10 + n3 * n11 + n11 * n7 * 7 / 5, n4 * 3 + n6 * 2, n7 * 7 / 5, n6, "" + string.charAt(n11), string.charAt(n11));
                ++n11;
            }
            this.d[n5 - 1] = new bn(n3, n4 * 3 + n6 * 2, n10 - n3 * 2, n6, "#+=", 0);
            this.d[n5 - 1].a(this, 7);
            f = n5 - 1;
            this.d[n5 + 5] = new bn(this.o - n3 - this.d[n5 - 1].d, n4 * 3 + n6 * 2, n10 - n3 * 2, n6, "DEL", -8);
            n5 = n5 + string.length() + 1;
            n10 = (z.r - n7 * 5 - n3 * 4) / 2;
            this.d[n5 + 2] = new bn(n10, n4 * 4 + n6 * 3, n7 * 4 + n3 * 4, n6, z.a && z.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar", 32);
            h = n5 + 2;
            this.d[n5] = new bn(n3, n4 * 4 + n6 * 3, n10 - n3 * 3 - n7, n6, "ABC", 0);
            this.d[n5].a(this, 8);
            this.d[n5 + 1] = new bn(n3 * 2 + this.d[n5].d, n4 * 4 + n6 * 3, n7, n6, z.a && z.b ? "Vi" : "En", -3);
            this.d[n5 + 1].a(this, 9);
            g = n5 + 1;
            this.d[n5 + 3] = new bn(this.d[n5 + 2].b + this.d[n5 + 2].d + n3, n4 * 4 + n6 * 3, n7, n6, "<", -3);
            this.d[n5 + 4] = new bn(this.d[n5 + 3].b + this.d[n5 + 3].d + n3, n4 * 4 + n6 * 3, n7, n6, ">", -4);
            this.d[n5 + 5] = new bn(this.d[n5 + 4].b + this.d[n5 + 4].d + n3, n4 * 4 + n6 * 3, n10 - n3 * 3 - n7, n6, e, 0);
            this.d[n5 + 5].a(this, 5);
            return;
        }
        if (n2 == 4) {
            this.d = new bn[33];
            String string = "[]{}#%^*+=";
            n7 = (this.o - n3 * 11) / 10;
            int n12 = (this.o - n7 * 10 - n3 * 9) / 2;
            int n13 = 0;
            while (n13 < string.length()) {
                this.d[n13 + 0] = new bn(n12 + n3 * n13 + n13 * n7, n4, n7, n6, "" + string.charAt(n13), string.charAt(n13));
                ++n13;
            }
            n5 = 0 + string.length();
            string = "_\\|~<>$&@\"";
            n13 = 0;
            while (n13 < string.length()) {
                this.d[n5 + n13] = new bn((z.r - n7 * 10 - n3 * 9) / 2 + n3 * n13 + n13 * n7, n4 * 2 + n6, n7, n6, "" + string.charAt(n13), string.charAt(n13));
                ++n13;
            }
            n5 = n5 + string.length() + 1;
            string = ".,?!'";
            n12 = (z.r - n7 * 7 - n3 * 4) / 2;
            n13 = 0;
            while (n13 < string.length()) {
                this.d[n5 + n13] = new bn(n12 + n3 * n13 + n13 * n7 * 7 / 5, n4 * 3 + n6 * 2, n7 * 7 / 5, n6, "" + string.charAt(n13), string.charAt(n13));
                ++n13;
            }
            this.d[n5 - 1] = new bn(n3, n4 * 3 + n6 * 2, n12 - n3 * 2, n6, "123", 0);
            f = n5 - 1;
            this.d[n5 - 1].a(this, 3);
            this.d[n5 + 5] = new bn(this.o - n3 - this.d[n5 - 1].d, n4 * 3 + n6 * 2, n12 - n3 * 2, n6, "DEL", -8);
            n5 = n5 + string.length() + 1;
            n12 = (z.r - n7 * 5 - n3 * 4) / 2;
            this.d[n5 + 2] = new bn(n12, n4 * 4 + n6 * 3, n7 * 4 + n3 * 4, n6, z.a && z.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar", 32);
            h = n5 + 2;
            this.d[n5] = new bn(n3, n4 * 4 + n6 * 3, n12 - n3 * 3 - n7, n6, "ABC", 0);
            this.d[n5].a(this, 8);
            this.d[n5 + 1] = new bn(n3 * 2 + this.d[n5].d, n4 * 4 + n6 * 3, n7, n6, z.a && z.b ? "Vi" : "En", -3);
            this.d[n5 + 1].a(this, 9);
            g = n5 + 1;
            this.d[n5 + 3] = new bn(this.d[n5 + 2].b + this.d[n5 + 2].d + n3, n4 * 4 + n6 * 3, n7, n6, "<", -3);
            this.d[n5 + 4] = new bn(this.d[n5 + 3].b + this.d[n5 + 3].d + n3, n4 * 4 + n6 * 3, n7, n6, ">", -4);
            this.d[n5 + 5] = new bn(this.d[n5 + 4].b + this.d[n5 + 4].d + n3, n4 * 4 + n6 * 3, n12 - n3 * 3 - n7, n6, e, 0);
            this.d[n5 + 5].a(this, 5);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(i);
        graphics.fillRect(n2 + this.m, n3 + this.n, this.o, this.p);
        if (this.d != null && this.d.length > 0) {
            int n4 = 0;
            while (n4 < this.d.length) {
                this.d[n4].a(graphics, this.m + n2, n3 + this.n);
                ++n4;
            }
        }
    }

    public final void i() {
        if (this.n != this.a) {
            if (this.a - this.n >= -3 && this.a - this.n <= 3) {
                this.n = this.a;
                return;
            }
            this.n += (this.a - this.n) / 2;
        }
    }

    public final void g(int n2, int n3) {
        n3 -= this.n;
        try {
            if (this.d != null && this.d.length > 0) {
                int n4 = 0;
                while (n4 < this.d.length) {
                    if (n2 >= this.d[n4].b && n2 <= this.d[n4].b + this.d[n4].d && n3 >= this.d[n4].c && n3 <= this.d[n4].c + this.d[n4].e) {
                        this.d[n4].a(n2, n3);
                        return;
                    }
                    ++n4;
                }
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void h(int n2, int n3) {
        n3 -= this.n;
        try {
            if (this.d != null && this.d.length > 0) {
                int n4 = 0;
                while (n4 < this.d.length) {
                    if (this.d[n4].g == 1) {
                        this.d[n4].b(n2, n3);
                        if (this.d[n4].h >= 65 && this.d[n4].h <= 90 && this.d[bo.f].f.equals("Shift")) {
                            int n5 = 0;
                            while (n5 < this.d.length) {
                                if (this.d[n5].h >= 65 && this.d[n5].h <= 90) {
                                    this.d[n5].f = this.d[n5].f.toLowerCase();
                                    this.d[n5].h += 32;
                                }
                                ++n5;
                            }
                            this.d[bo.f].f = "shift";
                        }
                    }
                    ++n4;
                }
                return;
            }
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
    }

    public final void d(int n2, int n3) {
        if (n3 == 6) {
            if (this.d[bo.f].f.equals("SHIFT")) {
                n2 = 0;
                while (n2 < this.d.length) {
                    if (this.d[n2].h >= 65 && this.d[n2].h <= 90) {
                        this.d[n2].f = this.d[n2].f.toLowerCase();
                        this.d[n2].h = this.d[n2].f.charAt(0);
                    }
                    ++n2;
                }
                this.d[bo.f].f = "shift";
                c = 2;
                return;
            }
            if (this.d[bo.f].f.equals("shift")) {
                n2 = 0;
                while (n2 < this.d.length) {
                    if (this.d[n2].h >= 97 && this.d[n2].h <= 122) {
                        this.d[n2].f = this.d[n2].f.toUpperCase();
                        this.d[n2].h = this.d[n2].f.charAt(0);
                    }
                    ++n2;
                }
                this.d[bo.f].f = "Shift";
                c = 1;
                return;
            }
            if (this.d[bo.f].f.equals("Shift")) {
                this.d[bo.f].f = "SHIFT";
                c = 0;
                return;
            }
        } else {
            if (n3 == 5) {
                if (b != null) {
                    b.a();
                }
                ak.a().f();
                return;
            }
            if (n3 == 3) {
                this.b(3);
                return;
            }
            if (n3 == 7) {
                this.b(4);
                return;
            }
            if (n3 == 8) {
                this.b(c);
                return;
            }
            if (n3 == 9) {
                if (!z.a) {
                    return;
                }
                z.b = !z.b;
                this.d[bo.g].f = z.b ? "Vi" : "En";
                this.d[bo.h].f = z.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar";
            }
        }
    }
}

