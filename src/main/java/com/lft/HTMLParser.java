package com.lft;

public class HTMLParser {
	
	private final String html;
	
	public HTMLParser(String html) {
		this.html = html;
	}
	
	public String getText() {
		return html.strip()
				.replaceAll("(<head>).*?(</head>)]", "")
				.replaceAll("(<script.*?>).*?(</script>)", "")
				.replaceAll("<.*?>", "");
	}
}
