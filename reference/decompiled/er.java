/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.ChoiceGroup
 *  javax.microedition.lcdui.Image
 */
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Image;

public final class er
extends ChoiceGroup
implements eq {
    private String[] a;
    private int b;

    public er(String string, int n2, String[] stringArray, Image[] imageArray, String[] stringArray2) {
        super(string, n2, stringArray, null);
        this.b = n2;
        this.a = stringArray2;
    }

    public final String a() {
        String string = "";
        Object object = this;
        if (((er)object).b == 2) {
            object = new boolean[this.size()];
            this.getSelectedFlags((boolean[])object);
            try {
                int n2 = 0;
                while (n2 < ((Object)object).length) {
                    if (object[n2] != false) {
                        string = string.length() > 0 ? String.valueOf(string) + " " + this.a[n2] : this.a[n2];
                    }
                    ++n2;
                }
            }
            catch (Throwable throwable) {}
        } else {
            try {
                string = this.a[this.getSelectedIndex()];
            }
            catch (Throwable throwable) {}
        }
        if (string != null && string.length() >= 0) {
            if ((string = string.trim()).indexOf(" ") > 0) {
                string = "\"" + string + "\"";
            }
            return string;
        }
        return null;
    }
}

