package project.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class HillClimblingSearch {
	
	public final static int[] goalStateEightPuzzle = {1,2,3,4,5,6,7,8,0};
	public final static int[] goalStateFifteenPuzzle = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
	public static int[] currentState;
	public static int currentValueHeuristic;
	public static int typePuzzle;
	
	public static int numCanMove;
	public static int[][] tempAllState;
	static ArrayList<Integer> positionMove = new ArrayList<Integer>();
	
	static Stack queueStateProcess = new Stack();
	static ArrayList<Integer> queueValueHeuristic = new ArrayList<Integer>();
	public static boolean statusGoal = false;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter type 8 puzzle or 15 puzzle : ");
		typePuzzle = sc.nextInt();
		int[] inputState = new int[typePuzzle+1];
		if(typePuzzle == 8 || typePuzzle == 15)
		{
			System.out.print("Please enter init state : ");
			for(int i = 0; i < inputState.length ;i++)
			{
				inputState[i] = sc.nextInt();
				if(typePuzzle == 8)
				{
					if(inputState[i] > 8 || inputState[i] < 0)
					{
						System.out.println("Invalid input!!!");
						System.exit(0);
					}
				}
				else
				{
					if(inputState[i] > 15 || inputState[i] < 0)
					{
						System.out.println("Invalid input!!!");
						System.exit(0);
					}
				}
			}
			
		}
		else
		{
			System.out.println("Incorrect puzzle type!!!");
			System.exit(0);
		}
		
	
		if(typePuzzle == 8)
		{
			currentState = inputState;
			currentValueHeuristic = checkInitStateHeuristicEigthPuzzle();
			
			System.out.println();
			System.out.println("Init State");
			System.out.println(currentState[0] + "|" + currentState[1] + "|" + currentState[2]);
			System.out.println(currentState[3] + "|" + currentState[4] + "|" + currentState[5]);
			System.out.println(currentState[6] + "|" + currentState[7] + "|" + currentState[8]);
			System.out.println("Heuristic init state = " + currentValueHeuristic);
			System.out.println();
			System.out.println("Goal State");
			System.out.println(goalStateEightPuzzle[0] + "|" + goalStateEightPuzzle[1] + "|" + goalStateEightPuzzle[2]);
			System.out.println(goalStateEightPuzzle[3] + "|" + goalStateEightPuzzle[4] + "|" + goalStateEightPuzzle[5]);
			System.out.println(goalStateEightPuzzle[6] + "|" + goalStateEightPuzzle[7] + "|" + goalStateEightPuzzle[8]);
			System.out.println();
			System.out.println("----------------------------------------------");
			
			System.out.println("Start process!!!");
			System.out.println();
			processEightPuzzle();
		}
		else
		{
			currentState = inputState;
			currentValueHeuristic = checkInitStateHeuristicFifteenPuzzle();
			
			System.out.println();
			System.out.println("Init State");
			System.out.println(currentState[0] + " |" + currentState[1] + " |" + currentState[2] + " |" + currentState[3]);
			System.out.println(currentState[4] + " |" + currentState[5] + " |" + currentState[6] + " |" + currentState[7]);
			System.out.println(currentState[8] + " |" + currentState[9] + " |" + currentState[10] + "|" + currentState[11]);
			System.out.println(currentState[12] + "|" + currentState[13] + "|" + currentState[14] + "|" + currentState[15]);
			System.out.println("Heuristic init state = " + currentValueHeuristic);
			System.out.println();
			System.out.println("Goal State");
			System.out.println(goalStateFifteenPuzzle[0] + " |" + goalStateFifteenPuzzle[1] + " |" + goalStateFifteenPuzzle[2] + " |" + goalStateFifteenPuzzle[3]);
			System.out.println(goalStateFifteenPuzzle[4] + " |" + goalStateFifteenPuzzle[5] + " |" + goalStateFifteenPuzzle[6] + " |" + goalStateFifteenPuzzle[7]);
			System.out.println(goalStateFifteenPuzzle[8] + " |" + goalStateFifteenPuzzle[9] + " |" + goalStateFifteenPuzzle[10] + "|" + goalStateFifteenPuzzle[11]);
			System.out.println(goalStateFifteenPuzzle[12] + "|" + goalStateFifteenPuzzle[13] + "|" + goalStateFifteenPuzzle[14] + "|" + goalStateFifteenPuzzle[15]);
			System.out.println();
			System.out.println("----------------------------------------------");
			
			System.out.println("Start process!!!");
			System.out.println();
			processFifteenPuzzle();
		}
		System.exit(0);
	}
	
	//ProcessEightPuzzle
	public static void processEightPuzzle(){
		queueStateProcess.push(currentState);
		queueValueHeuristic.add(currentValueHeuristic);
		checkGoalStateEightPuzzle();
	}
	
	public static int checkInitStateHeuristicEigthPuzzle()
	{
		int countHeurictis = 0;
		for(int i = 0; i < currentState.length ; i++)
		{
			if(currentState[i] != goalStateEightPuzzle[i] && currentState[i] != 0)
			{
				countHeurictis++;
			}
		}
		return countHeurictis;
	}
	
	public static void checkGoalStateEightPuzzle()
	{
		if(Arrays.equals(currentState, goalStateEightPuzzle))
		{
			tempAllState = null;
			positionMove = null;
			System.out.println("End process!!!");
			System.out.println("----------------------------------------------");
			System.out.println();
			System.out.println("This is goal state.");
			stackToArray();
		}
		else if(statusGoal != false)
		{
			tempAllState = null;
			positionMove = null;
			System.out.println("End process!!!");
			System.out.println("----------------------------------------------");
			System.out.println();
			System.out.println("No goal state,This is the best state.");
			stackToArray();
		}
		else
		{
			findZeroEightPuzzle();
		}
	}
	
	public static void findZeroEightPuzzle()
	{
		int positionZero = 0;
		for(int i = 0 ; i < currentState.length ; i++){
			if(currentState[i] == 0)
			{
				break;
			}
			else
			{
				positionZero++;
			}
		}
		canMoveEightPuzzle(positionZero);
	}
	
	public static void canMoveEightPuzzle(int positionZero)
	{
		if(positionZero == 0)
		{
			numCanMove = 2;
			positionMove.add(1);
			positionMove.add(3);
		}
		else if(positionZero == 1)
		{
			numCanMove = 3;
			positionMove.add(0);
			positionMove.add(4);
			positionMove.add(2);
		}
		else if(positionZero == 2)
		{
			numCanMove = 2;
			positionMove.add(1);
			positionMove.add(5);
		}
		else if(positionZero == 3)
		{
			numCanMove = 3;
			positionMove.add(0);
			positionMove.add(4);
			positionMove.add(6);
		}
		else if(positionZero == 4)
		{
			numCanMove = 4;
			positionMove.add(1);
			positionMove.add(3);
			positionMove.add(5);
			positionMove.add(7);
		}
		else if(positionZero == 5)
		{
			numCanMove = 3;
			positionMove.add(2);
			positionMove.add(4);
			positionMove.add(8);
		}
		else if(positionZero == 6)
		{
			numCanMove = 2;
			positionMove.add(3);
			positionMove.add(7);
		}
		else if(positionZero == 7)
		{
			numCanMove = 3;
			positionMove.add(6);
			positionMove.add(4);
			positionMove.add(8);
		}
		else
		{
			numCanMove = 2;
			positionMove.add(5);
			positionMove.add(7);
		}
		System.out.println("Can move zero = " + numCanMove);
		moveZeroEightPuzzle(positionZero);
	}
	
	public static void moveZeroEightPuzzle(int positionZero)
	{
		if(numCanMove == 2)
		{
			int[] tempState1 = Arrays.copyOf(currentState, 9);
			int[] tempState2 = Arrays.copyOf(currentState, 9);
			int tempvalue;
			for(int i = 0 ; i < numCanMove ; i++)
			{
				if(i == 0)
				{
					tempvalue = tempState1[positionMove.get(0)];
					tempState1[positionZero] = tempvalue;
					tempState1[positionMove.remove(0)] = 0;
				}
				else
				{
					tempvalue = tempState2[positionMove.get(0)];
					tempState2[positionZero] = tempvalue;
					tempState2[positionMove.remove(0)] = 0;
				}
			}
			tempAllState = new int[][]{tempState1,tempState2};
			tempState1 = null;
			tempState2 = null;
		}
		
		else if(numCanMove == 3)
		{
			int[] tempState1 = Arrays.copyOf(currentState, 9);
			int[] tempState2 = Arrays.copyOf(currentState, 9);
			int[] tempState3 = Arrays.copyOf(currentState, 9);
			int tempvalue;
			for(int i = 0 ; i < numCanMove ; i++)
			{
				if(i == 0)
				{
					tempvalue = tempState1[positionMove.get(0)];
					tempState1[positionZero] = tempvalue;
					tempState1[positionMove.remove(0)] = 0;
				}
				else if(i == 1)
				{
					tempvalue = tempState2[positionMove.get(0)];
					tempState2[positionZero] = tempvalue;
					tempState2[positionMove.remove(0)] = 0;
				}
				else
				{
					tempvalue = tempState3[positionMove.get(0)];
					tempState3[positionZero] = tempvalue;
					tempState3[positionMove.remove(0)] = 0;
				}
			}
			tempAllState = new int[][]{tempState1,tempState2,tempState3};
			tempState1 = null;
			tempState2 = null;
			tempState3 = null;
		}
		
		else
		{
			int[] tempState1 = Arrays.copyOf(currentState, 9);
			int[] tempState2 = Arrays.copyOf(currentState, 9);
			int[] tempState3 = Arrays.copyOf(currentState, 9);
			int[] tempState4 = Arrays.copyOf(currentState, 9);
			int tempvalue;
			for(int i = 0 ; i < numCanMove ; i++)
			{
				if(i == 0)
				{
					tempvalue = tempState1[positionMove.get(0)];
					tempState1[positionZero] = tempvalue;
					tempState1[positionMove.remove(0)] = 0;
				}
				else if(i == 1)
				{
					tempvalue = tempState2[positionMove.get(0)];
					tempState2[positionZero] = tempvalue;
					tempState2[positionMove.remove(0)] = 0;
				}
				else if(i == 2)
				{
					tempvalue = tempState3[positionMove.get(0)];
					tempState3[positionZero] = tempvalue;
					tempState3[positionMove.remove(0)] = 0;
				}
				else
				{
					tempvalue = tempState4[positionMove.get(0)];
					tempState4[positionZero] = tempvalue;
					tempState4[positionMove.remove(0)] = 0;
				}
			}
			tempAllState = new int[][]{tempState1,tempState2,tempState3,tempState4};
			tempState1 = null;
			tempState2 = null;
			tempState3 = null;
			tempState4 = null;
		}
		showTempEightPuzzle();
		checkTempHeuristicEightPuzzle();
	}
	
	public static void checkTempHeuristicEightPuzzle()
	{
		int[] valueStateTempHeuristic = new int[numCanMove];
		for(int i = 0; i < numCanMove ; i++)
		{
			int countHeurictis = 0;
			int[] tempState = Arrays.copyOf(tempAllState[i], 9);
			for(int j = 0 ; j < tempState.length ; j++)
			{
				if(tempState[j] != goalStateEightPuzzle[j] && tempState[j] != 0)
				{
					countHeurictis++;
				}
			}
			valueStateTempHeuristic[i] = countHeurictis;
			tempState = null;
		}
		
		System.out.println("Heuristic = " + Arrays.toString(valueStateTempHeuristic));
		System.out.println();
		compareTempHeuristicEightPuzzle(valueStateTempHeuristic);
	}
	
	public static void compareTempHeuristicEightPuzzle(int[] valueStateTempHeuristic)
	{
		if(numCanMove == 2)
		{
			if(valueStateTempHeuristic[0] == valueStateTempHeuristic[1])
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
						Random rand = new Random();
						int tempRand = rand.nextInt(2);
						currentValueHeuristic = valueStateTempHeuristic[tempRand];
						currentState = tempAllState[tempRand];
						processEightPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateEightPuzzle();
				}
				
			}
			else
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[0];
					currentState = tempAllState[0];
					processEightPuzzle();
				}
				else if(valueStateTempHeuristic[1] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[1];
					currentState = tempAllState[1];
					processEightPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateEightPuzzle();
				}
			}
		}
		
		else if(numCanMove == 3)
		{
			if(valueStateTempHeuristic[0] == valueStateTempHeuristic[1] && valueStateTempHeuristic[0] == valueStateTempHeuristic[2])
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					Random rand = new Random();
					int tempRand = rand.nextInt(3);
					currentValueHeuristic = valueStateTempHeuristic[tempRand];
					currentState = tempAllState[tempRand];
					processEightPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateEightPuzzle();
				}
			}
			else
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[0];
					currentState = tempAllState[0];
					processEightPuzzle();
				}
				else if(valueStateTempHeuristic[1] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[1];
					currentState = tempAllState[1];
					processEightPuzzle();
				}
				else if(valueStateTempHeuristic[2] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[2];
					currentState = tempAllState[2];
					processEightPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateEightPuzzle();
				}
			}
		}
		else
		{
			if(valueStateTempHeuristic[0] == valueStateTempHeuristic[1] && valueStateTempHeuristic[0] == valueStateTempHeuristic[2] && valueStateTempHeuristic[0] == valueStateTempHeuristic[3])
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					Random rand = new Random();
					int tempRand = rand.nextInt(4);
					currentValueHeuristic = valueStateTempHeuristic[tempRand];
					currentState = tempAllState[tempRand];
					processEightPuzzle();
				}
				else
				{
					statusGoal = true;
					 checkGoalStateEightPuzzle();
				}
			}
			else{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[0];
					currentState = tempAllState[0];
					processEightPuzzle();
				}
				else if(valueStateTempHeuristic[1] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[1];
					currentState = tempAllState[1];
					processEightPuzzle();
				}
				else if(valueStateTempHeuristic[2] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[2];
					currentState = tempAllState[2];
					processEightPuzzle();
				}
				else if(valueStateTempHeuristic[3] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[3];
					currentState = tempAllState[3];
					processEightPuzzle();
				}
				else
				{
					statusGoal = true;
					 checkGoalStateEightPuzzle();
				}
			}
		}
	}
	
	public static void showTempEightPuzzle()
	{
		if(numCanMove == 2)
		{
			System.out.println(tempAllState[0][0] + "|" + tempAllState[0][1] + "|" + tempAllState[0][2] + "	" + tempAllState[1][0] + "|" + tempAllState[1][1] + "|" + tempAllState[1][2]);
			System.out.println(tempAllState[0][3] + "|" + tempAllState[0][4] + "|" + tempAllState[0][5] + "	" + tempAllState[1][3] + "|" + tempAllState[1][4] + "|" + tempAllState[1][5]);
			System.out.println(tempAllState[0][6] + "|" + tempAllState[0][7] + "|" + tempAllState[0][8] + "	" + tempAllState[1][6] + "|" + tempAllState[1][7] + "|" + tempAllState[1][8]);
		}
		else if(numCanMove == 3)
		{
			System.out.println(tempAllState[0][0] + "|" + tempAllState[0][1] + "|" + tempAllState[0][2] + "	" + tempAllState[1][0] + "|" + tempAllState[1][1] + "|" + tempAllState[1][2] + "	" + tempAllState[2][0] + "|" + tempAllState[2][1] + "|" + tempAllState[2][2]);
			System.out.println(tempAllState[0][3] + "|" + tempAllState[0][4] + "|" + tempAllState[0][5] + "	" + tempAllState[1][3] + "|" + tempAllState[1][4] + "|" + tempAllState[1][5] + "	" + tempAllState[2][3] + "|" + tempAllState[2][4] + "|" + tempAllState[2][5]);
			System.out.println(tempAllState[0][6] + "|" + tempAllState[0][7] + "|" + tempAllState[0][8] + "	" + tempAllState[1][6] + "|" + tempAllState[1][7] + "|" + tempAllState[1][8] + "	" + tempAllState[2][6] + "|" + tempAllState[2][7] + "|" + tempAllState[2][8]);
		}
		else
		{
			System.out.println(tempAllState[0][0] + "|" + tempAllState[0][1] + "|" + tempAllState[0][2] + "	" + tempAllState[1][0] + "|" + tempAllState[1][1] + "|" + tempAllState[1][2] + "	" + tempAllState[2][0] + "|" + tempAllState[2][1] + "|" + tempAllState[2][2] + "	" + tempAllState[3][0] + "|" + tempAllState[3][1] + "|" + tempAllState[3][2]);
			System.out.println(tempAllState[0][3] + "|" + tempAllState[0][4] + "|" + tempAllState[0][5] + "	" + tempAllState[1][3] + "|" + tempAllState[1][4] + "|" + tempAllState[1][5] + "	" + tempAllState[2][3] + "|" + tempAllState[2][4] + "|" + tempAllState[2][5] + "	" + tempAllState[3][3] + "|" + tempAllState[3][4] + "|" + tempAllState[3][5]);
			System.out.println(tempAllState[0][6] + "|" + tempAllState[0][7] + "|" + tempAllState[0][8] + "	" + tempAllState[1][6] + "|" + tempAllState[1][7] + "|" + tempAllState[1][8] + "	" + tempAllState[2][6] + "|" + tempAllState[2][7] + "|" + tempAllState[2][8] + "	" + tempAllState[3][6] + "|" + tempAllState[3][7] + "|" + tempAllState[3][8]);
		}
	}
	
	public static void stackToArray(){
		int[][] stack = new int[queueStateProcess.size()][];
		int temp_i = 0;
		while(!queueStateProcess.empty())
		{
			stack[temp_i] = (int[]) queueStateProcess.pop();
			temp_i++;
		}
		int[][] sortStack = new int[stack.length][];
		for(int i = 0 ; i < stack.length ; i++)
		{
			sortStack[i] = stack[stack.length-(i+1)]; 
		}
		if(typePuzzle == 8)
		{
			showEndProcessEightPuzzle(sortStack);
		}
		else
		{
			showEndProcessFifteenPuzzle(sortStack);
		}
		
	}
	
	public static void showEndProcessEightPuzzle(int[][] arr){
		for(int i = 0 ; i < arr.length ; i++)
		{
			System.out.println(arr[i][0] + "|" + arr[i][1] + "|" + arr[i][2]);
			System.out.println(arr[i][3] + "|" + arr[i][4] + "|" + arr[i][5]);
			System.out.println(arr[i][6] + "|" + arr[i][7] + "|" + arr[i][8]);
			System.out.println();
		}
	}
	
	
	//ProcessFifteenPuzzle
	public static void processFifteenPuzzle(){
		queueStateProcess.push(currentState);
		queueValueHeuristic.add(currentValueHeuristic);
		checkGoalStateFifteenPuzzle();
	}
	
	public static int checkInitStateHeuristicFifteenPuzzle()
	{
		int countHeurictis = 0;
		for(int i = 0; i < currentState.length ; i++)
		{
			if(currentState[i] != goalStateFifteenPuzzle[i] && currentState[i] != 0)
			{
				countHeurictis++;
			}
		}
		return countHeurictis;
	}
	
	public static void checkGoalStateFifteenPuzzle()
	{
		if(Arrays.equals(currentState, goalStateFifteenPuzzle))
		{
			tempAllState = null;
			positionMove = null;
			System.out.println("End process!!!");
			System.out.println("----------------------------------------------");
			System.out.println();
			System.out.println("This is goal state.");
			stackToArray();
		}
		else if(statusGoal != false)
		{
			tempAllState = null;
			positionMove = null;
			System.out.println("End process!!!");
			System.out.println("----------------------------------------------");
			System.out.println();
			System.out.println("No goal state,This is the best state.");
			stackToArray();
		}
		else
		{
			findZeroFifteenPuzzle();
		}
	}
	
	public static void findZeroFifteenPuzzle()
	{
		int positionZero = 0;
		for(int i = 0 ; i < currentState.length ; i++){
			if(currentState[i] == 0)
			{
				break;
			}
			else
			{
				positionZero++;
			}
		}
		canMoveFifteenPuzzle(positionZero);
	}
	
	public static void canMoveFifteenPuzzle(int positionZero)
	{
		if(positionZero == 0)
		{
			numCanMove = 2;
			positionMove.add(1);
			positionMove.add(4);
		}
		else if(positionZero == 1)
		{
			numCanMove = 3;
			positionMove.add(0);
			positionMove.add(5);
			positionMove.add(2);
		}
		else if(positionZero == 2)
		{
			numCanMove = 3;
			positionMove.add(1);
			positionMove.add(6);
			positionMove.add(3);
		}
		else if(positionZero == 3)
		{
			numCanMove = 2;
			positionMove.add(2);
			positionMove.add(7);
		}
		else if(positionZero == 4)
		{
			numCanMove = 3;
			positionMove.add(0);
			positionMove.add(5);
			positionMove.add(8);
		}
		else if(positionZero == 5)
		{
			numCanMove = 4;
			positionMove.add(1);
			positionMove.add(4);
			positionMove.add(6);
			positionMove.add(9);
		}
		else if(positionZero == 6)
		{
			numCanMove = 4;
			positionMove.add(2);
			positionMove.add(5);
			positionMove.add(10);
			positionMove.add(7);
		}
		else if(positionZero == 7)
		{
			numCanMove = 3;
			positionMove.add(3);
			positionMove.add(6);
			positionMove.add(11);
		}
		else if(positionZero == 8)
		{
			numCanMove = 3;
			positionMove.add(4);
			positionMove.add(9);
			positionMove.add(12);
		}
		else if(positionZero == 9)
		{
			numCanMove = 4;
			positionMove.add(5);
			positionMove.add(8);
			positionMove.add(13);
			positionMove.add(10);
		}
		else if(positionZero == 10)
		{
			numCanMove = 4;
			positionMove.add(6);
			positionMove.add(9);
			positionMove.add(14);
			positionMove.add(11);
		}
		else if(positionZero == 11)
		{
			numCanMove = 3;
			positionMove.add(7);
			positionMove.add(10);
			positionMove.add(15);
		}
		else if(positionZero == 12)
		{
			numCanMove = 2;
			positionMove.add(8);
			positionMove.add(13);
		}
		else if(positionZero == 13)
		{
			numCanMove = 3;
			positionMove.add(12);
			positionMove.add(9);
			positionMove.add(14);
		}
		else if(positionZero == 14)
		{
			numCanMove = 3;
			positionMove.add(13);
			positionMove.add(10);
			positionMove.add(15);
		}
		else
		{
			numCanMove = 2;
			positionMove.add(14);
			positionMove.add(11);
		}
		System.out.println("Can move zero = " + numCanMove);
		moveZeroFifteenPuzzle(positionZero);
	}
	
	public static void moveZeroFifteenPuzzle(int positionZero)
	{
		if(numCanMove == 2)
		{
			int[] tempState1 = Arrays.copyOf(currentState, 16);
			int[] tempState2 = Arrays.copyOf(currentState, 16);
			int tempvalue;
			for(int i = 0 ; i < numCanMove ; i++)
			{
				if(i == 0)
				{
					tempvalue = tempState1[positionMove.get(0)];
					tempState1[positionZero] = tempvalue;
					tempState1[positionMove.remove(0)] = 0;
				}
				else
				{
					tempvalue = tempState2[positionMove.get(0)];
					tempState2[positionZero] = tempvalue;
					tempState2[positionMove.remove(0)] = 0;
				}
			}
			tempAllState = new int[][]{tempState1,tempState2};
			tempState1 = null;
			tempState2 = null;
		}
		
		else if(numCanMove == 3)
		{
			int[] tempState1 = Arrays.copyOf(currentState, 16);
			int[] tempState2 = Arrays.copyOf(currentState, 16);
			int[] tempState3 = Arrays.copyOf(currentState, 16);
			int tempvalue;
			for(int i = 0 ; i < numCanMove ; i++)
			{
				if(i == 0)
				{
					tempvalue = tempState1[positionMove.get(0)];
					tempState1[positionZero] = tempvalue;
					tempState1[positionMove.remove(0)] = 0;
				}
				else if(i == 1)
				{
					tempvalue = tempState2[positionMove.get(0)];
					tempState2[positionZero] = tempvalue;
					tempState2[positionMove.remove(0)] = 0;
				}
				else
				{
					tempvalue = tempState3[positionMove.get(0)];
					tempState3[positionZero] = tempvalue;
					tempState3[positionMove.remove(0)] = 0;
				}
			}
			tempAllState = new int[][]{tempState1,tempState2,tempState3};
			tempState1 = null;
			tempState2 = null;
			tempState3 = null;
		}
		
		else
		{
			int[] tempState1 = Arrays.copyOf(currentState, 16);
			int[] tempState2 = Arrays.copyOf(currentState, 16);
			int[] tempState3 = Arrays.copyOf(currentState, 16);
			int[] tempState4 = Arrays.copyOf(currentState, 16);
			int tempvalue;
			for(int i = 0 ; i < numCanMove ; i++)
			{
				if(i == 0)
				{
					tempvalue = tempState1[positionMove.get(0)];
					tempState1[positionZero] = tempvalue;
					tempState1[positionMove.remove(0)] = 0;
				}
				else if(i == 1)
				{
					tempvalue = tempState2[positionMove.get(0)];
					tempState2[positionZero] = tempvalue;
					tempState2[positionMove.remove(0)] = 0;
				}
				else if(i == 2)
				{
					tempvalue = tempState3[positionMove.get(0)];
					tempState3[positionZero] = tempvalue;
					tempState3[positionMove.remove(0)] = 0;
				}
				else
				{
					tempvalue = tempState4[positionMove.get(0)];
					tempState4[positionZero] = tempvalue;
					tempState4[positionMove.remove(0)] = 0;
				}
			}
			tempAllState = new int[][]{tempState1,tempState2,tempState3,tempState4};
			tempState1 = null;
			tempState2 = null;
			tempState3 = null;
			tempState4 = null;
		}
		showTempFifteenPuzzle();
		checkTempHeuristicFifteenPuzzle();
	}
	
	public static void checkTempHeuristicFifteenPuzzle()
	{
		int[] valueStateTempHeuristic = new int[numCanMove];
		for(int i = 0; i < numCanMove ; i++)
		{
			int countHeurictis = 0;
			int[] tempState = Arrays.copyOf(tempAllState[i], 16);
			for(int j = 0 ; j < tempState.length ; j++)
			{
				if(tempState[j] != goalStateFifteenPuzzle[j] && tempState[j] != 0)
				{
					countHeurictis++;
				}
			}
			valueStateTempHeuristic[i] = countHeurictis;
			tempState = null;
		}
		
		System.out.println("Heuristic = " + Arrays.toString(valueStateTempHeuristic));
		System.out.println();
		compareTempHeuristicFifteenPuzzle(valueStateTempHeuristic);
	}
	
	public static void compareTempHeuristicFifteenPuzzle(int[] valueStateTempHeuristic)
	{
		if(numCanMove == 2)
		{
			if(valueStateTempHeuristic[0] == valueStateTempHeuristic[1])
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
						Random rand = new Random();
						int tempRand = rand.nextInt(2);
						currentValueHeuristic = valueStateTempHeuristic[tempRand];
						currentState = tempAllState[tempRand];
						processFifteenPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateFifteenPuzzle();
				}
				
			}
			else
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[0];
					currentState = tempAllState[0];
					processFifteenPuzzle();
				}
				else if(valueStateTempHeuristic[1] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[1];
					currentState = tempAllState[1];
					processFifteenPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateFifteenPuzzle();
				}
			}
		}
		
		else if(numCanMove == 3)
		{
			if(valueStateTempHeuristic[0] == valueStateTempHeuristic[1] && valueStateTempHeuristic[0] == valueStateTempHeuristic[2])
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					Random rand = new Random();
					int tempRand = rand.nextInt(3);
					currentValueHeuristic = valueStateTempHeuristic[tempRand];
					currentState = tempAllState[tempRand];
					processFifteenPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateFifteenPuzzle();
				}
			}
			else
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[0];
					currentState = tempAllState[0];
					processFifteenPuzzle();
				}
				else if(valueStateTempHeuristic[1] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[1];
					currentState = tempAllState[1];
					processFifteenPuzzle();
				}
				else if(valueStateTempHeuristic[2] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[2];
					currentState = tempAllState[2];
					processFifteenPuzzle();
				}
				else
				{
					statusGoal = true;
					checkGoalStateFifteenPuzzle();
				}
			}
		}
		else
		{
			if(valueStateTempHeuristic[0] == valueStateTempHeuristic[1] && valueStateTempHeuristic[0] == valueStateTempHeuristic[2] && valueStateTempHeuristic[0] == valueStateTempHeuristic[3])
			{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					Random rand = new Random();
					int tempRand = rand.nextInt(4);
					currentValueHeuristic = valueStateTempHeuristic[tempRand];
					currentState = tempAllState[tempRand];
					processFifteenPuzzle();
				}
				else
				{
					statusGoal = true;
					 checkGoalStateFifteenPuzzle();
				}
			}
			else{
				if(valueStateTempHeuristic[0] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[0];
					currentState = tempAllState[0];
					processFifteenPuzzle();
				}
				else if(valueStateTempHeuristic[1] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[1];
					currentState = tempAllState[1];
					processFifteenPuzzle();
				}
				else if(valueStateTempHeuristic[2] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[2];
					currentState = tempAllState[2];
					processFifteenPuzzle();
				}
				else if(valueStateTempHeuristic[3] < currentValueHeuristic)
				{
					currentValueHeuristic = valueStateTempHeuristic[3];
					currentState = tempAllState[3];
					processFifteenPuzzle();
				}
				else
				{
					statusGoal = true;
					 checkGoalStateFifteenPuzzle();
				}
			}
		}
	}
	
	public static void showTempFifteenPuzzle()
	{
		if(numCanMove == 2)
		{
			System.out.println(tempAllState[0][0] + " |" + tempAllState[0][1] + " |" + tempAllState[0][2] + " |" + tempAllState[0][3] + "	" + tempAllState[1][0] + " |" + tempAllState[1][1] + " |" + tempAllState[1][2] + " |" + tempAllState[1][3]);
			System.out.println(tempAllState[0][4] + " |" + tempAllState[0][5] + " |" + tempAllState[0][6] + " |" + tempAllState[0][7] + "	" + tempAllState[1][4] + " |" + tempAllState[1][5] + " |" + tempAllState[1][6] + " |" + tempAllState[1][7]);
			System.out.println(tempAllState[0][8] + " |" + tempAllState[0][9] + " |" + tempAllState[0][10] + "|" + tempAllState[0][11] +"	" + tempAllState[1][8] + " |" + tempAllState[1][9] + " |" + tempAllState[1][10] + "|" + tempAllState[1][11]);
			System.out.println(tempAllState[0][12] + "|" + tempAllState[0][13] + "|" + tempAllState[0][14] + "|" + tempAllState[0][15] +"	" + tempAllState[1][12] + "|" + tempAllState[1][13] + "|" + tempAllState[1][14] + "|" + tempAllState[1][15]);
		}
		else if(numCanMove == 3)
		{
			System.out.println(tempAllState[0][0] + " |" + tempAllState[0][1] + " |" + tempAllState[0][2] + " |" + tempAllState[0][3] + "	" + tempAllState[1][0] + " |" + tempAllState[1][1] + " |" + tempAllState[1][2] + " |" + tempAllState[1][3] + "	" + tempAllState[2][0] + " |" + tempAllState[2][1] + " |" + tempAllState[2][2] + " |" + tempAllState[2][3]);
			System.out.println(tempAllState[0][4] + " |" + tempAllState[0][5] + " |" + tempAllState[0][6] + " |" + tempAllState[0][7] + "	" + tempAllState[1][4] + " |" + tempAllState[1][5] + " |" + tempAllState[1][6] + " |" + tempAllState[1][7] + "	" + tempAllState[2][4] + " |" + tempAllState[2][5] + " |" + tempAllState[2][6] + " |" + tempAllState[2][7]);
			System.out.println(tempAllState[0][8] + " |" + tempAllState[0][9] + " |" + tempAllState[0][10] + "|" + tempAllState[0][11] +"	" + tempAllState[1][8] + " |" + tempAllState[1][9] + " |" + tempAllState[1][10] + "|" + tempAllState[1][11] +"	" + tempAllState[2][8] + " |" + tempAllState[2][9] + " |" + tempAllState[2][10] + "|" + tempAllState[2][11]);
			System.out.println(tempAllState[0][12] + "|" + tempAllState[0][13] + "|" + tempAllState[0][14] + "|" + tempAllState[0][15] +"	" + tempAllState[1][12] + "|" + tempAllState[1][13] + "|" + tempAllState[1][14] + "|" + tempAllState[1][15] +"	" + tempAllState[2][12] + "|" + tempAllState[2][13] + "|" + tempAllState[2][14] + "|" + tempAllState[2][15]);
		}
		else
		{
			System.out.println(tempAllState[0][0] + " |" + tempAllState[0][1] + " |" + tempAllState[0][2] + " |" + tempAllState[0][3] + "	" + tempAllState[1][0] + " |" + tempAllState[1][1] + " |" + tempAllState[1][2] + " |" + tempAllState[1][3] + "	" + tempAllState[2][0] + " |" + tempAllState[2][1] + " |" + tempAllState[2][2] + " |" + tempAllState[2][3] + "	" + tempAllState[3][0] + " |" + tempAllState[3][1] + " |" + tempAllState[3][2] + " |" + tempAllState[3][3]);
			System.out.println(tempAllState[0][4] + " |" + tempAllState[0][5] + " |" + tempAllState[0][6] + " |" + tempAllState[0][7] + "	" + tempAllState[1][4] + " |" + tempAllState[1][5] + " |" + tempAllState[1][6] + " |" + tempAllState[1][7] + "	" + tempAllState[2][4] + " |" + tempAllState[2][5] + " |" + tempAllState[2][6] + " |" + tempAllState[2][7] + "	" + tempAllState[3][4] + " |" + tempAllState[3][5] + " |" + tempAllState[3][6] + " |" + tempAllState[3][7]);
			System.out.println(tempAllState[0][8] + " |" + tempAllState[0][9] + " |" + tempAllState[0][10] + "|" + tempAllState[0][11] +"	" + tempAllState[1][8] + " |" + tempAllState[1][9] + " |" + tempAllState[1][10] + "|" + tempAllState[1][11] +"	" + tempAllState[2][8] + " |" + tempAllState[2][9] + " |" + tempAllState[2][10] + "|" + tempAllState[2][11] +"	" + tempAllState[3][8] + " |" + tempAllState[3][9] + " |" + tempAllState[3][10] + "|" + tempAllState[3][11]);
			System.out.println(tempAllState[0][12] + "|" + tempAllState[0][13] + "|" + tempAllState[0][14] + "|" + tempAllState[0][15] +"	" + tempAllState[1][12] + "|" + tempAllState[1][13] + "|" + tempAllState[1][14] + "|" + tempAllState[1][15] +"	" + tempAllState[2][12] + "|" + tempAllState[2][13] + "|" + tempAllState[2][14] + "|" + tempAllState[2][15] +"	" + tempAllState[3][12] + "|" + tempAllState[3][13] + "|" + tempAllState[3][14] + "|" + tempAllState[3][15]);
		}
	}
	
	public static void showEndProcessFifteenPuzzle(int[][] arr){
		for(int i = 0 ; i < arr.length ; i++)
		{
			System.out.println(arr[i][0] + " |" + arr[i][1] + " |" + arr[i][2] + " |" + arr[i][3]);
			System.out.println(arr[i][4] + " |" + arr[i][5] + " |" + arr[i][6] + " |" + arr[i][7]);
			System.out.println(arr[i][8] + " |" + arr[i][9] + " |" + arr[i][10] + "|" + arr[i][11]);
			System.out.println(arr[i][12] + "|" + arr[i][13] + "|" + arr[i][14] + "|" + arr[i][15]);
			System.out.println();
		}
	}
}
