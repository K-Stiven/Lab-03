import java.util.Timer;
import java.util.TimerTask;

public class CronometroConAlarma {
    
    private Timer timer;
    private Timer alarmaTimer;
    private int segundosTranscurridos;
    private int alarmaIntervalo;
    private boolean alarmaActiva;
    
    public CronometroConAlarma() {
        timer = new Timer();
        alarmaTimer = new Timer();
        segundosTranscurridos = 0;
        alarmaActiva = false;
    }
    
    public void iniciarCronometro(int tiempoEnSegundos, int intervaloAlarmaEnSegundos) {
        segundosTranscurridos = 0;
        
        // Configurar la alarma para que se active después del tiempo dado
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                alarmaActiva = true;
                alarmaIntervalo = intervaloAlarmaEnSegundos;
                activarAlarma();
            }
        }, tiempoEnSegundos * 1000); // Convertir segundos a milisegundos

        // Configurar el cronómetro para que se actualice cada segundo
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                segundosTranscurridos++;
                mostrarHora();
            }
        }, 0, 1000); // Ejecutar cada segundo
    }
    
    private void activarAlarma() {
        alarmaTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (alarmaActiva) {
                    System.out.println("¡Alarma!");
                }
            }
        }, 0, alarmaIntervalo * 1000); // Convertir segundos a milisegundos
    }
    
    public void detenerCronometro() {
        timer.cancel();
        alarmaTimer.cancel();
    }
    
    private void mostrarHora() {
        int horas = segundosTranscurridos / 3600;
        int minutos = (segundosTranscurridos % 3600) / 60;
        int segundos = segundosTranscurridos % 60;
        System.out.printf("Tiempo transcurrido: %02d:%02d:%02d%n", horas, minutos, segundos);
    }
    
    public static void main(String[] args) {
        CronometroConAlarma cronometro = new CronometroConAlarma();
        // Configurar el cronómetro para iniciar en 10 segundos y alarma cada 5 segundos después de 10 segundos
        cronometro.iniciarCronometro(10, 5);
        
        // Esperar algunos segundos para ver el resultado
        try {
            Thread.sleep(30000); // Esperar 30 segundos para demostrar la funcionalidad
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Detener el cronómetro y la alarma
        cronometro.detenerCronometro();
    }
}
