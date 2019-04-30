package com.yst.im.imsdk.utils;


import static com.yst.im.imsdk.MessageConstant.CHAR_0X3;
import static com.yst.im.imsdk.MessageConstant.CHAR_0XF;
import static com.yst.im.imsdk.MessageConstant.CHAR_CAPITAL_A;
import static com.yst.im.imsdk.MessageConstant.CHAR_CAPITAL_Z;
import static com.yst.im.imsdk.MessageConstant.CHAR_LOWERCASE_A;
import static com.yst.im.imsdk.MessageConstant.CHAR_LOWERCASE_Z;
import static com.yst.im.imsdk.MessageConstant.CHAR_NUMBER_0;
import static com.yst.im.imsdk.MessageConstant.CHAR_NUMBER_9;
import static com.yst.im.imsdk.MessageConstant.NUM_0;
import static com.yst.im.imsdk.MessageConstant.NUM_25;
import static com.yst.im.imsdk.MessageConstant.NUM_26;
import static com.yst.im.imsdk.MessageConstant.NUM_51;
import static com.yst.im.imsdk.MessageConstant.NUM_52;
import static com.yst.im.imsdk.MessageConstant.NUM_61;

/**
 * Base64 工具类
 *
 * @author Lierpeng
 * @version 1.0.0
 * @date 2018/4/11.
 */
public class Base64Utils {
    private static final int BASE_LENGTH = 128;
    private static final int LOOK_UP_LENGTH = 64;
    private static final int TWENTY_FOUR_BIT_GROUP = 24;
    private static final int EIGHT_BIT = 8;
    private static final int SIX_TEEN_BIT = 16;
    private static final int FOUR_BYTE = 4;
    private static final int SIGN = -128;
    private static char PAD = '=';
    private static byte[] base64Alphabet = new byte[BASE_LENGTH];
    private static char[] lookUpBase64Alphabet = new char[LOOK_UP_LENGTH];

    static {
        for (int i = 0; i < BASE_LENGTH; ++i) {
            base64Alphabet[i] = -1;
        }
        for (int i = CHAR_CAPITAL_Z; i >= CHAR_CAPITAL_A; i--) {
            base64Alphabet[i] = (byte) (i - CHAR_CAPITAL_A);
        }
        for (int i = CHAR_LOWERCASE_Z; i >= CHAR_LOWERCASE_A; i--) {
            base64Alphabet[i] = (byte) (i - CHAR_LOWERCASE_A + 26);
        }

        for (int i = CHAR_NUMBER_9; i >= CHAR_NUMBER_0; i--) {
            base64Alphabet[i] = (byte) (i - CHAR_NUMBER_0 + 52);
        }

        base64Alphabet['+'] = 62;
        base64Alphabet['/'] = 63;

        for (int i = NUM_0; i <= NUM_25; i++) {
            lookUpBase64Alphabet[i] = (char) ('A' + i);
        }

        for (int i = NUM_26, j = 0; i <= NUM_51; i++, j++) {
            lookUpBase64Alphabet[i] = (char) ('a' + j);
        }

        for (int i = NUM_52, j = 0; i <= NUM_61; i++, j++) {
            lookUpBase64Alphabet[i] = (char) (CHAR_NUMBER_0 + j);
        }
        lookUpBase64Alphabet[62] = (char) '+';
        lookUpBase64Alphabet[63] = (char) '/';

    }

    private static boolean isWhiteSpace(char whiteSpace) {
        return (whiteSpace == 0x20 || whiteSpace == 0xd || whiteSpace == 0xa || whiteSpace == 0x9);
    }

    private static boolean isPad(char whiteSpace) {
        return (whiteSpace == PAD);
    }

    private static boolean isData(char whiteSpace) {
        return (whiteSpace < BASE_LENGTH && base64Alphabet[whiteSpace] != -1);
    }

