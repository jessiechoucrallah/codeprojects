class Compress{

public static String compress(String original){

char currentChar = original.charAt(0);
int currentCharCount = 1;
String compressedString = "";


for(int i=1; i<original.length(); i++){
  if(currentChar == original.charAt(i)){
    currentCharCount++;
}

else{
compressedString += (currentCharCount == 1) ? "" : currentCharCount;

compressedString += currentChar;
currentChar = original.charAt(i);
currentCharCount = 1;
}
}

compressedString += (currentCharCount == 1) ? "" : currentCharCount;
compressedString += currentChar;
return compressedString;
}

public static void main(String[] args){
System.out.println("Enter your string: ");

String s = IO.readString();
System.out.println("Original string is " + s);

System.out.println("Compressed string is " + compress(s));

}

}
