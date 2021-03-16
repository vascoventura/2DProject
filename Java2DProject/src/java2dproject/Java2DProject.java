package java2dproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.awt.image.WritableRaster;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import sun.awt.image.PixelConverter.Rgba;




public class Java2DProject extends JFrame implements ActionListener{
	
	ImgPanel imagem;
	ImgPanel image_copy;
	JFileChooser fc = new JFileChooser();
	BufferedImage bi = null;
	BufferedImageOp op = null;
	JLabel label;
	int altura_rect3;
	double largura_rect3;
	PrinterJob impressao;
	
	

	public static void main(String[] args) {
		JFrame frame = new Java2DProject();
		frame.setTitle("Projeto JAVA 2D");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		
		
	}
	
	public Java2DProject() {
		//Menus
			JMenuBar mb = new JMenuBar();
			setJMenuBar(mb);
			
			//Item da lista do menu
			
			JMenu menu = new JMenu("Ficheiro");
			JMenuItem mi = new JMenuItem("Abrir Imagem...");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Guardar");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Imprimir");
			mi.addActionListener(this);
			menu.add(mi);
			menu.addSeparator();
			mi = new JMenuItem("Sair");
			mi.addActionListener(this);
			menu.add(mi);
			mb.add(menu);
			
			menu = new JMenu("Processamento");
			mi = new JMenuItem("Suavizar");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Reiniciar");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Realçar");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Escala de Cinzento");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Binarização");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Rodar");
			mi.addActionListener(this);
			menu.add(mi);
			mi = new JMenuItem("Inverter Cores");
			mi.addActionListener(this);
			menu.add(mi);
			mb.add(menu);
			
			//Adicionar os panels
			imagem = new ImgPanel();
			Container cp = this.getContentPane();
			cp.setLayout(new FlowLayout());
			label = new JLabel(new Mensagem().getMensagem(1));
			label.setForeground(Color.RED);
			label.setFont(new Font("Tahoma", Font.PLAIN, 15));
			imagem.add(label);
			cp.add(imagem);
			
			impressao = PrinterJob.getPrinterJob();
			impressao.setPrintable(imagem);
			
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if("Abrir Imagem...".equals(cmd)) {
			int retval = fc.showOpenDialog(this);
			if (retval == JFileChooser.APPROVE_OPTION) {
				try {
					bi = ImageIO.read(fc.getSelectedFile());
					imagem.setImage(bi);
					imagem.setImage_copy(bi);				
					label.setText(new Mensagem().getMensagem(2));
					imagem.setTransparenciav(255);
					repaint();
				} catch (IOException e1) {
					e1.printStackTrace();
					imagem.setTransparenciax(255);
					repaint();
				}				
			}			
		} else if("Guardar".equals(cmd)) {
			if(bi != null) {
			int retval = fc.showSaveDialog(this);			
		      if (retval == JFileChooser.APPROVE_OPTION) {
		        try{
		          ImageIO.write(imagem.getImage(), "png", fc.getSelectedFile());
		          label.setText(new Mensagem().getMensagem(3));
		          imagem.setTransparenciav(255);
		          repaint();
		        } catch (IOException ex) {
		          ex.printStackTrace();
		          imagem.setTransparenciax(255);
		          repaint();
		          label.setText(new Mensagem().getMensagem(10));
		        }
		      } 	    	  
		      } else {
		    	  imagem.setTransparenciax(255);
		    	  label.setText(new Mensagem().getMensagem(10));
		    	  repaint();
		      }
		      		
		} else if("Imprimir".equals(cmd)) {
			if(bi != null) {
				label.setText(new Mensagem().getMensagem(4));
				repaint();
				if(impressao.printDialog()) {
					try {
						impressao.print();
						label.setText(new Mensagem().getMensagem(11));
						imagem.setTransparenciav(255);
						repaint();
					} catch (PrinterException e1) {
						e1.printStackTrace();
						label.setText(new Mensagem().getMensagem(12));
					}
				}				
			} else {
				label.setText(new Mensagem().getMensagem(10));
				imagem.setTransparenciax(255);
				repaint();
			}
			
			
		} else if("Sair".equals(cmd)) {
			System.exit(0);
		
		//Menu de Processamento da Imagem
			
		} else {
			process(cmd);			
		}
	}
	
	//Funcoes para o processamento de Imagem
	
	private void process(String cmd) {
		
		if(bi != null) {
		if("Suavizar".equals(cmd)) {
			float[] data = new float[9];
		    for (int i = 0; i < 9; i++) {
		    	data[i] = 1.0f/9.0f;
		    }
		    	Kernel ker = new Kernel(3,3,data);
		    	op = new ConvolveOp(ker);
		    	imagem.setImage(op.filter(imagem.getImage(), null));
		    	label.setText(new Mensagem().getMensagem(5));
		    	imagem.setTransparenciav(255);
		    	repaint();
			
		} else if("Realçar".equals(cmd)) {
			float data[] = {0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f};
			Kernel k = new Kernel(3, 3, data);
			op = new ConvolveOp(k);
			label.setText(new Mensagem().getMensagem(6));
			imagem.setTransparenciav(255);
		    repaint();
		} else if("Reiniciar".equals(cmd)) {
			 imagem.setImage(bi);
			 label.setText(new Mensagem().getMensagem(7));
			 imagem.setTransparenciav(255);
			 repaint();
		} else if("Escala de Cinzento".equals(cmd)) {
			op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);	
			 label.setText(new Mensagem().getMensagem(8));
			 imagem.setTransparenciav(255);
			 repaint();
		} else if("Binarização".equals(cmd)) {
			imagem.setImage(Binarization(imagem.getImage()));	
			label.setText(new Mensagem().getMensagem(9));
			imagem.setTransparenciav(255);
	    	repaint();
		} else if("Rodar".equals(cmd)) {
			AffineTransform at = new AffineTransform();
			at.setToRotation(Math.PI / 2, bi.getMinX() + bi.getWidth() / 2, bi.getMinY() + bi.getHeight() / 2);			
			op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
			label.setText(new Mensagem().getMensagem(14));
			repaint();
		} else if("Inverter Cores".equals(cmd)){
			imagem.setImage(invertImage(imagem.getImage()));
			label.setText(new Mensagem().getMensagem(13));
			imagem.setTransparenciav(255);
			repaint();
			
		}
		
		
		BufferedImage bi = op.filter(imagem.getImage(), null);
	    imagem.setImage(bi);
	    pack();
	    
		} else {
			imagem.setTransparenciax(255);
			label.setText(new Mensagem().getMensagem(10));
			repaint();
		}
	    
	  	}
	
	public BufferedImage invertImage(final BufferedImage src) {
		short[] invertTable = new short[256];
		for (int i = 0; i < 256; i++) {
			invertTable[i] = (short) (255 - i);
		}
		 int w = src.getWidth();
		 int h = src.getHeight();
		 BufferedImage dst = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		BufferedImageOp invertOp = new LookupOp(new ShortLookupTable(0, invertTable), null);
		return invertOp.filter(src, dst);
	}
	

	public BufferedImage Binarization(BufferedImage imgIn) {
		BufferedImage imgOut = new BufferedImage(imgIn.getWidth(), imgIn.getHeight(), imgIn.getType());
		
		//Aceder aos pixeis para aceder à cor e mudar essa cor
		
		WritableRaster rasterImgIn = imgIn.getRaster();
		WritableRaster rasterImgOut = imgOut.getRaster();
		
		int[] rgba = new int[4];
		
		
		for (int x = 0; x < imgIn.getWidth(); x++) {
			for(int y = 0; y < imgIn.getHeight(); y++) {
				rasterImgIn.getPixel(x, y, rgba);
				
				if (rgba[0] > 150) {
					rgba[0] = rgba[1] = rgba[2] = 255;
					rasterImgOut.setPixel(x, y, rgba);
				}else{
					rgba[0] = rgba[1] = rgba[2] = 0;
					rasterImgOut.setPixel(x, y, rgba);
				}
			}
		}	
		
		return imgOut;
	}

	
}


