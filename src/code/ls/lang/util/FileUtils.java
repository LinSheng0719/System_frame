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

/**
 * @author linsheng
 * @version V1.0
 * @Title: FileUtils.java
 * @Package com.ls.test.utils
 * @Description:
 * @email hi.linsheng@foxmail.com
 * @date 2015年11月11日 下午2:32:41
 */
public class FileUtils {

    public static final String ENCODING_UTF8 = "UTF-8";

    public static final String ENCODING_UNICODE = "Unicode";

    public static final String ENCODING_UTF16BE = "UTF-16BE";

    public static final String ENCODING_GBK = "GBK";

    /**
     * @param @param path
     * @return String
     * @Title:formatFilePath
     * @Description:格式化文件路径
     */
    public static String formatFilePath(String path) {
        return Paths.get(path).toString();
    }

    /**
     * @param @param  file
     * @param @param  encoding
     * @param @throws UnsupportedEncodingException
     * @param @throws FileNotFoundException
     * @return BufferedReader
     * @Title:newBufferedReader
     * @Description:新建bufferedReader
     */
    private static BufferedReader newBufferedReader(File file, String encoding) throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding));
    }

    private static BufferedReader newBufferedReader(File file) throws UnsupportedEncodingException, FileNotFoundException {
        return new BufferedReader(new InputStreamReader(new FileInputStream(file), ENCODING_UTF8));
    }

    /**
     * @param @param  file
     * @param @param  encoding
     * @param @param  append
     * @param @throws IOException
     * @return BufferedWriter
     * @Title:newBufferedWriter
     * @Description:新建BufferedWriter
     */
    private static BufferedWriter newBufferedWriter(File file, String encoding, boolean append) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), encoding));
    }

    private static BufferedWriter newBufferedWriter(File file, boolean append) throws IOException {
        return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, append), ENCODING_UTF8));
    }

    /**
     * @param @param  file
     * @param @throws IOException
     * @return String
     * @Title:getFileEncoding
     * @Description:获取文件编码
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
        if (bis != null)
            bis.close();
        return encoding;
    }

    /**
     * @param @param   file
     * @param @returnS
     * @param @throws  IOException
     * @return List<String>
     * @Title:readTextFileOutList
     * @Description:读取小文件文本，返回List
     */
    public static List<String> readTextFileOutList(File file) throws IOException {
        try (BufferedReader reader = newBufferedReader(file)) {
            return setDataList(reader);
        }
    }

    /**
     * @param @param  file
     * @param @param  encoding
     * @param @throws IOException
     * @return List<String>
     * @Title:readTextFileOutList
     * @Description:读取返回List
     */
    public static List<String> readTextFileOutList(File file, String encoding) throws IOException {
        try (BufferedReader reader = newBufferedReader(file, encoding)) {
            return setDataList(reader);
        }
    }

    private static List<String> setDataList(BufferedReader reader) throws IOException {
        List<String> result = new ArrayList<>();
        String line = null;
        while (null != (line = reader.readLine())) {
            result.add(line);
        }
        return result;
    }

    /**
     * @param @param  file
     * @param @return
     * @param @throws IOException
     * @return String
     * @Title:readTextFileOutString
     * @Description:读取小文本，返回String
     */
    public static String readTextFileOutString(File file) throws IOException {
        try (BufferedReader reader = newBufferedReader(file)) {
            return setDataString(reader);
        }
    }

    /**
     * @param @param  file
     * @param @param  encoding
     * @param @throws IOException
     * @return String
     * @Title:readTextFileOutString
     * @Description:读取返回String
     */
    public static String readTextFileOutString(File file, String encoding) throws IOException {
        try (BufferedReader reader = newBufferedReader(file, encoding)) {
            return setDataString(reader);
        }
    }

    private static String setDataString(BufferedReader reader) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line = null;
        while (null != (line = reader.readLine())) {
            sb.append(line);
        }
        return sb.toString();
    }

    /**
     * @param @param  is
     * @param @return
     * @param @throws IOException
     * @return byte[]
     * @Title:readStream
     * @Description: 获取文件字节流
     */
    public static byte[] readFileOutByte(File file) throws IOException {
        return readFileOutByte(new FileInputStream(file));
    }

    /**
     * @param @param  is
     * @param @return
     * @param @throws IOException
     * @return byte[]
     * @Title:readFileOutByte
     * @Description:获取文件字节流
     */
    public static byte[] readFileOutByte(InputStream is) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(is)) {
            byte[] b = new byte[bis.available()];
            bis.read(b, 0, b.length);
            return b;
        }
    }


    /**
     * @param @param  sourceFile
     * @param @param  targetFile
     * @param @return
     * @param @throws IOException
     * @return boolean
     * @Title:writeFileByteByFile
     * @Description:文件写入文件
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

    public static boolean writeFileByteByFile(String sourceFile, String targetFile) throws IOException {
        return writeFileByteByFile(new File(sourceFile), new File(targetFile));
    }

    /**
     * @param @param  temp
     * @param @param  file
     * @param @return
     * @param @throws IOException
     * @return boolean
     * @Title:writeFileByteByByte
     * @Description: 字节写入文件
     */
    public static boolean writeFileByteByByte(byte[] temp, File file) throws IOException {

        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
            bos.write(temp, 0, temp.length);
            bos.flush();
            return true;
        }
    }

    /**
     * @param @param  content
     * @param @param  file
     * @param @param  encoding
     * @param @throws IOException
     * @return boolean
     * @Title:wrieteTextAppend
     * @Description:追加方式添加文件
     */
    public static boolean writeTextAppend(String content, File file, String encoding) throws IOException {
        return writeTextFile(content, file, encoding, true);
    }

    public static boolean writeTextAppend(String content, File file) throws IOException {
        return writeTextFile(content, file, true);
    }

    /**
     * @param @param  content
     * @param @param  file
     * @param @param  encoding
     * @param @throws IOException
     * @return boolean
     * @Title:wrieteTextNew
     * @Description:覆盖方式添加文件
     */
    public static boolean writeTextNew(String content, File file, String encoding) throws IOException {
        return writeTextFile(content, file, encoding, false);
    }

    public static boolean writeTextNew(String content, File file) throws IOException {
        return writeTextFile(content, file, false);
    }

    /**
     * @param @param  content
     * @param @param  file
     * @param @param  append
     * @param @return
     * @param @throws IOException
     * @return boolean
     * @Title:writeTextFile
     * @Description:文本写入文本文件
     */
    public static boolean writeTextFile(String content, File file, String encoding, boolean append) throws IOException {

        try (BufferedWriter bufferWritter = newBufferedWriter(file, encoding, append)) {
            return writeText(bufferWritter, content);
        }
    }

    public static boolean writeTextFile(String content, File file, boolean append) throws IOException {
        try (BufferedWriter bufferWritter = newBufferedWriter(file, append)) {
            return writeText(bufferWritter, content);
        }
    }

    private static boolean writeText(BufferedWriter bufferWritter, String content) throws IOException {
        bufferWritter.write(content);
        bufferWritter.flush();
        return true;
    }

    /**
     * @param @param  dir
     * @param @throws IOException
     * @return boolean
     * @Title:createDir
     * @Description:创建文件夹
     */
    public static boolean createDir(File dir) throws IOException {

        if (!dir.exists()) {
            return dir.mkdirs();
        }
        return true;
    }

    /**
     * @param @param file
     * @return void
     * @throws IOException
     * @Title:createFile
     * @Description:创建文件
     */
    public static boolean createFile(File file) throws IOException {

        if (!file.exists()) {
            if (createDir(file.getParentFile()))
                return file.createNewFile();
            return false;
        }
        return true;
    }


    /**
     * @param @param file
     * @return boolean
     * @Title:deleteFolder
     * @Description:删除文件夹
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
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    /**
     * @param @param file
     * @param @param newName
     * @return boolean
     * @Title:reNameFile
     * @Description:重命名文件
     */
    public static boolean reNameFile(File file, String newName) {

        if (!file.exists())
            return false;
        File newFile = new File(file.getParent() + File.separator + newName);
        return moveFile(file, newFile);
    }

    /**
     * @param @param  source
     * @param @param  target
     * @param @return
     * @return boolean
     * @Title:moveFile
     * @Description:移动文件
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
     * @param @param  source
     * @param @param  target
     * @param @return
     * @param @throws IOException
     * @return boolean
     * @Title:copyFile
     * @Description:
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
     * @param @param  source
     * @param @param  target
     * @param @throws IOException
     * @return boolean
     * @Title:copyFile
     * @Description:复制文件
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
     * @param @param  sourceFolder
     * @param @param  targetFolder
     * @param @throws IOException
     * @return boolean
     * @Title:copyFolder
     * @Description:复制文件夹
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
     * @param @param  file
     * @param @return
     * @return long
     * @Title:getFolderLength
     * @Description:获取文件夹大小
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
     * @param @param  fileSize
     * @param @return
     * @return String
     * @Title:formatFileSize
     * @Description:文件大小格式转化
     */
    public static String formatFileSize(long fileSize) {
        StringBuffer sb = new StringBuffer();
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
     * @param @File imageFile
     * @return boolean
     * @throws
     * @MethodName: isImage
     * @Description: 判断图片
     */
    public static boolean isImg(InputStream in) {

        Image img = null;
        try {
            img = ImageIO.read(in);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0)
                return false;
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            img = null;
        }
    }

    public static boolean isImg(File file) {

        if (!file.exists())
            return false;

        Image img = null;
        try {
            InputStream in = new FileInputStream(file);
            img = ImageIO.read(in);
            in.close();
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0)
                return false;
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            img = null;
        }
    }
}
