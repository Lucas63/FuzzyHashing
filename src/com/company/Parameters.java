package com.company;

/**
 * Created by lucas63 on 15.12.15.
 */
public class Parameters {
    // The original algorithm works using UINT32 => 2 ^ 32
    private static final long UINT32 = (long)2 << 31;
    private static int MIN_BLOCKSIZE = 3;
    private static int SPAMSUM_LENGTH = 64;
    private static int CHARACTERS = 64;
    private static final int ROLLING_WINDOW = 7;
    private static final long HASH_PRIME = 0x01000193;
    private static final long HASH_INIT = 0x28021967;
    private static final char[] B64 = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefgh" +
            "ijklmnopqrstuvwxyz0123456789+/").toCharArray();


    public static long getUINT32() {
        return UINT32;
    }

    public static int getMinBlocksize() {
        return MIN_BLOCKSIZE;
    }

    public static void setMinBlocksize(int minBlocksize) {
        MIN_BLOCKSIZE = minBlocksize;
    }

    public static int getSpamsumLength() {
        return SPAMSUM_LENGTH;
    }

    public static void setSpamsumLength(int spamsumLength) {
        SPAMSUM_LENGTH = spamsumLength;
    }

    public static int getCHARACTERS() {
        return CHARACTERS;
    }

    public static void setCHARACTERS(int CHARACTERS) {
        Parameters.CHARACTERS = CHARACTERS;
    }

    public static int getRollingWindow() {
        return ROLLING_WINDOW;
    }

    public static long getHashPrime() {
        return HASH_PRIME;
    }

    public static long getHashInit() {
        return HASH_INIT;
    }

    public static char[] getB64() {
        return B64;
    }
}
