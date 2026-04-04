/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.TextField
 */
import javax.microedition.lcdui.TextField;

public final class es
extends TextField
implements eq {
    public es(String string, String string2, int n2, int n3) {
        super(string, string2, 20, n3);
    }

    public final String a() {
        String string = this.getString();
        if (string != null && string.length() >= 0) {
            if ((string = string.trim()).indexOf(" ") > 0) {
                string = "\"" + string + "\"";
            }
            return string;
        }
        return null;
    }
}

