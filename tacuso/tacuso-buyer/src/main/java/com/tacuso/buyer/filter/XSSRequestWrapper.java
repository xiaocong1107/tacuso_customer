package com.tacuso.buyer.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.regex.Pattern;

/**
 * XSS过滤器
 * @author administrator
 */
public class XSSRequestWrapper extends HttpServletRequestWrapper {

	    public XSSRequestWrapper(HttpServletRequest servletRequest) {
	        super(servletRequest);
	    }
	    @Override
	    public String[] getParameterValues(String parameter) {
	        String[] values = super.getParameterValues(parameter);
	        if (values == null) {
	            return null;
	        }
	        int count = values.length;
	        String[] encodedValues = new String[count];
	        for (int i = 0; i < count; i++) {
	            encodedValues[i] = stripXSS(values[i]);
	        }
	        return encodedValues;
	    }
	    @Override
	    public String getParameter(String parameter) {
	        String value = super.getParameter(parameter);
	        return stripXSS(value);
	    }
	    @Override
	    public String getHeader(String name) {
	        String value = super.getHeader(name);
	        return stripXSS(value);
	    }
	    private String stripXSS(String value) {
	        if (value != null) {
	            value = value.replaceAll("", "");
	            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");
//	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//	            value = scriptPattern.matcher(value).replaceAll("");
//	            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
//	            value = scriptPattern.matcher(value).replaceAll("");
	            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");
	            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");
	            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");
	            scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");
	            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");
	            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
	            value = scriptPattern.matcher(value).replaceAll("");
	            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
	            value = scriptPattern.matcher(value).replaceAll("");
	            value=value.trim();
	        }
	        return value;
	    }
	    
	    
	    public static String xssEncode(String s) {
	        if (s == null || s.equals("")) {
	            return s;
	        }
	        StringBuilder sb = new StringBuilder(s.length() + 16);
	        for (int i = 0; i < s.length(); i++) {
	            char c = s.charAt(i);
	            switch (c) {
	            case '>':
	                sb.append('＞');// 全角大于号
	                break;
	            case '<':
	                sb.append('＜');// 全角小于号
	                break;
	            case '\'':
	                sb.append('\\');
	                sb.append('\'');
	                sb.append('\\');
	                sb.append('\'');
	                break;
	            case '\"':
	                sb.append('\\');
	                sb.append('\"');// 全角双引号
	                break;
	            case '&':
	                sb.append('＆');// 全角
	                break;
	            case '\\':
	                sb.append('＼');// 全角斜线
	                break;
	            case '#':
	                sb.append('＃');// 全角井号
	                break;
	            case ':':
	                sb.append('：');// 全角冒号
	                break;
	            case '%':
	                sb.append("\\\\%");
	                break;
	            default:
	                sb.append(c);
	                break;
	            }
	        }
	        return sb.toString();
	    }
	}