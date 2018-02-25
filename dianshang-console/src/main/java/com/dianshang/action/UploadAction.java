package com.dianshang.action;

import com.dianshang.core.tools.FastDFSTool;
import com.dianshang.dictionary.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


/**
 * 上传文件控制器
 * 
 * @author Administrator
 *
 */
@Controller
public class UploadAction {

	// 上传文件
	@RequestMapping(value = "/uploadFile.do")
	@ResponseBody
	public HashMap<String, String> uploadFile(MultipartFile mpf)
			throws FileNotFoundException, IOException, Exception {

		System.out.println(mpf.getOriginalFilename());

		// 将文件上传到分布式文件系统，并返回文件的存储路径及名称
		String uploadFile = FastDFSTool.uploadFile(mpf.getBytes(),
				mpf.getOriginalFilename());
		// 返回json格式的字符串（图片的绝对网络存放地址）
		HashMap<String, String> hashMap = new HashMap<String, String>();
		System.err.println(uploadFile);
		hashMap.put("path", Constants.FDFS_SERVER + uploadFile);
		return hashMap;
	}
	// 同时上传多个文件
	@RequestMapping(value = "/uploadFiles.do")
	@ResponseBody
	public List<String> uploadFiles(@RequestParam MultipartFile[] mpfs)
			throws FileNotFoundException, IOException, Exception {

		// 上传文件返回的路径集合
		List<String> arrayList = new ArrayList<String>();
		System.err.println(mpfs);
		System.err.println(mpfs.length);

		for (MultipartFile mpf : mpfs) {

			// 将文件上传到分布式文件系统，并返回文件的存储路径及名称
			String uploadFile = FastDFSTool.uploadFile(mpf.getBytes(),
					mpf.getOriginalFilename());

			System.err.println(uploadFile+"123124124124");

			// 返回json格式的字符串（图片的绝对网络存放地址）
			arrayList.add(Constants.FDFS_SERVER + uploadFile);
		}
		System.err.println(arrayList.toString());
		return arrayList;
	}
	// 接收富文本编辑器传递的图片(无敌版：不考虑文件的name，强行接收)
	@RequestMapping(value = "/uploadFck.do")
	@ResponseBody
	public HashMap<String, Object> uploadFck(HttpServletRequest request,
											 HttpServletResponse response)
			throws FileNotFoundException, IOException, Exception {

		// 将request强转为spring提供的MultipartRequest
		MultipartRequest mr = (MultipartRequest) request;

		// 获得MultipartRequest里面的所有文件
		Set<Map.Entry<String, MultipartFile>> entrySet = mr.getFileMap().entrySet();

		for (Map.Entry<String, MultipartFile> entry : entrySet) {
			MultipartFile mpf = entry.getValue();
			// 将文件上传到分布式文件系统，并返回文件的存储路径及名称
			String uploadFile = FastDFSTool.uploadFile(mpf.getBytes(),
					mpf.getOriginalFilename());
			// 返回json格式的字符串（图片的绝对网络存放地址）
			HashMap<String, Object> hashMap = new HashMap<String, Object>();

			// error和url名字都是固定死的
			hashMap.put("error", 0);
			hashMap.put("url", Constants.FDFS_SERVER + uploadFile);
			return hashMap;
		}
		return null;
	}


}
