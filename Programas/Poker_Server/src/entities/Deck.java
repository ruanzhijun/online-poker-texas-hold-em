package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Card's deck composition and usage.
 * @author Mario Codes
 * @version 0.0.3 Implemented method to retrieve table cards.
 */
public class Deck {
    private ArrayList<Card> cards_table = new ArrayList<Card>();
    private ArrayList<Card> deck = new ArrayList<Card>(52);
    
    /**
     * Default constructor. Gets everything ready.
     */
    public Deck() {
        prepareDeck();
    }
    
    /**
     * Ini of the 13 cards of every suit. Must be repeated 4 times, once per suit.
     * @param suit Suit being initialized in the current run. 
     */
    private void iniSuits(String suit) {
        for (int i = 0; i < 9; i++)
            deck.add(new Card(Integer.toString(i+1), suit));
        
        deck.add(new Card("J", suit));
        deck.add(new Card("Q", suit));
        deck.add(new Card("K", suit));
        deck.add(new Card("A", suit));
    }
    
    /**
     * Initializes the deck with every card.
     */
    private void iniDeck() {
        iniSuits("hearts");
        iniSuits("picas");
        iniSuits("trebol");
        iniSuits("diamonds");
    }
    
    /**
     * Random shuffle of a deck.
     * Done through Fisher-Yates algorithm. I've adapted it to an AL instead of int[].
     * @param deck Deck to shuffle.
     */
    private void shuffle(ArrayList<Card> deck) {
        Random rand = ThreadLocalRandom.current();
        for (int i = deck.size() - 1; i > 0; i--) { // Shuffles a list random without repeating an index.
            int index = rand.nextInt(i+1);
            Card cartaRandom = deck.get(index);
            deck.set(index, deck.get(i));
            deck.set(i, cartaRandom);
        }
    }
    
    /**
     * Getting a full new deck ready.
     * Initialization and random shuffle.
     */
    private void prepareDeck() {
        iniDeck();
        shuffle(deck);
    }
    
    
    /**
     * As stated by the rules, burns the required number of cards.
     * @param cards Int. Number of cards to burn from the deck.
     * todo: check if used, if not delete.
     */
    private void burn(int cards) {
        for (int i = 0; i < cards; i++) {
            deck.remove(0);
        }
    }
    
    
    /**
     * Obtains one card from the deck.
     * @return Card removed from the deck.
     */
    public Card getCard() {
        return deck.remove(0);
    }
    
    
    /**
     * Obtains i cards from the deck.
     * @param number Number of cards we want to obtain.
     * @return AL<Card>. Contains the cards removed from the deck.
     */
    public ArrayList<Card> getCards(int number) {
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < number; i++) cards.add(deck.remove(0));
        
