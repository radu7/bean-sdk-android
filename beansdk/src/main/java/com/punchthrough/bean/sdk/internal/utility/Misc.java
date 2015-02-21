package com.punchthrough.bean.sdk.internal.utility;

import com.punchthrough.bean.sdk.internal.exception.NoEnumFoundException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.EnumSet;

public class Misc {

    public static int clamp(int n, int min, int max) {
        if (n < min) return min;
        if (n > max) return max;
        return n;
    }

    public static int clampToUInt8(int n) {
        return clamp(n, 0, 255);
    }

    public static byte[] asciiHexToBytes(String hex) throws DecoderException {
        return Hex.decodeHex(hex.toCharArray());
    }

    // From http://stackoverflow.com/a/4768950/254187
    public static int bytesToInt(byte high, byte low) {
        return ( (high & 0xFF) << 8 ) | ( low & 0xFF );
    }

    public static byte intToByte(int i) {
        return (byte) (i & 0xFF);
    }

    public static byte[] intArrayToByteArray(int[] intArray) {

        byte[] byteArray = new byte[intArray.length];

        for (int i = 0; i < intArray.length; i++) {
            byteArray[i] = intToByte(intArray[i]);
        }

        return byteArray;

    }

    public static byte[] intToUInt32(int i, ByteOrder endian) {
        int truncated = (int) ( (long) i );
        return ByteBuffer.allocate(4).order(endian).putInt(truncated).array();
    }

    // Based on http://stackoverflow.com/a/16406386/254187
    public static <T extends Enum & RawValuable> T enumWithRawValue(Class<T> enumClass, int value)
            throws NoEnumFoundException {

        for (Object oneEnumRaw : EnumSet.allOf(enumClass)) {
            T oneEnum = (T) oneEnumRaw;
            if (value == oneEnum.getRawValue()) {
                return oneEnum;
            }
        }
        throw new NoEnumFoundException(String.format(
                "No enum found for class %s with raw value %d", enumClass.getName(), value));

    }

    public static <T extends Enum & RawValuable> T enumWithRawValue(Class<T> enumClass, byte value)
            throws NoEnumFoundException {

        return enumWithRawValue(enumClass, (int) value);

    }

}
