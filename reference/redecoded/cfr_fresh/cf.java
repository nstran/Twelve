/*
 * Decompiled with CFR 0.152.
 */
import com.mg.smsgame.MGMIDlet;
import java.io.UnsupportedEncodingException;

final class cf {
    String a;
    String b;
    be c;

    cf(String object, String object2, be be2) {
        this.b = object;
        if (object != null) {
            this.b = ((String)object).replace('_', '-');
        }
        this.c = be2;
        object = this;
        if ((object2 = object2.trim()).length() == 1) {
            int n2 = Integer.parseInt(object2.trim());
            try {
                object2 = "8" + object2 + 31;
                byte[] byArray = cx.a(object2);
                byte[] byArray2 = new byte[byArray.length];
                System.arraycopy(v.h[n2], 0, byArray2, 0, 4);
                System.arraycopy(v.i[n2], 0, byArray2, 8, 4);
                System.arraycopy(v.j[n2], 0, byArray2, 4, 4);
                System.arraycopy(v.k[n2], 0, byArray2, 12, 4);
                if (!cx.a(byArray, byArray2)) {
                    cr.e();
                    object2 = MGMIDlet.d();
                    object2.notifyDestroyed();
                    return;
                }
                ((cf)object).a = object2;
                return;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                ((cf)object).a = "8731";
                return;
            }
        }
        ((cf)object).a = object2;
    }
}

