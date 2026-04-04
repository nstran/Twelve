/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

final class ee {
    private InputStream a;
    private dw b;

    public ee(InputStream inputStream) {
        this.a = inputStream;
    }

    public final dz a() {
        int n2;
        int n3;
        dz dz2 = new dz();
        byte[] byArray = new byte[7];
        int n4 = this.a(byArray);
        if (n4 <= 0) {
            return null;
        }
        int n5 = p.a(byArray[0], byArray[1]);
        dz2.a = n3 = p.a(byArray[2], byArray[3], byArray[4], byArray[5]);
        dz2.b = n2 = p.a(byArray[6]);
        if (n3 > 0) {
            dy[] dyArray = new dy[n5];
            n5 = 0;
            while (n5 < dyArray.length) {
                byte[] byArray2 = new byte[5];
                if (this.a(byArray2) < 0) {
                    return null;
                }
                short s2 = (short)p.a(byArray2[0]);
                int n6 = p.a(byArray2[1], byArray2[2], byArray2[3], byArray2[4]);
                if (this.a(byArray2 = new byte[n6]) < 0) {
                    return null;
                }
                dyArray[n5] = new dy();
                dyArray[n5].a = s2;
                dyArray[n5].b = byArray2;
                ++n5;
            }
            dz2.c = dyArray;
        }
        int n7 = n4 + n3;
        if (this.b != null) {
            this.b.a(n7);
        }
        return dz2;
    }

    private int a(byte[] byArray) {
        int n2 = 0;
        while (n2 < byArray.length) {
            int n3 = this.a.read(byArray, n2, byArray.length - n2);
            if (n3 < 0) {
                return -1;
            }
            n2 += n3;
        }
        return n2;
    }

    public final void b() {
        try {
            this.a.close();
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    public final void a(dw dw2) {
        this.b = dw2;
    }
}

