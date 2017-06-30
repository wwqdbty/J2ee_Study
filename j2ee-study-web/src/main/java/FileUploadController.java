import com.kulang.study.domain.util.PropertiesUtil;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wenqiang.wang on 2017/3/21.
 */
public class FileUploadController {
    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    @ResponseBody
    public String uploadImage(@RequestParam(value = "file", required = false) MultipartFile uploadedFile) throws Exception {
        if (uploadedFile == null) {
            return "empty file";
        }

        // 文件上传至在服务器的根路径.
        // 微型项目, 应用服务器与文件服务器在一起, 此处配置的即为应用部署的文件路径, 上传完成后可通过服务器域名访问. 如tomcat目录为/export/uploadTest/, 则此处配置的跟路径即此
        // 存在单独文件服务器的项目, 则对根路径所在位置无要求, 因为无需使游览器可访问到.
        String strFileRootDir = PropertiesUtil.getProperty("file.rootDir");
        // 文件上传至服务器的相对路径, 相对根路径
        String strFileRelativelyDir = PropertiesUtil.getProperty("file.relativelyDir");
        // 文件名
        String strExtName = getExtName(uploadedFile.getOriginalFilename());
        String strFileName = new Date().getTime() + strExtName;;
        // 文件全限定名
        String strFileFullyQualifiedName = strFileRootDir + strFileRelativelyDir + strFileName;
        File file = new File(strFileFullyQualifiedName);
        // 存在文件则删除
        if (file.exists()) {
            file.delete();
        }
        // 文件校验
        String strValidateResult = validate(uploadedFile);
        if (strValidateResult != null) {
            return strValidateResult;
        }

        // 保存文件
        boolean bUploadResult = saveFile(uploadedFile.getInputStream(), strFileRootDir + strFileRelativelyDir, strFileName);

        return bUploadResult ? "SUCCESS" : "FAILED";
    }

    /**
     * 文件校验
     * @param file
     * @return
     */
    private String validate(MultipartFile file) {
        // 文件大小校验
        if (file.getSize() > 1024000) {
            return "sizetoolarge";
        }
        // 文件格式校验
        String extName = getExtName(file.getOriginalFilename());
        if (StringUtils.isEmpty(extName)) {
            return "patternerror";
        }
        extName = extName.toLowerCase();
        if (!extName.equals(".png") && extName.equals(".jpg") && extName.equals(".jpeg") && extName.equals(".gif")) {
            return "patternerror";
        }

        return null;
    }

    /**
     * 获取文件后缀名
     * @param strFileName
     * @return
     */
    public String getExtName(String strFileName) {
        if (StringUtils.isEmpty(strFileName)) {
            return "";
        }

        int begin = strFileName.lastIndexOf(".");
        String extName = strFileName.substring(begin);
        return extName;
    }

    /**
     * 保存文件 - 最老式的做法
     *
     * @param stream 输入流对象
     * @param strFileAbsolutelyDir 保存至路径
     * @param strFileName 保存的文件的文件名
     * @throws IOException
     */
    private boolean saveFile(InputStream stream, String strFileAbsolutelyDir, String strFileName) {
        if (stream == null || StringUtils.isEmpty(strFileAbsolutelyDir) || StringUtils.isEmpty(strFileName)) {
            return false;
        }

        // 添加日期目录
        Calendar calendar = Calendar.getInstance();
        strFileAbsolutelyDir = strFileAbsolutelyDir + calendar.get(Calendar.YEAR) + File.separator + calendar.get(Calendar.MONTH) + File.separator + calendar.get(Calendar.DAY_OF_MONTH) + File.separator;
        // 不存在目录则创建
        File f = new File(strFileAbsolutelyDir);
        if (!f.exists()) {
            f.mkdirs();
        }

        boolean bUploadResult = true;
        // 文件全限定名
        String strFileFullyQualifiedName = strFileAbsolutelyDir + strFileName;
        FileOutputStream fs = null;
        try {
            fs = new FileOutputStream(strFileFullyQualifiedName);
            byte[] buffer = new byte[1024*1024];
            int byteRead = 0;
            while ((byteRead = stream.read(buffer)) != -1) {
                byteRead += byteRead;
                fs.write(buffer, 0, byteRead);
                fs.flush();
            }
        } catch (Exception e) {
            bUploadResult = false;
            e.printStackTrace();
        } finally {
            try {
                fs.close();
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bUploadResult;
    }

    /**
     * SpringMVC 提供的上传
     * @param file
     * @param strFileAbsolutelyDir
     * @return
     * @throws IOException
     */
    public boolean saveFile(MultipartFile file, String strFileAbsolutelyDir, String strFileName) throws IOException {
        if (file == null || StringUtils.isEmpty(strFileAbsolutelyDir) || StringUtils.isEmpty(strFileName)) {
            return false;
        }

        // 添加日期目录
        Calendar calendar = Calendar.getInstance();
        strFileAbsolutelyDir = strFileAbsolutelyDir + calendar.get(Calendar.YEAR) + File.separator + calendar.get(Calendar.MONTH) + File.separator + calendar.get(Calendar.DAY_OF_MONTH) + File.separator;
        File fileAbsolutelyDir = new File(strFileAbsolutelyDir);
        if (!fileAbsolutelyDir.exists()) {
            fileAbsolutelyDir.mkdirs();
        }

        // 文件全限定名
        String strFileFullyQualifiedName = strFileAbsolutelyDir + strFileName;
        try {
            FileCopyUtils.copy(file.getBytes(), new File(strFileFullyQualifiedName));
        } catch (Exception e) {
            e.printStackTrace();

            return false;
        }

        return true;
    }

    @Test
    public void test() {
        Calendar calendar = Calendar.getInstance();

        String s = calendar.get(Calendar.YEAR) + File.separator + calendar.get(Calendar.MONTH) + File.separator + calendar.get(Calendar.DAY_OF_MONTH) + File.separator;

        System.out.println(s);
    }
}
