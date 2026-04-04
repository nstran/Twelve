/*
 * Decompiled with CFR 0.152.
 */
import java.util.Random;

public final class cy {
    private static Random a = new Random(System.currentTimeMillis());

    public static final int a(int n2) {
        return a.nextInt(n2);
    }

    public static final int a() {
        return a.nextInt();
    }

    public static final int a(int n2, int n3) {
        return a.nextInt(n3 - n2 + 1) + n2;
    }
}

