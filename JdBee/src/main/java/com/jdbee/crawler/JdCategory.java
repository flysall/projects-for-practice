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

public class JdCategory {
	public static final Logger log = Logger.getLogger(JdCategory.class);

	/**
	 * 获取类目
	 */
	public static List<Category> getCategory() {
		List<SecondCategory> secondList = null;
		List<ThreeCategory> threeList = null;

		String content = HttpUtil.sendGet(Constants.JD_URL);
		List<Category> list = JsoupUtil.getFirstCategory(content);
		Document document = Jsoup.parse(content);

		Elements elements = document.select(".item-title span");
		for (Element element : elements) {
			String text = element.text();
			//遍历一级类目
			for (int i = 0; i < list.size(); i++) {
				String name = list.get(i).getName();
				if ("电脑办公".equals(text))
					text = "电脑、办公";
				if (name.contains(text)) {
					Element categoryItem = element.parent().parent().parent();
					Elements categories = categoryItem.select("dt a");  //所有二级类目
					Elements threeCate = categoryItem.select("dd a");	//所有三级类目
					secondList = new ArrayList<SecondCategory>();
					threeList = new ArrayList<ThreeCategory>();
					//遍历二级类目
					for (int j = 0; j < categories.size(); j++) {
						SecondCategory cate = new SecondCategory(); //二级类目
						cate.setName(categories.get(j).text());
						cate.setUrl("https:" + categories.get(j).attr("href"));
						secondList.add(cate);
						//遍历三级类目
						for (int k = 0; k < threeCate.size(); k++) {
							ThreeCategory threeCategory = new ThreeCategory(); //三级类目
							threeCategory.setUrl("http:" + threeCate.get(k).attr("href"));
							threeCategory.setName(threeCate.get(k).text());
							threeList.add(threeCategory);
						}
						cate.setThreeCates(threeList);
					}
					list.get(i).setSenondCates(secondList);
				}
			}
		}
		return list;
	}
}
