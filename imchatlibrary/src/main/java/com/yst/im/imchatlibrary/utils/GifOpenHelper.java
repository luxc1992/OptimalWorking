package com.yst.im.imchatlibrary.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import com.yst.im.imsdk.MessageConstant;

import java.io.InputStream;
import java.util.Vector;

/**
 * 动态表情辅助类
 *
 * @author lierpeng
 * @date 2018/03/28
 * @version 1.0.0
 */
public class GifOpenHelper {

    /**
     * to store *.gif data, Bitmap & delay
     */
    class GifFrame {
        /**
         * to access image & delay w/o interface
         */
        public Bitmap image;
        public int delay;

        public GifFrame(Bitmap im, int del) {
            image = im;
            delay = del;
        }

    }

    /**
     * to define some error type
     */
    public static final int STATUS_OK = 0;
    public static final int STATUS_FORMAT_ERROR = 1;
    public static final int STATUS_OPEN_ERROR = 2;

    protected int status;

    protected InputStream in;
    /**
     * full image width
     */

    protected int width;
    /**
     * full image height
     */
    protected int height;
    /**
     * global color table used
     */
    protected boolean gctFlag;
    /**
     * size of global color table
     */
    protected int gctSize;
    /**
     * iterations; 0 = repeat forever
     */
    protected int loopCount = 1;
    /**
     * global color table
     */
    protected int[] gct;
    /**
     * local color table
     */
    protected int[] lct;
    /**
     * active color table
     */
    protected int[] act;
    /**
     * background color index
     */
    protected int bgIndex;
    /**
     * background color
     */
    protected int bgColor;
    /**
     * previous bg color
     */
    protected int lastBgColor;
    /**
     * pixel aspect ratio
     */
    protected int pixelAspect;
    /**
     * local color table flag
     */
    protected boolean lctFlag;
    /**
     * interlace flag
     */
    protected boolean interlace;
    /**
     * local color table size
     */
    protected int lctSize;
    /**
     * current image rectangle
     */
    protected int ix, iy, iw, ih;
    protected int lrx, lry, lrw, lrh;
    /**
     * current frame
     */
    protected Bitmap image;
    /**
     * previous frame
     */
    protected Bitmap lastImage;
    protected int frameIndex = 0;

    public int getframeIndex() {
        return frameIndex;
    }

    public void setframeIndex(int frameIndex) {
        this.frameIndex = frameIndex;
        if (frameIndex > frames.size() - 1) {
            frameIndex = 0;
        }
    }

    /**
     * current data block
     */
    protected byte[] block = new byte[256];
    /**
     * block size
     */
    protected int blockSize = 0;

    /**
     * last graphic control extension info
     */
    protected int dispose = 0;
    /**
     * 0=no action; 1=leave in place; 2=restore to bg; 3=restore to prev
     */
    protected int lastDispose = 0;
    /**
     * use transparent color
     */
    protected boolean transparency = false;
    /**
     * delay in milliseconds
     */
    protected int delay = 0;
    /**
     * transparent color index
     */
    protected int transIndex;
    protected static final int MAX_STACK_SIZE = 4096;
    /**
     *     max decoder pixel stack size
     */

    /**
     * LZW decoder working arrays
     */
    protected short[] prefix;
    protected byte[] suffix;
    protected byte[] pixelStack;
    protected byte[] pixels;
    /**
     * frames read from current file
     */
    protected Vector<GifFrame> frames;
    protected int frameCount;

    /**
     * to get its Width / Height
     */
    public int getWidth() {
        return width;
    }

    public int getHeigh() {
        return height;
    }

