package com.funraiser;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
			RecommenderIntro.recommend();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