//Classe do Painel do Projeto


class ImgPanel extends JPanel implements KeyListener, Runnable, Printable{
	
	//Atributos
	
	BufferedImage image = null;
	BufferedImageOp op = null;
	BufferedImage image_copy = null;
	private Color cor;
	private Color corshape;
	private Random randomColor = new Random();
	int color = 0;
	Rectangle2D rect1;
	Rectangle2D rect2;
	Rectangle2D rect3;
	AffineTransform at = new AffineTransform();
	AffineTransform rt1 = new AffineTransform();
	AffineTransform rt2 = new AffineTransform();
	
	int ang = 0;
	Shape shape1 = null;
	Shape shape2 = null;
	Shape shape3 = null;
	Shape shape4 = null;
	Shape shape5 = null;
	Shape shape6 = null;
	int r = 15;
	int tx = 255;
	int tv = 255;
	private Random gradient = new Random();
	int grad;
	GradientPaint gp;
	GradientPaint gp2;
	
	//Todo implementar as shapes nos retangulos laterais
	
	
	//Construtor 1
	
	public ImgPanel() {
		image = null;
		setPreferredSize(new Dimension(800, 800));
		addKeyListener(this);
		setFocusable(true); //o painel pode receber focus
		
		addMouseListener(new MouseAdapter() { 
	          public void mousePressed(MouseEvent me) {
	        	  color = randomColor.nextInt(6);
	        	  grad = gradient.nextInt(5);
	      		
	      		if (color == 0) {
	      			cor = Color.CYAN;
	      			corshape = Color.RED;
	      		} else if (color == 1) {
	      			cor = Color.RED;
	      			corshape = Color.GREEN;
	      		} else if(color == 2) {
	      			cor = Color.GREEN;
	      			corshape = Color.ORANGE;
	      		} else if(color == 3) {
	      			cor = Color.ORANGE;
	      			corshape = Color.WHITE;
	      		} else if(color == 4) {
	      			cor = Color.WHITE;
	      			corshape = Color.YELLOW;
	      		} else if(color == 5) {
	      			cor = Color.YELLOW;
	      			corshape = Color.CYAN;
	      		}
	      		
	      		if (grad == 0) {
	      			gp = new GradientPaint(getWidth() / 3, 50 + 100, Color.CYAN, getWidth(), 50 + 100, Color.BLACK);
	      			gp2 = new GradientPaint(getWidth() / 4, 0,  Color.ORANGE, 500, 50 + 100, Color.BLACK);
	      		} else if (grad == 1) {
	      			gp = new GradientPaint(getWidth() / 3, 50 + 100, Color.GREEN, getWidth(), 50 + 100, Color.RED);
	      			gp2 = new GradientPaint(getWidth() / 4, 0, Color.CYAN, 500, 50 + 100, Color.BLACK);
	      		} else if(grad == 2) {
	      			gp = new GradientPaint(getWidth() / 3, 50 + 100, Color.PINK, getWidth(), 50 + 100, Color.BLUE);
	      			gp2 = new GradientPaint(getWidth() / 4, 0, Color.GREEN, 500, 50 + 100, Color.RED);
	      		} else if(grad == 3) {
	      			gp = new GradientPaint(getWidth() / 3, 50 + 100, Color.BLACK, getWidth(), 50 + 100, Color.YELLOW);
	      			gp2 = new GradientPaint(getWidth() / 4, 0, Color.PINK, 500, 50 + 100, Color.BLUE);
	      		} else if(grad == 4) {
	      			gp = new GradientPaint(getWidth() / 3, 50 + 100, Color.ORANGE, getWidth(), 50 + 100, Color.BLACK);
	      			gp2 = new GradientPaint(getWidth() / 4, 0, Color.CYAN, 500, 50 + 100, Color.BLACK);
	      		}
	      		
	      	
	      		
	            invalidate();
	    		repaint();
	          } 
	        });
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	//Construtor 2
	public ImgPanel(BufferedImage bufferedimage) {
		image = bufferedimage;
	}
	
	//Getters e Setters	
	public BufferedImage getImage() {
		return image;
	}
	
	public void setImage(BufferedImage bufferedimage) {
		image = bufferedimage;
		invalidate();
		repaint();
	}
	
	public BufferedImage getImage_copy() {
		return image_copy;
	}

	public void setImage_copy(BufferedImage image_copy) {
		this.image_copy = image_copy;
	}	

	public Rectangle2D getRect3() {
		return rect3;
	}

	public void setRect3(Rectangle2D rect3) {
		this.rect3 = rect3;
	}
	
	
	
	
	//Metodo Paint Component

	public int getTransparenciax() {
		return tx;
	}

	public void setTransparenciax(int transparenciax) {
		this.tx = transparenciax;
	}

	public int getTransparenciav() {
		return tv;
	}

	public void setTransparenciav(int transparenciav) {
		this.tv = transparenciav;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;		
		
		if(image != null) {
			desenhaImagem(g2);			
			rect1 = new Double(0, 0, 90 , image.getHeight());
			rect2 = new Double(image.getWidth() + 110, 0, 90 , image.getHeight());
			rect3 = new Double(0, image.getHeight() + 10, 200 + image.getWidth() , 150);			
		} else {			
			rect1 = new Double(0, 0, 90, getHeight() -160);
			rect2 = new Double(getWidth() - 90, 0, 90, getHeight() -160);
			rect3 = new Double(0, rect2.getHeight() + 10, getWidth(), 150);
			
			
			//formas centrais
			g2.setPaint(gp2);
			g2.fillRect(300, 200, 200, 200);
			g2.setPaint(gp);
			g2.fillOval(300, 0, 200, 200);
			g2.fillOval(100, 200, 200, 200);
			g2.fillOval(300, 200, 200, 200);
			g2.fillOval(300, 400 , 200, 200);
			g2.fillOval(500, 200, 200, 200);
			
			
			
		}
		
		//formas dos retângulos laterias
		shape1 = new Rectangle2D.Double(rect1.getX() + 30, rect1.getY() + 30, 30, 30);
		shape2 = new Ellipse2D.Double(-r, -r, 3*r, 0.5 * r);
		shape5 = new Rectangle2D.Double(rect2.getX() + 30, rect2.getY() + 30, 30, 30);
		shape6 = new Ellipse2D.Double(-r, -r, 3*r, 0.5 * r);
		
				
		if(image != null) {
			//elipses
			
			at.setToTranslation(rect1.getMinX() + 40, rect1.getMaxY() - 30);
			at.rotate(Math.toRadians(ang), shape2.getBounds().getCenterX(), shape2.getBounds().getCenterY());
			shape2 = at.createTransformedShape(shape2);
			
			at.setToTranslation(rect2.getMinX() + 40, rect2.getMaxY() - 30);
			at.rotate(Math.toRadians(ang), shape6.getBounds().getCenterX(), shape6.getBounds().getCenterY());
			shape6 = at.createTransformedShape(shape6);
			
		}
		else {			
			//quadrados
			rt1.rotate(Math.toRadians(ang), shape1.getBounds().getCenterX(), shape1.getBounds().getCenterY());
			shape1 = rt1.createTransformedShape(shape1);
			
			rt2.rotate(Math.toRadians(ang), shape5.getBounds().getCenterX(), shape5.getBounds().getCenterY());
			shape5 = rt2.createTransformedShape(shape5);
			
			at.setToTranslation(rect1.getMinX() + 40, rect1.getMaxY() - 50);
			at.rotate(Math.toRadians(ang), shape2.getBounds().getCenterX(), shape2.getBounds().getCenterY());
			shape2 = at.createTransformedShape(shape2);
			
			at.setToTranslation(rect2.getMinX() + 40, rect2.getMaxY() - 50);
			at.rotate(Math.toRadians(ang), shape6.getBounds().getCenterX(), shape6.getBounds().getCenterY());
			shape6 = at.createTransformedShape(shape6);
		}	
		
		
		//formas do tetris (retangulo 3)
		Form forma1 = new Form("t", (float) rect3.getMinX()+30, (float) rect3.getMinY() + 30);
		Form forma2 = new Form("o", (float) rect3.getMinX()+30, (float) rect3.getMinY() + 80);
		Form forma3 = new Form("i", (float) rect3.getMinX()+30, (float) rect3.getMinY() + 130);
		Form forma4 = new Form("t", (float) rect3.getMaxX()-50 , (float) rect3.getMinY() + 30);
		Form forma5 = new Form("o", (float) rect3.getMaxX()-50, (float) rect3.getMinY() + 80);
		Form forma6 = new Form("i", (float) rect3.getMaxX()-60 , (float) rect3.getMinY() + 130);
		
		
		Form shapev = new Form("v", (float) rect2.getMinX() + 20, (float) rect2.getHeight() / 2);
		Form shapex = new Form("x", (float) rect1.getMinX() + 30, (float) rect1.getHeight() / 2);
		
		g2.setColor(Color.BLACK);
		g2.draw(rect1);
		g2.setColor(cor);
		g2.fill(rect1);
		
		g2.setColor(Color.BLACK);		
		g2.draw(rect2);
		g2.setColor(cor);
		g2.fill(rect2);
		
		g2.setColor(Color.BLACK);
		g2.draw(rect3);
		g2.setColor(Color.LIGHT_GRAY);
		g2.fill(rect3);
		
		g2.setColor(Color.BLUE);		
		g2.fill(forma1);
		g2.fill(forma2);
		g2.fill(forma3);		
		g2.fill(forma4);
		g2.fill(forma5);
		g2.fill(forma6);
		
		
		g2.setColor(corshape);
		g2.fill(shape1);
		g2.fill(shape5);
		g2.fill(shape2);
		g2.fill(shape6);
		
		//Strokes
		Stroke s = new BasicStroke(5);				
		float[] dash = {10, 3};
		s = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, dash, 0);
		
		Stroke s2 = new BasicStroke(5);
		s2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.CAP_SQUARE, 0, dash, 0);
		
		
		g2.setColor(Color.BLACK);
		g2.setStroke(s);
		g2.draw(shape2);
		g2.draw(shape6);
		g2.setStroke(s2);
		g2.draw(shape1);
		g2.draw(shape5);
		
