/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import javax.microedition.lcdui.Graphics;

public final class ev
extends aq {
    private boolean i = true;
    private dt j;
    private String k = "";

    public ev(dt object, boolean bl2, int n2) {
        int n3;
        StringBuffer stringBuffer;
        Object object2;
        this.d(n2);
        dt dt2 = object;
        object = this;
        this.j = dt2;
        ds[] dsArray = dt2.c();
        if (gr.c && dsArray != null) {
            int n4 = 0;
            int n5 = 0;
            while (n5 < dsArray.length) {
                if (dsArray[n5].e() == 2) {
                    ++n4;
                }
                ++n5;
            }
            object2 = object;
            stringBuffer = new StringBuffer(String.valueOf(dt2.b())).append("(").append(n4).append("/");
            n3 = dsArray.length;
        } else {
            object2 = object;
            stringBuffer = new StringBuffer(String.valueOf(dt2.b())).append("(");
            n3 = dsArray == null ? 0 : dsArray.length;
        }
        ((ev)object2).k = stringBuffer.append(n3).append(")").toString();
        boolean bl3 = bl2;
        object = this;
        this.i = bl3;
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        if (bl2) {
            this.e(40);
            return;
        }
        this.e(22);
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        n3 += this.d();
        n2 += this.c() + 2;
        d d2 = bx.d;
        d2.c(true);
        if (this.g) {
            pc.e(graphics, n2 - 2, n3, this.e(), this.f());
        }
        d2.a(graphics, this.k, n2 + 25, n3 + 6, 0);
        d2.c();
        if (this.g) {
            d2 = bx.c;
            d2.a(graphics, this.j.b(), n2 + 25, n3 + 19, 0);
        }
        if (this.i) {
            pc.d(graphics, -18, n2 + 3, n3 + 9, 0);
        } else {
            pc.d(graphics, -17, n2 + 3, n3 + 9, 0);
        }
        this.c = false;
    }
}

