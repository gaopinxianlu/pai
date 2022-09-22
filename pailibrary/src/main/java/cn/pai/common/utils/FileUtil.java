package cn.pai.common.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

public class FileUtil {
    /*
     * 私有目录结构:
     * 1.模拟器
     *  /data/user/0/包名/cache
     *  /data/user/0/包名/code_cache
     *  /data/user/0/包名/databases
     *  /data/user/0/包名/shared_prefs
     *  /data/user/0/包名/files
     *
     * 2.真机
     *  /data/data/包名/lib
     *  /data/data/包名/cache
     *  /data/data/包名/databases
     *  /data/data/包名/files
     *  /data/data/包名/shared_prefs
     *
     *  Google官方建议我们将App的数据存储在外部存储的私有目录中该App的包名下,
     * 当用户卸载App后, 这些数据会一并删除.
     */

    /*----------------------------内部存储----------------------------*/

    /**
     * 获取应用私有目录
     * /data/data/包名/files
     * 或
     * /data/user/0/包名/files
     */
    public static File getFilesDir(Context context) {
        return context.getFilesDir();
    }

    /**
     * 获取应用缓存目录
     * /data/data/包名/cache
     * 或
     * /data/user/0/包名/cache
     */
    public static File getCacheDir(Context context) {
        return context.getCacheDir();
    }

    /**
     * 获取内部存储根目录
     * /data
     */
    public static File getInternalRootDir() {
        return Environment.getDataDirectory();
    }

    /**
     * 获取内部存储下载目录
     * /data/cache
     */
    public static File getInternalDownloadDir() {
        return Environment.getDownloadCacheDirectory();
    }

    /*----------------------------内部存储----------------------------*/

    /**
     * 获取应用在外部存储的私有目录下的文件目录
     * /storage/emulated/0/Android/data/包名/files/目录名
     * 目录名可以选择Environment中的多个常量
     */
    public static File getAppExternalDir(Context context, String dir) {
        return context.getExternalFilesDir(dir);
    }

    /**
     * 获取应用在外部存储的私有目录下的缓存目录
     * storage/emulated/0/Android/data/包名/cache
     */
    public static File getAppCacheDir(Context context) {
        return context.getExternalCacheDir();
    }

    /**
     * 获取外部存储根目录
     * /storage/emulated/0
     */
    public static File getExternalRootDir() {
        return Environment.getExternalStorageDirectory();
    }

    /**
     * 获取外部存储指定目录
     * /storage/emulated/0/目录名
     */
    public static File getExternalDir(String dir) {
        return Environment.getExternalStoragePublicDirectory(dir);
    }

    /**
     * 判断SD卡是否被挂载
     */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取SD卡根目录
     *
     * @return /storage/emulated/0
     */
    public static String getSDCardBaseDir() {
        if (isSDCardMounted()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return null;
    }

    /**
     * 获取SD卡大小(MB), 若未挂载, 返回-1
     */
    public static long getSDCardSize() {
        if (isSDCardMounted()) {
            StatFs statFs = new StatFs(getSDCardBaseDir());
            long count = statFs.getBlockCountLong();
            long size = statFs.getBlockSizeLong();
            return count * size / 1024 / 1024;
        }
        return -1;
    }

    /**
     * 获取SD卡空闲空间大小(MB), 若未挂载, 返回-1
     */
    public static long getSDCardFreeSize() {
        if (isSDCardMounted()) {
            StatFs statFs = new StatFs(getSDCardBaseDir());
            long freeCount = statFs.getFreeBlocksLong();
            long size = statFs.getBlockSizeLong();
            return freeCount * size / 1024 / 1024;
        }
        return -1;
    }

    /**
     * 获取SD卡可用空间大小(MB), 若未挂载, 返回-1
     */
    public static long getSDCardAvailableSize() {
        if (isSDCardMounted()) {
            StatFs statFs = new StatFs(getSDCardBaseDir());
            long availableCount = statFs.getAvailableBlocksLong();
            long size = statFs.getBlockSizeLong();
            return availableCount * size / 1024 / 1024;
        }
        return -1;
    }

    /**
     * 创建文件夹
     *
     * @param path
     * @return
     */
    public static boolean createDir(String path) {
        File file = new File(path);
        if (file.exists()) {
            return true;
        }
        return file.mkdirs();
    }

    /**
     * 创建文件
     *
     * @param path
     * @param name
     * @return
     */
    public static boolean createFile(String path, String name) {
        File file = new File(path, name);
        if (file.exists()) {
            return true;
        }
        if (createDir(path)) {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public static String getFileName(String path) {
        int index = path.lastIndexOf(path.contains("\\") ? "\\" : "/") + 1;
        String subName = path.substring(index);
        if (!subName.contains(".")) {
            return null;
        }
        return subName;
    }

    public static String getFilePath(String path) {
        int index = path.lastIndexOf(path.contains("\\") ? "\\" : "/") + 1;
        String subPath = path.substring(index);
        if (!subPath.contains(".")) {
            return path;
        }
        return path.substring(0, index);
    }

    /**
     * 文件读取
     *
     * @param path
     * @return
     */
    public static byte[] readBytes(String path) {
        InputStream is = null;
        ByteArrayOutputStream os = null;
        try {
            is = new FileInputStream(path);
            os = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int index;
            while ((index = is.read(buffer)) > 0) {
                os.write(buffer, 0, index);
            }
            return os.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * 写文件
     *
     * @param path
     * @param data
     */
    public static void writeBuffer(String path, byte[] data) {
        BufferedOutputStream bos = null;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bos.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @param path
     * @param data
     * @param charsetName
     */
    public static void writeBuffer(String path, String data, String charsetName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(path);
            bos = new BufferedOutputStream(fos);
            bos.write(data.getBytes(charsetName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.flush();
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 追加写入数据
     *
     * @param file
     * @param data
     */
    public static void writeAppend(String file, String data) {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(data + "\r\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
