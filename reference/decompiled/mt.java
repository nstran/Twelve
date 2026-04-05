/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 *  javax.microedition.lcdui.Image
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public final class mt {
    private Image a;
    private Image b;
    private boolean c;

    public mt(mr mr2, boolean bl2) {
        this.c = bl2;
        if (!this.c) {
            if (com.mg.sq.a.i == 0) {
                this.a = f.d("/play/bkboardv");
                return;
            }
            this.a = f.d("/play/bkboardh");
            return;
        }
        this.b = f.d("/play/ground");
    }

    public final void a(Graphics graphics, int n2, int n3, int n4) {
        if (!this.c) {
            graphics.drawImage(this.a, n2, n3, 0);
            return;
        }
        graphics.setColor(9828603);
        if (com.mg.sq.a.i == 0) {
            graphics.drawLine(n2, n3, n2 + 240, n3);
            graphics.fillRect(n2, n3, 5, 232);
            graphics.fillRect(n2 + 235, n3, 5, 232);
            graphics.fillRect(n2, n3 + 232, mt.a(), mt.b());
        } else {
            graphics.drawLine(n2, n3, n2 + 320, n3);
            graphics.fillRect(n2, n3, 45, 232);
            graphics.fillRect(n2 + 275, n3, 45, 232);
        }
        n4 = 0;
        while (n4 <= mt.a() / this.b.getWidth()) {
            graphics.drawImage(this.b, n2 + this.b.getWidth() * n4, n3 + mt.b() - this.b.getHeight(), 0);
            ++n4;
        }
        if (com.mg.sq.a.i == 0) {
            ot.a(n2, n3, graphics);
            return;
        }
        ot.b(n2, n3, graphics);
    }

    public static int a() {
        if (com.mg.sq.a.i == 0) {
            return 240;
        }
        return 320;
    }

    public static int b() {
        if (com.mg.sq.a.i == 0) {
            return 320;
        }
        return 240;
    }

    public final void c() {
        this.a = null;
        this.b = null;
    }
}

