package com.yangyang.whatcanieat.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class FtpUtil {

    @Value("${ftp.host}")
    private String host;

    @Value("${ftp.port}")
    private int port;

    @Value("${ftp.username}")
    private String username;

    @Value("${ftp.password}")
    private String password;

    /**
     * 上传文件到 FTP 指定路径
     * @param remotePath 远程路径，例如 "/upload/images/2026/03"
     * @param fileName 文件名
     * @param input 文件输入流
     * @return 上传是否成功
     */
    public boolean uploadFile(String remotePath, String fileName, InputStream input) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(host, port);
            boolean login = ftpClient.login(username, password);
            if (!login) {
                return false;
            }

            ftpClient.enterLocalPassiveMode(); // 被动模式
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // 二进制文件

            // 如果目录不存在则创建
            if (!ftpClient.changeWorkingDirectory(remotePath)) {
                String[] dirs = remotePath.split("/");
                String tempPath = "";
                for (String dir : dirs) {
                    if (dir.isEmpty()) continue;
                    tempPath += "/" + dir;
                    if (!ftpClient.changeWorkingDirectory(tempPath)) {
                        ftpClient.makeDirectory(tempPath);
                        ftpClient.changeWorkingDirectory(tempPath);
                    }
                }
            }

            boolean result = ftpClient.storeFile(fileName, input);
            input.close();
            ftpClient.logout();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}