package com.lims.common.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

/**
 * PDF生成工具类
 *
 * @author KING
 */
public class PDFUtils {

    /**
     * 利用模板生成pdf
     *
     * @param inputStream
     * @param param
     * @return
     */
    public static int fillTemplate(InputStream inputStream, Map<String, String> param) {
        String newPDFPath = param.get("filePath") + param.get("name") + ".pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            // 输出流
            out = new FileOutputStream(newPDFPath);
            reader = new PdfReader(inputStream);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);

            AcroFields form = stamper.getAcroFields();
            Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                form.setField(name, param.get(name));
                // form.setField(name, "1");
            }
            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        } catch (DocumentException e) {
            e.printStackTrace();
            return 2;
        }

    }

    public static byte[] fillTemplateWithImageAndTxt(InputStream input, Map<String, String> param) throws Exception {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 读取模板文件
            PdfReader reader = new PdfReader(input);
            PdfStamper stamper = new PdfStamper(reader, bos);
            // 提取pdf中的表单
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            // 填充第一个图片
            if (StringUtils.isNotBlank(param.get("fileName1"))) {
                // 图片1
                String fieldName1 = "picture1";
                // 图片路径
                String imagePath1 = param.get("img1");
                // 通过域名获取所在页和坐标，左下角为起点
                int pageNo1 = form.getFieldPositions(fieldName1).get(0).page;
                Rectangle signRect1 = form.getFieldPositions(fieldName1).get(0).position;
                float x1 = signRect1.getLeft();
                float y1 = signRect1.getBottom();

                // 读图片
                Image image1 = Image.getInstance(imagePath1);
                // 获取操作的页面
                PdfContentByte under1 = stamper.getOverContent(pageNo1);
                // 根据域的大小缩放图片
                image1.scaleToFit(signRect1.getWidth(), signRect1.getHeight());
                // 添加图片
                image1.setAbsolutePosition(x1, y1);
                under1.addImage(image1);
            }
            // 填充第一个图片
            if (StringUtils.isNotBlank(param.get("fileName2"))) {
                // 图片2
                String fieldName2 = "picture2";
                String imagePath2 = param.get("img2");

                int pageNo2 = form.getFieldPositions(fieldName2).get(0).page;
                Rectangle signRect2 = form.getFieldPositions(fieldName2).get(0).position;
                float x2 = signRect2.getLeft();
                float y2 = signRect2.getBottom();
                // 读图片
                Image image2 = Image.getInstance(imagePath2);
                // 获取操作的页面
                PdfContentByte under2 = stamper.getOverContent(pageNo2);
                // 根据域的大小缩放图片
                image2.scaleToFit(signRect2.getWidth(), signRect2.getHeight());
                // 添加图片
                image2.setAbsolutePosition(x2, y2);
                under2.addImage(image2);
            }
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                form.setField(name, param.get(name));
            }
            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] exportbyte(InputStream input, Map<String, String> param) throws Exception {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            // 读取模板文件
            PdfReader reader = new PdfReader(input);
            PdfStamper stamper = new PdfStamper(reader, bos);
            // 提取pdf中的表单
            AcroFields form = stamper.getAcroFields();
            form.addSubstitutionFont(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED));
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                form.setField(name, param.get(name));
            }
            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
            return bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getName();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + System.currentTimeMillis() + fileName
            .substring(fileName.indexOf("."), fileName.length()));
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

    public static ResponseEntity<FileSystemResource> export(File file, String fileName) {
        if (file == null) {
            return null;
        }
        String fn = null;
        try {
            fn = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + fn);
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity.ok().headers(headers).contentLength(file.length())
            .contentType(MediaType.parseMediaType("application/octet-stream")).body(new FileSystemResource(file));
    }

}