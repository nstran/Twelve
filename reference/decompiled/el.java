/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;

public final class el {
    private ByteArrayOutputStream a = new ByteArrayOutputStream();

    public final void a(short s2, byte[] byArray) {
        this.a.write((byte)s2);
        el el2 = this;
        try {
            byte[] byArray2 = p.a(byArray.length);
            el2.a.write(byArray2, 0, byArray2.length);
            el2.a.write(byArray, 0, byArray.length);
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    public final void a(short s2, String object) {
        this.a.write((byte)s2);
        el el2 = this;
        try {
            byte[] byArray = ((String)object).getBytes("UTF-8");
            if (byArray == null || byArray.length == 0) {
                byArray = ((String)object).getBytes();
            }
            byte[] byArray2 = p.a(byArray.length);
            el2.a.write(byArray2, 0, byArray2.length);
            el2.a.write(byArray, 0, byArray.length);
            return;
        }
        catch (Throwable throwable) {
            byte[] byArray = ((String)object).getBytes();
            object = p.a(byArray.length);
            try {
                el2.a.write((byte[])object, 0, ((Object)object).length);
                el2.a.write(byArray, 0, byArray.length);
                return;
            }
            catch (Throwable throwable2) {
                return;
            }
        }
    }

    public final void a(short s2, long l2) {
        this.a(s2, p.a(l2));
    }

    public final void a(short s2, int n2) {
        this.a(s2, p.a(n2));
    }

    public final void a(short s2, short s3) {
        this.a(s2, p.a(s3));
    }

    public final void a(short s2, byte by2) {
        this.a(s2, new byte[]{by2});
    }

    public final byte[] a() {
        return this.a.toByteArray();
    }
}

