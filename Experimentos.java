public class Experimentos {

    static final int NUM_PARTIDAS = 10;

    public static void main(String[] args) {
        int[] profundidades = {1, 2, 3, 4};

        for (int profundidad : profundidades) {
            System.out.println("\nPROFUNDIDAD " + profundidad);

            ejecutarExperimento(
                "AB + optimizada VS AB + inicial",
                new EstrategiaAlphaBeta(new EvaluadorOptimizado(), profundidad),
                new EstrategiaAlphaBeta(new EvaluadorPonderado(), profundidad)
            );

            ejecutarExperimento(
                "AB + optimizada VS AB + aleatoria",
                new EstrategiaAlphaBeta(new EvaluadorOptimizado(), profundidad),
                new EstrategiaAlphaBeta(new EvaluadorAleatorio(), profundidad)
            );

            ejecutarExperimento(
                "AB + optimizada VS MINIMAX + optimizada",
                new EstrategiaAlphaBeta(new EvaluadorOptimizado(), profundidad),
                new EstrategiaMiniMax(new EvaluadorOptimizado(), profundidad)
            );
        }
    }

    private static void ejecutarExperimento(String nombre, EstrategiaMiniMax estrategiaA, EstrategiaMiniMax estrategiaB) {
        int victoriasA = 0;
        int victoriasB = 0;
        int empates = 0;

        long nodosA = 0;
        long nodosB = 0;
        double tiempoA = 0;
        double tiempoB = 0;

        int movimientosA = 0;
        int movimientosB = 0;

        for (int i = 0; i < NUM_PARTIDAS; i++) {
            Resultado r;

        if (i % 2 == 0) {
        r = jugarPartida(estrategiaA, estrategiaB);

        if (r.ganador == 1) victoriasA++;
        else if (r.ganador == 2) victoriasB++;
        else empates++;

        nodosA += r.nodosA;
        nodosB += r.nodosB;
        tiempoA += r.tiempoA;
        tiempoB += r.tiempoB;
        movimientosA += r.movimientosA;
        movimientosB += r.movimientosB;

    } else {
        r = jugarPartida(estrategiaB, estrategiaA);

        if (r.ganador == 1) victoriasB++;
        else if (r.ganador == 2) victoriasA++;
        else empates++;

        nodosA += r.nodosB;
        nodosB += r.nodosA;
        tiempoA += r.tiempoB;
        tiempoB += r.tiempoA;
        movimientosA += r.movimientosB;
        movimientosB += r.movimientosA;
    }

        System.out.println(nombre);
        System.out.println("Victorias A: " + victoriasA * 100.0 / NUM_PARTIDAS + "%");
        System.out.println("Victorias B: " + victoriasB * 100.0 / NUM_PARTIDAS + "%");
        System.out.println("Empates: " + empates * 100.0 / NUM_PARTIDAS + "%");

        System.out.println("Tiempo medio A: " + tiempoA / Math.max(1, movimientosA) + " ms");
        System.out.println("Tiempo medio B: " + tiempoB / Math.max(1, movimientosB) + " ms");

        System.out.println("Nodos medios A: " + nodosA / Math.max(1, movimientosA));
        System.out.println("Nodos medios B: " + nodosB / Math.max(1, movimientosB));
        System.out.println("-----------------------------------");
    }

    private static Resultado jugarPartida(EstrategiaMiniMax estrategiaJ1, EstrategiaMiniMax estrategiaJ2) {
        Tablero tablero = new Tablero();
        tablero.inicializar();
        tablero.obtenerGanador();

        Resultado r = new Resultado();

        int jugadorActual = 1;

        while (!tablero.esFinal()) {
            EstrategiaMiniMax estrategiaActual = jugadorActual == 1 ? estrategiaJ1 : estrategiaJ2;

            int columna = estrategiaActual.buscarMovimiento(tablero, jugadorActual);

            if (columna == -1) {
                r.ganador = 0;
                return r;
            }

            tablero.anadirFicha(columna, jugadorActual);
            tablero.obtenerGanador();

            if (jugadorActual == 1) {
                r.nodosA += estrategiaActual.getNodosGenerados();
                r.tiempoA += estrategiaActual.getTiempoUltimaBusquedaMs();
                r.movimientosA++;
            } else {
                r.nodosB += estrategiaActual.getNodosGenerados();
                r.tiempoB += estrategiaActual.getTiempoUltimaBusquedaMs();
                r.movimientosB++;
            }

            jugadorActual = Jugador.alternarJugador(jugadorActual);
        }

        if (tablero.esGanador(1)) r.ganador = 1;
        else if (tablero.esGanador(2)) r.ganador = 2;
        else r.ganador = 0;

        return r;
    }

    static class Resultado {
        int ganador;
        long nodosA = 0;
        long nodosB = 0;
        double tiempoA = 0;
        double tiempoB = 0;
        int movimientosA = 0;
        int movimientosB = 0;
    }
}