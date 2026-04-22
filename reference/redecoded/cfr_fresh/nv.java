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

public final class nv
extends an
implements bf {
    private String[] a;
    private ex b;

    public nv() {
        super(6);
        this.a(new ba());
        this.a(com.mg.sq.a.n);
        this.a = bx.a("\nB\u1ea1n c\u00f3 ch\u1ea5p nh\u1eadn c\u00e1c quy \u0111\u1ecbnh c\u1ee7a Ola t\u1ea1i http://term.ola.vn kh\u00f4ng?", v.t - 4, bx.d);
        int n2 = 15 + bx.d.a() * (this.a.length + 1) + 4 + 10;
        this.b = new ex("\u0110\u1ed3ng \u00fd", 3);
        this.b.a((v.t - 80) / 2, n2, 80, 20);
        this.b.d(true);
        bs bs2 = new bs(new br[]{new br("\u0110\u1ed3ng \u00fd", 0), new br("Quy \u0111\u1ecbnh", 1), new br("Tho\u00e1t", 2)});
        bs2.a(this);
        this.a(bs2);
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
        graphics.setColor(v.am);
        graphics.fillRect(0, 0, v.t, v.u - ba.a);
        graphics.drawImage(pc.d, v.t, v.u - ba.a, 40);
        bx.d.c(true);
        bx.d.a(graphics, "Quy \u0111\u1ecbnh", v.t / 2, 15, 1);
        bx.d.c();
        int n2 = 15 + (bx.d.a() + 4);
        int n3 = 0;
        while (n3 < this.a.length) {
            bx.d.a(graphics, this.a[n3], v.t / 2, n2, 1);
            n2 += bx.d.a();
            ++n3;
        }
        this.b.a(graphics, 0, 0);
    }

    public final void d(int n2, int n3) {
        switch (n3) {
            case 0: {
                pd.z();
                ag.b().f(5);
                break;
            }
            case 2: {
                MGMIDlet mGMIDlet = MGMIDlet.d();
                mGMIDlet.notifyDestroyed();
                break;
            }
            case 1: {
                try {
                    MGMIDlet.d().platformRequest("http://term.ola.vn");
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

