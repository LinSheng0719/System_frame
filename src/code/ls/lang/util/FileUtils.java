package code.ls.lang.util;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;


public class FileUtils {

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String ENCODING_UNICODE = "Unicode";

    public static final String ENCODING_UTF16BE = "UTF-16BE";

    public static final String ENCODING_GBK = "GBK";

    /**
     * @param path 文件路径
     * @return 格式化的文件路径
     */
    public static String formatFilePath(String path) {
        return Paths.get(path).toString();
    }

    /**
     * @param file     读取的文件
     * @param encoding 读取的编码方式
     * @return BufferedReader   文件字符流
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     */
    private static BufferedReader newBufferedReader(File file, String encoding) throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
    }

    /**
     * @param file 读取的文件
     * @return BufferedReader 文件字符流，采用utf8的编码读取方式
     * @throws UnsupportedEncodingException
     * @throws FileNotFoundException
     * @see FileUtils#newBufferedReader(File, String)
     */
    private static BufferedReader newBufferedReader(File file) throws UnsupportedEncodingException, FileNotFoundException {
        return newBufferedReader(file, ENCODING_UTF8);
    }

    /**
     * @param file     写入的文件
     * @param encoding 写入的编码
     * @param append   是否以添加方式增加文件内容
     * @return 文件写入流
     * @throws IOException
     */
    private static BufferedWriter newBufferedWriter(File file, String encoding, boolean append) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encoding));
    }

    /**
     * 默认编码utf8
     *
     * @param file   写入的文件
     * @param append 是否以添加方式增加文件内容
     * @return 文件写入流
     * @throws IOException
     * @see FileUtils#newBufferedWriter(File, String, boolean)
     */
    private static BufferedWriter newBufferedWriter(File file, boolean append) throws IOException {
        return newBufferedWriter(file, ENCODING_UTF8, append);
    }

    /**
     * @param file 文件
     * @return String 文件的编码
     * @throws IOException
     */
    public static String getFileEncoding(File file) throws IOException {

        String encoding = null;
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        int p = (bis.read() << 8) + bis.read();
        switch (p) {
            case 0xefbb:
                encoding = ENCODING_UTF8;
                break;
            case 0xfffe:
                encoding = ENCODING_UNICODE;
                break;
            case 0xfeff:
                encoding = ENCODING_UTF16BE;
                break;
            default:
                encoding = ENCODING_GBK;
        }
        bis.close();
        return encoding;
    }

    /**
     * 读取文本<p>
     * 根据换行分割
     *
     * @param file 文件
     * @throws IOException
     */
    public static List<String> readTextFileOutList(File file) throws IOException {
        try (BufferedReader reader = newBufferedReader(file)) {
            return setDataList(reader);
        }
    }

    /**
     * @param file     文件
     * @param encoding 编码
     * @throws IOException
     */
    public static List<String> readTextFileOutList(File file, String encoding) throws IOException {
        try (BufferedReader reader = newBufferedReader(file, encoding)) {
            return setDataList(reader);
        }
    }

    /**
     * 根据<code>BufferReader</code>读取文本
     * <p>
     * 以换行分割
     *
     * @param reader 读取的<code>BufferdReader</code>
     * @throws IOException
     */
    private static List<String> setDataList(BufferedReader reader) throws IOException {
        List<String> result = new ArrayList<>();
        String line = null;
        while (null != (line = reader.readLine())) {
            result.add(line);
        }
        return result;
    }

    /**
     * 读取全部文本
     *
     * @param file 文件
     * @throws IOException
     */
    public static String readTextFileOutString(File file) throws IOException {
        try (BufferedReader reader = newBufferedReader(file)) {
            return setDataString(reader);
        }
    }

    /**
     * 根据编码读取文本
     *
     * @param file     文本
     * @param encoding 编码
     * @throws IOException
     */
    public static String readTextFileOutString(File file, String encoding) throws IOException {
        try (BufferedReader reader = newBufferedReader(file, encoding)) {
            return setDataString(reader);
        }
    }

    /**
     * 将读取流转换为文本
     *
     * @param reader 读取的文本流
     * @throws IOException
     */
    private static String setDataString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;
        while (null != (line = reader.readLine())) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * 以字节方式读取文件
     *
     * @param file 文件
     * @throws IOException
     */
    public static byte[] readFileOutByte(File file) throws IOException {
        return readFileOutByte(new FileInputStream(file));
    }

    /**
     * 以字节的方式读取流
     *
     * @param is 输入流
     * @throws IOException
     */
    public static byte[] readFileOutByte(InputStream is) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(is)) {
            byte[] b = new byte[bis.available()];
            int len = bis.read(b, 0, b.length);
            if (len == b.length)
                return b;
            return null;
        }
    }


    /**
     * 将文件内容写入另一个文件
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @throws IOException
     */
    public static boolean writeFileByteByFile(File sourceFile, File targetFile) throws IOException {

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile)); BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile))) {
            byte[] b = new byte[1024];
            while (bis.read(b, 0, b.length) != -1) {
                bos.write(b);
            }
            bos.flush();
        }
        return true;
    }

    /**
     * 将文件内容写入另一个文件
     *
     * @param sourceFile 源文件位置
     * @param targetFile 目标文件位置
     * @throws IOException
     */
    public static boolean writeFileByteByFile(String sourceFile, String targetFile) throws IOException {
        return writeFileByteByFile(new File(sourceFile), new File(targetFile));
    }

    /**
     * 将字节流写入文件
     *
     * @param bytes 字节流
     * @param file  目标文件
     * @throws IOException
     */
    public static boolean writeFileByteByByte(byte[] bytes, File file) throws IOException {

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(bytes, 0, bytes.length);
            bos.flush();
            return true;
        }
    }

    /**
     * 将文本以追加方式加入文本
     *
     * @param content  要追加的文本
     * @param file     目标文件
     * @param encoding 编码方式
     * @throws IOException
     */
    public static boolean writeTextAppend(String content, File file, String encoding) throws IOException {
        return writeTextFile(content, file, encoding, true);
    }

    /**
     * 将文本以追加方式加入文本,默认编码
     *
     * @param content 要追加的文本
     * @param file    目标文件
     * @throws IOException
     */
    public static boolean writeTextAppend(String content, File file) throws IOException {
        return writeTextFile(content, file, true);
    }

    /**
     * 将文本以覆盖方式加入文本
     *
     * @param content  要追加的文本
     * @param file     目标文件
     * @param encoding 编码方式
     * @throws IOException
     */
    public static boolean writeTextNew(String content, File file, String encoding) throws IOException {
        return writeTextFile(content, file, encoding, false);
    }

    /**
     * 将文本以覆盖方式加入文本，默认编码
     *
     * @param content 要追加的文本
     * @param file    目标文件
     * @throws IOException
     */
    public static boolean writeTextNew(String content, File file) throws IOException {
        return writeTextFile(content, file, false);
    }

    /**
     * 将文本加入文件
     *
     * @param content  加入的文本
     * @param file     目标文件
     * @param encoding 编码
     * @param append   是否追加
     * @throws IOException
     */
    public static boolean writeTextFile(String content, File file, String encoding, boolean append) throws IOException {
        try (BufferedWriter bufferedWriter = newBufferedWriter(file, encoding, append)) {
            return writeText(bufferedWriter, content);
        }
    }

    /**
     * 将文本加入文件,默认编码
     *
     * @param content 加入的文本
     * @param file    目标文件
     * @param append  是否追加
     * @throws IOException
     */
    public static boolean writeTextFile(String content, File file, boolean append) throws IOException {
        try (BufferedWriter bufferedWriter = newBufferedWriter(file, append)) {
            return writeText(bufferedWriter, content);
        }
    }

    /**
     * 文本写入写入流
     *
     * @param bufferWritter 写入流
     * @param content       文本
     * @throws IOException
     */
    private static boolean writeText(BufferedWriter bufferWritter, String content) throws IOException {
        bufferWritter.write(content);
        bufferWritter.flush();
        return true;
    }

    /**
     * 创建文件夹
     *
     * @param dir 文件夹
     * @throws IOException
     */
    public static boolean createDir(File dir) throws IOException {
        return dir.exists() || dir.mkdirs();
    }

    /**
     * 创建文件
     *
     * @param file 文件
     * @throws IOException
     */
    public static boolean createFile(File file) throws IOException {
        return file.exists() || createDir(file.getParentFile()) && file.createNewFile();
    }


    /**
     * 删除文件夹，及其包含的文件
     *
     * @param file 文件夹
     */
    public static boolean deleteFolder(File file) {

        File[] fileList = file.listFiles();
        if (fileList != null) {
            for (File f : fileList) {
                deleteFolder(f);
                if (!f.delete()) {
                    return false;
                }
            }
        }
        return file.exists() && file.delete();
    }

    /**
     * 重命名文件
     *
     * @param file    文件
     * @param newName 新名称
     */
    public static boolean reNameFile(File file, String newName) {

        if (!file.exists())
            return false;
        File newFile = new File(file.getParent() + File.separator + newName);
        return moveFile(file, newFile);
    }

    /**
     * 移动文件
     *
     * @param source 源文件
     * @param target 目标文件名
     */
    public static boolean moveFile(File source, File target) {
        if (!source.exists())
            return false;
        File parent = target.getParentFile();
        if (!parent.exists()) {
            if (parent.mkdirs()) {
                return source.renameTo(target);
            }
        }
        return false;
    }

    /**
     * 复制文件
     *
     * @param source 源文件
     * @param target 目标文件
     * @throws IOException
     */
    public static boolean copy(File source, File target) throws IOException {

        if (!source.exists())
            return false;
        if (target.exists())
            return false;

        if (source.isDirectory()) {
            return copyFolder(source, target);
        } else {
            return copyFile(source, target);
        }
    }

    /**
     * 复制单文件
     *
     * @param source 源文件
     * @param target 目标文件
     * @throws IOException
     */
    public static boolean copyFile(File source, File target) throws IOException {

        if (!source.exists())
            return false;
        if (target.exists())
            return false;

        try (FileInputStream fin = new FileInputStream(source); FileOutputStream fout = new FileOutputStream(target); FileChannel inc = fin.getChannel(); FileChannel outc = fout.getChannel()) {
            long size = getFolderLength(source);
            return outc.transferFrom(inc, 0, inc.size()) == size;
        }
    }

    /**
     * 复制文件夹
     *
     * @param sourceFolder 源文件夹
     * @param targetFolder 目标文件夹
     * @throws IOException
     */
    public static boolean copyFolder(File sourceFolder, File targetFolder) throws IOException {

        if (!sourceFolder.exists() || !sourceFolder.isDirectory())
            return false;
        createDir(targetFolder);

        File[] fileList = sourceFolder.listFiles();
        if (fileList == null)
            return true;

        for (File file : fileList) {
            File targetFile = new File(targetFolder.getAbsoluteFile() + File.separator + file.getName());
            if (file.isDirectory()) {
                copyFolder(file, targetFile);
            } else {
                copyFile(file, targetFile);
            }
        }
        return true;
    }

    /**
     * 获取文件或者文件夹大小
     *
     * @param file 文件或者文件夹
     * @return <code>long</code>类型值，计算的是文件的字节长度
     */
    public static long getFolderLength(File file) {

        long totalLength = file.length();
        File[] fileList = file.listFiles();
        if (fileList != null) {
            for (File f : fileList) {
                totalLength += getFolderLength(f);
            }
        }
        return totalLength;
    }

    /**
     * 文件大小格式转化
     * <p>
     * 最小单位为B，最大为GB
     * <p>
     * 单位从小到达有  B   KB  MB  GB
     *
     * @param fileSize 文件大小
     */
    public static String formatFileSize(long fileSize) {
        StringBuilder sb = new StringBuilder();
        DecimalFormat df = new DecimalFormat("0.00");
        if (fileSize < 1024) {
            sb.append(fileSize);
            sb.append("B");
        } else if (1048576 > fileSize && fileSize >= 1024) {
            Double num = fileSize / 1024.0;
            sb.append(df.format(num));
            sb.append("KB");
        } else if (1024 * 1048576 > fileSize && fileSize >= 1048576) {
            Double num = fileSize / (1024 * 1024.0);
            sb.append(df.format(num));
            sb.append("MB");
        } else {
            Double num = fileSize / (1048576 * 1024.0);
            sb.append(df.format(num));
            sb.append("GB");
        }
        return sb.toString();
    }

    /**
     * 判断输入流是不是图片类型
     *
     * @param in 输入流
     * @return true 是图片
     * <p>
     * false 不是图片
     */
    public static boolean isImg(InputStream in) {

        Image img;
        try {
            img = ImageIO.read(in);
            return !(img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0);
        } catch (IOException e) {
            return false;
        } finally {
            img = null;
        }
    }

    /**
     * 判断文件是不是图片
     *
     * @param file 文件
     */
    public static boolean isImg(File file) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            return isImg(in);
        }
    }
}
