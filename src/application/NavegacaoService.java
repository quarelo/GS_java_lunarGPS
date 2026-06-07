package application;

import domain.Obstaculo;
import domain.Posicao;
import infrastructure.PosicaoRepositorio;

import java.time.LocalDateTime;
import java.util.List;

public class NavegacaoService {

    private static final double RAIO_LUNAR_KM = 1737.4;

    private final PosicaoRepositorio repositorio = new PosicaoRepositorio();

    public void registrarPosicao(Posicao posicao) {
        posicao.setTimestamp(LocalDateTime.now());
        repositorio.salvar(posicao);
        System.out.println("[OK] Posicao registrada com ID: " + posicao.getId());
    }

    public Posicao buscarPosicaoPorId(int id) {
        return repositorio.buscarPorId(id);
    }

    public List<Posicao> listarPosicoes() {
        return repositorio.listarTodos();
    }

    // Overload 1: calcula distancia entre coordenadas de superficie
    public double calcularDistancia(float lat1, float lon1, float lat2, float lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAIO_LUNAR_KM * c;
    }

    // Overload 2: calcula distancia 3D (latitude, longitude e altitude)
    public double calcularDistancia(float lat1, float lon1, float alt1,
                                    float lat2, float lon2, float alt2) {
        double distSup = calcularDistancia(lat1, lon1, lat2, lon2);
        double deltaAltKm = (alt2 - alt1) / 1000.0;
        return Math.sqrt(distSup * distSup + deltaAltKm * deltaAltKm);
    }

    // Overload 3: calcula distancia a partir de dois objetos Posicao
    public double calcularDistancia(Posicao p1, Posicao p2) {
        return calcularDistancia(
                p1.getLatitude(), p1.getLongitude(), p1.getAltitude(),
                p2.getLatitude(), p2.getLongitude(), p2.getAltitude()
        );
    }

    public double calcularConsumoEnergetico(double distanciaKm, String tipoRota) {
        double fator;
        switch (tipoRota.toUpperCase()) {
            case "DIRETA":      fator = 0.5; break;
            case "ALTERNATIVA": fator = 0.7; break;
            case "EMERGENCIA":  fator = 1.2; break;
            default:            fator = 0.6; break;
        }
        return distanciaKm * fator;
    }

    public boolean rotaContemObstaculo(float lat1, float lon1, float lat2, float lon2,
                                        List<Obstaculo> obstaculos) {
        float midLat = (lat1 + lat2) / 2;
        float midLon = (lon1 + lon2) / 2;
        for (Obstaculo o : obstaculos) {
            double distGraus = Math.sqrt(
                    Math.pow(o.getLatitude() - midLat, 2) + Math.pow(o.getLongitude() - midLon, 2));
            double distMetros = distGraus * 111000;
            if (distMetros <= o.getRaioImpacto()) return true;
        }
        return false;
    }
}
