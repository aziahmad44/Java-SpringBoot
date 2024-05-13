package com.duaz.microservices.admin.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class FormatUtility {
  static Logger LOG = LogManager.getLogger(FormatUtility.class);
  
  private static final String ALPHA_NUM = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXyYyZz";
  
  private static final String NUM = "0123456789";
  
  private static final String NUM_NON_ZERO = "123456789";
  
  private static final String[] RCODE = new String[] { 
      "M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", 
      "V", "IV", "I" };
  
  private static final int[] BVAL = new int[] { 
      1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 
      5, 4, 1 };
  
  private static final long MEGABYTE = 1048576L;
  
  public String generateAlphanumericRandomString(int len) throws Exception {
    String result = "";
    StringBuffer sb = null;
    int ndx = -1;
    try {
      sb = new StringBuffer(len);
      for (int i = 0; i < len; i++) {
        ndx = (int)(Math.random() * "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXyYyZz".length());
        sb.append("0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSsTtUuVvWwXyYyZz".charAt(ndx));
      } 
      if (sb != null)
        result = sb.toString(); 
    } catch (Exception e) {
      LOG.error("Exception at generateAlphanumericRandomString");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {
      sb = null;
    } 
    return result;
  }
  
  public String generateNumericRandomString(int len) throws Exception {
    String result = "";
    StringBuffer sb = null;
    int ndx = -1;
    try {
      sb = new StringBuffer(len);
      for (int i = 0; i < len; i++) {
        ndx = (int)(Math.random() * "0123456789".length());
        sb.append("0123456789".charAt(ndx));
      } 
      if (sb != null)
        result = sb.toString(); 
    } catch (Exception e) {
      LOG.error("Exception at generateNumericRandomString");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {
      sb = null;
    } 
    return result;
  }
  
  public String generateNumericRandomStringNonZero(int len) throws Exception {
    String result = "";
    StringBuffer sb = null;
    int ndx = -1;
    try {
      sb = new StringBuffer(len);
      for (int i = 0; i < len; i++) {
        ndx = (int)(Math.random() * "123456789".length());
        sb.append("123456789".charAt(ndx));
      } 
      if (sb != null)
        result = sb.toString(); 
    } catch (Exception e) {
      LOG.error("Exception at generateNumericRandomStringNonZero");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {
      sb = null;
    } 
    return result;
  }
  
  public String generateHashPassword(String password) throws Exception {
    String hash = "";
    MessageDigest md = null;
    byte[] bytes = null;
    StringBuilder sb = null;
    try {
      md = MessageDigest.getInstance("MD5");
      md.update(password.getBytes());
      bytes = md.digest();
      sb = new StringBuilder();
      for (int i = 0; i < bytes.length; i++)
        sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1)); 
      hash = sb.toString();
    } catch (Exception e) {
      LOG.error("Exception at generateHashPassword");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {
      md = null;
      bytes = null;
      sb = null;
    } 
    return hash;
  }
  
  public String formatXML(String source) throws Exception {
    String result = "";
    try {
      result = source;
      result = result.replaceAll("&", "&amp;");
      result = result.replaceAll("<", "&lt;");
      result = result.replaceAll(">", "&gt;");
      result = result.replaceAll("\"", "&quot;");
      result = result.replaceAll("'", "&apos;");
      result = result.replaceAll("\n", " ");
    } catch (Exception e) {
      LOG.error("Exception at formatXML");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {}
    return result;
  }
  
  public String formatDayToOrdinal(Date date) throws Exception {
    Calendar cal = null;
    StringBuffer sb = null;
    String format = "";
    int day = -1;
    try {
      sb = new StringBuffer("");
      if (date != null) {
        cal = Calendar.getInstance();
        cal.setTime(date);
        day = cal.get(5);
        switch (day) {
          case 1:
          case 21:
          case 31:
            sb.append(day + "st");
            break;
          case 2:
          case 22:
            sb.append(day + "nd");
            break;
          case 3:
          case 23:
            sb.append(day + "rd");
            break;
          default:
            sb.append(day + "th");
            break;
        } 
        if (sb != null)
          format = sb.toString(); 
      } else {
        format = "";
      } 
    } catch (Exception e) {
      LOG.error("Exception at formatDayToOrdinal");
      LOG.error(FormatUtility.class, e);
      format = "";
    } finally {
      sb = null;
      cal = null;
    } 
    return format;
  }
  
  public String formatCSV(String source) throws Exception {
    String DOUBLE_QUOTE = "\"";
    String TWO_DOUBLE_QUOTES = "\"\"";
    String result = "";
    try {
      if (source != null && source.trim().length() > 0) {
        result = source;
        if (result.indexOf("\"") >= 0)
          result = result.replace("\"", "\"\""); 
        if (result.indexOf(",") >= 0 || result.indexOf("\"") >= 0)
          result = "\"" + result + "\""; 
      } else {
        result = "";
      } 
    } catch (Exception e) {
      LOG.error("Exception at formatCSV");
      LOG.error(FormatUtility.class, e);
      result = "";
    } finally {}
    return result;
  }
  
  public String leftPad(int n, int padding) throws Exception {
    String result = "";
    try {
      result = String.format("%0" + padding + "d", new Object[] { Integer.valueOf(n) });
    } catch (Exception e) {
      LOG.error("Exception at leftPad");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {}
    return result;
  }
  
  public String rightPadString(String str, int padding, String symbol) throws Exception {
    String result = "";
    try {
      result = String.format("%1$-" + padding + "s", new Object[] { str }).replaceAll(" ", symbol);
    } catch (Exception e) {
      LOG.error("Exception at rightPadString");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {}
    return result;
  }
  
  public String leftPadString(String str, int padding, String symbol) throws Exception {
    String result = "";
    try {
      result = String.format("%1$" + padding + "s", new Object[] { str }).replaceAll(" ", symbol);
    } catch (Exception e) {
      LOG.error("Exception at leftPadString");
      LOG.error(FormatUtility.class, e);
      throw e;
    } finally {}
    return result;
  }
  
  public boolean isValidMoney(String strMoney) {
    NumberFormat nf = null;
    try {
      nf = NumberFormat.getInstance();
      nf.parseObject(strMoney);
    } catch (Exception e) {
      LOG.error("Exception at isValidMoney");
      LOG.error(FormatUtility.class, e);
      return false;
    } finally {
      nf = null;
    } 
    return true;
  }
  
  public String convertArrayOfStringToCommaSeparatedString(String[] source) throws Exception {
    String result = "";
    StringBuilder sb = null;
    try {
      sb = new StringBuilder();
      if (source == null || source.length == 0) {
        result = "";
      } else {
        for (String s : source) {
          sb.append("'" + s + "'");
          sb.append(",");
        } 
        if (sb.length() == 0) {
          result = "";
        } else {
          result = sb.substring(0, sb.length() - 1);
        } 
      } 
    } catch (Exception e) {
      LOG.error("Exception at convertArrayOfStringToCommaSeparatedString");
      LOG.error(FormatUtility.class, e);
      result = "";
    } finally {
      sb = null;
    } 
    return result;
  }
  
  public boolean isIgnoredWords(String word) throws Exception {
    int THREE_CHARS = 3;
    char c = '&';
    boolean ignored = false;
    try {
      if (word == null || word.trim().length() == 0) {
        ignored = false;
      } else if (word.trim().length() == 3) {
        c = word.charAt(1);
        if (c == '&' || c == '$' || c == '#' || c == '\'' || c == '-')
          ignored = true; 
      } 
    } catch (Exception e) {
      LOG.error("Exception at isIgnoredWords");
      LOG.error(FormatUtility.class, e);
      ignored = false;
    } finally {}
    return ignored;
  }
  
  public String formatDate(Date date, String format) throws Exception {
    String dateFormat = "";
    SimpleDateFormat sdf = null;
    try {
      sdf = new SimpleDateFormat(format);
      dateFormat = sdf.format(date);
    } catch (Exception e) {
      LOG.error("Exception at formatDate");
      LOG.error(FormatUtility.class, e);
      dateFormat = "";
    } finally {
      sdf = null;
    } 
    return dateFormat;
  }
  
  private int decodeSingleCharacterRoman(char letter) {
    try {
      for (int i = 0; i < RCODE.length; i++) {
        if (RCODE[i].toString().equalsIgnoreCase(Character.toString(letter)))
          return BVAL[i]; 
      } 
    } catch (Exception e) {
      LOG.error("Exception at decodeSingleCharacterRoman");
      LOG.error(FormatUtility.class, e);
    } finally {}
    return 0;
  }
  
  public int romanToInteger(String roman) throws Exception {
    int result = 0;
    String uRoman = "";
    try {
      uRoman = roman.toUpperCase();
      for (int i = 0; i < uRoman.length() - 1; i++) {
        if (decodeSingleCharacterRoman(uRoman.charAt(i)) < decodeSingleCharacterRoman(uRoman.charAt(i + 1))) {
          result -= decodeSingleCharacterRoman(uRoman.charAt(i));
        } else {
          result += decodeSingleCharacterRoman(uRoman.charAt(i));
        } 
      } 
      result += decodeSingleCharacterRoman(uRoman.charAt(uRoman.length() - 1));
    } catch (Exception e) {
      LOG.error("Exception at romanToInteger");
      LOG.error(FormatUtility.class, e);
    } finally {}
    return result;
  }
  
  public String integerToRoman(int binary) throws Exception {
    String roman = "";
    try {
      if (binary <= 0 || binary >= 4000)
        throw new NumberFormatException("Value outside roman numeral range."); 
      for (int i = 0; i < RCODE.length; i++) {
        while (binary >= BVAL[i]) {
          binary -= BVAL[i];
          roman = roman + RCODE[i];
        } 
      } 
    } catch (Exception e) {
      LOG.error("Exception at integerToRoman");
      LOG.error(FormatUtility.class, e);
      roman = "";
    } finally {}
    return roman;
  }
  
  public long convertBytesToMegaBytes(long bytes) throws Exception {
    long result = 0L;
    try {
      result = bytes / 1048576L;
    } catch (Exception e) {
      LOG.error("Exception at convertBytesToMegaBytes");
      LOG.error(FormatUtility.class, e);
    } finally {}
    return result;
  }
  
  public long convertMegaBytesToBytes(long megaBytes) throws Exception {
    long result = 0L;
    try {
      result = megaBytes * 1048576L;
    } catch (Exception e) {
      LOG.error("Exception at convertMegaBytesToBytes");
      LOG.error(FormatUtility.class, e);
    } finally {}
    return result;
  }
  
  public String wrapText(String text, int intervalLength, String wrapSymbol) throws Exception {
    String result = "";
    String tmp = "";
    StringBuffer sb = null;
    try {
      if (text == null || text.trim().length() == 0) {
        result = "";
      } else {
        sb = new StringBuffer("");
        if (text.length() > intervalLength) {
          while (text.length() > intervalLength) {
            tmp = "";
            tmp = text.substring(0, intervalLength) + wrapSymbol + " ";
            sb.append(tmp);
            text = text.substring(intervalLength);
          } 
          sb.append(text);
          if (sb != null)
            result = sb.toString(); 
        } else {
          result = text;
        } 
      } 
    } catch (Exception e) {
      LOG.error("Exception at wrapText");
      LOG.error(FormatUtility.class, e);
    } finally {
      sb = null;
    } 
    return result;
  }
  
  public String formatNumberToIndonesianFormat(double d) throws Exception {
    String formatted = "";
    DecimalFormatSymbols dfs = null;
    DecimalFormat df = null;
    try {
      dfs = new DecimalFormatSymbols();
      dfs.setDecimalSeparator(',');
      dfs.setGroupingSeparator('.');
      df = new DecimalFormat("#,##,##0.00");
      df.setDecimalFormatSymbols(dfs);
      formatted = df.format(d);
    } catch (Exception e) {
      LOG.error("Exception at formatNumberToIndonesianFormat");
      LOG.error(FormatUtility.class, e);
    } finally {
      dfs = null;
      df = null;
    } 
    return formatted;
  }
  
  public String formatNumberToIndonesianWords(double d) throws Exception {
    String formatted = "";
    String[] nama = { "Nol", "Satu", "Dua", "Tiga", "Empat", "Lima", "Enam", "Tujuh", "Delapan", "Sembilan" };
    String[] besar = { "Triliun", "Milyar", "Juta", "Ribu", "" };
    double p = 1.0E12D;
    try {
      if (d == 0.0D)
        return nama[0]; 
      for (int i = 0; i < besar.length; i++, p /= 1000.0D) {
        if (d >= p) {
          double temp = d / p;
          boolean seribu = (p == 1000.0D);
          if (temp >= 100.0D) {
            formatted = formatted + nama[(int)temp / 100] + " Ratus ";
            temp %= 100.0D;
            seribu = false;
          } 
          if (temp >= 11.0D && temp <= 19.0D) {
            formatted = formatted + nama[(int)temp - 10] + " Belas ";
            temp = 0.0D;
            seribu = false;
          } 
          if (temp >= 10.0D) {
            formatted = formatted + nama[(int)temp / 10] + " Puluh ";
            temp %= 10.0D;
          } 
          if (temp > 0.0D)
            if (seribu && temp == 1.0D) {
              formatted = formatted + "se";
            } else {
              formatted = formatted + nama[(int)temp] + " ";
            }  
          d %= p;
          formatted = formatted + besar[i] + " ";
        } 
      } 
      formatted = formatted.replaceAll("Satu Ratus", "Seratus");
      formatted = formatted.replaceAll("Satu Belas", "Sebelas");
      formatted = formatted.replaceAll("Satu Puluh", "Sepuluh");
    } catch (Exception e) {
      LOG.error("Exception at formatNumberToIndonesianFormat");
      LOG.error(FormatUtility.class, e);
    } finally {
      nama = null;
      besar = null;
    } 
    return formatted;
  }
  
  public String formatNumberToCustomPattern(String pattern, double value) {
    String result = "";
    DecimalFormat myFormatter = null;
    try {
      myFormatter = new DecimalFormat(pattern);
      result = myFormatter.format(value);
    } catch (Exception e) {
      LOG.error("Exception at formatNumberToCustomPattern");
      LOG.error(FormatUtility.class, e);
    } finally {
      myFormatter = null;
    } 
    return result;
  }
  
  public String convertBulletHtmlJasper(String originalContent, int noWordsBreak, int noSpace) throws Exception {
    String result = "";
    String tmp = "";
    String inTmp = "";
    String[] arr = null;
    try {
      if (originalContent != null && originalContent.length() > 0) {
        originalContent = originalContent.replaceAll("<ul>", "").replaceAll("</ul>", "");
        while (originalContent != null && originalContent.length() > 0) {
          if (originalContent.indexOf("<li>") >= 0) {
            tmp = "";
            tmp = originalContent.substring(0, originalContent.indexOf("<li>"));
            if (tmp != null && tmp.length() > 0)
              result = result + tmp; 
            tmp = "";
            tmp = originalContent.substring(originalContent.indexOf("<li>") + 4, originalContent.indexOf("</li>"));
            inTmp = "<br/>";
            arr = null;
            if (tmp != null && tmp.length() > 0) {
              arr = null;
              arr = tmp.split(" ");
              if (arr != null && arr.length > 0)
                for (int i = 0; i < arr.length; i++) {
                  inTmp = inTmp + arr[i] + " ";
                  if (i > 0 && (i + 1) % noWordsBreak == 0)
                    if (i + 1 != arr.length) {
                      inTmp = inTmp + "<br/>";
                      int x = 1;
                      while (x <= noSpace) {
                        inTmp = inTmp + "&nbsp;";
                        x++;
                      } 
                    }  
                }  
              result = result + inTmp;
            } 
            originalContent = originalContent.substring(originalContent.indexOf("</li>") + 5, originalContent.length());
            continue;
          } 
          result = result + originalContent;
          originalContent = "";
        } 
      } else {
        result = "";
      } 
    } catch (Exception e) {
      LOG.error("Exception at convertBulletHtmlJasper");
      LOG.error(FormatUtility.class, e);
    } finally {
      arr = null;
    } 
    return result;
  }
  
  public String decodeURIComponent(String s) {
    if (s == null)
      return null; 
    String result = null;
    try {
      result = URLDecoder.decode(s, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      result = s;
    } 
    return result;
  }
  
  public String encodeURIComponent(String s) {
    String result = null;
    try {
      result = URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20").replaceAll("\\%21", "!").replaceAll("\\%27", "'").replaceAll("\\%28", "(").replaceAll("\\%29", ")").replaceAll("\\%7E", "~");
    } catch (UnsupportedEncodingException e) {
      result = s;
    } 
    return result;
  }
}
