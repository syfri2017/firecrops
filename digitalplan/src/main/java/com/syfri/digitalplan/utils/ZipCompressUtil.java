package com.syfri.digitalplan.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipFile;

/**
 * @Description:压缩工具类
 * @Author: lixiaoyang
 * @Modified By:
 * @Date: 2018/4/20 13:25
 */
public class ZipCompressUtil {

    private String zipFileName;      // 目的地Zip文件
    private String sourceFileName;   //源文件（带压缩的文件或文件夹）

    public ZipCompressUtil(String zipFileName,String sourceFileName)
    {
        this.zipFileName=zipFileName;
        this.sourceFileName=sourceFileName;
    }

    public void zip() throws Exception
    {
        //File zipFile = new File(zipFileName);
        /*System.out.println("压缩中...");*/

        //创建zip输出流
        ZipOutputStream out = new ZipOutputStream( new FileOutputStream(zipFileName));

        //创建缓冲输出流
        BufferedOutputStream bos = new BufferedOutputStream(out);

        File sourceFile = new File(sourceFileName);

        //调用函数
        compress(out,bos,sourceFile,sourceFile.getName());

        bos.close();
        out.close();
        /*System.out.println("压缩完成");*/

    }

    public void compress(ZipOutputStream out,BufferedOutputStream bos,File sourceFile,String base) throws Exception
    {
        //如果路径为目录（文件夹）
        if(sourceFile.isDirectory())
        {

            //取出文件夹中的文件（或子文件夹）
            File[] flist = sourceFile.listFiles();

            if(flist.length==0)//如果文件夹为空，则只需在目的地zip文件中写入一个目录进入点
            {
                /*System.out.println(base+"/");*/
                out.putNextEntry(  new ZipEntry(base+"/") );
            }
            else//如果文件夹不为空，则递归调用compress，文件夹中的每一个文件（或文件夹）进行压缩
            {
                for(int i=0;i<flist.length;i++)
                {
                    compress(out,bos,flist[i],base+"/"+flist[i].getName());
                }
            }
        }
        else//如果不是目录（文件夹），即为文件，则先写入目录进入点，之后将文件写入zip文件中
        {
            out.putNextEntry( new ZipEntry(base) );
            FileInputStream fos = new FileInputStream(sourceFile);
            BufferedInputStream bis = new BufferedInputStream(fos);

            int tag;
            System.out.println(base);
            //将源文件写入到zip文件中
            while((tag=bis.read())!=-1)
            {
                bos.write(tag);
            }
            bis.close();
            fos.close();

        }
    }

    /**
     * 解压到指定目录
     *
     * @param zipPath
     * @param descDir
     */
    public static void unZipFiles(String zipPath, String descDir) throws IOException {
        unZipFiles(new File(zipPath), descDir);
    }

    /**
     * 解压文件到指定目录 解压后的文件名，和之前一致
     *测试数据：unZipFiles("D:\\ideaSpace\\物美生活广场及地铁华苑站三维灭火预案.zip", "D:\\ideaSpace\\zipPath\\");
     * @param zipFile
     *            待解压的zip文件
     * @param descDir
     *            指定目录
     */
    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        @SuppressWarnings("resource")
        ZipFile zip = new ZipFile(zipFile, Charset.forName("GBK"));// 解决中文文件夹乱码

        String name  = "";
        if(zip.getName().contains("\\")){
            name = zip.getName().substring(zip.getName().lastIndexOf('\\') + 1, zip.getName().lastIndexOf('.'));
        }else{
            name = zip.getName().substring(zip.getName().lastIndexOf('/') + 1, zip.getName().lastIndexOf('.'));
        }

        File pathFile = new File(descDir + name);
        if (!pathFile.exists()) {
            pathFile.mkdirs();
        }

        for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
            ZipEntry entry = (ZipEntry) entries.nextElement();
            String zipEntryName = entry.getName();
            InputStream in = zip.getInputStream(entry);
            String outPath = (descDir + name + "/" + zipEntryName).replaceAll("\\*", "/");
            // 判断路径是否存在,不存在则创建文件路径
            File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
            if (!file.exists()) {
                file.mkdirs();
            }
            // 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
            if (new File(outPath).isDirectory()) {
                continue;
            }
            // 输出文件路径信息
            // System.out.println(outPath);

            FileOutputStream out = new FileOutputStream(outPath);
            byte[] buf1 = new byte[1024];
            int len;
            while ((len = in.read(buf1)) > 0) {
                out.write(buf1, 0, len);
            }
            in.close();
            out.close();
        }
        return;
    }




        public static void main(String[] args)
        {
            ZipCompressUtil zipCom = new ZipCompressUtil("D:\\电影.zip","F:\\电影");
            try
            {
                zipCom.zip();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

}

