package com.funraiser;

public class App {

	public static void main(String[] args) {
		try {
			RecommenderIntro.recommend();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
