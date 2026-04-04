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
extends au {
    private a i;
    private int j = 0;
    private String k;
    private int l;
    private n m;

    public fe(String string) {
        this.k = string;
        this.d = new n(0, 0, z.r - 20, z.s);
        this.m = new n(0, 0, this.d.c, 9 + ca.d.a());
        this.i = new a();
        this.l = this.m.d + 5;
    }

    public final void d(boolean bl2) {
        super.d(bl2);
        this.c = true;
    }

    public final void a(fk fk2) {
        fk2.a(this);
        fk2.a(0, this.l, this.d.c, ca.d.a());
        this.l += fk2.f() + 5;
        this.d.d = this.l + 5;
        this.e(this.d.d);
        this.i.a(fk2);
        if (this.i.d() == 1) {
            this.j = 0;
            fk2.d(true);
        }
        if (fk2.m()) {
            this.j = this.i.d() - 1;
        }
    }

    public final void b(int n2, int n3) {
        super.b(n2, n3);
        this.m = new n(0, 0, this.d.c, 9 + ca.d.a());
    }

    public final void a(int n2, int n3, int n4, int n5) {
        super.a(n2, n3, n4, n5);
        this.m = new n(this.d.a, this.d.b, this.d.c, 9 + ca.d.a());
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
                ((fk)this.i.b(this.j)).f(n2);
            }
        }
        if (n3 != this.j) {
            ((fk)this.i.b(n3)).d(false);
            ((fk)this.i.b(this.j)).d(true);
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
            fk fk2 = (fk)this.i.b(n4);
            if (fk2.c(n2, n3)) {
                this.j = n4;
            } else {
                fk2.d(false);
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
            oz.a(graphics, this.c() + n2, this.d() + n3, this.e(), this.f(), 7070703, -1);
            graphics.setColor(7267055);
            graphics.fillRect(this.c() + n2, this.d() + n3, this.m.c, this.m.d);
        }
        int n4 = n3 + this.d() + 4;
        int n5 = this.c() + n2 + 4;
        ca.d.c(true);
        ca.d.a(graphics, this.k, n5, n4, 0);
        ca.d.c(false);
        n4 = n3 + this.d.b;
        n5 += 5;
        int n6 = 0;
        int n7 = this.i.d();
        while (n6 < n7) {
            fk fk2 = (fk)this.i.b(n6);
            if (n6 == this.j && this.m()) {
                int n8 = n4;
                int n9 = this.c() + n2;
                Graphics graphics2 = graphics;
                fk fk3 = fk2;
                if (fk3.m()) {
                    graphics2.setColor(7267055);
                    graphics2.fillRect(fk3.c() + n9, fk3.d() + n8 - 1, fk3.e(), ca.d.a() + 2);
                }
            }
            fk2.a(graphics, n5, n4);
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
                fk fk2 = (fk)this.i.b(n2);
                dataOutputStream.writeUTF(fk2.a());
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
            fk fk2 = (fk)this.i.b(n2);
            fk2.a(dataInputStream.readUTF());
            ++n2;
        }
        dataInputStream.close();
        ((ByteArrayInputStream)object).close();
    }
}

