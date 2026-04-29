public class EstrategiaAlphaBeta extends EstrategiaMiniMax {

    /*
     * Estrategia que implementa una busqueda MINIMAX
     * con poda ALFA-BETA.
     */

    /** Creates a new instance of EstrategiaAlphaBeta */
    public EstrategiaAlphaBeta() {
        super();
    }

    public EstrategiaAlphaBeta(Evaluador evaluador, int capaMaxima) {
        super(evaluador, capaMaxima);
    }

    public int buscarMovimiento(Tablero tablero, int jugador) {
        // Primera capa del MINIMAX + seleccion jugada mas prometedora
        // capa 0 -> capa MAX -> maximiza
        // devuelve la columna con mayor evaluacion

        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;

        int valorSucesor;
        int mejorPosicion = -1;              // Movimiento nulo
        int mejorValor = _evaluador.MINIMO;  // Minimo valor posible

        _jugadorMAX = jugador;

        int alpha = _evaluador.MINIMO;
        int betha = _evaluador.MAXIMO;

        for (int col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();

                valorSucesor = ALFABETA(nuevoTablero,Jugador.alternarJugador(jugador),1,alpha,betha);

                nuevoTablero = null;

                if (valorSucesor >= mejorValor) {
                    mejorValor = valorSucesor;
                    mejorPosicion = col;
                }

                alpha = maximo2(alpha, mejorValor);
            }
        }

        return mejorPosicion;
    }

    public int ALFABETA(Tablero tablero, int jugador, int capa, int alpha, int betha) {
        // Casos base

        if (tablero.hayEmpate()) {
            return 0;
        }

        // La evaluacion de posiciones finales se hace siempre
        // desde la perspectiva de MAX.

        if (tablero.esGanador(_jugadorMAX)) {
            return _evaluador.MAXIMO;
        }

        if (tablero.esGanador(Jugador.alternarJugador(_jugadorMAX))) {
            return _evaluador.MINIMO;
        }

        if (capa == _capaMaxima) {
            return _evaluador.valoracion(tablero, _jugadorMAX);
        }

        if (esCapaMIN(capa)) {
            return ALFABETAMIN(tablero, jugador, capa, alpha, betha);
        } else {
            return ALFABETAMAX(tablero, jugador, capa, alpha, betha);
        }
    }

    private int ALFABETAMIN(Tablero tablero, int jugador, int capa, int alpha, int betha) {
        int bethaactual = betha;
        int vActual = _evaluador.MAXIMO;
        int aux = 0;

        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;

        for (int col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();

                aux = ALFABETA(nuevoTablero,Jugador.alternarJugador(jugador),capa + 1,alpha,bethaactual);

                nuevoTablero = null;

                if (vActual > aux) {
                    vActual = aux; // hacer minimo
                }

                if (bethaactual > vActual) {
                    bethaactual = vActual;
                }

                // Poda alfa-beta
                if (alpha >= bethaactual) {
                    return vActual;
                }
            }
        }

        return vActual;
    }

    private int ALFABETAMAX(Tablero tablero, int jugador, int capa, int alpha, int betha) {
        int alphaactual = alpha;
        int vActual = _evaluador.MINIMO;
        int aux = 0;

        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;

        for (int col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();

                aux = ALFABETA(nuevoTablero,Jugador.alternarJugador(jugador),capa + 1,alphaactual,betha);

                nuevoTablero = null;

                if (vActual < aux) {
                    vActual = aux; // hacer maximo
                }

                if (alphaactual < vActual) {
                    alphaactual = vActual;
                }

                // Poda alfa-beta
                if (betha <= alphaactual) {
                    return vActual;
                }
            }
        }

        return vActual;
    }

} // Fin clase EstrategiaAlphaBeta
