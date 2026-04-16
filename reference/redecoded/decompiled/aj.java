/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Canvas
 *  javax.microedition.lcdui.Displayable
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.smsgame.MGMIDlet;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;

public final class aj
extends Canvas
implements bf,
Runnable {
    private boolean a = false;
    private int b = 2;
    private al c;
    private int d;
    private int e;

    public aj() {
        this.setFullScreenMode(true);
        bx.a();
        v.z = this.hasPointerEvents();
        new Thread(this).start();
    }

    protected final void paint(Graphics object) {
        this.d = this.getWidth();
        this.e = this.getHeight();
        object.setColor(0);
        object.fillRect(0, 0, this.d, this.e);
        if (this.b > 0) {
            --this.b;
            if (this.b == 0) {
                object = this;
                v.t = object.d;
                v.u = object.e;
                try {
                    bw bw2 = new bw("C\u00e0i \u0111\u1eb7t tin nh\u1eafn");
                    bc bc2 = new bc("M\u00e1y \u0111i\u1ec7n tho\u1ea1i c\u1ee7a b\u1ea1n c\u1ea7n \u0111\u01b0\u1ee3c cho ph\u00e9p nh\u1eadn tin nh\u1eafn t\u1eeb h\u1ec7 th\u1ed1ng. H\u00e3y b\u1ea5m c\u00e0i \u0111\u1eb7t v\u00e0 sau \u0111\u00f3 ch\u1ecdn 'c\u00f3' ho\u1eb7c 'yes'.", 0);
                    bc bc3 = new bc("**Vi\u1ec7c cho ph\u00e9p nh\u1eadn tin nh\u1eafn \u0111\u1ec3 nh\u1eadn v\u1eadt ph\u1ea9m, mua m\u1ea1ng ch\u01a1i c\u0169ng nh\u01b0 c\u01a1 h\u1ed9i nh\u1eadn \u0111\u01b0\u1ee3c ph\u1ea7n th\u01b0\u1edfng t\u1eeb h\u1ec7 th\u1ed1ng.", 0);
                    bc3.a(bx.b);
                    bc bc4 = new bc("**Vi\u1ec7c kh\u00f4ng cho ph\u00e9p nh\u1eadn tin nh\u1eafn t\u1eeb h\u1ec7 th\u1ed1ng s\u1ebd l\u00e0m cho b\u1ea1n kh\u00f4ng th\u1ec3 mua m\u1ea1ng ch\u01a1i, c\u1eadp nh\u1eadt v\u1eadt ph\u1ea9m c\u0169ng nh\u01b0 kh\u00f4ng th\u1ec3 nh\u1eadn c\u00e1c gi\u1ea3i th\u01b0\u1edfng t\u1eeb h\u1ec7 th\u1ed1ng.", 0);
                    bc4.a(bx.b);
                    bw2.a(new au[]{bc2, bc3, bc4});
                    bw2.a((bf)object);
                    bw2.a(new bd("C\u00e0i \u0111\u1eb7t", -1));
                    object.c = bw2;
                    return;
                }
                catch (Exception exception) {
                    Exception exception2 = exception;
                    exception.printStackTrace();
                }
            }
            return;
        }
        this.c.h(true);
        this.c.a((Graphics)object, true);
    }

    public final void run() {
        this.a = false;
        while (!this.a) {
            try {
                long l2 = System.currentTimeMillis();
                this.repaint();
                long l3 = System.currentTimeMillis();
                if (l3 - l2 > 40L) continue;
                Thread.sleep(40L - (l3 - l2));
            }
            catch (Exception exception) {
                Exception exception2 = exception;
                ct.a(exception);
                exception2.printStackTrace();
            }
        }
        MGMIDlet.d().a();
        MGMIDlet.d();
        MGMIDlet.b();
        if (v.X) {
            new ce();
            return;
        }
        MGMIDlet.d().a((Displayable)new ce(), true);
    }

    public final void d(int n2, int n3) {
        this.a = true;
    }

    protected final void keyPressed(int n2) {
        if (this.a) {
            return;
        }
        n2 = ag.a(n2, true);
        v.c[n2] = 2;
    }

    protected final void keyReleased(int n2) {
        if (this.a) {
            return;
        }
        n2 = ag.a(n2, true);
        v.c[n2] = 1;
    }

    protected final void pointerPressed(int n2, int n3) {
        if (this.a) {
            return;
        }
        v.A = n2 - v.x;
        v.B = n3 - v.y;
    }

    protected final void pointerReleased(int n2, int n3) {
        if (this.a) {
            return;
        }
        v.C = n2 - v.x;
        v.D = n3 - v.y;
    }
}

