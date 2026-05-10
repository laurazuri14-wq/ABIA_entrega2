public class OptimizarPesos {

    private static final int PROFUNDIDAD = 4;
    private static final int NUM_PARTIDAS = 6;

    public static void main(String[] args) {
        PesosEvaluacion pesosActuales = new PesosEvaluacion(5, 10, 100, 3);

        PesosEvaluacion pesosMejores = optimizar(pesosActuales);

        System.out.println("Mejores pesos encontrados:");
        System.out.println("Centro: " + pesosMejores.pesoCentro);
        System.out.println("Lineas2: " + pesosMejores.pesoLineas2);
        System.out.println("Triples: " + pesosMejores.pesoTriples);
        System.out.println("Lineas4: " + pesosMejores.pesoLineas4);
    }

    public static PesosEvaluacion optimizar(PesosEvaluacion actuales) {
    boolean mejora = true;

    while (mejora) {
        mejora = false;

        PesosEvaluacion[] candidatos = generarCandidatos(actuales);
        PesosEvaluacion mejorCandidato = actuales;
        int mejorResultado = 0;

        int i = 0;
        while (i < candidatos.length) {
            int resultado = enfrentar(candidatos[i], actuales);

            if (resultado > mejorResultado) {
                mejorResultado = resultado;
                mejorCandidato = candidatos[i];
                mejora = true;
            }

            i++;
        }

        actuales = mejorCandidato;
    }

    return actuales;
}

    public static PesosEvaluacion[] generarCandidatos(PesosEvaluacion p) {
        return new PesosEvaluacion[] {
            new PesosEvaluacion(p.pesoCentro + 1, p.pesoLineas2, p.pesoTriples, p.pesoLineas4),
            new PesosEvaluacion(Math.max(1, p.pesoCentro - 1), p.pesoLineas2, p.pesoTriples, p.pesoLineas4),

            new PesosEvaluacion(p.pesoCentro, p.pesoLineas2 + 1, p.pesoTriples, p.pesoLineas4),
            new PesosEvaluacion(p.pesoCentro, Math.max(1, p.pesoLineas2 - 1), p.pesoTriples, p.pesoLineas4),

            new PesosEvaluacion(p.pesoCentro, p.pesoLineas2, p.pesoTriples + 10, p.pesoLineas4),
            new PesosEvaluacion(p.pesoCentro, p.pesoLineas2, Math.max(1, p.pesoTriples - 10), p.pesoLineas4),

            new PesosEvaluacion(p.pesoCentro, p.pesoLineas2, p.pesoTriples, p.pesoLineas4 + 1),
            new PesosEvaluacion(p.pesoCentro, p.pesoLineas2, p.pesoTriples, Math.max(1, p.pesoLineas4 - 1))
        };
    }

    public static int enfrentar(PesosEvaluacion candidato, PesosEvaluacion actual) {
        int puntosCandidato = 0;
        int puntosActual = 0;

        for (int i = 0; i < NUM_PARTIDAS; i++) {
            int resultado;

            if (i % 2 == 0) {
                resultado = jugarPartida(candidato, actual);

                if (resultado == 1) {
                    puntosCandidato += 3;
                } else if (resultado == 2) {
                    puntosActual += 3;
                } else {
                    puntosCandidato += 1;
                    puntosActual += 1;
                }
            } else {
                resultado = jugarPartida(actual, candidato);

                if (resultado == 1) {
                    puntosActual += 3;
                } else if (resultado == 2) {
                    puntosCandidato += 3;
                } else {
                    puntosCandidato += 1;
                    puntosActual += 1;
                }
            }
        }

        return puntosCandidato - puntosActual;
    }

    public static int jugarPartida(PesosEvaluacion pesosJ1, PesosEvaluacion pesosJ2) {
        Tablero tablero = new Tablero();

        Estrategia estrategiaJ1 = new EstrategiaAlphaBeta(
            new EvaluadorPonderado(pesosJ1),
            PROFUNDIDAD
        );

        Estrategia estrategiaJ2 = new EstrategiaAlphaBeta(
            new EvaluadorPonderado(pesosJ2),
            PROFUNDIDAD
        );

        int jugadorActual = 1;

        while (!tablero.finalJuego()) {
            int columna;

            if (jugadorActual == 1) {
                columna = estrategiaJ1.buscarMovimiento(tablero, 1);
            } else {
                columna = estrategiaJ2.buscarMovimiento(tablero, 2);
            }

            if (columna == -1) {
                return 0;
            }

            tablero.anadirFicha(columna, jugadorActual);
            tablero.obtenerGanador();

            jugadorActual = Jugador.alternarJugador(jugadorActual);
        }

        if (tablero.esGanador(1)) {
            return 1;
        }

        if (tablero.esGanador(2)) {
            return 2;
        }

        return 0;
    }
}
    

