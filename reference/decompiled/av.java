/*
 * Decompiled with CFR 0.152.
 */
public class av {
    private int a;
    private int[] b;
    private bj c;
    private int d;

    public av(int n2, int[] nArray, bj bj2) {
        this.a = n2;
        this.b = nArray;
        this.c = bj2;
    }

    public final void a(int n2) {
        int n3 = this.b[this.d];
        if (n3 == n2) {
            ++this.d;
            if (this.d >= this.b.length) {
                this.d = 0;
                if (this.c != null) {
                    this.c.d(-1987, this.a);
                    return;
                }
            }
        } else {
            this.d = 0;
        }
    }

    public av() {
    }

    public static String a() {
        String string = null;
        try {
            string = System.getProperty("Cell-ID");
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("CellID");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.nokia.mid.cellid");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.sonyericsson.net.cellid");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("phone.cid");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.samsung.cellid");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.siemens.cellid");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("cid");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                return null;
            }
        }
        catch (Exception exception) {
            return null;
        }
        try {
            int n2 = Integer.parseInt(string);
            string = Integer.toHexString(n2);
        }
        catch (Exception exception) {}
        return string;
    }

    public static String b() {
        String string = null;
        try {
            string = System.getProperty("phone.lac");
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.nokia.mid.lac");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.sonyericsson.net.lac");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("LocAreaCode");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                return null;
            }
        }
        catch (Exception exception) {
            return null;
        }
        try {
            int n2 = Integer.parseInt(string);
            string = Integer.toHexString(n2);
        }
        catch (Exception exception) {}
        return string;
    }

    public static String c() {
        String string = null;
        try {
            string = System.getProperty("phone.mcc");
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.nokia.mid.mcc");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.sonyericsson.net.mcc");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("mcc");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.nokia.mid.countrycode");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                return null;
            }
        }
        catch (Exception exception) {
            return null;
        }
        return string;
    }

    public static String d() {
        String string = null;
        try {
            string = System.getProperty("phone.mnc");
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.nokia.mid.mnc");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.sonyericsson.net.mnc");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("mnc");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.nokia.mid.networkID");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                string = System.getProperty("com.nokia.mid.networkid");
            }
            if (string == null || string.equals("null") || string.equals("")) {
                return null;
            }
        }
        catch (Exception exception) {
            return null;
        }
        String string2 = av.c();
        if (string2 != null) {
            if (!string.equals(string2) && string.startsWith(string2)) {
                string = string.substring(string2.length());
            }
            if (string != null && string.length() > 0) {
                int n2 = 0;
                int n3 = string.length();
                while (n2 < n3) {
                    if (string.charAt(n2) < '0' || string.charAt(n2) > '9') {
                        string = string.substring(0, n2);
                        break;
                    }
                    ++n2;
                }
            }
        }
        return string;
    }
}

