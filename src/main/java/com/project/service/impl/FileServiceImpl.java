package com.project.service.impl;

import com.project.model.RespEntity;
import com.project.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;

/**
 * Created by laishun on 2018/3/12.
 */
@Service("imageService")
public class FileServiceImpl implements FileService {

    private final static String DIRECTORY = "/mnt/pic/";
    private final static String URL = "http://47.100.46.80/img/";

    @Resource
    private RespEntity respEntity ;

    /**
     * 上传图片
     * @param request
     * @return
     */
    @Override
    public Object fileUpload(HttpServletRequest request){
        int code = 200;
        String msg = "上传成功";
        List<String> list = new ArrayList<>();
        try {
            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
            Iterator<String> iterator = req.getFileNames();
            while (iterator.hasNext()) {
                MultipartFile multipart = req.getFile(iterator.next());
                String fileOriginName = multipart.getOriginalFilename();
                int split = fileOriginName.lastIndexOf(".");
                String suffix = fileOriginName.substring(split);
                String newFileName = new Date().getTime() + "_" + new Random().nextInt(1000) + suffix;
                File file_path = new File(DIRECTORY+newFileName);
                if(file_path.exists()){
                   file_path.delete();
                }
                //上传文件
                multipart.transferTo(file_path);
                list.add(URL+newFileName);
            }
            respEntity.setData(list);
            if(list.size() == 0){
                code = 500;
                msg = "没有传入数据";
                respEntity.setData(null);
            }
        }catch (Exception e){
            e.printStackTrace();
            code = 500;
            msg = "服务器内容异常";
            respEntity.setData(null);
        }
        respEntity.setCode(code);
        respEntity.setMsg(msg);
        return respEntity;
    }

    /**
     * 处理大文件
     * @param path
     * @param file
     */
    public void uploadSingleFile(File path,MultipartFile file) {
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(path));
            int length = 0;
            byte[] buffer = new byte[1024];
            InputStream inputStream = file.getInputStream();
            while ((length = inputStream.read(buffer)) != -1){
                stream.write(buffer, 0, length);
            }
            stream.flush();
            stream.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
