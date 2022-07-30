package org.andemar.junit5_app.ejemplos;

import org.andemar.junit5_app.ejemplos.exceptions.DineroInsuficienteException;
import org.andemar.junit5_app.ejemplos.models.Banco;
import org.andemar.junit5_app.ejemplos.models.Cuenta;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;
//@TestInstance(TestInstance.Lifecycle.PER_CLASS) Esto hace que solo se cree una instancia y no una nueva por test
class AccountTest {

    Cuenta cuenta;

    private TestInfo testInfo;

    private TestReporter testReporter;

    @BeforeAll
    static void beforeAll() {
        System.out.println("Inicializando la clase test...");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando la clase test...");
    }

    @BeforeEach
    void initMetodoTest(TestInfo testInfo, TestReporter testReporter) {
        this.testInfo = testInfo;
        this.testReporter = testReporter;

        testReporter.publishEntry(" Ejecutando: " + testInfo.getDisplayName() + " " + testInfo.getTestMethod().get().getName() + " con las etiquetas " + testInfo.getTags());
        System.out.println("Test: " + testInfo.getDisplayName());
        this.cuenta = new Cuenta("Andemar", new BigDecimal(1000.12345));
        System.out.println("Iniciando el metodo.");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el metodo.");
    }

    @Tag("cuenta")
    @Nested
    @DisplayName("Test de cuenta: ")
    class CuentaTestNombreYSaldo {
        @Test
        @DisplayName("Probando nombre")
        void testAccountName() {
            testReporter.publishEntry(testInfo.getTags().toString());
            if(testInfo.getTags().contains("cuenta")) {
                testReporter.publishEntry("Hacer algo con la etiqueta cuenta");
            }
//        cuenta.setPersona("Andemar");
            String expected = "Andemar";
            String actual = cuenta.getPersona();

            // Si se envia el string el valor se creara en memoria, al enviarse
            // la lambda se ejecuta solo si la prueba falla
            assertNotNull(cuenta, () -> "La cuenta no puede ser nula");
            assertEquals(expected, actual, () -> "El nombre de la cuenta no es correcto");
            assertTrue(actual.equals(expected), () -> "nombre cuenta esperada deber ser igual a nombre cuenta real");
        }

        @Test
        @DisplayName("El saldo no sea null, mayor que cero, valor esperado")
        void testSaldoCuenta() {
            Cuenta account = new Cuenta("Andemar", new BigDecimal("1000.12345"));
            assertEquals(1000.12345, account.getSaldo().doubleValue());
            assertFalse(account.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        }

        @Test
        void testReferenciaDeCuenta() {
            cuenta = new Cuenta("Andemar Doe", new BigDecimal("8900.9997"));
            Cuenta cuenta2 = new Cuenta("Andemar Doe", new BigDecimal("8900.9997"));

//        assertNotEquals(cuenta, cuenta2);
            assertEquals(cuenta, cuenta2);
        }
    }

    @Nested
    class CuentaOperarionesTest {

        @Tag("cuenta")
        @Test
        void testDebitoAccount() {
            cuenta = new Cuenta("Andemar", new BigDecimal("1000.12345"));
            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(900, cuenta.getSaldo().intValue());
            assertEquals("900.12345", cuenta.getSaldo().toPlainString());
        }

        @Test
        void testCreditoAccount() {
            cuenta = new Cuenta("Andemar", new BigDecimal("1000.12345"));
            cuenta.credito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(1100, cuenta.getSaldo().intValue());
            assertEquals("1100.12345", cuenta.getSaldo().toPlainString());
        }

