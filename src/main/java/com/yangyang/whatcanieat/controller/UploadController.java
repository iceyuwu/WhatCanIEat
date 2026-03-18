package com.yangyang.whatcanieat.controller;

import com.yangyang.whatcanieat.entity.Recipe;
import com.yangyang.whatcanieat.entity.Result;
import com.yangyang.whatcanieat.service.RecipeService;
import com.yangyang.whatcanieat.util.FtpUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/upload")
public class UploadController {

    //上传图片保存的根路径 在配置文件中配置
//    @Value("${file.upload-path}")
//    private String UPLOAD_PATH;

    @Resource
    private RecipeService recipeService;
    @Resource
    private FtpUtil ftpUtil;

    /**
     * 上传图片接口
     * 上传的图片将保存为“根路径/recipe/用户账号/菜谱id/uuid_时间戳.jpg”
     *
     * @param file     图片文件
     * @param recipeId 菜谱id
     * @return 响应
     * @throws IOException
     */
    @PostMapping("/image/recipe/cover")
    public Result<Recipe> uploadRecipeCover(@RequestParam("file") MultipartFile file,
                                            @RequestParam Long recipeId) throws IOException {
        /*
        步骤一：生成路径名
        格式：根目录（UPLOAD_PATH）/用户账号/菜谱id/
         */
        // 获取用户账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String account = authentication.getName();

        // 获取原始文件名
        String originalName = file.getOriginalFilename();

        // 拼接完整路径
        String remotePath = "pic/recipe/" + account + "/" + recipeId + "/";
        /*
        步骤二：生成文件名
       格式为：随机文件名（uuid） + 时间戳 + 文件扩展名（jpg/png等等）
       */
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis();
        String suffix = originalName.substring(originalName.lastIndexOf("."));
        String fullFileName = uuid + "_" + timestamp + suffix;

         /*
        步骤三：上传文件
        */
//        File dest = new File(UPLOAD_PATH + remotePath + fullFileName);
//        if (!dest.getParentFile().exists()) {
//            dest.getParentFile().mkdirs();
//        }
//        file.transferTo(dest);
        boolean success = ftpUtil.uploadFile(remotePath, fullFileName, file.getInputStream());
        if (!success){
            return Result.fail("上传失败");
        }

       /*
       步骤四：记录数据库
       统一数据库路径分隔符为 "/"
        */
        Recipe recipe = recipeService.getById(recipeId);
        // 替换 Windows 的反斜杠
        String dbPath = (remotePath + fullFileName).replace("\\", "/");
        recipe.setCover(dbPath);
        recipeService.updateById(recipe);

        return Result.ok(recipe, "上传成功");
    }

    @PostMapping("/test")
    public Result<Recipe> uploadRecipeCover(@RequestParam("file") MultipartFile file) throws IOException {

        // 拼接完整路径
        String remotePath = "test/";
        /*
        步骤二：生成文件名
       格式为：随机文件名（uuid） + 时间戳 + 文件扩展名（jpg/png等等）
       */
        // 获取原始文件名
        String originalName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        long timestamp = System.currentTimeMillis();
        String suffix = originalName.substring(originalName.lastIndexOf("."));
        String fullFileName = uuid + "_" + timestamp + suffix;
        //上传
        boolean success = ftpUtil.uploadFile(remotePath, fullFileName, file.getInputStream());
        if (!success){
            return Result.fail("上传失败");
        }
        return Result.ok("上传成功");
    }
}
