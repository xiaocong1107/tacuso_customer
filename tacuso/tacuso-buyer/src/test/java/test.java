
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
	
	/**
     * 解析地址
     * @author lin
     * @param address
     * @return
     */
    public static List<Map<String,String>> addressResolution(String address){
        String regex="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇)?(?<village>.*)";
        Matcher m=Pattern.compile(regex).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
            table.add(row);
        }
        return table;
    }
 
	public static void main(String[] args) {
		System.out.println(addressResolution("广东省广州市海珠区江南西路紫金大街26号102"));
//		List<Map<String,String>> listMap = addressResolution("广东省广州市番禺区有衣宅送测试门店2号（收件）");
//		for (Map<String, String> m : listMap)
//	    {
//		    System.out.println("province=="+ m.get("province"));
//	        System.out.println("city=="+ m.get("city"));
//	        System.out.println("county=="+ m.get("county"));
//	        System.out.println("town=="+ m.get("town"));
//	        System.out.println("village=="+ m.get("village"));
//	    }
	}


}
