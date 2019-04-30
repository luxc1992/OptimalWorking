package com.yst.onecity.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;

import static com.yst.onecity.Constant.NO0_F;
import static com.yst.onecity.Constant.NO1_F;

/**
 * 二维码工具类
 *
 * @author liumanqing
 * @version 3.0.1
 * @date 2017/12/12
 */
public class ZxingUtils {
    /**
     * 创建二维码位图
     *
     * @param content 字符串内容
     * @param size    位图宽&高(单位:px)
     * @return
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(@Nullable String content, int size) {
        return createQRCodeBitmap(content, size, "UTF-8", "H", "4", Color.BLACK, Color.WHITE, null, null, 0F);
    }

    /**
     * 创建二维码位图 (自定义黑、白色块颜色)
     *
     * @param content    字符串内容
     * @param size       位图宽&高(单位:px)
     * @param colorBlack 黑色色块的自定义颜色值
     * @param colorWhite 白色色块的自定义颜色值
     * @return
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(@Nullable String content, int size, @ColorInt int colorBlack, @ColorInt int colorWhite) {
        return createQRCodeBitmap(content, size, "UTF-8", "H", "4", colorBlack, colorWhite, null, null, 0F);
    }

    /**
     * 创建二维码位图 (带Logo小图片)
     *
     * @param content     字符串内容
     * @param size        位图宽&高(单位:px)
     * @param logoBitmap  logo图片
     * @param logoPercent logo小图片在二维码图片中的占比大小,范围[0F,1F]。超出范围->默认使用0.2F
     * @return
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(String content, int size, @Nullable Bitmap logoBitmap, float logoPercent) {
        return createQRCodeBitmap(content, size, "UTF-8", "H", "4", Color.BLACK, Color.WHITE, null, logoBitmap, logoPercent);
    }

    /**
     * 创建二维码位图 (Bitmap颜色代替黑色) 注意!!!注意!!!注意!!! 选用的Bitmap图片一定不能有白色色块,否则会识别不出来!!!
     *
     * @param content      字符串内容
     * @param size         位图宽&高(单位:px)
     * @param targetBitmap 目标图片 (如果targetBitmap != null, 黑色色块将会被该图片像素色值替代)
     * @return
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(String content, int size, Bitmap targetBitmap) {
        return createQRCodeBitmap(content, size, "UTF-8", "H", "4", Color.BLACK, Color.WHITE, targetBitmap, null, 0F);
    }

    /**
     * 创建二维码位图 (支持自定义配置和自定义样式)
     *
     * @param content         字符串内容
     * @param size            位图宽&高(单位:px)
     * @param characterSet    字符集/字符转码格式 (支持格式:{@link CharacterSetECI })。传null时,zxing源码默认使用 "ISO-8859-1"
     * @param errorCorrection 容错级别 (支持级别:{@link ErrorCorrectionLevel })。传null时,zxing源码默认使用 "L"
     * @param margin          空白边距 (可修改,要求:整型且>=0), 传null时,zxing源码默认使用"4"。
     * @param colorBlack      黑色色块的自定义颜色值
     * @param colorWhite      白色色块的自定义颜色值
     * @param targetBitmap    目标图片 (如果targetBitmap != null, 黑色色块将会被该图片像素色值替代)
     * @param logoBitmap      logo小图片
     * @param logoPercent     logo小图片在二维码图片中的占比大小,范围[0F,1F],超出范围->默认使用0.2F。
     * @return
     */
    @Nullable
    public static Bitmap createQRCodeBitmap(@Nullable String content, int size,
                                            @Nullable String characterSet, @Nullable String errorCorrection, @Nullable String margin,
                                            @ColorInt int colorBlack, @ColorInt int colorWhite, @Nullable Bitmap targetBitmap,
                                            @Nullable Bitmap logoBitmap, float logoPercent) {

        //1.参数合法性判断 
        // 字符串内容判空
        if (TextUtils.isEmpty(content)) {
            return null;
        }
        // 宽&高都需要>0
        if (size <= 0) {
            return null;
        }

        try {
            // 2.设置二维码相关配置,生成BitMatrix(位矩阵)对象 
            Hashtable<EncodeHintType, String> hints = new Hashtable<>();

            if (!TextUtils.isEmpty(characterSet)) {
                // 字符转码格式设置
                hints.put(EncodeHintType.CHARACTER_SET, characterSet);
            }

            if (!TextUtils.isEmpty(errorCorrection)) {
                // 容错级别设置
                hints.put(EncodeHintType.ERROR_CORRECTION, errorCorrection);
            }

            if (!TextUtils.isEmpty(margin)) {
                // 空白边距设置
                hints.put(EncodeHintType.MARGIN, margin);
            }
            BitMatrix bitMatrix = new QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints);

            /** 3.根据BitMatrix(位矩阵)对象为数组元素赋颜色值 */
            if (targetBitmap != null) {
                targetBitmap = Bitmap.createScaledBitmap(targetBitmap, size, size, false);
            }
            int[] pixels = new int[size * size];
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    // 黑色色块像素设置
                    if (bitMatrix.get(x, y)) {
                        if (targetBitmap != null) {
                            pixels[y * size + x] = targetBitmap.getPixel(x, y);
                        } else {
                            pixels[y * size + x] = colorBlack;
                        }
                        // 白色色块像素设置
                    } else {
                        pixels[y * size + x] = colorWhite;
                    }
                }
            }

            /** 4.创建Bitmap对象,根据像素数组设置Bitmap每个像素点的颜色值,之后返回Bitmap对象 */
            Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, size, 0, 0, size, size);

            /** 5.为二维码添加logo小图标 */
            if (logoBitmap != null) {
                return addLogo(bitmap, logoBitmap, logoPercent);
            }

            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 向一张图片中间添加logo小图片(图片合成)
     *
     * @param srcBitmap   原图片
     * @param logoBitmap  logo图片
     * @param logoPercent 百分比 (用于调整logo图片在原图片中的显示大小, 取值范围[0,1], 传值不合法时使用0.2F)
     *                    原图片是二维码时,建议使用0.2F,百分比过大可能导致二维码扫描失败。
     * @return
     */
    @Nullable
    private static Bitmap addLogo(@Nullable Bitmap srcBitmap, @Nullable Bitmap logoBitmap, float logoPercent) {

        /** 1. 参数合法性判断 */
        if (srcBitmap == null) {
            return null;
        }

        if (logoBitmap == null) {
            return srcBitmap;
        }

        if (logoPercent < NO0_F || logoPercent > NO1_F) {
            logoPercent = 0.2F;
        }

        /** 2. 获取原图片和Logo图片各自的宽、高值 */
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int logoWidth = logoBitmap.getWidth();
        int logoHeight = logoBitmap.getHeight();

        /** 3. 计算画布缩放的宽高比 */
        float scaleWidth = srcWidth * logoPercent / logoWidth;
        float scaleHeight = srcHeight * logoPercent / logoHeight;

        /** 4. 使用Canvas绘制,合成图片 */
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(srcBitmap, 0, 0, null);
        canvas.scale(scaleWidth, scaleHeight, srcWidth / 2, srcHeight / 2);
        canvas.drawBitmap(logoBitmap, srcWidth / 2 - logoWidth / 2, srcHeight / 2 - logoHeight / 2, null);

        return bitmap;
    }
}
