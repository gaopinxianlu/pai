package cn.pai.common.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * @author linyt
 */

public class BitmapUtil {

//    /**
//     * 通过压缩像素占用的内存来达到压缩的效果，一般不建议使用ARGB_4444，画质差
//     * 相比ARGB_8888将节省一半的内存
//     */
//    private Bitmap compressRGB565(byte[] bitmaps) {
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        return BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length, options);
//    }


    /**
     * 采样率压缩 缩放bitamp的尺寸
     *
     * @param path       图片路径
     * @param sampleSize
     * @param isRGB565   是否转成RGB565格式，相比ARGB_8888将节省一半的内存
     * @return
     */
    public static Bitmap compressSampleSize(String path, int sampleSize, boolean isRGB565) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        if (isRGB565) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     *
     * @param inputStream 图片输入流
     * @param sampleSize
     * @param isRGB565    是否转成RGB565格式，相比ARGB_8888将节省一半的内存
     * @return
     */
    public static Bitmap compressSampleSize(InputStream inputStream, int sampleSize, boolean isRGB565) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        if (isRGB565) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     *
     * @param bitmaps
     * @param sampleSize
     * @param isRGB565   是否转成RGB565格式，相比ARGB_8888将节省一半的内存
     * @return
     */
    public static Bitmap compressSampleSize(byte[] bitmaps, int sampleSize, boolean isRGB565) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = sampleSize;
        if (isRGB565) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length, options);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     * 默认转成RGB565格式，相比ARGB_8888将节省一半的内存
     *
     * @param path       图片路径
     * @param sampleSize
     * @return
     */
    public static Bitmap compressSampleSize(String path, int sampleSize) {
        return compressSampleSize(path, sampleSize, true);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     * 默认转成RGB565格式，相比ARGB_8888将节省一半的内存
     *
     * @param inputStream
     * @param sampleSize
     * @return
     */
    public static Bitmap compressSampleSize(InputStream inputStream, int sampleSize) {
        return compressSampleSize(inputStream, sampleSize, true);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     * 默认转成RGB565格式，相比ARGB_8888将节省一半的内存
     *
     * @param bitmaps
     * @param sampleSize
     * @return
     */
    public static Bitmap compressSampleSize(byte[] bitmaps, int sampleSize) {
        return compressSampleSize(bitmaps, sampleSize, true);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     *
     * @param path     图片路径
     * @param width    需要压缩到的宽度
     * @param height   需要压缩到的高度
     * @param isRGB565 是否转成RGB565格式，相比ARGB_8888将节省一半的内存
     * @return
     */
    public static Bitmap compressSampleSize(String path, int width, int height, boolean isRGB565) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置inJustDecodeBounds为true只获取大小，不生成Btimap
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        if ((srcWidth > srcHeight && width < height) || (srcWidth < srcHeight && width > height)) {
            int temp = srcWidth;
            srcWidth = srcHeight;
            srcHeight = temp;
        }
//        int sampleRatio = Math.min(srcWidth / width, srcHeight / height);算平均数感觉更合理
        int sampleRatio = ((srcWidth / width) + (srcHeight / height)) / 2;
        options.inSampleSize = sampleRatio;
        options.inJustDecodeBounds = false;
        if (isRGB565) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     *
     * @param inputStream
     * @param width       需要压缩到的宽度
     * @param height      需要压缩到的高度
     * @param isRGB565    是否转成RGB565格式，相比ARGB_8888将节省一半的内存
     * @return
     */
    public static Bitmap compressSampleSize(InputStream inputStream, int width, int height, boolean isRGB565) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置inJustDecodeBounds为true只获取大小，不生成Btimap
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        if ((srcWidth > srcHeight && width < height) || (srcWidth < srcHeight && width > height)) {
            int temp = srcWidth;
            srcWidth = srcHeight;
            srcHeight = temp;
        }
//        int sampleRatio = Math.min(srcWidth / width, srcHeight / height);算平均数感觉更合理
        int sampleRatio = ((srcWidth / width) + (srcHeight / height)) / 2;
        options.inSampleSize = sampleRatio;
        options.inJustDecodeBounds = false;
        if (isRGB565) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     *
     * @param bitmaps
     * @param width    需要压缩到的宽度
     * @param height   需要压缩到的高度
     * @param isRGB565 是否转成RGB565格式，相比ARGB_8888将节省一半的内存
     * @return
     */
    public static Bitmap compressSampleSize(byte[] bitmaps, int width, int height, boolean isRGB565) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置inJustDecodeBounds为true只获取大小，不生成Btimap
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length, options);
        int srcWidth = options.outWidth;
        int srcHeight = options.outHeight;
        if ((srcWidth > srcHeight && width < height) || (srcWidth < srcHeight && width > height)) {
            int temp = srcWidth;
            srcWidth = srcHeight;
            srcHeight = temp;
        }
//        int sampleRatio = Math.min(srcWidth / width, srcHeight / height);算平均数感觉更合理
        int sampleRatio = ((srcWidth / width) + (srcHeight / height)) / 2;
        options.inSampleSize = sampleRatio;
        options.inJustDecodeBounds = false;
        if (isRGB565) {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        return BitmapFactory.decodeByteArray(bitmaps, 0, bitmaps.length, options);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     * 默认转成RGB565格式，相比ARGB_8888将节省一半的内存
     *
     * @param path   图片路径
     * @param width  需要压缩到的宽度
     * @param height 需要压缩到的高度
     * @return
     */
    public static Bitmap compressSampleSize(String path, int width, int height) {
        return compressSampleSize(path, width, height, true);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     * 默认转成RGB565格式，相比ARGB_8888将节省一半的内存
     *
     * @param inputStream
     * @param width       需要压缩到的宽度
     * @param height      需要压缩到的高度
     * @return
     */
    public static Bitmap compressSampleSize(InputStream inputStream, int width, int height) {
        return compressSampleSize(inputStream, width, height, true);
    }

    /**
     * 采样率压缩 缩放bitamp的尺寸
     * 默认转成RGB565格式，相比ARGB_8888将节省一半的内存
     *
     * @param bitmaps
     * @param width   需要压缩到的宽度
     * @param height  需要压缩到的高度
     * @return
     */
    public static Bitmap compressSampleSize(byte[] bitmaps, int width, int height) {
        return compressSampleSize(bitmaps, width, height, true);
    }

    /**
     * 质量压缩 保持像素的前提下改变图片的位深及透明度，图片长宽像素不变，所以内存大小不变
     * png格式这种图片没有作用，因为png是无损压缩
     *
     * @param src
     * @param quality 0-100
     * @return
     */
    public static Bitmap compressQuality(Bitmap src, int quality) {
        Bitmap bitmap;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        src.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        byte[] bytes = bos.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        src.recycle();
        src = null;
        return bitmap;
    }

    /**
     * 放缩法压缩 通过矩阵对图片进行裁剪，也是通过缩放图片尺寸,和采样率的原理一样
     *
     * @param src
     * @param sx  x轴缩放 0-1
     * @param sy  y轴缩放 0-1
     * @return
     */
    public static Bitmap compressMatrix(Bitmap src, float sx, float sy) {
        Bitmap bitmap;
        Matrix matrix = new Matrix();
        matrix.setScale(sx, sy);
        bitmap = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
        src.recycle();
        src = null;
        return bitmap;
    }

    /**
     * 将图片的大小压缩成用户的期望大小，来减少占用内存
     *
     * @param src
     * @param width
     * @param height
     * @return
     */
    public static Bitmap compressScaleBitmap(Bitmap src, int width, int height) {
        Bitmap bitmap;
        bitmap = Bitmap.createScaledBitmap(src, width, height, true);
        src.recycle();
        src = null;
        return bitmap;
    }

    /**
     * 输入流转Bitmap
     * 不会改变大小
     *
     * @param is
     * @return
     */
    public static Bitmap toBitmap(InputStream is) {
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * byte[] 转 bitmap
     * 不会改变大小
     *
     * @param bytes
     * @return
     */
    public static Bitmap toBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    /**
     * biteMap转bytes
     * 不会改变大小
     *
     * @param bitmap
     * @return
     */
    public static byte[] toBytes(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        return byteBuffer.array();
    }

    /**
     * biteMap转bytes
     *
     * @param bitmap
     * @param quality 0-100质量压缩值
     * @return
     */
    public static byte[] toBytes(Bitmap bitmap, int quality) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        return baos.toByteArray();
    }

    /**
     * 输入流转bytes
     *
     * @param is 输入流
     * @return
     */
    public static byte[] toBytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[2048];
        int index;
        try {
            while ((index = is.read(buffer)) > 0) {
                baos.write(buffer, 0, index);
            }
            return baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (baos != null) {
                try {
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
