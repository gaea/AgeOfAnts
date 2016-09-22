import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Poblacion
{
	public String especie;
	public Punto pocision_hormiguero;
	public Comida cantidad_alimento = new Comida(0);
	public Comida cantidad_alimento_hormiguero = new Comida(0);
	public Vector hormigas= new Vector();
	public Object[][] ambiente;
	
	public Poblacion( int cantidad_hormigas, Punto pocision_hormiguero, Object[][] ambiente, String especie )
	{
		this.ambiente = ambiente;
		this.pocision_hormiguero = pocision_hormiguero;
		this.especie = especie;

		int distancia= (int)Math.ceil( Math.sqrt( cantidad_hormigas ) );
		int count_ant = 0;

		for( int i=pocision_hormiguero.getX()+1; i<pocision_hormiguero.getX()+1+distancia; i++ )
		{
			for( int j=pocision_hormiguero.getY()+1; j<pocision_hormiguero.getY()+1+distancia; j++ )
			{
				if( count_ant < cantidad_hormigas )
				{
				      Hormiga hormiga = new Hormiga( especie, new Punto( i, j ), ambiente, pocision_hormiguero, cantidad_alimento, cantidad_alimento_hormiguero );
				      hormigas.add( hormiga );
				      ambiente[i][j]= hormiga;
				      count_ant++;
				}
				else
				{
				      j=pocision_hormiguero.getY()+1+distancia;
				      i=pocision_hormiguero.getX()+1+distancia;
				}
			}
		}
	}
	
	public void Generar_Hormiga( Punto pocision_hormiga, String epecie )
	{}
	
	public Punto get_pocision_hormiguero()
	{
	  return pocision_hormiguero;
	}
	
	public void desplazar()
	{
		if( cantidad_alimento.valor > 4 )
		{
			for(int i=pocision_hormiguero.getX()-1; i<pocision_hormiguero.getX()+2; i++)
			{
				for(int j=pocision_hormiguero.getY()-1; j<pocision_hormiguero.getY()+2; j++)
				{
					if( ambiente[i][j]==null || (ambiente[i][j].getClass().toString()).equals("class Feromona"))
					{
						Hormiga hormiga = new Hormiga( especie, new Punto( i, j ), ambiente, pocision_hormiguero, cantidad_alimento, cantidad_alimento_hormiguero );
						hormigas.add( hormiga );
						ambiente[ i ][ j ]= hormiga;
						cantidad_alimento.valor=0;
						i= pocision_hormiguero.getX()+2;
						j= pocision_hormiguero.getY()+2;

						//System.out.println("new hormiga:"+especie );
					}
				}
			}
		}

	  	for(int i=0; i<hormigas.size(); i++)
		{
			if( ((Hormiga)(hormigas.get(i))).vitalidad )
			{
			      ((Hormiga)(hormigas.get(i))).desplazar();
			}
			else
			{
				if( ((Hormiga)(hormigas.get(i))).tiempo_muerta>1 )
				{
					//System.out.println("************ kill ************  "+((Hormiga)(hormigas.get(i))).especie);
					ambiente[ ((Hormiga)(hormigas.get(i))).pocision_hormiga.getX() ][ ((Hormiga)(hormigas.get(i))).pocision_hormiga.getY() ]= null;
					hormigas.removeElementAt(i);
					//System.out.println("No Hormigas "+especie+", "+hormigas.size());
					if(i>0)
					{
					      i--;
					}
				}
				((Hormiga)(hormigas.get(i))).tiempo_muerta= ((Hormiga)(hormigas.get(i))).tiempo_muerta+1;
				//System.out.println("TIEMPO DE MUUERTA--------------->"+ ((Hormiga)(hormigas.get(i))).tiempo_muerta);
			}
		}
	}
}
