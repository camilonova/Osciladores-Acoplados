import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

public class Validador extends FocusAdapter {

	public void focusLost(FocusEvent ev){
		JTextField tEntrada = (JTextField)ev.getSource();
		try{
			Double.valueOf(tEntrada.getText()).doubleValue();
			if(Double.valueOf(tEntrada.getText()).doubleValue() < 0){
				System.out.println("Digite un numero valido");
				tEntrada.requestFocus();
				tEntrada.selectAll();
			}
		}
		catch(NumberFormatException e){
			System.out.println("Digite un numero valido");
			tEntrada.requestFocus();
			tEntrada.selectAll();
		}
	}
}