    /**
     * Gets display duration for specified frame.
     *
     * @param n int index of frame
     * @return delay in milliseconds
     */
    public int getDelay(int n) {
        delay = -1;
        if ((n >= 0) && (n < frameCount)) {
            delay = ((GifFrame) frames.elementAt(n)).delay;
        }
        return delay;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public Bitmap getImage() {
        return getFrame(0);
    }

    public int getLoopCount() {
        return loopCount;
    }

    protected void setPixels() {
        int[] dest = new int[width * height];
        // fill in starting image contents based on last image's dispose code
        if (lastDispose > 0) {
            if (lastDispose == MessageConstant.NUM_3) {
                // use image before last
                int n = frameCount - 2;
                if (n > 0) {
                    lastImage = getFrame(n - 1);
                } else {
                    lastImage = null;
                }
            }
            if (lastImage != null) {
                lastImage.getPixels(dest, 0, width, 0, 0, width, height);
                // copy pixels
                if (lastDispose == MessageConstant.NUM_2) {
                    // fill last image rect area with background color
                    int c = 0;
                    if (!transparency) {
                        c = lastBgColor;
                    }
                    for (int i = 0; i < lrh; i++) {
                        int n1 = (lry + i) * width + lrx;
                        int n2 = n1 + lrw;
                        for (int k = n1; k < n2; k++) {
                            dest[k] = c;
                        }
                    }
                }
            }
        }

        // copy each source line to the appropriate place in the destination
        int pass = 1;
        int inc = 8;
        int iLine = 0;
        for (int i = 0; i < ih; i++) {
            int line = i;
            if (interlace) {
                if (iLine >= ih) {
                    pass++;
                    switch (pass) {
                        case 2:
                            iLine = 4;
                            break;
                        case 3:
                            iLine = 2;
                            inc = 4;
                            break;
                        case 4:
                            iLine = 1;
                            inc = 2;
                        default:
                            break;
                    }
                }
                line = iLine;
                iLine += inc;
            }
            line += iy;
            if (line < height) {
                int k = line * width;
                // start of line in dest
                int dx = k + ix;
                // end of dest line
                int dlim = dx + iw;
                if ((k + width) < dlim) {
                    // past dest edge
                    dlim = k + width;
                }
                // start of line in source

                int sx = i * iw;
                while (dx < dlim) {
                    // map color and insert in destination
                    int index = ((int) pixels[sx++]) & 0xff;
                    int c = act[index];
                    if (c != 0) {
                        dest[dx] = c;
                    }
                    dx++;
                }
            }
        }
        image = Bitmap.createBitmap(dest, width, height, Config.ARGB_4444);
    }

    public Bitmap getFrame(int n) {
        Bitmap im = null;
        if ((n >= 0) && (n < frameCount)) {
            im = ((GifFrame) frames.elementAt(n)).image;
        }
        return im;
    }

    public Bitmap nextBitmap() {
        frameIndex++;
        if (frameIndex > frames.size() - 1) {
            frameIndex = 0;
        }
        return ((GifFrame) frames.elementAt(frameIndex)).image;
    }

    public int nextDelay() {
        return ((GifFrame) frames.elementAt(frameIndex)).delay;
    }

    /**
     * 读取所有gif图
     *
     * @param is 输入
     * @return 索引
     */
    public int read(InputStream is) {
        init();
        if (is != null) {
            in = is;

            readHeader();
            if (!err()) {
                readContents();
                if (frameCount < 0) {
                    status = STATUS_FORMAT_ERROR;
                }
            }
        } else {
            status = STATUS_OPEN_ERROR;
        }
        try {
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    protected void decodeImageData() {
        int nullCode = -1;
        int nPix = iw * ih;
        int available, clear, codeMask, codeSize, endOfInformation, inCode, oldCode, bits, code, count, i, datum, dataSize, first, top, bi, pi;

        if ((pixels == null) || (pixels.length < nPix)) {
            // allocate new pixel array
            pixels = new byte[nPix];
        }
        if (prefix == null) {
            prefix = new short[MAX_STACK_SIZE];
        }
        if (suffix == null) {
            suffix = new byte[MAX_STACK_SIZE];
        }
        if (pixelStack == null) {
            pixelStack = new byte[MAX_STACK_SIZE + 1];
        }
        // Initialize GIF data stream decoder.
        dataSize = read();
        clear = 1 << dataSize;
        endOfInformation = clear + 1;
        available = clear + 2;
        oldCode = nullCode;
        codeSize = dataSize + 1;
        codeMask = (1 << codeSize) - 1;
        for (code = 0; code < clear; code++) {
            prefix[code] = 0;
            suffix[code] = (byte) code;
        }

        // Decode GIF pixel stream.
        datum = bits = count = first = top = pi = bi = 0;
        for (i = 0; i < nPix; ) {
            if (top == 0) {
                if (bits < codeSize) {
                    // Load bytes until there are enough bits for a code.
                    if (count == 0) {
                        // Read a new data block.
                        count = readBlock();
                        if (count <= 0) {
                            break;
                        }
                        bi = 0;
                    }
                    datum += (((int) block[bi]) & 0xff) << bits;
                    bits += 8;
                    bi++;
                    count--;
                    continue;
                }
                // Get the next code.
                code = datum & codeMask;
                datum >>= codeSize;
                bits -= codeSize;

                // Interpret the code
                if ((code > available) || (code == endOfInformation)) {
                    break;
                }
                if (code == clear) {
                    // Reset decoder.
                    codeSize = dataSize + 1;
                    codeMask = (1 << codeSize) - 1;
                    available = clear + 2;
                    oldCode = nullCode;
                    continue;
                }
                if (oldCode == nullCode) {
                    pixelStack[top++] = suffix[code];
                    oldCode = code;
                    first = code;
                    continue;
                }
                inCode = code;
                if (code == available) {
                    pixelStack[top++] = (byte) first;
                    code = oldCode;
                }
                while (code > clear) {
                    pixelStack[top++] = suffix[code];
                    code = prefix[code];
                }
                first = ((int) suffix[code]) & 0xff;
                // Add a new string to the string table,
                if (available >= MAX_STACK_SIZE) {
                    break;
                }
                pixelStack[top++] = (byte) first;
                prefix[available] = (short) oldCode;
                suffix[available] = (byte) first;
                available++;
                if (((available & codeMask) == 0)
                        && (available < MAX_STACK_SIZE)) {
                    codeSize++;
                    codeMask += available;
                }
                oldCode = inCode;
            }

            // Pop a pixel off the pixel stack.
            top--;
            pixels[pi++] = pixelStack[top];
            i++;
        }
        for (i = pi; i < nPix; i++) {
            // clear missing pixels
            pixels[i] = 0;
        }
    }

    protected boolean err() {
        return status != STATUS_OK;
    }

    /**
     * to initia variable
     */
    public void init() {
        status = STATUS_OK;
        frameCount = 0;
        frames = new Vector<GifFrame>();
        gct = null;
        lct = null;
    }

    protected int read() {
        int curByte = 0;
        try {
            curByte = in.read();
        } catch (Exception e) {
            status = STATUS_FORMAT_ERROR;
        }
        return curByte;
    }

    protected int readBlock() {
        blockSize = read();
        int n = 0;
        if (blockSize > 0) {
            try {
                int count = 0;
                while (n < blockSize) {
                    count = in.read(block, n, blockSize - n);
                    if (count == -1) {
                        break;
                    }
                    n += count;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (n < blockSize) {
                status = STATUS_FORMAT_ERROR;
            }
        }
        return n;
    }

    /**
     * Global Color Table
     */
    protected int[] readColorTable(int ncolors) {
        int nbytes = 3 * ncolors;
        int[] tab = null;
        byte[] c = new byte[nbytes];
        int n = 0;
        try {
            n = in.read(c);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (n < nbytes) {
            status = STATUS_FORMAT_ERROR;
        } else {
            // max size to avoid bounds checks
            tab = new int[256];
            int i = 0;
            int j = 0;
            while (i < ncolors) {
                int r = ((int) c[j++]) & 0xff;
                int g = ((int) c[j++]) & 0xff;
                int b = ((int) c[j++]) & 0xff;
                tab[i++] = 0xff000000 | (r << 16) | (g << 8) | b;
            }
        }
        return tab;
    }

    /**
     * Image Descriptor
     */
    protected void readContents() {
        // read GIF file content blocks
        boolean done = false;
        while (!(done || err())) {
            int code = read();
            switch (code) {
                // image separator
                case 0x2C:
                    readImage();
                    break;
                // extension
                case 0x21:
                    code = read();
                    switch (code) {
                        // graphics control extension
                        case 0xf9:
                            readGraphicControlExt();
                            break;
                        // application extension
                        case 0xff:
                            readBlock();
                            String app = "";
                            for (int i = 0; i < MessageConstant.NUM_11; i++) {
                                app += (char) block[i];
                            }
                            if ("NETSCAPE2.0".equals(app)) {
                                readNetscapeExt();
                            } else {
                                skip(); // don't care
                            }
                            break;
                        default: // uninteresting extension
                            skip();
                    }
                    break;
                // terminator
                case 0x3b:
                    done = true;
                    break;
                // bad byte, but keep going and see what happens
                case 0x00:
                    break;
                default:
                    status = STATUS_FORMAT_ERROR;
            }
        }
    }

    protected void readGraphicControlExt() {
        // block size
        read();
        // packed fields
        int packed = read();
        // disposal method
        dispose = (packed & 0x1c) >> 2;
        if (dispose == 0) {
            // elect to keep old image if discretionary
            dispose = 1;
        }
        transparency = (packed & 1) != 0;
        // delay in milliseconds
        delay = readShort() * 10;
        // transparent color index
        transIndex = read();
        // block terminator
        read();
    }

    /**
     * to get Stream - Head
     */
    protected void readHeader() {
        String id = "";
        for (int i = 0; i < MessageConstant.NUM_6; i++) {
            id += (char) read();
        }
        if (!id.startsWith(MessageConstant.GIF)) {
            status = STATUS_FORMAT_ERROR;
            return;
        }
        readLSD();
        if (gctFlag && !err()) {
            gct = readColorTable(gctSize);
            bgColor = gct[bgIndex];
        }
    }

    protected void readImage() {
        // offset of X
        // (sub)image position & size
        ix = readShort();
        // offset of Y
        iy = readShort();
        // width of bitmap
        iw = readShort();
        // height of bitmap
        ih = readShort();

        // Local Color Table Flag
        int packed = read();
        // 1 - local color table flag
        lctFlag = (packed & 0x80) != 0;

        // Interlace Flag, to array with interwoven if ENABLE, with order
        // otherwise
        // 2 - interlace flag
        interlace = (packed & 0x40) != 0;
        // 3 - sort flag
        // 4-5 - reserved
        // 6-8 - local color table size
        lctSize = 2 << (packed & 7);
        if (lctFlag) {
            // read table
            lct = readColorTable(lctSize);
            // make local table active
            act = lct;
        } else {
            // make global table active
            act = gct;
            if (bgIndex == transIndex) {
                bgColor = 0;
            }
        }
        int save = 0;
        if (transparency) {
            save = act[transIndex];
            // set transparent color if specified
            act[transIndex] = 0;
        }
        if (act == null) {
            // no color table defined
            status = STATUS_FORMAT_ERROR;
        }
        if (err()) {
            return;
        }
        // decode pixel data
        decodeImageData();
        skip();
        if (err()) {
            return;
        }
        frameCount++;
        // create new image to receive frame data
        image = Bitmap.createBitmap(width, height, Config.ARGB_4444);
        // createImage(width, height);
        setPixels(); // transfer pixel data to image
        // add image to frame
        frames.addElement(new GifFrame(image, delay));
        // list
        if (transparency) {
            act[transIndex] = save;
        }
        resetFrame();
    }

    /**
     * Logical Screen Descriptor
     */
    protected void readLSD() {
        // logical screen size
        width = readShort();
        height = readShort();
        // packed fields
        int packed = read();
        // 1 : global color table flag
        gctFlag = (packed & 0x80) != 0;
        // 2-4 : color resolution
        // 5 : gct sort flag
        // 6-8 : gct size
        gctSize = 2 << (packed & 7);
        // background color index
        bgIndex = read();
        // pixel aspect ratio
        pixelAspect = read();
    }

    protected void readNetscapeExt() {
        do {
            readBlock();
            if (block[0] == 1) {
                // loop count sub-block
                int b1 = ((int) block[1]) & 0xff;
                int b2 = ((int) block[2]) & 0xff;
                loopCount = (b2 << 8) | b1;
            }
        } while ((blockSize > 0) && !err());
    }

    /**
     * read 8 bit data
     */
    protected int readShort() {
        // read 16-bit value, LSB first
        return read() | (read() << 8);
    }

    protected void resetFrame() {
        lastDispose = dispose;
        lrx = ix;
        lry = iy;
        lrw = iw;
        lrh = ih;
        lastImage = image;
        lastBgColor = bgColor;
        dispose = 0;
        transparency = false;
        delay = 0;
        lct = null;
    }

    /**
     * Skips variable length blocks up to and including next zero length block.
     */
    protected void skip() {
        do {
            readBlock();
        } while ((blockSize > 0) && !err());
    }
}
