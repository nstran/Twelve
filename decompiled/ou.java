/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.Image;

public final class ou {
    private a a;
    private ou b;

    public ou(ou ou2) {
        this.b = ou2;
        this.a = new a();
    }

    public final void a() {
        this.a.a();
    }

    public final Image a(int n2, boolean bl2) {
        byte[] byArray;
        Object object;
        ou ou2;
        block4: {
            if (this.b != null && (ou2 = this.b.a(n2, false)) != null) {
                return ou2;
            }
            long l2 = n2;
            ou2 = this;
            int n3 = 0;
            while (n3 < ou2.a.d()) {
                os os2 = (os)ou2.a.b(n3);
                if ((long)os2.a == l2) {
                    object = os2.b;
                    break block4;
                }
                ++n3;
            }
            object = ou2 = null;
        }
        if (object == null && bl2 && (byArray = ox.a().b(n2)) != null) {
            ou2 = f.a(byArray);
            this.a.a(new os(n2, (Image)ou2));
        }
        return ou2;
    }

    public final void a(int n2, Image image) {
        int n3 = 0;
        while (n3 < this.a.d()) {
            os os2 = (os)this.a.b(n3);
            if (os2.a == n2) {
                os2.b = image;
                return;
            }
            ++n3;
        }
        this.a.a(new os(n2, image));
    }
}

