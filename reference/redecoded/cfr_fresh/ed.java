/*
 * Decompiled with CFR 0.152.
 */
import java.io.InputStream;

final class ed {
    private InputStream a;
    private dv b;

    public ed(InputStream inputStream) {
        this.a = inputStream;
    }

    public final dy a() {
        int n2;
        int n3;
        dy dy2 = new dy();
        byte[] byArray = new byte[7];
        int n4 = this.a(byArray);
        if (n4 <= 0) {
            return null;
        }
        int n5 = m.a(byArray[0], byArray[1]);
        dy2.a = n3 = m.a(byArray[2], byArray[3], byArray[4], byArray[5]);
        dy2.b = n2 = m.a(byArray[6]);
        if (n3 > 0) {
            dx[] dxArray = new dx[n5];
            n5 = 0;
            while (n5 < dxArray.length) {
                byte[] byArray2 = new byte[5];
                if (this.a(byArray2) < 0) {
                    return null;
                }
                short s2 = (short)m.a(byArray2[0]);
                int n6 = m.a(byArray2[1], byArray2[2], byArray2[3], byArray2[4]);
                if (this.a(byArray2 = new byte[n6]) < 0) {
                    return null;
                }
                dxArray[n5] = new dx();
                dxArray[n5].a = s2;
                dxArray[n5].b = byArray2;
                ++n5;
            }
            dy2.c = dxArray;
        }
        n4 += n3;
        if (this.b != null) {
            this.b.a(n4);
        }
        return dy2;
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

    public final void a(dv dv2) {
        this.b = dv2;
    }
}

