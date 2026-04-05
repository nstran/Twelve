/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.ConnectionNotFoundException
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.smsgame.MGMIDlet;
import com.mg.sq.a;
import javax.microedition.io.ConnectionNotFoundException;
import javax.microedition.lcdui.Graphics;

public final class nt
extends ar
implements bj {
    private String[] a;
    private ex b;

    public nt() {
        super(6);
        this.a(new be());
        this.a(com.mg.sq.a.l);
        this.a = ca.a("\nB\u1ea1n c\u00f3 ch\u1ea5p nh\u1eadn c\u00e1c quy \u0111\u1ecbnh c\u1ee7a Ola t\u1ea1i http://term.ola.vn kh\u00f4ng?", z.r - 4, ca.d);
        int n2 = 15 + ca.d.a() * (this.a.length + 1) + 4 + 10;
        this.b = new ex("\u0110\u1ed3ng \u00fd", 3);
        this.b.a((z.r - 80) / 2, n2, 80, 20);
        this.b.d(true);
        bv bv2 = new bv(new bu[]{new bu("\u0110\u1ed3ng \u00fd", 0), new bu("Quy \u0111\u1ecbnh", 1), new bu("Tho\u00e1t", 2)});
        bv2.a(this);
        this.a(bv2);
    }

    protected final void a(int n2) {
        if (n2 == 95) {
            this.d(this.e, 0);
        }
    }

    protected final void e(int n2, int n3) {
        if (this.b.h().b(n2, n3)) {
            this.d(this.e, 0);
        }
    }

    protected final void c() {
    }

    protected final void a(Graphics graphics) {
        graphics.setColor(z.af);
        graphics.fillRect(0, 0, z.r, z.s - be.a);
        graphics.drawImage(oz.d, z.r, z.s - be.a, 40);
        ca.d.c(true);
        ca.d.a(graphics, "Quy \u0111\u1ecbnh", z.r / 2, 15, 1);
        ca.d.c();
        int n2 = 15 + (ca.d.a() + 4);
        int n3 = 0;
        while (n3 < this.a.length) {
            ca.d.a(graphics, this.a[n3], z.r / 2, n2, 1);
            n2 += ca.d.a();
            ++n3;
        }
        this.b.a(graphics, 0, 0);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                pa.z();
                ak.b().f(5);
                break;
            }
            case 2: {
                MGMIDlet.f().d();
                break;
            }
            case 1: {
                try {
                    MGMIDlet.f().platformRequest("http://term.ola.vn");
                    break;
                }
                catch (ConnectionNotFoundException connectionNotFoundException) {
                    ConnectionNotFoundException connectionNotFoundException2 = connectionNotFoundException;
                    connectionNotFoundException.printStackTrace();
                }
            }
        }
        this.c(false);
    }
}

