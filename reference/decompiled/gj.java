/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class gj
extends ey {
    private String i;
    private bl j = null;
    private byte k;
    private int[] l = new int[]{8076544, 12410368, 14052096, 16746248, 16746248, 12935680, 12935680, 10243584};
    private int[] m = new int[]{0x888888, 0xEEEEEE, 0xFFFFFF, 0xFFFFFF, 0xDBDBDB, 0xDBDBDB, 0xCADADAD, 0xCADADAD, 0x9C9C9C};
    private int[] n = new int[]{0x3D3D3D, 0x5E5E5E, 0x6B6B6B, 0x838383, 0x838383, 0x626262, 0x626262, 0x4E4E4E, 0x4E4E4E, 0x464646, 0x464646};

    public gj(String string) {
        this.i = string;
        this.d = new n(0, 0, this.d.c, 9 + ca.d.a());
    }

    public final void a(byte by2) {
        this.k = 1;
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        this.c = true;
    }

    public final void b(int n2, int n3) {
        super.b(n2, n3);
        this.d = new n(this.c(), this.d(), this.d.c, 9 + ca.d.a());
    }

    public final void a(int n2, int n3, int n4, int n5) {
        this.d = new n(n2, n3, n4, 9 + ca.d.a());
        this.i = com.mg.sq.a.a(this.i, this.d.c - 30);
    }

    public final void e(boolean bl2) {
        super.e(bl2);
        this.j.a(this, bl2);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        if (this.m()) {
            oz.a(graphics, this.c() + n2, this.d() + n3, this.e(), this.f(), 7070703, -1);
            graphics.setColor(7267055);
            graphics.fillRect(this.c() + n2, this.d() + n3, this.d.c, this.d.d);
        }
        int n4 = (n3 += this.d()) + 4;
        int n5 = (n2 += this.c()) + 4;
        ca.d.c(true);
        ca.d.a(graphics, this.i, n5, n4, 0);
        ca.d.c(false);
        n2 += this.e() - 30;
        n4 = this.f() - 6;
        n4 = (n3 += 3) + (n4 - ca.d.b()) / 2;
        n n6 = new n(0, 0, 30, this.f() - 6);
        n n7 = new n(0, 0, 32, this.f() - 4);
        switch (this.k) {
            case 0: {
                if (this.a()) {
                    oz.a(graphics, n7, 32, new int[]{0x2D2D2D}, n2 - 1, n3 - 1);
                    oz.a(graphics, n6, 30, this.l, n2, n3);
                    ca.d.a(graphics, "O", n2 + 7, n4, 1);
                    n6 = new n(0, 0, 15, n6.d);
                    oz.a(graphics, n6, 15, this.n, n2 + 15, n3);
                    break;
                }
                oz.a(graphics, n7, 32, new int[]{0x2D2D2D}, n2 - 1, n3 - 1);
                oz.a(graphics, n6, 30, this.m, n2, n3);
                ca.d.a(graphics, "I", n2 + 22, n4, 1);
                n6 = new n(0, 0, 15, n6.d);
                oz.a(graphics, n6, 15, this.n, n2, n3);
                break;
            }
            default: {
                if (this.a()) {
                    com.mg.sq.a.g.a(graphics, "M\u1edf", n2 + 7, n4, 1);
                    break;
                }
                com.mg.sq.a.g.a(graphics, "T\u1eaft", n2 + 7, n4, 1);
            }
        }
        this.c = false;
    }

    public final void a(bl bl2) {
        this.j = bl2;
    }
}

