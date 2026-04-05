/*
 * Decompiled with CFR 0.152.
 */
public final class ef {
    public String a;
    public String b;
    private String g;
    private static String h;
    public long c;
    public short d;
    public char e = (char)117;
    public byte[] f;

    static {
        char[] cArray = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        char[] cArray2 = new char[]{'a', 'b', 'c', 'd', 'L', 'M', 'N', 'e', 'G', 'H', 's', 'y', 'z', 'A', 'n', 'o', 'J', 'K', 'S', 'T', '2', 'f', 'P', '4', '5', 'p', 'q', 'r', '7', '6', 'v', 'w', '0', '8', 'B', 'C', 'g', 'h', 'i', 'Q', 'R', 't', 'u', 'D', 'U', 'V', 'X', 'm', '1', 'E', 'F', 'x', 'Y', 'Z', '3', 'O', 'W', 'I', 'j', 'k', 'l', '9'};
    }

    public ef() {
        h = "http://m.media.kaspee.com/v=5.1.2/d=true/c=";
    }

    public final void a(byte[] byArray) {
        this.f = byArray;
    }

    public final void a(String string) {
        try {
            if (string == null) {
                this.d = 0;
                this.g = null;
                return;
            }
            this.g = string;
            char c2 = this.g.charAt(1);
            switch (c2) {
                case 'P': {
                    this.d = 1;
                    this.b = String.valueOf(h) + this.g + "/w=" + dv.a().m.e + "/h=" + dv.a().m.f + "/q=" + String.valueOf(10) + "/photo" + string + ".jpg/";
                    break;
                }
                case 'S': {
                    this.d = (short)3;
                    this.b = String.valueOf(h) + this.g + "/q=" + String.valueOf(10) + "/sound" + string + ".mp3/";
                    break;
                }
                case 'V': {
                    this.d = (short)2;
                    this.b = String.valueOf(h) + this.g + "/q=" + String.valueOf(10) + "/video" + string + ".3gp/";
                    break;
                }
                default: {
                    this.d = 0;
                    this.b = String.valueOf(h) + this.g + "/" + "unknown" + string + ".txt/";
                }
            }
            if (this.g.length() > 6) {
                this.e = this.g.charAt(6);
            }
            this.g.charAt(2);
            switch (0) {
                case 49: 
                case 50: 
                case 51: 
                case 52: 
                case 53: 
                case 54: {
                    return;
                }
                case 97: {
                    return;
                }
                case 98: {
                    return;
                }
                case 99: {
                    return;
                }
                case 100: {
                    return;
                }
                case 109: {
                    return;
                }
                case 110: {
                    return;
                }
                case 112: {
                    return;
                }
            }
            return;
        }
        catch (Throwable throwable) {
            return;
        }
    }

    public final String a() {
        return this.g;
    }
}

