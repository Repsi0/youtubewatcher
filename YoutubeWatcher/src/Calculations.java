import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Calculations {
	
	public static int likes_ytr, dislikes_ytr, prev_likes, prev_dislikes, inc_likes_totaled;
	public static int inc_dislikes_totaled;
	public static int seconds;
	public static int views;
	public static int prev_views;
	public static int inc_views_totaled;
	public static double avg_inc_likes, avg_inc_dislikes, avg_inc_views;
	public static double ratio_total;
	public static double ratio_increase;
	public static Document doc;
	public static int views_inc, likes_inc, dislikes_inc;
	private static String youtube_id;
	
	public Calculations(String youtube_id) {
		try {
			doc = Jsoup.connect("https://dbase.tube/v/" + youtube_id).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Calculations.youtube_id = youtube_id;
		prev_likes = likes_ytr;
		likes_ytr = getInt(doc.getElementById("live-likes").html());
		prev_dislikes = dislikes_ytr;
		dislikes_ytr = getInt(doc.getElementById("live-dlikes").html());
		prev_views = views;
		views = getInt(doc.getElementById("live-views").html());
	}
	
	public static int getInt(String s) {
		String[] start = s.split(",");
		
		String final_str = "";
		for(int i = 0; i < start.length; i++) {
			final_str += start[i];
		}
		
		return Integer.parseInt(final_str);
	}

	public static void calculations() {
		try {
			doc = Jsoup.connect("https://dbase.tube/v/" + youtube_id).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		seconds++;
		prev_likes = likes_ytr;
		likes_ytr = getInt(doc.getElementById("live-likes").html());
		prev_dislikes = dislikes_ytr;
		dislikes_ytr = getInt(doc.getElementById("live-dlikes").html());
		prev_views = views;
		views = getInt(doc.getElementById("live-views").html());
		
		
		likes_inc = likes_ytr - prev_likes;
		dislikes_inc = dislikes_ytr - prev_dislikes;
		views_inc = views - prev_views;
		
		inc_likes_totaled += likes_inc;
		inc_dislikes_totaled += dislikes_inc;
		inc_views_totaled += views_inc;
		
		avg_inc_likes = (double) inc_likes_totaled / (double) seconds;
		avg_inc_dislikes = (double) inc_dislikes_totaled / (double) seconds;
		avg_inc_views = (double) inc_views_totaled / (double) seconds;
		
		ratio_total = (double) dislikes_ytr / (double) likes_ytr;
		ratio_increase = (double) avg_inc_dislikes / (double) avg_inc_likes;
	}
	
}
