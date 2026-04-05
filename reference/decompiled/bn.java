/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class bn {
    static int a = 0x383837;
    public int b;
    public int c;
    public int d;
    public int e;
    public String f = " ";
    public int g = 0;
    public int h = -1;
    private bj i;
    private int j = -1987;

    public bn(int n2, int n3, int n4, int n5, String string, int n6) {
        this.b = n2;
        this.c = n3;
        this.d = n4;
        this.e = n5;
        this.f = string;
        this.h = n6;
    }

    public final void a(bj bj2, int n2) {
        this.i = bj2;
        this.j = n2;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        int n4 = a;
        if (this.g == 0) {
            cz.b(graphics, 0x555555, this.b + 1 + n2, this.c + 1 + n3, this.d, this.e);
            graphics.setColor(n4);
            graphics.fillRect(this.b + 1 + n2, this.c + 1 + n3, this.d - 2, this.e - 2);
            cz.b(graphics, 0x888888, this.b + n2, this.c + n3, this.d, this.e);
            ca.d.a(graphics, this.f, this.b + this.d / 2 + n2, this.c + n3 + (this.e - ca.d.a()) / 2, 1);
            return;
        }
        if (this.g == 1) {
            graphics.setColor(n4);
            graphics.fillRect(this.b + n2 + 1, this.c + n3 + 1 - 2 - this.e, this.d - 1, this.e + 4 + this.e);
            cz.b(graphics, 0x555555, this.b + n2, this.c - this.e + 2 + n3 - 4, this.d + 1, this.e + 6 + this.e);
            ca.d.a(graphics, this.f, this.b + this.d / 2 + n2, this.c - this.e + n3 + (this.e - ca.d.a()) / 2, 1);
        }
    }

    public final void a(int n2, int n3) {
        if (n2 >= this.b && n2 <= this.b + this.d && n3 >= this.c && n3 <= this.c + this.e) {
            this.g = 1;
            if (this.i == null && this.h != -1987) {
                ak.c(this.h);
            }
        }
    }

    public final void b(int n2, int n3) {
        this.g = 0;
        if (this.i != null && n2 >= this.b && n2 <= this.b + this.d && n3 >= this.c && n3 <= this.c + this.e) {
            this.i.d(-1, this.j);
            return;
        }
        if (this.h != 0) {
            ak.d(this.h);
        }
    }
}

