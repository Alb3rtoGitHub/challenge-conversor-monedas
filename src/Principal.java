import controlador.ConversorDeMonedaControlador;

public class Principal {
    public static void main(String[] args) {
        // La clave API
        String apiKey = "5e1905de1c64e3ceb9ddf975";
        // Instancia del Controlador (lógica de negocio)
        ConversorDeMonedaControlador controlador = new ConversorDeMonedaControlador(apiKey);

        // Inicia la ejecución del controlador
        controlador.iniciar();
    }
}
