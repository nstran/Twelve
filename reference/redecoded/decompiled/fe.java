/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.lcdui.Graphics
 */
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import javax.microedition.lcdui.Graphics;

public final class fe
extends aq {
    private a i;
    private int j = 0;
    private String k;
    private int l;
    private k m;

    public fe(String string) {
        this.k = string;
        this.d = new k(0, 0, v.t - 20, v.u);
        this.m = new k(0, 0, this.d.c, 9 + bx.d.a());
        this.i = new a();
        this.l = this.m.d + 5;
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        this.c = true;
    }

    public final void a(fl fl2) {
        fl2.a(this);
        fl2.a(0, this.l, this.d.c, bx.d.a());
        this.l += fl2.f() + 5;
        this.d.d = this.l + 5;
        this.e(this.d.d);
        this.i.a(fl2);
        if (this.i.d() == 1) {
            this.j = 0;
            fl2.d(true);
        }
        if (fl2.m()) {
            this.j = this.i.d() - 1;
        }
    }

    public final void b(int n2, int n3) {
        super.b(n2, n3);
        this.m = new k(0, 0, this.d.c, 9 + bx.d.a());
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.m = new k(this.d.a, this.d.b, this.d.c, 9 + bx.d.a());
        this.k = com.mg.sq.a.a(this.k, this.d.c - 4);
    }

    public final boolean f(int n2) {
        if (this.i.d() <= 0) {
            return false;
        }
        int n3 = this.j;
        boolean bl2 = false;
        switch (n2) {
            case 98: {
                if (this.j >= this.i.d() - 1) break;
                ++this.j;
                bl2 = true;
                break;
            }
            case 99: {
                if (this.j <= 0) break;
                --this.j;
                bl2 = true;
                break;
            }
            case 95: {
                ((fl)this.i.b(this.j)).f(n2);
            }
        }
        if (n3 != this.j) {
            ((fl)this.i.b(n3)).d(false);
            ((fl)this.i.b(this.j)).d(true);
            this.c = true;
            this.b.c(true);
        }
        return bl2;
    }

    public final boolean c(int n2, int n3) {
        n2 -= this.c() + 5 + 4;
        n3 -= this.d();
        int n4 = 0;
        int n5 = this.i.d();
        while (n4 < n5) {
            fl fl2 = (fl)this.i.b(n4);
            if (fl2.c(n2, n3)) {
                this.j = n4;
            } else {
                fl2.d(false);
            }
            ++n4;
        }
        return true;
    }

    public final void a(Graphics graphics, int n2, int n3) {
        if (!this.c) {
            return;
        }
        if (this.m()) {
            pc.a(graphics, this.c() + n2, this.d() + n3, this.e(), this.f(), 7070703, -1);
            graphics.setColor(7267055);
            graphics.fillRect(this.c() + n2, this.d() + n3, this.m.c, this.m.d);
        }
        int n4 = n3 + this.d() + 4;
        int n5 = this.c() + n2 + 4;
        bx.d.c(true);
        bx.d.a(graphics, this.k, n5, n4, 0);
        bx.d.c(false);
        n4 = n3 + this.d.b;
        n5 += 5;
        int n6 = 0;
        int n7 = this.i.d();
        while (n6 < n7) {
            fl fl2 = (fl)this.i.b(n6);
            if (n6 == this.j && this.m()) {
                int n8 = n4;
                int n9 = this.c() + n2;
                Graphics graphics2 = graphics;
                fl fl3 = fl2;
                if (fl3.m()) {
                    graphics2.setColor(7267055);
                    graphics2.fillRect(fl3.c() + n9, fl3.d() + n8 - 1, fl3.e(), bx.d.a() + 2);
                }
            }
            fl2.a(graphics, n5, n4);
            ++n6;
        }
        this.c = false;
    }

    public final byte[] a() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        byte[] byArray = null;
        try {
            int n2 = 0;
            while (n2 < this.i.d()) {
                fl fl2 = (fl)this.i.b(n2);
                dataOutputStream.writeUTF(fl2.a());
                ++n2;
            }
            dataOutputStream.flush();
            byArray = byteArrayOutputStream.toByteArray();
            dataOutputStream.close();
        }
        catch (Exception exception) {
            Exception exception2 = exception;
            exception.printStackTrace();
        }
        return byArray;
    }

    public final void a(byte[] object) {
        object = new ByteArrayInputStream((byte[])object);
        DataInputStream dataInputStream = new DataInputStream((InputStream)object);
        int n2 = 0;
        while (n2 < this.i.d()) {
            fl fl2 = (fl)this.i.b(n2);
            fl2.a(dataInputStream.readUTF());
            ++n2;
        }
        dataInputStream.close();
        ((ByteArrayInputStream)object).close();
    }
}

