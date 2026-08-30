package principal;
import java.util.*;
class Somar {
	public static Scanner sc = new Scanner(System.in);
	
	public static void main(String[] args) {
			int n1, n2, soma;
			
			System.out.println("Digite um numero");
			n1 = sc.nextInt();
			System.out.println("Digite outro numero");
			n2 = sc.nextInt();
			
			soma = n1+n2;
			
			System.out.println("Soma: " + soma);

	}		

}
