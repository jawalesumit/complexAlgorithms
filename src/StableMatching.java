import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class StableMatching
{
	static String file_name = "input1.txt";
	static File file = null;
	static BufferedReader br = null;
	static HashMap<Integer, String[]> hmMenChoices = null;
	static HashMap<Integer, String[]> hmWomenChoices = null;
	static int n = 0;
	static HashMap<Integer, Integer> hmMenPair = null;
	static HashMap<Integer, Integer> hmWomenPair = null;
	static int[][] iMenPref = null;
	static int iStableMatch = 0;
	static int totalCount = 0;

	public static void main(String[] args) throws IOException
	{
		try
		{
			readInputFile();
			iMenPref = new int[n][2];
			for (int i = 0; i < n; i++)
			{
				for (int j = 0; j < 2; j++)
				{
					iMenPref[i][j] = -1;
				}
			}
			createBestPairs();
			hmMenPair = new HashMap<Integer, Integer>();
			hmWomenPair = new HashMap<Integer, Integer>();
			createPairs(new int[n], 0);
			// System.out.println("Total stable matches: " + iStableMatch);
			System.out.println(iStableMatch);
			// System.out.println("Stability check = " + totalCount);
		}
		catch (Exception e)
		{
			System.out.println("Exception");
			e.printStackTrace();
		}
		finally
		{
			if (file != null)
				file = null;
			if (br != null)
				br.close();
		}
	}

	/**
	 * To read the given input file
	 * 
	 * @throws Exception
	 */
	private static void readInputFile() throws Exception
	{
		file = new File(System.getProperty("user.dir") + "//src//" + file_name);
		if (!file.exists())
		{
			file = new File(file_name);
		}
		br = new BufferedReader(new FileReader(file));
		String st;
		if ((st = br.readLine()) != null)
		{
			n = Integer.parseInt(st);
			int i = 0;
			// For Men
			int m = 0;
			hmMenChoices = new HashMap<>();
			// For women
			int w = 0;
			hmWomenChoices = new HashMap<>();
			while ((st = br.readLine()) != null)
			{
				if (i < n)
				{
					hmMenChoices.put(m, st.split(" "));
					m++;
				}
				else
				{
					hmWomenChoices.put(w, st.split(" "));
					w++;
				}
				i++;
			}
		}
	}

	/**
	 * Create the best pairs with men proposing and women proposing
	 */
	private static void createBestPairs()
	{
		boolean flag = false;
		// Men proposing
		int[] iWomenVisited = new int[n];
		for (int i = 0; i < n; i++)
		{
			iWomenVisited[i] = -1;
		}
		do
		{
			flag = false;
			for (int i = 0; i < n; i++)
			{
				if (iMenPref[i][0] == -1)
				{
					String[] sMenChoices = hmMenChoices.get(i);
					for (int m = 0; m < sMenChoices.length; m++)
					{
						int iMenChoice = Integer.parseInt(sMenChoices[m]) - 1;
						if (iWomenVisited[iMenChoice] == -1)
						{
							iMenPref[i][0] = m;
							iWomenVisited[iMenChoice] = i;
							break;
						}
						else
						{
							String[] sWomenChoices = hmWomenChoices.get(iMenChoice);
							for (int w = 0; w < sWomenChoices.length; w++)
							{
								int iWomenChoice = Integer.parseInt(sWomenChoices[w]) - 1;
								if (iWomenChoice == i)
								{
									iMenPref[iWomenVisited[iMenChoice]][0] = -1;
									iMenPref[i][0] = m;
									iWomenVisited[iMenChoice] = i;
									flag = true;
									break;
								}
								else if (iWomenChoice == iWomenVisited[iMenChoice])
								{
									break;
								}
							}
							if (flag)
							{
								break;
							}
						}
					}
				}
			}
		} while (flag);
		// Women proposing
		int[] iMenPair = new int[n];
		for (int i = 0; i < n; i++)
		{
			iWomenVisited[i] = -1;
			iMenPair[i] = -1;
		}
		do
		{
			flag = false;
			for (int i = 0; i < n; i++)
			{
				if (iWomenVisited[i] == -1)
				{
					String[] sWomenChoices = hmWomenChoices.get(i);
					for (int w = 0; w < sWomenChoices.length; w++)
					{
						int iWomenChoice = Integer.parseInt(sWomenChoices[w]) - 1;
						if (iMenPair[iWomenChoice] == -1)
						{
							iWomenVisited[i] = iWomenChoice;
							iMenPair[iWomenChoice] = i;
							break;
						}
						else
						{
							String[] sMenChoices = hmMenChoices.get(iWomenChoice);
							for (int m = 0; m < sMenChoices.length; m++)
							{
								int iMenChoice = Integer.parseInt(sMenChoices[m]) - 1;
								if (iMenChoice == i)
								{
									for (int k = 0; k < n; k++)
									{
										if (iWomenVisited[k] == iWomenChoice)
										{
											iWomenVisited[k] = -1;
											break;
										}
									}
									iWomenVisited[i] = iWomenChoice;
									iMenPair[iWomenChoice] = i;
									flag = true;
									break;
								}
								else if (iMenChoice == iMenPair[iWomenChoice])
								{
									break;
								}
							}
							if (flag)
							{
								break;
							}
						}
					}
				}
			}
		} while (flag);
		updateMaxIndex(iMenPair);
	}

	public static void updateMaxIndex(int[] iMenVisited)
	{
		for (int k = 0; k < n; k++)
		{
			String[] sMenChoices = hmMenChoices.get(k);
			for (int m = 0; m < sMenChoices.length; m++)
			{
				int iMenChoice = Integer.parseInt(sMenChoices[m]) - 1;
				if (iMenChoice == iMenVisited[k])
				{
					iMenPref[k][1] = m;
				}
			}
		}
	}

	/**
	 * To create pairs with all possible combinations
	 * 
	 * @param iWomenArr
	 *            - Array to keep visited women
	 * @param iCurrMan
	 *            - Current man to pair with
	 */
	public static void createPairs(int[] iWomenArr, int iCurrMan)
	{
		if (iCurrMan == n)
		{
			if (checkForElimination())
			{
				totalCount++;
				checkStability();
				return;
			}
		}
		for (int i = 0; i < iWomenArr.length; i++)
		{
			if (iWomenArr[i] == 0)
			{
				hmMenPair.put(iCurrMan, i);
				hmWomenPair.put(i, iCurrMan);
				iWomenArr[i] = 1;
				createPairs(iWomenArr.clone(), iCurrMan + 1);
				iWomenArr[i] = 0;
			}
		}
	}

	/**
	 * @return true if the current pair of matches are invalid or unstable
	 */
	private static boolean checkForElimination()
	{
		boolean flag = true;
		int j = 0;
		for (int i = 0; i < n; i++)
		{
			int iManCurrMatch = hmMenPair.get(i);
			String[] sManChoices = hmMenChoices.get(i);
			for (j = 0; j < n; j++)
			{
				int iChoice = Integer.parseInt(sManChoices[j]) - 1;
				if (iChoice == iManCurrMatch)
				{
					break;
				}
			}
			if (j >= iMenPref[i][0] && j <= iMenPref[i][1])
			{
				continue;
			}
			else
			{
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * To find the current matches are stable or not
	 */
	public static void checkStability()
	{
		for (int i = 0; i < n; i++)
		{
			int iMatchedWoman = hmMenPair.get(i);
			String[] sMenChoices = hmMenChoices.get(i);
			for (int j = 0; j < n; j++)
			{
				int iManChoice = Integer.parseInt(sMenChoices[j]) - 1;
				if (iMatchedWoman == iManChoice)
				{
					break;
				}
				else
				{
					int iMacthedMan = hmWomenPair.get(iManChoice);
					String[] sWomenChoices = hmWomenChoices.get(iManChoice);
					for (int k = 0; k < n; k++)
					{
						int iWomanChoice = Integer.parseInt(sWomenChoices[k]) - 1;
						if (i == iWomanChoice)
						{
							return;
						}
						else if (iMacthedMan == iWomanChoice)
						{
							break;
						}
					}
				}
			}
		}
		iStableMatch++;
	}
}
