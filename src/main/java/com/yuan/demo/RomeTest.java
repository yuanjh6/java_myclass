package com.yuan.demo;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.List;

public class RomeTest {
	private static Logger logger = LogManager.getLogger(RomeTest.class
			.getName());

	public static void main(String[] args) {
		RomeTest rt=new RomeTest();
		rt.ReadFeedXml();
	}
	public void ReadFeedXml() {
		try {
			URL feedUrl = new URL(
					"http://www.16zhan.com/rss.php");
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(feedUrl));
			System.out.println(feed);

			// 从feed中得到entry
			List list = feed.getEntries();
			for (int i = 0; i < list.size(); i++) {
				SyndEntry entry = (SyndEntry) list.get(i);
				System.out.println("链接为 = " + entry.getLink());
				System.out.println("标题为 = " + entry.getTitle());
				System.out.println("时间为 = " + entry.getPublishedDate());
				System.out
						.println("内容为 = " + entry.getDescription().getValue());
				System.out.println("Categories为 = "
						+ entry.getCategories().get(0));
				System.out
						.println("==============================================================================");

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