        @Tag("cuenta")
        @Tag("Banco")
        @Test
        void testTransferirDineroCuentas() {
            Cuenta cuenta1 = new Cuenta("Andemar", new BigDecimal("2500"));
            Cuenta cuenta2 = new Cuenta("Mashiro", new BigDecimal("1500.8989"));

            Banco banco = new Banco();
            banco.setNombre("Banco de la Tierra");
            banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));
            assertEquals("1000.8989", cuenta2.getSaldo().toPlainString());
            assertEquals("3000", cuenta1.getSaldo().toPlainString());
        }
    }

    @Nested
    class SistemaOperativo {
        @Test
        @EnabledOnOs(OS.WINDOWS)
        void testSoloWindows() { }

        @Test
        @EnabledOnOs({OS.LINUX, OS.MAC})
        void testSoloLinuxMac() { }

        @Test
        @DisabledOnOs(OS.WINDOWS)
        void testNoWindows() { }
    }

    @Nested
    class JavaVersionTest {
        @Test
        @EnabledOnJre(JRE.JAVA_18)
        void testSoloJdk18() { }
    }

    @Nested
    class SistemPropertyTest {

        @Test
        void imprimirSytemProperties() {
            Properties properties = System.getProperties();
            properties.forEach((key, value) -> System.out.println(key + ": " + value));
        }

        // Variables de la JVM
        @Test
        @EnabledIfSystemProperty(named = "java.version", matches = "18.0.1.1")
        void testJavaVersion() { }

        @Test
        @DisabledIfSystemProperty(named = "os.arch", matches = ".*32.*")
        void testSolo64() {
        }

        @Test
        @EnabledIfSystemProperty(named = "ENV", matches = ".*dev.*")
        void testDev() { }
    }

    @Nested
    class VariableAmbienteTest {
        @Test
        void imprimirVariablesAmbiente() {
            Map<String, String> getenv = System.getenv();
            getenv.forEach((key, value) -> System.out.println(key + ": " + value));
        }

        // Pruebas con las variables del sistema
        @Test
        @EnabledIfEnvironmentVariable(named = "JAVA_HOME", matches = ".*jdk-17.*")
        void testJavaHome() { }

        @Test
        @EnabledIfEnvironmentVariable(named = "PROCESSOR_LEVEL", matches = "6")
        void testProcesadores() { }
    }

    @Nested
    class OtrosTest {

        @Test
        void testDineroInsuficienteExceptionCuenta() {
            cuenta = new Cuenta("Andemar", new BigDecimal("1000.12345"));
            Exception exception = assertThrows(DineroInsuficienteException.class, ()-> {
                cuenta.debito(new BigDecimal("1500"));
            });
            String actual = exception.getMessage();
            String esperado = "Dinero Insuficiente";
            assertEquals(esperado, actual);
        }

        @Test
            //@Disabled
        void testRelacionesBancoCuentas() {
            //fail();
            Cuenta cuenta1 = new Cuenta("Andemar", new BigDecimal("2500"));
            Cuenta cuenta2 = new Cuenta("Mashiro", new BigDecimal("1500.8989"));

            Banco banco = new Banco();
            banco.addCuenta(cuenta1);
            banco.addCuenta(cuenta2);

            String name = "Banco de la Tierra";
            banco.setNombre(name);
            banco.transferir(cuenta2, cuenta1, new BigDecimal("500"));

            assertAll(
                    () -> assertEquals("1000.8989", cuenta2.getSaldo().toPlainString()),
                    () -> assertEquals("3000", cuenta1.getSaldo().toPlainString()),
                    () -> assertEquals(2, banco.getCuentas().size()),
                    () -> assertEquals(name, cuenta1.getBanco().getNombre()),
                    () -> assertEquals("Andemar", banco.getCuentas().stream().filter(c -> c.getPersona().equals("Andemar")).findFirst().get().getPersona()),
                    () -> assertTrue(banco.getCuentas().stream().anyMatch(c -> c.getPersona().equals("Andemar")))
            );
        }

        @Test
        void testSaldoCuentaDev() {
            boolean isDev = "dev".equals(System.getenv("env"));

            // Si la condicion se cumple, se ejecuta el test
            assumeTrue(isDev);
            Cuenta account = new Cuenta("Andemar", new BigDecimal("1000.12345"));
            assertEquals(1000.12345, account.getSaldo().doubleValue());
            assertFalse(account.getSaldo().compareTo(BigDecimal.ZERO) < 0);
        }


        @Test
        void testSaldoCuentaDev2() {
            boolean isDev = "dev".equals(System.getenv("env"));
            Cuenta account = new Cuenta("Andemar", new BigDecimal("1000.12345"));

            // Si la condicion se cumple, se ejecuta los test dentro de la expresion
            assumingThat(isDev, () -> {
                assertEquals(1000.12345, account.getSaldo().doubleValue());
                assertFalse(account.getSaldo().compareTo(BigDecimal.ZERO) < 0);
            });
        }
    }

    @Nested
    class LoopTests {

        @DisplayName("Probando debito cuenta Repetir!!")
        @RepeatedTest(value = 5, name = "{displayName}: Repeticion {currentRepetition} de {totalRepetitions}")
        void testDebitoCuentaRepetir(RepetitionInfo info) {

            if(info.getCurrentRepetition() == 3) {
                System.out.println("Estamos en la repeticion : " + info.getCurrentRepetition());
            }

            cuenta.debito(new BigDecimal(100));
            assertNotNull(cuenta.getSaldo());
            assertEquals(23, cuenta.getSaldo().intValue());
            assertEquals("23", cuenta.getSaldo().toPlainString());
        }
    }

    @Tag("param")
    @Nested
    class pruebasParametrizadas {
        @ParameterizedTest(name = "Numero {index} ejecutando con valor {argumentsWithNames}")
        @ValueSource(strings = {"100", "200", "300", "500", "700", "1000"})
        void testDebitoCuentaValueSource(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "Numero {index} ejecutando con valor {argumentsWithNames}")
        @CsvSource({"1,100", "2,200", "3,300", "4,500", "5,700", "6,1000"})
        void testDebitoCuentaCsvSource(String index, String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "Numero {index} ejecutando con valor {argumentsWithNames}")
        @CsvSource({"200,100", "300,250", "400,399", "600,500", "750,700", "1001,1000"})
        void testDebitoCuentaCsvSource2(String saldo, String monto) {
            cuenta.setSaldo(new BigDecimal(saldo));
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "Numero {index} ejecutando con valor {argumentsWithNames}")
        @CsvFileSource(resources = "/data.csv")
        void testDebitoCuentaCsvFileSource(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }


        @ParameterizedTest(name = "Numero {index} ejecutando con valor {argumentsWithNames}")
        @MethodSource("montoList")
        void testDebitoCuentaMethodSource(String monto) {
            cuenta.debito(new BigDecimal(monto));
            assertNotNull(cuenta.getSaldo());
            assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
        }

        static List<String> montoList() {
            return Arrays.asList("100", "200", "300", "500", "700", "1000");
        }

    }

    @Nested
    @Tag("timeout")
    class timeOut {
        @Test
        @Timeout(1)
        void timeOut() throws InterruptedException {
            TimeUnit.SECONDS.sleep(2);
        }

        @Test
        @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
        void timeOutMs() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }

        @Test
        void testTimeoutAssertions() {
            assertTimeout(Duration.ofMillis(500), () -> {
                TimeUnit.MILLISECONDS.sleep(650);
            });
        }
    }



}