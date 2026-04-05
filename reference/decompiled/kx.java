/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.io.OutputStream;

final class kx
implements km {
    private DataOutputStream c;

    public kx(OutputStream outputStream) {
        this.c = new DataOutputStream(outputStream);
    }

    public final void a() {
        if (this.c != null) {
            this.c.close();
            this.c = null;
        }
    }

    final void a(kv object, short s2) {
        if (this.c == null) {
            return;
        }
        byte[] byArray = ((kv)object).a();
        object = byArray;
        if (byArray != null) {
            int n2 = 6 + ((Object)object).length;
            this.c.writeInt(n2);
            this.c.writeByte(1);
            this.c.write(km.b, 0, 4);
            this.c.writeByte(s2);
            this.c.write((byte[])object, 0, ((Object)object).length);
            kq.i += 6 + ((Object)object).length;
        } else {
            boolean bl2 = false;
            this.c.writeInt(10);
            this.c.writeByte(1);
            this.c.write(km.b, 0, 4);
            this.c.writeByte(s2);
            kq.i += 10;
        }
        this.c.flush();
    }
}

