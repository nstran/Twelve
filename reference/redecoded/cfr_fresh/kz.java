/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.io.OutputStream;

final class kz
implements kn {
    private DataOutputStream b;

    public kz(OutputStream outputStream) {
        this.b = new DataOutputStream(outputStream);
    }

    public final void a() {
        if (this.b != null) {
            this.b.close();
            this.b = null;
        }
    }

    final void a(kx object, short s2) {
        if (this.b == null) {
            return;
        }
        byte[] byArray = ((kx)object).a();
        object = byArray;
        if (byArray != null) {
            int n2 = 6 + ((Object)object).length;
            this.b.writeInt(n2);
            this.b.writeByte(1);
            this.b.write(kn.a, 0, 4);
            this.b.writeByte(s2);
            this.b.write((byte[])object, 0, ((Object)object).length);
            ks.h += 6 + ((Object)object).length;
        } else {
            this.b.writeInt(10);
            this.b.writeByte(1);
            this.b.write(kn.a, 0, 4);
            this.b.writeByte(s2);
            ks.h += 10;
        }
        this.b.flush();
    }
}

