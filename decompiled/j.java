/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.microedition.rms.RecordStore
 */
import java.io.InputStream;
import javax.microedition.rms.RecordStore;

public class j {
    private String a;
    private short b;
    private du[] c;

    public j() {
    }

    public static int a(InputStream inputStream, byte[] byArray, int n2) {
        while (n2 < byArray.length) {
            int n3 = inputStream.read(byArray, n2, byArray.length - n2);
            if (n3 < 0) {
                return -1;
            }
            n2 += n3;
        }
        return n2;
    }

    public static boolean a(String string) {
        if (l.a(string)) {
            return false;
        }
        String[] stringArray = RecordStore.listRecordStores();
        if (stringArray == null || string.length() <= 0) {
            return false;
        }
        string = string.toLowerCase();
        int n2 = 0;
        while (n2 < stringArray.length) {
            if (stringArray[n2].toLowerCase().equals(string)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    public static RecordStore a(String object, boolean bl2) {
        try {
            return RecordStore.openRecordStore((String)object, (boolean)bl2);
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
            return null;
        }
    }

    public static byte[] a(RecordStore object, int n2) {
        try {
            object = object.getRecord(n2);
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
            object = null;
        }
        return object;
    }

    public static void a(RecordStore recordStore, int n2, byte[] byArray) {
        int n3 = byArray.length;
        boolean bl2 = false;
        RecordStore recordStore2 = recordStore;
        recordStore2.setRecord(n2, byArray, 0, n3);
    }

    public static int a(RecordStore recordStore, byte[] byArray) {
        int n2 = byArray.length;
        boolean bl2 = false;
        RecordStore recordStore2 = recordStore;
        return recordStore2.addRecord(byArray, 0, n2);
    }

    public static int a(RecordStore object) {
        try {
            return object.getNumRecords();
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
            return 0;
        }
    }

    public static void b(String object) {
        try {
            RecordStore.deleteRecordStore((String)object);
            return;
        }
        catch (Throwable throwable) {
            object = throwable;
            throwable.printStackTrace();
            return;
        }
    }

    public j(String string) {
        this.a = string;
    }

    public short a() {
        return this.b;
    }

    public void a(short s2) {
        this.b = s2;
    }

    public String b() {
        return this.a;
    }

    public du[] c() {
        return this.c;
    }

    public void a(du[] duArray) {
        this.c = duArray;
        this.d();
    }

    public void d() {
        if (this.c == null) {
            return;
        }
        int n2 = 0;
        while (n2 < this.c.length) {
            if (this.c[n2].e() != 2) {
                boolean bl2 = false;
                int n3 = this.c.length - 1;
                while (n3 > n2) {
                    if (this.c[n3].e() == 2) {
                        bl2 = true;
                        du du2 = this.c[n2];
                        this.c[n2] = this.c[n3];
                        this.c[n3] = du2;
                    }
                    --n3;
                }
                if (!bl2) {
                    return;
                }
            }
            ++n2;
        }
    }
}

