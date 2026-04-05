/*
 * Decompiled with CFR 0.152.
 */
import com.mg.smsgame.MGMIDlet;
import java.io.UnsupportedEncodingException;

final class ci {
    String a;
    String b;
    bi c;

    ci(String object, String object2, bi bi2) {
        this.b = object;
        if (object != null) {
            this.b = ((String)object).replace('_', '-');
        }
        this.c = bi2;
        object = this;
        if (((String)(object2 = ((String)object2).trim())).length() == 1) {
            int n2 = Integer.parseInt(((String)object2).trim());
            String string = null;
            try {
                string = String.valueOf(8) + (String)object2 + 31;
                byte[] byArray = da.a(string);
                object2 = byArray;
                byte[] byArray2 = new byte[byArray.length];
                System.arraycopy(z.f[n2], 0, byArray2, 0, 4);
                System.arraycopy(z.g[n2], 0, byArray2, 8, 4);
                System.arraycopy(z.h[n2], 0, byArray2, 4, 4);
                System.arraycopy(z.i[n2], 0, byArray2, 12, 4);
                if (!da.a((byte[])object2, byArray2)) {
                    cu.e();
                    MGMIDlet.f().d();
                    return;
                }
                ((ci)object).a = string;
                return;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                ((ci)object).a = "8731";
                return;
            }
        }
        ((ci)object).a = object2;
    }
}

