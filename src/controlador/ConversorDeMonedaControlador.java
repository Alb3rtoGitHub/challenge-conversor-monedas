package controlador;

import modelo.Moneda;
import servicio.APIServicio;
import vista.MenuVista;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ConversorDeMonedaControlador {
    private final APIServicio apiServicio;
    private final MenuVista vista;
    private Moneda moneda;
    private final List<String> historialConversiones = new ArrayList<>();
    private final Scanner leer = new Scanner(System.in);
    private static final Set<String> MONEDAS_DISPONIBLES = Set.of("USD", "EUR", "GBP", "JPY", "ARS", "BRL", "CLP", "CAD", "AUD", "CHF", "CNY", "MXN", "INR", "RUB", "ZAR");

    public ConversorDeMonedaControlador(String apiKey) {
        this.apiServicio = new APIServicio(apiKey);
        this.vista = new MenuVista();
    }

    public void iniciar() {
        boolean salir = false;
        int opcion;

        while (!salir) {
            vista.mostrarMenu();
            try {
                opcion = Integer.parseInt(leer.nextLine());
                double valorAConvertir;

                switch (opcion) {
                    case 1: {
                        moneda = new Moneda("USD", "ARS");
                        break;
                    }
                    case 2: {
                        moneda = new Moneda("ARS", "USD");
                        break;
                    }
                    case 3: {
                        moneda = new Moneda("USD", "BRL");
                        break;
                    }
                    case 4: {
                        moneda = new Moneda("BRL", "USD");
                        break;
                    }
                    case 5: {
                        moneda = new Moneda("EUR", "ARS");
                        break;
                    }
                    case 6: {
                        moneda = new Moneda("ARS", "EUR");
                        break;
                    }
                    case 7: {
                        moneda = new Moneda("GBP", "EUR");
                        break;
                    }
                    case 8: {
                        moneda = new Moneda("EUR", "GBP");
                        break;
                    }
                    case 9: {
                        otrasOpcionesDeMoneda();
                        continue;
                    }
                    case 10: {
                        mostrarHistorial();
                        continue;
                    }
                    case 11: {
                        salir = true;
                        continue;
                    }
                    default: {
                        System.out.println("\nOpción inválida.");
                        continue;
                    }
                }
                valorAConvertir = leerValorAConvertir();
                convertir(moneda, valorAConvertir);
            } catch (NumberFormatException e) {
                System.out.println("\nEntrada no válida. Por favor, ingresa un número.");
            }
        }
    }

    private double leerValorAConvertir() {
        double valor = 0.0;
        boolean esValido = false;

        while (!esValido) {
            System.out.print("\nIngrese el valor que desea convertir: ");
            String entrada = leer.nextLine();

            // Reemplazar la coma por el punto si el usuario ingresa un número con coma decimal
            entrada = entrada.replace(",", ".");

            try {
                valor = Double.parseDouble(entrada);

                // Validar que el valor no sea negativo o cero
                if (valor > 0) {
                    esValido = true;  // Si el parseo es exitoso, el valor es válido
                } else {
                    System.out.println("\nError: El valor debe ser un número positivo. Ingrese el valor nuevamente.");
                }

            } catch (NumberFormatException e) {
                System.out.println("\nError: Entrada no válida. Por favor, ingresa un número válido.");
            }
        }
        return valor;
    }

    private void convertir(Moneda moneda, double valorAConvertir) {
        double tasaDeCambio = apiServicio.consultarTasaDeCambio(moneda.monedaBase(), moneda.monedaDestino());

        //Validación de tasaDeCambio
        if (tasaDeCambio == 0.0) {
            System.out.println("No se pudo realizar la conversión. Verifica los códigos de las monedas ingresadas.");
            return;
        }

        double valorConvertido = valorAConvertir * tasaDeCambio;
        String resultado = String.format("El valor de %.2f [%s] corresponde al valor final de =>>> %.2f [%s]%n", valorAConvertir, moneda.monedaBase(), valorConvertido, moneda.monedaDestino());
        System.out.printf(resultado);

        //Registro de conversion y marca de tiempo
        String marcaTiempo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        historialConversiones.add(marcaTiempo + " Tasa de cambio: " + tasaDeCambio + " - " + resultado);
    }

    private void mostrarHistorial() {
        System.out.println("""
                
                --------------------------------------------
                Historial de conversiones:""");
        if (historialConversiones.isEmpty()) {
            System.out.println("No hay historial de conversiones realizadas.");
        } else {
            for (String conversion : historialConversiones) {
                System.out.println(conversion);
            }
        }
        System.out.println("--------------------------------------------");
    }

    private void otrasOpcionesDeMoneda() {
        //Lista de monedas...
        vista.mostrarMonedasDisponibles();

        String monedaBase = "";
        String monedaDestino = "";

        // Leer los códigos de monedas a convertir y validar con metodo contains
        // Validación para la moneda de origen
        while (!MONEDAS_DISPONIBLES.contains(monedaBase)) {
            System.out.print("\nIngrese el código de la moneda de origen (Ejemplo: USD): ");
            monedaBase = leer.nextLine().toUpperCase();
            if (!MONEDAS_DISPONIBLES.contains(monedaBase)) {
                System.out.println("Moneda no válida. Por favor, ingresa un código ISO 4217 válido.");
            }
        }

        // Validación para la moneda de destino
        while (!MONEDAS_DISPONIBLES.contains(monedaDestino)) {
            System.out.print("Ingrese el código de la moneda de destino (Ejemplo: EUR): ");
            monedaDestino = leer.nextLine().toUpperCase();
            if (!MONEDAS_DISPONIBLES.contains(monedaDestino)) {
                System.out.println("Moneda no válida. Por favor, ingresa un código ISO 4217 válido.");
            }
        }

        moneda = new Moneda(monedaBase, monedaDestino);
        double valorAConvertir = leerValorAConvertir();
        convertir(moneda, valorAConvertir);
    }
}
