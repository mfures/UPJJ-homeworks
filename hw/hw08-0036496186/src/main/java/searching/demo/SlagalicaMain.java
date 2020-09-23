package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

public class SlagalicaMain {
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("Program zahtjeva točno jedan argument");
			System.exit(1);
		}
		
		if(args[0].length()!=9) {
			System.out.println("Program zahtjeva ulazni argument koji sadrži sve znamenke od 0 do 9");
			System.exit(1);
		}
		
		for(int i=0;i<9;i++) {
			if(!args[0].contains(String.valueOf(i))) {
				System.out.println("Ulazni argument ne sadrži znamenku "+i);
				System.exit(1);
			}
		}
		
		char[] inputC=args[0].toCharArray();
		int[] data=new int[9];
		
		for(int i=0;i<9;i++) {
			data[i]=Integer.parseInt(String.valueOf(inputC[i]));
		}
		
		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(data));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
	}
}