		g2.setColor(new Color(36, 252, 3, tv));
		g2.fill(shapev);
		g2.setColor(new Color(247, 64, 64, tx));
		g2.fill(shapex);
		
		
		
		//Mensagem
		
		
		Font font = new Font("Serif", Font.BOLD,15);
		FontRenderContext frc = g2.getFontRenderContext();
		
		Shape shapeMensagem = font.createGlyphVector(frc, new Mensagem().getMensagem(1)).getOutline();
		
		Area shape = new Area(rect3);
		shape.add(new Area(shapeMensagem));
		
		g2.translate(rect3.getWidth() / 4 - shapeMensagem.getBounds().width / 2, rect3.getCenterY());
		
	}
	
private void desenhaImagem(Graphics2D g2) {
		g2.drawImage(image, 100, 0, null);
		
	}

	//Operacoes de processamento de Imagem	
	//Colocar a cinzento
		public BufferedImage cinzento(BufferedImage imagem) {
				op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
				setPreferredSize(new Dimension(imagem.getWidth(), imagem.getHeight()));
				image = op.filter(imagem, null);
				tv = 255;
				tx = 0;
				invalidate();
				repaint();
				
			return imagem;
		}
		
	//Sharpen
		public BufferedImage sharpen(BufferedImage imagem) {
			float[] data = {0f, -1f, 0f, -1f, 5f, -1f, 0f, -1f, 0f};
		    Kernel ker = new Kernel(3,3,data);
		    op = new ConvolveOp(ker);
		    setPreferredSize(new Dimension(imagem.getWidth(), imagem.getHeight()));
			image = op.filter(imagem, null);
			tv = 255;
			tx = 0;
			invalidate();
			repaint();
			return imagem;
		}
		
		//Smooth
		public BufferedImage smooth(BufferedImage imagem) {
			float[] data = new float[9];
		    for (int i = 0; i < 9; i++) {
		    	data[i] = 1.0f/9.0f;
		    }
	    	Kernel ker = new Kernel(3,3,data);
	    	op = new ConvolveOp(ker);
	    	setPreferredSize(new Dimension(imagem.getWidth(), imagem.getHeight()));
			image = op.filter(imagem, null);
			tv = 255;
			tx = 0;
			invalidate();
			repaint();
			return imagem;
		}
		
		//Metodo Teclado	
	
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();		
			switch (keyCode) {
				case KeyEvent.VK_LEFT:
					if(image_copy != null) {
						smooth(image);
						tv = 255;
						tx = 0;						
					} else {
						tv = 0;
						tx = 255;					
					}							
					repaint();
					break;
				case KeyEvent.VK_RIGHT:
					if(image_copy != null) {
						sharpen(image);
						tv = 255;
						tx = 0;						
					} else {
						tv = 0;
						tx = 255;					
					}					
					repaint();
					break;
				case KeyEvent.VK_UP:
					if(image_copy != null) {
						cinzento(image);
						tv = 255;
						tx = 0;						
					} else {
						tv = 0;
						tx = 255;						
					}
					repaint();
					
				
					break;
				case KeyEvent.VK_DOWN:
					if(image_copy != null) {
						setImage(image_copy);
						tv = 255;
						tx = 0;
						repaint();
					} else {
						tv = 0;
						tx = 255;
						repaint();
					}
					break;
			}		
		}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		while(true) {
			tv = 0;
			tx = 0;
			
			if(image != null) {
				ang = 0;
				
			} else {
				ang = ang + 5;
				
				if (ang == 360) {
				}
				
				ang = (ang + 5) % 360;				
			}
			
			repaint();
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	//Metodo para Imprimir
	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		switch (pageIndex) {
		case 0:
			desenhaImagem((Graphics2D) g);
			tv = 255;
			tx = 0;
			break;
		default:
			return NO_SUCH_PAGE;				
		}
		
		return PAGE_EXISTS;
	}	
}






