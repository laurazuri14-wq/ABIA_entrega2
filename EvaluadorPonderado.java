/*
 * Evaluador.java
 *
 * Created on 10 de enero de 2004, 17:03
 */

/**
 *
 * @author  ribadas
 */
public  class EvaluadorPonderado extends Evaluador {
    /* Implementa la superclase del patron Estrategia para encapsular
     * las distintas funciones de evaluacion
     * 
     * Define el interfaz (funcion "valoracion")
     */
   
    
    
    
    /** Creates a new instance of Evaluador */
    public EvaluadorPonderado() {
    }
    
    public  int valoracion(Tablero tablero, int jugador){
        return tablero.contarCentro();
    }
    
}