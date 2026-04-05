/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import com.mg.sq.a;
import javax.microedition.lcdui.Graphics;

public final class de
extends ax {
    private n a;
    private n b;
    private n c;
    private n d;
    private n e;
    private lf f;
    private me g;

    public de(lf object) {
        this.f = object;
        this.g = lz.a((lf)object, false);
        this.g.a(la.a((lf)object));
        this.g.a(np.a((lf)object));
        this.g.c(2);
        this.o = 240;
        this.p = 70;
        object = this;
        this.a = new n(5, 5, 56, 63);
        ((de)object).b = new n(142, 5, 92, 14);
        ((de)object).c = new n(142, 22, 92, 14);
        ((de)object).d = new n(142, 39, 92, 14);
        ((de)object).e = new n(142, 56, 92, 14);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        graphics.setColor(z.ac);
        oz.b(graphics, this.b.a + n2, this.b.b + n3, this.b.c, this.b.d, 1070484, 16579764, 14542575);
        ca.d.a(graphics, "Danh Hi\u1ec7u", this.b.a + n2 - 6, this.b.b + n3, 2);
        oz.b(graphics, this.c.a + n2, this.c.b + n3, this.c.c, this.c.d, 1070484, 16579764, 14542575);
        ca.d.a(graphics, "X\u1ebfp H\u1ea1ng", this.c.a + n2 - 6, this.c.b + n3, 2);
        oz.b(graphics, this.d.a + n2, this.d.b + n3, this.d.c, this.d.d, 1070484, 16579764, 14542575);
        ca.d.a(graphics, "Bang", this.d.a + n2 - 6, this.d.b + n3, 2);
        oz.b(graphics, this.e.a + n2, this.e.b + n3, this.e.c, this.e.d, 1070484, 16579764, 14542575);
        ca.d.a(graphics, "Th\u1eafng/Thua", this.e.a + n2 - 6, this.e.b + n3, 2);
        if (this.g != null) {
            com.mg.sq.a.h.a(graphics, this.f.S, this.b.a + n2 + this.b.c / 2, this.b.b + n3, 1);
            ca.c.a(graphics, this.f.U, this.c.a + n2 + this.c.c / 2, this.c.b + n3, 1);
            ca.c.a(graphics, this.f.T, this.d.a + n2 + this.d.c / 2, this.d.b + n3, 1);
            ca.c.a(graphics, String.valueOf(this.f.O) + "/" + this.f.P, this.e.a + n2 + this.e.c / 2, this.e.b + n3, 1);
        }
        oz.c(graphics, this.a.a + n2, this.a.b + n3, this.a.c, this.a.d);
        if (this.g != null) {
            oz.b(graphics, this.a.a + n2 + this.a.c - 13, this.a.b + n3 + this.a.d - 16, this.f.g);
            this.g.a(graphics, this.a.a + n2 + 2, this.a.b + n3 + 7);
        }
    }

    public final void i() {
        if (this.g != null) {
            this.g.i();
        }
    }
}

