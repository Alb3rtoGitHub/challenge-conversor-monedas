package vista;

public class MenuVista {
    public void mostrarMenu() {
        System.out.println("""
                
                ********************************************
                Sea bienvenido/a al Conversor de Moneda =]
                
                1) Dólar =>> Peso argentino
                2) Peso argentino =>> Dólar
                3) Dólar =>> Real brasileño
                4) Real brasileño =>> Dólar
                5) Euro =>> Peso argentino
                6) Peso argentino =>> Euro
                7) Libra Esterlina =>> Euro
                8) Euro =>> Libra Esterlina
                9) Otras Opciones de Moneda
                10) Ver Historial de Conversiones
                11) Salir
                ********************************************""");
        System.out.print("Elija una opción válida: ");
    }

    public void mostrarMonedasDisponibles() {
        System.out.println("""
                ********************************************
                Códigos ISO 4217 de monedas más comunes:
                        USD - Dólar estadounidense
                        EUR - Euro
                        GBP - Libra esterlina
                        JPY - Yen japonés
                        ARS - Peso argentino
                        BRL - Real brasileño
                        CLP - Peso chileno
                        CAD - Dólar canadiense
                        AUD - Dólar australiano
                        CHF - Franco suizo
                        CNY - Yuan chino
                        MXN - Peso mexicano
                        INR - Rupia india
                        RUB - Rublo ruso
                        ZAR - Rand sudafricano
                ********************************************""");
    }
}
