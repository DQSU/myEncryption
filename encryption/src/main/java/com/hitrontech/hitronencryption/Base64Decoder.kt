package com.hitrontech.hitronencryption

import android.text.TextUtils
import java.io.*

class Base64Decoder
/***
 * Constructs a new Base64 decoder that reads input from the given
 * InputStream.
 *
 * @param in
 * the input stream
 */
private constructor(`in`: InputStream) : FilterInputStream(`in`) {

    private var charCount: Int = 0
    private var carryOver: Int = 0

    /***
     * Returns the next decoded character from the stream, or -1 if end of
     * stream was reached.
     *
     * @return the decoded character, or -1 if the end of the input stream is
     * reached
     * @exception IOException
     * if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun read(): Int {
        // Read the next non-whitespace character
        var x: Int
        do {
            x = `in`.read()
            if (x == -1) {
                return -1
            }
        } while (Character.isWhitespace(x.toChar()))
        charCount++

        // The '=' sign is just padding
        if (x == '='.toInt()) {
            return -1 // effective end of stream
        }

        // Convert from raw form to 6-bit form
        x = ints[x]

        // Calculate which character we're decoding now
        val mode = (charCount - 1) % 4

        // First char save all six bits, go for another
        if (mode == 0) {
            carryOver = x and 63
            return read()
        } else if (mode == 1) {
            val decoded = (carryOver shl 2) + (x shr 4) and 255
            carryOver = x and 15
            return decoded
        } else if (mode == 2) {
            val decoded = (carryOver shl 4) + (x shr 2) and 255
            carryOver = x and 3
            return decoded
        } else if (mode == 3) {
            return (carryOver shl 6) + x and 255
        }// Fourth char use previous two bits and all six new bits
        // Third char use previous four bits and first four new bits,
        // save last two bits
        // Second char use previous six bits and first two new bits,
        // save last four bits
        return -1 // can't actually reach this line
    }

    /***
     * Reads decoded data into an array of bytes and returns the actual number
     * of bytes read, or -1 if end of stream was reached.
     *
     * @param buf
     * the buffer into which the data is read
     * @param off
     * the start offset of the data
     * @param len
     * the maximum number of bytes to read
     * @return the actual number of bytes read, or -1 if the end of the input
     * stream is reached
     * @exception IOException
     * if an I/O error occurs
     */
    @Throws(IOException::class)
    override fun read(buf: ByteArray, off: Int, len: Int): Int {
        if (buf.size < len + off - 1) {
            throw IOException("The input buffer is too small: " + len + " bytes requested starting at offset " + off + " while the buffer " + " is only " + buf.size + " bytes long.")
        }

        // This could of course be optimized
        var i: Int
        i = 0
        while (i < len) {
            val x = read()
            if (x == -1 && i == 0) { // an immediate -1 returns -1
                return -1
            } else if (x == -1) { // a later -1 returns the chars read so far
                break
            }
            buf[off + i] = x.toByte()
            i++
        }
        return i
    }

    companion object {

        private val chars = charArrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/')

        // A mapping between char values and six-bit integers
        private val ints = IntArray(128)

        init {
            for (i in 0..63) {
                ints[chars[i].toInt()] = i
            }
        }

        /***
         * Returns the decoded form of the given encoded string, as a String. Note
         * that not all binary data can be represented as a String, so this method
         * should only be used for encoded String data. Use decodeToBytes()
         * otherwise.
         *
         * @param encoded
         * the string to decode
         * @return the decoded form of the encoded string
         */
        fun decode(encoded: String): String {
            return if (TextUtils.isEmpty(encoded)) {
                ""
            } else String(decodeToBytes(encoded))
        }

        /***
         * Returns the decoded form of the given encoded string, as bytes.
         *
         * @param encoded
         * the string to decode
         * @return the decoded form of the encoded string
         */
        fun decodeToBytes(encoded: String): ByteArray {
            val bytes = encoded.toByteArray()
            val input = Base64Decoder(ByteArrayInputStream(bytes))
            val out = ByteArrayOutputStream((bytes.size * 0.75).toInt())
            try {
                val buf = ByteArray(4 * 1024) // 4K buffer
                var bytesRead: Int = input.read(buf)

                while (bytesRead != -1) {
                    out.write(buf, 0, bytesRead)
                    bytesRead = input.read(buf)
                }
                return out.toByteArray()
            } catch (e: IOException) {
                throw RuntimeException(e)
            } finally {
                try {
                    input.close()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }

                try {
                    out.close()
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }

            }
        }
    }
}