    /**
     * Hex工具类转byte成String
     *
     * @param binaryData 数组
     * @return string
     */
    public static String encode(byte[] binaryData) {

        if (binaryData == null) {
            return null;
        }

        int lengthDataBits = binaryData.length * EIGHT_BIT;
        if (lengthDataBits == 0) {
            return "";
        }

        int fewerThan24bits = lengthDataBits % TWENTY_FOUR_BIT_GROUP;
        int numberTriplets = lengthDataBits / TWENTY_FOUR_BIT_GROUP;
        int numberQuartet = fewerThan24bits != 0 ? numberTriplets + 1
                : numberTriplets;
        char[] encodedData = null;

        encodedData = new char[numberQuartet * 4];

        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

        int encodedIndex = 0;
        int dataIndex = 0;

        for (int i = 0; i < numberTriplets; i++) {
            b1 = binaryData[dataIndex++];
            b2 = binaryData[dataIndex++];
            b3 = binaryData[dataIndex++];

            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2)
                    : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4)
                    : (byte) ((b2) >> 4 ^ 0xf0);
            byte val3 = ((b3 & SIGN) == 0) ? (byte) (b3 >> 6)
                    : (byte) ((b3) >> 6 ^ 0xfc);

            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[(l << 2) | val3];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[b3 & 0x3f];
        }

        if (fewerThan24bits == EIGHT_BIT) {
            b1 = binaryData[dataIndex];
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2)
                    : (byte) ((b1) >> 2 ^ 0xc0);
            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex++] = PAD;
            encodedData[encodedIndex++] = PAD;
        } else if (fewerThan24bits == SIX_TEEN_BIT) {
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = (byte) (b2 & 0x0f);
            k = (byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? (byte) (b1 >> 2)
                    : (byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? (byte) (b2 >> 4)
                    : (byte) ((b2) >> 4 ^ 0xf0);

            encodedData[encodedIndex++] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[val2 | (k << 4)];
            encodedData[encodedIndex++] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex++] = PAD;
        }

        return new String(encodedData);
    }

    /**
     * Decodes Base64 data into whiteSpaces
     *
     * @param encoded string containing Base64 data
     * @return Array containind decoded data.
     */
    public static byte[] decode(String encoded) {

        if (encoded == null) {
            return null;
        }

        char[] base64Data = encoded.toCharArray();
        // remove white spaces
        int len = removeWhiteSpace(base64Data);

        if (len % FOUR_BYTE != 0) {
            return null;
        }

        int numberQuadruple = (len / FOUR_BYTE);

        if (numberQuadruple == 0) {
            return new byte[0];
        }

        byte[] decodedData = null;
        byte b1 = 0, b2 = 0, b3 = 0, b4 = 0;
        char d1 = 0, d2 = 0, d3 = 0, d4 = 0;

        int i = 0;
        int encodedIndex = 0;
        int dataIndex = 0;
        decodedData = new byte[(numberQuadruple) * 3];

        for (; i < numberQuadruple - 1; i++) {
            // 数据为空
            if (!isData((d1 = base64Data[dataIndex++]))
                    || !isData((d2 = base64Data[dataIndex++]))
                    || !isData((d3 = base64Data[dataIndex++]))
                    || !isData((d4 = base64Data[dataIndex++]))) {
                return null;
            }

            b1 = base64Alphabet[d1];
            b2 = base64Alphabet[d2];
            b3 = base64Alphabet[d3];
            b4 = base64Alphabet[d4];

            decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
            decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
        }

        if (!isData((d1 = base64Data[dataIndex++]))
                || !isData((d2 = base64Data[dataIndex++]))) {
            return null;
        }

        b1 = base64Alphabet[d1];
        b2 = base64Alphabet[d2];

        d3 = base64Data[dataIndex++];
        d4 = base64Data[dataIndex++];
        if (!isData((d3)) || !isData((d4))) {
            if (isPad(d3) && isPad(d4)) {
                if ((b2 & CHAR_0XF) != 0) {
                    return null;
                }
                byte[] tmp = new byte[i * 3 + 1];
                System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                tmp[encodedIndex] = (byte) (b1 << 2 | b2 >> 4);
                return tmp;
            } else if (!isPad(d3) && isPad(d4)) {
                b3 = base64Alphabet[d3];
                if ((b3 & CHAR_0X3) != 0) {
                    return null;
                }
                byte[] tmp = new byte[i * 3 + 2];
                System.arraycopy(decodedData, 0, tmp, 0, i * 3);
                tmp[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
                tmp[encodedIndex] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                return tmp;
            } else {
                return null;
            }
        } else {
            // No PAD e.g 3cQl
            b3 = base64Alphabet[d3];
            b4 = base64Alphabet[d4];
            decodedData[encodedIndex++] = (byte) (b1 << 2 | b2 >> 4);
            decodedData[encodedIndex++] = (byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            decodedData[encodedIndex++] = (byte) (b3 << 6 | b4);
        }
        return decodedData;
    }

    /**
     * remove WhiteSpace from MIME containing encoded Base64 data.
     *
     * @param data the byte array of base64 data (with WS)
     * @return the new length
     */
    private static int removeWhiteSpace(char[] data) {
        if (data == null) {
            return 0;
        }
        // count characters that's not whitespace
        int newSize = 0;
        int len = data.length;
        for (int i = 0; i < len; i++) {
            if (!isWhiteSpace(data[i])) {
                data[newSize++] = data[i];
            }
        }
        return newSize;
    }
}