import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CovertidorDeMonedas {
    private static double obtenerTipoCambio() throws IOException {
        URL url = new URL("https://api.exchangerate-api.com/v4/latest/USD");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject ratesObject = jsonObject.getAsJsonObject("rates");
        JsonElement mxnRateElement = ratesObject.get("VES");
        return mxnRateElement.getAsDouble();
    }

    private static double obtenerTipoCambio1() throws IOException {
        URL url = new URL("https://api.exchangerate-api.com/v4/latest/EUR");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
        JsonObject ratesObject = jsonObject.getAsJsonObject("rates");
        JsonElement mxnRateElement = ratesObject.get("VES");
        return mxnRateElement.getAsDouble();
    }

    public static void main(String[] args) throws IOException {
        double tipoCambio;
        double tipoCambio1;
        try {
            tipoCambio = obtenerTipoCambio();
            tipoCambio1 = obtenerTipoCambio1();
        } catch (IOException e) {
            tipoCambio = 36.5;
            tipoCambio1 = 36.5;
            System.err.println("Error buscando tipo de cambio: " + e.getMessage());
            return;
        }

        Scanner teclado = new Scanner(System.in);
        int eleccionUsuario;

        do {
            String bienvenida = """
             ***********************************************************************************************************
             *                                      Convertidor de Monedas                                             *
             *                                                                                                         *
             * Elija una de las Siguientes Opciones:                                                                   *
             *                                                                                                         *
             *   1.- Convertir Bolivares Venezolanos a Dolar Americano                                                 *
             *   2.- Convertir Dolar Americano a Bolivar Venezolano                                                    *
             *   3.- Convertir Bolivares Venezolanos a EURO                                                            *
             *   4.- Convertir EURO a Bolivar Venezolano                                                               *
             *   5.- Salir                                                                                             *
             *                                                                                                         *
             ***********************************************************************************************************
             """;

            System.out.println(bienvenida);
            System.out.println("Por favor digita el número acorde a tu elección:");
            eleccionUsuario = teclado.nextInt();

            switch (eleccionUsuario) {
                case 1:
                    System.out.print("Ingrese la cantidad en Bolivares Venezolanos: ");
                    double cantidadBs= teclado.nextDouble();
                    double dolares = cantidadBs / tipoCambio;
                    System.out.println("La cantidad en dólares americanos es: " + dolares);
                    break;
                case 2:
                    System.out.print("Ingrese la cantidad en dólares americanos: ");
                    double dolaresAmericanos = teclado.nextDouble();
                    double conversionPeso = dolaresAmericanos * tipoCambio;
                    System.out.println("La cantidad en Bolivares Venezolanos es: " + conversionPeso);
                    break;
                case 3:
                    System.out.print("Ingrese la cantidad en Bolivares Venezolanos: ");
                    double cantidadBs1 = teclado.nextDouble();
                    double dolares1 = cantidadBs1 / tipoCambio1;
                    System.out.println("La cantidad en dólares americanos es: " + dolares1);
                    break;
                case 4:
                    System.out.print("Ingrese la cantidad en dólares americanos: ");
                    double dolaresAmericanos1 = teclado.nextDouble();
                    double conversionPeso1 = dolaresAmericanos1 * tipoCambio1;
                    System.out.println("La cantidad en Bolivares Venezolanos es: " + conversionPeso1);
                    break;
                case 5:
                    System.out.println("Gracias por su preferencia");
                    break;
                default:
                    System.out.println("Opción no válida. Seleccione del (1 al 6)");
            }
        } while (eleccionUsuario != 5);

        teclado.close();
    }
}
