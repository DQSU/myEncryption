package com.hitrontech.hitronencryption

import java.io.ByteArrayOutputStream
import java.io.FilterOutputStream
import java.io.IOException
import java.io.OutputStream

class Base64Encoder
/***
 * Constructs a new Base64 encoder that writes output to the given
 * OutputStream.
 *
 * @param out
 * the output stream
 */
private constructor(out: OutputStream) : FilterOutputStream(out) {

    private var charCount: Int = 0
    private var carryOver: Int = 0
    // 是否每76字节换行
    private var isWrapBreak = true

    /***
     * Constructs a new Base64 encoder that writes output to the given
     * OutputStream.
     *
     * @param out
     * the output stream
     */
    private constructor(out: OutputStream, isWrapBreak: Boolean) : this(out) {
        this.isWrapBreak = isWrapBreak
    }

    /***
     * Writes the given byte to the output stream in an encoded form.
     *
     * @exception IOException
     * if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun write(b: Int) {
        var b = b
        // Take 24-bits from three octets, translate into four encoded chars
        // Break lines at 76 chars
        // If necessary, pad with 0 bits on the right at the end
        // Use = signs as padding at the end to ensure encodedLength % 4 == 0

        // Remove the sign bit,
        // thanks to Christian Schweingruber <chrigu@lorraine.ch>
        if (b < 0) {
            b += 256
        }

        // First byte use first six bits, save last two bits
        when {
            charCount % 3 == 0 -> {
                val lookup = b shr 2
                carryOver = b and 3 // last two bits
                out.write(chars[lookup].toInt())
            }
            charCount % 3 == 1 -> {
                val lookup = (carryOver shl 4) + (b shr 4) and 63
                carryOver = b and 15 // last four bits
                out.write(chars[lookup].toInt())
            }
            charCount % 3 == 2 -> {
                var lookup = (carryOver shl 2) + (b shr 6) and 63
                out.write(chars[lookup].toInt())
                lookup = b and 63 // last six bits
                out.write(chars[lookup].toInt())
                carryOver = 0
            }// Third byte use previous four bits and first two new bits,
            // then use last six new bits
            // Second byte use previous two bits and first four new bits,
            // save last four bits

            // Add newline every 76 output chars (that's 57 input chars)
        }// Third byte use previous four bits and first two new bits,
        // then use last six new bits
        // Second byte use previous two bits and first four new bits,
        // save last four bits
        charCount++

        // Add newline every 76 output chars (that's 57 input chars)
        if (this.isWrapBreak && charCount % 57 == 0) {
            out.write('\n'.toInt())
        }
    }

    /***
     * Writes the given byte array to the output stream in an encoded form.
     *
     * @param buf
     * the data to be written
     * @param off
     * the start offset of the data
     * @param len
     * the length of the data
     * @exception IOException
     * if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun write(buf: ByteArray, off: Int, len: Int) {
        // This could of course be optimized
        for (i in 0 until len) {
            write(buf[off + i].toInt())
        }
    }

    /***
     * Closes the stream, this MUST be called to ensure proper padding is
     * written to the end of the output stream.
     *
     * @exception IOException
     * if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun close() {
        // Handle leftover bytes
        if (charCount % 3 == 1) { // one leftover
            val lookup = carryOver shl 4 and 63
            out.write(chars[lookup].toInt())
            out.write('='.toInt())
            out.write('='.toInt())
        } else if (charCount % 3 == 2) { // two leftovers
            val lookup = carryOver shl 2 and 63
            out.write(chars[lookup].toInt())
            out.write('='.toInt())
        }
        super.close()
    }

    companion object {

        private val chars = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/')

        /***
         * Returns the encoded form of the given unencoded string.
         *
         * @param bytes
         * the bytes to encode
         * @param isWrapBreak
         * 是否每76字节换行
         * @return the encoded form of the unencoded string
         * @throws IOException
         */
        @JvmOverloads
        fun encode(bytes: ByteArray, isWrapBreak: Boolean = true): String {
            val out = ByteArrayOutputStream((bytes.size * 1.4).toInt())
            val encodedOut = Base64Encoder(out, isWrapBreak)
            try {
                encodedOut.write(bytes)
            } catch (e: IOException) {
                throw RuntimeException(e)
            } finally {
                try {
                    encodedOut.close()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }

            }
            return out.toString()
        }
    }

}
/***
 * Returns the encoded form of the given unencoded string.<br></br>
 * 默认是否每76字节换行
 *
 * @param bytes
 * the bytes to encode
 * @return the encoded form of the unencoded string
 * @throws IOException
 */