        return cards;
    }
    
    /**
     * Retrieves cards from the deck and adds them into the AL.
     * @param number Number of cards to retrieve.
     */
    public void retrieveTableCards(int number) {
        for (int i = 0; i < number; i++) getCards_table().add(getCard());
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        int i = 1;
        for(Card carta: deck) {
            sb.append('#');
            sb.append(i++);
            sb.append(" ");
            sb.append(carta);
            sb.append("\n");
        }
        
        return sb.toString();
    }

    /**
     * @return the cards_table
     */
    public ArrayList<Card> getCards_table() {
        return cards_table;
    }
    
    /**
     * Private, inner static class to check what does a player have and assign it a value. Comparing those values we get the winner.
     * Checks it from high to low, when detects a play, returns it.
     * Done as inner because it has no sense for it to exist without a deck.
     */
    private static class Play {    
        public static int valor; //fixme: 0,5. Cambiar a private.
        public static String jugada;
    
        /**
         * Obtener el valor numerico de una carta. A = 14, K = 13...
         * @param carta Carta de la cual obtenemos el valor.
         * @return Valor numerico de la Carta.
         */
        public static int getValor(Card carta) {
            int valor = -1;
            String v = carta.toString().substring(0, 1);
            if(v.matches("1")) v = "10";
            try {
                valor = Integer.parseInt(v);
            }catch(ClassCastException|NumberFormatException ex) {
                switch(v) {
                    case "A":
                        valor = 14;
                        break;
                    case "K":
                        valor = 13;
                        break;
                    case "Q":
                        valor = 12;
                        break;
                    case "J":
                        valor = 11;
                        break;
                    default:
                        valor = -1;
                        System.out.println("Valor por defecto en getValor().");
                        break;
                }
            }

            return valor;
        }

        /**
         * Obtencion del palo de la carta para comprobar escaleras, fulls y demas.
         * @param carta Carta a obtener el palo.
         * @return Palo de la carta introducida.
         */
        private static String getPalo(Card carta) {
            return carta.toString().substring(carta.toString().indexOf(',')+1);
        }

        /**
         * Comprobacion para la escalera de color. Le paso una arraylist de cartas y compruebo si la carta con el valor que me interesa, es del palo que busco.
         * @param cartas Cartas totales en juego para rebuscar.
         * @param palo Palo que me tiene la carta que busco.
         * @param valor Valor que tiene la carta que debo encontrar.
         * @return True si la carta con valor x es del palo interesado.
         */
        private static boolean checkValorYPalo(ArrayList<Card> cartas, String palo, int valor) {
            boolean match = false;

            for(Card c: cartas) {
                if(getPalo(c).matches(palo) && getValor(c) == valor) match = true;
            }

            return match;
        }

        /**
         * Comprobacion de si hay escalera real. A - K - Q - J - 10; todos del mismo palo.
         * @param propias Cartas propias de cada jugador.
         * @param comunes Cartas comunes a todos los jugadores.
         * @return True si hay escalera real.
         */
        private static boolean checkEscaleraReal(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);
            ArrayList<Card> cartas = new ArrayList<>();
            cartas.addAll(propias);
            cartas.addAll(comunes);

            for (int i = 0; i < valores.size(); i++) {
                int valor = valores.get(i);
                String palo = getPalo(cartas.get(i));
    //            System.out.println("Valor: " +valor);
                if(valor == 10) { //Siempre tiene que empezar en 10.
                    if(valores.contains((Integer) valor+1) && checkValorYPalo(cartas, palo, valor+1)) {
                        if(valores.contains((Integer) valor+2) && checkValorYPalo(cartas, palo, valor+2)) {
                            if(valores.contains((Integer) valor+3) && checkValorYPalo(cartas, palo, valor+3)) {
                                if(valores.contains((Integer) valor+4) && checkValorYPalo(cartas, palo, valor+4)) { //A partir de aqui, escalera real encontrada. No hace falta los otros metodos ya que la real son 5 max y las mas altas.
                                    Play.valor = valor+(valor+1)+(valor+2)+(valor+3)+(valor+4);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Comprobacion de si hay Escalera de Color. Escalera, todas del mismo palo.
         * @param propias Cartas propias del jugador.
         * @param comunes Cartas comunes a todos. 
         * @return True si hay escalera.
         */
        private static boolean checkEscaleraColor(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);
            ArrayList<Card> cartas = new ArrayList<>();
            cartas.addAll(propias);
            cartas.addAll(comunes);

            for (int i = 0; i < valores.size(); i++) {
                int valor = valores.get(i);
                String palo = getPalo(cartas.get(i));
    //            System.out.println("Valor: " +valor);
                if(valores.contains((Integer) valor+1) && checkValorYPalo(cartas, palo, valor+1)) {
                    if(valores.contains((Integer) valor+2) && checkValorYPalo(cartas, palo, valor+2)) {
                        if(valores.contains((Integer) valor+3) && checkValorYPalo(cartas, palo, valor+3)) {
                            if(valores.contains((Integer) valor+4) && checkValorYPalo(cartas, palo, valor+4)) { //A partir de aqui ya hay escalera existente. El resto es por si existe escalera de +5 cartas, que pille los valores mas altos.
                                Play.valor = valor+(valor+1)+(valor+2)+(valor+3)+(valor+4);
                                if(valores.contains((Integer) valor+5) && checkValorYPalo(cartas, palo, valor+5)) {
                                    Play.valor -= valor;
                                    Play.valor += (valor+5);
                                    if(valores.contains((Integer) valor+6) && checkValorYPalo(cartas, palo, valor+6)) { //Mas de 7 Cartas nunca habra en Juego.
                                        Play.valor -= valor+1;
                                        Play.valor += (valor+6);
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Check de Poker.
         * @param propias Cartas propias del jugador.
         * @param comunes Cartas comunes a todos.
         * @return True si hay poker.
         */
        private static boolean checkPoker(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);

            for (int i = 0; i < valores.size(); i++) { //Buscado de una primera pareja.
                int valor1 = valores.get(i);
    //            System.out.println("\tValor1: " +valor1 +", Indice: " +i);
                for (int j = 0; j < valores.size(); j++) {
                    int valor2 = valores.get(j);
    //                System.out.println("\tValor2: " +valor2 +", Indice: " +j);
                    if(j != i) {
                        for (int q = 0; q < valores.size(); q++) {
                            int valor3 = valores.get(q);
    //                        System.out.println("\tValor3 " +valor3 +", Indice: " +q);
                            if(q != j && q != i) { 
                                if(valor3 == valor2 && valor3 == valor1) { //Trio encontrado
                                    for (int k = 0; k < valores.size(); k++) {
                                        int valor4 = valores.get(k);
                                        if(k != q && k != j && k != i) { //Poker Encontrado.
                                            if(valor4 == valor3) {
                                                Play.valor = valor4+valor3+valor2+valor1;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Check de si existe un full.
         * @param propias Cartas propias del jugador.
         * @param comunes Cartas comunes a todos.
         * @return True si hay full.
         */
        private static boolean checkFull(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);
            boolean parejaEncontrada = false;
            boolean trioEncontrado = false;
            int valorTrio = 0;

            for (int i = 0; i < valores.size() && !parejaEncontrada; i++) { //Buscado de trio.
                int valor1 = valores.get(i);
    //            System.out.println("\tValor1: " +valor1 +", Indice: " +i);
                for (int j = 0; j < valores.size() && !parejaEncontrada; j++) {
                    int valor2 = valores.get(j);
    //                System.out.println("\tValor2: " +valor2 +", Indice: " +j);
                    if(j != i) {
                        for (int q = 0; q < valores.size() && !parejaEncontrada; q++) {
                            int valor3 = valores.get(q);
    //                        System.out.println("\tValor3 " +valor3 +", Indice: " +q);
                            if(q != j && q != i) {
                                if(valor3 == valor2 && valor3 == valor1) {
                                    Play.valor = valor3+valor2+valor1;
                                    trioEncontrado = true;
                                    valorTrio = valor1;
    //                                System.out.println("Trio de: " +valor1); //fixme: 0,5. Esta mal, aqui llega 6 veces, deberia 1. No se porque, lo he testeado y funciona bien de todos modos. Mirarlo mas adelante si sobra tiempo.
                                }
                            }
                        }
                    }
                }
            }
    //        System.out.println("Trio de: " +valorTrio);

            if(trioEncontrado) {
                for (int i = 0; i < valores.size(); i++) { //Buscado de pareja.
                    int valor = valores.get(i);
                    for (int j = 0; j < valores.size(); j++) {
                        int valor2 = valores.get(j);
                        if(j != i) {
                            if(valor == valor2 && valor != valorTrio) { //Full encontrado.
                                Play.valor += valor+valor2;

                                for (int k = 0; k < valores.size(); k++) { //Buscado de otra pareja con mas valor que la actual;
                                    int valorSegunda = valores.get(k);
                                    for (int l = 0; l < valores.size(); l++) {
                                        int valorSegunda2 = valores.get(l);
                                        if(l != k && l != j && l != i) {
                                            if(valorSegunda == valorSegunda2 && valorSegunda != valor && valorSegunda != valorTrio) {
                                                Play.valor -= valor+valor2;
                                                Play.valor += valorSegunda*2;
                                                valor = valorSegunda;
                                            }
                                        }
                                    }
                                }

    //                            System.out.println("Pareja de valor: " +valor);
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Check de si existe color.
         * @param propias Cartas propias del jugador.
         * @param comunes Cartas comunes a todos.
         * @return True si existe color.
         */
        private static boolean checkColor(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Card> cartas = new ArrayList<>();
            cartas.addAll(propias);
            cartas.addAll(comunes);

            ArrayList<String> palos = getPalos(propias, comunes);
            boolean existe = false;
            String paloColor = "";
            int iguales = 1;

            for (int i = 0; i < palos.size(); i++) {
                String palo = palos.get(i);
    //            System.out.println("Palo a comprobar: " +palo);
                for (int j = 0; j < palos.size(); j++) {
                    if(j != i) {
                        if(palos.get(j).matches(palo)) iguales++;
                        if(iguales == 5) {
                            existe = true;
                            paloColor = palo;
                        }
                    }
                }
                iguales = 1;
            }

            if(existe) {
                int valor = 0;
                for (int i = 0; i < cartas.size(); i++) {
                    if(getPalo(cartas.get(i)).matches(paloColor) && getValor(cartas.get(i)) > valor) valor = getValor(cartas.get(i));
                }
                Play.valor = valor;
            }

            return existe;
        }

        /**
         * Check de si existe escalera Normal. (5 cartas consecutivas de distinto palo).
         * @param propias Cartas exclusivas del jugador.
         * @param comunes Cartas comunes a todos los jugadores.
         * @return True si existe escalera.
         */
        private static boolean checkEscalera(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);

            for (int i = 0; i < valores.size(); i++) {
                int valor = valores.get(i);
    //            System.out.println("Valor: " +valor);
                if(valores.contains((Integer) valor+1)) {
                    if(valores.contains((Integer) valor+2)) {
                        if(valores.contains((Integer) valor+3)) {
                            if(valores.contains((Integer) valor+4)) { //A partir de aqui ya hay escalera existente. El resto es por si existe escalera de +5 cartas, que pille los valores mas altos.
                                Play.valor = valor+(valor+1)+(valor+2)+(valor+3)+(valor+4);
                                if(valores.contains((Integer) valor+5)) {
                                    Play.valor -= valor;
                                    Play.valor += (valor+5);
                                    if(valores.contains((Integer) valor+6)) { //Mas de 7 Cartas nunca habra en Juego.
                                        Play.valor -= valor+1;
                                        Play.valor += (valor+6);
                                    }
                                }
                                return true;
                            }
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Comprobacion de si hay trio. Tres cartas iguales.
         * @param propias Cartas propias del Jugador.
         * @param comunes Cartas comunes a todos.
         * @return True si hay trio.
         */
        private static boolean checkTrio(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);
            boolean parejaEncontrada = false;

            for (int i = 0; i < valores.size() && !parejaEncontrada; i++) { //Buscado de una primera pareja.
                int valor1 = valores.get(i);
    //            System.out.println("\tValor1: " +valor1 +", Indice: " +i);
                for (int j = 0; j < valores.size() && !parejaEncontrada; j++) {
                    int valor2 = valores.get(j);
    //                System.out.println("\tValor2: " +valor2 +", Indice: " +j);
                    if(j != i) {
                        for (int q = 0; q < valores.size() && !parejaEncontrada; q++) {
                            int valor3 = valores.get(q);
    //                        System.out.println("\tValor3 " +valor3 +", Indice: " +q);
                            if(q != j && q != i) {
                                if(valor3 == valor2 && valor3 == valor1) {
                                    Play.valor = valor3+valor2+valor1;
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Check para comprobar si tiene dos parejas independientes.
         * @param propias Cartas propias del jugador.
         * @param comunes Cartas comunes a todos.
         * @return True si dispone de doble pareja.
         */
        private static boolean checkDoblePareja(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);
            boolean parejaEncontrada = false;
            int parejaUno = 0;

            for (int i = 0; i < valores.size() && !parejaEncontrada; i++) { //Buscado de una primera pareja.
                int valor = valores.get(i);
                for (int j = 0; j < valores.size() && !parejaEncontrada; j++) {
                    int valor2 = valores.get(j);
                    if(j != i) {
                        if(valor == valor2) {
                            parejaUno += valor+valor2;
                            parejaEncontrada = true;
                        }
                    }
                }
            }

            if(parejaUno == 0) return false; //No hace falta que siga buscando.

            for (int i = 0; i < valores.size(); i++) { //Segunda pareja, que no sea la primera.
                int valor = valores.get(i);
                for (int j = 0; j < valores.size(); j++) {
                    int valor2 = valores.get(j);
                    if(j != i) {
                        if(valor == valor2 && ((valor+valor2) != parejaUno)) {
                            Play.valor += (valor+valor2+parejaUno);
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Comprobacion de si el jugador dispone de una pareja simple. (2 cartas iguales).
         * @param propias Cartas propias del jugador.
         * @param comunes Cartas comunes a todos.
         * @return True si dispone de pareja.
         */
        public static boolean checkPareja(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = getValores(propias, comunes);

            for (int i = 0; i < valores.size(); i++) {
                int valor = valores.get(i);
                for (int j = 0; j < valores.size(); j++) {
                    int valor2 = valores.get(j);
                    if(j != i) {
                        if(valor == valor2) {
                            Play.valor = valor+valor2;
                            return true;
                        }
                    }
                }
            }

            return false;
        }

        /**
         * Comprobacion de cual es la carta mas alta.
         * @param propias Cartas propias del jugador.
         * @param comunes Cartas comunes a todos.
         * @return True siempre ya que es la minima combinacion posible.
         */
        private static boolean checkCartaAlta(ArrayList<Card> propias, ArrayList<Card> comunes) {
            int maxValor = getValor(propias.get(0));
            if(getValor(propias.get(1)) > maxValor) maxValor = getValor(propias.get(1));
            else {
                for(Card c: comunes) {
                    if(getValor(c) > maxValor) maxValor = getValor(c);
                }
            }

            Play.valor = maxValor;
            return true;
        }

        /**
         * Mezcla de todas las cartas para chequear palos en una unica ArrayList.
         * @param propias Cartas propias del Jugador.
         * @param comunes Cartas comunes a todos los jugadores.
         * @return ArrayList conjunta de todos los palos existentes.
         */
        private static ArrayList<String> getPalos(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<String> palos = new ArrayList<>();
            for(Card c: propias) palos.add(getPalo(c));
            for(Card c: comunes) palos.add(getPalo(c));
            palos.sort(Comparator.naturalOrder());
            return palos;
        }

        /**
         * Mezcla de todas las cartas para chequear combinaciones posibles en una unica ArrayList.
         * @param propias Cartas propias del Jugador.
         * @param comunes Cartas comunes a todos.
         * @return ArrayList conjunta a todas las cartas.
         */
        private static ArrayList<Integer> getValores(ArrayList<Card> propias, ArrayList<Card> comunes) {
            ArrayList<Integer> valores = new ArrayList<>();
            for(Card c: propias) valores.add(getValor(c));
            for(Card c: comunes) valores.add(getValor(c));
    //        valores.sort(Comparator.naturalOrder());
            return valores;
        }
        
        /**
         * Method to use externally.
         * Checks the play with all the cards in play.
         * Checks the value to untie.
         * @param own Private player card's. They're always 2.
         * @param common Common cards to all the players. They're 5 but we only take into account the 3 best.
         */
        private static void checkJugada(ArrayList<Card> own, ArrayList<Card> common) {
            if(checkEscaleraReal(own, common)) Play.jugada = "Escalera Real";
            else {
                if(checkEscaleraColor(own, common)) Play.jugada = "Escalera de Color";
                else {
                    if(checkPoker(own, common)) Play.jugada = "Poker";
                    else {
                        if(checkFull(own, common)) Play.jugada = "Full";
                        else {
                            if(checkColor(own, common)) Play.jugada = "Color";
                            else {
                                if(checkEscalera(own, common)) Play.jugada = "Escalera";
                                else {
                                    if(checkTrio(own, common)) Play.jugada = "Trio";
                                    else {
                                        if(checkDoblePareja(own, common)) Play.jugada = "Doble Pareja";
                                        else {
                                            if(checkPareja(own, common)) Play.jugada = "Pareja";
                                            else {
                                                if(checkCartaAlta(own, common)) Play.jugada = "Carta Alta";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
