/*
 * Evaluador.java
 *
 * Created on 10 de enero de 2004, 17:03
 */

/**
 *
 * @author  ribadas
 */
public abstract class EvaluadorPonderado {
    /* Implementa la superclase del patron Estrategia para encapsular
     * las distintas funciones de evaluacion
     * 
     * Define el interfaz (funcion "valoracion")
     */
   
    
    
    
    /** Creates a new instance of Evaluador */
    public EvaluadorPonderado() {
    }
    
    public abstract int valoracion(Tablero tablero, int jugador);
    
}