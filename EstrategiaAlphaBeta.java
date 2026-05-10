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

        

        for (int col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();
                valorSucesor = ALFABETA(nuevoTablero,Jugador.alternarJugador(jugador),1,_evaluador.MINIMO,_evaluador.MAXIMO);
                nuevoTablero = null;

                if (valorSucesor >= mejorValor) {
                    mejorValor = valorSucesor;
                    mejorPosicion = col;
                }

                
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
        int bactual = betha;
        int vActual = _evaluador.MAXIMO;

        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;

        for (int col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();

                int aux = ALFABETA(nuevoTablero,Jugador.alternarJugador(jugador),capa + 1,alpha,bactual);

                nuevoTablero = null;
                vActual = minimo2(vActual,aux);
                bactual = minimo2(bactual,vActual);

                

                // Poda alfa-beta
                if (alpha >= bactual) {
                    return vActual;
                }


            }
        }

        return vActual;
    }

    private int ALFABETAMAX(Tablero tablero, int jugador, int capa, int alpha, int betha) {
        int aActual = alpha;
        int vActual = _evaluador.MINIMO;

        boolean movimientosPosibles[] = tablero.columnasLibres();
        Tablero nuevoTablero;

        for (int col = 0; col < Tablero.NCOLUMNAS; col++) {
            if (movimientosPosibles[col]) {
                nuevoTablero = (Tablero) tablero.clone();
                nuevoTablero.anadirFicha(col, jugador);
                nuevoTablero.obtenerGanador();

                int aux = ALFABETA(nuevoTablero,Jugador.alternarJugador(jugador),capa + 1,aActual,betha);

                nuevoTablero = null;

                vActual = maximo2(vActual,aux);
                aActual = maximo2(aActual,vActual);

                // Poda alfa-beta
                if (betha <= aActual) {
                    
                    return vActual;
                }
            }
        }

        return vActual;
    }

} // Fin clase EstrategiaAlphaBeta
