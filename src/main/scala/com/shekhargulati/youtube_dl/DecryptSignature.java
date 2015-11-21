package com.shekhargulati.youtube_dl;

public class DecryptSignature {
    String sig;

    public DecryptSignature(String signature) {
        this.sig = signature;
    }

    String s(int b, int e) {
        return sig.substring(b, e);
    }

    String s(int b) {
        return sig.substring(b, b + 1);
    }

    String se(int b) {
        return s(b, sig.length());
    }

    String s(int b, int e, int step) {
        String str = "";

        while (b != e) {
            str += sig.charAt(b);
            b += step;
        }
        return str;
    }

    // https://github.com/rg3/youtube-dl/blob/master/youtube_dl/extractor/youtube.py
    String decrypt() {
        switch (sig.length()) {
            case 93:
                return s(86, 29, -1) + s(88) + s(28, 5, -1);
            case 92:
                return s(25) + s(3, 25) + s(0) + s(26, 42) + s(79) + s(43, 79) + s(91) + s(80, 83);
            case 91:
                return s(84, 27, -1) + s(86) + s(26, 5, -1);
            case 90:
                return s(25) + s(3, 25) + s(2) + s(26, 40) + s(77) + s(41, 77) + s(89) + s(78, 81);
            case 89:
                return s(84, 78, -1) + s(87) + s(77, 60, -1) + s(0) + s(59, 3, -1);
            case 88:
                return s(7, 28) + s(87) + s(29, 45) + s(55) + s(46, 55) + s(2) + s(56, 87) + s(28);
            case 87:
                return s(6, 27) + s(4) + s(28, 39) + s(27) + s(40, 59) + s(2) + se(60);
            case 86:
                return s(80, 72, -1) + s(16) + s(71, 39, -1) + s(72) + s(38, 16, -1) + s(82) + s(15, 0, -1);
            case 85:
                return s(3, 11) + s(0) + s(12, 55) + s(84) + s(56, 84);
            case 84:
                return s(78, 70, -1) + s(14) + s(69, 37, -1) + s(70) + s(36, 14, -1) + s(80) + s(0, 14, -1);
            case 83:
                return s(80, 63, -1) + s(0) + s(62, 0, -1) + s(63);
            case 82:
                return s(80, 37, -1) + s(7) + s(36, 7, -1) + s(0) + s(6, 0, -1) + s(37);
            case 81:
                return s(56) + s(79, 56, -1) + s(41) + s(55, 41, -1) + s(80) + s(40, 34, -1) + s(0) + s(33, 29, -1)
                        + s(34) + s(28, 9, -1) + s(29) + s(8, 0, -1) + s(9);
            case 80:
                return s(1, 19) + s(0) + s(20, 68) + s(19) + s(69, 80);
            case 79:
                return s(54) + s(77, 54, -1) + s(39) + s(53, 39, -1) + s(78) + s(38, 34, -1) + s(0) + s(33, 29, -1)
                        + s(34) + s(28, 9, -1) + s(29) + s(8, 0, -1) + s(9);
        }

        throw new RuntimeException("Unable to decrypt signature, key length " + sig.length()
                + " not supported; retrying might work");
    }
}