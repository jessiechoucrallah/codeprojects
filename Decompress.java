public class StringRec{

  public static String decompress(String compressedText) {
       char c;

       String decompressed="";

       if(compressedText.length()<=1)

       return compressedText;

       c=compressedText.charAt(0);


       if(Character.isDigit(c)) {

       if(c!='0'){
          c--;


       decompressed=decompressed+compressedText.substring(1,2)+decompress(c+compressedText.substring(1));

       }

       else{

    decompressed=decompressed+decompress(compressedText.substring(2));

       }

       }

       else{

      decompressed=decompressed+compressedText.substring(0,1)+decompress(compressedText.substring(1));

       }



       return decompressed;

   }



          public static void main(String args[]){

              StringRec strObj=new StringRec();

            String str = "q9w5e2rt5y4qw2Er3T";

            System.out.println("The decompression of string " + str + " gives: " +strObj.decompress(str));

        }
}
