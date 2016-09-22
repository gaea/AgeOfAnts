import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Ambiente
{
	public Object[][] ambiente;
	private int tamano;
	public Poblacion poblacion1;
	public Poblacion poblacion2;
	private Random random;
	public Punto pos_hormiguero1;
	public Punto pos_hormiguero2;
	
	public Ambiente( int tamano, int numero_poblacion1, int numero_poblacion2 )
	{
		this.tamano = tamano;
		random = new Random();
		ambiente = new Object [tamano][tamano];
		//int pos_x_hormiguero1= random.nextInt(tamano/2);
		//int pos_y_hormiguero1= random.nextInt(tamano/2);
		pos_hormiguero1= new Punto( 10, 10 );
		pos_hormiguero2= new Punto( 10, 30 );
		poblacion1 = new Poblacion(numero_poblacion1, pos_hormiguero1, ambiente, "Roja" );
		poblacion2 = new Poblacion(numero_poblacion2, pos_hormiguero2, ambiente, "Azul" );
		
		for(int i=0; i<6; i++)
		{
		      generar_alimento();
		}
	}
	
	public void generar_alimento( )
	{
	      int r_x =  random.nextInt(50);
	      int r_y =  random.nextInt(50);

	      for(int i=0+r_x; i<10+r_x; i++)
	      {
		    for(int j=0+r_y; j<10+r_y; j++)
		    {
			  if( i>=0 && i<50  && j>=0 && j<50 )
			  {
				if( ambiente[i][j]== null )
				{
				      ambiente[i][j]= new Comida(1);
				}
			  }
		    }
	      }
	}
}