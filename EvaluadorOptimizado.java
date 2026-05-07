public class EvaluadorOptimizado extends EvaluadorPonderado {

    /*
     * Evaluador optimizado.
     * Usa los pesos obtenidos por el OptimizadorPesos.
     */

    public EvaluadorOptimizado() {
        super(new PesosEvaluacion(5, 10, 100, 3));
    }
}
