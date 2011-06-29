package com.nicolatesser.germanadjectivedeclension;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class AdjectiveDeclensionSentenceGenerator {
	
	//(bestimmter Artikel);
	Declension[][] type1 = getDeclensionType1();
	//(unbestimmter Artikel);
	Declension[][] type2 = getDeclensionType2();
	//(nullArtikel);
	Declension[][] type3 = getDeclensionType3();
	
	List<String>[][] type1Prefix = new List[4][4];

	List<String>[][] type2Prefix = new List[4][4];

	List<String>[][] type3Prefix= new List[4][4];

	List<String>[][] words = new List[4][4];
	
	List<String> adjectives;
	
	private Random rg = new Random();
	
	
	public AdjectiveDeclensionSentenceGenerator()
	{
		
		//typ 1 (bestimmter Artikel) prefixes
		type1Prefix[0][0] = Arrays.asList("der");
		type1Prefix[0][1] = Arrays.asList("das");
		type1Prefix[0][2] = Arrays.asList("die");
		type1Prefix[0][3] = Arrays.asList("die");
		type1Prefix[1][0] = Arrays.asList("den");
		type1Prefix[1][1] = Arrays.asList("das");
		type1Prefix[1][2] = Arrays.asList("die");
		type1Prefix[1][3] = Arrays.asList("die");
		type1Prefix[2][0] = Arrays.asList("dem");
		type1Prefix[2][1] = Arrays.asList("dem");
		type1Prefix[2][2] = Arrays.asList("der");
		type1Prefix[2][3] = Arrays.asList("den");
		type1Prefix[3][0] = Arrays.asList("des");
		type1Prefix[3][1] = Arrays.asList("des");
		type1Prefix[3][2] = Arrays.asList("der");
		type1Prefix[3][3] = Arrays.asList("der");
		
		//typ 1 (bestimmter Artikel) prefixes
		type2Prefix[0][0] = Arrays.asList("kein");
		type2Prefix[0][1] = Arrays.asList("kein");
		type2Prefix[0][2] = Arrays.asList("keine");
		type2Prefix[0][3] = Arrays.asList("keine");
		type2Prefix[1][0] = Arrays.asList("keinen");
		type2Prefix[1][1] = Arrays.asList("kein");
		type2Prefix[1][2] = Arrays.asList("keine");
		type2Prefix[1][3] = Arrays.asList("keine");
		type2Prefix[2][0] = Arrays.asList("keinem");
		type2Prefix[2][1] = Arrays.asList("keinem");
		type2Prefix[2][2] = Arrays.asList("keiner");
		type2Prefix[2][3] = Arrays.asList("keinen");
		type2Prefix[3][0] = Arrays.asList("keines");
		type2Prefix[3][1] = Arrays.asList("keines");
		type2Prefix[3][2] = Arrays.asList("keiner");
		type2Prefix[3][3] = Arrays.asList("keiner");
		
		//typ 3 (KeiAutosnartikel) prefixes
		type3Prefix[0][0] = Arrays.asList("1");
		type3Prefix[0][1] = Arrays.asList("1");
		type3Prefix[0][2] = Arrays.asList("1");
		type3Prefix[0][3] = Arrays.asList("2");
		type3Prefix[1][0] = Arrays.asList("1");
		type3Prefix[1][1] = Arrays.asList("1");
		type3Prefix[1][2] = Arrays.asList("1");
		type3Prefix[1][3] = Arrays.asList("2");
		type3Prefix[2][0] = Arrays.asList("1");
		type3Prefix[2][1] = Arrays.asList("1");
		type3Prefix[2][2] = Arrays.asList("1");
		type3Prefix[2][3] = Arrays.asList("2");
		type3Prefix[3][0] = Arrays.asList("1");
		type3Prefix[3][1] = Arrays.asList("1");
		type3Prefix[3][2] = Arrays.asList("1");
		type3Prefix[3][3] = Arrays.asList("2");
		
		
		// Words
		words[0][0] = Arrays.asList("Mann","Papa","Opa");
		words[0][1] = Arrays.asList("Mädchen","Kind","Baby");
		words[0][2] = Arrays.asList("Frau","Mama","Oma");
		words[0][3] = Arrays.asList("Männer","Eltern","Frauen");
		words[1][0] = Arrays.asList("Mann","Papa","Opa");
		words[1][1] = Arrays.asList("Mädchen","Kind","Baby");
		words[1][2] = Arrays.asList("Frau","Mama","Oma");
		words[1][3] = Arrays.asList("Männer","Eltern","Frauen");
		words[2][0] = Arrays.asList("Mann","Papa","Opa");
		words[2][1] = Arrays.asList("Mädchen","Kind","Baby");
		words[2][2] = Arrays.asList("Frau","Mama","Oma");
		words[2][3] = Arrays.asList("Männern","Eltern","Frauen");
		words[3][0] = Arrays.asList("Mannes","Papas","Opas");
		words[3][1] = Arrays.asList("Mädchens","Kindes","Babys");
		words[3][2] = Arrays.asList("Frau","Mama","Oma");
		words[3][3] = Arrays.asList("Männer","Eltern","Frauen");
		
		// adjectives
		adjectives = Arrays.asList("gut","alt","jung","lieb");	
	}
	
	
	public Object[] next()
	{
		Object[]result = new Object[2];
		
		int caseIndex = rg.nextInt(4);
		int genderIndex= rg.nextInt(4);
		
		int typeIndex = rg.nextInt(3);
		
		switch (typeIndex) {
		case 0:
			result[0] = type1[caseIndex][genderIndex];
			result[1] = buildSentence(type1Prefix, caseIndex, genderIndex,typeIndex);
			break;
		case 1:
			result[0] = type2[caseIndex][genderIndex];
			result[1] = buildSentence(type2Prefix, caseIndex, genderIndex,typeIndex);
			break;
		case 2:
			result[0] = type3[caseIndex][genderIndex];
			result[1] = buildSentence(type3Prefix, caseIndex, genderIndex,typeIndex);
			break;
		}
		
		return result;
	}
	
	
	public String buildSentence(List<String>[][] prefixes, int caseIndex, int genderIndex, int typeIndex)
	{
		StringBuffer sentence= new StringBuffer();
		List<String> possiblePrefixes = prefixes[caseIndex][genderIndex];
		Collections.shuffle(possiblePrefixes);

		sentence.append(possiblePrefixes.get(0));
		sentence.append(" ");
		
		Collections.shuffle(adjectives);
		sentence.append(adjectives.get(0));
		sentence.append("_ ");
		
		List<String> possibleWords = words[caseIndex][genderIndex];
		Collections.shuffle(possibleWords);

		sentence.append(possibleWords.get(0));
		sentence.append(" (");

		switch (genderIndex) {
		case 0:
			sentence.append("m");
			break;
		case 1:
			sentence.append("n");
			break;
		case 2:
			sentence.append("f");
			break;
		case 3:
			sentence.append("p");
			break;

		}
		
		if (typeIndex==2)
		{
			switch (caseIndex) {
			case 0:
				sentence.append("/n");
				break;
			case 1:
				sentence.append("/a");
				break;
			case 2:
				sentence.append("/d");
				break;
			case 3:
				sentence.append("/g");
				break;
			}
		}
		
		sentence.append(").");

		
		return sentence.toString();
	}
	
	
	
	
	
	public Declension[][] getDeclensionType1()
	{
		Declension[][] declension = new Declension[4][4];
		//nominativ
		declension[0][0] = Declension.E;
		declension[0][1] = Declension.E;
		declension[0][2] = Declension.E;
		declension[0][3] = Declension.EN;
		//akkusativ
		declension[1][0] = Declension.EN;
		declension[1][1] = Declension.E;
		declension[1][2] = Declension.E;
		declension[1][3] = Declension.EN;
		//dativ
		declension[2][0] = Declension.EN;
		declension[2][1] = Declension.EN;
		declension[2][2] = Declension.EN;
		declension[2][3] = Declension.EN;
		//genitiv
		declension[3][0] = Declension.EN;
		declension[3][1] = Declension.EN;
		declension[3][2] = Declension.EN;
		declension[3][3] = Declension.EN;
		return declension;
	}
	
	public Declension[][] getDeclensionType2()
	{
		Declension[][] declension = new Declension[4][4];
		//nominativ
		declension[0][0] = Declension.ER;
		declension[0][1] = Declension.ES;
		declension[0][2] = Declension.E;
		declension[0][3] = Declension.E;
		//akkusativ
		declension[1][0] = Declension.EN;
		declension[1][1] = Declension.ES;
		declension[1][2] = Declension.E;
		declension[1][3] = Declension.E;
		//dativ
		declension[2][0] = Declension.EN;
		declension[2][1] = Declension.EN;
		declension[2][2] = Declension.EN;
		declension[2][3] = Declension.EN;
		//genitiv
		declension[3][0] = Declension.EN;
		declension[3][1] = Declension.EN;
		declension[3][2] = Declension.EN;
		declension[3][3] = Declension.ER;
		return declension;
	}
	
	public Declension[][] getDeclensionType3()
	{
		Declension[][] declension = new Declension[4][4];
		//nominativ
		declension[0][0] = Declension.ER;
		declension[0][1] = Declension.ES;
		declension[0][2] = Declension.E;
		declension[0][3] = Declension.E;
		//akkusativ
		declension[1][0] = Declension.ER;
		declension[1][1] = Declension.ES;
		declension[1][2] = Declension.E;
		declension[1][3] = Declension.E;
		//dativ
		declension[2][0] = Declension.EM;
		declension[2][1] = Declension.EM;
		declension[2][2] = Declension.ER;
		declension[2][3] = Declension.EN;
		//genitiv
		declension[3][0] = Declension.ES;
		declension[3][1] = Declension.ES;
		declension[3][2] = Declension.ER;
		declension[3][3] = Declension.ER;
		return declension;
	}
	
	public static void main (String[]args)
	{
		AdjectiveDeclensionSentenceGenerator target = new AdjectiveDeclensionSentenceGenerator();
		
		for (int i=0;i<100;i++)
		{
			Object[] next = target.next();
			System.out.println(next[0]+" -- "+next[1]);
		}
		
	}
	

}
