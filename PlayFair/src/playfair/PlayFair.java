/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playfair;


import java.util.Scanner;

/**
 *
 * @author phat
 */
public class PlayFair {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        Scanner input = new Scanner(System.in);
        System.out.print("Input the key: ");
        String key = input.nextLine(); //dung next line de co the space
        System.out.print("Input the plain text: ");
        String plaintext = input.nextLine();
        System.out.print("Input the cipher text: ");
        String ciphertext = input.nextLine();
        //step 1
        System.out.print("Remove non-letter characters for P and C: ");
        System.out.println(removeNonLetterCharactersForPAndC(plaintext));
        //step 2
        System.out.print("Padding: ");
        String[] pairs =padding(plaintext);
        for(String pair : pairs)
        {
            System.out.print(pair+" ");
        }
        System.out.println();
        System.out.println("Output matrix: ");
        char[][] matrix = new char[5][5];
        createMaTrix(key, matrix);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(matrix[i][j] + " ");
            }
        System.out.println();
        }
        
        //encode
        System.out.print("Encrypt: ");
        System.out.println(enCode(plaintext, key, matrix));
        System.out.print("Decrypt: ");
        System.out.println(deCode(ciphertext, key, matrix));
    }
    
   public static String removeNonLetterCharactersForPAndC(String input) // xóa không phải kí tự của plaintext và ciphertext
{
    String result = "";//tạo chuỗi rỗng
    for (int i = 0; i < input.length(); i++) { // dùng vòng lặp for để duyệt lấy kí tự
        char c = input.charAt(i); //dùng kiểu dữ liệu char để lấy một kí tự trong chuỗi string dùng charAt
        if (Character.isLetter(c)) { // kiểm tra có phải là kí tự không
            result += c; // nếu là kí tự thì thêm vào chuỗi
        }
    }
    return result;
}
    
  public static String[] padding(String input) { // ham tạo cặp
    input = removeNonLetterCharactersForPAndC(input); // dùng lại xóa những thứ không phải là ký tự
    input = input.toLowerCase();// chuyển các chữ về in thường hết
      if (input.length()% 2 != 0) { // nếu độ dài chữ là lẻ thì thêm ký tự "z"
          input +="z";
      }
      int dem=0; // khởi tạo biến đếm
      for (int i = 1 ; i < input.length(); i+=2) {
          if (input.charAt(i) == input.charAt(i-1)) { //nếu kí tự sao giống kí tự trước thì tăng biến đếm
              dem++;
          }
      }
      String [] pairs = new String [(input.length()/2)+dem]; //dùng để tạo các cặp nếu trong đó có cặp kí tự trùng nhau thì dùng biến đếm để tăng mảng lên
      int arr=0;// khởi tạo mảng
      for (int i = 0; i < input.length(); i+=2) {
          String current=input.charAt(i)+""+input.charAt(i+1);//lấy kí tự từ biến input gán vào biến current dùng "" để chuyển char sang string
          if (current.charAt(0) == current.charAt(1)) { // kiểm tra kí tự cặp có trùng nhau 
             
              String b=current.charAt(0)+"z";// dùng biến b để gán kí tự đầu trong cặp cộng thêm "z"
              pairs[arr++]= b;//thêm cặp kí tự từ b vào mảng 0 và tăng mảng lên 1
              String a ="z"+ current.charAt(1);// giống như b
              pairs[arr++]=a;
          }
          else{
              pairs[arr++]=current; // nêú không trùng thì lấy biến current gán thẳng vào mảng
          }
      }
      return pairs;
}

    public static void createMaTrix(String key, char[][] matrix) // hàm tạo ma trận key
    {
        String a ="abcdefghiklmnopqrstuvwxyz"; // khởi tạo từ chuỗi kí tự từ a-z
        //key=key.toLowerCase()+a;
        key=key.toLowerCase(); // chuyển sang in thường
        key=removeNonLetterCharactersForKey(key);//Xóa các từ trùng nhau của key và những thứ không phải kí tự
        a+=key;//cộng thêm chuỗi key vào a
        
        for(int i=0;i<a.length();i++){
            if (a.charAt(i) !='j' && key.indexOf(a.charAt(i)) == -1) // tại i với j cùng nên ô nên t chỉ cần chọn i , loại j và nếu kí tự đó chưa có trong key thì thêm vào key
            {
                key+=a.charAt(i);
            }
        }
        int k=0;
        for (int i = 0; i < 5; i++) {     // tạo ma trận 2 chiều của key 
            for (int j = 0; j < 5; j++) {
                matrix[i][j]=key.charAt(k);
                k++;
            }
        }
        
        
            
    }
    public static String removeNonLetterCharactersForKey(String key) // hàm xóa mọi thứ không phải là kí tự của key
    {
        String result = "";
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (result.indexOf(c)== -1 && Character.isLetter(c)){ //nêú chưa có kí tự thì thêm vào chuỗi rỗng đã khởi tạo trước đó và kiểm tra nó có phải kí tự
                result+=(c);
            }
        }
        return result;
    }
    
    public static String enCode(String plaintext, String key,char[][] matrix) // mã hóa
    {
        createMaTrix(key, matrix);//tạo ma trận của key
        String resual = removeNonLetterCharactersForPAndC(plaintext);//xóa không phải kí tự của plaintext
        String[] pairs = padding(resual);//tạo cặp vào gán vào mảng 1 chiều pairs
        String encodeText="";//chuỗi rỗng
        for(String pair : pairs) //lấy cặp kí tự trong pairs
        {
            char c1 = pair.charAt(0);//lấy kí tư thứ 1 trong cặp đầu
            char c2 = pair.charAt(1);//lấy kí tự thứ 2 trong cặp đầu
            int col1=-1,row1 = -1,col2 = -1,row2=-1;//khởi tạo dòng một và cột 1 , dòng 2 và cột 2 gắn tất cả bằng -1 nếu không tìm thấy 
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (matrix[i][j] == c1) { //tìm thấy kí tự thứ nhất trong ma trận thì gắn dòng cột ở vị trí trong mảng của kí tự thứ nhất
                        row1=i;
                        col1=j;
                    }
                    if (matrix[i][j] == c2) { // giống như kí tự thứ nhất
                        row2=i;
                        col2=j;
                    }
                }
            }
            if (row1 == row2) { //nếu cùng dòng thì dịch sang 1 ô bên phải 
                encodeText += matrix[row1][(col1 + 1) % 5]; //đảm bảo rằng chỉ số hàng dùng %5 gặp kí tự tìm thấy trong ma trận sau khi dịch chuyển vào chuỗi rộng đã tạo trước đó
                encodeText += matrix[row2][(col2 + 1) % 5]; //đảm bảo rằng chỉ số hàng dùng %5 gặp kí tự tìm thấy trong ma trận sau khi dịch chuyển vào chuỗi rộng đã tạo trước đó
            }
            else if (col1 == col2) { //dịch xuống dưới 1 ô theo cột
                encodeText += matrix[row1+1 % 5][(col1 )]; //theo cột thì t chuyển sang phần row trong ma trận 
                encodeText += matrix[row2+1 % 5][(col2 )];//theo cột thì t chuyển sang phần row trong ma trận 
            }
            else // trường hợp nếu không cùng hàng thì lấy ví dòng và cột cuả kí tự  đổi cho nhau
            {
                encodeText += matrix[row1][col2];
                encodeText += matrix[row2][col1];
            }
        }
        return encodeText;
    }
    
    public static String deCode(String cipher, String key,char[][] matrix)//giải mã
    {
        createMaTrix(key, matrix);
        String resual = removeNonLetterCharactersForPAndC(cipher);
        String[] pairs = padding(resual);
        String DecodeText="";
        for(String pair : pairs)
        {
            char c1 = pair.charAt(0);
            char c2 = pair.charAt(1);
            int col1=-1,row1 = -1,col2 = -1,row2=-1;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (matrix[i][j] == c1) {
                        row1=i;
                        col1=j;
                    }
                    if (matrix[i][j] == c2) {
                        row2=i;
                        col2=j;
                    }
                }
            }
            if (row1 == row2) {
                DecodeText += matrix[row1][(col1 - 1 + 5) % 5]; //trong trường hợp ra âm thì ta cộng thêm 5 để chắc kí tự nằm trong hàng
                DecodeText += matrix[row2][(col2 - 1 + 5) % 5];
            }
            else if (col1 == col2) {
                DecodeText += matrix[(row1 - 1 + 5) % 5][col1];
                DecodeText += matrix[(row2 - 1 + 5) % 5][col2];
            }
            else
            {
                DecodeText += matrix[row1][col2];
                DecodeText += matrix[row2][col1];
            }
        }
        return DecodeText;
    }
}
