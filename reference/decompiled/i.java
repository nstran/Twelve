/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.io.Connector
 *  javax.microedition.io.SocketConnection
 */
import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;

public class i {
    private a d;
    public int a;
    public int b;
    public int c;

    public i() {
    }

    public static SocketConnection a(String string, int n2) {
        int n3 = 5;
        String string2 = string;
        string2 = (SocketConnection)Connector.open((String)("socket://" + string2 + ":" + n2));
        string2.setSocketOption((byte)1, 5);
        return string2;
    }

    public i(String string, int n2, int n3, int n4) {
        this.d = new a();
        this.b = -1;
        this.c = 0;
        this.a = n4;
        this.a(string, n2, n3);
    }

    public void a(String string, int n2, int n3) {
        this.d.a(new dm(string, n2, n3));
    }

    public int a() {
        int n2 = 0;
        int n3 = 0;
        while (n3 < this.d.d()) {
            n2 += this.a(n3);
            ++n3;
        }
        return n2 -= 4;
    }

    public int a(int n2) {
        return this.b(n2).c() + 8;
    }

    public dm b(int n2) {
        return (dm)this.d.b(n2);
    }

    public lq c(int n2) {
        return this.b(n2).a();
    }

    public lq b() {
        return this.b(this.c).a();
    }

    public int c() {
        return this.d.d();
    }
}

