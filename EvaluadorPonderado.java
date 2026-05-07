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
    private PesosEvaluacion pesos;
    public EvaluadorPonderado() {
        this.pesos = new PesosEvaluacion(5, 10, 100, 3);
    }

    // Constructor con pesos definidos
    public EvaluadorPonderado(PesosEvaluacion pesos) {
        this.pesos = pesos;
    }

    public int valoracion(Tablero tablero, int jugador) {
        int oponente = Jugador.alternarJugador(jugador);
        int funcionEvaluacion = 0;

        funcionEvaluacion += tablero.contarLineas(jugador, 2) * pesos.pesoLineas2;
        funcionEvaluacion -= tablero.contarLineas(oponente, 2) * pesos.pesoLineas2;

        funcionEvaluacion += tablero.contarTriples(jugador) * pesos.pesoTriples;
        funcionEvaluacion -= tablero.contarTriples(oponente) * pesos.pesoTriples;

        funcionEvaluacion += tablero.contarCentro(jugador) * pesos.pesoCentro;
        funcionEvaluacion -= tablero.contarCentro(oponente) * pesos.pesoCentro;

        funcionEvaluacion += tablero.contarLineas(jugador, 4) * pesos.pesoLineas4;
        funcionEvaluacion -= tablero.contarLineas(oponente, 4) * pesos.pesoLineas4;

        return funcionEvaluacion;
    }

    
}