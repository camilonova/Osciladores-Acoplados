import java.awt.*;

public class MiCanvas extends Canvas{

	int wAncho;
	int wAlto;
	int cAlto;
	int cAncho;
	int orgY;
	int orgX;
	int orgYX;
	int escalaX;
	double escalaT;
	double t;
	double dt;
	double tGrafica;
	double w1;
	double w2;
	double separacion;
	final int numero = 2;
	int pos[];
	double x0A;
	double x0B;
	final int MAXPUNTOS = 550;
	int tiempo[];
	int posicionA[];
	int posicionB[];
	int iGuardar;
	final int N = 8;
	int unidad;
	Image imag;
	Graphics gBuffer;

	public MiCanvas(){
		pos = new int[4];
		tiempo = new int[550];
		posicionA = new int[550];
		posicionB = new int[550];
		setBackground(Color.white);
		dt = 0.10000000000000001D;
	}

	void setNuevo(double posA, double posB, double cteMuelle, double cteAcoplamiento){
		t = 0.0D;
		tGrafica = 0.0D;
		w1 = Math.sqrt(cteMuelle);
		w2 = Math.sqrt((double)2 * cteAcoplamiento + cteMuelle);
		x0A = posA;
		x0B = posB;
		iGuardar = 0;
	}

	void origen(Graphics g){
		wAncho = 500;
		wAlto = 600;
		cAlto = g.getFontMetrics().getHeight();
		cAncho = g.getFontMetrics().stringWidth("0");
		orgX = 2 * cAlto;
		orgY = 4 * cAlto;
		separacion = (double)(wAncho - 2 * orgX) / (double)3;
		unidad = (int)(((double)2 * separacion) / ((double)8 * Math.sqrt(2D)));
		for(int i = 0; i <= 3; i++)
			pos[i] = orgX + (int)((double)i * separacion);

		escalaX = 2 * unidad;
		escalaT = (double)(wAncho - orgX) / (double)50;
		orgYX = (2 * wAlto) / 3;
	}

	void graficas(Graphics g){
		g.setColor(Color.black);
		g.fill3DRect(orgX, orgYX, wAncho, 3, true);
		g.fill3DRect(orgX, orgY+80, 3, wAlto-200, true);
		g.setColor(Color.blue);
		g.drawPolyline(tiempo, posicionA, iGuardar);
		g.setColor(Color.black);
		g.fill3DRect(orgX, 250, wAncho, 3, true);
		g.setColor(Color.red);
		g.drawPolyline(tiempo, posicionB, iGuardar);
	}

	void disposicion(Graphics g){
		g.setColor(Color.LIGHT_GRAY);
		g.fill3DRect(0, orgY - 2 * cAlto, orgX, 4 * cAlto,true);
		g.fill3DRect(wAncho - orgX, orgY - 2 * cAlto, orgX, 4 * cAlto,true);
		g.setColor(Color.BLACK);
		dibujaMuelle(g, Color.red, pos[1] - pos[0], pos[0], orgY);
		dibujaMuelle(g, Color.black, pos[2] - pos[1], pos[1], orgY);
		dibujaMuelle(g, Color.blue, pos[3] - pos[2], pos[2], orgY);
		g.setColor(Color.red);
		g.fillOval(pos[1] - cAncho, orgY - cAncho, 2 * cAncho, 2 * cAncho);
		g.setColor(Color.blue);
		g.fillOval(pos[2] - cAncho, orgY - cAncho, 2 * cAncho, 2 * cAncho);
		g.setColor(Color.black);
		g.drawString("Tiempo: " + String.valueOf(Math.floor(t * (double)100) / (double)100) + " seg.", (int)wAncho/2, cAlto);
	}

	void mover(){
		double xA = ((x0A + x0B) * Math.cos(w1 * t)) / (double)2 + ((x0A - x0B) * Math.cos(w2 * t)) / (double)2;
		double xB = ((x0A + x0B) * Math.cos(w1 * t)) / (double)2 - ((x0A - x0B) * Math.cos(w2 * t)) / (double)2;
		pos[1] = orgX + (int)(separacion + (double)escalaX * xA);
		pos[2] = orgX + (int)((double)2 * separacion + (double)escalaX * xB);
		posicionA[iGuardar] = orgYX - (int)((double)escalaX * xA);
		tiempo[iGuardar] = orgX + (int)((t - tGrafica) * escalaT);
		posicionB[iGuardar] = orgYX - (int)((double)escalaX * xB)-150;
		if(tiempo[iGuardar] > wAncho)
		{
			tGrafica = t;
			iGuardar = 0;
		}
		repaint();
		iGuardar++;
		t += dt;
	}

	public void dibujaMuelle(Graphics g, Color color, int longitud, int x, int y){
		double angulo = Math.acos((double)longitud / (Math.sqrt(2D) * (double)8 * (double)unidad));
		double dx = (double)unidad * Math.sqrt(2D) * Math.cos(angulo);
		double dy = (double)unidad * Math.sqrt(2D) * Math.sin(angulo);
		int x1 = x + (int)(dx / (double)2);
		int y1 = y - (int)(dy / (double)2);
		g.setColor(color);
		g.drawLine(x, y, x1, y1);
		for(int i = 1; i < 8; i++)
			g.drawLine(x1 + (int)((double)(i - 1) * dx), y - (int)((dy * (double)signo(i - 1)) / (double)2), x1 + (int)((double)i * dx), y - (int)((dy * (double)signo(i)) / (double)2));

		g.drawLine(x1 + (int)((double)7 * dx), y - (int)((dy * (double)signo(7)) / (double)2), x1 + (int)((double)7 * dx + dx / (double)2), y);
	}

	private int signo(int x){
		return x % 2 != 0 ? -1 : 1;
	}

	public void paint(Graphics g){
		origen(g);
		disposicion(g);
	}

	public void update(Graphics g){
		if(gBuffer == null)
		{
			imag = createImage(wAncho, wAlto);
			gBuffer = imag.getGraphics();
		}
		gBuffer.setColor(getBackground());
		gBuffer.fillRect(0, 0, wAncho, wAlto);
		disposicion(gBuffer);
		graficas(gBuffer);
		g.drawImage(imag, 0, 0, null);
	}
}