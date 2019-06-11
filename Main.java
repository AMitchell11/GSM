import java.util.Stack;
import java.util.ArrayList;

public class Main {
    private static int ASCIISIZE = 8;

    // converts the string to a character array
    public static char[] stringToChar(String sequence){
        char[] A = sequence.toCharArray();
        return A;

    }

    // converts the char array to its corresponding integer values
    public static int[] charArrToASCII(char[] A){
        int[] asciiArr = new int[A.length];
        for(int i = 0; i < A.length; i++){
            asciiArr[i] = (int) A[i];
        }

        return asciiArr;
    }

    // loop through the array of ascii values and break each one using mod 2 to get remainder. store that in a stack,
    // account for necessary padding, and then create a new string by popping items off the stack
    // the issue with Java's toBinaryString is that it does not pad 0's. We can use a stack to get around this
    public static String toBinary(int[] asciiArr){
        String binaryString = "";
        Stack<Integer> binaryStack = new Stack<Integer>();

        for(int i = 0; i < asciiArr.length; i++){
            int temp = asciiArr[i];

            while(temp > 0){
                binaryStack.push(temp % 2);
                temp = temp/2;
            }

            // need to get the correct padding
            int fillLength = ASCIISIZE - binaryStack.size();

            for(int j = 0; i < fillLength; i++) {
                binaryStack.push(0);
            }

            // with correct padding we can simply pop each element to get our binary string
            while(!binaryStack.isEmpty()){
                binaryString += binaryStack.pop().toString();
            }

        }

        return binaryString;
    }

    // Objective 1
    // just grab 2 pieces of the string as a substring and check it against a switch statement, then grab the next 2 etc.
    static void toStringDNA(String sequence){
        String DNA = "";
        for(int i = 0; i < sequence.length(); i+=2){
            String dnaLetter = sequence.substring(i, i+2);

            // Alternatively you could put the switch contents into a HashMap and query that
            switch(dnaLetter){
                case "00":
                    DNA += "A";
                    break;
                case "01":
                    DNA += "T";
                    break;
                case "10":
                    DNA += "G";
                    break;
                case "11":
                    DNA += "C";
                    break;
            }
        }

        System.out.println("The DNA sequence is " + DNA);
    }

    // Objective 2
    // simply change the switch statement. In a driver program you can simply ask whether the input string
    // is needed in RNA sequence or DNA sequence
    static void toStringRNA(String sequence){
        String RNA = "";
        for(int i = 0; i < sequence.length(); i+=2){
            String rnaLetter = sequence.substring(i, i+2);

            switch(rnaLetter){
                case "00":
                    RNA += "A";
                    break;
                case "01":
                    RNA += "U";
                    break;
                case "10":
                    RNA += "G";
                    break;
                case "11":
                    RNA += "C";
                    break;
            }
        }

        System.out.println("The RNA sequence is "  + RNA);
    }

    //Objective 3
    //Assuming you want to pass in a DNA sequence to find in a string (so 2 parameters)
    //For example, you have a 20 character DNA sequence and want to find if the DNA sequence "ACTG" exists in it
    public static int findEncodedDNA(String str, String sequence){

        // we can do this simply by using Java's indexOf method
        // if the substring is found, it returns the index of the first position
        // if it's not found, it returns a -1
        int index = str.indexOf(sequence);
        return index;
    }


    // Objective 4
    // Takes the input sequence, converts it to a char array and converts each character to its respective binary
    // representation. Upon checking 4 characters we know that we are done with a letter (since each character is 8
    // bits and each DNA letter is 2 bits. We convert the 4 converted characters to an int by parsing, store it in an array
    // and continue for the rest of the sequence doing the same thing. Afterwards we simply loop through the list, cast each
    // integer to its char value and print it
    public static void dnaToString(String sequence){
        String result = "";
        char[] pieces = sequence.toCharArray();
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for(int i = 0; i < sequence.length(); i++){
            switch(sequence.charAt(i)){
                case 'A':
                    result += "00";
                    break;
                case 'T':
                    result += "01";
                    break;
                case 'G':
                    result += "10";
                    break;
                case 'C':
                    result += "11";
                    break;
            }
            if((i+1) % 4 == 0){
                int decimal = Integer.parseInt(result,2);
                temp.add(decimal);
                result = "";
            }
        }

        for(int i = 0; i < temp.size(); i++){
            int x = temp.get(i);
            char c = (char) x;
            System.out.print(c);
        }

        System.out.println();
    }

    // Objective 5
    // We can use dynamic programming to reduce the runtime since a recursive call would result in extra calls
    // that have already been calculated
    public static int longestCommonSubsequence(char[] a, char[] b, int x, int y){
        int[][] results = new int[x+1][y+1];

        for(int i = 0; i <= x; i++){
            for(int j = 0; j <= y; j++){
                if(i == 0 || j == 0){
                    results[i][j] = 0;
                }
                else if(a[i-1] == b[j-1]){
                    results[i][j] = results[i-1][j-1] + 1;
                }
                else{
                    if(results[i-1][j] > results[i][j-1]){
                        results[i][j] = results[i-1][j];
                    }
                    else{
                        results[i][j] = results[i][j-1];
                    }
                }
            }
        }

        return results[x][y];
    }

    public static void main(String[] args) {
	    char[] A = stringToChar("a");
	    int[] asciiArr = new int[A.length];
	    asciiArr = charArrToASCII(A);
	    String binaryString = toBinary(asciiArr);

	    //Objective 1
	    toStringDNA(binaryString);

	    //Objective 2
	    toStringRNA(binaryString);

	    //Objective 3
      int position = findEncodedDNA("TGAT", "XY");
      System.out.println(position);

      //Objective 4
      dnaToString("TGAT");

	    //Objective 5
	    String x = "TGAT";
	    String y = "TGAT";
	    char[] first = x.toCharArray();
	    char[] second = y.toCharArray();
	    int result = longestCommonSubsequence(first,second, first.length, second.length);
	    System.out.println("The longest common subsequence between " + x + " and " + y + " is " + result);
    }
}
