/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class ox {
    private a a;
    private ox b;

    public ox(ox ox2) {
        this.b = ox2;
        this.a = new a();
    }

    public final void a() {
        this.a.a();
    }

    public final Image a(int n2, boolean bl2) {
        Object object;
        int n3;
        ox ox2;
        block5: {
            if (this.b != null && (ox2 = this.b.a(n2, false)) != null) {
                return ox2;
            }
            long l2 = n2;
            ox2 = this;
            n3 = 0;
            while (n3 < ox2.a.d()) {
                ov ov2 = (ov)ox2.a.b(n3);
                if ((long)ov2.a == l2) {
                    object = ov2.b;
                    break block5;
                }
                ++n3;
            }
            object = ox2 = null;
        }
        if (object == null && bl2) {
            n3 = n2;
            Object object2 = pa.a();
            byte[] byArray = ((pa)object2).b(n3, false);
            object2 = byArray;
            if (byArray != null) {
                ox2 = f.a((byte[])object2);
                this.a.a(new ov(n2, (Image)ox2));
            }
        }
        return ox2;
    }

    public final void a(int n2, Image image) {
        int n3 = 0;
        while (n3 < this.a.d()) {
            ov ov2 = (ov)this.a.b(n3);
            if (ov2.a == n2) {
                ov2.b = image;
                return;
            }
            ++n3;
        }
        this.a.a(new ov(n2, image));
    }
}

