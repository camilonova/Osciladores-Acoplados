import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Programa extends JApplet {
	Hilo hilo;
	MiCanvas canvas;
	private boolean isStandalone = false;
	private JPanel panel = new JPanel();
	private JPanel controles = new JPanel();
	private JPanel Panel2 = new JPanel();
	private JPanel Panel3 = new JPanel();
	private JPanel Panel5 = new JPanel();
	private JPanel Panel6 = new JPanel();
	private JPanel Panel7 = new JPanel();
	private JPanel Panel8 = new JPanel();
	private FlowLayout flowLayout = new FlowLayout();
	private JTextField tInicial_A = new JTextField("0",4);
	private JTextField tInicial_B = new JTextField("1",4);
	private JTextField tCteMuelles = new JTextField("10",4);
	private JTextField tCteAcoplamiento = new JTextField("0.5",4);
	private JButton btnEmpieza = new JButton("Comenzar");
	
	public Programa(){
		canvas = new MiCanvas();
		hilo = new Hilo(this);
		panel.setLayout(new BorderLayout());
		
		Panel3.setLayout(new BorderLayout());
		Panel8.setLayout(new FlowLayout());
		Panel7.setLayout(new FlowLayout());
		Panel2.setLayout(new BorderLayout());
		Panel6.setLayout(new FlowLayout());
		Panel5.setLayout(new FlowLayout());
		getContentPane().setLayout(new BorderLayout());
		btnEmpieza.addActionListener(new _cls1());
		tInicial_A.addFocusListener(new Validador());
		tInicial_B.addFocusListener(new Validador());
		tCteMuelles.addFocusListener(new Validador());
		tCteAcoplamiento.addFocusListener(new Validador());
		
		getContentPane().add(canvas, "Center");
		getContentPane().add(panel, "South");

		panel.add(Panel2, "West");
		Panel2.add(Panel5, "Center");
		Panel5.add(new JLabel("Posicion inicial de Rojo:"));
		Panel5.add(tInicial_A);
		Panel2.add(Panel6, "South");
		Panel6.add(new JLabel("Posicion inicial de Azul:"));
		Panel6.add(tInicial_B);
		panel.add(Panel3, "Center"); 	
		Panel3.add(Panel7, "Center");
		Panel7.add(new JLabel("K de los extremos:"));
		Panel7.add(tCteMuelles);
		Panel3.add(Panel8, "South");
		Panel8.add(new JLabel("K del acoplamiento:"));
		Panel8.add(tCteAcoplamiento);
		panel.add(controles, "East");
		
		flowLayout.setVgap(15);
		controles.setLayout(flowLayout);
		controles.add(btnEmpieza);
		hilo.start();
	}

	class _cls1 implements ActionListener{
		public void actionPerformed(ActionEvent e){
			canvas.setNuevo(Double.valueOf(tInicial_A.getText()).doubleValue(), Double.valueOf(tInicial_B.getText()).doubleValue(), Double.valueOf(tCteMuelles.getText()).doubleValue(), Double.valueOf(tCteAcoplamiento.getText()).doubleValue());
			hilo.putMsg(2);
		}
	}

	public String getAppletInfo(){
		return "Universidad Distrital Fransisco Jose de Caldas - 2004";
	}
}