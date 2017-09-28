package com.zs.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class Test extends HttpServlet{
	
	private final String tomcatName="apache-tomcat-9.0.0.M17";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		final String PATH_BEGIN=req.getRealPath("/");
		req.setCharacterEncoding("utf-8");
		String path=req.getParameter("path")==null?PATH_BEGIN+"files":URLDecoder.decode(req.getParameter("path"),"UTF-8");
		File file=new File(path);
		File[] tempList = file.listFiles();
		List<String> ss=new ArrayList<String>();
		if (tempList!=null) {
			String pathtmp="";
			if (tempList.length>0) {
				pathtmp=tempList[0].getParentFile().getParentFile().toString();
			}else {
				pathtmp=path;
			}
			if(isTopDir(pathtmp)==false){
				ss.add("<a href='test?path="+URLEncoder.encode(pathtmp,"UTF-8")+"' class='menu'>上一级目录</a>");
			}
			ss.add("<hr><table border='1' class='table1'>");
			ss.add("<tr>");
			ss.add("<th>名称</th>");
			ss.add("<th width='30%'>最后修改时间</th>");
			ss.add("<th width='20%'>大小</th>");
			ss.add("<th width='20%'>操作</th>");
			ss.add("</tr>");
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isFile()) {
					String str=initPath(PATH_BEGIN,tempList[i].toString());
					//获取大小
					FileInputStream fis= new FileInputStream(tempList[i]);  
					FileChannel fc= fis.getChannel();  
					long flieSize=fc.size();
					String StringSize=tranSize(flieSize);
					//获取最后修改时间
					Calendar cal = Calendar.getInstance();   
					long time = tempList[i].lastModified();   
					cal.setTimeInMillis(time);     
					String datetime=cal.getTime().toLocaleString();
					ss.add("<tr>");
					ss.add("<td><a href='"+str+"' class='filetype'>"+tempList[i].getName()+"</a></td>");
					ss.add("<td class='td_center'><span class='lastTime'>"+datetime+"</span></td>");
					ss.add("<td class='td_center'><span class='size'>"+StringSize+"</span></td>");
					ss.add("<td><a href='"+str+"' download='"+tempList[i].getName()+"' class='filetype'>下载</a></td>");
					ss.add("</tr>");
				}else if (tempList[i].isDirectory()) {
					//获取最后修改时间
					Calendar cal = Calendar.getInstance();   
					long time = tempList[i].lastModified();   
					cal.setTimeInMillis(time);     
					String datetime=cal.getTime().toLocaleString();
					ss.add("<tr>");
					ss.add("<td><a href='test?path="+URLEncoder.encode(tempList[i].toString(),"UTF-8")+"' class='dirtype' >"+tempList[i].getName()+"</a></td>");
					ss.add("<td class='td_center'><span class='lastTime'>"+datetime+"</span></td>");
					ss.add("<td></td>");
					ss.add("<td></td>");
					ss.add("</tr>");
				}
			}
			ss.add("</table>");
		}
		req.setAttribute("array", ss);
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private String initPath(String patgFirst,String str) {
		String ssfirst[]=patgFirst.split("\\\\");
		String ss[]=str.split("\\\\");
		String str1="";
		int begin=ssfirst.length;
		for (int i = begin; i < ss.length; i++) {
			str1= i==begin?str1+ss[i]:str1+"/"+ss[i];
		}
		return str1;
	}
	
	
	private void ssToString(String ss[]){
		String string="[";
		for (int i = 0; i < ss.length; i++) {
			if (i==ss.length-1) {
				string=string+ss[i];
			}else {
				string=string+ss[i]+",";
			}
		}
		string=string+"]";
		System.out.println("【"+getLineInfo()+"】:"+string+"【长度："+ss.length+"】");
	}
	
	
	private String getLineInfo()
    {
        StackTraceElement ste = new Throwable().getStackTrace()[1];
        return ste.getFileName() + ": Line " + ste.getLineNumber();
    }
	
	private boolean isTopDir(String str) {
		String ss[]=str.split("\\\\");
//		ssToString(ss);
		for (int i = 0; i < ss.length; i++) {
			if (ss[i].equals(tomcatName)) {
				if (i+2<ss.length-1) {
					return false;
				}else {
					return true;
				}
			}
		}
		return true;
	}
	
	
	private String tranSize(long size) {
		//如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义  
	    if (size < 1024) {  
	        return String.valueOf(size) + "B";  
	    } else {  
	        size = size / 1024;  
	    }  
	    //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位  
	    //因为还没有到达要使用另一个单位的时候  
	    //接下去以此类推  
	    if (size < 1024) {  
	        return String.valueOf(size) + "KB";  
	    } else {  
	        size = size / 1024;  
	    }  
	    if (size < 1024) {  
	        //因为如果以MB为单位的话，要保留最后1位小数，  
	        //因此，把此数乘以100之后再取余  
	        size = size * 100;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "MB";  
	    } else {  
	        //否则如果要以GB为单位的，先除于1024再作同样的处理  
	        size = size * 100 / 1024;  
	        return String.valueOf((size / 100)) + "."  
	                + String.valueOf((size % 100)) + "GB";  
	    }  
	}
	
	
}

