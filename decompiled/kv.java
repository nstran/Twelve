/*
 * Decompiled with CFR 0.152.
 */
import java.io.ByteArrayOutputStream;

public final class kv {
    private ByteArrayOutputStream a = new ByteArrayOutputStream();

    public final void a(short s2, byte[] byArray) {
        if (byArray == null) {
            return;
        }
        this.a.write((byte)s2);
        kv kv2 = this;
        try {
            byte[] byArray2 = p.a(byArray.length);
            kv2.a.write(byArray2, 0, byArray2.length);
            kv2.a.write(byArray, 0, byArray.length);
            return;
        }
        catch (Throwable throwable) {
            Throwable throwable2 = throwable;
            throwable.printStackTrace();
            return;
        }
    }

    public final void a(short s2, String object) {
        if (object == null) {
            return;
        }
        this.a.write((byte)s2);
        Object object2 = this;
        try {
            byte[] byArray = ((String)object).getBytes("UTF-8");
            if (byArray == null || byArray.length == 0) {
                byArray = ((String)object).getBytes();
            }
            byte[] byArray2 = p.a(byArray.length);
            ((kv)object2).a.write(byArray2, 0, byArray2.length);
            ((kv)object2).a.write(byArray, 0, byArray.length);
            return;
        }
        catch (Throwable throwable) {
            byte[] byArray = ((String)object).getBytes();
            object = p.a(byArray.length);
            try {
                ((kv)object2).a.write((byte[])object, 0, ((Object)object).length);
                ((kv)object2).a.write(byArray, 0, byArray.length);
                return;
            }
            catch (Throwable throwable2) {
                object2 = throwable2;
                throwable2.printStackTrace();
                return;
            }
        }
    }

    public final void a(short s2, byte by2) {
        this.a(s2, new byte[]{by2});
    }

    public final void a(short s2, int n2) {
        this.a(s2, new byte[]{(byte)n2});
    }

    public final void b(short s2, int n2) {
        this.a(s2, p.a(n2));
    }

    public final void a(short s2, long l2) {
        this.a(s2, p.a(l2));
    }

    public final byte[] a() {
        return this.a.toByteArray();
    }
}

