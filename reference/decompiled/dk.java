/*
 * Decompiled with CFR 0.152.
 */
public final class dk {
    public final int a;
    public final String b;
    public String c;
    public final String d;

    public dk(int n2, String string, String string2, String string3) {
        this.a = n2;
        this.b = string;
        this.c = string2;
        this.d = string3;
    }

    public final String toString() {
        String string = "RankInfo[";
        string = String.valueOf(string) + "idx=" + this.a;
        string = String.valueOf(string) + ",rank=" + this.b;
        string = String.valueOf(string) + ",name=" + this.c;
        string = String.valueOf(string) + ",detail=" + this.d;
        string = String.valueOf(string) + "]";
        return string;
    }
}

