package com.jdbee.crawler;

import com.jdbee.model.Category;
import com.jdbee.model.SecondCategory;
import com.jdbee.model.ThreeCategory;
import com.jdbee.utils.Constants;
import com.jdbee.utils.HttpUtil;
import com.jdbee.utils.JsoupUtil;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品类目
 */
public class JdCategory {
	public static final Logger log = Logger.getLogger(JdCategory.class);

	/**
	 * 获取类目
	 */
	public static List<Category> getCategory() {
		String content = HttpUtil.sendGet(Constants.JD_URL);
		List<Category> list = JsoupUtil.getFirstCategory(content);
		Document document = Jsoup.parse(content);

		Elements elements = document.select(".item-title span");  //获取所有的一级类目
		int i = 0;  //变量i用于遍历一级类目
		//遍历一级类目
		for (Element element : elements) {
			String text = element.text();
			
			String name = list.get(i).getName();
			if ("电脑办公".equals(text))
				text = "电脑、办公";
			if (name.contains(text)) {
				Element secondCategoryItem = element.parent().parent().parent();
				Elements secondCategories = secondCategoryItem.select("dt a");  //第i个一级类目下的所有二级类目
				List<SecondCategory> secondList = new ArrayList<SecondCategory>();
				
				//遍历某个一级类目下的所有二级类目
				for(int j = 0; j < secondCategories.size(); j++){
					Element thirdCategoryItem = secondCategories.get(j).parent().parent();
					Elements thirdCategories = thirdCategoryItem.select("dd a"); //第j个二级类目下的所有三级类目
					SecondCategory secondCategory = new SecondCategory();
					secondCategory.setName(secondCategories.get(j).text());
					secondCategory.setUrl("https:" + secondCategories.get(j).attr("href"));
					List<ThreeCategory> threeList = new ArrayList<ThreeCategory>();
					//遍历第j个二级类目下的所有三级类目
					for(int k = 0; k < thirdCategories.size(); k++){
						
						ThreeCategory threeCategory = new ThreeCategory();
						threeCategory.setName(thirdCategories.get(k).text());
						threeCategory.setUrl("https:" + thirdCategories.get(k).attr("href"));
						threeList.add(threeCategory); 
					}
					secondCategory.setThreeCates(threeList);
					secondList.add(secondCategory);
				}
				list.get(i).setSenondCates(secondList);
			}
			i++;
			
		}
		return list;
	}
}
