import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author jawal
 *
 */
public class ClosestPairOfPoints
{
	static String file_name = "input2.txt";
	static File file = null;
	static BufferedReader br = null;
	static int n = 0;
	static ArrayList<int[]> alPointsXSorted = null;
	static ArrayList<int[]> alPointsYSorted = null;
	static ArrayList<int[]> alPointsYSortedMiddle = null;
	static int iMinDist = -1;

	public static void main(String[] args) throws IOException
	{
		try
		{
			readInputFile();
			// sort the arraylist of point on x axis
			alPointsXSorted = mergeSort(alPointsXSorted, 0);
			// sort the arraylist of point on y axis
			alPointsYSorted = mergeSort(alPointsYSorted, 1);
			// printPoints(alPointsXSorted);
			// printPoints(alPointsYSorted);
			// find the minimum distance
			minDist();
			System.out.println(iMinDist);
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
		alPointsXSorted = new ArrayList<>();
		alPointsYSorted = new ArrayList<>();
		String[] arrT = new String[2];
		int[] iArr = null;
		while ((st = br.readLine()) != null)
		{
			arrT = st.split(" ");
			iArr = new int[2];
			iArr[0] = Integer.parseInt(arrT[0]);
			iArr[1] = Integer.parseInt(arrT[1]);
			alPointsXSorted.add(iArr);
			alPointsYSorted.add(iArr);
			n++;
		}
	}

	/**
	 * Print the given array list
	 * 
	 * @param alPoints
	 *            - the arraylist to print
	 */
	/*
	 * private static void printPoints(ArrayList<int[]> alPoints) { int[] arrT = new int[2]; for (int i = 0; i <
	 * alPoints.size(); i++) { arrT = alPoints.get(i); System.out.println(arrT[0] + " " + arrT[1]); } }
	 */
	/**
	 * @param iArr
	 *            - Array to sort
	 * @param a
	 *            - 0:x-axis 1:y-axis
	 * @return
	 */
	public static ArrayList<int[]> mergeSort(ArrayList<int[]> iArr, int a)
	{
		divide_sort(iArr, 0, iArr.size() - 1, a);
		return iArr;
	}

	/**
	 * To divide for sorting
	 * 
	 * @param iArr
	 *            - the array to sort
	 * @param iFirst
	 *            - first element
	 * @param iLast
	 *            - last element
	 * @param a
	 *            - axis to sort(x or y)
	 */
	private static void divide_sort(ArrayList<int[]> iArr, int iFirst, int iLast, int a)
	{
		if (iFirst < iLast)
		{
			int iMiddle = (iFirst + iLast) / 2;
			divide_sort(iArr, iFirst, iMiddle, a);
			divide_sort(iArr, iMiddle + 1, iLast, a);
			conquer_sort(iArr, iFirst, iMiddle, iLast, a);
		}
	}

	private static void conquer_sort(ArrayList<int[]> iArr, int iFirst, int iMiddle, int iLast, int a)
	{
		int iL = iMiddle - iFirst + 1;
		int iR = iLast - iMiddle;
		// Creating temp arrays
		ArrayList<int[]> iLeftArr = new ArrayList<>();
		ArrayList<int[]> iRightArr = new ArrayList<>();
		// Adding data in temp arrays
		for (int i = 0; i < iL; i++)
		{
			iLeftArr.add(iArr.get(iFirst + i));
		}
		for (int i = 0; i < iR; i++)
		{
			iRightArr.add(iArr.get(iMiddle + i + 1));
		}
		int i = 0;
		int j = 0;
		int k = iFirst;
		int[] iLeftTemp = new int[2];
		int[] iRightTemp = new int[2];
		// merging the temp arrays
		while (i < iL && j < iR)
		{
			iLeftTemp = iLeftArr.get(i);
			iRightTemp = iRightArr.get(j);
			if (iLeftTemp[a] <= iRightTemp[a])
			{
				iArr.set(k, iLeftTemp);
				i++;
			}
			else
			{
				iArr.set(k, iRightTemp);
				j++;
			}
			k++;
		}
		// adding remaining of left array
		while (i < iL)
		{
			iArr.set(k++, iLeftArr.get(i));
			i++;
		}
		// adding remaining of right array
		while (j < iR)
		{
			iArr.set(k++, iRightArr.get(j));
			j++;
		}
	}

	private static void minDist()
	{
		divide_dist(0, n - 1);
		get_middleStrip();
		find_distMiddle();
	}

	/**
	 * divide the array for finding min distance
	 * 
	 * @param iFirst
	 *            - first element
	 * @param iLast
	 *            - last element
	 */
	private static void divide_dist(int iFirst, int iLast)
	{
		if (iFirst < iLast)
		{
			int iMiddle = (iFirst + iLast) / 2;
			divide_dist(iFirst, iMiddle);
			divide_dist(iMiddle + 1, iLast);
			find_distSubArrays(iFirst, iMiddle, iLast);
		}
	}

	/**
	 * To find min distance in subarrays
	 * 
	 * @param iFirst
	 *            - first element
	 * @param iMiddle
	 *            - middle element
	 * @param iLast
	 *            - last element
	 */
	private static void find_distSubArrays(int iFirst, int iMiddle, int iLast)
	{
		int iL = iMiddle - iFirst + 1;
		int iR = iLast - iMiddle;
		// Creating temp arrays
		ArrayList<int[]> iLeftArr = new ArrayList<>();
		ArrayList<int[]> iRightArr = new ArrayList<>();
		// Adding data in temp arrays
		for (int i = 0; i < iL; i++)
		{
			iLeftArr.add(alPointsXSorted.get(iFirst + i));
		}
		for (int i = 0; i < iR; i++)
		{
			iRightArr.add(alPointsXSorted.get(iMiddle + i + 1));
		}
		int x1, x2, y1, y2;
		int iDL = -1;
		if (iLeftArr.size() == 2)
		{
			x1 = iLeftArr.get(0)[0];
			y1 = iLeftArr.get(0)[1];
			x2 = iLeftArr.get(1)[0];
			y2 = iLeftArr.get(1)[1];
			iDL = getDist(x1, y1, x2, y2);
		}
		int iDR = -1;
		if (iRightArr.size() == 2)
		{
			x1 = iRightArr.get(0)[0];
			y1 = iRightArr.get(0)[1];
			x2 = iRightArr.get(1)[0];
			y2 = iRightArr.get(1)[1];
			iDR = getDist(x1, y1, x2, y2);
		}
		int d = -1;
		if (iDL != -1 && iDR != -1)
		{
			d = Math.min(iDL, iDR);
		}
		else if (iDL == -1)
		{
			d = iDR;
		}
		else
		{
			d = iDL;
		}
		if (iMinDist == -1 || iMinDist > d)
		{
			iMinDist = d;
		}
	}

	/**
	 * To git the points lying in middle strip based on min dist
	 */
	private static void get_middleStrip()
	{
		int iMiddle = (0 + n) / 2;
		int xMid = alPointsXSorted.get(iMiddle)[0];
		int x1, y1, d;
		alPointsYSortedMiddle = new ArrayList<>();
		int[] iArr = null;
		for (int i = 0; i < n; i++)
		{
			x1 = alPointsYSorted.get(i)[0];
			y1 = alPointsYSorted.get(i)[1];
			d = Math.abs(x1 - xMid);
			if (iMinDist == -1 || d <= Math.sqrt(iMinDist))
			{
				iArr = new int[2];
				iArr[0] = x1;
				iArr[1] = y1;
				alPointsYSortedMiddle.add(iArr);
			}
		}
		// printPoints(alPointsYSortedMiddle);
	}

	/**
	 * To find min dist in middle strip
	 */
	private static void find_distMiddle()
	{
		int x1, y1;
		int x2, y2;
		for (int i = 0; i < alPointsYSortedMiddle.size(); i++)
		{
			x1 = alPointsYSortedMiddle.get(i)[0];
			y1 = alPointsYSortedMiddle.get(i)[1];
			for (int j = i + 1; j < alPointsYSortedMiddle.size() && (iMinDist == -1 || Math.abs(y1 - alPointsYSortedMiddle.get(j)[1]) < Math.sqrt(iMinDist)); j++)
			{
				x2 = alPointsYSortedMiddle.get(j)[0];
				y2 = alPointsYSortedMiddle.get(j)[1];
				iMinDist = getDist(x1, y1, x2, y2);
			}
		}
	}

	/**
	 * Get distance between two points
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return
	 */
	private static int getDist(int x1, int y1, int x2, int y2)
	{
		int x = Math.abs(x1 - x2);
		int y = Math.abs(y1 - y2);
		int d = x * x + y * y;
		return d;
	}
}
