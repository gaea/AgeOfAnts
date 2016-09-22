import javax.swing.*;
import java.util.*;
import java.awt.*;

public class Hormiga
{
	private Random random;
	private int capacidad;
	private int velocidad;
	public int direccion;
	public String estado;
	public String especie;
	public Object[][] ambiente;
	public Punto pos_Hormiguero;
	public Punto pocision_hormiga;
	public Vector <Punto> pocisiones_validas;
	public Comida alimento_hormiguero;
	public boolean vitalidad;
	public int tiempo_muerta;
	public Comida cantidad_alimento_hormiguero;

	public Hormiga( String especie, Punto pocision_hormiga, Object[][] ambiente, Punto pos_Hormiguero, Comida alimento_hormiguero, Comida cantidad_alimento_hormiguero )
	{
		this.especie= especie;
		this.ambiente= ambiente;
		this.pocision_hormiga= pocision_hormiga;
		this.pos_Hormiguero= pos_Hormiguero;
		this.alimento_hormiguero = alimento_hormiguero;
		this.vitalidad = true;
		this.cantidad_alimento_hormiguero = cantidad_alimento_hormiguero;
		
		estado = "buscar";
		random = new Random();
		pocisiones_validas= new Vector();
	}

	public void desplazar()
	{
		validar_hostilidad();
		if( vitalidad )
		{
		      if( estado.equals("buscar") )
		      {
			      buscar();
		      }
		      else
		      {
			      if( estado.equals("regresar") )
			      {
				      if( pocision_hormiga.getX()>=(pos_Hormiguero.getX()-1) && pocision_hormiga.getX()<=(pos_Hormiguero.getX()+1)
					  && pocision_hormiga.getY()>=(pos_Hormiguero.getY()-1) && pocision_hormiga.getY()<=(pos_Hormiguero.getY()+1 ) )
				      {
					      //System.out.println("***************"+"hormigero "+pos_Hormiguero.getX()+", "+pos_Hormiguero.getY()+ "  hormiga "+pocision_hormiga.getX()+", "+pocision_hormiga.getY());

					      alimento_hormiguero.incrementar();
					      cantidad_alimento_hormiguero.incrementar();
					      pocisiones_validas.clear();
					      //System.out.println("Alimento colonia: "+cantidad_alimento_hormiguero.valor+", especie: "+especie);
					      capacidad= 0;
					      estado= "buscar";
				      }
				      else
				      {
					      regresar();
				      }
			      }
		      }
		}

	}	

	public void buscar()
	{
		for(int i=pocision_hormiga.getX()-1; i<pocision_hormiga.getX()+2; i++)
		{
			for(int j=pocision_hormiga.getY()-1; j<pocision_hormiga.getY()+2; j++)
			{
			      if( validar( i, j ) )
			      {
				    if(estado.equals("recoger_comida"))
				    {
					  pocisiones_validas.clear();
					  pocisiones_validas.add( new Punto( i, j ) );
					  i=pocision_hormiga.getX()+2;
					  j=pocision_hormiga.getY()+2;
				    }
				    else
				    {
					  pocisiones_validas.add( new Punto( i, j ) );
				    }
			      }
			}
		}
		
		
		if( pocisiones_validas.size()>0 )
		{
			//System.out.println( pocisiones_validas.size() );
			if( estado.equals("buscar") )
			{
				direccion= random.nextInt( pocisiones_validas.size() );
				
				ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= null;
				pocision_hormiga.setX( pocisiones_validas.get( direccion ).getX() );
				pocision_hormiga.setY( pocisiones_validas.get( direccion ).getY() );
				ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= this;
				
				pocisiones_validas.clear();
			}
			else
			{
			      if( estado.equals("seguir_feromona") )
			      {
				      double dist = 0;
				      int temp_pos = 0;

				      for( int i=0; i<pocisiones_validas.size(); i++ )
				      {
					     if( ambiente[pocisiones_validas.get(i).getX()][pocisiones_validas.get(i).getY()] != null )
					     {
						    if( (ambiente[pocisiones_validas.get(i).getX()][pocisiones_validas.get(i).getY()]).getClass().toString().equals("class Feromona") )
						    {
							  int x = pocisiones_validas.get(i).getX();
							  int y = pocisiones_validas.get(i).getY();
							  double res = Math.sqrt( Math.pow( (double)(x - pos_Hormiguero.getX()), 2 ) + Math.pow( (double)(y - pos_Hormiguero.getY()), 2 ) );
							  if( res >= dist )
							  {
							      dist = res;
							      temp_pos = i;
							  }
						    }
					      }
				      }

					      ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= null;

				      pocision_hormiga.setX( pocisiones_validas.get( temp_pos ).getX() );
				      pocision_hormiga.setY( pocisiones_validas.get( temp_pos ).getY() );
				      ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= this;
				      estado = "buscar";
				      
				      pocisiones_validas.clear();
			      }
			      else
			      {
				      if( estado.equals("recoger_comida") )
				      {
					    recoger_comida( pocisiones_validas.get(0).getX(), pocisiones_validas.get(0).getY() );
				      }
			      }
			}
		}
	}
	
