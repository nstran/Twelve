/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bk
extends at
implements bf {
    private static int c = 0;
    private bj[] d;
    public int a;
    private static String e = "OK";
    private static int f;
    private static int g;
    private static int h;
    private static int i;
    public static be b;

    static {
        i = 8160656;
        b = null;
    }

    public static void a(int n2) {
        bj.a = 0xFFFFFF;
    }

    public bk(int n2) {
        this.b(n2);
        bk bk2 = this;
        this.n = v.u;
        bk2.a = v.u - bk2.p;
    }

    private void b(int n2) {
        int n3;
        int n4;
        this.m = 0;
        this.o = v.t;
        this.p = v.u / 2;
        if (this.p < 160) {
            this.p = 160;
        }
        this.n = v.u - this.p;
        int n5 = 4;
        int n6 = 8;
        if (v.d != 3) {
            n5 = 2;
            n6 = 4;
        }
        if ((n4 = (this.p - 5 * n6) / 4) > (n3 = (this.o - n5 * 11) / 10) * 5 / 4) {
            n4 = n3 * 5 / 4;
            this.p = 4 * n4 + 5 * n6;
            this.n = v.u - this.p;
        }
        if (n2 == 0 || n2 == 1 || n2 == 2) {
            c = n2;
            this.d = new bj[34];
            String string = c == 2 ? "qwertyuiop" : "QWERTYUIOP";
            n3 = (this.o - n5 * 11) / 10;
            int n7 = (this.o - n3 * 10 - n5 * 9) / 2;
            int n8 = 0;
            while (n8 < string.length()) {
                this.d[n8 + 0] = new bj(n7 + n5 * n8 + n8 * n3, n6, n3, n4, "" + string.charAt(n8), string.charAt(n8));
                ++n8;
            }
            int n9 = 0 + string.length();
            string = c == 2 ? "asdfghjkl" : "ASDFGHJKL";
            n8 = 0;
            while (n8 < string.length()) {
                this.d[n9 + n8] = new bj((v.t - n3 * 9 - n5 * 8) / 2 + n5 * n8 + n8 * n3, 2 * n6 + n4, n3, n4, "" + string.charAt(n8), string.charAt(n8));
                ++n8;
            }
            n9 = n9 + string.length() + 1;
            string = c == 2 ? "zxcvbnm" : "ZXCVBNM";
            n7 = (v.t - n3 * 7 - n5 * 6) / 2;
            n8 = 0;
            while (n8 < string.length()) {
                this.d[n9 + n8] = new bj(n7 + n5 * n8 + n8 * n3, 3 * n6 + 2 * n4, n3, n4, "" + string.charAt(n8), string.charAt(n8));
                ++n8;
            }
            if (c == 2) {
                this.d[n9 - 1] = new bj(n5, 3 * n6 + 2 * n4, n7 - 2 * n5, n4, "shift", 0);
            } else if (c == 1) {
                this.d[n9 - 1] = new bj(n5, 3 * n6 + 2 * n4, n7 - 2 * n5, n4, "Shift", 0);
            } else if (c == 0) {
                this.d[n9 - 1] = new bj(n5, 3 * n6 + 2 * n4, n7 - 2 * n5, n4, "SHIFT", 0);
            }
            f = n9 - 1;
            this.d[n9 - 1].a(this, 6);
            this.d[n9 + 7] = new bj(this.o - n5 - this.d[n9 - 1].d, 3 * n6 + 2 * n4, n7 - 2 * n5, n4, "DEL", -8);
            n9 = n9 + string.length() + 1;
            n7 = (v.t - 5 * n3 - 4 * n5) / 2;
            this.d[n9 + 2] = new bj(n7, 4 * n6 + 3 * n4, 4 * n3 + 4 * n5, n4, v.a && v.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar", 32);
            h = n9 + 2;
            this.d[n9] = new bj(n5, 4 * n6 + 3 * n4, n7 - 3 * n5 - n3, n4, ".?123", 0);
            this.d[n9].a(this, 3);
            this.d[n9 + 1] = new bj(2 * n5 + this.d[n9].d, 4 * n6 + 3 * n4, n3, n4, v.a && v.b ? "Vi" : "En", -3);
            this.d[n9 + 1].a(this, 9);
            g = n9 + 1;
            this.d[n9 + 3] = new bj(this.d[n9 + 2].b + this.d[n9 + 2].d + n5, 4 * n6 + 3 * n4, n3, n4, "<", -3);
            this.d[n9 + 4] = new bj(this.d[n9 + 3].b + this.d[n9 + 3].d + n5, 4 * n6 + 3 * n4, n3, n4, ">", -4);
            this.d[n9 + 5] = new bj(this.d[n9 + 4].b + this.d[n9 + 4].d + n5, 4 * n6 + 3 * n4, n7 - 3 * n5 - n3, n4, e, 0);
            this.d[n9 + 5].a(this, 5);
            return;
        }
        if (n2 == 3) {
            this.d = new bj[33];
            String string = "1234567890";
            n3 = (this.o - n5 * 11) / 10;
            int n10 = (this.o - n3 * 10 - n5 * 9) / 2;
            int n11 = 0;
            while (n11 < string.length()) {
                this.d[n11 + 0] = new bj(n10 + n5 * n11 + n11 * n3, n6, n3, n4, "" + string.charAt(n11), string.charAt(n11));
                ++n11;
            }
            int n12 = 0 + string.length();
            string = "-/:;()$&@\"";
            n11 = 0;
            while (n11 < string.length()) {
                this.d[n12 + n11] = new bj((v.t - n3 * 10 - n5 * 9) / 2 + n5 * n11 + n11 * n3, 2 * n6 + n4, n3, n4, "" + string.charAt(n11), string.charAt(n11));
                ++n11;
            }
            n12 = n12 + string.length() + 1;
            string = ".,?!'";
            n10 = (v.t - n3 * 7 - 4 * n5) / 2;
            n11 = 0;
            while (n11 < string.length()) {
                this.d[n12 + n11] = new bj(n10 + n5 * n11 + n11 * n3 * 7 / 5, 3 * n6 + 2 * n4, n3 * 7 / 5, n4, "" + string.charAt(n11), string.charAt(n11));
                ++n11;
            }
            this.d[n12 - 1] = new bj(n5, 3 * n6 + 2 * n4, n10 - 2 * n5, n4, "#+=", 0);
            this.d[n12 - 1].a(this, 7);
            f = n12 - 1;
            this.d[n12 + 5] = new bj(this.o - n5 - this.d[n12 - 1].d, 3 * n6 + 2 * n4, n10 - 2 * n5, n4, "DEL", -8);
            n12 = n12 + string.length() + 1;
            n10 = (v.t - 5 * n3 - 4 * n5) / 2;
            this.d[n12 + 2] = new bj(n10, 4 * n6 + 3 * n4, 4 * n3 + 4 * n5, n4, v.a && v.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar", 32);
            h = n12 + 2;
            this.d[n12] = new bj(n5, 4 * n6 + 3 * n4, n10 - 3 * n5 - n3, n4, "ABC", 0);
            this.d[n12].a(this, 8);
            this.d[n12 + 1] = new bj(2 * n5 + this.d[n12].d, 4 * n6 + 3 * n4, n3, n4, v.a && v.b ? "Vi" : "En", -3);
            this.d[n12 + 1].a(this, 9);
            g = n12 + 1;
            this.d[n12 + 3] = new bj(this.d[n12 + 2].b + this.d[n12 + 2].d + n5, 4 * n6 + 3 * n4, n3, n4, "<", -3);
            this.d[n12 + 4] = new bj(this.d[n12 + 3].b + this.d[n12 + 3].d + n5, 4 * n6 + 3 * n4, n3, n4, ">", -4);
            this.d[n12 + 5] = new bj(this.d[n12 + 4].b + this.d[n12 + 4].d + n5, 4 * n6 + 3 * n4, n10 - 3 * n5 - n3, n4, e, 0);
            this.d[n12 + 5].a(this, 5);
            return;
        }
        if (n2 == 4) {
            this.d = new bj[33];
            String string = "[]{}#%^*+=";
            n3 = (this.o - n5 * 11) / 10;
            int n13 = (this.o - n3 * 10 - n5 * 9) / 2;
            int n14 = 0;
            while (n14 < string.length()) {
                this.d[n14 + 0] = new bj(n13 + n5 * n14 + n14 * n3, n6, n3, n4, "" + string.charAt(n14), string.charAt(n14));
                ++n14;
            }
            int n15 = 0 + string.length();
            string = "_\\|~<>$&@\"";
            n14 = 0;
            while (n14 < string.length()) {
                this.d[n15 + n14] = new bj((v.t - n3 * 10 - n5 * 9) / 2 + n5 * n14 + n14 * n3, 2 * n6 + n4, n3, n4, "" + string.charAt(n14), string.charAt(n14));
                ++n14;
            }
            n15 = n15 + string.length() + 1;
            string = ".,?!'";
            n13 = (v.t - n3 * 7 - 4 * n5) / 2;
            n14 = 0;
            while (n14 < string.length()) {
                this.d[n15 + n14] = new bj(n13 + n5 * n14 + n14 * n3 * 7 / 5, 3 * n6 + 2 * n4, n3 * 7 / 5, n4, "" + string.charAt(n14), string.charAt(n14));
                ++n14;
            }
            this.d[n15 - 1] = new bj(n5, 3 * n6 + 2 * n4, n13 - 2 * n5, n4, "123", 0);
            f = n15 - 1;
            this.d[n15 - 1].a(this, 3);
            this.d[n15 + 5] = new bj(this.o - n5 - this.d[n15 - 1].d, 3 * n6 + 2 * n4, n13 - 2 * n5, n4, "DEL", -8);
            n15 = n15 + string.length() + 1;
            n13 = (v.t - 5 * n3 - 4 * n5) / 2;
            this.d[n15 + 2] = new bj(n13, 4 * n6 + 3 * n4, 4 * n3 + 4 * n5, n4, v.a && v.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar", 32);
            h = n15 + 2;
            this.d[n15] = new bj(n5, 4 * n6 + 3 * n4, n13 - 3 * n5 - n3, n4, "ABC", 0);
            this.d[n15].a(this, 8);
            this.d[n15 + 1] = new bj(2 * n5 + this.d[n15].d, 4 * n6 + 3 * n4, n3, n4, v.a && v.b ? "Vi" : "En", -3);
            this.d[n15 + 1].a(this, 9);
            g = n15 + 1;
            this.d[n15 + 3] = new bj(this.d[n15 + 2].b + this.d[n15 + 2].d + n5, 4 * n6 + 3 * n4, n3, n4, "<", -3);
            this.d[n15 + 4] = new bj(this.d[n15 + 3].b + this.d[n15 + 3].d + n5, 4 * n6 + 3 * n4, n3, n4, ">", -4);
            this.d[n15 + 5] = new bj(this.d[n15 + 4].b + this.d[n15 + 4].d + n5, 4 * n6 + 3 * n4, n13 - 3 * n5 - n3, n4, e, 0);
            this.d[n15 + 5].a(this, 5);
        }
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(i);
        graphics.fillRect(n2 + this.m, n3 + this.n, this.o, this.p);
        if (this.d != null && this.d.length > 0) {
            int n4 = 0;
            while (n4 < this.d.length) {
                int n5 = n3 + this.n;
                int n6 = this.m + n2;
                Graphics graphics2 = graphics;
                bj bj2 = this.d[n4];
                int n7 = bj.a;
                if (bj2.g == 0) {
                    cw.b(graphics2, 0x555555, bj2.b + 1 + n6, bj2.c + 1 + n5, bj2.d, bj2.e);
                    graphics2.setColor(n7);
                    graphics2.fillRect(bj2.b + 1 + n6, bj2.c + 1 + n5, bj2.d - 2, bj2.e - 2);
                    cw.b(graphics2, 0x888888, bj2.b + n6, bj2.c + n5, bj2.d, bj2.e);
                    bx.d.a(graphics2, bj2.f, bj2.b + bj2.d / 2 + n6, bj2.c + n5 + (bj2.e - bx.d.a()) / 2, 1);
                } else if (bj2.g == 1) {
                    graphics2.setColor(n7);
                    graphics2.fillRect(bj2.b + n6 + 1, bj2.c + n5 + 1 - 2 - bj2.e, bj2.d - 1, bj2.e + 4 + bj2.e);
                    cw.b(graphics2, 0x555555, bj2.b + n6, bj2.c - bj2.e + 2 + n5 - 4, bj2.d + 1, bj2.e + 6 + bj2.e);
                    bx.d.a(graphics2, bj2.f, bj2.b + bj2.d / 2 + n6, bj2.c - bj2.e + n5 + (bj2.e - bx.d.a()) / 2, 1);
                }
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
                        bj bj2 = this.d[n4];
                        n4 = n3;
                        n3 = n2;
                        bj bj3 = bj2;
                        if (n3 >= bj3.b && n3 <= bj3.b + bj3.d && n4 >= bj3.c && n4 <= bj3.c + bj3.e) {
                            bj3.g = 1;
                            if (bj3.i == null && bj3.h != -1987) {
                                ag.c(bj3.h);
                            }
                        }
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
                        int n5 = n3;
                        int n6 = n2;
                        bj bj2 = this.d[n4];
                        this.d[n4].g = 0;
                        if (bj2.i != null && n6 >= bj2.b && n6 <= bj2.b + bj2.d && n5 >= bj2.c && n5 <= bj2.c + bj2.e) {
                            bj2.i.d(-1, bj2.j);
                        } else if (bj2.h != 0) {
                            ag.d(bj2.h);
                        }
                        if (this.d[n4].h >= 65 && this.d[n4].h <= 90 && this.d[bk.f].f.equals("Shift")) {
                            int n7 = 0;
                            while (n7 < this.d.length) {
                                if (this.d[n7].h >= 65 && this.d[n7].h <= 90) {
                                    this.d[n7].f = this.d[n7].f.toLowerCase();
                                    this.d[n7].h += 32;
                                }
                                ++n7;
                            }
                            this.d[bk.f].f = "shift";
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
            if (this.d[bk.f].f.equals("SHIFT")) {
                n2 = 0;
                while (n2 < this.d.length) {
                    if (this.d[n2].h >= 65 && this.d[n2].h <= 90) {
                        this.d[n2].f = this.d[n2].f.toLowerCase();
                        this.d[n2].h = this.d[n2].f.charAt(0);
                    }
                    ++n2;
                }
                this.d[bk.f].f = "shift";
                c = 2;
                return;
            }
            if (this.d[bk.f].f.equals("shift")) {
                n2 = 0;
                while (n2 < this.d.length) {
                    if (this.d[n2].h >= 97 && this.d[n2].h <= 122) {
                        this.d[n2].f = this.d[n2].f.toUpperCase();
                        this.d[n2].h = this.d[n2].f.charAt(0);
                    }
                    ++n2;
                }
                this.d[bk.f].f = "Shift";
                c = 1;
                return;
            }
            if (this.d[bk.f].f.equals("Shift")) {
                this.d[bk.f].f = "SHIFT";
                c = 0;
                return;
            }
        } else {
            if (n3 == 5) {
                if (b != null) {
                    b.a();
                }
                ag.a().e();
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
                if (!v.a) {
                    return;
                }
                v.b = !v.b;
                this.d[bk.g].f = v.b ? "Vi" : "En";
                this.d[bk.h].f = v.b ? "Kho\u1ea3ng tr\u1eafng" : "SpaceBar";
            }
        }
    }
}

