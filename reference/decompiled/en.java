/*
 * Decompiled with CFR 0.152.
 */
import java.io.DataOutputStream;
import java.io.OutputStream;

final class en
implements eh {
    private DataOutputStream a;
    private dw b;

    public en(OutputStream outputStream) {
        this.a = new DataOutputStream(outputStream);
    }

    public final void a() {
        if (this.a != null) {
            try {
                this.a.close();
                this.a = null;
                return;
            }
            catch (Exception exception) {}
        }
    }

    final void a(el object, short s2) {
        if (this.a == null) {
            return;
        }
        object = ((el)object).a();
        int n2 = 0;
        if (object != null) {
            this.a.writeByte(4);
            this.a.write(eh.A, 0, 4);
            this.a.writeInt(((Object)object).length);
            this.a.writeByte(s2);
            this.a.write((byte[])object, 0, ((Object)object).length);
            n2 = 0 + (10 + ((Object)object).length);
        } else {
            this.a.writeByte(4);
            this.a.write(eh.A, 0, 4);
            this.a.writeInt(0);
            this.a.writeByte(s2);
            n2 += 10;
        }
        if (this.b != null) {
            this.b.b(n2);
        }
        this.a.flush();
    }

    public final void a(dw dw2) {
        this.b = dw2;
    }
}

