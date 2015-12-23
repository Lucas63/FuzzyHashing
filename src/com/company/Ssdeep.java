package com.company;

/**
 * Created by lucas63 on 15.12.15.
 */
public class Ssdeep {

    protected long[] rollingWindow;
    protected long rollingH1;
    protected long rollingH2;
    protected long rollingH3;
    protected long rollingN;
    protected int blocksize;
    protected char[] left;
    protected char[] right;

    /**
     * Computes and returns the spamsum signature of this string.
     * The block size is automatically computed
     *
     * @param string
     * @return spamsum signature
     */
    public String HashString(String string) {
        return HashString(string, 0);
    }

    /**
     * Computes and returns the spamsum signature of this string.
     *
     * @param string
     * @param bsize block size; if 0, the block size is automatically computed
     * @return spamsum signature
     */
    public String HashString(String string, int bsize) {
        byte[] in = string.getBytes(); // = StandardCharsets.UTF_8
        int length = in.length;


        if (bsize == 0) {
            /* guess a reasonable block size */
            blocksize = Parameters.getMinBlocksize();
            while (blocksize * Parameters.getSpamsumLength() < length) {
                blocksize = blocksize * 2;
            }
        } else {
            blocksize = bsize;
        }

        while (true) {
            left = new char[Parameters.getSpamsumLength()];
            right = new char[Parameters.getSpamsumLength()];
            int k = 0;
            int j = 0;
            long h3 = Parameters.getHashInit();
            long h2 = Parameters.getHashInit();
            long h = rollingHashReset();


            for (int i = 0; i < length; i++) {
                int character = (in[i] + 256) % 256;
                h = rollingHash(character);
                h2 = sumHash(character, h2);
                h3 = sumHash(character, h3);

                if (h % blocksize == (blocksize - 1)) {
                    left[j] = Parameters.getB64()[(int) (h2 % Parameters.getCHARACTERS())];

                    if (j < Parameters.getSpamsumLength() - 1) {
                        h2 = Parameters.getHashInit();
                        j++;
                    }
                }

                if (h % (blocksize * 2) == ((blocksize * 2) - 1)) {
                    right[k] = Parameters.getB64()[(int) (h3 % Parameters.getCHARACTERS())];
                    if (k < Parameters.getSpamsumLength() / 2 - 1) {
                        h3 = Parameters.getHashInit();
                        k++;
                    }
                }
            }

            if (h != 0) {
                left[j] = Parameters.getB64()[(int) (h2 % Parameters.getCHARACTERS())];
                right[k] = Parameters.getB64()[(int) (h3 % Parameters.getCHARACTERS())];
            }

            if (
                    (bsize != 0) ||                 // blocksize was manually specified
                    (blocksize <= Parameters.getMinBlocksize()) || // current blocksize is already too small
                    (j >= Parameters.getSpamsumLength() / 2)       // dividing by 2 would produce a hash too small...
                    )
            {
                break;
            }
            else
            {
                blocksize = blocksize / 2;
            }
        }


        return toString();
    }


    @Override
    public String toString() {
        return "" + blocksize + ":"
                + Left() + ":"+ Right();
    }


    /**
     *
     * @return block size
     */
    public long BlockSize() {
        return blocksize;
    }


    /**
     *
     * @return left part of the signature
     */
    public String Left() {
        return String.valueOf(left).trim();
    }


    /**
     *
     * @return right part of the signature
     */
    public String Right() {
        return String.valueOf(right).trim();
    }

    protected static long sumHash(long c, long h) {
        h = (h * Parameters.getHashPrime()) % Parameters.getUINT32();
        h = (h ^ c) % Parameters.getUINT32();
        return h;
    }


    protected long rollingHash(long c) {
        this.rollingH2 -= this.rollingH1;
        this.rollingH2 = (this.rollingH2 + Parameters.getRollingWindow() * c) % Parameters.getUINT32();

        rollingH1 = (rollingH1 + c) % Parameters.getUINT32();
        rollingH1 -= this.rollingWindow[(int) this.rollingN % Parameters.getRollingWindow()];

        this.rollingWindow[(int) (this.rollingN % Parameters.getRollingWindow())] = c;
        this.rollingN++;


        rollingH3 = ((rollingH3 << 5) & 0xFFFFFFFF) % Parameters.getUINT32();
        rollingH3 = (rollingH3 ^ c) % Parameters.getUINT32(); // Bitwize XOR

        return (rollingH1 + rollingH2 + rollingH3) % Parameters.getUINT32();
    }

    /**
     * Reset rolling hash value
     * @return 0
     */
    protected long rollingHashReset() {
        this.rollingWindow = new long[Parameters.getRollingWindow()];

        this.rollingH1 = 0;
        this.rollingH2 = 0;
        this.rollingH3 = 0;
        this.rollingN = 0;

        return 0;
    }



}
