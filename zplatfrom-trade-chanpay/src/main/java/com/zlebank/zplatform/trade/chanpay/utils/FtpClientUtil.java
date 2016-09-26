package com.zlebank.zplatform.trade.chanpay.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpClient;

public class FtpClientUtil {
	
	/**
	 * FTP客户端，实现从服务器上下载文件。
	 */
	private FTPClient ftp;

	private String userName;
	private String password;
	private String hostIp;
	private int port;
	
	protected static Logger logger = LoggerFactory.getLogger(FtpClient.class);

	public FtpClientUtil() {
		ftp = new FTPClient();
	}

	public FtpClientUtil(String hostIp, int port, String userName, String password) {
		this.hostIp = hostIp;
		this.port = port;
		this.userName = userName;
		this.password = password;
		ftp = new FTPClient();
	}

	/**
	 * 从FTP服务器下载文件
	 * 
	 * @param localFile
	 * @param remoteFile
	 * @throws Exception
	 */
	public void download(String remoteFile, String localFile) throws Exception {
		// login();
		downloadFile(remoteFile, localFile);
		// logout();
	}

	/**
	 * 从FTP服务器下载文件
	 * 
	 * @param remoteFile
	 * @param os
	 * @throws Exception
	 */
	public void download(String remoteFile, OutputStream os) throws Exception {
		// login();
		getFile(remoteFile, os);
		// logout();
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param remoteFile
	 * @param is
	 * @throws IOException
	 */
	public void upload(String remoteFile, InputStream is) throws IOException {
		storeFile(remoteFile, is);
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param localFile
	 * @param remoteFile
	 * @throws IOException
	 */
	public void upload(String localFile, String remoteFile) throws IOException {
		storeFile(remoteFile, new BufferedInputStream(new FileInputStream(localFile)));
	}

	/**
	 * 在FTP服务器上创建目录
	 * 
	 * @param dir
	 * @throws IOException
	 */
	public void mkDirectory(String dir) throws IOException {
		ftp.makeDirectory(dir);
	}

	/**
	 * 把读取的输入流，存入远程文件中
	 * 
	 * @param remoteFile
	 *            远程文件的全路径，包括文件名称，如(/export/home/sa/test.txt)
	 * @param is
	 *            文件输入流
	 * @return 如果上传文件成功则返回true,否则为false
	 */
	public boolean storeFile(String remoteFile, InputStream is) {
		try {
			int lastIndex = remoteFile.lastIndexOf("/");
			if (lastIndex < 0) {
				logger.info("the remote file path is relative to ftp user home path!");

			} else {
				String path = remoteFile.substring(0, lastIndex + 1);
				logger.info("the remote file path is:" + path);
				boolean isMade = ftp.makeDirectory(path);
				logger.info("the make directory operation is:" + isMade);
			}

			ftp.storeFile(remoteFile, is);
		} catch (IOException e) {
			logger.info(e.getMessage());
			return false;
		}
		return true;
	}

	private OutputStream getFile(String remoteFile, OutputStream os) throws IOException {
		// 下载文件
		boolean reply = ftp.retrieveFile(remoteFile, os);
		if (reply) {
			logger.info("Download OK.");
		} else {
			logger.info("Download failed.");
		}
		return os;
	}

	private void downloadFile(String remoteFile, String localFile) throws IOException {
		File localF = null;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try {
			logger.info("Downloading file from " + remoteFile);
			// 下载文件
			localF = new File(localFile);
			logger.info("To local file: " + localF.getAbsolutePath());
			fos = new FileOutputStream(localF);
			bos = new BufferedOutputStream(fos);
			getFile(remoteFile, bos);
		} finally {
			IOUtils.closeQuietly(bos);
			IOUtils.closeQuietly(fos);
		}
	}

	/**
	 * 连接并登录FTP服务器
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @throws Exception
	 */
	public void login() throws Exception {
		try {
			ftp.connect(hostIp, port);
		} catch (IOException e) {
			StringBuffer msg = new StringBuffer();
			msg.append("Cannot connect to ftp server '");
			msg.append(hostIp + ":" + port).append("', cause by ");
			msg.append(e.getMessage());
			throw new Exception(msg.toString(), e);
		}

		// 执行登录，如果登录失败则抛出异常
		StringBuffer loginFailureMsg = new StringBuffer();
		loginFailureMsg.append("Cannot login ftp server '");
		loginFailureMsg.append(hostIp + ":" + port);
		loginFailureMsg.append("', username or password is wrong.");
		try {
			if (!ftp.login(userName, password)) {
				throw new Exception(loginFailureMsg.toString());
			}
		} catch (IOException e) {
			throw new Exception(loginFailureMsg.toString(), e);
		}

		// 设置FTP属性和模式，服务器不支持则忽略
		try {
			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
		} catch (Exception e) {
			logger.info("Set ftp parameters to ftp server '" + hostIp + ":" + port + "' failed, ignored.");
		}
	}

	/**
	 * 注销登录，并断开到FTP服务器的网络连接
	 */
	public void logout() {
		if (ftp != null) {
			try {
				ftp.logout();
			} catch (Exception e) {
				// 安全的忽略该异常
			}
			try {
				ftp.disconnect();
			} catch (Exception e) {
				// 安全的忽略该异常
			}
		}
	}

	/**
	 * 列出FTP服务器，指定目录下的所有文件的文件名。
	 * 
	 * @param dir
	 *            FTP目录的路径。
	 * @return 保存文件名的数组。
	 * @throws Exception
	 *             如果在访问FTP服务器的过程中，发生网络中断等异
	 *             常情况，那么将抛出该异常。
	 */
	public String[] listFileNames(String dir) throws Exception {
		try {
			String[] retVal = ftp.listNames(dir);
			if (retVal == null) {
				retVal = new String[0];
			}
			return retVal;
		} catch (IOException e) {
			StringBuffer msg = new StringBuffer();
			msg.append("Cannot list directory '").append(dir);
			msg.append("', cause by:").append(e.getMessage());
			throw new Exception(msg.toString(), e);
		}
	}

	public String getHostName() {
		return hostIp;
	}

	public void setHostName(String hostName) {
		this.hostIp = hostName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取FTP客户端，实现从服务器上下载文件。<p>
	 * @return  ftp  FTP客户端，实现从服务器上下载文件。<br>
	 */
	protected FTPClient getFtp() {
		return ftp;
	}

	public static void main(String[] args) {
		FtpClientUtil ftpClient = new FtpClientUtil("133.40.16.253", 20136, "polling", "zznode");
		try {
			ftpClient.login();
			ftpClient.download("D:\\product\\db2file\\temp", "/home/polling/report/ss.txt");
			ftpClient.logout();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

	public void test1() throws Exception {
		//
		String strTemp = "";

		// InetAddress ia = InetAddress.getByName("192.168.0.193");
		FTPClient ftp = new FTPClient();
		ftp.connect("172.16.0.142", 22);

		boolean blogin = ftp.login("ivr", "ivr");
		if (!blogin) {
			System.out.println("连接失败");
			ftp.disconnect();
			ftp = null;
			return;
		}

		/*
		 * //如果是中文名必需进行字符集转换 boolean bMakeFlag = ftp.makeDirectory(new
		 * String("测试目录".getBytes( "gb2312"), "iso-8859-1")); //在服务器创建目录
		 * //上传文件到服务器，目录自由创建 File file = new File("c:\\test.properties");
		 * ftp.storeFile("test.properties",new FileInputStream(file));
		 */

		System.out.println(ftp.getSystemName());

		FTPFile[] ftpFiles = ftp.listFiles();
		if (ftpFiles != null) {
			for (int i = 0; i < ftpFiles.length; i++) {
				System.out.println(ftpFiles[i].getName());
				// System.out.println(ftpFiles[i].isFile());
				if (ftpFiles[i].isFile()) {
					FTPFile ftpf = new FTPFile();
					/*
					 * System.err.println(ftpf.hasPermission(FTPFile.GROUP_ACCESS
					 * , FTPFile.EXECUTE_PERMISSION));
					 * System.err.println("READ_PERMISSION="
					 * +ftpf.hasPermission(FTPFile.USER_ACCESS,
					 * FTPFile.READ_PERMISSION));
					 * System.err.println("EXECUTE_PERMISSION="
					 * +ftpf.hasPermission(FTPFile.USER_ACCESS,
					 * FTPFile.EXECUTE_PERMISSION));
					 * System.err.println("WRITE_PERMISSION="
					 * +ftpf.hasPermission(FTPFile.USER_ACCESS,
					 * FTPFile.WRITE_PERMISSION));
					 * System.err.println(ftpf.hasPermission
					 * (FTPFile.WORLD_ACCESS, FTPFile.READ_PERMISSION));
					 */

				}
				// System.out.println(ftpFiles[i].getUser());
			}
		}

		// 下载服务器文件
		FileOutputStream fos = new FileOutputStream("e:/proftpd-1.2.10.tar.gz");
		ftp.retrieveFile("proftpd-1.2.10.tar.gz", fos);
		fos.close();

		// 改变ftp目录
		// ftp.changeToParentDirectory();//回到父目录
		// ftp.changeWorkingDirectory("");//转移工作目录
		// ftp.completePendingCommand();//

		// 删除ftp服务器文件
		// ftp.deleteFile("");

		// 注销当前用户，
		// ftp.logout();
		// ftp.structureMount("");
		ftp.disconnect();
		ftp = null;
	}
}