class Mensagem{
	String mensagem;
	
	
	public Mensagem() {
		
	}
	
	public String getMensagem(int numero) {
		if(numero == 1) {
			mensagem = "Bem-vindo ao Java2DProject!    Carregue uma Imagem para Começar...";
		} else if (numero == 2) {
			mensagem = "Imagem carregada com sucesso!";			
		} else if (numero == 3) {
			mensagem = "Imagem Guardada com Sucesso!";			
		} else if (numero == 4) {
			mensagem = "A preparar Impressão...";			
		} else if (numero == 5) {
			mensagem = "Suavização Processada";			
		} else if (numero == 6) {
			mensagem = "Realce da Imagem Processado";			
		} else if (numero == 7) {
			mensagem = "Imagem Por Defeito Definida";			
		} else if (numero == 8) {
			mensagem = "Escala de Cinzento Implementada";
		} else if (numero == 9) {
			mensagem = "Binarização Processada";			
		} else if (numero == 10) {
			mensagem = "Imagem Não Carregada. Carregue uma Imagem...";			
		} else if (numero == 11) {
			mensagem = "Impressão Concluída";			
		} else if (numero == 12) {
			mensagem = "Método de Impressão Não Reconhecido";			
		} else if (numero == 13) {
			mensagem = "Inversão de Cores Processada com Sucesso!";			
		} else if (numero == 14) {
			mensagem = "Rotação Realizada com Sucesso!";			
		} 
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
}
