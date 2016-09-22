import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.Timer;

public class MiPanel extends JPanel implements ActionListener
{
	private int ancho, alto;
	private int tamano_area;
	private int contador_alimento = 0;
	private long time;
	public Timer temporizador;

	private Image colonia = Toolkit.getDefaultToolkit().getImage( "../images/colonia.png" );
	private Image hormiguero_rojo = Toolkit.getDefaultToolkit().getImage( "../images/hormiguero rojo.png" );
	private Image hormiguero_azul = Toolkit.getDefaultToolkit().getImage( "../images/hormiguero azul.png" );
	private Image hormiga_azul = Toolkit.getDefaultToolkit().getImage( "../images/hormiga azul.png" );
	private Image hormiga_roja = Toolkit.getDefaultToolkit().getImage( "../images/hormiga roja.png" );
	private Image hormiga_azul_cargada = Toolkit.getDefaultToolkit().getImage( "../images/hormiga azul cargada.png" );
	private Image hormiga_roja_cargada = Toolkit.getDefaultToolkit().getImage( "../images/hormiga roja cargada.png" );
	private Image trebol = Toolkit.getDefaultToolkit().getImage( "../images/comida.png" );
	private Image feromona_azul = Toolkit.getDefaultToolkit().getImage( "../images/feromona azul.png" );
	private Image feromona_roja = Toolkit.getDefaultToolkit().getImage( "../images/feromona roja.png" );
	private Image kill = Toolkit.getDefaultToolkit().getImage( "../images/skull2.png" );
	

	Object[][] ambiente;
	Ambiente all;

	public MiPanel( Ambiente ambiente )
	{
		setPreferredSize( new java.awt.Dimension ( 1000, 1000 ) );
		this.ambiente= ambiente.ambiente;
		all=ambiente;
		
		temporizador = new Timer( 800, this );
	}

	public void paintComponent( Graphics s )
	{
		super.paintComponent(s);
		pintar_panel( s );
	}

	public void pintar_panel(  Graphics p  )
	{
		p.setFont( new Font( "Arial", 1, 20 ) );

		p.drawImage( colonia, 0, 0, this );
		p.drawImage( hormiguero_rojo, (all.pos_hormiguero1.getX()*20)-20, (all.pos_hormiguero1.getY()*20)-20, this );
		p.drawImage( hormiguero_azul, (all.pos_hormiguero2.getX()*20)-20, (all.pos_hormiguero2.getY()*20)-20, this );

		p.setColor( new Color( 100, 100, 100 ) );

		for( int i=0; i< 1000; i+=20 )
		{
			p.drawLine( i, 0, i, 1000 );
			p.drawLine( 0, i, 1000, i );
		}

		for( int i=0; i< 50; i++ )
		{
			for( int j=0; j< 50; j++ )
			{
				if( ambiente[i][j]!= null )
				{
					
					if( ( (ambiente[i][j].getClass().toString()).equals("class Hormiga") ) )
					{
						if ( ((Hormiga)ambiente[i][j]).vitalidad == false )
						{
							p.drawImage( kill, (i*20)-20, (j*20)-20, this );
						}

						if ( ((Hormiga)ambiente[i][j]).especie == "Roja")
						{
							if ( ((Hormiga)ambiente[i][j]).estado == "regresar")
							{
								p.drawImage( hormiga_roja_cargada, i*20, j*20, this );
							}
							else
							{
								p.drawImage( hormiga_roja, i*20, j*20, this );
							}
						}
						if ( ((Hormiga)ambiente[i][j]).especie == "Azul")
						{
							if ( ((Hormiga)ambiente[i][j]).estado == "regresar")
							{
								p.drawImage( hormiga_azul_cargada, i*20, j*20, this );
							}
							else
							{
								p.drawImage( hormiga_azul, i*20, j*20, this );
							}
						}
						
					}
					if( ( (ambiente[i][j].getClass().toString()).equals("class Feromona") ) )
					{
						if ( ((Feromona)ambiente[i][j]).tipo == "Roja")
						{
							p.drawImage( feromona_roja, i*20, j*20, this );
						}
						if ( ((Feromona)ambiente[i][j]).tipo == "Azul")
						{
							p.drawImage( feromona_azul, i*20, j*20, this );
						}
					}
					if( ( (ambiente[i][j].getClass().toString()).equals("class Comida") ) )
					{
						p.drawImage( trebol, i*20, j*20, this );
					}
				}	
			}
		}

		p.setColor( Color.white );
		p.drawString( "Hormigas Rojas: "+all.poblacion1.hormigas.size()+", Alimento Rojas: "+all.poblacion1.cantidad_alimento_hormiguero.valor, 0, 20 );
		p.drawString( "Hormigas Azules: "+all.poblacion2.hormigas.size()+", Alimento Azules: "+all.poblacion2.cantidad_alimento_hormiguero.valor, 0, 40 );
	}
	
	public void actionPerformed( ActionEvent s )
	{
		repaint();
		
		all.poblacion1.desplazar();
		all.poblacion2.desplazar();
		if(contador_alimento > 100)
		{
		      all.generar_alimento();
		      contador_alimento=0;
		}
		contador_alimento++;
	}
}
