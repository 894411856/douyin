package com.openwan.controller.util;

import java.util.List;


/**
 * 
 *  
 */
public class DouClass {

	private List<AwemeList> aweme_list;

	public List<AwemeList> getAweme_list() {
		return aweme_list;
	}

	public void setAweme_list(List<AwemeList> aweme_list) {
		this.aweme_list = aweme_list;
	}

	@Override
	public String toString() {
		return "DouClass [aweme_list=" + aweme_list + "]";
	}

	 

	 
	
}



/**
 * 大對象
 *  
 */
class AwemeList{
	private Video video;
	private ShareInfo share_info ;

	private String desc ;
	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	 

	public ShareInfo getShare_info() {
		return share_info;
	}

	public void setShare_info(ShareInfo share_info) {
		this.share_info = share_info;
	}

	
	
	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "AwemeList [video=" + video + ", share_info=" + share_info
				+ ", desc=" + desc + "]";
	}

	 
	
}


/**
 * 視頻
 *  
 */
class Video {
	private PlayAddr play_addr ;

	public PlayAddr getPlay_addr() {
		return play_addr;
	}

	public void setPlay_addr(PlayAddr play_addr) {
		this.play_addr = play_addr;
	}

	@Override
	public String toString() {
		return "Video [play_addr=" + play_addr + "]";
	}

	 

}


/**
 * 視頻地址
 *  
 */
class PlayAddr{
	private List<String> url_list;

	public List<String> getUrl_list() {
		return url_list;
	}

	public void setUrl_list(List<String> url_list) {
		this.url_list = url_list;
	}

	@Override
	public String toString() {
		return "PlayAddr [url_list=" + url_list + "]";
	}
	
}



/**
 * 視頻內容
 *  
 */
class ShareInfo{
	private String share_title;

	public String getShare_title() {
		return share_title;
	}

	public void setShare_title(String share_title) {
		this.share_title = share_title;
	}

	@Override
	public String toString() {
		return "ShareInfo [share_title=" + share_title + "]";
	}
	
	
}
 



 

