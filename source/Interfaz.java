import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Interfaz implements ActionListener
{
	private	int ancho, alto;
	private	Dimension tv= Toolkit.getDefaultToolkit().getScreenSize();
	private	JFrame ventana = new JFrame( "Proyecto Evolutiva (Age of Antz)" );
	private	JPanel principal= new JPanel();
	private JScrollPane scroll;
	
	private JMenuBar barra;
	private JMenu menu_opciones, menu_otros;
	private JMenuItem item_iniciar, item_detener, item_creditos, item_salir;
			
	Ambiente mi_ambiente =new Ambiente( 50, 20, 20 );
	private	MiPanel mi_panel= new MiPanel( mi_ambiente );


	public Interfaz()
	{
        barra= new JMenuBar();

		menu_opciones= new JMenu("Opciones");
        menu_otros= new JMenu("Otros");

        item_iniciar = new JMenuItem( "Iniciar Simulacion" );
        item_detener = new JMenuItem( "Detener Simulacion" );
        item_creditos = new JMenuItem( "Creditos" );
        item_salir = new JMenuItem( "Salir" );
        
		item_iniciar.addActionListener(this);
		item_detener.addActionListener(this);
		item_creditos.addActionListener(this);
		item_salir.addActionListener(this);
		
		menu_opciones.add( item_iniciar );
		menu_opciones.add( item_detener );
        menu_otros.add( item_creditos );
        menu_otros.add( item_salir );

        barra.add( menu_opciones );
        barra.add( menu_otros );

        ventana.setJMenuBar(barra);
		
		principal.setBorder( BorderFactory.createTitledBorder( BorderFactory.createLineBorder(Color.black), "Poblacion", 2, 2 ) );
		principal.setLayout( new GridLayout( 1, 1, 0, 0 ) );

		scroll= new JScrollPane( mi_panel );
		scroll.setWheelScrollingEnabled( true );
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		principal.add( scroll );

		ventana.getContentPane().add( principal, BorderLayout.CENTER );
		
		ventana.setDefaultCloseOperation( WindowConstants.EXIT_ON_CLOSE );
		ventana.setLocation( 10, 10 );
		ventana.setResizable( false );
		ventana.setVisible( true );


		ancho= (( tv.width>=1000 )? 950 : tv.width-50 );
		alto= (( tv.height>=1000 )? 950 : tv.height-50 );;

		ventana.setSize( ancho , alto );
	
	}
	
	public void actionPerformed( ActionEvent e )
	{
		
		if ( e.getSource() == item_creditos )
		JOptionPane.showMessageDialog( ventana, "PROYECTO COMPUTACION EVOLUTIVA \n\n                  Creado por: \n      Gustavo Adolfo Espinosa \n         Eduar Jonny Hincapie \n \n                 Junio/24/2010 " );
		
		if( e.getSource() == item_salir )
		System.exit( 0 );

		if( e.getSource() == item_iniciar )
		mi_panel.temporizador.start();

		if( e.getSource() == item_detener )
		mi_panel.temporizador.stop();
	}
	
	public static void main(String[] args)
	{ new Interfaz();	}
}