	public boolean validar( int x, int y )
	{
		if( x>=0 && x<50  && y>=0 && y<50 )
		{
			if( ambiente[x][y] == null  )
			{
				return true;
			}
			else
			{
				if( ambiente[x][y].getClass().toString().equals("class Comida") && ( estado.equals("buscar") || estado.equals("seguir_feromona") ) )
				{
					estado = "recoger_comida";
					return true;
				}
				else
				{	
					if( ambiente[x][y].getClass().toString().equals("class Feromona") )
					{
						if(((Feromona)ambiente[x][y]).tipo.equals(especie) )
						{
							if(estado.equals("buscar"))
							{
							      estado = "seguir_feromona";
							}
							return true;
						}
						else
						return false;
					}
					else
					return false;
				}
			}			
		}
		return false;
	}
	
	public void recoger_comida( int x ,int y )
	{
		capacidad++;
		estado= "regresar";
		ambiente[x][y]= null;
		ambiente[pocision_hormiga.getX()][pocision_hormiga.getY()]= null;
		pocision_hormiga.setX( x );
		pocision_hormiga.setY( y );
		ambiente[x][y]= this;
	}
	
 	public void regresar()
	{

		      //bajando
		if( pos_Hormiguero.getY()-pocision_hormiga.getY()>= 0 )
		{
			if( validar( pocision_hormiga.getX(), pocision_hormiga.getY()+1 ) )
			{
				ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= new Feromona( especie );
				pocision_hormiga.setY( pocision_hormiga.getY()+1 );
				ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= this;
				
			}	
		}
		else
		{
			//subiendo
			if( pos_Hormiguero.getY()-pocision_hormiga.getY()< 0 )
			{
				if( validar( pocision_hormiga.getX(), pocision_hormiga.getY()-1 ) )
				{
					ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= new Feromona( especie );
					pocision_hormiga.setY( pocision_hormiga.getY()-1 );
					ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= this;
				}
			}
		}
		

			if( pos_Hormiguero.getX()-pocision_hormiga.getX()>= 0 )
			{
				if( validar( pocision_hormiga.getX()+1, pocision_hormiga.getY() ) )
				{
					ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= new Feromona( especie );
					pocision_hormiga.setX( pocision_hormiga.getX()+1 );
					ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= this;
				}	
			}
			else
			{
				//izquierda
				if( pos_Hormiguero.getX()-pocision_hormiga.getX()< 0 )
				{
					if( validar( pocision_hormiga.getX()-1, pocision_hormiga.getY() ) )
					{
						ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= new Feromona( especie );
						pocision_hormiga.setX( pocision_hormiga.getX()-1 );
						ambiente[ pocision_hormiga.getX() ][ pocision_hormiga.getY() ]= this;
					}	
				}			
			}
		
	}

	public void validar_hostilidad()
	{
		int cantidad_enemigos= 0;

		for(int i=pocision_hormiga.getX()-1; i<pocision_hormiga.getX()+2; i++)
		{
			for(int j=pocision_hormiga.getY()-1; j<pocision_hormiga.getY()+2; j++)
			{
			      if( i>=0 && i<50 && j>=0 && j<50 )
			      {
				    if( ambiente[i][j] != null )
				    {
					  if( ambiente[i][j].getClass().toString().equals("class Hormiga") )
					  {
						if( !(((Hormiga)ambiente[i][j]).especie.equals(especie)) )
						{
						      cantidad_enemigos++;
						}
					  }
				    }
			      }
			}
		}
		
		if( cantidad_enemigos > 1 )
		{
		      vitalidad = false;
		}
	}
}

